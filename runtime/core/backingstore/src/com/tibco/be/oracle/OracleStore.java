/*
 * Copyright(c) 2004-2013 TIBCO Software Inc.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.oracle;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;

import com.tibco.be.oracle.impl.OracleActiveConnectionPool;
import com.tibco.be.oracle.impl.OracleAdapter;
import com.tibco.be.oracle.impl.OracleConnectionManager;
import com.tibco.be.oracle.impl.OracleConnectionPool;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultScheduler.WorkTupleDBId;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.DBConnectionsBusyException;
import com.tibco.cep.runtime.util.DBException;
import com.tibco.cep.runtime.util.DBNotAvailableException;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public abstract class OracleStore implements BackingStore, OracleStoreMBean, BackingStore.ConnectionPoolListener {

    private Cluster cacheCluster;
    private RuleServiceProvider rsp;
    private final ArrayList<OracleAdapter> dbAdapters = new ArrayList<OracleAdapter>();
    private final ArrayList<AdapterStats> adStats = new ArrayList<AdapterStats>();

    public final static int CACHE_TYPE_MASTER = 0;
    public final static int CACHE_TYPE_METADATA = 1;
    public final static int CACHE_TYPE_CONCEPT = 2;
    public final static int CACHE_TYPE_STATEMACHINE = 3;
    public final static int CACHE_TYPE_EVENT = 4;
    public final static int CACHE_TYPE_RECOVERY = 5;
    public final static int CACHE_TYPE_CACHEIDGENERATOR = 6;
    public final static int CACHE_TYPE_OBJECTTABLE = 7;
    public final static int CACHE_TYPE_OBJECTEXTIDTABLE = 8;
    public final static int CACHE_TYPE_SEQUENCE = 9;
    public final static int CACHE_TYPE_TYPEID = 10;
    public final static int CACHE_TYPE_RECOVERYTABLE = 11;
    public final static int CACHE_TYPE_TOPICS = 12;
    public final static int CACHE_TYPE_CLUSTERLOCKS = 13;
    public final static int CACHE_TYPE_CACHESEQUENCE = 14;
    public final static int CACHE_TYPE_WORKMANAGER = 15;
    public final static int CACHE_TYPE_LOADTABLE = 16;
    public final static int CACHE_TYPE_TOTALSTABLE = 17;
    public final static int CACHE_TYPE_METADATAREGISTRY = 18;
    public final static int CACHE_TYPE_EXTERNALCLASSES = 19;
    public final static int CACHE_TYPE_HOTDEPLOYER = 20;
    public final static int CACHE_TYPE_BPMN = 21;

    protected final static int ORACLE_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE = 1;
    protected final static int MAX_RETRY_COUNT = 3;
    protected final static int WARNING_THRESHOLD = 2000;

    protected final static Logger logger = LogManagerFactory.getLogManager().getLogger(OracleStore.class);

    protected String agentName;
    protected String cacheName;
    protected String tableName;

    protected int cacheType = CACHE_TYPE_MASTER;

    protected boolean initialized = false;
    protected long numConceptUpserts, totalConceptTimeUpserts, numConceptStores;
    protected int commitBatchSize = 10;
    protected int preloadBatchSize = 10000;
    protected boolean deleteInBatch = true;

    protected boolean useObjectTable = true;
    protected boolean isObjectTablePersister = false;
    protected boolean isObjectCacheFullyLoaded = false;

    protected Long partitionId;
    protected String className;
    protected boolean m_isIgnore = false;
    protected Class entityClz = null;
    protected boolean forRecovery = false;
    protected boolean isScheduler = false;
    protected static Map mDeletedEntitiesMap = Collections.synchronizedMap(new HashMap());
    protected Set deletedEntities = new HashSet(100);
    private boolean registeredAdapterMBean = false;
    private Object reconnectLock = new Object();
    private static boolean writeMode = false;
    private BackingStore backingStoreHandle = null;

    /**
     * @param cacheName
     */
    public OracleStore(String cacheName) {
        this.cacheName = cacheName;
        registerMBean();
        try {
            initialize(false, false);
            registerAdapterMBean();
            addConnectionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OracleStore(String cacheName, Integer readOnly) {
        this.cacheName = cacheName;
        registerMBean();
        try {
            initialize(false, readOnly == 1);
            registerAdapterMBean();
            addConnectionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Used by Active Space implementation
    public OracleStore(String cacheName, Integer readOnly, String className) {
        this.cacheName = cacheName;
        this.className = className;
        registerMBean();
        try {
            initialize(false, readOnly == 1);
            if (tableName == null) {
                //tableName = config.generatedOracleTableName(className);
            }
            registerAdapterMBean();
            addConnectionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OracleStore(String cacheName, boolean forRecovery) {
        this.cacheName = cacheName;
        this.forRecovery = true;
        registerMBean();
        try {
            initialize(true, false);
            addConnectionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OracleStore(String cacheName, String schedulerCacheName) {
        this.cacheName = cacheName;
        this.isScheduler = true;
        registerMBean();
        try {
            initialize(false, false);
            registerAdapterMBean();
            addConnectionListener(this);
            if (tableName == null) {
                tableName = "WorkItems";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OracleStore(String cacheName, String schedulerCacheName, Integer readOnly) {
        this.cacheName = cacheName;
        this.isScheduler = true;
        registerMBean();
        try {
            initialize(true, readOnly == 1);
            registerAdapterMBean();
            addConnectionListener(this);
            if (tableName == null) {
                tableName = "WorkItems";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OracleStore() {
        this.forRecovery = true;
        registerMBean();
        try {
            initialize(true, false);
            addConnectionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void switched(boolean toSecondary) {

    }

    public void disconnected() {
    }
    
    public void reconnected() {
        synchronized (reconnectLock) {
            reconnectLock.notifyAll();
        }
    }

    protected boolean getCacheClusterWriteMode() {
        /**
        if (backingStoreHandle == null) {
            backingStoreHandle = cacheCluster.getRecoveryBackingStore();
        }
        if (backingStoreHandle != null) {
            return backingStoreHandle.getWriteMode();
        }
        return false;
        */
        return writeMode;
    }

    private void registerAdapterMBean() {
        if (this.registeredAdapterMBean == true) {
            return;
        }
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        for (int i = 0; i < dbAdapters.size(); i++) {
            AdapterStats stats = new AdapterStats(i, dbAdapters.get(i), cacheName);
            adStats.add(stats);
            try {
                ObjectName name = new ObjectName("com.tibco.be:service=BackingStore,name=" + cacheName + ",adapter=" + i);
                if(!mbs.isRegistered(name)) {
                	mbs.registerMBean(stats, name);
                }
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            } catch (MBeanRegistrationException e) {
                e.printStackTrace();
            } catch (InstanceAlreadyExistsException e) {
                e.printStackTrace();
            } catch (NotCompliantMBeanException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = null;
            if (this.isScheduler) {
                //name = new ObjectName("com.tibco.be:service=BackingStore,type=scheduler,name=" + cacheName);
            } else if (cacheType == CACHE_TYPE_SEQUENCE) {
                //name = new ObjectName("com.tibco.be:service=BackingStore,name=" + cacheName);
            } else if (this.forRecovery) {
                //name = new ObjectName("com.tibco.be:service=BackingStore,type=recovery,name=" + cacheName);
            } else {
                name = new ObjectName("com.tibco.be:service=BackingStore,name=" + cacheName);
            }
            if (name != null) {
            	if(!mbs.isRegistered(name)) {
                	mbs.registerMBean(this, name);
            	}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCacheName() {
        return cacheName;
    }

    public String getClassName() {
        return className;
    }

    public Long getPartitionId() {
        return partitionId;
    }

    public String getOracleTableName() {
        return tableName;
    }

    public boolean isDebug() {
        return (logger.getLevel() == Level.ALL);
    }

    public void setDebug(boolean debug) {
        if (debug) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(Level.INFO);
        }
    }

    private String getSubstitutedStringValue(GlobalVariables gv, String value) {
        final CharSequence cs = gv.substituteVariables(value);
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }
    }

    private String decryptPwd(String encryptedPwd) {
        try {
            String decryptedPwd = encryptedPwd;
            if (ObfuscationEngine.hasEncryptionPrefix(encryptedPwd)) {
                decryptedPwd = new String(ObfuscationEngine.decrypt(encryptedPwd));
            }
            return decryptedPwd;
        } catch (AXSecurityException e) {
            logger.log(Level.WARN, e.getMessage());
            return encryptedPwd;
        }
    }

    /*
    private void setOracleParams(GlobalVariables gv, XiNode dbNode) {
        XiNode dbConfig = XiChild.getChild(dbNode, ExpandedName.makeName("config"));
        try {
            oracleUserName = getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("user")));
            oraclePassword = decryptPwd(getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("password"))));
            oracleURI = getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("location")));
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Failed to get Oracle login credentials from JDBC connection resource");
        }
    }
    */

    private synchronized OracleConnectionPool registerOracleDataSource(RuleServiceProvider rsp, String key, String dburi, Properties properties, boolean isActive) throws Exception {
        try {
            OracleConnectionPool pool = OracleConnectionManager.getDataSource(key);
            if (pool == null) {
                XiNode db = rsp.getProject().getSharedArchiveResourceProvider().getResourceAsXiNode(dburi);
                if (db == null) {
                    throw new RuntimeException("The following shared resource was not exported in the SharedArchive:" + dburi);
                }
                pool = registerOracleDataSource(rsp, rsp.getGlobalVariables(), db, key, properties, isActive);
            }
            return pool;
        } catch (Exception ex) {
            logger.log(Level.WARN, "Failed to register datasource: %s", key);
            logger.log(Level.WARN, "Error - %s", ex.getMessage());
            throw ex;
        }
    }

    private synchronized OracleConnectionPool registerOracleDataSource(RuleServiceProvider rsp, GlobalVariables gv, XiNode dbNode, String key, Properties properties, boolean isActive) throws Exception {
        //setOracleParams(gv,dbNode);
        XiNode dbConfig = XiChild.getChild(dbNode, ExpandedName.makeName("config"));
        String oracleUserName = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("user")));
        String oraclePassword = decryptPwd(getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("password"))));
        String oracleURI = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("location")));

        // cache specific pool sizes override global pool size definition
        OracleDriver oracleDriver = new OracleDriver();
        DriverManager.registerDriver(oracleDriver);
        //logger.log(Level.DEBUG, "Major Version=" + oracleDriver.getMajorVersion() + ", Minor Version=" + oracleDriver.getMinorVersion());
        //if (oracleDriver.getMajorVersion() >= 10) {
        //    properties.put("useExplicitTemporaryBlobs", false);
        //} else {
        //    properties.put("useExplicitTemporaryBlobs", true);
        //}
        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
        ds.setURL(oracleURI);
        ds.setUser(oracleUserName);
        ds.setPassword(oraclePassword);
        ds.setDataSourceName(key);
        Properties props = new Properties();
        props.put("oracle.jdbc.ReadTimeout", rsp.getProperties().getProperty("be.backingstore.readtimeout", "0").trim());
        ds.setConnectionProperties(props);
        logger.log(Level.DEBUG, "DataSource properties: %s", props);
        //OracleConnectionCache.registerConnection(key, ds, oraclePoolSize);
        logger.log(Level.INFO, "Registering DataSource, rsp=%s, key=%s, uri=%s, user=%s, cacheName=%s", rsp.getName(), key, oracleURI, oracleUserName, this.cacheName);
        return OracleConnectionManager.registerDataSource(key, ds, properties, isActive);
    }

    private void initialize(boolean forRecovery, boolean isReadOnly) throws Exception {
        if (initialized) {
            return;
        }
        
        /*
         *  Search for the corresponding rule service provider
         */
        try {
            cacheCluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cacheCluster == null) {
                logger.log(Level.FATAL, "CLUSTER NOT FOUND...");
                System.exit(-1);
            }

            if (!forRecovery) {
                // Suresh TODO : Vincent check if this needed
                //cacheCluster.registerBackingStore(cacheName, this);
            }

            rsp = cacheCluster.getRuleServiceProvider();
            agentName = rsp.getName();
            
            //TODO: Vincent - Not sure we need this ContextClassLoader or not
            Thread.currentThread().setContextClassLoader(rsp.getClassLoader());

            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                initialized = true;
                logger.log(Level.INFO, "INITIALIZED OracleStore with cacheName=%s, forRecovery %s", this.cacheName, forRecovery);
                return;
            }

            deleteInBatch = Boolean.valueOf(rsp.getProperties().getProperty("be.backingstore.deleteInBatch", "true").trim());
            commitBatchSize = Integer.valueOf(rsp.getProperties().getProperty("be.backingstore.commitSize", "10").trim());
            preloadBatchSize = Integer.valueOf(rsp.getProperties().getProperty(SystemProperty.DATAGRID_RECOVERY_BATCHSIZE.getPropertyName(), "10000").trim());

            useObjectTable = cacheCluster.getClusterConfig().useObjectTable();
            
            if (cacheName != null) {
                if (className == null) {
                    className = getCacheClassName(cacheName);
                }

                if (className.endsWith(ControlDaoType.ObjectTableIds.name())) {
                    if (!isReadOnly && !forRecovery) {
                        if (cacheCluster.getClusterConfig().isCacheAside()) {
                            logger.log(Level.FATAL, "Invalid cache configuration. Cache %s not configured as read-only when cluster is cache aside.", this.cacheName);
                            System.exit(-1);
                        }
                    }
                    cacheType = CACHE_TYPE_OBJECTTABLE;
                    tableName = "ObjectTable";
                    isObjectTablePersister = true;
                    isObjectCacheFullyLoaded = Boolean.parseBoolean(rsp.getProperties().getProperty(SystemProperty.CLUSTER_CACHE_FULLY_LOADED.getPropertyName(), "false").trim());
                    initialized = true;
                    commitBatchSize = 1;
                } else if (className.endsWith(ControlDaoType.ObjectTableExtIds.name())) {
                    if (!isReadOnly && !forRecovery) {
                        if (cacheCluster.getClusterConfig().isCacheAside()) {
                            logger.log(Level.FATAL, "Invalid cache configuration. Cache %s not configured as read-only when cluster is cache aside.", this.cacheName);
                            System.exit(-1);
                        }
                    }
                    cacheType = CACHE_TYPE_OBJECTEXTIDTABLE;
                    tableName = "ObjectTable";
                    isObjectTablePersister = true;
                    isObjectCacheFullyLoaded = Boolean.parseBoolean(rsp.getProperties().getProperty(SystemProperty.CLUSTER_CACHE_FULLY_LOADED.getPropertyName(), "false").trim());
                    initialized = true;
                    commitBatchSize = 1;
                } else if (className.endsWith(("SequenceManager"))) {
                    cacheType = CACHE_TYPE_SEQUENCE;
                    initialized = true;
                } else if (className.endsWith(("TypeIds"))) {
                    cacheType = CACHE_TYPE_TYPEID;
                    initialized = true;
                } else if (className.endsWith(("RecoveryTable"))) {
                    cacheType = CACHE_TYPE_RECOVERYTABLE;
                    initialized = true;
                } else if (className.endsWith(("Topics"))) {
                    cacheType = CACHE_TYPE_TOPICS;
                    initialized = true;
                } else if (className.endsWith(("ClusterLocks"))) {
                    cacheType = CACHE_TYPE_CLUSTERLOCKS;
                    initialized = true;
                } else if (className.endsWith(("CacheSequence"))) {
                    cacheType = CACHE_TYPE_CACHESEQUENCE;
                    initialized = true;
                } else if (className.endsWith(("WorkManager"))) {
                    cacheType = CACHE_TYPE_WORKMANAGER;
                    initialized = true;
                } else if (className.endsWith(("LoadTable"))) {
                    cacheType = CACHE_TYPE_LOADTABLE;
                    initialized = true;
                } else if (className.endsWith(("TotalsTable"))) {
                    cacheType = CACHE_TYPE_TOTALSTABLE;
                    initialized = true;
                } else if (className.endsWith(("MetadataRegistry"))) {
                    cacheType = CACHE_TYPE_METADATAREGISTRY;
                    initialized = true;
                } else if (className.endsWith(("ExternalClasses"))) {
                    cacheType = CACHE_TYPE_EXTERNALCLASSES;
                    initialized = true;
                } else if (className.endsWith(("HotDeployer"))) {
                    cacheType = CACHE_TYPE_HOTDEPLOYER;
                    initialized = true;
                } else if (className.endsWith(("MergeGatewayTable")) ||
                           className.endsWith(("ProcessTemplates")) ||
                           className.endsWith(("LoopCounter"))) {  
                    cacheType = CACHE_TYPE_BPMN;
                    initialized = true;
                } else if (!isScheduler) {
                    try {
                        entityClz = ((ClassLoader) (rsp.getTypeManager())).loadClass(className);
                        if (!isReadOnly && !forRecovery) {
                            if (cacheCluster.getClusterConfig().isCacheAside()) {
                                logger.log(Level.FATAL, "Invalid cache configuration. Cache %s not configured as read-only when cluster is cache aside.", this.cacheName);
                                System.exit(-1);
                            }
                        }

                        if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
                            cacheType = CACHE_TYPE_STATEMACHINE;
                        } else {
                            cacheType = Concept.class.isAssignableFrom(entityClz) ? CACHE_TYPE_CONCEPT : CACHE_TYPE_EVENT;
                        }

                        isObjectCacheFullyLoaded = Boolean.parseBoolean(rsp.getProperties().getProperty(SystemProperty.CLUSTER_CACHE_FULLY_LOADED.getPropertyName(), "false").trim());
                        logger.log(Level.DEBUG, "%s is Fully Loaded=%s", this.cacheName, this.isObjectCacheFullyLoaded);
                    } catch (Exception ex) {
                        logger.log(Level.ERROR, "INITIALIZATION %s failed - Error %s ", entityClz, ex);
                        entityClz = null;
                        m_isIgnore = true;
                        initialized = true;
                        return;
                    }
                }
            }

            if (forRecovery) {
                logger.log(Level.DEBUG, "INITIALIZED OracleStore for recovery with commit size=%d", this.commitBatchSize);
            } else {
                logger.log(Level.DEBUG, "INITIALIZED OracleStore for %s with commit size=%d", this.className, this.commitBatchSize);
            }

            boolean oracleDebug = Boolean.valueOf(rsp.getProperties().getProperty("be.backingstore.debug", "false").trim()).booleanValue();
            if (oracleDebug) {
                //TODO: Set all loggers under backingstore to DEBUG
                logger.setLevel(Level.DEBUG);
            }

            ArrayList<OracleActiveConnectionPool> activePools = new ArrayList<OracleActiveConnectionPool>();
            int dbCount = Integer.valueOf(rsp.getProperties().getProperty("be.backingstore.dburi.count", "1").trim());
            if (dbCount > 2) {
                throw new Exception("be.backingstore.dburi.count=" + dbCount + " which is more than the maximum allowed (2)");
            }

            boolean useTemporaryBlobs = Boolean.valueOf(rsp.getProperties().getProperty("be.backingstore.useTemporaryBlobs", "true").trim());
            boolean recreateOnRecovery = Boolean.valueOf(rsp.getProperties().getProperty("be.backingstore.recreateOnRecovery", "false").trim());

            for (int i = 0; i < dbCount; i++) {
                String dburi = rsp.getProperties().getProperty("be.backingstore.dburi." + i);
                if (dburi == null || dburi.trim().length() == 0) {
                    throw new Exception("Invalid value for be.backingstore.dburi." + i + " (not specified or blank)");
                }
                Properties properties = new Properties();
                properties.put("LoadTypes", true); // Load Types for Oracle Connections
                dburi = dburi.trim();
                synchronized (OracleConnectionManager.class) {
                    if (OracleConnectionManager.getDataSource(dburi) == null) {
                        boolean enforceSize = Boolean.parseBoolean(rsp.getProperties().getProperty("be.backingstore.dburi.pool.enforce." + i, "false").trim());
                        String schema = rsp.getProperties().getProperty("be.backingstore.dburi.schema." + i);
                        schema = (schema == null ? null : (schema.trim().length() > 0 ? schema.trim() : null));
                        String initialSize = rsp.getProperties().getProperty("be.backingstore.dburi.pool.initial." + i, "10").trim();
                        String minSize = rsp.getProperties().getProperty("be.backingstore.dburi.pool.min." + i, "10").trim();
                        String maxSize = rsp.getProperties().getProperty("be.backingstore.dburi.pool.max." + i, "10").trim();
                        String waitTimeout = rsp.getProperties().getProperty("be.backingstore.dburi.pool.waitTimeout." + i, "1").trim();
                        String inactivityTimeout = rsp.getProperties().getProperty("be.backingstore.dburi.pool.inactivityTimeout." + i, "900").trim();
                        String retryInterval = rsp.getProperties().getProperty("be.backingstore.dburi.pool.retryinterval." + i, "5").trim();
                        boolean isActive = Boolean.parseBoolean(rsp.getProperties().getProperty("be.backingstore.dburi.active." + i, "true").trim());

                        String failBack = null;
                        if (isActive) {
                            failBack = rsp.getProperties().getProperty("be.backingstore.dburi.failBack." + i, "").trim();
                        }

                        if (schema != null) {
                            properties.put("schema", schema);
                        }
                        properties.put("RetryInterval", retryInterval);
                        properties.put("RecreateOnRecovery", recreateOnRecovery);
                        properties.put("AutoFailover", cacheCluster.getClusterConfig().isAutoFailover());
                        properties.put("FailoverInterval", cacheCluster.getClusterConfig().getAutoFailoverInterval());

                        if (enforceSize) {
                            //Check for the situation where prop is specified but as empty
                            if (minSize != null && minSize.length() > 0)
                                properties.put("MinLimit", minSize);
                            if (initialSize != null && initialSize.length() > 0)
                                properties.put("InitialLimit", initialSize);
                            if (maxSize != null && maxSize.length() > 0)
                                properties.put("MaxLimit", maxSize);
                            if (waitTimeout != null && waitTimeout.length() > 0)
                                properties.put("WaitTimeout", waitTimeout);
                            if (inactivityTimeout != null && inactivityTimeout.length() > 0)
                                properties.put("InactivityTimeout", inactivityTimeout);
                        }

                        logger.log(Level.INFO, "Initializing DataSource: schema=" + (schema==null ? "Default schema" : schema) + ", cacheName=" + cacheName + ", key=" + dburi + ", initialSize=" + initialSize + ", minSize=" + minSize + ", maxSize=" + maxSize);
                        logger.log(Level.INFO, "EnforcePoolSize=" + enforceSize + ", WaitTimeout=" + waitTimeout + ", InactivityTimeout=" + inactivityTimeout + ", CommitBatchSize=" + commitBatchSize); // + ", FailoverTo=" + failBack);
                        OracleConnectionPool pool = registerOracleDataSource(rsp, dburi, dburi, properties, isActive);
                        if (pool != null) {
                            if (isActive) {
                                OracleActiveConnectionPool activePool = OracleConnectionManager.getActiveConnectionPool(dburi);
                                if (failBack != null && failBack.length() > 0) {
                                    logger.log(Level.INFO, "Registering key=%s as failback connection", failBack);
                                    //pool.setFailbackKey(failBack);
                                    activePool.setFailbackKey(failBack);
                                } else if (!cacheCluster.getClusterConfig().isUsePrimaryDatasource()) {
                                    logger.log(Level.WARN, "Incompatible properties: No failback defined while usePrimaryDatasource is false");
                                    throw new Exception("Incompatible properties: No failback defined while usePrimaryDatasource is false");
                                }
                                activePool.setUsePrimary(cacheCluster.getClusterConfig().isUsePrimaryDatasource());
                                activePools.add(activePool);
                            }
                        }
                    } else {
                        OracleActiveConnectionPool activePool = OracleConnectionManager.getActiveConnectionPool(dburi);
                        if (activePool != null)
                            activePools.add(activePool);
                    }
                }
            }
            // Create OracleAdapters for each Active Connection pool
            for (OracleActiveConnectionPool activePool : activePools) {
                OracleAdapter config = new OracleAdapter(activePool, cacheCluster, useTemporaryBlobs);
                dbAdapters.add(config);
                if (className != null && tableName == null) {
                    tableName = config.generatedOracleTableName(className);
                }
            }

            partitionId = Long.parseLong(rsp.getProperties().getProperty("be.backingstore.partition", "1").trim());
            initialized = true;
        } catch (Exception ex) {
            logger.log(Level.FATAL, ex, "OracleStore initialization failed.");
            System.exit(-1);
        }
    }

    public List<WorkTuple> getWorkItems(String workQueue, long time, int status) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    return config.getWorkItems(workQueue, time, status);
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                config.releaseConnection();
            }
        }
        return null;
    }

    public void saveWorkItem(WorkTuple item) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            while (true) {
                //logger.log(Level.DEBUG, "In OracleStore, begin of loop to saveWorkItem()");
                try {
                    if (config.isActive()) {
                        config.saveWorkItem(item);
                        config.commit();
                        return;
                    }
                } catch (DBConnectionsBusyException busyEx) {
                    throw busyEx;
                }
                catch (SQLException ex) {
                    logger.log(Level.DEBUG, "SQLException while saving work item. Key=%s, scheduler=%s, exception=%s",
                            item.getWorkId(), item.getWorkQueue(), ex.getMessage());
                    if (ex.getErrorCode() == ORACLE_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE) {
                        throw ex;
                    }
                    //logger.log(Level.DEBUG, "Exc in saveWorkItems, workkey=" + item.getWorkId() + ", Error Code=" + ex.getErrorCode() + ", Msg=" + ex.getMessage());
                    try {
                        check(ex, config, config.getCurrentConnection());
                        config.rollback(false);
                        throw ex;
                    } catch (DBNotAvailableException dbe) {
                        //logger.log(Level.DEBUG, "In OracleStore saveWorkItem(), Got DBNotAvailableException from check, waitingToReconn");
                        if (!waitForReconnect(60000 * 5, -1)) {
                            throw ex;
                        }
                        //else retry in the loop.
                        //logger.log(Level.DEBUG, "Non-fatal error, rolled back");
                    }
                    //throw ex; // TODO: Check this
                } finally {
                    config.releaseConnection();
                }
            }
        }
        return;
    }

    @Override
	public WorkTuple getWorkItem(String key) throws Exception {
    	return _getWorkItem(key);
    }
    private WorkTuple _getWorkItem(String key) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    return config.getWorkItem(key);
                }
            }
            catch (SQLException ex) {
                if (check(ex, config, config.getCurrentConnection()))
                    config.rollback(false);
                throw ex;
            }
            catch (Exception ex) {
                config.rollback(false);
                throw ex;
            }
            finally {
                config.releaseConnection();
            }
        }
        return null;
    }

    public void removeWorkItem(String key) throws Exception {
        _removeWorkItems(null, key);
    }
    public void removeWorkItems(Collection<WorkTupleDBId> keysAndScheduledTimes) throws Exception {
        _removeWorkItems(keysAndScheduledTimes, null);
    }
    private void _removeWorkItems(Collection<WorkTupleDBId> keysAndScheduledTimes, String key) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    if (keysAndScheduledTimes == null) {
                        config.removeWorkItem(key);
                        config.commit();
                    } else if (keysAndScheduledTimes.size() <= 1000) {
                        if (deleteInBatch) {
                            config.removeWorkItemsInBatch(keysAndScheduledTimes);
                        } else {
                            config.removeWorkItems(keysAndScheduledTimes);
                        }
                        config.commit();
                    } else {
                        Iterator<WorkTupleDBId> iter = keysAndScheduledTimes.iterator();
                        Collection<WorkTupleDBId> coll = new ArrayList(1000);
                        int count = 0;
                        while (iter.hasNext()) {
                            coll.add(iter.next());
                            count++;
                            if (count == 1000) {
                                if (deleteInBatch) {
                                    config.removeWorkItemsInBatch(coll);
                                } else {
                                    config.removeWorkItems(coll);
                                }
                                config.commit();
                                coll = new ArrayList(1000);
                                count = 0;
                            }
                        }
                        if (coll.size() > 0) {
                            if (deleteInBatch) {
                                config.removeWorkItemsInBatch(coll);
                            } else {
                                config.removeWorkItems(coll);
                            }
                            config.commit();
                        }
                    }
                    return;
                }
            }
            catch (SQLException ex) {
                if (check(ex, config, config.getCurrentConnection()))
                    config.rollback(false);
                throw ex;
            }
            catch (Exception ex) {
                config.rollback(false);
                throw ex;
            }
            finally {
                config.releaseConnection();
            }
        }
        return;
    }

    public void updateWorkItem(WorkTuple item) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    config.updateWorkItem(item);
                    config.commit();
                    return;
                }
            }
            catch (SQLException ex) {
                if (check(ex, config, config.getCurrentConnection()))
                    config.rollback(false);
                throw ex;
            }
            catch (Exception ex) {
                config.rollback(false);
                throw ex;
            } finally {
                config.releaseConnection();
            }
        }
        return;
    }

    private boolean check(SQLException sqlEx, OracleAdapter config, Connection conn) {
        boolean success = false;
        logger.log(Level.TRACE, "In Check: conn=" + (conn==null?"Null":"Not-Null") + " SQLException=" + sqlEx.getMessage());
        try {
            if (conn == null) {
                try {
                    conn = config.getConnectionPool().getConnection();
                }
                catch (DBConnectionsBusyException busyEx) {
                    //No need to refresh connections
                    success = true;
                }
            }
            if (conn != null) {
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                    stmt.executeQuery("select 10 from dual");
                    success = true;
                } catch (SQLException sqle) {
                    // Bad connection. No operation
                    logger.log(Level.TRACE, "SQLException - Check query failed." + sqle);
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Exception e1) {
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Exception e1) {
                        }
                    }
                }
                logger.log(Level.TRACE, "SQLException - Check query worked.");
            }
        } catch (Exception ex) {
            // Bad connection. No operation
            logger.log(Level.WARN, "In Check caught Exception=" + ex.getMessage(), ex);
        }
        if (!success) {
            //logger.log(Level.DEBUG, "In Check, calling refresh conns & throwing DBNotAvailableException");
            config.releaseConnection();
            try { Thread.sleep(5000); } catch (Exception e) { }
            config.getConnectionPool().refreshConnections();
            logger.log(Level.WARN, "Check failed. Unable to connect to database.");
            throw new DBNotAvailableException(sqlEx.getMessage());
        } else {
            //logger.log(Level.DEBUG, "In Check, returning true");
        }
        //logger.log(Level.DEBUG, "Check returning :" + success);
        return success;
    }

    public boolean waitForReconnect(long timeout, long maxTries) throws InterruptedException {
        OracleAdapter config = null;
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter tmp = (OracleAdapter) dbAdapters.get(i);
            if (tmp.isActive()) {
                config = tmp;
                break;
            }
        }

        if (config == null) {
            throw new RuntimeException("No Database Adapters found");
        }
        if (config.getConnectionPool().isAvailable()) {
            return true;
        }

        int numTries = 0;
        while (true) {
            synchronized (reconnectLock) {
                reconnectLock.wait(timeout);
            }
            ++numTries;
            if (maxTries > 0) {
                if (numTries >= maxTries) {
                    return false;
                }
            }
            if (config.getConnectionPool().isAvailable()) {
                return true;
            }
        }
    }

    public void check(SQLException sqlEx) {
        OracleAdapter config = null;
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter tmp = (OracleAdapter) dbAdapters.get(i);
            if (tmp.isActive()) {
                config = tmp;
                break;
            }
        }

        if (config == null) {
            throw new RuntimeException("No active Oracle Adapters found");
        }
        synchronized (dbAdapters) {
            try {
                if (!config.isActive()) {
                    return;
                }
                boolean success = false;
                // Even if no-fatal error found, try getting connection to make sure....
                Connection conn = null;
                Statement stmt = null;
                try {
                    conn = config.getConnectionPool().getConnection();
                    // Check if the connection is usable
                    stmt = conn.createStatement();
                    stmt.executeQuery("select 10 from dual");
                    success = true;
                } catch (SQLException sqle) {
                    // Bad connection. No operation
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Exception e1) {
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Exception e1) {
                        }
                    }
                }
                if (!success) {
                    config.releaseConnection();
                    try { Thread.sleep(5000); } catch (Exception e) { }
                    config.getConnectionPool().refreshConnections();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean isAnyActive() {
        synchronized (dbAdapters) {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter other = (OracleAdapter) dbAdapters.get(i);
                if (other.isActive()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param key
     * @param value
     */
    public void store(Object key, Object value, Boolean exists) {
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                logger.log(Level.WARN, "BackingStore not enabled for %s ", this.cacheName);
                return;
            }

            if (this.m_isIgnore) {
                logger.log(Level.WARN, "Cache %s not registered for write behind", this.cacheName);
                return;
            }

            if (!this.getCacheClusterWriteMode()) {
                logger.log(Level.TRACE, "Cache %s WRITE MODE is OFF", this.cacheName);
                return;
            }

            if (!this.useObjectTable && isObjectTablePersister) {
                logger.log(Level.TRACE, "Cache %s OBJECT TABLE is OFF", this.cacheName);
                return;
            }

            if (rsp != null) {
                logger.log(Level.DEBUG, "Store(key,value) key=%s, value=%s", key, value);
            }

            if (!isAnyActive()) {
                logger.log(Level.WARN, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        long start = System.currentTimeMillis();
                        save(config, key, value, exists);
                        config.commit();
                        if (adStats.get(i) != null) {
                            adStats.get(i).incrNumStores();
                            adStats.get(i).incrTimeStores(System.currentTimeMillis()-start);
                        }
                    }
                } catch (DBConnectionsBusyException busyEx) {
                    throw new RuntimeException(busyEx);
                } catch (SQLException sqlex) {
                    check(sqlex, config, config.getCurrentConnection());
                    config.rollback(false);
                    throw sqlex;
                } catch (Exception ex) {
                    config.rollback(false);
                    throw ex;
                } finally {
                    if (config != null) {
                        config.releaseConnection();
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Error during store: %s", e, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void save(OracleAdapter config, Object key, Object value, Boolean exists) throws Exception, SQLException {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        logger.log(Level.DEBUG, "Save called. Key=%s, value=%s", key, value);
        try {
            if (value instanceof Concept) {
                if (this.getCacheClusterWriteMode()) {
                    Concept cept = (Concept) value;
                    if (((ConceptImpl) cept).isMarkedDeleted()) {
                        delete(config, cept);
                    } else {
                        boolean entityExists = (exists != null) ? exists.booleanValue() : config.entityExists(tableName, cept.getId(), partitionId.longValue());
                        if (entityExists) {
                            logger.log(Level.DEBUG, "Updating concept in %s [CID=%s] with id=%s, extId=%s",
                                    this.tableName, this.partitionId, cept.getId(), cept.getExtId());
                            if (cacheCluster.getClusterConfig().isNewSerializationEnabled()) {
                                if (cept instanceof StateMachineConcept) {
                                    config.updateStateMachine(tableName, (StateMachineConcept) cept);
                                } else {
                                    config.updateConcept(tableName, cept, partitionId.longValue());
                                }
                            } else {
                                config.updateConcept(tableName, cept, partitionId.longValue());
                            }
                        } else {
                            logger.log(Level.DEBUG, "Inserting concept in %s [CID=%s] with id=%s, extId=%s",
                                    this.tableName, this.partitionId, cept.getId(), cept.getExtId());
                            if (cacheCluster.getClusterConfig().isNewSerializationEnabled()) {
                                if (cept instanceof StateMachineConcept) {
                                    config.insertStateMachine(tableName, (StateMachineConcept) cept);
                                } else {
                                    config.insertConcept(tableName, cept, partitionId.longValue());
                                }
                            } else {
                                config.insertConcept(tableName, cept, partitionId.longValue());
                            }
                        }
                    }
                } else {
                    logger.log(Level.DEBUG, "Warning: Store flag is off - store(key,value) key=%s, value=", key, value);
                }
            } else if (value instanceof Event) {
                if (this.getCacheClusterWriteMode()) {
                    com.tibco.cep.kernel.model.entity.Event event = (com.tibco.cep.kernel.model.entity.Event) value;
                    logger.log(Level.DEBUG, "Inserting event in %s [CID=%s] with id=%s, extId=%s",
                            this.tableName, this.partitionId, event.getId(), event.getExtId());
                    if (!config.entityExists(tableName, event.getId(), partitionId.longValue())) {
                        config.insertEvent(tableName, event, partitionId.longValue());
                        //if (event instanceof StateMachineConceptImpl.StateTimeoutEvent) {
                        //    config.insertObjectTable(new EntityTupleImpl(event.getId(), null,StateMachineConceptImpl.StateTimeoutEvent.STATETIMEOUTEVENT_TYPEID));
                        //}
                        //config.commit();
                    }
                } else {
                    logger.log(Level.DEBUG, "Warning: Store flag is off - store(key,value) key=%s, value=%s", key, value);
                }
            } else if (value instanceof WorkTuple) {
                WorkTuple tuple = (WorkTuple) value;
                config.saveWorkItem(tuple);
            } else if (cacheType == CACHE_TYPE_OBJECTTABLE) {
                ObjectTupleImpl tuple = (ObjectTupleImpl)value;
                EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(tuple.getTypeId());
                if (!provider.getConfig().hasBackingStore()) {
                    logger.log(Level.DEBUG,
                            "###### SAVING OBJECTTABLE, IGNORE (BackingStore not set) for class=%s",
                            this.cacheCluster.getMetadataCache().getClass(tuple.getTypeId()));
                    return;
                }
                if (tuple.isDeleted()) {
                    logger.log(Level.DEBUG,
                            "###### SAVING OBJECTTABLE, MARKING AS DELETED %s, class=%s",
                            tuple.getId(), this.cacheCluster.getMetadataCache().getClass(tuple.getTypeId()));
                    config.updateObjectTable(tuple.getId());
                } else {
                    logger.log(Level.DEBUG,
                            "###### SAVING OBJECTTABLE, INSERTING ENTRY %s, class=%s",
                            tuple.getId(), this.cacheCluster.getMetadataCache().getClass(tuple.getTypeId()));
                    config.insertObjectTable(tuple);
                }
            } else if (cacheType == CACHE_TYPE_OBJECTEXTIDTABLE) {
                // Do nothing!!! Already done through CACHE_TYPE_OBJECTTABLE 
            }
        } catch (SQLException sqlex) {
            int ec = sqlex.getErrorCode();
            if (ec == ORACLE_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE) {
                // Silently ignore error ORA-00001: unique constraint exception.
                // Caches OBJECTTABLE and OBJECTEXTIDTABLE are both
                // mapped to the same table, and this causes the exception.
                return;
            }
            logger.log(Level.ERROR, sqlex, "SQL Error in save [%s = %s]. %s", key, value, sqlex.getMessage());
            check(sqlex, config, config.getCurrentConnection());
            throw sqlex;
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Error in save [%s = %s]. %s", key, value, ex.getMessage());
            throw ex;
        } finally {
        }
    }

    private Object getById(OracleAdapter config, Object key) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        if (this.m_isIgnore) {
            return null;
        }

        long t1 = System.nanoTime();
        for (int retryCount = 0; retryCount < MAX_RETRY_COUNT; retryCount++) {
            try {
                if (cacheType == CACHE_TYPE_CONCEPT) {
                    if (isObjectCacheFullyLoaded) {
                        return null;
                    }
                    com.tibco.cep.runtime.model.element.Concept cept = config.getConceptById(tableName, ((Long) key).longValue(), partitionId.longValue());
                    if (cept != null) {
                        logger.log(Level.DEBUG, "GetById done. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    } else {
                        logger.log(Level.DEBUG, "GetById - No matching record. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    }
                    config.releaseConnection();
                    return cept;
                } else if (cacheType == CACHE_TYPE_STATEMACHINE) {
                    if (isObjectCacheFullyLoaded) {
                        return null;
                    }
                    StateMachineConcept sm = config.getStateMachineById(tableName, ((Long) key).longValue(), partitionId.longValue());
                    if (sm != null) {
                        logger.log(Level.DEBUG, "GetById done. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    } else {
                        logger.log(Level.DEBUG, "GetById - No matching record. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    }
                    config.releaseConnection();
                    return sm;
                } else if (cacheType == CACHE_TYPE_EVENT) {
                    if (isObjectCacheFullyLoaded) {
                        return null;
                    }
                    com.tibco.cep.kernel.model.entity.Event event = config.getEventById(tableName, ((Long) key).longValue(), partitionId.longValue());
                    if (event != null) {
                        logger.log(Level.DEBUG, "GetById done. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    } else {
                        logger.log(Level.DEBUG, "GetById - No matching record. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    }
                    config.releaseConnection();
                    return event;
                } else if (this.isScheduler) {
                    WorkTuple tuple = config.getWorkItem((String) key);
                    if (tuple != null) {
                        logger.log(Level.DEBUG, "GetById done. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    } else {
                        logger.log(Level.DEBUG, "GetById - No matching record. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                    }
                    config.releaseConnection();
                    return tuple;
                } else if (cacheType == CACHE_TYPE_OBJECTTABLE) {
                    if (isObjectCacheFullyLoaded) {
                        logger.log(Level.DEBUG, "GetById (%s) - Ignored with ObjectCacheFullyLoaded flag", key);
                        return null;
                    }
                    ObjectTupleImpl tuple = null;
                    if (cacheCluster == null) {
                        tuple = config.getObjectTable((Long) key);
                    } else if (this.useObjectTable) {
                        tuple = config.getObjectTable((Long) key);
                    } else {
                        long keyLong = ((Long) key).longValue();
                        int typeId = IDEncoder.decodeTypeId(keyLong);
                        if (!cacheCluster.getMetadataCache().isValidTypeId(typeId)) {
                            logger.log(Level.DEBUG, "GetById (%s) - Ignored with Invalid TypeId=%s", key, typeId);
                            return null;
                        }
                        Class clz = cacheCluster.getMetadataCache().getClass(typeId);
                        if (clz != null) {
                        	tuple = config.getBaseTable(clz.getName(), keyLong);
                    	}
                    }
                    if (tuple != null) {
                        logger.log(Level.DEBUG, "GetById done. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                        // Do not need to serialize it because the cache is expecting a tuple
                        //return ObjectTupleImpl.serialize(tuple);
                        return tuple;
                    } else {
                        logger.log(Level.DEBUG, "GetById - No matching record. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                        return null;
                    }
                } else if (cacheType == CACHE_TYPE_OBJECTEXTIDTABLE) {
                    if (isObjectCacheFullyLoaded) {
                        logger.log(Level.DEBUG, "GetById (%s) - Ignored with ObjectCacheFullyLoaded flag", key);
                        return null;
                    }
                    ObjectTupleImpl tuple = null;
                    if (this.useObjectTable) {
                        tuple = config.getObjectTable((String) key);
                    } else {
                        String uri = (String) cacheCluster.getObjectTableCache().getURI((String) key);
                        if (uri != null) {
                            TypeDescriptor td = rsp.getTypeManager().getTypeDescriptor(uri);
                            if (td != null) {
                                Class clz = td.getImplClass();
                                tuple = config.getTupleByType((String) key, clz);
                            } else {
                                tuple = searchTupleInAllTypes(config, (String) key);
                            }
                        } else {
                            tuple = searchTupleInAllTypes(config, (String) key);
                        }
                    }
                    if (tuple != null) {
                        logger.log(Level.DEBUG, "GetById done. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                        // Do not need to serialize it because the cache is expecting a tuple
                        //return ObjectTupleImpl.serialize(tuple);
                        return tuple;
                    } else {
                        logger.log(Level.DEBUG, "GetById - No matching record. id=%s, table=%s time(ms)=%d", key, this.tableName, ((System.nanoTime()-t1)/(1000 * 1000)));
                        return null;
                    }
                }
            } catch (SQLException sqlex) {
                String msg = "Attempt " + retryCount + ", failed to get " + 
                    (cacheType == CACHE_TYPE_CONCEPT ? "Concept" : "Object") + " of id=" + key +
                    " error=" + sqlex.getMessage();
                if (retryCount >= (MAX_RETRY_COUNT - 1)) {
                    msg += ", give up...";
                    logger.log(Level.ERROR, msg);
                    throw sqlex;
                } else {
                    msg += ", try again...";
                    logger.log(Level.WARN, msg);
                }
            } finally {
                if (config != null) {
                    config.releaseConnection();
                }
            }
        }
        return null;
    }

    private void delete(OracleAdapter config, Object key) throws SQLException, Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (cacheType == CACHE_TYPE_OBJECTEXTIDTABLE) {
            return;
        }
        logger.log(Level.DEBUG, "Delete called. %s %s", key.getClass(), key);
        try {
            if (cacheType == CACHE_TYPE_METADATA) {
            } else if (cacheType == CACHE_TYPE_CONCEPT) {
                config.deleteEntityById(tableName, ((Long) key).longValue(), partitionId.longValue(), className);
            } else if (cacheType == CACHE_TYPE_EVENT) {
                config.deleteEntityById(tableName, ((Long) key).longValue(), partitionId.longValue(), className);
            } else if (this.isScheduler) {
                config.removeWorkItem((String) key);
            } else if (cacheType == CACHE_TYPE_OBJECTTABLE) {
                config.deleteObjectTable((Long) key);
            }
        } catch (SQLException sqlex) {
            logger.log(Level.ERROR, sqlex, "SQL Error in delete [key = %s]. %s", key, sqlex.getMessage());
            check(sqlex, config, config.getCurrentConnection());
            throw sqlex;
        }
    }

    public void storeAll(Map map) {
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                logger.log(Level.DEBUG, "BackingStore not enabled for %s", this.cacheName);
                return;
            }

            if (this.m_isIgnore) {
                logger.log(Level.DEBUG, "Cache %s not registered for write behind", this.cacheName);
                return;
            }

            if (!this.getCacheClusterWriteMode()) {
                logger.log(Level.DEBUG, "Cache %s WRITE MODE is OFF", this.cacheName);
                return;
            }

            if (!this.useObjectTable && isObjectTablePersister) {
                logger.log(Level.DEBUG, "Cache %s OBJECT TABLE is OFF", this.cacheName);
                return;
            }

            if (rsp != null) {
                logger.log(Level.DEBUG, "%s StoreAll map=%s", this.cacheName, map);
            }

            if (!isAnyActive()) {
                logger.log(Level.WARN, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                if (config.isActive()) {
                    try {
                        int num_p = 0;
                        Iterator allValues = map.entrySet().iterator();
                        while (allValues.hasNext()) {
                            Map.Entry entry = (Map.Entry) allValues.next();
                            long start = System.currentTimeMillis();
                            save(config, entry.getKey(), entry.getValue(), null);
                            ++num_p;
                            if (num_p == commitBatchSize) {
                                config.commit();
                                num_p = 0;
                            }
                            if (adStats.get(i) != null) {
                                adStats.get(i).incrNumStores();
                                adStats.get(i).incrTimeStores(System.currentTimeMillis() - start);
                            }
                        }
                        if (num_p > 0) {
                            config.commit();
                            num_p = 0;
                        }
                    }
                    catch (SQLException ex) {
                        check(ex, config, config.getCurrentConnection());
                        config.rollback(false);
                        throw ex;
                    }
                    catch (Exception ex) {
                        config.rollback(false);
                        throw ex;
                    } finally {
                        config.releaseConnection();
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed storing-all key=%s", e, map);
            throw new RuntimeException(e);
        }
    }

    public void erase(Object object) {
        try {
            if (this.m_isIgnore) {
                return;
            }

            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return;
            }

            if (!this.getCacheClusterWriteMode()) {
                logger.log(Level.DEBUG, "%s [Write mode is 'off']", this.cacheName);
                return;
            }

            if (cacheType == CACHE_TYPE_OBJECTEXTIDTABLE) {
                return;
            }

            logger.log(Level.DEBUG, "%s [ %s ] Erase called. %s", this.cacheName, this.tableName, object);

            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        long start = System.currentTimeMillis();
                        delete(config, object);
                        config.commit();
                        if (adStats.get(i) != null) {
                            adStats.get(i).incrNumErase();
                            adStats.get(i).incrTimeErase(System.currentTimeMillis() - start);
                        }
                    }
                } catch (SQLException sqlex) {
                    if (check(sqlex, config, config.getCurrentConnection())) {
                        config.rollback(false);
                    }
                    throw sqlex;
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed erase key=%s", e, object);
            throw new RuntimeException(e);
        }
    }

    public void eraseAll(Collection collection) {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }

        if (this.m_isIgnore) {
            return;
        }

        if (!this.getCacheClusterWriteMode()) {
            return;
        }

        if (cacheType == CACHE_TYPE_OBJECTEXTIDTABLE) {
            return;
        }

        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        try {
            if (WARNING_THRESHOLD < collection.size()) {
                logger.log(Level.WARN, "Store[%d] exceeds the threshold limit. Decrease the write-queue delay.", collection.size());
            }

            if ((cacheType == CACHE_TYPE_CONCEPT) || (cacheType == CACHE_TYPE_EVENT) || (cacheType == CACHE_TYPE_OBJECTTABLE) || isScheduler) {
                if (collection != null && collection.size() > 0) {
                    logger.log(Level.DEBUG, "Batch deleting concepts in %s [CID=%s] with size=%s", this.tableName, this.partitionId, collection.size());
                    for (int i = 0; i < dbAdapters.size(); i++) {
                        OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                        try {
                            if (config.isActive()) {
                                long start = System.currentTimeMillis();
                                if (!isScheduler) {
                                    if ((cacheType == CACHE_TYPE_CONCEPT) || (cacheType == CACHE_TYPE_EVENT)) {
                                        config.deleteEntitiesInBatch(tableName, collection, partitionId.longValue());
                                        config.commit();
                                    } else if (cacheType == CACHE_TYPE_OBJECTTABLE) {
                                        int num_p = 0;
                                        Iterator all = collection.iterator();
                                        while (all.hasNext()) {
                                            config.deleteObjectTable((Long) all.next());
                                            ++num_p;
                                            if (num_p == commitBatchSize) {
                                                config.commit();
                                            }
                                        }
                                        if (num_p > 0) {
                                            config.commit();
                                        }
                                    }
                                    if (adStats.get(i) != null) {
                                        adStats.get(i).incrNumErase(collection.size());
                                        adStats.get(i).incrTimeErase(System.currentTimeMillis() - start);
                                    }
                                } else {
                                    if (deleteInBatch) {
                                        config.removeWorkItemsInBatch(collection);
                                    } else {
                                        config.removeWorkItems(collection);
                                    }
                                    config.commit();
                                }
                            }
                        } catch (SQLException sqlex) {
                            sqlex.printStackTrace();
                            if (check(sqlex, config, config.getCurrentConnection())) {
                                config.rollback(false);
                            }
                            throw sqlex;
                        } catch (Exception ex) {
                            config.rollback(false);
                            throw new RuntimeException(ex);
                        } finally {
                            config.releaseConnection();
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed erase-all key=%s", e, collection);
            throw new RuntimeException(e);
        }
    }

    public Object load(Object object) {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        if (!isAnyActive()) {
            logger.log(Level.WARN, this.className + " has no active connections to load %s.", object);
            throw new RuntimeException(this.className + " has no active connections");
        }

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    long start = System.currentTimeMillis();
                    Object ret = getById(config, object);
                    if (adStats.get(i) != null) {
                        adStats.get(i).incrNumLoads();
                        adStats.get(i).incrTimeLoads(System.currentTimeMillis() - start);
                    }
                    if (ret != null) {
                        return ret;
                    }
                }
            }
            catch (DBConnectionsBusyException busyEx) {
                logger.log(Level.WARN, "DBConnectionsBusyException in load() of : " + object + ", Message: " + busyEx.getMessage());
                throw new RuntimeException(DBException.DatabaseException_MESSAGE, busyEx); // Change#23699
            }
            catch (DBNotAvailableException dbnaEx) {
                logger.log(Level.WARN, "DBNotAvailableException in load() of : " + object + ", Message: " + dbnaEx.getMessage());
                throw dbnaEx;
            }
            catch (SQLException sqlex) {
                logger.log(Level.WARN, "SQLException in load() of : " + object + ", Message: " + sqlex.getMessage());
                check(sqlex, config, config.getCurrentConnection());
                throw new DBException(); // Change#23699
            }
            catch (Exception e) {
                logger.log(Level.WARN, e, "Exception in load() of : " + object + ", Message: " + e.getMessage());
                throw new RuntimeException(e);
            } finally {
                config.releaseConnection();
            }
        }
        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    public Iterator objectSet() throws Exception {
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return null;
            }
            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                if (config.isActive()) {
                    try {
                        if (cacheType == CACHE_TYPE_CONCEPT) {
                            return config.getConcepts(tableName, partitionId.longValue());
                        } else if (cacheType == CACHE_TYPE_EVENT) {
                            return config.getEvents(tableName, partitionId.longValue());
                        } else {
                            throw new Exception("Database is not configured properly");
                        }
                    } catch (SQLException sqlex) {
                        check(sqlex, config, config.getCurrentConnection());
                        throw sqlex;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed object-set loading", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     *
     */
    public Iterator keySet() throws Exception {
        return null;
    }

    /**
     * @throws Exception
     */
    public long loadKeys(int typeId, ObjectTable objectTable) throws Exception {
        logger.log(Level.INFO, "Recovering %s from database, TypeID=%s", this.cacheName, typeId);
        long minEntityId = 0L;
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return 0L;
            }
            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            int i = 0;
            ObjectTable.ElementWriter writer = null;
            EntityDao thisCache = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (ConceptImpl.class.isAssignableFrom(thisCache.getEntityClass())) {
                writer = objectTable.createElementWriter();
            }
            for (int j = 0; j < dbAdapters.size(); j++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
                if (config.isActive()) {
                    if ((cacheType == CACHE_TYPE_CONCEPT) || (cacheType == CACHE_TYPE_EVENT)) {
                        try {
                            Iterator allKeys = config.keyPairs(tableName, partitionId.longValue());
                            while (allKeys.hasNext()) {
                                ObjectTupleImpl kt = (ObjectTupleImpl) allKeys.next();
                                kt.setTypeId(typeId);
                                writer.addObject(kt);
                                if ((++i % 10000) == 0) {
                                    writer.commit();
                                }
                                if (kt.getId() > minEntityId) {
                                    minEntityId = kt.getId();
                                }
                            }
                            writer.commit();
                            logger.log(Level.INFO, "Recovered %s from database, thread=%s", this.cacheName, Thread.currentThread());
                        } catch (SQLException sqlex) {
                            check(sqlex, config, config.getCurrentConnection());
                            throw sqlex;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed load-keys", e);
            throw new RuntimeException(e);
        }
        return minEntityId;
    }

    public long loadKeys(int typeId, ObjectTable objectTable, EventTable eventQueue) throws Exception {
        logger.log(Level.INFO, "Recovering %s from database, TypeID=%s", this.cacheName, typeId);
        long minEntityId = 0L;
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return 0L;
            }
            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            int i = 0;
            ObjectTable.ElementWriter writer = null;
            EventTable.EventWriter eventWriter = null;
            if (eventQueue != null) {
                eventWriter = eventQueue.createEventWriter();
            }

            EntityDao thisCache = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (ConceptImpl.class.isAssignableFrom(thisCache.getEntityClass())) {
                writer = objectTable.createElementWriter();
            }

            for (int j = 0; j < dbAdapters.size(); j++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
                if (config.isActive()) {
                    if ((cacheType == CACHE_TYPE_CONCEPT) || (cacheType == CACHE_TYPE_EVENT)) {
                        try {
                            Iterator allKeys = config.keyPairs(tableName, partitionId.longValue());
                            while (allKeys.hasNext()) {
                                ObjectTupleImpl kt = (ObjectTupleImpl) allKeys.next();
                                // Suresh TODO : Why will it not have the typeId info
                                kt.setTypeId(typeId);
                                writer.addObject(kt);
                                if (eventWriter != null) {
                                    eventWriter.addEvent(new EventTuple(kt.getId()));
                                }
                                if ((++i % 10000) == 0) {
                                    writer.commit();
                                    eventWriter.commit();
                                }
                                if (kt.getId() > minEntityId) {
                                    minEntityId = kt.getId();
                                }
                            }
                            writer.commit();
                            if (eventWriter != null) {
                                eventWriter.commit();
                            }
                            logger.log(Level.INFO, "Recovered %s from database, thread=%s", this.cacheName, Thread.currentThread());
                        } catch (SQLException sqlex) {
                            check(sqlex, config, config.getCurrentConnection());
                            throw sqlex;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed load-keys", e);
            throw new RuntimeException(e);
        }
        return minEntityId;
    }

    public long getNumEntities(int typeId, long maxCount) throws Exception {
        long count = maxCount;
        for (int j=0; j < dbAdapters.size(); j++) {
            OracleAdapter config= (OracleAdapter) dbAdapters.get(j);
            if (config.isActive()) {
                try {
                    count = config.countEntities(typeId);
                } catch (SQLException sqlex) {
                    sqlex.printStackTrace();
                } finally {
                    config.releaseConnection();
                }
            }
        }
        if ((0 < maxCount) && (maxCount < count)) {
            return maxCount;
        }
        return count;
    }

    /**
     * This method is called during recovery to populate object-tables
     */
    public long recoverObjectTable(int typeId, long maxRows) throws Exception {
        NumberFormat numf = NumberFormat.getInstance();
        String clzName = cacheCluster.getMetadataCache().getClass(typeId).getName();
        logger.log(Level.INFO, "Recovering ObjectTable from database, class=%s", clzName);
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return 0L;
            }
            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            long recoveredRows = 0;
            long total = 0;
            long start = System.currentTimeMillis();
            long current;
            long last = 0;
            long totalDbFetchTime = 0;
            long totalCacheWriteTime = 0;

            ObjectTable.ElementWriter writer = cacheCluster.getObjectTableCache().createElementWriter();
            last = start;

            for (int j = 0; j < dbAdapters.size(); j++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
                if (config.isActive()) {
                    try {
                        Iterator allKeys = config.recoverType(cacheCluster.getClusterConfig().getSiteId(), typeId, 0);
                        current = System.currentTimeMillis();
                        logger.log(Level.INFO, "Recovering ObjectTable from database, class=%s, db query time=%dms", clzName, (current - last));
                        last = current;
                        totalDbFetchTime = 0;
                        totalCacheWriteTime = 0;
                        while (allKeys.hasNext()) {
                            ObjectTupleImpl kt = (ObjectTupleImpl) allKeys.next();
                            writer.addObject(kt);
                            if ((++recoveredRows % preloadBatchSize) == 0) {
                                current = System.currentTimeMillis();
                                totalDbFetchTime += (current - last);
                                last = current;
                                writer.commit();
                                current = System.currentTimeMillis();
                                totalCacheWriteTime += (current - last);
                                last = current;
                            }
                        }
                        current = System.currentTimeMillis();
                        totalDbFetchTime += (current - last);
                        last = current;
                        writer.commit();
                        current = System.currentTimeMillis();
                        totalCacheWriteTime += (current - last);
                        last = current;
                        total = System.currentTimeMillis() - start;
                        String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                        logger.log(Level.INFO, "Recovered %s from database, handles loaded=%s, time=%s (dbfetchtime=%dms, cachewritetime=%dms)", clzName, numf.format(recoveredRows), totalStr, totalDbFetchTime, totalCacheWriteTime);
                        return recoveredRows;
                    } catch (SQLException sqlex) {
                        sqlex.printStackTrace();
                        check(sqlex, config, config.getCurrentConnection());
                        throw sqlex;
                    } finally {
                        config.releaseConnection();
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed recover-object-table", e);
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public long loadWorkitems(String workQueue, Map targetCache) {
        logger.log(Level.INFO, "Loading work items from database for queue= %s", workQueue);
        for (int j = 0; j < dbAdapters.size(); j++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
            if (config.isActive()) {
                try {
                    return config.loadWorkItems(workQueue, targetCache);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return 0;
    }
    
    /**
     * This method is called during preload to directly populate entity cache
     */
    public long loadObjects(int typeId, long maxRows, boolean loadHandles, boolean loadEntities) throws Exception {
        Class entityClz = cacheCluster.getMetadataCache().getClass(typeId);
        logger.log(Level.INFO, "Loading objects from database, class= %s", entityClz.getName());
        for (int j = 0; j < dbAdapters.size(); j++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
            if (config.isActive()) {
                try {
                    Iterator itr = null;
                    long start = System.currentTimeMillis();
                    if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
                        itr = config.loadConcepts(typeId);
                    } else if (Concept.class.isAssignableFrom(entityClz)) {
                        itr = config.loadConcepts(typeId);
                    } else {
                        itr = config.loadEvents(typeId);
                    }
                    return loadObjects(typeId, maxRows, entityClz, itr, start, loadHandles, loadEntities);
                } catch (SQLException sqlex) {
                    check(sqlex, config, config.getCurrentConnection());
                    throw sqlex;
                }
            }
        }
        return 0;
    }

    /**
     * This is called from InferenceEngine during recovery of handles for C+M entities
     */
    public Iterator getObjects(int typeId) throws Exception {
        Class entityClz = cacheCluster.getMetadataCache().getClass(typeId);
        logger.log(Level.INFO, "Loading objects from database, class=%s", entityClz.getName());
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return null;
            }
            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            EntityDao cacheProvider = cacheCluster.getMetadataCache().getEntityDao(typeId);

            if (cacheProvider == null) {
                throw new RuntimeException("Provider for " + entityClz.getName() + " not found in the cluster");
            }

            for (int j = 0; j < dbAdapters.size(); j++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
                if (config.isActive()) {
                    try {
                        if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
                            return config.loadStateMachines(typeId);
                        } else if (Concept.class.isAssignableFrom(entityClz)) {
                            return config.loadConcepts(typeId);
                        } else {
                            return config.loadEvents(typeId);
                        }
                    } catch (SQLException sqlex) {
                        check(sqlex, config, config.getCurrentConnection());
                        throw sqlex;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed get-objects", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public long getNumHandles() throws Exception {
        try {
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return 0L;
            }
            if (!isAnyActive()) {
                logger.log(Level.DEBUG, "No active connections.");
                throw new RuntimeException("No active connections");
            }

            for (int j = 0; j < dbAdapters.size(); j++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
                if (config.isActive()) {
                    try {
                        if ((cacheType == CACHE_TYPE_CONCEPT) || (cacheType == CACHE_TYPE_EVENT)) {
                            long numHandles = config.countKeyPairs(tableName, partitionId.longValue());
                            config.releaseConnection();
                            return numHandles;
                        }
                    } catch (SQLException sqlex) {
                        check(sqlex, config, config.getCurrentConnection());
                        throw sqlex;
                    } finally {
                        config.releaseConnection();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public Map loadAll(Collection keys) {
        try {
            if (!initialized) {
                initialize(true, false);
            }
            if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cacheType == CACHE_TYPE_RECOVERY) {
            return null;
        }

        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if ((cacheType == CACHE_TYPE_METADATA) || (cacheType == CACHE_TYPE_CACHEIDGENERATOR)) {
                    Iterator it = keys.iterator();
                    HashMap ret = new HashMap();
                    long start = System.currentTimeMillis();
                    while (it.hasNext()) {
                        Object key = it.next();
                        Object r = load(key);
                        if (r != null) {
                            ret.put(key, r);
                        }
                    }
                    if (adStats.get(i) != null) {
                        adStats.get(i).incrNumLoads();
                        adStats.get(i).incrTimeLoads(System.currentTimeMillis() - start);
                    }
                    return ret;
                } else if (cacheType == CACHE_TYPE_CONCEPT) {
                    long start = System.currentTimeMillis();
                    Map result = getByIds(config, keys);
                    if (adStats.get(i) != null) {
                        adStats.get(i).incrNumLoads(keys.size());
                        adStats.get(i).incrTimeLoads(System.currentTimeMillis() - start);
                    }
                    return result;
                } else if (cacheType == CACHE_TYPE_EVENT) {
                    long start = System.currentTimeMillis();
                    Map result = getByIds(config, keys);
                    if (adStats.get(i) != null) {
                        adStats.get(i).incrNumLoads(keys.size());
                        adStats.get(i).incrTimeLoads(System.currentTimeMillis() - start);
                    }
                    return result;
                }
            } catch (DBNotAvailableException bde) {
                //e.printStackTrace();
                //logger.log(Level.DEBUG, "Got DBNA-Exc in OracleStore loadAll()");
                throw bde;
            }
            catch (SQLException sqlex) {
                //logger.log(Level.DEBUG, "Got SQL-Exc in OracleStore.loadAll():" + sqlex.getMessage());
                check(sqlex, config, config.getCurrentConnection());
                throw new RuntimeException(sqlex);
            }
            catch (Exception e) {
                //logger.log(Level.DEBUG, "Got Exc in OracleStore.loadAll():" + e.getMessage());
                //e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                config.releaseConnection();
            }
        }
        return null;
    }

    private Map getByIds(OracleAdapter config, Collection keys) throws Exception {
        if (!initialized) {
            initialize(true, false);
        }
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        try {
            if (cacheType == CACHE_TYPE_CONCEPT) {
                Map concepts = config.getConceptByIds(tableName, keys, partitionId.longValue());
                HashMap conceptAdapters = new HashMap();
                Iterator keyIterator = concepts.keySet().iterator();
                while (keyIterator.hasNext()) {
                    Object key = keyIterator.next();
                    com.tibco.cep.runtime.model.element.Concept cept = (com.tibco.cep.runtime.model.element.Concept) concepts.get(key);
                    if (cept != null) {
                        //conceptAdapters.put(key, new ConceptAdapter((ObjectCache)getParentCache(), cept, true));
                        conceptAdapters.put(key, cept);
                    } else {
                        //conceptAdapters.put(key, null);
                    }
                }
                return conceptAdapters;
            } else if (cacheType == CACHE_TYPE_EVENT) {
                Map events = config.getConceptByIds(tableName, keys, partitionId.longValue());
                HashMap eventAdapters = new HashMap();
                Iterator keyIterator = events.keySet().iterator();
                while (keyIterator.hasNext()) {
                    Object key = keyIterator.next();
                    com.tibco.cep.kernel.model.entity.Event event = (com.tibco.cep.kernel.model.entity.Event) events.get(key);
                    if (event != null) {
                        eventAdapters.put(key, event);
                    } else {
                        //eventAdapters.put(key, null);
                    }
                }
                return eventAdapters;
            } else {
                throw new Exception("Unknown Type " + cacheType);
            }
        }
        catch (Exception e) {
            try {
                if (config != null)
                    config.rollback(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            throw e;
        } finally {
            config.releaseConnection();
        }
    }

    public void setWriteMode(boolean on) {
        Level level = Level.INFO;
        if (writeMode == on) {
            level = Level.DEBUG;
        }
        writeMode = on;
        if (writeMode) {
            logger.log(level, "========== WRITE MODE IS ON ==========");
        } else {
            logger.log(level, "========== WRITE MODE IS OFF ==========");
        }
    }

    public long getMaxEntityId() throws Exception {
        if (!initialized) {
            initialize(true, false);
        }
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return 0L;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }
        long maxEntityId = 0L;

        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    if (this.useObjectTable) {
                        maxEntityId = config.getMaxEntityId(cacheCluster.getClusterConfig().getSiteId());
                    } else {
                        maxEntityId = config.getMaxEntityAcrossTypes(cacheCluster.getClusterConfig().getSiteId());
                    }
                    return maxEntityId;
                }
            } catch (Exception ex) {
                if (i == (dbAdapters.size() - 1)) {
                    logger.log(Level.WARN, ex, "Exception in Query processing.");
                    throw ex;
                }
            } finally {
                config.releaseConnection();
            }
        }
        return maxEntityId;
    }

    public ResultSetCache query(String query, Object[] args) throws Exception {
        if (!initialized) {
            initialize(true, false);
        }
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        logger.log(Level.DEBUG, "Query registered: %s", query);
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    return config.query(query, args);
                }
            } catch (Exception ex) {
                if (i == (dbAdapters.size() - 1)) {
                    logger.log(Level.WARN, ex, "Exception in Query processing.");
                    throw ex;
                }
            } finally {
                config.releaseConnection();
            }
        }
        throw new RuntimeException("UKNOWN Exception in Query processing " + query);
    }

    public void saveClassRegistry(Map classRegistry) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        // This is a temporary place for making this call.
        //DBManager.getInstance().initProcessTables();
        logger.log(Level.DEBUG, "Saving class registry to backing store.");
        try {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        config.saveClassRegistry(classRegistry);
                        config.commit();
                    }
                } catch (Exception ex) {
                    if (config != null) {
                        config.rollback(false);
                    }
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Saving ClassRegistry failed: %s", ex.getMessage());
            throw ex;
        }
    }

    public void saveClassRegistration(String className, int typeId) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        // This is a temporary place for making this call.
        //DBManager.getInstance().initProcessTables();
        logger.log(Level.DEBUG, "Saving class registration to backing store.");
        try {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        config.saveClassRegistration(className, typeId);
                        config.commit();
                    }
                } catch (Exception ex) {
                    if (config != null) {
                        config.rollback(false);
                    }
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Saving class registration failed: %s", ex.getMessage());
            throw ex;
        }
    }
    
    public LinkedHashMap getClassRegistry() throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return null;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        logger.log(Level.DEBUG, "Loading class registry from backing store.");
        try {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        LinkedHashMap classRegistry = config.getClassRegistry();
                        return classRegistry;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Reading ClassRegistry failed: %s", ex.getMessage());
            throw ex;
        }
        return null;
    }

    public void clear() throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        logger.log(Level.DEBUG, "Deleting deleted entities from ObjectTable table.");
        try {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        Class[] allTypes = cacheCluster.getMetadataCache().getRegisteredTypes();
                        for (int j = 0; j < allTypes.length; j++) {
                            if (allTypes[j] != null) {
                                String tableName = config.generatedOracleTableName(allTypes[j].getName());
                                if (tableName != null) {
                                    logger.log(Level.INFO, "TRUNCATE %s ...", tableName);
                                    config.truncate_entityTable(tableName);
                                }
                            }
                        }
                        logger.log(Level.INFO, "TRUNCATE System Tables ...");
                        config.truncate_systemTables();
                        config.commit();
                    }
                } catch (Exception ex) {
                    config.rollback(false);
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Deleting entities failed: %s", ex.getMessage());
            throw ex;
        }
    }

    public void cleanup() throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        logger.log(Level.DEBUG, "Deleting deleted entities from ObjectTable table.");
        try {
            long subtract = ((BEProperties) cacheCluster.getRuleServiceProvider().getProperties()).getLong("be.backingstore.purge.time", 1000 * 60L);
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        config.purgeDeletedObjects(System.currentTimeMillis() - subtract);
                        config.commit();
                    }
                } catch (Exception ex) {
                    config.rollback(false);
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Deleting entities failed: %s", ex.getMessage());
            throw ex;
        }
    }

    private void insertConcepts(OracleAdapter config, Map<Integer, Map<Long, Concept>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Concept>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (provider.getConfig().hasBackingStore()) {
                String className = provider.getEntityClass().getName();
                Map<Long, Concept> txnMap = entry.getValue();
                if (cacheCluster.getClusterConfig().isNewSerializationEnabled()) {
                    if (StateMachineConcept.class.isAssignableFrom(provider.getEntityClass())) {
                        config.insertStateMachines(className, txnMap);
                    } else {
                        config.insertConcepts(className, txnMap);
                    }
                } else {
                    config.insertConcepts(className, txnMap);
                }
            }
        }
    }

    private void modifyConcepts(OracleAdapter config, Map<Integer, Map<Long, Concept>> entries) throws Exception {
        // Modify concepts as needed
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Concept>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (provider.getConfig().hasBackingStore()) {
                Map<Long, Concept> txnMap = (Map) entry.getValue();
                String className = provider.getEntityClass().getName();
                if (cacheCluster.getClusterConfig().isNewSerializationEnabled()) {
                    if (StateMachineConcept.class.isAssignableFrom(provider.getEntityClass())) {
                        config.modifyStateMachines(className, txnMap);
                    } else {
                        config.modifyConcepts(className, txnMap);
                    }
                } else {
                    config.modifyConcepts(className, txnMap);
                }
            }
        }
    }

    private void deleteConcepts(OracleAdapter config, Map<Integer, Set<Long>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Set<Long>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (provider.getConfig().hasBackingStore()) {
                Set<Long> txnMap = entry.getValue();
                String className = provider.getEntityClass().getName();
                String tableName = config.generatedOracleTableName(className);
                if (deleteInBatch) {
                    config.deleteEntitiesInBatch(tableName, txnMap);
                }
                else {
                    config.deleteEntities(tableName, txnMap);
                }
            }
        }
    }

    private void deleteEvents(OracleAdapter config, Map<Integer, Set<Long>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Set<Long>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (provider.getConfig().hasBackingStore()) {
                Set<Long> txnMap = entry.getValue();
                String className = provider.getEntityClass().getName();
                String tableName = config.generatedOracleTableName(className);
                if (deleteInBatch) {
                    config.deleteEntitiesInBatch(tableName, txnMap);
                }
                else {
                    config.deleteEntities(tableName, txnMap);
                }
            }
        }
    }

    private void insertEvents(OracleAdapter config, Map<Integer, Map<Long, Event>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Event>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(typeId);
            if (provider.getConfig().hasBackingStore()) {
                Map<Long, Event> txnMap = (Map) entry.getValue();
                String className = provider.getEntityClass().getName();
                String tableName = config.generatedOracleTableName(className);
                config.insertEvents(className, tableName, txnMap);
            }
        }
    }

    private void saveObjectTable(OracleAdapter config, Map<Long, ObjectTable.Tuple> entries) throws Exception {
        if (entries.size() > 0) {
            if (this.useObjectTable) {
                config.saveObjectTable(entries);
            }
        }
    }

    private void removeObjectTable(OracleAdapter config, Set<Long> entries) throws Exception {
        if (entries.size() > 0) {
            if (this.useObjectTable) {
                if (deleteInBatch) {
                	config.removeObjectTableInBatch(entries);
                }
                else {
                	config.removeObjectTable(entries);
                }
            }
        }
    }

    public void removeEntities(Class entityClz, Set<Long> entries) throws Exception {
    	logger.log(Level.INFO, "Removing %s entities [%s]", entityClz.getName(), entries.size());
        for (int i = 0; i < dbAdapters.size(); i++) {
        	OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    String tableName = config.generatedOracleTableName(entityClz.getName());
                    
                    config.deleteEntitiesInBatch(tableName, entries);

                	logger.log(Level.INFO, "Removing %s objecttable [%s]", entityClz.getName(), entries.size());
                    removeObjectTable(config, entries);
                    
                	config.commit();
                }
            }
            catch (DBConnectionsBusyException busyEx) {
                throw busyEx;
            }
        }
    	logger.log(Level.INFO, "Removing %s entities [%s] completed", entityClz.getName(), entries.size());
    }
    
    private void saveTransaction(OracleAdapter config, RtcTransaction txn) throws Exception {
        // Concepts and StateMachines
        if (txn.getDeletedConcepts().isEmpty() == false) {
            deleteConcepts(config, txn.getDeletedConcepts());
        }
        if (txn.getAddedConcepts().isEmpty() == false) {
            insertConcepts(config, txn.getAddedConcepts());
        }
        if (txn.getModifiedConcepts().isEmpty() == false) {
            modifyConcepts(config, txn.getModifiedConcepts());
        }
        // Events
        if (txn.getDeletedEvents().isEmpty() == false) {
            deleteEvents(config, txn.getDeletedEvents());
        }
        if (txn.getAddedEvents().isEmpty() == false) {
            insertEvents(config, txn.getAddedEvents());
        }
        // Common ObjectTable
        if (txn.getDBObjectTable_delete().isEmpty() == false) {
            removeObjectTable(config, txn.getDBObjectTable_delete());
        }
        if (txn.getDBObjectTable_save().isEmpty() == false) {
            saveObjectTable(config, txn.getDBObjectTable_save());
        }
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG, "SaveTransaction:"
                    + " ADD Concept=%s Event=%s"
                    + " DEL Concept=%s Event=%s"
                    + " MOD Concept=%s"
                    + " OBJ Save=%s Delete=%s",
                    txn.getAddedConcepts().size(), txn.getAddedEvents().size(),
                    txn.getDeletedConcepts().size(), txn.getDeletedEvents().size(),
                    txn.getModifiedConcepts().size(),
                    txn.getDBObjectTable_save().size(), txn.getDBObjectTable_delete().size());
        }
    }

    public void saveTransaction(RtcTransaction txn) throws Exception {
        Collection<RtcTransaction> coll = new ArrayList<RtcTransaction>();
        coll.add(txn);
        saveTransaction(coll);
    }

    // This method should throw a non-RuntimeException only when the intent is for the caller
    // to not retry.
    public void saveTransaction(Collection<RtcTransaction> txns) throws Exception {
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    Iterator<RtcTransaction> allTxns = txns.iterator();
                    while (allTxns.hasNext()) {
                        saveTransaction(config, allTxns.next());
                    }
                    config.commit();
                }
            }
            catch (DBConnectionsBusyException busyEx) {
                throw busyEx;
            }
            catch (SQLException ex) {
                logger.log(Level.WARN, "SaveTransaction failed. Error=%s  code=%s", ex.getMessage(), ex.getErrorCode());
                logger.log(Level.DEBUG, ex, "SaveTransaction SQLException.");

                if (ex.getErrorCode() == ORACLE_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE) {
                    config.rollback(false);
                    throw new DuplicateException(ex);
                }
                // Adding this to show additional info.
                // May be we will pass it to 'check' in the future if it provides more accurate exceptions
                SQLException nex = ex.getNextException();
                while (nex != null) {
                    logger.log(Level.WARN, "SaveTransaction failed - nested error. Error=%s  code=%s", nex.getMessage(), nex.getErrorCode());
                    nex = nex.getNextException();
                }
                check(ex, config, config.getCurrentConnection());
                config.rollback(true);
                throw new RuntimeException(ex);
            } finally {
                config.releaseConnection();
            }
        }
    }

    // These functions are accessed by ClusterSequenceFunctions
    public void createSequence(String sequenceName, long minValue, long maxValue, long start, int increment) throws SQLException {
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    config.createSequence(sequenceName, minValue, maxValue, start, increment);
                }
            } catch (SQLException ex) {
                check(ex, config, config.getCurrentConnection());
                throw ex;
            } finally {
                config.releaseConnection();
            }
        }
    }

    public void removeSequence(String sequenceName) throws SQLException {
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    config.removeSequence(sequenceName);
                }
            } catch (SQLException ex) {
                check(ex, config, config.getCurrentConnection());
                throw ex;
            } finally {
                config.releaseConnection();
            }
        }
    }

    public long nextSequence(String sequenceName) throws Exception {
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    return config.nextSequence(sequenceName);
                }
            } catch (SQLException ex) {
                check(ex, config, config.getCurrentConnection());
                throw ex;
            } finally {
                config.releaseConnection();
            }
        }
        throw new Exception("No active connections");
    }

    public boolean isConnectionAlive() {
        OracleAdapter config = null;
        for (int i = 0; i < dbAdapters.size(); i++) {
            config = (OracleAdapter) dbAdapters.get(i);
            if (config.isActive()) {
                break;
            } else {
                config = null;
            }
        }

        if (config != null) {
            if (config.getConnectionPool().isAvailable()) {
                //logger.log(Level.DEBUG, "Connection pool alive: " + config.getConnectionPool().toString());
                return true;
            } else {
                //logger.log(Level.DEBUG, "Connection pool not-alive: " + config.getConnectionPool().toString());
                return false;
            }
        }
        return false;
    }

    public void addConnectionListener(ConnectionPoolListener lsnr) {
        for (int i = 0; i < dbAdapters.size(); i++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
            if (config.isActive()) {
                config.getConnectionPool().addConnectionListener(lsnr);
            }
        }
    }

    public void cleanup(int typeId) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        try {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        config.purgeDeletedEntities(cacheCluster.getClusterConfig().getSiteId(), typeId);
                        if (typeId == StateMachineConceptImpl.StateTimeoutEvent.STATETIMEOUTEVENT_TYPEID)
                            config.purgeStateMachineTimeouts(cacheCluster.getClusterConfig().getSiteId());
                        config.commit();
                    }
                }
                catch (SQLException ex) {
                    if (check(ex, config, config.getCurrentConnection()))
                        config.rollback(false);
                    throw ex;
                }
                catch (Exception ex) {
                    config.rollback(false);
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    public void migrate(int typeId) throws Exception {
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return;
        }
        if (!isAnyActive()) {
            logger.log(Level.DEBUG, "No active connections.");
            throw new RuntimeException("No active connections");
        }

        try {
            for (int i = 0; i < dbAdapters.size(); i++) {
                OracleAdapter config = (OracleAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        config.migrateToObjectTable(cacheCluster.getClusterConfig().getSiteId(), typeId);
                        config.commit();
                    }
                } catch (Exception ex) {
                    config.rollback(false);
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    private ObjectTupleImpl searchTupleInAllTypes(OracleAdapter config, String extId) throws Exception {
        try {
            logger.log(Level.TRACE, "Searching tuple in all types for extId=%s", extId);
            Class[] clzs = cacheCluster.getMetadataCache().getRegisteredTypes();
            for (int i = 0; i < clzs.length; i++) {
                Class clz = clzs[i];
                if (clz != null) {
                    ObjectTupleImpl tuple = config.getTupleByType(extId, clz);
                    if (tuple != null) {
                        return tuple;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            config.releaseConnection();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.tibco.cep.runtime.service.om.coherence.CoherenceBackingStore#getMaxId(int)
     *
     * Returns the max id from the table associated with given typeId
     */
    public long getMaxId(int typeId) throws Exception {
        return getMinMaxId(typeId, true);
    }

    /*
     * (non-Javadoc)
     * @see com.tibco.cep.runtime.service.om.coherence.CoherenceBackingStore#getMinId(int)
     *
     * Returns the min id from the table associated with given typeId
     */
    public long getMinId(int typeId) throws Exception {
        return getMinMaxId(typeId, false);
    }

    /*
     * To avoid code repetetion.
     * flag=true is max and flag=false is min
     */
    private long getMinMaxId(int typeId, boolean flag) throws Exception {
        if (!initialized) {
            initialize(true, false);
        }
        if (!cacheCluster.getClusterConfig().isHasBackingStore()) {
            return 0L;
        }
        if (!isAnyActive()) {
            if (logger != null) {
                logger.log(Level.DEBUG, "No active connections.");
            }
            throw new RuntimeException("No active connections");
        }

        for (int i=0; i < dbAdapters.size(); i++) {
            OracleAdapter config= (OracleAdapter) dbAdapters.get(i);
            try {
                if (config.isActive()) {
                    if (flag) { // get max
                        long maxEntityId=config.getMaxId(typeId);
                        return maxEntityId;
                    } else {
                        long minEntityId=config.getMinId(typeId);
                        return minEntityId;
                    }
                }
            } catch (Exception ex) {
                if (i == (dbAdapters.size() -1)) {
                    logger.log(Level.WARN, "Exception in Query processing: " + ex + ", " + ex.getMessage());
                    ex.printStackTrace();
                    throw ex;
                }
            } finally {
                config.releaseConnection();
            }
        }
        return 0L;
    }

    /**
     * This method is called during preload to directly populate entity cache
     */
    public long loadObjects(int typeId, long maxRows, long startId, long endId, boolean loadHandles, boolean loadEntities) throws Exception {

        Class<?> entityClz = cacheCluster.getMetadataCache().getClass(typeId);
        if (loadHandles) {
            logger.log(Level.INFO, "Loading objects with handle from database class=%s," +
                    " batch-size=%s, start=%s, end=%s",
                    entityClz.getName(), preloadBatchSize, startId, endId);
        } else {
            logger.log(Level.INFO, "Loading objects from database class=%s," +
            		" batch-size=%s, start=%s, end=%s",
            		entityClz.getName(), preloadBatchSize, startId, endId);
        }

        for (int j=0; j < dbAdapters.size(); j++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
            if (config.isActive()) {
                try {
                    Iterator<?> itr = null;
                    long start = System.currentTimeMillis();
                    if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
                        itr = config.loadConcepts(typeId, startId, endId);
                    } else if (Concept.class.isAssignableFrom(entityClz)) {
                        itr = config.loadConcepts(typeId, startId, endId);
                    } else {
                        itr = config.loadEvents(typeId, startId, endId);
                    }
                    return loadObjects(typeId, maxRows, entityClz, itr, start, loadHandles, loadEntities);
                } catch (SQLException sqlex) {
                    check(sqlex, config, config.getCurrentConnection());
                    throw sqlex;
                } finally {
                    config.releaseConnection();
                }
            }
        }
        return 0;
    }

    /**
     * @param maxRows
     * @param numf
     * @param entityClz
     * @param cacheProvider
     * @param itr
     * @param totalDbaseOps
     * @param totalCacheOps
     * @param start
     * @return
     */
    private long loadObjects(int typeId, long maxRows, Class entityClz,
            Iterator itr, long start, boolean loadHandles, boolean loadEntities) throws Exception {
        if (loadHandles) {
            logger.log(Level.DEBUG, "Putting objects with handles in cache for type=%s," +
                    " put-size=%s",
                    entityClz.getName(), preloadBatchSize);
        } else {
            logger.log(Level.DEBUG, "Putting objects in cache for type=%s," +
                    " put-size=%s",
                    entityClz.getName(), preloadBatchSize);
        }

        NumberFormat numf = NumberFormat.getInstance();
        EntityDao cacheProvider = cacheCluster.getMetadataCache().getEntityDao(typeId);
        long total = 0;
        long totalDbaseOps = 0;
        long totalCacheOps = 0;
        long dbOpsStartTime = 0;
        long dbOpsEndTime = 0;

        ObjectTable.ElementWriter writer = null;
        if (loadHandles) {
            writer = cacheCluster.getObjectTableCache().createElementWriter();
        }

        if (itr != null) {
            Map<Long, Entity> loadSet = new HashMap<Long, Entity>();
            long loaded = 0;
            dbOpsStartTime = System.currentTimeMillis();
            while (itr.hasNext()) {
                Entity entity = (Entity)itr.next();
                if (loadEntities) {
                    loadSet.put(entity.getId(), entity);
                }
                if (loadHandles) {
                    writer.addObject(new ObjectTupleImpl(entity.getId(), entity.getExtId(), typeId));
                }
                if ((++loaded % preloadBatchSize) == 0) {
                    dbOpsEndTime = System.currentTimeMillis();
                    totalDbaseOps += (dbOpsEndTime - dbOpsStartTime);
                    cacheProvider.putAll(loadSet);
                    if (loadHandles) {
                        writer.commit();
                    }
                    loadSet.clear();
                    dbOpsStartTime = System.currentTimeMillis();
                    totalCacheOps += (dbOpsStartTime - dbOpsEndTime);
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        total = System.currentTimeMillis() - start;
                        String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                        logger.log(Level.DEBUG, "#### Pre-load class=%s " +
                                "reached=%s time=%s [db=%s cs=%s]",
                                entityClz.getName(), numf.format(loaded),
                                totalStr, (100*totalDbaseOps/(1+total)),
                                (100*totalCacheOps/(1+total)));
                    }
                }

                if ((maxRows > 0) && (loaded >= maxRows)) {
                    break;
                }
            }

            dbOpsEndTime = System.currentTimeMillis();
            totalDbaseOps += (dbOpsEndTime - dbOpsStartTime);
            cacheProvider.putAll(loadSet);
            if (loadHandles) {
                writer.commit();
            }
            loadSet.clear();
            dbOpsStartTime = System.currentTimeMillis();
            totalCacheOps += (dbOpsStartTime - dbOpsEndTime);

            if (logger.isEnabledFor(Level.INFO)) {
                total = System.currentTimeMillis() - start;
                String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                logger.log(Level.INFO,"######## Pre-loaded" +
                        " class=%s instances=%s time=%s [dbase=%s cache=%s]",
                        entityClz.getName(), numf.format(loaded), totalStr,
                        (100*totalDbaseOps/(1+total)), (100*totalCacheOps/(1+total)));
            }
            return loaded;
        } else {
            logger.log(Level.ERROR, "####### Pre-loading %s failed!", entityClz.getName());
            return 0;
        }
    }

    /**
     * This method is called during preload to directly populate entity cache
     */
    public long loadObjectKeys(int typeId, int maxRows) throws Exception {

        Class<?> entityClz = cacheCluster.getMetadataCache().getClass(typeId);
        logger.log(Level.INFO,"Loading entity keys from database for class=%s " +
                "max-rows=%s", entityClz.getName(), maxRows);

        for (int j=0; j < dbAdapters.size(); j++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
            if (config.isActive()) {
                try {
                    long start = System.currentTimeMillis();
                    Iterator iterator = config.recoverIds(cacheCluster.getClusterConfig().getSiteId(), typeId, maxRows).iterator();
                    long dbTime = System.currentTimeMillis() - start;
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        String totalStr = (dbTime/3600000)+"h"+((dbTime/60000)%60)+"m"+((dbTime/1000)%60)+"s ";
                        logger.log(Level.DEBUG,"#### Pre-load %s dbtime=%s", entityClz.getName(), totalStr);
                    }
                    return loadObjectKeys(typeId, maxRows, entityClz, iterator, dbTime, start);
                } catch (SQLException sqlex) {
                    check(sqlex, config, config.getCurrentConnection());
                    throw sqlex;
                } finally {
                    config.releaseConnection();
                }
            }
        }
        return 0;
    }

    private long loadObjectKeys(int typeId, int maxRows, Class entityClz,
            Iterator iter, long dbTime, long start) throws Exception {
        NumberFormat numf = NumberFormat.getInstance();
        EntityDao cacheProvider = cacheCluster.getMetadataCache().getEntityDao(typeId);
        long total = 0;
        long totalDbaseOps = 0;
        long totalCacheOps = 0;
        long dbOpsStartTime = 0;
        long dbOpsEndTime = 0;
        if (iter != null) {
            Map<Long, Entity> loadSet = new HashMap<Long, Entity>();
            long loaded = 0;
            dbOpsStartTime = System.currentTimeMillis();
            while (iter.hasNext()) {
                Long entityId = (Long)iter.next();
                loadSet.put(entityId, null);
                if ((++loaded % preloadBatchSize) == 0) {
                    dbOpsEndTime = System.currentTimeMillis();
                    totalDbaseOps += (dbOpsEndTime - dbOpsStartTime);
                    cacheProvider.putAll(loadSet);
                    loadSet.clear();
                    dbOpsStartTime = System.currentTimeMillis();
                    totalCacheOps += (dbOpsStartTime - dbOpsEndTime);
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        total = System.currentTimeMillis() - start;
                        String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                        logger.log(Level.DEBUG,"#### Pre-load entity ids for" +
                                " class=%s reached=%s time=%s [db=%s cs=%s]",
                                entityClz.getName(), numf.format(loaded),
                                totalStr, (100*totalDbaseOps/(1+total)),
                                (100*totalCacheOps/(1+total)));
                    }
                }

                if ((maxRows > 0) && (loaded >= maxRows)) {
                    break;
                }
            }
            dbOpsEndTime = System.currentTimeMillis();
            totalDbaseOps += (dbOpsEndTime - dbOpsStartTime);
            cacheProvider.putAll(loadSet);
            loadSet.clear();
            dbOpsStartTime = System.currentTimeMillis();
            totalCacheOps += (dbOpsStartTime - dbOpsEndTime);
            if (logger.isEnabledFor(Level.INFO)) {
                total = System.currentTimeMillis() - start;
                String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                logger.log(Level.INFO,"######## Pre-loaded entity ids for" +
                        " class=%s instances=%s time=%s [dbase=%s cache=%s]",
                        entityClz.getName(), numf.format(loaded), totalStr,
                        (100*totalDbaseOps/(1+total)), (100*totalCacheOps/(1+total)));
            }
            return loaded;
        } else {
            logger.log(Level.ERROR,"####### Pre-loading entity ids" +
                    " for %s failed!", entityClz.getName());
            return 0;
        }
    }

    @Override
    public long loadObjects(int typeId, Long[] keys, boolean loadHandles, boolean loadEntities) throws Exception {
        Class<?> entityClz = cacheCluster.getMetadataCache().getClass(typeId);
        logger.log(Level.INFO, "Loading objects using keys from database, class=%s",
                entityClz.getName());

        for (int j=0; j < dbAdapters.size(); j++) {
            OracleAdapter config = (OracleAdapter) dbAdapters.get(j);
            if (config.isActive()) {
                try {
                    Iterator<?> itr = null;
                    long start = System.currentTimeMillis();
                    if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
                        itr = config.loadConcepts(typeId, keys);
                    } else if (Concept.class.isAssignableFrom(entityClz)) {
                        itr = config.loadConcepts(typeId, keys);
                    } else {
                        itr = config.loadEvents(typeId, keys);
                    }
                    return loadObjects(typeId, -1, entityClz, itr, start, loadHandles, loadEntities);
                } catch (SQLException sqlex) {
                    check(sqlex, config, config.getCurrentConnection());
                    throw sqlex;
                } finally {
                    config.releaseConnection();
                }
            }
        }
        return 0;
    }

    @Override
    public boolean getWriteMode() {
        return writeMode;
    }

    @Override
    public void setClusterDatasource(boolean usePrimary) {
        //TODO: Implement
    }

    @Override
    public void setUsePrimaryDatasource(boolean usingPrimary) {
        //TODO: Implement
    }

    @SuppressWarnings("unused")
    protected String getCacheClassName(String name) throws Exception{
        int beginIndex = 0;
        int endIndex = name.indexOf('$');
        String type;
        String clusterName;
        String agentName;
        String cacheName;

        if (endIndex != -1) {
            type = name.substring(beginIndex, endIndex);
        } else {
            throw new Exception("Invalid Cache Name " + name);
        }

        beginIndex = endIndex + 1;
        endIndex = name.indexOf('$', beginIndex);

        if (endIndex != -1) {
            clusterName = name.substring(beginIndex, endIndex);
        } else {
            throw new Exception("Invalid Cache Name " + name);
        }

        beginIndex = endIndex + 1;
        endIndex = name.indexOf('$', beginIndex);

        if (endIndex != -1) {
            agentName = name.substring(beginIndex, endIndex);
        } else {
            throw new Exception("Invalid Cache Name " + name);
        }

        beginIndex = endIndex + 1;
        //endIndex = name.indexOf('$', beginIndex);

        cacheName = name.substring(beginIndex);
        //logger.log(Level.INFO, name + " cache-name=" + cacheName);
        return cacheName;
    }
    
    public void registerType (Class type, int version) {
        
    }
    
    public void reInitializeTypes () {
        
    }

    @Override
    public String toString() {
        return "OracleStore:" + this.cacheName + "/" + this.tableName;
    }
}
