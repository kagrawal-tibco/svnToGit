package com.tibco.cep.driver.tcp.impl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

import com.tibco.cep.driver.tcp.TcpListener;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 3, 2005
 * Time: 1:05:40 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class handles all Client request
 */
public class TcpClientHandler2 extends Thread {

    SocketChannel channel;
    TcpConnectionImpl connection;
    Selector readSelector;
    TcpListener listener;
    boolean isClosed;


    TcpClientHandler2(SocketChannel channel, TcpListener listener) throws Exception {
        setName("TcpClientHandler2-"+channel.socket());
        this.channel = channel;
        this.listener = listener;
        this.connection = new TcpConnectionImpl(channel);
        readSelector = SelectorProvider.provider().openSelector();
        acceptChannel(channel);
    }

    public void run() {
        try {
            while(!isClosed && (readSelector.select() > 0))
            {
                Set readyKeys = readSelector.selectedKeys();
                Iterator i = readyKeys.iterator();
                while(i.hasNext())
                {
                    SelectionKey sk = (SelectionKey)i.next();
                    i.remove();
                    if(sk.isReadable())
                    {
                        SocketChannel channel = (SocketChannel)sk.channel();
                        readChannel(channel);
                    }
                } //i.hasNext();
            } //i.readSelector.next()
            System.out.println("Socket closed...");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptChannel(SocketChannel channel) throws Exception {
        channel.configureBlocking(false);
        channel.register(readSelector, SelectionKey.OP_READ );
        channel.socket().setSoTimeout(1000);
    }

    private void readChannel(SocketChannel channel) throws Exception   {
        try {
            int len=0;
            ByteBuffer READBUF = ByteBuffer.allocate(4096);
            while ((len=connection.read(READBUF))>0) {
                byte[] msg = READBUF.array();
                listener.onMessage(connection, msg, len);
                ((Buffer)READBUF).clear();
            }
            if (len == -1) {

                closeChannel(channel);
            }
        }
        catch (java.io.IOException ioe) {
            closeChannel(channel);
        }
        catch (Exception e) {
            closeChannel(channel);
            e.printStackTrace();
        }
    }

    private void closeChannel(SocketChannel channel) throws Exception {
        connection.close();
        isClosed = true;
        readSelector.close();
        System.out.println(channel + " Closed");
    }
}
