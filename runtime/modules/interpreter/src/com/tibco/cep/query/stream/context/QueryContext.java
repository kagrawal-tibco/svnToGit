package com.tibco.cep.query.stream.context;

import java.util.Map;

/*
* Author: Ashwin Jayaprakash Date: Jul 24, 2008 Time: 2:51:23 PM
*/
public interface QueryContext {
    String getRegionName();

    String getQueryName();

    Map<String, Object> getGenericStore();
}
