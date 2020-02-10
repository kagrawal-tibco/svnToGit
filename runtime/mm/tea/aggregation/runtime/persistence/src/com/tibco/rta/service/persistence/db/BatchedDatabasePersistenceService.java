package com.tibco.rta.service.persistence.db;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.FatalException;
import com.tibco.rta.common.exception.PersistenceStoreNotAvailableException;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.RtaTransaction.FactHr;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.common.service.RtaTransaction.TxnType;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.AlertNodeStateKey;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;

public class BatchedDatabasePersistenceService extends DatabasePersistenceService {

    @Override
    public void applyTransaction(boolean retryInfinite) throws Exception {
        applyTransactionBatched(retryInfinite);
    }

    private void applyTransactionBatched(boolean retryInfinite) throws Exception {
        RtaTransaction txn = (RtaTransaction) RtaTransaction.get();
        Map<String, BatchedNodePerSchema> txnPerSchemaStore = new HashMap<String, BatchedNodePerSchema>();
        createBatch(txn, txnPerSchemaStore);
        processBatch(retryInfinite, txnPerSchemaStore, txn.getClientBatchSize(), true);
    }

    private void createBatch(RtaTransaction txn,
                             Map<String, BatchedNodePerSchema> txnPerSchemaStore) {
        for (Map.Entry<Key, RtaTransactionElement<?>> e : txn.getTxnList().entrySet()) {

            Key key = e.getKey();
            RtaTransactionElement txnE = e.getValue();

            if (key instanceof MetricKey) {
                MetricNode metricNode = (MetricNode) txnE.getNode();
                MetricKey mKey = (MetricKey) metricNode.getKey();
                BatchedNodePerSchema store = getBatchedNodeSchema(getIdentifier(mKey), txnPerSchemaStore,
                        BatchedNodePerSchema.NodeType.METRIC);
                if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                    store.addNode(RtaTransaction.TxnType.NEW, metricNode);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                    store.addNode(RtaTransaction.TxnType.UPDATE, metricNode);

                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
                    store.addNode(RtaTransaction.TxnType.DELETE, metricNode);
                }

            } else if (key instanceof FactKeyImpl) {
                Fact fact = (Fact) txnE.getNode();
                BatchedNodePerSchema store = getBatchedNodeSchema(getIdentifier(fact), txnPerSchemaStore,
                        BatchedNodePerSchema.NodeType.FACT);
                if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW) && ((FactImpl) fact).isNew()) {
                    store.addNode(RtaTransaction.TxnType.NEW, fact);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                    store.addNode(RtaTransaction.TxnType.UPDATE, fact);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
                    delete(fact);
                    store.addNode(RtaTransaction.TxnType.DELETE, fact);
                }
            } else if (key instanceof RuleMetricNodeStateKey && !(key instanceof AlertNodeStateKey)) {
                RuleMetricNodeState ruleMetricNodeState = (RuleMetricNodeState) txnE.getNode();
                BatchedNodePerSchema store = getBatchedNodeSchema(getIdentifier(ruleMetricNodeState.getKey()), txnPerSchemaStore,
                        BatchedNodePerSchema.NodeType.RULE_METRIC);
                if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                    store.addNode(RtaTransaction.TxnType.NEW, ruleMetricNodeState);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                    store.addNode(RtaTransaction.TxnType.UPDATE, ruleMetricNodeState);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
                    store.addNode(RtaTransaction.TxnType.DELETE, ruleMetricNodeState);
                }
            } else if (key instanceof FactHr) {
                FactHr factHr = (FactHr) txnE.getNode();
                BatchedNodePerSchema store = getBatchedNodeSchema(getIdentifier(factHr), txnPerSchemaStore,
                        BatchedNodePerSchema.NodeType.FACTHR);
                if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
                    store.addNode(RtaTransaction.TxnType.UPDATE, factHr);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
                    store.addNode(RtaTransaction.TxnType.UPDATE, factHr);
                } else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {

                }
            }
        }
    }


    private void processBatch(boolean retryInfinite, Map<String, BatchedNodePerSchema> txnPerSchema,
                              int clientBa1tchSize, boolean batchExecute) throws Exception {
        int retryCnt = 0;
        while (true) {
            boolean shouldClearConn = true;

            retryCnt++;
            if (retryCnt > 1) {
                LOGGER.log(Level.DEBUG,
                        "Retrying transaction. Retry count [%d]", retryCnt);
            }
            try {
                connectionPool.getSqlConnection();
                for (Map.Entry<String, BatchedNodePerSchema> entry : txnPerSchema.entrySet()) {
                    BatchedNodePerSchema store = entry.getValue();
                    if (BatchedNodePerSchema.NodeType.METRIC.equals(store.getNodeType())) {
                        // Process metric node
                        List<Object> newMetricList = store.getNodeList(TxnType.NEW);
                        if (!newMetricList.isEmpty()) {
                            // perform the inserts first
                            processMetricNodeBatchInsert(newMetricList);
                        }
                        List<Object> updateMetricList = entry.getValue().getNodeList(TxnType.UPDATE);
                        if (!updateMetricList.isEmpty()) {
                            processMetricNodeBatchUpdates(updateMetricList);
                        }
                        List<Object> deleteMetricList = entry.getValue().getNodeList(TxnType.DELETE);
                        for (Object obj : deleteMetricList) {
                            MetricNode mn = (MetricNode) obj;
                            delete(mn);
                        }
                    } else if (BatchedNodePerSchema.NodeType.FACT.equals(store.getNodeType())) {
                        List<Object> saveFactList = entry.getValue().getNodeList(TxnType.NEW);
                        if (!saveFactList.isEmpty()) {
                            processFactBatchSave(saveFactList, batchExecute);
                        }
                        List<Object> updateFactList = entry.getValue().getNodeList(TxnType.UPDATE);
                        if (!updateFactList.isEmpty()) {
                            processFactBatchSave(updateFactList, batchExecute);
                        }
                    } else if (BatchedNodePerSchema.NodeType.RULE_METRIC.equals(store.getNodeType())) {

                        // Process metric node
                        List<Object> newMetricRuleList = store.getNodeList(TxnType.NEW);
                        if (!newMetricRuleList.isEmpty()) {
                            // perform the inserts first
                            processRuleMetricNodeBatchInsert(newMetricRuleList);
                        }
                        List<Object> updateMetricRuleList = entry.getValue().getNodeList(TxnType.UPDATE);
                        if (!updateMetricRuleList.isEmpty()) {
                            processRuleMetricNodeBatchUpdates(updateMetricRuleList);
                        }
                        List<Object> deleteMetricRuleList = entry.getValue().getNodeList(TxnType.DELETE);
                        for (Object obj : deleteMetricRuleList) {
                            RuleMetricNodeState mn = (RuleMetricNodeState) obj;
                            deleteRuleMetricNode(mn.getKey());
                        }
                    } else if (BatchedNodePerSchema.NodeType.FACTHR.equals(store.getNodeType())) {

                        // Processed facts
                        List<Object> newFactHrList = entry.getValue().getNodeList(TxnType.NEW);
                        if (!newFactHrList.isEmpty()) {
//							if (insertProcessed) {
//								if (isInsertProcessedWithMultipleTable) {
//									insertProcessedFactMultiTable(newFactHrList, batchExecute);
//								} else {
//									insertProcesedFact(newFactHrList, batchExecute);
//								}
//							}
                        }
                        List<Object> updateFactHrList = entry.getValue().getNodeList(TxnType.UPDATE);
                        if (!updateFactHrList.isEmpty()) {
//							if (insertProcessed) {
//								if (isInsertProcessedWithMultipleTable) {
//									insertProcessedFactMultiTable(updateFactHrList, batchExecute);
//								} else {
//									insertProcesedFact(updateFactHrList, batchExecute);
//								}
//							}
                        }
                    }
                }
                commit();
                break;
            } catch (DBConnectionsBusyException busyEx) {
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR,
                            "Database connections are busy. Will NOT retry.");
                    throw busyEx;
                }

                LOGGER.log(Level.ERROR, "Database connections are busy. Will retry.");
                ThreadSleep(5000);
            } catch (PersistenceStoreNotAvailableException dbne) {
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR,
                            "Database not available, Will NOT wait for reconnect..");
                    throw dbne;
                }
                LOGGER.log(Level.ERROR,
                        "Database not available, Waiting for reconnect..");
                connectionPool.waitForReconnect(30000, -1);
                LOGGER.log(Level.INFO,
                        "Database reconnected. Processing will continue");
            } catch (SQLIntegrityConstraintViolationException e) {
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR,
                            "SQLIntegrityConstraintViolationException. Will NOT retry.");
                    throw e;
                }

                //Bala: Code will reach here ONLY if there is an exception in FACTS or FACTHR insert.
                //For other types, SPM will not ignore the duplicates as that is considered a serious defect.
                LOGGER.log(Level.WARN, "SQLIntegrityConstraintViolationException encountered while applying transaction.  Duplicate removed. Will retry transaction.");
                //try execute the batch again!
                batchExecute = true;
            } catch (BatchUpdateException e) {
                if (!retryInfinite) {
                    LOGGER.log(Level.ERROR,
                            "BatchUpdateException. Will NOT retry.");
                    throw e;
                }
                //Bala: Code will reach here ONLY if there is an exception in FACTS or FACTHR insert.
                //For other types, SPM will not ignore the duplicates as that is considered a serious defect.
                LOGGER.log(Level.DEBUG, "BatchException encountered while applying transaction. Checking if it is a constraint violation.", e);

                Iterator i = e.iterator();
                Throwable t = null;
                boolean foundCV = false;
                SQLException sqlE = null;
                while (i.hasNext()) {
                    t = (Throwable) i.next();
                    if (t instanceof SQLIntegrityConstraintViolationException) {
                        foundCV = true;
                        break;
                    } else if (t instanceof SQLException
                            && ((SQLException) t).getSQLState() != null
                            && ((SQLException) t).getSQLState()
                            .startsWith("23")) {
                        // sql state code starting with [23] stands for
                        // integrity constraint violation
                        foundCV = true;
                        break;
                    } else if (t instanceof SQLException) {
                        sqlE = (SQLException) t;
                    }
                }

                //first time around.
                if (batchExecute) {
                    if (foundCV) {
                        LOGGER.log(Level.WARN, "SQLIntegrityConstraintViolationException encountered while applying transaction. Will retry transaction to identify the duplicate.");
                        batchExecute = false;
                    } else {
                        checkForDatabaseDownAndWait(retryInfinite, sqlE);
                    }
                }
                //second time around
                else {
                    if (foundCV) {
                        LOGGER.log(Level.ERROR, "SQLIntegrityConstraintViolationException NOT expected here. FATAL error will be thrown.", e);
                        throw new FatalException("SQLIntegrityConstraintViolationException NOT expected here. FATAL error will be thrown.");
                    } else {
                        checkForDatabaseDownAndWait(retryInfinite, sqlE);

                    }
                }
            } catch (SQLException ex) {
                checkForDatabaseDownAndWait(retryInfinite, ex);
            } catch (FatalException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new FatalException("Will not retry transaction.", ex);
            } finally {
                // Dont do this here if an exception is to be thrown outside.
                // This will be done outside by the OM layer.
                // else if it should keep retrying, clear it.
                if (shouldClearConn) {
                    connectionPool.releaseCurrentConnection();
                }
            }
        }
    }

    private void checkForDatabaseDownAndWait(boolean retryInfinite,
                                             SQLException ex) throws SQLException, InterruptedException {
        if (!retryInfinite) {
            LOGGER.log(
                    Level.ERROR,
                    "SQLException encountered while applying transaction. Will NOT retry",
                    ex);
            throw ex;
        }
        LOGGER.log(Level.ERROR,
                "SQLException encountered while applying transaction",
                ex);

        try {
            connectionPool.check(ex, connectionPool.getCurrentConnection());
        } catch (PersistenceStoreNotAvailableException e) {
            LOGGER.log(Level.ERROR, "Connection check failed..Database is disconnected. Waiting for reconnect.");
            Thread.sleep(5000);
            connectionPool.waitForReconnect(30000, -1);
        }
    }


    private String getIdentifier(MetricKey mKey) {
        return BatchedNodePerSchema.NodeType.METRIC + DBConstant.SEP + mKey.getSchemaName() + DBConstant.SEP
                + mKey.getCubeName() + DBConstant.SEP + mKey.getDimensionHierarchyName();
    }

    private String getIdentifier(RuleMetricNodeStateKey mKey) {
        return BatchedNodePerSchema.NodeType.RULE_METRIC + DBConstant.SEP + mKey.getMetricKey().getSchemaName() + DBConstant.SEP
                + mKey.getMetricKey().getCubeName() + DBConstant.SEP + mKey.getMetricKey().getDimensionHierarchyName();
    }

    private String getIdentifier(FactHr factHr) {
        return BatchedNodePerSchema.NodeType.FACTHR + DBConstant.SEP + factHr.getSchemaName() + DBConstant.SEP
                + factHr.getCubeName() + DBConstant.SEP + factHr.getHierarchyName();
    }

    private String getIdentifier(Fact fact) {
        return BatchedNodePerSchema.NodeType.FACT + DBConstant.SEP + fact.getOwnerSchema().getName();
    }

    private BatchedNodePerSchema getBatchedNodeSchema(String identifier,
                                                      Map<String, BatchedNodePerSchema> TxnPerSchema, BatchedNodePerSchema.NodeType type) {
        if (TxnPerSchema.keySet().contains(identifier)) {
            return TxnPerSchema.get(identifier);
        } else {
            BatchedNodePerSchema newBatchStore = new BatchedNodePerSchema(identifier, type);
            TxnPerSchema.put(identifier, newBatchStore);
            return newBatchStore;
        }
    }

    private void processRuleMetricNodeBatchUpdates(List<Object> updateMetricRuleList) throws Exception {
        Connection connection = connectionPool.getSqlConnection();
        PreparedStatement stmt = null;
        try {
            RuleMetricNodeState mn = (RuleMetricNodeState) updateMetricRuleList.get(0);
            String updateSql = getUpdateRuleMetricSql(mn);
            stmt = connection.prepareStatement(updateSql);
            int count = 0;
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "databaseBatchSize [%s]", databaseBatchSize);
            }
            for (Object node : updateMetricRuleList) {
                RuleMetricNodeState ruleNode = (RuleMetricNodeState) node;
                setRuleMetricValueToUpdateStatement(connection, stmt, ruleNode);
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Batched Rule Metric UPDATE, [%d] Metric Node = [%s]", count, ruleNode);
                }
                stmt.addBatch();
                if (++count == databaseBatchSize) {
                    stmt.executeBatch();
                    count = 0;
                }
            }
            if (count != 0) {
                stmt.executeBatch();
            }
            stmt.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private void processRuleMetricNodeBatchInsert(List<Object> newMetricRuleList) throws Exception {
        Connection connection = connectionPool.getSqlConnection();
        PreparedStatement stmt = null;
        RuleMetricNodeState mn = (RuleMetricNodeState) newMetricRuleList.get(0);

        try {
            String insertSql = getInsertRuleMetricSql(mn);
            stmt = connection.prepareStatement(insertSql);
            int count = 0;
            for (Object node : newMetricRuleList) {
                RuleMetricNodeState ruleMetricNode = (RuleMetricNodeState) node;
                setRuleMetricValueToInsertStatement(insertSql, connection,
                        stmt, ruleMetricNode);

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Batched Rule Metric INSERT, [%d] Metric Node = [%s]", count, ruleMetricNode);
                }
                stmt.addBatch();
                if (++count == databaseBatchSize) {
                    stmt.executeBatch();
                    count = 0;
                }
            }
            if (count != 0) {
                stmt.executeBatch();
            }
            stmt.close();
        } catch (BatchUpdateException e) {
            Iterator i = e.iterator();
            Throwable t = null;
            while (i.hasNext()) {
                t = (Throwable) i.next();
                if (t instanceof SQLIntegrityConstraintViolationException) {
                    throw new FatalException(String.format("Duplicate rule metric node detected [%s]", mn.getKey().toString()));
                } else if (t instanceof SQLException
                        && ((SQLException) t).getSQLState() != null
                        && ((SQLException) t).getSQLState()
                        .startsWith("23")) {
                    // sql state code starting with [23] stands for
                    // integrity constraint violation
                    throw new FatalException(String.format("Duplicate rule metric node detected [%s]", mn.getKey().toString()));
                }
            }
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private void processMetricNodeBatchInsert(List<Object> newMetricList) throws Exception {

        Connection connection = connectionPool.getSqlConnection();
        PreparedStatement stmt = null;
        MetricNode mn = (MetricNode) newMetricList.get(0);

        try {
            String insertSql = getMetricInsertSql((MetricKey) mn.getKey());
            stmt = connection.prepareStatement(insertSql);
            int count = 0;
            if (LOGGER_CRUD.isEnabledFor(Level.DEBUG)) {
                LOGGER_CRUD.log(Level.DEBUG, " INSERT, HeirarchyName = [%s]", ((MetricKey) mn.getKey()).getDimensionHierarchyName());
            }

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "databaseBatchSize [%s]", databaseBatchSize);
            }
            for (Object node : newMetricList) {
                MetricNode metricNode = (MetricNode) node;
                setMetricValueToInsertStatement(insertSql, connection, stmt, metricNode);
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Batched INSERT, [%d] Metric Node [%s]", count, metricNode);
                }
                if (LOGGER_CRUD.isEnabledFor(Level.DEBUG)) {
                    LOGGER_CRUD.log(Level.DEBUG, "Batched INSERT, [%d] Metric Node [%s]", count, metricNode);
                }

                stmt.addBatch();
                if (++count == databaseBatchSize) {
                    stmt.executeBatch();
                    count = 0;
                }
            }
            if (count != 0) {
                stmt.executeBatch();
            }
            stmt.close();
        } catch (BatchUpdateException e) {
            Iterator i = e.iterator();
            Throwable t = null;
            while (i.hasNext()) {
                t = (Throwable) i.next();
                if (t instanceof SQLIntegrityConstraintViolationException) {
                    throw new FatalException(String.format("Duplicate metric node detected [%s]", mn.getKey().toString()));
                } else if (t instanceof SQLException
                        && ((SQLException) t).getSQLState() != null
                        && ((SQLException) t).getSQLState()
                        .startsWith("23")) {
                    // sql state code starting with [23] stands for
                    // integrity constraint violation
                    throw new FatalException(String.format("Duplicate metric node detected [%s]", mn.getKey().toString()));
                }
            }
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }

    }


    private void processMetricNodeBatchUpdates(List<Object> updateMetricList) throws Exception {
        Connection connection = connectionPool.getSqlConnection();
        PreparedStatement stmt = null;
        try {
            MetricNode mn = (MetricNode) updateMetricList.get(0);
            String updateSql = getUpdateMetricSql((MetricKey) mn.getKey());
            stmt = connection.prepareStatement(updateSql);
            int count = 0;
            if (LOGGER_CRUD.isEnabledFor(Level.DEBUG)) {
                LOGGER_CRUD.log(Level.DEBUG, " UPDATE, HeirarchyName = [%s]", ((MetricKey) mn.getKey()).getDimensionHierarchyName());
            }

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "databaseBatchSize [%s]", databaseBatchSize);
            }
            for (Object node : updateMetricList) {
                MetricNode metricNode = (MetricNode) node;
                setMetricValueToUpdateStatement(connection, stmt, metricNode);
                if (LOGGER_CRUD.isEnabledFor(Level.DEBUG)) {
                    LOGGER_CRUD.log(Level.DEBUG, "Batched UPDATE, [%d] Metric Node = [%s]", count, metricNode);
                }
                stmt.addBatch();
                if (++count == databaseBatchSize) {
                    stmt.executeBatch();
                    count = 0;
                }
            }
            if (count != 0) {
                stmt.executeBatch();
            }
            stmt.close();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }

    }

    private void processFactBatchSave(List<Object> facts, boolean batchExecute) throws Exception {
        if (facts.isEmpty()) {
            return;
        }
        Connection connection = connectionPool.getSqlConnection();
        PreparedStatement stmt = null;
        try {
            if (batchExecute) {
                String insertSql = getInsertFactSql(((Fact) facts.get(0)).getOwnerSchema());
                stmt = connection.prepareStatement(insertSql);
                int count = 0;
                for (Object node : facts) {
                    Fact fact = (Fact) node;
                    setInsertFactStatementValue(stmt, fact, insertSql);
                    stmt.addBatch();
                    if (++count == databaseBatchSize) {
                        stmt.executeBatch();
                        count = 0;
                    }
                }
                if (count != 0) {
                    stmt.executeBatch();
                }
                stmt.close();
            } else {
                String insertSql = getInsertFactSql(((Fact) facts.get(0)).getOwnerSchema());
                stmt = connection.prepareStatement(insertSql);
                int i = 0;
                for (Object node : facts) {
                    Fact fact = (Fact) node;
                    setInsertFactStatementValue(stmt, fact, insertSql);
                    try {
                        stmt.execute();
                    } catch (SQLIntegrityConstraintViolationException e) {
                        // ignore it 2nd time around..
                        LOGGER.log(Level.WARN, "Fact already exists in the database" + fact.getKey().toString());
                        ((FactImpl) fact).setIsDuplicate(true);
                        facts.remove(i);
                        throw e;
                    } catch (SQLException e) {
                        if (((SQLException) e).getSQLState() != null && ((SQLException) e).getSQLState().startsWith("23")) {
                            LOGGER.log(Level.WARN, "Fact already exists in the database " + fact.getKey().toString());
                            ((FactImpl) fact).setIsDuplicate(true);
                            facts.remove(i);
                            throw new SQLIntegrityConstraintViolationException(e);
                        } else {
                            throw e;
                        }
                    }
                    i++;
                }
            }
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }


//	private void insertProcesedFact(List<Object> factHrs, boolean batchExecute) throws Exception {
//		if (factHrs.isEmpty()) {
//			return;
//		}
//		Connection connection = connectionPool.getSqlConnection();
//		PreparedStatement stmt = null;
//		try {
//			if (batchExecute) {
//				String insertSql = getInsertProcessedFactSql(((FactHr) factHrs
//						.get(0)).getSchemaName());
//				stmt = connection.prepareStatement(insertSql);
//				int count = 0;
//				for (Object object : factHrs) {
//					FactHr factHr = (FactHr) object;
//					setInsertProcessedFactStatementValue(stmt, factHr);
//					stmt.addBatch();
//					if (++count == databaseBatchSize) {
//						stmt.executeBatch();
//						count = 0;
//					}
//				}
//				if (count != 0) {
//					stmt.executeBatch();
//				}
//				stmt.close();
//			} else {
//				String insertSql = getInsertProcessedFactSql(((FactHr) factHrs.get(0)).getSchemaName());
//				stmt = connection.prepareStatement(insertSql);
//				int i=0;
//				for (Object node : factHrs) {
//					FactHr factHr = (FactHr) node;
//					setInsertProcessedFactStatementValue(stmt, factHr);
//					try {
//						stmt.execute();
//					} catch (SQLIntegrityConstraintViolationException e) {
//						// ignore it 2nd time around..
//						LOGGER.log(Level.WARN, "FactHr already exists in the database" + factHr.toString());
//						factHrs.remove(i);
//						throw e;
//					} catch (SQLException e) {
//						if (((SQLException) e).getSQLState() != null  && ((SQLException) e).getSQLState().startsWith("23")) {
//							LOGGER.log(Level.WARN, "FactHr already exists in the database " + factHr.toString());
//							factHrs.remove(i);
//						}
//						throw new SQLIntegrityConstraintViolationException(e);
//					}
//					i++;
//				}
//			}
//		} finally {
//			if (stmt != null) {
//				try {
//					stmt.close();
//				} catch (Exception e) {
//				}
//			}
//		}
//	}
//	
//	private void insertProcessedFactMultiTable(List<Object> factHrs, boolean batchExecute) throws Exception {
//		if (factHrs.isEmpty()) {
//			return;
//		}
//		Connection connection = connectionPool.getSqlConnection();
//		PreparedStatement stmt = null;
//		try {
//			if (batchExecute) {
//				FactHr firstFactHr = (FactHr) factHrs.get(0);
//				String insertSql = getInsertProcessedFactMultipleTableSql(
//						firstFactHr.getSchemaName(), firstFactHr.getCubeName(),
//						firstFactHr.getHierarchyName());
//				stmt = connection.prepareStatement(insertSql);
//				int count = 0;
//				for (Object object : factHrs) {
//					FactHr factHr = (FactHr) object;
//					setInsertProcessedFactParameters(stmt, factHr.getFact());
//					stmt.addBatch();
//					if (++count == databaseBatchSize) {
//						stmt.executeBatch();
//						count = 0;
//					}
//				}
//				if (count != 0) {
//					stmt.executeBatch();
//				}
//				stmt.close();
//			} else {
//				FactHr firstFactHr = (FactHr) factHrs.get(0);
//				String insertSql = getInsertProcessedFactMultipleTableSql(firstFactHr.getSchemaName(),
//						firstFactHr.getCubeName(), firstFactHr.getHierarchyName());
//				stmt = connection.prepareStatement(insertSql);
//				int i=0;
//				for (Object object : factHrs) {
//					FactHr factHr = (FactHr) object;
//					setInsertProcessedFactParameters(stmt, factHr.getFact());
//					try {
//						stmt.execute();
//					} catch (SQLIntegrityConstraintViolationException e) {
//						// ignore it 2nd time around..
//						LOGGER.log(Level.WARN, "FactHr already exists in the database" + factHr.toString());
//						factHrs.remove(i);
//						throw e;
//					} catch (SQLException e) {
//						if (((SQLException) e).getSQLState() != null  && ((SQLException) e).getSQLState().startsWith("23")) {
//							LOGGER.log(Level.WARN, "FactHr already exists in the database " + factHr.toString());
//							factHrs.remove(i);
//						}
//						throw new SQLIntegrityConstraintViolationException(e);
//					}					
//					i++;
//				}
//			}
//		} finally {
//			if (stmt != null) {
//				try {
//					stmt.close();
//				} catch (Exception e) {
//				}
//			}
//		}
//	}
}
