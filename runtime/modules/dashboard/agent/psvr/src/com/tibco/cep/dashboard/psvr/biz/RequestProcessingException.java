package com.tibco.cep.dashboard.psvr.biz;

/**
 * @author RGupta
 * 
 */
public class RequestProcessingException extends Exception {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3111545963300533592L;

    /**
	 * 
	 */
	public RequestProcessingException() {
		super();
	}

	/**
	 * @param message
	 */
	public RequestProcessingException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public RequestProcessingException(Throwable cause) {
		super(cause);
	}

}