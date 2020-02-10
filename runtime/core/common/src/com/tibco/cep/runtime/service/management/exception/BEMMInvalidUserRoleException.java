package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/31/11
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEMMInvalidUserRoleException extends BEMMException {
    public BEMMInvalidUserRoleException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BEMMInvalidUserRoleException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BEMMInvalidUserRoleException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BEMMInvalidUserRoleException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
