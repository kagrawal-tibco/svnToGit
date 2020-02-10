package com.tibco.cep.driver.tcp.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

import com.tibco.cep.driver.tcp.TcpListener;
import com.tibco.cep.driver.tcp.TcpTransportDriver;

public class TcpTransportDriverImpl implements TcpTransportDriver {

    ServerSocketChannel ssch;
    TcpListener lsnr;
    SocketAddress bindAddress;
    Selector readSelector;
    int[] ports = new int[0];
    InetAddress addr = null;
    boolean isBound = false;
    TcpClientHandler clientHandler;

    protected TcpTransportDriverImpl() throws Exception {
        readSelector = SelectorProvider.provider().openSelector();
        ssch = SelectorProvider.provider().openServerSocketChannel();
        //clientHandler = new TcpClientHandler();
    }

    protected TcpTransportDriverImpl(int port) throws Exception {
        this();
        bindAddress = new InetSocketAddress(port);
    }

    protected TcpTransportDriverImpl(String host, int port) throws Exception {
        this();
        addr = InetAddress.getByName(host);
        bindAddress = new InetSocketAddress(addr, port);
    }

    /**
     *
     * @param host
     * @param portList is a list of port specified using a comma. eg 8333, 8334, *. "*" has a special value means any available
     * @throws Exception
     */
    protected TcpTransportDriverImpl(String host, String portList) throws Exception {
        this();

        String[] pp = portList.split(",");
        ports = new int[pp.length];
        for (int i=0; i < pp.length; i++) {
            String portStr = pp[i].trim();
            if ("*".equals(portStr)) {
                ports[i] = 0;
            }
            else {
                ports[i] = Integer.parseInt(portStr);
            }
        }

        addr = InetAddress.getByName(host);
    }

    public SocketAddress bind() throws Exception {

        if (isBound) return bindAddress;

        ssch.configureBlocking(false);
        ServerSocket socket = ssch.socket();

        if (ports.length == 0) {
            socket.bind(bindAddress);
        }
        else {
            IOException le = null;
            for (int i=0; i<ports.length; i++) {
                try {
                    bindAddress = new InetSocketAddress(addr, ports[i]);
                    socket.bind(bindAddress);
                    bindAddress = socket.getLocalSocketAddress();
                    le = null;
                }
                catch (IOException e) {
                    le = e;
                }
            }
            if (le != null) {
                throw new Exception("Failed to bind to the following ports:" + ports + le.getMessage());
            }
        }

        ssch.register(readSelector, SelectionKey.OP_ACCEPT );
        //ssch.register(readSelector, 16);
        isBound = true;
        return bindAddress;
        //ssch.register(readSelector, SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);
    }

    public void setSocketListener(TcpListener lsnr) {
        this.lsnr = lsnr;
        //clientHandler.setListener(lsnr);
    }

    protected void startListening() throws Exception {
        //System.out.println("Listening on :[" + Thread.currentThread() + "]" + bindAddress);
        try {
            //clientHandler.start();
            while(readSelector.select() > 0)
            {
                //System.out.println("Inside the readSelector.select()");
                Set readyKeys = readSelector.selectedKeys();
                Iterator i = readyKeys.iterator();
                while(i.hasNext())
                {
                    SelectionKey sk = (SelectionKey)i.next();
                    i.remove();
                    if(sk.isAcceptable())
                    {
                        ServerSocketChannel nextReady = (ServerSocketChannel)sk.channel();
                        SocketChannel channel = nextReady.accept();
                        TcpClientHandler2 handler = new TcpClientHandler2(channel, lsnr);
                        //clientHandler.acceptChannel(channel);
                        handler.start();
                    } else if(sk.isReadable())
                    {
                        SocketChannel channel = (SocketChannel)sk.channel();
                        readChannel(channel);
                    }
                } //i.hasNext();
            } //i.readSelector.next()
        }
        catch (Exception e) {
            System.out.println("TCP Server on "+ addr.toString()+" stopped due to " + e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * REMOVE THIS - NO LONGER USED - Either TcpClientHandler or TcpClientHandler2 is used
     * @param channel
     * @throws Exception
     */
    private void readChannel(SocketChannel channel) throws Exception   {

        TcpConnectionImpl connectionImpl = new TcpConnectionImpl(channel);
        try {

            byte msg[] = connectionImpl.readMsg();
            if(msg == null)
            {
                channel.close();
                //System.out.println(channel + "closed");
                return;
            }
            lsnr.onMessage(connectionImpl, msg, msg.length);
            return;
        }
        catch (java.io.IOException ioe) {
            connectionImpl.close();
            System.out.println(channel + "closed");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void acceptChannel(SocketChannel channel) throws Exception   {
        //System.out.println("Remote channel connected :[" + Thread.currentThread() + "]" + channel);
        channel.configureBlocking(false);
        channel.register(readSelector, SelectionKey.OP_READ);
        channel.socket().setSoTimeout(1000);
    }

    public void run() {
        try
        {
            bind();
            startListening();
        }
        catch(Exception e)
        {
            //e.printStackTrace();  //TODO - visit l8r : Had to comment due BugID: 1-5AJLOH
        }
    }

    public SocketAddress getInetAddress()
    {
        return bindAddress;
    }
}