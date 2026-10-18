import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * QuizServer - a tiny web server (only core Java, no extra libraries).
 * Job 1: send index.html, style.css and script.js to the browser.
 * Job 2: receive a prompt at /api/quiz, forward it to the Claude API,
 *        and send Claude's reply back to the browser.
 * The API key is read from an environment variable so it never goes to the browser.
 *
 * Run:  javac QuizServer.java
 *       java QuizServer          (then open http://localhost:8080)
 */
public class QuizServer {

    static String API_KEY = System.getenv("ANTHROPIC_API_KEY");
    static String API_URL = "https://api.anthropic.com/v1/messages";
    static String MODEL = "claude-sonnet-5-5";

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", QuizServer::handleFiles);      // every normal page request
        server.createContext("/api/quiz", QuizServer::handleQuiz); // quiz requests
        server.start();
        System.out.println("Server running at http://localhost:8080");
    }

    // ---------- Job 1: serve the three website files ----------
    static void handleFiles(HttpExchange ex) throws Exception {
        String path = ex.getRequestURI().getPath();
        if (path.equals("/")) path = "/index.html";

        String type;
        if (path.equals("/index.html")) type = "text/html; charset=utf-8";
        else if (path.equals("/style.css")) type = "text/css; charset=utf-8";
        else if (path.equals("/script.js")) type = "text/javascript; charset=utf-8";
        else {
            send(ex, 404, "text/plain", "Not found");
            return;
        }

        byte[] data = Files.readAllBytes(Path.of(path.substring(1))); // remove leading "/"
        ex.getResponseHeaders().set("Content-Type", type);
        ex.sendResponseHeaders(200, data.length);
        OutputStream out = ex.getResponseBody();
        out.write(data);
        out.close();
    }

    // ---------- Job 2: talk to the Claude API ----------
    static void handleQuiz(HttpExchange ex) throws Exception {
        String prompt = new String(ex.getRequestBody().readAllBytes(), "UTF-8");

        // Build the JSON text that the Claude API expects
        String json = "{\"model\":\"" + MODEL + "\",\"max_tokens\":4096,"
                + "\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(prompt) + "\"}]}";

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL))
                .header("content-type", "application/json")
                .header("x-api-key", API_KEY)
                .header("anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            send(ex, 200, "text/plain", extractText(response.body()));
        } else {
            send(ex, 500, "text/plain", "Claude API error: " + response.body());
        }
    }

    // ---------- helper methods ----------
    static void send(HttpExchange ex, int status, String type, String body) throws Exception {
        byte[] data = body.getBytes("UTF-8");
        ex.getResponseHeaders().set("Content-Type", type + "; charset=utf-8");
        ex.sendResponseHeaders(status, data.length);
        OutputStream out = ex.getResponseBody();
        out.write(data);
        out.close();
    }

    // Makes a normal string safe to place inside a JSON string (quotes, newlines, backslashes)
    static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Finds  "text":"....."  in the API reply and returns the text with escapes undone
    static String extractText(String json) {
        String key = "\"text\":\"";
        int start = json.indexOf(key) + key.length();
        StringBuilder result = new StringBuilder();
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"') break;                  // closing quote = end of text
            if (c == '\\') {                      // escape sequence like \n or \"
                i++;
                char next = json.charAt(i);
                if (next == 'n') result.append('\n');
                else if (next == 't') result.append('\t');
                else if (next == 'u') {           // \uXXXX unicode character
                    result.append((char) Integer.parseInt(json.substring(i + 1, i + 5), 16));
                    i += 4;
                } else result.append(next);       // handles \" and \\
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
