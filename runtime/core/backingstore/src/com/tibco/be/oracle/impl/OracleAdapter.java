/*
 * Copyright(c) 2004-2013 TIBCO Software Inc.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.be.oracle.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
//import oracle.jdbc.OracleResultSetCache;
import oracle.jdbc.OracleTypes;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BLOB;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.oracle.OracleLOBManager;
import com.tibco.be.oracle.PropertyArrayMap;
import com.tibco.be.oracle.PropertyAtomMap;
import com.tibco.be.oracle.PropertyAtomSimpleMap;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.serializers.OracleConceptDeserializer;
import com.tibco.be.oracle.serializers.OracleConceptSerializerImpl;
import com.tibco.be.oracle.serializers.OracleEventDeserializer;
import com.tibco.be.oracle.serializers.OracleEventSerializer_V2;
import com.tibco.be.oracle.serializers.OracleSerializer;
import com.tibco.be.oracle.serializers.OracleStateMachineDeserializer;
import com.tibco.be.oracle.serializers.OracleStateMachineSerializer;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.EntityDescription;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultScheduler.WorkTupleDBId;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;

public class OracleAdapter {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(OracleAdapter.class);
    private final static Logger sqltext = LogManagerFactory.getLogManager().getLogger("sql.text");
    private final static Logger sqlvars = LogManagerFactory.getLogManager().getLogger("sql.vars");
    private static int PREFETCH_SIZE = Integer.parseInt(System.getProperty("be.engine.tangosol.oracle.prefetch", "1000").trim());
    public final static int DELETE_IN_LIMIT = 500;
    public static int DEFAULT_ORACLE_BATCH_SIZE = 10;

    private RuleServiceProvider rsp;
    private Cluster cluster;
    private String entityTableName = "ALL_ENTITIES";
    private ThreadLocal m_currentConnection = new ThreadLocal();
    private OracleActiveConnectionPool m_pool;
    private boolean useExplicitTemporaryBlobs = true;
    private boolean rollbackAfterRelease = true;

    // Maximum Oracle in list is 1000
    private static int IN_BATCH_SIZE = 900;

    private final static String MERGE_OBJECTTABLE_STATEMENT = "MERGE INTO OBJECTTABLE T USING (SELECT ? GLOBALID, ? SITEID, ? ID, ? EXTID, ? CLASSNAME, ? ISDELETED, ? TIMEDELETED FROM DUAL) I " +
                                                              "ON (T.GLOBALID=I.GLOBALID) WHEN MATCHED THEN UPDATE SET T.ISDELETED=1, T.TIMEDELETED=I.TIMEDELETED WHEN NOT MATCHED THEN INSERT " +
                                                              "(GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED) VALUES (I.GLOBALID,I.SITEID,I.ID,I.EXTID,I.CLASSNAME, I.ISDELETED, I.TIMEDELETED) ";
    private int queryTimeout = 0;
    // Based on this variable, SITEID column be used in OBJECTTABLE queries
    // Default value is true
    private boolean multisite = false;
    private int workItemsBatchSize = 500;

    static String name2OracleType(Entity cept) {
        return "T_" + cept.getName().replaceAll("\\.", "\\$").toUpperCase();
    }

    public OracleAdapter(OracleActiveConnectionPool pool, Cluster cluster, boolean useExplicitTemporaryBlobs) throws Exception {
        this.m_pool = pool;
        this.cluster = cluster;
        this.rsp = cluster.getRuleServiceProvider();
        this.useExplicitTemporaryBlobs = useExplicitTemporaryBlobs;

        logger.log(Level.DEBUG, "OracleAdapter <init> with cluster %s", this.cluster.getClusterName());
        String sBatchSize = System.getProperty("be.engine.tangosol.oracle.batchSize");
        if ((sBatchSize != null) && (sBatchSize.trim().length() > 0)) {
            DEFAULT_ORACLE_BATCH_SIZE = Integer.valueOf(sBatchSize.trim()).intValue();
        }
        queryTimeout = Integer.parseInt(rsp.getProperties().getProperty("be.backingstore.querytimeout", "0").trim());
        multisite = "true".equals(rsp.getProperties().getProperty("be.engine.cluster.multisite", "false").trim());
        //Activate the pool
        m_pool.activate();
        //Initialize maps
        initializeOracleMaps(null);
        
        try {
            workItemsBatchSize = Integer.parseInt(rsp.getProperties().getProperty("be.engine.scheduler.workitems.batchsize", "500"));
        } catch (Exception e) {
        }
        logger.log(Level.DEBUG, "WorkItems batch size %s [%s]", workItemsBatchSize, 
                rsp.getProperties().getProperty("be.engine.scheduler.workitems.batchsize"));
    }

    public OracleAdapter(OracleActiveConnectionPool pool, RuleServiceProvider rsp) throws Exception {
        this.m_pool = pool;
        this.rsp = rsp;
        String sBatchSize = System.getProperty("be.engine.tangosol.oracle.batchSize");
        if ((sBatchSize != null) && (sBatchSize.trim().length() > 0)) {
            DEFAULT_ORACLE_BATCH_SIZE = Integer.valueOf(sBatchSize.trim()).intValue();
        }
        queryTimeout = Integer.parseInt(rsp.getProperties().getProperty("be.backingstore.querytimeout", "0").trim());
        multisite = "true".equals(rsp.getProperties().getProperty("be.engine.cluster.multisite", "false").trim());
        //Activate the pool
        m_pool.activate();
        //Initialize maps
        initializeOracleMaps(null);
    }

    // OracleAdapter is always active, even if a reconnection is being tried
    public boolean isActive() {
        //return getConnectionPool().isAvailable();
        return true;
    }

    public OracleActiveConnectionPool getConnectionPool() {
        return m_pool;
    }

    public void setEntityTableName(String entityTableName) {
        this.entityTableName = entityTableName;
    }

    /**
     * @param className
     * @return
     */
    public String generatedOracleTableName(String className) {
        OracleEntityMap map = null;
        try {
            map = (OracleEntityMap) getEntityPropsMap(null).get(className);
        }
        catch (Exception ignore) {
            logger.log(Level.WARN, "Failed to generate table name for %s error=%s", className, ignore.getMessage());
        }
        if (map != null) {
            return map.getOracleTableName();
        }
        return null;
    }

    /**
     * @param className
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @Deprecated
    private void registerCache(String className) throws Exception {
        throw new Exception("Method registerCache(String className) replaced by initializeOracleMaps() ");
    }

    /**
     * Initializes Oracle maps for all classes defined in the database.
     * Note: This initialization needs to be done 'per-connection', since
     * Oracle-11g libraries have internal synchronization on connection
     * while dealing with types.
     *
     * @param conn Connection to initialize types with
     * @throws Exception
     */
    public void initializeOracleMaps(OracleConnection conn) throws Exception {
        if (m_pool.areMapsInitialized(conn)) {
            return;
        }
        boolean localConn = false;
        String className = null;
        if (conn == null) {
            localConn = true;
            conn = getSqlConnection();
        }
        try {
            Map aliases = m_pool.getAliases();
            Map entityPropertiesMap = new HashMap();
            Map oracleTypes2Description = new HashMap();

            Map typeMappings = getTypeMappings();
            for (Iterator iter = typeMappings.keySet().iterator(); iter.hasNext();) {
                className = (String) iter.next();
                Class entityClass = ((ClassLoader) rsp.getTypeManager()).loadClass(className);
                if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(entityClass)) {
                    // Handle Concepts
                    List mapping = (List) typeMappings.get(className);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier() + (String) mapping.get(0), conn);
                    // Here we connect to DB to inquire Concept data type description...
                    OracleConceptMap conceptMap = new OracleConceptMap((ConceptDescription) new ConceptDescription(className), sd, (String) mapping.get(1), conn, aliases);
                    //conceptMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(entityClass.getName(), conceptMap);
                    oracleTypes2Description.put(sd.getName(), conceptMap);
                } else if (StateMachineConceptImpl.StateTimeoutEvent.class.getName().equals(className)) {
                    List mapping = (List) typeMappings.get(className);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier() + (String) mapping.get(0), conn);
                    OracleTimeEventMap eventMap = new OracleTimeEventMap(new StateMachineTimeoutDescription(), sd, (String) mapping.get(1), conn, aliases);
                    //eventMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(className, eventMap);
                    oracleTypes2Description.put(sd.getName(), eventMap);
                } else if (SimpleEventImpl.class.isAssignableFrom(entityClass)) {
                    List mapping = (List) typeMappings.get(className);
                    //StructDescriptor sd = StructDescriptor.createDescriptor(name2OracleType(cache.getConceptModel()), conn);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier() + (String) mapping.get(0), conn);
                    // Here we connect to DB to inquire Event data type description...
                    OracleEventMap eventMap = new OracleEventMap(new SimpleEventDescription(className), sd, (String) mapping.get(1), conn, aliases);
                    //eventMap.setOracleTableName(OracleDeployment.name2OracleTable(oracleType));
                    //eventMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(className, eventMap);
                    oracleTypes2Description.put(sd.getName(), eventMap);
                } else {
                    List mapping = (List) typeMappings.get(className);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier() + (String) mapping.get(0), conn);
                    OracleTimeEventMap eventMap = new OracleTimeEventMap(new TimeEventDescription(className), sd, (String) mapping.get(1), conn, aliases);
                    //eventMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(entityClass.getName(), eventMap);
                    oracleTypes2Description.put(sd.getName(), eventMap);
                }
            }
            // Cache the results by connection
            // Also sets default maps for 'null' connection
            m_pool.setMapsInitialized(entityPropertiesMap, oracleTypes2Description, conn);
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Unable to register class %s with Oracle", className);
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if (localConn) {
                releaseConnection();
            }
        }
    }

    private Map getEntityPropsMap(OracleConnection conn) throws Exception {
        if (!m_pool.areMapsInitialized(conn)) {
            initializeOracleMaps(conn);
        }
        return m_pool.getEntityPropsMap(conn);
    }

    private Map getOracleTypesMap(OracleConnection conn) throws Exception {
        if (!m_pool.areMapsInitialized(conn)) {
            initializeOracleMaps(conn);
        }
        return m_pool.getOracleTypesMap(conn);
    }

    /**
     * @param sequenceName
     * @return
     * @throws SQLException
     */
    public long getNextValue(String sequenceName) throws SQLException {
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT " + sequenceName + ".nextVal FROM DUAL");
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        long nextCacheId = 0L;
        while (rs.next()) {
            nextCacheId = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return nextCacheId;
    }

    @SuppressWarnings("unused")
    private void setNextCacheId(String cacheIdGeneratorName, long nextCacheId) throws SQLException {
        // check if the row already exist
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT nextCacheId FROM CacheIds WHERE cacheIdGeneratorName=? ");
        stmt.setString(1, cacheIdGeneratorName);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        boolean hasEntry = false;
        while (rs.next()) {
            hasEntry = true;
        }
        rs.close();
        stmt.close();

        if (!hasEntry) {
            stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement("INSERT INTO CacheIds VALUES(?,?) ");
            stmt.setString(1, cacheIdGeneratorName);
            stmt.setOracleObject(2, new oracle.sql.NUMBER(0));
            stmt.executeUpdate();
            stmt.close();
        } else {
            stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement("UPDATE CacheIds SET nextCacheId=? WHERE cacheIdGeneratorName=? ");
            stmt.setOracleObject(1, new oracle.sql.NUMBER(nextCacheId));
            stmt.setString(2, cacheIdGeneratorName);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    @SuppressWarnings("unused")
    private Long getNextCacheId(String cacheIdGeneratorName) throws SQLException {
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT nextCacheId FROM CacheIds WHERE cacheIdGeneratorName=? ");
        stmt.setString(1, cacheIdGeneratorName);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        Long cacheId = null;
        while (rs.next()) {
            cacheId = new Long(rs.getLong(1));
        }
        rs.close();
        stmt.close();
        return cacheId;
    }

    //CREATE TABLE ObjectTable(SITEID NUMBER, ID NUMBER, EXTID VARCHAR2(4000), ISDELETED INT);
    private OraclePreparedStatement getObjectTableStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT ID FROM OBJECTTABLE WHERE GLOBALID=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement mergeObjectTableStatement() throws SQLException {
        //StringBuffer queryBuf = new StringBuffer();
        //queryBuf.append("MERGE INTO OBJECTTABLE T USING (SELECT ? GLOBALID, ? SITEID, ? ID, ? EXTID, ? CLASSNAME, ? ISDELETED, ? TIMEDELETED FROM DUAL) VALUES (?,?,?,?,?,?,?)");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(MERGE_OBJECTTABLE_STATEMENT);
    }

    private OraclePreparedStatement insertObjectTableStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("INSERT INTO OBJECTTABLE (GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED) VALUES (?,?,?,?,?,?,?)");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement deleteObjectTableStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("DELETE FROM OBJECTTABLE WHERE GLOBALID=? ");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement updateObjectTableStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("UPDATE OBJECTTABLE SET ISDELETED=1, TIMEDELETED=? WHERE GLOBALID=? ");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getObjectTableStatementWithId() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT EXTID, CLASSNAME  FROM OBJECTTABLE  WHERE GLOBALID=? AND ISDELETED=0");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getObjectTableStatementWithExtId() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT GLOBALID, CLASSNAME FROM OBJECTTABLE  WHERE EXTID=? AND ISDELETED=0");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getMaxEntityIdStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (multisite) {
            queryBuf.append("SELECT MAX(ID) FROM OBJECTTABLE  WHERE SITEID=?");
        } else {
            queryBuf.append("SELECT MAX(GLOBALID) FROM OBJECTTABLE");
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement purgeObjectTableStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("DELETE FROM OBJECTTABLE WHERE ISDELETED = 1 AND TIMEDELETED <= ?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getLoadStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (multisite) {
            queryBuf.append("SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?");
        } else {
            queryBuf.append("SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0");
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OracleCallableStatement getLoadConceptsStatement(String tableName, StringBuffer queryBuf) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.CACHEID, T.ENTITY  FROM " + tableName + " T ");
        if (!cluster.getClusterConfig().isCacheAside()) {
            if (multisite) {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?) ");
            } else {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0) ");
            }
        }
        queryBuf.append("; end; ");
        sqltext.log(Level.TRACE, queryBuf.toString());
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private OracleCallableStatement getLoadEventsStatement(String tableName, StringBuffer queryBuf) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.ENTITY  FROM " + tableName + " T ");
        if (!cluster.getClusterConfig().isCacheAside()) {
            if (multisite) {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?) ");
            } else {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0) ");
            }
        }
        queryBuf.append("; end; ");
        sqltext.log(Level.TRACE, queryBuf.toString());
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private OraclePreparedStatement getRecoveryStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (multisite) {
            queryBuf.append("SELECT GLOBALID, EXTID, CLASSNAME FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?");
        } else {
            queryBuf.append("SELECT GLOBALID, EXTID, CLASSNAME FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0");
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement purgeDeletedItemsStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("DELETE FROM " + tableName + " T WHERE T.CACHEID=? AND T.ENTITY.ID$ IN " +
                "(SELECT X.ENTITY.ID$ FROM " + tableName + " X, ObjectTable Z WHERE Z.ISDELETED=1 AND X.ENTITY.ID$=Z.ID) ");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement migrateToObjectTableStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO OBJECTTABLE (GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED) SELECT T.ENTITY.ID$, ?, T.ENTITY.ID$, T.ENTITY.EXTID$, ?, 0 FROM " + tableName + " T ");
        if (multisite) {
            queryBuf.append(" WHERE T.ENTITY.ID$ NOT IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=0 AND SITEID=?)");
        } else {
            queryBuf.append(" WHERE T.ENTITY.ID$ NOT IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=0)");
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement numInstancesStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT COUNT(*) FROM " + tableName + " T");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement numHandlesStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT COUNT(*) FROM " + tableName + " T WHERE T.CACHEID=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement keysStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT T.ENTITY.ID$, T.ENTITY.EXTID$ FROM " + tableName + " T WHERE T.CACHEID=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @param attributes
     * @param oracleType
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement insertStatement(String tableName, Datum[] attributes, StructDescriptor oracleType) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO " + tableName + " VALUES(?," + oracleType.getName() + "(");
        for (int i = 0; i < attributes.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append("))");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement upsertStatement(String tableName, Datum[] attributes, StructDescriptor oracleType) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("MERGE INTO " + tableName + " T USING DUAL ON (DUAL.DUMMY IS NOT NULL AND T.ENTITY.ID$=? ) WHEN NOT MATCHED THEN INSERT VALUES(?," + oracleType.getName() + "(");
        for (int i = 0; i < attributes.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append(")) ");
        queryBuf.append("WHEN MATCHED THEN ");
        //queryBuf.append("UPDATE SET T.ENTITY = " + oracleType.getName() + "(");
        queryBuf.append("UPDATE SET ");
        for (int i = 1; i < attributes.length; i++) {
            String attributeName = "\"" + oracleType.getOracleTypeADT().getAttributeName(i + 1) + "\"";
            if (i > 1) {
                queryBuf.append(",");
            }
            queryBuf.append("T.ENTITY." + attributeName + "=?");
        }
        //queryBuf.append(")");

        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    //TODO: Cache results so it is called only once (not once per connection created)
    protected Map getTypeMappings() throws Exception {
        Map map = new HashMap();
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT CLASSNAME, ORACLETYPE, oracleTable FROM ClassToOracleType");
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        while (rs.next()) {
            List info = new ArrayList(2);
            info.add(rs.getString(2).trim().toUpperCase());
            info.add(rs.getString(3).trim().toUpperCase());
            map.put(rs.getString(1).trim(), info);
        }
        rs.close();
        stmt.close();

        // Hack for classes not defined in table ClassToOracleType
        // StateMachineConceptImpl.StateTimeoutEvent.class
        String className = StateMachineConceptImpl.StateTimeoutEvent.class.getName();
        List info = new ArrayList(2);
        info.add("T_STATEMACHINE_TIMEOUT");
        info.add("StateMachineTimeout$$");
        map.put(className, info);

        return map;
    }

    private OraclePreparedStatement updateStatement(String tableName, Datum[] attributes, StructDescriptor oracleType, boolean useCacheId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("UPDATE " + tableName + " T SET T.CACHEID=? , T.ENTITY = " + oracleType.getName() + "(");
        for (int i = 0; i < attributes.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        if (useCacheId) {
            queryBuf.append(") WHERE T.ENTITY.ID$=? AND T.CACHEID=?");
        } else {
            queryBuf.append(") WHERE T.ENTITY.ID$=?");
        }

        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @param attributes
     * @param oracleType
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement updateStatement(String tableName, Datum[] attributes, StructDescriptor oracleType) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("UPDATE " + tableName + " T SET T.ENTITY = " + oracleType.getName() + "(");
        for (int i = 0; i < attributes.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append(") WHERE T.ENTITY.ID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @param tableName
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement queryStatementByCacheId(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT P.ENTITY FROM " + tableName + "  P WHERE P.CACHEID=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement queryStatementById(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT P.CACHEID, P.ENTITY FROM " + tableName + "  P WHERE P.ENTITY.ID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @param tableName
     * @param useCacheId
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement queryStatementById(String tableName, boolean useCacheId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT P.ENTITY FROM " + tableName + "  P WHERE P.ENTITY.ID$=? AND P.CACHEID=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @param tableName
     * @param size
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement queryStatementByIds(String tableName, int size) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT P.ENTITY FROM " + tableName + "  P WHERE P.CACHEID=? AND P.ENTITY.ID$ IN (");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                queryBuf.append("?,");
            } else {
                queryBuf.append("?)");
            }
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement verifyStatementById(String tableName, boolean useCacheId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (useCacheId) {
            queryBuf.append("SELECT P.ENTITY.ID$ FROM " + tableName + "  P WHERE P.ENTITY.ID$=? AND P.CACHEID=?");
        } else {
            queryBuf.append("SELECT P.ENTITY.ID$ FROM " + tableName + "  P WHERE P.ENTITY.ID$=?");
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement queryStatementByExtId(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT P.ENTITY FROM " + tableName + "  P WHERE P.ENTITY.EXTID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement deleteStatementById(String tableName, StringBuffer queryBuf) throws SQLException {
        queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ENTITY.ID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement deleteStatementById(String tableName, boolean useCacheId, StringBuffer queryBuf) throws SQLException {
        if (useCacheId) {
            queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ENTITY.ID$=? AND P.CACHEID=?" );
        } else {
            queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ENTITY.ID$=?");
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement deleteBatchStatementById(String tableName, int size) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(100);
        queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ENTITY.ID$ IN (");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append(") AND P.CACHEID=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @return
     * @throws SQLException
     */
    private OraclePreparedStatement deleteStatementByExtId(String tableName, StringBuffer queryBuf) throws SQLException {
        queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ENTITY.EXTID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /**
     * @throws Exception
     */
    public void purgeAll(String tableName) throws SQLException {
        OraclePreparedStatement ps = (OraclePreparedStatement)
                getSqlConnection().prepareStatement("DELETE FROM " + tableName);
        ps.executeUpdate();
        ps.close();
    }

    public boolean deleteEntityById(String tableName, long id, long cacheId, String className) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        // Try Deleting the Entity
        OraclePreparedStatement stmt = deleteStatementById(tableName, true, queryBuf);
        sqltext.log(Level.TRACE, "%s (%s, %s)", queryBuf, id, cacheId);
        stmt.setOracleObject(1, new oracle.sql.NUMBER(id));
        stmt.setOracleObject(2, new oracle.sql.NUMBER(cacheId));
        stmt.executeUpdate();
        stmt.close();
        return true;
    }

    public void deleteEntitiesInBatch(String tableName, Collection entities, long cacheId) throws SQLException, Exception {
        if (entities == null || entities.size() <= 0) return;
        PreparedStatement stmt = deleteBatchStatementById(tableName, entities.size());
        int j = 0;
        Iterator it = entities.iterator();
        while (it.hasNext()) {
            long id = ((Long) it.next()).longValue();
            stmt.setLong(j + 1, id);
            ++j;
        }
        stmt.setLong(j + 1, cacheId);
        stmt.executeUpdate();
        stmt.close();
    }

    public long[] deleteEntities(String tableName, Collection entities, long cacheId, String className) throws Exception {
        if (entities == null || entities.size() <= 0) return new long[0];
        long[] ret = new long[entities.size()];

        int j = 0;
        Iterator it = entities.iterator();
        try {
            while (it.hasNext()) {
                long id = ((Long) it.next()).longValue();
                if (!this.deleteEntityById(tableName, id, cacheId, className)) {
                    ret[j++] = id;
                } else {
                    ret[j++] = -1;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ret;
    }

    /**
     * @param id
     * @throws Exception
     */
    public void deleteEntityById(String tableName, long id) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        OraclePreparedStatement ps = deleteStatementById(tableName, queryBuf);
        ps.setOracleObject(1, new oracle.sql.NUMBER(id));
        ps.executeUpdate();
        ps.close();
    }

    public long countEntities(int typeId) throws SQLException {
        Class entityClz=cluster.getMetadataCache().getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            OraclePreparedStatement stmt = numInstancesStatement(tableName);
            OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
            long count=0L;
            while (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            stmt.close();
            return count;
        }
        return 0L;
    }
    
    public ConceptsCursorIterator loadStateMachines(int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Loading %s entries from %s", entityClz.getName(), tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt = this.getLoadConceptsStatement(tableName, queryBuf);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            if (!cluster.getClusterConfig().isCacheAside()) {
                stmt.setString(2, entityClz.getName());
                if (multisite) {
                    stmt.setLong(3, cluster.getClusterConfig().getSiteId());
                }
            }
            sqltext.log(Level.TRACE, "%s (<cursor>, %s, %s)",
                    queryBuf, entityClz.getName(), (this.multisite ? cluster.getClusterConfig().getSiteId() : ""));
            stmt.execute();
            return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1), false);
//            if (cluster.getClusterConfig().isNewSerializationEnabled()) {
//                return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1), true);
//            } else {
//                return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1), false);
//            }
        } else {
            logger.log(Level.ERROR, "Failed loading %s entries - table UNKNOWN", entityClz.getName());
            return null;
        }
    }

    public ConceptsCursorIterator loadConcepts(int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Loading %s entries from %s", entityClz.getName(), tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt = this.getLoadConceptsStatement(tableName, queryBuf);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            if (!cluster.getClusterConfig().isCacheAside()) {
                stmt.setString(2, entityClz.getName());
                if (multisite) {
                    stmt.setLong(3, cluster.getClusterConfig().getSiteId());
                }
            }
            sqltext.log(Level.TRACE, "%s (<cursor>, %s, %s)",
                    queryBuf, entityClz.getName(), (this.multisite ? cluster.getClusterConfig().getSiteId() : ""));
            stmt.execute();
            return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1));
        } else {
            logger.log(Level.ERROR, "Failed loading %s entries - table UNKNOWN", entityClz.getName());
            return null;
        }
    }

    public EventsCursorIterator loadEvents(int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Loading %s entries from %s", entityClz.getName(), tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt = this.getLoadEventsStatement(tableName, queryBuf);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            if (!cluster.getClusterConfig().isCacheAside()) {
                stmt.setString(2, entityClz.getName());
                if (multisite) {
                    stmt.setLong(3, cluster.getClusterConfig().getSiteId());
                }
            }
            sqltext.log(Level.TRACE, "%s (<cursor>, %s, %s)",
                    queryBuf, entityClz.getName(), (this.multisite ? cluster.getClusterConfig().getSiteId() : ""));
            stmt.execute();
            return new EventsCursorIterator((OracleResultSet) stmt.getCursor(1));
        } else {
            logger.log(Level.ERROR, "Failed loading %s entries - table UNKNOWN", entityClz.getName());
            return null;
        }
    }

    /**
     * @param oracleTableName
     * @param cacheId
     * @return
     * @throws Exception
     */
    public ConceptsIterator getConcepts(String oracleTableName, long cacheId) throws SQLException {
        OraclePreparedStatement ps = queryStatementByCacheId(oracleTableName);
        ps.setRowPrefetch(PREFETCH_SIZE);
        ps.setOracleObject(1, new oracle.sql.NUMBER(cacheId));
        OracleResultSet rs = (OracleResultSet) ps.executeQuery();
        return new ConceptsIterator(rs);
    }

    /**
     * @param oracleTableName
     * @param cacheId
     * @return
     * @throws Exception
     */
    public EventsIterator getEvents(String oracleTableName, long cacheId) throws SQLException {
        OraclePreparedStatement ps = queryStatementByCacheId(oracleTableName);
        ps.setRowPrefetch(PREFETCH_SIZE);
        ps.setOracleObject(1, new oracle.sql.NUMBER(cacheId));
        OracleResultSet rs = (OracleResultSet) ps.executeQuery();
        return new EventsIterator(rs);
    }

    /**
     * @param cacheId
     * @return
     * @throws Exception
     */
    public KeyIterator ids(String oracleTableName, long cacheId) throws Exception {
        OraclePreparedStatement ps = (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT P.ENTITY.ID$ FROM " + oracleTableName + " P WHERE P.CACHEID=?");
        ps.setRowPrefetch(PREFETCH_SIZE);
        ps.setOracleObject(1, new oracle.sql.NUMBER(cacheId));
        OracleResultSet rs = (OracleResultSet) ps.executeQuery();
        return new KeyIterator(rs);
    }

    public KeyIterator classes(long cacheId) throws Exception {
        OraclePreparedStatement ps = (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT P.current_version.entityClass$ FROM BE_METADATA$ P WHERE P.current_version.cacheid$=?");
        ps.setOracleObject(1, new oracle.sql.NUMBER(cacheId));
        OracleResultSet rs = (OracleResultSet) ps.executeQuery();
        return new ClassIterator(rs);
    }

    public void deleteEntityByExtId(String tableName, String extId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        OraclePreparedStatement ps = deleteStatementByExtId(tableName, queryBuf);
        ps.setOracleObject(1, new oracle.sql.CHAR(extId, null));
        ps.executeUpdate();
        ps.close();
    }

    com.tibco.cep.kernel.model.entity.Entity createEntity(STRUCT oracleDatum) throws Exception {
        com.tibco.cep.kernel.model.entity.Entity entity = null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleEntityMap desc = (OracleEntityMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc instanceof OracleConceptMap) {
                entity = createConcept(oracleDatum);
            } else if (desc instanceof OracleEventMap) {
                entity = createEvent(oracleDatum);
            }
        }
        return entity;
    }

    protected ConceptDeserializer createConceptDeserializer(OracleConceptMap desc, OracleConnection oracle, oracle.sql.Datum[] attributes) {
        return new OracleConceptDeserializer(desc, oracle, attributes);
//        if ((cluster != null) && cluster.getClusterConfig().isNewSerializationEnabled()) {
//            return new OracleConceptDeserializer_NoNullProps(desc, oracle, attributes);
//        } else {
//            return new OracleConceptDeserializer(desc, oracle, attributes);
//        }
    }

    /**
     * @param oracleDatum
     * @return
     * @throws Exception
     */
    com.tibco.cep.runtime.model.element.Concept createConcept(STRUCT oracleDatum) throws Exception {
        com.tibco.cep.runtime.model.element.Concept cept = null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleConceptMap desc = (OracleConceptMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc != null) {
                Class conceptClz = desc.getEntityClass(rsp);
                ConceptDeserializer deser = createConceptDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                //OracleConceptDeserializer deser= new OracleConceptDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                cept = (com.tibco.cep.runtime.model.element.Concept) desc.newInstance(conceptClz, deser.getId(), deser.getExtId());
                cept.deserialize(deser);
            }
        }
        return cept;
    }

    StateMachineConcept createStateMachine(STRUCT oracleDatum) throws Exception {
        StateMachineConcept sm = null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleConceptMap desc = (OracleConceptMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc != null) {
                Class conceptClz = desc.getEntityClass(rsp);
                OracleStateMachineDeserializer deser = new OracleStateMachineDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                sm = (StateMachineConcept) desc.newInstance(conceptClz, deser.getId(), deser.getExtId());
                sm.deserialize(deser);
            }
        }
        return sm;
    }

    /**
     * @param predicate
     * @throws Exception
     */
    public void queryAndAssert(String predicate, RuleSession ruleSession) throws Exception {
        StringBuffer sqlQuery = new StringBuffer("SELECT P.ENTITY FROM " + entityTableName + " P ");
        if (predicate != null) {
            sqlQuery.append(predicate);
        }
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(sqlQuery.toString());
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
            com.tibco.cep.kernel.model.entity.Entity entity = createEntity(ret);
            if (entity != null) {
                ruleSession.assertObject(entity, true);
            }
        }
        rs.close();
        stmt.close();
    }

    public boolean entityExists(String tableName, long id, long cacheId) throws SQLException {
        OraclePreparedStatement stmt = verifyStatementById(tableName, false);
        stmt.setLong(1, id);
        //stmt.setLong(2, cacheId);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        oracle.sql.NUMBER ret = null;
        while (rs.next()) {
            ret = rs.getNUMBER(1);
        }
        rs.close();
        stmt.close();
        return (ret != null);
    }

    /**
     * @param tableName
     * @param cacheId
     * @return
     * @throws Exception
     */
    public Iterator keyPairs(String tableName, long cacheId) throws Exception {
        OraclePreparedStatement stmt = this.keysStatement(tableName);
        stmt.setLong(1, cacheId);
        stmt.setRowPrefetch(PREFETCH_SIZE);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        return new KeyPairIterator(rs);
    }

    public long countKeyPairs(String tableName, long cacheId) throws Exception {
        OraclePreparedStatement purge_stmt = purgeDeletedItemsStatement(tableName);
        purge_stmt.setLong(1, cacheId);
        purge_stmt.executeUpdate();
        purge_stmt.close();

        OraclePreparedStatement stmt = this.numHandlesStatement(tableName);
        stmt.setLong(1, cacheId);
        stmt.setRowPrefetch(PREFETCH_SIZE);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        long count = 0L;
        while (rs.next()) {
            count = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return count;
    }

    public com.tibco.cep.kernel.model.entity.Entity getEntityById(String tableName, long id, long cacheId) throws Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.kernel.model.entity.Entity entity = null;

        OraclePreparedStatement stmt = queryStatementById(tableName, true);
        stmt.setLong(1, id);
        stmt.setLong(2, cacheId);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        OracleEntityMap map = (OracleEntityMap) getOracleTypesMap(getSqlConnection()).get(ret.getSQLTypeName());

        if (map instanceof OracleConceptMap) {
            entity = createConcept(ret);
        } else {
            entity = createEvent(ret);
        }
        rs.close();
        stmt.close();
        return entity;
    }

    public com.tibco.cep.kernel.model.entity.Entity getEntityById(String tableName, long id) throws Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.kernel.model.entity.Entity entity = null;

        OraclePreparedStatement stmt = queryStatementById(tableName);
        stmt.setLong(1, id);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        OracleEntityMap map = (OracleEntityMap) getOracleTypesMap(getSqlConnection()).get(ret.getSQLTypeName());

        if (map instanceof OracleConceptMap) {
            entity = createConcept(ret);
        } else {
            entity = createEvent(ret);
        }
        rs.close();
        stmt.close();
        return entity;
    }

    public StateMachineConcept getStateMachineById(String tableName, long id, long cacheId) throws SQLException, Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        return (StateMachineConcept) getConceptById(tableName, id, cacheId);

    }

    public com.tibco.cep.runtime.model.element.Concept getConceptById(String tableName, long id, long cacheId) throws SQLException, Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.runtime.model.element.Concept cept = null;

        OraclePreparedStatement stmt = queryStatementById(tableName);
        stmt.setLong(1, id);
        //stmt.setLong(2, cacheId);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        int version = 1;
        while (rs.next()) {
            version = rs.getInt(1);
            ret = rs.getSTRUCT(2);
        }
        if (ret != null) {
            cept = createConcept(ret);
            ((ConceptImpl) cept).setVersion(version);
        } else {
            logger.log(Level.DEBUG, "No results found - %s, %s, %s.", tableName, id, cacheId);
        }
        rs.close();
        stmt.close();
        return cept;
    }

    public Map getConceptByIds(String tableName, Collection keys, long cacheId) throws Exception {
        int size = keys.size();
        if (size > IN_BATCH_SIZE) {
            HashMap concepts = new HashMap();
            List batches = getBatches(keys);
            Iterator it = batches.iterator();
            while (it.hasNext()) {
                List subset = (List) it.next();
                concepts.putAll(getSafeConceptByIds(tableName, subset, cacheId));
            }
            return concepts;
        } else {
            return getSafeConceptByIds(tableName, keys, cacheId);
        }
    }

    @SuppressWarnings("unused")
   private List getBatches(Collection keys) {
        int size = keys.size();
        List batches = new ArrayList();
        int batch = size / IN_BATCH_SIZE;
        Iterator it = keys.iterator();
        for (int i = 0; i < batch; i++) {
            int count = 0;
            List subset = new ArrayList();
            while (it.hasNext() && count < IN_BATCH_SIZE) {
                subset.add(it.next());
                count++;
            }
            batches.add(subset);
        }
        List remainder = new ArrayList();
        int count = 0;
        while (it.hasNext()) {
            remainder.add(it.next());
            count++;
        }
        if (count > 0) {
            // if there is a remainder add it
            batches.add(remainder);
        }
        return batches;
    }

    public Map getSafeConceptByIds(String tableName, Collection keys, long cacheId) throws Exception {
        OraclePreparedStatement stmt = queryStatementByIds(tableName, keys.size());
        stmt.setLong(1, cacheId);
        Iterator it = keys.iterator();
        int count = 2;
        HashMap concepts = new HashMap();
        while (it.hasNext()) {
            long id = ((Long) it.next()).longValue();
            stmt.setLong(count, id);
            count++;
        }
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        Iterator keyIterator = keys.iterator();
        while (rs.next()) {
            STRUCT s = rs.getSTRUCT(1);
            com.tibco.cep.runtime.model.element.Concept cept = createConcept(s);
            concepts.put(keyIterator.next(), cept);
        }
        rs.close();
        stmt.close();
        return concepts;
    }

    public com.tibco.cep.runtime.model.element.Concept getConceptById(String tableName, long id) throws Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.runtime.model.element.Concept cept = null;

        OraclePreparedStatement stmt = queryStatementById(tableName);
        stmt.setLong(1, id);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        cept = createConcept(ret);
        rs.close();
        stmt.close();
        return cept;
    }

    public com.tibco.cep.runtime.model.element.Concept getConceptByExtId(String tableName, String extId) throws Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.runtime.model.element.Concept cept = null;

        OraclePreparedStatement stmt = queryStatementByExtId(tableName);
        stmt.setString(1, extId);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        cept = createConcept(ret);
        rs.close();
        stmt.close();
        return cept;
    }

    private void setNull(OraclePreparedStatement stmt, OracleConceptMap desc, int oracleIndex, int stmtIndex) throws SQLException {
        PropertyMap pm = desc.getPropertyMapAt(oracleIndex);
        if (pm instanceof PropertyAtomSimpleMap) {
            int sqlTypeCode = ((PropertyAtomSimpleMap) pm).getSQLTypeCode();
            TypeDescriptor td = ((PropertyAtomSimpleMap) pm).getTypeDescriptor();
            if (td != null) {
                stmt.setNull(stmtIndex, sqlTypeCode, td.getTypeName());
            } else {
                stmt.setNull(stmtIndex, sqlTypeCode);
            }
        } else if (pm instanceof PropertyAtomMap) {
            //int sqlTypeCode=((PropertyAtomMap) pm).getSQLTypeCode();
            TypeDescriptor td = ((PropertyAtomMap) pm).getTypeDescriptor();
            if (td != null) {
                stmt.setNull(stmtIndex, Types.STRUCT, td.getTypeName());
            } else {
                throw new RuntimeException("Fatal Exception " + pm.getColumnName());
            }
        } else if (pm instanceof PropertyArrayMap) {
            //int sqlTypeCode=((PropertyArrayMap) pm).getSQLTypeCode();
            ArrayDescriptor td = (ArrayDescriptor) ((PropertyArrayMap) pm).getTypeDescriptor();
            //stmt.setArray(stmtIndex,null);
            //stmt.setNull(stmtIndex, sqlTypeCode, td.getTypeName());
            stmt.setArray(stmtIndex, new oracle.sql.ARRAY(td, stmt.getConnection(), null));
        } else {
            if (oracleIndex == 1) { //extId
                stmt.setNull(stmtIndex, Types.VARCHAR);
            } else {
                logger.log(Level.ERROR, "Unknown property type");
                throw new RuntimeException("Fatal Exception " + pm.getColumnName());
            }
        }
    }

    protected OracleSerializer createConceptSerializer(OracleConceptMap desc, OracleConnection oracle, String qualifier) throws Exception {
        return new OracleConceptSerializerImpl(desc, oracle, qualifier);
//        if ((cluster != null) && cluster.getClusterConfig().isNewSerializationEnabled()) {
//            return new OracleConceptSerializer_NoNullProps(desc, oracle, qualifier);
//        } else {
//            return new OracleConceptSerializerImpl(desc, oracle, qualifier);
//        }
    }

    public LinkedHashMap getClassRegistry() throws SQLException {
        LinkedHashMap classRegistry = new LinkedHashMap();
        OraclePreparedStatement getStmt = (OraclePreparedStatement) this.getSqlConnection().prepareStatement("SELECT className, typeId FROM ClassRegistry");
        ResultSet rs = getStmt.executeQuery();
        while (rs.next()) {
            String entityClz = (String) rs.getString(1);
            Integer typeId = (Integer) rs.getInt(2);
            classRegistry.put(entityClz, typeId);
        }
        rs.close();
        getStmt.close();
        return classRegistry;
    }

    public void saveClassRegistry(Map classRegistry) throws SQLException {
        // Delete the existing registry
        OraclePreparedStatement delStmt = (OraclePreparedStatement) this.getSqlConnection().prepareStatement("DELETE FROM ClassRegistry");
        delStmt.executeUpdate();
        delStmt.close();
        // Insert complete list
        Iterator allClasses = classRegistry.entrySet().iterator();
        while (allClasses.hasNext()) {
            Map.Entry entry = (Map.Entry) allClasses.next();
            String entityClz = (String) entry.getKey();
            Integer typeId = (Integer) entry.getValue();
            OraclePreparedStatement stmt = (OraclePreparedStatement) this.getSqlConnection().prepareStatement("INSERT INTO ClassRegistry (className, typeId) VALUES (?,?)");
            stmt.setString(1, entityClz);
            stmt.setInt(2, typeId);
            stmt.executeUpdate();
            stmt.close();
        }
    }
    
    public void saveClassRegistration(String entityClz, int typeId) throws SQLException {
        OraclePreparedStatement delStmt = (OraclePreparedStatement) this.getSqlConnection().prepareStatement("DELETE FROM ClassRegistry WHERE className = ?");
        delStmt.setString(1, entityClz);
        delStmt.executeUpdate();
        delStmt.close();
        OraclePreparedStatement stmt = (OraclePreparedStatement) this.getSqlConnection().prepareStatement("INSERT INTO ClassRegistry (className, typeId) VALUES (?,?)");
        stmt.setString(1, entityClz);
        stmt.setInt(2, typeId);
        stmt.executeUpdate();
        stmt.close();
        releaseConnection();
    }

    /**
     * @param time
     * @throws Exception
     */
    public void purgeDeletedObjects(long time) throws Exception {
        OraclePreparedStatement stmt = purgeObjectTableStatement();
        stmt.setLong(1, time);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * @param tuple
     * @return
     * @throws Exception
     */
    public boolean entryExistsInObjectTable(ObjectTupleImpl tuple) throws Exception {
        OraclePreparedStatement stmt = this.getObjectTableStatement();
        stmt.setLong(1, tuple.getId());
        ResultSet rs = stmt.executeQuery();
        boolean found = false;
        while (rs.next()) {
            found = true;
        }
        rs.close();
        stmt.close();
        return found;
    }

    public void saveObjectTable(Map<Long, ObjectTable.Tuple> tuples) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        OraclePreparedStatement stmt = this.insertObjectTableStatement(queryBuf);
        stmt.setExecuteBatch(tuples.size());
        try {
            Iterator entrySet = tuples.entrySet().iterator();
            while (entrySet.hasNext()) {
                Map.Entry entry = (Map.Entry) entrySet.next();
                ObjectTable.Tuple tuple = (ObjectTable.Tuple) entry.getValue();
                stmt.setLong(1, tuple.getId());
                stmt.setLong(2, EntityIdMask.getMaskedId(tuple.getId()));
                stmt.setLong(3, EntityIdMask.getEntityId(tuple.getId()));
                if (tuple.getExtId() != null) {
                    stmt.setString(4, tuple.getExtId());
                } else {
                    stmt.setNull(4, java.sql.Types.VARCHAR);
                }
                stmt.setString(5, cluster.getMetadataCache().getClass(tuple.getTypeId()).getName());
                stmt.setInt(6, tuple.isDeleted() ? 1 : 0);
                if (tuple.isDeleted()) {
                    stmt.setLong(7, tuple.getTimeDeleted());
                } else {
                    stmt.setNull(7, java.sql.Types.NUMERIC);
                }
                if (sqltext.isEnabledFor(Level.TRACE)) {
                    sqltext.log(Level.TRACE, "%s (%s, %s, %s, %s, %s, %s, %s)",
                            queryBuf,
                            tuple.getId(),
                            EntityIdMask.getMaskedId(tuple.getId()),
                            EntityIdMask.getEntityId(tuple.getId()),
                            tuple.getExtId(),
                            this.cluster.getMetadataCache().getClass(tuple.getTypeId()).getName(),
                            (tuple.isDeleted() ? 1 : 0),
                            (tuple.isDeleted() ? tuple.getTimeDeleted() : null));
                }

                stmt.executeUpdate();
            }
            stmt.sendBatch();
        } catch (Exception ex) {
            //rollback();
            throw ex;
        } finally {
            stmt.close();
        }
    }

    //INSERT INTO OBJECTTABLE (SITEID, ID, EXTID, ISDELETED) VALUES (?,?,?,?)
    public void insertObjectTable(ObjectTupleImpl tuple) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        OraclePreparedStatement stmt = this.insertObjectTableStatement(queryBuf);
        try {
            stmt.setLong(1, tuple.getId());
            stmt.setLong(2, EntityIdMask.getMaskedId(tuple.getId()));
            stmt.setLong(3, EntityIdMask.getEntityId(tuple.getId()));
    
            if (tuple.getExtId() != null) {
                stmt.setString(4, tuple.getExtId());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }
    
            stmt.setString(5, cluster.getMetadataCache().getClass(tuple.getTypeId()).getName());
            stmt.setInt(6, tuple.isDeleted() ? 1 : 0);
            if (tuple.isDeleted()) {
                stmt.setLong(7, tuple.getTimeDeleted());
            } else {
                stmt.setNull(7, java.sql.Types.NUMERIC);
            }
            if (sqltext.isEnabledFor(Level.TRACE)) {
                sqltext.log(Level.TRACE, "%s (%s, %s, %s, %s, %s, %s, %s)",
                        queryBuf,
                        tuple.getId(),
                        EntityIdMask.getMaskedId(tuple.getId()),
                        EntityIdMask.getEntityId(tuple.getId()),
                        tuple.getExtId(),
                        this.cluster.getMetadataCache().getClass(tuple.getTypeId()).getName(),
                        (tuple.isDeleted() ? 1 : 0),
                        (tuple.isDeleted() ? tuple.getTimeDeleted() : null));
            }
            stmt.executeUpdate();
        } catch (Exception ex) {
            //rollback();
            throw ex;
        } finally {
            stmt.close();
        }
    }

    /**
     * @param siteId
     * @param typeId
     * @param maxRows
     * @return
     * @throws Exception
     */
    public Iterator recoverType(long siteId, int typeId, int maxRows) throws Exception {
        String clzName = cluster.getMetadataCache().getClass(typeId).getName();
        OraclePreparedStatement stmt = null;
        if (cluster.getClusterConfig().useObjectTable() && !cluster.getClusterConfig().isCacheAside()) {
            stmt = this.getRecoveryStatement();
            stmt.setString(1, clzName);
            if (multisite) {
                stmt.setLong(2, siteId);
            }
        } else {
            stmt = getRecoveryStatementUsingBaseTables(clzName);
        }
        stmt.setRowPrefetch(PREFETCH_SIZE);
        if (maxRows > 0) {
            stmt.setMaxRows(maxRows);
        }
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        if (cluster.getClusterConfig().useObjectTable() && !cluster.getClusterConfig().isCacheAside()) {
            return new ObjectTableIterator(rs);
        } else {
            return new ObjectTableIteratorUsingBaseTables(rs, typeId);
        }
    }

    public Set recoverIds(long siteId, int typeId, int maxRows) throws Exception {
        HashSet ret = new HashSet(maxRows);
        String clzName = cluster.getMetadataCache().getClass(typeId).getName();
        OraclePreparedStatement stmt = null;
        if (cluster.getClusterConfig().useObjectTable()) {
            stmt = this.getLoadStatement();
            stmt.setString(1, clzName);
            if (multisite) {
                stmt.setLong(2, siteId);
            }
        } else {
            stmt = this.getLoadStatementUsingBaseTables(clzName);
        }
        stmt.setRowPrefetch(PREFETCH_SIZE);
        if (maxRows > 0) {
            stmt.setMaxRows(maxRows);
        }
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        while (rs.next()) {
            ret.add(rs.getLong(1));
        }
        rs.close();
        stmt.close();
        return ret;
    }

    /**
     * @param siteId
     * @param typeId
     * @throws Exception
     */
    public void purgeDeletedEntities(long siteId, int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = null;
        if (entityClz != null) {
            tableName = generatedOracleTableName(entityClz.getName());
            if (tableName != null) {
                logger.log(Level.INFO, "Purging entries marked as deleted from: %s for class %s", tableName, entityClz.getName());

                String statement = "";
                if (multisite) {
                    statement = "DELETE FROM " + tableName + " T WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 AND CLASSNAME=? AND SITEID=?)";
                } else {
                    statement = "DELETE FROM " + tableName + " T WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 AND CLASSNAME=?)";
                }

                OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(statement);
                stmt.setString(1, cluster.getMetadataCache().getClass(typeId).getName());
                if (multisite) {
                    stmt.setLong(2, siteId);
                }
                stmt.executeUpdate();
                stmt.close();
            } else {
                logger.log(Level.INFO, "Unable to purge, table not defined: %s", entityClz.getName());
            }
        } else {
            logger.log(Level.INFO, "Unable to purge, type not defined: %s", typeId);
        }
    }

    public void purgeStateMachineTimeouts(long siteId) throws Exception {
        String tableName = generatedOracleTableName(StateMachineConceptImpl.StateTimeoutEvent.class.getName());
        String statement = "";
        if (multisite) {
            statement = "DELETE FROM OBJECTTABLE WHERE CLASSNAME = ? AND SITEID = ? AND GLOBALID NOT IN (SELECT ID FROM " + tableName + ")";
        } else {
            statement = "DELETE FROM OBJECTTABLE WHERE CLASSNAME = ? AND GLOBALID NOT IN (SELECT ID FROM " + tableName + ")";
        }
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(statement);
        stmt.setString(1, StateMachineConceptImpl.StateTimeoutEvent.class.getName());
        if (multisite) {
            stmt.setLong(2, siteId);
        }
        stmt.executeUpdate();
        stmt.close();
    }

    public void migrateToObjectTable(long siteId, int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Migrating entries to ObjectTable from %s", tableName);

            OraclePreparedStatement stmt = this.migrateToObjectTableStatement(tableName);
            stmt.setLong(1, siteId);
            stmt.setString(2, cluster.getMetadataCache().getClass(typeId).getName());
            if (multisite) {
                stmt.setLong(3, siteId);
            }
            stmt.executeUpdate();
            stmt.close();
        } else {
            logger.log(Level.INFO, "Unable to migrate, table not defined - %s", entityClz);
        }
    }

    public void deleteObjectTable(long id) throws SQLException {
        OraclePreparedStatement stmt = this.deleteObjectTableStatement();
        stmt.setLong(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public void updateObjectTable(long id) throws SQLException {
        OraclePreparedStatement stmt = this.updateObjectTableStatement();
        stmt.setLong(1, System.currentTimeMillis());
        stmt.setLong(2, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public ObjectTupleImpl getObjectTable(long id) throws Exception {
        OraclePreparedStatement stmt = this.getObjectTableStatementWithId();
        //SELECT SITEID, ID, EXTID  FROM OBJECTTABLE  WHERE SITEID=? AND ID=? AND ISDELETED=0
        stmt.setLong(1, id);
        stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        while (rs.next()) {
            String extId = rs.getString(1);
            String className = rs.getString(2);
            tuple = new ObjectTupleImpl(id, extId, cluster.getMetadataCache().getTypeId(className));
        }
        rs.close();
        stmt.close();
        return tuple;
    }

    public ObjectTupleImpl getObjectTable(String extId) throws Exception {
        OraclePreparedStatement stmt = this.getObjectTableStatementWithExtId();
        //SELECT SITEID, ID, CLASSNAME FROM OBJECTTABLE  WHERE EXTID=? AND ISDELETED=0
        stmt.setString(1, extId);
        stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        while (rs.next()) {
            long id = rs.getLong(1);
            String className = rs.getString(2);
            tuple = new ObjectTupleImpl(id, extId, cluster.getMetadataCache().getTypeId(className));
        }
        rs.close();
        stmt.close();
        return tuple;
    }

    public long getMaxEntityId(long siteId) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        OraclePreparedStatement stmt = this.getMaxEntityIdStatement();
        if (multisite) {
            stmt.setLong(1, siteId);
            sqltext.log(Level.TRACE, "%s (%s)", queryBuf, siteId);
        } else {
            sqltext.log(Level.TRACE, "%s", queryBuf);
        }

        ResultSet rs = stmt.executeQuery();
        long maxId = 0L;

        while (rs.next()) {
            maxId = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return maxId;
    }

//    public boolean existsInObjectTable(long id) throws SQLException {
//        OraclePreparedStatement stmt=getObjectTableStatement();
//        stmt.setLong(1, id);
//        ResultSet rs=stmt.executeQuery();
//        int num=0;
//        while (rs.next()) {
//            num=rs.getInt(1);
//        }
//        rs.close();
//        stmt.close();
//        return (num > 0)? true: false;
//    }

    /**
     * @param cept
     * @throws Exception
     */
    public void insertConcept(String tableName, com.tibco.cep.runtime.model.element.Concept cept) throws Exception {
        insertConcept(tableName, cept, -1);
    }

    public void insertConcept(String tableName, com.tibco.cep.runtime.model.element.Concept cept, long cacheId) throws Exception, SQLException {
        OracleSerializer serializer = null;
        try {
            OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(cept.getClass().getName());
            if (desc != null) {

                serializer = createConceptSerializer(desc, getSqlConnection(), getQualifier());
                serializer = new OracleConceptSerializerImpl(desc, getSqlConnection(), getQualifier());
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }

                Datum[] attrs = serializer.getOracleAttributes();
                OraclePreparedStatement stmt = insertStatement(tableName, attrs, desc.getTypeDescriptor());
                stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) cept).getVersion()));
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 2, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 2);
                    }
                }
                try {
                    stmt.executeUpdate();
                } catch (Exception e) {
                    logger.log(Level.ERROR, e, e.getMessage());
                }
                stmt.close();
            } else {
                throw new Exception("Concept " + cept + " Not Registered With the Oracle Adapter");
            }
        }
        finally {
            if (serializer != null)
                serializer.release();
        }
    }

    public void insertStateMachine(String tableName, StateMachineConcept sm) throws Exception, SQLException {
        OracleStateMachineSerializer serializer = null;
        OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(sm.getClass().getName());
        if (desc != null) {
            serializer = new OracleStateMachineSerializer(desc, getSqlConnection(), getQualifier());
            sm.serialize(serializer);
            if (serializer.error) {
                throw new Exception("Error in Serialization of " + sm + " with message " + serializer.msg);
            }

            Datum[] attrs = serializer.getOracleAttributes();
            OraclePreparedStatement stmt = insertStatement(tableName, attrs, desc.getTypeDescriptor());
            stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) sm).getVersion()));
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] != null) {
                    stmt.setOracleObject(i + 2, attrs[i]);
                } else {
                    setNull(stmt, desc, i, i + 2);
                }
            }
            try {
                stmt.executeUpdate();
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
            }
            stmt.close();
        } else {
            throw new Exception("StateMachine " + sm + " Not Registered With the Oracle Adapter");
        }
    }

    public void insertEvents(String className, String tableName, Map<Long, Event> entries) throws Exception {
        List serializers = new ArrayList();
        OracleEventMap desc = (OracleEventMap) getEntityPropsMap(getSqlConnection()).get(className);
        try {
            OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(desc.getInsertStatement());
            stmt.setExecuteBatch(entries.size());

            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, Event> entry = (Map.Entry<Long, Event>) all_entries.next();
                @SuppressWarnings("unused")
                Long id = entry.getKey();
                Event event = entry.getValue();
                OracleEventSerializer_V2 serializer = new OracleEventSerializer_V2(desc, stmt, 2, getSqlConnection(), getQualifier(), useExplicitTemporaryBlobs);
                serializers.add(serializer);
                //Datum[] attrs = serializer.getOracleAttributes();
                stmt.setOracleObject(1, new oracle.sql.NUMBER(1));
                if (event instanceof SimpleEvent) {
                    ((SimpleEvent) event).serialize(serializer);
                } else {
                    ((TimeEvent) event).serialize(serializer);
                }
                logEvent(desc.getInsertStatement(), event);

//                    for (int i = 0; i < attrs.length; i++) {
//                        if (attrs[i] != null) {
//                            stmt.setOracleObject(i+2, attrs[i]);
//                        } else {
//                            if (i ==1) {
//                                stmt.setNull(i+2, Types.VARCHAR);
//                            } else {
//                                OracleType oracleType = desc.getOracleType(i);
//                                if (oracleType instanceof OracleTypeADT ) {
//                                    OracleTypeADT t = (OracleTypeADT) oracleType;
//                                    stmt.setNull(i+2, Types.STRUCT, t.getFullName());
//                                } else {
//                                    if (oracleType != null) {
//                                        stmt.setNull(i+2, oracleType.getTypeCode());
//                                    } else {
//                                        logger.log(Level.ERROR, "Type not found for attribute index " + i + " for type " + tableName);
//                                        throw new RuntimeException("Type Not Found for attribute index " + i + " for type " + tableName);
//                                    }
//                                }
//                            }
//                        }
//                    }

                stmt.executeUpdate();
            }
            stmt.sendBatch();
            stmt.close();
        } catch (Exception ex) {
            throw ex;
        } finally {
            for (int i = 0; i < serializers.size(); i++) {
                ((OracleEventSerializer_V2) serializers.get(i)).releaseLobTempSpace();
            }
        }
    }

    public void insertStateMachines(String className, Map entries) throws Exception {
        OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(className);
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(desc.getInsertStatement());
        stmt.setExecuteBatch(entries.size());

        if (desc != null) {
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, StateMachineConcept> entry = (Map.Entry<Long, StateMachineConcept>) all_entries.next();
                Long id = entry.getKey();
                StateMachineConcept sm = entry.getValue();
                OracleStateMachineSerializer serializer = new OracleStateMachineSerializer(desc, getSqlConnection(), getQualifier());
                sm.serialize(serializer);
                Datum[] attrs = serializer.getOracleAttributes();
                stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) sm).getVersion()));
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 2, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 2);
                    }
                }
                stmt.executeUpdate();
            }
            stmt.sendBatch();
            stmt.close();
        }
    }

    public void insertConcepts(String className, Map<Long, Concept> entries) throws Exception {
        OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(className);
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(desc.getInsertStatement());
        stmt.setExecuteBatch(entries.size());

        if (desc != null) {
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, Concept> entry = (Map.Entry<Long, Concept>) all_entries.next();
                Long id = entry.getKey();
                Concept cept = entry.getValue();
                OracleSerializer serializer = createConceptSerializer(desc, getSqlConnection(), getQualifier());
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }
                logConcept(desc.getInsertStatement(), cept, null);
                Datum[] attrs = serializer.getOracleAttributes();
                stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) cept).getVersion()));
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 2, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 2);
                    }
                }
                stmt.executeUpdate();
            }
            stmt.sendBatch();
            stmt.close();
        }
    }

    public void modifyStateMachines(String className, Map entries) throws Exception {
        OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(className);
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(desc.getUpdateStatement());
        stmt.setExecuteBatch(entries.size());

        if (desc != null) {
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, StateMachineConcept> entry = (Map.Entry<Long, StateMachineConcept>) all_entries.next();
                Long id = entry.getKey();
                StateMachineConcept sm = entry.getValue();
                OracleStateMachineSerializer serializer = new OracleStateMachineSerializer(desc, getSqlConnection(), getQualifier());
                sm.serialize(serializer);
                Datum[] attrs = serializer.getOracleAttributes();
                stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) sm).getVersion()));
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 2, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 2);
                    }
                }
                stmt.setOracleObject(attrs.length + 2, attrs[0]);
                stmt.executeUpdate();
            }
            stmt.sendBatch();
            stmt.close();
        }
    }

    public void modifyConcepts(String className, Map<Long, Concept> entries) throws Exception {
        OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(className);
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(desc.getUpdateStatement());
        stmt.setExecuteBatch(entries.size());
        logger.log(Level.DEBUG, "Modify concepts() for %s, number of entries=%s", className, entries.size());
        if (desc != null) {
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, Concept> entry = (Map.Entry<Long, Concept>) all_entries.next();
                Long id = entry.getKey();
                Concept cept = entry.getValue();
                OracleSerializer serializer = createConceptSerializer(desc, getSqlConnection(), getQualifier());
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }
                logConcept(desc.getUpdateStatement(), cept, null);
                Datum[] attrs = serializer.getOracleAttributes();
                stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) cept).getVersion()));
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 2, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 2);
                    }
                }
                stmt.setOracleObject(attrs.length + 2, attrs[0]);
                //stmt.setLong(attrs.length);
                stmt.executeUpdate();
            }
            stmt.sendBatch();
            stmt.close();
        }
    }

    /**
     * @param entries
     * @throws Exception
     */
    public void removeObjectTable(Set<Long> entries) throws Exception {
        if (entries.size() > DELETE_IN_LIMIT) {
            int i = 0;
            StringBuffer query = new StringBuffer("DELETE FROM OBJECTTABLE WHERE GLOBALID IN (");
            Iterator all_entries = entries.iterator();
            boolean first = true;
            while (all_entries.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    query.append(" , ");
                }
                query.append(all_entries.next());
                if (++i == DELETE_IN_LIMIT) {
                    query.append(" ) ");
                    final String queryString = query.toString();
                    final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
                    sqltext.log(Level.TRACE, queryString);
                    stmt.executeUpdate();
                    stmt.close();
                    first = true;
                    i = 0;
                    query = new StringBuffer("DELETE FROM OBJECTTABLE WHERE GLOBALID IN (");
                }
            }
            if (!first) {
                query.append(" ) ");
                final String queryString = query.toString();
                final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
                sqltext.log(Level.TRACE, queryString);
                stmt.executeUpdate();
                stmt.close();
            }
        } else {
            StringBuffer query = new StringBuffer("DELETE FROM OBJECTTABLE WHERE GLOBALID IN (");
            Iterator all_entries = entries.iterator();
            boolean first = true;
            while (all_entries.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    query.append(" , ");
                }
                query.append(all_entries.next());
            }
            query.append(" ) ");
            final String queryString = query.toString();
            final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
            sqltext.log(Level.TRACE, queryString);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public void removeObjectTableInBatch(Set<Long> entries) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = getSqlConnection().prepareStatement("DELETE FROM OBJECTTABLE WHERE GLOBALID = ?");
            sqltext.log(Level.TRACE, "Delete in batch OBJECTTABLE - %s", "DELETE FROM OBJECTTABLE WHERE GLOBALID = ?");
            for (Iterator itr = entries.iterator(); itr.hasNext();) {
                String id = itr.next().toString();
                stmt.setLong(1, new Long(id));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }

    /**
     * @param tableName
     * @param entries
     * @throws Exception
     */
    public void deleteEntities(String tableName, Set<Long> entries) throws Exception {
        if (entries.size() > DELETE_IN_LIMIT) {
            int i = 0;
            StringBuffer query = new StringBuffer("DELETE FROM " + tableName + " T ");
            query.append(" WHERE T.ENTITY.ID$ IN (");
            Iterator all_entries = entries.iterator();
            boolean first = true;
            while (all_entries.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    query.append(" , ");
                }
                query.append(all_entries.next());
                if (++i == DELETE_IN_LIMIT) {
                    query.append(" ) ");
                    final String queryString = query.toString();
                    final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
                    sqltext.log(Level.TRACE, queryString);
                    stmt.executeUpdate();
                    stmt.close();
                    query = new StringBuffer("DELETE FROM " + tableName + " T ");
                    query.append(" WHERE T.ENTITY.ID$ IN (");
                    first = true;
                    i = 0;
                }
            }
            if (!first) {
                query.append(" ) ");
                final String queryString = query.toString();
                final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
                sqltext.log(Level.TRACE, queryString);
                stmt.executeUpdate();
                stmt.close();
            }
        } else {
            StringBuffer query = new StringBuffer("DELETE FROM " + tableName + " T ");
            query.append(" WHERE T.ENTITY.ID$ IN (");
            Iterator all_entries = entries.iterator();
            boolean first = true;
            while (all_entries.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    query.append(" , ");
                }
                query.append(all_entries.next());
            }
            query.append(" ) ");
            final String queryString = query.toString();
            final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
            sqltext.log(Level.TRACE, "%s", queryString);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public void deleteEntitiesInBatch(String tableName, Set<Long> entries) throws Exception {
        StringBuffer query = new StringBuffer("DELETE FROM " + tableName + " T WHERE T.ENTITY.ID$=?");
        sqltext.log(Level.TRACE, query.toString());
        OraclePreparedStatement stmt = null;
        try {
            stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(query.toString());
            for (Iterator itr = entries.iterator(); itr.hasNext();) {
                String id = itr.next().toString();
                stmt.setOracleObject(1, new oracle.sql.NUMBER(id));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } 
        finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }

    public void updateStateMachine(String tableName, StateMachineConcept sm) throws Exception, SQLException {
        OracleStateMachineSerializer serializer = null;
        OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(sm.getClass().getName());
        if (desc != null) {
            serializer = new OracleStateMachineSerializer(desc, getSqlConnection(), getQualifier());
            sm.serialize(serializer);
            if (serializer.error) {
                throw new Exception("Error in Serialization of " + sm + " with message " + serializer.msg);
            }

            Datum[] attrs = serializer.getOracleAttributes();
            OraclePreparedStatement stmt = updateStatement(tableName, attrs, desc.getTypeDescriptor(), false);
            stmt.setInt(1, ((VersionedObject) sm).getVersion());
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] != null) {
                    stmt.setOracleObject(i + 2, attrs[i]);
                } else {
                    setNull(stmt, desc, i, i + 2);
                }
            }
            stmt.setOracleObject(attrs.length + 2, attrs[0]);
            //stmt.setOracleObject(attrs.length+2, new oracle.sql.NUMBER(cacheId));
            stmt.executeUpdate();
            stmt.close();
        } else {
            throw new Exception("StateMachine " + sm + " Not Registered With the Oracle Adapter");
        }
    }

    public void updateConcept(String tableName, com.tibco.cep.runtime.model.element.Concept cept, long cacheId) throws Exception, SQLException {
        OracleSerializer serializer = null;
        try {
            OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(cept.getClass().getName());
            if (desc != null) {
                serializer = createConceptSerializer(desc, getSqlConnection(), getQualifier());
                serializer = new OracleConceptSerializerImpl(desc, getSqlConnection(), getQualifier());
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }

                Datum[] attrs = serializer.getOracleAttributes();
                OraclePreparedStatement stmt = updateStatement(tableName, attrs, desc.getTypeDescriptor(), false);
                stmt.setInt(1, ((VersionedObject) cept).getVersion());
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 2, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 2);
                    }
                }
                stmt.setOracleObject(attrs.length + 2, attrs[0]);
                //stmt.setOracleObject(attrs.length+2, new oracle.sql.NUMBER(cacheId));
                stmt.executeUpdate();
                stmt.close();
            } else {
                throw new Exception("Concept " + cept + " Not Registered With the Oracle Adapter");
            }
        }
        finally {
            if (serializer != null)
                serializer.release();
        }
    }

    /**
     * @param cept
     * @throws Exception
     */
    public void updateConcept(String tableName, com.tibco.cep.runtime.model.element.Concept cept) throws Exception {
        OracleSerializer serializer = null;
        try {
            OracleConceptMap desc = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(cept.getClass().getName());
            if (desc != null) {
                serializer = createConceptSerializer(desc, getSqlConnection(), getQualifier());
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }

                Datum[] attrs = serializer.getOracleAttributes();
                OraclePreparedStatement stmt = updateStatement(tableName, attrs, desc.getTypeDescriptor());
                for (int i = 0; i < attrs.length; i++) {
                    if (attrs[i] != null) {
                        stmt.setOracleObject(i + 1, attrs[i]);
                    } else {
                        setNull(stmt, desc, i, i + 1);
                    }
                }
                stmt.setOracleObject(attrs.length + 1, attrs[0]);
                stmt.executeUpdate();
                stmt.close();
            } else {
                throw new Exception("Concept " + cept + " not registered with the Oracle adapter");
            }
        }
        finally {
            if (serializer != null)
                serializer.release();
        }
    }

    com.tibco.cep.kernel.model.entity.Event createEvent(STRUCT oracleDatum) throws Exception {
        com.tibco.cep.kernel.model.entity.Event event = null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleEventMap desc = (OracleEventMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc != null) {
                Class eventClz = desc.getEntityClass(rsp);
                OracleEventDeserializer deser = new OracleEventDeserializer(rsp, desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                event = (com.tibco.cep.kernel.model.entity.Event) desc.newInstance(eventClz, deser.getId(), deser.getExtId());
                if (event instanceof SimpleEvent) {
                    ((SimpleEvent) event).deserialize(deser);
                } else {
                    ((TimeEvent) event).deserialize(deser);
                }
            }
        }
        return event;
    }

    public com.tibco.cep.kernel.model.entity.Event getEventById(String tableName, long id, long cacheId) throws Exception {
        com.tibco.cep.kernel.model.entity.Event event = null;

        OraclePreparedStatement stmt = queryStatementById(tableName, true);
        stmt.setLong(1, id);
        stmt.setLong(2, cacheId);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        event = createEvent(ret);
        rs.close();
        stmt.close();
        return event;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public com.tibco.cep.kernel.model.entity.Event getEventById(String tableName, long id) throws Exception {
        com.tibco.cep.kernel.model.entity.Event event = null;

        OraclePreparedStatement stmt = queryStatementById(tableName);
        stmt.setLong(1, id);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        event = createEvent(ret);
        rs.close();
        stmt.close();
        return event;
    }

    /**
     * @param extId
     * @return
     * @throws Exception
     */
    public com.tibco.cep.kernel.model.entity.Event getEventByExtId(String tableName, String extId) throws Exception {
        com.tibco.cep.kernel.model.entity.Event event = null;

        OraclePreparedStatement stmt = queryStatementByExtId(tableName);
        stmt.setString(1, extId);
        stmt.setQueryTimeout(queryTimeout);
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        STRUCT ret = null;
        while (rs.next()) {
            ret = rs.getSTRUCT(1);
        }
        event = createEvent(ret);
        rs.close();
        stmt.close();
        return event;
    }

    /**
     * @param event
     * @throws Exception
     */
    public void insertEvent(String tableName, com.tibco.cep.kernel.model.entity.Event event) throws Exception {
        insertEvent(tableName, event, -1);
    }

    /**
     * @param event
     * @param cacheId
     * @throws Exception
     */
    public void insertEvent(String tableName, com.tibco.cep.kernel.model.entity.Event event, long cacheId) throws Exception {
        OracleEventSerializer_V2 serializer = null;
        try {
            OracleEventMap desc = (OracleEventMap) getEntityPropsMap(getSqlConnection()).get(event.getClass().getName());
            if (desc != null) {
                OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(desc.getInsertStatement());
                serializer = new OracleEventSerializer_V2(desc, stmt, 2, getSqlConnection(), getQualifier(), useExplicitTemporaryBlobs);
                //Datum[] attrs = serializer.getOracleAttributes();
                //OraclePreparedStatement stmt=insertStatement(tableName, attrs, desc.getTypeDescriptor());
                stmt.setOracleObject(1, new oracle.sql.NUMBER(cacheId));
                if (event instanceof SimpleEvent) {
                    ((SimpleEvent) event).serialize(serializer);
                } else {
                    ((TimeEvent) event).serialize(serializer);
                }

//            for (int i = 0; i < attrs.length; i++) {
//                if (attrs[i] != null) {
//                    stmt.setOracleObject(i+2, attrs[i]);
//                } else {
//                    if (i ==1) {
//                        stmt.setNull(i+2, Types.VARCHAR);
//                    } else {
//                        OracleType oracleType = desc.getOracleType(i);
//                        if (oracleType instanceof OracleTypeADT ) {
//                            OracleTypeADT t = (OracleTypeADT) oracleType;
//                            stmt.setNull(i+2, Types.STRUCT, t.getFullName());
//                        } else {
//                            if (oracleType != null) {
//                                stmt.setNull(i+2, oracleType.getTypeCode());
//                            } else {
//                                logger.log(Level.ERROR, "Type not found for attribute index " + i + " for type " + tableName);
//                                throw new RuntimeException("Type not found for attribute index " + i + " for type " + tableName);
//                            }
//                        }
//                    }
//                }
//            }

                stmt.executeUpdate();
                stmt.close();
            } else {
                throw new Exception("Event " + event + " not registered with the Oracle adapter");
            }
        } finally {
            //Release temp-table space
            if (serializer != null)
                serializer.releaseLobTempSpace();
        }
    }

//    public void insertEvents(String tableName, long cacheId, Collection events) throws Exception {
//        if (events == null || events.size() <= 0) return;
//        Iterator itr = events.iterator();
//        while (itr.hasNext()) {
//            EventAdapter eventAdapter = (EventAdapter)itr.next();
//            if (eventAdapter.isRecovered()) {
//                return;
//            }
//            try {
//                this.insertEvent(tableName, eventAdapter.getEvent(), cacheId);
//            } catch (Exception ex) {
//                logger.log(Level.ERROR, ex, "Error in inserting Event %s to table %s", eventAdapter.getEvent(), tableName);
//                throw ex;
//            }
//        }
//    }

    public void insertEvents(String tableName, long cacheId, Collection events) throws Exception {
        if (events == null || events.size() <= 0) return;
        Iterator itr = events.iterator();
        while (itr.hasNext()) {
            com.tibco.cep.kernel.model.entity.Event event = (com.tibco.cep.kernel.model.entity.Event) itr.next();
            try {
                this.insertEvent(tableName, event, cacheId);
            } catch (Exception ex) {
                logger.log(Level.ERROR, ex, "Error in inserting Event %s to table %s", event, tableName);
                throw ex;
            }
        }
    }

    // These functions are accessed by ClusterSequenceFunctions
    public long nextSequence(String sequenceName) throws Exception {
        OracleConnection connection = this.getSqlConnection();
        String query = " SELECT  " + sequenceName.toUpperCase() + ".nextVal from DUAL ";
        sqltext.log(Level.TRACE, "Next sequence " + query);
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        long nextVal = -1;

        while (rs.next()) {
            nextVal = rs.getLong(1);
        }

        rs.close();
        stmt.close();
        return nextVal;
    }

    public void removeSequence(String sequenceName) throws SQLException {
        OracleConnection connection = this.getSqlConnection();
        String query = " DROP SEQUENCE " + sequenceName + " ";
        sqltext.log(Level.TRACE, "Remove sequence " + query);
        OraclePreparedStatement stmt= (OraclePreparedStatement) connection.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();
    }

    public void createSequence(String sequenceName, long minValue, long maxValue, long start, int increment) throws SQLException {
        OracleConnection connection = this.getSqlConnection();
        boolean sequenceExists = false;
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement("SELECT sequence_name from all_sequences where sequence_name = ?");
        stmt.setString(1, sequenceName.toUpperCase());

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            sequenceExists = true;
        }
        rs.close();
        stmt.close();
        if (!sequenceExists) {
            StringBuffer query = new StringBuffer(" CREATE SEQUENCE " + sequenceName + " ");
            query.append(" START WITH " + start + " ");
            if (minValue >= 0) {
                query.append(" MINVALUE " + minValue + " ");
            } else {
                query.append(" NOMINVALUE ");
            }

            if (maxValue > 0) {
                query.append(" MAXVALUE " + maxValue + " ");
            } else {
                query.append(" NOMAXVALUE ");
            }

            if (increment > 1) {
                query.append(" INCREMENT BY " + increment);
            }

            query.append(" NOCACHE CYCLE");

            sqltext.log(Level.TRACE, "Create sequence " + query);
            stmt = (OraclePreparedStatement) connection.prepareStatement(query.toString());
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public long loadWorkItems(String workQueue, Map targetCache) throws Exception {
        long start = System.currentTimeMillis();
        Connection connection = this.getSqlConnection();
        String query = "SELECT workKey, workQueue, scheduledTime, workStatus, work from WorkItems where workQueue = ?";
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG, "loadWorkItems(workQueue) - SELECT * from WorkItems where workQueue = %s", workQueue);
        }

        OraclePreparedStatement stmt = (OraclePreparedStatement)connection.prepareStatement(query);
        stmt.setString(1, workQueue);
        ResultSet rs = stmt.executeQuery();
        long count = 0;
        while (rs.next()) {
            count++;
            WorkTuple item = createWorkTuple(rs);
            targetCache.put(item.getWorkId(), item);
        }
        rs.close();
        stmt.close();
        logger.log(Level.DEBUG, "loadWorkItems(workQueue) - loaded=%s time=%s msec", count, (System.currentTimeMillis()-start));
        return count;
    }
    
    public List<WorkTuple> getWorkItems(String workQueue, long time, int status) throws Exception {
        Connection connection = this.getSqlConnection();
        String query = "SELECT workKey, workQueue, scheduledTime, workStatus, work from WorkItems where  workQueue = ? and workStatus = ? and scheduledTime <= ? ORDER BY scheduledTime ASC";
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG, "getWorkItems(workQueue,time,status) - SELECT * from WorkItems where workQueue = %s and workStatus = %s and scheduledTime <= %s ORDER BY scheduledTime ASC", 
                    workQueue, status, time);
        }

        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        stmt.setMaxRows(workItemsBatchSize);
        ArrayList ret = new ArrayList();
        stmt.setString(1, workQueue);
        stmt.setInt(2, status);
        stmt.setLong(3, time);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            WorkTuple item = createWorkTuple(rs);
            ret.add(item);
        }
        rs.close();
        stmt.close();
        return ret;
    }

    private WorkTuple createWorkTuple(ResultSet rs) throws Exception {
        WorkTuple item = new WorkTuple();
        String workId = rs.getString(1);
        item.setWorkId(workId);
        item.setWorkQueue(rs.getString(2));
        item.setScheduledTime(rs.getLong(3));
        item.setWorkStatus((rs.getInt(4)));
        BLOB blob = (BLOB) rs.getBlob(5);

        blob.open(BLOB.MODE_READONLY);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = blob.getBinaryStream();
        int bufferSize = blob.getBufferSize();
        byte[] buffer = new byte[bufferSize];
        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        byte[] buf = baos.toByteArray();
        item.setBuf(buf);
        is.close();
        blob.close();
        if (blob.isTemporary())
            BLOB.freeTemporary(blob);
        return item;
    }

    public WorkTuple getWorkItem(String key) throws Exception {
        Connection connection = this.getSqlConnection();
        String query = "SELECT workKey, workQueue, scheduledTime, workStatus, work from WorkItems where workKey = ? ";
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE,"getWorkItem(key) - %s(%s)", query.toString(), key);
        }
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            WorkTuple item = createWorkTuple(rs);
            rs.close();
            stmt.close();
            return item;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public void removeWorkItem(String key) throws Exception {
        Connection connection = this.getSqlConnection();
        String query = "DELETE from WorkItems where workKey = ? ";
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        if (sqltext.isEnabledFor(Level.TRACE)) {
	        sqltext.log(Level.TRACE, "removeWorkItem(key) - DELETE from WorkItems where workKey = %s ", key);
        }
        stmt.setString(1, key);
        stmt.executeUpdate();
        stmt.close();
        return;
    }

    public void removeWorkItems(Collection<WorkTupleDBId> keysAndScheduledTimes) throws Exception {
        if (keysAndScheduledTimes.size() <= 0) {
            return;
        }
        StringBuffer query = new StringBuffer("DELETE from WorkItems where workKey IN (");
        for (int i = 0; i < keysAndScheduledTimes.size(); i++) {
            query.append("?,");
        }
        query.setCharAt(query.length() - 1, ')');

        final String queryString = query.toString();
        OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
        if (sqltext.isEnabledFor(Level.TRACE)) {
	        sqltext.log(Level.TRACE, "removeWorkItems(keys) " + queryString + " keys=%s", keysAndScheduledTimes);
        }
        int i = 1;
        for(WorkTupleDBId tupid : keysAndScheduledTimes) {
          //TODO AA: this does not check the timestamp because the default is to call removeWorkItemsInBatch
            stmt.setString(i++, tupid.dbKey);
        }
        stmt.executeUpdate();
        stmt.close();
        return;
    }

    public void removeWorkItemsInBatch(Collection<WorkTupleDBId> keysAndScheduledTimes) throws Exception {
        if (keysAndScheduledTimes.size() <= 0) {
            return;
        }
        final String queryString = "DELETE from WorkItems where workKey = ? AND scheduledTime = ? ";
        final OraclePreparedStatement stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(queryString);
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, "removeWorkItemsInBatch(keys) " + queryString + " keys=%s", keysAndScheduledTimes);
        }
        for(WorkTupleDBId tupid : keysAndScheduledTimes) {
            stmt.setString(1, tupid.dbKey);
            stmt.setLong(2, tupid.scheduledTime);
            stmt.addBatch();
        }
        stmt.executeBatch();
        stmt.clearBatch();
        stmt.close();
        return;
    }

    private void saveWorkItems(HashMap workItems) throws Exception {
        Iterator allWorkItems = workItems.entrySet().iterator();
        while (allWorkItems.hasNext()) {
            Map.Entry entry = (Map.Entry) allWorkItems.next();
            WorkTuple tuple = (WorkTuple) entry.getValue();
            saveWorkItem(tuple);
        }
    }

    //CREATE TABLE WorkItems (workKey VARCHAR2(2000), workQueue VARCHAR2(255), workStatus NUMBER, scheduledTime NUMBER, work BLOB);
    public void saveWorkItem(WorkTuple tuple) throws Exception {
		OraclePreparedStatement stmt = null;
        OracleConnection connection = this.getSqlConnection();
        String query = "INSERT INTO WorkItems(workKey, workQueue, scheduledTime, workStatus, work) VALUES (?,?,?,?,?)";
        stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        stmt.setString(1, tuple.getWorkId());
        stmt.setString(2, tuple.getWorkQueue());
        stmt.setLong(3, tuple.getScheduledTime());
        stmt.setInt(4, tuple.getWorkStatus());
        byte[] buf = tuple.getBuf();
        if (this.useExplicitTemporaryBlobs) {
            if (buf.length > 1024) {
                BLOB blob = (BLOB) OracleLOBManager.createBLOB(connection, buf);
                stmt.setBLOB(5, blob);
            } else {
                stmt.setBytesForBlob(5, buf);
            }
        } else {
            stmt.setBytesForBlob(5, buf);
        }
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, "INSERT INTO WorkItems (workKey, workQueue, scheduledTime, workStatus, work) "
                    + " VALUES (%s, %s, %s, %s, <blob>[%s])",
                    tuple.getWorkId(), tuple.getWorkQueue(), tuple.getScheduledTime(), tuple.getWorkStatus(), tuple.getWork().getClass().getName());
        }
        stmt.executeUpdate();
        stmt.close();
    }

    public void updateWorkItem(WorkTuple tuple) throws Exception {
        OracleConnection connection = this.getSqlConnection();
        String query = "UPDATE WorkItems SET scheduledTime = ?, workStatus = ?, work = ? where workKey = ? ";
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        stmt.setString(4, tuple.getWorkId());
        stmt.setLong(1, tuple.getScheduledTime());
        stmt.setInt(2, tuple.getWorkStatus());
        byte[] buf = tuple.getBuf();
        if (this.useExplicitTemporaryBlobs) {
            if (buf.length > 1024) {
                BLOB blob = (BLOB) OracleLOBManager.createBLOB(connection, buf);
                stmt.setBLOB(3, blob);
            } else {
                stmt.setBytesForBlob(3, buf);
            }
        } else {
            stmt.setBytesForBlob(3, buf);
        }
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, "UPDATE WorkItems SET scheduledTime = %s, workStatus = %s, work = %s where workKey = %s ",
                    tuple.getScheduledTime(), tuple.getWorkStatus(), tuple.getWork().getClass().getName(), tuple.getWorkId());
        }
        stmt.executeUpdate();
        stmt.close();
    }

    public void truncate_entityTable(String tableName) throws SQLException {
        Connection connection = this.getSqlConnection();
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement("TRUNCATE TABLE " + tableName);
        stmt.executeUpdate();
        stmt.close();
    }

    public void truncate_systemTables() throws SQLException {
        Connection connection = this.getSqlConnection();
        OraclePreparedStatement stmt = null;

        stmt = (OraclePreparedStatement) connection.prepareStatement("TRUNCATE TABLE ObjectTable ");
        stmt.executeUpdate();
        stmt.close();

        stmt = (OraclePreparedStatement) connection.prepareStatement("TRUNCATE TABLE WorkItems ");
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * @param query
     * @param args
     * @return
     * @throws Exception
     */
    public BackingStore.ResultSetCache query(String query, Object[] args) throws IOException, SQLException {
        Connection connection = this.getSqlConnection();
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
        ResultSet rs = null;
        OracleRSCache rsCache = null;
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        rs = stmt.executeQuery();
        int numColumns = rs.getMetaData().getColumnCount();
        rsCache = new OracleRSCache(numColumns);
        int rowNum = 0;
        while (rs.next()) {
            for (int i = 0; i < numColumns; i++) {
                rsCache.put(rowNum, i, rs.getObject(i + 1));
            }
            rowNum++;
        }
        stmt.close();
        return rsCache;
    }

    /**
     * @throws SQLException
     */
    public void rollback(boolean releaseConnection) {
        OracleConnection cnx = (OracleConnection) m_currentConnection.get();
        try {
            if (cnx != null) {
                cnx.rollback();
            }
        } catch (SQLException sqlex) {
        } finally {
            if (releaseConnection) {
                releaseConnection();
            }
        }
    }

    /**
     * @throws SQLException
     */
    public void commit() throws SQLException {
        commit(this.getSqlConnection(), true);
    }

    private void commit(OracleConnection cnx, boolean releaseConnection) throws SQLException {
        cnx.commit();
        if (releaseConnection) {
            releaseConnection();
        }
    }

    /**
     * @throws SQLException
     */
    private void close() throws SQLException {
        releaseConnection();
    }

    private String getQualifier() throws SQLException {
        return m_pool.getQualifier();
    }

    /**
     * @return
     * @throws SQLException
     */
    private OracleConnection getSqlConnection() throws SQLException {
        if (m_currentConnection.get() == null) {
            Connection conn = m_pool.getConnection();
            m_currentConnection.set(conn);
        }
        OracleConnection orclconn = (OracleConnection) m_currentConnection.get();
        return orclconn;
    }

    public OracleConnection getCurrentConnection() {
        return (OracleConnection) m_currentConnection.get();
    }

    /**
     * @throws SQLException
     */
    public void releaseConnection() {
        Connection cnx = (Connection) m_currentConnection.get();
        if (cnx != null) {
            try {
                try {
                    OracleLOBManager.free();
                } catch (Exception e) {
                    logger.log(Level.WARN, "OracleAdapter release connection (free) failed: %s", e.getMessage());
                }
                // Rollback any uncommitted txn, for safety
                try {
	                if (rollbackAfterRelease) {
	                    cnx.rollback();
                    }
                } catch (Exception e) {
                    logger.log(Level.WARN, "OracleAdapter release connection (rollback) failed: %s", e.getMessage());
                }
                cnx.close();
            } catch (Exception ex) {
                logger.log(Level.WARN, "OracleAdapter release connection (close) failed: %s", ex.getMessage());
            } finally {
                m_currentConnection.set(null);
            }
        }
    }

    class KeyIterator implements Iterator {
        OracleResultSet resultSet;

        KeyIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    return new Long(((oracle.sql.NUMBER) resultSet.getOracleObject(1)).longValue());
                } else {
                    throw new Exception("KeyIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class ConceptsIterator implements Iterator {
        OracleResultSet resultSet;

        ConceptsIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    STRUCT ret = resultSet.getSTRUCT(1);
                    com.tibco.cep.runtime.model.element.Concept cept = createConcept(ret);
                    return cept;
                } else {
                    throw new Exception("ConceptsIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class ConceptsWithVersionIterator implements Iterator {
        OracleResultSet resultSet;
        boolean isStateMachine = false;

        ConceptsWithVersionIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        ConceptsWithVersionIterator(OracleResultSet resultSet, boolean isStateMachine) {
            this.resultSet = resultSet;
            this.isStateMachine = isStateMachine;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    int version = resultSet.getInt(1);
                    STRUCT ret = resultSet.getSTRUCT(2);
                    if (isStateMachine) {
                        StateMachineConcept sm = createStateMachine(ret);
                        ((ConceptImpl) sm).setVersion(version);
                        return sm;
                    } else {
                        com.tibco.cep.runtime.model.element.Concept cept = createConcept(ret);
                        ((ConceptImpl) cept).setVersion(version);
                        return cept;
                    }
                } else {
                    throw new Exception("ConceptsWithVersionIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class ConceptsCursorIterator implements Iterator {
        OracleResultSet resultSet;
        boolean isStateMachine = false;

        ConceptsCursorIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        ConceptsCursorIterator(OracleResultSet resultSet, boolean isStateMachine) {
            this.resultSet = resultSet;
            this.isStateMachine = isStateMachine;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    int version = resultSet.getInt(1);
                    STRUCT ret = resultSet.getSTRUCT(2);
                    if (isStateMachine) {
                        StateMachineConcept sm = createStateMachine(ret);
                        ((ConceptImpl) sm).setVersion(version);
                        return sm;
                    } else {
                        com.tibco.cep.runtime.model.element.Concept cept = createConcept(ret);
                        ((ConceptImpl) cept).setVersion(version);
                        return cept;
                    }
                } else {
                    throw new Exception("ConceptsCursorIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class EventsIterator implements Iterator {
        OracleResultSet resultSet;

        EventsIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    STRUCT ret = resultSet.getSTRUCT(1);
                    com.tibco.cep.kernel.model.entity.Event evt = createEvent(ret);
                    return evt;
                } else {
                    throw new Exception("EventsIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class EventsCursorIterator implements Iterator {
        OracleResultSet resultSet;

        EventsCursorIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    STRUCT ret = resultSet.getSTRUCT(1);
                    com.tibco.cep.kernel.model.entity.Event evt = createEvent(ret);
                    return evt;
                } else {
                    throw new Exception("EventsIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class KeyPairIterator implements Iterator {
        OracleResultSet resultSet;

        KeyPairIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    long id = resultSet.getLong(1);
                    String extId = resultSet.getString(2);
                    return new ObjectTupleImpl(id, extId);
                } else {
                    throw new Exception("KeyPairIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class ObjectTableIterator implements Iterator {
        OracleResultSet resultSet;

        ObjectTableIterator(OracleResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        //SELECT ID, EXTID, CLASSNAME FROM OBJECTTABLE  WHERE SITEID=? AND CLASSNAME=? AND ISDELETED=0
        public Object next() {
            try {
                if (resultSet != null) {
                    long id = resultSet.getLong(1);
                    String extId = resultSet.getString(2);
                    String className = resultSet.getString(3);
                    return new ObjectTupleImpl(id, extId, cluster.getMetadataCache().getTypeId(className));
                } else {
                    throw new Exception("ObjectTableIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        private void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public static class ConceptDescription implements EntityDescription {
        private ArrayList m_properties = new ArrayList();
        private boolean isContained = false;
        protected com.tibco.cep.runtime.model.element.Concept TEMPLATE;
        protected Class m_szConceptClass;

        public ConceptDescription(String conceptClassName) throws Exception {
            RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
            this.m_szConceptClass = ((ClassLoader) rsp.getTypeManager()).loadClass(conceptClassName);
            initialize();
        }

        /**
         * @return
         */
        public String getImplClass() {
            return this.m_szConceptClass.getName();
        }

        protected void initialize() throws Exception {
            TEMPLATE = (com.tibco.cep.runtime.model.element.Concept) this.m_szConceptClass.newInstance();
            setContained((TEMPLATE instanceof ContainedConcept));
            Property[] allProperties = TEMPLATE.getProperties();

            for (int i = 0; i < allProperties.length; i++) {
                Property property = allProperties[i];
                ConceptProperty p = new ConceptProperty();
                p.propertyName = property.getName();
                p.isArray = (property instanceof PropertyArray);
                p.historySize = property.getHistorySize();
                if (property instanceof Property.PropertyString) {
                    p.type = RDFTypes.STRING_TYPEID;
                } else if (property instanceof Property.PropertyBoolean) {
                    p.type = RDFTypes.BOOLEAN_TYPEID;
                } else if (property instanceof Property.PropertyDateTime) {
                    p.type = RDFTypes.DATETIME_TYPEID;
                } else if (property instanceof Property.PropertyDouble) {
                    p.type = RDFTypes.DOUBLE_TYPEID;
                } else if (property instanceof Property.PropertyLong) {
                    p.type = RDFTypes.LONG_TYPEID;
                } else if (property instanceof Property.PropertyContainedConcept) {
                    p.type = RDFTypes.CONCEPT_TYPEID;
                    p.referredToConceptPath = ((Property.PropertyContainedConcept) property).getType().getName();
                } else if (property instanceof Property.PropertyConceptReference) {
                    p.type = RDFTypes.CONCEPT_REFERENCE_TYPEID;
                    p.referredToConceptPath = ((Property.PropertyConceptReference) property).getType().getName();
                } else if (property instanceof Property.PropertyInt) {
                    p.type = RDFTypes.INTEGER_TYPEID;
                } else {
                    throw new RuntimeException("UnIdentified Property Type " + property);
                }
                p.index = i;
                m_properties.add(p);
            }
        }

        /**
         * @return
         */
        public ArrayList getProperties() {
            return m_properties;
        }

        /**
         * @return
         */
        public boolean isContained() {
            return isContained;
        }

        /**
         * @param contained
         */
        public void setContained(boolean contained) {
            isContained = contained;
        }

        public class ConceptProperty {
            public int index;
            public String propertyName;
            public int type;
            public boolean isArray;
            public int historySize;
            public String referredToConceptPath;

            public boolean sameAs(ConceptProperty other) {
                return (propertyName.equalsIgnoreCase(other.propertyName)) &&
                       (type == other.type) &&
                       (isArray == other.isArray) &&
                        checkConceptReference(other);
            }

            boolean checkConceptReference(ConceptProperty other) {
                if ((other.referredToConceptPath != null) && (referredToConceptPath != null)) {
                    return referredToConceptPath.equals(other.referredToConceptPath);
                } else if ((other.referredToConceptPath == null) && (referredToConceptPath == null)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        class PropertyDescriptionImpl implements ConceptSerializer.PropertyDescription {
            int prev_index;
            int cur_index;
            Property deletedProperty;

            protected PropertyDescriptionImpl(int prev_index, int cur_index) {
                this.prev_index = prev_index;
                this.cur_index = cur_index;
            }

            protected PropertyDescriptionImpl(int prev_index, Property deletedProperty) {
                this.prev_index = prev_index;
                this.cur_index = -1;
                this.deletedProperty = deletedProperty;
            }

            public int previousIndex() {
                return prev_index;
            }

            public int currentIndex() {
                return cur_index;
            }

            public Property getDeletedProperty() {
                return deletedProperty;
            }
        }
    }

    class ClassIterator extends KeyIterator {
        ClassIterator(OracleResultSet resultSet) {
            super(resultSet);
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    return ((oracle.sql.CHAR) resultSet.getOracleObject(1)).stringValue();
                } else {
                    throw new Exception("ClassIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public static class SimpleEventDescription implements EntityDescription {
        protected ArrayList m_properties = new ArrayList();
        protected SimpleEventImpl TEMPLATE;
        protected Class m_szEventClass;

        public SimpleEventDescription(String eventClassName) throws Exception {
            RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
            this.m_szEventClass = ((ClassLoader) rsp.getTypeManager()).loadClass(eventClassName);
            initialize();
        }

        protected void initialize() throws Exception {
            try {
                TEMPLATE = (SimpleEventImpl) m_szEventClass.newInstance();
                String[] propertyNames = TEMPLATE.getPropertyNames();
                int[] propertyTypes = TEMPLATE.getPropertyTypes();

                for (int i = 0; i < propertyNames.length; i++) {
                    String propertyName = propertyNames[i];
                    int propertyType = propertyTypes[i];

                    EventProperty p = new EventProperty();
                    p.propertyName = propertyName;
                    p.type = propertyType;
                    p.index = i;
                    m_properties.add(p);
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
            }
        }

        /**
         * @return
         */
        public String getImplClass() {
            return this.m_szEventClass.getName();
        }

        public Collection getProperties() {
            return m_properties;
        }

        public class EventProperty {
            public int index;
            public String propertyName;
            public int type;

            /**
             * @param other
             * @return
             */
            public boolean sameAs(EventProperty other) {
                return (propertyName.equalsIgnoreCase(other.propertyName)) &&
                       (type == other.type);
            }
        }

        class PropertyDescriptionImpl implements EventSerializer.PropertyDescription {
            int prev_index;
            int cur_index;
            EventSerializer.PropertyDrainer deletedProperty;

            protected PropertyDescriptionImpl(int prev_index, int cur_index) {
                this.prev_index = prev_index;
                this.cur_index = cur_index;
            }

            protected PropertyDescriptionImpl(int prev_index, EventSerializer.PropertyDrainer deletedProperty) {
                this.prev_index = prev_index;
                this.cur_index = -1;
                this.deletedProperty = deletedProperty;
            }

            public int previousIndex() {
                return prev_index;
            }

            public int currentIndex() {
                return cur_index;
            }

            public EventSerializer.PropertyDrainer getDeletedProperty() {
                return deletedProperty;
            }
        }

        class IntegerPropertyDrainer implements EventSerializer.PropertyDrainer {
            EventProperty deletedProperty;

            public IntegerPropertyDrainer(EventProperty property) {
                this.deletedProperty = property;
            }

            public void drain(EventDeserializer deserializer) {
            	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
            		deserializer.getIntProperty();
            	}
                deserializer.endProperty();
            }
        }

        class StringPropertyDrainer implements EventSerializer.PropertyDrainer {
            EventProperty deletedProperty;

            public StringPropertyDrainer(EventProperty property) {
                this.deletedProperty = property;
            }

            public void drain(EventDeserializer deserializer) {
            	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
            		deserializer.getStringProperty();
            	}
                deserializer.endProperty();
            }
        }

        class DateTimePropertyDrainer implements EventSerializer.PropertyDrainer {
            EventProperty deletedProperty;

            public DateTimePropertyDrainer(EventProperty property) {
                this.deletedProperty = property;
            }

            public void drain(EventDeserializer deserializer) {
            	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
            		deserializer.getDateTimeProperty();
            	}
                deserializer.endProperty();
            }
        }

        class DoublePropertyDrainer implements EventSerializer.PropertyDrainer {
            EventProperty deletedProperty;

            public DoublePropertyDrainer(EventProperty property) {
                this.deletedProperty = property;
            }

            public void drain(EventDeserializer deserializer) {
            	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
            		deserializer.getDoubleProperty();
            	}
                deserializer.endProperty();
            }
        }

        class LongPropertyDrainer implements EventSerializer.PropertyDrainer {
            EventProperty deletedProperty;

            public LongPropertyDrainer(EventProperty property) {
                this.deletedProperty = property;
            }

            public void drain(EventDeserializer deserializer) {
            	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
            		deserializer.getLongProperty();
            	}
                deserializer.endProperty();
            }
        }

        class BooleanPropertyDrainer implements EventSerializer.PropertyDrainer {
            EventProperty deletedProperty;

            public BooleanPropertyDrainer(EventProperty property) {
                this.deletedProperty = property;
            }

            public void drain(EventDeserializer deserializer) {
            	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
            		deserializer.getBooleanProperty();
            	}
                deserializer.endProperty();
            }
        }
    }

    /**
     *
     */
    public static class TimeEventDescription extends SimpleEventDescription {

        public TimeEventDescription(String timeEventClassName) throws Exception {
            super(timeEventClassName);
            //initialize();
        }

        protected void initialize() throws Exception {
            EventProperty p1 = new EventProperty();
            p1.propertyName = "currentTime";
            p1.index = 0;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "nextTime";
            p1.index = 1;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "closure";
            p1.index = 2;
            p1.type = RDFTypes.STRING_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "ttl";
            p1.index = 3;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "fired";
            p1.index = 4;
            p1.type = RDFTypes.BOOLEAN_TYPEID;
            m_properties.add(p1);
        }
    }

    public static class StateMachineTimeoutDescription extends TimeEventDescription {

        /**
         * @throws Exception
         */
        public StateMachineTimeoutDescription() throws Exception {
            super(StateMachineConceptImpl.StateTimeoutEvent.class.getName());
            //initialize();
        }

        protected void initialize() {

            EventProperty p1 = new EventProperty();
            p1.propertyName = "currentTime";
            p1.index = 0;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "nextTime";
            p1.index = 1;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "closure";
            p1.index = 2;
            p1.type = RDFTypes.STRING_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "ttl";
            p1.index = 3;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "fired";
            p1.index = 4;
            p1.type = RDFTypes.BOOLEAN_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "smid";
            p1.index = 5;
            p1.type = RDFTypes.LONG_TYPEID;
            m_properties.add(p1);

            p1 = new EventProperty();
            p1.propertyName = "propertyName";
            p1.index = 6;
            p1.type = RDFTypes.STRING_TYPEID;
            m_properties.add(p1);
        }
    }

    public class OracleRSCache implements BackingStore.ResultSetCache { //, OracleResultSetCache { /* Removed from jdbc 12c driver */
        ArrayList m_Rows = new ArrayList();
        int numColumns;

        public OracleRSCache(int numColumns) {
            this.numColumns = numColumns;
        }

        /**
         * @param i
         * @param j
         * @return
         * @throws Exception
         */
        public Object getColumn(int i, int j) throws Exception {
            return get(i, j);
        }

        public Object[] getRow(int i) {
            return (Object[]) m_Rows.get(i);
        }

        /**
         * @param start
         * @param end
         * @return
         */
        public Set getRows(int start, int end) {
            LinkedHashSet set = new LinkedHashSet();
            if ((start < 0) || (end < 0)) {
                return set;
            }
            for (int i = start; i < ((end > size()) ? size() : end); i++) {
                set.add(m_Rows.get(i));
            }
            return set;
        }

        /**
         * @return
         */
        public int size() {
            return m_Rows.size();
        }

        /**
         *
         */
        public void closeSet() {
            try {
                close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void put(int i, int i1, Object object) throws IOException {
            Object[] m_Row = null;
            if (i >= m_Rows.size()) {
                m_Row = new Object[numColumns];
                m_Rows.add(m_Row);
            } else {
                m_Row = (Object[]) m_Rows.get(i);
            }
            m_Row[i1] = object;
        }

        public Object get(int i, int i1) throws IOException {
            return ((Object[]) m_Rows.get(i))[i1];
        }

        public void remove(int i) throws IOException {
            System.out.println("Remove Not Implemented Yet");
        }

        public void remove(int i, int i1) throws IOException {
            System.out.println("Remove Not Implemented Yet");
        }

        public void clear() throws IOException {
            m_Rows.clear();
        }

        public void close() throws IOException {
            clear();
        }
    }

    private static String prettyPrintLvl1(Concept cept) {
        if (cept == null) {
            return "concept=null";
        }
        return "Type=" + cept.getClass().getName() + ", id=" + cept.getId() + ", extId=" + cept.getExtId();
    }

    private static String prettyPrintLvl2(Concept cept, Object vals) {
        if (cept == null) {
            if (vals == null) {
                return "concept=null";
            } else {
                if (vals instanceof Object[]) 
                    return Arrays.toString((Object[])vals);
                else
                    return String.valueOf(vals);
            }
        }
        StringBuilder b = new StringBuilder(100);
        b.append("Type=" + cept.getClass().getName() + ", id=" + cept.getId() + ", extId=" + cept.getExtId() + " values: ");
        try {
            Property[] pA = cept.getProperties();
            for (int i = 0; i < pA.length; i++) {
                Property p = pA[i];
                if (p == null) {
                    continue;
                }
                if (p instanceof PropertyAtom) {
                    PropertyAtom pa = (PropertyAtom) p;
                    b.append(p.getName() + "=" + pa.getString() + ", ");
                }
            }
            return b.toString();
        } catch (Exception e) {
        }
        return b.toString();
    }

    private void logConcept(String operation, Concept cept, Object vals) {
        if (sqltext.isEnabledFor(Level.TRACE) || sqlvars.isEnabledFor(Level.TRACE)) {
            String oprn = operation;
            if (cept != null) { // If logging Concept values, skip the statement
                if (operation.startsWith("insert") || operation.startsWith("INSERT")) oprn = "INSERT";
                else if (operation.startsWith("update") || operation.startsWith("UPDATE")) oprn = "UPDATE";
                else if (operation.startsWith("delete") || operation.startsWith("DELETE")) oprn = "DELETE";
            }
            if (sqlvars.isEnabledFor(Level.TRACE)) {
                sqlvars.log(Level.TRACE, "%s %s", operation, prettyPrintLvl2(cept, vals));
            }
            else {
	            sqltext.log(Level.TRACE, "%s %s", oprn, prettyPrintLvl1(cept));
	        }
	    }
    }

    private String prettyPrintEventLv1(Event e) {
        return "Type=" + e.getClass().getName() + ", id=" + e.getId() + ", extId=" + e.getExtId();
    }

    private String prettyPrintEventLv2(Event e) {
        if (e == null) {
            return "event=null";
        }
        StringBuilder b = new StringBuilder("Type=" + e.getClass().getName() + ", id=" + e.getId() + ", extId=" + e.getExtId() + " values: ");
        try {
            if (e instanceof SimpleEvent) {
                String[] pNms = ((SimpleEvent) e).getPropertyNames();
                for (int i = 0; i < pNms.length; i++) {
                    try {
                        Object val = ((SimpleEvent) e).getProperty(pNms[i]);
                        if (val == null) {
                            continue;
                        }
                        if (val instanceof Calendar) {
                            b.append(pNms[i] + "=" + ((Calendar) val).getTime() + ", ");
                        } else {
                            b.append(pNms[i] + "=" + val.toString() + ", ");
                        }
                    } catch (Exception ex) {
                    }
                }
            }
            return b.toString();
        } catch (Exception ex) {
        }
        return b.toString();
    }

    private void logEvent(String operation, Event e) {
        if (sqltext.isEnabledFor(Level.TRACE) || sqlvars.isEnabledFor(Level.TRACE)) {
            String oprn = operation;
            if (operation.startsWith("insert") || operation.startsWith("INSERT")) oprn = "INSERT";
            else if (operation.startsWith("update") || operation.startsWith("UPDATE")) oprn = "UPDATE";
            else if (operation.startsWith("delete") || operation.startsWith("DELETE")) oprn = "DELETE";
            if (sqlvars.isEnabledFor(Level.TRACE)) {
                sqlvars.log(Level.TRACE, "%s %s", operation, prettyPrintEventLv2(e));
            }
            else {
	            sqltext.log(Level.TRACE, "%s %s", oprn, prettyPrintEventLv1(e));
	        }
	    }
    }

    public ObjectTupleImpl getBaseTable(String className, long id) throws Exception {
        String tblNm = generatedOracleTableName(className);
        if (tblNm == null) {
            return null;
        }
        OraclePreparedStatement stmt = this.getBaseTableStatementWithId(tblNm);
        stmt.setLong(1, id);
        stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        String extId = null;
        while (rs.next()) {
            extId = rs.getString(1);
        }
        rs.close();
        stmt.close();
        tuple = new ObjectTupleImpl(id, extId, cluster.getMetadataCache().getTypeId(className));
        return tuple;
    }

    public ObjectTupleImpl getTupleByType(String extId, Class clz) throws Exception {
        if (clz == null) {
            return null;
        }
        String tblNm = generatedOracleTableName(clz.getName());
        if (tblNm == null) {
            return null;
        }
        OraclePreparedStatement stmt = this.getBaseTableStatementWithExtId(tblNm);
        stmt.setString(1, extId);
        stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        long id = -1;
        while (rs.next()) {
            id = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        String className = clz.getName();
        tuple = (id == -1) ? null : new ObjectTupleImpl(id, extId, cluster.getMetadataCache().getTypeId(className));

        return tuple;
    }

    public long getMaxEntityAcrossTypes(long siteId) throws Exception {
        long maxId = 0;
        Class[] clzs = cluster.getMetadataCache().getRegisteredTypes();
        try {
            for (int i = 0; i < clzs.length; i++) {

                Class clz = clzs[i];
                if (clz == null) {
                    continue;
                }
                String tabNm = generatedOracleTableName(clz.getName());
                if (tabNm != null) {
                    long tblMax = -1;
                    tblMax = getMaxIdForSiteId(siteId, tabNm);
                    
                    //remove siteId first
                    tblMax &= ~EntityIdMask.SITE_MASK;

                    //then remove typeId
                    tblMax = IDEncoder.removeTypeId(tblMax);
                    maxId = maxId < tblMax ? tblMax : maxId;
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            releaseConnection();
        }
        return maxId;
    }
    
    private long getMaxIdForSiteId(long siteId, String tabNm) throws Exception {
    	long tblMax = 0;
        long rangeMin = siteId << EntityIdMask.SITE_MASK_SHIFT;
        long rangeMax = rangeMin | ~EntityIdMask.SITE_MASK;
        
        String query = "select max(T.ENTITY.ID$) from " + tabNm + " t "
        		+ "where T.ENTITY.ID$ >= ? and T.ENTITY.ID$ <= ?";
        PreparedStatement stmt = getSqlConnection().prepareStatement(query);
        
        stmt.setLong(1, rangeMin);
        stmt.setLong(2, rangeMax);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tblMax = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return tblMax;
    }
    
//    private long getMaxId(String tabNm) throws Exception {
//    	long tblMax = 0;
//        String query = RDBMSType.sSqlType.optimizeSelectStatement("select max id$ from " + tabNm);
//        Statement stmt = getSqlConnection().createStatement();
//        
//        ResultSet rs = stmt.executeQuery(query);
//        while (rs.next()) {
//            tblMax = rs.getLong(1);
//        }
//        rs.close();
//        stmt.close();
//        return tblMax;
//    }
    
    private OraclePreparedStatement getBaseTableStatementWithExtId(String tblNm) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT T.ENTITY.ID$ FROM ").append(tblNm).append(" T WHERE T.ENTITY.EXTID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getBaseTableStatementWithId(String tblNm) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT T.ENTITY.EXTID$ FROM ").append(tblNm).append(" T WHERE T.ENTITY.ID$=?");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getLoadStatementUsingBaseTables(String clzName) throws SQLException {
        String tblNm = generatedOracleTableName(clzName);
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT T.ENTITY.ID$ FROM ").append(tblNm).append(" T ");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private OraclePreparedStatement getRecoveryStatementUsingBaseTables(String clzName) throws SQLException {
        String tblNm = generatedOracleTableName(clzName);
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT T.ENTITY.ID$, T.ENTITY.EXTID$ ").append(" FROM ").append(tblNm).append(" T");
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(queryBuf.toString());
    }

    private class ObjectTableIteratorUsingBaseTables implements Iterator {
        OracleResultSet resultSet;
        int typeId;
        String clzName;

        ObjectTableIteratorUsingBaseTables(OracleResultSet resultSet, int typeId) {
            this.resultSet = resultSet;
            this.typeId = typeId;
            this.clzName = cluster.getMetadataCache().getClass(typeId).getName();
        }

        public boolean hasNext() {
            try {
                boolean hasNext = resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    long id = resultSet.getLong(1);
                    String extId = resultSet.getString(2);
                    return new ObjectTupleImpl(id, extId, typeId);
                } else {
                    throw new Exception("ObjectTableIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet = null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    
    /*
     * Get max(ID$) for the given type
     */
    public long getMaxId(int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.getMaxIdStatement(tableName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getLong(1);
            }
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
        }
        return -1L;
    }
    
    /*
     * Get min(ID$) for the given type
     */
    public long getMinId(int typeId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.getMinIdStatement(tableName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getLong(1);
            }
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
        }
        return -1L;
    }
        
    /*
     * Get preparedstatement to get max(id) of a type
     */
    OraclePreparedStatement getMaxIdStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT MAX(T.ENTITY.ID$) FROM ").append(tableName).append(" T");
        if (sqltext.isEnabledFor(Level.TRACE)) {
        	sqltext.log(Level.TRACE, queryBuf.toString());
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(
                queryBuf.toString());
    }
    
    /*
     * Get preparedstatement to get min(id) of a type
     */
    OraclePreparedStatement getMinIdStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT MIN(T.ENTITY.ID$) FROM ").append(tableName).append(" T");
        if (sqltext.isEnabledFor(Level.TRACE)) {
        	sqltext.log(Level.TRACE, queryBuf.toString());
        }
        return (OraclePreparedStatement) getSqlConnection().prepareStatement(
                queryBuf.toString());
    }
    
    public ConceptsCursorIterator loadConcepts(int typeId, long startId, long endId) throws Exception {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Loading " + entityClz.getName() + " entries from " + tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt=this.getLoadConceptsStatement(tableName, queryBuf, startId, endId);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            if (!cluster.getClusterConfig().isCacheAside()) { // why only for write-behind?
                stmt.setString(2, entityClz.getName());
                if (multisite) {
                    stmt.setLong(3, cluster.getClusterConfig().getSiteId());
                    stmt.setLong(4, startId);
                    stmt.setLong(5, endId);
                } else {
                    stmt.setLong(3, startId);
                    stmt.setLong(4, endId);
                }
            } else {
                stmt.setLong(2, startId);
                stmt.setLong(3, endId);
            }
            
            if (sqltext.isEnabledFor(Level.TRACE)) {
                queryBuf.append(" (" +  "<cursor>, " + entityClz.getName() + ", ");
                if (multisite) {
                    queryBuf.append(cluster.getClusterConfig().getSiteId());
                }
                queryBuf.append(",").append(startId).append(",").append(endId);
                queryBuf.append(")");
                sqltext.log(Level.TRACE, queryBuf.toString());
            }
            stmt.execute();
            return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1));
        } else {
            logger.log(Level.ERROR, "Failed loading " + entityClz.getName() + " entries - table UNKNOWN");
            return null;
        }
    }
    
    OracleCallableStatement getLoadConceptsStatement(String tableName, StringBuffer queryBuf, long startId, long endId) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.CACHEID, T.ENTITY  FROM " + tableName + " T ");
        if (!cluster.getClusterConfig().isCacheAside()) {
            if (multisite) {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=? AND GLOBALID >= ? AND GLOBALID <= ?) ");
            } else {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND GLOBALID >= ? AND GLOBALID <= ?) ");
            }
        } else {
            queryBuf.append("WHERE T.ENTITY.ID$ >= ? AND T.ENTITY.ID$ <= ?");
        }
        queryBuf.append("; end; ");
        if (sqltext.isEnabledFor(Level.TRACE)) {
        	sqltext.log(Level.TRACE, queryBuf.toString());
        }
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }
    
    public EventsCursorIterator loadEvents(int typeId, long startId, long endId) throws Exception {
        Class entityClz=cluster.getMetadataCache().getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Loading " + entityClz.getName() + " entries from " + tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt = this.getLoadEventsStatement(tableName, queryBuf, startId, endId);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            if (!cluster.getClusterConfig().isCacheAside()) {
                stmt.setString(2, entityClz.getName());
                if (multisite) {
                    stmt.setLong(3, cluster.getClusterConfig().getSiteId());
                    stmt.setLong(4, startId);
                    stmt.setLong(5, endId);
                } else {
                    stmt.setLong(3, startId);
                    stmt.setLong(4, endId);
                }
            } else {
                stmt.setLong(2, startId);
                stmt.setLong(3, endId);
            }
            if (sqltext.isEnabledFor(Level.TRACE)) {
                queryBuf.append(" (" +  "<cursor>, " + entityClz.getName() + ", ");
                if (multisite) {
                    queryBuf.append(cluster.getClusterConfig().getSiteId());
                }
                queryBuf.append(",").append(startId).append(",").append(endId);
                queryBuf.append(")");
                sqltext.log(Level.TRACE, queryBuf.toString());
            }
            stmt.execute();
            return new EventsCursorIterator((OracleResultSet) stmt.getCursor(1));
        } else {
            logger.log(Level.ERROR, "Failed loading " + entityClz.getName() + " entries - table UNKNOWN");
            return null;
        }
    }
    
    OracleCallableStatement getLoadEventsStatement(String tableName, StringBuffer queryBuf, long startId, long endId) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.ENTITY  FROM " + tableName + " T ");
        if (!cluster.getClusterConfig().isCacheAside()) {
            if (multisite) {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=? AND GLOBALID >= ? AND GLOBALID <= ?) ");
            } else {
                queryBuf.append("WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND GLOBALID >= ? AND GLOBALID <= ?) ");
            }
        } else {
            queryBuf.append("WHERE T.ENTITY.ID$  >= ? AND T.ENTITY.ID$ <= ?");
        }
        queryBuf.append("; end; ");
        if (sqltext.isEnabledFor(Level.TRACE)) {
        	sqltext.log(Level.TRACE, queryBuf.toString());
        }
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }
    
    public ConceptsCursorIterator loadConcepts(int typeId, Long[] entityIds) throws Exception {
        Class entityClz=cluster.getMetadataCache().getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Loading " + entityClz.getName() + " entries from " + tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt = null;
           	stmt = getLoadConceptsUsingKeysStatement(tableName, queryBuf, entityIds);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            for(int i = 0; i < entityIds.length;) {
            	long entityId = entityIds[i];
            	stmt.setLong(++i + 1, entityId);
            }
            if (logger.isEnabledFor(Level.TRACE)) {
                queryBuf.append(" (" +  "<cursor>, " + entityClz.getName() + ", ");
                if (multisite) {
                    queryBuf.append(cluster.getClusterConfig().getSiteId());
                }
                queryBuf.append(")");
                logger.log(Level.TRACE,queryBuf.toString());
            }
            stmt.execute();
            return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1));
        } else {
            logger.log(Level.ERROR,"Failed loading " + entityClz.getName() + " entries - table UNKNOWN");
            return null;
        }
    }
    
    OracleCallableStatement getLoadConceptsUsingKeysStatement(String tableName, StringBuffer queryBuf, Long[] entityIds) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.CACHEID, T.ENTITY  FROM " + tableName + " T ");
        queryBuf.append("WHERE T.ENTITY.ID$ IN (");
        int length = entityIds.length;
        for(int i = 0;i < length;) {
        	queryBuf.append("?");
        	if (++i < length) {
        		queryBuf.append(",");
        	}
        }
        queryBuf.append("); end; ");
        logger.log(Level.TRACE,queryBuf.toString());
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }
    
    public EventsCursorIterator loadEvents(int typeId, Long[] entityIds) throws Exception {
        Class entityClz=cluster.getMetadataCache().getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO,"Loading " + entityClz.getName() + " entries from " + tableName);
            StringBuffer queryBuf = new StringBuffer(100);
            OracleCallableStatement stmt = this.getLoadEventsStatement(tableName, queryBuf, entityIds);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            for(int i = 0; i < entityIds.length;) {
            	long entityId = entityIds[i];
            	stmt.setLong(++i + 1, entityId);
            }
            if (logger.isEnabledFor(Level.TRACE)) {
                queryBuf.append(" (" +  "<cursor>, " + entityClz.getName() + ", ");
                for(int i = 0; i < entityIds.length;) {
                	long entityId = entityIds[i];
                	 queryBuf.append(",").append(entityId);
                }
                queryBuf.append(")");
                logger.log(Level.TRACE,queryBuf.toString());
            }
            stmt.execute();
            return new EventsCursorIterator((OracleResultSet) stmt.getCursor(1));
        } else {
            logger.log(Level.ERROR,"Failed loading " + entityClz.getName() + " entries - table UNKNOWN");
            return null;
        }
    }
    
    OracleCallableStatement getLoadEventsStatement(String tableName, StringBuffer queryBuf, Long[] entityIds) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.ENTITY  FROM " + tableName + " T ");
        queryBuf.append("WHERE T.ENTITY.ID$ IN (");
        int length = entityIds.length;
        for(int i = 0;i < length;) {
        	queryBuf.append("?");
        	if (++i < length) {
        		queryBuf.append(",");
        	}
        }
        queryBuf.append("); end; ");
        logger.log(Level.TRACE,queryBuf.toString());
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }
}
