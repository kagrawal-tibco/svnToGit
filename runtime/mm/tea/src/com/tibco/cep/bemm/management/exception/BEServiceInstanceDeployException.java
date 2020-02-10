/**
 * 
 */
package com.tibco.cep.bemm.management.exception;

/**
 * This class is used to throw the exception when deployment related
 * things(deploy, un-deploy,re-deploy) fails
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstanceDeployException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6963276058149997778L;

	/**
	 * Default constructor
	 */
	public BEServiceInstanceDeployException() {
	}

	/**
	 * Parameterized constructor to set error message.
	 * 
	 * @param message
	 *            -Message shown when exception is thrown.
	 */
	public BEServiceInstanceDeployException(String message) {
		super(message);
	}

	/**
	 * Parameterized constructor used to set cause of exception.
	 * 
	 * @param cause
	 *            - Cause of Exception.
	 */
	public BEServiceInstanceDeployException(Throwable cause) {
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
	public BEServiceInstanceDeployException(String message, Throwable cause) {
		super(message, cause);
	}
}
