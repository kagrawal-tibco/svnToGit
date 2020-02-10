package com.tibco.cep.runtime.service.om.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.om.api.CacheType;
import com.tibco.cep.runtime.service.om.api.CommonCacheType;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.DaoSeed;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.api.OtherCacheType;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;
import com.tibco.cep.runtime.service.om.impl.invm.DefaultLocalCache;
import com.tibco.cep.runtime.service.om.impl.invm.NoOpLocalCache;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.Helper;
import com.tibco.cep.util.annotation.Optional;

/*
 * Author: Ashwin Jayaprakash / Date: Nov 24, 2010 / Time: 5:29:36 PM
 */
public abstract class AbstractDaoProvider implements DaoProvider {

    public static final boolean descriptiveNames = Boolean.parseBoolean(System.getProperty(SystemProperty.CLUSTER_HAS_DESCRIPTIVE_NAMES.getPropertyName(), "true")); 
    
	protected final ReentrantLock createLock;

    protected Cluster cluster;

    protected ConcurrentHashMap<String, ControlDao> controlDaos;

    protected ConcurrentHashMap<String, EntityDao> entityDaos;

    protected ConcurrentHashMap<String, String> classNameToCacheMap;
    
    protected String cacheLoaderClassname = null;

    protected boolean backingStoreEnabled = false;

    protected boolean isCacheAside = true;

    protected GenericBackingStore backingStore = null;

    protected Logger logger;

    protected AbstractDaoProvider() {
        this.createLock = new ReentrantLock();
    }

    @Override
    public final void init(Cluster cluster) throws Exception {
        this.cluster = cluster;

        this.controlDaos = new ConcurrentHashMap<String, ControlDao>();
        this.entityDaos = new ConcurrentHashMap<String, EntityDao>();
        this.classNameToCacheMap = new ConcurrentHashMap<String, String>();

        ResourceProvider resourceProvider = cluster.getResourceProvider();
        this.logger = Helper.$logger(resourceProvider, getClass());

        logger.log(Level.INFO, "Initializing [" + getClass().getSimpleName() + "]");

        initHook();

        logger.log(Level.INFO, "Initialized [" + getClass().getSimpleName() + "]");
    }

    protected CacheManagerConfig getCacheManagerConfig() {
        Properties properties = cluster.getRuleServiceProvider().getProperties();
        ClusterConfig cc = (ClusterConfig) properties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());

        return cc.getObjectManagement().getCacheManager();
    }

    /**
     * All fields can be accessed. They are already initialized.
     *
     * @throws Exception
     */
    protected abstract void initHook() throws Exception;

    protected abstract <K, E extends Entity> EntityDao makeNew(Class<E> entityClass, EntityDaoConfig daoConfig,
                                                               DaoSeed daoSeed);

    protected abstract <K, V> ControlDao<K, V> makeNew(ControlDaoType daoType, DaoSeed daoSeed);

    protected DaoSeed createDaoSeed(CacheType cacheType, String clusterName, String cacheName) {
        return createDaoSeed(cacheType, clusterName, null, cacheName);
    }

    protected DaoSeed createDaoSeed(CacheType cacheType, String clusterName, @Optional String agentName,
                                    String cacheName, Object... props) {
        String s = cacheType.getAlias()
                + "$" + clusterName
                + "$" + (agentName == null ? "" : agentName)
                + "$" + cacheName;
        String entityCacheSizeStr = System.getProperty(SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(), "10000");
        entityCacheSizeStr = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(entityCacheSizeStr).toString();
        return new DaoSeed(cacheType, s, Integer.parseInt(entityCacheSizeStr), props);
    }

    public DaoSeed createControlDaoSeed(ClusterConfiguration cc, ControlDaoType daoType, Object... additionalProps) {
        String clusterName = cc.getClusterName();
        if(GvCommonUtils.isGlobalVar(clusterName)){
        	GlobalVariableDescriptor gv=cluster.getRuleServiceProvider().getGlobalVariables().getVariable(GvCommonUtils.stripGvMarkers(clusterName));
        	clusterName=gv.getValueAsString();
        	
        }

        switch (daoType) {
            case AgentTxn$AgentId: {
                String agentName = (String) additionalProps[0];
                String agentId = (String) additionalProps[1];
                String cacheName = daoType.getAlias() + agentId;

                return createDaoSeed(OtherCacheType.DISTRIBUTED_CACHE_AGENTTXNLOG, clusterName,
                        agentName, cacheName);
            }

            case DeletedEntities: {
                return createDaoSeed(OtherCacheType.DISTRIBUTED_CACHE_DELETEDENTITIES, clusterName,
                        daoType.getAlias());
            }

            case Master:
            case MasterId:
            case AgentTable:
            case DeletedExternalEntities: {
                return createDaoSeed(OtherCacheType.REPLICATED_CACHE_UNLIMITED, clusterName, daoType.getAlias());
            }

            case EventQueue$EventClass: {
                String eventClassName = (String) additionalProps[0];
                if (eventClassName.startsWith("com.tibco")) {
                    int lastIndexOf = eventClassName.lastIndexOf('.');
                    eventClassName = eventClassName.substring(lastIndexOf + 1);
                }
                String cacheName = eventClassName + daoType.getAlias();

                return createDaoSeed(CommonCacheType.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE,
                        clusterName, null, cacheName, additionalProps);
            }

            case ExtIdTable: {
                return createDaoSeed(OtherCacheType.DISTRIBUTED_CACHE_LIMITED_NOOBJECTTABLE,
                        clusterName, daoType.getAlias());
            }

            case LockManager: {
                return createDaoSeed(OtherCacheType.DISTRIBUTED_CACHE_LOCKMANAGER, clusterName,
                        daoType.getAlias());
            }

            case ObjectTableExtIds:
            case ObjectTableIds: {
                OtherCacheType cacheType = OtherCacheType.DISTRIBUTED_CACHE_OBJECTTABLE_NOBACKINGSTORE;
                if (cc.isHasBackingStore()) {
                    if (cc.isCacheAside()) {
                        cacheType = OtherCacheType.DISTRIBUTED_CACHE_OBJECTTABLE_READONLY_WITHBACKINGSTORE;
                    }
                    else {
                        cacheType = OtherCacheType.DISTRIBUTED_CACHE_OBJECTTABLE_WITHBACKINGSTORE;
                    }
                }
                DaoSeed daoSeed = createDaoSeed(cacheType, clusterName, daoType.getAlias());
                if (cc.isCacheLimited()) {
                	String objectTableCacheSize = System.getProperty(SystemProperty.OBJECT_TABLE_BACK_SIZE_LIMIT.getPropertyName(), "100000");
                	objectTableCacheSize = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(objectTableCacheSize).toString();
                    daoSeed.setLimitedCacheCapacity(Long.parseLong(objectTableCacheSize));
                }
                else {
                    daoSeed.setLimitedCacheCapacity(Long.MAX_VALUE);
                }
                return daoSeed;
            }

            case WorkList$SchedulerId: {
                OtherCacheType cacheType = OtherCacheType.REPLICATED_CACHE_UNLIMITED;
                if (cc.isHasBackingStore()) {
                    if (cc.isCacheAside()) {
                        cacheType = OtherCacheType.DISTRIBUTED_CACHE_SCHEDULER_READONLY_WITHBACKINGSTORE;
                    }
                    else {
                        cacheType = OtherCacheType.DISTRIBUTED_CACHE_SCHEDULER_WITHBACKINGSTORE;
                    }
                }

                String schedulerIdOrAgentName = (String) additionalProps[0];

                return createDaoSeed(cacheType, clusterName, schedulerIdOrAgentName, daoType.getAlias());
            }

            case ClusterLocks: {
                return createDaoSeed(CommonCacheType.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE, clusterName, daoType.getAlias());
            }

            case BPMN$MergeGatewayTable:
            case BPMN$LoopCounterTable:
            case BPMN$ProcessTemplateTable:
            case PublisherQueue: {
                CommonCacheType cacheType = CommonCacheType.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE;
                if (cc.isHasBackingStore()) {
                    if (cc.isCacheAside()) {
                        cacheType = CommonCacheType.DISTRIBUTED_CACHE_LIMITED_READONLY_WITHBACKINGSTORE;
                    }
                    else {
                        cacheType = CommonCacheType.DISTRIBUTED_CACHE_LIMITED_WITHBACKINGSTORE;
                    }
                }
                DaoSeed daoSeed = createDaoSeed(cacheType, clusterName, daoType.getAlias());
                daoSeed.setLimitedCacheCapacity(Long.parseLong(
                        System.getProperty(SystemProperty.OBJECT_TABLE_BACK_SIZE_LIMIT.getPropertyName(), "100000")));
                return daoSeed;

            }

            case InvocationService:
            case BackingStoreTasks:
            case CacheSequence:
            case CacheSequenceManager:
            case Classes:
            case ExternalClasses:
            case ExternalClassesLock:
            case HotDeployer:
            case LoadBalancer:
            case LoadTable:
            case TotalsTable:
            case RecoveryTable:
            case MetadataRegistry:
            case Topics:
            case TypeIds:
            case WorkManager: {
                OtherCacheType cacheType = OtherCacheType.REPLICATED_CACHE_UNLIMITED;
                return createDaoSeed(cacheType, clusterName, daoType.getAlias());
            }

            default:
                throw new IllegalArgumentException(
                        "The " + daoType.getClass().getSimpleName() + "[" + daoType.name() + "] is not recognized.");
        }
    }


//    protected DaoSeed constructEntityDaoSeed(String entityClassFqn) {
//        ClusterConfiguration cc = cluster.getClusterConfig();
//        String clusterName = cc.getClusterName();
//        CommonCacheType commonCacheType = CommonCacheType.getCommonCacheType(cc);
//
//        return createDaoSeed(commonCacheType, clusterName, entityClassFqn);
//    }

    protected DaoSeed constructEntityDaoSeed(Class entityClz) {
        ClusterConfiguration cc = cluster.getClusterConfig();
        String clusterName = cc.getClusterName();
        EntityDaoConfig entityDaoConfig = cluster.getMetadataCache().getEntityDaoConfig(entityClz);
        CommonCacheType commonCacheType = CommonCacheType.getCommonCacheType(cc, entityDaoConfig);
        return createDaoSeed(commonCacheType, clusterName, entityClz.getName());
    }

    @Override
    public <K, E extends Entity> EntityDao<K, E> createEntityDao(Class<E> entityClass, EntityDaoConfig daoConfig) {
        return this.createEntityDao(entityClass, daoConfig, false);
    }

    @Override
    public <K, E extends Entity> EntityDao<K, E> createEntityDao(
            Class<E> entityClass,
            EntityDaoConfig daoConfig,
            boolean overwrite) {

        DaoSeed daoSeed = constructEntityDaoSeed(entityClass);

        EntityDao entityDao;
        if (!overwrite) {
            entityDao = entityDaos.get(daoSeed.getName());
            if (entityDao != null) {
                return entityDao;
            }
        }

        //------------------

        entityDao = internalCreate(entityClass, daoConfig, daoSeed);

        return entityDao;
    }

    protected final <K, E extends Entity> EntityDao<K, E> internalCreate(Class<E> entityClass,
                                                                         EntityDaoConfig daoConfig, DaoSeed daoSeed) {
        createLock.lock();
        try {
            EntityDao entityDao = entityDaos.get(daoSeed.getName());
            if (entityDao != null) {
                return entityDao;
            }

            //------------------

            entityDao = makeNew(entityClass, daoConfig, daoSeed);

            EntityDao existingEntityDao = entityDaos.putIfAbsent(daoSeed.getName(), entityDao);
            if (existingEntityDao == null) {
                logger.log(Level.FINE,
                        "Created [" + EntityDao.class.getSimpleName() + "] instance " +
                                "for [" + entityClass.getName() + "] with name [" + daoSeed.getName() + "]");
            }
            else {
                entityDao = existingEntityDao;
            }

            return entityDao;
        }
        finally {
            createLock.unlock();
        }
    }

    @Override
    public <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass,
                                                    ControlDaoType daoType, Object... additionalProps) {
        ClusterConfiguration cc = cluster.getClusterConfig();

        DaoSeed daoSeed = createControlDaoSeed(cc, daoType, additionalProps);

        ControlDao<K, V> controlDao = controlDaos.get(daoSeed.getName());
        if (controlDao != null) {
            return controlDao;
        }

        //------------------

        controlDao = internalCreate(daoType, daoSeed);

        return controlDao;
    }

    protected final <K, V> ControlDao<K, V> internalCreate(ControlDaoType daoType, DaoSeed daoSeed) {
        createLock.lock();
        try {
            ControlDao<K, V> controlDao = controlDaos.get(daoSeed.getName());
            if (controlDao != null) {
                return controlDao;
            }

            //------------------

            controlDao = makeNew(daoType, daoSeed);
            controlDao.start();

            controlDaos.putIfAbsent(daoSeed.getName(), controlDao);

            logger.log(Level.FINE,
                    "Created [" + ControlDao.class.getSimpleName() + "] instance " +
                            "for [" + daoType.name() + "] with name [" + daoSeed.getName() + "]");

            return controlDao;
        }
        finally {
            createLock.unlock();
        }
    }

    @Override
    public <K, E extends Entity> EntityDao<K, E> getEntityDao(Class<E> entityClass) {
        String cacheName = classNameToCacheMap.get(entityClass.getName());
        if (cacheName == null) {
            DaoSeed daoSeed = constructEntityDaoSeed(entityClass);
            cacheName = daoSeed.getName();
            classNameToCacheMap.put(entityClass.getName(), cacheName);
        }
        return entityDaos.get(cacheName);
    }

    @Override
    public <K, E extends Entity> EntityDao<K, E> getEntityDao(String cacheName) {
        return entityDaos.get(cacheName);
    }

    @Override
    public <K, V> ControlDao<K, V> getControlDao(String daoName) {
        return controlDaos.get(daoName);
    }

    /**
     * @return
     * @throws RuntimeException
     */
    @Override
    public LocalCache newLocalCache() {
        LocalCache cache = ManagedObjectManager.isOn() ? new NoOpLocalCache() : new DefaultLocalCache();

        Properties properties = cluster.getRuleServiceProvider().getProperties();
        Configuration configuration = new Configuration("$temp", properties);
        try {
            cache.init(configuration);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cache;
    }

    @Override
    public Collection<? extends EntityDao> getAllEntityDao() {
        return entityDaos.values();
    }

    @Override
    public Collection<? extends ControlDao> getAllControlDao() {
        return controlDaos.values();
    }

    @Override
    public void stop() {

    }
}
