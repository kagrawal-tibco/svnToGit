package com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters;

import java.util.Collection;

import org.eclipse.core.databinding.conversion.Converter;

public class CollectionNotEmptyToBooleanConverter extends Converter {

	public CollectionNotEmptyToBooleanConverter() {
	    super(Collection.class, Boolean.class);
    }

	@Override
	public Object convert(Object fromObject) {
		Collection<?> collection = (Collection<?>) fromObject;
		return null != collection && !collection.isEmpty();
	}

}