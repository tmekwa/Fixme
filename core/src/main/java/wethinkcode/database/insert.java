package wethinkcode.database;

import java.sql.*;

import wethinkcode.utils.Database_Helper;
public class insert {

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:SQLite/" + Database_Helper.DBFile;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Lol database");
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
            System.out.println("A new entry has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Lol insert into " + table);
        }
    }

    public static void insertInTransactions(String broker, String market, String message, String checksum, String status)
    {
        String sql = "INSERT INTO _Transaction (BrokerId, MarketId, MessageType, Checksum, Status) VALUES(?,?,?,?,?)";
        
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, broker);
            pstmt.setString(2, market);
            pstmt.setString(3, message);
            pstmt.setString(4, checksum);
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            System.out.println("A new entry has been created.");

        }catch(SQLException e){
            System.out.println(e.getMessage());          
            System.out.println("Lol insert into transactions");
        }
    }

    public static void insertIntoInstruments(String instrumentName, int price, int quantity)
    {
        String sql = "INSERT INTO Instruments(InstrumentName, Price, Quantity) VALUES(?,?,?)";
        
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, instrumentName);
            pstmt.setInt(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            System.out.println("A new entry has been created.");

        }catch(SQLException e){
            System.out.println(e.getMessage());          
            System.out.println("Lol insert into transactions");
        }
    }
}