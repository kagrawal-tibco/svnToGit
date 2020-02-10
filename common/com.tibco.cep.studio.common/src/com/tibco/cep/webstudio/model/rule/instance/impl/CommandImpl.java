package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;

public class CommandImpl extends RuleTemplateInstanceObject implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5667897696352122624L;
	private String actionType;
	private String alias;
	private String type;
	private BuilderSubClause subClause;

	@Override
	public String getActionType() {
		return this.actionType;
	}

	@Override
	public void setActionType(String value) {
		this.actionType = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, ACTION_TYPE_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public BuilderSubClause getSubClause() {
		return this.subClause;
	}

	@Override
	public void setSubClause(BuilderSubClause value) {
		this.subClause = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, SUB_CLAUSE_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public String getAlias() {
		return this.alias;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setAlias(String alias) {
		this.alias = alias;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, ALIAS_TYPE_FEATURE_ID, this, alias);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void setType(String type) {
		this.type = type;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, TYPE_FEATURE_ID, this, type);
		fireInstanceChanged(changeEvent);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof CommandImpl)) {
			return false;
		}
		
		CommandImpl command = (CommandImpl) obj;
		return (this.getActionType().equals(command.getActionType()) && this.getAlias().equals(command.getAlias()) && this.getType().equals(command.getType()));
	}
	
	@Override
	public String getFilterId() {
		return filterId;
	}
	
	@Override
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
}
