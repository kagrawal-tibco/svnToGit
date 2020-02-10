package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.runtime.model.RtaNodeContext;

import java.util.LinkedHashMap;
import java.util.Map;

public class RtaNodeContextImpl implements RtaNodeContext {
	
	private static final long serialVersionUID = -7635566467069103737L;
	
	protected Map<String,Object> contextMap = new LinkedHashMap<String,Object>();

	@Override
	public String[] getTupleNames() {
		String [] keys = new String[contextMap.size()];
		int i = 0;
		for (String s : contextMap.keySet()) {
			keys[i++] = s;
		}
		return keys;
	}

	@Override
	public Object getTupleValue(String name) {
		return contextMap.get(name);
	}

	@Override
	public void setTuple(String name, Object value) {
		contextMap.put(name, value);
	}

	@Override
	public RtaNodeContext deepCopy() {
		RtaNodeContextImpl cloned = new RtaNodeContextImpl();
		//assuming contextMap only contains immutable objects.
		cloned.contextMap = new LinkedHashMap<String,Object>(contextMap);
		return cloned;
	}
}
