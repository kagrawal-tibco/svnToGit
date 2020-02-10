package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.be.model.util.ModelNameUtil;

public abstract class GeneratedStateTransitionRule extends StateTransitionRule {

	public GeneratedStateTransitionRule(String _name) {
		super(_name, null);
	}

	public GeneratedStateTransitionRule(String _name, int _priority) {
		super(_name, null, _priority);
	}
	
	
	//this is so that TypeDescriptor.getUri and Rule.getUri return the same value for generated State machine rules.
	public String getUri() {
		if(uri == null) {
			uri = ModelNameUtil.generatedClassNameToModelPath(getClass().getName());
		}
		return uri;
	}
}
