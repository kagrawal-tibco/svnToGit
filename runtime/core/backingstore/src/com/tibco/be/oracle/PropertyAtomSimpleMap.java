package com.tibco.be.oracle;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;
import oracle.sql.StructDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2006
 * Time: 7:55:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomSimpleMap extends PropertyMap{
    int columnIndex;
    int sqlTypeCode;

    /**
     *
     * @param oracle
     * @param pd
     * @param columnName
     */
    public PropertyAtomSimpleMap(OracleConnection oracle,
                                 Object pd, String columnName, int columnIndex,
                                 StructDescriptor typeDescriptor, int sqlTypeCode) throws SQLException {
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

    /**
     *
     * @return
     */
    public int getSQLTypeCode() {
        return sqlTypeCode;
    }
}
