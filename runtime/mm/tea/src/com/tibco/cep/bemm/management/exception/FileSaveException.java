package com.tibco.cep.bemm.management.exception;

/**
 * @author dijadhav
 *
 */
public class FileSaveException extends Exception {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1131198406731042566L;

	/**
	 * Default constructor
	 */
	public FileSaveException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public FileSaveException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public FileSaveException(Throwable cause) {
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
	public FileSaveException(String message, Throwable cause) {
		super(message, cause);
	}
}
