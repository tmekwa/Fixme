package wethinkcode;

import wethinkcode.config.Config;

public class App
{
    public static void main( String[] args )
    {
        new Config();
        new NonBlockingBroker();
    }
}
