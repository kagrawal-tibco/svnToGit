package com.tibco.cep.dashboard.psvr.common;

/**
 * @author apatil
 * 
 */
public class NonFatalException extends Exception {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -259027019970791001L;

    /**
	 * 
	 */
	public NonFatalException() {
		super();
	}

	/**
	 * @param message
	 */
	public NonFatalException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NonFatalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public NonFatalException(Throwable cause) {
		super(cause);
	}
}