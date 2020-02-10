package com.tibco.cep.driver.tcp.impl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
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
public class TcpClientHandler extends Thread {

    HashMap clients;
    Selector readSelector;
    TcpListener listener;
    static ByteBuffer READBUF = ByteBuffer.allocateDirect(4096);

    TcpClientHandler() throws Exception {
        clients = new HashMap();
        readSelector = SelectorProvider.provider().openSelector();
    }

    public void setListener(TcpListener listener) {
        this.listener = listener;
    }

    public void run() {
        try {
            while (true) {
                synchronized (readSelector) {
                    while(readSelector.selectNow() > 0)
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
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptChannel(SocketChannel channel) throws Exception {
        try {
            synchronized(readSelector) {
                channel.configureBlocking(false);
                channel.register(readSelector, SelectionKey.OP_READ );
                channel.socket().setSoTimeout(1000);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readChannel(SocketChannel channel) throws Exception   {
        TcpConnectionImpl connection = (TcpConnectionImpl)clients.get(channel);
        if (connection == null) {
            connection = new TcpConnectionImpl(channel);
            clients.put(channel, connection);
        }
        try {
            int len=0;
            while ((len=connection.read(READBUF))>0) {
                byte[] msg = new byte[len];
                READBUF.get(msg);
                //byte[] msg = READBUF.array();
                listener.onMessage(connection, msg, len);
                ((Buffer)READBUF).clear();
            }
//            byte msg[] = connection.readMsg();
//            if (msg == null)
//            {
//                channel.close();
//                System.out.println(channel + " Closed");
//                return;
//            }
//            listener.onMessage(connection, msg);
//            return;
        }
        catch (java.io.IOException ioe) {
            connection.close();
            clients.remove(channel);
            System.out.println(channel + " Closed");
        }
        catch (Exception e) {
            connection.close();
            clients.remove(channel);
            e.printStackTrace();
        }
    }
}
