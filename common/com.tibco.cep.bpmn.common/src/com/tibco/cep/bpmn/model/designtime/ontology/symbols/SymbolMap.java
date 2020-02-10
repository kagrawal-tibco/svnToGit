package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;

public interface SymbolMap {

	public EObject getFlowElement();
	
	public boolean isRootMap();

	public ProcessSymbolMap getProcessSymbolMap();

	public Collection<SymbolMap> getIncomingMaps();

	public Collection<SymbolMap> getOutgoingMaps();

	public Stack<SymbolEntry> getSymbolStack();

	public SymbolMap findSymbolMap(EObject eObj);

	public Map<String, EObject> getScopeSymbolEntries();

	public Map<String, EObject> getScopeSymbolEntries(Collection<SymbolMap> visited);

	public List<EObject> findScopeSymbolByName(String name);
	
	public Map<String,EObject> getSymbolEntries();
	
	public EObject pushSymbol(final EObject item);

	public EObject popSymbol();

	public EObject addSymbol(EObject symbol);
	
	public EObject addSymbol(String name, String type);
	
	public EObject generateSymbol(String name, String type);

	public SymbolMap getParentSymbolMap();


}
