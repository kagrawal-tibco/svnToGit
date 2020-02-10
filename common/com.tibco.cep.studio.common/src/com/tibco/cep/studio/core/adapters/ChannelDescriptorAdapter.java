package com.tibco.cep.studio.core.adapters;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.designtime.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;

public class ChannelDescriptorAdapter implements ChannelDescriptor {

	private com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor adaptedDescriptor;
	
	public ChannelDescriptorAdapter(com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor descriptor) {
		this.adaptedDescriptor = descriptor;
	}

	@Override
	public DestinationDescriptor getDestinationDescriptor() {
		com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor destinationDescriptor = adaptedDescriptor.getDestinationDescriptor();
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, PropertyDescriptor>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyDescriptor get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyDescriptor put(String key, PropertyDescriptor value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends PropertyDescriptor> t) {
		// TODO Auto-generated method stub

	}

	@Override
	public PropertyDescriptor remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<PropertyDescriptor> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
