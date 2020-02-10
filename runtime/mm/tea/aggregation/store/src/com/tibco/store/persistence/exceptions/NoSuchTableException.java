package com.tibco.store.persistence.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/1/14
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class NoSuchTableException extends Exception {

    public NoSuchTableException() {
    }

    public NoSuchTableException(String message) {
        super(message);
    }

    public NoSuchTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTableException(Throwable cause) {
        super(cause);
    }
}
