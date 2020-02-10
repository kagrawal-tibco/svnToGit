package com.tibco.cep.driver.ancillary.tcp;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.tibco.cep.driver.ancillary.api.AbstractManagedResource;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 10:04:17 AM
*/

public class ReaderImpl extends AbstractManagedResource implements Runnable, Reader {
    protected InputStream inputStream;

    protected byte[] buffer;

    protected ReaderListener optionalListener;

    protected String id;

    protected String sessionId;

    protected Logger logger;

    protected Socket socket;

    protected ExecutorService executorService;

    protected volatile Future selfListenerNotifier;

    public void init(Socket socket, String sessionId, Logger logger,
                     ExecutorService executorService) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.id = sessionId + ":Reader";
        this.sessionId = sessionId;
        this.logger = logger;
        this.executorService = executorService;
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

    public void setOptionalListener(ReaderListener listener) {
        this.optionalListener = listener;
    }

    public ReaderListener getOptionalListener() {
        return optionalListener;
    }

    public boolean isReaderNotificationSynchronous() {
        return true;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void start() throws Exception {
        if (optionalListener != null) {
            buffer = new byte[8192];
            selfListenerNotifier = executorService.submit(this);
        }
    }

    public int read(byte[] data, int offset, int length) throws Exception {
        return inputStream.read(data, offset, length);
    }

    /**
     * Only when {@link #optionalListener} is set.
     */
    public void run() {
        while (socket.isInputShutdown() == false && isStopped() == false) {
            try {
                int c = inputStream.read(buffer);
                if (c > 0) {
                    optionalListener.onData(buffer, 0, c);
                }
                //EOF.
                else if (c == -1) {
                    break;
                }
            }
            catch (SocketTimeoutException e) {
                continue;
            }
            catch (Throwable t) {
                if (isStopped() == false) {
                    optionalListener.onException(new RuntimeException(t));

                    break;
                }
                else {
                    logger.log(Level.DEBUG, t, "Error occurred while reading in session: " + sessionId);
                }
            }
        }

        optionalListener.onEnd();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (selfListenerNotifier != null) {
            selfListenerNotifier.cancel(true);
        }

        try {
            if (socket.isInputShutdown() == false) {
                socket.shutdownInput();

                logger.log(Level.DEBUG, "Closed reader stream for session: %s", this.sessionId);
            }
        }
        catch (Exception e) {
            logger.log(Level.WARN, e, "Error occurred while closing reader stream for session: %s", this.sessionId);
        }
    }
}
