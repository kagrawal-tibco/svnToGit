package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import java.io.Serializable;

import com.tangosol.util.Filter;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 2:44:13 PM
*/
public class CoherenceNonIndexedFilter implements Filter, Serializable {

	private static final long serialVersionUID = 8340306233054079998L;

	protected com.tibco.cep.runtime.service.om.api.Filter beFilter;

    public CoherenceNonIndexedFilter() {
    }

    public CoherenceNonIndexedFilter(com.tibco.cep.runtime.service.om.api.Filter beFilter) {
        this.beFilter = beFilter;
    }

    public com.tibco.cep.runtime.service.om.api.Filter getBeFilter() {
        return beFilter;
    }

    @Override
    public boolean evaluate(Object o) {
        return beFilter.evaluate(o, null);
    }
}
