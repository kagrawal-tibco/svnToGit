package com.tibco.cep.loadbalancer.server.core;

/*
* Author: Ashwin Jayaprakash / Date: Jul 22, 2010 / Time: 3:55:46 PM
*/
public class LoadBalancerException extends Exception {
    public LoadBalancerException() {
    }

    public LoadBalancerException(String message) {
        super(message);
    }

    public LoadBalancerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadBalancerException(Throwable cause) {
        super(cause);
    }
}
