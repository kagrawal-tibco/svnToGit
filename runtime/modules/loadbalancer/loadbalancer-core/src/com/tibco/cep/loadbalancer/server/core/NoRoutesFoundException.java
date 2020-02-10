package com.tibco.cep.loadbalancer.server.core;

/*
* Author: Ashwin Jayaprakash / Date: Jul 22, 2010 / Time: 3:40:37 PM
*/
public class NoRoutesFoundException extends LoadBalancerException {
    public NoRoutesFoundException() {
    }

    public NoRoutesFoundException(String message) {
        super(message);
    }

    public NoRoutesFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRoutesFoundException(Throwable cause) {
        super(cause);
    }
}
