package com.tibco.cep.runtime.service.om.api.query;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 1:49:32 PM
*/

public class SelectAlwaysFilter implements Filter {
    public SelectAlwaysFilter() {
    }

    /**
     * @param o
     * @param context
     * @return Always returns true.
     */
    @Override
    public final boolean evaluate(Object o, FilterContext context) {
        return true;
    }
}
