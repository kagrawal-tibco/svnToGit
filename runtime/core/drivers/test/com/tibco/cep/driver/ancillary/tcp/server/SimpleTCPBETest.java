package com.tibco.cep.driver.ancillary.tcp.server;

import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.driver.ancillary.tcp.client.TCPClient;
import com.tibco.cep.kernel.service.impl.DefaultLogger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 4:30:30 PM
*/
public class SimpleTCPBETest implements SessionManager.SessionListener {
    protected TCPServer tcpServer;

    protected TCPClient tcpClient;

    protected ArrayBlockingQueue<Session> serverSessions = new ArrayBlockingQueue<Session>(1);

    public void test() throws Exception {
        DefaultLogger logger = new DefaultLogger();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        TCPServer.Parameters serverParams =
                new TCPServer.Parameters("server-tcp-localhost", "localhost", 37000);
        tcpServer = new TCPServer();
        tcpServer.init(null, serverParams, logger);
        tcpServer.setListener(this);
        tcpServer.start();

        TCPClient.Parameters clientParams =
                new TCPClient.Parameters("client-tcp-localhost", "localhost", 31900);
        tcpClient = new TCPClient();
        tcpClient.init(clientParams, logger, executorService);
        tcpClient.start();

        //------------

        Writer clientWriter = tcpClient.getWriter();
        byte[] bytes = new String("Hello from client").getBytes();
        clientWriter.write(bytes, 0, bytes.length);
        clientWriter.flush();
        clientWriter.stop();

        Reader clientReader = tcpClient.getReader();
        byte[] readBytes = new byte[1024];
        int c = 0;

        for (; ;) {
            c = clientReader.read(readBytes, 0, readBytes.length);

            if (c == -1) {
                System.out.println("Server closed its output.");
                clientReader.stop();
                break;
            }
            else {
                String response = new String(readBytes, 0, c);
                System.out.println("Client received data: " + response);
            }
        }

        tcpClient.stop();

        //---------------

        Session session = serverSessions.take();
        runLocalServer(session);
    }

    public void onNewSession(Session session) {
        try {
            System.err.println("New server session: " + session.getId());

            serverSessions.offer(session);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void runLocalServer(Session session) throws Exception {
        Reader serverReader = session.getReader();
        byte[] readBytes = new byte[1024];
        int c = 0;

        for (; ;) {
            c = serverReader.read(readBytes, 0, readBytes.length);

            if (c == -1) {
                System.err.println("Client closed its output.");
                serverReader.stop();
                break;
            }
            else {
                String response = new String(readBytes, 0, c);
                System.err.println("Server received data: " + response);
            }
        }

        //----------

        Writer serverWriter = session.getWriter();
        byte[] bytes = new String("Go away!").getBytes();
        serverWriter.write(bytes, 0, bytes.length);

        bytes = new String("I said...GO AWAY!").getBytes();
        serverWriter.write(bytes, 0, bytes.length);

        serverWriter.flush();
        serverWriter.stop();

        //----------

        tcpServer.stopSession(session);
        tcpServer.stop();
    }

    public static void main(String[] args) throws Exception {
        new SimpleTCPBETest().test();

        System.exit(0);
    }
}
