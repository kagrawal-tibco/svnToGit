package com.tibco.be.oracle;

import java.sql.SQLException;

import oracle.sql.TypeDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2006
 * Time: 7:55:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyMap {
    private Object pd;
    private String columnName;
    protected TypeDescriptor typeDescriptor;
    protected String typeDescriptorName;

    public PropertyMap(Object pd, String columnName, TypeDescriptor typeDescriptor) throws SQLException {
        this.pd=pd;
        this.columnName=columnName;
        this.typeDescriptor=typeDescriptor;
        if (typeDescriptor != null)
            this.typeDescriptorName=typeDescriptor.getName();
    }
    /**
     *
     * @return
     */
    public String getColumnName() {
        return columnName;
    }

    public String getTypeDescriptorName() {
        return typeDescriptorName;
    }
    /**
     *
     * @return
     */
    public TypeDescriptor getTypeDescriptor() {
        return typeDescriptor;
    }

    public Object getPd() {
        return pd;
    }

    public void setPd(Object pd) {
        this.pd = pd;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public abstract int getColumnIndex();
    public int getSQLTypeCode() {
        return -1;
    }
}
