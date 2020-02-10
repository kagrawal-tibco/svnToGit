package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

/**
 * @deprecated
 */
public class SynXMLType extends SynStringType {

	public SynXMLType() {
		super();
		// TODO: must imlement XML validation
	}

	public Object cloneThis() throws Exception {
		SynXMLType clone = new SynXMLType();
		super.cloneThis(clone);
		return clone;
	}
}
