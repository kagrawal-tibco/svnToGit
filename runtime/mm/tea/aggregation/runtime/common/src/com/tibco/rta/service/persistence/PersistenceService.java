package com.tibco.rta.service.persistence;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;

import java.util.List;



/**
 * 
 * @author bgokhale
 * 
 * This service provides persistence of Metrics to a store
 * 
 */

public interface PersistenceService extends StartStopService {
	
	/**
	 * Returns a metric node corresponding to the given parameters. null if not exist
	 * @param key Key for looup
	 * @return corresponding node or null if not found
	 * @throws Exception 
	 */
	
	<N> RtaNode getNode (Key key) throws Exception;


	/**
	 * Save a node to the store.
	 * 
	 * @param node
	 * @throws Exception 
	 */
	void save (RtaNode node) throws Exception;
	
	/**
	 * Delete a node from the store
	 * 
	 * @param node
	 */
	void delete (RtaNode node);
	

	/**
	 * Return the node's parent node
	 * @param node
	 * @return
	 */
	
	RtaNode getParentNode(RtaNode node);
	
	/**
	 * Iterate over all of the fact nodes corresponding to this node.
	 * @param node
	 * @param orderByList 
	 * @return
	 */

	Browser<Fact> getFactBrowser(RtaNode node, List<MetricFieldTuple> orderByList);
	
	void createSchema(RtaSchema schema) throws Exception;
	
	void addFact(RtaNode metricNode, Fact fact) throws Exception;
	
	void deleteFact(RtaNode metricNode, Fact fact) throws Exception;
	
	void save(Fact fact) throws Exception;
	
	void printAllNodes();
	
	Browser<MetricNode> getMetricNodeBrowser(QueryDef queryDef) throws Exception;

	void delete(Fact node);

	void beginTransaction() throws Exception;

	void commit() throws Exception;

	void rollback() throws Exception;

	void saveProcessedFact(Fact fact, String schemaName, String cubeName,
			String hierarhyName) throws Exception;
	
	void updateProcessedFact(Fact fact, String schemaName, String cubeName,
			String hierarhyName) throws Exception;
	
	Browser<Fact> getUnProcessedFact(String schemaName) throws Exception;
	
	Browser<Fact> getUnProcessedFact(String schemaName, String cubeName, String hierarchyName) throws Exception;

	RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception;

	void deleteRuleMetricNode(RuleMetricNodeStateKey key) throws Exception;

	void saveRuleMetricNode(RuleMetricNodeState rmNode) throws Exception;

	Browser<MetricNode> getMatchingAssets(String schemaName, String cubeName, String hierarchyName, Fact fact) throws Exception;
	
	/**
	 * 
	 * @param retryInfinite set to false if should not retry operation infinitely. (for certain synchronous calls)
	 * @throws Exception
	 */
	void applyTransaction(boolean retryInfinite) throws Exception;

	Browser<MetricNode> getSnapshotMetricNodeBrowser(QueryDef queryDef) throws Exception;

	void save(RtaNode node, boolean isNew) throws Exception;

	long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) throws Exception;

	long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan, boolean storeFacts, boolean storeProcessedFacts) throws Exception;

	void insertRule(RuleDef ruleDef) throws Exception;

	RuleDef getRule(String ruleName) throws Exception;

	List<RuleDef> getAllRuleNames() throws Exception;

	boolean deleteRule(String ruleName) throws Exception;

	void updateRule(RuleDef ruleDef) throws Exception;

	Browser<RuleMetricNodeState> getScheduledActions(String schemaName, String cubeName, String hierarchyName, long currentTimeMillis);
	
	boolean isSortingProvided();

}