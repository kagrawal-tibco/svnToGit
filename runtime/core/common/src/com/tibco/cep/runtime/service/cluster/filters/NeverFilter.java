package com.tibco.cep.runtime.service.cluster.filters;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

public class NeverFilter implements Filter {

    public boolean evaluate(Object o, FilterContext context) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
