package com.tibco.rta.client;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/12/12
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionException extends Exception {

 	private static final long serialVersionUID = 8284580909171983331L;

	public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
