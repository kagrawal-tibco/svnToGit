package com.tibco.cep.designtime;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.model.Ontology;

public class CommonOntologyCache {
	
	private Map<String,Ontology> adapterMap = new HashMap<String,Ontology>();
	public static CommonOntologyCache INSTANCE = new CommonOntologyCache();
	
	private CommonOntologyCache() {	
	}
	
	public void addOntology(String name ,Ontology adapter) {
		if(!adapterMap.containsKey(name)){
			adapterMap.put(name, adapter);
		}
	}
	
	public Ontology getOntology(String name){
		return adapterMap.get(name);
	}
	
	

}
