package com.tibco.cep.query.stream.impl.rete.integ.filter;

import java.lang.reflect.Constructor;

import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.expression.SimpleGlobalContext;
import com.tibco.cep.query.stream.impl.expression.SimpleQueryContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.CacheServiceProvider;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;

/*
* Author: Ashwin Jayaprakash / Date: Dec 5, 2010 / Time: 8:01:40 PM
*/
public class FilterHelper {
    public static Filter createFilter(Class entityClass, ReteEntityFilter[] reteFilters,
                                      SimpleGlobalContext simpleGlobalContext,
                                      SimpleQueryContext simpleQueryContext,
                                      SharedObjectSourceRepository sosRepo) {

        SharedObjectSource source = sosRepo.getSource(entityClass.getName());
        EntityDao entityDao = (EntityDao) source.getInternalSource();

        FilterBuilder fb = createFB(entityClass, reteFilters, simpleGlobalContext, simpleQueryContext, entityDao);
        return fb.build();
    }

    protected static FilterBuilder createFB(Class klass, ReteEntityFilter[] reteFilters,
                                            SimpleGlobalContext simpleGlobalContext,
                                            SimpleQueryContext simpleQueryContext, EntityDao entityDao) {
        FilterBuilder fb = null;

        Class<? extends FilterBuilder> fbClass = CacheServiceProvider.getFilterBuilderClass();
        try {
            Constructor ctor = fbClass.getConstructor(ReteEntityFilter[].class, Class.class, EntityDao.class,
                    SimpleGlobalContext.class, QueryContext.class);

            fb = (FilterBuilder) ctor
                    .newInstance(reteFilters, klass, entityDao, simpleGlobalContext, simpleQueryContext);
        }
        catch (Throwable throwable) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);

            logger.log(LogLevel.WARNING,
                    "Error occurred while creating [" + fbClass.getName() + "]." +
                            " Reverting to [" + FilterBuilder.class.getName() + "]", throwable);

            fb = new FilterBuilder(reteFilters, klass, entityDao, simpleGlobalContext, simpleQueryContext);
        }

        return fb;
    }
}
