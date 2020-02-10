package com.tibco.be.model.types;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 3:29:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConversionException extends java.lang.Exception{
    public ConversionException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ConversionException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ConversionException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ConversionException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
