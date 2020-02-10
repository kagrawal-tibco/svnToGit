package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;

public class MultiFilterImpl extends RuleTemplateInstanceObject implements MultiFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5223570507083214135L;
	private String matchType;
	private BuilderSubClause subClause;

	public MultiFilterImpl() {
		this.matchType = "Match Any";
		this.subClause = new BuilderSubClauseImpl();
	}
	
	public MultiFilterImpl(String matchType) {
		this.matchType = matchType;
		this.subClause = new BuilderSubClauseImpl();
	}

	@Override
	public String getMatchType() {
		return this.matchType;
	}

	@Override
	public void setMatchType(String value) {
		this.matchType = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, MATCH_TYPE_FEATURE_ID, this, value);
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
	public String getFilterId() {
		return filterId;
	}
	
	@Override
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
}
