package wethinkcode.config;

import wethinkcode.database.createDB;
import wethinkcode.database.createTable;

public class Config
{
    public static String SERVER_ADDRESS;
    public static Integer MARKET_PORT;
    public static Integer BROKER_PORT;

    public Config()
    {
        this.init();
    }

    private void init()
    {
        createDB.createNewDatabase();
        createTable.createTransactionTable();
        SERVER_ADDRESS = "127.0.0.1";
        BROKER_PORT = 5000;
        MARKET_PORT = 5001;
    }
}