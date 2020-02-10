/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * Thrown if there is failure while getting JSCH connection
 * 
 * @author dijadhav
 *
 */
public class JschConnectionException extends Exception {

	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = -5590788726992705951L;

	/**
	 * Default constructor
	 */
	public JschConnectionException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public JschConnectionException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public JschConnectionException(Throwable cause) {
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
	public JschConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
