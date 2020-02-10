/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.dao.impl.tibas;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.as.space.ASException;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.IndexDef;
import com.tibco.as.space.IndexDef.IndexType;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.SpaceDef.CachePolicy;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.as.space.SpaceDef.PersistenceType;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.event.EvictEvent;
import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.listener.EvictListener;
import com.tibco.as.space.listener.ExpireListener;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.as.space.listener.ListenerDef.DistributionScope;
import com.tibco.as.space.listener.ListenerDef.TimeScope;
import com.tibco.as.space.persistence.Persister;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.map.SpaceMapCreator;
import com.tibco.cep.as.kit.map.SpaceMapCreator.Parameters;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.model.serializers.as.ConceptTupleAdaptor;
import com.tibco.cep.runtime.model.serializers.as.EventTupleAdaptor;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTableCache;
import com.tibco.cep.runtime.service.dao.impl.tibas.versionedget.ASVersionedGet;
import com.tibco.cep.runtime.service.om.api.ComparisonResult;
import com.tibco.cep.runtime.service.om.api.CompositeIndexConfig;
import com.tibco.cep.runtime.service.om.api.DataCacheConfig;
import com.tibco.cep.runtime.service.om.api.EncryptionConfig;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig.CacheMode;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.IndexConfig;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.query.SelectAlwaysFilter;
import com.tibco.cep.runtime.service.om.api.query.SqlFilter;
import com.tibco.cep.runtime.service.om.impl.AbstractEntityDao;
import com.tibco.cep.runtime.service.rmi.ASFilteredInvocableWrapper;
import com.tibco.cep.runtime.service.rmi.RemoteFilterRunner;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xdc.Collector;
import com.tibco.xdc.Common;
import com.tibco.xdc.Receiver;

/*
* Author: Ashwin Jayaprakash / Date: Nov 30, 2010 / Time: 10:51:17 AM
*/
public class ASEntityDao<E extends Entity> extends AbstractEntityDao<E, SpaceMap<Long, E>> {

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(ASEntityDao.class);

    protected Metaspace metaspace;

    protected SpaceMap<Long, E> target;

    protected VersionType versionType = VersionType.UNVERSIONED;

    protected boolean recoveryEnabled = false;

    //the daoProvider argument is only to discourage creating a new Dao outside of ASDaoProvider.makeNew.
    //this is so that it is safer to call cluster.getDaoProvider() in startHook()
    protected ASEntityDao(Metaspace metaspace, ASDaoProvider daoProvider) {
        if (daoProvider == null) {
            throw new IllegalArgumentException("Tried to create ASEntityDao without daoProvider");
        }
        this.metaspace = metaspace;
    }

    public static <E extends Entity> ASEntityDao<E> newASEntityDao(ASDaoProvider daoProvider, Metaspace metaspace) {
        return new ASEntityDao<E>(metaspace, daoProvider);
    }

    @Override
    protected SpaceMap<Long, E> startHook(boolean overwrite) {
        Persister persister = null;
        try {
            DataCacheConfig dataCacheConfig = daoConfig.getDataCacheConfig();

            SpaceMapCreator.Parameters parameters =
                    new SpaceMapCreator.Parameters<Long, Entity>()
                            .setSpaceName(cacheName)
                            .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                            .setReplicationCount(dataCacheConfig.getBackupCount())
                            .setKeyColumnName(SpaceMapConstants.TUPLE_COLUMN_NAME_KEY)
                            .setKeyClass(Long.class)
                            .setTupleCodec(((ASDaoProvider)cluster.getDaoProvider()).getTupleCodec())
                            .setRedefine(overwrite);

            RuleServiceProvider RSP = cluster.getRuleServiceProvider();
            BEProperties properties = (BEProperties) RSP.getProperties();
            boolean usesSharedNothing = ((ASDaoProvider) cluster.getDaoProvider()).isSharedNothing();
            boolean usesSharedAllWithBDB = ((ASDaoProvider) cluster.getDaoProvider()).isSharedAllWithBDB();

            int minSeeders = cluster.getClusterConfig().getMinSeeders();
            parameters.setMinSeeders(minSeeders);

            String isseeder = System.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
            boolean storageEnabled = Boolean.parseBoolean(isseeder);
            if (storageEnabled) {
                parameters.setRole(DistributionRole.SEEDER);
                recoveryEnabled = true;
            }
            else {
                parameters.setRole(DistributionRole.LEECH);
                recoveryEnabled = false;
            }

            handleValueType(parameters, dataCacheConfig);

            if (dataCacheConfig.isLimited() && dataCacheConfig.getLimitedSize() > 0) {
                parameters.setLimitedCacheCapacity(dataCacheConfig.getLimitedSize());
            } else {
                parameters.setLimitedCacheCapacity(dataCacheConfig.getUnlimitedSize(usesSharedNothing));
            }

            // Get DAO Configuration
            EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(this.getEntityClass());

            // Assign TTL settings for concepts only (others are -1)
            parameters.setTtl(entityConfig.getConceptTTL());
            logger.log(Level.INFO, "Entity DAO %s ttl=%s", cacheName, parameters.getTtl());

            // Check if the override "has backing store" has been checked on/off for this entity
            boolean backingStoreOverrideEnabled = true;
            if (entityConfig != null && !entityConfig.hasBackingStore()) {
                backingStoreOverrideEnabled = false;
                logger.log(Level.DEBUG, "Backing store override disabled for %s", this.cacheName);
            } else {
                logger.log(Level.DEBUG, "Backing store override enabled for %s", this.cacheName);
            }

            // Enable shared nothing persistence
            if (usesSharedNothing && backingStoreOverrideEnabled) {
                parameters.setPersistenceType(SpaceDef.PersistenceType.SHARE_NOTHING);
            }

            if (daoConfig.hasBackingStore()) {
                String persistencePolicy = ((ASDaoProvider) cluster.getDaoProvider()).getPersistencePolicy();
                if (BackingStoreConfig.PERSISTENCE_POLICY_ASYNC.equals(persistencePolicy)) {
                    parameters.setPersistencePolicy(SpaceDef.PersistencePolicy.ASYNC);
                }
                else if (BackingStoreConfig.PERSISTENCE_POLICY_SYNC.equals(persistencePolicy)) {
                    parameters.setPersistencePolicy(SpaceDef.PersistencePolicy.SYNC);
                }

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
                        if (dataCacheConfig.isCacheAside() == true) {
                            parameters.setCachePolicy(CachePolicy.READ_THROUGH);
                        }
                        try {
                            // Only seeder should have persister installed
                            if (storageEnabled) {
                                Constructor cons = cacheLoaderClass
                                        .getConstructor(new Class[]{String.class, Integer.class, String.class, getClass()});
                                parameters.setPersister((Persister) cons.newInstance(
                                        new Object[]{cacheName, dataCacheConfig.isCacheAside() ? 1 : 0, entityClass.getName(),
                                                this}));
                                logger.log(Level.DEBUG,
                                        "Creating Entity DAO backing store persister(%s) for %s(cache-aside=%s class=%s)",
                                        new Object[]{persisterDistributionType, parameters.getSpaceName(), dataCacheConfig.isCacheAside(),
                                                cacheLoaderClass.getName()});
                            }
                            else {
                                logger.log(Level.DEBUG,
                                        "Not creating Entity DAO backing store persister for %s(cache-aside=%s class=%s)",
                                        new Object[]{parameters.getSpaceName(), dataCacheConfig.isCacheAside(),
                                                cacheLoaderClass.getName()});
                            }
                        }
                        catch (Exception ex) {
                            logger.log(Level.WARN,
                                    "Creating Entity DAO backing store persister failed for " + parameters.getSpaceName() +
                                            "(class=" + cacheLoaderClass.getName() + ")", ex);
                        }
                    }
                }
            }
            else {
                // TODO: If unlimited, then set Capacity to -1
                //if (dataCacheConfig.isLimited() == false) {
                //    parameters.setLimitedCacheCapacity(dataCacheConfig.getUnlimitedSize(true));
                //}
                // TODO: Why is expiry enabled only without persistence?
                //long ttl = TypeHelper.$extractOtherTTL(cluster, entityClass);
                //if (ttl > 0) {
                //    parameters.setTtl(ttl);
                //}
            }

            if (usesSharedAllWithBDB && backingStoreOverrideEnabled) {
                parameters.setPersistenceType(SpaceDef.PersistenceType.SHARE_ALL);
                parameters.setPersistencePolicy(SpaceDef.PersistencePolicy.SYNC);
                parameters.setPersistenceDistributionPolicy(DistributionPolicy.NON_DISTRIBUTED);
                logger.log(Level.INFO, "Backing store persistence policy is SYNC for %s", this.cacheName);
            }

            boolean addExtIdIndex = properties.getBoolean(SystemProperty.AS_EXTID_INDEX.getPropertyName(), false);
            if ((ManagedObjectManager.isOn() || addExtIdIndex) &&
                ensureIndexable(parameters.getExplicitFieldDefs(), PortablePojoConstants.PROPERTY_NAME_EXT_ID)) {
                List<IndexDef> indexDefs = parameters.getIndexDefs();
                if (indexDefs == null) {
                    indexDefs = new LinkedList<IndexDef>();
                    parameters.setIndexDefs(indexDefs);
                }

                IndexDef indexDef = IndexDef.create("extId_idx")
                        .setFieldNames(PortablePojoConstants.PROPERTY_NAME_EXT_ID)
                        .setIndexType(IndexType.HASH);
                indexDefs.add(indexDef);
            }
            
            logger.log(Level.DEBUG, "Entity DAO %s role=%s policy=%s capacity=%s size=%s replication=%s ttl=%s sync=%s cachePolicy=%s ignoreOldValue=%s minSeeder=%s",
                    cacheName, parameters.getRole().name(), parameters.getDistributionPolicy(),
                    ((parameters.getLimitedCacheCapacity() > 0) ? "limited" : "unlimited"),
                    parameters.getLimitedCacheCapacity(), parameters.getReplicationCount(), parameters.getTtl(),
                    parameters.isSyncReplication(), parameters.getCachePolicy(), parameters.isForgetOldValue(), parameters.getMinSeeders());

            target = SpaceMapCreator.create(metaspace, parameters);

            if (usesSharedAllWithBDB && storageEnabled && backingStoreOverrideEnabled) {
                ASPersistenceProvider asPersistenceProvider =
                        ((ASDaoProvider) cluster.getDaoProvider()).getBDBPersistenceProvider();
                persister = asPersistenceProvider.createDataPersister(target.getTupleAdaptor(), target.getSpace().getName());
                if (persister != null) {
                    target.getSpace().setPersister(persister);
                }
            }

            postCreate(parameters, target);

            if (Common.isCollector()) {
                Collector.INSTANCE.register(this);
            }
            else if (Common.isReceiver()) {
                Receiver.INSTANCE.register(this);
            }
            return target;
        }
        catch (ASException e) {
            throw new RuntimeException("Error occurred while creating entity DAO [" + cacheName + "]", e);
        }
    }

    @Override
    public void waitUntilReady(boolean recover) throws ASException {
        // TODO: Enable 'establishQuorum' if needed
        //SpaceMapCreator.establishQuorum(metaspace, target.getSpace());

        long begin_time = System.currentTimeMillis();
        String msg = "";
        if (recoveryEnabled && recover) {
            SpaceMapCreator.performSpaceRecovery(metaspace, target.getSpace());
        }
        cache.waitUntilReady();
        if (target.getSpace().getSpaceDef().getPersistenceType() == PersistenceType.SHARE_NOTHING) {
            try {
                msg = String.format("Current number of items in space [%d]", target.getSpace().size());
            }
            catch (ASException asex) {
                // When there are many nodes, calling space.size() causes OPERATION_TIMEOUT
                msg = String.format("Current number of items in space [%s]", "unknown");
            }
        }
        long end_time = System.currentTimeMillis();
        logger.log(Level.INFO, "Space [%s] became [%s] after [%s]ms. %s",
                this.cacheName, target.getSpace().getSpaceState(), (end_time - begin_time), msg);
    }

    protected void handleValueType(Parameters parameters, DataCacheConfig dataCacheConfig) {
        String uri = daoConfig.getUri();
        com.tibco.cep.designtime.model.Entity entity =
                cluster.getRuleServiceProvider().getProject().getOntology().getEntity(uri);

        boolean custom = false;

        Properties properties = cluster.getRuleServiceProvider().getProperties();
        String codecExplicitStr = properties.getProperty(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(), Boolean.FALSE.toString());
        boolean codecExplicit = Boolean.parseBoolean(codecExplicitStr);

        String securityEnabledStr = properties.getProperty(SystemProperty.AS_SECURITY_ENABLE.getPropertyName(), Boolean.FALSE.toString());
        boolean securityEnabled = Boolean.parseBoolean(securityEnabledStr);

        if (codecExplicit) {
            if (entity instanceof Concept /*&& !entity.toString().contains("Scorecard")*/) {
                custom = ConceptTupleAdaptor.canHandle(cluster, parameters, (Concept) entity);
            }
            else if (entity instanceof Event) {
                custom = EventTupleAdaptor.canHandle(cluster, parameters, (Event) entity);
            }
        }

        if (!custom) {
            parameters.setValueColumnName(SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE)
                      .setValueClass(Entity.class);
            if (VersionedObject.class.isAssignableFrom(entityClass)) {
                versionType = VersionType.CONCEPT_BLOB_ASSERIALIZER;
            }
        } else {
            handleIndexing(parameters, entity);

            if (securityEnabled) {
                handleEncryption(parameters, dataCacheConfig);
            }

            if (entity instanceof Concept /*&& !entity.toString().contains("Scorecard")*/) {
                versionType = VersionType.CONCEPT_TUPLE;
            }
        }
    }

    private List<IndexConfig> getIndexConfigsFromEntity(com.tibco.cep.designtime.model.Entity entity) {
        List<IndexConfig> configs = new ArrayList<IndexConfig>();
        while (entity != null) {
            String uri = entity.getFullPath();
            TypeManager.TypeDescriptor td = this.cluster.getRuleServiceProvider().getTypeManager()
                    .getTypeDescriptor(uri);
            EntityDao entityDao = this.cluster.getMetadataCache().getEntityDao(td.getTypeId());
            IndexConfig[] confs = entityDao.getConfig().getDataCacheConfig().getIndexConfigs();
            if (confs != null) {
                Collections.addAll(configs, confs);
            }
            if (entity instanceof Concept) {
                entity = ((Concept) entity).getSuperConcept();
            } else if (entity instanceof Event) {
                entity = ((Event) entity).getSuperEvent();
            } else {
                entity = null;
            }
        }
        return configs;
    }
    
	protected void handleIndexing(Parameters parameters, com.tibco.cep.designtime.model.Entity entity) {
		List<IndexConfig> indexConfigs = getIndexConfigsFromEntity(entity);
		List<FieldDef> fieldDefs = parameters.getExplicitFieldDefs();

		if (indexConfigs != null) {
			LinkedList<IndexDef> indexDefs = new LinkedList<IndexDef>();

			int i = 0;
			for (IndexConfig indexConfig : indexConfigs) {
				IndexDef indexDef = null;
				if (indexConfig.getClass() == CompositeIndexConfig.class) {
					// composite Index
					CompositeIndexConfig cmpIndexConfig = (CompositeIndexConfig) indexConfig;
					List<String> fieldNames = new ArrayList<String>();
					for (String field : cmpIndexConfig.getFieldNames()) {
						if (ensureIndexable(fieldDefs, field)) {
							fieldNames.add(field);
						}
					}
					if (fieldNames.size() > 0) {
						//can not directly use name from the IndexConfig because parent and child config's might have same name in cdd
						indexDef = IndexDef.create(cacheName + "_cmpidx_" + (i++));
						indexDef.setFieldNames(fieldNames.toArray(new String[fieldNames.size()]));
					} else {
						// skip adding this composite index as none of the fields are indexable
						logger.log(Level.WARN, "Skipping composite index [%s], %s in cache=%s since all fields are of type BLOB",
								cmpIndexConfig.getIndexName(), Arrays.toString(cmpIndexConfig.getFieldNames()), cacheName);
						continue;
					}
				} else {
					indexDef = IndexDef.create(cacheName + "_idx_" + (i++));
					String name = indexConfig.getFieldNames()[0];
					if (!ensureIndexable(fieldDefs, name)) {
						logger.log(Level.WARN, "Skipping index [%s] in cache=%s", indexDef, cacheName);
						continue;
					}
					indexDef.setFieldNames(name);
				}
				// No CDD option yet to choose.
				indexDef.setIndexType(IndexType.HASH);
				indexDefs.add(indexDef);
				logger.log(Level.DEBUG, "Creating index [%s] in cache=%s", indexDef, cacheName);
			}

			parameters.setIndexDefs(indexDefs);
		}
	}

    protected void handleEncryption(Parameters parameters, DataCacheConfig dataCacheConfig) {
        EncryptionConfig encryptionConfig = dataCacheConfig.getEncryptionConfig();

        if (encryptionConfig != null) {
            List<FieldDef> fieldDefs = parameters.getExplicitFieldDefs();

            for (FieldDef fieldDef : fieldDefs) {
                if (encryptionConfig.getFieldNames().contains(fieldDef.getName())) {
                    /*
                    if (!ensureEncryptible(fieldDefs, name)) {
                        logger.log(Level.WARN, "Skipping encryption [%s] in cache=%s", indexDef, cacheName);
                        continue;
                    }
                    */
                    fieldDef.setEncrypted(true);
                    logger.log(Level.INFO, "Creating encrypted field [%s] in cache=%s", fieldDef.getName(), cacheName);
                }
            }
        }
    }

    public static boolean ensureIndexable(List<FieldDef> fieldDefs, String fieldName) {
        if (fieldDefs == null) {
            return false;
        }

        for (FieldDef fieldDef : fieldDefs) {
            if (fieldDef.getName().equals(fieldName)) {
                if (ensureIndexable(fieldDef.getType())) {
                    return true;
                }

                break;
            }
        }

        return false;
    }

    public static boolean ensureIndexable(FieldType fieldType) {
        return fieldType != FieldType.BLOB;
    }

    private void postCreate(SpaceMapCreator.Parameters<Long, Entity> parameters, SpaceMap<Long, E> spaceMap) {
        if (parameters.getRole() != DistributionRole.SEEDER || daoConfig.getCacheMode() != CacheMode.Cache) {
            return;
        }

        try {
            // Enable Expiry/Eviction listeners to purge ObjectTable references, under following cases:
            //  (i) TTL is set (Expiry and deletion)
            // (ii) Capacity is set without persistence (Eviction with no chance to recover)
            // Note: It is possible to configure an entity with TTL, along with limited Capacity.
            //       In that case we would like to delete the ObjectTable entry when Expiry happens,
            //       but not when Eviction happens. Therefore we need to handle (i) and (ii) separately.
            if (spaceMap.getSpace().getSpaceDef().getTTL() > 0) {
                ListenerDef listenerDef = ListenerDef.create(TimeScope.ALL, DistributionScope.SEEDED);

                Space space = spaceMap.getSpace();
                space.listen(new TupleExpiryListener(), listenerDef);

                logger.log(Level.INFO, "Registered TTL expiry listener on [" + space.getName() + "]");
            }

            if (spaceMap.getSpace().getSpaceDef().getPersistenceType() == SpaceDef.PersistenceType.NONE &&
                spaceMap.getSpace().getSpaceDef().getCapacity() > 0 && spaceMap.getSpace().getSpaceDef().getCapacity() < Long.MAX_VALUE) {
                ListenerDef listenerDef = ListenerDef.create(TimeScope.ALL, DistributionScope.SEEDED);

                Space space = spaceMap.getSpace();
                space.listen(new TupleEvictListener(), listenerDef);

                logger.log(Level.INFO, "Registered Capacity eviction listener on [" + space.getName() + "]");
            }
        }
        catch (ASException e) {
            logger.log(Level.ERROR, "Error ocurred while registering event expiry/eviction listener", e);
        }
    }

    @Override
    public boolean lock(Long key, long timeoutMillis) {
        return cache.lock(key, timeoutMillis);
    }

    @Override
    public boolean unlock(Long key) {
        return cache.unlock(key);
    }

    @Override
    public Collection<E> getAll() {
        return cache.values();
    }

    @Override
    public Collection<E> getAll(Collection<Long> keys) {
        return cache.getAll(keys);
    }

    public KeyValueTupleAdaptor<Long, E> getTupleAdaptor() {
        return cache.getTupleAdaptor();
    }

    @Override
    public SpaceMap<Long, E> getInternal() {
        return super.getInternal();
    }

    @Override
    public int clear(String filter) {
        return ((SpaceMap)cache).clear(filter);
    }

    @Override
    public void removeAll(Set<Long> keys) {
        long start = System.currentTimeMillis();
        long removed = keys.size();

        cache.removeAll(keys);

        numRemoves.addAndGet(removed);
        timeRemoves.addAndGet(System.currentTimeMillis() - start);
    }

    //---------------

    /**
     * @param filter If this is an instance of {@link SqlFilter} then the {@link SqlFilter#getQuery() query string} will be
     *               executed directly against the cache.
     * @param limit
     * @return
     */
    @Override
    public Set entrySet(Filter filter, int limit) {
        // Come here if explicit tuple is true
        if (filter instanceof SqlFilter) {
            return cache.entrySet(((SqlFilter) filter).getQuery());
        }
        else if (filter instanceof SelectAlwaysFilter) {
            // Come here if it is a select * without a filter
            // eg: select * from /ConceptModel/Trades t where t.ticker = "TIBX";
            return cache.entrySet();
        }

        // Come here if explicit tuple is false
        // You then need to run code on the seeders to deserialize and filter and return the results.
        return RemoteFilterRunner.entrySet(cache, filter);
    }

    /**
     * @param filter If this is an instance of {@link SqlFilter} then the {@link SqlFilter#getQuery() query string} will be
     *               executed directly against the cache.
     * @param limit
     * @return
     */
    @Override
    public Set keySet(Filter filter, int limit) {
        if (filter instanceof SqlFilter) {
            return cache.keySet(((SqlFilter) filter).getQuery());
        }
        else if (filter instanceof SelectAlwaysFilter) {
            return cache.keySet();
        }

        return RemoteFilterRunner.keySet(cache, filter);
    }

    @Override
    public Map invoke(Filter filter, Invocable invocable) {
        return new ASFilteredInvocableWrapper().invoke(cache, filter, invocable);
    }

    @Override
    public Invocable.Result invokeWithKey(Object key, Invocable invocable) {
        return new ASFilteredInvocableWrapper().invokeWithKey(cache, key, invocable);
    }

    @Override
    public Map invoke(Set keys, Invocable invocable) {
        Map m = new HashMap();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            Object key = i.next();
            Invocable.Result result = new ASFilteredInvocableWrapper().invokeWithKey(cache, key, invocable);
            m.put(key, result);
        }
        return m;
    }

    @Override
    protected Object fetchLatestEntityOrComparisonResult(Long key, int version) throws ASException {
        if (versionType == VersionType.UNVERSIONED) {
            return ComparisonResult.NOT_VERSIONED;
        } else {
            return ASVersionedGet.doInvoke(key, version, versionType, getInternal());
        }
    }

    protected class TupleExpiryListener extends TupleListener implements ExpireListener {

        int expiryBatchSize = BATCH_SIZE;

        public TupleExpiryListener() {
            super();
            this.expiryBatchSize = Integer.parseInt(System.getProperty(SystemProperty.OBJECT_TABLE_EXPIRY_BATCH_SIZE.getPropertyName(), String.valueOf(BATCH_SIZE)));
            logger.log(Level.DEBUG, "Expiry listener created with batch size [%s] = %s", SystemProperty.OBJECT_TABLE_EXPIRY_BATCH_SIZE.getPropertyName(), expiryBatchSize);
        }

        @Override
        public void onExpire(ExpireEvent expireEvent) {
            Long id = null;
            try {
                id = super.removeFromObjectTable(expireEvent.getTuple(), this.expiryBatchSize);
            }
            catch (Exception e) {
                logger.log(Level.ERROR, e,
                        "Error occurred while attempting to handle expiry of entry with id [" + id + "]");
            }
        }
    }

    protected class TupleEvictListener extends TupleListener implements EvictListener {

        int evictionBatchSize = 0;

        public TupleEvictListener() {
            super();
            this.evictionBatchSize = Integer.parseInt(System.getProperty(SystemProperty.OBJECT_TABLE_EVICTION_BATCH_SIZE.getPropertyName(), "0"));
            logger.log(Level.DEBUG, "Eviction batch size [%s] = %s", SystemProperty.OBJECT_TABLE_EVICTION_BATCH_SIZE.getPropertyName(), evictionBatchSize);
        }

        @Override
        public void onEvict(EvictEvent evictEvent) {
            Long id = null;
            try {
                id = super.removeFromObjectTable(evictEvent.getTuple(), this.evictionBatchSize);
            }
            catch (Exception e) {
                logger.log(Level.ERROR, e,
                        "Error occurred while attempting to handle eviction of entry with id [" + id + "]");
            }
        }
    }

    protected class TupleListener {

        static final int BATCH_SIZE = 9999;

        ObjectTable objectTable;

        List<Long> batchedIds;

        List<String> batchedExtIds;

        EvictExpiryEnsurer delayedEnsurer;

        ScheduledThreadPoolExecutor evictExpiryExecutor;
        
        long EXPIRY_DELAY_TIME;

        public TupleListener() {
            this.objectTable = cluster.getObjectTableCache();
            this.batchedIds = Collections.synchronizedList(new ArrayList<Long>(BATCH_SIZE));
            this.batchedExtIds = Collections.synchronizedList(new ArrayList<String>(BATCH_SIZE));

            this.delayedEnsurer = new EvictExpiryEnsurer(this);
            this.evictExpiryExecutor = new ScheduledThreadPoolExecutor(1);
            
            Properties properties = cluster.getRuleServiceProvider().getProperties();
            EXPIRY_DELAY_TIME = Long.parseLong(properties.getProperty(SystemProperty.OBJECT_TABLE_EVICTION_DELAY_INTERVAL.getPropertyName(), "60"));
        }

        private Long removeFromObjectTable(Tuple tuple, int batchSize) throws Exception {
            KeyValueTupleAdaptor<Long, E> tupleAdaptor = cache.getTupleAdaptor();
            Long id = tupleAdaptor.extractKey(tuple);
            Entity entity = tupleAdaptor.extractValue(tuple);
            String extId = entity.getExtId();

            if (batchedIds.isEmpty()) {
                evictExpiryExecutor.schedule(delayedEnsurer, EXPIRY_DELAY_TIME, TimeUnit.SECONDS);
            }

            batchedIds.add(id);

            if (extId != null) {
                batchedExtIds.add(extId);
            }

            removeTuples(batchSize);

            return id;
        }

        public void removeTuples (int batchSize) throws Exception {
            synchronized (batchedIds) {
                if ((batchedIds.size() > batchSize)) {
                	((ObjectTableCache)objectTable).removeAll(batchedIds, batchedExtIds, true, getEntityClass());

                    logger.log(Level.INFO,
                            batchedIds.size() + " entries expired/evicted from [" + cache.getSpace().getName() + "] recently");

                    batchedIds.clear();
                    batchedExtIds.clear();
                }
            }
        }
    }

    protected class EvictExpiryEnsurer implements Runnable {

        TupleListener target;

        public EvictExpiryEnsurer (TupleListener target) {
            this.target = target;
        }

        public void run() {
            try {
                logger.log(Level.TRACE,
                        "Triggering to expire/evict entries from [" + cache.getSpace().getName() + "] on timer");
                target.removeTuples(0); // Remove everything after delay
            }
            catch (Exception e) {
                logger.log(Level.WARN, e,
                        "Failed to expire/evict entries from [" + cache.getSpace().getName() + "]");
            }
        }
    }

    public enum VersionType {
        UNVERSIONED, CONCEPT_TUPLE, CONCEPT_BLOB_ASSERIALIZER;
    }
}
