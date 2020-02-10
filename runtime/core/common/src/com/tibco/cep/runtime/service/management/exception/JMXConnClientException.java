package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/31/11
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXConnClientException extends Exception {
    public JMXConnClientException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public JMXConnClientException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public JMXConnClientException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public JMXConnClientException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
