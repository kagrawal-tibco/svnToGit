package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class LinkGenerator {
	
	private String baseURI;
	
	private Map<String,String> parameters;
	
	public LinkGenerator(String baseURI) {
		this.baseURI = baseURI;
		parameters = new java.util.LinkedHashMap<String, String>();
	}
	
	public LinkGenerator() {
		this("");
	}

	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	public String removeParameter(String key){
		return parameters.remove(key);
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	
	public String toString(){
		StringBuilder sb = new StringBuilder(baseURI);
		Iterator<Entry<String, String>> entryIterator = parameters.entrySet().iterator();
		while (entryIterator.hasNext()) {
			Map.Entry<String, String> entry = entryIterator.next();
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			if (entryIterator.hasNext() == true) {
				sb.append("&");
			}
		}
		return sb.toString();
	}
}