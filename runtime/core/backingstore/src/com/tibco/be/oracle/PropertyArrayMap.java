package com.tibco.be.oracle;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;
import oracle.sql.ArrayDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2006
 * Time: 8:26:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayMap extends PropertyMap {
    PropertyMap baseMap;
    int columnIndex;

    /**
     *
     * @param oracle
     * @param pd
     * @param columnName
     */
    public PropertyArrayMap(OracleConnection oracle, Object pd,
                            String columnName, int columnIndex,
                            ArrayDescriptor arrayDescriptor, PropertyMap baseMap) throws SQLException {
        super(pd,columnName, arrayDescriptor);
        this.baseMap=baseMap;
        this.columnIndex=columnIndex;
    }

    /**
     *
     * @return
     */
    public PropertyMap getBaseMap() {
        return baseMap;
    }

    /**
     *
     * @return
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    public int getSQLTypeCode() {
        return baseMap.getSQLTypeCode();
    }
}
