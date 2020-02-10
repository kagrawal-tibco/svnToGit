package com.tibco.cep.dashboard.security;

public class InvalidTokenException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3695009913765924858L;

	public InvalidTokenException() {
		super();
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException(Throwable cause) {
		super(cause);
	}

}