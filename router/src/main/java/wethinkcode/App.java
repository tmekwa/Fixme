package wethinkcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wethinkcode.config.Config;
import wethinkcode.server.NonBlockingServer;
import wethinkcode.utils.*;
import wethinkcode.database.*;

public class App 
{
    public static void main( String[] args )
    {
        CreateInstruments.createInstruments();
        DeleteInstruments.removeInstrumentsFromDB();
        // createDB.createNewDatabase();
        // createTable.createInstrumentTable();
        // //insert.insertIntoInstruments("BITCOIN", 40, 3);
        // Select.selectAll("Instruments", "instrumentName");
        //Validators.ValidateChecksum("Heythereprettyboy");  
        new Config();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new NonBlockingServer(Config.MARKET_PORT));
        executorService.submit(new NonBlockingServer(Config.BROKER_PORT));
        executorService.shutdown();
    }
}
