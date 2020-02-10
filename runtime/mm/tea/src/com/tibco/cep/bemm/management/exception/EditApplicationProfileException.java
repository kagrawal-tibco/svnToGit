package com.tibco.cep.bemm.management.exception;

/**
 * Throws if Edit application profile fails
 * 
 * @author dijadhav
 *
 */
public class EditApplicationProfileException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8421239686151560847L;

	/**
	 * Default constructor
	 */
	public EditApplicationProfileException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public EditApplicationProfileException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public EditApplicationProfileException(Throwable cause) {
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
	public EditApplicationProfileException(String message, Throwable cause) {
		super(message, cause);
	}

}
