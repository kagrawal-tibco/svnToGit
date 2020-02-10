package com.tibco.cep.runtime.model.element;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 30, 2006
 * Time: 12:16:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExtIdAlreadyBoundException extends RuntimeException {
    public ExtIdAlreadyBoundException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ExtIdAlreadyBoundException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ExtIdAlreadyBoundException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ExtIdAlreadyBoundException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
