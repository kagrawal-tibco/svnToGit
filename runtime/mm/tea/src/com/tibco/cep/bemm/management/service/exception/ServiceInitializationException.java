package com.tibco.cep.bemm.management.service.exception;

public class ServiceInitializationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -895250416909085429L;

	/**
	 * Default constructor
	 */
	public ServiceInitializationException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public ServiceInitializationException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public ServiceInitializationException(Throwable cause) {
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
	public ServiceInitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
