package com.tibco.be.oracle.serializers;

import oracle.sql.Datum;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 24, 2009
 * Time: 1:21:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OracleSerializer {
    Datum[] getOracleAttributes() throws Exception;
    boolean hasErrors();
    String getErrorMessage();
    void release();
}
