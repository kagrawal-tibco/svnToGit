package com.tibco.cep.query.stream.group;

/*
* Author: Ashwin Jayaprakash Date: Jun 5, 2008 Time: 3:12:38 PM
*/
public interface GroupKey {
    Object[] getGroupColumns();

    int getGroupHash();
}
