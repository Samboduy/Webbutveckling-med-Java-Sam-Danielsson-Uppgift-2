package Servlets;

import Models.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet (urlPatterns = "/attendance")
public class AttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        //String sql ="SELECT * FROM attendance";
        String sql = "SELECT s.Fname,s.Id,c.Name,c.Id FROM attendance a JOIN students s ON a.StudentId = s.Id JOIN courses c ON a.CourseId = c.Id";
        out.println("<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"nav-cont\">\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"nav-cont\">\n" +
                "        <nav>\n" +
                "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                "        </nav>\n" +
                "   </div>" +
                "    <div class=\"text\">\n" +
                "    <h1>Attendance</h1>\n" +
                "    </div>");
        out.println("<div class=\"table-cont\">\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <td>Id</td>\n" +
                "                <td>Name</td>\n" +
                "                <td>Id</td>\n" +
                "                <td>Course</td>\n" +
                "            </tr>");
        try {
            Connection con = Database.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                out.println("<tr>" +
                        "<th>" + rs.getString("s.Id") + " </th> " +
                        "<th>" + rs.getString("Fname") + " </th> " +
                        "<th>" + rs.getString("c.Id") + "</th>"+
                        "<th>" + rs.getString("Name") + "</th>"+
                        "</tr>");
            }
            out.println("</table>");
            out.println("</div>");
            out.println("</body>\n" +
                    "</div>\n" +
                    "</html>");
        } catch (SQLException e) {
            Database.PrintSQLException(e);
        }


    }
}
