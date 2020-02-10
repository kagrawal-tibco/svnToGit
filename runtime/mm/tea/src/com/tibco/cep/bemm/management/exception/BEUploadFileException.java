package com.tibco.cep.bemm.management.exception;

/**
 * This exception is thrown when  file upload fails
 * 
 * @author dijadhav
 *
 */
public class BEUploadFileException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2860073214278610832L;

	/**
	 * Default constructor
	 */
	public BEUploadFileException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEUploadFileException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEUploadFileException(Throwable cause) {
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
	public BEUploadFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
