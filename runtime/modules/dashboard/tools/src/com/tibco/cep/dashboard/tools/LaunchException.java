package com.tibco.cep.dashboard.tools;

public class LaunchException extends Exception {

	private static final long serialVersionUID = -1850836768400586254L;

	public LaunchException() {
	}

	public LaunchException(String message) {
		super(message);
	}

	public LaunchException(Throwable cause) {
		super(cause);
	}

	public LaunchException(String message, Throwable cause) {
		super(message, cause);
	}

}
