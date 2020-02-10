package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

/**
 * Boolean properties can only accept 'true' or 'false' as a value
 *
 */
public class SynBooleanType extends SynPrimitiveType {

	public SynBooleanType() {
		super(boolean.class);
	}

	public Object cloneThis() throws Exception {
		SynBooleanType clone = new SynBooleanType();
		super.cloneThis(clone);
		return clone;
	}
}