package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;

public class DestinationDescriptorAdapter implements DestinationDescriptor {

	private com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor adaptedDescriptor;
	
	public DestinationDescriptorAdapter(com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor descriptor) {
		this.adaptedDescriptor = descriptor;
	}

	@Override
	public void clear() {
		adaptedDescriptor.getDescriptors().clear();
	}

	@Override
	public boolean containsKey(Object key) {
		EList<PropertyDescriptorMapEntry> descriptors = adaptedDescriptor.getDescriptors();
		for (int i=0; i<descriptors.size(); i++) {
			if (key.equals((descriptors.get(i).getKey()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		EList<PropertyDescriptorMapEntry> descriptors = adaptedDescriptor.getDescriptors();
		for (int i=0; i<descriptors.size(); i++) {
			if (value.equals((descriptors.get(i).getValue()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, PropertyDescriptor>> entrySet() {
		Map<String, PropertyDescriptor> map = new LinkedHashMap<String, PropertyDescriptor>();
		EList<PropertyDescriptorMapEntry> descriptors = adaptedDescriptor.getDescriptors();
		for (int i=0; i<descriptors.size(); i++) {
			PropertyDescriptorMapEntry entry = descriptors.get(i);
			map.put(entry.getKey(), new PropertyDescriptorAdapter(entry.getValue()));
		}
		return map.entrySet();
	}

	@Override
	public PropertyDescriptor get(Object key) {
		EList<PropertyDescriptorMapEntry> descriptors = adaptedDescriptor.getDescriptors();
		for (int i=0; i<descriptors.size(); i++) {
			if (key.equals((descriptors.get(i).getKey()))) {
				return new PropertyDescriptorAdapter(descriptors.get(i).getValue());
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return adaptedDescriptor.getDescriptors().size() == 0;
	}

	@Override
	public Set<String> keySet() {
		EList<PropertyDescriptorMapEntry> descriptors = adaptedDescriptor.getDescriptors();
		Set<String> keySet = new LinkedHashSet<String>();
		for (int i=0; i<descriptors.size(); i++) {
			keySet.add(descriptors.get(i).getKey());
		}
		return keySet;
	}

	@Override
	public PropertyDescriptor put(String key, PropertyDescriptor value) {
		throw new UnsupportedOperationException("Changing descriptor not supported");
	}

	@Override
	public void putAll(Map<? extends String, ? extends PropertyDescriptor> t) {
		throw new UnsupportedOperationException("Changing descriptor not supported");
	}

	@Override
	public PropertyDescriptor remove(Object key) {
		throw new UnsupportedOperationException("Changing descriptor not supported");
	}

	@Override
	public int size() {
		return adaptedDescriptor.getDescriptors().size();
	}

	@Override
	public Collection<PropertyDescriptor> values() {
		EList<PropertyDescriptorMapEntry> descriptors = adaptedDescriptor.getDescriptors();
		List<PropertyDescriptor> values = new ArrayList<PropertyDescriptor>();
		for (int i=0; i<descriptors.size(); i++) {
			com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor value = descriptors.get(i).getValue();
			values.add(new PropertyDescriptorAdapter(value));
		}

		return values;
	}

}
