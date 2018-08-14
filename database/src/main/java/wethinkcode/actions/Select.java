package wethinkcode.actions;

import wethinkcode.utils.Console;
import wethinkcode.utils.DatabaseHelper;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Select {

    private static Connection connect() {
        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Console.consoleOutput(e.getMessage());
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
                Console.consoleOutput("=>" + rs.getInt("id") +  "\t" +
                        rs.getString(columnName));
            }
        } catch (SQLException e) {
            Console.consoleOutput (e.getMessage());
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
                Console.consoleOutput("=>" + rs.getInt("id"));
            }
        } catch (SQLException e) {
            Console.consoleOutput (e.getMessage());
        }
    }
}