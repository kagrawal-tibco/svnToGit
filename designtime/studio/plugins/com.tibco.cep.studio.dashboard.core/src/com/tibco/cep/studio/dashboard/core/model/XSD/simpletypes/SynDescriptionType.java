package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * @ *
 */
public class SynDescriptionType extends SynStringType {

	public SynDescriptionType() {
		super();
		/*
		 * Description has no practical length limit
		 */
		setMaxLength(BasicValidations.MAX_STRNG_LENGTH);
	}

	public Object cloneThis() throws Exception {
		SynDescriptionType clone = new SynDescriptionType();
		super.cloneThis(clone);
		return clone;
	}

}
