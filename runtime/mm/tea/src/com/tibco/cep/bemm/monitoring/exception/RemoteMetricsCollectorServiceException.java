package com.tibco.cep.bemm.monitoring.exception;

/**
 * This class is used to throw exception when loading of Business Event
 * application fails.
 * 
 * @author dijadhav
 *
 */
public class RemoteMetricsCollectorServiceException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6381730144845920260L;

	/**
	 * Default constructor
	 */
	public RemoteMetricsCollectorServiceException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public RemoteMetricsCollectorServiceException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public RemoteMetricsCollectorServiceException(Throwable cause) {
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
	public RemoteMetricsCollectorServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
