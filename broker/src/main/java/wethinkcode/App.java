package wethinkcode;

public class App
{
    public static void main( String[] args )
    {
        System.out.println("ClientMain::0");
        new NonBlockingBroker("127.0.0.1", 5000);
        System.out.println("ClientMain::1");
    }
}
