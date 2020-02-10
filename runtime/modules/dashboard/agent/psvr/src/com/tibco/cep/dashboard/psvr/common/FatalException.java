package com.tibco.cep.dashboard.psvr.common;

/**
 * @author apatil
 * 
 */
public class FatalException extends Exception {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2431258861448874831L;

    /**
	 * 
	 */
	public FatalException() {
		super();
	}

	/**
	 * @param message
	 */
	public FatalException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FatalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public FatalException(Throwable cause) {
		super(cause);
	}

}