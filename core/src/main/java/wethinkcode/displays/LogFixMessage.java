package wethinkcode.displays;

import wethinkcode.utils.*;

public class LogFixMessage
{
    private static String _message;
   // private static String _separator = "|";

    public static String LogMessage(String encryptedStr)
    { 
        String[] mssg_parts = encryptedStr.split("\\|");

        _message = "MRKT=" + mssg_parts[0] + " | RQST_TYP=" + mssg_parts[1] + " | CHKSM=" + mssg_parts[2];
        return _message;
    }
}