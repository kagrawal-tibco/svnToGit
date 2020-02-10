package com.tibco.cep.query.stream.impl.expression;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.context.QueryContext;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 11:16:21 AM
*/
public class SimpleQueryContext implements QueryContext, Serializable {
    protected String regionName;

    protected String queryName;

    protected Map<String, Object> genericStore;

    /**
     * @param regionName
     * @param queryName
     * @param genericStore A copy of this map is made by this ctor.
     */
    public SimpleQueryContext(String regionName, String queryName,
                              Map<String, Object> genericStore) {
        this.regionName = regionName;
        this.queryName = queryName;

        this.genericStore = (genericStore == null) ? new HashMap<String, Object>() :
                new HashMap<String, Object>(genericStore);
    }

    public String getRegionName() {
        return regionName;
    }

    public String getQueryName() {
        return queryName;
    }

    public Map<String, Object> getGenericStore() {
        return genericStore;
    }
}
