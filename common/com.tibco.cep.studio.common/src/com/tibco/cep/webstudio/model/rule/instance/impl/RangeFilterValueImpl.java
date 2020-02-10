package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.RangeFilterValue;

public class RangeFilterValueImpl extends RuleTemplateInstanceObject implements RangeFilterValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2510139816666664557L;
	/**
	 * 
	 */
	private String minValue;
	private String maxValue;

	@Override
	public String getMinValue() {
		return this.minValue;
	}

	@Override
	public String getMaxValue() {
		return this.maxValue;
	}
	
	@Override
	public void setMinValue(String value) {
		this.minValue = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, MIN_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void setMaxValue(String value) {
		this.maxValue = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, MAX_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}
}
