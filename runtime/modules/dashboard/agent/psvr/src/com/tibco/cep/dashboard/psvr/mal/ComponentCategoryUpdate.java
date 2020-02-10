package com.tibco.cep.dashboard.psvr.mal;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;

class ComponentCategoryUpdate {
	
	static enum Operation {Add,Remove,Hide,Unhide,Replace}
	
	MALComponent component;
	
	MALComponent replacement;
	
	Operation operation;

	ComponentCategoryUpdate(MALComponent component, MALComponent replacement, Operation operation) {
		if (component == null){
			throw new IllegalArgumentException("component cannot be null");
		}
		if (operation == Operation.Replace && replacement == null){
			throw new IllegalArgumentException("replacement cannot be null for "+operation);
		}
		this.component = component;
		this.replacement = replacement;
		this.operation = operation;
	}
	
}