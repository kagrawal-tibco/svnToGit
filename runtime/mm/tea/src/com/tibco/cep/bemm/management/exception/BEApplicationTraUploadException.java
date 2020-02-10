package com.tibco.cep.bemm.management.exception;

/**
 * This exception is thrown when TRA file upload fails
 * 
 * @author dijadhav
 *
 */
public class BEApplicationTraUploadException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1846184052739403303L;

	/**
	 * Default constructor
	 */
	public BEApplicationTraUploadException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEApplicationTraUploadException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationTraUploadException(Throwable cause) {
		super(cause);
	}

	/**
	 * Parameterized constructor used to set error message and cause of
	 * exception.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationTraUploadException(String message, Throwable cause) {
		super(message, cause);
	}
}
