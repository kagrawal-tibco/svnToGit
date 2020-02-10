package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * @deprecated
 */
public class SynPasswordType extends SynStringType {

	public SynPasswordType() {
		super();
	}

	public SynPasswordType(String[] enumValues) {
		super(enumValues);
	}

	public SynPasswordType(List<Object> enumValues) {
		super(enumValues);
	}

	public Object cloneThis() throws Exception {
		SynPasswordType clone = new SynPasswordType();
		super.cloneThis(clone);
		return clone;
	}

}
