package wethinkcode.database;

import java.sql.*;

public class createDB
{
    public static void createNewDatabase() {
 
        String url = "jdbc:sqlite:SQLite/" + "fix-me.db";
 
        try
        {
            Connection conn = DriverManager.getConnection(url);
            Class.forName("org.sqlite.JDBC");

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        catch (ClassNotFoundException e) { System.out.println(e.getMessage()); }
    }
}