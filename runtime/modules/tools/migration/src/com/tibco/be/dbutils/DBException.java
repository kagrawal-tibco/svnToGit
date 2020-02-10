package com.tibco.be.dbutils;

import com.sleepycat.je.DatabaseException;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 4/16/12
 * Time: 2:18 PM
 */
public class DBException extends DatabaseException {

    public DBException(Throwable t) {
        super(t);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable t) {
        super(message, t);
    }
}
