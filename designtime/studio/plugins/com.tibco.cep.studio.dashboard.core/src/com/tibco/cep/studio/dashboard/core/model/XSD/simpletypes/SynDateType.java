package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

/**
 * Date properties corresponds to the java.util.Date
 *
 */
public class SynDateType extends SynPrimitiveType {

	public SynDateType() {
		super(java.util.Date.class);
	}

	public SynDateType(String[] enumValues) {
		super(java.util.Date.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynDateType clone = new SynDateType();
		super.cloneThis(clone);
		return clone;
	}
}