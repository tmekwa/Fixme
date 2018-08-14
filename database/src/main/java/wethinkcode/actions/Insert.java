package wethinkcode.actions;

import wethinkcode.utils.DatabaseHelper;
import wethinkcode.utils.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Console.consoleOutput(e.getMessage());
            Console.consoleOutput("Lol database");
        }
        return conn;
    }

    public static void insert(String table, String column, String name) {
        String sql = "INSERT INTO "  + table + "(" + column + ") VALUES(?)";

        try
        {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            Console.consoleOutput("A new entry has been created.");
        } catch (SQLException e) {
            Console.consoleOutput(e.getMessage());
            Console.consoleOutput("Lol insert into " + table);
        }
    }

    public static void insertInTransactions(String broker, String market, String message, String checksum, String status)
    {
        String sql = "INSERT INTO Transaction (BrokerId, MarketId, MessageType, Checksum, Status) VALUES(?,?,?,?,?)";
        
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, broker);
            pstmt.setString(2, market);
            pstmt.setString(3, message);
            pstmt.setString(4, checksum);
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            Console.consoleOutput("A new entry has been created.");

        }catch(SQLException e){
            Console.consoleOutput(e.getMessage());          
            Console.consoleOutput("Lol insert into transactions");
        }
    }
}