package com.tibco.cep.dashboard.integration.be;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.xml.data.primitive.ExpandedName;


class SimpleDBEvent extends SimpleEventImpl {
	
	static final String URI = SimpleDBEvent.class.getName();

	static final ExpandedName EXPANDED_NAME = ExpandedName.makeName(SimpleDBEvent.class.getName());
	
	private Map<String, Object> properties = new HashMap<String, Object>(); 

	@Override
	public void deserializeProperty(EventDeserializer deserializer, int index) {
	}

	@Override
	public ExpandedName getExpandedName() {
		return EXPANDED_NAME;
	}

	@Override
	public String[] getPropertyNames() {
		return properties.keySet().toArray(new String[properties.size()]);
	}
	
	@Override
	public Object getProperty(String name) throws NoSuchFieldException {
		if (properties.containsKey(name)) {
			return properties.get(name);
		}
		return super.getProperty(name);
	}
	
	@Override
	public void setProperty(String name, Object value) throws Exception {
		properties.put(name, value);
	}
	
	@Override
	public void setProperty(String name, String value) throws Exception {
		properties.put(name, value);
	}

	@Override
	public long getTTL() {
		return 0;
	}

	@Override
	public String getType() {
		return null;
	}
	
	

}
