package com.tibco.cep.runtime.service.om.exception;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 16, 2004
 * Time: 2:59:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class OMFetchException extends OMException {
    public OMFetchException() {
        super();
    }

    public OMFetchException(String message) {
        super(message);
    }

    public OMFetchException(Throwable cause) {
        super(cause);
    }

    public OMFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
