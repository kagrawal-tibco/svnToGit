package com.tibco.cep.studio.core.index.resolution;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public class SimpleResolutionContext extends DefaultResolutionContext {

	private List<GlobalVariableDef> fGlobalVariables;

	public SimpleResolutionContext(ScopeBlock scopeBlock) {
		super(scopeBlock);
		fGlobalVariables = new ArrayList<GlobalVariableDef>();
	}
	
	public void addGlobalVariable(GlobalVariableDef def) {
		if (!fGlobalVariables.contains(def)) {
			fGlobalVariables.add(def);
		}
	}

	public List<GlobalVariableDef> getGlobalVariables() {
		return fGlobalVariables;
	}
	
}
