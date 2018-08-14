package wethinkcode.actions;

import wethinkcode.utils.Console;
import wethinkcode.utils.DatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

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

    public static void update(String tabelName, int id, String columnName, String name) {
        String sql = "UPDATE "+ tabelName +" SET " + columnName + " = ? WHERE Id = ?";

        try
        {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            Console.consoleOutput("Update successful on id: "  + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}