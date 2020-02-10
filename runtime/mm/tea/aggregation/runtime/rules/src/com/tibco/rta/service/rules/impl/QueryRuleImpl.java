package com.tibco.rta.service.rules.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.StreamingQueryEvaluator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.mutable.MutableRule;
import com.tibco.rta.service.rules.RuleService;

public class QueryRuleImpl implements MutableRule {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_ACTIONS.getCategory());

	protected StreamingQueryEvaluator setConditionEvaluator;

	protected Map <String, Action> setActionList = new LinkedHashMap <String, Action>();

	protected RuleService ruleService;

	protected RuleDef ruleDef;

	public QueryRuleImpl(RuleDef ruleDef) throws Exception {
		this.ruleService = ServiceProviderManager.getInstance().getRuleService();
		this.ruleDef = ruleDef;
		if (ruleDef.getSetCondition() !=null) {
			this.setConditionEvaluator = new StreamingQueryEvaluator(ruleDef.getSetCondition());
		}
	}

	@Override
	public String getName() {
		return ruleDef.getName();
	}

	@Override
	public RuleDef getRuleDef() {
		return ruleDef;
	}

	@Override
	public void addClearAction(Action action) {
	}

	@Override
	public void addSetAction(Action action) {
		setActionList.put(action.getActionDef().getName(), action);

	}

	@Override
	public Action getSetAction(String actionName) {
		return setActionList.get(actionName);
	}

	@Override
	public Action getClearAction(String actionName) {
		return null;
	}

	@Override
	public Collection <Action> getSetActions() {
		return setActionList.values();
	}

	@Override
	public Collection <Action> getClearActions() {
		return null;
	}

	@Override
	public void eval(MetricNodeEvent nodeEvent) throws Exception {
		MetricNode mNode = nodeEvent.getMetricNode();

		if (setConditionEvaluator ==null) {
			return;
		}
		boolean eval = setConditionEvaluator.eval(mNode);
		if (eval) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "QueryRuleImpl, SetCondition evaluated true for MetricNode [%s]", mNode);
			}
			handleSetConditionForSetActions(nodeEvent);
		}
	}

	private void handleSetConditionForSetActions(MetricNodeEvent nodeEvent) {
		try {
			MetricKey mKey = (MetricKey)nodeEvent.getMetricNode().getKey();
			for (Action action: setActionList.values()) {
				handleSetConditionForSetAction(action, nodeEvent);
			}
		} catch ( Exception e ) {
			LOGGER.log(Level.ERROR, "Error while handling set conditions", e);
		}
	}

	private void handleSetConditionForSetAction(Action action, MetricNodeEvent nodeEvent) {
		ActionDef actionDef = action.getActionDef();
		try {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Firing query action: %s", actionDef.getName());
			}
			action.performAction(this, nodeEvent);
		} catch ( Exception e ) {
			LOGGER.log(Level.ERROR, "Error while handlig query action %s", e, actionDef.getName());
		}
	}
}
