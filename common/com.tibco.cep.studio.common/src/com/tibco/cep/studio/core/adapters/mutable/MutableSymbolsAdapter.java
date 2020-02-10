package com.tibco.cep.studio.core.adapters.mutable;

import java.util.List;

import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.core.adapters.SymbolsAdapter;


public class MutableSymbolsAdapter  extends SymbolsAdapter implements com.tibco.cep.designtime.model.rule.Symbols {

	public MutableSymbolsAdapter(
			List<com.tibco.cep.designtime.core.model.rule.Symbol> symbols,
			Ontology ontology) {
		super(symbols, ontology);
		// TODO Auto-generated constructor stub
	}

	public MutableSymbolsAdapter(Ontology ontology) {
		super(ontology);
		// TODO Auto-generated constructor stub
	}

	public MutableSymbolsAdapter(Symbols original, Ontology ontology) {
		super(original, ontology);
		// TODO Auto-generated constructor stub
	}

	public MutableSymbolsAdapter(	com.tibco.cep.designtime.model.rule.Symbols original,Ontology ontology) {
		super(original, ontology);
		// TODO Auto-generated constructor stub
	}
	
	
	

	

}
