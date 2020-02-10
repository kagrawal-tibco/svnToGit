package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

public class PropertyNode extends StudioNavigatorNode {

	public PropertyNode(PropertyDefinition propertyDef, boolean isSharedElement) {
		super(propertyDef, isSharedElement);
	}

	public PROPERTY_TYPES getType() {
		return ((PropertyDefinition) getEntity()).getType();
	}

}
