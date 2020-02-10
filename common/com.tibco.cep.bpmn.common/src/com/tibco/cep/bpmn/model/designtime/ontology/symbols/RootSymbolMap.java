package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;


public interface RootSymbolMap extends SymbolMap {

	Collection<SymbolEntry> getGlobalSymbolEntries();
	
	/**
	 * @param processName
	 * @return
	 */
	public ProcessSymbolMap getProcessSymbolMap(EObject process);
	
	public void refresh();
}
