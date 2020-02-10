package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

public class ToUpperCaseConverter extends Converter {

	public ToUpperCaseConverter() {
	    super(String.class, String.class);
    }

	@Override
	public Object convert(Object fromObject) {
		String fromString = (String) fromObject;
		return fromString.toUpperCase();
	}

}
