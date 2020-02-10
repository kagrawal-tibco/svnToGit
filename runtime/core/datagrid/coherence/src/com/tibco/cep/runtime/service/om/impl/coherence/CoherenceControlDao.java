/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.impl.coherence;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.om.impl.CoherenceFilteredInvocableWrapper;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoSeed;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CoherenceFilterTransformer;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CoherenceNonIndexedFilter;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Ashwin Jayaprakash Date: Apr 28, 2009 Time: 10:14:52 AM
*/
public class CoherenceControlDao<K, V> implements ControlDao<K, V> {
    
    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(CoherenceControlDao.class);
    
    protected NamedCache target;

    protected ControlDaoType type;

    protected String cacheName;

    protected Cluster cluster;

    protected ConcurrentHashMap<ChangeListener, MapListenerAdapter> listenerMap;

    public CoherenceControlDao(ControlDaoType type, DaoSeed daoSeed, Cluster cluster) {
        this.type = type;
        this.cacheName = daoSeed.getName();
        this.cluster = cluster;
        this.listenerMap = new ConcurrentHashMap<ChangeListener, MapListenerAdapter>();
    }

    public String getName() {
        return this.cacheName;
    }

    @Override
    public ControlDaoType getType() {
        return type;
    }

    @Override
    public void start() {
        RuleServiceProvider rsp = cluster.getRuleServiceProvider();
        target = CacheFactory.getCache(cacheName, (ClassLoader) rsp.getTypeManager());
    }

    public boolean lockAll(long timeoutMillis) {
        return target.lock(NamedCache.LOCK_ALL, timeoutMillis);
    }

    public boolean lock(Object key, long timeoutMillis) {
        return target.lock(key, timeoutMillis);
    }

    public boolean unlock(Object key) {
        return target.unlock(key);
    }

    public void unlockAll() {
        target.unlock(NamedCache.LOCK_ALL);
    }

    public void discard() {
        target.release();
        target = null;

        listenerMap.clear();
    }

    public void registerListener(ChangeListener changeListener) {
        MapListenerAdapter adapter = new MapListenerAdapter(changeListener);
        listenerMap.put(changeListener, adapter);

        target.addMapListener(adapter);
    }

    public void unregisterListener(ChangeListener changeListener) {
        MapListenerAdapter adapter = listenerMap.remove(changeListener);
        if (adapter == null) {
            return;
        }

        target.removeMapListener(adapter);
    }

    //--------------

    public int size() {
        return target.size();
    }

    public boolean isEmpty() {
        return target.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return target.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return target.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return (V) target.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return target.put(key, value);
    }
    
    @Override
    public V remove(Object key) {
        return (V) target.remove(key);
    }
    
    @Override
    public void putAll(Map t) {
        target.putAll(t);
    }

    @Override
    public void clear() {
        target.clear();
    }

    @Override
    public Set keySet() {
        return target.keySet();
    }

    @Override
    public Collection values() {
        return target.values();
    }

    @Override
    public Set entrySet() {
        return target.entrySet();
    }

    //--------------

    @Override
    public Collection getAll(Collection keys) {
        return target.getAll(keys).values();
    }

    @Override
    public void removeAll(Collection<K> ks) {
        switch (type) {
            case ObjectTableIds:
            case ObjectTableExtIds:
                target.keySet().removeAll(ks);
                return;

	        case WorkList$SchedulerId:
	            long msec = System.currentTimeMillis();
	            target.keySet().removeAll(ks);
	            if (logger.isEnabledFor(Level.TRACE)) {
	                logger.log(Level.TRACE, "%s removed %s items in %s msec.", type, ks.size(), (System.currentTimeMillis()-msec));
	            }
	            return;
	        
	        default:
        }

        target.keySet().removeAll(ks);
    }

    @Override
    public Set entrySet(com.tibco.cep.runtime.service.om.api.Filter filter, int limit) {
        com.tangosol.util.Filter tangosolFilter = CoherenceFilterTransformer.toTangosolFilter(filter);

        return target.entrySet(tangosolFilter);
    }

    @Override
    public Set keySet(com.tibco.cep.runtime.service.om.api.Filter filter, int limit) {
        com.tangosol.util.Filter tangosolFilter = CoherenceFilterTransformer.toTangosolFilter(filter);

        return target.keySet(tangosolFilter);
    }
    
    public NamedCache getNamedCache() {
        return target;
    }

    @Override
    public Map getInternal() {
        return target;
    }

    protected static class MapListenerAdapter implements MapListener {
        protected ChangeListener changeListener;

        public MapListenerAdapter(ChangeListener changeListener) {
            this.changeListener = changeListener;
        }

        public void entryInserted(MapEvent event) {
            changeListener.onPut(event.getKey(), event.getNewValue());
        }

        public void entryUpdated(MapEvent event) {
            changeListener.onUpdate(event.getKey(), event.getOldValue(), event.getNewValue());
        }

        public void entryDeleted(MapEvent event) {
            changeListener.onRemove(event.getKey(), event.getOldValue());
        }
    }

	@Override
	public Map<Object, Invocable.Result> invoke(Filter filter, Invocable invocable) {
		return target.invokeAll(new CoherenceNonIndexedFilter(filter), new CoherenceFilteredInvocableWrapper(invocable));
	}

	@Override
	public Invocable.Result invokeWithKey(Object key, Invocable invocable) {
		return (Invocable.Result) target.invoke(key, new CoherenceFilteredInvocableWrapper(invocable));
	}

	@Override
	public Map<Object, Invocable.Result> invoke(Set keys, Invocable invocable) {
		return target.invokeAll(keys, new CoherenceFilteredInvocableWrapper(invocable));
	}

}
