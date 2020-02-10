package com.tibco.rta;

import java.io.IOException;

/**
 * An exception to indicate that a connection is refused.
 */
public class ConnectionRefusedException extends IOException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1511313016223119895L;

	public ConnectionRefusedException() {
    }

    public ConnectionRefusedException(String message) {
        super(message);
    }

    public ConnectionRefusedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionRefusedException(Throwable cause) {
        super(cause);
    }
}
