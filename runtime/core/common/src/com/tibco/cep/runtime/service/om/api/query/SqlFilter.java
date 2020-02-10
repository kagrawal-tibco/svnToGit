package com.tibco.cep.runtime.service.om.api.query;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 1:49:15 PM
*/
public class SqlFilter implements Filter {
    protected String query;

    public SqlFilter() {
    }

    public SqlFilter(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public boolean evaluate(Object o, FilterContext context) {
        return true;
    }

    @Override
    public String toString() {
    	return query;
    }
}
