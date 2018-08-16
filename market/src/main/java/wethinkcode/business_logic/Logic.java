package wethinkcode.business_logic;

import java.util.*;
import wethinkcode.model.*;
import wethinkcode.utils.DisplayMarketData;

public class Logic
{
    private String[] _response = {"EXECUTED", "REJECTED"};
    private String _mssg_type;
    private String _instrumentName;
    private int _quantity;
    private int _price;
    private List<InstrumentModel> _instrumentList;


    public Logic(String mssg_type,String instrumentName, int quantity, int price, List<InstrumentModel> instrumentList)
    {
        this._instrumentList = instrumentList;
        this._mssg_type = mssg_type;
        this._instrumentName = instrumentName;
        this._quantity = quantity;
        this._price = price;
    }

    public String doLogic() //please can someone teach me how to name shit -_-, i mean look at this class' name.. soo stupid
    {
        DisplayMarketData.Print(this._instrumentList);
        String resp = null;
        if (this._mssg_type.equalsIgnoreCase("BUY"))
            resp = this.Buy();
        else if ((this._mssg_type.equalsIgnoreCase("SELL")))
            resp = this.Sell();
        return resp;
    }

    private String Buy()
    {
        boolean transactionSuccess = false;
        for (InstrumentModel im : this._instrumentList)
        {
            int final_price = im.get_price() * this._quantity;
            if (im.get_Name().equalsIgnoreCase(this._instrumentName) && final_price == this._price)
            {
                im.set_quantity(im.get_quantity() - this._quantity);
                transactionSuccess = true;
                if (im.get_quantity() <= 0)
                    this._instrumentList.remove(im);
            }    
        }
        if (!transactionSuccess)
            return _response[1];
        return _response[0];
    }

    private String Sell()
    {
        boolean transactionSuccess = false;
        for (InstrumentModel im : this._instrumentList)
        {
            int final_price = im.get_price() * this._quantity;
            if (im.get_Name().equalsIgnoreCase(this._instrumentName) && final_price == this._price)
            {
                im.set_quantity(im.get_quantity() + this._quantity);
                transactionSuccess = true;
            }   
        }
        if (!transactionSuccess)
            return _response[1];
        return _response[0];
    }
}