package com.tibco.cep.studio.core.adapters;

import java.util.regex.Pattern;

import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;

public class PropertyDescriptorAdapter implements PropertyDescriptor {

	private com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor adaptedDescriptor;

	public PropertyDescriptorAdapter(com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor descriptor) {
		this.adaptedDescriptor = descriptor;
	}

	@Override
	public String getDefaultValue() {
		return adaptedDescriptor.getDefaultValue();
	}

	@Override
	public String getName() {
		return adaptedDescriptor.getName();
	}

	@Override
	public Pattern getPattern() {
		String pattern = adaptedDescriptor.getPattern();
		return Pattern.compile(pattern);
	}

	@Override
	public int getType() {
		return Integer.valueOf(adaptedDescriptor.getType());
	}

}
