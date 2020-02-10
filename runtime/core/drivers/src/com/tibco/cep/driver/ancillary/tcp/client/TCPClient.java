package com.tibco.cep.driver.ancillary.tcp.client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.driver.ancillary.api.AbstractManagedResource;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.driver.ancillary.tcp.ReaderImpl;
import com.tibco.cep.driver.ancillary.tcp.WriterImpl;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 4:47:44 PM
*/

public class TCPClient extends AbstractManagedResource implements Session {
    protected Socket socket;

    protected ReaderImpl reader;

    protected WriterImpl writer;

    protected Logger logger;

    protected ExecutorService readerExecutorService;

    protected String name;

    protected InetAddress address;

    protected int port;

    /**
     * @param parameters
     * @param logger
     * @param readerExecutorService
     * @throws java.io.IOException
     */
    public void init(Parameters parameters, Logger logger, ExecutorService readerExecutorService)
            throws IOException {
        this.name = parameters.getName();
        this.address = InetAddress.getByName(parameters.getHost());
        this.port = parameters.getPort();

        this.logger = logger;

        this.readerExecutorService = readerExecutorService;

        this.socket = new Socket(this.address, this.port);

        this.reader = new ReaderImpl();
        this.reader.init(this.socket, this.name, logger, readerExecutorService);

        this.writer = new WriterImpl();
        this.writer.init(this.socket, this.name, logger);
    }

    /**
     * @param parameters
     * @param readerExecutorService
     * @throws java.io.IOException
     */
    public void init(Parameters parameters, ExecutorService readerExecutorService)
            throws IOException {
        Logger l = LogManagerFactory.getLogManager().getLogger(getClass());

        init(parameters, l, readerExecutorService);
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Available after {@link #init(com.tibco.cep.driver.ancillary.tcp.client.TCPClient.Parameters,com.tibco.cep.kernel.service.logging.Logger,java.util.concurrent.ExecutorService)}.
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Available after {@link #init(com.tibco.cep.driver.ancillary.tcp.client.TCPClient.Parameters,com.tibco.cep.kernel.service.logging.Logger,java.util.concurrent.ExecutorService)}.
     *
     * @return
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Available after {@link #init(com.tibco.cep.driver.ancillary.tcp.client.TCPClient.Parameters,com.tibco.cep.kernel.service.logging.Logger,java.util.concurrent.ExecutorService)}.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Available after {@link #init(com.tibco.cep.driver.ancillary.tcp.client.TCPClient.Parameters,com.tibco.cep.kernel.service.logging.Logger,java.util.concurrent.ExecutorService)}.
     *
     * @return
     */
    public String getId() {
        return name;
    }

    public Reader getReader() {
        return reader;
    }

    public Writer getWriter() {
        return writer;
    }

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
                logger.log(Level.WARN, "Error occurred while stopping writer for session: %s", this.getId());
            }
        }

        if (reader != null) {
            try {
                reader.stop();
            }
            catch (Exception e) {
                logger.log(Level.WARN, "Error occurred while stopping reader for session: %s", this.getId());
            }
        }

        try {
            socket.close();
        }
        catch (IOException e) {
            logger.log(Level.WARN, "Error occurred while closing socket for session: %s", this.getId());
        }
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
}
