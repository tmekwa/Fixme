package wethinkcode;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

import wethinkcode.config.Config;
import wethinkcode.utils.*;
import wethinkcode.business_logic.*;
import wethinkcode.model.*;

public class NonBlockingMarket
{
    private SocketChannel socketChannel;
    private Selector selector;
    private static String _response;
    private Logic logic;
    private static List<InstrumentModel> _instrumentList;


    public NonBlockingMarket()
    {
        try {
            this.init();
        } catch (Exception exc)
        {
            System.out.println(this.getClass().getSimpleName() + " [Exception]: " + exc.getMessage());
        }
    }

    private void init() throws Exception
    {
        _instrumentList =  CreateInstruments.createInstrumentList();
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
                DisplayMarketData.Print(_instrumentList);
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
                    System.out.println("[Broker]: " + message);
                    logic = new Logic(fixedMessage[1], fixedMessage[2], Integer.parseInt(fixedMessage[3]), Integer.parseInt(fixedMessage[4]), _instrumentList);
                    _response = logic.doLogic(message);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
            }
            if (key.isWritable())
            {
                String response = _response;

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