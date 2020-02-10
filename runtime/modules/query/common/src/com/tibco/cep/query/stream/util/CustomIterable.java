package com.tibco.cep.query.stream.util;

/*
* Author: Ashwin Jayaprakash Date: May 29, 2009 Time: 1:53:24 PM
*/
public interface CustomIterable<X> extends Iterable<X> {
    int size();

    ReusableIterator<X> iterator();
}
