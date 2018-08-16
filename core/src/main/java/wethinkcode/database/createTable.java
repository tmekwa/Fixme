package wethinkcode.database;

import java.sql.*;

import wethinkcode.utils.Database_Helper;

public class createTable {

    public static void createMarketModelTable() {
        String url = "jdbc:sqlite:SQLite/" + Database_Helper.DBFile;

        String sql = "CREATE TABLE IF NOT EXISTS Market (Id integer PRIMARY KEY, MarketName varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("A new market table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createBrokerModelTable() {
        String url = "jdbc:sqlite:SQLite/" + Database_Helper.DBFile;

        String sql = "CREATE TABLE IF NOT EXISTS Broker (Id integer PRIMARY KEY, BrokerName varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("A new broker table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createConnectingTable(String tblName) {
        String url = "jdbc:sqlite:SQLite/" + Database_Helper.DBFile;

        String sql = "CREATE TABLE IF NOT EXISTS" + tblName + "(Id integer PRIMARY KEY, Name varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("A new table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTransactionTable()
    {
        String url = "jdbc:sqlite:SQLite/" + Database_Helper.DBFile;

        String sql = "CREATE TABLE IF NOT EXISTS _Transaction (Id integer PRIMARY KEY, BrokerId varchar(255) NOT NULL, MarketId varchar(255) NOT NULL, MessageType varchar(255) NOT NULL, Checksum varchar(255) NOT NULL, Status varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("A new table Transactions has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
        }
    }
}