package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 2:08:40 PM
*/

/**
 * This is purely to transport the real Coherence filter inside - merely to pass thru the BE APIs.
 */
public class CoherenceFilterContainer implements Filter {
    protected com.tangosol.util.Filter coherenceFilter;

    public CoherenceFilterContainer(com.tangosol.util.Filter coherenceFilter) {
        this.coherenceFilter = coherenceFilter;
    }

    public com.tangosol.util.Filter getCoherenceFilter() {
        return coherenceFilter;
    }

    /**
     * Use the actual filter {@link #getCoherenceFilter()}.
     *
     * @param o
     * @param context
     * @return
     */
    @Override
    public boolean evaluate(Object o, FilterContext context) {
        return coherenceFilter.evaluate(o);
    }
}
