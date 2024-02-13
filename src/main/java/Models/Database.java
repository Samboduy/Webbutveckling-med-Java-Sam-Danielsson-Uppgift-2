package Models;

import java.sql.*;

public class Database {
    private static Connection con;
    public static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //PORT and DbName should be changed

            con = DriverManager.getConnection("jdbc:mysql://localhost:13306/gritacademy","user","");
            return con;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public static Connection connectInsert(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //PORT and DbName should be changed

            con = DriverManager.getConnection("jdbc:mysql://localhost:13306/gritacademy","userRead","");
            return con;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static void PrintSQLException(SQLException sqle) {
        PrintSQLException(sqle, false);
    }
    public static void PrintSQLException(SQLException sqle, Boolean printStackTrace) {
        while(sqle != null) {
            System.out.println("\n---SQLException Caught---\n");
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("ErrorCode: " + sqle.getErrorCode());
            System.out.println("Message: " + sqle.getMessage());
            if(printStackTrace) sqle.printStackTrace();
            sqle = sqle.getNextException();
        }
    }
}
