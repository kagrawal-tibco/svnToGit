/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * Exception is thrown when loading of deployment variable fails.
 * 
 * @author dijadhav
 *
 */
public class DeploymentVariableLoadFailException extends Exception {
	/**
	 * Serial Version UID.
	 */

	private static final long serialVersionUID = 7971580196992452025L;

	/**
	 * Default constructor
	 */
	public DeploymentVariableLoadFailException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public DeploymentVariableLoadFailException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public DeploymentVariableLoadFailException(Throwable cause) {
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
	public DeploymentVariableLoadFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
