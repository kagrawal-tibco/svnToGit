package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.DisplayModel;
import com.tibco.cep.webstudio.model.rule.instance.DisplayProperty;

public class DisplayModelImpl extends RuleTemplateInstanceObject implements DisplayModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3331223027561737516L;
	/**
	 * 
	 */
	private String displayText;
	private String entity;
	private List<DisplayProperty> props;

	public DisplayModelImpl() {
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	@Override
	public void setDisplayProperties(List<DisplayProperty> props) {
		this.props = props;
	}

	@Override
	public List<DisplayProperty> getDisplayProperties() {
		if (props == null) {
			props = new ArrayList<DisplayProperty>();
		}
		return props;
	}
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

}
