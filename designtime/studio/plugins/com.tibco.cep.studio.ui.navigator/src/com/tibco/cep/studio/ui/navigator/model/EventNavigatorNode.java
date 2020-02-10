package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

public abstract class EventNavigatorNode extends StudioNavigatorNode {

	public EventNavigatorNode(PropertyDefinition wrappedUserProperty, boolean isSharedElement) {
		super(wrappedUserProperty, isSharedElement);
	}
	
	public PropertyDefinition getUserProperty() {
		return (PropertyDefinition) super.getEntity();
	}

	public String getName() {
		return super.getName();
	}
}
