package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw exception when copy of instance fails.
 * 
 * @author dijadhav
 *
 */
public class BeServiceInstanceCopyException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2915749576130881133L;

	/**
	 * Default constructor
	 */
	public BeServiceInstanceCopyException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BeServiceInstanceCopyException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BeServiceInstanceCopyException(Throwable cause) {
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
	public BeServiceInstanceCopyException(String message, Throwable cause) {
		super(message, cause);
	}
}
