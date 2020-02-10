package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Clause;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;

public abstract class ClauseImpl extends RuleTemplateInstanceObject implements Clause {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7293514330996633236L;
	private BuilderSubClause subClause;

	public ClauseImpl() {
		// TODO Auto-generated constructor stub
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

}
