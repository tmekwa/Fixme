package wethinkcode.hashing;

import java.util.*;


public class EncryptANDdecrypt {
	private static Map<String, String> m = new HashMap<String, String>();
	private static Map<String, String> mm = new HashMap<String, String>();
	private static String[] keys = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z", " " , "|" , "1", "2" , "3" , "4" , "5", "6", "7", "8", "9", "0"};
	private static String[] values = { "D%", "9", "I", "8", "N", "7", "E", "6", "S", "5", "H", "4", "R", "3", "K", "2", "A",
			"1", "@", "$", "%", "&", "*", "!", "0", "X", "Q" , "/", "-", "+", "?", "~", "Z", "Y", "M", "J", "C", "W"};

	public static String encrypt(String value) {
		for (int i = 0; i < keys.length; i++) {
			m.put(keys[i], values[i]);
		}
		String data = "";
		char[] cc = value.toCharArray();
		int len = cc.length;
		int count = 0;
		for (int i = 0; i < m.size(); i++) {
			if (count < len) {
				data = data + m.get("" + cc[i] + "");
				count++;
			}
		}
		return data;
    }

	public static String decrypt(String value) {
		for (int i = 0; i < values.length; i++) {
			mm.put(values[i], keys[i]);
		}
		String data = "";
		char[] cc = value.toCharArray();
		int len = cc.length;
		int count = 0;
		for (int i = 0; i < mm.size(); i++) {
			if (count < len) {
				data = data + mm.get("" + cc[i] + "");
				count++;
			}
		}
		return data;
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