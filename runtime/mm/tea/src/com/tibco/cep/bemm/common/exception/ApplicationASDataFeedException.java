package com.tibco.cep.bemm.common.exception;

/**
 * @author dijadhav
 *
 */
public class ApplicationASDataFeedException extends Exception {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1131198406731042566L;

	/**
	 * Default constructor
	 */
	public ApplicationASDataFeedException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public ApplicationASDataFeedException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public ApplicationASDataFeedException(Throwable cause) {
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
	public ApplicationASDataFeedException(String message, Throwable cause) {
		super(message, cause);
	}
}
