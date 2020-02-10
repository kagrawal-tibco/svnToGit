package com.tibco.cep.designtime.model.exception;


/**
 * The base Exception class for BusinessEvents.
 *
 * @author ishaan
 * @version Apr 7, 2004 5:27:06 PM
 */

public class BEException extends Exception {


    public BEException(String message, Throwable cause) {
        super(message, cause);
    }


    public BEException(Throwable cause) {
        super(cause);
    }


    public BEException(String message) {
        super(message);
    }
}
