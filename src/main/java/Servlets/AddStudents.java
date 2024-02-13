package Servlets;

import Models.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/addstudent")
public class AddStudents extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String sql = "SELECT * FROM students";
        out.println("<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
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
                "    <h1>Students</h1>\n" +
                "    </div>");
        out.println("<div class=\"table-cont\">\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <td>Id</td>\n" +
                "                <td>Name</td>\n" +
                "                <td>Last Name</td>\n" +
                "            </tr>");
        try {
            PreparedStatement ps = Database.connect().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                out.println("<tr>" +
                        "<th>" + rs.getString("Id") + " </th> " +
                        "<th>" + rs.getString("Fname") + " </th> " +
                        "<th>" + rs.getString("Lname") + "</th>"+
                        "</tr>");
            }
            out.println("</table>\n" +
                    "        </div>\n" +
                    "        <div class=\"form-cont\">\n" +
                    "            <form action=\"/addstudent\" method=\"POST\">\n" +
                    "                <label for=\"fname\">First name:</label><br>\n" +
                    "                <input type=\"text\" id=\"fname\" name=\"fname\" required><br>\n" +
                    "                <label for=\"lname\">Last name:</label><br>\n" +
                    "                <input type=\"text\" id=\"lname\" name=\"lname\" required><br>\n" +
                    "                <label for=\"town\">Town:</label><br>\n" +
                    "                <input type=\"text\" id=\"town\" name=\"town\" required><br>\n" +
                    "                <label for=\"hobby\">Hobby:</label><br>\n" +
                    "                <input type=\"text\" id=\"hobby\" name=\"hobby\" required><br>\n" +
                    "                <input type=\"submit\" value=\"Add\">\n" +
                    "            </form>\n" +
                    "        </div> \n" +
                    "    </div>");
            out.println("</body>\n" +
                    "</div>\n" +
                    "</html>");

        } catch (SQLException e) {
            Database.PrintSQLException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String town = req.getParameter("town");
        String hobby = req.getParameter("hobby");
        try {
            if (!fname.trim().isEmpty()&&!lname.trim().isEmpty()&&!town.trim().isEmpty()&&!hobby.trim().isEmpty()){
                    String sql = "INSERT INTO students(Fname,Lname,Town,Hobby) VALUES (?,?,?,?)";
                    PreparedStatement ps = Database.connectInsert().prepareStatement(sql);
                    ps.setString(1, fname);
                    ps.setString(2, lname);
                    ps.setString(3, town);
                    ps.setString(4, hobby);
                    ps.executeUpdate();

                    PrintWriter out = resp.getWriter();
                    out.println("<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                            "    <title>Document</title>\n" +
                            "</head>\n" +
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
                            "    <h1>Students</h1>\n" +
                            "    </div>");
                    out.println("<div class=\"table-cont\">\n" +
                            "        <table>\n" +
                            "            <tr>\n" +
                            "                <td>Id</td>\n" +
                            "                <td>Name</td>\n" +
                            "                <td>Last Name</td>\n" +
                            "            </tr>");
                    try {
                        String sqlStudents = "SELECT * FROM students";
                        PreparedStatement prepareStudents = Database.connect().prepareStatement(sqlStudents);

                        ResultSet rs = prepareStudents.executeQuery();
                        while (rs.next()) {
                            out.println("<tr>" +
                                    "<th>" + rs.getString("Id") + " </th> " +
                                    "<th>" + rs.getString("Fname") + " </th> " +
                                    "<th>" + rs.getString("Lname") + "</th>" +
                                    "</tr>");
                        }
                        out.println("</table>\n" +
                                "        </div>\n" +
                                "        <div class=\"form-cont\">\n" +
                                "            <form action=\"/addstudent\" method=\"POST\">\n" +
                                "                <label for=\"fname\">First name:</label><br>\n" +
                                "                <input type=\"text\" id=\"fname\" name=\"fname\"required><br>\n" +
                                "                <label for=\"lname\">Last name:</label><br>\n" +
                                "                <input type=\"text\" id=\"lname\" name=\"lname\"required><br>\n" +
                                "                <label for=\"town\">Town:</label><br>\n" +
                                "                <input type=\"text\" id=\"town\" name=\"town\"required><br>\n" +
                                "                <label for=\"hobby\">Hobby:</label><br>\n" +
                                "                <input type=\"text\" id=\"hobby\" name=\"hobby\" required><br>\n" +
                                "                <input type=\"submit\" value=\"Add\">\n" +
                                "            </form>\n" +
                                "        </div> \n" +
                                "    </div>");
                        out.println("</body>\n" +
                                "</div>\n" +
                                "</html>");

                    } catch (SQLException e) {
                        Database.PrintSQLException(e);
                    }
            }else {
                PrintWriter out = resp.getWriter();
                String failMessage = "Fill in all the blank textfields";
                failMessage(out,failMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void failMessage(PrintWriter out, String failMessage) {
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
                failMessage +
                "</div> " +
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
                "                    <th>Id</th>\n" +
                "                    <th>Name</th>\n" +
                "                    <th>Last Name</th>\n" +
                "                </tr>");
        try {
            String sql = "SELECT * FROM students";
            PreparedStatement ps = Database.connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.println("<tr>" +
                        "<th>" + rs.getString("Id") + " </th> " +
                        "<th>" + rs.getString("Fname") + " </th> " +
                        "<th>" + rs.getString("Lname") + "</th>" +
                        "</tr>");
            }
            out.println("</table>\n" +
                    "        </div>\n" +
                    "        <div class=\"form-cont\">\n" +
                    "            <form action=\"/addstudent\" method=\"POST\">\n" +
                    "                <label for=\"fname\">First name:</label><br>\n" +
                    "                <input type=\"text\" id=\"fname\" name=\"fname\" required><br>\n" +
                    "                <label for=\"lname\">Last name:</label><br>\n" +
                    "                <input type=\"text\" id=\"lname\" name=\"lname\" required><br>\n" +
                    "                <label for=\"town\">Town:</label><br>\n" +
                    "                <input type=\"text\" id=\"town\" name=\"town\" required><br>\n" +
                    "                <label for=\"hobby\">Hobby:</label><br>\n" +
                    "                <input type=\"text\" id=\"hobby\" name=\"hobby\" required><br>\n" +
                    "                <input type=\"submit\" value=\"Add\">\n" +
                    "            </form>\n" +
                    "        </div> \n" +
                    "    </div>");
            out.println("</body>\n" +
                    "</div>\n" +
                    "</html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }


