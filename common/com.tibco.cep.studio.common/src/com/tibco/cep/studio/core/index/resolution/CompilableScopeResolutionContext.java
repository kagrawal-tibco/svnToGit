package com.tibco.cep.studio.core.index.resolution;

import java.util.List;

import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public class CompilableScopeResolutionContext extends DefaultResolutionContext {
	
	private CompilableScope fCompilableScope;

	public CompilableScopeResolutionContext(ScopeBlock scopeBlock, CompilableScope element) {
		super(scopeBlock);
		this.fCompilableScope = element;
	}

	@Override
	public List<GlobalVariableDef> getGlobalVariables() {
		return fCompilableScope.getGlobalVariables();
	}

}
