package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * Byte properties can only accept a Byte as a value
 *
 */
public class SynByteType extends SynNumericType {

	public SynByteType() {
		super(byte.class);
	}

	public SynByteType(boolean allowsNegative) {
		super(byte.class, allowsNegative);
	}

	public SynByteType(String[] enumValues) {
		super(byte.class, enumValues);
	}

	public SynByteType(List<Object> enumValues) {
		super(byte.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynByteType clone = new SynByteType();
		super.cloneThis(clone);
		return clone;
	}

}