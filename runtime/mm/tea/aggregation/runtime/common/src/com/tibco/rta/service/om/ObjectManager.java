package com.tibco.rta.service.om;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.L1Cache;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;

public interface ObjectManager extends StartStopService {

//	Fact getFact(Key key) throws Exception;

	<N> RtaNode getNode(Key key) throws Exception;
	
	<N> RtaNode getNode(Key key, boolean clone) throws Exception;

	void save(Fact fact) throws Exception;
	
	void save(RtaNode node) throws Exception;
	
	void save(Fact fact, DimensionHierarchy dh) throws Exception;

	void delete(Fact node);

	void delete(RtaNode node);

	void addChildFact(RtaNode node, Fact fact) throws Exception;

	void deleteChildFact(RtaNode metricNode, Fact fact) throws Exception;

	void commit(boolean retryInfinte) throws Exception;

	void rollback() throws Exception;

	void beginTransaction() throws Exception;
	
	
	//there is a need to integrate this for performance and other reasons.
	
	RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception;
	
//	RuleMetricNodeState createRuleMetricNode (String ruleName, String actionName, MetricKey key) throws Exception;

	void removeRuleMetricNode(RuleMetricNodeState key) throws Exception;

//	void update(RuleMetricNodeState actionState) throws Exception;

	void save(RuleMetricNodeState actionState);
	
	void save(AlertNodeState alertNodeState);

	void deleteRuleMetricNodeState(String ruleName) throws Exception;

	L1Cache<RuleMetricNodeStateKey, RuleMetricNodeState> getRuleMetricCache();

}
