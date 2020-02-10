package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2010
 * Time: 6:38:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEMMIllegalSetupException extends BEMMException {
    public BEMMIllegalSetupException() {
        super();
    }

    public BEMMIllegalSetupException(String message) {
        super(message);
    }

    public BEMMIllegalSetupException(String message, Throwable cause) {
        super(message, cause);
    }

    public BEMMIllegalSetupException(Throwable cause) {
        super(cause);
    }
}
