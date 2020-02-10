package com.tibco.cep.dashboard.security;

public class InvalidPrincipalException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6309322284340558949L;

	public InvalidPrincipalException() {
		super();
	}

	public InvalidPrincipalException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPrincipalException(String message) {
		super(message);
	}

	public InvalidPrincipalException(Throwable cause) {
		super(cause);
	}

}