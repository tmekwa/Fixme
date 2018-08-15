package wethinkcode.utils;

import wethinkcode.database.*;

public class DeleteInstruments
{
    public static void removeInstrumentsFromDB()
    {
        int TotalcolumnNum = 6;
        
        for (int counter = 0; counter <= TotalcolumnNum; counter++)
            Delete.delete("Instruments", counter);
    }
}