package com.tibco.be.oracle;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;
import oracle.sql.ArrayDescriptor;
import oracle.sql.StructDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2006
 * Time: 8:11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomMap extends PropertyMap{
    private ArrayDescriptor  historyTableDescriptor;
    private StructDescriptor historyTupleDescriptor;
    private StructDescriptor primitiveDescriptor;
    int columnIndex;

    public PropertyAtomMap(OracleConnection oracle, Object pd,
                           String columnName, int columnIndex, StructDescriptor typeDescriptor,
                           ArrayDescriptor  historyTableDescriptor,
                           StructDescriptor historyTupleDescriptor,
                           StructDescriptor primitiveDescriptor) throws SQLException {
        super(pd, columnName, typeDescriptor);
        this.setHistoryTableDescriptor(historyTableDescriptor);
        this.setHistoryTupleDescriptor(historyTupleDescriptor);
        this.setPrimitiveDescriptor(primitiveDescriptor);
        this.columnIndex=columnIndex;
    }


    public void setTypeDescriptor(StructDescriptor typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
    }

    public ArrayDescriptor getHistoryTableDescriptor() {
        return historyTableDescriptor;
    }

    public void setHistoryTableDescriptor(ArrayDescriptor historyTableDescriptor) {
        this.historyTableDescriptor = historyTableDescriptor;
    }

    public StructDescriptor getHistoryTupleDescriptor() {
        return historyTupleDescriptor;
    }

    public void setHistoryTupleDescriptor(StructDescriptor historyTupleDescriptor) {
        this.historyTupleDescriptor = historyTupleDescriptor;
    }

    public StructDescriptor getPrimitiveDescriptor() {
        return primitiveDescriptor;
    }

    public void setPrimitiveDescriptor(StructDescriptor primitiveDescriptor) {
        this.primitiveDescriptor = primitiveDescriptor;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
