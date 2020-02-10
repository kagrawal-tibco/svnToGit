package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.om.impl.CoherenceFilteredInvocableWrapper;
import com.tibco.cep.runtime.service.om.api.*;
import com.tibco.cep.runtime.service.om.impl.AbstractEntityDao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Mar 16, 2008 Time: 10:51:49 PM To change this template use File |
 * Settings | File Templates.
 */
public class BEEntityCache<E extends Entity> extends AbstractEntityDao<E, Map<Long, E>> implements Invoker {
    protected NamedCache namedCache;

    public BEEntityCache() {
    }

    @Override
    protected NamedCache startHook(
            boolean overwrite) {

        ClassLoader classLoader = (ClassLoader) cluster.getRuleServiceProvider().getTypeManager();
        namedCache = CacheFactory.getCache(cacheName, classLoader);
        CacheFactory.getService("TransactionalCache").setContextClassLoader(classLoader);

        DataCacheConfig dataCacheConfig = daoConfig.getDataCacheConfig();
        if (dataCacheConfig != null) {
            IndexConfig[] indexConfigs = dataCacheConfig.getIndexConfigs();
            if (indexConfigs != null) {
                for (IndexConfig indexConfig : indexConfigs) {
                    //todo We only support single field indexing.
                    String propertyToIndex = indexConfig.getFieldNames()[0];

                    ReflectionExtractor[] input = new ReflectionExtractor[]{
                    		new ReflectionExtractor("getProperty", new Object[]{propertyToIndex}),
                            new ReflectionExtractor("getValue")};

                    logger.log(Level.INFO, "Creating index for property=%s in cache=%s",
                            propertyToIndex, this.cacheName);

                    namedCache.addIndex(new ChainedExtractor(input), false, null);
                }
            }
        }

        return namedCache;
    }

    @Override
    public boolean lock(Long key, long timeoutMillis) {
        return namedCache.lock(key, timeoutMillis);
    }

    @Override
    public boolean unlock(Long key) {
        return namedCache.unlock(key);
    }

    @Override
    public Collection<E> getAll() {
        return namedCache.values();
    }

    @Override
    public Collection<E> getAll(Collection<Long> keys) {
        return namedCache.getAll(keys).values();
    }

    @Override
    public int clear(String filter) {
    	//TODO: If needed
    	return 0;
    }

    //---------------

    @Override
    public Set entrySet(Filter filter, int limit) {
        com.tangosol.util.Filter tangosolFilter = CoherenceFilterTransformer.toTangosolFilter(filter);

        return namedCache.entrySet(tangosolFilter);
    }

    @Override
    public Set keySet(Filter filter, int limit) {
        com.tangosol.util.Filter tangosolFilter = CoherenceFilterTransformer.toTangosolFilter(filter);

        return namedCache.keySet(tangosolFilter);
    }

	@Override
	public Map<Object, Invocable.Result> invoke(Filter filter, Invocable invocable) {
		return namedCache.invokeAll(new CoherenceNonIndexedFilter(filter), new CoherenceFilteredInvocableWrapper(invocable));
	}

	@Override
	public Invocable.Result invokeWithKey(Object key, Invocable invocable) {
		return (Invocable.Result) namedCache.invoke(key, new CoherenceFilteredInvocableWrapper(invocable));
	}

	@Override
	public Map<Object, Invocable.Result> invoke(Set keys, Invocable invocable) {
		return namedCache.invokeAll(keys, new CoherenceFilteredInvocableWrapper(invocable));
	}

}