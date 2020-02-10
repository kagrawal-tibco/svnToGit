package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * Double properties can only accept a Double as a value
 *
 */
public class SynDoubleType extends SynNumericType {

	public SynDoubleType() {
		super(double.class);
	}

	public SynDoubleType(boolean allowsNegative) {
		super(double.class, allowsNegative);
	}

	public SynDoubleType(String[] enumValues) {
		super(double.class, enumValues);
	}

	public SynDoubleType(List<Object> enumValues) {
		super(double.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynDoubleType clone = new SynDoubleType();
		super.cloneThis(clone);
		return clone;
	}

}