package com.tibco.cep.query.stream.impl.rete.integ.filter;

import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.impl.expression.SimpleGlobalContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.query.SelectAlwaysFilter;
import com.tibco.cep.runtime.service.om.api.query.SelectAnyFilter;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 1:15:38 PM
*/

public class FilterBuilder {
    protected ReteEntityFilter[] reteFilters;

    protected Class entityClass;

    protected EntityDao entityDao;

    protected SimpleGlobalContext simpleGlobalContext;

    protected QueryContext queryContext;

    protected FilterStrategy filterStrategy;

    public FilterBuilder(ReteEntityFilter[] reteFilters, Class entityClass, EntityDao entityDao,
                         SimpleGlobalContext simpleGlobalContext, QueryContext queryContext) {
        this.reteFilters = reteFilters;
        this.entityClass = entityClass;
        this.entityDao = entityDao;
        this.simpleGlobalContext = simpleGlobalContext;
        this.queryContext = queryContext;
    }

    public final Filter build() {
        if (reteFilters == null || reteFilters.length == 0) {
            return new SelectAlwaysFilter();
        }

        return doBuild();
    }

    /**
     * @return
     * @see InterpretingFilter
     */
    protected Filter doBuild() {
        if (reteFilters.length == 1) {
            return new InterpretingFilter(reteFilters[0], simpleGlobalContext, queryContext);
        }

        //--------------

        Filter[] filters = new Filter[reteFilters.length];

        for (int i = 0; i < reteFilters.length; i++) {
            filters[i] = new InterpretingFilter(reteFilters[i], simpleGlobalContext, queryContext);
        }

        return new SelectAnyFilter(filters);
    }
}
