package wethinkcode.hashing;

/*import java.math.BigInteger;
import java.security.MessageDigest;
import javax.swing.JOptionPane;

public class Encrypting
{
    public static void main(String[] args) {
        
        String login;
        String password;

        login = JOptionPane.showInputDialog("Login : ");
        password = JOptionPane.showInputDialog("Password : ");

        MessageDigest m; 

        try
        {
            m = MessageDigest.getInstance("MD5");
            m.update(login.getBytes(), 0, login.length());
            BigInteger login1 = new BigInteger(1, m.digest());
            login = String.format("%1$032X", login1);

            m.reset(); // <---- Reset before doing the password
            m.update(password.getBytes(), 0, password.length());
            BigInteger password1 = new BigInteger(1, m.digest());
            password = String.format("%1$032X", password1);

            System.out.println(login);
            System.out.println(password);
                //System.out.println("login : "+ login); 
                //System.out.println("password : " + password);
        }catch (Exception exception)
        {
            System.out.println("lol");
        }
    } 
}*/

/*import java.util.Base64;    
import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;   
import javax.crypto.SecretKey;  


class Encrypting
{
    private Cipher cipher;
    private SecretKey secretKey;
    private KeyGenerator keyGenerator;

    public Encrypting()
    {
        try 
        {
             
                create key 
                If we need to generate a new key use a KeyGenerator
                If we have existing plaintext key use a SecretKeyFactory
              
            this.keyGenerator = KeyGenerator.getInstance("AES");
            this.keyGenerator.init(128);
            this.secretKey = keyGenerator.generateKey();
            
            Cipher Info
            Algorithm : for the encryption of electronic data
            mode of operation : to avoid repeated blocks encrypt to the same values.
            padding: ensuring messages are the proper length necessary for certain ciphers 
            mode/padding are not used with stream cyphers.  
            
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

    public static String encrypt(String plain) {
		String b64encoded = Base64.getEncoder().encodeToString(plain.getBytes());

		// Reverse the string
		String reverse = new StringBuffer(b64encoded).reverse().toString();

		StringBuilder tmp = new StringBuilder();
		final int OFFSET = 4;
		for (int i = 0; i < reverse.length(); i++) {
			tmp.append((char)(reverse.charAt(i) + OFFSET));
		}
		return tmp.toString();
	}

	public static String decrypt(String secret) {
		StringBuilder tmp = new StringBuilder();
		final int OFFSET = 4;
		for (int i = 0; i < secret.length(); i++) {
			tmp.append((char)(secret.charAt(i) - OFFSET));
		}

		String reversed = new StringBuffer(tmp.toString()).reverse().toString();
		return new String(Base64.getDecoder().decode(reversed));
	}

    public static void main(String[] args) 
    {
        String tt = "hello world!";
        
        EncryptANDdecrypt encryptANDdecrypt = new EncryptANDdecrypt();
        System.out.println(encryptANDdecrypt.encrypt(tt));
        System.out.println(encryptANDdecrypt.decrypt(tt));
    }
}*/