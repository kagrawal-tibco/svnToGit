package com.tibco.cep.bemm.management.exception;

/**
 * Throws if delete application profile fails
 * 
 * @author dijadhav
 *
 */
public class DeleteApplicationProfileException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 5941230966424265395L;

	/**
	 * Default constructor
	 */
	public DeleteApplicationProfileException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public DeleteApplicationProfileException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public DeleteApplicationProfileException(Throwable cause) {
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
	public DeleteApplicationProfileException(String message, Throwable cause) {
		super(message, cause);
	}

}
