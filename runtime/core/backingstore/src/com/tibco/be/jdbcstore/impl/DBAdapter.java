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

package com.tibco.be.jdbcstore.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.jdbcstore.serializers.DBConceptSerializer;
import com.tibco.be.jdbcstore.serializers.DBEventSerializer;
import com.tibco.be.oracle.OracleLOBManager;
import com.tibco.be.oracle.impl.OracleConnectionPool;
import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.process.ObjectBean;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultScheduler.WorkTupleDBId;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction.ConceptStatus;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class DBAdapter {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBAdapter.class);
    private final static Logger sqltext = LogManagerFactory.getLogManager().getLogger("sql.text");
    private final static Logger sqlvars = LogManagerFactory.getLogManager().getLogger("sql.vars");
    private final static Logger sqlquery = LogManagerFactory.getLogManager().getLogger("sql.query");
    //TODO: private static int PREFETCH_SIZE = Integer.parseInt(System.getProperty("be.engine.tangosol.oracle.prefetch", "1000").trim());
    private final static int DELETE_IN_LIMIT = 500; // FIX THIS - What is this
    private static int DEFAULT_ORACLE_BATCH_SIZE = 10;

    private RuleServiceProvider _rsp;
    private Cluster _cluster;
    private ThreadLocal _currentConnection = new ThreadLocal();
    private ConnectionPool _pool;
    private boolean _useTemporaryBlobs = false;
    private boolean _recreateOnRecovery = true;
    private boolean _rollbackAfterRelease = true;

    // Maximum JDBC in list is 1000
    private static int IN_BATCH_SIZE = 900;

    @SuppressWarnings("unused")
    private final static String MERGE_OBJECTTABLE_STATEMENT = "MERGE INTO OBJECTTABLE T USING (SELECT ? GLOBALID, ? SITEID, ? ID, ? EXTID, ? CLASSNAME, ? ISDELETED, ? TIMEDELETED FROM DUAL) I " +
                                                              "ON (T.GLOBALID=I.GLOBALID) WHEN MATCHED THEN UPDATE SET T.ISDELETED=1, T.TIMEDELETED=I.TIMEDELETED WHEN NOT MATCHED THEN INSERT " +
                                                              "(GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED) VALUES (I.GLOBALID,I.SITEID,I.ID,I.EXTID,I.CLASSNAME, I.ISDELETED, I.TIMEDELETED) ";
    //TODO: private int queryTimeout = 0;
    // Based on this variable, SITEID column be used in OBJECTTABLE queries
    // Default value is true
    private boolean multisite = false;
    private int workItemsBatchSize = 500;

    public DBAdapter(ConnectionPool pool, Cluster cluster) throws Exception {
        this._pool = pool;
        this._cluster = cluster;
        this._rsp = _cluster.getRuleServiceProvider();
        this._useTemporaryBlobs = DBManager._useTemporaryBlobs;
        this._recreateOnRecovery = DBManager._recreateOnRecovery;
        this._rollbackAfterRelease = DBManager._rollbackAfterRelease;

        logger.log(Level.DEBUG, "DBAdapter <init> with cluster %s", this._cluster.getClusterName());
        String sBatchSize = System.getProperty("be.engine.tangosol.oracle.batchSize");
        if ((sBatchSize != null) && (sBatchSize.trim().length() > 0)) {
            DEFAULT_ORACLE_BATCH_SIZE = Integer.valueOf(sBatchSize.trim()).intValue();
        }
        //TODO: queryTimeout = Integer.parseInt(_rsp.getProperties().getProperty("be.backingstore.querytimeout", "0").trim());
        multisite = "true".equals(_rsp.getProperties().getProperty("be.engine.cluster.multisite", "false").trim());
        //Activate the pool
        //_pool.activate();
        //Initialize maps
        //initializeJdbcMaps();
        
        try {
            workItemsBatchSize = Integer.parseInt(_rsp.getProperties().getProperty("be.engine.scheduler.workitems.batchsize", "500"));
        } catch (Exception e) {
        }
        logger.log(Level.DEBUG, "WorkItems batch size %s [%s]", workItemsBatchSize, 
                _rsp.getProperties().getProperty("be.engine.scheduler.workitems.batchsize"));
    }

    public boolean isActive() {
	    // Jdbc DBAdapter is always active, even if a reconnection is being tried
        return true;
    }

    public ConnectionPool getConnectionPool() {
        return this._pool;
    }

    public String generatedJdbcTableName(String className) throws Exception {
        String tableName = DBManager.getInstance().getTableName(className);
        return tableName;
    }

    private PreparedStatement getLoopTupleWithKeyStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("SELECT JOBKEY, TASKNAME, COUNTER, MAXCOUNTER, ISCOMPLETE FROM PROCESSLOOPSTATE WHERE LOOPKEY=?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement insertLoopTupleStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("INSERT INTO PROCESSLOOPSTATE (LOOPKEY, JOBKEY, TASKNAME, COUNTER, MAXCOUNTER, ISCOMPLETE) VALUES (?,?,?,?,?,?)");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement updateLoopTupleStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("UPDATE PROCESSLOOPSTATE SET JOBKEY=?, TASKNAME=?, COUNTER=?, MAXCOUNTER=?, ISCOMPLETE=? WHERE LOOPKEY=?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement deleteLoopTupleStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("DELETE FROM PROCESSLOOPSTATE WHERE LOOPKEY=? ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeUpdateStatement(queryBuf.toString()));
    }

    private PreparedStatement getMergeTupleWithKeyStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("SELECT TOKENCOUNT, EXPECTEDTOKENCOUNT, ISCOMPLETE, PROCESSID, PROCESSTIME, TRANSITIONNAME, ISERROR FROM PROCESSMERGESTATE WHERE MERGEKEY=?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement insertMergeTupleStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("INSERT INTO PROCESSMERGESTATE (MERGEKEY, TOKENCOUNT, EXPECTEDTOKENCOUNT, ISCOMPLETE, PROCESSID, PROCESSTIME, TRANSITIONNAME, ISERROR) VALUES (?,?,?,?,?,?,?,?)");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement updateMergeTupleStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("UPDATE PROCESSMERGESTATE set TOKENCOUNT=?, ISCOMPLETE=? WHERE MERGEKEY=?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement updateMergeTupleAndEntryStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("UPDATE PROCESSMERGESTATE set TOKENCOUNT=?, ISCOMPLETE=?, PROCESSID=?, PROCESSTIME=?, TRANSITIONNAME=?, ISERROR=? WHERE MERGEKEY=?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement deleteMergeTupleStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("DELETE FROM PROCESSMERGESTATE WHERE MERGEKEY=? ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeUpdateStatement(queryBuf.toString()));
    }

    private PreparedStatement insertObjectTableStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("INSERT INTO OBJECTTABLE (GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED) VALUES (?,?,?,?,?,?,?)");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement deleteObjectTableStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("DELETE FROM OBJECTTABLE WHERE GLOBALID=? ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeUpdateStatement(queryBuf.toString()));
    }

    private PreparedStatement updateObjectTableStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("UPDATE OBJECTTABLE SET ISDELETED=1, TIMEDELETED=? WHERE GLOBALID=? ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeUpdateStatement(queryBuf.toString()));
    }

    private PreparedStatement getObjectTableStatementWithId() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT EXTID, CLASSNAME  FROM OBJECTTABLE  WHERE GLOBALID=? AND ISDELETED=0 ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement getObjectTableStatementWithExtId() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT GLOBALID, CLASSNAME FROM OBJECTTABLE  WHERE EXTID=? AND ISDELETED=0 ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement getMaxEntityIdStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (multisite) {
            queryBuf.append("SELECT MAX(ID) FROM OBJECTTABLE  WHERE SITEID=?");
        } else {
            queryBuf.append("SELECT MAX(GLOBALID) FROM OBJECTTABLE");
        }
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement purgeObjectTableStatement(StringBuffer queryBuf) throws SQLException {
        queryBuf.append("DELETE FROM OBJECTTABLE WHERE ISDELETED = 1 AND TIMEDELETED <= ?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(queryBuf.toString()));
    }

    private PreparedStatement getLoadStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (multisite) {
            queryBuf.append("SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?");
        } else {
            queryBuf.append("SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0");
        }
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement getLoadConceptsStatement(String baseSelectSql, String idField, String[] orderBy) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(baseSelectSql);
        if (_cluster.getClusterConfig().useObjectTable() && !_cluster.getClusterConfig().isCacheAside()) {
            queryBuf.append(" WHERE T.");
            queryBuf.append(idField);
            if (multisite) {
                queryBuf.append(" IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?) ");
            } else {
                queryBuf.append(" IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0) ");
            }
        }
        queryBuf.append("ORDER BY T.").append(idField);
        if (orderBy != null) {
            for (int i = 0; i < orderBy.length; i++) {
                if (orderBy[i] != null) {
                    queryBuf.append(", T.");
                    queryBuf.append(orderBy[i]);
                }
            }
        }
        final String queryString = queryBuf.toString();
        sqltext.log(Level.TRACE, "Load concepts sql query : %s", queryString);
        return getSqlConnection().prepareStatement(queryString);
    }

    // This is used by QueryManager to handle BEView query and drilldown
    // This handles both regular query and count query
    private PreparedStatement getLoadMetricStatement(String baseSelectSql, String condition, String orderBy, String direction, boolean isSecondary) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(baseSelectSql);
        if (condition != null && condition.length() > 0) {
            queryBuf.append(" WHERE ");
            queryBuf.append(condition);
            if (isSecondary == true) {
                queryBuf.append(" AND T1.pid$ = T.id$ ");
            }
        } else {
            if (isSecondary == true) {
                queryBuf.append(" WHERE T1.pid$ = T.id$ ");
            } else {
                queryBuf.append(" ");
            }
        }
        /* FIX THIS - how to deal with multisite??
        if (multisite) {
            queryBuf.append(" IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?) ORDER BY T.");
        } else {
            queryBuf.append(" IN (SELECT GLOBALID FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0) ORDER BY T.");
        }
        */
        if (orderBy != null) {
            queryBuf.append(" order by ");
            queryBuf.append(orderBy);
            if (direction != null) {
                queryBuf.append(" ");
                queryBuf.append(direction);
            }
        }
        final String queryString = queryBuf.toString();
        if (sqlquery.isEnabledFor(Level.TRACE)) {
            sqlquery.log(Level.TRACE, "Load metric sql query : " + queryString);
        }
        return getSqlConnection().prepareStatement(queryString);
    }

    // This is used by QueryManager to handle BEView query and drilldown
    // We do not join it with object table
    private PreparedStatement getLoadMetricGroupsStatement(String tableName, String condition, String groupBy, String orderBy, String direction) throws SQLException {
        StringBuffer queryBuf = new StringBuffer("select ");
        queryBuf.append(groupBy);
        queryBuf.append(", count(1) from ");
        queryBuf.append(tableName + " T ");
        if (condition != null && condition.length() > 0) {
            queryBuf.append(" WHERE ");
            queryBuf.append(condition);
        } else {
            queryBuf.append(" ");
        }
        queryBuf.append(" group by ");
        queryBuf.append(groupBy);
        if (orderBy != null) {
            queryBuf.append(" order by ");
            queryBuf.append(orderBy);
            if (direction != null) {
                queryBuf.append(" ");
                queryBuf.append(direction);
            }
        }

        final String queryString = queryBuf.toString();
        if (sqlquery.isEnabledFor(Level.TRACE)) {
            sqlquery.log(Level.TRACE, "Group by sql query : " + queryString);
        }
        return getSqlConnection().prepareStatement(queryString);
    }

    private PreparedStatement getRecoveryStatement() throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (multisite) {
            queryBuf.append("SELECT GLOBALID, EXTID, CLASSNAME FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0 AND SITEID=?");
        } else {
            queryBuf.append("SELECT GLOBALID, EXTID, CLASSNAME FROM OBJECTTABLE  WHERE CLASSNAME=? AND ISDELETED=0");
        }
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement purgeDeletedItemsStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("DELETE FROM " + tableName + " T WHERE T.CACHEID = ? AND T.ID$ IN " +
                "(SELECT X.ID$ FROM " + tableName + " X, ObjectTable Z WHERE Z.ISDELETED=1 AND X.ID$=Z.ID) ");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(queryBuf.toString()));
    }

    private PreparedStatement migrateToObjectTableStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO OBJECTTABLE (GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED) SELECT T.ID$, ?, T.ID$, T.EXTID$, ?, 0 FROM " + tableName + " T ");
        if (multisite) {
            queryBuf.append(" WHERE T.ID$ NOT IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=0 AND SITEID=?)");
        } else {
            queryBuf.append(" WHERE T.ID$ NOT IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=0)");
        }
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(queryBuf.toString()));
    }

    private PreparedStatement numInstancesStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT COUNT(*) FROM " + tableName + " T");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement numHandlesStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT COUNT(*) FROM " + tableName + " T WHERE T.CACHEID = ?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement keysStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT T.ID$, T.EXTID$ FROM " + tableName + " T WHERE T.CACHEID = ?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement queryStatementByCacheId(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT * FROM " + tableName + "  P WHERE P.CACHEID=?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    // FIX THIS - Need to do a group select statements
    private PreparedStatement queryStatementById(String baseSelectSql, String idField, String[] orderBy, boolean useCacheId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(baseSelectSql);
        queryBuf.append(" WHERE T.");
        queryBuf.append(idField);
        queryBuf.append("=?");
        if (useCacheId) {
            queryBuf.append(" AND T.CACHEID=?");
        }
        if (orderBy != null && orderBy[0] != null) {
            queryBuf.append(" ORDER BY T.");
            queryBuf.append(orderBy[0]);
            for (int i = 1; i < orderBy.length; i++) {
                if (orderBy[i] != null) {
                    queryBuf.append(", T.");
                    queryBuf.append(orderBy[i]);
                }
            }
        }
        final String queryString = queryBuf.toString();
        sqltext.log(Level.TRACE, queryString);
        return getSqlConnection().prepareStatement(queryString);
    }

    private PreparedStatement verifyStatementById(String tableName, boolean useCacheId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        if (useCacheId) {
            queryBuf.append("SELECT P.ID$ FROM " + tableName + "  P WHERE P.ID$=? AND P.CACHEID=? ");
        } else {
            queryBuf.append("SELECT P.ID$ FROM " + tableName + "  P WHERE P.ID$=? ");
        }
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement deleteStatementById(StringBuffer queryBuf, String tableName) throws SQLException {
        queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ID$ = ?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(queryBuf.toString()));
    }

    private PreparedStatement deleteBatchStatementById(StringBuffer queryBuf, String tableName, int size) throws SQLException {
        queryBuf.append("DELETE FROM " + tableName + " P WHERE P.ID$ IN (");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append(") AND P.CACHEID= ?");
        return getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(queryBuf.toString()));
    }

    public boolean deleteEntityById(String tableName, long id) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        PreparedStatement stmt = deleteStatementById(queryBuf, tableName);
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, "%s (%s, %s)", queryBuf, tableName, id);
        }
        stmt.setLong(1, id);
        //stmt.setLong(2, cacheId); // TODO: Fatih - Validate this
        stmt.executeUpdate();
        stmt.close();
        return true;
    }

    public void deleteEntitiesInBatch(String tableName, Collection entities, long cacheId) throws SQLException, Exception {
        if (entities == null || entities.size() <= 0) return;
        StringBuffer queryBuf = new StringBuffer();
        PreparedStatement stmt = deleteBatchStatementById(queryBuf, tableName, entities.size());
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

    public long countEntities(int typeId) throws Exception {
        Class entityClz = _cluster.getMetadataCache().getClass(typeId);
        String tableName = DBManager.getInstance().getTableName(entityClz.getName());
        if (tableName != null) {
            PreparedStatement stmt = numInstancesStatement(tableName);
            ResultSet rs = stmt.executeQuery();
            long count = 0L;
            while (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            stmt.close();
            return count;
        }
        return 0L;
    }

    public ConceptsWithVersionIterator loadConcepts(int typeId, int fetchSize) throws Exception {
        List resultList = new ArrayList();
        String className = this._cluster.getMetadataCache().getClass(typeId).getName();
        Class entityClz = this._cluster.getMetadataCache().getClass(typeId);
        DBConceptMap conceptMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (conceptMap == null) {
            logger.log(Level.WARN, "%s missing in the entity-map", className);
            logger.log(Level.DEBUG, "%s missing in the entity-map %s", className, DBManager.getInstance().getEntityPropsMap());
            return null;
        }
        String tableName = conceptMap.getTableName();
        if (tableName != null) {
            logger.log(Level.INFO, "Loading %s entries from %s", className, tableName);
            PreparedStatement stmt = this.getLoadConceptsStatement(conceptMap.getPrimarySelectSql(), DBConceptMap.ID_FIELD_NAME, null);
            stmt.setFetchSize(fetchSize);
            if (_cluster.getClusterConfig().useObjectTable() && !_cluster.getClusterConfig().isCacheAside()) {
                stmt.setString(1, entityClz.getName());
                if (multisite) {
                    stmt.setLong(2, _cluster.getClusterConfig().getSiteId());
                }
            }

            ResultSet rs = stmt.executeQuery();
            ResultMap rm = new ResultMap(null, null, stmt, rs);
            resultList.add(rm);

            Map secondaryTableSelect = conceptMap.getSecondarySelectSqlMap();
            if (secondaryTableSelect != null && secondaryTableSelect.size() > 0) {
                for (Iterator iter = secondaryTableSelect.keySet().iterator(); iter.hasNext();) {
                    String fieldName = (String) iter.next();
                    String selectSql = (String) secondaryTableSelect.get(fieldName);
                    DBEntityMap.DBFieldMap fMap = conceptMap.getFieldMap(fieldName);
                    String additionalOrderBy[] = new String[2];
                    if (fMap.isArray) {
                        additionalOrderBy[0] = "valPid$";
                        if (fMap.hasHistory) {
                            additionalOrderBy[1] = "timeidx";
                        }
                    } else if (fMap.hasHistory) {
                        additionalOrderBy[0] = "timeidx";
                    }
                    stmt = this.getLoadConceptsStatement(selectSql, "pid$", additionalOrderBy);
                    stmt.setFetchSize(fetchSize);
                    if (_cluster.getClusterConfig().useObjectTable() && !_cluster.getClusterConfig().isCacheAside()) {
                        stmt.setString(1, entityClz.getName());
                        if (multisite) {
                            stmt.setLong(2, _cluster.getClusterConfig().getSiteId());
                        }
                    }
                    rs = stmt.executeQuery();
                    rm = new ResultMap(fieldName, fMap, stmt, rs);
                    resultList.add(rm);
                }
            }
            return new ConceptsWithVersionIterator(resultList, this, conceptMap);
        }
        return null;
    }

    // general query for metric and metric dvm.
    // valuelist and typelist are for bind variables
    public ConceptsWithVersionIterator loadMetrics(String condition, String orderBy, String direction, DBConceptMap conceptMap, List valueList, List<Integer> typeList) {
        try {
            if (logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG, "LoadMetrics loading primary tables for %s entries from %s", conceptMap.getEntityClass().getName(), conceptMap.getTableName());
            }
            
            Class entityClz = conceptMap.getEntityClass(_rsp);
            List resultList = new ArrayList();
            PreparedStatement stmt = this.getLoadMetricStatement(conceptMap.getPrimarySelectSql(), condition, orderBy, direction, false);

            int size = valueList.size();
            for (int i = 0; i < size; i++) {
                DBHelper.setColumnData(stmt, i + 1, typeList.get(i), valueList.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            ResultMap rm = new ResultMap(null, null, stmt, rs);
            resultList.add(rm);

            if (!conceptMap.isMetric()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG, "LoadMetrics loading secondary tables for %s entries from %s", conceptMap.getEntityClass().getName(), conceptMap.getTableName());               
                }
                Map secondaryTableSelect = conceptMap.getSecondarySelectSqlMap();
                if (secondaryTableSelect != null && secondaryTableSelect.size() > 0) {
                    for (Iterator iter = secondaryTableSelect.keySet().iterator(); iter.hasNext();) {
                        String fieldName = (String) iter.next();
                        String selectSql = (String) secondaryTableSelect.get(fieldName);
                        DBEntityMap.DBFieldMap fMap = conceptMap.getFieldMap(fieldName);
                        String additionalOrderBy[] = new String[2];
                        if (fMap.isArray) {
                            additionalOrderBy[0] = "valPid$";
                            if (fMap.hasHistory) {
                                additionalOrderBy[1] = "timeidx";
                            }
                        } else if (fMap.hasHistory) {
                            additionalOrderBy[0] = "timeidx";
                        }
                        selectSql = selectSql.replace(" T.", " T1.");
                        selectSql = selectSql.replace(" T ", " T1, ");
                        selectSql += conceptMap.getTableName();
                        selectSql += " T ";
                        stmt = this.getLoadMetricStatement(selectSql, condition, orderBy, direction, true);
                        //stmt = this.getLoadConceptsStatement(selectSql, "pid$", additionalOrderBy);
                        size = valueList.size();
                        for (int i = 0; i < size; i++) {
                            DBHelper.setColumnData(stmt, i + 1, typeList.get(i), valueList.get(i));
                        }
                        rs = stmt.executeQuery();
                        rm = new ResultMap(fieldName, fMap, stmt, rs);
                        resultList.add(rm);
                    }
                }
            }
            
            // Cleanup is done with next is false in ConceptsWithVersionIterator
            return new ConceptsWithVersionIterator(resultList, this, conceptMap);
        }
        catch (Exception e) {
            logger.log(Level.INFO, "Failed to query metric", e);
            releaseConnection();
        }
        return null;
    }

    public int loadMetricCount(String tableName, String condition, List valueList, List<Integer> typeList) {
        StringBuffer sqlBuf = new StringBuffer("select count(1) from ");
        sqlBuf.append(tableName);
        sqlBuf.append(" T ");

        int count = 0;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = this.getLoadMetricStatement(sqlBuf.toString(), condition, null, null, false);
            int size = valueList.size();
            for (int i = 0; i < size; i++) {
                DBHelper.setColumnData(stmt, i + 1, typeList.get(i), valueList.get(i));
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch (Exception e) {
            logger.log(Level.INFO, "Query metric count failed", e);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (Exception e) {
                    logger.log(Level.INFO, "Failed to close result set", e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception e) {
                    logger.log(Level.INFO, "Failed to close statement", e);
                }
            }
            releaseConnection();
        }
        return count;
    }

    public Map<String, Integer> loadMetricGroups(String tableName, String condition, String groupBy, String orderBy, String direction, List valueList, List<Integer> typeList, int groupByType) {
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sval = null;
        int count = 0;

        try {
            stmt = this.getLoadMetricGroupsStatement(tableName, condition, groupBy, orderBy, direction);
            int size = valueList.size();
            for (int i = 0; i < size; i++) {
                DBHelper.setColumnData(stmt, i + 1, typeList.get(i), valueList.get(i));
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (groupByType == DBEntityMap.FTYPE_DATETIME) {
                    java.sql.Timestamp val = (java.sql.Timestamp) DBHelper.getColumnData(rs, 1, DBEntityMap.FTYPE_TIMESTAMP);
                    if (val != null) {
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    	sval = sdf.format(val);
                    }
                } else if (groupByType == DBEntityMap.FTYPE_BOOLEAN) {
                    Boolean val = (Boolean) DBHelper.getColumnData(rs, 1, DBEntityMap.FTYPE_BOOLEAN);
                    if (val != null) {
                    	sval = val.toString();
                    }
                } else {
                    sval = rs.getString(1);
                }
                count = rs.getInt(2);
                resultMap.put(sval, count);
            }
        }
        catch (Exception e) {
            logger.log(Level.INFO, "Query group by metric failed", e);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (Exception e) {
                    logger.log(Level.INFO, "Failed to close result set", e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception e) {
                    logger.log(Level.INFO, "Failed to close statement", e);
                }
            }
            releaseConnection();
        }
        return resultMap;
    }

    public EventsIterator loadEvents(int typeId, int fetchSize) throws Exception {
        String className = this._cluster.getMetadataCache().getClass(typeId).getName();
        Class entityClz = this._cluster.getMetadataCache().getClass(typeId);
        DBEventMap eventMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (eventMap == null) {
            logger.log(Level.WARN, "%s missing in the entity-map", className);
            logger.log(Level.DEBUG, "%s missing in the entity-map %s", className, DBManager.getInstance().getEntityPropsMap());
            return null;
        }
        String tableName = eventMap.getTableName();
        if (tableName != null) {
            logger.log(Level.INFO, "Loading %s entries from %s", className, tableName);
            PreparedStatement stmt = this.getLoadConceptsStatement(eventMap.getPrimarySelectSql(), DBEventMap.ID_FIELD_NAME, null);
            stmt.setFetchSize(fetchSize);
            if (_cluster.getClusterConfig().useObjectTable() && !_cluster.getClusterConfig().isCacheAside()) {
                stmt.setString(1, entityClz.getName());
                if (multisite) {
                    stmt.setLong(2, _cluster.getClusterConfig().getSiteId());
                }
            }
            ResultSet rs = stmt.executeQuery();
            return new EventsIterator(rs, this, eventMap);
        }
        return null;
    }

    /**
     * @param jdbcTableName
     * @param cacheId
     * @return
     * @throws Exception
     */
    public ConceptsIterator getConcepts(String tableName, long cacheId) throws SQLException {
        //TODO: Fatih - Fix this!!!
        logger.log(Level.ERROR, "Outdated version of getConcepts is used");
        PreparedStatement ps = queryStatementByCacheId(tableName);
        //TODO: ps.setFetchSize(PREFETCH_SIZE);
        ps.setLong(1, cacheId);
        ResultSet rs = ps.executeQuery();
        return new ConceptsIterator(rs);
    }

    /**
     * @param jdbcTableName
     * @param cacheId
     * @return
     * @throws Exception
     */
    public EventsIterator getEvents(String tableName, long cacheId) throws SQLException {
        //TODO: Fatih - Fix this!!!
        logger.log(Level.ERROR, "Outdated version of getEvents is used");
        PreparedStatement ps = queryStatementByCacheId(tableName);
        //TODO: ps.setFetchSize(PREFETCH_SIZE);
        ps.setLong(1, cacheId);
        ResultSet rs = ps.executeQuery();
        return new EventsIterator(rs, this, null);
    }

    public boolean entityExists(String tableName, long id, long cacheId) throws SQLException {
        PreparedStatement stmt = verifyStatementById(tableName, false);
        stmt.setLong(1, id);
        //stmt.setLong(2, cacheId);
        ResultSet rs = stmt.executeQuery();
        long ret = -1L;
        while (rs.next()) {
            ret = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return (ret != -1L);
    }

    /**
     * @param tableName
     * @param cacheId
     * @return
     * @throws Exception
     */
    public Iterator keyPairs(String tableName, long cacheId) throws Exception {
        PreparedStatement stmt = this.keysStatement(tableName);
        stmt.setLong(1, cacheId);
        //TODO: stmt.setFetchSize(PREFETCH_SIZE);
        ResultSet rs = stmt.executeQuery();
        return new KeyPairIterator(rs);
    }

    public long countKeyPairs(String tableName, long cacheId) throws Exception {
        PreparedStatement stmt = this.numHandlesStatement(tableName);
        stmt.setLong(1, cacheId);
        //TODO: stmt.setFetchSize(PREFETCH_SIZE);
        ResultSet rs = stmt.executeQuery();
        long count = 0L;
        while (rs.next()) {
            count = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return count;
    }

    public StateMachineConcept getStateMachineById(String tableName, long id, long cacheId) throws SQLException, Exception {
        if (!this._cluster.getClusterConfig().isNewSerializationEnabled()) {
            return (StateMachineConcept) getConceptById(tableName, id, cacheId);
        }
        DBConceptMap entityMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMapByTableName().get(tableName);
        if (entityMap == null) {
            logger.log(Level.WARN, "%s has no state machine map", tableName);
            return null;
        }

        StateMachineConcept sm = null;
        List resultList = new ArrayList();
        PreparedStatement stmt = queryStatementById(entityMap.getPrimarySelectSql(), DBConceptMap.ID_FIELD_NAME, null, false);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        ResultMap rm = new ResultMap(null, null, stmt, rs);
        resultList.add(rm);
        Iterator itr = new ConceptsWithVersionIterator(resultList, this, entityMap);
        if (itr.hasNext()) {
            sm = (StateMachineConcept) itr.next();
        } else {
            logger.log(Level.DEBUG, "No results found %s, %s, %s", tableName, id, cacheId);
        }
        ((ConceptsWithVersionIterator) itr).close();
        return sm;
    }

    public com.tibco.cep.runtime.model.element.Concept getConceptById(String tableName, long id, long cacheId) throws SQLException, Exception {
        DBConceptMap entityMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMapByTableName().get(tableName);
        if (entityMap == null) {
            logger.log(Level.WARN, "%s has no concept map - %s",
                    tableName, DBManager.getInstance().getEntityPropsMapByTableName());
            return null;
        }

        com.tibco.cep.runtime.model.element.Concept cept = null;
        List resultList = new ArrayList();
        PreparedStatement stmt = queryStatementById(entityMap.getPrimarySelectSql(), DBConceptMap.ID_FIELD_NAME, null, false);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        ResultMap rm = new ResultMap(null, null, stmt, rs);
        resultList.add(rm);

        Map secondaryTableSelect = entityMap.getSecondarySelectSqlMap();
        if (secondaryTableSelect != null && secondaryTableSelect.size() > 0) {
            for (Iterator iter = secondaryTableSelect.keySet().iterator(); iter.hasNext();) {
                String fieldName = (String) iter.next();
                String selectSql = (String) secondaryTableSelect.get(fieldName);
                DBEntityMap.DBFieldMap fMap = entityMap.getFieldMap(fieldName);
                String additionalOrderBy[] = new String[2];
                if (fMap.isArray) {
                    additionalOrderBy[0] = "valPid$";
                    if (fMap.hasHistory) {
                        additionalOrderBy[1] = "timeidx";
                    }
                } else if (fMap.hasHistory) {
                    additionalOrderBy[0] = "timeidx";
                }
                stmt = queryStatementById(selectSql, "pid$", additionalOrderBy, false);
                stmt.setLong(1, id);
                rs = stmt.executeQuery();
                rm = new ResultMap(fieldName, fMap, stmt, rs);
                resultList.add(rm);
            }
        }
        Iterator itr = new ConceptsWithVersionIterator(resultList, this, entityMap);
        if (itr.hasNext()) {
            cept = (Concept) itr.next();
        }
        ((ConceptsWithVersionIterator) itr).close();
        return cept;
    }

    //TODO: Fatih - Validate if needed
    public Map getConceptByIds(String tableName, Collection keys, long cacheId) throws Exception {
        int size = keys.size();
        if (size > IN_BATCH_SIZE) {
            HashMap concepts = new HashMap();
            List batches = getBatches(keys);
            Iterator it = batches.iterator();
            while (it.hasNext()) {
                List subset = (List)it.next();
                concepts.putAll(getSafeConceptByIds(tableName, subset, cacheId));
            }
            return concepts;
        } else {
            return getSafeConceptByIds(tableName, keys, cacheId);
        }
    }

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

    //TODO: Fatih - Fix and Validate if needed
    private Map getSafeConceptByIds(String tableName, Collection keys, long cacheId) throws Exception {
        /* FIX THIS
        PreparedStatement stmt = queryStatementByIds(tableName, keys.size());
        stmt.setLong(1, cacheId);
        Iterator it = keys.iterator();
        int count = 2;
        HashMap concepts = new HashMap();
        while (it.hasNext()) {
            long id = ((Long)it.next()).longValue();
            stmt.setLong(count, id);
            count++;
        }
        ResultSet rs = stmt.executeQuery();
        Iterator keyIterator = keys.iterator();
        while (rs.next()) {
            STRUCT s = rs.getSTRUCT(1);
            com.tibco.cep.runtime.model.element.Concept cept = createConcept(s);
            concepts.put(keyIterator.next(), cept);
        }
        rs.close();
        stmt.close();
        return concepts;
        */
        return null;
    }

    public LinkedHashMap getClassRegistry() throws SQLException {
        LinkedHashMap classRegistry = new LinkedHashMap();
        PreparedStatement getStmt = this.getSqlConnection().prepareStatement("SELECT className, typeId FROM ClassRegistry");
        ResultSet rs = getStmt.executeQuery();
        while (rs.next()) {
            String entityClz = (String) rs.getString(1);
            Integer typeId = (Integer) rs.getInt(2);
            classRegistry.put(entityClz, typeId);
        }
        //this.getSqlConnection().rollback();
        rs.close();
        getStmt.close();
        return classRegistry;
    }

    public void saveClassRegistry(Map classRegistry) throws SQLException {
        // Delete the existing registry
        PreparedStatement delStmt = this.getSqlConnection().prepareStatement("DELETE FROM ClassRegistry");
        delStmt.executeUpdate();
        delStmt.close();
        // Insert complete list
        Iterator allClasses = classRegistry.entrySet().iterator();
        while (allClasses.hasNext()) {
            Map.Entry entry = (Map.Entry) allClasses.next();
            String entityClz = (String) entry.getKey();
            Integer typeId = (Integer) entry.getValue();
            PreparedStatement stmt = this.getSqlConnection().prepareStatement("INSERT INTO ClassRegistry (className, typeId) VALUES (?,?)");
            logger.log(Level.DEBUG, "INSERT INTO ClassRegistry (className, typeId) VALUES (%s,%s)", entityClz, typeId); 
            stmt.setString(1, entityClz);
            stmt.setInt(2, typeId);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public void saveClassRegistration(String entityClz, int typeId) throws SQLException {
        PreparedStatement delStmt = this.getSqlConnection().prepareStatement("DELETE FROM ClassRegistry WHERE className = ?");
        delStmt.setString(1, entityClz);
        delStmt.executeUpdate();
        delStmt.close();
        PreparedStatement stmt = this.getSqlConnection().prepareStatement("INSERT INTO ClassRegistry (className, typeId) VALUES (?,?)");
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
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = purgeObjectTableStatement(queryBuf);
        stmt.setLong(1, time);
        stmt.executeUpdate();
        stmt.close();
    }

    public void saveObjectTable(Map<Long, ObjectTable.Tuple> tuples) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.insertObjectTableStatement(queryBuf);
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
                stmt.setString(5, this._cluster.getMetadataCache().getClass(tuple.getTypeId()).getName());
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
                            this._cluster.getMetadataCache().getClass(tuple.getTypeId()).getName(),
                            (tuple.isDeleted() ? 1 : 0),
                            (tuple.isDeleted() ? tuple.getTimeDeleted() : null));
                }
                stmt.addBatch();
            }
            stmt.executeBatch();
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
        PreparedStatement stmt = this.insertObjectTableStatement(queryBuf);
        try {
            stmt.setLong(1, tuple.getId());
            stmt.setLong(2, EntityIdMask.getMaskedId(tuple.getId()));
            stmt.setLong(3, EntityIdMask.getEntityId(tuple.getId()));
    
            if (tuple.getExtId() != null) {
                stmt.setString(4, tuple.getExtId());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }

            stmt.setString(5, this._cluster.getMetadataCache().getClass(tuple.getTypeId()).getName());
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
                        this._cluster.getMetadataCache().getClass(tuple.getTypeId()).getName(),
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
    public Iterator recoverType(long siteId, int typeId, int maxRows, int fetchSize) throws Exception {
        String clzName = _cluster.getMetadataCache().getClass(typeId).getName();
        PreparedStatement stmt = null;
        if (_cluster.getClusterConfig().useObjectTable() && !_cluster.getClusterConfig().isCacheAside()) {
            stmt = this.getRecoveryStatement();
            stmt.setString(1, clzName);
            if (multisite) {
                stmt.setLong(2, siteId);
            }
        } else {
            stmt = getRecoveryStatementUsingBaseTables(clzName);
        }
        //TODO: stmt.setFetchSize(PREFETCH_SIZE);
        if (maxRows > 0) {
            stmt.setMaxRows(maxRows);
        }
        stmt.setFetchSize(fetchSize);
        ResultSet rs = stmt.executeQuery();
        if (_cluster.getClusterConfig().useObjectTable() && !_cluster.getClusterConfig().isCacheAside()) {
            return new ObjectTableIterator(rs);
        } else {
            return new ObjectTableIteratorUsingBaseTables(rs, typeId);
        }
    }

    public Set recoverIds(long siteId, int typeId, int maxRows) throws Exception {
        HashSet ret = new HashSet(maxRows);
        String clzName = _cluster.getMetadataCache().getClass(typeId).getName();
        PreparedStatement stmt = null;
        if (_cluster.getClusterConfig().useObjectTable()) {
            stmt = this.getLoadStatement();
            stmt.setString(1, clzName);
            if (multisite) {
                stmt.setLong(2, siteId);
            }
        } else {
            stmt = this.getLoadStatementUsingBaseTables(clzName);
        }
        //TODO: stmt.setFetchSize(PREFETCH_SIZE);
        if (maxRows > 0) {
            stmt.setMaxRows(maxRows);
        }
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ret.add(rs.getLong(1));
        }
        rs.close();
        stmt.close();
        return ret;
    }

    public Iterator recoverIdsWithIterator(long siteId,int typeId, int maxRows) throws Exception {
        String clzName = _cluster.getMetadataCache().getClass(typeId).getName();
        PreparedStatement stmt = null;
        if (_cluster.getClusterConfig().useObjectTable()) {
            stmt = this.getLoadStatement();
            stmt.setString(1, clzName);
            if (multisite) {
                stmt.setLong(2,siteId);
            }
        } else {
            stmt = this.getLoadStatementUsingBaseTables(clzName);
        }
        //TODO: stmt.setFetchSize(PREFETCH_SIZE);
        if (maxRows > 0) {
            stmt.setMaxRows(maxRows);
        }
        ResultSet rs = stmt.executeQuery();
        //rs.close();
        //stmt.close();
        return new IdIterator(rs);
    }

    public class IdIterator implements Iterator {
        ResultSet rs;
        public IdIterator(ResultSet rs) {
            this.rs = rs;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = rs.next();
                if (!hasNext) {
                    close();
                }
                return hasNext;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Iterator failure", e);
            }
            return false;
        }

        public Object next() {
            try {
                return rs.getLong(1);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Iterator failure", e);
            }
            return null;
        }

        public void remove() {
            // TODO Auto-generated method stub
        }

        private void close() {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Iterator failure", e);
            }
        }
    }

    /**
     * @param siteId
     * @param typeId
     * @throws Exception
     */
    public void purgeDeletedEntities(long siteId, int typeId) throws Exception {
        String className = this._cluster.getMetadataCache().getClass(typeId).getName();
        DBEntityMap entityMap = (DBEntityMap) DBManager.getInstance().getEntityPropsMap().get(className);
        String tableName = entityMap.getTableName();
        String[] secondaryTableNames = entityMap.getSecondaryTableNames();

        String delSql = "";
        if (multisite) {
            delSql = "DELETE FROM " + tableName + " WHERE ID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 AND CLASSNAME=? AND SITEID=?)";
        } else {
            delSql = "DELETE FROM " + tableName + " WHERE ID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 AND CLASSNAME=?)";
        }
        delSql = RDBMSType.sSqlType.optimizeDeleteStatement(delSql);
        logger.log(Level.DEBUG, "%s : %s", className, delSql);
        PreparedStatement stmt = getSqlConnection().prepareStatement(delSql);
        stmt.setString(1, className);
        if (multisite) {
            stmt.setLong(2, siteId);
        }
        stmt.executeUpdate();
        stmt.close();
        if (secondaryTableNames != null) {
            for (int i = 0; i < secondaryTableNames.length; i++) {
                if (multisite) {
                    delSql = "DELETE FROM " + secondaryTableNames[i] + " WHERE PID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 AND CLASSNAME=? AND SITEID=?)";
                } else {
                    delSql = "DELETE FROM " + secondaryTableNames[i] + " WHERE PID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 AND CLASSNAME=?)";
                }
                delSql = RDBMSType.sSqlType.optimizeDeleteStatement(delSql);
                logger.log(Level.DEBUG, delSql);
                stmt = getSqlConnection().prepareStatement(delSql);
                stmt.setString(1, className);
                if (multisite) {
                    stmt.setLong(2, siteId);
                }
                stmt.executeUpdate();
                stmt.close();
            }
        }
    }

    public void purgeStateMachineTimeouts(long siteId) throws Exception {
        String tableName = ((DBEntityMap) DBManager.getInstance().getEntityPropsMap().get(StateMachineConceptImpl.StateTimeoutEvent.class.getName())).getTableName();
        String statement = "";
        if (multisite) {
            statement = "DELETE FROM OBJECTTABLE WHERE CLASSNAME = ? AND SITEID = ? AND GLOBALID NOT IN (SELECT ID$ FROM " + tableName + ")";
        } else {
            statement = "DELETE FROM OBJECTTABLE WHERE CLASSNAME = ? AND GLOBALID NOT IN (SELECT ID$ FROM " + tableName + ")";
        }
        statement = RDBMSType.sSqlType.optimizeDeleteStatement(statement);
        PreparedStatement stmt = getSqlConnection().prepareStatement(statement);
        stmt.setString(1, StateMachineConceptImpl.StateTimeoutEvent.class.getName());
        if (multisite) {
            stmt.setLong(2, siteId);
        }
        stmt.executeUpdate();
        stmt.close();
    }

    public void migrateToObjectTable(long siteId, int typeId) throws Exception {
        Class entityClz = this._cluster.getMetadataCache().getClass(typeId);
        String tableName = generatedJdbcTableName(entityClz.getName());
        if (tableName != null) {
            logger.log(Level.INFO, "Migrating entries to ObjectTable from %s", tableName);
            PreparedStatement stmt = this.migrateToObjectTableStatement(tableName);
            stmt.setLong(1, siteId);
            stmt.setString(2, this._cluster.getMetadataCache().getClass(typeId).getName());
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
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.deleteObjectTableStatement(queryBuf);
        stmt.setLong(1, id);
        stmt.executeUpdate();
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, "%s (%s)",
                    queryBuf,
                    id);
        }
        stmt.close();
    }

    public void updateObjectTable(long id) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.updateObjectTableStatement(queryBuf);
        stmt.setLong(1, System.currentTimeMillis());
        stmt.setLong(2, id);
        stmt.executeUpdate();
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, "%s (%s, %s)",
                    queryBuf,
                    System.currentTimeMillis(),
                    id);
        }
        stmt.close();
    }

    public ObjectTupleImpl getObjectTable(long id) throws Exception {
        PreparedStatement stmt = this.getObjectTableStatementWithId();
        /** SELECT EXTID, CLASSNAME  FROM OBJECTTABLE  WHERE GLOBALID=? AND ISDELETED=0 */
        stmt.setLong(1, id);
        //TODO: stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        while (rs.next()) {
            String extId = rs.getString(1);
            String className = rs.getString(2);
            tuple = new ObjectTupleImpl(id, extId, this._cluster.getMetadataCache().getTypeId(className));
        }
        rs.close();
        stmt.close();
        return tuple;
    }

    public ObjectTupleImpl getObjectTable(String extId) throws Exception {
        PreparedStatement stmt = this.getObjectTableStatementWithExtId();
        /** SELECT GLOBALID, CLASSNAME FROM OBJECTTABLE  WHERE EXTID=? AND ISDELETED=0 */
        stmt.setString(1, extId);
        //TODO: stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        while (rs.next()) {
            long id = rs.getLong(1);
            String className = rs.getString(2);
            tuple = new ObjectTupleImpl(id, extId, this._cluster.getMetadataCache().getTypeId(className));
        }
        rs.close();
        stmt.close();
        return tuple;
    }

    public long getMaxEntityId(long siteId) throws Exception {
        PreparedStatement stmt = this.getMaxEntityIdStatement();
        if (multisite) {
            stmt.setLong(1, siteId);
        }
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, this.multisite
                ? "SELECT MAX(ID) FROM OBJECTTABLE  WHERE SITEID=?"
                : "SELECT MAX(GLOBALID) FROM OBJECTTABLE");
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

    public void insertEvents(String className, Map<Long, Event> entries) throws Exception {
        List serializers = new ArrayList();
        DBEventMap entityMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (entityMap == null) {
            logger.log(Level.WARN, "InsertEvents cannot find map for %s", className);
            return;
        }
        PreparedStatement stmt = null;
        try {
            stmt = getSqlConnection().prepareStatement(entityMap.getPrimaryInsertSql());
            //stmt.setExecuteBatch(entries.size());
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, Event> entry = (Map.Entry<Long, Event>) all_entries.next();
                @SuppressWarnings("unused")
                Long id = entry.getKey();
                Event event = entry.getValue();
                DBEventSerializer serializer = new DBEventSerializer(entityMap, getSqlConnection(), getQualifier(), stmt, 2, _useTemporaryBlobs);
                serializers.add(serializer);
                DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, new Long(1));
                if (event instanceof SimpleEvent) {
                    ((SimpleEvent) event).serialize(serializer);
                } else {
                    ((TimeEvent) event).serialize(serializer);
                }
                /*
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + event + " with message " + serializer.getErrorMessage());
                }
                */
                logger.log(Level.DEBUG, "Try to insert %s, %s", className, event.getId());
                logEvent(entityMap.getPrimaryInsertSql(), event);
                stmt.addBatch();
            }
            int[] result = stmt.executeBatch();
            examineBatchResults("Insert-events", result);
        } catch (Exception ex) {
            throw ex;
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

    public Map<Long,List<Long>> insertConcepts(String className, Map<Long, Concept> entries) throws Exception {
        DBConceptMap entityMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (entityMap == null) {
            logger.log(Level.WARN, "InsertConcepts cannot find map for %s", className);
            return Collections.EMPTY_MAP;
        }
        PreparedStatement stmt = null;
        Map stmtMap = new HashMap();
        Map<Long,List<Long>> addedConcepts = new HashMap<Long,List<Long>>();
        try {
            stmt = getSqlConnection().prepareStatement(entityMap.getPrimaryInsertSql());
            Map secondaryInsertSqlMap = entityMap.getSecondaryInsertSqlMap();

            for (Iterator itr = secondaryInsertSqlMap.keySet().iterator(); itr.hasNext();) {
                String fieldName = (String) itr.next();
                PreparedStatement secStmt = getSqlConnection().prepareStatement((String) secondaryInsertSqlMap.get(fieldName));
                stmtMap.put(fieldName, secStmt);
            }
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, Concept> entry = (Map.Entry<Long, Concept>) all_entries.next();
                Long id = entry.getKey();
                Concept cept = entry.getValue();
				try {
                	if (null != ((ConceptImpl)cept).getParentReference()) {
                	    Long pid = ((ConceptImpl)cept).getParentReference().getId();
		                addedConcepts = appendList(addedConcepts, pid, id);
                	}
				} catch (Exception e) {
	                logger.log(Level.WARN, "Failed to get parent-id: %s", e, e.getMessage());
				}
                DBConceptSerializer serializer = new DBConceptSerializer(entityMap, getSqlConnection(), getQualifier(), _useTemporaryBlobs);
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }
                logConcept(entityMap.getPrimaryInsertSql(), cept, null);
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
                logger.log(Level.DEBUG, "Try to insert %s, %s, %s", className, attrs[0], attrs[1]);
                stmt.addBatch();
                for (Iterator itr = secondaryAttributeMap.keySet().iterator(); itr.hasNext();) {
                    String fieldName = (String) itr.next();
                    Object value = secondaryAttributeMap.get(fieldName);
                    if (value == null) {
                        continue;
                    }
                    PreparedStatement sstmt = (PreparedStatement) stmtMap.get(fieldName);
                    DBHelper.populateSecondaryTable(entityMap.getFieldMap(fieldName), sstmt, value, id);
                }
            }
            int[] result = stmt.executeBatch();
            examineBatchResults("Insert-concepts", result);
            for (Iterator itr = stmtMap.values().iterator(); itr.hasNext();) {
            	int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary insert-concepts", secresult);
            }
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
        return addedConcepts;
    }

    public void modifyConcepts(String className, Map<Long, Concept> entries,
            Map<Long,List<Long>> added, List<Long> deleted, List<Long> modified,
            Map<Long, ConceptStatus> statuses) throws Exception {
        if (DBHelper._skipUnmodifiedProperties == true) {
            modifyConceptsOptimized(className, entries, added, deleted, modified, statuses);
        } else {
            modifyConceptsUnoptimized(className, entries, added, deleted, modified);
        }
    }

    public void modifyConceptsUnoptimized(String className, Map<Long, Concept> entries,
            Map<Long,List<Long>> added, List<Long> deleted, List<Long> modified) throws Exception {
        boolean isDebugLevelLog = logger.isEnabledFor(Level.DEBUG);
        DBConceptMap entityMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (entityMap == null) {
            logger.log(Level.WARN, "ModifyConcepts cannot find map for %s", className);
            return;
        }
        PreparedStatement stmt = null;
        Map stmtInsMap = new HashMap();
        Map stmtDelMap = new HashMap();
        Map stmtRemMap = new HashMap();
        try {
            stmt = getSqlConnection().prepareStatement(entityMap.getPrimaryUpdateSql());
            Map secondaryDeleteSqlMap = entityMap.getSecondaryDeleteSqlMap();
            Map secondaryRemoveSqlMap = entityMap.getSecondaryRemoveSqlMap();
            Map secondaryInsertSqlMap = entityMap.getSecondaryInsertSqlMap();

            for (Iterator itr = secondaryDeleteSqlMap.keySet().iterator(); itr.hasNext();) {
                String fieldName = (String) itr.next();
                PreparedStatement secStmt = getSqlConnection().prepareStatement((String) secondaryInsertSqlMap.get(fieldName));
                stmtInsMap.put(fieldName, secStmt);
                //System.err.println("ModifyConcepts - field:" + fieldName + "/" + secondaryInsertSqlMap.get(fieldName));
                secStmt = getSqlConnection().prepareStatement((String) secondaryDeleteSqlMap.get(fieldName));
                stmtDelMap.put(fieldName, secStmt);
                //System.err.println("ModifyConcepts - field:" + fieldName + "/" + secondaryDeleteSqlMap.get(fieldName));
                secStmt = getSqlConnection().prepareStatement((String) secondaryRemoveSqlMap.get(fieldName));
                stmtRemMap.put(fieldName, secStmt);
                //System.err.println("ModifyConcepts - field:" + fieldName + "/" + secondaryRemoveSqlMap.get(fieldName));
            }
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                Map.Entry<Long, Concept> entry = (Map.Entry<Long, Concept>) all_entries.next();
                Long id = entry.getKey();
                Concept cept = entry.getValue();
                DBConceptSerializer serializer = new DBConceptSerializer(entityMap, getSqlConnection(), getQualifier(), _useTemporaryBlobs);
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }
                logConcept(entityMap.getPrimaryUpdateSql(), cept, null);
                Object[] attrs = serializer.getAttributes();
                Map secondaryAttributeMap = serializer.getSecondaryAttributeMap();
                Map<String,List<Long>> secondaryDatabaseMap = new HashMap<String,List<Long>>();
                DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, new Long(((VersionedObject) cept).getVersion()));
                int fieldCount = entityMap.getFieldCount();
                int dataIdx = 0;
                int fieldIndexOffset = 2;
                for (int i = 0; i < fieldCount; i++) {
                    DBEntityMap.DBFieldMap fMap = entityMap.getFieldMap(i);
                    if (fMap.secondaryTableName == null) {
                        if (fMap.classFieldName.equals(DBConceptMap.TCREATED_FIELD_NAME)) {
                            // Adjust the position of the parameter in the sql statement that skips
                            // over the time_created$ field.
                            fieldIndexOffset = 1;
                            dataIdx++;
                            continue;
                        }
                        //logger.log(Level.TRACE, "[%s] Field: %s  position in statement=%s data= %s", i, fMap.classFieldName, (fMap.tableFieldIndex + fieldIndexOffset), dataIdx);
                        DBHelper.setColumnData(stmt, fMap.tableFieldIndex + fieldIndexOffset, fMap.tableFieldMappingType, attrs[dataIdx++]);
                    }
                }
                DBHelper.setColumnData(stmt, entityMap.getLastUpdateParamPos(), DBEntityMap.FTYPE_LONG, id);
                if (isDebugLevelLog) {
                    logger.log(Level.DEBUG, "Try to update %s, %s, %s", className, attrs[0], attrs[1]);
                }
                stmt.addBatch();
                for (Iterator itr = secondaryDeleteSqlMap.keySet().iterator(); itr.hasNext();) {
                    String fieldName = (String) itr.next();
                    DBEntityMap.DBFieldMap fm = entityMap.getFieldMap(fieldName);
                    if (DBHelper._containedConceptArrayAddRemoveOnly && fm.isContainment == true) {
                        ClusterConfiguration config = _cluster.getClusterConfig();
                        List finalValues = new ArrayList<Long>();
                        if (config.isCacheAside()) {
	                        finalValues.addAll(deleted);
	                        finalValues.addAll(modified);
							finalValues.remove(id);
                        }
                        else {
                            Object value = secondaryAttributeMap.get(fieldName);
                            List queryValues = queryCurrentReferences(fm.secondaryTableName, "id$", "pid$", id);
                            secondaryDatabaseMap.put(fieldName, queryValues);
                            List childValues = (value == null) ? Collections.EMPTY_LIST:Arrays.asList((Object[])value);
                            finalValues.addAll(queryValues);
                            finalValues.removeAll(childValues);
                        }
                        logger.log(Level.TRACE, "Secondary deletes %s del:%s mod:%s merged:%s", 
                                fieldName, deleted, modified, finalValues);
                        logConcept((String) secondaryRemoveSqlMap.get(fieldName), null, finalValues);
                        PreparedStatement sstmt = (PreparedStatement) stmtRemMap.get(fieldName);
                        for (int i=0; i < finalValues.size(); i++) {
                            DBHelper.setColumnData(sstmt, 1, DBEntityMap.FTYPE_LONG, id);
                            DBHelper.setColumnData(sstmt, 2, DBEntityMap.FTYPE_LONG, finalValues.get(i));
                            sstmt.addBatch();
                        }
                    }
                    else {
	                    logConcept((String) secondaryDeleteSqlMap.get(fieldName), null, id);
	                    PreparedStatement sstmt = (PreparedStatement) stmtDelMap.get(fieldName);
	                    DBHelper.setColumnData(sstmt, 1, DBEntityMap.FTYPE_LONG, id);
	                    sstmt.addBatch();
                    }
                }
                for (Iterator itr = secondaryAttributeMap.keySet().iterator(); itr.hasNext();) {
                    String fieldName = (String) itr.next();
                    DBEntityMap.DBFieldMap fm = entityMap.getFieldMap(fieldName);
                    Object value = secondaryAttributeMap.get(fieldName);
                    if (value == null) {
                        continue;
                    }
                    if (DBHelper._containedConceptArrayAddRemoveOnly && fm.isContainment == true) {
                        ClusterConfiguration config = _cluster.getClusterConfig();
                        List finalValues = new ArrayList<Long>();
                        List childValues = Arrays.asList((Object[])value);
                        if (config.isCacheAside()) {
	                        List addedValues = (added.get(id) == null) ? Collections.EMPTY_LIST : added.get(id);
	                        finalValues.addAll(addedValues);
	                        finalValues.addAll(modified);
	                        finalValues.retainAll(childValues);
                        }
                        else {
                            List queryValues = secondaryDatabaseMap.get(fieldName);
                            finalValues.addAll(childValues);
                            finalValues.removeAll(queryValues);
                        }
                        logger.log(Level.TRACE, "Secondary inserts %s curr:%s merged:%s", 
                                fieldName, childValues, finalValues);
                        if (childValues.contains(null)) {
                            logger.log(Level.WARN, "Modified concept %s contains null value(s) for %s field." +
                                " They are not supported and will be ignored.", className, fieldName);
                        }
                        value = finalValues.toArray();
                    }
                    logConcept((String) secondaryInsertSqlMap.get(fieldName), null, value);
                    PreparedStatement sstmt = (PreparedStatement) stmtInsMap.get(fieldName);
                    DBHelper.populateSecondaryTable(entityMap.getFieldMap(fieldName), sstmt, value, id);
                }
            }
            int[] result = stmt.executeBatch();
            examineBatchResults("Batch modify-concepts", result);
            for (Iterator itr = stmtDelMap.values().iterator(); itr.hasNext();) {
                int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary modify/delete-concepts", secresult);
            }
            for (Iterator itr = stmtRemMap.values().iterator(); itr.hasNext();) {
            	int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary modify/remove-concepts", secresult);
            }
            for (Iterator itr = stmtInsMap.values().iterator(); itr.hasNext();) {
            	int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary modify/insert-concepts", secresult);
            }
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
            for (Iterator itr = stmtDelMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
            for (Iterator itr = stmtRemMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
            for (Iterator itr = stmtInsMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
        }
    }

    /*
     * TODO: Preliminary optimization using dirty bit
     * - If none of the primary table properties are modified, do not execute update to the primary table
     * - If secondary table has no update, do not execute the empty batch.  Just close it
     * - How to handle metric RRF? - dirty bits seem to be set
     * - Why metric rrf is marked dirty to be deleted?
     */
    public void modifyConceptsOptimized(String className, Map<Long, Concept> entries,
            Map<Long,List<Long>> added, List<Long> deleted, List<Long> modified, 
            Map<Long, ConceptStatus> statuses) throws Exception {
        boolean isDebugLevelLog = logger.isEnabledFor(Level.DEBUG);
        DBConceptMap entityMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (isDebugLevelLog) {
            logger.log(Level.DEBUG, "Modifying concepts for %s - Added:%s Deleted:%s Modified:%s Status:%s", className, added, deleted, modified, statuses);
        }
        if (entityMap == null) {
            logger.log(Level.WARN, "ModifyConcepts cannot find map for %s", className);
            return;
        }
        PreparedStatement stmt = null;
        PreparedStatement vstmt = null; // statement for system fields only such as 'cacheid'
        Map stmtInsMap = new HashMap();
        Map stmtDelMap = new HashMap();
        Map stmtRemMap = new HashMap();
        StringBuffer sbPrimaryMod = new StringBuffer();
        int primaryBatchCount = 0;
        int primaryVersionBatchCount = 0;
        try {
            stmt = getSqlConnection().prepareStatement(entityMap.getPrimaryUpdateSql());
            vstmt = getSqlConnection().prepareStatement(entityMap.getPrimaryVersionUpdateSql());
            Map secondaryDeleteSqlMap = entityMap.getSecondaryDeleteSqlMap();
            Map secondaryRemoveSqlMap = entityMap.getSecondaryRemoveSqlMap();
            Map secondaryInsertSqlMap = entityMap.getSecondaryInsertSqlMap();

            for (Iterator itr = secondaryDeleteSqlMap.keySet().iterator(); itr.hasNext();) {
                String fieldName = (String) itr.next();
                PreparedStatement secStmt = getSqlConnection().prepareStatement((String) secondaryInsertSqlMap.get(fieldName));
                stmtInsMap.put(fieldName, secStmt);
                secStmt = getSqlConnection().prepareStatement((String) secondaryDeleteSqlMap.get(fieldName));
                stmtDelMap.put(fieldName, secStmt);
                secStmt = getSqlConnection().prepareStatement((String) secondaryRemoveSqlMap.get(fieldName));
                stmtRemMap.put(fieldName, secStmt);
            }
            Iterator all_entries = entries.entrySet().iterator();
            while (all_entries.hasNext()) {
                int primaryArgumentCount = 0;
                Map.Entry<Long, Concept> entry = (Map.Entry<Long, Concept>) all_entries.next();
                Long id = entry.getKey();
                Concept cept = entry.getValue();
                ConceptStatus cs = statuses.get(id);
                int[] bits = cs.dirtyBitArray;
                byte dstat = cs.rtcStatus;
                if (bits == null) {
                    logger.log(Level.DEBUG, "Null dirty bits");
                }
                DBConceptSerializer serializer = new DBConceptSerializer(entityMap, getSqlConnection(), getQualifier(), _useTemporaryBlobs);
                cept.serialize((ConceptSerializer) serializer);
                if (serializer.hasErrors()) {
                    throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                }
                logConcept(entityMap.getPrimaryUpdateSql(), cept, null);
                Object[] attrs = serializer.getAttributes();
                Map secondaryAttributeMap = serializer.getSecondaryAttributeMap();
                DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, new Long(((VersionedObject) cept).getVersion()));
                DBHelper.setColumnData(vstmt, 1, DBEntityMap.FTYPE_LONG, new Long(((VersionedObject) cept).getVersion()));
                int fieldCount = entityMap.getFieldCount();
                int dataIdx = 0;
                int fieldIndexOffset = 2;
                sbPrimaryMod.setLength(0);
                for (int i = 0; i < fieldCount; i++) {
                    DBEntityMap.DBFieldMap fMap = entityMap.getFieldMap(i);
                    if (fMap.secondaryTableName == null) {
                        if (checkIsModified(fMap, dstat, bits) == true) {
                            primaryArgumentCount++;
                            if (isDebugLevelLog) {
                                sbPrimaryMod.append(fMap.classFieldName);
                                sbPrimaryMod.append(" ");
                            }
                        }
                        if (fMap.classFieldName.equals(DBConceptMap.TCREATED_FIELD_NAME)) {
                            // Adjust the position of the parameter in the sql statement that skips
                            // over the time_created$ field.
                            fieldIndexOffset = 1;
                            dataIdx++;
                            continue;
                        }
                        //logger.log(Level.TRACE, "[%s] Field: %s position in statement=%s data= %s", i, fMap.classFieldName, (fMap.tableFieldIndex + fieldIndexOffset), dataIdx);
                        DBHelper.setColumnData(stmt, fMap.tableFieldIndex + fieldIndexOffset, fMap.tableFieldMappingType, attrs[dataIdx++]);
                    }
                }
                DBHelper.setColumnData(stmt, entityMap.getLastUpdateParamPos(), DBEntityMap.FTYPE_LONG, id);
                if (isDebugLevelLog) {
                    logger.log(Level.DEBUG, "%s(%d) modified %s - primary count %d", className, id, sbPrimaryMod.toString(), primaryArgumentCount);
                    logger.log(Level.DEBUG, "Try to update %s, %s, %s, 0x%x, %s %s", className, attrs[0], attrs[1], dstat, translateRtcStatus(dstat), (bits == null ? "- dirty bits are null" : ""));
                }
                if (primaryArgumentCount > 0) {
                    stmt.addBatch();
                    primaryBatchCount++;
                } else {
                    DBHelper.setColumnData(vstmt, 2, DBEntityMap.FTYPE_TIMESTAMP, attrs[4]);
                    DBHelper.setColumnData(vstmt, 3, DBEntityMap.FTYPE_LONG, attrs[5]);
                    DBHelper.setColumnData(vstmt, 4, DBEntityMap.FTYPE_LONG, id);
                    vstmt.addBatch();
                    primaryVersionBatchCount++;
                }
                for (Iterator itr = secondaryDeleteSqlMap.keySet().iterator(); itr.hasNext();) {
                    String fieldName = (String) itr.next();
                    DBEntityMap.DBFieldMap fm = entityMap.getFieldMap(fieldName);
                    if (checkIsModified(fm, dstat, bits) == false) {
                        continue;
                    }
                    if (DBHelper._containedConceptArrayAddRemoveOnly && fm.isContainment == true) {
                        List finalValues = new ArrayList<Long>();
                        finalValues.addAll(deleted);
                        finalValues.addAll(modified);
                        finalValues.remove(id);
                        logger.log(Level.TRACE, "Secondary deletes %s del:%s mod:%s merged:%s", 
                                fieldName, deleted, modified, finalValues);
                        logConcept((String) secondaryRemoveSqlMap.get(fieldName), null, finalValues);
                        PreparedStatement sstmt = (PreparedStatement) stmtRemMap.get(fieldName);
                        for (int i=0; i < finalValues.size(); i++) {
                            DBHelper.setColumnData(sstmt, 1, DBEntityMap.FTYPE_LONG, id);
                            DBHelper.setColumnData(sstmt, 2, DBEntityMap.FTYPE_LONG, finalValues.get(i));
                            sstmt.addBatch();
                        }
                    }
                    else {
                        logConcept((String) secondaryDeleteSqlMap.get(fieldName), null, id);
                        PreparedStatement sstmt = (PreparedStatement) stmtDelMap.get(fieldName);
                        DBHelper.setColumnData(sstmt, 1, DBEntityMap.FTYPE_LONG, id);
                        sstmt.addBatch();
                    }
                    if (isDebugLevelLog) {
                        logger.log(Level.DEBUG, entityMap.getEntityClass().getSimpleName() + ":" + id + ":" + fieldName + " is modified(delete secondary table)");
                    }
                }
                for (Iterator itr = secondaryAttributeMap.keySet().iterator(); itr.hasNext();) {
                    String fieldName = (String) itr.next();
                    DBEntityMap.DBFieldMap fm = entityMap.getFieldMap(fieldName);
                    Object value = secondaryAttributeMap.get(fieldName);
                    if (value == null) {
                        continue;
                    }
                    if (checkIsModified(fm, dstat, bits) == false) {
                        continue;
                    }
                    if (DBHelper._containedConceptArrayAddRemoveOnly && fm.isContainment == true) {
                        List addedValues = (added.get(id) == null) ? Collections.EMPTY_LIST : added.get(id);
                        List childValues = Arrays.asList((Object[])value);
                        List finalValues = new ArrayList<Long>();
                        finalValues.addAll(addedValues);
                        finalValues.addAll(modified);
                        finalValues.retainAll(childValues);
                        logger.log(Level.TRACE, "Secondary inserts %s curr:%s merged:%s", 
                                fieldName, childValues, finalValues);
                        if (childValues.contains(null)) {
                            logger.log(Level.WARN, "Modified concept %s contains null value(s) for %s field." +
                                " They are not supported and will be ignored.", className, fieldName);
                        }
                        value = finalValues.toArray();
                    }
                    logConcept((String) secondaryInsertSqlMap.get(fieldName), null, value);
                    PreparedStatement sstmt = (PreparedStatement) stmtInsMap.get(fieldName);
                    DBHelper.populateSecondaryTable(entityMap.getFieldMap(fieldName), sstmt, value, id);
                    if (isDebugLevelLog) {
                        logger.log(Level.DEBUG, entityMap.getEntityClass().getSimpleName() + ":" + id + ":" + fieldName + " is modified(insert secondary table)");
                    }
                }
            }
            if (primaryBatchCount > 0) {
                if (isDebugLevelLog) {
                    logger.log(Level.DEBUG, "Primary table for %s is updated(%d)", className, primaryBatchCount);
                }
                int[] result = stmt.executeBatch();
                examineBatchResults("Batch modify-concepts optimized", result);
            }
            if (primaryVersionBatchCount > 0) {
                if (isDebugLevelLog) {
                    logger.log(Level.DEBUG, "Primary table for %s is updated(%d) for version", className, primaryVersionBatchCount);
                }
                int[] result = vstmt.executeBatch();
                examineBatchResults("Batch modify-concepts optimized", result);
            }
            //TODO: Add a count to delete and insert. If zero, don't do executeBatch
            for (Iterator itr = stmtDelMap.values().iterator(); itr.hasNext();) {
            	int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary modify/delete-concepts optimized", secresult);
            }
            for (Iterator itr = stmtRemMap.values().iterator(); itr.hasNext();) {
            	int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary modify/remove-concepts optimized", secresult);
            }
            for (Iterator itr = stmtInsMap.values().iterator(); itr.hasNext();) {
            	int[] secresult = ((PreparedStatement) itr.next()).executeBatch();
           		examineBatchResults("Secondary modify/insert-concepts optimized", secresult);
            }
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
            if (vstmt != null) {
                vstmt.clearBatch();
                vstmt.close();
            }
            for (Iterator itr = stmtDelMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
            for (Iterator itr = stmtRemMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
            for (Iterator itr = stmtInsMap.values().iterator(); itr.hasNext();) {
                PreparedStatement sstmt = (PreparedStatement) itr.next();
                if (sstmt != null) {
                    sstmt.clearBatch();
                    sstmt.close();
                }
            }
        }
        if (isDebugLevelLog) {
            logger.log(Level.DEBUG, "Modifying concepts completed for %s", className);
        }
    }
    
    private List<Long> queryCurrentReferences(String tableName, String fieldName, String filterField, long filterValue) {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            stmt = getSqlConnection().prepareStatement("select " + fieldName + " from " + tableName + 
                    ((filterField == null) ? "":" where " + filterField) + "=?");
            if (filterField != null) {
                stmt.setLong(1, filterValue);
            }
            rset = stmt.executeQuery();
            List<Long> results = new ArrayList<Long>();
            while (rset.next()) {
                results.add(rset.getLong(1));
            }
            return results;
        } catch (SQLException e) {
            logger.log(Level.WARN, "Querying current references failed %s", e, e.getMessage());
        } finally {
            try {
                rset.close();
            } catch (SQLException e) {
            }
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
        return Collections.EMPTY_LIST;
    }

    // Check each concept property including system one to see it's modified or not.
    protected boolean checkIsModified(DBEntityMap.DBFieldMap fm, byte rtcStatus, int[] dirtyBits) {
        boolean isDebugLevelLog = logger.isEnabledFor(Level.DEBUG);

        // Reverse ref status is maintained in the rtcStatus flag instead of the dirtyBits
        if ((rtcStatus & 8) > 0 && fm.classFieldName.equalsIgnoreCase(DBConceptMap.REVERSEREF_FIELD_NAME)) {
            if (isDebugLevelLog) {
                logger.log(Level.TRACE, "rrf is modified");
            }
            return true;
        }
        // parent id is maintained in rtcStatus but always get updated in the db no matter what.  Therefore return false
        if ((rtcStatus & 0x10) > 0 && fm.classFieldName.equalsIgnoreCase(DBConceptMap.PARENT_FIELD_NAME)) {
            if (isDebugLevelLog) {
                logger.log(Level.TRACE, "parentid is modified");
            }
            return false;
        }
        // Only system fields should have this combination
        if (fm.modifiedIndex == -1) {
            if (isDebugLevelLog) {
                logger.log(Level.TRACE, "SysField : %s", fm.classFieldName);
            }
            return false;
        }
        // If dirtyBits is null, it means the none of the user properties are modified.
        if (dirtyBits == null) {
            return false;
        }
        // Check to see a user property is modified
        if (fm.modifiedIndex != -1) {
            return BitSet.isBitSet(dirtyBits, fm.modifiedIndex);
        }
        // Safest thing is to return true to force an update
        if (isDebugLevelLog) {
            logger.log(Level.DEBUG, "checkIsModified encountered an unknown situation for %s", fm.classFieldName);
        }
        return true;
    }

    // Need to be in sync with the definitions in BaseHandle class
    private String translateRtcStatus(byte rtcStatus) {
        StringBuffer sb = new StringBuffer();
        if ((rtcStatus & 1 /*RTC_ASSERTED*/) > 0) {
            sb.append("asserted ");
        }
        if ((rtcStatus & 2 /*RTC_MODIFIED*/) > 0) {
            sb.append("modified ");
        }
        if ((rtcStatus & 4 /*RTC_DELETED*/) > 0) {
            sb.append("deleted ");
        }
        if ((rtcStatus & 8 /*RTC_REVERSE_REF*/) > 0) {
            sb.append("rrefmod ");
        }
        if ((rtcStatus & 0x10 /*RTC_CONTAINER_REF*/) > 0) {
            sb.append("containermod ");
        }
        if ((rtcStatus & 0x20 /*RTC_TOUCHED*/) > 0) {
            sb.append("touched ");
        }
        if ((rtcStatus & 0x40 /*RTC_STATE_CHANGED*/) > 0) {
            sb.append("statechanged ");
        }
        if ((rtcStatus & 0x40 /*RTC_SCHEDULED*/) > 0) {
            sb.append("scheduled ");
        }
        return sb.toString();
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
                    query.append(", ");
                }
                query.append(all_entries.next());
                if (++i == DELETE_IN_LIMIT) {
                    query.append(" ) ");
                    final String queryString = query.toString();
                    final PreparedStatement stmt = getSqlConnection().prepareStatement(queryString);
                    if (sqltext.isEnabledFor(Level.TRACE)) {
                        sqltext.log(Level.TRACE, queryString);
                    }
                    stmt.executeUpdate();
                    stmt.close();
                    first = true;
                    i = 0;
                    query = new StringBuffer("DELETE FROM OBJECTTABLE WHERE GLOBALID IN ( ");
                }
            }
            if (!first) {
                query.append(" ) ");
                final String queryString = query.toString();
                final PreparedStatement stmt = getSqlConnection().prepareStatement(queryString);
                if (sqltext.isEnabledFor(Level.TRACE)) {
                    sqltext.log(Level.TRACE, queryString);
                }
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
                    query.append(", ");
                }
                query.append(all_entries.next());
            }
            query.append(" ) ");
            final String queryString = query.toString();
            final PreparedStatement stmt = getSqlConnection().prepareStatement(queryString);
            if (sqltext.isEnabledFor(Level.TRACE)) {
                sqltext.log(Level.TRACE, queryString);
            }
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public void removeObjectTableInBatch(Set<Long> entries) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = getSqlConnection().prepareStatement("DELETE FROM OBJECTTABLE WHERE GLOBALID = ?");
            sqlvars.log(Level.TRACE, "Delete in batch OBJECTTABLE - %s ID's:%s", "DELETE FROM OBJECTTABLE WHERE GLOBALID = ?", entries);
            for (Iterator<Long> itr = entries.iterator(); itr.hasNext();) {
                Long id = itr.next();
                DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, id);
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
     * Note: Use same logic for primary table and then use DBXXXMap for secondary table delete
     */
    public List deleteConcepts(String className, Set<Long> entries) throws Exception {
        DBConceptMap conceptMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (conceptMap == null) {
            logger.log(Level.WARN, "DeleteConcepts cannot find map for %s", className);
            return Collections.EMPTY_LIST;
        }
        // There should be at least one secondary table which is for the reverseRef table
        String[] secTableNames = conceptMap.getSecondaryTableNames();
        StringBuffer[] delSB = new StringBuffer[secTableNames.length + 1];
        int processedCount = 0;
        List<Long> deletedConcepts = new ArrayList<Long>();
        PreparedStatement stmt = null;
        try {
            for (Iterator<Long> itr = entries.iterator(); itr.hasNext();) {
                if (processedCount == 0) {
                    StringBuffer query = new StringBuffer("DELETE FROM " + conceptMap.getTableName() + "  ");
                    query.append(" WHERE ID$ IN (");
                    delSB[0] = query;
                    for (int i = 0; i < (delSB.length - 1); i++) {
                        query = new StringBuffer("DELETE FROM " + secTableNames[i] + "  ");
                        query.append(" WHERE PID$ IN (");
                        delSB[i + 1] = query;
                    }
                } else {
                    for (int i = 0; i < delSB.length; i++) {
                        delSB[i].append(", ");
                    }
                }
                Long id = itr.next();
                for (int i = 0; i < delSB.length; i++) {
                    delSB[i].append(id);
                }
                deletedConcepts.add(id);
                processedCount++;
                if (processedCount == DELETE_IN_LIMIT) {
                    for (int i = 0; i < delSB.length; i++) {
                        StringBuffer sb = delSB[i];
                        sb.append(")");
                        final String queryString = sb.toString();
                        sqltext.log(Level.TRACE, queryString);
                        stmt = getSqlConnection().prepareStatement(queryString);
                        stmt.executeUpdate();
                        stmt.close();
                    }
                    processedCount = 0;
                }
            }
            if (processedCount > 0) {
                for (int i = 0; i < delSB.length; i++) {
                    StringBuffer sb = delSB[i];
                    sb.append(")");
                    final String queryString = sb.toString();
                    sqltext.log(Level.TRACE, queryString);
                    stmt = getSqlConnection().prepareStatement(queryString);
                    stmt.executeUpdate();
                    stmt.close();
                }
            }
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return deletedConcepts;
    }

    public List deleteConceptsInBatch(String className, Set<Long> entries) throws Exception {
        DBConceptMap conceptMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (conceptMap == null) {
            logger.log(Level.WARN, "DeleteConcepts cannot find map for %s", className);
            return Collections.EMPTY_LIST;
        }
        // There should be at least one secondary table which is for the reverseRef table
        Map secondaryMap = conceptMap.getSecondaryDeleteSqlMap();
        List<Long> deletedConcepts = new ArrayList<Long>();
        PreparedStatement[] stmts = new PreparedStatement[secondaryMap.size() + 1];
        try {
            int idx = 0;
            stmts[idx++] = getSqlConnection().prepareStatement(conceptMap.getPrimaryDeleteSql());
            sqlvars.log(Level.TRACE, "Delete in batch %s %s ID's:%s", className, conceptMap.getPrimaryDeleteSql(), entries);
            for (Iterator itr = secondaryMap.values().iterator(); itr.hasNext(); ) {
                String secDeleteSql = (String)itr.next();
                stmts[idx++] = getSqlConnection().prepareStatement(secDeleteSql);
                sqltext.log(Level.TRACE, "Delete in batch %s %s", className, secDeleteSql);
            }
            for (Iterator<Long> itr = entries.iterator(); itr.hasNext();) {
                Long id = itr.next();
                for (int i = 0; i < stmts.length; i++) {
                    DBHelper.setColumnData(stmts[i], 1, DBEntityMap.FTYPE_LONG, id);
                    stmts[i].addBatch();
                }
                deletedConcepts.add(id);
            }
            for (int i = 0; i < stmts.length; i++) {
                stmts[i].executeBatch();
            }
        }
        finally {
            for (int i = 0; i < stmts.length; i++) {
                if (stmts[i] != null) {
                    stmts[i].clearBatch();
                    stmts[i].close();
                }
            }
        }
        return deletedConcepts;
    }
    
    /**
     * @param tableName
     * @param entries
     * @throws Exception
     * Note: Use same logic for primary table and then use DBXXXMap for secondary table delete
     */
    public void deleteEvents(String className, Set<Long> entries) throws Exception {
        DBEventMap eventMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (eventMap == null) {
            logger.log(Level.WARN, "DeleteEvents cannot find map for %s", className);
            return;
        }
        int processedCount = 0;
        PreparedStatement stmt = null;
        StringBuffer query = null;
        try {
            for (Iterator itr = entries.iterator(); itr.hasNext();) {
                if (processedCount == 0) {
                    query = new StringBuffer("DELETE FROM " + eventMap.getTableName() + "  ");
                    query.append(" WHERE ID$ IN (");
                } else {
                    query.append(", ");
                }
                String id = itr.next().toString();
                query.append(id);
                processedCount++;
                if (processedCount == DELETE_IN_LIMIT) {
                    query.append(")");
                    stmt = getSqlConnection().prepareStatement(query.toString());
                    stmt.executeUpdate();
                    stmt.close();
                    processedCount = 0;
                }
            }
            if (processedCount > 0) {
                query.append(")");
                stmt = getSqlConnection().prepareStatement(query.toString());
                stmt.executeUpdate();
                stmt.close();
            }
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void deleteEventsInBatch(String className, Set<Long> entries) throws Exception {
        DBEventMap eventMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        if (eventMap == null) {
            logger.log(Level.DEBUG, "DeleteEvents cannot find map for %s", className);
            return;
        }
        PreparedStatement stmt = null;
        try {
            stmt = getSqlConnection().prepareStatement(eventMap.getPrimaryDeleteSql());
            sqltext.log(Level.TRACE, "Delete in batch %s %s", className, eventMap.getPrimaryDeleteSql());
            for (Iterator itr = entries.iterator(); itr.hasNext();) {
                String id = itr.next().toString();
                DBHelper.setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, new Long(id));
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
    
    public com.tibco.cep.kernel.model.entity.Event getEventById(String tableName, long id, long cacheId) throws Exception {
        DBEventMap entityMap = (DBEventMap) DBManager.getInstance().getEntityPropsMapByTableName().get(tableName);
        if (entityMap == null) {
            logger.log(Level.WARN, "%s has no event map", tableName);
            return null;
        }

        com.tibco.cep.kernel.model.entity.Event event = null;
        PreparedStatement stmt = queryStatementById(entityMap.getPrimarySelectSql(), DBEventMap.ID_FIELD_NAME, null, false);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            event = DBHelper.createEvent(this._rsp, entityMap, getCurrentConnection(), rs);
        }
        rs.close();
        stmt.close();
        return event;
    }

    // These functions are accessed by ClusterSequenceFunctions
    public long nextSequence(String sequenceName) throws Exception {
        boolean isTraceLevelLog = sqltext.isEnabledFor(Level.TRACE);

        Connection connection = this.getSqlConnection();
        PreparedStatement stmt = null;
        long nextVal = -1;
        // Read the current start value
        String readQuery = RDBMSType.sSqlType.getSequenceNextQuery(sequenceName.toUpperCase(), false);
        if (readQuery != null) {
            if (isTraceLevelLog) {
                sqltext.log(Level.TRACE, "Read sequence " + readQuery);
            }
            stmt = connection.prepareStatement(readQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nextVal = rs.getLong(1);
            }
            rs.close();
            stmt.close();
        }
        // Reserve the next block
        String updateQuery = RDBMSType.sSqlType.getSequenceNextQuery(sequenceName.toUpperCase(), true);
        if (updateQuery != null) {
            if (isTraceLevelLog) {
                sqltext.log(Level.TRACE, "Next sequence " + updateQuery);
            }
            stmt = connection.prepareStatement(updateQuery);
            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        }
        return nextVal;
    }

    public void removeSequence(String sequenceName) throws SQLException {
        Connection connection = this.getSqlConnection();
        String deleteQuery = RDBMSType.sSqlType.getSequenceDropQuery(sequenceName.toUpperCase());
        sqltext.log(Level.TRACE, "Remove sequence " + deleteQuery);
        PreparedStatement stmt = connection.prepareStatement(deleteQuery);
        stmt.executeUpdate();
        connection.commit();
        stmt.close();
    }

    public void createSequence(String sequenceName, long minValue, long maxValue, long start, int increment) throws SQLException {
        Connection connection = this.getSqlConnection();
        boolean sequenceExists = false;
        String checkQuery = RDBMSType.sSqlType.getSequenceCheckQuery(sequenceName.toUpperCase(), increment);
        sqltext.log(Level.TRACE, "Check sequence " + checkQuery);
        PreparedStatement stmt = connection.prepareStatement(checkQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            sequenceExists = true;
        }
        rs.close();
        stmt.close();
        
        if (!sequenceExists) {
            String createQuery = RDBMSType.sSqlType.getSequenceCreateQuery(sequenceName.toUpperCase(), minValue, maxValue, start, increment);
            sqltext.log(Level.TRACE, "Create sequence " + createQuery);
            stmt = connection.prepareStatement(createQuery);
            stmt.execute();
            connection.commit();
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

        PreparedStatement stmt = connection.prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(query));
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
        String query = "SELECT workKey, workQueue, scheduledTime, workStatus, work from WorkItems where workQueue = ? and workStatus = ? and scheduledTime <= ? ORDER BY scheduledTime ASC";
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG, "getWorkItems(workQueue,time,status) - SELECT * from WorkItems where workQueue = %s and workStatus = %s and scheduledTime <= %s ORDER BY scheduledTime ASC", 
                    workQueue, status, time);
        }

        PreparedStatement stmt = connection.prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(query));
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
        byte[] buf = DBHelper.getBlob(rs, 5);
        item.setBuf(buf);
        return item;
    }

    public WorkTuple getWorkItem(String key) throws Exception {
        Connection connection = this.getSqlConnection();
        String query = "SELECT workKey, workQueue, scheduledTime, workStatus, work from WorkItems where workKey = ? ";
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE,"getWorkItem(key) - %s(%s)", query.toString(), key);
        }
        PreparedStatement stmt = connection.prepareStatement(RDBMSType.sSqlType.optimizeSelectStatement(query));
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
        PreparedStatement stmt = connection.prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(query));
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
        final PreparedStatement stmt = getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(queryString));
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
        final PreparedStatement stmt = getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeDeleteStatement(queryString));
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
        PreparedStatement stmt = null; 
        try {
            String query = "INSERT INTO WorkItems(workKey, workQueue, scheduledTime, workStatus, work) VALUES (?,?,?,?,?)";
            stmt = this.getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeInsertStatement(query));
            stmt.setString(1, tuple.getWorkId());
            stmt.setString(2, tuple.getWorkQueue());
            stmt.setLong(3, tuple.getScheduledTime());
            stmt.setInt(4, tuple.getWorkStatus());
            byte[] buf = tuple.getBuf();
            DBHelper.setBlob(stmt, 5, buf);
            if (sqltext.isEnabledFor(Level.TRACE)) {
                sqltext.log(Level.TRACE, "INSERT INTO WorkItems(workKey, workQueue, scheduledTime, workStatus, work) "
                        + " VALUES (%s, %s, %s, %s, <blob>[%s])",
                        tuple.getWorkId(), tuple.getWorkQueue(), tuple.getScheduledTime(), tuple.getWorkStatus(), tuple.getWork().getClass().getName());
            }
            stmt.executeUpdate();
        } finally {
        	if (stmt != null) stmt.close();
        }
    }

    public void updateWorkItem(WorkTuple tuple) throws Exception {
        PreparedStatement stmt = null; 
        try {
            String query = "UPDATE WorkItems SET scheduledTime = ?, workStatus = ?, work = ? where workKey = ? ";
            stmt = getSqlConnection().prepareStatement(RDBMSType.sSqlType.optimizeUpdateStatement(query));
            stmt.setString(4, tuple.getWorkId());
            stmt.setLong(1, tuple.getScheduledTime());
            stmt.setInt(2, tuple.getWorkStatus());
            byte[] buf = tuple.getBuf();
            DBHelper.setBlob(stmt, 3, buf);
            if (sqltext.isEnabledFor(Level.TRACE)) {
                sqltext.log(Level.TRACE, "UPDATE WorkItems SET scheduledTime = %s, workStatus = %s, work = %s where workKey = %s ",
                        tuple.getScheduledTime(), tuple.getWorkStatus(), tuple.getWork().getClass().getName(), tuple.getWorkId());
            }
            stmt.executeUpdate();
        } finally {
            stmt.close();
        }
    }

    public void truncate_entityTable(String tableName) throws SQLException {
        Connection connection = this.getSqlConnection();
        PreparedStatement stmt = connection.prepareStatement("TRUNCATE TABLE " + tableName);
        stmt.executeUpdate();
        stmt.close();
    }

    public void truncate_systemTables() throws SQLException {
        Connection connection = this.getSqlConnection();
        PreparedStatement stmt = null;
        
        stmt = connection.prepareStatement("TRUNCATE TABLE ObjectTable ");
        stmt.executeUpdate();
        stmt.close();

        stmt = connection.prepareStatement("TRUNCATE TABLE WorkItems ");
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
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = null;
        JdbcRSCache rsCache = null;
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        rs = stmt.executeQuery();
        int numColumns = rs.getMetaData().getColumnCount();
        rsCache = new JdbcRSCache(numColumns);
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
        Connection cnx = (Connection) this._currentConnection.get();
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

    private void commit(Connection cnx, boolean releaseConnection) throws SQLException {
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
        //VWC rework return this._pool.getQualifier();
        return "";
    }

    /**
     * @return
     * @throws SQLException
     */
    private Connection getSqlConnection() throws SQLException {
        if (this._currentConnection.get() == null) {
            this._currentConnection.set(this._pool.getConnection());
        }
        Connection jdbcconn = (Connection) this._currentConnection.get();
        return jdbcconn;
    }

    public Connection getCurrentConnection() {
        return (Connection) this._currentConnection.get();
    }

    /**
     * @throws SQLException
     */
    public void releaseConnection() {
        Connection cnx = (Connection) this._currentConnection.get();
        releaseConnection(cnx, false);
    }

    public void releaseConnection(Connection cnx, boolean forceClose) {
        if (cnx != null) {
            try {
                if (DBManager._useOracleStrategy) {
                    try {
                        OracleLOBManager.free();
                    } catch (Exception e) {
                        if (logger.isEnabledFor(Level.DEBUG)) {
                            logger.log(Level.DEBUG, "DBAdapter release oracle pool connection (free) failed: %s", e.getMessage(), e);
                        } else {
                            logger.log(Level.WARN, "DBAdapter release oracle pool connection (free) failed: %s", e.getMessage());
                        }
                    }
                    // Rollback any uncommitted txn, for safety
                    try {
                        if (_rollbackAfterRelease) {
                            cnx.rollback();
                        }
                    } catch (Exception e) {
                        if (logger.isEnabledFor(Level.DEBUG)) {
                            logger.log(Level.DEBUG, "DBAdapter release oracle pool connection (rollback) failed: %s", e.getMessage(), e);
                        } else {
                            logger.log(Level.WARN, "DBAdapter release oracle pool connection (rollback) failed: %s", e.getMessage());
                        }
                    }
                    if (forceClose) {
                        ((oracle.jdbc.OracleConnection)cnx).close(oracle.jdbc.OracleConnection.INVALID_CONNECTION);
                    } else {
                        ((oracle.jdbc.OracleConnection)cnx).close();
                    }
                } else {
                    // Rollback any uncommitted txn, for safety
                    try {
                        if (_rollbackAfterRelease) {
                            cnx.rollback();
                        }
                    } catch (Exception e) {
                        if (logger.isEnabledFor(Level.DEBUG)) {
                            logger.log(Level.DEBUG, "DBAdapter release jdbc connection (rollback) failed: %s", e.getMessage(), e);
                        } else {
                            logger.log(Level.WARN, "DBAdapter release jdbc connection (rollback) failed: %s", e.getMessage());
                        }
                    }
                    if (forceClose) {
                        ((DBConnectionPool) this._pool).closeConnection(cnx);
                    } else {
                        ((DBConnectionPool) this._pool).free(cnx);
                    }
                }
                logger.log(Level.DEBUG, "Closed resources for connection: %s close: %s", cnx.hashCode(), forceClose);
            } catch (Exception ex) {
                logger.log(Level.WARN, "DBAdapter release connection (close) failed: %s", ex.getMessage());
            } finally {
            	this._currentConnection.set(null);
            }
        }
    }

    public void refreshConnections() {
        try {
            if (DBManager._useOracleStrategy) {
                try {
                    ((OracleConnectionPool) this._pool).refreshConnections(_recreateOnRecovery);
                } catch (Exception e) {
                    logger.log(Level.WARN, e, "Failed refreshing Oracle connections");
                }
            } else {
                try {
                    ((DBConnectionPool) this._pool).recycle();
                } catch (Exception e) {
                    logger.log(Level.WARN, e, "Failed refreshing Jdbc connections");
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARN, "DBAdapter refreshConnections failed: %s", ex.getMessage());
        } finally {
        }
    }
    
    public void cleanUpIterators(Iterator iterator) {
    	if (iterator instanceof ConceptsWithVersionIterator) {
    		((ConceptsWithVersionIterator) iterator).close();
    	} else if (iterator instanceof EventsIterator) {
    		((EventsIterator) iterator).close();
    	}
    	releaseConnection();
    }

    class KeyIterator implements Iterator {
        ResultSet resultSet;

        KeyIterator(ResultSet resultSet) {
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
                    return new Long(resultSet.getLong(1));
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
        ResultSet resultSet;

        ConceptsIterator(ResultSet resultSet) {
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
                    return new ConceptsWithVersionIterator(resultSet).next();
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

    class EventsIterator implements Iterator {
        DBEventMap _entityMap;
        ResultSet _resultSet;
        DBAdapter _adapter;

        EventsIterator(ResultSet resultSet, DBAdapter adapter, DBEventMap entityMap) {
            _resultSet = resultSet;
            _entityMap = entityMap;
            _adapter = adapter;
        }

        public boolean hasNext() {
            try {
                boolean hasNext = _resultSet.next();
                if (!hasNext) {
                    close();
                    _adapter.releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (_resultSet != null) {
                    return DBHelper.createEvent(_adapter._rsp, _entityMap, _adapter.getCurrentConnection(), _resultSet);
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
                Statement stmt = _resultSet.getStatement();
                _resultSet.close();
                stmt.close();
                _resultSet = null;
                logger.log(Level.DEBUG, "Completed & Closed resources for event: %s", this._entityMap.tableName);
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class KeyPairIterator implements Iterator {
        ResultSet resultSet;

        KeyPairIterator(ResultSet resultSet) {
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
                logger.log(Level.WARN, "Event iterator failed: %s", e, e.getMessage());
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
                logger.log(Level.WARN, "Event iterator close failed: %s", e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    class ObjectTableIterator implements Iterator {
        ResultSet resultSet;

        ObjectTableIterator(ResultSet resultSet) {
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

        // SELECT ID, EXTID, CLASSNAME FROM OBJECTTABLE  WHERE SITEID=? AND CLASSNAME=? AND ISDELETED=0
        public Object next() {
            try {
                if (resultSet != null) {
                    long id = resultSet.getLong(1);
                    String extId = resultSet.getString(2);
                    String className = resultSet.getString(3);
                    return new ObjectTupleImpl(id, extId, _cluster.getMetadataCache().getTypeId(className));
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

    class ClassIterator extends KeyIterator {
        ClassIterator(ResultSet resultSet) {
            super(resultSet);
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    return resultSet.getString(1);
                } else {
                    throw new Exception("ClassIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public class JdbcRSCache implements BackingStore.ResultSetCache {
        ArrayList m_Rows = new ArrayList();
        int numColumns;

        public JdbcRSCache(int numColumns) {
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
            System.out.println("Remove Not Implemented Yest");
        }

        public void remove(int i, int i1) throws IOException {
            System.out.println("Remove Not Implemented Yest");
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
        String tblNm = generatedJdbcTableName(className);
        if (tblNm == null) {
            return null;
        }
        PreparedStatement stmt = this.getBaseTableStatementWithId(tblNm);
        stmt.setLong(1, id);
        //stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        String extId = null;
        while (rs.next()) {
            extId = rs.getString(1);
        }
        rs.close();
        stmt.close();
        tuple = new ObjectTupleImpl(id, extId, _cluster.getMetadataCache().getTypeId(className));
        return tuple;
    }

    public ObjectTupleImpl getTupleByType(String extId, Class clz) throws Exception {
        if (clz == null) {
            return null;
        }
        String tblNm = generatedJdbcTableName(clz.getName());
        if (tblNm == null) {
            return null;
        }
        PreparedStatement stmt = this.getBaseTableStatementWithExtId(tblNm);
        stmt.setString(1, extId);
        //stmt.setQueryTimeout(queryTimeout);
        ResultSet rs = stmt.executeQuery();
        ObjectTupleImpl tuple = null;
        long id = -1;
        while (rs.next()) {
            id = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        String className = clz.getName();
        tuple = (id == -1) ? null : new ObjectTupleImpl(id, extId, _cluster.getMetadataCache().getTypeId(className));

        return tuple;
    }

    public long getMaxEntityAcrossTypes(long siteId) throws Exception {
        long maxId = 0;
        Class[] clzs = _cluster.getMetadataCache().getRegisteredTypes();
        try {
            for (int i = 0; i < clzs.length; i++) {

                Class clz = clzs[i];
                if (clz == null) {
                    continue;
                }
                String tabNm = generatedJdbcTableName(clz.getName());
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
        
        String query = RDBMSType.sSqlType.optimizeSelectStatement("select max(t.id$) from " + tabNm + " t "
        		+ "where t.id$ >= ? and t.id$ <= ?");
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

    private PreparedStatement getBaseTableStatementWithExtId(String tblNm) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT ID$ FROM ").append(tblNm).append(" T WHERE EXTID$=?");
        return (PreparedStatement) getSqlConnection().prepareStatement(
            RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement getBaseTableStatementWithId(String tblNm) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT EXTID$ FROM ").append(tblNm).append(" T WHERE ID$=?");
        return (PreparedStatement) getSqlConnection().prepareStatement(
            RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement getLoadStatementUsingBaseTables(String clzName) throws Exception {
        String tblNm = generatedJdbcTableName(clzName);
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT ID$ FROM ").append(tblNm);
        return (PreparedStatement) getSqlConnection().prepareStatement(
            RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private PreparedStatement getRecoveryStatementUsingBaseTables(String clzName) throws Exception {
        String tblNm = generatedJdbcTableName(clzName);
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT ID$, EXTID$ ").append(" FROM ").append(tblNm);
        return (PreparedStatement) getSqlConnection().prepareStatement(
            RDBMSType.sSqlType.optimizeSelectStatement(queryBuf.toString()));
    }

    private class ObjectTableIteratorUsingBaseTables implements Iterator {
        ResultSet resultSet;
        int typeId;
        String clzName;

        ObjectTableIteratorUsingBaseTables(ResultSet resultSet, int typeId) {
            this.resultSet = resultSet;
            this.typeId = typeId;
            this.clzName = _cluster.getMetadataCache().getClass(typeId).getName();
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
                logger.log(Level.WARN, "ObjectTable iterator failed: %s", e, e.getMessage());
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
                logger.log(Level.WARN, "ObjectTable iterator close failed: %s", e, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Get max(ID$) for the given type
     */
    public long getMaxId(int typeId) throws Exception {
        Class entityClz = _cluster.getMetadataCache().getClass(typeId);
        String tableName = DBManager.getInstance().getTableName(entityClz.getName());
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
        Class entityClz = _cluster.getMetadataCache().getClass(typeId);
        String tableName = DBManager.getInstance().getTableName(entityClz.getName());
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
    public PreparedStatement getMaxIdStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT MAX(id$) FROM ").append(tableName);
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, queryBuf.toString());
        }

        return getSqlConnection().prepareStatement(queryBuf.toString());
    }

    /*
     * Get preparedstatement to get min(id) of a type
     */
    public PreparedStatement getMinIdStatement(String tableName) throws SQLException {
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("SELECT MIN(id$) FROM ").append(tableName);
        if (sqltext.isEnabledFor(Level.TRACE)) {
            logger.log(Level.TRACE, queryBuf.toString());
        }
        return getSqlConnection().prepareStatement(queryBuf.toString());
    }

    public ConceptsWithVersionIterator loadConcepts(int typeId, long startId, long endId) throws Exception {
        List<ResultMap> resultList = new ArrayList<ResultMap>();
        String className = _cluster.getMetadataCache().getClass(typeId).getName();
        Class<?> entityClz = _cluster.getMetadataCache().getClass(typeId);
        DBConceptMap conceptMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        String tableName = conceptMap.getTableName();
        if (tableName != null) {
            logger.log(Level.INFO, "Loading " + entityClz.getName() + " entries from " + tableName);
        }
        else {
            return null;
        }
        PreparedStatement stmt = this.getLoadConceptsStatement(conceptMap.getPrimarySelectSql(), DBConceptMap.ID_FIELD_NAME, null, startId, endId);
        ResultSet rs = stmt.executeQuery();
        ResultMap rm = new ResultMap(null, null, stmt, rs);
        resultList.add(rm);
        Map<?,?> secondaryTableSelect = conceptMap.getSecondarySelectSqlMap();
        if (secondaryTableSelect != null && secondaryTableSelect.size() > 0) {
            for (Iterator<?> iter = secondaryTableSelect.keySet().iterator(); iter.hasNext();) {
                String fieldName = (String) iter.next();
                String selectSql = (String) secondaryTableSelect.get(fieldName);
                DBEntityMap.DBFieldMap fMap = conceptMap.getFieldMap(fieldName);
                String additionalOrderBy[] = new String[2];
                if (fMap.isArray) {
                    additionalOrderBy[0] = "valPid$";
                    if (fMap.hasHistory) {
                        additionalOrderBy[1] = "timeidx";
                    }
                } else if (fMap.hasHistory) {
                    additionalOrderBy[0] = "timeidx";
                }
                stmt = this.getLoadConceptsStatement(selectSql, "pid$", additionalOrderBy, startId, endId);
                rs = stmt.executeQuery();
                rm = new ResultMap(fieldName, fMap, stmt, rs);
                resultList.add(rm);
            }
        }
        return new ConceptsWithVersionIterator(resultList, this, conceptMap);
    }

    PreparedStatement getLoadConceptsStatement(String baseSelectSql, String idField, String[] orderBy, long startId, long endId) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(baseSelectSql);
        queryBuf.append(" WHERE T.").append(idField).append(" >= ? AND T.").append(idField).append(" <= ? ");
        queryBuf.append(" ORDER BY T.").append(idField);
        if (orderBy != null) {
            for (int i = 0; i < orderBy.length; i++) {
                if (orderBy[i] != null) {
                    queryBuf.append(", T.");
                    queryBuf.append(orderBy[i]);
                }
            }
        }
        final String queryString = queryBuf.toString();
        if (sqltext.isEnabledFor(Level.TRACE)) {
            sqltext.log(Level.TRACE, queryString);
        }
        PreparedStatement stmt = getSqlConnection().prepareStatement(queryString);
        stmt.setLong(1, startId);
        stmt.setLong(2, endId);
        return stmt;
    }

    public EventsIterator loadEvents(int typeId, long startId, long endId) throws Exception {
        String className = _cluster.getMetadataCache().getClass(typeId).getName();
        Class<?> entityClz = _cluster.getMetadataCache().getClass(typeId);
        DBEventMap eventMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        String tableName = eventMap.getTableName();
        if (tableName != null) {
            logger.log(Level.INFO, "Loading " + entityClz.getName() + " entries from " + tableName);
        } else {
            return null;
        }
        PreparedStatement stmt = this.getLoadConceptsStatement(eventMap.getPrimarySelectSql(), DBEventMap.ID_FIELD_NAME, null, startId, endId);
        ResultSet rs = stmt.executeQuery();
        return new EventsIterator(rs, this, eventMap);
    }

    public ConceptsWithVersionIterator loadConcepts(int typeId, Long[] entityIds) throws Exception {
        List resultList = new ArrayList();
        String className = _cluster.getMetadataCache().getClass(typeId).getName();
        Class entityClz = _cluster.getMetadataCache().getClass(typeId);
        DBConceptMap conceptMap = (DBConceptMap) DBManager.getInstance().getEntityPropsMap().get(className);
        String tableName = conceptMap.getTableName();
        if (tableName != null) {
            logger.log(Level.TRACE,"Loading " + entityClz.getName() + " entries from " + tableName);
        }
        else {
            return null;
        }

        //logger.log(Level.INFO,"Loading " + entityClz.getName() + " entries from " + tableName);
        PreparedStatement stmt = getLoadConceptUsingIdsStatement(conceptMap.getPrimarySelectSql(), DBConceptMap.ID_FIELD_NAME, null, entityIds.length);
        for(int i =0; i < entityIds.length; i++) {
            stmt.setLong(i+1, entityIds[i]);
        }
        ResultSet rs = stmt.executeQuery();
        ResultMap rm = new ResultMap(null, null, stmt, rs);
        resultList.add(rm);

        Map secondaryTableSelect = conceptMap.getSecondarySelectSqlMap();
        if (secondaryTableSelect != null && secondaryTableSelect.size() > 0) {
            for (Iterator iter = secondaryTableSelect.keySet().iterator(); iter.hasNext();) {
                String fieldName = (String) iter.next();
                String selectSql = (String) secondaryTableSelect.get(fieldName);
                DBEntityMap.DBFieldMap fMap = conceptMap.getFieldMap(fieldName);
                String additionalOrderBy[] = new String[2];
                if (fMap.isArray) {
                    additionalOrderBy[0] = "valPid$";
                    if (fMap.hasHistory) {
                        additionalOrderBy[1] = "timeidx";
                    }
                } else if (fMap.hasHistory) {
                    additionalOrderBy[0] = "timeidx";
                }
                stmt = getLoadConceptUsingIdsStatement(selectSql, "pid$", additionalOrderBy, entityIds.length);
                for(int i =0; i < entityIds.length; i++) {
                    stmt.setLong(i+1, entityIds[i]);
                }
                rs = stmt.executeQuery();
                rm = new ResultMap(fieldName, fMap, stmt, rs);
                resultList.add(rm);
            }
        }
        return new ConceptsWithVersionIterator(resultList, this, conceptMap);
    }

    PreparedStatement getLoadConceptUsingIdsStatement(String baseSelectSql, String idField, String[] orderBy, int length) throws SQLException {
        StringBuffer queryBuf = new StringBuffer(baseSelectSql);
        queryBuf.append(" WHERE " + idField + " IN (");
        for(int i = 0;i < length;) {
            queryBuf.append("?");
            if (++i < length) {
                queryBuf.append(",");
            }
        }
        queryBuf.append(")");
        queryBuf.append(" ORDER BY T.").append(idField);
        if (orderBy != null) {
            for (int i = 0; i < orderBy.length; i++) {
                if (orderBy[i] != null) {
                    queryBuf.append(", T.");
                    queryBuf.append(orderBy[i]);
                }
            }
        }
        final String queryString = queryBuf.toString();
        if (logger.isEnabledFor(Level.TRACE)) {
            logger.log(Level.TRACE, queryString);
        }
        return getSqlConnection().prepareStatement(queryString);
    }

    public EventsIterator loadEvents(int typeId, Long[] entityIds) throws Exception {
        String className = _cluster.getMetadataCache().getClass(typeId).getName();
        Class entityClz = _cluster.getMetadataCache().getClass(typeId);
        DBEventMap eventMap = (DBEventMap) DBManager.getInstance().getEntityPropsMap().get(className);
        String tableName = eventMap.getTableName();
        if (tableName != null) {
            logger.log(Level.TRACE,"Loading " + entityClz.getName() + " entries from " + tableName);
        } else {
            return null;
        }

        PreparedStatement stmt = getLoadConceptUsingIdsStatement(eventMap.getPrimarySelectSql(), DBEventMap.ID_FIELD_NAME, null, entityIds.length);
        for(int i =0; i < entityIds.length; i++) {
            stmt.setLong(i+1, entityIds[i]);
        }
        ResultSet rs = stmt.executeQuery();
        return new EventsIterator(rs, this, eventMap);
    }
    
    private Map<Long,List<Long>> appendList(Map<Long,List<Long>> base, long key, long value) {
        List<Long> values = base.get(key);
        if (values == null) {
            values = new ArrayList<Long>();
	        base.put(key, values);
        }
        values.add(value);
        return base;
    }

    public void insertObjectTuples(Map<Integer, Map<String, ObjectBean>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<String, ObjectBean>> entry = (Map.Entry) all_entries.next();
            // This is the hash code of LoopTuple or MergeTuple
            int typeId = entry.getKey();
            Map<String, ObjectBean> tupleMap = entry.getValue();
            Iterator tupleKeys = tupleMap.keySet().iterator();
            while(tupleKeys.hasNext()) {
                String tupleKey = (String) tupleKeys.next();
                ObjectBean ob = tupleMap.get(tupleKey);
                logger.log(Level.DEBUG, "Object Tuple type name : %s", ob.getType().getName());
                if (ob.getType().getName().equals("com.tibco.cep.runtime.model.process.LoopTuple")) {
                    logger.log(Level.DEBUG, "Insert Loop Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                    insertLoopTuples(tupleMap);
                    break;
                } else if (ob.getType().getName().equals("com.tibco.cep.runtime.model.process.MergeTuple")) {
                    logger.log(Level.DEBUG, "Insert Merge Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                    insertMergeTuples(tupleMap);
                    break;
                } else {
                    logger.log(Level.WARN, "Insert Unknown Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                }
            }
        }
    }

    public void insertLoopTuples(Map<String, ObjectBean> entries) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.insertLoopTupleStatement(queryBuf);
        try {
            Iterator valueSet = entries.values().iterator();
            while (valueSet.hasNext()) {
                LoopTuple lt = (LoopTuple) valueSet.next();
                logger.log(Level.DEBUG, "Insert Loop Tuple - key : %s, job key : %s, " +
                    "task name : %s, counter : %d, max count  : %d, bean op : %s, isComplete : %b",  
                    lt.getKey(), lt.getJobKey(), lt.getTaskName(), 
                    lt.getCounter(), lt.getCounterMax(), lt.getBeanOp(), lt.isComplete());
                stmt.setString(1, lt.getKey());
                stmt.setString(2, lt.getJobKey());
                stmt.setString(3, lt.getTaskName());
                stmt.setInt(4, lt.getCounter());
                stmt.setInt(5, lt.getCounterMax());
                stmt.setInt(6, lt.isComplete() ? 1 : 0);
                if (sqltext.isEnabledFor(Level.TRACE)) {
                    sqltext.log(Level.TRACE, "%s (%s, %s, %s, %d, %d, %d)",
                        queryBuf,
                        lt.getKey(),
                        lt.getJobKey(),
                        lt.getTaskName(),
                        lt.getCounter(),
                        lt.getCounterMax(),
                        (lt.isComplete() ? 1 : 0));
                }
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Insert loop tuple failed with exception %s", ex, ex.getMessage());
            throw ex;
        } finally {
            stmt.close();
        }
    }

    public void insertMergeTuples(Map<String, ObjectBean> entries) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.insertMergeTupleStatement(queryBuf);
        try {
            Iterator valueSet = entries.values().iterator();
            while (valueSet.hasNext()) {
                MergeTuple mt = (MergeTuple) valueSet.next();
                String key = mt.getKey();
                short count = mt.getTokenCount();
                short maxCount = mt.getExpectedTokenCount();
                boolean isComplete = mt.isComplete();
                MergeTuple.MergeEntry[]  me = mt.getMergeEntries();
                logger.log(Level.DEBUG, "Insert Merge Tuple - key : %s,  token count : %d " +
                    "expected token count : %d, bean op : %s, isComplete : %b",  
                    mt.getKey(), mt.getTokenCount(), mt.getExpectedTokenCount(),
                    mt.getBeanOp(), mt.isComplete());
                if (me.length == 0) {
                stmt.setString(1, key);
                    stmt.setShort(2, count);
                    stmt.setShort(3, maxCount);
                    stmt.setInt(4, isComplete ? 1 : 0);
                    stmt.setNull(5, java.sql.Types.INTEGER);
                    stmt.setNull(6, java.sql.Types.INTEGER);
                    stmt.setNull(7, java.sql.Types.VARCHAR);
                    boolean isError = false;
                    stmt.setInt(8, isError ? 1 : 0);
                    stmt.addBatch();
                    continue;
                }
                for (int i=0; i<me.length; i++) {
                    stmt.setString(1, key);
                    stmt.setShort(2, count);
                    stmt.setShort(3, maxCount);
                    stmt.setShort(4, (short) (isComplete ? 1 : 0));
                    stmt.setLong(5, me[i].getProcessId());
                    stmt.setLong(6, me[i].getTimeMillis());
                    stmt.setString(7, me[i].getTransitionName());
                    boolean isError = me[i].hasError();
                    stmt.setInt(8, isError ? 1 : 0);
                    logger.log(Level.INFO, "Merge entry - procid : %d, procmillis : %d, trname : %s", me[i].getProcessId(), me[i].getTimeMillis(), me[i].getTransitionName());
                    if (sqltext.isEnabledFor(Level.TRACE)) {
                        sqltext.log(Level.TRACE, "%s (%s, %d, %d, %d, %d, %d, %s, %b)",
                            queryBuf,
                            key,
                            count,
                            maxCount,
                            isComplete ? 1 : 0,
                            me[i].getProcessId(),
                            me[i].getTimeMillis(),
                            me[i].getTransitionName(),
                            me[i].hasError());
                    }
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Insert merge tuple failed with exception %s", ex, ex.getMessage());
            throw ex;
        } finally {
            stmt.close();
        }
    }

    public void modifyObjectTuples(Map<Integer, Map<String, ObjectBean>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<String, ObjectBean>> entry = (Map.Entry) all_entries.next();
            // This is the hash code of LoopTuple or MergeTuple
            int typeId = entry.getKey();
            Map<String, ObjectBean> tupleMap = entry.getValue();
            Iterator tupleKeys = tupleMap.keySet().iterator();
            while(tupleKeys.hasNext()) {
                String tupleKey = (String) tupleKeys.next();
                ObjectBean ob = tupleMap.get(tupleKey);
                logger.log(Level.DEBUG, "Object Tuple type name : %s", ob.getType().getName());
                if (ob.getType().getName().equals("com.tibco.cep.runtime.model.process.LoopTuple")) {
                    logger.log(Level.DEBUG, "Modify Loop Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                    modifyLoopTuples(tupleMap);
                    break;
                } else if (ob.getType().getName().equals("com.tibco.cep.runtime.model.process.MergeTuple")) {
                    logger.log(Level.DEBUG, "Modify Merge Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                    modifyMergeTuples(tupleMap);
                    break;
                } else {
                    logger.log(Level.WARN, "Modify Unknown Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                }
            }
        }
    }

    public void modifyLoopTuples(Map<String, ObjectBean> entries) throws Exception {
        StringBuffer queryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.updateLoopTupleStatement(queryBuf);
        try {
            Iterator valueSet = entries.values().iterator();
            while (valueSet.hasNext()) {
                LoopTuple lt = (LoopTuple) valueSet.next();
                logger.log(Level.DEBUG, "Modify Loop Tuple - key : %s, job key : %s, " +
                        "task name : %s, counter : %d, max count  : %d, bean op : %s, isComplete : %b",  
                        lt.getKey(), lt.getJobKey(), lt.getTaskName(), 
                        lt.getCounter(), lt.getCounterMax(), lt.getBeanOp(), lt.isComplete());
                stmt.setString(1, lt.getJobKey());
                stmt.setString(2, lt.getTaskName());
                stmt.setLong(3, lt.getCounter());
                stmt.setLong(4, lt.getCounterMax());
                stmt.setInt(5, lt.isComplete() ? 1 : 0);
                stmt.setString(6, lt.getKey());
                if (sqltext.isEnabledFor(Level.TRACE)) {
                    sqltext.log(Level.TRACE, "%s (%s, %s, %s, %d, %d, %d)",
                            queryBuf,
                            lt.getKey(),
                            lt.getJobKey(),
                            lt.getTaskName(),
                            lt.getCounter(),
                            lt.getCounterMax(),
                            (lt.isComplete() ? 1 : 0));
                }
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Modify loop tuple failed with exception %s", ex, ex.getMessage());
            throw ex;
        } finally {
            stmt.close();
        }
    }

    public void modifyMergeTuples(Map<String, ObjectBean> entries) throws Exception {
        StringBuffer insertBuf = new StringBuffer(100);
        StringBuffer updateBuf = new StringBuffer(100);
        StringBuffer updateWithEntryBuf = new StringBuffer(100);
        PreparedStatement stmt = this.insertMergeTupleStatement(insertBuf);
        PreparedStatement stmtUpdate = null;
        PreparedStatement stmtUpdateWithEntry = null;
        try {
            Iterator valueSet = entries.values().iterator();
            while (valueSet.hasNext()) {
                MergeTuple mt = (MergeTuple) valueSet.next();
                MergeTuple.MergeEntry[]  me = mt.getMergeEntries();
                logger.log(Level.DEBUG, "Modify Merge Tuple - key : %s, token count : %d " +
                        "expected token count : %d, bean op : %s, isComplete : %b",  
                        mt.getKey(), mt.getTokenCount(), mt.getExpectedTokenCount(),
                        mt.getBeanOp(), mt.isComplete());
                String key = mt.getKey();
                short count = mt.getTokenCount();
                short maxCount = mt.getExpectedTokenCount();
                boolean isComplete = mt.isComplete();
                // When there is only one row, the existing record must be without
                // merge entry info.  Therefore, needs to update the existing row 
                // using merge entry data
                if (me.length == 1) {
                    if (stmtUpdateWithEntry == null) {
                        stmtUpdateWithEntry = this.updateMergeTupleAndEntryStatement(updateWithEntryBuf);
                    }
                    stmtUpdateWithEntry.setShort(1, count);
                    stmtUpdateWithEntry.setShort(2, (short) (isComplete ? 1 : 0));
                    stmtUpdateWithEntry.setLong(3, me[0].getProcessId());
                    stmtUpdateWithEntry.setLong(4, me[0].getTimeMillis());
                    stmtUpdateWithEntry.setString(5, me[0].getTransitionName());
                    boolean isError = me[0].hasError();
                    stmtUpdateWithEntry.setShort(6,(short)( isError ? 1 : 0));
                    stmtUpdateWithEntry.setString(7, key);
                    stmtUpdateWithEntry.addBatch();
                    continue;
                } else {
                    if (stmtUpdate == null) {
                        stmtUpdate = this.updateMergeTupleStatement(updateBuf);
                    }
                    stmtUpdate.setShort(1, count);
                    stmtUpdate.setShort(2, (short) (isComplete ? 1 : 0));
                    stmtUpdate.setString(3, key);
                    stmtUpdate.addBatch();
                }
                for (int i=0; i<me.length; i++) {
                    if (!me[i].isMerged()) {
                        continue;
                    }
                    stmt.setString(1, key);
                    stmt.setShort(2, count);
                    stmt.setShort(3, maxCount);
                    stmt.setShort(4, (short) (isComplete ? 1 : 0));
                    stmt.setLong(5, me[i].getProcessId());
                    stmt.setLong(6, me[i].getTimeMillis());
                    stmt.setString(7, me[i].getTransitionName());
                    boolean isError = me[i].hasError();
                    stmt.setShort(8, (short) (isError ? 1 : 0));
                    logger.log(Level.INFO, "Merge entry - procid : %d, procmillis : %d, trname : %s", me[i].getProcessId(), me[i].getTimeMillis(), me[i].getTransitionName());
                    if (sqltext.isEnabledFor(Level.TRACE)) {
                        sqltext.log(Level.TRACE, "%s (%s, %d, %d, %d, %d, %d, %s, %b)",
                                insertBuf,
                                key,
                                count,
                                maxCount,
                                isComplete ? 1 : 0,
                                me[i].getProcessId(),
                                me[i].getTimeMillis(),
                                me[i].getTransitionName(),
                                isError);
                    }
                    stmt.addBatch();
                }
            }
            if (stmtUpdate != null) {
                stmtUpdate.executeBatch();
            }
            if (stmtUpdateWithEntry != null) {
                stmtUpdateWithEntry.executeBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Modify merge tuple failed with exception %s", ex, ex.getMessage());
            throw ex;
        } finally {
            if (stmtUpdate != null) {
                stmtUpdate.close();
            }
            if (stmtUpdateWithEntry != null) {
                stmtUpdateWithEntry.close();
            }
            stmt.close();
        }
    }

    public void deleteObjectTuples(Map<Integer, Map<String, ObjectBean>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<String, ObjectBean>> entry = (Map.Entry) all_entries.next();
            // This is the hash code of LoopTuple or MergeTuple
            int typeId = entry.getKey();
            Map<String, ObjectBean> tupleMap = entry.getValue();
            Iterator tupleKeys = tupleMap.keySet().iterator();
            while(tupleKeys.hasNext()) {
                String tupleKey = (String) tupleKeys.next();
                ObjectBean ob = tupleMap.get(tupleKey);
                logger.log(Level.DEBUG, "Object Tuple type name : %s", ob.getType().getName());
                if (ob.getType().getName().equals("com.tibco.cep.runtime.model.process.LoopTuple")) {
                    logger.log(Level.DEBUG, "Delete Loop Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                    deleteLoopTuples(tupleMap);
                    break;
                } else if (ob.getType().getName().equals("com.tibco.cep.runtime.model.process.MergeTuple")) {
                    logger.log(Level.DEBUG, "Delete Merge Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                    deleteMergeTuples(tupleMap);
                    break;
                } else {
                    logger.log(Level.WARN, "Delete Unknown Tuples - type : %d, class type : %s", typeId, ob.getType().getName());
                }
            }
        }
    }

    public void deleteLoopTuples(Map<String, ObjectBean> entries) throws Exception {
        StringBuffer queryBuf = new StringBuffer();
        PreparedStatement stmt = this.deleteLoopTupleStatement(queryBuf);
        try {
            Iterator valueSet = entries.values().iterator();
            while (valueSet.hasNext()) {
                LoopTuple lt = (LoopTuple) valueSet.next();
                logger.log(Level.DEBUG, "Delete Loop Tuple - key : %s, job key : %s, " +
                        "task name : %s, counter : %d, max count  : %d, bean op : %s, isComplete : %b",  
                        lt.getKey(), lt.getJobKey(), lt.getTaskName(), 
                        lt.getCounter(), lt.getCounterMax(), lt.getBeanOp(), lt.isComplete());
                stmt.setString(1, lt.getKey());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Delete loop tuple failed with exception %s", ex, ex.getMessage());
            throw ex;
        } finally {
            stmt.close();
        }
    }

    public void deleteMergeTuples(Map<String, ObjectBean> entries) throws Exception {
        StringBuffer queryBuf = new StringBuffer();
        PreparedStatement stmt = this.deleteMergeTupleStatement(queryBuf);
        try {
            Iterator valueSet = entries.values().iterator();
            while (valueSet.hasNext()) {
                MergeTuple mt = (MergeTuple) valueSet.next();
                MergeTuple.MergeEntry[]  me = mt.getMergeEntries();
                logger.log(Level.DEBUG, "Delete Merge Tuple - key : %s, token count : %d " +
                        "expected token count : %d, bean op : %s, isComplete : %b",  
                        mt.getKey(), mt.getTokenCount(), mt.getExpectedTokenCount(),
                        mt.getBeanOp(), mt.isComplete());
                for (int i=0; i<me.length; i++) {
                    logger.log(Level.INFO, "Merge entry - procid : %d, procmillis : %d, trname : %s", me[i].getProcessId(), me[i].getTimeMillis(), me[i].getTransitionName());
                }
                stmt.setString(1, mt.getKey());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Delete merge tuple failed with exception %s", ex, ex.getMessage());
            throw ex;
        } finally {
            stmt.close();
        }
    }

    public ObjectBean getProcessLoopTupleByKey(String key) throws Exception {
        StringBuffer queryBuf = new StringBuffer();
        PreparedStatement stmt = this.getLoopTupleWithKeyStatement(queryBuf);
        ResultSet rs = null;
        try {
            stmt.setString(1, key);
            rs = stmt.executeQuery();
            LoopTuple lt = null;
            while (rs.next()) {
                String jobKey = rs.getString(1);
                String taskName = rs.getString(2);
                int counter = rs.getInt(3);
                int maxCounter = rs.getInt(4);
                boolean isComplete = (rs.getInt(5) > 0);
                Class tupleClass = ((ClassLoader) _rsp.getTypeManager()).loadClass("com.tibco.cep.bpmn.runtime.activity.tasks.DefaultLoopTuple");
                Constructor tupleCon = tupleClass.getConstructor(new Class[]{String.class, String.class, String.class, int.class});
                 lt = (LoopTuple) tupleCon.newInstance(new Object[] {jobKey, key, taskName, new Integer(maxCounter)});
                 lt.setCounter(counter);
                 lt.setComplete(isComplete);
                 break;
            }
            return lt;
        } finally {
            if (rs != null) {
                rs.close();
            }
            stmt.close();
        }
    }

    public ObjectBean getProcessMergeTupleByKey(String key) throws Exception {
    	StringBuffer queryBuf = new StringBuffer();
        PreparedStatement stmt = this.getMergeTupleWithKeyStatement(queryBuf);
        ResultSet rs = null;
        try {
            stmt.setString(1, key);
            rs = stmt.executeQuery();
            MergeTuple mt = null;
            if (rs.next()) {
                short count = rs.getShort(1);
                short maxCounter = rs.getShort(2);
                boolean isComplete = (rs.getInt(3) > 0);
                Class mtClass = ((ClassLoader) _rsp.getTypeManager()).loadClass("com.tibco.cep.bpmn.runtime.activity.gateways.DefaultMergeTuple");
                Constructor mtCon = mtClass.getConstructor(new Class[]{String.class, short.class});
                mt = (MergeTuple) mtCon.newInstance(new Object[] {key, new Short(maxCounter)});
                if (count > 0) {
                    Class meClass = ((ClassLoader) _rsp.getTypeManager()).loadClass("com.tibco.cep.bpmn.runtime.activity.gateways.DefaultMergeEntry");
                    Constructor meCon = meClass.getConstructor(new Class[]{long.class, String.class, boolean.class, long.class});
                    do {
                        long processId = rs.getLong(4);
                        long processTime = rs.getLong(5);
                        String transitionName = rs.getString(6);
                        boolean isError = rs.getBoolean(7);
                        MergeTuple.MergeEntry me = (MergeTuple.MergeEntry) meCon.newInstance(new Object[] {new Long(processId), transitionName,isError, new Long(processTime)});
                        mt.getMerges().add(me);
                    } while (rs.next());
                }
                if (mt.isComplete()) {
                    mt.setComplete();
                }
            }
            return mt;
        } finally {
            rs.close();
            stmt.close();
        }
    }

    //---------------------
    
    /**
     * The constant indicating that the current 'ResultSet' object
     * should be closed when calling 'getMoreResults'.
     *	CLOSE_CURRENT_RESULT = 1;
     * The constant indicating that the current 'ResultSet' object
     * should not be closed when calling 'getMoreResults'.
     *	KEEP_CURRENT_RESULT = 2;
     * The constant indicating that all 'ResultSet' objects that
     * have previously been kept open should be closed when calling
     * 'getMoreResults'.
     *	CLOSE_ALL_RESULTS = 3;
     * The constant indicating that a batch statement executed successfully
     * but that no count of the number of rows it affected is available.
     *	SUCCESS_NO_INFO = -2;
     * The constant indicating that an error occurred while executing a
     * batch statement.
     *	EXECUTE_FAILED = -3;
     * The constant indicating that generated keys should be made
     * available for retrieval.
     *	RETURN_GENERATED_KEYS = 1;
     * The constant indicating that generated keys should not be made
     * available for retrieval.
     *	NO_GENERATED_KEYS = 2;
     */
    private void examineBatchResults(String category, int[] results) {
    	if (logger.isEnabledFor(Level.TRACE)) {
	    	for (int i = 0; i < results.length; i++) {
	    		if (results[i] < 0) {//Any value below zero is failure
	    			logger.log(Level.WARN, new Exception("Batch operation failure!"), "ERROR: Unexpected %s result %s", category, results[i]); 
	    		}
	    	}
	    	logger.log(Level.TRACE, "Succesfull %s batch operation [%d]", category, results.length);
    	}
    }
}
