/*
 * Copyright(c) 2004-2013 TIBCO Software Inc.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.jdbcstore.impl;

import java.security.KeyStore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.JdbcStore;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.jdbcstore.SqlType;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.be.oracle.impl.OracleActiveConnectionPool;
import com.tibco.be.oracle.impl.OracleConnectionManager;
import com.tibco.be.oracle.impl.OracleConnectionPool;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityObjectFactory;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

//  This should be a singleton and to be initialized before any backing store transactions can be started
//  It is mainly used to initialization for the new table based backing store but it also does a
//  small amount of initialization for oracle type based backing store
//  It centralizes collections of all mapping information
//  It initializes table based connection pool

public class DBManager {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(JdbcStore.class);
    public static String PROP_PREFETCH_SIZE = "be.engine.tangosol.oracle.prefetch";
    public static boolean _useOracleStrategy = false;
    public static boolean _useTemporaryBlobs = false;
    public static boolean _recreateOnRecovery = false;
    public static boolean _rollbackAfterRelease = true;
    public static boolean _timestampUseDataTimeZone = false;
    public static boolean _skipUnreferencedSecondaryTableEntries = true;
    public static boolean _optimizeReadStatements = false;
    public static boolean _optimizeWriteStatements = false;
    // If enabled, only update contained concept array for addition or removal to the array
    public static boolean _containedConceptArrayAddRemoveOnly = false;
    public static boolean _skipUnmodifiedProperties = false;
    public static boolean _skipNullValue = false;
    public static boolean _skipReverseReferences = false;
    
    private static boolean _bInitialized = false;
    private static DBManager _instance = new DBManager();
    private Cluster _cacheCluster;
    private RuleServiceProvider _rsp;
    private ArrayList<ConnectionPool> _activeOraclePools = new ArrayList<ConnectionPool>();
    private ArrayList<ConnectionPool> _activeJdbcPools = new ArrayList<ConnectionPool>();
    private Map _entityPropertiesMap = new HashMap();
    private Map _entityPropertiesMapByTableName = new HashMap();
    private Map _beAliases = new HashMap();
    private Map _tableMappings = null;
    private Map<String, Class> baseNameToProcessClassMap = null;
    private Map<String, Integer> processClassNameToProcessVersionMap = null;

    private DBManager() {
    }

    public static DBManager getInstance() {
        return _instance;
    }

    // Most of the logic here is extracted from JdbcStore
    // Put pool and database initialization stuff here
    public void init() throws Exception {
        synchronized (_instance) {
            if (_bInitialized) {
                return;
            }
            _cacheCluster = CacheClusterProvider.getInstance().getCacheCluster();
            _rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
            String dbType = _rsp.getProperties().getProperty("be.backingstore.database.type", RDBMSType.RDBMS_TYPE_NAME_ORACLE);
            RDBMSType.setDefaultSqlType(dbType, true);

            _timestampUseDataTimeZone = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.timestamp.useDataTimeZone", "false").trim());
            DBHelper._timestampUseDataTimeZone = _timestampUseDataTimeZone;
            _skipUnreferencedSecondaryTableEntries = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.unreferenced.skip", "true").trim());
            DBHelper._skipUnreferencedSecondaryTableEntries = _skipUnreferencedSecondaryTableEntries;
            _containedConceptArrayAddRemoveOnly = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.containedconceptarray.addremoveonly", "false").trim());
            DBHelper._containedConceptArrayAddRemoveOnly = _containedConceptArrayAddRemoveOnly;
            _skipUnmodifiedProperties = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.unmodified.skip", "false").trim());
            DBHelper._skipUnmodifiedProperties = Boolean.parseBoolean(
                System.getProperty(SystemProperty.DONT_PERSIST_UNMODIFIED_CONTAINED_CONCEPT_PROPERTY_VALUE.getPropertyName(), "false").trim());
            _skipReverseReferences = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.reverse.references.skip", "false").trim());
            DBHelper._skipReverseReferences = _skipReverseReferences;
            _skipNullValue = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.nullarrayorhistoryvalue.skip", "false").trim());
            DBHelper._skipNullValue = _skipNullValue;
            _optimizeReadStatements = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.optimize.reads", "false").trim());
            SqlType.optimizeReads = _optimizeReadStatements;
            _optimizeWriteStatements = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.optimize.writes", "false").trim());
            SqlType.optimizeWrites = _optimizeWriteStatements;
            logger.log(Level.INFO, "Backingstore database type=%s. Flags skip-no-ref=%s add-remove-only=%s optimize-reads=%s optimize-writes=%s skip-unmod-props=%s skip-null-array/history-value=%s",
                dbType, _skipUnreferencedSecondaryTableEntries, _containedConceptArrayAddRemoveOnly, _optimizeReadStatements, _optimizeWriteStatements, _skipUnmodifiedProperties, _skipNullValue);
            
            int dbCount = Integer.valueOf(_rsp.getProperties().getProperty("be.backingstore.dburi.count", "1").trim());
            if (dbCount > 2) {
                throw new Exception("be.backingstore.dburi.count=" + dbCount + " which is more than the maximum allowed (2)");
            }
            // Each database count represents a single database setup
            for (int i = 0; i < dbCount; i++) {
                String dburi = _rsp.getProperties().getProperty("be.backingstore.dburi." + i);
                if (dburi == null || dburi.trim().length() == 0) {
                    dburi = _rsp.getProperties().getProperty("be.jdbc.dburi." + i);
                    if (dburi == null || dburi.trim().length() == 0) {
                        throw new Exception("Invalid value for be.backingstore.dburi." + i + " (not specified or blank)");
                    }
                }
                dburi = dburi.trim();
				GlobalVariables gv = _rsp.getGlobalVariables();
				XiNode db = _rsp.getProject().getSharedArchiveResourceProvider().getResourceAsXiNode(dburi);
				if (db != null) {
					XiNode dbConfig = XiChild.getChild(db, ExpandedName.makeName("config"));
					String dbDriver = getSubstitutedStringValue(gv,
							XiChild.getString(dbConfig, ExpandedName.makeName("driver")));
					String sharedDbType = getDbType(dbDriver);
					if (!dbType.equals(sharedDbType)) {
						throw new Exception("database type in sharedresource [ " + sharedDbType
								+ " ] does not match with type in cdd [ " + dbType + " ]");
					}
				} else {
					logger.log(Level.WARN, "The following shared resource was not exported in the SharedArchive: %s",
							dburi);
					throw new Exception("The following shared resource was not exported in the SharedArchive:" + dburi);
				}

                String readTimeout = _rsp.getProperties().getProperty("be.backingstore.readtimeout", "0").trim(); // ~oracle.jdbc.readtimeout
                _useTemporaryBlobs = Boolean.valueOf(_rsp.getProperties().getProperty("be.backingstore.useTemporaryBlobs", "false").trim());
                _recreateOnRecovery = Boolean.valueOf(_rsp.getProperties().getProperty("be.backingstore.recreateOnRecovery", "false").trim());
                _rollbackAfterRelease = Boolean.valueOf(_rsp.getProperties().getProperty("be.backingstore.connection.rollbackafterrelease", "true").trim());
                String strategy = _rsp.getProperties().getProperty("be.backingstore.dburi.strategy." + i, "jdbc").trim();
                if (strategy.equalsIgnoreCase("oracle")) {
                    _useOracleStrategy = true;
                    logger.log(Level.INFO, "%s using 'oracle' specific database scheme with temporary blobs '%s' and rollbacks '%s'",
                            dburi, _useTemporaryBlobs, _rollbackAfterRelease);
                } else {
                    _useOracleStrategy = false;
                    _useTemporaryBlobs = false;
                    _recreateOnRecovery = false;
                    logger.log(Level.INFO, "%s using 'jdbc' specific database scheme with rollbacks '%s'", dburi, _rollbackAfterRelease);
                }
                boolean enforceSize = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.dburi.pool.enforce." + i, "false").trim());
                String schema = _rsp.getProperties().getProperty("be.backingstore.dburi.schema." + i);
                schema = (schema == null ? null : (schema.trim().length() > 0 ? schema.trim() : null));
                String initialSize = _rsp.getProperties().getProperty("be.backingstore.dburi.pool.initial." + i, "").trim();
                if (initialSize == null || initialSize.trim().length() == 0) {
                    initialSize = _rsp.getProperties().getProperty("be.jdbc.dburi.pool.initial." + i, "10").trim();
                }
                String minSize = _rsp.getProperties().getProperty("be.backingstore.dburi.pool.min." + i, "").trim();
                if (minSize == null || minSize.trim().length() == 0) {
                    minSize = _rsp.getProperties().getProperty("be.jdbc.dburi.pool.min." + i, "10").trim();
                }
                String maxSize = _rsp.getProperties().getProperty("be.backingstore.dburi.pool.max." + i, "").trim();
                if (maxSize == null || maxSize.trim().length() == 0) {
                    maxSize = _rsp.getProperties().getProperty("be.jdbc.dburi.pool.max." + i, "10").trim();
                }
                String waitTimeout = _rsp.getProperties().getProperty("be.backingstore.dburi.pool.waitTimeout." + i, "1").trim();
                String inactivityTimeout = _rsp.getProperties().getProperty("be.backingstore.dburi.pool.inactivityTimeout." + i, "900").trim();
                String retryInterval = _rsp.getProperties().getProperty("be.backingstore.dburi.pool.retryinterval." + i, "5").trim();
                boolean isActive = Boolean.parseBoolean(_rsp.getProperties().getProperty("be.backingstore.dburi.active." + i, "true").trim());

                String failBack = null;
                if (isActive) {
                    failBack = _rsp.getProperties().getProperty("be.backingstore.dburi.failBack." + i, "").trim();
                }

                Properties properties = new Properties();
                properties.put("LoadTypes", false); // Don't load Types for Oracle Connections
                if (schema != null) {
                    properties.put("schema", schema);
                }
                properties.put("ReadTimeout", readTimeout);
                properties.put("RetryInterval", retryInterval);
                if (_cacheCluster != null) {
                    properties.put("AutoFailover", _cacheCluster.getClusterConfig().isAutoFailover());
                    properties.put("FailoverInterval", _cacheCluster.getClusterConfig().getAutoFailoverInterval());
                }
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

                logger.log(Level.INFO, "Initializing DB connection pool: schema=" + (schema == null ? "Default schema" : schema) + ", key=" + dburi + ", initialSize=" + initialSize + ", minSize=" + minSize + ", maxSize=" + maxSize);
                logger.log(Level.INFO, "EnforcePoolSize=" + enforceSize + ", WaitTimeout=" + waitTimeout + ", InactivityTimeout=" + inactivityTimeout); // + ", CommitBatchSize=" + commitBatchSize + ", FailoverTo=" + failBack);

                // This code would setup a pool for Oracle back-end storage
                // (enabling population of both storage types). Not used anymore
                if (_useOracleStrategy) {
                    logger.log(Level.DEBUG, "DataSource properties: %s", properties);
                    OracleConnectionPool pool = registerOracleDataSource(_rsp, dburi, dburi, properties, isActive);
                    if (pool != null) {
                        if (isActive) {
                            OracleActiveConnectionPool activePool = OracleConnectionManager.getActiveConnectionPool(dburi);
                            if (failBack != null) {
                                logger.log(Level.INFO, "Registering Key=" + failBack + " as failback connection");
                                //pool.setFailbackKey(failBack);
                                activePool.setFailbackKey(failBack);
                            } else if (!_cacheCluster.getClusterConfig().isUsePrimaryDatasource()) {
                                logger.log(Level.WARN, "Incompatible properties: No failback defined while usePrimaryDatasource is false");
                                throw new Exception("Incompatible properties: No failback defined while usePrimaryDatasource is false");
                            }
                            activePool.setUsePrimary(_cacheCluster.getClusterConfig().isUsePrimaryDatasource());
                            activePool.activate();
                            _activeOraclePools.add(activePool.getPoolInUse());
                        }
                    }
                } else {
                    XiNode dbConfig = XiChild.getChild(db, ExpandedName.makeName("config"));
                    String dbUserName = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("user")));
                    String dbUserPassword = decryptPwd(getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("password"))));
                    String dbURL = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("location")));
                    String dbDriver = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("driver")));
                    String useSsl = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("useSsl")));
                    
                    JdbcSSLConnectionInfo sslConnectionInfo = null;
                    if ("true".equalsIgnoreCase(useSsl)) {
                    	XiNode sslConfig = XiChild.getChild(dbConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "ssl"));
                    	sslConnectionInfo = getSSLConnectionInfo(dbUserName, dbUserPassword, dbURL, dbDriver, sslConfig, _rsp, dburi);
                    }
                    
                    DBConnectionPool connPool = new DBConnectionPool(dburi, dbDriver, dbURL, dbUserName, dbUserPassword,
                            Integer.valueOf(initialSize), Integer.valueOf(maxSize), Integer.valueOf(waitTimeout), sslConnectionInfo);
                    DBConnectionPoolManager.addConnectionPool(dbURL, connPool);
                    if (isActive) {
                        _activeJdbcPools.add(connPool);
                    }
                }
            }

            // Initialized connection pool
            DBConnectionPoolManager.init();

            loadAliases();
            initializeJdbcMaps();
            _bInitialized = true;
        }
    }

    private static String getSubstitutedStringValue(GlobalVariables gv, String value) {
        final CharSequence cs = gv.substituteVariables(value);
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }
    }

    private static String decryptPwd(String encryptedPwd) {
        try {
            String decryptedPwd = encryptedPwd;
            if (ObfuscationEngine.hasEncryptionPrefix(encryptedPwd)) {
                decryptedPwd = new String(ObfuscationEngine.decrypt(encryptedPwd));
            }
            return decryptedPwd;
        } catch (AXSecurityException e) {
            logger.log(Level.WARN, e.getMessage());
            return encryptedPwd;
        } finally {
            restoreProviders();
        }
    }

    /**
     * Remove all security providers installed by ObfuscationEngine,
     * prior to creating database connections.
     * Keeping them causes SSL handshake failures with JDK-1.6
     * These providers might be added as a result of earlier calls
     * to ObfuscationEngine, in this JVM instance.
     */
    private static void restoreProviders() {
        java.security.Security.removeProvider("Entrust");
        java.security.Security.removeProvider("ENTRUST");
        java.security.Security.removeProvider("IAIK");
    }

	private static String getDbType(String jdbcDriver) {
		jdbcDriver = jdbcDriver.toLowerCase();
		if (jdbcDriver.contains(RDBMSType.RDBMS_TYPE_NAME_DB2.toLowerCase())) {
			return BackingStoreConfig.TYPE_DB2;
		} else if (jdbcDriver.contains(RDBMSType.RDBMS_TYPE_NAME_ORACLE.toLowerCase())) {
			return BackingStoreConfig.TYPE_ORACLE;
		} else if (jdbcDriver.contains(RDBMSType.RDBMS_TYPE_NAME_POSTGRES.toLowerCase())) {
			return BackingStoreConfig.TYPE_POSTGRES;
		} else if (jdbcDriver.contains(RDBMSType.RDBMS_TYPE_NAME_MYSQL.toLowerCase())) {
			return BackingStoreConfig.TYPE_MYSQL;
		} else if (jdbcDriver.contains(RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.toLowerCase())) {
			return BackingStoreConfig.TYPE_SQLSERVER;
		} else {
			return BackingStoreConfig.TYPE_BDB;
		}
	}
    
    private synchronized OracleConnectionPool registerOracleDataSource(RuleServiceProvider rsp, String key, String dburi, Properties properties, boolean isActive) throws Exception {
        try {
            OracleConnectionPool pool = OracleConnectionManager.getDataSource(key);
            if (pool == null) {
                XiNode db = rsp.getProject().getSharedArchiveResourceProvider().getResourceAsXiNode(dburi);
                if (db == null) {
                    logger.log(Level.WARN, "The following shared resource was not exported in the SharedArchive:" + dburi);
                    throw new RuntimeException("The following shared resource was not exported in the SharedArchive:" + dburi);
                }
                pool = registerOracleDataSource(rsp, rsp.getGlobalVariables(), db, key, properties, isActive, dburi);
            }
            return pool;
        } catch (Exception ex) {
            logger.log(Level.WARN, "Failed to register datasource: %s", key);
            logger.log(Level.WARN, "Error - %s", ex, ex.getMessage());
            throw ex;
        }
    }

    private synchronized OracleConnectionPool registerOracleDataSource(RuleServiceProvider rsp, GlobalVariables gv, XiNode dbNode, String key, Properties poolProps, boolean isActive, String sharedResourceUri) throws Exception {
        Properties datasrcProps = new Properties();
        XiNode dbConfig = XiChild.getChild(dbNode, ExpandedName.makeName("config"));
        String dbUserName = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("user")));
        String dbUserPassword = decryptPwd(getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("password"))));
        String dbURL = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("location")));
        String dbDriver = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("driver")));
        String useSsl = getSubstitutedStringValue(gv, XiChild.getString(dbConfig, ExpandedName.makeName("useSsl")));
        datasrcProps.put("user", dbUserName);
        datasrcProps.put("password", dbUserPassword);
        datasrcProps.put("location", dbURL);

        JdbcSSLConnectionInfo sslConnectionInfo = null;
        if ("true".equalsIgnoreCase(useSsl)) {
        	XiNode sslConfig = XiChild.getChild(dbConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "ssl"));
        	sslConnectionInfo = getSSLConnectionInfo(dbUserName, dbUserPassword, dbURL, dbDriver, sslConfig, _rsp, sharedResourceUri);
        }
        
        logger.log(Level.INFO, "Registering DataSource, rsp=%s, key=%s, uri=%s, user=%s",
                rsp.getName(), key, datasrcProps.getProperty("location"), datasrcProps.getProperty("user"));
        return OracleConnectionManager.registerDataSource(key, datasrcProps, poolProps, isActive, sslConnectionInfo);
    }

    public void createDBAdapters(ArrayList<DBAdapter> adapters) throws Exception {
        // Create OracleAdapters for each Active Connection pool
        Iterator itr;
        if (DBManager._useOracleStrategy) {
            itr = _activeOraclePools.iterator();
        } else {
            itr = _activeJdbcPools.iterator();
        }
        while (itr.hasNext()) {
            ConnectionPool connPool = (ConnectionPool) itr.next();
            DBAdapter config = new DBAdapter(connPool, _cacheCluster);
            adapters.add(config);
        }
    }

    public void registerType(Class type, int version) {
        try {
            if (baseNameToProcessClassMap == null) {
                baseNameToProcessClassMap = new HashMap<String, Class>();
                processClassNameToProcessVersionMap = new HashMap<String, Integer>();
            }
            logger.log(Level.DEBUG, "Register %s with backing store", type.getName());
            String procSuffix = "$9y00245zproc";
            StringBuffer sb = new StringBuffer("_" + version + "_");
            sb.append(procSuffix);
            String name = type.getName();
            String baseClassName = name.substring(0, name.indexOf(sb.toString()));
            baseNameToProcessClassMap.put(baseClassName, type);
            processClassNameToProcessVersionMap.put(name, version);
        }
        catch (Exception e) {
            logger.log(Level.INFO, "Failed to register %s", type.getName());
        }
    }
    
    private void populateTableMapWithProcessClassName() throws Exception {
        Map<String, Map> processClassTableMap = new HashMap<String, Map>();
        
        if (_tableMappings == null) {
            _tableMappings = getTypeMappings();
        }
        for (Iterator itr = _tableMappings.keySet().iterator(); itr.hasNext();) {
            String baseClassName = (String) itr.next();
            Map tableInfo = (Map) _tableMappings.get(baseClassName);
            Class processClass = baseNameToProcessClassMap.get(baseClassName);
            if (processClass != null) {
                processClassTableMap.put(processClass.getName(), tableInfo);
            }
        }
        _tableMappings.putAll(processClassTableMap);
    }
    
    /**
     * JDBC Maps are initialized twice to support dynamic BPMN classes 
     */
    private void initializeJdbcMaps() throws Exception {
        logger.log(Level.INFO, "Initializing jdbc table mappings");
        String className = null;
        try {
            // Get the list of classes from the database
            if (_tableMappings == null) {
                _tableMappings = getTypeMappings();
            }
            for (Iterator iter = _tableMappings.keySet().iterator(); iter.hasNext();) {
                String processClassName = null;
                className = (String) iter.next();
                //get java class definitions
                Class entityClass = null;
                try {
                    if (baseNameToProcessClassMap != null) {
                        Class processClass = baseNameToProcessClassMap.get(className);
                        if (processClass != null) {
                            entityClass = processClass;
                            processClassName = entityClass.getName();
                        } else {
                            entityClass = ((ClassLoader) _rsp.getTypeManager()).loadClass(className);
                        }
                    } else {
                        entityClass = ((ClassLoader) _rsp.getTypeManager()).loadClass(className);
                    }
                } catch (ClassNotFoundException ex) {
                    logger.log(Level.INFO, "Cannot load class : %s, skip to next class", className);
                    continue;
                }
                
                if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(entityClass)) {
                    // If it's already created, no need to do it again 
                    if (_entityPropertiesMap.get(className) != null) { 
                        continue; 
                    } 

                    // Handle Concepts...
                    Map mappings = (Map) _tableMappings.get(className);
                    DBConceptMap conceptMap = new DBConceptMap(new ConceptDescription(className, entityClass, processClassName != null),
                            mappings, _beAliases);
                    conceptMap.setEntityClass(entityClass);
                    logger.log(Level.DEBUG, "%s", conceptMap);
                    // Need to set the secondary table names
                    // associate a java class with the corresponding concept map
                    _entityPropertiesMap.put(className, conceptMap);
                    if (processClassName != null) {
                        _entityPropertiesMap.put(processClassName, conceptMap);
                        conceptMap.setProcessVersion(processClassNameToProcessVersionMap.get(processClassName));
                    }
                    _entityPropertiesMapByTableName.put((String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), conceptMap);
                    // What is this for??
                    //FIX THIS -- jdbcTypes2Description.put(sd.getName(), conceptMap);
                } else if (StateMachineConceptImpl.StateTimeoutEvent.class.getName().equals(className)) {
                    // Handle StateMachine TimeoutEvents...
                    Map mappings = (Map) _tableMappings.get(className);
                    DBTimeEventMap eventMap = new DBTimeEventMap(new StateMachineTimeoutDescription(),
                            (String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), _beAliases);
                    eventMap.setEntityClass(entityClass);
                    logger.log(Level.DEBUG, "%s", eventMap);
                    _entityPropertiesMap.put(className, eventMap);
                    _entityPropertiesMapByTableName.put((String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), eventMap);
                    //FIX THIS -- jdbcTypes2Description.put(sd.getName(), eventMap);
                } else if (SimpleEventImpl.class.isAssignableFrom(entityClass)) {
                    // Handle Events...
                    Map mappings = (Map) _tableMappings.get(className);
                    DBEventMap eventMap = new DBEventMap(new SimpleEventDescription(className, entityClass),
                            (String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), _beAliases);
                    eventMap.setEntityClass(entityClass);
                    logger.log(Level.DEBUG, "%s", eventMap);
                    _entityPropertiesMap.put(className, eventMap);
                    _entityPropertiesMapByTableName.put((String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), eventMap); // [was:mapping.get(1)]
                    //FIX THIS -- jdbcTypes2Description.put(sd.getName(), eventMap);
                } else {
                    // Handle TimeEvents...
                    Map mappings = (Map) _tableMappings.get(className);
                    DBTimeEventMap eventMap = new DBTimeEventMap(new TimeEventDescription(className, entityClass),
                            (String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), _beAliases);
                    eventMap.setEntityClass(entityClass);
                    logger.log(Level.DEBUG, "%s", eventMap);
                    _entityPropertiesMap.put(className, eventMap);
                    _entityPropertiesMapByTableName.put((String) mappings.get(DBEntityMap.PRIMARY_TABLE_NAME), eventMap);
                    //FIX THIS -- jdbcTypes2Description.put(sd.getName(), eventMap);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Unable to register class %s with database.", className);
            throw ex;
        }
        finally {
            //releaseConnection();
        }
    }
    
    public void reInitializeTypes() {
        try {
            if (baseNameToProcessClassMap != null) {
                logger.log(Level.INFO, "Try to reload classes again");
                //initializeProcessClassMap();
                populateTableMapWithProcessClassName();
                initializeJdbcMaps();
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Failed to re-initialize jdbc mappings", e);
        }
    }

    public Map getEntityPropsMap() throws Exception {
        return _entityPropertiesMap;
    }

    public Map getEntityPropsMapByTableName() throws Exception {
        return _entityPropertiesMapByTableName;
    }

    // FIX THIS - Need to load alias somewhere during initialization
    private void loadAliases() throws Exception {
        Connection cnx = null;
        try {
            // FIX THIS - need to deal with number of active pools and pool switching
            if (DBManager._useOracleStrategy) {
                cnx = _activeOraclePools.get(0).getConnection();
            } else {
                cnx = _activeJdbcPools.get(0).getConnection();
            }
            if (cnx != null) {
                try {
                    PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM BEAliases");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String beName = rs.getString(1);
                        String alias = rs.getString(2);
                        _beAliases.put(beName, alias);
                    }
                    cnx.rollback();
                    rs.close();
                    stmt.close();
                } catch (SQLException sqlex) {
                    logger.log(Level.FATAL, "Failed to query BEAliases database table: %s - " +
                            " Make sure backingstore database is setup properly.", sqlex.getMessage());
                    throw new RuntimeException("Failed to query BEAliases table: " + sqlex.getMessage());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (cnx != null) {
                if (DBManager._useOracleStrategy) {
                    ((Connection) cnx).close();
                } else {
                    ((DBConnectionPool) _activeJdbcPools.get(0)).free(cnx);
                }
                logger.log(Level.DEBUG, "Loaded aliases table and returned resources");
            }
        }
    }

    // FIX THIS Need finally block to free the connection properly.
    // Getting the table names correspond to the class names
    private Map getTypeMappings() throws Exception {
        int entryCount = 0;
        Map map = new HashMap();
        // FIX THIS - is get(0) the right way of doing this?
        Connection cnx = null;
        try {
            if (DBManager._useOracleStrategy) {
                cnx = _activeOraclePools.get(0).getConnection();
            } else {
                cnx = _activeJdbcPools.get(0).getConnection();
            }

            PreparedStatement stmt = cnx.prepareStatement("SELECT className, fieldName, tableName FROM ClassToTable ORDER BY fieldName DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String className = rs.getString(1);
                String fieldName = rs.getString(2);
                String tableName = rs.getString(3);
                Map info = (Map) map.get(className);
                if (info == null) {
                    info = new HashMap(2);
                }
                if (fieldName == null) {
                    info.put(DBEntityMap.PRIMARY_TABLE_NAME, tableName.trim().toUpperCase());
                    entryCount++;
                } else if (fieldName.trim().equals(DBConceptMap.REVERSEREF_FIELD_NAME)) { //FIXME: FATIH Use a new KEY!
                    info.put(DBEntityMap.REVERSE_TABLE_NAME, tableName.trim().toUpperCase());
                } else {
                    info.put(fieldName, tableName.trim().toUpperCase());
                }
                map.put(className.trim(), info);
            }
            cnx.rollback();
            rs.close();
            stmt.close();

            if (entryCount == 0) {
                logger.log(Level.WARN, "No class to table mappings found! Please check DDL execution.");
            }

            // Hack for classes not defined in table ClassToJdbcType
            // StateMachineConceptImpl.StateTimeoutEvent.class
            String className = StateMachineConceptImpl.StateTimeoutEvent.class.getName();
            Map info = new HashMap(2);
            info.put(DBEntityMap.PRIMARY_TABLE_NAME, "StateMachineTimeout$$");
            info.put(DBEntityMap.REVERSE_TABLE_NAME, "StateMachineTimeout$$");
            map.put(className, info);
            return map;
        } catch (Exception e) {
            logger.log(Level.WARN, e, "Loading type mappings failed: %s", e.getMessage());
            throw e;
        } finally {
            if (cnx != null) {
                try {
                    if (DBManager._useOracleStrategy) {
                        ((Connection) cnx).close();
                    } else {
                        ((DBConnectionPool) _activeJdbcPools.get(0)).free(cnx);
                    }
                } catch (Exception e) {
                }
                logger.log(Level.DEBUG, "Loaded table mappings and returned resources");
            }
        }
    }

    @Deprecated
    @SuppressWarnings("unused")
    private String getAlias(String key) throws Exception {
        return (String) _beAliases.get(key);
    }

    public String getTableName(String className) throws Exception {
        Map info = (Map) _tableMappings.get(className);
        if (info != null) {
            return (String) info.get(DBEntityMap.PRIMARY_TABLE_NAME);
        }
        logger.log(Level.WARN, "Missing table mapping for %s", className);        
        return null;
    }

    public ArrayList<ConnectionPool> getActiveConnPool() {
        if (DBManager._useOracleStrategy) {
            return _activeOraclePools;
        } else {
            return _activeJdbcPools;
        }
    }

    // Used by QueryManager in dashboard
    public void closeLoadResult(Iterator itr) {
        if (itr instanceof ConceptsWithVersionIterator) {
            ((ConceptsWithVersionIterator) itr).close();
        }
    }
    
    /**
     * 
     * @param username
     * @param password
     * @param connUrl
     * @param jdbcDriver
     * @param sslConfig
     * @param gv
     * @return A JdbcSSLConnectionInfo instance with all the necessary SSL configurations.
     * @throws Exception 
     */
    public static JdbcSSLConnectionInfo getSSLConnectionInfo(String username, String password, String connUrl, String jdbcDriver, XiNode sslConfig, RuleServiceProvider rsp, String sharedResourceUri) throws Exception {
    	
    	JdbcSSLConnectionInfo sslConnectionInfo = JdbcSSLConnectionInfo.createConnectionInfo(username, password, connUrl, jdbcDriver);
    	SharedArchiveResourceProvider sharedArchiveResourceProvider = rsp
    			.getProject()
    			.getSharedArchiveResourceProvider();
    	GlobalVariables gv = rsp.getGlobalVariables();
    	
    	String trustedCertsURI = getSubstitutedStringValue(gv,
    			XiChild.getString(sslConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "cert")));
    	
    	String trustStorePassword = decryptPwd(
    			getSubstitutedStringValue(gv,
    					XiChild.getString(sslConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "trustStorePassword"))));

    	KeyStore trustedKeysStore = SSLUtils.createKeystore(trustedCertsURI, null, sharedArchiveResourceProvider, gv, true);
    	String trustedKsFileName = formFileKeystoreName(rsp.getProject().getName(), rsp.getName(), sharedResourceUri);
    	String trustStore = SSLUtils.storeKeystore(trustedKeysStore, trustStorePassword, trustedKsFileName);
    	
    	sslConnectionInfo.setTrustStoreProps(trustStore, SSLUtils.KEYSTORE_JKS_TYPE, trustStorePassword);
    	
		String clientAuth = getSubstitutedStringValue(gv,
				XiChild.getString(sslConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "requiresClientAuthentication")));
		
		if ("true".equalsIgnoreCase(clientAuth)) {
			String identityPath = getSubstitutedStringValue(gv,
        			XiChild.getString(sslConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "identity")));
			
			BEIdentity keyStoreIdentity = getIdentity(identityPath, sharedArchiveResourceProvider, gv);
			if (keyStoreIdentity != null && keyStoreIdentity instanceof BEKeystoreIdentity) {
				sslConnectionInfo.setKeyStoreProps(
						((BEKeystoreIdentity)keyStoreIdentity).getStrKeystoreURL(),
						((BEKeystoreIdentity)keyStoreIdentity).getStrStoreType(),
						((BEKeystoreIdentity)keyStoreIdentity).getStrStorePassword());
			}
			else {
				String message = "Identity Resource - '" + identityPath + "' must be of type 'Identity file'";
				throw new Exception("JDBC Connection - " + message);
			}
		}
		
		String verifyHostName = getSubstitutedStringValue(gv,
    			XiChild.getString(sslConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "verifyHostName")));
		
		if ("true".equalsIgnoreCase(verifyHostName)) {
			String expectedHostName = getSubstitutedStringValue(gv,
        			XiChild.getString(sslConfig, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "expectedHostName")));
			sslConnectionInfo.setVerifyHostname(expectedHostName);
		}
		
		return sslConnectionInfo;
    }
    
    /**
     * Creates the name for the jdbc ssl trusted certs keystore file, such that it is unique for an engine and jdbc resource.
     * @param projectName
     * @param engineName
     * @param sharedResourceUri
     * @return
     */
    private static String formFileKeystoreName(String projectName, String engineName, String sharedResourceUri) {
    	if (sharedResourceUri.indexOf('.') > -1) {
    		sharedResourceUri = sharedResourceUri.substring(0, sharedResourceUri.indexOf('.'));//Remove the shared resource extension
    	}
    	String name = projectName + "_" + engineName + "_" + sharedResourceUri;
    	String prefix = "jdbc_ssl_";
    	String extension = ".ks";
    	return prefix + name.replaceAll("[/\\\\. ]", "_") + extension;//Replace all slashes, spaces, periods with underscore.
    }
    
    /**
     * 
     * @param idReference
     * @param provider
     * @param gv
     * @return A BEIdentity instance for specified identity file path.
     * @throws Exception
     */
    public static BEIdentity getIdentity(String idReference, ArchiveResourceProvider provider, GlobalVariables gv) throws Exception {
    	BEIdentity beIdentity = null;
    	if ((idReference != null) && !idReference.trim().isEmpty()) {
    		if (idReference.startsWith("/")) {
    			beIdentity = BEIdentityUtilities.fetchIdentity(provider, gv, idReference);
    		}
    		else {
    			throw new Exception("Incorrect Trusted Certificate Folder string: " + idReference);
    		}
    	}
    	return beIdentity;
    }

    /*
    private Connection getDBConnection() throws SQLException{
        if (m_currentConnection.get() == null) {
            m_currentConnection.set(_activeConnPools.get(0).getConnection());
        }
        return (Connection) m_currentConnection.get();
    }
    */

    // First, create a simple mappings using indexes
    // convert all non-type specific sqls to jdbc based instead of oracle.
    // run the system with this first
    // then, test the type specific things.
}
