package com.tibco.cep.runtime.management.impl.cluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 28, 2009 Time: 2:54:38 PM
*/

/**
 * This class has to be independent from all runtime BE dependencies. This class will also be
 * instantiated and used by non-BE clients.
 */
public class CoherenceMetricTable implements InternalMetricTable {
    /**
     * {@value}.
     */
    public static final String NAME_METRIC_DEF_CACHE_PREFIX = "tibco-be-internal-metric-def-cache$";

    /**
     * {@value}.
     */
    public static final String NAME_METRIC_DATA_CACHE_PREFIX =
            "tibco-be-internal-metric-data-cache$";

    protected static final FQName NAME_WILDCARD = new FQName("*");

    protected NamedCache metricDefCache;

    protected NamedCache metricDataCache;

    protected ReentrantLock adaptersLock;

    protected HashMap<FQName, CoherenceMLAdapter> adapters;

    public void init(String clusterName, String role) {
        ClassLoader contextCL = Thread.currentThread().getContextClassLoader();

        metricDefCache =
                CacheFactory.getCache(NAME_METRIC_DEF_CACHE_PREFIX + clusterName, contextCL);

        metricDataCache =
                CacheFactory.getCache(NAME_METRIC_DATA_CACHE_PREFIX + clusterName, contextCL);

        //------------

        adaptersLock = new ReentrantLock();
        adapters = new HashMap<FQName, CoherenceMLAdapter>();
    }

    public void discard() {
        for (CoherenceMLAdapter adapter : adapters.values()) {
            adapter.discard();
        }
        adapters.clear();
        adapters = null;

        metricDataCache = null;
    }

    //-------------

    public Collection<FQName> getMetricDefNames() {
        return metricDefCache.keySet();
    }

    public void addMetricDef(MetricDef metricDef) {
    	try{
    		metricDefCache.put(metricDef.getName(), metricDef);
        }catch(Throwable t){
        	if(t.getMessage().contains("No storage-enabled nodes")){
        		LogManagerFactory.getLogManager().getLogger(getClass())
                .log(Level.DEBUG, "Metric publication failed", t);
        	}
        	else{
        		throw new RuntimeException(t);
        	}
        }
    }

    public com.tibco.cep.runtime.management.MetricDef getMetricDef(FQName fqn) {
        return (com.tibco.cep.runtime.management.MetricDef) metricDefCache.get(fqn);
    }

    public com.tibco.cep.runtime.management.MetricDef removeMetricDef(FQName fqn) {
        return (com.tibco.cep.runtime.management.MetricDef) metricDefCache.remove(fqn);
    }

    //-------------

    public Collection<FQName> getMetricNames() {
        return metricDataCache.keySet();
    }

    public void addMetricData(FQName fqn, Data data) {
    	try{
    		metricDataCache.put(fqn, data);
        }catch(Throwable t){
        	if(t.getMessage().contains("No storage-enabled nodes")){
        		LogManagerFactory.getLogManager().getLogger(getClass())
                .log(Level.DEBUG, "Metric publication failed", t);
        	}
        	else{
        		throw new RuntimeException(t);
        	}
        }
    }

    public Data getMetricData(FQName fqn) {
        return (Data) metricDataCache.get(fqn);
    }

    public Data removeMetricData(FQName fqn) {
        return (Data) metricDataCache.remove(fqn);
    }

    //-------------

    public void registerListener(DataListener listener, FQName fqnToListenTo) {
        if (fqnToListenTo == null) {
            fqnToListenTo = NAME_WILDCARD;
        }

        adaptersLock.lock();
        try {
            CoherenceMLAdapter adapter = adapters.get(fqnToListenTo);

            if (adapter == null) {
                adapter = new CoherenceMLAdapter();

                //adapters.put(fqnToListenTo, adapter); --Nickx:move to after added as listener in case the register failed.

                if (fqnToListenTo == NAME_WILDCARD) {
                    metricDataCache.addMapListener(adapter);
                }
                else {
                    metricDataCache.addMapListener(adapter, fqnToListenTo, false /*Need values.*/);
                }
                
                adapters.put(fqnToListenTo, adapter);
            }

            adapter.addListener(listener);
        }
        finally {
            adaptersLock.unlock();
        }
    }

    public void unregisterListener(String listenerName, FQName fqnToListenTo) {
        if (fqnToListenTo == null) {
            fqnToListenTo = NAME_WILDCARD;
        }

        adaptersLock.lock();
        try {
            CoherenceMLAdapter adapter = adapters.get(fqnToListenTo);

            if (adapter != null) {
                int numListenersRemaining = adapter.removeListener(listenerName);

                if (numListenersRemaining == 0) {
                    adapters.remove(fqnToListenTo);
                    adapter.discard();

                    metricDataCache.removeMapListener(adapter, fqnToListenTo);
                    
                }
            }
        }
        finally {
            adaptersLock.unlock();
        }
    }

    //-------------

    protected static class CoherenceMLAdapter implements MapListener {
        protected HashMap<String, DataListener> listeners;

        protected volatile DataListener[] cachedListeners;

        public CoherenceMLAdapter() {
            listeners = new HashMap<String, DataListener>();
        }

        public void discard() {
            listeners.clear();
            listeners = null;

            cachedListeners = null;
        }

        public Collection<DataListener> getListeners() {
            return listeners.values();
        }

        /**
         * Uses {@link com.tibco.cep.runtime.management.MetricTable.DataListener#getName()}.
         *
         * @param listener
         */
        public void addListener(DataListener listener) {
            String name = listener.getName();

            if (listeners.containsKey(name)) {
                String allNames = listeners.keySet().toString();

                throw new RuntimeException(
                        "Another listener already exists with the same name: " + allNames);
            }

            listeners.put(name, listener);
            cachedListeners = recacheListeners();
        }

        private DataListener[] recacheListeners() {
            int size = listeners.size();
            return listeners.values().toArray(new DataListener[size]);
        }

        /**
         * @param listenerName
         * @return Current number of listeners.
         */
        public int removeListener(String listenerName) {
            DataListener listener = listeners.remove(listenerName);

            if (listener != null) {
                cachedListeners = recacheListeners();
            }

            return listeners.size();
        }

        //--------------

        public void entryInserted(MapEvent event) {
            notifyListeners(event.getKey(), event.getNewValue());
        }

        public void entryUpdated(MapEvent event) {
            notifyListeners(event.getKey(), event.getNewValue());
        }

        public void entryDeleted(MapEvent event) {
        }

        protected void notifyListeners(Object key, Object value) {
            DataListener[] listeners = cachedListeners;
            if (listeners == null) {
                return;
            }

            FQName fqname = (FQName) key;
            Data data = (Data) value;
            for (DataListener listener : listeners) {
                try {
                    listener.onNew(fqname, data);
                }
                catch (Exception e) {
                    //Ignore.
                }
            }
        }
    }
}
