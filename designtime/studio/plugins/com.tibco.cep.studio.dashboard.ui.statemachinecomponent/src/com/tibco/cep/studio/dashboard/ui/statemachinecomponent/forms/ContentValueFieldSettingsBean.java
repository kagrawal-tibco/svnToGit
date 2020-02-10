package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import com.tibco.cep.studio.dashboard.ui.forms.ValueFieldSettingsBean;

public class ContentValueFieldSettingsBean extends ValueFieldSettingsBean {
	
	public static enum VALUE_OPTION { CONSTANT, FIELD }
	
	private VALUE_OPTION minValueOption;
	
	private String minValue;
	
	private VALUE_OPTION maxValueOption;
	
	private String maxValue;

	public ContentValueFieldSettingsBean(String fieldName, String dataType) {
		super(fieldName, dataType);
	}

	public VALUE_OPTION getMinValueOption() {
		return minValueOption;
	}

	public void setMinValueOption(VALUE_OPTION minValueOption) {
		this.minValueOption = minValueOption;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public VALUE_OPTION getMaxValueOption() {
		return maxValueOption;
	}

	public void setMaxValueOption(VALUE_OPTION maxValueOption) {
		this.maxValueOption = maxValueOption;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	
}