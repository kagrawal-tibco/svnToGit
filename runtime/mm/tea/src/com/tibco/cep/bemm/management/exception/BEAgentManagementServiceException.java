package com.tibco.cep.bemm.management.exception;

/**
 * This exception is thrown from
 * 
 * @author dijadhav
 *
 */
public class BEAgentManagementServiceException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1846184052739403303L;

	/**
	 * Default constructor
	 */
	public BEAgentManagementServiceException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEAgentManagementServiceException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEAgentManagementServiceException(Throwable cause) {
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
	public BEAgentManagementServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
