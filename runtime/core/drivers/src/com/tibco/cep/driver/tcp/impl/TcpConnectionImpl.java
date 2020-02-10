package com.tibco.cep.driver.tcp.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

import com.tibco.cep.driver.tcp.TcpConnection;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 28, 2005
 * Time: 4:19:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TcpConnectionImpl implements  TcpConnection {

    SocketChannel clientSocket;
    SocketAddress remoteAddress;
    static SelectorProvider provider = SelectorProvider.provider();
    Selector selector;
    static ByteBuffer DATABUF = ByteBuffer.allocate(4096);
    static byte[] EMPTY_BUF = new byte[0];
    static byte[] ACK_PACKET = new byte[] {(byte)0xff};

    protected TcpConnectionImpl(String host, int port) throws Exception {
        initConnection(host, port);
    }

    private void initConnection(String host, int port) throws IOException {
        remoteAddress = new InetSocketAddress(host, port);
        clientSocket = provider.openSocketChannel();
        selector = provider.openSelector();
        clientSocket.configureBlocking(false);
        clientSocket.register(selector, SelectionKey.OP_READ );
    }

    protected TcpConnectionImpl(SocketChannel channel) {
        clientSocket = channel;
    }

    protected TcpConnectionImpl(String url) throws Exception {
        URI urn = new URI(url);
        String protocol = urn.getScheme();
        if ("tcp".equalsIgnoreCase(protocol)) {
            String host = urn.getHost();
            int port = urn.getPort();
            initConnection(host, port);
            return;
        }
        throw new Exception ("Unsupported protocol :" + url);
    }

    public boolean connect() throws Exception {
        clientSocket.connect(remoteAddress);
        return clientSocket.finishConnect();
    }

    //TODO : use UserId, and password
    public boolean connect(String userId, String passwd) throws Exception {
        return connect();
    }

    public int send(byte[] data) throws Exception {
        return send(data, false);
    }

    public int send(byte[] data, boolean noAck) throws Exception {

        ByteBuffer buf = ByteBuffer.wrap(data);
        int ret = clientSocket.write(buf);
        //clientSocket.socket().getOutputStream().flush();
//        if (!noAck) {
//            if (!recvAck()) throw new Exception("Socket closed...");
//        }
        return ret;
    }

    public byte[] sendWithReply(byte[] data) throws Exception {
        send(data);
        return recv(0);
    }

    public byte[] recv(int timeOut) throws Exception {
        while (selector.select(timeOut) > 0) {
            Set readySet = selector.selectedKeys();
            Iterator itr = readySet.iterator();
            while (itr.hasNext()) {
                SelectionKey key = (SelectionKey)itr.next();
                itr.remove();
                if (key.isReadable()) {
                    return readMsg();
                }
            }
        }
        return EMPTY_BUF;
    }

    /**
     * Only one threads enters this method.
     *
     *
     * @throws Exception
     */
    protected byte[] readMsg() throws Exception {
    	DATABUF = (ByteBuffer) ((Buffer)DATABUF).clear();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        while ((len = clientSocket.read(DATABUF)) > 0) {
            if (len == -1) return null;
            ((Buffer)DATABUF).flip();
            bos.write(DATABUF.array(),0,len);
            DATABUF = (ByteBuffer) ((Buffer)DATABUF).clear();
        }
        return bos.toByteArray();
//        int readCount = clientSocket.read(DATABUF);
//        if (readCount == -1) {
//            return null; //NULL indicates connection has been severed from the client side;
//        }
//        else {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bos.write(DATABUF.array(), 0, readCount );
//            while ((DATABUF.capacity() == readCount))  {
//                DATABUF.rewind();
//                readCount = clientSocket.read(DATABUF);
//                bos.write(DATABUF.array(), 0, readCount);
//            }
//            return bos.toByteArray();
//        }
    }

    protected int read(ByteBuffer buf) throws Exception {
        return clientSocket.read(buf);
    }

    public void close() throws Exception{
        clientSocket.close();
    }

    protected void sendAck() throws Exception {
        ByteBuffer buf = ByteBuffer.wrap(ACK_PACKET, 0, ACK_PACKET.length);
        clientSocket.write(buf);
    }

    protected boolean recvAck() throws Exception {
        byte[] ack = recv(1000);
        if (ack == null) throw new Exception("Remote peer closed the connection");
        if (ack.length > 0) {
            if ((ack[0] & 0xff) == 0xff) {
                return true;
            }
        }
        return false;
    }

    protected boolean isAck(byte[] ack) throws Exception {
        if (ack == null) throw new Exception("Remote peer closed the connection");
        if (ack.length > 0) {
            if ((ack[0] & 0xff) == 0xff) {
                return true;
            }
        }
        return false;
    }

    public String getURL() {
        return remoteAddress.toString();        
    }
}
