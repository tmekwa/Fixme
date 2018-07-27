package wethinkcode.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import wethinkcode.config.Config;
import wethinkcode.models.MessageModel;
import wethinkcode.models.SocketModel;

public class NonBlockingServer implements Runnable
{
    private int _port;
    private static List<MessageModel> _messageList;
    private static List<SocketModel> _brokerList;
    private static List<SocketModel> _marketList;

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
            InetAddress hostIpAddress = InetAddress.getByName(Config.SERVER_ADDRESS);
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

                    SocketModel socketModel = new SocketModel(socketChannel, this.getPortInt(socketAddress));
                    if (this.getPortInt(socketChannel.getLocalAddress().toString()) == Config.BROKER_PORT)
                    {
                        clientType = "Broker";
                        _brokerList.add(socketModel);
                    }
                    if (this.getPortInt(socketChannel.getLocalAddress().toString()) == Config.MARKET_PORT)
                    {
                        clientType = "Market";
                        _marketList.add(socketModel);
                    }
                    System.out.println("Server Accepted: [" + socketChannel.getRemoteAddress() + " as " + clientType + "] on [" + socketChannel.getLocalAddress()+ "]");
                }
                if (key.isReadable())
                {
                    String client_message = this.processRead(key).trim();

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
                                String[] fixed_mssg = client_message.split("#");

                                if (fixed_mssg != null && fixed_mssg.length > 0 && fixed_mssg[0].equals(sm.getIdString()))
                                {
                                    String brokerPort = this.getPort(socketChannel.getRemoteAddress().toString());
                                    String marketPort = this.getPort(sm.getSocketChannel().getRemoteAddress().toString());

                                    _messageList.add(new MessageModel(brokerPort, marketPort));
                                    System.out.println("[Broker="+ brokerPort +"] to [Market="+ marketPort +"]: " + client_message);
                                    // Todo: Check processRead() return
                                    this.processWrite(sm.getSocketChannel(), client_message);
                                }
                            }
                        }
                        if (this._port == Config.MARKET_PORT)
                        {
                            for (MessageModel mm: _messageList)
                            {
                                if (mm.getTo().equals(this.getPort(socketChannel.getRemoteAddress().toString())))
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
                    String response = "N/A";

                    if (this._port == Config.BROKER_PORT)
                    {
                        List<MessageModel> messagesToRemove = new ArrayList<MessageModel>();

                        for (MessageModel mm: _messageList)
                        {
                            if (mm.getFrom().equals(this.getPort(socketChannel.getRemoteAddress().toString())) && mm.getMessage() != null)  
                            {
                                messagesToRemove.add(mm);
                                response = mm.getMessage();
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

                    if (response != null)
                    {
                        this.processWrite(socketChannel, response);
                        socketChannel.register(key.selector(), SelectionKey.OP_READ);
                        socketChannel.finishConnect();
                    }
                }
            }
        }
        catch (Exception exc)
        {
            System.out.println("processKeys()->[Exception]: " + exc.getMessage());    
        }
    }

    private String processRead(SelectionKey key) throws Exception
    {
        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
        String message = charBuffer.toString();
        return (message);
    }

    // private String processRead(SelectionKey key)
    // {
    //     try
    //     {
    //         SocketChannel socketChannel = (SocketChannel) key.channel();
    //         ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    //         if (socketChannel.read(byteBuffer) > 0)
    //         {
    //             byteBuffer.flip();
    //             return (new String(byteBuffer.array()));
    //         }
    //     }
    //     catch (Exception exc)
    //     {
    //         System.out.println("processRead()->[Exception]: " + exc.getMessage());
    //     }
    //     return (null);
    // }

    private boolean processWrite(SocketChannel socketChannel, String message)
    {
        try
        {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
            return (true);
        } catch (Exception e) {}
        return (false);
    }

    private String getPort(String address)
    {
        try
        {
            String[] addressParts = address.split(":");
            if (addressParts != null && addressParts.length == 2)
                return (addressParts[1]);
        }
        catch (Exception e) {}
        return (null);
    }

    private int getPortInt(String address)
    {
        String port = this.getPort(address);
        if (port != null)
        {
            try
            {
                return (Integer.parseInt(port));
            } catch (Exception e) {}
        }
        return (0);
    }
}