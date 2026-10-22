import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * QuizServlet  (Tomcat 9, javax.servlet)
 *   doGet  : index.html sends the topic -> servlet shows 10 random questions from MySQL
 *   doPost : the quiz form is submitted -> servlet checks answers in MySQL and shows the score
 */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {

    // change PASS to your MySQL root password
    private static final String URL  = "jdbc:mysql://localhost:3306/quizdb?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "123456";

    private static final String STYLE =
        "<style>body{font-family:Arial;background:#f2f4f8}"
      + ".box{max-width:600px;margin:30px auto;padding:20px;background:white;border-radius:8px}"
      + ".right{color:green}.wrong{color:red}</style>";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String topic = req.getParameter("topic");
        out.println("<html><head><title>Quiz</title>" + STYLE + "</head><body><div class='box'>");
        out.println("<h2>" + topic + " Quiz</h2>");
        out.println("<form action='quiz' method='post'>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement(
                "SELECT id, question, option1, option2, option3, option4 " +
                "FROM questions WHERE topic = ? ORDER BY RAND() LIMIT 10");
            ps.setString(1, topic);
            ResultSet rs = ps.executeQuery();
            int n = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<p><b>Q" + n + ". " + rs.getString("question") + "</b><br>");
                out.println("<input type='hidden' name='ids' value='" + id + "'>");
                for (int i = 0; i < 4; i++) {
                    out.println("<input type='radio' name='q" + id + "' value='" + i + "'> "
                              + rs.getString("option" + (i + 1)) + "<br>");
                }
                out.println("</p>");
                n++;
            }
            con.close();
            if (n == 1) {
                out.println("<p>No questions found for this topic.</p>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
        out.println("<input type='submit' value='Submit Answers'>");
        out.println("</form></div></body></html>");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String[] ids = req.getParameterValues("ids");   // ids of the questions that were shown
        out.println("<html><head><title>Result</title>" + STYLE + "</head><body><div class='box'>");
        if (ids == null) {
            out.println("<p>No answers received.</p><a href='index.html'>Back</a></div></body></html>");
            return;
        }
        int score = 0;
        StringBuilder details = new StringBuilder();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement(
                "SELECT question, option1, option2, option3, option4, answer, explanation " +
                "FROM questions WHERE id = ?");
            int n = 1;
            for (String id : ids) {
                ps.setInt(1, Integer.parseInt(id));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int correct = rs.getInt("answer");
                    String chosen = req.getParameter("q" + id);   // null if not answered
                    String correctText = rs.getString("option" + (correct + 1));
                    details.append("<p><b>Q" + n + ". " + rs.getString("question") + "</b><br>");
                    if (chosen != null && Integer.parseInt(chosen) == correct) {
                        score++;
                        details.append("<span class='right'>Correct: " + correctText + "</span><br>");
                    } else {
                        details.append("<span class='wrong'>Wrong. Correct answer: " + correctText + "</span><br>");
                    }
                    details.append("<i>" + rs.getString("explanation") + "</i></p>");
                    n++;
                }
            }
            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
        out.println("<h2>You scored " + score + " out of " + ids.length + "</h2>");
        out.println(details);
        out.println("<a href='index.html'>Try Another Quiz</a>");
        out.println("</div></body></html>");
    }
}
