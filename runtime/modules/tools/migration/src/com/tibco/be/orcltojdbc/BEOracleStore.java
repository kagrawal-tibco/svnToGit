/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.orcltojdbc;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;

import com.tibco.be.oracle.OracleDebug;
import com.tibco.be.oracle.impl.OracleActiveConnectionPool;
import com.tibco.be.oracle.impl.OracleConnectionManager;
import com.tibco.be.oracle.impl.OracleConnectionPool;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class BEOracleStore extends BEStore {

    private com.tibco.cep.kernel.service.logging.Logger logger;
    static final ArrayList<BSMigrationOrclAdapter> dbAdapters = new ArrayList<BSMigrationOrclAdapter>();

    private RuleServiceProvider rsp;
    private boolean isNewSerEnabled = false; // Keep disabled (not implemented)
    private int commitBatchSize = 10;
    private static boolean initialized = false;
    private static int globalConnIndex = 0;
    private int connIndex = 0;

    public BEOracleStore(RuleServiceProvider rsp) {
        this.rsp = rsp;
        this.logger = rsp.getLogger(BEOracleStore.class);
        try {
            initialize(true, false);
            connIndex = (globalConnIndex++);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map loadClassRegistry() throws Exception {
        this.logger.log(Level.DEBUG, "Loading Class Registry From Backing Store..");

        try {
            for (int i=0; i<dbAdapters.size(); i++) {
                BSMigrationOrclAdapter config = (BSMigrationOrclAdapter) dbAdapters.get(i);
                try {
                    if (config.isActive()) {
                        Map classRegistry = config.getClassRegistry();
                        return classRegistry;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                } finally {
                    config.releaseConnection();
                }
            }
        } catch (Exception ex) {
            this.logger.log(Level.ERROR, ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        return null;
    }

    public void setClassRegistry(ClassRegistry classRegistry) {
        super.setClassRegistry(classRegistry);
        for (BSMigrationOrclAdapter config : dbAdapters) {
            config.setClassRegistry(classRegistry);
        }
    }

    public Iterator getObjects(int typeId, long maxEntityId) throws Exception {
        Class entityClz = classRegistry.getClass(typeId);
        BSMigrationOrclAdapter config = (BSMigrationOrclAdapter) dbAdapters.get(connIndex);

        if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
            //same as concepts for now...
            return config.loadConcepts(typeId, maxEntityId);
        } else if (Concept.class.isAssignableFrom(entityClz)) {
            return config.loadConcepts(typeId, maxEntityId);
        } else {
            return config.loadEvents(typeId, maxEntityId);
        }
    }

    public int countObjects(int typeId, long maxEntityId) throws Exception {
        if (rsp.getProperties().getProperty("be.migration.countobjects", "true").trim().equals("true")) {
            BSMigrationOrclAdapter config = (BSMigrationOrclAdapter) dbAdapters.get(connIndex);
            return config.countEntities(typeId, maxEntityId);
        } else {
            return 0;
        }
    }

    public Iterator getWorkItems() throws Exception {
        BSMigrationOrclAdapter config = (BSMigrationOrclAdapter) dbAdapters.get(connIndex);
        return config.getWorkItems().values().iterator();
    }

    @Override
    public void insertEvents(Map<Integer, Map<Long, Event>> entries) throws Exception {
        BSMigrationOrclAdapter config = (BSMigrationOrclAdapter) dbAdapters.get(connIndex);
        try {
            long maxEntityId = -1;
            int typeId = -1;
            Map<Long, Event> txnMap = null;
            Class clz = null;
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Integer, Map<Long, Event>> entry = (Map.Entry) all_entries.next();
                typeId = entry.getKey();
                clz = classRegistry.getClass(typeId);
                if (SimpleEvent.class.isAssignableFrom(clz)) {
                    String className = clz.getName();
                    txnMap = (Map) entry.getValue();
                    maxEntityId = config.insertEvents(className, txnMap);
                } else {
                    throw new Exception ("Class is not a SimpleEvent: "+ clz.getName());
                }
                //config.udpateMaxEntityId(typeId, maxEntityId);
            }
            config.commit();
            this.logger.log(Level.DEBUG, "Inserted %s events of type %s, maxId=%s",
                    txnMap.size(), clz.getName(), maxEntityId);
        } catch (Exception e) {
            config.rollback(true);
            throw e;
        } finally {
            config.releaseConnection();
        }
    }
    
    @Override
    public void insertConcepts(Map<Integer, Map<Long, Concept>> entries) throws Exception {
        BSMigrationOrclAdapter config = dbAdapters.get(connIndex);
        try {
            Iterator all_entries = entries.entrySet().iterator();
            long maxEntityId = -1;
            int typeId = -1;
            Map<Long, Concept> txnMap = null;
            Class clz = null;
            while (all_entries.hasNext()) {
                Map.Entry<Integer, Map<Long, Concept>> entry = (Map.Entry) all_entries.next();
                typeId = entry.getKey();
                clz = classRegistry.getClass(typeId);
                String className = clz.getName();
                txnMap = entry.getValue();

                if (isNewSerEnabled) {
                    if (StateMachineConcept.class.isAssignableFrom(clz)) {
                        maxEntityId = config.insertStateMachines(className, txnMap);
                    } else if (Concept.class.isAssignableFrom(clz)) {
                        maxEntityId = config.insertConcepts(className, txnMap);
                    }
                } else if (Concept.class.isAssignableFrom(clz)) {
                    maxEntityId = config.insertConcepts(className, txnMap);
                }
                //config.updateMaxEntityId(typeId, maxEntityId);
            }
            config.commit();
            this.logger.log(Level.DEBUG, "Inserted %s concepts of type %s, maxId=%s",
                    txnMap.size(), clz.getName(), maxEntityId);
        } catch (Exception e) {
            config.rollback(true);
            throw e;
        } finally {
            config.releaseConnection();
        }
    }

    @Override
    public void saveWorkItem(WorkTuple item) throws Exception {
        BSMigrationOrclAdapter config = dbAdapters.get(connIndex);
        try {
            config.saveWorkItem(item);
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw e;
        } finally {
            config.releaseConnection();
        }
    }

    public Map getMigrationProgress() throws Exception {
        Map progress = super.getMigrationProgress();
        return progress;
    }
    
    @Override
    public void markTypeAsDone(int typeId) throws Exception {
        super.markTypeAsDone(typeId);
    }

    public oracle.jdbc.OracleConnection getConnection() throws Exception {
        BSMigrationOrclAdapter config = (BSMigrationOrclAdapter) dbAdapters.get(connIndex);
        return config.getSqlConnection();
    }

    public Iterator getTypeIdIterator() {
        return classRegistry.typeIdToClz.keySet().iterator();
    }

    private String getSubstitutedStringValue(GlobalVariables gv, String value) {
        final CharSequence cs = gv.substituteVariables(value);
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }
    }

    private String decryptPwd(GlobalVariables gv, String pwd) {
        try {
            char[] pass = ObfuscationEngine.decrypt(pwd);
            return new String(pass);
        }
        catch(Exception e) {
            try {
                this.logger.log(Level.WARN, e.getMessage());
                return new String(ObfuscationEngine .decrypt( getSubstitutedStringValue(gv, pwd)));
            } catch (AXSecurityException e1) {
                this.logger.log(Level.WARN, e1.getMessage());
                return getSubstitutedStringValue(gv, pwd);
            }
        }
    }

    private synchronized OracleConnectionPool registerOracleDataSource(RuleServiceProvider rsp, String key, String dburi, Properties properties, boolean isActive) throws Exception{
        try {
            OracleConnectionPool pool= OracleConnectionManager.getDataSource(key);
            if (pool == null) {
                XiNode db=rsp.getProject().getSharedArchiveResourceProvider().getResourceAsXiNode(dburi);
                if(db == null) {
                    throw new RuntimeException("The following shared resource was not exported in the SharedArchive:"+dburi);
                }
                pool=registerOracleDataSource(rsp, rsp.getGlobalVariables(), db, key, properties, isActive);
            }
            return pool;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private synchronized OracleConnectionPool registerOracleDataSource(RuleServiceProvider rsp, GlobalVariables gv, XiNode dbNode, String key, Properties properties, boolean isActive) throws Exception {
        XiNode dbConfig = XiChild.getChild(dbNode, ExpandedName.makeName("config"));
        String oracleUserName = getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("user")));
        String oraclePassword = decryptPwd(gv,XiChild.getString(dbConfig, ExpandedName.makeName("password")));
        String oracleURI = getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("location")));

        // cache specific pool sizes override global pool size definition
        OracleDriver oracleDriver = new OracleDriver();
        DriverManager.registerDriver(oracleDriver);

        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
        ds.setURL(oracleURI);
        ds.setUser(oracleUserName);
        ds.setPassword(oraclePassword);
        ds.setDataSourceName(key);
        Properties props = new Properties();
        props.put("oracle.jdbc.ReadTimeout", rsp.getProperties().getProperty("be.oracle.jdbc.readtimeout", "0").trim());
        ds.setConnectionProperties(props);
        this.logger.log(Level.INFO, "Registering datasource, rsp=%s, key=%s, uri=%s, user=%s",
                rsp.getName(), key, oracleURI, oracleUserName);
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
            Thread.currentThread().setContextClassLoader(rsp.getClassLoader());
            boolean migrateObjectTable = Boolean.valueOf(rsp.getProperties().getProperty(
                    "be.migration.objecttable", "true").trim());
            this.logger.log(Level.INFO, "Migration of object table is set to %s", migrateObjectTable);

            commitBatchSize = Integer.valueOf(rsp.getProperties().getProperty(
                    "be.oracle.commitSize", "10").trim());

            boolean oracleDebug = Boolean.valueOf(rsp.getProperties().getProperty(
                    "be.oracle.debug", "false").trim());
            if (oracleDebug) {
                OracleDebug.setDebugLevel(9);
            } else {
                OracleDebug.setDebugLevel(0);
            }

            ArrayList<OracleActiveConnectionPool> activePools = new ArrayList<OracleActiveConnectionPool>();
            int dbCount = Integer.valueOf(rsp.getProperties().getProperty(
                    "be.oracle.dburi.count", "1").trim());
            if (dbCount > 2) {
                throw new Exception("be.oracle.dburi.count=" + dbCount
                        + " which is more than the maximum allowed (2)");
            }

            boolean useTemporaryBlobs = Boolean.valueOf(rsp.getProperties()
                    .getProperty("be.oracle.useTemporaryBlobs", "true").trim());

            for (int i = 0; i < dbCount; i++) {
                String dburi = rsp.getProperties().getProperty(
                        "be.oracle.dburi." + i);
                if (dburi == null || dburi.trim().length() == 0) {
                    throw new Exception("Invalid value for be.oracle.dburi."
                            + i + " (not specified or blank)");
                }
                Properties properties = new Properties();
                properties.put("LoadTypes", true); // Load Types for Oracle Connections
                dburi = dburi.trim();
                synchronized (OracleConnectionManager.class) {
                    if (OracleConnectionManager.getDataSource(dburi) == null) {
                        boolean enforceSize = Boolean.parseBoolean(rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.enforce." + i, "false").trim());
                        String schema = rsp.getProperties().getProperty(
                                "be.oracle.dburi.schema." + i);
                        schema = (schema == null ? null : (schema.trim()
                                .length() > 0 ? schema.trim() : null));
                        String initialSize = rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.initial." + i, "10").trim();
                        String minSize = rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.min." + i, "10").trim();
                        String maxSize = rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.max." + i, "10").trim();
                        String waitTimeout = rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.waitTimeout." + i, "1").trim();
                        String inactivityTimeout = rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.inactivityTimeout." + i, "900").trim();
                        String retryInterval = rsp.getProperties().getProperty(
                                "be.oracle.dburi.pool.retryinterval." + i, "5").trim();
                        boolean isActive = Boolean.parseBoolean(rsp.getProperties().getProperty(
                                "be.oracle.dburi.active." + i, "true").trim());

                        String failBack = null;
                        if (isActive) {
                            failBack = rsp.getProperties().getProperty(
                                    "be.oracle.dburi.failBack." + i, "").trim();
                        }

                        if (schema != null)
                            properties.put("schema", schema);
                        properties.put("RetryInterval", retryInterval);
                        properties.put("AutoFailover", false);
                        properties.put("FailoverInterval", 300);
                        properties.put("LoadTypes", true);

                        if (enforceSize) {
                            // Check for the situation where prop is specified but as empty
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

                        this.logger.log(Level.DEBUG,
                                "Initializing dataSource: schema=%s, key=%s, initialSize=%s, minSize=%s, maxSize=%s",
                                ((schema == null) ? "Default schema" : schema), dburi, initialSize, minSize, maxSize);
                        this.logger.log(Level.DEBUG,
                                "Enforce pool size=%s, waitTimeout=%s, inactivityTimeout=%s, commitBatchSize=%s, fail over to=%s",
                                enforceSize, waitTimeout, inactivityTimeout, this.commitBatchSize, failBack);
                        OracleConnectionPool pool = registerOracleDataSource(
                                rsp, dburi, dburi, properties, isActive);
                        if (pool != null) {
                            if (isActive) {
                                OracleActiveConnectionPool activePool = OracleConnectionManager
                                        .getActiveConnectionPool(dburi);
                                activePool.setUsePrimary(true);
                                activePools.add(activePool);
                            }
                        }
                    } else {
                        OracleActiveConnectionPool activePool = OracleConnectionManager
                                .getActiveConnectionPool(dburi);
                        if (activePool != null) {
                            activePools.add(activePool);
                        }
                    }
                }
            }
            // Create OracleAdapters for each Active Connection pool
            for (OracleActiveConnectionPool activePool : activePools) {
                BSMigrationOrclAdapter config = new BSMigrationOrclAdapter(activePool, migrateObjectTable, useTemporaryBlobs);
                dbAdapters.add(config);
            }
            initialized = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public String getConnectionPoolState() {
        return dbAdapters.get(connIndex).getConnectionPool().toString();
    }
}
