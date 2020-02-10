package com.tibco.cep.studio.wizard.as.commons.utils;

import java.util.HashMap;
import java.util.Map;

public class SimpleContext implements IContext {

	private Map<String, Object> delegate = new HashMap<String, Object>();

	public static IContext newInstance() {
		return new SimpleContext();
	}

	@Override
	public void bind(String key, Object value) {
		delegate.put(key, value);
	}

	@Override
	public Object unbind(String key) {
		return delegate.remove(key);
	}

	@Override
    public Object get(String key) {
	    return delegate.get(key);
    }

}
