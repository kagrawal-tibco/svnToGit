package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

public class StringToBooleanConverter extends Converter {

	public StringToBooleanConverter() {
	    super(String.class, Boolean.class);
    }

	@Override
	public Object convert(Object fromObject) {
		String fromString = (String) fromObject;
		return Boolean.valueOf(fromString);
	}

}
