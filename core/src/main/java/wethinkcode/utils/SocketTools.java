package wethinkcode.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class SocketTools
{
    public static String ProcessRead(SelectionKey key) throws Exception
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

    public static boolean ProcessWrite(SocketChannel socketChannel, String message)
    {
        try
        {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
            return (true);
        }
        catch (Exception e) {}
        return (false);
    }
    public static boolean ProcessConnection(SelectionKey key) throws Exception
    {
        SocketChannel serverSocketChannel = (SocketChannel) key.channel();

        while (serverSocketChannel.isConnectionPending())
        {
            serverSocketChannel.finishConnect();
        }
        System.out.println("Client Running: "+ serverSocketChannel.getLocalAddress());
        System.out.println("Client Connected to: " + serverSocketChannel.getRemoteAddress() + "\n");

        System.out.println("ID [" + serverSocketChannel.getLocalAddress().toString().split(":")[1] +"]\n");
        return (true);
    }
}