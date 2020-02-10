package com.tibco.cep.bpmn.ui.graph.model.controller;

import java.util.Collection;
import java.util.Map;

import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class ModelGraphNodeRegistry<T extends TSEObject> {
	
	//Any BPMN entity which can be rendered by the graph
	private Map<String, T> idNodeRegistry = new java.util.HashMap<String, T>();
	
	public boolean addTSObject(String nodeid, T graphObject) {
		if (!idNodeRegistry.containsKey(nodeid)) {
			return idNodeRegistry.put(nodeid, graphObject) != null;
		}
		return false;
	}
	
	public boolean addTSObject(T graphObject) {
		//Generate id
		String nodeId = BpmnGraphUtils.generateNodeId();
		return addTSObject(nodeId, graphObject);
	}
	
	public T getNode(String nodeid) {
		T object = idNodeRegistry.get(nodeid);
		return object;
	}
	
	public void clearCache() {
		idNodeRegistry.clear();
	}
	
	public boolean containsKey(String id) {
		return idNodeRegistry.containsKey(id);
	}
	
	public Collection<T> getAllNodes(){
		return idNodeRegistry.values();
	}

}
