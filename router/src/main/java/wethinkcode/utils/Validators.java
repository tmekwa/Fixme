package wethinkcode.utils;

public class Validators
{
    private static boolean isValid;
    public static String clientID;
    public static String mssg_type;
    public static String checksum;

    public static boolean ValidateMessage(String message)
    {
        isValid = false;
        try
        {
            String[] mssg_parts = message.split("\\|");

            if (mssg_parts.length == 3)
            {
                clientID = mssg_parts[0];
                mssg_type = mssg_parts[1];
                checksum = mssg_parts[2];

                if (validateID(clientID) == true && validateMessageType(mssg_type) == true)
                    isValid = true;
                else 
                    isValid = false;
            }
        }
        catch (Exception e) {}
        return (isValid);
    }

    public static boolean ValidateChecksum(String checksum)
    {

        return (false);
    }

    public static boolean validateMessageType(String message)
    {
        if (message.equalsIgnoreCase("Buy"))
            return true;
        else if (message.equalsIgnoreCase("Sell"))
            return true;
        return false;
    }

    public static boolean validateID(String ID) 
    {
        try
        {
            // checking valid integer using parseInt() method
            Integer.parseInt(clientID);
            System.out.println(clientID + " is a valid integer number");
            return true;
        } 
        catch (NumberFormatException e) { System.out.println(clientID + " is not a valid integer number"); }    
        return false;
    }
}