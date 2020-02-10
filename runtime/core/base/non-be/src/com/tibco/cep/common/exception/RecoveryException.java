package com.tibco.cep.common.exception;

/*
* Author: Ashwin Jayaprakash Date: Jul 13, 2009 Time: 1:21:42 PM
*/
public class RecoveryException extends Exception {
    public RecoveryException() {
    }

    public RecoveryException(String message) {
        super(message);
    }

    public RecoveryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecoveryException(Throwable cause) {
        super(cause);
    }
}
