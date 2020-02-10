package com.tibco.cep.runtime.service.cluster.filters;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

public class MapEventFilter implements Filter {

    Filter delegateFilter;

    public MapEventFilter(Filter delegateFilter) {
        this.delegateFilter = delegateFilter;
    }

    public boolean evaluate(Object o, FilterContext context) {
        return delegateFilter.evaluate(o, context);
    }

    public Filter getDelegateFilter() {
        return this.delegateFilter;
    }
}
