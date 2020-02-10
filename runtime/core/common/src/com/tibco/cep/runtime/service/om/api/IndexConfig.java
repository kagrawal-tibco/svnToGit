package com.tibco.cep.runtime.service.om.api;

/*
* Author: Ashwin Jayaprakash / Date: Sep 8, 2010 / Time: 4:41:43 PM
*/
public class IndexConfig {
    protected String[] fieldNames;

    protected boolean sorted;

    protected boolean sortAscending;

    public IndexConfig() {
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }
}
