package com.tibco.rta;

/**
 * This exception is used to indicate exception conditions while performing operations
 * on the {@code RtaSession} and {@code RtaConnection}
 */
public class RtaException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8107486828378860245L;

    /**
     * Instantiates a new rta exception.
     *
     * @param message the message
     */
    public RtaException(String message) {
        super(message);
    }

    public RtaException(Exception e) {
        super(e);
    }

    public RtaException(Throwable e) {
        super(e);
    }
}
