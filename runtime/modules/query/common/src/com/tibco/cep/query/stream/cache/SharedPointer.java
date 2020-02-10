package com.tibco.cep.query.stream.cache;

/*
* Author: Ashwin Jayaprakash Date: May 9, 2008 Time: 3:40:13 PM
*/
public interface SharedPointer {
    SharedObjectSource getSource();

    Object getKey();

    Object getObject();
}
