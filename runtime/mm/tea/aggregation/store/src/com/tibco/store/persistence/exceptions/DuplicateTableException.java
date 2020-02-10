package com.tibco.store.persistence.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/1/14
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateTableException extends Exception {

    public DuplicateTableException() {
    }

    public DuplicateTableException(String message) {
        super(message);
    }

    public DuplicateTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTableException(Throwable cause) {
        super(cause);
    }
}
