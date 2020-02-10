package com.tibco.rta.service.persistence.db;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.KeyFactory;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MultiValueMetric;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.exception.PersistenceStoreNotAvailableException;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.RtaTransaction.FactHr;
import com.tibco.rta.common.service.RtaTransaction.MetricFact;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.impl.BaseMetricImpl;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.impl.MultiValueMetricImpl;
import com.tibco.rta.impl.RecoveredFactImpl;
import com.tibco.rta.impl.SingleValueMetricImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.MetricValueDescriptorImpl;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.ModelSerializationConstants;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.model.stats.DBConnectionPoolStats;
import com.tibco.rta.model.stats.DBTransactionStats;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.RtaNodeContext;
import com.tibco.rta.runtime.model.impl.MetricNodeImpl;
import com.tibco.rta.runtime.model.impl.RtaNodeContextImpl;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.AlertNodeStateKey;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateImpl;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.persistence.PersistenceServiceStatsProvider;
import com.tibco.rta.util.HashGenerator;
import com.tibco.rta.util.IOUtils;
import org.xml.sax.InputSource;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DatabasePersistenceService extends AbstractStartStopServiceImpl implements PersistenceService, PersistenceServiceStatsProvider, DatabasePersistenceServiceMBean {

    private final static int ORACLE_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE = 1;
    private final static int MSSQL_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE = 2601;
    private final static int IBMDB2_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE1 = 4228;
    private final static int IBMDB2_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE2 = -4228;

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
    protected static final Logger LOGGER_DTLS = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE_DETAILS.getCategory());
    protected static final Logger LOGGER_CRUD = LogManagerFactory.getLogManager().getLogger("rta.services.persistence.crud");

    protected DatabaseConnectionPool connectionPool;
    protected DatabaseSchemaManager databaseSchemaManager;
    protected static boolean usePK;
    protected static boolean isMSSqlServer;
    //	protected boolean insertProcessed;
//	protected boolean isInsertProcessedWithMultipleTable;
    protected boolean isGenerateSchema;
    protected boolean isGenerateRule;
    protected int databaseBatchSize = 1000;
    protected AtomicLong insertCount = new AtomicLong(0);
    protected AtomicLong updateCount = new AtomicLong(0);
    protected AtomicLong deleteCount = new AtomicLong(0);
    protected AtomicLong transactionCount = new AtomicLong(0);
    /**
     * Whether to use BLOB for multivalued Metric or not.
     */
    protected boolean isToUseBlob;
    // Cache metric SQLs
    private Map<String, String> selectMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    private Map<String, String> insertMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    private Map<String, String> updateMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    private Map<String, String> deleteMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    // Cache Fact SQLs
    private Map<String, String> insertFactSqlPerSchema = new ConcurrentHashMap<String, String>();
    private Map<String, String> deleteFactSqlPerSchema = new ConcurrentHashMap<String, String>();
    // Cache MetricFact SQLs
    private Map<String, String> insertMetricFactSqlPerDh = new ConcurrentHashMap<String, String>();
    // Cache RuleMetric SQLs
    private Map<String, String> selectRuleMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    private Map<String, String> insertRuleMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    private Map<String, String> updateRuleMetricSqlPerDh = new ConcurrentHashMap<String, String>();
    private Map<String, String> deleteRuleMetricSqlPerDh = new ConcurrentHashMap<String, String>();


    // Cache Processed Fact SQLs
    private Map<String, String> insertProcessedFactSqlPerDh = new ConcurrentHashMap<String, String>();

    //Cache multivalued metric insert SQL per Measurement.
    private Map<String, String> insertMetricMultiValuedSqlPerMeasurement = new ConcurrentHashMap<String, String>();

    private int queryFetchSize;

    @Override
    public void init(Properties config) throws Exception {
    	isMSSqlServer = isMsSqlServer(config);
        String usePKStr = System.getProperty("use_pk");
        if (usePKStr == null) {
            // No System variable configured.
            if (isMSSqlServer) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "MS Sql Server, Default No Primary Key mode.");
                }
                usePK = false;
            } else {
                // Default use Primary key.
                usePK = true;
            }
        } else {
            // System variable configured, go as configured.
            usePK = "true".equalsIgnoreCase(usePKStr);
        }

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Initializing Database persistence service with Primary Key as : %s", usePK ? "ENABLED" : "DISABLED");
        }
//		insertProcessed = System.getProperty("insert_processed", "false").equals("true");
//		isInsertProcessedWithMultipleTable = System.getProperty("insert_processed_multiple", "false").equals("true");
        // Generate schema for DEV mode only.
        isGenerateSchema = System.getProperty("gen_db_schema", "false").equals("true");
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Generate database Schema/tables : %s", isGenerateSchema ? "ENABLED" : "DISABLED");
        }

        connectionPool = new DatabaseConnectionPool(config);
        connectionPool.init();
        registerMBean(config);
        String batchSize = (String) ConfigProperty.RTA_JDBC_BATCH_SIZE.getValue(config);
        try {
            databaseBatchSize = Integer.parseInt(batchSize);
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Database batch size: [%d]", databaseBatchSize);
            }
        } catch (NumberFormatException nfe) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Database batch size [%d]", databaseBatchSize);
            }
        }

        String value = (String) ConfigProperty.RTA_METRICS_MULTIVALUES_EXPLICIT.getValue(config);
        isToUseBlob = "false".equalsIgnoreCase(value);
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Configuration: %s [%s]", ConfigProperty.RTA_METRICS_MULTIVALUES_EXPLICIT.name(), !isToUseBlob);
        }

        databaseSchemaManager = new DatabaseSchemaManager(this, usePK, isGenerateSchema, isToUseBlob);
        boolean isSchemaMultiValued = false;
        boolean isDbUsingBlobType = false;
        Collection<RtaSchema> schemas = ServiceProviderManager.getInstance().getAdminService().getAllSchemas();
        // Check whether given schema has a multi-valued metric function or not
        schemaLoop:
        for (RtaSchema schema : schemas) {
            for (Cube cube : schema.getCubes()) {
                for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                    for (Measurement measurement : dh.getMeasurements()) {
                        MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
                        if (md.isMultiValued()) {
                            isSchemaMultiValued = true;
                            // Check whether database is using BLOB datatype or not
                            isDbUsingBlobType = isDbUsingBlob(schema, cube, dh, measurement);
                            // break if any schema is using multi-valued function.
                            break schemaLoop;
                        }
                    }
                }
            }
        }
        if (isSchemaMultiValued) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "isSchemaMultiValued=[%s] isToUseBlob=[%s] isDbUsingBlobType[%s]", isSchemaMultiValued,
                        isToUseBlob, isDbUsingBlobType);
            }

            if (isToUseBlob && !isDbUsingBlobType) {
                LOGGER.log(Level.WARN, "Server is configured NOT to store multivalues of metric explicitly but Database schema is genereated to store them explicitly, "
                        + "hence overriding the configured server property %s to true.", ConfigProperty.RTA_METRICS_MULTIVALUES_EXPLICIT.name());
                isToUseBlob = false;
            }
            if (!isToUseBlob && isDbUsingBlobType) {
                LOGGER.log(Level.WARN, "Server is configured to store multivalues of metric explicitly but Database schema is generated NOT to store them explicitly, "
                        + "hence overriding the configured server property %s to false.", ConfigProperty.RTA_METRICS_MULTIVALUES_EXPLICIT.name());
                isToUseBlob = true;
            }
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "No multivalued metric function defined in schemas.");
            }
        }

        for (RtaSchema schema : schemas) {
            // check table presence for production mode and create table for dev mode
            createSchema(schema);
        }

        ServiceProviderManager.getInstance().getAdminService().addModelChangeListener(databaseSchemaManager);

        String queryFetchSizeStr = (String) ConfigProperty.RTA_JDBC_QUERY_FETCH_SIZE.getValue(config);
        queryFetchSize = Integer.parseInt(queryFetchSizeStr);

    }

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Database Persistence service..");
        }
        super.start();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Database Persistence service Complete.");
        }
    }

    @Override
    public void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Database Persistence service..");
        }
        if (connectionPool != null) {
            connectionPool.close();
        }
        super.stop();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Database Persistence service Complete.");
        }
    }

    @Override
    public void suspend() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isStarted() {
        if (connectionPool != null) {
            return connectionPool.isStarted();
        }
        return false;
    }

    @Override
    public <N> RtaNode getNode(Key key) throws Exception {
        int retryCount = -1;
        ResultSet rs = null;
        PreparedStatement pst = null;
        while (true) {
            retryCount++;
            try {
                MutableMetricNode metricNode = null;
                if (key instanceof MetricKey) {
                    MetricKey mKey = (MetricKey) key;
					if (LOGGER_CRUD.isEnabledFor(Level.DEBUG)) {
						LOGGER_CRUD.log(Level.DEBUG, "Invoked getNode() Key=" + mKey + " HeirarchyName=" + mKey.getDimensionHierarchyName());
					}

                    DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());
                    String selectSql = getSelectMetricSql(mKey, dh);
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Invoked getNode(), Schema=[%s] key=[%s] SqlQuery=[%s]", mKey.getSchemaName(), key, selectSql);
                    }
                    Connection connection = connectionPool.getSqlConnection();
                    pst = connection.prepareStatement(selectSql);
                    setWhereClauseParamForMetricSelection(pst, mKey, dh, 1);
                    rs = pst.executeQuery();
                    metricNode = fetchMetricNodeFromRS(rs, mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName(), null);

                }
                if (retryCount > 0) {
                    LOGGER.log(Level.DEBUG, "Database reconnected. Retrying getNode: [%s] successful.", key.toString());

                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "getNode(), Returned MetricNode=[%s]", metricNode);
                }
                return metricNode;
            } catch (DBConnectionsBusyException busyEx) {
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "Database connections are busy while getNode: [%s]. Retrying in 5 seconds.", key.toString());
                }
                ThreadSleep(5000);
            } catch (PersistenceStoreNotAvailableException dbne) {
                LOGGER.log(Level.ERROR, "Database not available while getNode: [%s]. Waiting for reconnect..", key.toString());
                connectionPool.waitForReconnect(30000, -1);
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Database reconnected. Processing will continue");
                }
            } catch (SQLException ex) {
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "SQLException in getNode() of  key %s: ", ex, key.toString());
                }
                try {
                    connectionPool.check(ex, connectionPool.getCurrentConnection());
                } catch (PersistenceStoreNotAvailableException e) {
                    LOGGER.log(Level.ERROR, "Database not available.. Waiting for reconnect..");
                    connectionPool.waitForReconnect(30000, -1);
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Database reconnected. Retrying getNode()..");
                    }
                }
                // throw new DBException();

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while getNode() getNode() of  key %s: ", e, key.toString());
                throw e;
            } finally {
                // BALA: need to release it.
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                connectionPool.releaseCurrentConnection();
            }
        }

    }

    // is called from applyTransaction, so covered for retries etc.
    @Override
    public void save(Fact fact) throws Exception {
        PreparedStatement pst = null;
        String insertSql = null;
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "save(), FackKeyUid : %s", ((FactKeyImpl) fact.getKey()).getUid());
            }
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            RtaSchema schema = fact.getOwnerSchema();
            insertSql = getInsertFactSql(schema);
            pst = dbConnection.prepareStatement(insertSql);
            setInsertFactStatementValue(pst, fact, insertSql);
            pst.executeUpdate();
            insertCount.getAndIncrement();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while saving Fact, FactKeyUid: %s InsertFactSql: %s", e, ((FactKeyImpl) fact.getKey()).getUid(), insertSql);
            throw e;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }

    }

    // called from inside applyTransaction
    @Override
    public void save(RtaNode node) throws Exception {
        if (!(node instanceof MetricNode)) {
            return;
        }
        MetricNode metricNode = (MetricNode) node;
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "save(), MetricNode Key : %s", metricNode.getKey());
        }
        MetricKey mKey = (MetricKey) metricNode.getKey();
        String tableName = databaseSchemaManager.makeMetricSchemaTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());
        if (metricNode.isNew()) {
            insertMetricNode(metricNode, tableName);
        } else {
            updateMetricNode(metricNode, tableName);
        }
    }

    // called from inside applyTransaction
    @Override
    public void save(RtaNode node, boolean isNew) throws Exception {
        if (!(node instanceof MetricNode)) {
            return;
        }
        MetricNode metricNode = (MetricNode) node;
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "save(), MetricNode Key : %s", metricNode.getKey());
        }
        MetricKey mKey = (MetricKey) metricNode.getKey();
        String tableName = databaseSchemaManager.makeMetricSchemaTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());
        if (isNew) {
            insertMetricNode(metricNode, tableName);
            insertCount.getAndIncrement();
        } else {
            updateMetricNode(metricNode, tableName);
            updateCount.getAndIncrement();
        }
    }

    @Override
    public void delete(RtaNode node) {
        if (!(node instanceof MetricNode)) {
            return;
        }
        MetricNode metricNode = (MetricNode) node;
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "delete(), MetricNode Key : %s", metricNode.getKey());
        }
        if (LOGGER_CRUD.isEnabledFor(Level.DEBUG)) {
            LOGGER_CRUD.log(Level.DEBUG, "delete(), MetricNode Key : %s", metricNode.getKey());
        }
        MetricKey mKey = (MetricKey) metricNode.getKey();
        PreparedStatement pst = null;
        String deleteSql = "";
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());
            deleteSql = getDeleteMetricSql(mKey, dh);
            pst = dbConnection.prepareStatement(deleteSql);
            setMetricValueToDeleteStatement(pst, mKey, dh);
            pst.executeUpdate();
            deleteCount.getAndIncrement();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while deleting Metric Node. deleteSql = %s", e, deleteSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }

    }

    @Override
    public RtaNode getParentNode(RtaNode node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Browser<Fact> getFactBrowser(RtaNode node, List<MetricFieldTuple> orderByList) {

        try {
            if (node instanceof MetricNode) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "getFactBrowser(), MetricNode Key : %s", node.getKey());
                }
                return new DBFactNodeBrowser(this, (MetricNode) node, orderByList);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while fetching FactBrowser.", e);
        }
        return new EmptyIterator<Fact>();
    }

    @Override
    public boolean deleteRule(String ruleName) throws Exception {
        PreparedStatement pst = null;
        Connection dbConnection = null;
        try {
            dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }

            String deleteSql = "DELETE FROM " + DBConstant.RULE_TABLE_NAME + " WHERE " + DBConstant.RULE_NAME_FIELD + "= ?";
            pst = dbConnection.prepareStatement(deleteSql);
            pst.setString(1, ruleName);
            int rowsDeleted = pst.executeUpdate();
            deleteCount.getAndIncrement();
            if (rowsDeleted == 1) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Deleted rule [%s]", ruleName);
                }

                deleteFromRuleMetricNodeTable(ruleName);
                ServiceProviderManager.getInstance().getObjectManager().deleteRuleMetricNodeState(ruleName);
                dbConnection.commit();
                transactionCount.getAndIncrement();
                return true;
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error in deleting rule:" + ruleName + " from database", e);
            throw e;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            connectionPool.releaseCurrentConnection();
        }
        return false;
    }

    @Override
    public List<RuleDef> getAllRuleNames() throws Exception {
        List<RuleDef> rules = new ArrayList<RuleDef>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        RuleDef ruleDef;
        Connection dbConnection;

        try {
            dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }

            String selectSql = "SELECT * FROM " + DBConstant.RULE_TABLE_NAME;
            pst = dbConnection.prepareStatement(selectSql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Reader reader = rs.getCharacterStream(DBConstant.RULE_CONTENT_FIELD);
                ruleDef = SerializationUtils.deserializeRule(new InputSource(reader));
                rules.add(ruleDef);
            }
            return rules;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error in selecting rule from database ", e);
            throw e;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public RuleDef getRule(String ruleName) throws Exception {
        PreparedStatement pst = null;
        ResultSet rs = null;
        RuleDef ruleDef;
        Connection dbConnection;
        try {
            dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }

            String selectSql = "SELECT * FROM " + DBConstant.RULE_TABLE_NAME + " WHERE " + DBConstant.RULE_NAME_FIELD + " = ?";
            pst = dbConnection.prepareStatement(selectSql);
            pst.setString(1, ruleName);
            rs = pst.executeQuery();
            if (rs.next()) {
                Reader reader = rs.getCharacterStream(DBConstant.RULE_CONTENT_FIELD);
                ruleDef = SerializationUtils.deserializeRule(new InputSource(reader));
                return ruleDef;
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error in selecting rule from database ", e);
            throw e;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            connectionPool.releaseCurrentConnection();
        }
        return null;
    }

    @Override
    public void updateRule(RuleDef ruleDef) throws Exception {
        PreparedStatement pst = null;
        Connection dbConnection = null;
        try {
            dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }

            StringBuilder fieldBldr = new StringBuilder();
            StringBuilder valueBldr = new StringBuilder();
            String s = SerializationUtils.serializeRule(ruleDef);

            fieldBldr.append(DBConstant.RULE_NAME_FIELD + " , ");
            fieldBldr.append(DBConstant.RULE_CONTENT_FIELD);

            valueBldr.append("?,?");

            String updateSql = "UPDATE " + DBConstant.RULE_TABLE_NAME + " SET " + DBConstant.RULE_CONTENT_FIELD + "=?" + " WHERE " + DBConstant.RULE_NAME_FIELD + "=?";
            pst = dbConnection.prepareStatement(updateSql);


            StringReader stringReader = new StringReader(s);
            pst.setCharacterStream(1, stringReader, s.length());
            pst.setString(2, ruleDef.getName());

            int rowsUpadated = pst.executeUpdate();

            if (rowsUpadated > 0) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "row sucessfully updated in rule table");
                }
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Upadated rule [%s]", ruleDef.getName());
                }
            } else {
                String insertSql = "INSERT INTO " + DBConstant.RULE_TABLE_NAME + " (" + fieldBldr + ") VALUES ( " + valueBldr + ")";
                pst = dbConnection.prepareStatement(insertSql);
                pst.setString(1, ruleDef.getName());
                StringReader stringReader1 = new StringReader(s);
                pst.setCharacterStream(2, stringReader1, s.length());
                rowsUpadated = pst.executeUpdate();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "row sucessfully inserted in rule table");
                }
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Inserted rule [%s]", ruleDef.getName());
                }
            }
            updateCount.getAndIncrement();
            transactionCount.getAndIncrement();
            dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while inserting rule:" + ruleDef.getName());
            throw e;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public void insertRule(RuleDef ruleDef) throws Exception {
        PreparedStatement pst = null;
        Connection dbConnection = null;

        try {
            dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }

            StringBuilder fieldBldr = new StringBuilder();
            StringBuilder valueBldr = new StringBuilder();
            String s = SerializationUtils.serializeRule(ruleDef);

            fieldBldr.append(DBConstant.RULE_NAME_FIELD + " , ");
            fieldBldr.append(DBConstant.RULE_CONTENT_FIELD);
            valueBldr.append("?,?");
            String insertSql = "INSERT INTO " + DBConstant.RULE_TABLE_NAME + " (" + fieldBldr + ") VALUES ( " + valueBldr + ")";
            pst = dbConnection.prepareStatement(insertSql);
            pst.setString(1, ruleDef.getName());

            StringReader stringReader = new StringReader(s);
            pst.setCharacterStream(2, stringReader, s.length());

            int rowsUpadated = pst.executeUpdate();
            insertCount.getAndIncrement();
            transactionCount.getAndIncrement();
            if (rowsUpadated > 0) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "row sucessfully inserted into rule table");
                }
            }
            dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while inserting rule:" + ruleDef.getName());
            throw e;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public void createSchema(RtaSchema schema) throws Exception {
        if (isGenerateSchema) {
            // Create DB schema.
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Generating Database Schema/Tables for RTA SchemaName : %s", schema.getName());
            }
            databaseSchemaManager.createSchema(schema);

        } else {
            // Check whether fact table exists or not.
            String factTableName = databaseSchemaManager.makeFactTableName(schema.getName());
            if (databaseSchemaManager.isTableExists(factTableName)) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Database Table [%s] exists.", factTableName);
                }
            }
        }
    }

    @Override
    public void addFact(RtaNode node, Fact fact) throws Exception {
        if (node instanceof MetricNode) {
            MetricNode metricNode = (MetricNode) node;
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "addFact(), FactKeyUid : %s NodeKey: %s", ((FactKeyImpl) fact.getKey()).getUid(), node.getKey());
            }

            // boolean isPresent = isCompositeKeyPresent(tableName,
            // ((MetricKey) metricNode.getKey()).toString(),
            // ((FactKeyImpl) fact.getKey()).getUid());
            // if (isPresent) {
            // updateMetricFact(metricNode, fact, tableName);
            // } else {
            saveMetricFactToDB(metricNode, fact);
            insertCount.getAndIncrement();

            // }

        }
    }

    @Override
    public void deleteFact(RtaNode metricNode, Fact fact) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void printAllNodes() {
        // TODO Auto-generated method stub

    }

    @Override
    public Browser<MetricNode> getMetricNodeBrowser(QueryDef queryDef) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "getMetricNodeBrowser(), QueryDef Name : %s", queryDef.getName());
        }
        if (queryDef instanceof QueryByKeyDef) {
            return new DBChildMetricNodeBrowser(this, (QueryByKeyDef) queryDef);
        } else {
            return new DBFilterBasedMetricNodeBrowser(this, (QueryByFilterDef) queryDef);
        }
    }

    @Override
    public Browser<MetricNode> getSnapshotMetricNodeBrowser(QueryDef queryDef) throws Exception {
        // TODO Auto-generated method stub
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "getMetricNodeBrowser(), QueryDef Name : %s", queryDef.getName());
        }
        if (queryDef instanceof QueryByKeyDef) {
            return new DBKeyBasedMetricNodeBrowser(this, (QueryByKeyDef) queryDef, usePK);
        } else {
            return new DBFilterBasedMetricNodeBrowser(this, (QueryByFilterDef) queryDef);
        }
    }

    @Override
    public void delete(Fact fact) {
        PreparedStatement pst = null;
        String deleteSql = null;
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "delete(), FackKeyUid : %s", ((FactKeyImpl) fact.getKey()).getUid());
            }
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            RtaSchema schema = fact.getOwnerSchema();
            deleteSql = getDeleteFactSql(schema);
            pst = dbConnection.prepareStatement(deleteSql);
            pst.setString(1, ((FactKeyImpl) fact.getKey()).getUid());
            pst.executeUpdate();
            deleteCount.getAndIncrement();

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while deleting Fact, FactKeyUid: %s deleteFactSql: %s", e, ((FactKeyImpl) fact.getKey()).getUid(), deleteSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void beginTransaction() throws SQLException {
        // connectionPool.getSqlConnection();
    }

    @Override
    public void commit() throws Exception {
        Connection con = null;
        try {
            con = connectionPool.getCurrentConnection();
            if (con != null) {
                con.commit();
                transactionCount.getAndIncrement();
            } else {
                LOGGER.log(Level.ERROR, "Cannot commit, No connection found in ThreadLocal");
                throw new RuntimeException("Cannot commit, No connection found in ThreadLocal");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred while commit.", e);
            throw e;
        } finally {
            connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public void rollback() throws Exception {
        Connection con;
        try {
            con = connectionPool.getCurrentConnection();
            if (con != null) {
                con.rollback();
            } else {
                LOGGER.log(Level.WARN, "Cannot rollback, No connection found in ThreadLocal");
//                throw new RuntimeException("Cannot rollback, No connection found in ThreadLocal");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred while Rollback.", e);
            throw e;
        } finally {
            connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception {
        // Bala: Use the action name.
        int retryCount = -1;
        ResultSet rs = null;
        PreparedStatement pst = null;
        RuleMetricNodeState rmNode = null;
        Connection dbConnection = null;
        String selectRuleMetricSql = getSelectRuleMetricSql(key);

        while (true) {
            retryCount++;
            try {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "getRuleMetricNode(), for RuleName: %s RuleMetricKey: %s", ruleName, key.toString());
                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "getRuleMetricNode(), SqlQuery : %s", selectRuleMetricSql);
                }
                dbConnection = connectionPool.getSqlConnection();
                pst = dbConnection.prepareStatement(selectRuleMetricSql);
                int index = 1;
                if(isMSSqlServer && !usePK){
                	pst.setString(index++, HashGenerator.hash(key.toString()));
                }
                pst.setString(index++, key.toString());
                pst.setString(index++, ruleName);
                pst.setString(index++, actionName);
                rs = pst.executeQuery();

                MetricNode mNode = fetchMetricNodeFromRS(rs, key.getSchemaName(), key.getCubeName(), key.getDimensionHierarchyName(), null);

                if (mNode != null) {
                    rmNode = new RuleMetricNodeStateImpl(ruleName, rs.getString(DBConstant.RULE_ACTION_NAME_FIELD), key);
                    rmNode.setCount(rs.getInt(DBConstant.RULE_SET_COUNT_FIELD));
                    rmNode.setLastFireTime(rs.getLong(DBConstant.RULE_LAST_FIRED_TIME_FIELD));
                    rmNode.setScheduledTime(rs.getLong(DBConstant.RULE_SCHEDULED_TIME_FIELD));
                    rmNode.setMetricNode(mNode);
                } else {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Rule Not Found with RuleName: %s RuleMetricKey: %s", ruleName, key.toString());
                    }
                }
                if (retryCount > 0) {
                    LOGGER.log(Level.DEBUG, "Database reconnected. Retrying getRuleMetricNode: [%s] successful.", key.toString());

                }
                return rmNode;
            } catch (DBConnectionsBusyException busyEx) {
                LOGGER.log(Level.WARN, "Database connections are busy while getRuleMetricNode: [%s]. Retrying in 5 seconds.", key.toString());
                ThreadSleep(5000);
            } catch (PersistenceStoreNotAvailableException dbne) {
                LOGGER.log(Level.ERROR, "Database not available while getRuleMetricNode: [%s]. Waiting for reconnect..", key.toString());
                connectionPool.waitForReconnect(30000, -1);
                LOGGER.log(Level.INFO, "Database reconnected. Processing will continue");
            } catch (SQLException ex) {
                LOGGER.log(Level.WARN, "SQLException in getRuleMetricNode() of  key %s: ", ex, key.toString());
                try {
                    connectionPool.check(ex, connectionPool.getCurrentConnection());
                } catch (PersistenceStoreNotAvailableException e) {
                    LOGGER.log(Level.ERROR, "Database not available.. Waiting for reconnect..");
                    connectionPool.waitForReconnect(30000, -1);
                    LOGGER.log(Level.DEBUG, "Database reconnected. Retrying getRuleMetricNode()..");
                }
                // throw new DBException();

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while getRuleMetricNode() of  key %s: ", e, key.toString());
                throw e;
            } finally {
                // BALA: need to release it.
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                connectionPool.releaseCurrentConnection();
            }
        }

    }

    @Override
    public void saveRuleMetricNode(RuleMetricNodeState rmNode) {
        RuleMetricNodeStateKey rmKey = rmNode.getKey();
        MetricKey mKey = rmKey.getMetricKey();
        try {
            String tableName = databaseSchemaManager.makeRuleMetricTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());
            //			boolean isPresent = isCompositeKeyPresent(tableName, mKey.toString(), rmKey.getRuleName(), rmKey.getActionName());
            if (!rmNode.isNew()) {
                updateRuleMetricNode(rmNode, tableName, mKey);
            } else {
                insertRuleMetricNode(rmNode, tableName, mKey);
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while Saving Rule Metric Node.", e);
        }

    }

    @Override
    public void deleteRuleMetricNode(RuleMetricNodeStateKey rmKey) {
        MetricKey mKey = rmKey.getMetricKey();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, " DELETE RuleMetricNode(), for RuleName: %s Action: %s RuleMetricKey: %s",
                    rmKey.getRuleName(), rmKey.getActionName(), mKey.toString());
        }
        PreparedStatement pst = null;
        String deleteRuleMetricSql = null;
        try {
            deleteRuleMetricSql = getDeleteRuleMetricSql(mKey);
            Connection dbConnection = connectionPool.getSqlConnection();
            pst = dbConnection.prepareStatement(deleteRuleMetricSql);
            int index = 1;
            if(isMSSqlServer && !usePK){
            	pst.setString(index++, HashGenerator.hash(mKey.toString()));
            }
            pst.setString(index++, mKey.toString());
            pst.setString(index++, rmKey.getRuleName());
            pst.setString(index++, rmKey.getActionName());
            pst.executeUpdate();
            deleteCount.getAndIncrement();

            // dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while deleteRuleMetricNode(), deleteRuleMetricSql = %s", e, deleteRuleMetricSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
            // connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public Browser<MetricNode> getMatchingAssets(String schemaName,
                                                 String cubeName, String hierarchyName, Fact fact) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "getMatchingAssets(), for schemaName : %s",
                    schemaName);
        }
        return new DBAssetMetricNodeBrowser(this, schemaName, cubeName,
                hierarchyName, fact);
    }

    @Override
    public void saveProcessedFact(Fact fact, String schemaName, String cubeName, String hierarhyName) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "saveProcessedFact(), FactKey : %s SchemaName: %s", fact.getKey(), schemaName);
        }
        // String tableName =
        // databaseSchemaManager.getProcessedFactsSchema(schemaName, cubeName);
        // PreparedStatement pst = null;
        // try {
        // Connection dbConnection = connectionPool.getSqlConnection();
        // if (dbConnection == null) {
        // throw new
        // RuntimeException("Cannot get Connection from ThreadLocal.");
        // }
        // StringBuilder querybldr = new StringBuilder("INSERT INTO " +
        // tableName);
        // querybldr.append("(" + DatabasePersistenceService.KEY_FIELD + ")");
        // querybldr.append(" VALUES (?)");
        // pst = dbConnection.prepareStatement(querybldr.toString());
        // pst.setString(1, ((FactKeyImpl) fact.getKey()).getUid());
        // pst.executeUpdate();
        // } catch (Exception e) {
        // LOGGER.log(Level.ERROR,
        // "An error occurred, while inserting in ProcessedFact.", e);
        // throw e;
        // } finally {
        // if (pst != null) {
        // pst.close();
        // }
        // }

    }

    @Override
    public void updateProcessedFact(Fact fact, String schemaName, String cubeName, String hierarhyName) throws Exception {
        String sql = getUpdateProcessedFactSql(schemaName, cubeName, hierarhyName) + "= ?";
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "updateProcessedFact(), SchemaName: %s preparedSQL: %s FactKeyUid : %s ", schemaName, sql, ((FactKeyImpl) fact.getKey()).getUid());
        }
        PreparedStatement pst = null;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Using connection [%s]", dbConnection);
            }
            pst = dbConnection.prepareStatement(sql);
            int index = 1;
            pst.setString(index++, "Y");
            pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
            pst.setString(index++, ((FactKeyImpl) fact.getKey()).getUid());
            int recordCount = pst.executeUpdate();
            updateCount.getAndIncrement();

            if (recordCount < 1) {
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "updateProcessedFact(), No Record Updated for FactKeyUid = %s ", ((FactKeyImpl) fact.getKey()).getUid());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while updating ProcessedFact.", e);
            throw e;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public Browser<Fact> getUnProcessedFact(String schemaName) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "getUnProcessedFact(), for SchemaName: %s ", schemaName);
        }
        try {
            //return new DBUnprocessedFactBrowser(this, schemaName, insertProcessed);
            return new DBUnprocessedFactBrowser(this, schemaName, false);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while fetching UnProcessed Facts.", e);
        }
        return new EmptyIterator<Fact>();
    }

    @Override
    public Browser<Fact> getUnProcessedFact(String schemaName, String cubeName, String hierarchyName) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "getUnProcessedFact(), for SchemaName: %s CubeName: %s dhName: %s", schemaName, cubeName, hierarchyName);
        }
        try {
            return new DBUnprocessedFactBrowser(this, schemaName, cubeName, hierarchyName);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while fetching UnProcessed Facts for hierarchyName: %s", e, hierarchyName);
        }
        return new EmptyIterator<Fact>();
    }

    @Override
    public void applyTransaction(boolean retryInfinite) throws Exception {
        int retryCnt = 0;
        RtaTransaction txn = (RtaTransaction) RtaTransaction.get();
        while (true) {
            retryCnt++;
            if (retryCnt > 1) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Retrying transaction. Retry count [%d]", retryCnt);
                }
            }
            try {
                connectionPool.getSqlConnection();
                for (Map.Entry<Key, RtaTransactionElement<?>> e : txn.getTxnList().entrySet()) {

                    Key key = e.getKey();
                    RtaTransactionElement txnE = e.getValue();

                    if (key instanceof MetricKey) {
                        MetricNode metricNode = (MetricNode) txnE.getNode();
                        if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                            // System.out.println("NEW MetricKey " +
                            // metricNode.getKey());
                            save(metricNode, true);
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                            // System.out.println("UPDT MetricKey " +
                            // metricNode.getKey());
                            save(metricNode, false);
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
                            // System.out.println("DEL MetricKey " +
                            // metricNode.getKey());

                            delete(metricNode);
                        }

                    } else if (key instanceof FactKeyImpl) {
                        Fact fact = (Fact) txnE.getNode();
                        if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                            // System.out.println("NEW Fact " +
                            // fact.getKey().toString());
                            save(fact);
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                            // System.out.println("UPDT Fact " +
                            // fact.getKey().toString());

                            // Nothing to do, Update fact call.
                            // save(fact);
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
                            delete(fact);
                        }
                    } else if (key instanceof RtaTransaction.MetricFact.MFKey) {
                        RtaTransaction.MetricFact metricFact = (MetricFact) txnE.getNode();
                        if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                            // System.out.println("NEW metric-fact " +
                            // metricFact.getKey().toString() + ", " +
                            // metricFact.getMetricNode().getKey());

                            saveMetricFactToDB(metricFact.getMetricNode(), metricFact.getFact());
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                            // System.out.println("UPDT metric-fact " +
                            // metricFact.getKey().toString() + ", " +
                            // metricFact.getMetricNode().getKey());

                            saveMetricFactToDB(metricFact.getMetricNode(), metricFact.getFact());
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {

                        }
                    } else if (key instanceof RuleMetricNodeStateKey && !(key instanceof AlertNodeStateKey)) {
                        RuleMetricNodeState ruleMetricNodeState = (RuleMetricNodeState) txnE.getNode();
                        if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                            saveRuleMetricNode(ruleMetricNodeState);
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                            saveRuleMetricNode(ruleMetricNodeState);
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
                            deleteRuleMetricNode(ruleMetricNodeState.getKey());
                        }
                    } else if (key instanceof FactHr) {
                        FactHr factHr = (FactHr) txnE.getNode();
                        if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                            // System.out.println("NEW FACT Hr " +
                            // factHr.getHierarchyName() + ", " +
                            // factHr.getFact().getKey());
//							if (insertProcessed) {
//								if (isInsertProcessedWithMultipleTable) {
//									insertProcessedFactMultipleTable(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(), factHr.getHierarchyName());
//
//								} else {
//									insertProcessedFact(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(), factHr.getHierarchyName());
//								}
//							} else {
//								updateProcessedFact(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(), factHr.getHierarchyName());
//							}
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                            // System.out.println("UPDT FACT Hr " +
                            // factHr.getHierarchyName() + ", " +
                            // factHr.getFact().getKey());
//							if (insertProcessed) {
//								if (isInsertProcessedWithMultipleTable) {
//									insertProcessedFactMultipleTable(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(), factHr.getHierarchyName());
//
//								} else {
//									insertProcessedFact(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(), factHr.getHierarchyName());
//								}
//							} else {
//								updateProcessedFact(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(), factHr.getHierarchyName());
//							}
                        } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {

                        }
                    }

                }
                commit();
                break;
            } catch (DBConnectionsBusyException busyEx) {
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR, "Database connections are busy. Will NOT retry.");
                    throw busyEx;
                }
                LOGGER.log(Level.WARN, "Database connections are busy. Will retry.");
                ThreadSleep(5000);
            } catch (PersistenceStoreNotAvailableException dbne) {
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR, "Database not available, Will NOT wait for reconnect..");
                    throw dbne;
                }
                LOGGER.log(Level.ERROR, "Database not available, Waiting for reconnect..");
                connectionPool.waitForReconnect(30000, -1);
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Database reconnected. Processing will continue");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                LOGGER.log(Level.WARN, "SQLIntegrityConstraintViolationException encountered while applying transaction.", e);
                // No use of retry.
                throw e;
            } catch (SQLException ex) {
                if (ex.getErrorCode() == ORACLE_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE || ex.getErrorCode() == MSSQL_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE || ex.getErrorCode() == IBMDB2_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE1 || ex.getErrorCode() == IBMDB2_UNIQUE_CONSTRAINT_VIOLATION_ERROR_CODE2) {
                    LOGGER.log(Level.WARN, "SQL unique constraint violated while applying transaction", ex);
                    throw ex;
                }
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR, "SQLException encountered while applying transaction. Will NOT retry.", ex);
                    throw ex;
                }
                LOGGER.log(Level.ERROR, "SQLException encountered while applying transaction.", ex);

                try {
                    connectionPool.check(ex, connectionPool.getCurrentConnection());
                } catch (PersistenceStoreNotAvailableException e) {
                    LOGGER.log(Level.ERROR, "Connection check failed..Database is disconnected. Waiting for reconnect.");
                    Thread.sleep(5000);
                }
                Thread.sleep(5000);

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while applying transaction.", e);
                throw e;
            } finally {
                //DONT release connection here. Will be handled outside.
//				connectionPool.releaseCurrentConnection();
            }
        }
    }

    @Override
    public long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) {
        PreparedStatement pst = null;
        String deleteSql = null;
        int rowPurged = 0;
        try {
            deleteSql = getPurgeMetricSql(schemaName, cubeName, hierarchyName);
            Connection dbConnection = connectionPool.getSqlConnection();
            pst = dbConnection.prepareStatement(deleteSql);
            int index = 1;
            pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis() - purgeOlderThan));
            rowPurged = pst.executeUpdate();
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Number of Records purged from Metric table = %s", rowPurged);
            }
            deleteCount.getAndIncrement();
            transactionCount.getAndIncrement();
            dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while purgeMetricsForHierarchy(), deleteSql = %s", e, deleteSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
            connectionPool.releaseCurrentConnection();
        }
        return rowPurged;
    }

    @Override
    public long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan,
                                         boolean storeFacts, boolean storeProcessedFacts) {

        long totalFactsPurged = 0;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            long currentTime = System.currentTimeMillis();

            if (storeProcessedFacts) {
//				if (insertProcessed) {
//					if (isInsertProcessedWithMultipleTable) {
//						purgeProcessedFactMultiTable(schemaName, qualifier, purgeOlderThan, dbConnection, currentTime);
//					} else {
//						purgeProcessedFact(schemaName, qualifier, purgeOlderThan, dbConnection, currentTime);
//					}
//				}
            }
            if (storeFacts) {
                totalFactsPurged = purgeFact(schemaName, qualifier, purgeOlderThan, dbConnection, currentTime);
            }
            transactionCount.getAndIncrement();
            dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while purging facts: ", e);
        } finally {
            connectionPool.releaseCurrentConnection();
        }
        return totalFactsPurged;
    }

    @Override
    public Browser<RuleMetricNodeState> getScheduledActions(String schemaName, String cubeName, String hierarchyName,
                                                            long currentTimeMillis) {
        try {
            return new DBRuleMetricNodeStateBrowser(this, schemaName, cubeName, hierarchyName, currentTimeMillis);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while fetching UnProcessed Facts.", e);
        }
        return new EmptyIterator<RuleMetricNodeState>();
    }

    @Override
    public long getInsertCount() {
        return insertCount.get();
    }

    @Override
    public long getUpdateCount() {
        return updateCount.get();
    }

    @Override
    public long getDeleteCount() {
        return deleteCount.get();
    }

    @Override
    public long getTransactionCount() {
        return transactionCount.get();
    }

    @Override
    public boolean isSortingProvided() {
        return true;
    }


    protected void ThreadSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (Exception e) {

        }
    }

    protected String getInsertRuleMetricSql(RuleMetricNodeState rmNode) {
        RuleMetricNodeStateKey rmKey = rmNode.getKey();
        MetricKey mKey = rmKey.getMetricKey();
        String tableName = databaseSchemaManager.makeRuleMetricTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());
        return getInsertRuleMetricSql(rmNode, tableName, mKey);
    }

    protected String getUpdateRuleMetricSql(RuleMetricNodeState rmNode) {
        RuleMetricNodeStateKey rmKey = rmNode.getKey();
        MetricKey mKey = rmKey.getMetricKey();
        String tableName = databaseSchemaManager.makeRuleMetricTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());
        return getUpdateRuleMetricSql(rmNode, tableName, mKey);
    }

    protected String getUpdateMetricSql(MetricKey mKey) throws Exception {
        String cachingKey = mKey.getSchemaName() + "_" + mKey.getCubeName() + "_" + mKey.getDimensionHierarchyName();
        if (updateMetricSqlPerDh.containsKey(cachingKey)) {
            String sql = updateMetricSqlPerDh.get(cachingKey);
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Metric UPDATE sql for cachingKey [%s] is [%s]", cachingKey, sql);
            }
            return sql;
        }
        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());

        String tableName = databaseSchemaManager.makeMetricSchemaTableName(dh.getOwnerSchema().getName(), dh.getOwnerCube().getName(), dh.getName());

        Collection<Measurement> measurements = dh.getMeasurements();

        StringBuilder queryBldr = new StringBuilder("UPDATE " + tableName + " SET ");
        // Append Metric Name
        String prefix = "";
        for (Measurement measurement : measurements) {
            if (isToUseBlob || !measurement.getMetricFunctionDescriptor().isMultiValued()) {
                String metricName = measurement.getName();
                queryBldr.append(prefix + metricName + "=? ");
                prefix = ",";

                // queryBldr.append(prefix + metricName +
                // METRIC_CONTEXT_FIELD_SUFFIX + "=? ");
                for (FunctionParam param : measurement.getMetricFunctionDescriptor().getFunctionContexts()) {
                    queryBldr.append(prefix + measurement.getName() + "_" + param.getName() + "=?");
                }
            }
        }
        queryBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD + "=?");
        queryBldr.append("," + DBConstant.IS_PROCESSED + "=?");
        queryBldr.append(getWhereClauseForMetricSelection(dh));
        String sqlStr = queryBldr.toString();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Metric UPDATE sql for hierarchy [%s] is [%s]", dh.getName(), sqlStr);
        }
        updateMetricSqlPerDh.put(cachingKey, sqlStr);
        return sqlStr;
    }

    protected void setMetricValueToUpdateStatement(Connection con, PreparedStatement pst, MetricNode metricNode) throws Exception {

        MetricKey mKey = (MetricKey) metricNode.getKey();

        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());
        int index = 1;
        // values part
        Collection<Measurement> measurements = dh.getMeasurements();
        for (Measurement measurement : measurements) {
            Object value = null;
            Metric metric = metricNode.getMetric(measurement.getName());
            if (metric != null) {
                if (!metric.isMultiValued()) {
                    SingleValueMetric svm = (SingleValueMetric) metric;
                    value = svm.getValue();
                    DataType dataType = getStorageDataType(measurement);
                    if (LOGGER.isEnabledFor(Level.TRACE)) {
                        LOGGER.log(Level.TRACE, "Setting Metric UPDATE Parameter. Index=" + index + " Name=" + measurement.getName()
                                + " Datatype=" + dataType + " Value=" + value);
                    }

                    if (value == null && dataType != null && getSqlDataType(dataType) == Types.BOOLEAN) {
                        pst.setObject(index++, "N");
                    } else if (dataType != null && getSqlDataType(dataType) == Types.BOOLEAN) {
                        if (value instanceof String) {
                            pst.setString(index++, (Boolean.parseBoolean((String) value)) ? "Y" : "N");
                        } else {
                            pst.setString(index++, ((Boolean) value) ? "Y" : "N");
                        }

                    } else {
                    	if (value instanceof BigDecimal) {
        					BigDecimal bigDecimalValue = roundOfBigDecimal((BigDecimal) value);
        					pst.setObject(index++, bigDecimalValue);
        				} else {
        					pst.setObject(index++, value);
        				}
                    }
                } else {
                    MultiValueMetric mvm = (MultiValueMetric) metric;
                    List<Object> vals = mvm.getValues();
                    if (isToUseBlob) {
                        value = IOUtils.serialize((ArrayList) vals);
                        pst.setObject(index++, value);
                    } else {
                        saveMultiValuedMetricValue(con, mKey, vals, measurement.getName());
                    }
                }
            } else {
            	if (value instanceof BigDecimal) {
					BigDecimal bigDecimalValue = roundOfBigDecimal((BigDecimal) value);
					pst.setObject(index++, bigDecimalValue);
				} else {
					pst.setObject(index++, value);
				}
            }
            // pst.setObject(index++,
            // IOUtils.serialize(metricNode.getContext(measurement.getName())));
            RtaNodeContext context = metricNode.getContext(measurement.getName());
            MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
            for (FunctionParam param : md.getFunctionContexts()) {
                if (LOGGER.isEnabledFor(Level.TRACE)) {
                    LOGGER.log(Level.TRACE, "Setting Metric Context UPDATE Parameter. Index=" + index + " Name=" + param.getName() + " Datatype="
                            + param.getDataType() + " Value=" + (context == null ? null : context.getTupleValue(param.getName())));
                }
                if (context == null || context.getTupleValue(param.getName()) == null) {
                    pst.setObject(index++, null);
                } else {
                	Object contextValue = context.getTupleValue(param.getName());
					if (contextValue instanceof BigDecimal) {
						BigDecimal bigDecimalValue = roundOfBigDecimal((BigDecimal) contextValue);
						pst.setObject(index++, bigDecimalValue);
					} else {
						pst.setObject(index++, contextValue);
					}
                }
            }
        }

        pst.setTimestamp(index++, new Timestamp(metricNode.getLastModifiedTime()));
        pst.setString(index++, metricNode.isProcessed()==true? "Y" : "N");
        setWhereClauseParamForMetricSelection(pst, mKey, dh, index);
    }
    
    private BigDecimal roundOfBigDecimal(BigDecimal value){
		// Special Handling for BigDecimal, if scale is more than 15.
		if (value.scale() > 15) {
			// Set scale to 15.
			value = value.setScale(15, RoundingMode.HALF_UP);
		}
		return value;
	}

    protected void setMultiValuedMetricValuetoInsert(PreparedStatement pst, MetricKey mKey, Object value, int valueIndex) throws Exception {
        int pstIndex = 1;
        pst.setString(pstIndex++, mKey.toString());
        pst.setObject(pstIndex++, value);
        pst.setInt(pstIndex++, valueIndex);
        pst.setTimestamp(pstIndex++, new Timestamp(System.currentTimeMillis()));
    }

    protected int getSqlDataType(DataType dataType) {
        int sqlType = Types.NULL;
        switch (dataType) {
            case BOOLEAN:
                sqlType = Types.BOOLEAN;
                break;
            case DOUBLE:
                sqlType = Types.DOUBLE;
                break;
            case INTEGER:
                sqlType = Types.INTEGER;
                break;
            case LONG:
                sqlType = Types.NUMERIC;
                break;
            case SHORT:
                sqlType = Types.SMALLINT;
                break;
            case BYTE:
                sqlType = Types.TINYINT;
                break;
            case STRING:
                sqlType = Types.VARCHAR;
                break;
            case CLOB:
                sqlType = Types.CLOB;
                break;
        }
        return sqlType;
    }

    protected String getMetricInsertSql(MetricKey key) throws Exception {
        String cachingKey = key.getSchemaName() + "_" + key.getCubeName() + "_" + key.getDimensionHierarchyName();
        if (insertMetricSqlPerDh.containsKey(cachingKey)) {
            String sql = insertMetricSqlPerDh.get(cachingKey);
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Metric INSERT statement for cachingKey [%s] is [%s]", cachingKey, sql);
            }
            return sql;
        }

        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(key.getSchemaName()).getCube(key.getCubeName()).getDimensionHierarchy(key.getDimensionHierarchyName());

        String tableName = databaseSchemaManager.makeMetricSchemaTableName(dh.getOwnerSchema().getName(), dh.getOwnerCube().getName(), dh.getName());

        Collection<Measurement> measurements = dh.getMeasurements();

        StringBuilder fieldBldr = new StringBuilder(DBConstant.DIMENSION_LEVEL_NAME_FIELD);
        fieldBldr.append(", " + DBConstant.DIMENSION_LEVEL_FIELD);
        if(isMSSqlServer && !usePK){
        	 fieldBldr.append(", " + DBConstant.METRIC_KEY_HASH_FIELD);
        }        
        fieldBldr.append(", " + DBConstant.METRIC_KEY_FIELD);
        StringBuilder valueBldr = new StringBuilder(" ?");
        valueBldr.append(", ?");
		if (isMSSqlServer && !usePK) {
			valueBldr.append(", ?");
		}
        valueBldr.append(", ?");

        for (Dimension d : dh.getDimensions()) {
            fieldBldr.append("," + d.getName());
            valueBldr.append(", ?");
        }

        for (Measurement m : measurements) {
            String metricName = m.getName();
            if (isToUseBlob || !m.getMetricFunctionDescriptor().isMultiValued()) {
                fieldBldr.append("," + metricName);
                valueBldr.append(",? ");

                Measurement measurement = dh.getMeasurement(metricName);
                for (FunctionParam param : measurement.getMetricFunctionDescriptor().getFunctionContexts()) {
                    fieldBldr.append("," + measurement.getName() + "_" + param.getName());
                    valueBldr.append(",? ");
                }
            }
        }
        fieldBldr.append("," + DBConstant.CREATED_DATE_TIME_FIELD);
        fieldBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD);
        fieldBldr.append("," + DBConstant.IS_PROCESSED);
        valueBldr.append(",?,?,?");
        String insertSql = "INSERT INTO " + tableName + "(" + fieldBldr + ") VALUES (" + valueBldr + ")";
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Metric INSERT statement for dimension hierarchy [%s] is [%s]", dh.getName(), insertSql);
        }
        insertMetricSqlPerDh.put(cachingKey, insertSql);
        return insertSql;
    }

    protected String getMultiValuedMetricInsertSql(MetricKey key, String measurementName) throws Exception {
        String cachingKey = key.getSchemaName() + "_" + key.getCubeName() + "_" + key.getDimensionHierarchyName() + "_" + measurementName;
        if (insertMetricMultiValuedSqlPerMeasurement.containsKey(cachingKey)) {
            String sql = insertMetricMultiValuedSqlPerMeasurement.get(cachingKey);
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Metric MultiValued INSERT statement for cachingKey [%s] is [%s]", cachingKey, sql);
            }
            return sql;
        }
        StringBuilder fieldBldr = new StringBuilder(DBConstant.METRIC_KEY_FIELD);
        fieldBldr.append(", " + DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
        fieldBldr.append(", " + DBConstant.MULTIVALUED_METRIC_TABLE_INDEX_FIELD);
        fieldBldr.append(", " + DBConstant.CREATED_DATE_TIME_FIELD);

        String insertSql = "INSERT INTO " + getMultiValuedMetricTableName(key, measurementName) + "(" + fieldBldr + ") VALUES (?, ?, ?, ?)";
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Metric MultiValued INSERT statement is [%s]", insertSql);
        }
        insertMetricMultiValuedSqlPerMeasurement.put(cachingKey, insertSql);
        return insertSql;
    }

    protected String getMultiValuedMetricTableName(MetricKey key, String measurementName) {
        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(key.getSchemaName()).getCube(key.getCubeName()).getDimensionHierarchy(key.getDimensionHierarchyName());
        return databaseSchemaManager.makeMetricMultiValuedTableName(dh, measurementName);
    }

    protected void setMetricValueToInsertStatement(String insertSql, Connection con, PreparedStatement pst, MetricNode metricNode) throws Exception {

        MetricKey mKey = (MetricKey) metricNode.getKey();

        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());

        int index = 1;
        StringBuilder sb = new StringBuilder("[[");
        // pst.setString(index++, mKey.getDimensionHierarchyName());
        pst.setString(index++, mKey.getDimensionLevelName());
        sb.append(mKey.getDimensionLevelName() + " ,");


        int level = dh.getLevel(mKey.getDimensionLevelName());
        pst.setObject(index++, level);
        sb.append(level + " ,");
		if (isMSSqlServer && !usePK) {
			pst.setString(index++, HashGenerator.hash(mKey.toString()));
		}
        pst.setObject(index++, mKey.toString());
        sb.append(mKey.toString() + " ,");

        // key part
        for (Dimension dimension : dh.getDimensions()) {
            String dimensionName = dimension.getName();
            if (mKey.getDimensionValue(dimensionName) == null && getSqlDataType(dimension.getAssociatedAttribute().getDataType()) == Types.BOOLEAN) {
                pst.setObject(index++, "N");
            } else {
                pst.setObject(index++, mKey.getDimensionValue(dimensionName));
            }

            sb.append(mKey.getDimensionValue(dimensionName) + " ,");
        }

        // value part
        Collection<Measurement> measurements = dh.getMeasurements();
        for (Measurement measurement : measurements) {
            Object value = null;
            Metric metric = metricNode.getMetric(measurement.getName());
            if (metric != null) {
                if (!metric.isMultiValued()) {
                    SingleValueMetric svm = (SingleValueMetric) metric;
                    value = svm.getValue();
                    DataType dataType = getStorageDataType(measurement);
                    if (value == null && dataType != null && getSqlDataType(dataType) == Types.BOOLEAN) {
                        pst.setObject(index++, "N");
                        sb.append("N" + " ,");
                    } else if (dataType != null && getSqlDataType(dataType) == Types.BOOLEAN) {
                        pst.setString(index++, ((Boolean) value) ? "Y" : "N");
                        sb.append((((Boolean) value) ? "Y" : "N") + " ,");
                    } else {
                        pst.setObject(index++, value);
                    }
                } else {
                    MultiValueMetric mvm = (MultiValueMetric) metric;
                    List<Object> vals = mvm.getValues();
                    if (isToUseBlob) {
                        value = IOUtils.serialize((ArrayList) vals);
                        pst.setObject(index++, value);
                        sb.append("BLOB" + " ,");

                    } else {
                        saveMultiValuedMetricValue(con, mKey, vals, measurement.getName());
                    }
                }
            } else {
                DataType dataType = getStorageDataType(measurement);
                //Null for boolean values does not work
                if (value == null && dataType != null && getSqlDataType(dataType) == Types.BOOLEAN) {
                    pst.setObject(index++, "N");
                    sb.append("N" + " ,");
                } else {
                    pst.setObject(index++, value);
                    sb.append(value + " ,");
                }


            }
            // pst.setObject(index++,
            // IOUtils.serialize(metricNode.getContext(measurement.getName())));
            RtaNodeContext context = metricNode.getContext(measurement.getName());
            MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
            for (FunctionParam param : md.getFunctionContexts()) {
                if (context == null) {
                    // index++;
                    pst.setObject(index++, null);
                    sb.append(null + " ,");
                } else {
                    pst.setObject(index++, context.getTupleValue(param.getName()));
                    sb.append(context.getTupleValue(param.getName()) + " ,");
                }
            }
        }

        // create/updated times
        Timestamp ts = new Timestamp(metricNode.getCreatedTime());
        pst.setTimestamp(index++, ts);
        sb.append(ts + ", ");
        pst.setTimestamp(index++, ts);
        sb.append(ts + ", ");
        pst.setString(index++, metricNode.isProcessed()==true? "Y" : "N");
        sb.append((metricNode.isProcessed()?"Y":"N") + ", ");
        sb.append("]]");
        if(LOGGER.isEnabledFor(Level.DEBUG)) {
        	LOGGER.log(Level.DEBUG, "<<<< INSERT STATEMENT >>>> " + insertSql + " ----- " + sb.toString());
        }
    }

    protected void setRuleMetricValueToInsertStatement(String insertSql, Connection con, PreparedStatement pst, RuleMetricNodeState rmNode) throws Exception {
        int index = 1;
        RuleMetricNodeStateKey rmKey = rmNode.getKey();
        MetricKey mKey = rmKey.getMetricKey();
        if(isMSSqlServer && !usePK){
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, " RULE METRIC INSERT STATEMENT -- Hash="
								+ HashGenerator.hash(rmKey.getMetricKey().toString()) + " Key=" + rmKey.getMetricKey().toString());
			}
        	pst.setString(index++, HashGenerator.hash(rmKey.getMetricKey().toString()));
        }
        pst.setString(index++, rmKey.getMetricKey().toString());
        pst.setString(index++, rmKey.getRuleName());
        pst.setString(index++, rmKey.getActionName());
        pst.setInt(index++, rmNode.getCount());
        pst.setLong(index++, rmNode.getScheduledTime());
        pst.setLong(index++, rmNode.getLastFireTime());
        pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
        pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
        pst.setString(index++, "N");

        pst.setString(index++, mKey.getDimensionLevelName());
        // set and clear condition metric key
        MetricKey setConditionKey = rmNode.getSetConditionKey();
        pst.setString(index++, (setConditionKey != null) ? setConditionKey.toString() : "");
        MetricKey clearConditionKey = rmNode.getClearConditionKey();
        pst.setString(index++, (clearConditionKey != null) ? clearConditionKey.toString() : "");

        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName())
                .getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());

        for (Dimension dimension : dh.getDimensions()) {
            String dimensionName = dimension.getName();
            pst.setObject(index++, mKey.getDimensionValue(dimensionName));
        }

        // Metric value part
        Collection<Measurement> measurements = dh.getMeasurements();
        for (Measurement measurement : measurements) {
            Object value = null;
            Metric metric = rmNode.getMetricNode().getMetric(measurement.getName());
            if (metric == null) {
            } else if (!metric.isMultiValued()) {
                SingleValueMetric svm = (SingleValueMetric) metric;
                value = svm.getValue();
            } else {
                MultiValueMetric mvm = (MultiValueMetric) metric;
                List<Object> vals = mvm.getValues();
                value = IOUtils.serialize((ArrayList) vals);
            }
            pst.setObject(index++, value);
            // pst.setObject(index++,
            // IOUtils.serialize(metricNode.getContext(measurement.getName())));
            RtaNodeContext context = rmNode.getMetricNode().getContext(measurement.getName());
            MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
			if (context != null) {
				for (FunctionParam param : md.getFunctionContexts()) {
					if (context.getTupleValue(param.getName()) != null) {
						pst.setObject(index++, context.getTupleValue(param.getName()));
					} else {
						pst.setNull(index++, getSqlDataType(param.getDataType()));
					}
				}
			} else {
				for (FunctionParam param : md.getFunctionContexts()) {
					pst.setNull(index++, getSqlDataType(param.getDataType()));
				}
			}
        }
    }

    protected void setRuleMetricValueToUpdateStatement(Connection con, PreparedStatement pst, RuleMetricNodeState rmNode) throws Exception {
        int index = 1;
        RuleMetricNodeStateKey rmKey = rmNode.getKey();
        MetricKey mKey = rmKey.getMetricKey();

        pst.setString(index++, rmKey.getActionName());
        pst.setInt(index++, rmNode.getCount());
        pst.setLong(index++, rmNode.getScheduledTime());
        pst.setLong(index++, rmNode.getLastFireTime());
        pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
        // set and clear condition metric key
        MetricKey setConditionKey = rmNode.getSetConditionKey();
        pst.setString(index++, (setConditionKey != null) ? setConditionKey.toString() : "");
        MetricKey clearConditionKey = rmNode.getClearConditionKey();
        pst.setString(index++, (clearConditionKey != null) ? clearConditionKey.toString() : "");

        // metric value
        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());
        Collection<Measurement> measurements = dh.getMeasurements();
        for (Measurement measurement : measurements) {
            Object value = null;
            Metric metric = rmNode.getMetricNode().getMetric(measurement.getName());
            if (metric == null) {
            } else if (!metric.isMultiValued()) {
                SingleValueMetric svm = (SingleValueMetric) metric;
                value = svm.getValue();
            } else {
                MultiValueMetric mvm = (MultiValueMetric) metric;
                List<Object> vals = mvm.getValues();
                value = IOUtils.serialize((ArrayList) vals);
            }
            pst.setObject(index++, value);
            // pst.setObject(index++,
            // IOUtils.serialize(metricNode.getContext(measurement.getName())));
            RtaNodeContext context = rmNode.getMetricNode().getContext(measurement.getName());
            MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
			if (context != null) {
				for (FunctionParam param : md.getFunctionContexts()) {
					if (context.getTupleValue(param.getName()) != null) {
						pst.setObject(index++, context.getTupleValue(param.getName()));
					} else {
						pst.setNull(index++, getSqlDataType(param.getDataType()));
					}					
				}
			} else {
				for (FunctionParam param : md.getFunctionContexts()) {
					pst.setNull(index++, getSqlDataType(param.getDataType()));
				}
			}
        }

        // WHERE clause parameters.
        if(isMSSqlServer && !usePK){
        	pst.setString(index++, HashGenerator.hash(rmKey.getMetricKey().toString()));
        }
        pst.setString(index++, rmKey.getMetricKey().toString());
        pst.setString(index++, rmKey.getRuleName());
        pst.setString(index++, rmKey.getActionName());

    }

    protected DatabaseConnectionPool getConnectionpool() {
        return connectionPool;
    }

    public DBConnectionPoolStats getConnectionPoolStats() {
        int connectionPoolSize = connectionPool.getConnectionPoolSize();
        String poolState = connectionPool.getPoolState();
        int connectionsInUse = connectionPool.getConnectionsInUse();
        int availableConnections = connectionPool.getAvailableConnections();

        return new DBConnectionPoolStats(connectionPoolSize, poolState, connectionsInUse, availableConnections);
    }

    @Override
    public DBTransactionStats getTransactionStats() {
        long insertCount = getInsertCount();
        long updateCount = getUpdateCount();
        long deleteCount = getDeleteCount();
        long transactionCount = getTransactionCount();

        return new DBTransactionStats(insertCount, updateCount, deleteCount, transactionCount);
    }

    protected String getInsertProcessedFactMultipleTableSql(String schemaName, String cubeName, String hierarchyName) {
        String cacheKey = schemaName + DBConstant.SEP + cubeName + DBConstant.SEP + hierarchyName;
        if (insertProcessedFactSqlPerDh.containsKey(cacheKey)) {
            return insertProcessedFactSqlPerDh.get(cacheKey);
        }
        String tableName = databaseSchemaManager.makeProcessedFactTableName(schemaName, cubeName, hierarchyName);
        StringBuilder fieldBldr = new StringBuilder(DBConstant.FACT_KEY_FIELD + "," + DBConstant.CREATED_DATE_TIME_FIELD);
        StringBuilder valuesBldr = new StringBuilder("?, ?");
        String insertSql = "INSERT INTO " + tableName + "(" + fieldBldr + ") VALUES ( " + valuesBldr + ")";
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "save(), Fact InsertFactMultiTable Sql : %s", insertSql);
        }
        insertProcessedFactSqlPerDh.put(cacheKey, insertSql);
        return insertSql;
    }

    protected void setInsertProcessedFactParameters(PreparedStatement pst, Fact fact) throws Exception {
        int index = 1;
        pst.setString(index++, ((FactKeyImpl) fact.getKey()).getUid());
        pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
    }

    protected String getUpdateProcessedFactSql(String schemaName, String cubeName, String hierarhyName) {
        String tableName = databaseSchemaManager.makeFactTableName(schemaName);
        StringBuilder queryBldr = new StringBuilder("UPDATE " + tableName + " SET ");
        String dhColumnName = cubeName + "_" + databaseSchemaManager.getStorageSchema(schemaName, cubeName, hierarhyName);
        queryBldr.append(dhColumnName + "=?");
        queryBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD + "=?");
        queryBldr.append(" WHERE " + DBConstant.FACT_KEY_FIELD);
        return queryBldr.toString();

    }

    protected RuleMetricNodeState fetchRuleNodeFromRS(ResultSet rs, String schemaName, String cubeName, String hierarchyName) {
        RuleMetricNodeState ruleNode = null;
        try {
            if (rs == null || !rs.next()) {
                return null;
            }

            String dimLevelName = rs.getString(DBConstant.DIMENSION_LEVEL_NAME_FIELD);
            MetricKeyImpl key = (MetricKeyImpl) KeyFactory.newMetricKey(schemaName, cubeName, hierarchyName,
                    dimLevelName);
            RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
            Cube cube = schema.getCube(cubeName);
            DimensionHierarchy hierarchy = cube.getDimensionHierarchy(hierarchyName);

            int level = hierarchy.getLevel(dimLevelName);
            for (int i = 0; i <= level; i++) {
                Dimension d = hierarchy.getDimension(i);
                String dimName = d.getName();
                Object value = getDimensionValue(d, rs, dimName);
                key.addDimensionValueToKey(dimName, value);
            }
            // add null values to rest of the dimensions
            for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
                String dimName = hierarchy.getDimension(i).getName();
                key.addDimensionValueToKey(dimName, null);
            }


            String ruleName = rs.getString(DBConstant.RULE_NAME_FIELD);
            String ruleActionName = rs.getString(DBConstant.RULE_ACTION_NAME_FIELD);


            ruleNode = new RuleMetricNodeStateImpl(ruleName, ruleActionName, key);
            ruleNode.setCount(rs.getInt(DBConstant.RULE_SET_COUNT_FIELD));
            ruleNode.setLastFireTime(rs.getLong(DBConstant.RULE_LAST_FIRED_TIME_FIELD));
            ruleNode.setScheduledTime(rs.getLong(DBConstant.RULE_SCHEDULED_TIME_FIELD));

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while fetching rule metric node state from Database.", e);
        }
        return ruleNode;
    }

    protected MetricNodeImpl fetchMetricNodeFromRS(ResultSet rs, String schemaName, String cubeName, String hierarchyName, String queryStr) throws SQLException {
        try {
            if (rs == null) {
                return null;
            }

            long ts = System.currentTimeMillis();
            boolean rsNext = rs.next();
            long ts2 = System.currentTimeMillis();
            if (LOGGER_DTLS.isEnabledFor(Level.TRACE) && queryStr != null) {
                LOGGER_DTLS.log(Level.TRACE, "Time taken for query [%s], resultset.next() [%d] ms", queryStr, (ts2 - ts));
            }
            if (!rsNext) {
                return null;
            }
            RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
            Cube cube = schema.getCube(cubeName);
            DimensionHierarchy hierarchy = cube.getDimensionHierarchy(hierarchyName);
            String dimLevelName = rs.getString(DBConstant.DIMENSION_LEVEL_NAME_FIELD);
            MetricKeyImpl key = (MetricKeyImpl) KeyFactory.newMetricKey(schemaName, cubeName, hierarchyName, dimLevelName);
            int level = hierarchy.getLevel(dimLevelName);
            for (int i = 0; i <= level; i++) {
                Dimension d = hierarchy.getDimension(i);
                String dimName = d.getName();
                Object value = getDimensionValue(d, rs, dimName);
                key.addDimensionValueToKey(dimName, value);
            }
            // add null values to rest of the dimensions
            for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
                String dimName = hierarchy.getDimension(i).getName();
                key.addDimensionValueToKey(dimName, null);
            }

            MetricNodeImpl metricNodeImpl = new MetricNodeImpl(key);

            Timestamp cts = rs.getTimestamp(DBConstant.CREATED_DATE_TIME_FIELD);
            long createdTime = cts.getTime();
            Timestamp uts = rs.getTimestamp(DBConstant.UPDATED_DATE_TIME_FIELD);
            long lastModifiedTime = uts.getTime();
            
            metricNodeImpl.setProcessed(rs.getString(DBConstant.IS_PROCESSED).equalsIgnoreCase("Y"));
            metricNodeImpl.setCreatedTime(createdTime);
            metricNodeImpl.setLastModifiedTime(lastModifiedTime);
            // loop over metric descriptor
            for (Measurement measurement : hierarchy.getMeasurements()) {

                MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
                boolean isMultiValued = md.isMultiValued();
                BaseMetricImpl bmi = null;
               /* if (isToUseBlob) {
                    Object value = rs.getObject(measurement.getName());
                    if (value == null) {
                        continue;
                    }
                }*/

                if (isMultiValued) {
                    MultiValueMetricImpl mvm = new MultiValueMetricImpl();
                    ArrayList<Object> vals = null;
                    if (isToUseBlob) {
                        // stored as BLOB
                        byte[] metricVal = rs.getBytes(measurement.getName());
                        if (metricVal != null) {
                            vals = IOUtils.deserialize(metricVal);
                        }

                    } else {
                        // Stored in different table.
                        vals = getMultiValuedMetricValue(key, md, measurement.getName());
                    }
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Fetched MultiValued Metric Value = [%s]", vals);
                    }
                    mvm.setValues(vals);
                    bmi = mvm;
                } else {
                    Object metricVal;
                    if(rs.getObject(measurement.getName()) == null){
                    	metricVal = null;
                    }
                    else if (measurement.getDataType() == DataType.INTEGER) {
                        metricVal = rs.getInt(measurement.getName());
                    } else if (measurement.getDataType() == DataType.LONG) {
                        metricVal = rs.getLong(measurement.getName());
                    } else if (measurement.getDataType() == DataType.DOUBLE) {
                        metricVal = rs.getDouble(measurement.getName());
                    } else if (measurement.getDataType() == DataType.SHORT) {
                        metricVal = rs.getShort(measurement.getName());
                    } else if (measurement.getDataType() == DataType.BYTE) {
                        metricVal = rs.getByte(measurement.getName());
                    } else if (measurement.getDataType() == DataType.STRING) {
                        metricVal = rs.getString(measurement.getName());
                    } else if (measurement.getDataType() == DataType.BOOLEAN) {                    	 
                        //Bala: Boolean metrics are stored as char(1) :"Y/y" or "N/n" in the database.
                        metricVal = rs.getString(measurement.getName()).equalsIgnoreCase("Y");
                    } else {
                        metricVal = rs.getObject(measurement.getName());
                        if (metricVal instanceof Clob) {
                        	metricVal = getStringFromClob((Clob)metricVal);
                        }
                    }
                    SingleValueMetricImpl svm = new SingleValueMetricImpl();
                    svm.setValue(metricVal);
                    bmi = svm;
                }

                RtaNodeContextImpl nodeContext = new RtaNodeContextImpl();

                for (FunctionParam param : measurement.getMetricFunctionDescriptor().getFunctionContexts()) {
                    Object contextVal = null;
                    String fldNm = measurement.getName() + DBConstant.SEP + param.getName();

                    if (param.getDataType() == DataType.INTEGER) {
                        contextVal = rs.getInt(fldNm);
                    } else if (param.getDataType() == DataType.LONG) {
                        contextVal = rs.getLong(fldNm);
                    } else if (param.getDataType() == DataType.DOUBLE) {
                        contextVal = rs.getDouble(fldNm);
                    } else if (md.getMetricDataType() == DataType.SHORT) {
                        contextVal = rs.getShort(fldNm);
                    } else if (md.getMetricDataType() == DataType.BYTE) {
                        contextVal = rs.getByte(fldNm);
                    } else if (param.getDataType() == DataType.STRING) {
                        contextVal = rs.getString(fldNm);
                    } else if (param.getDataType() == DataType.BOOLEAN) {
                        contextVal = rs.getBoolean(fldNm);
                    } else {
                        contextVal = rs.getObject(fldNm);
                    }
                    nodeContext.setTuple(param.getName(), contextVal);
                }

                MetricValueDescriptorImpl mvd = new MetricValueDescriptorImpl(dimLevelName, hierarchyName, cubeName, schemaName, measurement.getName());
                bmi.setDescriptor(mvd);
                if (!"root".equals(dimLevelName)) {
                    Dimension d = hierarchy.getDimension(dimLevelName);
                    Object dimValue = getDimensionValue(d, rs, dimLevelName);
                    bmi.setDimensionValue(dimValue);
                }
                bmi.setKey(key);
                bmi.setMetricName(measurement.getName());
                bmi.setCreatedTime(createdTime);
                bmi.setLastModifiedTime(lastModifiedTime);
                metricNodeImpl.setMetric(measurement.getName(), bmi);
                metricNodeImpl.setContext(measurement.getName(), nodeContext);
            }
            return metricNodeImpl;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "A database error occurred, while getting metric node.", e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while getting metric node.", e);
            throw new IllegalStateException(e);
        }
    }
    
	private String getStringFromClob(Clob data) throws SQLException, IOException {
		Reader reader = data.getCharacterStream();
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();

		String line;
		while (null != (line = br.readLine())) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

    protected Object getDimensionValue(Dimension dim, ResultSet rs, String dimName) throws Exception {
        Object dimValue = null;
        if (dim.getAssociatedAttribute().getDataType().equals(DataType.STRING)) {
            dimValue = rs.getString(dimName);
        } else if (dim.getAssociatedAttribute().getDataType().equals(DataType.INTEGER)) {
            dimValue = rs.getInt(dimName);
        } else if (dim.getAssociatedAttribute().getDataType().equals(DataType.LONG)) {
            dimValue = rs.getLong(dimName);
        } else if (dim.getAssociatedAttribute().getDataType().equals(DataType.BOOLEAN)) {
            dimValue = rs.getBoolean(dimName);
        } else if (dim.getAssociatedAttribute().getDataType().equals(DataType.SHORT)) {
            dimValue = rs.getShort(dimName);
        } else if (dim.getAssociatedAttribute().getDataType().equals(DataType.BYTE)) {
            dimValue = rs.getByte(dimName);
        } else if (dim.getAssociatedAttribute().getDataType().equals(DataType.DOUBLE)) {
            dimValue = rs.getDouble(dimName);
        } else {
            dimValue = rs.getObject(dimName);
        }
        return dimValue;
    }

    // @Override
    // public Fact getFact(Key key) throws Exception {
    // while (true) {
    // try {
    // Fact fact = null;
    // if (key instanceof FactKeyImpl) {
    // FactKeyImpl factKey = (FactKeyImpl) key;
    // if (LOGGER.isEnabledFor(Level.DEBUG)) {
    // LOGGER.log(Level.DEBUG, "getFact(), FackKey UId: %s",
    // factKey.getUid());
    // }
    // String tableName = databaseSchemaManager
    // .getFactSchema(factKey.getSchemaName());
    // fact = getFactFromDatabase(tableName, factKey.getUid());
    //
    // }
    // return fact;
    // } catch (DBConnectionsBusyException busyEx) {
    // LOGGER.log(Level.WARN, "Database connections are busy");
    // ThreadSleep(5000);
    // } catch (DBNotAvailableException dbne) {
    // LOGGER.log(Level.ERROR,
    // "Database not available, Waiting for reconnect..");
    // connectionPool.waitForReconnect(30000, -1);
    // LOGGER.log(Level.INFO, "Database reconnected. Processing will continue");
    // } catch (SQLException ex) {
    // LOGGER.log(Level.WARN, "SQLException encountered");
    // try {
    // connectionPool.check(ex, connectionPool.getCurrentConnection());
    // } catch (DBNotAvailableException e) {
    // LOGGER.log(Level.ERROR,
    // "Database not available.. Waiting for reconnect..");
    // connectionPool.waitForReconnect(30000, -1);
    // LOGGER.log(Level.DEBUG, "Database reconnected. Retrying operation..");
    // }
    // // throw new DBException();
    // } catch (Exception e) {
    // LOGGER.log(Level.ERROR, "Other error", e);
    // throw e;
    // } finally {
    // // connectionPool.releaseCurrentConnection();
    // }
    // }
    //
    // }
    //
    // private Fact getFactFromDatabase(String tableName, String factKey)
    // throws Exception {
    // Connection dbConnection = connectionPool.getSqlConnection();
    // ResultSet rs = null;
    // PreparedStatement pst = null;
    // FactImpl fact = null;
    // pst = dbConnection.prepareStatement("SELECT * FROM " + tableName
    // + " WHERE " + KEY_FIELD + " = ?");
    // pst.setString(1, factKey);
    // rs = pst.executeQuery();
    // String ownerSchema = null;
    // if (rs.next()) {
    // fact = new FactImpl();
    // ownerSchema = rs.getString(OWNER_SCHEMA);
    // RtaSchema schema = ModelRegistry.INSTANCE
    // .getRegistryEntry(ownerSchema);
    // fact.setOwnerSchema(schema);
    // fact.setKey(new FactKeyImpl(ownerSchema, factKey));
    // for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
    // if (DatabasePersistenceService.KEY_FIELD.equalsIgnoreCase(rs
    // .getMetaData().getColumnName(i))
    // || DatabasePersistenceService.OWNER_SCHEMA
    // .equalsIgnoreCase(rs.getMetaData()
    // .getColumnName(i))) {
    // continue;
    // }
    // String attrName = rs.getMetaData().getColumnName(i)
    // .toLowerCase();
    // Object value = rs.getObject(i);
    // if (value != null) {
    // fact.setAttribute(attrName, value);
    // }
    // }
    // }
    //
    // return fact;
    //
    // }

    protected Fact fetchFactFromRS(ResultSet rs, RtaSchema schema) {
        return fetchFactFromRS(rs, schema, false);
    }

    /**
     * To fetch Fact from ResultSet
     *
     * @param rs
     * @param schema
     * @param isRecovery - whether its a recovery fact or not.
     * @return Fact
     */
    protected Fact fetchFactFromRS(ResultSet rs, RtaSchema schema, boolean isRecovery) {
        FactImpl fact = null;
        try {
            if (rs == null || !rs.next()) {
                return null;
            }
            if (isRecovery) {
                fact = new RecoveredFactImpl();
            } else {
                fact = new FactImpl();
            }
            fact.setOwnerSchema(schema);
            String factKey = rs.getString(DBConstant.FACT_KEY_FIELD);
            fact.setKey(new FactKeyImpl(schema.getName(), factKey));
            setFactAttributes(schema, fact, rs);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while fetching Fact from Database.", e);
        }
        return fact;

    }

    protected void releaseResources(ResultSet rs, Statement st) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "An error occurred while closing ResultSet.", e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "An error occurred while closing Sql Statement.", e);
            }
        }
    }

    protected void setFactAttributes(RtaSchema schema, FactImpl fact, ResultSet rs) throws Exception {
        Map<String, Object> attributesMap = new LinkedHashMap<String, Object>(schema.getAttributes().size());
        for (Attribute attr : schema.getAttributes()) {
            DataType datatype = attr.getDataType();
            Object value;
            if (datatype.equals(DataType.INTEGER)) {
                value = rs.getInt(attr.getName());
            } else if (datatype.equals(DataType.STRING)) {
                value = rs.getString(attr.getName());
            } else if (datatype.equals(DataType.LONG)) {
                value = rs.getLong(attr.getName());
            } else if (datatype.equals(DataType.SHORT)) {
                value = rs.getShort(attr.getName());
            } else if (datatype.equals(DataType.BYTE)) {
                value = rs.getByte(attr.getName());
            } else if (datatype.equals(DataType.DOUBLE)) {
                value = rs.getDouble(attr.getName());
            } else if (datatype.equals(DataType.BOOLEAN)) {
            	try{
            		value = rs.getBoolean(attr.getName());
            	}
            	catch(Exception e){
            		String s = (String)(rs.getObject(attr.getName()));
            		value = s.equalsIgnoreCase("N")?false : true;
            	}
            } else {
                value = rs.getObject(attr.getName());
            }
            if (value != null) {
                attributesMap.put(attr.getName(), value);
            }
        }
        fact.setAttributes(attributesMap);
    }

    protected void setInsertFactStatementValue(PreparedStatement pst, Fact fact, String insertSql) throws SQLException {
        int indexOfValues = insertSql.lastIndexOf("VALUES");
        StringBuilder bldr = new StringBuilder(insertSql.substring(0, indexOfValues));
        bldr.append(" VALUES ( ");
        RtaSchema schema = fact.getOwnerSchema();
        int index = 1;
        String keyValue = ((FactKeyImpl) fact.getKey()).getUid();
        pst.setString(index++, keyValue);
        bldr.append(keyValue).append(", ");
        pst.setString(index++, schema.getName());
        bldr.append(schema.getName()).append(", ");
        Iterator<Attribute> itr = schema.getAttributes().iterator();
        while (itr.hasNext()) {
            Attribute attr = itr.next();
            Object attrValue = fact.getAttribute(attr.getName());
            if (attrValue == null) {
                //Bala: setNull does not work with booleans apparantly, hence if a boolean attrib is null, set it to false!
                if (getSqlDataType(attr.getDataType()) == Types.BOOLEAN) {
                    pst.setString(index++, "N");
                    bldr.append("N").append(", ");
                } else {
                    pst.setNull(index++, getSqlDataType(attr.getDataType()));
                    bldr.append("null").append(", ");
                }
            } else if (attrValue instanceof Double) {
                pst.setDouble(index++, (Double) attrValue);
                bldr.append(attrValue.toString()).append(", ");
            } else if (attrValue instanceof Integer) {
                pst.setInt(index++, (Integer) attrValue);
                bldr.append(attrValue.toString()).append(", ");
            } else if (attrValue instanceof String) {
                pst.setString(index++, (String) attrValue);
                bldr.append(attrValue.toString()).append(", ");
            } else if (attrValue instanceof Long) {
                pst.setLong(index++, (Long) attrValue);
                bldr.append(attrValue.toString()).append(", ");
            } else if (attrValue instanceof Short) {
                pst.setShort(index++, (Short) attrValue);
                bldr.append(attrValue.toString()).append(", ");
            } else if (attrValue instanceof Byte) {
                pst.setByte(index++, (Byte) attrValue);
                bldr.append(attrValue.toString()).append(", ");
            } else if (attrValue instanceof Boolean) {
                pst.setString(index++, ((Boolean) attrValue) ? "Y" : "N");
                bldr.append(((Boolean) attrValue) ? "Y" : "N").append(", ");
            }
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        pst.setTimestamp(index++, ts);
        bldr.append(ts.toString()).append(", ");
        pst.setTimestamp(index++, ts);
        bldr.append(ts.toString()).append(") ");
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, bldr.toString());
        }
    }

    protected String getInsertFactSql(RtaSchema schema) throws Exception {
        String cacheKey = schema.getName();
        if (insertFactSqlPerSchema.containsKey(cacheKey)) {
            return insertFactSqlPerSchema.get(cacheKey);
        }

        String tableName = databaseSchemaManager.makeFactTableName(schema.getName());
        StringBuilder fieldBldr = new StringBuilder(DBConstant.FACT_KEY_FIELD + "," + DBConstant.OWNER_SCHEMA_FIELD);
        StringBuilder valuesBldr = new StringBuilder("?, ?");
        Iterator<Attribute> itr = schema.getAttributes().iterator();
        while (itr.hasNext()) {
            fieldBldr.append("," + itr.next().getName());
            valuesBldr.append(",?");
        }
        fieldBldr.append("," + DBConstant.CREATED_DATE_TIME_FIELD);
        fieldBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD);
        valuesBldr.append(",?,?");
        String insertSql = "INSERT INTO " + tableName + "(" + fieldBldr + ") VALUES ( " + valuesBldr + ")";
        //		if (LOGGER.isEnabledFor(Level.DEBUG)) {
        //			LOGGER.log(Level.DEBUG, "save(), Fact InsertFactSql : %s", insertSql);
        //		}
        insertFactSqlPerSchema.put(cacheKey, insertSql);
        return insertSql;
    }

    protected String getInsertProcessedFactSql(String schemaName) throws Exception {

        String tableName = databaseSchemaManager.makeProcessedFactTableName(schemaName);
        StringBuilder fieldBldr = new StringBuilder(DBConstant.FACT_KEY_FIELD + "," + DBConstant.CUBE_FIELD + "," + DBConstant.DIMHR_FIELD + "," + DBConstant.CREATED_DATE_TIME_FIELD);
        StringBuilder valuesBldr = new StringBuilder("?, ?, ? , ?");
        String insertSql = "INSERT INTO " + tableName + "(" + fieldBldr + ") VALUES ( " + valuesBldr + ")";
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "save(), Fact InsertFactSql : %s", insertSql);
        }
        return insertSql;
    }

    protected void setInsertProcessedFactStatementValue(PreparedStatement pst, FactHr factHr) throws SQLException {
        int index = 1;
        pst.setString(index++, ((FactKeyImpl) factHr.getFact().getKey()).getUid());
        pst.setString(index++, factHr.getCubeName());
        pst.setString(index++, factHr.getHierarchyName());
        pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
    }

    protected DatabaseSchemaManager getSchemaManager() {
        return databaseSchemaManager;
    }

    protected int getQueryFetchSize() {
        return queryFetchSize;
    }

    private void insertProcessedFact(Fact fact, String schemaName, String cubeName, String hierarchyName) throws Exception {
        String sql = getInsertProcessedFactSql(schemaName);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "insertProcessedFact(), SchemaName: %s preparedSQL: %s FactKeyUid : %s ", schemaName, sql, ((FactKeyImpl) fact.getKey()).getUid());
        }
        PreparedStatement pst = null;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Using connection [%s]", dbConnection);
            }
            pst = dbConnection.prepareStatement(sql);
            int index = 1;
            pst.setString(index++, ((FactKeyImpl) fact.getKey()).getUid());
            pst.setString(index++, cubeName);
            pst.setString(index++, hierarchyName);
            pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            insertCount.getAndIncrement();

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while inserting ProcessedFact.", e);
            throw e;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }
    }

    private void insertProcessedFactMultipleTable(Fact fact, String schemaName, String cubeName, String hierarchyName) throws Exception {
        String sql = getInsertProcessedFactMultipleTableSql(schemaName, cubeName, hierarchyName);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "insertProcessedFactMultipleTable(), SchemaName: %s preparedSQL: %s FactKeyUid : %s ", schemaName, sql, ((FactKeyImpl) fact.getKey()).getUid());
        }
        PreparedStatement pst = null;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Using connection [%s]", dbConnection);
            }
            pst = dbConnection.prepareStatement(sql);
            setInsertProcessedFactParameters(pst, fact);
            pst.executeUpdate();
            insertCount.getAndIncrement();

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while inserting ProcessedFact per dimension hierarchy. SQL= %s", e, sql);
            throw e;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }
    }

    private void insertMetricNode(MetricNode metricNode, String tableName) throws Exception {
        MetricKey mKey = (MetricKey) metricNode.getKey();
        PreparedStatement pst = null;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            String insertSql = getMetricInsertSql(mKey);
            pst = dbConnection.prepareStatement(insertSql);
            setMetricValueToInsertStatement(insertSql, dbConnection, pst, metricNode);
            pst.executeUpdate();
            insertCount.getAndIncrement();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while saving(insert) Metric Node.", e);
            throw e;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }

    }

    /**
     * To check whether the Database schema created is using BLOB datatype to
     * store multi-valued metric values.
     *
     * @param schema
     * @return
     */
    private boolean isDbUsingBlob(RtaSchema schema, Cube cube, DimensionHierarchy dh, Measurement measurement) {
        String metricTableName = databaseSchemaManager.makeMetricSchemaTableName(schema, cube, dh);
        String sql = "SELECT " + measurement.getName() + " FROM " + metricTableName;
        Statement stmt = null;
        try {
            Connection connection = connectionPool.getSqlConnection();
            stmt = connection.createStatement();
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "isDbUsingBlob(), SchemaName=[%s] Executing SQL=[%s]", schema.getName(), sql);
            }
            stmt.execute(sql);
            // Statement executed successfully means Blob field exists.
            return true;
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "isDbUsingBlob(), An exception encountered.(ONLY DEBUG)", e);
            }
            // don't do anything just return false.
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            connectionPool.releaseCurrentConnection();
        }
        return false;
    }

    private void registerMBean(Properties config) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(config);
        ObjectName name = new ObjectName(mbeanPrefix + ".persistence:type=DatabaseService");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(this, name);
        }
    }

    private String getSelectMetricSql(MetricKey mKey, DimensionHierarchy dh) throws Exception {
        String cacheKey = mKey.getSchemaName() + DBConstant.SEP + mKey.getCubeName() + DBConstant.SEP + mKey.getDimensionHierarchyName();
        if (selectMetricSqlPerDh.containsKey(cacheKey)) {
            return selectMetricSqlPerDh.get(cacheKey);
        }

        String tableName = databaseSchemaManager.makeMetricSchemaTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());

        StringBuilder queryBldr = new StringBuilder("SELECT * FROM ");
        queryBldr.append(tableName);
        queryBldr.append(getWhereClauseForMetricSelection(dh));
        String selectSql = queryBldr.toString();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Invoked getSelectMetricSql(), Key Schema: %s \n" + "Prepared SqlQuery: %s", mKey.getSchemaName(), selectSql);
        }
        selectMetricSqlPerDh.put(cacheKey, selectSql);
        return selectSql;
    }

    private ArrayList<Object> getMultiValuedMetricValue(MetricKeyImpl key, MetricFunctionDescriptor md, String measurementName) throws Exception {
        // Default order by Ascending order.
        String selectSql = " SELECT " + DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD + " FROM "
                + getMultiValuedMetricTableName(key, measurementName) + " WHERE " + DBConstant.METRIC_KEY_FIELD + " =? ORDER BY "
                + DBConstant.MULTIVALUED_METRIC_TABLE_INDEX_FIELD;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<Object> values = null;
        try {
            Connection connection = connectionPool.getSqlConnection();
            pst = connection.prepareStatement(selectSql);
            pst.setString(1, key.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                if (values == null) {
                    values = new ArrayList<Object>();
                }
                Object value;
                if (md.getMetricDataType() == DataType.INTEGER) {
                    value = rs.getInt(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else if (md.getMetricDataType() == DataType.LONG) {
                    value = rs.getLong(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else if (md.getMetricDataType() == DataType.DOUBLE) {
                    value = rs.getDouble(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else if (md.getMetricDataType() == DataType.SHORT) {
                    value = rs.getShort(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else if (md.getMetricDataType() == DataType.BYTE) {
                    value = rs.getByte(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else if (md.getMetricDataType() == DataType.STRING) {
                    value = rs.getString(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else if (md.getMetricDataType() == DataType.BOOLEAN) {
                    value = rs.getBoolean(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                } else {
                    value = rs.getObject(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD);
                }
                values.add(value);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            connectionPool.releaseCurrentConnection();
        }

        return values;
    }


    // @Override
    // public Fact getFact(Key key) throws Exception {
    // while (true) {
    // try {
    // Fact fact = null;
    // if (key instanceof FactKeyImpl) {
    // FactKeyImpl factKey = (FactKeyImpl) key;
    // if (LOGGER.isEnabledFor(Level.DEBUG)) {
    // LOGGER.log(Level.DEBUG, "getFact(), FackKey UId: %s",
    // factKey.getUid());
    // }
    // String tableName = databaseSchemaManager
    // .getFactSchema(factKey.getSchemaName());
    // fact = getFactFromDatabase(tableName, factKey.getUid());
    //
    // }
    // return fact;
    // } catch (DBConnectionsBusyException busyEx) {
    // LOGGER.log(Level.WARN, "Database connections are busy");
    // ThreadSleep(5000);
    // } catch (DBNotAvailableException dbne) {
    // LOGGER.log(Level.ERROR,
    // "Database not available, Waiting for reconnect..");
    // connectionPool.waitForReconnect(30000, -1);
    // LOGGER.log(Level.INFO, "Database reconnected. Processing will continue");
    // } catch (SQLException ex) {
    // LOGGER.log(Level.WARN, "SQLException encountered");
    // try {
    // connectionPool.check(ex, connectionPool.getCurrentConnection());
    // } catch (DBNotAvailableException e) {
    // LOGGER.log(Level.ERROR,
    // "Database not available.. Waiting for reconnect..");
    // connectionPool.waitForReconnect(30000, -1);
    // LOGGER.log(Level.DEBUG, "Database reconnected. Retrying operation..");
    // }
    // // throw new DBException();
    // } catch (Exception e) {
    // LOGGER.log(Level.ERROR, "Other error", e);
    // throw e;
    // } finally {
    // // connectionPool.releaseCurrentConnection();
    // }
    // }
    //
    // }
    //
    // private Fact getFactFromDatabase(String tableName, String factKey)
    // throws Exception {
    // Connection dbConnection = connectionPool.getSqlConnection();
    // ResultSet rs = null;
    // PreparedStatement pst = null;
    // FactImpl fact = null;
    // pst = dbConnection.prepareStatement("SELECT * FROM " + tableName
    // + " WHERE " + KEY_FIELD + " = ?");
    // pst.setString(1, factKey);
    // rs = pst.executeQuery();
    // String ownerSchema = null;
    // if (rs.next()) {
    // fact = new FactImpl();
    // ownerSchema = rs.getString(OWNER_SCHEMA);
    // RtaSchema schema = ModelRegistry.INSTANCE
    // .getRegistryEntry(ownerSchema);
    // fact.setOwnerSchema(schema);
    // fact.setKey(new FactKeyImpl(ownerSchema, factKey));
    // for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
    // if (DatabasePersistenceService.KEY_FIELD.equalsIgnoreCase(rs
    // .getMetaData().getColumnName(i))
    // || DatabasePersistenceService.OWNER_SCHEMA
    // .equalsIgnoreCase(rs.getMetaData()
    // .getColumnName(i))) {
    // continue;
    // }
    // String attrName = rs.getMetaData().getColumnName(i)
    // .toLowerCase();
    // Object value = rs.getObject(i);
    // if (value != null) {
    // fact.setAttribute(attrName, value);
    // }
    // }
    // }
    //
    // return fact;
    //
    // }

    // called from inside applyTransaction
    private void updateMetricNode(MetricNode metricNode, String tableName) throws Exception {
        MetricKey mKey = (MetricKey) metricNode.getKey();
        PreparedStatement pst = null;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }
            String updateSql = getUpdateMetricSql(mKey);
            pst = dbConnection.prepareStatement(updateSql);
            setMetricValueToUpdateStatement(dbConnection, pst, metricNode);
            pst.executeUpdate();
            updateCount.getAndIncrement();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while saving(update) Metric Node.", e);
            throw e;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }
    }

    private String getWhereClauseForMetricSelection(DimensionHierarchy dh) {

        StringBuilder queryBldr = new StringBuilder(" WHERE ");
        if (usePK) {
            queryBldr.append(DBConstant.METRIC_KEY_FIELD + "=?");
        } else {
			if (isMSSqlServer) {
				queryBldr.append(DBConstant.METRIC_KEY_HASH_FIELD + "=? AND " + DBConstant.METRIC_KEY_FIELD + "=? ");
			} else {
				queryBldr.append(DBConstant.DIMENSION_LEVEL_NAME_FIELD + "=? ");
				// Append Dimensions Names
				for (Dimension d : dh.getDimensions()) {
					String dimensionName = d.getName();
					String handleNulls = String.format(" ((%s=?) OR ((? IS NULL) AND (%s IS NULL))) ", dimensionName, dimensionName);
					queryBldr.append(" AND " + handleNulls);
				}
			}
        }
        return queryBldr.toString();
    }

    private void setWhereClauseParamForMetricSelection(PreparedStatement pst, MetricKey mKey, DimensionHierarchy dh, int index) throws Exception {
        if (usePK) {
            pst.setString(index++, mKey.toString());
        } else {        	
        	if(isMSSqlServer){
        		pst.setString(index++, HashGenerator.hash(mKey.toString()));
        		pst.setString(index++, mKey.toString());
			} else {

				pst.setString(index++, mKey.getDimensionLevelName());
				// key part.
				for (Dimension dimension : dh.getDimensions()) {
					String dimensionName = dimension.getName();
					DataType dt = dimension.getAssociatedAttribute().getDataType();
					Object value = mKey.getDimensionValue(dimensionName);
					if (LOGGER.isEnabledFor(Level.TRACE)) {
						LOGGER.log(Level.TRACE, "Setting Metric Where clause Parameter. Index=" + index + " Name="
								+ dimensionName + " Datatype=" + dt + " Value=" + value);
					}
					if (value == null) {
						pst.setNull(index++, getSqlDataType(dt));
						pst.setNull(index++, getSqlDataType(dt));
					} else {
						if (DataType.STRING.equals(dt)) {
							pst.setString(index++, (String) value);
							pst.setString(index++, (String) value);

						} else if (DataType.LONG.equals(dt)) {
							pst.setLong(index++, (Long) value);
							pst.setLong(index++, (Long) value);

						} else if (DataType.DOUBLE.equals(dt)) {
							pst.setDouble(index++, (Double) value);
							pst.setDouble(index++, (Double) value);

						} else if (DataType.INTEGER.equals(dt)) {
							pst.setInt(index++, (Integer) value);
							pst.setInt(index++, (Integer) value);
						} else {
							pst.setObject(index++, value);
							pst.setObject(index++, value);
						}
					}
				}
			}
        }
    }

    private void saveMultiValuedMetricValue(Connection con, MetricKey key, List<Object> values, String measurementName) throws Exception {
        PreparedStatement stmt = null;
        PreparedStatement deleteStatement = null;
        try {
            // Delete all previously saved values.
            String deleteSql = "DELETE FROM " + getMultiValuedMetricTableName(key, measurementName) + " WHERE " + DBConstant.METRIC_KEY_FIELD + " = ?";
            deleteStatement = con.prepareStatement(deleteSql);
            deleteStatement.setString(1, key.toString());
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "saveMultiValuedMetricValue(), DELETE multi-valued values, SQL = [%s] with MetricKey= [%s]", deleteSql, key);
            }
            deleteStatement.execute();
            // Now insert new values.
            String insertSql = getMultiValuedMetricInsertSql(key, measurementName);
            stmt = con.prepareStatement(insertSql);
            int index = 1;
            for (Object value : values) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "saveMultiValuedMetricValue(), Inserting key=[%s] value=[%s] index=[%d]", key, value, index);
                }
                setMultiValuedMetricValuetoInsert(stmt, key, value, index++);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private DataType getStorageDataType(MetadataElement metaElem) {
        String dataType = metaElem.getProperty(ModelSerializationConstants.ATTR_STORAGE_DATATYPE_NAME);
        if (dataType != null && dataType.length() != 0) {
            return DataType.valueOf(dataType);
        } else if (metaElem instanceof Attribute) {
            return ((Attribute) metaElem).getDataType();
        }
        return null;
    }


    private String getDeleteMetricSql(MetricKey mKey, DimensionHierarchy dh) {
        String cachingKey = mKey.getSchemaName() + "_" + mKey.getCubeName() + "_" + mKey.getDimensionHierarchyName();
        if (deleteMetricSqlPerDh.containsKey(cachingKey)) {
            return deleteMetricSqlPerDh.get(cachingKey);
        }
        StringBuilder sqlBldr = new StringBuilder("DELETE FROM ");
        sqlBldr.append(databaseSchemaManager.makeMetricSchemaTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName()));
        sqlBldr.append(getWhereClauseForMetricSelection(dh));
        String sqlStr = sqlBldr.toString();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Metric DELETE sql for hierarchy [%s] is [%s]", dh.getName(), sqlStr);
        }
        deleteMetricSqlPerDh.put(cachingKey, sqlStr);
        return sqlStr;
    }

    private void setMetricValueToDeleteStatement(PreparedStatement pst, MetricKey mKey, DimensionHierarchy dh) throws Exception {
        setWhereClauseParamForMetricSelection(pst, mKey, dh, 1);
    }

    private void deleteFromRuleMetricNodeTable(String ruleName) throws Exception {
        PreparedStatement pst = null;

        Connection dbConnection = connectionPool.getSqlConnection();
        if (dbConnection == null) {
            throw new RuntimeException("Cannot get Connection from ThreadLocal.");
        }

        for (RtaSchema schema : ServiceProviderManager.getInstance().getAdminService().getAllSchemas()) {
            for (Cube cube : schema.getCubes()) {
                for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                    String deleteSql = getSqlForRuleStateDelete(schema, cube, dh);
                    pst = dbConnection.prepareStatement(deleteSql);
                    pst.setString(1, ruleName);
                    int rowsDeleted = pst.executeUpdate();
                    deleteCount.getAndAdd((long) rowsDeleted);
                }
            }
        }
    }

    private String getSqlForRuleStateDelete(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
        StringBuilder sqlBldr = new StringBuilder("DELETE FROM ");
        sqlBldr.append(databaseSchemaManager.makeRuleMetricTableName(schema.getName(), cube.getName(), dh.getName()));
        sqlBldr.append(" WHERE " + DBConstant.RULE_NAME_FIELD + "=?");
        String sql = sqlBldr.toString();
        return sql;
    }

    private void saveMetricFactToDB(MetricNode metricNode, Fact fact) throws Exception {
        MetricKey mKey = (MetricKey) metricNode.getKey();
        PreparedStatement pst = null;
        String insertSql = null;
        try {
            Connection dbConnection = connectionPool.getSqlConnection();
            if (dbConnection == null) {
                throw new RuntimeException("Cannot get Connection from ThreadLocal.");
            }

            insertSql = getInsertMetricFactSql(metricNode);
            pst = dbConnection.prepareStatement(insertSql);
            int index = 1;

            pst.setString(index++, ((FactKeyImpl) fact.getKey()).getUid());
            if (usePK) {
                pst.setString(index++, mKey.toString());
            } else {
                pst.setString(index++, mKey.getDimensionLevelName());
                for (String dimensionName : mKey.getDimensionNames()) {
                    pst.setObject(index++, mKey.getDimensionValue(dimensionName));
                }
            }
            pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            insertCount.getAndIncrement();

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while adding MetricFact, insertMetricFactSql : %s ", e, insertSql);
            throw e;
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }

    private String getInsertMetricFactSql(MetricNode metricNode) throws Exception {
        MetricKey mKey = (MetricKey) metricNode.getKey();
        String cachekey = mKey.getSchemaName() + DBConstant.SEP + mKey.getCubeName() + DBConstant.SEP + mKey.getDimensionHierarchyName();
        if (insertMetricFactSqlPerDh.containsKey(cachekey)) {
            return insertMetricFactSqlPerDh.get(cachekey);
        }
        String tableName = databaseSchemaManager.makeMetricFactTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName());
        StringBuilder fieldBldr = new StringBuilder();
        StringBuilder valueBldr = new StringBuilder();
        fieldBldr.append(DBConstant.FACT_TABLE_PREFIX + DBConstant.FACT_KEY_FIELD + ",");
        valueBldr.append("?,");
        if (usePK) {
            fieldBldr.append(DBConstant.METRIC_TABLE_PREFIX + DBConstant.METRIC_KEY_FIELD);
            valueBldr.append("?");
        } else {
            fieldBldr.append(DBConstant.DIMENSION_LEVEL_NAME_FIELD);
            valueBldr.append("?");
            Iterator<String> itr = mKey.getDimensionNames().iterator();
            while (itr.hasNext()) {
                fieldBldr.append("," + itr.next());
                valueBldr.append(", ?");
            }
        }
        fieldBldr.append("," + DBConstant.CREATED_DATE_TIME_FIELD);
        fieldBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD);
        valueBldr.append(",?,?");
        String insertSql = "INSERT INTO " + tableName + "(" + fieldBldr.toString() + ") VALUES (" + valueBldr.toString() + ")";
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "getInsertMetricFactSql() insertMetricFactSql : %s", insertSql);
        }
        insertMetricFactSqlPerDh.put(cachekey, insertSql);
        return insertSql;

    }

    private String getDeleteFactSql(RtaSchema schema) {
        String cacheKey = schema.getName();
        if (deleteFactSqlPerSchema.containsKey(cacheKey)) {
            return deleteFactSqlPerSchema.get(cacheKey);
        }
        StringBuilder sqlbldr = new StringBuilder("DELETE FROM ");
        sqlbldr.append(databaseSchemaManager.makeFactTableName(schema.getName()));
        sqlbldr.append(" WHERE " + DBConstant.FACT_KEY_FIELD + "=?");
        String sql = sqlbldr.toString();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "delete(), deleteFactSql : %s", sql);
        }
        deleteFactSqlPerSchema.put(cacheKey, sql);
        return sql;
    }

    private String getSelectRuleMetricSql(MetricKey key) throws Exception {
        String cachekey = key.getSchemaName() + DBConstant.SEP + key.getCubeName() + DBConstant.SEP + key.getDimensionHierarchyName();
        if (selectRuleMetricSqlPerDh.containsKey(cachekey)) {
            return selectRuleMetricSqlPerDh.get(cachekey);
        }
        String tableName = databaseSchemaManager.makeRuleMetricTableName(key.getSchemaName(), key.getCubeName(), key.getDimensionHierarchyName());
        StringBuilder queryBldr = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        if(isMSSqlServer && !usePK){
        	queryBldr.append(DBConstant.RULE_METRIC_KEY_HASH_FIELD + " = ? AND ");
        }
        queryBldr.append(DBConstant.METRIC_KEY_FIELD + " = ? AND ");
        queryBldr.append(DBConstant.RULE_NAME_FIELD + " = ? AND ");
        queryBldr.append(DBConstant.RULE_ACTION_NAME_FIELD + " = ? ");
        selectRuleMetricSqlPerDh.put(cachekey, queryBldr.toString());
        return queryBldr.toString();
    }

    private void insertRuleMetricNode(RuleMetricNodeState rmNode, String tableName, MetricKey mKey) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "insertRuleMetricNode(), for RuleName: %s RuleMetricKey: %s Action: %s", rmNode.getKey()
                    .getRuleName(), mKey.toString(), rmNode.getKey().getActionName());
        }
        PreparedStatement pst = null;
        String insertRuleMetricSql = null;
        try {
            insertRuleMetricSql = getInsertRuleMetricSql(rmNode, tableName, mKey);
            Connection dbConnection = connectionPool.getSqlConnection();
            pst = dbConnection.prepareStatement(insertRuleMetricSql);
            setRuleMetricValueToInsertStatement(insertRuleMetricSql, dbConnection, pst, rmNode);
            pst.executeUpdate();
            insertCount.getAndIncrement();

            // dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while inserting Rule Metric Node, insertRuleMetricSql= %s", e,
                    insertRuleMetricSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // connectionPool.releaseCurrentConnection();
        }
    }


    private String getInsertRuleMetricSql(RuleMetricNodeState rmNode, String tableName, MetricKey mKey) {
        // String cachekey = mKey.getSchemaName() + DBConstant.SEP +
        // mKey.getCubeName() + DBConstant.SEP +
        // mKey.getDimensionHierarchyName();
        // if (insertRuleMetricSqlPerDh.containsKey(cachekey)) {
        // return insertRuleMetricSqlPerDh.get(cachekey);
        // }
        // StringBuilder queryBldr = new StringBuilder("INSERT INTO " +
        // tableName + " (");
        // queryBldr.append(DBConstant.KEY_FIELD);
        // queryBldr.append("," + DBConstant.RULE_NAME_FIELD);
        // queryBldr.append("," + DBConstant.RULE_ACTION_NAME_FIELD);
        // queryBldr.append("," + DBConstant.RULE_SET_COUNT_FIELD);
        // queryBldr.append("," + DBConstant.RULE_SCHEDULED_TIME_FIELD);
        // queryBldr.append("," + DBConstant.RULE_LAST_FIRED_TIME_FIELD);
        // queryBldr.append("," + DBConstant.CREATED_DATE_TIME_FIELD);
        // queryBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD);
        // queryBldr.append(" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        // String sql = queryBldr.toString();
        // insertRuleMetricSqlPerDh.put(cachekey, sql);
        // if (LOGGER.isEnabledFor(Level.DEBUG)) {
        // LOGGER.log(Level.DEBUG, "insertRuleMetricNode(), SqlQuery : %s",
        // sql);
        // }
        // return sql;

        String cachekey = mKey.getSchemaName() + DBConstant.SEP + mKey.getCubeName() + DBConstant.SEP
                + mKey.getDimensionHierarchyName();
        if (insertRuleMetricSqlPerDh.containsKey(cachekey)) {
            return insertRuleMetricSqlPerDh.get(cachekey);
        }
        StringBuilder fieldBldr = new StringBuilder("INSERT INTO " + tableName + " (");
        if(isMSSqlServer && !usePK){
			fieldBldr.append(DBConstant.RULE_METRIC_KEY_HASH_FIELD + ",");
		}
        fieldBldr.append(DBConstant.METRIC_KEY_FIELD);
        fieldBldr.append("," + DBConstant.RULE_NAME_FIELD);
        fieldBldr.append("," + DBConstant.RULE_ACTION_NAME_FIELD);
        fieldBldr.append("," + DBConstant.RULE_SET_COUNT_FIELD);
        fieldBldr.append("," + DBConstant.RULE_SCHEDULED_TIME_FIELD);
        fieldBldr.append("," + DBConstant.RULE_LAST_FIRED_TIME_FIELD);
        fieldBldr.append("," + DBConstant.CREATED_DATE_TIME_FIELD);
        fieldBldr.append("," + DBConstant.UPDATED_DATE_TIME_FIELD);
        fieldBldr.append("," + DBConstant.IS_PROCESSED);
        fieldBldr.append("," + DBConstant.DIMENSION_LEVEL_NAME_FIELD);
        fieldBldr.append("," + DBConstant.RULE_SET_CONDITION_KEY_FILED);
        fieldBldr.append("," + DBConstant.RULE_CLEAR_CONDITION_KEY_FILED);
        StringBuilder valueBldr = new StringBuilder("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
        if(isMSSqlServer && !usePK){
        	valueBldr.append(", ?");
    	}

        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());
        for (Dimension d : dh.getDimensions()) {
            fieldBldr.append("," + d.getName());
            valueBldr.append(", ?");
        }

        Collection<Measurement> measurements = dh.getMeasurements();
        for (Measurement m : measurements) {
            String metricName = m.getName();
            fieldBldr.append("," + metricName);
            valueBldr.append(",? ");
            Measurement measurement = dh.getMeasurement(metricName);
            for (FunctionParam param : measurement.getMetricFunctionDescriptor().getFunctionContexts()) {
                fieldBldr.append("," + measurement.getName() + "_" + param.getName());
                valueBldr.append(",? ");
            }
        }
        fieldBldr.append(" ) VALUES (" + valueBldr.toString() + ")");
        String sql = fieldBldr.toString();
        insertRuleMetricSqlPerDh.put(cachekey, sql);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "insertRuleMetricNode(), SqlQuery : %s", sql);
        }
        return sql;
    }


    private void updateRuleMetricNode(RuleMetricNodeState rmNode, String tableName, MetricKey mKey) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "updateRuleMetricNode(), for RuleName: %s Action: %s RuleMetricKey: %s", rmNode
                    .getKey().getRuleName(), rmNode.getKey().getActionName(), mKey.toString());
        }
        PreparedStatement pst = null;
        String updateRuleMetricSql = null;
        try {
            updateRuleMetricSql = getUpdateRuleMetricSql(rmNode, tableName, mKey);
            Connection dbConnection = connectionPool.getSqlConnection();
            pst = dbConnection.prepareStatement(updateRuleMetricSql);

            setRuleMetricValueToUpdateStatement(dbConnection, pst, rmNode);
            pst.executeUpdate();
            updateCount.getAndIncrement();

            // dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while updateRuleMetricNode(), updateRuleMetricSql = %s", e, updateRuleMetricSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
            // connectionPool.releaseCurrentConnection();
        }

    }

    private String getUpdateRuleMetricSql(RuleMetricNodeState rmNode, String tableName, MetricKey mKey) {
        String cachekey = mKey.getSchemaName() + DBConstant.SEP + mKey.getCubeName() + DBConstant.SEP + mKey.getDimensionHierarchyName();
        if (updateRuleMetricSqlPerDh.containsKey(cachekey)) {
            return updateRuleMetricSqlPerDh.get(cachekey);
        }
        StringBuilder queryBldr = new StringBuilder("UPDATE " + tableName + " SET ");
        queryBldr.append(DBConstant.RULE_ACTION_NAME_FIELD + "=?, ");
        queryBldr.append(DBConstant.RULE_SET_COUNT_FIELD + "=?, ");
        queryBldr.append(DBConstant.RULE_SCHEDULED_TIME_FIELD + "=?, ");
        queryBldr.append(DBConstant.RULE_LAST_FIRED_TIME_FIELD + "=?, ");
        queryBldr.append(DBConstant.UPDATED_DATE_TIME_FIELD + "=?,  ");
        queryBldr.append(DBConstant.RULE_SET_CONDITION_KEY_FILED + "=?, ");
        queryBldr.append(DBConstant.RULE_CLEAR_CONDITION_KEY_FILED + "=?");
        DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName()).getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());
        String prefix = ",";
        for (Measurement measurement : dh.getMeasurements()) {
            String metricName = measurement.getName();
            queryBldr.append(prefix + metricName + "=? ");
            // queryBldr.append(prefix + metricName +
            // METRIC_CONTEXT_FIELD_SUFFIX + "=? ");
            for (FunctionParam param : measurement.getMetricFunctionDescriptor().getFunctionContexts()) {
                queryBldr.append(prefix + measurement.getName() + "_" + param.getName() + "=?");
            }
        }

		queryBldr.append(" WHERE ");
		if(isMSSqlServer && !usePK){
			queryBldr.append(DBConstant.RULE_METRIC_KEY_HASH_FIELD + " =?  AND ");
		}
		queryBldr.append(DBConstant.METRIC_KEY_FIELD + " =?");
        queryBldr.append(" AND " + DBConstant.RULE_NAME_FIELD + " =?");
        queryBldr.append(" AND " + DBConstant.RULE_ACTION_NAME_FIELD + " =?");
        String sql = queryBldr.toString();
        updateRuleMetricSqlPerDh.put(cachekey, sql);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "updateRuleMetricNode(), SqlQuery : %s", sql);
        }
        return sql;

    }

    private String getDeleteRuleMetricSql(MetricKey mKey) {
        String cachekey = mKey.getSchemaName() + DBConstant.SEP + mKey.getCubeName() + DBConstant.SEP + mKey.getDimensionHierarchyName();
        if (deleteRuleMetricSqlPerDh.containsKey(cachekey)) {
            return deleteRuleMetricSqlPerDh.get(cachekey);
        }
        StringBuilder sqlBldr = new StringBuilder("DELETE FROM ");
        sqlBldr.append(databaseSchemaManager.makeRuleMetricTableName(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName()));
        sqlBldr.append(" WHERE ");
        if(isMSSqlServer && !usePK){
        	sqlBldr.append(DBConstant.RULE_METRIC_KEY_HASH_FIELD + "=? AND ");
        }        
        sqlBldr.append(DBConstant.METRIC_KEY_FIELD + "=?");
        sqlBldr.append(" AND " + DBConstant.RULE_NAME_FIELD + " =?");
        sqlBldr.append(" AND " + DBConstant.RULE_ACTION_NAME_FIELD + " =?");
        String sql = sqlBldr.toString();
        deleteRuleMetricSqlPerDh.put(cachekey, sql);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "deleteRuleMetricNode(), SqlQuery : %s", sql);
        }
        return sql;
    }

    private String getPurgeMetricSql(String schemaName, String cubeName, String hierarchyName) {
        StringBuilder queryBldr = new StringBuilder("DELETE FROM ");
        queryBldr.append(databaseSchemaManager.makeMetricSchemaTableName(schemaName, cubeName, hierarchyName));
        queryBldr.append(" WHERE ");
        queryBldr.append(DBConstant.UPDATED_DATE_TIME_FIELD + " < ?");
        return queryBldr.toString();
    }

    private void purgeProcessedFactMultiTable(String schemaName, Qualifier qualifier, long purgeOlderThan,
                                              Connection dbConnection, long currentTime) {
        if (qualifier.equals(Qualifier.FACT)) {

            RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
            for (Cube cube : schema.getCubes()) {
                for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                    runPurgeQuery(schemaName, purgeOlderThan, dbConnection, currentTime, cube, dh);
                }
            }
        }
    }

    private void runPurgeQuery(String schemaName, long purgeOlderThan, Connection dbConnection, long currentTime,
                               Cube cube, DimensionHierarchy dh) {
        PreparedStatement pst = null;
        String deleteSql = null;

        deleteSql = getPurgeProcessedFactSql(schemaName, cube.getName(), dh.getName());

        try {

            pst = dbConnection.prepareStatement(deleteSql);
            int index = 1;
            pst.setTimestamp(index++, new Timestamp(currentTime - purgeOlderThan));
            int rowPurged = pst.executeUpdate();
            deleteCount.getAndIncrement();

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG,
                        "Number of Records purged from Processed Fact table of Cube [%s] and Dimension [%s]= %s",
                        cube.getName(), dh.getName(), rowPurged);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "An error occurred, while purgeProcessedFact(), deleteSql = %s", e, deleteSql);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {

                }
            }
        }
    }

    private int purgeFact(String schemaName, Qualifier qualifier, long purgeOlderThan, Connection dbConnection, long currentTime) {
        if (qualifier.equals(Qualifier.FACT)) {

            PreparedStatement pst = null;
            String deleteSql = null;
            try {
                deleteSql = getPurgeFactSql(schemaName);

                pst = dbConnection.prepareStatement(deleteSql);
                int index = 1;
                pst.setTimestamp(index++, new Timestamp(currentTime - purgeOlderThan));
                int rowPurged = pst.executeUpdate();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Number of Records purged from Fact table = %s", rowPurged);
                }
                deleteCount.getAndIncrement();

                return rowPurged;
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "An error occurred, while purgeFact(), deleteSql = %s", e, deleteSql);
            } finally {
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (Exception e) {

                    }
                }
            }
        }
        return 0;
    }

    private void purgeProcessedFact(String schemaName, Qualifier qualifier, long purgeOlderThan, Connection dbConnection, long currentTime) {
        if (qualifier.equals(Qualifier.FACT)) {

            PreparedStatement pst = null;
            String deleteSql = null;
            try {
                deleteSql = getPurgeProcessedFactSql(schemaName);
                pst = dbConnection.prepareStatement(deleteSql);
                int index = 1;
                pst.setTimestamp(index++, new Timestamp(currentTime - purgeOlderThan));
                int rowPurged = pst.executeUpdate();
                deleteCount.getAndIncrement();

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Number of Records purged from Processed Fact table = %s", rowPurged);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "An error occurred, while purgeProcessedFact(), deleteSql = %s", e, deleteSql);
            } finally {
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    private String getPurgeProcessedFactSql(String schemaName) {
        StringBuilder queryBldr = new StringBuilder("DELETE FROM ");

        StringBuilder subqueryBldr = new StringBuilder("SELECT " + DBConstant.FACT_KEY_FIELD + " FROM ");
        subqueryBldr.append(databaseSchemaManager.makeFactTableName(schemaName));
        subqueryBldr.append(" WHERE ");
        subqueryBldr.append(DBConstant.CREATED_DATE_TIME_FIELD + " < ?");

        queryBldr.append(databaseSchemaManager.makeProcessedFactTableName(schemaName));
        queryBldr.append(" WHERE " + DBConstant.FACT_KEY_FIELD + " IN (");
        queryBldr.append(subqueryBldr.toString() + ")");
        return queryBldr.toString();
    }

    private String getPurgeProcessedFactSql(String schemaName, String cubeName, String dimensionHierarchyName) {
        StringBuilder queryBldr = new StringBuilder("DELETE FROM ");

        StringBuilder subqueryBldr = new StringBuilder("SELECT " + DBConstant.FACT_KEY_FIELD + " FROM ");
        subqueryBldr.append(databaseSchemaManager.makeFactTableName(schemaName));
        subqueryBldr.append(" WHERE ");
        subqueryBldr.append(DBConstant.CREATED_DATE_TIME_FIELD + " < ?");

        queryBldr.append(databaseSchemaManager.makeProcessedFactTableName(schemaName, cubeName, dimensionHierarchyName));
        queryBldr.append(" WHERE " + DBConstant.FACT_KEY_FIELD + " IN (");
        queryBldr.append(subqueryBldr.toString() + ")");
        return queryBldr.toString();
    }

    private String getPurgeFactSql(String schemaName) {
        StringBuilder queryBldr = new StringBuilder("DELETE FROM ");
        queryBldr.append(databaseSchemaManager.makeFactTableName(schemaName));
        queryBldr.append(" WHERE ");
        queryBldr.append(DBConstant.CREATED_DATE_TIME_FIELD + " < ?");
        return queryBldr.toString();
    }

    public static void main(String args[]) throws Exception {
        java.util.Properties config = new java.util.Properties();
        config.put("tea.agent.jdbc.driver", "org.h2.Driver");
        config.put("tea.agent.jdbc.url", "jdbc:h2:tcp://localhost/~/spm_db;MVCC=true");
        config.put("tea.agent.jdbc.user", "sa");
        config.put("tea.agent.jdbc.password", "");
        config.put("tea.agent.jdbc.initial.connection.size", "10");
        config.put("tea.agent.jdbc.max.connection.size", "10");
        DatabasePersistenceService pService = new DatabasePersistenceService();
        pService.init(config);
        pService.purgeMetricsForQualifier("amx_3_0", null, 1000 * 60 * 2, true, true);

    }

    private boolean isMsSqlServer(Properties config) {
        String jdbcDriver = (String) ConfigProperty.RTA_JDBC_DRIVER.getValue(config);
        if (jdbcDriver != null && jdbcDriver.toLowerCase().contains("sqlserver")) {
            return true;
        }
        return false;
    }

}
