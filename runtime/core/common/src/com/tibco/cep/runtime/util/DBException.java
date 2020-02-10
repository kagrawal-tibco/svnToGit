package com.tibco.cep.runtime.util;

/**
 * Created by IntelliJ IDEA.
 * User: amgupta
 * Date: Sep 3, 2009
 * Time: 10:43:40 AM
 * This exception is used to denote various types of DB errors
 */
public class DBException extends RuntimeException {
    // Do not change this message, as Tangosol clients depend on this message string to find cause of problem
    public final static String DatabaseException_MESSAGE ="DatabaseException";
    public final static String PersistersOffline_MESSAGE ="PERSISTER_OFFLINE";
    public final static String OperationTimeout_MESSAGE = "OPERATION_TIMEOUT";
    private static String EXC_MESSAGE = DatabaseException_MESSAGE + " : Database Not Available.";

    public DBException() {
        super(EXC_MESSAGE);
    }

    public DBException(String msg) {
        super(EXC_MESSAGE + ", Exception=" + msg);
    }
}