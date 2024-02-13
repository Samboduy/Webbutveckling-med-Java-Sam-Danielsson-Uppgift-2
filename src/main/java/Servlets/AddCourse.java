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

@WebServlet (urlPatterns = "/addcourse")
public class AddCourse extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT * FROM courses";
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
                "    <h1>Courses</h1>\n" +
                "    </div>");
        out.println("<div class=\"table-cont\">\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <td>Name</td>\n" +
                "                <td>YHP</td>\n" +
                "                <td>Description</td>\n" +
                "            </tr>");
        try {
            PreparedStatement ps = Database.connect().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                out.println("<tr>" +
                        "<th>" + rs.getString("Id") + " </th> " +
                        "<th>" + rs.getString("Name") + " </th> " +
                        "<th>" + rs.getString("YHP") + "</th>"+
                        "<th>" + rs.getString("Description") + "</th>"+
                        "</tr>");
            }
            out.println("</table>\n" +
                    "        </div>\n" +
                    "        <div class=\"form-cont\">\n" +
                    "            <form action=\"/addcourse\" method=\"POST\">\n" +
                    "                <label for=\"name\">Name:</label><br>\n" +
                    "                <input type=\"text\" id=\"name\" name=\"name\" required><br>\n" +
                    "                <label for=\"yhp\">YHP:</label><br>\n" +
                    "                <input type=\"text\" id=\"yhp\" name=\"yhp\" required><br>\n" +
                    "                <label for=\"description\">Description:</label><br>\n" +
                    "                <input type=\"text\" id=\"description\" name=\"description\" required><br>\n" +
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
        String sql = "INSERT INTO courses(Name,YHP,Description) VALUES (?,?,?)";
        String name = req.getParameter("name");
        String yhp = req.getParameter("yhp");
        String descript = req.getParameter("description");

        try {
            PreparedStatement ps = Database.connectInsert().prepareStatement(sql);
            if (!name.trim().isEmpty()&&!yhp.trim().isEmpty()&&!descript.trim().isEmpty()) {
                ps.setString(1, name);
                ps.setString(2, yhp);
                ps.setString(3, descript);
                ps.executeUpdate();


                String sqlCourse = "SELECT * FROM courses";
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
                        "    <h1>Courses</h1>\n" +
                        "    </div>");
                out.println("<div class=\"table-cont\">\n" +
                        "        <table>\n" +
                        "            <tr>\n" +
                        "                <td>Id</td>\n" +
                        "                <td>Name</td>\n" +
                        "                <td>YHP</td>\n" +
                        "                <td>Description</td>\n" +
                        "            </tr>");
                try {
                    PreparedStatement preparedCourse = Database.connect().prepareStatement(sqlCourse);

                    ResultSet rs = preparedCourse.executeQuery();
                    while (rs.next()){
                        out.println("<tr>" +
                                "<th>" + rs.getString("Id") + " </th> " +
                                "<th>" + rs.getString("Name") + " </th> " +
                                "<th>" + rs.getString("YHP") + "</th>"+
                                "<th>" + rs.getString("Description") + "</th>"+
                                "</tr>");
                    }
                    out.println("</table>\n" +
                            "        </div>\n" +
                            "        <div class=\"form-cont\">\n" +
                            "            <form action=\"/addcourse\" method=\"POST\">\n" +
                            "                <label for=\"name\">Name:</label><br>\n" +
                            "                <input type=\"text\" id=\"name\" name=\"name\" required><br>\n" +
                            "                <label for=\"yhp\">YHP:</label><br>\n" +
                            "                <input type=\"text\" id=\"yhp\" name=\"yhp\" required><br>\n" +
                            "                <label for=\"description\">Description:</label><br>\n" +
                            "                <input type=\"text\" id=\"description\" name=\"description\" required><br>\n" +
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
            } else {
                String sqlCourse = "SELECT * FROM courses";
                PrintWriter out = resp.getWriter();
                out.println("<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                        "    <title>Document</title>\n" +
                        "</head>\n" +
                        "<div class=\"container\">\n" +
                        " <div class=\"alert\">\n" +
                        "  <span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>\n" +
                        "Fill in all of the textfields.\n" +
                        "</div> "+
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
                        "    <h1>Courses</h1>\n" +
                        "    </div>");
                out.println("<div class=\"table-cont\">\n" +
                        "        <table>\n" +
                        "            <tr>\n" +
                        "                <td>Id</td>\n" +
                        "                <td>Name</td>\n" +
                        "                <td>YHP</td>\n" +
                        "                <td>Description</td>\n" +
                        "            </tr>");
                try {
                    PreparedStatement prepareCourse = Database.connect().prepareStatement(sqlCourse);

                    ResultSet rs = prepareCourse.executeQuery();
                    while (rs.next()){
                        out.println("<tr>" +
                                "<th>" + rs.getString("Id") + " </th> " +
                                "<th>" + rs.getString("Name") + " </th> " +
                                "<th>" + rs.getString("YHP") + "</th>"+
                                "<th>" + rs.getString("Description") + "</th>"+
                                "</tr>");
                    }
                    out.println("</table>\n" +
                            "        </div>\n" +
                            "        <div class=\"form-cont\">\n" +
                            "            <form action=\"/addcourse\" method=\"POST\">\n" +
                            "                <label for=\"name\">Name:</label><br>\n" +
                            "                <input type=\"text\" id=\"name\" name=\"name\" required><br>\n" +
                            "                <label for=\"yhp\">YHP:</label><br>\n" +
                            "                <input type=\"text\" id=\"yhp\" name=\"yhp\" required><br>\n" +
                            "                <label for=\"description\">Description:</label><br>\n" +
                            "                <input type=\"text\" id=\"description\" name=\"description\" required><br>\n" +
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
    } catch (SQLException e) {
            String sqlFail = "SELECT * FROM courses";
            PrintWriter out = resp.getWriter();
            out.println("<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "<div class=\"container\">\n" +
                    " <div class=\"alert\">\n" +
                    "  <span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>\n" +
                    " Duplicate entry course name " +
                    "</div> " +
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
                    "    <h1>Courses</h1>\n" +
                    "    </div>");
            out.println("<div class=\"table-cont\">\n" +
                    "        <table>\n" +
                    "            <tr>\n" +
                    "                <td>Name</td>\n" +
                    "                <td>YHP</td>\n" +
                    "                <td>Description</td>\n" +
                    "            </tr>");
            try {
                PreparedStatement ps = Database.connect().prepareStatement(sqlFail);

                ResultSet rsFail = ps.executeQuery();
                while (rsFail.next()){
                    out.println("<tr>" +
                            "<th>" + rsFail.getString("Id") + " </th> " +
                            "<th>" + rsFail.getString("Name") + " </th> " +
                            "<th>" + rsFail.getString("YHP") + "</th>"+
                            "<th>" + rsFail.getString("Description") + "</th>"+
                            "</tr>");
                }
                out.println("</table>\n" +
                        "        </div>\n" +
                        "        <div class=\"form-cont\">\n" +
                        "            <form action=\"/addcourse\" method=\"POST\">\n" +
                        "                <label for=\"name\">Name:</label><br>\n" +
                        "                <input type=\"text\" id=\"name\" name=\"name\" required><br>\n" +
                        "                <label for=\"yhp\">YHP:</label><br>\n" +
                        "                <input type=\"text\" id=\"yhp\" name=\"yhp\" required><br>\n" +
                        "                <label for=\"description\">Description:</label><br>\n" +
                        "                <input type=\"text\" id=\"description\" name=\"description\" required><br>\n" +
                        "                <input type=\"submit\" value=\"Add\">\n" +
                        "            </form>\n" +
                        "        </div> \n" +
                        "    </div>");
                out.println("</body>\n" +
                        "</div>\n" +
                        "</html>");

            } catch (SQLException ex) {
                Database.PrintSQLException(ex);
            }
        }
    }
}
