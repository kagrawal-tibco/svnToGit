package com.tibco.cep.sharedresource.model;

import java.util.LinkedHashMap;
import java.util.Map;

/*
@author ssailapp
@date Feb 5, 2010 1:02:36 PM
 */

public class SharedResModel {
	public Map<String, Object> values;
	public String name;
	
	public SharedResModel() {
		values = new LinkedHashMap<String, Object>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
