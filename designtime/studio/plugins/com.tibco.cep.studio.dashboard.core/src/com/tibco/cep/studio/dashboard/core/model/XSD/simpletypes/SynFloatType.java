package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * Float properties can only accept a float as a value
 *
 */
public class SynFloatType extends SynNumericType {

	public SynFloatType() {
		super(float.class);
	}

	public SynFloatType(boolean allowsNegative) {
		super(float.class, allowsNegative);
	}

	public SynFloatType(String[] enumValues) {
		super(float.class, enumValues);
	}

	public SynFloatType(List<Object> enumValues) {
		super(float.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynFloatType clone = new SynFloatType();
		super.cloneThis(clone);
		return clone;
	}

}