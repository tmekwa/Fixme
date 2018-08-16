package wethinkcode;

import wethinkcode.config.Config;
import wethinkcode.utils.*;
import wethinkcode.model.*;
import java.util.*;

public class App
{
    public static void main( String[] args )
    {
        new Config();
        new NonBlockingMarket();
    }
}
