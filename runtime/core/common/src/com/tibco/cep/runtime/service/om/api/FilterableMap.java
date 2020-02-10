package com.tibco.cep.runtime.service.om.api;

import java.util.Set;

/*
* Author: Ashwin Jayaprakash / Date: Oct 21, 2010 / Time: 4:00:12 PM
*/
public interface FilterableMap {
    Set entrySet(Filter filter, int limit);

    Set keySet(Filter filter, int limit);
}
