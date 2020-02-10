package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Compilable;
import com.tibco.cep.designtime.model.rule.ScopeContainer;
import com.tibco.cep.designtime.model.rule.Symbols;

public abstract class CompilableAdapter<C extends com.tibco.cep.designtime.core.model.rule.Compilable> extends EntityAdapter<C> implements Compilable, ScopeContainer, ICacheableAdapter {

	public CompilableAdapter(C adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	protected C getAdapted() {
		return adapted;
	}
	
	public String getActionText() {
		return adapted.getActionText();
	}

	public int getCompilationStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getConditionText() {
		return adapted.getConditionText();
	}

	public String getReturnType() {
		return adapted.getReturnType();
	}

	public Symbols getScope() {
		return new SymbolsAdapter(adapted.getSymbols(),emfOntology);
	}

}
