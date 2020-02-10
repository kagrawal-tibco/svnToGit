package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * int properties can only accept an int as a value
 *
 */
public class SynIntType extends SynNumericType {

	public SynIntType() {
		super(int.class);
	}

	public SynIntType(boolean allowsNegative) {
		super(int.class, allowsNegative);
	}

	public SynIntType(String[] enumValues) {
		super(int.class, enumValues);
	}

	public SynIntType(List<Object> enumValues) {
		super(int.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynIntType clone = new SynIntType();
		super.cloneThis(clone);
		return clone;
	}

}