package com.tibco.be.oracle;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.ARRAY;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.STRUCT;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 3, 2006
 * Time: 11:51:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEntity implements ORAData, ORADataFactory {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(OracleEntity.class);
    private final static OracleEntity _factory = new OracleEntity();

    public static ORADataFactory getORADataFactory() {
        return _factory;
    }

    public Datum toDatum(Connection connection) throws SQLException {
        return null;
    }

    public ORAData create(Datum datum, int sqlType) throws SQLException {
        if (datum instanceof STRUCT) {
            STRUCT s = (STRUCT) datum;
            printStruct(s);
        } else {
            logger.log(Level.DEBUG, "Non-Struct Datum %s", datum);
        }
        return null;
    }

    private void printStruct(STRUCT s) throws SQLException {
        logger.log(Level.DEBUG, "Oracle Type = %s", s.getSQLTypeName());
        //Object[] attributes= s.getAttributes();
        Object[] attributes= s.getOracleAttributes();
        for (int i=0; i < attributes.length; i++) {
            logger.log(Level.DEBUG, "Attribute Object = %s", attributes[i]);
            if (attributes[i] instanceof STRUCT) {
                printStruct((STRUCT) attributes[i]);
            } else if (attributes[i] instanceof ARRAY) {
                printArray((ARRAY) attributes[i]);
            }
        }
    }

    private void printArray(ARRAY s) throws SQLException {
        logger.log(Level.DEBUG, "[ARRAY]Oracle Type = %s", s.getSQLTypeName());
        logger.log(Level.DEBUG, "[ARRAY]Oracle Base Type = %s", s.getBaseTypeName());
        Datum[] elements= s.getOracleArray();
        for (int i=0; i < elements.length; i++) {
            if (elements[i] instanceof STRUCT) {
                printStruct((STRUCT) elements[i]);
            } else if (elements[i] instanceof ARRAY) {
                printArray((ARRAY) elements[i]);
            } else {
                logger.log(Level.DEBUG, "Non-Struct Datum %s", elements[i]);
            }
        }
    }
}
