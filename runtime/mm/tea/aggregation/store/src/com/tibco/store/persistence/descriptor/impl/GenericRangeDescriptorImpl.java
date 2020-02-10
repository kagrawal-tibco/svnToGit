package com.tibco.store.persistence.descriptor.impl;

import com.tibco.store.persistence.descriptor.RangeDescriptor;
import com.tibco.store.persistence.model.invm.impl.CompareFactory;

public class GenericRangeDescriptorImpl implements RangeDescriptor<Object>{

	private Object start;
	private Object end;
	
	public GenericRangeDescriptorImpl(Object start, Object end) {
		if (start == null || end == null) {
			throw new IllegalArgumentException("Name for EqualsDescriptor can not be null");
		}
		this.start = start;
		this.end = end;
	}
	
	@Override
	public Object getStart() {
		return start;
	}

	@Override
	public Object getEnd() {
		return end;
	}

	@Override
	public boolean encloses(Object value) {
		if (CompareFactory.compareLE(start, value) && CompareFactory.compareLE(value, end)) {
			return true;
		}
		return false;
	}

}
