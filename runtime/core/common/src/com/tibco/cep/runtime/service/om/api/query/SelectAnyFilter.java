package com.tibco.cep.runtime.service.om.api.query;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 1:49:15 PM
*/
public class SelectAnyFilter implements Filter {
    protected Filter[] parts;

    public SelectAnyFilter() {
    }

    public SelectAnyFilter(Filter[] parts) {
        this.parts = parts;
    }

    public Filter[] getParts() {
        return parts;
    }

    @Override
    public boolean evaluate(Object o, FilterContext context) {
        for (Filter part : parts) {
            boolean result = part.evaluate(o, context);

            if (result == true) {
                return true;
            }
        }

        return false;
    }
}
