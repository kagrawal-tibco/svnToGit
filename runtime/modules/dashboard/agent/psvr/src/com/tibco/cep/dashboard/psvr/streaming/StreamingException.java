package com.tibco.cep.dashboard.psvr.streaming;

//PORT StreamingException is not a good name it should be StreamRequestProcessingException
public class StreamingException extends Exception {

	private static final long serialVersionUID = -7940676731461902214L;

	public StreamingException() {
	}

	public StreamingException(String message) {
		super(message);
	}

	public StreamingException(Throwable cause) {
		super(cause);
	}

	public StreamingException(String message, Throwable cause) {
		super(message, cause);
	}

}
