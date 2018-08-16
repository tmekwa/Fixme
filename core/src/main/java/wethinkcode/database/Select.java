package wethinkcode.database;

import wethinkcode.utils.*;

import java.sql.*;

public class Select {

    private static Connection connect() {
        String url = "jdbc:sqlite:SQLite/" + Database_Helper.DBFile;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void selectAll(String tableName, String columnName){
        String sql = "SELECT id, "+ columnName+" FROM " + tableName;

        try
        {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("=>" + rs.getInt("id") +  "\t" +
                        rs.getString(columnName));
            }
        } catch (SQLException e) {
            System.out.println (e.getMessage());
        }
    }

    public static void selectId(String tableName, String columnName){
        String sql = "SELECT id, "+ columnName+" FROM " + tableName;

        try
        {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("=>" + rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println (e.getMessage());
        }
    }
}