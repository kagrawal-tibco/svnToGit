package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import static com.tibco.cep.studio.wizard.as.commons.utils.StringUtils.isNotBlank;

import org.eclipse.core.databinding.conversion.Converter;

public class StringNonBlankToBooleanConverter extends Converter {

	public StringNonBlankToBooleanConverter() {
	    super(String.class, Boolean.class);
    }

	@Override
	public Object convert(Object fromObject) {
		String fromString = (String) fromObject;
		return isNotBlank(fromString);
	}

}
