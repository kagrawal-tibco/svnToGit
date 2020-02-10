package com.tibco.rta.service.persistence.memory.adapter;

import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;

public interface SPMDataServiceAdapter {

	public MetricNode getMetricNode(MetricKey key) throws Exception;

	public void deleteMetricNode(MetricNode node) throws Exception;

	public void deleteRuleMetricNode(RuleMetricNodeStateKey key) throws Exception;

	public void saveMetricNode(MetricNode node) throws Exception;

	public Browser<MetricNode> getMatchingAssets(String schemaName, String cubeName, String hierarchyName, Fact fact) throws Exception;

	public void saveRuleMetricNode(RuleMetricNodeState rmNode) throws Exception;

	public RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception;

	public Browser<MetricNode> getFilterBasedMetricNodeBrowser(QueryByFilterDef queryDef) throws Exception;

	public Browser<MetricNode> getKeyBasedMetricNodeBrowser(QueryByKeyDef queryDef) throws Exception;

	public Browser<MetricNode> getChildMetricNodeBrowser(QueryByKeyDef queryDef) throws Exception;

	public Browser<Fact> getFactBrowser(RtaNode node, List<MetricFieldTuple> orderByList) throws Exception;

	public Browser<RuleMetricNodeState> getScheduledActions(String schemaName, String cubeName, String hierarchyName, long currentTimeMillis) throws Exception;

	public long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) throws Exception;

	public long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan, boolean storeFacts) throws Exception;

	public void createSchema(RtaSchema schema, boolean usePartitioning);

	public void saveFact(Fact fact);

}
