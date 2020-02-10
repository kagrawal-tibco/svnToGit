package com.tibco.store.persistence.model.invm.impl;

import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.util.ObjectSizeCalculator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MemoryTupleImpl implements MemoryTuple {

	private MemoryKey mKey;

	protected Map<String, Object> attributes = new LinkedHashMap<String, Object>();
	
	private long memoryTupleSize;

	public MemoryTupleImpl(MemoryKey mKey) {
		this.mKey = mKey;
	}

	@Override
	public MemoryKey getMemoryKey() {
		return mKey;
	}

	@Override
	public Object getAttributeValue(String attributeName) {
		return attributes.get(attributeName);
	}


	@Override
	public void clearAttributes() {
		attributes.clear();
		memoryTupleSize = 0;
	}

	@Override
	public void setMemoryKey(MemoryKey key) {
		this.mKey = key;
	}

	@Override
	public void setAttribute(String attrName, Object value) {
		if (attrName != null && !attrName.equals("")) {
			attributes.put(attrName, value);
			memoryTupleSize += ObjectSizeCalculator.getSizeInBytes(attrName) + ObjectSizeCalculator.getSizeInBytes(value);
		}
	}
	
	@Override
	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MemoryTupleImpl that = (MemoryTupleImpl) o;

		return mKey.equals(that.mKey);
	}

	@Override
	public int hashCode() {
		return mKey.hashCode();
	}

	@Override
	public String toString() {
        String pattern = "[mKey=%s, attributes=%s]";
		return String.format(pattern, mKey, attributes);
	}

	@Override
	public Object getWrappedObject() {		
		return mKey;
	}

    @Override
    public boolean hasAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }
}
