package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw exception when JSCH command execution fails.
 * 
 * @author dijadhav
 *
 */
public class JschCommandFailException extends Exception {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 919651150501478793L;

	/**
	 * Default constructor
	 */
	public JschCommandFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public JschCommandFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public JschCommandFailException(Throwable cause) {
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
	public JschCommandFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
