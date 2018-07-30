package wethinkcode;


public class App
{
    public static void main( String[] args )
    {
        System.out.println("ClientMain::0");
        new NonBlockingMarket("127.0.0.1", 5001);
        System.out.println("ClientMain::1");
    }
}
