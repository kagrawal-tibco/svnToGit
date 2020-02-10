package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

import com.tibco.cep.studio.wizard.as.commons.utils.StringUtils;

public class CapitalizeConverter extends Converter {

	public CapitalizeConverter() {
	    super(String.class, String.class);
    }

	@Override
	public Object convert(Object fromObject) {
		String fromString = (String) fromObject;
		return StringUtils.capitalize(fromString.toLowerCase());
	}

}
