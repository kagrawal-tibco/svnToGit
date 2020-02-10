package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.concurrent.atomic.AtomicReference;

import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterBuilder;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/*
* Author: Ashwin Jayaprakash / Date: Oct 22, 2010 / Time: 3:01:01 PM
*/
public class CacheServiceProvider {
    public static final String COHERENCE_INDEXED_FILTER_BUILDER_CLASSNAME =
            "com.tibco.cep.query.stream.impl.rete.integ.filter.coherence.IndexAwareFilterBuilder";

    public static final String AS_INDEXED_FILTER_BUILDER_CLASSNAME =
            "com.tibco.cep.query.stream.impl.rete.integ.filter.as.FieldAwareFilterBuilder";

    protected static final AtomicReference<CacheProvider> providerRef = new AtomicReference<CacheProvider>();

    public static CacheProvider findCacheProvider() {
        CacheProvider cacheProvider = providerRef.get();
        if (cacheProvider != null) {
            return cacheProvider;
        }

        //---------------

        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();

        Cluster cluster = rsp.getCluster();
        DaoProvider daoProvider = cluster.getDaoProvider();

        String daoProviderClass = daoProvider.getClass().getName().toLowerCase();

        if (daoProviderClass.contains(CacheProvider.coherence.name())) {
            providerRef.set(CacheProvider.coherence);
        }
        else {
            providerRef.set(CacheProvider.internal);
        }

        cacheProvider = providerRef.get();

        Logger logger = Registry.getInstance().getComponent(Logger.class);
        logger.log(LogLevel.DEBUG, CacheProvider.class.getSimpleName() + " is [" + cacheProvider.name() + "]");

        return cacheProvider;
    }

    public static Class<? extends FilterBuilder> getFilterBuilderClass() {
        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        Cluster cluster = rsp.getCluster();

        CacheProvider cacheProvider = findCacheProvider();


        String className = null;
        if (cacheProvider == CacheProvider.coherence) {
            className = COHERENCE_INDEXED_FILTER_BUILDER_CLASSNAME;
        }
        else if (cacheProvider == CacheProvider.internal) {
            className = AS_INDEXED_FILTER_BUILDER_CLASSNAME;
        }

        if (className != null) {
            ClassLoader classLoader = cluster.getClass().getClassLoader();

            try {
                return (Class<? extends FilterBuilder>) Class.forName(className, true, classLoader);
            }
            catch (Throwable t) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);

                logger.log(LogLevel.WARNING,
                        "Error occurred while creating [" + className + "]." +
                                " Reverting to [" + FilterBuilder.class.getName() + "]", t);
            }
        }

        return FilterBuilder.class;
    }

    //-----------------

    public static enum CacheProvider {
        coherence,
        internal
    }
}
