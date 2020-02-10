package com.tibco.cep.dashboard.psvr.context;

/**
 * @author apatil
 *
 */
public class ContextException extends Exception {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6044052694611742163L;

    /**
     * 
     */
    public ContextException() {
        super();
    }

    /**
     * @param message
     */
    public ContextException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ContextException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }

}