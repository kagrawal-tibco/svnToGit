package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

public class BooleanToStringConverter extends Converter {

	public BooleanToStringConverter() {
	    super(Boolean.class, String.class);
    }

	@Override
	public Object convert(Object fromObject) {
		Boolean fromBoolean = (Boolean) fromObject;
		return fromBoolean.toString();
	}

}
