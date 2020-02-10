/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * Used when export of an application fails
 * 
 * @author dijadhav
 *
 */
public class BEApplicationExportException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3352612493482455480L;

	/**
	 * Default constructor
	 */
	public BEApplicationExportException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEApplicationExportException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationExportException(Throwable cause) {
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
	public BEApplicationExportException(String message, Throwable cause) {
		super(message, cause);
	}
}
