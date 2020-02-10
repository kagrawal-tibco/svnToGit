package com.tibco.cep.dashboard.psvr.alerts;

public class ExecException extends Exception {

	private static final long serialVersionUID = 4820977946551920408L;

	public ExecException() {
	}

	public ExecException(String message) {
		super(message);
	}

	public ExecException(Throwable cause) {
		super(cause);
	}

	public ExecException(String message, Throwable cause) {
		super(message, cause);
	}

}
