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
public class BEProcessingUnitStartFailException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 6374418880051412131L;

	/**
	 * Default constructor
	 */
	public BEProcessingUnitStartFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEProcessingUnitStartFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEProcessingUnitStartFailException(Throwable cause) {
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
	public BEProcessingUnitStartFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
