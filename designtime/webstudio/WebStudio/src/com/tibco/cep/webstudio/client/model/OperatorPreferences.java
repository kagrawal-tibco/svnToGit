package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

public class OperatorPreferences {
	
	private String fieldType;
	private List<IBuilderOperator> commandOperators;
	private List<IBuilderOperator> filterOperators;
	
	
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public List<IBuilderOperator> getCommandOperators() {
		return commandOperators;
	}
	public void setCommandOperators(List<IBuilderOperator> commandOperators) {
		this.commandOperators = commandOperators;
	}
	public List<IBuilderOperator> getFilterOperators() {
		return filterOperators;
	}
	public void setFilterOperators(List<IBuilderOperator> filterOperators) {
		this.filterOperators = filterOperators;
	}
	
	public OperatorPreferences() {
	}
	
	public OperatorPreferences(OperatorPreferences operatorPreferences){
		this.fieldType = operatorPreferences.fieldType;
		this.commandOperators = new ArrayList<IBuilderOperator>(operatorPreferences.commandOperators);
		this.filterOperators = new ArrayList<IBuilderOperator>(operatorPreferences.filterOperators);
	} 
}