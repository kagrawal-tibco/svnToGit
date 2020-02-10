package com.tibco.rta.service.rules;

import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.service.rules.impl.RuleRegistry;

import java.util.List;

public interface RuleService extends StartStopService {
	
	void createRule(RuleDef rule) throws Exception;
	
    void updateRule (RuleDef rule) throws Exception;
	
    void deleteRule (String ruleName) throws Exception;
	
    List<RuleDef> getRules() throws Exception;
	
    RuleDef getRuleDef(String ruleName) throws Exception;
	
	ActionHandlerContext getActionHandlerContext(String name);
	
	Rule getRule(String ruleName) throws Exception;

	ActionManager getActionManager();
	
	WorkItemService getSystemMetricsWorkHandler();

	void createAndAssertAlertFactFromAlertNode(AlertNodeState alertNode);

	void clearAlerts(QueryDef queryDef) throws Exception;
	
	boolean isProcessDeleteEvents();
	
	public RuleRegistry getRuleRegistry();

}
