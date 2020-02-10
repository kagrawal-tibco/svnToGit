package com.tibco.rta.service.persistence.as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceResultList;
import com.tibco.as.space.Tuple;
import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.RtaTransaction.FactHr;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.common.service.RtaTransaction.TxnType;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.db.BatchedNodePerSchema;
import com.tibco.rta.service.persistence.db.DBConstant;

public class BatchedASPersistenceService extends ASPersistenceService{


	private static final Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	
	@Override
	public void applyTransaction(boolean retryInfinite) throws Exception {
		applyTransactionBatched();
	}

	private void applyTransactionBatched() {
		// TODO Auto-generated method stub
		int retryCnt = 0;
		RtaTransaction txn = (RtaTransaction) RtaTransaction.get();
		while (true) {
			boolean shouldClearConn = true;
			Map<String, BatchedNodePerSchema> txnPerSchemaStore = new HashMap<String, BatchedNodePerSchema>();

			retryCnt++;
			if (retryCnt > 1) {
				LOGGER.log(Level.DEBUG, "Retrying transaction. Retry count [%d]", retryCnt);
			}
			try {
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
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW) && ((FactImpl)fact).isNew()) {
							store.addNode(RtaTransaction.TxnType.NEW, fact);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							store.addNode(RtaTransaction.TxnType.UPDATE, fact);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
							delete(fact);
							store.addNode(RtaTransaction.TxnType.DELETE, fact);
						}
					} else if (key instanceof RtaTransaction.MetricFact.MFKey) {
						// RtaTransaction.MetricFact metricFact = (MetricFact)
						// txnE.getNode();
						// if
						// (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW))
						// {
						// saveMetricFactToDB(metricFact.getMetricNode(),
						// metricFact.getFact());
						// } else if
						// (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE))
						// {
						// saveMetricFactToDB(metricFact.getMetricNode(),
						// metricFact.getFact());
						// } else if
						// (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE))
						// {
						//
						// }
					} else if (key instanceof RuleMetricNodeStateKey) {
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
						BatchedNodePerSchema store = getBatchedNodeSchema(getIdentifier(factHr), txnPerSchemaStore,
								BatchedNodePerSchema.NodeType.FACTHR);
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							store.addNode(RtaTransaction.TxnType.UPDATE, factHr);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							store.addNode(RtaTransaction.TxnType.UPDATE, factHr);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {

						}
					}
//					else if (key instanceof AssetKeyImpl) {
//						AssetFact asset = (AssetFact) txnE.getNode();
//						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
////							saveAsset(asset);
//						}
//					}

				}

				processBatch(txnPerSchemaStore, txn.getClientBatchSize());
				commit();
				break;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
}
	

	private void processBatch(Map<String, BatchedNodePerSchema> txnPerSchema, int clientBatchSize) throws Exception {
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
			}
			if (BatchedNodePerSchema.NodeType.FACT.equals(store.getNodeType())) {
				List<Object> saveFactList = entry.getValue().getNodeList(TxnType.NEW);
				if (!saveFactList.isEmpty()) {
					processFactBatchSave(saveFactList);
				}
				List<Object> updateFactList = entry.getValue().getNodeList(TxnType.UPDATE);
				if (!updateFactList.isEmpty()) {
					processFactBatchSave(updateFactList);
				}
			}
//			if (BatchedNodePerSchema.NodeType.FACTHR.equals(store.getNodeType())) {
//
//				List<Object> newFactHrList = entry.getValue().getNodeList(TxnType.NEW);
//				if (!newFactHrList.isEmpty()) {
//					if (insertProcessed) {
//						if (isInsertProcessedWithMultipleTable) {
//							insertProcessedFactMultiTable(newFactHrList);
//						} else {
//							insertProcesedFact(newFactHrList);
//						}
//					} else {
//						updateProcessedFactWithInClause(newFactHrList, clientBatchSize);
//					}
//				}
//				List<Object> updateFactHrList = entry.getValue().getNodeList(TxnType.UPDATE);
//				if (!updateFactHrList.isEmpty()) {
//					if (insertProcessed) {
//						if (isInsertProcessedWithMultipleTable) {
//							insertProcessedFactMultiTable(updateFactHrList);
//						} else {
//							insertProcesedFact(updateFactHrList);
//						}
//					} else {
//						updateProcessedFactWithInClause(updateFactHrList, clientBatchSize);
//					}
//				}
//			}
		}

	}

	/**
	 * @param updateMetricList
	 * @throws Exception
	 * 
	 * same as insert 
	 * 
	 */
	private void processMetricNodeBatchUpdates(List<Object> updateMetricList) throws Exception {
		MetricNode dummyNode = (MetricNode) updateMetricList.get(0);
		MetricKey dummyKey = (MetricKey) dummyNode.getKey();
		Space space = schemaManager.getOrCreateMetricSchema(dummyKey.getSchemaName(), dummyKey.getCubeName(),
				dummyKey.getDimensionHierarchyName());
		List<Tuple> tupleList = new ArrayList<Tuple>();

		for (Object node : updateMetricList) {
			MetricNode metricNode = (MetricNode) node;
			Tuple metricTuple = nodeToTuple(metricNode);
			tupleList.add(metricTuple);
		}

		space.putAll(tupleList);
	}

	private void processFactBatchSave(List<Object> saveFactList) throws Exception {
		FactKeyImpl dummyKey = (FactKeyImpl) ((Fact)saveFactList.get(0)).getKey();
		Space space = schemaManager.getOrCreateFactSchema(dummyKey.getSchemaName());
		List<Tuple> factTupleList = new ArrayList<Tuple>();
		for (Object factObject : saveFactList) {
			Fact fact = (Fact) factObject;
			Tuple tuple= factToTuple(fact);
			factTupleList.add(tuple);
		}
		space.putAll(factTupleList);
	}

	private void processMetricNodeBatchInsert(List<Object> newMetricList) throws Exception {

		MetricNode dummyNode = (MetricNode) newMetricList.get(0);
		MetricKey dummyKey = (MetricKey) dummyNode.getKey();
		Space space = schemaManager.getOrCreateMetricSchema(dummyKey.getSchemaName(),dummyKey.getCubeName(), dummyKey.getDimensionHierarchyName());
		List<Tuple> tupleList = new ArrayList<Tuple>();
		
		for(Object node : newMetricList){
			MetricNode metricNode = (MetricNode)node;
			Tuple metricTuple = nodeToTuple(metricNode);
			tupleList.add(metricTuple);
		}
		SpaceResultList spaceResultList = space.putAll(tupleList);
	}

	private String getIdentifier(MetricKey mKey) {
		return BatchedNodePerSchema.NodeType.METRIC + DBConstant.SEP + mKey.getSchemaName() + DBConstant.SEP
				+ mKey.getCubeName() + DBConstant.SEP + mKey.getDimensionHierarchyName();
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
}

