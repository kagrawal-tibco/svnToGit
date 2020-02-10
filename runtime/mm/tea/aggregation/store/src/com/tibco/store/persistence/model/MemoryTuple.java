package com.tibco.store.persistence.model;

import java.util.Collection;

public interface MemoryTuple {

	public MemoryKey getMemoryKey();

	public void clearAttributes();

	public void setMemoryKey(MemoryKey key)  throws Exception;

	public void setAttribute(String attrName, Object value);

	public Object getAttributeValue(String attributeName);

    public boolean hasAttribute(String attributeName);
	
	public Collection<String> getAttributeNames();
	
	public Object getWrappedObject();

}
