package wethinkcode.utils;

import java.util.*;
import wethinkcode.model.*;

public class DisplayMarketData
{
    public static void Print(List<InstrumentModel> _instrumentList)
    {
        System.out.println("\nLIST OF ITEMS ON THE MARKET__\n");
        for(InstrumentModel im : _instrumentList)
            System.out.println("Id. " + im.get_id() + " " + im.get_Name().toUpperCase() + " Quantity = " + im.get_quantity() + " Price = " + im.get_price());
    }
}

