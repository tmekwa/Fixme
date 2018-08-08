package wethinkcode.actions;

import wethinkcode.utils.DatabaseHelper;
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
            System.out.println("Lol insert");
        }
    }
}