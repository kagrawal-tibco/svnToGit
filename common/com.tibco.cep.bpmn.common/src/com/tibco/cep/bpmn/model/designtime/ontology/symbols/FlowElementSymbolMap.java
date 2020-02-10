package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;

public class FlowElementSymbolMap implements SymbolMap {

	protected EObject flowElement;
	protected SymbolMap parentSymbolMap;
	protected Set<SymbolMap> incomingMaps = new HashSet<SymbolMap>();
	protected Set<SymbolMap> outgoingMaps = new HashSet<SymbolMap>();
	
	
	protected Stack<SymbolEntry> symbolMap = new Stack<SymbolEntry>();	
	
	public FlowElementSymbolMap(EObject eObj,SymbolMap parentMap) {
		this.flowElement = eObj;
		this.parentSymbolMap = parentMap;
	}
	
	public EObject addSymbol(EObject sym) {
		return pushSymbol(sym);
	}
	
	public EObject addSymbol(String name,String type) {
		return pushSymbol(generateSymbol(name, type));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowElementSymbolMap other = (FlowElementSymbolMap) obj;
		if (flowElement == null) {
			if (other.flowElement != null)
				return false;
		} else if (!flowElement.equals(other.flowElement))
			return false;
		return true;
	}
	
	public List<EObject> findScopeSymbolByName(String name) {
		String sname = null;
		List<EObject> slist = new LinkedList<EObject>();
		Map<String, EObject> scopeSymbols = getScopeSymbolEntries();
		String prefix = BpmnCommonModelUtils.BPMN_VARIABLE_PREFIX+BpmnCommonModelUtils.BPMN_PREFIX_SEPARATOR+name;
		int symbolMapSize = scopeSymbols.size() == 0 ? 1 : scopeSymbols.size();
		for(int i=0; i < symbolMapSize; i++) {
			sname = prefix+i;			
			if(scopeSymbols.containsKey(sname)){
				slist.add(scopeSymbols.get(sname));
			}
		}
		return slist;
	}
	
	
	public SymbolMap findSymbolMap(EObject eObj) {
		if(eObj == flowElement) return this;
		ProcessSymbolMap processMap = null;
		if(this.isProcessMap()) {
			processMap = (ProcessSymbolMap) this;
		} else {
			processMap = getProcessSymbolMap();
		}
		return processMap.getSymbolMapRegistry().get(eObj);
	}
	
	public EObject generateSymbol(String name,String type) {
		String sname = null;
		Map<String, EObject> symbolMap = getScopeSymbolEntries();
		if(symbolMap.size() == 0) {
			symbolMap = getAllSymbolEntries();
		}
		String prefix = BpmnCommonModelUtils.BPMN_VARIABLE_PREFIX+BpmnCommonModelUtils.BPMN_PREFIX_SEPARATOR+name;
		int symbolMapSize = symbolMap.size() == 0 ? 1 : symbolMap.size();
		for(int i=0; i < symbolMapSize; i++) {
			sname = prefix+i;			
			if(!symbolMap.containsKey(sname)){
				break;
			}
		}
		return BpmnCommonModelUtils.createBpmnSymbol(name, type);
	}
	
	private Map<String, EObject> getAllSymbolEntries() {
		Map<String,EObject> allentries = new HashMap<String,EObject>();
		Collection<FlowElementSymbolMap> symbolMaps = getProcessSymbolMap().getSymbolMapRegistry().values();
		for(FlowElementSymbolMap smap:symbolMaps) {
			Set<Entry<String, EObject>> entries = smap.getSymbolEntries().entrySet();
			for(Entry<String, EObject> entry:entries) {
				allentries.put(entry.getKey(), entry.getValue());
			}
		}
		return allentries;
	}
	
	public EObject getFlowElement() {
		return flowElement;
	}
	public Set<SymbolMap> getIncomingMaps() {
		return incomingMaps;
	}
	
	public Set<SymbolMap> getOutgoingMaps() {
		return outgoingMaps;
	}
	
	
	public SymbolMap getParentSymbolMap() {
		return parentSymbolMap;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getProcess(org.eclipse.emf.ecore.EObject)
	 */
	protected EObject getProcess(EObject element) {
		if(BpmnModelClass.PROCESS.isSuperTypeOf(element.eClass())) {
			return element;
		}
		EObject container = element.eContainer();
		while(container != null && !BpmnModelClass.PROCESS.isSuperTypeOf(container.eClass())) {
			container = container.eContainer();
		}
		return container;
	}
	
	public ProcessSymbolMap getProcessSymbolMap() {
		SymbolMap smap = getParentSymbolMap();
		if(smap instanceof ProcessSymbolMap) {
			return (ProcessSymbolMap) smap;
		}
		while(smap != null ) {
			smap = smap.getParentSymbolMap();
			if(smap instanceof ProcessSymbolMap) {
				break;
			}
		}
		return (ProcessSymbolMap) smap;
	}
	
	public Map<String, EObject> getScopeSymbolEntries() {
		Collection<SymbolMap> visited = new HashSet<SymbolMap>();
		return getScopeSymbolEntries(visited);		
	}
	
	public Map<String, EObject> getScopeSymbolEntries(Collection<SymbolMap> visited) {
		Map<String, EObject> symbols = new HashMap<String, EObject>();
		if(visited.contains(this)) {
			return Collections.emptyMap();
		} else {
			visited.add(this);
		}
		if(incomingMaps == null) {
			return getSymbolEntries();
		} else {
			symbols.putAll(getSymbolEntries());
		}
		for(SymbolMap incomingMap: incomingMaps) {
			symbols.putAll(incomingMap.getScopeSymbolEntries(visited));
		}
		return symbols;
	}
	
	public Map<String,EObject> getSymbolEntries() {
		Map<String,EObject> map = new HashMap<String,EObject>();
		for(SymbolEntry e: symbolMap) {
			map.put(e.getKey(),e.getValue());
		}
		return map;
	}
	
	public Stack<SymbolEntry> getSymbolStack() {
		return symbolMap;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((flowElement == null) ? 0 : flowElement.hashCode());
		return result;
	}
	
	public boolean isProcessMap() {
		return this instanceof ProcessSymbolMap;
	}

	public boolean isRootMap() {
		return this instanceof RootSymbolMap;
	}
	

	public EObject popSymbol() {
		SymbolEntry entry = symbolMap.pop();
		return entry.getValue();
	}

	public EObject pushSymbol(final EObject item) {
		SymbolEntry entry = symbolMap.push(new SymbolEntryImpl(item));
		return entry.getValue();
	}
	
	

}
