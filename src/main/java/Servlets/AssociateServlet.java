package Servlets;

import Models.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet (urlPatterns = "/associate")
public class AssociateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<html lang=\"en\">\n" +
                "                <head>\n" +
                "                    <meta charset=\"UTF-8\\\">\n" +
                "                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "                    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                "                    <title>Document</title>\n" +
                "                </head>\n" +
                "                <div class=\"container\">\n" +
                "                    <body>\n" +
                "                        <div class=\"nav-cont\">\n" +
                "        <nav>\n" +
                "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                "        </nav>\n" +
                "                        </div>\n" +
                "                        <div class=\"text\">\n" +
                "                            <h1>Add Attendance</h1>\n" +
                "                        </div>\n" +
                "                        <div class=\"table-cont\">\n" +
                "                            <table>\n" +
                "                                <tr>\n" +
                "                                    <th>Id</th>\n" +
                "                                    <th>Name</th>\n" +
                "                                    <th>Last Name</th>\n" +
                "                                </tr>");
        String sql = "SELECT Id,Fname,Lname FROM students";
        try {
            PreparedStatement ps = Database.connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.println("<tr>\n" +
                        "<td>"+rs.getString("Id")+"</td>\n" +
                        "<td>"+rs.getString("Fname")+"</td>\n" +
                        "<td>"+rs.getString("Lname")+"</td>\n" +
                        "</tr>");
            }
            out.println("</table>");
            out.println("<table>\n" +
                    "<tr>\n" +
                    "<th>Id</th>\n" +
                    "<th>Course</th>\n" +
                    "<th>YHP</th>\n" +
                    "<th>Description</th>\n" +
                    "</tr>");
            String sqlCourse = "SELECT * FROM courses";
            PreparedStatement prepareCourse = Database.connect().prepareStatement(sqlCourse);
           ResultSet resultCourse = prepareCourse.executeQuery();
            while (resultCourse.next()){
                out.println("<tr>\n" +
                        "<td>"+resultCourse.getString("Id")+"</td>\n" +
                        "<td>"+resultCourse.getString("Name")+"</td>\n" +
                        "<td>"+resultCourse.getString("YHP")+"</td>\n" +
                        "<td>"+resultCourse.getString("Description")+"</td>\n" +
                        "</tr>");
            }
            out.println(" </table>\n" +
                    "</div>"+
                    "</div>\n" +
                    "<div class=\"form-cont\">\n" +
                    "<form action=\"/associate\" method=\"POST\">\n" +
                    "<label for=\"studentid\">Student Id:</label><br>\n" +
                    "<input type=\"text\" id=\"studentid\" name=\"studentid\"required><br>\n" +
                    "<label for=\"courseid\">Course Id:</label><br>\n" +
                    "<input type=\"text\" id=\"courseid\" name=\"courseid\"required><br>\n" +
                    "<input type=\"submit\" value=\"Add\">\n" +
                    "</form>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</div>\n" +
                    "</html>");
        } catch (SQLException ex) {
            Database.PrintSQLException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int studentID = Integer.parseInt(req.getParameter("studentid"));
            int courseID = Integer.parseInt(req.getParameter("courseid"));
            try {
                String sqlInsertStudentToCourse = "INSERT INTO attendance (StudentId, CourseId) VALUES (?,?)";
                PreparedStatement preparedInsert = Database.connectInsert().prepareStatement(sqlInsertStudentToCourse);
                preparedInsert.setInt(1,studentID);
                preparedInsert.setInt(2,courseID);
                preparedInsert.executeUpdate();

            PrintWriter out = resp.getWriter();
            out.println("<html lang=\"en\">\n" +
                    "                <head>\n" +
                    "                    <meta charset=\"UTF-8\\\">\n" +
                    "                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "                    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                    "                    <title>Document</title>\n" +
                    "                </head>\n" +
                    "                <div class=\"container\">\n" +
                    "                    <body>\n" +
                    "                        <div class=\"nav-cont\">\n" +
                    "        <nav>\n" +
                    "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                    "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                    "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                    "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                    "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                    "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                    "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                    "        </nav>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"text\">\n" +
                    "                            <h1>Add Attendance</h1>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"table-cont\">\n" +
                    "                            <table>\n" +
                    "                                <tr>\n" +
                    "                                    <th>Name</th>\n" +
                    "<th>Last Name</th>\n" +
                    "<th>Course</th>\n" +
                    "</tr>");

                String sql = "SELECT s.Fname, s.Lname, c.Name\n" +
                        "FROM attendance a\n" +
                        "JOIN students s \n" +
                        "ON s.Id = a.StudentId\n" +
                        "JOIN courses c\n" +
                        "ON c.Id = a.CourseId\n" +
                        "WHERE s.Id = ?";
                PreparedStatement ps = Database.connectInsert().prepareStatement(sql);
                ps.setInt(1,studentID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    out.println("<tr>\n" +
                            "<td>" + rs.getString("Fname") + "</td>\n" +
                            "<td>" + rs.getString("Lname") + "</td>\n" +
                            "<td>" + rs.getString("Name") + "</td>\n" +
                            "</tr>");
                }
                out.println(" </table>\n" +
                        "</div>"+
                        "</div>\n" +
                        "<div class=\"form-cont\">\n" +
                        "<form action=\"/associate\" method=\"POST\">\n" +
                        "<label for=\"studentid\">Student Id:</label><br>\n" +
                        "<input type=\"text\" id=\"studentid\" name=\"studentid\" required><br>\n" +
                        "<label for=\"courseid\">Course Id:</label><br>\n" +
                        "<input type=\"text\" id=\"courseid\" name=\"courseid\" required><br>\n" +
                        "<input type=\"submit\" value=\"Add\">\n" +
                        "</form>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</div>\n" +
                        "</html>");
            } catch (SQLException e) {
                PrintWriter out = resp.getWriter();
                    String failMessage = "Either unknown student/course or wrong input";
                    failMessage(failMessage,out);
                Database.PrintSQLException(e);
            }
    }
    public void failMessage(String failMessage,PrintWriter out){
        out.println("<html lang=\"en\">\n" +
                "                <head>\n" +
                "                    <meta charset=\"UTF-8\\\">\n" +
                "                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "                    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                "                    <title>Document</title>\n" +
                "                </head>\n" +
                "                <div class=\"container\">\n" +
                "                    <body>\n" +
                " <div class=\"alert\">\n" +
                "  <span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>\n" +
                failMessage +
                "</div> " +
                "                        <div class=\"nav-cont\">\n" +
                "        <nav>\n" +
                "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                "        </nav>\n" +
                "                        </div>\n" +
                "                        <div class=\"text\">\n" +
                "                            <h1>Add Attendance</h1>\n" +
                "                        </div>\n" +
                "                        <div class=\"table-cont\">\n" +
                "                            <table>\n" +
                "                                <tr>\n" +
                "                                    <th>Id</th>\n" +
                "                                    <th>Name</th>\n" +
                "                                    <th>Last Name</th>\n" +
                "                                </tr>");
        String sql = "SELECT Id,Fname,Lname FROM students";
        try {
            PreparedStatement ps = Database.connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.println("<tr>\n" +
                        "<td>"+rs.getString("Id")+"</td>\n" +
                        "<td>"+rs.getString("Fname")+"</td>\n" +
                        "<td>"+rs.getString("Lname")+"</td>\n" +
                        "</tr>");
            }
            out.println("</table>");
            out.println("<table>\n" +
                    "<tr>\n" +
                    "<th>Id</th>\n" +
                    "<th>Course</th>\n" +
                    "<th>YHP</th>\n" +
                    "<th>Description</th>\n" +
                    "</tr>");
            String sqlCourse = "SELECT Id,Name,YHP,Description FROM courses";
            PreparedStatement prepareCourse = Database.connect().prepareStatement(sqlCourse);
            ResultSet resultCourse = prepareCourse.executeQuery();
            while (resultCourse.next()){
                out.println("<tr>\n" +
                        "<td>"+resultCourse.getString("Id")+"</td>\n" +
                        "<td>"+resultCourse.getString("Name")+"</td>\n" +
                        "<td>"+resultCourse.getString("YHP")+"</td>\n" +
                        "<td>"+resultCourse.getString("Description")+"</td>\n" +
                        "</tr>");
            }
            out.println(" </table>\n" +
                    "</div>"+
                    "</div>\n" +
                    "<div class=\"form-cont\">\n" +
                    "<form action=\"/associate\" method=\"POST\">\n" +
                    "<label for=\"studentid\">Student Id:</label><br>\n" +
                    "<input type=\"text\" id=\"studentid\" name=\"studentid\" required><br>\n" +
                    "<label for=\"courseid\">Course Id:</label><br>\n" +
                    "<input type=\"text\" id=\"courseid\" name=\"courseid\" required><br>\n" +
                    "<input type=\"submit\" value=\"Add\">\n" +
                    "</form>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</div>\n" +
                    "</html>");
        } catch (SQLException ex) {
            Database.PrintSQLException(ex);
        }
    }

}
