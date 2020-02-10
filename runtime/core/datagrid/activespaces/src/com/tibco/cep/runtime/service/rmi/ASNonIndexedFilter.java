package com.tibco.cep.runtime.service.rmi;

import java.io.Serializable;

import com.tibco.cep.runtime.service.om.api.Filter;

/*
* Author: Ashwin Jayaprakash / Date: Dec 1, 2010 / Time: 2:42:28 PM
*/
public class ASNonIndexedFilter implements /*todo Where is ASFilter?,*/ Serializable {
    protected Filter beFilter;

    public ASNonIndexedFilter(Filter beFilter) {
        this.beFilter = beFilter;
    }

    public Filter getBeFilter() {
        return beFilter;
    }

    //todo Where is the AS API? @Override
    public boolean evaluate(Object o) {
        return beFilter.evaluate(o, null);
    }
}
