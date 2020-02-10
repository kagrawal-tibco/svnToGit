package com.tibco.cep.runtime.service.om.exception;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 16, 2004
 * Time: 4:42:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class OMDeleteException extends OMException {

    public boolean recordNotFound = false;

    public OMDeleteException() {
    }

    public OMDeleteException(String message) {
        super(message);
    }

    public OMDeleteException(Throwable cause) {
        super(cause);
    }

    public OMDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public OMDeleteException(boolean notFound) {
        recordNotFound = notFound;
    }
}
