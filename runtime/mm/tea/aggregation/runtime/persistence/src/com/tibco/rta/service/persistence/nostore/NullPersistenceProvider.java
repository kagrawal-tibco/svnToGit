package com.tibco.rta.service.persistence.nostore;

import java.util.List;
import java.util.Properties;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.util.LoggerUtil;

public class NullPersistenceProvider extends AbstractStartStopServiceImpl implements PersistenceService {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	
	@Override
	public void init(Properties config) throws Exception {
		super.init(config);
		LoggerUtil.log(LOGGER, Level.ERROR, " ################# Initializing Null - Persistence Service ###############");
	}
	
	@Override
	public <N> RtaNode getNode(Key key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(RtaNode node) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(RtaNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public RtaNode getParentNode(RtaNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Browser<Fact> getFactBrowser(RtaNode node,
			List<MetricFieldTuple> orderByList) {
		// return empty browser
		return new EmptyIterator<Fact>();
	}

	@Override
	public void createSchema(RtaSchema schema) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFact(RtaNode metricNode, Fact fact) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFact(RtaNode metricNode, Fact fact) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Fact fact) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void printAllNodes() {
		// TODO Auto-generated method stub

	}

	@Override
	public Browser<MetricNode> getMetricNodeBrowser(QueryDef queryDef)
			throws Exception {
		// return empty browser
		return new EmptyIterator<MetricNode>();
	}

	@Override
	public void delete(Fact node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginTransaction() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void commit() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveProcessedFact(Fact fact, String schemaName,
			String cubeName, String hierarhyName) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProcessedFact(Fact fact, String schemaName,
			String cubeName, String hierarhyName) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Browser<Fact> getUnProcessedFact(String schemaName) throws Exception {
		// return empty browser
		return new EmptyIterator<Fact>();
	}

	@Override
	public Browser<Fact> getUnProcessedFact(String schemaName, String cubeName,
			String hierarchyName) throws Exception {
		// return empty browser
		return new EmptyIterator<Fact>();
	}

	@Override
	public RuleMetricNodeState getRuleMetricNode(String ruleName,
			String actionName, MetricKey key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRuleMetricNode(RuleMetricNodeStateKey key)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveRuleMetricNode(RuleMetricNodeState rmNode) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Browser<MetricNode> getMatchingAssets(String schemaName,
			String cubeName, String hierarchyName, Fact fact) throws Exception {
		// return empty browser
		return new EmptyIterator<MetricNode>();
	}


	@Override
	public void applyTransaction(boolean retryInfinite) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Browser<MetricNode> getSnapshotMetricNodeBrowser(QueryDef queryDef)
			throws Exception {
		// return empty browser
		return new EmptyIterator<MetricNode>();
	}

	@Override
	public void save(RtaNode node, boolean isNew) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long purgeMetricsForHierarchy(String schemaName, String cubeName,
			String hierarchyName, long purgeOlderThan) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long purgeMetricsForQualifier(String schemaName,
			Qualifier qualifier, long purgeOlderThan, boolean storeFacts,
			boolean storeProcessedFacts) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertRule(RuleDef ruleDef) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public RuleDef getRule(String ruleName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RuleDef> getAllRuleNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRule(String ruleName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateRule(RuleDef ruleDef) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Browser<RuleMetricNodeState> getScheduledActions(String schemaName,
			String cubeName, String hierarchyName, long currentTimeMillis) {
		// return empty browser
		return new EmptyIterator<RuleMetricNodeState>();
	}

	@Override
	public boolean isSortingProvided() {
		// TODO Auto-generated method stub
		return false;
	}

}
