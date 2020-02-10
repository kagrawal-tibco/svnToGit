package com.tibco.rta.common.service;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

import java.util.List;
import java.util.Map;

public interface Transaction {

	MetricNode getNode(Key key);

	Fact getFact(Key key);

	void commit() throws Exception;

	void rollback() throws Exception;

	void clear();

	List<MetricNodeEvent> getMetricNodeChanges();

	void beginTransaction() throws Exception;


	void recordNewMetricNode(MetricNode metricNode);

	void recordUpdateMetricNode(MetricNode metricNode);

	void recordDeleteMetricNode(MetricNode metricNode);

	void recordNewFact(Fact fact);
	
	void recordUpdateFact(Fact fact);
	
	void recordDeleteFact(Fact node);

	void recordMetricNodeChild(MetricNode metricNode, Fact fact);

	void mergeTransaction(Transaction txn);

	Map<Key, RtaTransactionElement<?>> getTxnList();

	void recordfactProcessedForHr(Fact fact, DimensionHierarchy hr);
	
	void setClientBatchSize(int batchSize);
	
	int getClientBatchSize();
	
	RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception;

	void recordUpdateRuleMetricNode(RuleMetricNodeState actionState);

	void recordNewRuleMetricNode(RuleMetricNodeState actionState);

	void recordDeleteRuleMetricNode(RuleMetricNodeState key);

	void recordUpdateAlertNodeState(AlertNodeState actionState);

	void recordNewAlertNodeState(AlertNodeState actionState);

	void recordDeleteAlertNodeState(AlertNodeState actionState);

	void commit(boolean retryInfinite) throws Exception;

	void remove();
	
}