package com.tibco.cep.studio.core.util;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class DisplayProperties {

	private String targetName;
	private String displayText;
	private boolean hidden;
	private List<DisplayProperties> displayProperties = new ArrayList<>();
	private PropertyDefinition propDef;
	
	public DisplayProperties(PropertyDefinition propDef) {
		this.propDef = propDef;
	}

	public List<DisplayProperties> getDisplayProperties() {
		return displayProperties;
	}

	public String getTargetName() {
		return targetName;
	}
	
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public String getDisplayText() {
		return displayText == null ? "" : displayText;
	}
	
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public PropertyDefinition getPropertyDefinition() {
		return this.propDef;
	}
	
}
