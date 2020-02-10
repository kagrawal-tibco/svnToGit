package com.tibco.cep.studio.wizard.as.internal.beans.databinding.converters;

import java.util.List;

import org.eclipse.core.databinding.conversion.Converter;

public class ErrorsToStringConverter extends Converter {

	public ErrorsToStringConverter() {
	    super(List.class, String.class);
    }

	@SuppressWarnings("unchecked")
    @Override
	public Object convert(Object fromObject) {
		String message = null;
		List<Exception> errors = (List<Exception>) fromObject;
		if (null != errors && false == errors.isEmpty()) {
			StringBuffer messageSB = new StringBuffer();
			for (Exception ex: errors) {
				messageSB.append(ex.getMessage()).append('\n');
			}
			message = messageSB.toString();
		}
		return message;
	}

}
