package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.DisplayProperty;

public class DisplayPropertyImpl extends RuleTemplateInstanceObject implements DisplayProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017625273759757212L;
	private String id;
	private String value;
	private boolean hidden = false;

	public DisplayPropertyImpl() {
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

	@Override
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}
}
