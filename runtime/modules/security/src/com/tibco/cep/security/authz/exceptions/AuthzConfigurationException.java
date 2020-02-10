/**
 * 
 */
package com.tibco.cep.security.authz.exceptions;

/**
 * @author aathalye
 *
 */
public class AuthzConfigurationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3518038738957262811L;

	public AuthzConfigurationException(final String message) {
		super(message);
	}
	
	public AuthzConfigurationException(final String msg,
			                           final Throwable th) {
		super(msg, th);
	}
}
