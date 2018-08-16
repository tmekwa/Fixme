package wethinkcode.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class Encrypt {
	public static String encrypt(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return password;
    }
    
	/*	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		EncryptANDdecrypt crypto = new EncryptANDdecrypt();
		
		System.out.println("Enter the String to perform Encryption and Decryption : ");
		String strToEncrypt = scanner.nextLine();
		
		String strToDecrypt = crypto.encrypt(strToEncrypt);
		
		System.out.println("The string \""+strToEncrypt+"\" Encrypted as : "+strToDecrypt);
		
		System.out.println("The string \""+strToDecrypt+"\" Decrypted as : "+crypto.decrypt(strToDecrypt));
	}	
		*/
}