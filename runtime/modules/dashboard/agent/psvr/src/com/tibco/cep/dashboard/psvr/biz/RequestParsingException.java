package com.tibco.cep.dashboard.psvr.biz;

/**
 * @author RGupta
 * 
 */
public class RequestParsingException extends Exception {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6107718638429378586L;

    /**
	 * 
	 */
	public RequestParsingException() {
		super();
	}

	/**
	 * @param message
	 */
	public RequestParsingException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public RequestParsingException(Throwable cause) {
		super(cause);
	}

}