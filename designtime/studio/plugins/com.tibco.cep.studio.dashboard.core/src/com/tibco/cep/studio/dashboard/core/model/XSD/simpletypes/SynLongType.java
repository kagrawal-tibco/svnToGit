package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * Long properties can only accept a Long as a value
 */
public class SynLongType extends SynNumericType {

	public SynLongType() {
		super(long.class);
	}

	public SynLongType(boolean allowsNegative) {
		super(long.class, allowsNegative);
	}

	public SynLongType(String[] enumValues) {
		super(long.class, enumValues);
	}

	public SynLongType(List<Object> enumValues) {
		super(long.class, enumValues);

	}

	/**
	 * Assumption: maxExclusive is greater than or equal to minExclusive.
	 *
	 * @param minExclusive must be a valid number, null, or "".
	 * @param maxExclusive must be a valid number, null, or "".
	 */
	public SynLongType(String minExclusive, String maxExclusive) {
		super(long.class, minExclusive, maxExclusive);
	}

	public Object cloneThis() throws Exception {
		SynLongType clone = new SynLongType();
		super.cloneThis(clone);
		return clone;
	}

}