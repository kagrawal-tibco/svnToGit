package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.Binding;

public class BindingImpl extends RuleTemplateInstanceObject implements Binding {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017625273759757212L;
	private String id;
	private String value;

	public BindingImpl() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
