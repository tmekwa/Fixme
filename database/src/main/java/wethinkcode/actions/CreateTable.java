package wethinkcode.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import wethinkcode.utils.DatabaseHelper;
import wethinkcode.utils.Console;

public class CreateTable {

    public static void createMarketModelTable() {
        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;

        String sql = "CREATE TABLE IF NOT EXISTS MarketModel (Id integer PRIMARY KEY, MarketName varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            Console.ConsoleOutput("A new market table has been created.");
        } catch (SQLException e) {
            Console.ConsoleOutput(e.getMessage());
        }
    }

    public static void createBrokerModelTable() {
        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;

        String sql = "CREATE TABLE IF NOT EXISTS BrokerModel (Id integer PRIMARY KEY, BrokerName varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            Console.ConsoleOutput("A new broker table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createConnectingTable(String tblName) {
        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;

        String sql = "CREATE TABLE IF NOT EXISTS" + tblName + "(Id integer PRIMARY KEY, Name varchar(255) NOT NULL);";

        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            Console.ConsoleOutput("A new table has been created.");
        } catch (SQLException e) {
            Console.ConsoleOutput(e.getMessage());
        }
    }
}
