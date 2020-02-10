package com.tibco.cep.bemm.management.exception;

/**
 * Exception is thrown when fail to delete remote file
 * 
 * @author dijadhav
 *
 */
public class BEDeleteRemoteFileException extends Exception {

	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = 6476185656747078444L;

	/**
	 * Default constructor
	 */
	public BEDeleteRemoteFileException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEDeleteRemoteFileException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEDeleteRemoteFileException(Throwable cause) {
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
	public BEDeleteRemoteFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
