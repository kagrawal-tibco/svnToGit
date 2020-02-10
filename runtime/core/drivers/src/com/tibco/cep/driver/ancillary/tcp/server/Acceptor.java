package com.tibco.cep.driver.ancillary.tcp.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.driver.ancillary.api.AbstractManagedResource;
import com.tibco.cep.driver.ancillary.api.SessionManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;


/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 10:04:17 AM
*/

public class Acceptor extends AbstractManagedResource implements Runnable {

    protected ServerSocket serverSocket;

    protected SessionManager.SessionListener sessionListener;

    protected String id;

    protected String serverId;

    protected Logger logger;

    protected ExecutorService acceptorExecutorService;

    protected ExecutorService readerExecutorService;

    protected volatile Future self;

    /**
     * @param serverSocket
     * @param serverId
     * @param logger
     * @param acceptorExecutorService
     * @param readerExecutorService
     * @param sessionListener
     */
    public void init(ServerSocket serverSocket, String serverId, Logger logger,
                     ExecutorService acceptorExecutorService,
                     ExecutorService readerExecutorService,
                     SessionManager.SessionListener sessionListener) {
        this.serverSocket = serverSocket;
        this.id = serverId + ":Acceptor";
        this.serverId = serverId;
        this.logger = logger;
        this.acceptorExecutorService = acceptorExecutorService;
        this.readerExecutorService = readerExecutorService;
        this.sessionListener = sessionListener;
    }

    /**
     * Available after {@link #init(java.net.ServerSocket, String, com.tibco.cep.kernel.service.logging.Logger,
     * java.util.concurrent.ExecutorService, java.util.concurrent.ExecutorService, com.tibco.cep.driver.ancillary.api.SessionManager.SessionListener)}
     * .
     *
     * @return
     */
    public String getId() {
        return id;
    }

    public void start() throws Exception {
        self = acceptorExecutorService.submit(this);
    }

    public void run() {
        boolean soTimeoutAlreadySet = false;

        logger.log(Level.INFO, "Server [%s] is ready to accept new connections", this.serverId);

        for (; serverSocket.isClosed() == false && isStopped() == false;) {
            ServerSession session = null;
            Socket socket = null;

            try {
                if (soTimeoutAlreadySet == false) {
                    serverSocket.setSoTimeout(10 * 1000);

                    soTimeoutAlreadySet = true;
                }

                socket = serverSocket.accept();
                session = new ServerSession();
                session.init(socket, serverId, logger, readerExecutorService);
                sessionListener.onNewSession(session);
                session.start();

            }
            catch (SocketTimeoutException ste) {
                continue;
            }
            catch (InterruptedException e) {
                //Ignore.
            }
            catch (Exception e) {
                if (isStopped() == false) {
                    logger.log(Level.ERROR, e, "Error occurred while accepting connection to server [%s]", serverId);
                }

                closeResources(session, socket);
            }
        }

        logger.log(Level.INFO, "Server [%s] will not accept new connections now", serverId);
    }

    private void closeResources(ServerSession session, Socket socket) {
        if (session != null) {
            try {
                session.stop();
            }
            catch (Exception e) {
                logger.log(Level.WARN, e,
                        "Error occurred while cleaning resources after failed connection to server [%s]", serverId);
            }
        }

        if (socket != null && socket.isClosed() == false) {
            try {
                socket.close();
            }
            catch (IOException e) {
                logger.log(Level.WARN, e,
                        "Error occurred while cleaning resources after failed connection to server [%s]", serverId);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        self.cancel(true);

        for (int i = 0; i < 10 && (!self.isCancelled() && !self.isDone()); i++) {
            Thread.sleep(25);
        }
    }
}
