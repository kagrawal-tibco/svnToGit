package com.tibco.cep.runtime.service.om.api.query;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 11:45:43 AM
*/
public class SelectAllFilter implements Filter {
    protected Filter[] parts;

    public SelectAllFilter() {
    }

    public SelectAllFilter(Filter[] parts) {
        this.parts = parts;
    }

    public Filter[] getParts() {
        return parts;
    }

    @Override
    public boolean evaluate(Object o, FilterContext context) {
        for (Filter part : parts) {
            boolean result = part.evaluate(o, context);

            if (result == false) {
                return false;
            }
        }

        return true;
    }
}
