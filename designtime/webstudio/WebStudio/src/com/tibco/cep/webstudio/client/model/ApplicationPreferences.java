package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPreferences {

	private List<OperatorPreferences> operatorPreferences;

	public List<OperatorPreferences> getOperatorPreferences() {
		return this.operatorPreferences;
	}

	public void setOperatorPreferences(List<OperatorPreferences> operatorPreferences) {
		this.operatorPreferences = operatorPreferences;
	}

	public ApplicationPreferences() {
	}
	
	public ApplicationPreferences(ApplicationPreferences applicationPreferences) {
		List<OperatorPreferences> operatorPreferences = new ArrayList<OperatorPreferences>();
		if(applicationPreferences != null) {
			for(OperatorPreferences opPreferences: applicationPreferences.operatorPreferences) {
				operatorPreferences.add(new OperatorPreferences(opPreferences));
			}
		}
		this.operatorPreferences = operatorPreferences;
	}
	
}
