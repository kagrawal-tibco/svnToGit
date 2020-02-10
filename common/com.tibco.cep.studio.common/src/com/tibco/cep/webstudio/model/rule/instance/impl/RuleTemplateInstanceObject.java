package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.IRuleTemplateInstanceObject;

public abstract class RuleTemplateInstanceObject implements IRuleTemplateInstanceObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6657927904640192008L;
	private transient List<IInstanceChangedListener> listeners = new ArrayList<IInstanceChangedListener>();
	protected String filterId;
	
	protected void fireInstanceChanged(IInstanceChangedEvent changeEvent) {
		for (IInstanceChangedListener listener : listeners) {
			listener.instanceChanged(changeEvent);
		}
	}
	
	@Override
	public void addChangeListener(IInstanceChangedListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeChangeListener(IInstanceChangedListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
}
