package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw the exception when JSCH authentication fails.
 * 
 * @author dijadhav
 *
 */
public class JschAuthenticationException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 8611778790082534155L;

	/**
	 * Default constructor
	 */
	public JschAuthenticationException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public JschAuthenticationException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public JschAuthenticationException(Throwable cause) {
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
	public JschAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
