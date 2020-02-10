package com.tibco.cep.bemm.persistence.service.exception;

/**
 * Throws if application profile not exist
 * 
 * @author dijadhav
 *
 */
public class BEApplicationProfileNotExistException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7616681608582549481L;

	/**
	 * Default constructor
	 */
	public BEApplicationProfileNotExistException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEApplicationProfileNotExistException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEApplicationProfileNotExistException(Throwable cause) {
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
	public BEApplicationProfileNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
