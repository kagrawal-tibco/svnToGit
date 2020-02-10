package com.tibco.cep.loadbalancer.server.core.sink;

import com.tibco.cep.loadbalancer.endpoint.EndpointException;

/*
* Author: Ashwin Jayaprakash / Date: Jul 9, 2010 / Time: 5:23:15 PM
*/
public class SinkException extends EndpointException {
    public SinkException() {
    }

    public SinkException(String message) {
        super(message);
    }

    public SinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public SinkException(Throwable cause) {
        super(cause);
    }
}
