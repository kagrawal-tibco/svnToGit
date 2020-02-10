package com.tibco.store.persistence.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/1/14
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateColumnException extends Exception {

    public DuplicateColumnException() {
    }

    public DuplicateColumnException(String message) {
        super(message);
    }

    public DuplicateColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateColumnException(Throwable cause) {
        super(cause);
    }
}
