package com.tibco.cep.runtime.model.serializers._migration_.command;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 6:16:24 PM
*/
public class NonFatalException extends Exception {
    public NonFatalException(String message) {
        super(message);
    }

    public NonFatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonFatalException(Throwable cause) {
        super(cause);
    }
}
