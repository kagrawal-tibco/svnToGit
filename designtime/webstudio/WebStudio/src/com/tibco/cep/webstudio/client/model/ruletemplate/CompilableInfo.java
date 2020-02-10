package com.tibco.cep.webstudio.client.model.ruletemplate;

import java.io.Serializable;
import java.util.List;


public class CompilableInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String conditionText;
	private String actionText;
	private String description;
	private String name;
	private int priority;
	private List<SymbolInfo> declarations;

	public String getConditionText() {
		return conditionText;
	}

	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}

	public String getActionText() {
		return actionText;
	}

	public void setActionText(String actionText) {
		this.actionText = actionText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public List<SymbolInfo> getDeclarations() {
		return declarations;
	}

	public void setDeclarations(List<SymbolInfo> declarations) {
		this.declarations = declarations;
	}

}
