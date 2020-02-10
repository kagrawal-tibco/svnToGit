package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;


public interface ProcessSymbolMap extends SymbolMap {
	
	Map<EObject,FlowElementSymbolMap> getSymbolMapRegistry();
	
	public SymbolMap getElementSymbolMap(EObject flowElement);
	
	public boolean containsElementSymbolMap(EObject flowElement);
	
	public SymbolMap initElementSymbolMap(EObject flowElement);

	Collection<SymbolMap> getOutgoingMaps();

	Collection<SymbolMap> getIncomingMaps();
	
	Collection<SymbolMap> getStartSymbolMaps();
	
	Collection<SymbolMap> getEndSymbolMaps();
	
	SymbolMap getRootSymbolMap();

	public boolean hasRootSymbolMap();

}
