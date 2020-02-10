package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw the exception when download of instance log
 * fails.
 * 
 * @author dijadhav
 *
 */
public class DownloadInstanceLogException extends Exception {

	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = 1337915139065466424L;

	/**
	 * Default constructor
	 */
	public DownloadInstanceLogException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public DownloadInstanceLogException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public DownloadInstanceLogException(Throwable cause) {
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
	public DownloadInstanceLogException(String message, Throwable cause) {
		super(message, cause);
	}
}
