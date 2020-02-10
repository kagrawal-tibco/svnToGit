package com.tibco.cep.bemm.management.exception;

/**
 * Throws if add application profile fails
 * 
 * @author dijadhav
 *
 */
public class AddApplicationProfileException extends Exception {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5255511574365129914L;

	/**
	 * Default constructor
	 */
	public AddApplicationProfileException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public AddApplicationProfileException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public AddApplicationProfileException(Throwable cause) {
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
	public AddApplicationProfileException(String message, Throwable cause) {
		super(message, cause);
	}
}
