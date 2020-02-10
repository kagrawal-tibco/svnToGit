package com.tibco.cep.webstudio.model.rule.instance;


public interface IInstanceChangedEvent {
	
	public static final int ADDED = 0;
	public static final int REMOVED = 1;
	public static final int CHANGED = 2;
	
	public int getFeatureId();
	public int getChangeType();
	public IRuleTemplateInstanceObject getParentObject();
	public Object getValue();
	
}
