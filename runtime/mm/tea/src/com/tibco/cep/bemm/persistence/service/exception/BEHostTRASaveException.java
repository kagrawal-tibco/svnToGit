package com.tibco.cep.bemm.persistence.service.exception;

/**
 * When persistence of host TRA fails.
 * 
 * @author dijadhav
 *
 */
public class BEHostTRASaveException extends Exception {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 6432736793447821995L;

	/**
	 * Default constructor
	 */
	public BEHostTRASaveException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEHostTRASaveException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEHostTRASaveException(Throwable cause) {
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
	public BEHostTRASaveException(String message, Throwable cause) {
		super(message, cause);
	}
}
