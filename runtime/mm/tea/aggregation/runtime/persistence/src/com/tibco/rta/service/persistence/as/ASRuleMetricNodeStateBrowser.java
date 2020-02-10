package com.tibco.rta.service.persistence.as;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.query.Browser;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

public class ASRuleMetricNodeStateBrowser implements Browser<RuleMetricNodeState> {

	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

	private ASPersistenceService persistenceService;
	private String hierarchyName;
	private String cubeName;
	private String schemaName;
	private Space space;
	private com.tibco.as.space.browser.Browser browser;
	private boolean isStopped;
	private RuleMetricNodeState ruleMetricNode;

	public ASRuleMetricNodeStateBrowser(ASPersistenceService asPersistenceService, String schemaName, String cubeName,
			String hierarchyName, long currentTimeMillis) throws Exception {
		this.persistenceService = asPersistenceService;
		this.schemaName = schemaName;
		this.cubeName = cubeName;
		this.hierarchyName = hierarchyName;
		this.space = persistenceService.getSchemaManager().getOrCreateRuleMetricSchema(schemaName, cubeName,
				hierarchyName);

		BrowserDef browserDef = BrowserDef.create();
		browserDef.setDistributionScope(DistributionScope.ALL);
		browserDef.setTimeScope(TimeScope.SNAPSHOT);
		String query = "(" + ASPersistenceService.RULE_SCHEDULED_TIME_FIELD + "<=" + currentTimeMillis + ") AND ("
				+ ASPersistenceService.RULE_SCHEDULED_TIME_FIELD + "> 0)";
		browser = space.browse(BrowserType.GET, browserDef, query);
	}

	@Override
	public boolean hasNext() {
		try {
			if (isStopped) {
				return false;
			}
			if (ruleMetricNode == null) {
				Tuple ruleMetricTuple = browser.next();
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "Query tuple = {%s} ", ruleMetricTuple);
				}
				ruleMetricNode = persistenceService.fetchRuleMetricNodeFromTuple(ruleMetricTuple, schemaName, cubeName,
						hierarchyName);

				if (ruleMetricNode == null) {
					browser.stop();
					isStopped = true;
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while iterating the rule metric browser", e);
		}
		return false;
	}

	@Override
	public RuleMetricNodeState next() {
		if (isStopped) {
			return null;
		}
		RuleMetricNodeState next = ruleMetricNode;
		ruleMetricNode = null;
		return next;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		try {
			browser.stop();
		} catch (ASException e) {
			LOGGER.log(Level.ERROR, "Error while stopping the browser", e);
		}
		isStopped = true;
	}

}
