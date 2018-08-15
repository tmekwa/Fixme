package wethinkcode;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

import wethinkcode.config.Config;
import wethinkcode.utils.SocketTools;


public class NonBlockingMarket
{
    private SocketChannel socketChannel;
    private Selector selector;
    private static String[] _response = {"EXECUTED","REJECTED"};
    private static Random rand;
    private static int randomized_response;

    public NonBlockingMarket()
    {
        try {
            this.init();
        } catch (Exception exc) {
            System.out.println(this.getClass().getSimpleName() + " [Exception]: " + exc.getMessage());
        }
    }

    private void init() throws Exception
    {
        InetAddress inetAddress = InetAddress.getByName(Config.SERVER_ADDRESS);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, Config.SERVER_PORT);
        selector = Selector.open();
        socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);
        socketChannel.connect(inetSocketAddress);
        socketChannel.register(selector, (SelectionKey.OP_CONNECT | SelectionKey.OP_READ));

        while (true)
        {
            if (selector.selectNow() > 0)
            {
                boolean isDone = processKeys(selector.selectedKeys());
                if (isDone == true)
                    break ;
            }
        }
    }

    private boolean processKeys(Set<SelectionKey> readySet) throws Exception
    {
        Iterator<SelectionKey> iterator = readySet.iterator();

        while (iterator.hasNext())
        {
            SelectionKey key = iterator.next();
            iterator.remove();

            if (key.isConnectable())
            {
                boolean connected = SocketTools.ProcessConnection(key);

                if (connected == false)
                    return (true);
            }
            if (key.isReadable())
            {
                String message = SocketTools.ProcessRead(key);
                String address = socketChannel.getLocalAddress().toString();
                String[] fixedMessage = message.split("\\|");

                if (fixedMessage != null && fixedMessage.length > 0 && fixedMessage[0].equals(address.split(":")[1]))
                {
                    System.out.println("[Server]: " + message);
                    rand = new Random();
                    randomized_response = rand.nextInt(2);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
            }
            if (key.isWritable())
            {
                String response = _response[randomized_response];

                switch (response)
                {
                    case "EXECUTED":
                        System.out.println("Transaction Success!");
                        break;
                    case "REJECTED":
                        System.out.println("Transaction Declined!");
                        break;
                }

                if (response != null && response.length() > 0)
                {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(response.getBytes());
                    socketChannel.write(byteBuffer);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //_response = null;
                }
                /*if (userInput != null && userInput.equalsIgnoreCase("exit"))
                {
                    socketChannel.close();
                    return (true);
                }*/
            }
        }
        return (false);
    }
}