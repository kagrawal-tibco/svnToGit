package com.tibco.cep.dashboard.management;

public class ManagementException extends Exception {

	private static final long serialVersionUID = -6247940425646967993L;

	public ManagementException() {
	}

	public ManagementException(String message) {
		super(message);
	}

	public ManagementException(Throwable cause) {
		super(cause);
	}

	public ManagementException(String message, Throwable cause) {
		super(message, cause);
	}

}