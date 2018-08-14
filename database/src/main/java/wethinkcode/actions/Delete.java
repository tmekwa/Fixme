package wethinkcode.actions;

import wethinkcode.utils.DatabaseHelper;
import wethinkcode.utils.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

    private static Connection connect() {
        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void delete(String tableName, int id) {
        String sql = "DELETE FROM " + tableName +" WHERE id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Console.consoleOutput("Deleted entry on id: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}