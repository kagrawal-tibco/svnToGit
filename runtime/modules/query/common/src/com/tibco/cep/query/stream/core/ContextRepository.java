package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.monitor.ResourceId;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Author: Ashwin Jayaprakash Date: Oct 29, 2007 Time: 4:17:16 PM
 */

public class ContextRepository implements Component {
    protected final ResourceId resourceId;

    protected final DefaultGlobalContext globalContext;

    protected final ConcurrentHashMap<String, ConcurrentHashMap<String, DefaultQueryContext>> regionNamesAndQueryContexts;

    protected final ReentrantLock lock;

    public ContextRepository() {
        this.resourceId = new ResourceId(ContextRepository.class.getName());

        this.globalContext = new DefaultGlobalContext();

        this.regionNamesAndQueryContexts =
                new ConcurrentHashMap<String, ConcurrentHashMap<String, DefaultQueryContext>>();

        lock = new ReentrantLock();
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void init(Properties properties) throws Exception {
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void discard() throws Exception {
        lock.lock();
        try {
            for (ConcurrentHashMap<String, DefaultQueryContext> qcMap : regionNamesAndQueryContexts
                    .values()) {
                qcMap.clear();
            }
            regionNamesAndQueryContexts.clear();

            resourceId.discard();
        } finally {
           lock.unlock();
        }
    }

    // ----------

    public DefaultGlobalContext getGlobalContext() {
        return globalContext;
    }

    /**
     * Gets registered under "{@link com.tibco.cep.query.stream.context.DefaultQueryContext#getRegionName()
     * region-name} -> {@link com.tibco.cep.query.stream.context.DefaultQueryContext#getQueryName()
     * query-name}".
     *
     * @param context
     */
    public void registerContext(DefaultQueryContext context) {
        String regionName = context.getRegionName();
        String queryName = context.getQueryName();

        lock.lock();
        try {
            ConcurrentHashMap<String, DefaultQueryContext> qcMap = regionNamesAndQueryContexts.get(regionName);

            if (qcMap == null) {
                qcMap = new ConcurrentHashMap<String, DefaultQueryContext>();

                ConcurrentHashMap<String, DefaultQueryContext> tempMap =
                        regionNamesAndQueryContexts.putIfAbsent(regionName, qcMap);

                //Some other Thread beat us to the punch.
                if (tempMap != null) {
                    qcMap = tempMap;
                }
            }

            qcMap.put(queryName, context);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param regionName
     * @param queryName
     * @return
     */
    public DefaultQueryContext getQueryContext(String regionName, String queryName) {
        lock.lock();
        try {
            ConcurrentHashMap<String, DefaultQueryContext> qcMap = regionNamesAndQueryContexts.get(regionName);

            return qcMap.get(queryName);
        } finally {
          lock.unlock();
        }
    }

    /**
     * @param regionName
     * @param queryName
     */
    public void unregisterContext(String regionName, String queryName) {
        lock.lock();
        try {
            ConcurrentHashMap<String, DefaultQueryContext> qcMap = regionNamesAndQueryContexts.get(regionName);

            qcMap.remove(queryName);

            //Cleanup.
            if (qcMap.isEmpty()) {
                regionNamesAndQueryContexts.remove(regionName);
            }
        } finally {
          lock.unlock();
        }
    }
}
