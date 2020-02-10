package com.tibco.cep.loadbalancer.impl.transport.tcp;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.driver.ancillary.api.Reader.ReaderListener;
import com.tibco.cep.loadbalancer.impl.transport.StreamMessageCodec;
import com.tibco.cep.loadbalancer.impl.transport.StreamMessageCodec.StreamDecodeComboException;

/*
* Author: Ashwin Jayaprakash / Date: Jul 29, 2010 / Time: 2:25:01 PM
*/

public abstract class AbstractTcpReaderListener implements ReaderListener {
    protected StreamMessageCodec messageCodec;

    protected String sessionId;

    protected Logger logger;

    public AbstractTcpReaderListener(String sessionId, StreamMessageCodec messageCodec, Logger logger) {
        this.sessionId = sessionId;
        this.messageCodec = messageCodec;
        this.logger = logger;
    }

    @Override
    public final void onData(byte[] data, int offset, int length) throws Exception {
        Object fullObject = null;

        try {
            fullObject = messageCodec.read(data, offset, length);

            callOnMessage(fullObject);
        }
        catch (StreamDecodeComboException e) {
            fullObject = e.getBatchValues();

            if (fullObject != null) {
                logger.log(Level.SEVERE,
                        "An error occured while decoding the stream from the session [" + sessionId + "]." +
                                " There are a few messages that were decoded successfully and those will be processed now.",
                        e);

                callOnMessage(fullObject);
            }

            throw e;
        }
    }

    private void callOnMessage(Object fullObject) {
        if (fullObject == null) {
            return;
        }

        if (fullObject instanceof List) {
            List<Object> list = (List<Object>) fullObject;

            for (Object o : list) {
                onMessage(o);
            }
        }
        else {
            onMessage(fullObject);
        }
    }

    /**
     * @param message Always a single message.
     */
    protected abstract void onMessage(Object message);

    public void discard() {
        messageCodec.discard();
        messageCodec = null;
    }
}
