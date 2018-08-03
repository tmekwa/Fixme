package wethinkcode.utils;

public class Convertors
{
    public static String GetPort_String(String address)
    {
        try
        {
            String[] addressParts = address.split(":");
            if (addressParts != null && addressParts.length == 2)
                return (addressParts[1]);
        }
        catch (Exception e) {}
        return (null);
    }

    public static int GetPort_Integer(String address)
    {
        String port = GetPort_String(address);
        if (port != null)
        {
            try
            {
                return (Integer.parseInt(port));
            } catch (Exception e) {}
        }
        return (0);
    }
}