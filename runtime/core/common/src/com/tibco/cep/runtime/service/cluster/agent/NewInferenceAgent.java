/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.agent;


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Nov 24, 2008 Time: 2:17:54 PM
*/

public class NewInferenceAgent extends InferenceAgent {


    /**
     * {@value}.
     */
    public static final String PROP_CONSTANT_ENTITY_CLASS_SUFFIX = ".constant";

    protected ObjectTable objectTable;

    /**
     * Can be <code>null</code>.
     */
    protected HashSet<String> constantEntityClassNames;

    /**
     * Can be <code>null</code>.
     */
    protected ConcurrentHashMap<Long, Entity> constantEntities;

    private boolean lenientMode;

    public NewInferenceAgent(AgentConfiguration agentConfig, RuleServiceProvider rsp, CacheAgent.Type type) throws Exception {
        super(agentConfig, rsp, type);
    }


    @Override
    protected void onInit() throws Exception {
        super.onInit();
        initConstants();
        objectTable = getCluster().getObjectTableCache();

        AgentConfiguration config = getAgentConfig();
        lenientMode = config.isLenient();

        if (type == Type.CACHESERVER || Boolean.parseBoolean(readProperty(SystemProperty.CLUSTER_NODE_ISSEEDER, "false"))) {
            onInitOfCacheServer();
        }
    }

    private String readProperty(SystemProperty systemProperty, String defaultValue) {
        return cluster.getRuleServiceProvider().getProperties().getProperty(systemProperty.getPropertyName(), defaultValue);
    }

    protected void onInitOfCacheServer() throws Exception {
        Properties beProperties = cluster.getRuleServiceProvider().getProperties();
        String specialOmFlag = readProperty(SystemProperty.VM_SPECIAL_CACHE_OM, "false");

        if (Boolean.parseBoolean(specialOmFlag)) {
            logger.log(Level.DEBUG, "Special OM requested. Initiating...");

            Class cacheServerAgentService = Class.forName(
                    "com.tibco.cep.query.stream.impl.rete.integ.CacheServerAgentService");

            Method csasInitMethod =
                    cacheServerAgentService.getMethod("init", String.class, Cluster.class, Integer.TYPE);

            String maxThreadsFlag = readProperty(SystemProperty.VM_SPECIAL_CACHE_OM_MAX_THREADS, "16");
            int maxThreads = Integer.parseInt(maxThreadsFlag);

            csasInitMethod.invoke(null /*static*/, "RemoteQueryFilterSpecialOM", cluster, maxThreads);

            logger.log(Level.INFO, "Special OM initialized with [" + maxThreads + "] threads");
        }
        else {
            logger.log(Level.DEBUG, "Special OM not requested");
        }
    }

//    @Override
//    public void registerAgent() throws Exception {
//        super.registerAgent();
//
//        initConstants();
//        objectTable = getCluster().getObjectTableCache();
//
//        AgentConfiguration config = getAgentConfig();
//        lenientMode = config.isLenient();
//    }

    private void initConstants() {
        RuleServiceProvider rsp = ruleSession.getRuleServiceProvider();
        BEProperties props = (BEProperties) rsp.getProperties();

        HashSet<String> classNames = new HashSet<String>();

        java.util.Iterator allConfigs = this.cluster.getMetadataCache().getEntityConfigurations().entrySet().iterator();
        while (allConfigs.hasNext()) {
            Map.Entry entry = (Map.Entry) allConfigs.next();
            Class entityClz = (Class) entry.getKey();
            EntityDaoConfig config = (EntityDaoConfig) entry.getValue();
            if (config.isConstant()) {
                classNames.add(entityClz.getName());
            }
        }

        if (classNames.isEmpty() == false) {
            constantEntityClassNames = classNames;
            constantEntities = new ConcurrentHashMap<Long, Entity>();
        }
    }

    //----------------

//    protected void refreshConstant(long id, Class entityClz) throws Exception {
//        final Long idAsObject = id;
//        constantEntities.remove(idAsObject);
//        Entity entity = fetchFromDistributedCache(idAsObject, entityClz);
//        if (entity != null) {
//            constantEntities.put(idAsObject, entity);
//        }
//    }

    public void refreshEntity(long id, int typeId, int version) throws Exception {
        refreshEntity(id, getCluster().getMetadataCache().getClass(typeId), version);
    }


    /**
     * @param id
     * @param entityClass
     * @throws Exception
     */
    public void refreshEntity(long id, Class entityClass, int version) throws Exception {
        Long idAsObject = id;
        final boolean constantsAreSet = (constantEntities != null);

        if (constantsAreSet) {
            if (constantEntityClassNames.contains(entityClass.getName())) {
                int local_version = -1;
                Entity local_en = constantEntities.get(idAsObject);
                if (local_en instanceof VersionedObject) {
                    local_version = ((VersionedObject) local_en).getVersion();
                }
                EntityDao entityProvider = getCluster().getMetadataCache().getEntityDao(entityClass);
                if (entityProvider != null) {
                    Entity en = null;
                    if (local_version >= 0) {
                        EntityDao.Result<Entity> ret = entityProvider.getIfVersionGreater(idAsObject, local_version);
                        en = handleVersionedGetResult(local_en, ret);
                    }
                    else {
                        en = (Entity) entityProvider.getByPrimaryKey(idAsObject);
                    }
                    if (en != null) {
                        constantEntities.put(idAsObject, en);
                    }
                    else {
                        constantEntities.remove(idAsObject);
                    }
                }
                return;
            }
        }

        Entity entity = fetchFromLocalCache(idAsObject);
        if (entity != null) {
            if (entity instanceof VersionedObject) {
                int local_version = ((VersionedObject) entity).getVersion();
                if ((local_version < version) || (version < 0)) {
                    EntityDao entityProvider = getCluster().getMetadataCache().getEntityDao(entityClass);
                    if (entityProvider != null) {
                        Entity en = (Entity) entityProvider.get(id);
                        if (en != null) {
                            localCache.put(en);
                        }
                        else {
                            localCache.remove(entity);
                        }
                        int typeId = cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass());
                        agentStats.incrementNumHitsInL1Cache(typeId);
                    }
                }
            }
            else {
                EntityDao entityProvider = getCluster().getMetadataCache().getEntityDao(entityClass);
                if (entityProvider != null) {
                    Entity en = (Entity) entityProvider.get(id);
                    if (en != null) {
                        localCache.put(en);
                    }
                    int typeId = cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass());
                    agentStats.incrementNumHitsInL1Cache(typeId);
                }
            }
        }
        else {
            EntityDao entityProvider = getCluster().getMetadataCache().getEntityDao(entityClass);
            if (entityProvider != null) {
                Entity en = (Entity) entityProvider.get(id);
                if (en != null) {
                    localCache.put(en);
                }
                int typeId = cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass());
                agentStats.incrementNumMissesInL1Cache(typeId);
            }
        }
    }

    /*
     * Only pass accurate values for entityClz, otherwise pass null or call the other method without the class argument.
     */
    @Override
    public Entity getEntityById(long id, Class entityClz) throws Exception {
        Entity ret = doGetEntityById(id, entityClz);
        if (ret != null) {
            ret.setLoadedFromCache();
        }
        return ret;
    }

    @Override
    public Entity getEntityById(long id) throws Exception {
        Entity ret = doGetEntityById(id, null);
        if (ret != null) {
            ret.setLoadedFromCache();
        }
        return ret;
    }

    /**
     * @param id
     * @param entityClass Can be <code>null</code>.
     * @return
     * @throws Exception
     */
    protected Entity doGetEntityById(long id, Class entityClass) throws Exception {
        final Long idAsObject = id;

        //-------------

        if (isHandleDeleted(idAsObject)) {
            return null;
        }

        //-------------

        final boolean constantsAreSet = (constantEntities != null);

        if (constantsAreSet) {
            if (entityClass == null) {
                Entity constantEntity = constantEntities.get(idAsObject);
                if (constantEntity != null) {
                    return constantEntity;
                }
            }
            else {
                if (constantEntityClassNames.contains(entityClass.getName())) {
                    Entity constantEntity = constantEntities.get(idAsObject);
                    if (constantEntity != null) {
                        return constantEntity;
                    }
                }
            }
        }

        //-------------

        Entity entity = fetchFromLocalCache(idAsObject);

        final boolean foundInLocalCache = (entity != null);
        if (foundInLocalCache && logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG, "Found in local cache: " + entity.getClass().getName()
                    + ", Id: " + id + ", ExtId: " + entity.getExtId());
        }

        if (foundInLocalCache) {
            int typeId = cluster.getMetadataCache().getTypeId(entityClass);
            agentStats.incrementNumHitsInL1Cache(typeId);
        }
        else {
            entity = fetchFromDistributedCache(idAsObject, entityClass);
            if (entity != null) {
                int typeId = cluster.getMetadataCache().getTypeId(entityClass);
                agentStats.incrementNumMissesInL1Cache(typeId);
            }
        }

        //Still null. Hmmm.. then return.
        if (entity == null) {
            return null;
        }

        //-------------

        //A constant and we reached here means that it was not in the constants-cache.
        if (constantsAreSet && constantEntityClassNames.contains(entity.getClass().getName())) {
            constantEntities.put(idAsObject, entity);

            return entity;
        }

        //-------------

        boolean localVersionInvalid = false;

        //Found something in the local cache. Check if it is the latest version.
        if (foundInLocalCache) {
            if (entity instanceof VersionedObject) {
                final Entity localVersion = entity;

                entity = fetchLatestVersion(localVersion);

                //Still null. Hmmm.. then return.
                if (entity == null) {
                    localCache.removeV2(idAsObject);

                    return null;
                }

                if (localVersion != entity) {
                    localVersionInvalid = true;
                }
            }
            else if (entity instanceof SimpleEvent) {
                SimpleEvent localVersion = (SimpleEvent) entity;

                localVersion = checkIfExists(localVersion);
                //Still null. Hmmm.. then return.
                if (localVersion == null) {
                    localCache.removeV2(idAsObject);

                    return null;
                }

                return localVersion;
            }
        }

        //Refresh only when required.
        if (!foundInLocalCache || localVersionInvalid) {
            putToLocalCache(entity);
        }

        return entity;
    }

    /**
     * @param idAsObject
     * @return
     */
    private Entity fetchFromLocalCache(Long idAsObject) {
        return localCache.getV2(idAsObject);
    }

    /**
     * Just a fetch. Does not put it in any map/local cache.
     *
     * @param id
     * @param entityClz Can be <code>null</code>.
     * @return
     * @throws Exception
     */
    private Entity fetchFromDistributedCache(Long id, Class entityClz) throws Exception {
        EntityDao entityProvider = null;

        if (entityClz == null) {
            Tuple tuple = objectTable.getById(id);

            //if (tuple == null || (lenientMode == false && tuple.isDeleted())) {
            if (tuple == null || (tuple.isDeleted())) {
                //Update the local cache.
                localCache.removeV2(id);
                return null;
            }

            entityProvider = getCluster().getMetadataCache().getEntityDao(tuple.getTypeId());
        } else {
            entityProvider = getCluster().getMetadataCache().getEntityDao(entityClz);
        }

        Entity entity = null;
        if (entityProvider != null) {
            entity = entityProvider.getByPrimaryKey(id);
        }

        return entity;
    }

    /**
     * @param event
     * @return The same event if it is still alive. Else, <code>null</code>.
     * @throws Exception
     */
    protected SimpleEvent checkIfExists(SimpleEvent event) throws Exception {
        Long id = event.getId();

        boolean alive = objectTable.isEventAlive(id);
        if (alive) {
            return event;
        }

        return null;
    }

    protected Entity fetchLatestVersion(Entity entity) throws Exception {
        Class clazz = entity.getClass();
        VersionedObject versionedObject = (VersionedObject) entity;
        Long id = entity.getId();

        EntityDao entityProvider = getCluster().getMetadataCache().getEntityDao(clazz);
        if (entityProvider.getConfig().requiresVersionCheck()) {
            EntityDao.Result versionResult = entityProvider.getIfVersionGreater(id, versionedObject.getVersion());

            return handleVersionedGetResult(entity, versionResult);
        }
        else {
            return entity;
        }
    }

    private void putToLocalCache(Entity latestVersion) {
        if (getAgentState() == CacheAgent.AgentState.ACTIVATED) {
            localCache.put(latestVersion);
        }
    }

    private Entity handleVersionedGetResult(Entity entity, EntityDao.Result<Entity> versionResult) {
    	if (versionResult == null) {
    		//Bala: should never be null here
    		if (entity instanceof VersionedObject)
    			logger.log(Level.WARN, "Version Result is null. Should never be so. Returning original object : " 
    					+ entity.getExtId() + " "  + ((VersionedObject)entity).getVersion());
    		return entity;
    	}
    	Entity latestVersion = versionResult.getResult();
        if (logger.isEnabledFor(Level.DEBUG)) {
            String s = null;

            if (latestVersion != null && latestVersion instanceof VersionedObject) {
                s = "New object: " + latestVersion + ", Version: " +
                        ((VersionedObject) latestVersion).getVersion();
            }
            else {
                s = "Version result: " + versionResult.getVersion();
            }

            logger.log(Level.DEBUG, "Original object: " + entity
                    + ", Version: " + ((VersionedObject) entity).getVersion()
                    + ". " + s);
        }
        return latestVersion == null? entity : latestVersion;
    }

//    public Entity getEntityIndexedByExtId(String extId, Class entityClass) throws Exception {
//        //Found nothing.
//        return null;
//    }
}
