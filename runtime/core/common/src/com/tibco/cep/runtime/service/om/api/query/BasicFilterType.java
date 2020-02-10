package com.tibco.cep.runtime.service.om.api.query;

import com.tibco.cep.runtime.service.om.api.Filter;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 4:15:53 PM
*/

public enum BasicFilterType {
    always(SelectAlwaysFilter.class),
    any(SelectAnyFilter.class),
    all(SelectAllFilter.class),
    sql(SqlFilter.class);

    protected Class<? extends Filter> filterClass;

    BasicFilterType(Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }

    public Class<? extends Filter> getFilterClass() {
        return filterClass;
    }

    /**
     * @param filterClass
     * @return null if this is not a basic type.
     */
    public static BasicFilterType identify(Class<? extends Filter> filterClass) {
        for (BasicFilterType filterType : values()) {
            if (filterType.getFilterClass().isAssignableFrom(filterClass)) {
                return filterType;
            }
        }

        return null;
    }

}
