package com.tibco.cep.webstudio.client.diff;

import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;

/**
 * 
 * @author moshaikh
 * 
 */
public class ModificationEntry {
	private ModificationType modificationType;
	private String previousValue;
	private boolean applied = false;
	private String currentValue;

	private Object previousValueObj;
	private Object currentValueObj;

	public ModificationEntry(ModificationType modificationType) {
		this.modificationType = modificationType;
	}

	public ModificationEntry(ModificationType modificationType,
			String previousValue) {
		this.modificationType = modificationType;
		this.previousValue = previousValue;
	}

	public ModificationEntry(ModificationType modificationType,
			String previousValue, String currentValue) {
		this(modificationType, previousValue);
		this.currentValue = currentValue;
	}

	public ModificationEntry(ModificationType modificationType, Object previousValueObj, Object currentValueObj) {
		this.previousValueObj = previousValueObj;
		this.currentValueObj = currentValueObj;
		if (previousValueObj instanceof SingleFilterImpl) {
			this.previousValue = DiffHelper.getFilterAsString((SingleFilterImpl) previousValueObj);
		}
		if (currentValueObj instanceof SingleFilterImpl) {
			this.currentValue = DiffHelper.getFilterAsString((SingleFilterImpl) currentValueObj);
		}
		this.modificationType = modificationType;
	}

	public void setModificationType(ModificationType modificationType) {
		this.modificationType = modificationType;
	}
	
	public ModificationType getModificationType() {
		return modificationType;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public boolean isApplied() {
		return applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}
	
	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public Object getPreviousValueObj() {
		return previousValueObj;
	}

	public Object getCurrentValueObj() {
		return currentValueObj;
	}
}
