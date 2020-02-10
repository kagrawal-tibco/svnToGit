package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDRestrictionSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDSimpleTypeDefinition;

/**
 * Object properties can only accept any Object as a value
 *
 */
public class SynObjectType extends SynXSDRestrictionSimpleTypeDefinition {

	public SynObjectType() {
		super();
	}

	public SynObjectType(String name, SynXSDSimpleTypeDefinition base) {
		super(name, base);
	}

	public Object cloneThis() throws Exception {
		SynObjectType clone = new SynObjectType();
		super.cloneThis(clone);
		return clone;
	}
}