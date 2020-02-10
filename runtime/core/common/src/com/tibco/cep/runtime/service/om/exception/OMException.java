package com.tibco.cep.runtime.service.om.exception;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 22, 2006
 * Time: 1:51:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class OMException extends Exception {

    public OMException() {
        super();
    }

    public OMException(String message) {
        super(message);
    }

    public OMException(Throwable cause) {
        super(cause);
    }

    public OMException(String message, Throwable cause) {
        super(message, cause);
    }

}
