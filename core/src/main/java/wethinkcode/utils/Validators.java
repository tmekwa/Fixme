package wethinkcode.utils;

import wethinkcode.hashing.EncryptANDdecrypt;
// import wethinkcode.actions.Insert;

public class Validators
{
    private static boolean isValid;
    public static String price;
    public static String mssg_type;
    public static String instrument;
    public static String quantity;
    public static String marketID;


    public static boolean ValidateMessage(String message)
    {
        isValid = false;
        try
        {
            String[] mssg_parts = message.split("\\|");

            if (mssg_parts.length == 5)
            {
                marketID = mssg_parts[0];
                mssg_type = mssg_parts[1];
                instrument = mssg_parts[2];
                quantity = mssg_parts[3];
                price = mssg_parts[4];

                if (validateINTS(marketID, quantity, price) == true && validateMessageType(mssg_type) == true)
                    isValid = true;
                else 
                    isValid = false;
            }
        }
        catch (Exception e) {}
        return (isValid);
    }

    public static void ValidateChecksum(String checksum)
    {/*
    {
        // Insert insertChecksum = new Insert();
        EncryptANDdecrypt valiAnDdecrypt = new EncryptANDdecrypt();
        checksum = valiAnDdecrypt.encrypt(checksum);
        System.out.println("encrypt: " + checksum);
        checksum = valiAnDdecrypt.decrypt(checksum);
        System.out.println("decrypt: " + checksum);
        //return (false);*/
    }

    public static boolean validateMessageType(String message)
    {
        if (message.equalsIgnoreCase("Buy"))
            return true;
        else if (message.equalsIgnoreCase("Sell"))
            return true;
        return false;
    }

    public static boolean validateINTS(String _ID, String _quantity, String _price) 
    {
        try
        {
            // checking valid integer using parseInt() method
            Integer.parseInt(_ID);
            Integer.parseInt(_quantity);
            Integer.parseInt(_price);
            return true;
        } 
        catch (NumberFormatException e) { System.out.println("\nOne of your inputs [market || quantity || price is not an interger]"); }    
        return false;
    }
}