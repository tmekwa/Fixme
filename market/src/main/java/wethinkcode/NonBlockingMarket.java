package wethinkcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;


public class NonBlockingMarket
{
    private BufferedReader userInputReader = null;
    private SocketChannel socketChannel;
    private Selector selector;
    private int _portNumber;
    private String _address = "127.0.0.1";
    private static String _message = null;

    public NonBlockingMarket(String address, int port)
    {
        try {
            this._address = address;
            this._portNumber = port;
            this.init();
        } catch (Exception exc) {
            System.out.println(this.getClass().getSimpleName() + " [Exception]: " + exc.getMessage());
        }
    }

    private void init() throws Exception
    {
        InetAddress inetAddress = InetAddress.getByName(this._address);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, this._portNumber);
        selector = Selector.open();
        socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);
        socketChannel.connect(inetSocketAddress);
        // int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE;
        int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_READ;
        socketChannel.register(selector, operations);
        this.userInputReader = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            // if (selector.select() > 0)
            if (selector.selectNow() > 0)
            {
                boolean isDone = processReadySet(selector.selectedKeys());
                if (isDone == true)
                {
                    break ;
                }
            }
            // socketChannel.close();
        }
    }

    private boolean processReadySet(Set<SelectionKey> readySet) throws Exception
    {
        Iterator<SelectionKey> iterator = readySet.iterator();

        while (iterator.hasNext())
        {
            SelectionKey key = iterator.next();
            iterator.remove();

            if (key.isConnectable())
            {
                boolean connected = processConnection(key);

                if (connected == false)
                {
                    return (true);
                }
            }
            if (key.isReadable())
            {
                String message = processRead(key);
                String address = socketChannel.getLocalAddress().toString();
                String[] fixedMessage = message.split("#");

                if (fixedMessage != null && fixedMessage.length > 0 && fixedMessage[0].equals(address.split(":")[1]))
                {
                    System.out.println("[Server]: " + message);
                    _message = "Something to say...";
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
            }
            if (key.isWritable())
            {
                // System.out.println("Enter message (\"exit\" to quit)");
                // String userInput = this.userInputReader.readLine();
                String userInput = _message;//"Market is that and that...";

                if (userInput != null && userInput.length() > 0)
                {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(userInput.getBytes());
                    socketChannel.write(byteBuffer);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    _message = null;
                }
                if (userInput != null && userInput.equalsIgnoreCase("exit"))
                {
                    socketChannel.close();
                    return (true);
                }
            }
        }
        return (false);
    }

    private boolean processConnection(SelectionKey key) throws Exception
    {
        SocketChannel serverSocketChannel = (SocketChannel) key.channel();

        while (serverSocketChannel.isConnectionPending())
        {
            serverSocketChannel.finishConnect();
        }
        System.out.println("Client Running: "+ this.socketChannel.getLocalAddress());
        System.out.println("Client Connected to: " + serverSocketChannel.getRemoteAddress() + "\n");
        return (true);
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
}