package com.tibco.cep.dashboard.psvr.ogl;

/**
 * @author apatil
 *
 */
public class OGLException extends Exception {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5582046009619287297L;

    /**
     *
     */
    public OGLException() {
        super();
    }

    /**
     * @param message
     */
    public OGLException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public OGLException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public OGLException(String message, Throwable cause) {
        super(message, cause);
    }

}