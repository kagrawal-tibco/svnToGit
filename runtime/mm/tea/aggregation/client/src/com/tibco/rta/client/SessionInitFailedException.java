package com.tibco.rta.client;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/6/13
 * Time: 1:52 PM
 * Exception thrown when {@link com.tibco.rta.RtaSession} init fails.
 */
public class SessionInitFailedException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2866642376221753693L;

	public SessionInitFailedException() {
    }

    public SessionInitFailedException(String message) {
        super(message);
    }

    public SessionInitFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionInitFailedException(Throwable cause) {
        super(cause);
    }
}
