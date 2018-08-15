package wethinkcode.utils;

import wethinkcode.database.*;

public class CreateInstruments
{
    public static void createInstruments()
    {
        insert.insertIntoInstruments("BITCOIN", 200, 20);
        insert.insertIntoInstruments("LITECOIN", 180, 40);
        insert.insertIntoInstruments("ZCASH", 150, 30);
        insert.insertIntoInstruments("DASH", 100, 150);
        insert.insertIntoInstruments("RIPPLE", 50, 300);
        insert.insertIntoInstruments("MONERO", 170, 100);
    }
}