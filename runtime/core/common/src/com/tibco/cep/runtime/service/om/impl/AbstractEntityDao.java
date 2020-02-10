package com.tibco.cep.runtime.service.om.impl;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.om.VersionedGetV2;
import com.tibco.cep.runtime.service.om.api.*;
import com.tibco.cep.runtime.util.DBException;
import com.tibco.cep.util.RuleTriggerManager;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/*
* Author: Ashwin Jayaprakash / Date: Nov 30, 2010 / Time: 10:48:03 AM
*/

public abstract class AbstractEntityDao<E extends Entity, C extends Map<Long, E>>
        implements EntityDao<Long, E>, BEEntityCacheMBean {
    
    protected C cache = null;

    //----------------

    protected String cacheName;

    protected Class<E> entityClass;

    protected Cluster cluster;

    protected EntityDaoConfig daoConfig;

    protected Logger logger;

    //----------------

    protected AtomicLong numPuts = new AtomicLong();

    protected AtomicLong numGets = new AtomicLong();

    protected AtomicLong numRemoves = new AtomicLong();

    protected AtomicLong timePuts = new AtomicLong();

    protected AtomicLong timeGets = new AtomicLong();

    protected AtomicLong timeRemoves = new AtomicLong();

    //----------------

    protected int typeId = -1;

    protected long numHandlesInStore = 0L;

    protected AtomicBoolean started = new AtomicBoolean();

    protected AbstractEntityDao() {
    }

    public void init(Cluster cluster, Class<E> entityClass, String cacheName, EntityDaoConfig daoConfig) {
        this.cluster = cluster;
        this.logger = cluster.getRuleServiceProvider().getLogger(getClass());
        this.entityClass = entityClass;
        this.cacheName = cacheName;

        this.daoConfig = daoConfig;
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            StandardMBean standardMBean = new StandardMBean(this, BEEntityCacheMBean.class);
            ObjectName name = new ObjectName("com.tibco.be:service=Cache,name=" + cacheName);

            mbs.registerMBean(standardMBean, name);
        }
        catch (InstanceAlreadyExistsException e) {
            logger.log(Level.DEBUG, e, "Error occurred while registering MBean");
        }
        catch (Exception e) {
            logger.log(Level.WARN, e, "Error occurred while registering MBean");
        }
    }

    public final void start(
            boolean overwrite)
            throws Exception {

        if (started.get() && !overwrite) {
            return;
        }

        logger.log(Level.DEBUG, String.format("Starting [%s] for entity [%s] with cache name [%s]",
                getClass().getSimpleName(), entityClass.getName(), cacheName));

        //--------------------

        if (!overwrite) {
            registerMBean();
        }

        cache = startHook(overwrite);

        //--------------------

        started.set(true);

        logger.log(Level.INFO, String.format("Started [%s] for entity [%s] with cache name [%s]",
                getClass().getSimpleName(), entityClass.getName(), cacheName));
    }

    public void waitUntilReady(boolean recover) throws Exception {
        // Do nothing by default. Override for AS.
    }

    /**
     * Everything else is already initialized.
     *
     * @return Create and initialize the internal cache.
     */
    protected abstract C startHook(boolean overwrite);


    @Override
    public C getInternal() {
        return cache;
    }

    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public EntityDaoConfig getConfig() {
        return daoConfig;
    }

    @Override
    public void put(E e) throws Exception {
        long start = System.currentTimeMillis();

        cache.put(e.getId(), e);

        numPuts.incrementAndGet();
        timePuts.addAndGet(System.currentTimeMillis() - start);
    }

    @Override
    public void putAll(Map<Long, E> map) throws Exception {
        long start = System.currentTimeMillis();

        cache.putAll(map);

        numPuts.addAndGet(map.size());
        timePuts.addAndGet(System.currentTimeMillis() - start);
    }

    @Override
    public E getByPrimaryKey(Long k) {
        int retryCount = 0;

        while (true) {
            try {
                long start = System.currentTimeMillis();

                Object ret = cache.get(k);

                numGets.incrementAndGet();
                timeGets.addAndGet(System.currentTimeMillis() - start);

                return (E) ret;
            }
            catch (RuntimeException rte) {
                retryCount++;
                String message = rte.getMessage();

                logger.log(Level.WARN, "Cache:  Exception in get(%s): Retry num: %s : %s", k, retryCount, message);

                if (RuleTriggerManager.retryOnException() && message != null &&
                        ((message.indexOf(DBException.DatabaseException_MESSAGE) >= 0) ||
                        (message.indexOf(DBException.PersistersOffline_MESSAGE) >= 0) ||
                        (message.indexOf(DBException.OperationTimeout_MESSAGE) >= 0))) {

                    try {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e) {
                    }
                }
                else {
                    throw rte;
                }
            }
        }
    }

    @Override
    public Entity get(Long k) {
        return getByPrimaryKey(k);
    }

    @Override
    public final Result<E> getIfVersionGreater(Long k, int version) {
        long start = System.currentTimeMillis();

        Object invokeResult = null;
        try {
        	invokeResult = fetchLatestEntityOrComparisonResult(k, version);
        } catch (Exception e) {
            logger.log(Level.WARN, e, "Exception during fetch-latest %s : %s", k, e.getMessage());
        }

        ResultImpl result = handleVersionedGetResult(version, invokeResult);
        numGets.incrementAndGet();
        timeGets.addAndGet(System.currentTimeMillis() - start);

        return result;
    }

    /**
     * @param k
     * @param version
     * @return {@link Entity} or {@link ComparisonResult}
     */
    protected Object fetchLatestEntityOrComparisonResult(Long k, int version) throws Exception {
        Invocable.Result result = invokeWithKey(k, new VersionedGetV2(version));
        if (result != null) {
            return result.getResult();
        }
        return null;
    }

    protected ResultImpl handleVersionedGetResult(int requestedVersion, Object invokeResult) {
        if (invokeResult == null) {
            return new ResultImpl(0, true, null);
        }
        else if (invokeResult instanceof Entity) {
            Entity entity = (Entity) invokeResult;
            int v = (entity instanceof VersionedObject) ? ((VersionedObject) invokeResult).getVersion() : 0;

            return new ResultImpl(v, false, entity);
        }

        ComparisonResult comparisonResult = (ComparisonResult) invokeResult;
        switch (comparisonResult) {
            case SAME_VERSION:
                return new ResultImpl(requestedVersion, false, null);

            case NOT_VERSIONED:
                return new ResultImpl(requestedVersion, false, null);

            case VALUE_NOT_PRESENT:
            default:
                return new ResultImpl(0, true, null);
        }
    }

    @Override
    public void removeAll(Set<Long> keys) {
        long start = System.currentTimeMillis();
        long removed = keys.size();

        cache.keySet().removeAll(keys);

    	numRemoves.addAndGet(removed);
    	timeRemoves.addAndGet(System.currentTimeMillis() - start);
    }

    //---------------

    @Override
    public String getCacheName() {
        return cacheName;
    }

    @Override
    public String getClassName() {
        return entityClass.getName();
    }

    @Override
    public int getTypeId() {
        if (typeId < 0) {
            typeId = cluster.getMetadataCache().getTypeId(entityClass);
        }
        return typeId;
    }

    @Override
    public void updatePutStats(long putTime) {
        numPuts.incrementAndGet();
        timePuts.addAndGet(putTime);
    }

    @Override
    public long getPutCount() {
        return numPuts.get();
    }

    @Override
    public double getPutAvgTime() {
        if (numPuts.get() > 0) {
            return timePuts.get() / (numPuts.get() * 1.0);
        }
        else {
            return 0.0;
        }
    }

    @Override
    public void updateGetStats(long getTime) {
        numGets.incrementAndGet();
        timeGets.addAndGet(getTime);
    }

    @Override
    public long getGetCount() {
        return numGets.get();
    }

    @Override
    public double getGetAvgTime() {
        if (numGets.get() > 0) {
            return timeGets.get() / (numGets.get() * 1.0);
        }
        else {
            return 0.0;
        }
    }

    @Override
    public void updateRemoveStats(long removeTime) {
        numRemoves.incrementAndGet();
        timeRemoves.addAndGet(removeTime);
    }

    @Override
    public long getRemoveCount() {
        return numRemoves.get();
    }

    @Override
    public double getRemoveAvgTime() {
        if (timeRemoves.get() > 0) {
            return timeRemoves.get() / (numRemoves.get() * 1.0);
        }
        else {
            return 0.0;
        }
    }

    @Override
    public long getNumHandlesInStore() {
        return numHandlesInStore;
    }

    @Override
    public int getCacheSize() {
        return cache.size();
    }

    //---------------

    public static class ResultImpl implements Result {
        protected int version;

        protected boolean deleted;

        protected Entity entity;

        public ResultImpl(int version, boolean deleted, Entity entity) {
            this.version = version;
            this.deleted = deleted;
            this.entity = entity;
        }

        @Override
        public int getVersion() {
            return version;
        }

        @Override
        public boolean isDeleted() {
            return deleted;
        }

        @Override
        public Entity getResult() {
            return entity;
        }
    }
}
