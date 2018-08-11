package wethinkcode.hashing;

import java.util.Base64;    
import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;   
import javax.crypto.SecretKey;  


public class EncryptANDdecrypt
{
    private Cipher cipher;
    private SecretKey secretKey;
    private KeyGenerator keyGenerator;

    public EncryptANDdecrypt()
    {
        try 
        {
            /* 
                create key 
                If we need to generate a new key use a KeyGenerator
                If we have existing plaintext key use a SecretKeyFactory
            */  
            this.keyGenerator = KeyGenerator.getInstance("AES");
            //this.keyGenerator.init(128);
            this.secretKey = keyGenerator.generateKey();
            /*
            Cipher Info
            Algorithm : for the encryption of electronic data
            mode of operation : to avoid repeated blocks encrypt to the same values.
            padding: ensuring messages are the proper length necessary for certain ciphers 
            mode/padding are not used with stream cyphers.  
            */
            this.cipher = Cipher.getInstance("AES"); 
        } catch (Exception e) { e.printStackTrace(); }
           
    }

    public String encrypt(String plainText)
    {
        
        String returnString = null;

        try {
            byte[] plainTextByte = plainText.getBytes();
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            byte[] encryptedByte = this.cipher.doFinal(plainTextByte);
            Base64.Encoder encoder = Base64.getEncoder();
            String encryptedText = encoder.encodeToString(encryptedByte);
            returnString = encryptedText;
        } catch (Exception e) { e.printStackTrace(); }
        
        return returnString;
    }

    public String decrypt(String encryptedText)
    {
        String returString = null;
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedTextByte = decoder.decode(encryptedText);
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            byte[] decryptedByte = this.cipher.doFinal(encryptedTextByte);
            String decryptedText = new String(decryptedByte);
            returString = decryptedText;
        } catch (Exception e) { e.printStackTrace(); }
        
        return returString;
    }
}