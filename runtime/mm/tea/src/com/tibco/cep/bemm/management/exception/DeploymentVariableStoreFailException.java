/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * Exception is thrown when storing of deployment variable fails.
 * 
 * @author dijadhav
 *
 */
public class DeploymentVariableStoreFailException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -3912995529165360336L;

	/**
	 * Default constructor
	 */
	public DeploymentVariableStoreFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public DeploymentVariableStoreFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public DeploymentVariableStoreFailException(Throwable cause) {
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
	public DeploymentVariableStoreFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
