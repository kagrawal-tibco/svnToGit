package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDSimpleTypeDefinition;

/**
 * Instance reference properties can only accept instances of MDClassifierInstance as a value
 *
 */
public class SynInstanceReferenceType extends SynObjectType {

	protected SynInstanceReferenceType() {
		super();
	}

	public SynInstanceReferenceType(String name, SynXSDSimpleTypeDefinition base) {
		super(name, base);
	}

	public Object cloneThis() throws Exception {
		SynInstanceReferenceType clone = new SynInstanceReferenceType();
		super.cloneThis(clone);
		return clone;
	}
}