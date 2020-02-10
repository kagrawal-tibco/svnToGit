package com.tibco.cep.query.api;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 3:01:10 PM
*/
public interface Row {
    void discard();

    Object getColumn(String name);

    Object getColumn(int index);

    public Object[] getColumns();

    int getSize();
}
