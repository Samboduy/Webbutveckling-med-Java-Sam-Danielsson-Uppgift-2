package Servlets;

import Models.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = "/poststudents")
public class PostStudents extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT * FROM students";
        try {
            PreparedStatement ps = Database.connect().prepareStatement(sql);
            showTableStudents(ps,req,resp);
        }catch (SQLException ex){
            Database.PrintSQLException(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!req.getParameter("fname").trim().isEmpty() && !req.getParameter("fname").trim().isEmpty()) {
                String sql = "SELECT s.Fname,s.Id,s.Lname,c.Name\n" +
                        "FROM attendance a\n" +
                        "JOIN students s \n" +
                        "ON s.Id = a.StudentId\n" +
                        "JOIN courses c\n" +
                        "ON c.Id = a.CourseId\n" +
                        "WHERE s.Fname = ? AND s.Lname = ?";
                PreparedStatement ps = Database.connect().prepareStatement(sql);
                showTableStudentsCourses(ps, req, resp);
            } else{
                String sql = "SELECT * FROM students";
                PreparedStatement ps = Database.connect().prepareStatement(sql);
                String failMessage = "Fill in all the blank textfields";
                alertTableStudents(ps,req,resp,failMessage);
            }
        }catch (SQLException e){
            Database.PrintSQLException(e);
        }
    }

    private void showTableStudentsCourses(PreparedStatement ps, HttpServletRequest req,HttpServletResponse resp){
        try {
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"container\">\n" +
                    "        <div class=\"nav-cont\">\n" +
                    "        <nav>\n" +
                    "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                    "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                    "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                    "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                    "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                    "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                    "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                    "        </nav>\n" +
                    "        </div>\n" +
                    "        <div class=\"text\">\n" +
                    "            <h1>Students</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"table-cont\">\n" +
                    "            <table>\n" +
                    "                <tr>\n" +
                    "                    <th>Name</th>\n" +
                    "                    <th>Last Name</th>\n" +
                    "                    <th>Courses</th>\n" +
                    "                </tr>");
            ps.setString(1,req.getParameter("fname"));
            ps.setString(2,req.getParameter("lname"));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                out.println("<tr>" +
                        "<th>" + rs.getString("Fname") + " </th> " +
                        "<th>" + rs.getString("Lname") + "</th>"+
                        "<th>" + rs.getString("Name") + "</th>"+
                        "</tr>");
            }
            out.println("</table>\n" +
                    "        </div>\n" +
                    "        <div class=\"form-cont\">\n" +
                    "            <form action=\"/poststudents\" method=\"POST\">\n" +
                    "                <label for=\"fname\">First name:</label><br>\n" +
                    "                <input type=\"text\" id=\"fname\" name=\"fname\"><br>\n" +
                    "                <label for=\"lname\">Last name:</label><br>\n" +
                    "                <input type=\"text\" id=\"lname\" name=\"lname\"><br>\n" +
                    "                <input type=\"submit\" value=\"Search\">\n" +
                    "            </form>\n" +
                    "        </div>\n" +
                    " <div class=\"bt-cont\" method=\"GET\">\n" +
                    "            <button id=\"BT\" name=\"resetBT\" value=\"reset\" onclick=location.href='/poststudents'>reset</button>\n" +
                    "        </div>" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void showTableStudents(PreparedStatement ps, HttpServletRequest req,HttpServletResponse resp) throws SQLException {
        try {
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"container\">\n" +
                    "        <div class=\"nav-cont\">\n" +
                    "        <nav>\n" +
                    "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                    "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                    "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                    "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                    "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                    "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                    "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                    "        </nav>\n" +
                    "        </div>\n" +
                    "        <div class=\"text\">\n" +
                    "            <h1>Students</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"table-cont\">\n" +
                    "            <table>\n" +
                    "                <tr>\n" +
                    "                    <th>Name</th>\n" +
                    "                    <th>Last Name</th>\n" +
                    "                </tr>");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                out.println("<tr>" +
                        "<th>" + rs.getString("Fname") + " </th> " +
                        "<th>" + rs.getString("Lname") + "</th>"+
                        "</tr>");
            }
            out.println("</table>\n" +
                    "        </div>\n" +
                    "        <div class=\"form-cont\">\n" +
                    "            <form action=\"/poststudents\" method=\"POST\">\n" +
                    "                <label for=\"fname\">First name:</label><br>\n" +
                    "                <input type=\"text\" id=\"fname\" name=\"fname\"><br>\n" +
                    "                <label for=\"lname\">Last name:</label><br>\n" +
                    "                <input type=\"text\" id=\"lname\" name=\"lname\"><br>\n" +
                    "                <input type=\"submit\" value=\"Search\">\n" +
                    "            </form>\n" +
                    "        </div>\n" +
                    "<div class=\"bt-cont\" method=\"GET\">\n" +
                    "            <button id=\"BT\" name=\"resetBT\" value=\"reset\" onclick=location.href='/poststudents'>reset</button>\n" +
                    "        </div>" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        }catch (Exception e){
            System.out.println(e.getMessage());
            String sql = "SELECT * FROM students";
            PreparedStatement prepeareStudent = Database.connect().prepareStatement(sql);
            String failMessage ="cant find student";
            alertTableStudents(ps,req,resp,failMessage);
        }
    }
    private void alertTableStudents(PreparedStatement ps, HttpServletRequest req,HttpServletResponse resp,String failText) {
        try {
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    " <div class=\"alert\">\n" +
                            "  <span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>\n" +
                            failText +
                            "</div> "+
                    "<div class=\"container\">\n" +
                    "        <div class=\"nav-cont\">\n" +
                    "        <nav>\n" +
                    "            <a href=\"http://localhost:23309/students\">Students</a>\n" +
                    "            <a href=\"http://localhost:23309/courses\">Courses</a>\n" +
                    "            <a href=\"http://localhost:23309/attendance\">Attendance</a>\n" +
                    "            <a href=\"http://localhost:23309/poststudents\">Search Students</a>\n" +
                    "            <a href=\"http://localhost:23309/addcourse\">Add Course</a>\n" +
                    "            <a href=\"http://localhost:23309/addstudent\">Add Student</a>\n" +
                    "            <a href=\"http://localhost:23309/associate\">Add Student to Course</a>\n" +
                    "        </nav>\n" +
                    "        </div>\n" +
                    "        <div class=\"text\">\n" +
                    "            <h1>Students</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"table-cont\">\n" +
                    "            <table>\n" +
                    "                <tr>\n" +
                    "                    <th>Name</th>\n" +
                    "                    <th>Last Name</th>\n" +
                    "                </tr>");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.println("<tr>" +
                        "<th>" + rs.getString("Fname") + " </th> " +
                        "<th>" + rs.getString("Lname") + "</th>" +
                        "</tr>");
            }
            out.println("</table>\n" +
                    "        </div>\n" +
                    "        <div class=\"form-cont\">\n" +
                    "            <form action=\"/poststudents\" method=\"POST\">\n" +
                    "                <label for=\"fname\">First name:</label><br>\n" +
                    "                <input type=\"text\" id=\"fname\" name=\"fname\"><br>\n" +
                    "                <label for=\"lname\">Last name:</label><br>\n" +
                    "                <input type=\"text\" id=\"lname\" name=\"lname\"><br>\n" +
                    "                <input type=\"submit\" value=\"Search\">\n" +
                    "            </form>\n" +
                    "        </div>\n" +
                    " <div class=\"bt-cont\" method=\"GET\">\n" +
                    "            <button id=\"BT\" name=\"resetBT\" value=\"reset\" onclick=location.href='/poststudents'>reset</button>\n" +
                    "        </div>" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}


