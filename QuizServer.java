import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * QuizServer: a small web server for the Ten Questions project, written with the Java
 * standard library only (no Maven, no Tomcat, no extra jar files).
 *
 *   1. Serves index.html, style.css and script.js from the folder it is started in.
 *   2. Accepts POST /api/quiz with the prompt as plain text, forwards it to the Claude
 *      Messages API using the secret key from the environment, and returns the reply text.
 *
 * The key stays on the server and never reaches the browser.
 *
 * Open:  http://localhost:8080
 */
public class QuizServer {

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String API_KEY = System.getenv("ANTHROPIC_API_KEY");
    private static final String MODEL = System.getenv().getOrDefault("ANTHROPIC_MODEL", "claude-sonnet-5-5");
    private static final int PORT = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
    private static final int MAX_PROMPT_BYTES = 30_000;

    private static final Path ROOT = Path.of("").toAbsolutePath();

    // Only these three files are ever served, which also prevents path traversal.
    private static final Map<String, String> FILES = Map.of(
            "/index.html", "text/html; charset=utf-8",
            "/projectstyle.css", "text/css; charset=utf-8",
            "/script.js", "text/javascript; charset=utf-8");

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    public static void main(String[] args) throws IOException {
        // Listen on the local machine only, so nobody else on the network can use your key.
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", PORT), 0);
        server.createContext("/", QuizServer::handleStatic);
        server.createContext("/api/quiz", QuizServer::handleQuiz);
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        System.out.println("Ten Questions is running at http://localhost:" + PORT);
        System.out.println("Serving files from: " + ROOT);
        System.out.println("Model: " + MODEL);
        if (API_KEY == null || API_KEY.isBlank()) {
            System.out.println("WARNING: ANTHROPIC_API_KEY is not set. The page will open, "
                    + "but quizzes cannot be generated.");
        }
    }

    // ---------------------------------------------------------------- static files
    private static void handleStatic(HttpExchange ex) throws IOException {
        try {
            if (!ex.getRequestMethod().equals("GET")) {
                send(ex, 405, "text/plain; charset=utf-8", "Method not allowed");
                return;
            }
            String path = ex.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            String type = FILES.get(path);
            Path file = type == null ? null : ROOT.resolve(path.substring(1));
            if (file == null || !Files.isRegularFile(file)) {
                send(ex, 404, "text/plain; charset=utf-8", "Not found");
                return;
            }
            send(ex, 200, type, Files.readAllBytes(file));
        } finally {
            ex.close();
        }
    }

    // ---------------------------------------------------------------- quiz endpoint
    private static void handleQuiz(HttpExchange ex) throws IOException {
        try {
            if (!ex.getRequestMethod().equals("POST")) {
                send(ex, 405, "text/plain; charset=utf-8", "Use POST");
                return;
            }
            if (API_KEY == null || API_KEY.isBlank()) {
                send(ex, 500, "text/plain; charset=utf-8",
                        "The server has no ANTHROPIC_API_KEY set. Stop the server, set the key and start it again.");
                return;
            }

            byte[] bodyBytes = ex.getRequestBody().readNBytes(MAX_PROMPT_BYTES + 1);
            if (bodyBytes.length > MAX_PROMPT_BYTES) {
                send(ex, 413, "text/plain; charset=utf-8", "The request is too large.");
                return;
            }
            String prompt = new String(bodyBytes, StandardCharsets.UTF_8).trim();
            if (prompt.isEmpty()) {
                send(ex, 400, "text/plain; charset=utf-8", "The prompt is empty.");
                return;
            }

            String json = "{\"model\":\"" + jsonEscape(MODEL) + "\",\"max_tokens\":4096,"
                    + "\"messages\":[{\"role\":\"user\",\"content\":\"" + jsonEscape(prompt) + "\"}]}";

            HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL))
                    .timeout(Duration.ofSeconds(120))
                    .header("content-type", "application/json")
                    .header("x-api-key", API_KEY)
                    .header("anthropic-version", "2023-06-01")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response;
            try {
                response = HTTP.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                send(ex, 502, "text/plain; charset=utf-8", "The request was interrupted.");
                return;
            } catch (IOException e) {
                send(ex, 502, "text/plain; charset=utf-8", "Could not reach the Claude service: " + e.getMessage());
                return;
            }

            if (response.statusCode() == 200) {
                String text = extractString(response.body(), "text");
                if (text == null) {
                    send(ex, 502, "text/plain; charset=utf-8", "The reply contained no text.");
                } else {
                    send(ex, 200, "text/plain; charset=utf-8", text);
                }
            } else {
                String message = extractString(response.body(), "message");
                int status = switch (response.statusCode()) {
                    case 401, 403 -> 401;
                    case 429 -> 429;
                    default -> 502;
                };
                send(ex, status, "text/plain; charset=utf-8",
                        message != null ? message : "The Claude service returned status " + response.statusCode());
            }
        } finally {
            ex.close();
        }
    }

    // ---------------------------------------------------------------- helpers
    private static void send(HttpExchange ex, int status, String type, String body) throws IOException {
        send(ex, status, type, body.getBytes(StandardCharsets.UTF_8));
    }

    private static void send(HttpExchange ex, int status, String type, byte[] body) throws IOException {
        ex.getResponseHeaders().set("Content-Type", type);
        ex.getResponseHeaders().set("Cache-Control", "no-store");
        ex.sendResponseHeaders(status, body.length);
        try (OutputStream out = ex.getResponseBody()) {
            out.write(body);
        }
    }

    /** Escapes a Java string so it can be placed inside a JSON string literal. */
    private static String jsonEscape(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                default -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /** Finds the first "key":"value" pair in a JSON text and returns the decoded value, or null. */
    private static String extractString(String json, String key) {
        String needle = "\"" + key + "\":\"";
        int at = json.indexOf(needle);
        if (at < 0) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = at + needle.length(); i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"') return sb.toString();
            if (c != '\\') { sb.append(c); continue; }
            if (++i >= json.length()) break;
            char e = json.charAt(i);
            switch (e) {
                case 'n' -> sb.append('\n');
                case 'r' -> sb.append('\r');
                case 't' -> sb.append('\t');
                case 'b' -> sb.append('\b');
                case 'f' -> sb.append('\f');
                case 'u' -> {
                    if (i + 4 >= json.length()) return null;
                    sb.append((char) Integer.parseInt(json.substring(i + 1, i + 5), 16));
                    i += 4;
                }
                default -> sb.append(e); // covers \" \\ and \/
            }
        }
        return null;
    }
}
