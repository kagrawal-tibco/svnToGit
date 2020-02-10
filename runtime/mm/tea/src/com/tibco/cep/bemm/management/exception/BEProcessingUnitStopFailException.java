/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * Exception is thrown when failed to stop the instance.
 * 
 * @author dijadhav
 *
 */
public class BEProcessingUnitStopFailException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -393162138292460300L;

	/**
	 * Default constructor
	 */
	public BEProcessingUnitStopFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEProcessingUnitStopFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEProcessingUnitStopFailException(Throwable cause) {
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
	public BEProcessingUnitStopFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
