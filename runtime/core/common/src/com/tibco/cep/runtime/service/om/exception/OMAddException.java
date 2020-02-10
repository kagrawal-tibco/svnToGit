package com.tibco.cep.runtime.service.om.exception;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 16, 2004
 * Time: 2:25:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class OMAddException extends OMException {

    public boolean duplicate = false;

    public OMAddException() {
        super();
    }

    public OMAddException( String message ) {
        super(message);
    }

    public OMAddException(Throwable cause) {
        super(cause);
    }

    public OMAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public OMAddException(boolean duplicate) {
        this.duplicate = duplicate;
    }
}
