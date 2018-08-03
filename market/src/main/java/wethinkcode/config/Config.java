package wethinkcode.config;

public class Config
{
    public static String SERVER_ADDRESS;
    public static Integer SERVER_PORT;

    public Config()
    {
        this.init();
    }

    private void init()
    {
        SERVER_ADDRESS = "127.0.0.1";
        SERVER_PORT = 5001;
    }
}