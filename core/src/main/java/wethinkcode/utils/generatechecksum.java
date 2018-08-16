package wethinkcode.utils;

import wethinkcode.hashing.*;

public class generatechecksum
{
    private static String separator = "|";

    public static String generateChecksum(String message) 
    {
        String[] mssg_parts = message.split("\\|");

        String marketId = mssg_parts[0]; 
        String messType = mssg_parts[1];
        String instru = mssg_parts[2];
        String quant = mssg_parts[3];
        String price = mssg_parts[4];

        String checksum = marketId + separator + messType + separator + Encrypt.encrypt(instru + separator + quant + separator + price);
        return (checksum);    
    }

    /*public static String undoChecksum(String checksum) 
    {
        String[] mssg_parts = checksum.split("\\|");

        String marketID = mssg_parts[0];
        String messType = mssg_parts[1];
        String checkshum = mssg_parts[2];

        String decryptedString = marketID + separator + messType + separator + EncryptANDdecrypt.decrypt(checkshum);
        return (decryptedString);
    }*/

    /*public static void main(String[] args) 
    {
        String message = "BUY|GUYTAR|6|56140|60";

        System.out.println(createChecksum(message));
    }*/
}