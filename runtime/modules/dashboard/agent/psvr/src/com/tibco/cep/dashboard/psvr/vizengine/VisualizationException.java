package com.tibco.cep.dashboard.psvr.vizengine;

/**
 * @author apatil
 *
 */
public class VisualizationException extends Exception {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6586258712292477316L;

    /**
     * 
     */
    public VisualizationException() {
        super();
    }

    /**
     * @param message
     */
    public VisualizationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public VisualizationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public VisualizationException(String message, Throwable cause) {
        super(message, cause);
    }

}
