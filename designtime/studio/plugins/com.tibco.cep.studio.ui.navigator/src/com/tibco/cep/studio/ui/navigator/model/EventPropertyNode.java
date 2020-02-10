package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class EventPropertyNode extends EventNavigatorNode {

	public EventPropertyNode(PropertyDefinition propertyDef, boolean isSharedEntity) {
		super(propertyDef, isSharedEntity);
	}
	
	public PROPERTY_TYPES getType() {
		return ((PropertyDefinition) getUserProperty()).getType();
	}
	
}