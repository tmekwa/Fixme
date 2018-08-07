package wethinkcode.utils;

public class Validators
{
    public static boolean ValidateMessage(String message)
    {
        try
        {
            String[] mssg_parts = message.split("|");

            if (mssg_parts.length == 3)
            {
                String clientID = mssg_parts[0];
                String mssg_type = mssg_parts[1];
                String checksum = mssg_parts[2];

                return (true);
            }
        }
        catch (Exception e) {}
        return (false);
    }

    public static boolean ValidateChecksum(String checksum)
    {

        return (false);
    }
}