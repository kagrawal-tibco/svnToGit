package com.tibco.be.oracle;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 3, 2006
 * Time: 11:34:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class OraclePropertyAtomInt implements ORAData, ORADataFactory {
    
    public Datum toDatum(Connection connection) throws SQLException {
        return null;
    }

    public ORAData create(Datum datum, int i) throws SQLException {
        return null;
    }
}
