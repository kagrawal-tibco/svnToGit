package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import com.tangosol.util.filter.AllFilter;
import com.tangosol.util.filter.AlwaysFilter;
import com.tangosol.util.filter.AnyFilter;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.query.BasicFilterType;
import com.tibco.cep.runtime.service.om.api.query.SelectAllFilter;
import com.tibco.cep.runtime.service.om.api.query.SelectAnyFilter;

/*
* Author: Ashwin Jayaprakash / Date: 12/13/10 / Time: 12:01 PM
*/
public class CoherenceFilterTransformer {
    public static com.tangosol.util.Filter toTangosolFilter(Filter filter) {
        com.tangosol.util.Filter tangosolFilter = null;

        BasicFilterType filterType = BasicFilterType.identify(filter.getClass());

        if (filterType == null) {
            if (filter instanceof CoherenceFilterContainer) {
                CoherenceFilterContainer filterContainer = (CoherenceFilterContainer) filter;

                tangosolFilter = filterContainer.getCoherenceFilter();
            }
        }
        else {
            switch (filterType) {
                case always: {
                    tangosolFilter = new AlwaysFilter();
                    break;
                }

                case all: {
                    SelectAllFilter allFilter = (SelectAllFilter) filter;
                    Filter[] beParts = allFilter.getParts();

                    com.tangosol.util.Filter[] tangosolParts = toTangosolFilterArray(beParts);

                    tangosolFilter = new AllFilter(tangosolParts);
                    break;
                }

                case any: {
                    SelectAnyFilter anyFilter = (SelectAnyFilter) filter;
                    Filter[] beParts = anyFilter.getParts();

                    com.tangosol.util.Filter[] tangosolParts = toTangosolFilterArray(beParts);

                    tangosolFilter = new AnyFilter(tangosolParts);
                    break;
                }
            }
        }

        //Still null?
        if (tangosolFilter == null) {
            tangosolFilter = new CoherenceNonIndexedFilter(filter);
        }
        return tangosolFilter;
    }

    public static com.tangosol.util.Filter[] toTangosolFilterArray(Filter[] beParts) {
        com.tangosol.util.Filter[] tangosolParts = new com.tangosol.util.Filter[beParts.length];

        for (int i = 0; i < beParts.length; i++) {
            tangosolParts[i] = new CoherenceNonIndexedFilter(beParts[i]);
        }

        return tangosolParts;
    }
}
