package com.tibco.store.persistence.model.invm.impl;

import com.tibco.store.persistence.model.MemoryKey;

public class StringMemoryKey implements MemoryKey {

	private String mKey;

	public StringMemoryKey() {
	}

	public StringMemoryKey(String key) {
		this.mKey = key;
	}

	@Override
	public String toString() {
		return mKey;
	}

	@Override
	public int hashCode() {
		return mKey.hashCode();
	}

	@Override
	public boolean equals(Object keyObj) {

		if (keyObj == null || getClass() != keyObj.getClass()) {
			return false;
		}

		if (!(keyObj instanceof StringMemoryKey)) {
			return false;
		}
		StringMemoryKey key = (StringMemoryKey) keyObj;

		return this.mKey.equals(key.mKey);
	}

	@Override
	public Object getWrappedObject() {	
		return mKey;
	}

}
