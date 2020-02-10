package com.tibco.cep.studio.core.index.resolution;

import java.util.List;

import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public class RuleElementResolutionContext extends DefaultResolutionContext {
	
	private RuleElement fRuleElement;

	public RuleElementResolutionContext(ScopeBlock scopeBlock, RuleElement element) {
		super(scopeBlock);
		this.fRuleElement = element;
	}

	@Override
	public List<GlobalVariableDef> getGlobalVariables() {
		return fRuleElement.getGlobalVariables();
	}

}
