package com.tibco.rta.bemm.actions;

import java.util.Properties;

import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;

public class StreamingQueryActionHandler implements ActionHandlerContext {

	@Override
	public void init(Properties paramProperties) {
		
	}

	@Override
	public void stop() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Action getAction(Rule rule, ActionDef actionDef) {
		return StreamingQueryAction.getInstance(rule, actionDef, this);
	}

}
