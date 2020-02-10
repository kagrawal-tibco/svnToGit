package com.tibco.cep.webstudio.model.rule.instance;


public class InstanceChangedEvent implements IInstanceChangedEvent {

	private int changeType;
	private int featureId;
	private IRuleTemplateInstanceObject parentObject;
	private Object childObject;
	
	public InstanceChangedEvent(int changeType, int featureId, IRuleTemplateInstanceObject parent, Object child) {
		this.changeType = changeType;
		this.featureId = featureId;
		this.parentObject = parent;
		this.childObject = child;
	}

	@Override
	public int getFeatureId() {
		return featureId;
	}

	@Override
	public int getChangeType() {
		return changeType;
	}

	@Override
	public IRuleTemplateInstanceObject getParentObject() {
		return parentObject;
	}

	@Override
	public Object getValue() {
		return childObject;
	}

}
