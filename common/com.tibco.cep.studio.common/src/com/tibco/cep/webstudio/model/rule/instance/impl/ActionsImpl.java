package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.Actions;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;

public class ActionsImpl extends RuleTemplateInstanceObject implements Actions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 443705085762825850L;
	private List<Command> actions;

	public ActionsImpl() {
		super();
	}

	@Override
	public List<Command> getActions() {
		if (this.actions == null) {
			this.actions = new ArrayList<Command>();
		}
		return this.actions;
	}
	
	@Override
	public void addAction(Command command) {
		if (!getActions().contains(command)) {
			getActions().add(command);
			InstanceChangedEvent instanceChangedEvent = new InstanceChangedEvent(IInstanceChangedEvent.ADDED, ACTIONS_FEATURE_ID, this, command);
			fireInstanceChanged(instanceChangedEvent);
		}
	}

	@Override
	public void removeAction(Command command) {
		if (getActions().contains(command)) {
			getActions().remove(command);
			InstanceChangedEvent instanceChangedEvent = new InstanceChangedEvent(IInstanceChangedEvent.REMOVED, ACTIONS_FEATURE_ID, this, command);
			fireInstanceChanged(instanceChangedEvent);
		}
	}
}
