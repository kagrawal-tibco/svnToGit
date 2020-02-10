package com.tibco.cep.driver.ancillary.tcp.server;

import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.driver.ancillary.tcp.client.TCPClient;
import com.tibco.cep.kernel.service.impl.DefaultLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 4:30:30 PM
*/
public class SimpleTCPSelfTest implements SessionManager.SessionListener {
    protected TCPServer tcpServer;

    protected TCPClient tcpClient;

    public void test() throws Exception {
        DefaultLogger logger = new DefaultLogger();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        TCPServer.Parameters serverParams =
                new TCPServer.Parameters("server-tcp-localhost", "localhost", 21300);
        tcpServer = new TCPServer();
        tcpServer.init(null, serverParams, logger);
        tcpServer.setListener(this);
        tcpServer.start();

        TCPClient.Parameters clientParams =
                new TCPClient.Parameters("client-tcp-localhost", "localhost", 21300);
        tcpClient = new TCPClient();
        tcpClient.init(clientParams, logger, executorService);
        Reader clientReader = tcpClient.getReader();
        clientReader.setOptionalListener(new Reader.ReaderListener() {
            public void onException(Exception e) {
                e.printStackTrace();
            }

            public void onData(byte[] data, int offset, int length) {
                System.out.println(Thread.currentThread().getName() + ":Client received data: " +
                        new String(data, offset, length));
            }

            public void onEnd() {
            }
        });
        tcpClient.start();

        //------------

        Writer clientWriter = tcpClient.getWriter();
        byte[] bytes = new String("Hello from client").getBytes();
        clientWriter.write(bytes, 0, bytes.length);
        clientWriter.flush();
        clientWriter.stop();

        Thread.sleep(2000);

        tcpClient.stop();
        tcpServer.stop();
    }

    public void onNewSession(Session session) {
        System.out.println(Thread.currentThread().getName() + ":New Session: " + session.getId());

        final Writer serverWriter = session.getWriter();

        Reader serverReader = session.getReader();
        serverReader.setOptionalListener(new Reader.ReaderListener() {
            public void onException(Exception e) {
                e.printStackTrace();
            }

            public void onData(byte[] data, int offset, int length) {
                String s = new String(data, offset, length);

                System.out.println(Thread.currentThread().getName() + ":Server received data: " +
                        s);

                StringBuilder sb = new StringBuilder(s);
                sb = sb.reverse();
                byte[] response = sb.toString().getBytes();
                try {
                    serverWriter.write(response, 0, response.length);
                    serverWriter.flush();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onEnd() {
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new SimpleTCPSelfTest().test();

        System.exit(0);
    }
}