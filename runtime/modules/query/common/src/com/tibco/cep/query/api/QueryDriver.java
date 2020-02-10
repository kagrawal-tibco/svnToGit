package com.tibco.cep.query.api;

import java.util.Properties;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 1:37:12 PM
*/
public interface QueryDriver {
    /**
     * Attempts to connect to a query engine.
     *
     * @param url  String url of the query engine to connect to.
     * @param info Properties.
     * @return QueryConnection.
     * @throws QueryException if the connection could not be established.
     */
    public QueryConnection connect(String url, Properties info) throws QueryException;
}
 
