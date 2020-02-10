package com.tibco.cep.loadbalancer.impl.server.transport.tcp;

/*
* Author: Ashwin Jayaprakash / Date: Jul 30, 2010 / Time: 2:36:39 PM
*/

/**
 * No stack trace.
 */
public class NegAckLiteException extends Exception {
    public NegAckLiteException() {
    }

    public NegAckLiteException(String message) {
        super(message);
    }

    public NegAckLiteException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegAckLiteException(Throwable cause) {
        super(cause);
    }

    /**
     * No-op.
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
