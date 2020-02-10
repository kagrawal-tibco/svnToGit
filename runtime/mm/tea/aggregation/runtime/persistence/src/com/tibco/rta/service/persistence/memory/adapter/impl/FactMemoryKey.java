package com.tibco.rta.service.persistence.memory.adapter.impl;

import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.store.persistence.model.MemoryKey;

public class FactMemoryKey extends FactKeyImpl implements MemoryKey{

	private static final long serialVersionUID = 1L;
	private FactKeyImpl wrapper;

	public FactMemoryKey(FactKeyImpl key) {
		super(key.getSchemaName(), key.getUid());
		this.wrapper = key;
	}
	
	@Override
	public int hashCode() {
		return wrapper.hashCode();
	}

	@Override
	public boolean equals(Object keyObj) {
		if (!(keyObj instanceof FactMemoryKey)) {
			return false;
		}
		FactMemoryKey key = (FactMemoryKey) keyObj;

		return wrapper.equals(key.getWrappedObject());
	}

	
	public String getUid() {
		return wrapper.getUid();
	}

	public String getSchemaName() {
		return wrapper.getSchemaName();
	}

    public void setSchemaName(String schemaName) {
       wrapper.setSchemaName(schemaName);
    }

    public void setUid(String uid) {
       wrapper.setUid(uid);
    }

	
	@Override
	public Object getWrappedObject() {		
		return wrapper;
	}

	@Override
	public String toString() {
		return wrapper.toString();
	}

}
