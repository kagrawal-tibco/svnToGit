/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.dao.impl.tibas;

import com.tibco.as.space.*;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.SpaceDef.CachePolicy;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.listener.ExpireListener;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.as.space.listener.ListenerDef.DistributionScope;
import com.tibco.as.space.listener.ListenerDef.TimeScope;
import com.tibco.as.space.listener.PutListener;
import com.tibco.as.space.listener.TakeListener;
import com.tibco.as.space.persistence.Persister;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapCreator;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.util.TypeHelper;
import com.tibco.cep.runtime.service.om.api.*;
import com.tibco.cep.runtime.service.rmi.ASFilteredInvocableWrapper;
import com.tibco.cep.runtime.service.rmi.RemoteFilterRunner;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
* Author: Ashwin Jayaprakash / Date: Nov 24, 2010 / Time: 2:23:46 PM
*/
public class ASControlDao<K, V> implements ControlDao<K, V> {

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(ASControlDao.class);

    public static final String CTX_PROP_IS_CONTROL_DAO = "isControlDao";

    public static final String CTX_PROP_CONTROL_DAO_TYPE = "controlDaoType";

    public static final String CTX_PROP_DAO_SEED = "daoSeed";

    public static final String CTX_PROP_WHO = "who";

    protected SpaceMap<Object, Object> target;

    protected ControlDaoType type;

    protected String cacheName;

    protected DaoSeed daoSeed;

    protected Metaspace metaspace;

    protected TupleCodec tupleCodec;

    protected Cluster cluster;

    protected ConcurrentHashMap<ChangeListener, MapListenerAdapter> listenerMap;

    protected boolean hasStarted = false;

    protected boolean recoveryEnabled = false;

    protected KeyValueTupleAdaptor<K, V> tupleAdaptor;

    private static String LOCK_ALL = "-1";

    //the daoProvider argument is only to discourage creating a new Dao outside of ASDaoProvider.makeNew.
    //this is so that it makes sense to call cluster.getDaoProvider() in start()
    protected ASControlDao(ControlDaoType type, DaoSeed daoSeed, Metaspace metaspace,
            TupleCodec tupleCodec, Cluster cluster, ASDaoProvider daoProvider)
    {
    	if (daoProvider == null || cluster.getDaoProvider() != daoProvider) {
    	    throw new IllegalArgumentException("Tried to create ASControlDao without proper ASDaoProvider");
    	}
        this.type = type;
        this.cacheName = daoSeed.getName();
        this.daoSeed = daoSeed;
        this.metaspace = metaspace;
        this.tupleCodec = tupleCodec;
        this.cluster = cluster;

        this.listenerMap = new ConcurrentHashMap<ChangeListener, MapListenerAdapter>();
    }


    public static <K, V> ASControlDao<K, V> newASControlDao(ASDaoProvider daoProvider, ControlDaoType type, DaoSeed daoSeed,
            Metaspace metaspace, TupleCodec tupleCodec, Cluster cluster)
    {
    	return new ASControlDao<K, V>(type, daoSeed, metaspace, tupleCodec, cluster, daoProvider);
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public ControlDaoType getType() {
        return type;
    }

    @Override
    public void start() {

        Persister persister = null;

        if (hasStarted == true) {
            logger.log(Level.DEBUG, cacheName + " has already started.");
            return;
        }

        try {
            SpaceMapCreator.Parameters parameters = null;

            RuleServiceProvider RSP = cluster.getRuleServiceProvider();
            BEProperties properties = (BEProperties) RSP.getProperties();
            boolean usesSharedNothing = ((ASDaoProvider) cluster.getDaoProvider()).isSharedNothing();
            boolean usesSharedAllWithBDB = ((ASDaoProvider) cluster.getDaoProvider()).isSharedAllWithBDB();

            if (usesSharedAllWithBDB) {
                // BDB persistence does not yet work with non-blob keys
                parameters = new SpaceMapCreator.Parameters<Object, Object>().setKeyClass(Object.class);
            } else {
                // For others, minimize key size using non-blob 
                if (type == ControlDaoType.ObjectTableIds) {
                    parameters = new SpaceMapCreator.Parameters<Long, Object>().setKeyClass(Long.class);
                } else if (type == ControlDaoType.ObjectTableExtIds) {
                    parameters = new SpaceMapCreator.Parameters<String, Object>().setKeyClass(String.class);
                } else {
                    parameters = new SpaceMapCreator.Parameters<Object, Object>().setKeyClass(Object.class);
                }
            }
            parameters.setSpaceName(cacheName)
                      .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                      .setValueClass(Object.class)
                      .setTupleCodec(tupleCodec);

            int minSeeders = 1; //TODO: Decide between; 1 OR cluster.getClusterConfig().getMinSeeders();
            parameters.setMinSeeders(minSeeders);

            String seeder = System.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
            boolean storageEnabled = Boolean.parseBoolean(seeder);
            String forced = System.getProperty(SystemProperty.CLUSTER_NODE_ISFORCED.getPropertyName(), "false");
            boolean forcedSeeder = Boolean.parseBoolean(forced);
            boolean remoteClient = this.metaspace.getSelfMember().isRemote();
            
            // Inference engines (without storage) should not join Scheduler spaces as Seeders
            // TODO: SharedNothing and BDB persistence creates Scheduler spaces as 'replicated' - why?
            if (storageEnabled || 
               (daoSeed.getCacheType().isReplicated() && isSeederType(type, forcedSeeder) && !remoteClient)) {
                parameters.setRole(DistributionRole.SEEDER);
                recoveryEnabled = true;
            }
            else {
                parameters.setRole(DistributionRole.LEECH);
                recoveryEnabled = false;
            }

            if (usesSharedNothing) {
                switch (type) {
                	case ObjectTableIds:
                	case ObjectTableExtIds:
                    	// if objecttable is false do not store the extid and id
                        if (cluster.getClusterConfig().useObjectTable() == false) {
                            break;
                        }
                    case MasterId:
                    case TypeIds:
                        parameters.setPersistenceType(SpaceDef.PersistenceType.SHARE_NOTHING);
                        setPersistencePolicy(parameters);
                        break;
					case WorkList$SchedulerId:
						parameters.setPersistenceType(SpaceDef.PersistenceType.SHARE_NOTHING);
						// BE-26476: Provides a option to change persistence policy.
						// With async, removed entries may get fetched in subsequent query as
						// entries are not physically deleted yet, this property will helps change
						// the policy to sync.
						String persistencePolicy = System.getProperty(
								SystemProperty.CLUSTER_DATAGRID_SCHEDULER_PERSISTENCE_POLICY.getPropertyName());
						if (persistencePolicy != null && !persistencePolicy.isEmpty()) {
							setPersistencePolicy(parameters, persistencePolicy);
						} else {
							setPersistencePolicy(parameters);
						}
						break;
                }
            }

            if (daoSeed.getCacheType().isReplicated()) {
                parameters.setReplicationCount(SpaceDef.REPLICATE_ALL);
            }
            else {
            	String backupCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
            	backupCountStr = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(backupCountStr).toString();
                parameters.setReplicationCount(Integer.parseInt(backupCountStr));
            }

            if (daoSeed.getCacheType().isCacheLimited()) {
                parameters.setLimitedCacheCapacity(daoSeed.getLimitedCacheCapacity());
            }

            if (daoSeed.getCacheType().hasBackingStore()) {
            	String persistencePolicy = setPersistencePolicy(parameters);
                
                if (usesSharedAllWithBDB == false) {
                    logger.log(Level.INFO, "Backing store persistence policy is %s for %s", persistencePolicy, this.cacheName);
                }
                
                GenericBackingStore genericBackingStore = cluster.getDaoProvider().getBackingStore();
                if (genericBackingStore != null && (genericBackingStore instanceof BackingStore)) {
                    Class cacheLoaderClass = genericBackingStore.getClass();
                    if (cacheLoaderClass != null) {
                        parameters.setPersistenceType(SpaceDef.PersistenceType.SHARE_ALL);
                        Boolean isDistributedPersister = Boolean.parseBoolean(properties.getProperty(SystemProperty. 
                            BACKING_STORE_PERSISTER_DISTRIBUTED.getPropertyName(), "true").trim());
                        String persisterDistributionType = isDistributedPersister ? "distributed" : "non-distributed";
                        parameters.setPersistenceDistributionPolicy(isDistributedPersister ? DistributionPolicy.DISTRIBUTED : DistributionPolicy.NON_DISTRIBUTED);
                        parameters.setForgetOldValue(true);
                        if (daoSeed.getCacheType().isCacheAside() == true) {
                            parameters.setCachePolicy(CachePolicy.READ_THROUGH);
                        }
                        try {
                            if (this.getType().equals(ControlDaoType.WorkList$SchedulerId)) {
                                // Only seeder should have persister installed
                                if (storageEnabled) {
                                    Constructor cons = cacheLoaderClass.getConstructor(
                                            new Class[]{String.class, String.class, Integer.class, getClass()});
                                    parameters.setPersister((Persister) cons.newInstance(
                                            new Object[]{cacheName, cacheName, daoSeed.getCacheType().isCacheAside() ? 1 : 0, this}));
                                }
                            }
                            else {
                                // Only seeder should have persister installed
                                if (storageEnabled) {
                                    Constructor cons = cacheLoaderClass.getConstructor(
                                            new Class[]{String.class, Integer.class, getClass()});
                                    parameters.setPersister((Persister) cons.newInstance(
                                            new Object[]{cacheName, daoSeed.getCacheType().isCacheAside() ? 1 : 0, this}));
                                }
                            }
                            if (storageEnabled) {
                                logger.log(Level.DEBUG,
                                        "Creating Control DAO backing store persister(%s) for %s(cache-aside=%s class=%s)",
                                        new Object[]{persisterDistributionType, parameters.getSpaceName(), daoSeed.getCacheType().isCacheAside(),
                                                cacheLoaderClass.getName()});
                            }
                            else {
                                logger.log(Level.DEBUG,
                                        "Not creating Control DAO backing store persister for %s(cache-aside=%s class=%s)",
                                        new Object[]{parameters.getSpaceName(), daoSeed.getCacheType().isCacheAside(),
                                                cacheLoaderClass.getName()});
                            }
                        }
                        catch (Exception ex) {
                            logger.log(Level.WARN,
                                    "Creating Control DAO backing store persister failed for " + parameters.getSpaceName() +
                                            "(class=" + cacheLoaderClass.getName() + ")", ex);
                        }
                    }
                }
            }

            if (getType() == ControlDaoType.EventQueue$EventClass) {
                Object[] additionalProps = daoSeed.getAdditionalProps();
                String eventCacheName = null;
                if (additionalProps[0] instanceof String) {
                    eventCacheName = (String) additionalProps[0];
                } else if (additionalProps[0] instanceof Integer) {
                    eventCacheName = String.valueOf(additionalProps[0]);
                }

                Class clazz = TypeHelper.$mapNameToClass(cluster, eventCacheName);

                if (clazz != null) {
                    long ttl = TypeHelper.$extractOtherTTL(cluster, clazz);
                    if (ttl > 0) {
                        parameters.setTtl(ttl);
                    }
                }
            }

            // No need to check for storageEnabled here because node can be a leech
            // If leech it should create space parameters in the same way but not set a persister
            if (usesSharedAllWithBDB) {
                switch (type) {
                    case ObjectTableExtIds:
                    case MasterId:
                    case ObjectTableIds:
                    case WorkList$SchedulerId:
                        parameters.setPersistenceType(SpaceDef.PersistenceType.SHARE_ALL);
                        // Need SYNC, because AS loses underlying persistence errors if ASYNC
                        parameters.setPersistencePolicy(SpaceDef.PersistencePolicy.SYNC);
                        parameters.setPersistenceDistributionPolicy(DistributionPolicy.NON_DISTRIBUTED);
                        parameters.setLimitedCacheCapacity(Long.MAX_VALUE);
                        break;
                    default:
                        break;
                }
            }

            logger.log(Level.DEBUG, "Control DAO %s role=%s policy=%s capacity=%s size=%s replication=%s sync=%s cachePolicy=%s ignoreOldValue=%s minSeeder=%s",
                    cacheName, parameters.getRole().name(), parameters.getDistributionPolicy(),
                    (daoSeed.getCacheType().isCacheLimited() ? "limited" : "unlimited"),
                    parameters.getLimitedCacheCapacity(), parameters.getReplicationCount(),
                    parameters.isSyncReplication(), parameters.getCachePolicy(), parameters.isForgetOldValue(), parameters.getMinSeeders());

            Tuple contextTuple = Tuple.create();
            contextTuple.put(CTX_PROP_IS_CONTROL_DAO, true);
            contextTuple.put(CTX_PROP_WHO, metaspace.getSelfMember().toString());
            tupleCodec.putInTuple(contextTuple, CTX_PROP_CONTROL_DAO_TYPE, DataType.SerializedBlob, type);
            tupleCodec.putInTuple(contextTuple, CTX_PROP_DAO_SEED, DataType.SerializedBlob, daoSeed);
            parameters.setContextTuple(contextTuple);

            target = SpaceMapCreator.create(metaspace, parameters);

            tupleAdaptor = (KeyValueTupleAdaptor<K, V>) target.getTupleAdaptor();

            if (usesSharedAllWithBDB && storageEnabled) {

                ASPersistenceProvider asPersistenceProvider =
                        ((ASDaoProvider) cluster.getDaoProvider()).getBDBPersistenceProvider();
                if (asPersistenceProvider != null) {
                    switch (type) {
                        case ObjectTableExtIds:
                        case MasterId:
                        case WorkList$SchedulerId:
                        case ObjectTableIds:
                            // If the persistence implementation class is provided in the property
                            // then the persistence needs to be enabled on the AS space
                            persister = asPersistenceProvider.createControlPersister(tupleAdaptor,
                                        type, target.getSpace().getName());
                            break;

                        default:
                            break;
                    }

                    if (persister != null) {
                        target.getSpace().setPersister(persister);
                    }
                }
            }

            hasStarted = true;

            waitUntilReady(true);
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }
    
	private String setPersistencePolicy(SpaceMapCreator.Parameters parameters) {
		String persistencePolicy = ((ASDaoProvider) cluster.getDaoProvider()).getPersistencePolicy();
		return setPersistencePolicy(parameters, persistencePolicy);
	}

	private String setPersistencePolicy(SpaceMapCreator.Parameters parameters, String persistencePolicy) {
		if (BackingStoreConfig.PERSISTENCE_POLICY_ASYNC.equals(persistencePolicy)) {
			parameters.setPersistencePolicy(SpaceDef.PersistencePolicy.ASYNC);
		} else if (BackingStoreConfig.PERSISTENCE_POLICY_SYNC.equals(persistencePolicy)) {
			parameters.setPersistencePolicy(SpaceDef.PersistencePolicy.SYNC);
		}
		return persistencePolicy;
	}
    
    public void waitUntilReady(boolean recover) throws ASException {
        if (recoveryEnabled && recover) {
            SpaceMapCreator.performSpaceRecovery(metaspace, target.getSpace());
        }
        
        target.waitUntilReady();
    }

    public boolean isSeederType(ControlDaoType type, boolean forcedSeeder) {
		// For leeches do not seed any spaces
		if (!forcedSeeder) {
			return false;
		}

        boolean result = true;
        switch (type) {
            case AgentTable:
            case AgentTxn$AgentId:
            case Master:
            case MasterId:
            case WorkManager:
                result = forcedSeeder;
                break;
                
            case WorkList$SchedulerId:
            case PublisherQueue:
                result = false;
                break;
                
            default:
                break;
        }
        return result;
    }

    @Override
    public boolean lockAll(long timeoutMillis) {
        return target.lock(LOCK_ALL, timeoutMillis);
    }

    @Override
    public boolean lock(K key, long timeoutMillis) {
        return target.lock(key, timeoutMillis);
    }

    @Override
    public boolean unlock(K key) {
        try {
            return target.unlock(key);
        }
        catch (RuntimeException exception) {
            Throwable t = exception.getCause();

            if (t != null && t instanceof ASException && ((ASException) t).getStatus() == ASStatus.NOT_FOUND) {
                //Ignore. As per AS team - http://jira.tibco.com/browse/BE-13433
                return true;
            }

            throw exception;
        }
    }

    @Override
    public void unlockAll() {
        target.unlock(LOCK_ALL);
    }

    @Override
    public void discard() {
        listenerMap.clear();

        target.discard();
        target = null;

        metaspace = null;
    }

    @Override
    public void registerListener(ChangeListener changeListener) {
        try {
            MapListenerAdapter adapter = new MapListenerAdapter(changeListener);

            ListenerDef listenerDef = ListenerDef.create(TimeScope.NEW_EVENTS, DistributionScope.ALL);
            target.getSpace().listen(adapter, listenerDef/*todo Suresh: , filter*/);

            listenerMap.put(changeListener, adapter);
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unregisterListener(ChangeListener changeListener) {
        MapListenerAdapter adapter = listenerMap.remove(changeListener);
        if (adapter == null) {
            return;
        }

        try {
            target.getSpace().stopListener(adapter);
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------

    @Override
    public int size() {
        return target.size();
    }

    @Override
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
    public V put(K key, V value) {
        //true parameter - Ask AS to return the old value on the put
        return (V) target.put(key, value, true);
    }

    @Override
    public V remove(Object key) {
        return (V) target.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        target.putAll(m);
    }

    @Override
    public void clear() {
        target.clear();
    }

    @Override
    public Set<K> keySet() {
        return (Set<K>) target.keySet();
    }

    @Override
    public Collection<V> values() {
        return (Collection<V>) target.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set set = target.entrySet();

        return set;
    }

    //--------------

    @Override
    public Collection<V> getAll(Collection<K> keys) {
        return (Collection<V>) target.getAll((Collection<Object>) keys);
    }

    @Override
    public void removeAll(Collection<K> ks) {
        switch (type) {
            case ObjectTableIds:
            case ObjectTableExtIds:
                target.removeAll((Collection<Object>) ks);
                return;

            case WorkList$SchedulerId:
                long msec = System.currentTimeMillis();
                target.removeAll((Collection<Object>) ks);
                if (logger.isEnabledFor(Level.TRACE)) {
                    logger.log(Level.TRACE, "%s removed %s items in %s msec.", type, ks.size(), (System.currentTimeMillis()-msec));
                }
                return;

            default:
        }

        for (K key : ks) {
            target.remove(key);
        }
    }

    @Override
    public Set entrySet(com.tibco.cep.runtime.service.om.api.Filter filter, int limit) {
        return RemoteFilterRunner.entrySet(target, filter);
    }

    @Override
    public Set keySet(com.tibco.cep.runtime.service.om.api.Filter filter, int limit) {
        return RemoteFilterRunner.keySet(target, filter);
    }

    public SpaceMap getSpaceMap() {
        return target;
    }

    @Override
    public Map getInternal() {
        return target;
    }

    public KeyValueTupleAdaptor<K, V> getTupleAdaptor() {
        return tupleAdaptor;
    }

    //--------------

    protected class MapListenerAdapter implements PutListener, TakeListener, ExpireListener {
        protected ChangeListener changeListener;

        public MapListenerAdapter(ChangeListener changeListener) {
            this.changeListener = changeListener;
        }

        @Override
        public void onExpire(ExpireEvent expireEvent) {
            handleRemove(expireEvent.getTuple());
        }

        @Override
        public void onPut(PutEvent putEvent) {
            Object key = null;
            Object oldValue = null;
            Object newValue = null;

            Tuple tuple = null;
            try {
                tuple = putEvent.getTuple();
                try {
                    KeyValueTupleAdaptor<Object, Object> tupleAdaptor = target.getTupleAdaptor();

                    key = tupleAdaptor.extractKey(tuple);
                    newValue = tupleAdaptor.extractValue(tuple);
                }
                finally {

                }
            }
            finally {

            }

            if (putEvent.hasOldTuple()) {
                try {
                    tuple = putEvent.getOldTuple();

                    try {
                        oldValue = target.getTupleAdaptor().extractValue(tuple);
                    }
                    finally {
                    }
                }
                finally {
                }

                if (newValue == null) {
                    changeListener.onRemove(key, oldValue);
                }
                else {
                    changeListener.onUpdate(key, oldValue, newValue);
                }
            }
            else {
                changeListener.onPut(key, newValue);
            }
        }

        @Override
        public void onTake(TakeEvent takeEvent) {
            Tuple tuple = takeEvent.getTuple();
            handleRemove(tuple);
        }

        protected void handleRemove(Tuple tuple) {
            Object key = null;
            Object oldValue = null;

            try {
                KeyValueTupleAdaptor<Object, Object> tupleAdaptor = target.getTupleAdaptor();

                key = tupleAdaptor.extractKey(tuple);
                oldValue = tupleAdaptor.extractValue(tuple);
            }
            finally {

            }

            changeListener.onRemove(key, oldValue);
        }
    }

    @Override
    public Map invoke(Filter filter, Invocable invocable) {
        return new ASFilteredInvocableWrapper().invoke(target, filter, invocable);
    }

    @Override
    public Invocable.Result invokeWithKey(Object key, Invocable invocable) {
        return new ASFilteredInvocableWrapper().invokeWithKey(target, key, invocable);

    }

    @Override
    public Map invoke(Set keys, Invocable invocable) {
        Map m = new HashMap();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            Object key = i.next();
            Invocable.Result result = new ASFilteredInvocableWrapper().invokeWithKey(target, key, invocable);
            m.put(key, result);
        }
        return m;
    }
}
