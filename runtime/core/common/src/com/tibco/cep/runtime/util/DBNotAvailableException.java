package com.tibco.cep.runtime.util;

/**
 * Created by IntelliJ IDEA.
 * User: amgupta
 * Date: Mar 17, 2009
 * Time: 1:41:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBNotAvailableException extends RuntimeException {
    // Do not change this message, as clients depend on this message string to find cause of problem
    private static String EXC_MESSAGE = DBException.DatabaseException_MESSAGE + " : Database Not Available.";

    public DBNotAvailableException() {
        super(EXC_MESSAGE);
    }

    public DBNotAvailableException(String msg) {
        super(EXC_MESSAGE + " Exception=" + msg);
    }
    
    public DBNotAvailableException(Throwable throwable) {
        super(EXC_MESSAGE + " Exception=" + throwable.getMessage(), throwable);
    }
}
