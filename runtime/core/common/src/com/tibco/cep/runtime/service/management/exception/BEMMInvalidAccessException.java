package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/31/11
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEMMInvalidAccessException extends BEMMException {
    public BEMMInvalidAccessException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BEMMInvalidAccessException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BEMMInvalidAccessException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BEMMInvalidAccessException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
