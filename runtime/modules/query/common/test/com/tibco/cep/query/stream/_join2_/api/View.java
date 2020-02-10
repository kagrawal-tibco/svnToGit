package com.tibco.cep.query.stream._join2_.api;

import com.tibco.cep.query.stream.util.CustomIterable;

/*
* Author: Ashwin Jayaprakash Date: May 27, 2009 Time: 8:59:08 PM
*/
public interface View<V> {
    CustomIterable<V> getAll();
}
