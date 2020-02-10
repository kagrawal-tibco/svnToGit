package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Oct 4, 2010
 * Time: 7:00:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEMMException extends Exception{

    public BEMMException() {
        super();
    }

    public BEMMException(String message) {
        super(message);
    }

    public BEMMException(String message, Throwable cause) {
        super(message, cause);
    }

    public BEMMException(Throwable cause) {
        super(cause);
    }
}
