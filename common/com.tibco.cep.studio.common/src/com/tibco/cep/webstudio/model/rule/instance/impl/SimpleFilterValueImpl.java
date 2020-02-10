package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;

public class SimpleFilterValueImpl extends RuleTemplateInstanceObject implements SimpleFilterValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4423524257776917494L;
	private String value;

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, VALUE_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}
}
