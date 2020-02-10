package com.tibco.cep.studio.dashboard.ui.navigator.providers;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;

public class MetricPropertyNode extends PropertyNode {

	public MetricPropertyNode(PropertyDefinition propertyDef, boolean isSharedElement) {
		super(propertyDef, isSharedElement);
	}

	public PROPERTY_TYPES getType() {
		return ((PropertyDefinition) getEntity()).getType();
	}

}
