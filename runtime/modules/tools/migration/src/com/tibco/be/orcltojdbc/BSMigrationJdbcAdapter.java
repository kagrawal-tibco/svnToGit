/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.orcltojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBConnectionPool;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.be.jdbcstore.impl.DBEventMap;
import com.tibco.be.jdbcstore.impl.DBHelper;
import com.tibco.be.jdbcstore.impl.DBManager;
import com.tibco.be.jdbcstore.serializers.DBConceptSerializer;
import com.tibco.be.jdbcstore.serializers.DBEventSerializer;
import com.tibco.be.jdbcstore.serializers.DBStateMachineSerializer;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

public class BSMigrationJdbcAdapter {

    private RuleServiceProvider _rsp;
    private com.tibco.cep.kernel.service.logging.Logger _logger;
    private ThreadLocal _currentConnection = new ThreadLocal();
    private DBConnectionPool _pool;
    private boolean _isOracleType;
    private boolean _insertObjectTable;
    private boolean _useExplicitTemporaryBlobs;
    private boolean _migrationProgressTableExist;
    
    int siteId = 0;
    boolean isNewSerEnabled = false;

    /**
     * 
     * @param pool
     * @param databaseType
     * @param insertObjectTable
     * @param useExplicitTemporaryBlobs
     * @throws Exception
     */
    public BSMigrationJdbcAdapter(DBConnectionPool pool, String databaseType, 
            boolean insertObjectTable, boolean useExplicitTemporaryBlobs) throws Exception {
        this._pool = pool;
        this._rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        this._logger = this._rsp.getLogger(BSMigrationJdbcAdapter.class);
        this._logger.log(Level.DEBUG, "DBAdapter <init> with cluster");
        _isOracleType = databaseType.equalsIgnoreCase("oracle");
        _insertObjectTable = insertObjectTable;
        _useExplicitTemporaryBlobs = useExplicitTemporaryBlobs;
    }

    // JdbcAdaptor is always active, unless a reconnection is being tried
    public boolean isActive() {
        // VWC rework return getConnectionPool().isAvailable();
        return true;
    }

    public DBConnectionPool getConnectionPool() {
        return _pool;
    }

    /**
     * 
     * @throws SQLException
     */
    public void rollback(boolean releaseConnection) {
        Connection cnx = (Connection) _currentConnection.get();
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
     * 
     * @throws SQLException
     */
    public void commit() throws SQLException {
        commit(this.getSqlConnection(), true);
    }

    /**
     * 
     * @throws SQLException
     */
    public void close() throws SQLException {
        releaseConnection();
    }

    /**
     * 
     * @throws SQLException
     */
    public void releaseConnection() {
        Connection cnx = (Connection) _currentConnection.get();
        if (cnx != null) {
            try {
                // OracleLOBManager.free();
                _pool.free(cnx);
                cnx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            _currentConnection.set(null);
        }
    }

    public Connection getCurrentConnection() {
        return (Connection) _currentConnection.get();
    }

    public Map getClassRegistry() throws SQLException {
        Map classRegistry = new HashMap();
        // Delete the existing registry
        PreparedStatement getStmt = this.getSqlConnection().prepareStatement("SELECT className, typeId FROM ClassRegistry");
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

    public long insertEvents(String className, Map<Long, Event> entries) throws Exception {
        List serializers = new ArrayList();
        DBEventMap entityMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (entityMap == null) {
            throw new Exception("Cannot find map for " + className);
        }
        long maxEntityId = 0;
        PreparedStatement stmt = null;
        try {
            String priSql = entityMap.getPrimaryInsertSql();
            if (_isOracleType) {
                priSql = priSql.replaceFirst("insert", "insert /*+ append */");
            }
            this._logger.log(Level.DEBUG, priSql);

            stmt = getSqlConnection().prepareStatement(priSql);
            // stmt.setExecuteBatch(entries.size());
            
            Iterator all_entries = entries.entrySet().iterator();
            Event event = null;
            while (all_entries.hasNext()) {
                try {
                    Map.Entry<Long, Event> entry = (Map.Entry<Long, Event>) all_entries.next();
                    Long id = entry.getKey();
                    maxEntityId = id;
                    event = entry.getValue();
                    DBEventSerializer serializer = new DBEventSerializer(entityMap, getSqlConnection(), getQualifier(), stmt, 2, _useExplicitTemporaryBlobs);
                    serializers.add(serializer);
                    DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, new Long(1));
                    if (event instanceof SimpleEvent) {
                        ((SimpleEvent) event).serialize(serializer);
                    } else {
                        ((TimeEvent) event).serialize(serializer);
                    }

                    stmt.addBatch();

                    // insert into ObjectTable also
                    if (_insertObjectTable) {
                        insertObjectTable(event);
                    }
                } catch (Exception e) {
                    this._logger.log(Level.ERROR, "Failed to insert event of type %s, with id=%s, extId=%s",
                            className, event.getId(), event.getExtId());
                    throw e;
                }
            }
            stmt.executeBatch();
            return maxEntityId;
            // stmt.setExecuteBatch(entries.size());
        } finally {
            // FIX THIS - need to resolve clob model
            for (int i = 0; i < serializers.size(); i++) {
                ((DBEventSerializer) serializers.get(i)).releaseLobTempSpace();
            }
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }

    @Deprecated 
    // Mark as @deprecated so it should not used until completely implemented
    public long insertStateMachines(String className, Map entries) throws Exception {
        DBConceptMap entityMap = (DBConceptMap) getEntityPropsMap().get(className);
        if (entityMap == null) {
            throw new Exception("Cannot find map for " + className);
        }
        long maxEntityId = 0;
        PreparedStatement stmt = null;
        // PreparedStatement stmt=getSqlConnection().prepareStatement(desc.getInsertStatement());
        // stmt.setExecuteBatch(entries.size());
        try {
            Iterator all_entries = entries.entrySet().iterator();
            StateMachineConcept sm = null;
            while (all_entries.hasNext()) {
                try {
                    Map.Entry<Long, StateMachineConcept> entry = (Map.Entry<Long, StateMachineConcept>) all_entries.next();
                    Long id = entry.getKey();
                    maxEntityId = id;
                    sm = entry.getValue();
                    DBStateMachineSerializer serializer = new DBStateMachineSerializer(entityMap, getSqlConnection(), getQualifier());
                    sm.serialize(serializer);
                    Object[] attrs = serializer.getAttributes();
                    stmt.setObject(1, new oracle.sql.NUMBER(((VersionedObject) sm).getVersion()));
                    for (int i = 0; i < attrs.length; i++) {
                        if (attrs[i] != null) {
                            stmt.setObject(i + 2, attrs[i]);
                        } else {
                            setNull(stmt, entityMap, i, i + 2);
                        }
                    }
                    stmt.addBatch();

                    // insert into ObjectTable also
                    if (_insertObjectTable) {
                        insertObjectTable(sm);
                    }
                } catch (Exception e) {
                    this._logger.log(Level.ERROR, "Failed to insert statemachine of type %s, with id=%s, extid=%s",
                            className, sm.getId(), sm.getExtId());
                    throw e;
                }
            }
            stmt.executeBatch();
            stmt.close();
            return maxEntityId;
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }

    public long insertConcepts(String className, Map<Long, Concept> entries) throws Exception {
        DBConceptMap entityMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (entityMap == null) {
            throw new Exception("Cannot find map for " + className);
        }
        // List stmtList = new ArrayList();
        PreparedStatement stmt = null;
        Map stmtMap = new HashMap();
        long maxEntityId = 0;
        try {
            String priSql = entityMap.getPrimaryInsertSql();
            if (_isOracleType) {
                priSql = priSql.replaceFirst("insert", "insert /*+ append */");
            }
            this._logger.log(Level.DEBUG, priSql);

            stmt = getSqlConnection().prepareStatement(priSql);
            Map secondaryInsertSqlMap = entityMap.getSecondaryInsertSqlMap();

            for (Iterator itr = secondaryInsertSqlMap.keySet().iterator(); itr.hasNext();) {
                String fieldName = (String) itr.next();
                String secSql = (String)secondaryInsertSqlMap.get(fieldName);
                if (_isOracleType) {
                    secSql = secSql.replaceFirst("insert", "insert /*+ append */");
                }
                this._logger.log(Level.DEBUG, secSql);

                PreparedStatement secStmt = getSqlConnection().prepareStatement(secSql);
                stmtMap.put(fieldName, secStmt);
            }
            // stmt.setExecuteBatch(entries.size());
            Iterator all_entries = entries.entrySet().iterator();
            Concept cept = null;
            while (all_entries.hasNext()) {
                try {
                    // stmt = getSqlConnection().prepareStatement(entityMap.getPrimaryInsertSql());
                    Map.Entry<Long, Concept> entry = (Map.Entry<Long, Concept>) all_entries.next();
                    Long id = entry.getKey();
                    maxEntityId = id;
                    cept = entry.getValue();
                    DBConceptSerializer serializer = new DBConceptSerializer(entityMap, getSqlConnection(), getQualifier(), _useExplicitTemporaryBlobs);
                    cept.serialize((ConceptSerializer) serializer);
                    if (serializer.hasErrors()) {
                        throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                    }
                    Object[] attrs = serializer.getAttributes();
                    Map secondaryAttributeMap = serializer.getSecondaryAttributeMap();
                    DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, new Long(((VersionedObject) cept).getVersion()));
                    int fieldCount = entityMap.getFieldCount();
                    int dataIdx = 0;
                    for (int i = 0; i < fieldCount; i++) {
                        DBEntityMap.DBFieldMap fMap = entityMap.getFieldMap(i);
                        if (fMap.secondaryTableName == null) {
                            DBHelper.setColumnData(stmt, fMap.tableFieldIndex + 2, fMap.tableFieldMappingType, attrs[dataIdx++]);
                        }
                    }

                    stmt.addBatch();

                    // insert into object table also...
                    if (_insertObjectTable) {
                        insertObjectTable(cept);
                    }

                    // stmt.executeUpdate();
                    // stmt.close();
                    for (Iterator itr = secondaryAttributeMap.keySet().iterator(); itr.hasNext();) {
                        String fieldName = (String) itr.next();
                        Object value = secondaryAttributeMap.get(fieldName);
                        if (value == null) {
                            continue;
                        }
                        PreparedStatement sstmt = (PreparedStatement) stmtMap.get(fieldName);
                        /*
                         * if (DBHelper.populateSecondaryTable(entityMap.getFieldMap(fieldName), stmt, value, id)) { 
                         *     sstmt.addBatch(); 
                         * }
                         */
                        DBHelper.populateSecondaryTable(entityMap.getFieldMap(fieldName), sstmt, value, id);
                    }
                } catch (Exception e) {
                    this._logger.log(Level.ERROR, "Failed to insert concept of type %s, with id=%s, extId=%s",
                            className, cept.getId(), cept.getExtId());
                    e.printStackTrace();
                }
            }
            stmt.executeBatch(); // Concurrent
            for (Iterator itr = stmtMap.values().iterator(); itr.hasNext();) {
                ((PreparedStatement) itr.next()).executeBatch();
            }
            return maxEntityId;
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
            for (Iterator itr = stmtMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
        }
    }

    public void saveWorkItem(WorkTuple tuple) throws Exception {
        PreparedStatement stmt = null;
        try {
            Connection connection = this.getSqlConnection();
            stmt =  connection.prepareStatement("INSERT INTO WorkItems(workKey, workQueue, scheduledTime, workStatus, work) VALUES (?,?,?,?,?)");
            stmt.setString(1, tuple.getWorkId());
            stmt.setString(2, tuple.getWorkQueue());
            stmt.setLong(3, tuple.getScheduledTime());
            stmt.setInt(4, tuple.getWorkStatus());
            byte[] buf = tuple.getBuf();
            DBHelper.setBlob(stmt, 5, buf);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }
    
    public void dropMigrationProgress() throws SQLException {
        PreparedStatement stmt = null;
        try {
            // Check existence of the table
            if (tableExists(getSqlConnection(), "MIGRATIONPROGRESS") == true) {
                stmt = this.getSqlConnection().prepareStatement("DROP TABLE MIGRATIONPROGRESS");
                stmt.executeQuery();
            }
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }               
    }
    
    // This functionality is not enabled/tested yet.
    public void initializeMigrationProgress() throws SQLException {
        PreparedStatement stmt = null;
        try {
            // Check existence of the table
            if (tableExists(getSqlConnection(), "MIGRATIONPROGRESS") == false) {
                stmt = this.getSqlConnection().prepareStatement("CREATE TABLE MIGRATIONPROGRESS (className VARCHAR2(4000), typeId INTEGER, maxEntityId number, done number)");
                stmt.executeQuery();
            }
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }               
        try {
            // Check existence of the table contents
            if (tableEmpty(getSqlConnection(), "MIGRATIONPROGRESS") == false) {
                stmt = this.getSqlConnection().prepareStatement("INSERT INTO MIGRATIONPROGRESS (select className, typeId, 0, 0 from CLASSREGISTRY)");
                stmt.executeQuery();
            }
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }               
    }

    public Map getMigrationProgress() throws SQLException {
        Map migrationProgress = new TreeMap();
        PreparedStatement getStmt = null;
        ResultSet rs = null;
        _migrationProgressTableExist = false;
        try {
            if (tableExists(this.getSqlConnection(), "MIGRATIONPROGRESS") == false) {
                this._logger.log(Level.DEBUG, "MIGRATIONPROGRESS table does not exist. Create one using; \n" +
                        "        drop table BE_JDBC.MIGRATIONPROGRESS; \n" +
                        "        create table BE_JDBC.MIGRATIONPROGRESS (className VARCHAR2(4000), \n" +
                        "                   typeId INTEGER, maxEntityId number, done number); \n" +
                        "        insert into BE_JDBC.MIGRATIONPROGRESS \n" +
                        "                   (select className, typeId, 0, 0 from BE_ORCL.CLASSREGISTRY);"
                );
            } else {
                getStmt = this.getSqlConnection().prepareStatement("SELECT className, typeId, maxEntityId, done FROM MIGRATIONPROGRESS where className not like 'com.tibco.cep.runtime%' order by className");
                rs = getStmt.executeQuery();
                while (rs.next()) {
                    MigrationProgress mp = new MigrationProgress();
                    mp.className = rs.getString(1);
                    mp.typeId = rs.getInt(2);
                    mp.maxEntityId = rs.getLong(3);
                    mp.done = rs.getInt(4);
                    mp.isPersisted = true;
                    migrationProgress.put(mp.className, mp);
                    _migrationProgressTableExist = true;
                }
                this._logger.log(Level.DEBUG, "Loaded MIGRATIONPROGRESS table: %s", migrationProgress);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            this._logger.log(Level.FATAL, sqle, "Failed querying MIGRATIONPROGRESS table.");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (getStmt != null) {
                getStmt.close();
            }
        }
        return migrationProgress;
    }

    public void markTypeAsDone(int typeId) throws SQLException {
        if (_migrationProgressTableExist) {
            Statement stmt = this.getSqlConnection().createStatement();
            String stmtStr = "UPDATE MIGRATIONPROGRESS SET DONE = 1 WHERE TYPEID = " + typeId;
            stmt.execute(stmtStr);
            stmt.close();
        }
    }

    public void updateMaxEntityId(int typeId, long maxEntityId) throws SQLException {
        if (_migrationProgressTableExist) {
            Statement stmt = this.getSqlConnection().createStatement();
            String stmtStr = "UPDATE MIGRATIONPROGRESS SET MAXENTITYID = " + maxEntityId + " WHERE TYPEID = " + typeId;
            stmt.execute(stmtStr);
            stmt.close();
        }
    }

    private void insertObjectTable(Entity entity) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = this.insertObjectTableStatement();
            stmt.setLong(1, entity.getId());
            stmt.setLong(2, EntityIdMask.getMaskedId(entity.getId()));
            stmt.setLong(3, EntityIdMask.getEntityId(entity.getId()));
            if (entity.getExtId() != null) {
                stmt.setString(4, entity.getExtId());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }
            stmt.setString(5, entity.getClass().getName());
            stmt.setInt(6, 0); // Always set to 0
            stmt.setNull(7, java.sql.Types.NUMERIC);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }               
    }

    private Map getEntityPropsMap() throws Exception {
        return DBManager.getInstance().getEntityPropsMap();
    }

    private PreparedStatement insertObjectTableStatement() throws SQLException {
        String objSql = new String("insert into OBJECTTABLE(GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED) values (?,?,?,?,?,?,?)");
        if (_isOracleType) {
            objSql = objSql.replaceFirst("insert", "insert /*+ append */");
        }
        this._logger.log(Level.DEBUG, objSql);
        return getSqlConnection().prepareStatement(objSql);
    }

    private void setNull(PreparedStatement stmt, DBConceptMap desc, int jdbcIndex, int stmtIndex) throws SQLException {

    }

    private String getQualifier() throws SQLException {
        // VWC rework return _pool.getQualifier();
        return "";
    }

    private void commit(Connection cnx, boolean releaseConnection) throws SQLException {
        cnx.commit();
        if (releaseConnection)
            releaseConnection();
    }

    private Connection getSqlConnection() throws SQLException {
        if (_currentConnection.get() == null) {
            _currentConnection.set(_pool.getConnection());
        }
        return (Connection) _currentConnection.get();
    }

    private void freeSqlConnection(Connection connection) throws SQLException {
        _pool.free(connection);
    }

    public boolean tableExists(Connection conn, String tableName) throws SQLException {
        String sqlExists = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            sqlExists = formatSqlExists(tableName);
            this._logger.log(Level.DEBUG, "Exists SQL %s : %s;", tableName, sqlExists);
            stmt = conn.prepareStatement(sqlExists);
            rs = stmt.executeQuery();
        } catch (Exception e) {
            if ((e.getMessage().indexOf("not found") < 0) && (e.getMessage().indexOf("does not exist") < 0)&&
                (e.getMessage().indexOf("Invalid object name") < 0))
                this._logger.log(Level.ERROR, e, "Failure...");
            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            this.freeSqlConnection(conn);
        }
        return true;
    }

    protected String formatSqlExists(String tableName) {
        return "SELECT * FROM " + tableName + " WHERE 1 = 0 ";
    }

    public boolean tableEmpty(Connection conn, String tableName) throws SQLException {
        String sql = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            sql = formatSqlSelect(tableName);
            this._logger.log(Level.DEBUG, "Empty SQL %s: %s;", tableName, sql);
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.first()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            this.freeSqlConnection(conn);
        }
    }

    protected String formatSqlSelect(String tableName) {
        return "SELECT * FROM " + tableName + " WHERE 1 = 1 ";
    }
}
