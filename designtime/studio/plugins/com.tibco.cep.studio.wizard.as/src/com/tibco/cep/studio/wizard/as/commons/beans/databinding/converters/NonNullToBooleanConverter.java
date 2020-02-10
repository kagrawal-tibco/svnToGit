package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

public class NonNullToBooleanConverter extends Converter {

	public NonNullToBooleanConverter() {
	    super(Object.class, Boolean.class);
    }

	@Override
	public Object convert(Object fromObject) {
		return null != fromObject;
	}

}
