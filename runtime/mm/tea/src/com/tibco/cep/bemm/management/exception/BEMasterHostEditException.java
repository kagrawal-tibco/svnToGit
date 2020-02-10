package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw exception when adding the host in Business Event
 * application fails.
 * 
 * @author dijadhav
 *
 */
public class BEMasterHostEditException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6381730144845920260L;

	/**
	 * Default constructor
	 */
	public BEMasterHostEditException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEMasterHostEditException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEMasterHostEditException(Throwable cause) {
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
	public BEMasterHostEditException(String message, Throwable cause) {
		super(message, cause);
	}
}
