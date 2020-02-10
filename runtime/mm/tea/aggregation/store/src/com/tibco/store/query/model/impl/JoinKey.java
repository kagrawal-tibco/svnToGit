package com.tibco.store.query.model.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 7/1/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinKey {

    private String tableName;

    private String columnName;

    public JoinKey(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinKey joinKey = (JoinKey) o;

        return columnName.equals(joinKey.columnName) && tableName.equals(joinKey.tableName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public int hashCode() {
        int result = tableName.hashCode();
        result = 31 * result + columnName.hashCode();
        return result;
    }
}
