package com.tibco.cep.bemm.common.exception;

/**
 * Exception is throws if MBean operation fails.
 * 
 * @author dijadhav
 *
 */
public class MBeanOperationFailException extends Exception {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1131198406731042566L;

	/**
	 * Default constructor
	 */
	public MBeanOperationFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public MBeanOperationFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public MBeanOperationFailException(Throwable cause) {
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
	public MBeanOperationFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
