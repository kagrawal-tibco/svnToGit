package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import com.tangosol.net.NamedCache;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.expression.SimpleGlobalContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterBuilder;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CoherenceFilterContainer;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 1:57:36 PM
*/
public class IndexAwareFilterBuilder extends FilterBuilder {
    public IndexAwareFilterBuilder(ReteEntityFilter[] reteFilters, Class entityClass, EntityDao entityDao,
                                   SimpleGlobalContext simpleGlobalContext, QueryContext queryContext) {
        super(reteFilters, entityClass, entityDao, simpleGlobalContext, queryContext);
    }

    @Override
    protected Filter doBuild() {
        if (CoherenceFilterHelper.isFilterOptimizationEnabled() == false) {
            return super.doBuild();
        }

        //----------------

        NamedCache namedCache = (NamedCache) entityDao.getInternal();

        filterStrategy = new FilterStrategyImpl(entityClass, simpleGlobalContext, queryContext, namedCache);

        try {
            filterStrategy.resolveBindVariables(queryContext);
        }
        catch (Exception e) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);

            logger.log(LogLevel.WARNING,
                    "Error occurred while resolving bind variables in [" + getClass().getName() + "]." +
                            " Reverting to [" + FilterBuilder.class.getName() + "]", e);

            return super.doBuild();
        }

        com.tangosol.util.Filter coherenceFilter =
                CoherenceFilterHelper.convert(filterStrategy, simpleGlobalContext, queryContext, reteFilters);

        return new CoherenceFilterContainer(coherenceFilter);
    }
}
