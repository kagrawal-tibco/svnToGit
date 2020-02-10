package com.tibco.be.oracle;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;
import oracle.sql.StructDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 23, 2006
 * Time: 2:28:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventPropertyMap extends PropertyMap{
    int columnIndex;
    int sqlTypeCode;

    /**
     *
     * @param oracle
     * @param pd
     * @param columnName
     */
    public EventPropertyMap(OracleConnection oracle,
                            Object pd, int columnIndex,
                            String columnName, StructDescriptor typeDescriptor, int sqlTypeCode) throws SQLException {
        super(pd, columnName, typeDescriptor);
        this.columnIndex=columnIndex;
        this.sqlTypeCode=sqlTypeCode;
    }

    /**
     *
     * @return
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    public int getSQLTypeCode() {
        return sqlTypeCode;
    }
}
