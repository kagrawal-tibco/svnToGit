package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;

public class FactMemoryTuple implements Fact, MemoryTuple {

	private static final long serialVersionUID = 1L;
	private Fact wrapper;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private FactMemoryKey mKey;
	private Set<String> attrNames = new HashSet<String>();

	public FactMemoryTuple(Fact fact) {
		wrapper = fact;
		mKey = new FactMemoryKey((FactKeyImpl) fact.getKey());
	}

	@Override
	public Key getKey() {
		return wrapper.getKey();
	}

	public Map<String, Object> getProperties() {
		return attributes;
	}

	public Object getProperty(String attrName) {
		return attributes.get(attrName);
	}

	public void setProperty(String attrName, Object value) {
		attributes.put(attrName, value);
	}

	@Override
	public void setAttribute(String attrName, Object value) {
		attributes.put(attrName, value);
	}

	@Override
	public Object getAttribute(String attrName) {
		return wrapper.getAttribute(attrName);
	}

	@Override
	public Collection<String> getAttributeNames() {
		return wrapper.getAttributeNames();
	}

	@Override
	public void clear() {
		wrapper.clear();
	}

	@Override
	public RtaSchema getOwnerSchema() {
		return wrapper.getOwnerSchema();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof FactMemoryTuple)) {
			return false;
		}
		FactMemoryTuple otherNode = (FactMemoryTuple) object;
		return wrapper.equals(otherNode.getWrappedObject());
	}

	@Override
	public int hashCode() {
		return wrapper.hashCode();
	}

	public Object getWrappedObject() {
		return wrapper;
	}

	@Override
	public MemoryKey getMemoryKey() {
		return mKey;
	}

	@Override
	public void clearAttributes() {
		attributes.clear();
	}

	@Override
	public void setMemoryKey(MemoryKey key) throws Exception {
		if (key instanceof FactMemoryKey) {
			this.mKey = (FactMemoryKey) key;
		}
	}

	@Override
	public Object getAttributeValue(String attrName) {
		if (attributes.get(attrName) != null) {
			return attributes.get(attrName);
		}
		return wrapper.getAttribute(attrName);
	}

	@Override
	public boolean hasAttribute(String attributeName) {
		return attrNames.contains(attributeName);
	}

    @Override
    public void setAttributes(Map<String, Object> attributes) throws UndefinedSchemaElementException, DataTypeMismatchException {
        this.attributes = attributes;
    }
}
