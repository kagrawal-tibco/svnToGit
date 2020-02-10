package com.tibco.cep.bemm.management.exception;

public class BEApplicationSaveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3510117372982620086L;

	/**
	 * Default constructor
	 */
	public BEApplicationSaveException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEApplicationSaveException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationSaveException(Throwable cause) {
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
	public BEApplicationSaveException(String message, Throwable cause) {
		super(message, cause);
	}	
}
