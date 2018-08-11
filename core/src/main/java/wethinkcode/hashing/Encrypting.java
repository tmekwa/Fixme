package wethinkcode.hashing;

import java.math.BigInteger;
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
}