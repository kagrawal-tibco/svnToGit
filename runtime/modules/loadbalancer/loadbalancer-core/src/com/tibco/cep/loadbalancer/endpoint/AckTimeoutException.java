package com.tibco.cep.loadbalancer.endpoint;

/*
* Author: Ashwin Jayaprakash / Date: Jul 9, 2010 / Time: 5:48:40 PM
*/
public class AckTimeoutException extends EndpointException {
    public AckTimeoutException() {
    }

    public AckTimeoutException(String message) {
        super(message);
    }

    public AckTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public AckTimeoutException(Throwable cause) {
        super(cause);
    }
}
