package com.tibco.cep.bemm.management.exception;

/**
 * Throws when failed to geth JMX connection
 * 
 * @author dijadhav
 *
 */
public class JmxConnectionException extends Exception {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6928151036550696208L;

	/**
	 * Default Constructor
	 */
	public JmxConnectionException() {
	}

	/**
	 * Set exception message
	 * 
	 * @param message
	 */
	public JmxConnectionException(String message) {
		super(message);
	}

	/**
	 * Set exception cause
	 * 
	 * @param cause
	 */
	public JmxConnectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Set exception message and cause
	 * 
	 * @param message
	 * @param cause
	 */
	public JmxConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
