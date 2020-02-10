/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.orcltojdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.tibco.be.jdbcstore.impl.DBConnectionPool;
import com.tibco.be.jdbcstore.impl.DBManager;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class BEJdbcStore extends BEStore {

    private com.tibco.cep.kernel.service.logging.Logger logger;
    final ArrayList<BSMigrationJdbcAdapter> dbAdapters = new ArrayList<BSMigrationJdbcAdapter>();

    private RuleServiceProvider rsp;
    private DBManager mDBManager = null;
    private boolean isNewSerEnabled = false; // Keep disabled (not implemented)
    private static boolean initialized = false;
    private static int globalConnIndex = 0;
    private int connIndex = 0;

    public BEJdbcStore(RuleServiceProvider rsp) {
        this.rsp = rsp;
        this.logger = rsp.getLogger(BEJdbcStore.class);
        try {
            initialize(true, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
   
    public Map loadClassRegistry() throws Exception {
        BSMigrationJdbcAdapter config = dbAdapters.get(0);

        this.logger.log(Level.DEBUG, "Loading class registry from backing store...");
        try {
            Map classRegistry = config.getClassRegistry();
            return classRegistry;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            config.releaseConnection();
        }
    }

    public void setClassRegistry(ClassRegistry classRegistry) {
        super.setClassRegistry(classRegistry);
    }

    @Override
    public int countObjects(int typeId, long maxEntityId) throws Exception {
        throw new Exception("BEJdbcStore.countObjects() is not implemented.");
    }

    @Override
    public Iterator getObjects(int typeId, long maxEntityId) throws Exception {
        throw new Exception("BEJdbcStore.getObjects() is not implemented.");
    }

    @Override
    public Iterator getWorkItems() throws Exception {
        throw new Exception("BEJdbcStore.getWorkItems() is not implemented.");
    }
    
    public void insertEvents(Map<Integer, Map<Long, Event>> entries) throws Exception {
        BSMigrationJdbcAdapter config = dbAdapters.get(0);
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
                config.updateMaxEntityId(typeId, maxEntityId);
            }
            config.commit();
            this.logger.log(Level.DEBUG, "Inserted %d events of type %s, maxId=%s",
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
        BSMigrationJdbcAdapter config = dbAdapters.get(0);
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
                config.updateMaxEntityId(typeId, maxEntityId);
            }
            config.commit();
            this.logger.log(Level.DEBUG, "Inserted %d concepts of type %s, maxId = %s",
                    txnMap.size(), clz.getName(), maxEntityId);
        } catch (Exception e) {
            config.rollback(true);
            throw e;
        } finally {
            config.releaseConnection();
        }
    }
    
    public void saveWorkItem(WorkTuple item) throws Exception {
        BSMigrationJdbcAdapter config = dbAdapters.get(0);
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
        BSMigrationJdbcAdapter config = (BSMigrationJdbcAdapter) dbAdapters.get(0);
        progress.putAll(config.getMigrationProgress());
        return progress;
    }

    public void markTypeAsDone(int typeId) throws Exception {
        super.markTypeAsDone(typeId);
        BSMigrationJdbcAdapter config = (BSMigrationJdbcAdapter) dbAdapters.get(0);
        try {
            config.markTypeAsDone(typeId);
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
        } finally {
            config.releaseConnection();
        }
    }

    private void createDBAdapters(ArrayList<BSMigrationJdbcAdapter> adapters) throws Exception {
        String targetJdbcType = rsp.getProperties().getProperty("be.jdbc.database.type", "oracle").trim();
        this.logger.log(Level.INFO, "Target JDBC database type is %s", targetJdbcType);
        boolean migrateObjectTable = Boolean.parseBoolean(rsp.getProperties().getProperty("be.migration.objecttable", "true").trim());
        this.logger.log(Level.INFO, "Migration of object table is set to %s", migrateObjectTable);
        boolean useExplicitTemporaryBlobs = Boolean.parseBoolean(rsp.getProperties().getProperty("be.migration.temporaryblobs", "false").trim());
        this.logger.log(Level.INFO, "Explicit temporary blobs is set to %s", useExplicitTemporaryBlobs);
        // Create Adapters for each Active Connection pool
        Iterator itr = mDBManager.getActiveConnPool().iterator();
        while (itr.hasNext()) {
            DBConnectionPool connPool = (DBConnectionPool)itr.next();
            BSMigrationJdbcAdapter config = new BSMigrationJdbcAdapter(connPool, targetJdbcType, migrateObjectTable, useExplicitTemporaryBlobs);
            adapters.add(config);
        }
    }

    private void initialize(boolean forRecovery, boolean isReadOnly) throws Exception {
        if (initialized) {
            return;
        }

        try {
            Thread.currentThread().setContextClassLoader(rsp.getClassLoader());

            mDBManager = DBManager.getInstance();
            mDBManager.init();
            createDBAdapters(dbAdapters);
            initialized = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public String getConnectionPoolState() {
        return dbAdapters.get(0).getConnectionPool().toString();
    }
}
