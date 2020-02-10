package com.tibco.cep.runtime.service.om.exception;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 16, 2004
 * Time: 4:41:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class OMModifyException extends OMException {
    public OMModifyException() {
        super();
    }

    public OMModifyException(String message) {
        super(message);
    }

    public OMModifyException(Throwable cause) {
        super(cause);
    }

    public OMModifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
