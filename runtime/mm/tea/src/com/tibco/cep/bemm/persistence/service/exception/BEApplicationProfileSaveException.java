/**
 * 
 */
package com.tibco.cep.bemm.persistence.service.exception;

/**
 * Throws if fails to save application profile
 * 
 * @author dijadhav
 *
 */
public class BEApplicationProfileSaveException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -6637454002900721449L;

	/**
	 * Default constructor
	 */
	public BEApplicationProfileSaveException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEApplicationProfileSaveException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationProfileSaveException(Throwable cause) {
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
	public BEApplicationProfileSaveException(String message, Throwable cause) {
		super(message, cause);
	}

}
