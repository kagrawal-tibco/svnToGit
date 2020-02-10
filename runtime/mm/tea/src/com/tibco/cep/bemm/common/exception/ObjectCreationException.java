package com.tibco.cep.bemm.common.exception;

/**
 * Exception is thrown when creation of object fails.
 * 
 * @author dijadhav
 *
 */
public class ObjectCreationException extends Exception {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2432264704638102440L;

	/**
	 * Default constructor
	 */
	public ObjectCreationException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public ObjectCreationException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public ObjectCreationException(Throwable cause) {
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
	public ObjectCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
