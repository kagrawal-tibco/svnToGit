package com.tibco.cep.query.api;

/*
* Author: Ashwin Jayaprakash Date: Jun 04, 2008 Time: 5:09:57 PM
*/
public class QueryException extends Exception {
    /**
     * {@inheritDoc}
     */
    public QueryException() {
    }

    /**
     * {@inheritDoc}
     */
    public QueryException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public QueryException(Throwable cause) {
        super(cause);
    }
}
