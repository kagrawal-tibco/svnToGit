package com.tibco.cep.runtime.service.om.exception;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 16, 2004
 * Time: 12:11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class OMInitException extends OMException {
    public OMInitException() {
        super();
    }

    public OMInitException(String message) {
        super(message);
    }

    public OMInitException(Throwable cause) {
        super(cause);
    }

    public OMInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
