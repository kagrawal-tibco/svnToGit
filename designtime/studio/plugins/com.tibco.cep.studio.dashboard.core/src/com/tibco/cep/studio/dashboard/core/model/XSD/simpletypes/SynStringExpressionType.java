package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * @deprecated
 */
public class SynStringExpressionType extends SynStringType {

	public SynStringExpressionType() {
		super();
		/*
		 * String expression has no practical lenght limit
		 */
		setMaxLength(BasicValidations.MAX_STRNG_LENGTH);
	}

	public Object cloneThis() throws Exception {
		SynStringExpressionType clone = new SynStringExpressionType();
		super.cloneThis(clone);
		return clone;
	}
}
