package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw exception when adding the service instance in
 * Business Event application fails.
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstanceAddException extends Exception {

	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = 2807081210415305758L;

	/**
	 * Default constructor
	 */
	public BEServiceInstanceAddException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEServiceInstanceAddException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEServiceInstanceAddException(Throwable cause) {
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
	public BEServiceInstanceAddException(String message, Throwable cause) {
		super(message, cause);
	}
}
