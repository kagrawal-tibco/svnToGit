package com.tibco.rta.util;

public class RTASecurityException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6015045652901156056L;

	public RTASecurityException() {
	}

	public RTASecurityException(String message) {
		super(message);
	}

	public RTASecurityException(Throwable cause) {
		super(cause);
	}

	public RTASecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public RTASecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
	}
}
