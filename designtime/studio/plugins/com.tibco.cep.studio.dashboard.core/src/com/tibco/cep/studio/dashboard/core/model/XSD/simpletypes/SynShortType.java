package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * Short properties can only accept aa Short as a value
 *
 */
public class SynShortType extends SynNumericType {

	public SynShortType() {
		super(short.class);
	}

	public SynShortType(boolean allowsNegative) {
		super(short.class, allowsNegative);
	}

	public SynShortType(String[] enumValues) {
		super(short.class, enumValues);
	}

	public SynShortType(List<Object> enumValues) {
		super(short.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynShortType clone = new SynShortType();
		super.cloneThis(clone);
		return clone;
	}

}