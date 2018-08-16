package wethinkcode.utils;

import wethinkcode.model.*;
import java.util.*;

public class CreateInstruments
{
    public static List<InstrumentModel> _instrumentList = new ArrayList<InstrumentModel>();
    private static InstrumentModel instrument;

    public static List<InstrumentModel> createInstrumentList()
    {
        String[] _instrumentNames = {"BITCOIN","LITECOIN","ZCASH","ETHERIUM","RIPPLE","MONERO"};
        int[] _prices = {160, 140, 115, 110, 80, 40};
        int[] _quantity = {10, 30, 22, 35, 45, 60};

        for (int cout = 0; cout <= 5; cout++)
        {
            instrument = new InstrumentModel(_instrumentNames[cout], _quantity[cout], _prices[cout]);
            _instrumentList.add(instrument);
        }
        return _instrumentList;
    }

}