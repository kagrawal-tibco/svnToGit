/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.migration.expimp.providers.db;

import java.lang.reflect.Constructor;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;

import com.tibco.be.migration.BEMigrationConstants;
import com.tibco.be.oracle.OracleDebug;
import com.tibco.be.oracle.impl.OracleActiveConnectionPool;
import com.tibco.be.oracle.impl.OracleAdapter;
import com.tibco.be.oracle.impl.OracleConnectionManager;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 27, 2008
 * Time: 10:08:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityStore {


    public final static int CACHE_MODE_WMONLY = 0;
    public final static int CACHE_MODE_CACHEONLY = 1;
    public final static int CACHE_MODE_CACHEANDWM = 2;
    public final static String [] cacheModes = new String[]{"memory", "cache", "cacheAndMemory"};
    public final static int WARNING_THRESHOLD = 2000;
    static final int MAX_RETRY_COUNT = 3;

    private DbStore dbStore;
    private String cacheName;
    private String connStr;
    private int partitionId;
    private OracleAdapter config;
    private String oracleTableName;

    private int cacheType;
    private Class implClass;
    private boolean isDeployed;
    private int cacheMode;
    private String uri;
    private Entity entityModel;
    private Logger logger;
    private boolean customSetting;
    private RuleServiceProvider mrsp;
    private Constructor entityConstructor;
    long numConceptUpserts, totalConceptTimeUpserts, numConceptStores;
    private boolean m_isIgnore;

//    public EntityStore(DbStore dbStore, String cacheName, int partitionId, String connStr) {
//        this.dbStore = dbStore;
//        this.cacheName = cacheName;
//        this.connStr = connStr;
//        this.partitionId = partitionId;
//    }


    public EntityStore(DbStore dbStore, Entity entity, int partitionId, String connStr) throws Exception {
        this.dbStore = dbStore;
        this.mrsp = dbStore.getRuleServiceProvider();
        this.logger = this.mrsp.getLogger(EntityStore.class);
        if (entity instanceof StateMachine) {
            StateMachine sm = (StateMachine) entity;
            ExpandedName uri = ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI + sm.getOwnerConcept().getFullPath() + "/" + sm.getName(), sm.getName());
            TypeManager.TypeDescriptor smtd = mrsp.getTypeManager().getTypeDescriptor(uri);
            this.implClass = smtd.getImplClass();
        } else {
            this.implClass = mrsp.getTypeManager().getTypeDescriptor(entity.getFullPath()).getImplClass();
        }
        if (entity instanceof StateMachineConceptImpl.StateTimeoutEvent)
            this.entityConstructor = implClass.getConstructor(new Class[]{long.class, long.class, String.class, long.class});
        else
            this.entityConstructor = implClass.getConstructor(new Class[]{long.class, String.class});
        loadAdvancedSettings(dbStore.getOmConfig(), entity.getFullPath());
        this.cacheName = dbStore.getEntityStoreName(implClass.getName());
        this.connStr = connStr;
        this.partitionId = partitionId;
    }

    public EntityStore(DbStore dbStore, Class implClass, int partitionId, String connStr) throws Exception {
        this.dbStore = dbStore;
        this.mrsp = dbStore.getRuleServiceProvider();
        this.logger = this.mrsp.getLogger(EntityStore.class);
        final TypeManager.TypeDescriptor td = mrsp.getTypeManager().getTypeDescriptor(implClass);
        if (null != td) {
            this.entityModel = mrsp.getProject().getOntology().getEntity(td.getURI());
            loadAdvancedSettings(dbStore.getOmConfig(), td.getURI());
        }

        this.implClass = implClass;
        if (implClass.getName() == "com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl$StateTimeoutEvent")
            this.entityConstructor = implClass.getConstructor(new Class[]{long.class, long.class, String.class, long.class});
        else
            this.entityConstructor = implClass.getConstructor(new Class[]{long.class, String.class});
        this.cacheName = dbStore.getEntityStoreName(implClass.getName());
        this.connStr = connStr;
        this.partitionId = partitionId;
    }

    public void init() throws Exception {
        boolean oracleDebug = Boolean.valueOf(mrsp.getProperties().getProperty("be.oracle.debug", "false").trim()).booleanValue();
        if (oracleDebug || this.logger.isEnabledFor(Level.DEBUG)) {
            OracleDebug.setDebugLevel(9);
        } else {
            OracleDebug.setDebugLevel(0);
        }

        if (!cacheName.startsWith(dbStore.getMasterCacheName() + ".")) {
            throw new IllegalArgumentException("Unknown Cache :" + cacheName);
        }

        final String className=cacheName.substring(getMasterCacheName().length()+1);
            if ((className == null) || (className.trim().length() <= 0)) {
                m_isIgnore=true;
                return;
            } else {
                m_isIgnore=false;
        }
        OracleActiveConnectionPool pool = registerOracleDataSource(cacheName, connStr, partitionId);

        this.config = new OracleAdapter(pool, mrsp);
        //config.getConnectionPool().activate();
        try {
            //config.registerCache(implClass.getName());
            this.oracleTableName = config.generatedOracleTableName(implClass.getName());
        } catch (Exception ex) {
            this.getLogger().log(Level.ERROR, ex, ex.getMessage());
            throw ex;
        } finally {
            config.releaseConnection();
        }

        if (Concept.class.isAssignableFrom((implClass))) {
            cacheType = DistributedCacheBasedStore.CacheType.CONCEPT.value();
        } else if (Event.class.isAssignableFrom(implClass)) {
            cacheType = DistributedCacheBasedStore.CacheType.EVENT.value();
        } else {
            throw new RuntimeException("Unsupported entityClz " + implClass);
        }
    }

    private OracleActiveConnectionPool registerOracleDataSource(String cacheName, String connStr, int key) throws Exception {
        try {
            OracleActiveConnectionPool pool= OracleConnectionManager.getActiveConnectionPool("migtest");
            if (pool == null) {
                DriverManager.registerDriver(new OracleDriver());
                OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
                final String oracleUser;
                final String oraclePassword;
                final String oracleURI;
                if (!connStr.trim().startsWith("jdbc:oracle:thin:")) {
                    throw new IllegalArgumentException("Unsupported database type :" + connStr);
                }
                connStr = connStr.trim().substring("jdbc:oracle:thin:".length());
                String[] cons = connStr.split("@");
                String [] up = cons[0].split("/");
                oracleURI = "jdbc:oracle:thin:" + "@" + cons[1];
                oracleUser = up[0];
                oraclePassword = up[1];
                ds.setURL(oracleURI);
                ds.setUser(oracleUser);
                ds.setPassword(oraclePassword);
                ds.setDataSourceName(String.valueOf(key));
                String rspName = mrsp.getName();
                this.logger.log(Level.INFO, "Registering DataSource, "
                        + "cacheName=%s, partition id=%s, rsp=%s, uri=%s, user=%s",
                        cacheName, key, rspName.substring(rspName.lastIndexOf('.') + 1), oracleURI, oracleUser);

                // cache specific pool sizes override global pool size definition
                String poolSize = mrsp.getProperties().getProperty(BEMigrationConstants.PROP_DB_POOLSIZE, "10").trim();
                String retryInterval = mrsp.getProperties().getProperty(BEMigrationConstants.PROP_DB_RETRYINTERVAL, "5").trim();
                Properties properties = new Properties();
                properties.put("MinLimit", poolSize );
                properties.put("InitialLimit", poolSize );
                properties.put("RetryInterval", retryInterval);

                OracleConnectionManager.registerDataSource("migtest", ds, properties, true);
                return OracleConnectionManager.getActiveConnectionPool("migtest");
            }

            return pool;
        } catch (Exception ex) {
            this.logger.log(Level.ERROR, ex, ex.getMessage());
            throw ex;
        }
    }


    public void connect() {

    }

    protected TypeManager getTypeManager() {
        return mrsp.getTypeManager();
    }

    public boolean isCustomSetting() {
        return customSetting;
    }

    public Logger getLogger() {
        return logger;
    }

    public Entity getModelEntity() {
        return entityModel;
    }

    public com.tibco.cep.kernel.model.entity.Entity newEntity(long id, String extId) throws Exception {
        return (com.tibco.cep.kernel.model.entity.Entity) entityConstructor.newInstance(new Object[]{new Long(id), extId});
    }

    // for StateTimeoutEvent
    public com.tibco.cep.kernel.model.entity.Entity newEntity(long id, long smId, String propertyName, long scheduledTime) throws Exception {
        return (com.tibco.cep.kernel.model.entity.Entity) entityConstructor
                .newInstance(new Object[]{new Long(id), new Long(smId), propertyName, new Long(scheduledTime)});
    }

    public String getCacheName() {
        return getMasterCacheName() + "." + implClass.getName();
    }

    public String getMasterCacheName() {
        return dbStore.getMasterCacheName();
    }

    public Class getImplClass() {
        return implClass;
    }

    public String getStoreName() {
        return this.config.generatedOracleTableName(implClass.getName());
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public int getCacheMode() {
        return cacheMode;
    }

    public String getURI() {
        return uri;
    }


    public boolean isCacheEnabled() {
        return isDeployed() && ((cacheMode == CACHE_MODE_CACHEONLY) || (cacheMode == CACHE_MODE_CACHEANDWM));
    }

    protected void loadAdvancedSettings(Properties cacheConfig, String entityUri) throws Exception {
        ArrayList configs = new ArrayList();
        XiNode advancedSettings = (XiNode) cacheConfig.get("omtgAdvancedEntitySettings");
        String [] cacheModes = new String[]{"memory", "cache", "cacheAndMemory"};
        if (advancedSettings != null) {
            Iterator allRows = advancedSettings.getChildren();
            while (allRows.hasNext()) {
                XiNode rowNode = (XiNode) allRows.next();
                loadEntitySettings(rowNode);
                this.customSetting = true;
            }
        }
        return;
    }

    private void loadEntitySettings(XiNode configNode) throws Exception {
        this.uri = XiChild.getString(configNode, ExpandedName.makeName("uri"));
        this.isDeployed = XiChild.getBoolean(configNode, ExpandedName.makeName("deployed"));
        String sCacheMode = XiChild.getString(configNode, ExpandedName.makeName("cacheMode"));
        for (int i = 0; i < cacheModes.length; i++) {
            if (cacheModes[i].equalsIgnoreCase(sCacheMode)) {
                this.cacheMode = i;
                break;
            }
        }
    }

    private void storeAllConcepts(Map map) throws Exception {
        // To enable upsert, uncomment this line
        ++this.numConceptStores;

        if (WARNING_THRESHOLD < map.size()) {
            this.logger.log(Level.WARN, "Store[%s] exceeds the threshold limit. Decrease the write-queue delay.",
                    map.size());
        }
        Iterator allValues = map.values().iterator();
        while (allValues.hasNext()) {
            com.tibco.cep.runtime.model.element.Concept concept = (com.tibco.cep.runtime.model.element.Concept) allValues.next();
            Long key = new Long(concept.getId());
            this.logger.log(Level.DEBUG, "Storing Concept=%s id=%s into %s",
                    concept.getExpandedName().getLocalName(), key, oracleTableName);
            try {
                if (config.entityExists(oracleTableName, concept.getId(), partitionId)) {
                    config.updateConcept(oracleTableName, concept, partitionId);
                } else {
                    config.insertConcept(oracleTableName, concept, partitionId);
                }
            }
            catch (Exception e) {
                if (config != null)
                config.rollback(true);
                this.logger.log(Level.ERROR, e,
                        "Exception when storing Concept=%s id=%s into %s: %s. Concept ingnored.",
                        concept.getExpandedName().getLocalName(), key, oracleTableName, e.getMessage());
            }
        }
        this.numConceptUpserts += map.size();

        if (config != null)
            config.commit();

        if ((this.numConceptStores % 50000) == 0) {
            this.logger.log(Level.INFO, "%s NumConceptStores=%s, AverageUpsertTime=%s, NumConcepts=%s",
                    this.cacheName,
                    this.numConceptStores,
                    this.totalConceptTimeUpserts / (this.numConceptUpserts * 1.0),
                    this.numConceptUpserts);
        }
    }

    private void storeAllEvents(Map map) throws Exception {
        if (WARNING_THRESHOLD < map.size()) {
            this.logger.log(Level.WARN, "Store[%s] exceeds the threshold limit. Decrease the write-queue delay.",
                    map.size());
        }

        if (map != null && map.values().size() > 0) {
            if (OracleDebug.isDebugOn())
                OracleDebug.debugln("Batch Inserting Events in " + oracleTableName + " with size = " + map.values().size());
            config.insertEvents(oracleTableName, partitionId, map.values());
        }

        if (config != null)
            config.commit();
    }

    public void storeAll(Map map) {
        
        try {
            if (this.m_isIgnore) {
                return;
            }
            this.logger.log(Level.DEBUG, "%s StoreAll map = %s", this.cacheName, map);

            if (cacheType == DistributedCacheBasedStore.CacheType.CONCEPT.value()) {
                storeAllConcepts(map);
            } else if (cacheType == DistributedCacheBasedStore.CacheType.EVENT.value()) {
                storeAllEvents(map);
            }

        } catch (Exception e) {
            if (config != null)
                config.rollback(false);
            this.logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Object getById(long Id) throws Exception{

        if(OracleDebug.isDebugOn()) OracleDebug.debugln("@" + System.currentTimeMillis() + ": GetById Called .. " + Id + ", table name =" + this.oracleTableName);

        for(int retryCount = 0; retryCount < MAX_RETRY_COUNT; retryCount++) {
            try {
                if (cacheType == DistributedCacheBasedStore.CacheType.CONCEPT.value()) {
                    com.tibco.cep.runtime.model.element.Concept cept= config.getConceptById(oracleTableName, Id, partitionId);
                    if (cept != null) {
                        //return new ConceptAdapter((ObjectCache)getParentCache(), cept, true);
                        if(OracleDebug.isDebugOn()) OracleDebug.debugln("@" + System.currentTimeMillis() + ": GetById Done .. " + Id + "cept=" + cept.getClass().getName());
                    } else {
                        if(OracleDebug.isDebugOn()) OracleDebug.debugln("@" + System.currentTimeMillis() + ": No Matching Record  .. " + Id);
                    }
                    config.releaseConnection();
                    return cept;
                } else if (cacheType == DistributedCacheBasedStore.CacheType.EVENT.value()) {
                    com.tibco.cep.kernel.model.entity.Event event= config.getEventById(oracleTableName,Id, partitionId);
                    if (event != null) {
                        //return new EventAdapter((EventCache)getParentCache(), event, true);
                        if(OracleDebug.isDebugOn()) OracleDebug.debugln("@" + System.currentTimeMillis() + ": GetById Done .. " + Id);
                    }
                    config.releaseConnection();
                    return event;
                } else {
                    throw new Exception("Unknown Type " + cacheType);
                }

            } catch (Exception e) {
                config.rollback(false);
                String msg = "Attempt " + retryCount + ", failed to get " + (cacheType == DistributedCacheBasedStore.CacheType.CONCEPT.value()? "Concept Instance": "Event") + " of id=" + Id;
                if(retryCount >= (MAX_RETRY_COUNT-1)) {
                    msg += ", give up...";
                    this.logger.log(Level.ERROR, e, msg);
                    throw e;
                } else {
                    msg += ", try again...";
                    this.logger.log(Level.WARN, e, msg);
                }
            }
        }
        return null;
    }

    public long getCount() throws Exception {
        try {

            int i=0;
            HashMap tmp = new HashMap();

            if ((cacheType == DistributedCacheBasedStore.CacheType.CONCEPT.value()) || (cacheType == DistributedCacheBasedStore.CacheType.EVENT.value())) {
                long numHandles= config.countKeyPairs(oracleTableName, partitionId);
                config.releaseConnection();
                return numHandles;
            }
        } catch (Exception e) {
            if (config != null)
                config.rollback(false);
            this.logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public TypeManager.TypeDescriptor getTypeDescriptor() {
        return this.mrsp.getTypeManager().getTypeDescriptor(getImplClass());
    }

    public DbStore getDbStore() {
        return this.dbStore;
    }
}
