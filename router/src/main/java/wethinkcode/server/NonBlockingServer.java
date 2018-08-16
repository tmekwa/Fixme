package wethinkcode.server;

import java.net.*;
import java.nio.channels.*;
import java.util.*;

import wethinkcode.config.Config;
import wethinkcode.database.insert;
import wethinkcode.models.*;
import wethinkcode.utils.*;
// import wethinkcode.actions.*;  
import wethinkcode.displays.*;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           


public class NonBlockingServer implements Runnable
{
    private int _port;
    private static List<MessageModel> _messageList;
    private static List<SocketModel> _brokerList;
    private static List<SocketModel> _marketList;
    private static String store_mess_type;

    public NonBlockingServer(int port)
    {
        this._port = port;
        _messageList= new ArrayList<MessageModel>();
        _brokerList = new ArrayList<SocketModel>();
        _marketList = new ArrayList<SocketModel>();
    }

    // @Override
    public void run()
    {
        try
        {
            this.init();    
        }
        catch (Exception exc)
        {
            System.out.println(this.getClass().getSimpleName() + " [Exception]: " + exc.getMessage());
        }
    }
    
    private void init() throws Exception
    {
        try
        {
            //InetAddress hostIpAddress = InetAddress.getByName(Config.SERVER_ADDRESS);
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            /*
            *   Config socket
            *   Set socket to: non-blocking using => ssc.configureBlocking(false);
            */
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(Config.SERVER_ADDRESS, this._port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server Running: " + serverSocketChannel.getLocalAddress());

            while (true)
            {
                if (selector.selectNow() <= 0)
                    continue ;
                this.processKeys(selector.selectedKeys());
            }
        }
        catch (Exception e)
        {
            System.out.println("init()->[Exception]: " + e.getMessage());
        }
    }

    private void processKeys(Set<SelectionKey> readySet) // throws Exception
    {
        try
        {
            Iterator<SelectionKey> iterator = readySet.iterator();
            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable())
                {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = (SocketChannel) serverSocketChannel.accept();
                    String clientType = null;
                    String socketAddress = socketChannel.getRemoteAddress().toString();

                    socketChannel.configureBlocking(false);
                    socketChannel.register(key.selector(), SelectionKey.OP_READ);

                    SocketModel socketModel = new SocketModel(socketChannel, Convertors.GetPort_Integer(socketAddress));
                    if (Convertors.GetPort_Integer(socketChannel.getLocalAddress().toString()) == Config.BROKER_PORT)
                    {
                        clientType = "Broker";
                        _brokerList.add(socketModel);
                    }
                    if (Convertors.GetPort_Integer(socketChannel.getLocalAddress().toString()) == Config.MARKET_PORT)
                    {
                        clientType = "Market";
                        _marketList.add(socketModel);
                    }
                    System.out.println("Server Accepted: [" + socketChannel.getRemoteAddress() + " as " + clientType + "] on [" + socketChannel.getLocalAddress()+ "]");
                    
                }
                if (key.isReadable())
                {
                    // String client_message = this.processRead(key).trim();
                    String client_message = SocketTools.ProcessRead(key);
 
                    if (client_message != null && client_message.length() > 0)
                    {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.register(key.selector(), SelectionKey.OP_WRITE);

                        if (client_message.equalsIgnoreCase("exit"))
                        {
                            key.cancel();
                            socketChannel.close();
                            continue ;
                        }
                        if (this._port == Config.BROKER_PORT)
                        {
                            for (SocketModel sm: _marketList)
                            {
                                String[] fixed_mssg = client_message.split("\\|");
                                if (fixed_mssg != null && fixed_mssg.length > 0 && fixed_mssg[0].equals(sm.getIdString()))
                                {
                                    store_mess_type = fixed_mssg[1];
                                    String brokerPort = Convertors.GetPort_String(socketChannel.getRemoteAddress().toString());
                                    String marketPort = Convertors.GetPort_String(sm.getSocketChannel().getRemoteAddress().toString());
                                    
                                    _messageList.add(new MessageModel(brokerPort, marketPort));
                                    String message = generatechecksum.generateChecksum(client_message);
                                    System.out.println("Routing message from ["+ brokerPort +"] to ["+ marketPort +"] : " + LogFixMessage.LogMessage(message));
                                    //client_message = generatechecksum.generateChecksum(client_message);
                                    // Todo: Check processRead() return
                                    // this.processWrite(sm.getSocketChannel(), client_message);
                                    SocketTools.ProcessWrite(sm.getSocketChannel(), client_message);
                                }
                            }
                        }
                        if (this._port == Config.MARKET_PORT)
                        {
                            for (MessageModel mm: _messageList)
                            {
                                if (mm.getTo().equals(Convertors.GetPort_String(socketChannel.getRemoteAddress().toString())))
                                {
                                    mm.setMessage(client_message);
                                }
                            }
                        }
                    }
                }
                if (key.isWritable())
                {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    String response = "Market Does not exist in the routing table.. try a valid ID.";
                    boolean check = false;

                    if (this._port == Config.BROKER_PORT)
                    {
                        List<MessageModel> messagesToRemove = new ArrayList<MessageModel>();

                        for (MessageModel mm: _messageList)
                        {
                            if (mm.getFrom().equals(Convertors.GetPort_String(socketChannel.getRemoteAddress().toString())) && mm.getMessage() != null)  
                            {
                                messagesToRemove.add(mm);
                                response = mm.getMessage();
                                check = true;
                            }
                            else
                            {
                                response = null;
                            }
                        }
                        for (MessageModel mm: messagesToRemove)
                        {
                            if (_messageList.contains(mm))
                                _messageList.remove(mm);
                        }
                    }
                    if (this._port == Config.MARKET_PORT) {}
                    
                    if (check)
                    {
                        String[] data = response.split("\\|");
                        insert.insertInTransactions(socketChannel.getRemoteAddress().toString().split(":")[1], data[0].trim(), store_mess_type, data[2].trim(), data[1].trim());
                    }

                    if (response != null)
                    {
                        // this.processWrite(socketChannel, response);
                        SocketTools.ProcessWrite(socketChannel, response);
                        socketChannel.register(key.selector(), SelectionKey.OP_READ);
                        socketChannel.finishConnect();
                    }
                }
            }
        }
        catch (Exception exc)
        {
           // System.out.println(this._port + " >> In here: " + socketChannel.getRemoteAddress().toString() + " - " + response +  " = " + store_mess_type);
            exc.printStackTrace();
            //System.out.println("processKeys()->[Exception]: " + exc.getMessage());    
        }
    }
}