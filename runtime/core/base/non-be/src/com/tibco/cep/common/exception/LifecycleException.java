package com.tibco.cep.common.exception;

/*
* Author: Ashwin Jayaprakash Date: Jul 23, 2009 Time: 10:45:19 AM
*/
public class LifecycleException extends Exception {
    public LifecycleException() {
    }

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }
}
