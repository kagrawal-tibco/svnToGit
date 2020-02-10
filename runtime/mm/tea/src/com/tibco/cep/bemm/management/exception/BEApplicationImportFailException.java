package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw exception when importing of Business Event
 * application fails.
 * 
 * @author dijadhav
 *
 */
public class BEApplicationImportFailException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2439071960080906044L;

	/**
	 * Default constructor
	 */
	public BEApplicationImportFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEApplicationImportFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationImportFailException(Throwable cause) {
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
	public BEApplicationImportFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
