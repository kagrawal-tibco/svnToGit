package com.tibco.cep.driver.ancillary.tcp.server;


import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.driver.ancillary.api.AbstractManagedResource;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.driver.ancillary.tcp.ReaderImpl;
import com.tibco.cep.driver.ancillary.tcp.WriterImpl;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 10:04:17 AM
*/

public class ServerSession extends AbstractManagedResource implements Session {
    protected Socket socket;

    protected ReaderImpl reader;

    protected WriterImpl writer;

    protected String id;

    protected String serverId;

    protected Logger logger;

    public void init(Socket socket, String serverId, Logger logger, ExecutorService executorService)
            throws IOException {
        this.socket = socket;
        this.id = serverId + ":Session:" + socket.getRemoteSocketAddress();
        this.serverId = serverId;
        this.logger = logger;

        this.reader = new ReaderImpl();
        this.reader.init(socket, id, logger, executorService);

        this.writer = new com.tibco.cep.driver.ancillary.tcp.WriterImpl();
        this.writer.init(socket, id, logger);
    }

    /**
     * Available after {@link #init(java.net.Socket, String, com.tibco.cep.kernel.service.logging.Logger,
     * java.util.concurrent.ExecutorService)} .
     *
     * @return
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * Available after {@link #init(java.net.Socket, String, com.tibco.cep.kernel.service.logging.Logger,
     * java.util.concurrent.ExecutorService)} .
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Available after {@link #init(java.net.Socket, String, com.tibco.cep.kernel.service.logging.Logger,
     * java.util.concurrent.ExecutorService)} .
     *
     * @return
     */
    public Writer getWriter() {
        return writer;
    }

    /**
     * Available after {@link #init(java.net.Socket, String, com.tibco.cep.kernel.service.logging.Logger,
     * java.util.concurrent.ExecutorService)} .
     *
     * @return
     */
    public Reader getReader() {
        return reader;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Starts the {@link #reader} and {@link #writer}.
     *
     * @throws Exception
     */
    public void start() throws Exception {
        reader.start();
        writer.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (writer != null) {
            try {
                writer.stop();
            }
            catch (Exception e) {
                logger.log(Level.WARN, e, "Error occurred while stopping writer for session: " + getId());
            }
        }

        if (reader != null) {
            try {
                reader.stop();
            }
            catch (Exception e) {
                logger.log(Level.WARN, e, "Error occurred while stopping reader for session: " + getId());
            }
        }

        try {
            socket.close();
        }
        catch (Exception e) {
            logger.log(Level.WARN, e, "Error occurred while closing socket for session: " + getId());
        }

        logger.log(Level.INFO, "Closed socket for session: " + getId());
    }
}
