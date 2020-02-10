package com.tibco.cep.driver.ancillary.tcp;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.tibco.cep.driver.ancillary.api.AbstractManagedResource;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 3:59:22 PM
*/

public class WriterImpl extends AbstractManagedResource implements Writer {
    protected OutputStream outputStream;

    protected int bytesWritten;

    protected String id;

    protected String sessionId;

    protected Logger logger;

    protected Socket socket;

    public void init(Socket socket, String sessionId, Logger logger)
            throws IOException {
        this.socket = socket;
        this.outputStream = socket.getOutputStream();
        this.id = sessionId + ":Writer";
        this.sessionId = sessionId;
        this.logger = logger;
    }

    /**
     * Available after {@link #init(java.net.Socket, String, com.tibco.cep.kernel.service.logging.Logger)} .
     *
     * @return
     */
    public String getId() {
        return id;
    }

    public boolean isWriteSynchronous() {
        return true;
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void start() throws Exception {
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        outputStream.write(data, offset, length);

        bytesWritten += length;
        if (bytesWritten > 4096) {
            outputStream.flush();
            bytesWritten = 0;
        }
    }

    public void flush() throws Exception {
        outputStream.flush();
        bytesWritten = 0;
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        try {
            if (socket.isClosed() == false) {
                if (socket.isOutputShutdown() == false) {
                    outputStream.flush();

                    socket.shutdownOutput();

                    logger.log(Level.DEBUG, "Closed writer stream for session: " + this.sessionId);
                }
            }
        }
        catch (Exception e) {
            logger.log(Level.WARN, e, "Error occurred while closing writer stream for session: " + this.sessionId);
        }
    }
}
