package wethinkcode.actions;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import wethinkcode.utils.DatabaseHelper;

public class CreateDatabase {

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:" + DatabaseHelper.DATABASEFILE_STRING;

        try
        {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
