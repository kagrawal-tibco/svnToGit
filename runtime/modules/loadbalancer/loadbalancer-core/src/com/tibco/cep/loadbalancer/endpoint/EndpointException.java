package com.tibco.cep.loadbalancer.endpoint;

/*
* Author: Ashwin Jayaprakash / Date: Jul 21, 2010 / Time: 4:57:45 PM
*/
public class EndpointException extends Exception {
    public EndpointException() {
    }

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }
}
