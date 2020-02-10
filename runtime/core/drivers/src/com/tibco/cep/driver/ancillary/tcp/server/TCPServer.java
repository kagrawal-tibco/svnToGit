package com.tibco.cep.driver.ancillary.tcp.server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.util.SimpleThreadFactory;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 10:04:17 AM
*/

public class TCPServer implements Service, SessionManager {
    protected ServerSocket serverSocket;

    /**
     * Map being used as a set. Values are dummy.
     */
    protected ConcurrentHashMap<ServerSession, ServerSession> allSessions;

    protected Acceptor acceptor;

    protected ExecutorService acceptorExecutorService;

    protected ExecutorService generalExecutorService;

    protected Logger logger;

    protected SessionListener externalListener;

    protected SessionListenerProxy internalListener;

    protected String name;

    protected InetAddress address;

    protected int port;

    /**
     * @param configuration
     * @param otherArgs     Expects {@link TCPServer.Parameters} in the position 0 in the array, optionally {@link
     *                      com.tibco.cep.kernel.service.logging.Logger} in position 1.
     * @throws java.io.IOException
     */
    public void init(Configuration configuration, Object... otherArgs) throws IOException {
        Parameters parameters = (Parameters) otherArgs[0];
        this.name = parameters.getName();
        this.address = InetAddress.getByName(parameters.getHost());
        this.port = parameters.getPort();

        if (otherArgs.length > 1) {
            this.logger = (Logger) otherArgs[1];
        }
        else {
            this.logger = LogManagerFactory.getLogManager().getLogger(getClass());
        }

        this.allSessions = new ConcurrentHashMap<ServerSession, ServerSession>();

        this.acceptorExecutorService = Executors.newSingleThreadExecutor(
                new SimpleThreadFactory(this.name + "." + Acceptor.class.getSimpleName()));

        /*
         We need 2 separate Thread pools because the acceptor submits a Reader job on the queue
         while running on a thread from the same pool - this seems to cause a dead lock.
         Also, the core pool size has to be at least 1 (> 0) for it to work. JVM Bug - most likely.
         */
        int capacity = Integer.parseInt(System.getProperty("tcp.server.queue.capacity", "1024"));
        this.generalExecutorService = new ThreadPoolExecutor(512, 512,
                3 * 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(capacity),
                new SimpleThreadFactory(this.name + "." + Reader.class.getSimpleName()),
                new ThreadPoolExecutor.CallerRunsPolicy());
        ((ThreadPoolExecutor)generalExecutorService).allowCoreThreadTimeOut(true);

        logger.log(Level.INFO, "TCP Server attempting to bind to address %s on port %d with capacity %d", this.address, this.port, capacity);
        serverSocket = createServerSocket();
        logger.log(Level.INFO, "TCP Server successfully bound to address %s on port %d with capacity %d", this.address, this.port, capacity);

        this.internalListener = new SessionListenerProxy();

        acceptor = createAcceptor();
    }

    private ServerSocket createServerSocket() throws IOException {
        if (this.address == null ||
            this.address.getHostAddress() == null ||
            this.address.getHostName() == null ||
            this.address.getHostAddress().equals("127.0.0.1") ||
            this.address.getHostName().equals("localhost")) {
            return new ServerSocket(this.port, 50);
        }
        else {
            return new ServerSocket(this.port, 50, this.address);
        }
    }

    private Acceptor createAcceptor() {
        Acceptor acceptor = new Acceptor();
        acceptor.init(this.serverSocket, this.name, this.logger, this.acceptorExecutorService,
                this.generalExecutorService, this.internalListener);
        return acceptor;
    }

    /**
     * Available after {@link #init(Configuration, Object...)}.
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Available after {@link #init(Configuration, Object...)}.
     *
     * @return
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Available after {@link #init(Configuration, Object...)}.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Available after {@link #init(Configuration, Object...)}.
     *
     * @return
     */
    public ExecutorService getAcceptorExecutorService() {
        return acceptorExecutorService;
    }

    /**
     * Available after {@link #init(Configuration, Object...)}.
     *
     * @return
     */
    public ExecutorService getGeneralExecutorService() {
        return generalExecutorService;
    }

    /**
     * Available after {@link #init(Configuration, Object...)}.
     *
     * @return
     */
    public String getId() {
        return name;
    }

    public void start() throws Exception {
        acceptor.start();
    }

    public void pause() throws Exception {
        //stop the acceptor
        try {
            acceptor.stop();
        }
        catch (Throwable t) {
            logger.log(Level.WARN, t, "Error occurred while pausing server: %s", this.getId());
        }
        //do not close the sessions
        //close the socket
        serverSocket.close();
    }

    public void resume() throws Exception {
    	//create a new server socket
    	serverSocket = createServerSocket();
    	//create a new acceptor;
    	acceptor = createAcceptor();
    	//start the acceptor
    	acceptor.start();
    }

    public void stop() throws Exception {
        try {
            try {
                acceptor.stop();
            }
            catch (Throwable t) {
                logger.log(Level.WARN, t, "Error occurred while stopping acceptor for server: %s", this.getId());
            }

            for (ServerSession session : allSessions.keySet()) {
                try {
                    session.stop();
                }
                catch (Throwable t) {
                    logger.log(Level.WARN, t, "Error occurred while stopping session for server: %s", this.getId());
                }
            }
            allSessions.clear();

            generalExecutorService.shutdownNow();
            try {
                generalExecutorService.awaitTermination(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                //Ignore.
            }

            //Acceptors seems to get stuck on "socket.accept()".
            acceptorExecutorService.shutdownNow();
            try {
                acceptorExecutorService.awaitTermination(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                //Ignore.
            }
        }
        finally {
            try {
                serverSocket.close();
            }
            catch (Throwable t) {
                logger.log(Level.WARN, t, "Error occurred while closing socket for server: %s", this.getId());
            }
        }

        logger.log(Level.INFO, "Server [%s] has been closed", this.getId());
    }

    //------------

    public Collection<? extends Session> listSessions() {
        return allSessions.keySet();
    }

    public void stopSession(Session session) throws Exception {
        ServerSession localSession = allSessions.remove(session);
        if (localSession == null) {
            return;
        }

        externalListener.removeSession(localSession);

        localSession.stop();
    }

    /**
     * Must be set before calling {@link #start()}.
     *
     * @param listener
     */
    public void setListener(SessionListener listener) {
        externalListener = listener;
    }

    protected void onNewSession(Session session) throws Exception {
        ServerSession sessionImpl = (ServerSession) session;

        externalListener.onNewSession(session);

        allSessions.put(sessionImpl, sessionImpl);
    }

    public SessionListener getListener() {
        return externalListener;
    }

    //------------

    public static class Parameters {
        protected String name;

        protected String host;

        protected int port;

        public Parameters(String name, String host, int port) {
            this.name = name;
            this.host = host;
            this.port = port;
        }

        public String getName() {
            return name;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }

    protected class SessionListenerProxy implements SessionListener {
        public void onNewSession(Session session) throws Exception {
            TCPServer.this.onNewSession(session);
        }

        @Override
        public void removeSession(Session session) {
            try {
                TCPServer.this.stopSession(session);
            }
            catch (Exception e) {
                logger.log(Level.ERROR, e, "Error occurred while stopping session [%s] for server [%s]",
                        session.getId(), getId());
            }
        }
    }
}
