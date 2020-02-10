package com.tibco.cep.bemm.management.exception;

public class BEServiceInstanceSaveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 90668594242393394L;

	/**
	 * Default constructor
	 */
	public BEServiceInstanceSaveException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEServiceInstanceSaveException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEServiceInstanceSaveException(Throwable cause) {
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
	public BEServiceInstanceSaveException(String message, Throwable cause) {
		super(message, cause);
	}	
	
}
