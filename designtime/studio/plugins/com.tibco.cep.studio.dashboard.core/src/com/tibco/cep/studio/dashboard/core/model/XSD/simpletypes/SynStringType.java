package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.ISynPropertyEnumProvider;

/**
 * String properties are free form text string and so do not need additional validation aside from the default null validation of the
 *
 */
public class SynStringType extends SynPrimitiveType {

	public SynStringType() {
		super(java.lang.String.class);
	}

	public SynStringType(String[] enumValues) {
		super(java.lang.String.class, enumValues);
	}

	public SynStringType(List<Object> enumValues) {
		super(String.class, enumValues);
	}

	public SynStringType(ISynPropertyEnumProvider enumProvider) {
		super(String.class, enumProvider);
	}

	public Object cloneThis() throws Exception {
		SynStringType clone = new SynStringType();
		super.cloneThis(clone);
		return clone;
	}

}