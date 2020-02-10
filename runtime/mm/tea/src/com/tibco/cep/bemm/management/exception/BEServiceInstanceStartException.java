/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw the exception when start of processing unit
 * fails.
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstanceStartException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 6374418880051412131L;

	/**
	 * Default constructor
	 */
	public BEServiceInstanceStartException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEServiceInstanceStartException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEServiceInstanceStartException(Throwable cause) {
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
	public BEServiceInstanceStartException(String message, Throwable cause) {
		super(message, cause);
	}
}
