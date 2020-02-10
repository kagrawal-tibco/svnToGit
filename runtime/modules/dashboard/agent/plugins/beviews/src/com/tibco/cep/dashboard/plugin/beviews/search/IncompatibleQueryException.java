package com.tibco.cep.dashboard.plugin.beviews.search;

public class IncompatibleQueryException extends Exception {

	private static final long serialVersionUID = -4127556582417716086L;

	public IncompatibleQueryException() {
	}

	public IncompatibleQueryException(String message) {
		super(message);
	}

	public IncompatibleQueryException(Throwable cause) {
		super(cause);
	}

	public IncompatibleQueryException(String message, Throwable cause) {
		super(message, cause);
	}

}
