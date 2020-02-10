package com.tibco.store.persistence.descriptor.impl;

import com.tibco.store.persistence.descriptor.RangeDescriptor;

public class StringRangeDescriptor implements RangeDescriptor<String> {

	private String name;

	public StringRangeDescriptor(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name for EqualsDescriptor can not be null");
		}
		this.name = name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof StringRangeDescriptor)) {
			return false;
		}
		StringRangeDescriptor desc = (StringRangeDescriptor) other;

		return desc.name != null && this.name.equals(desc.name);
	}

	@Override
	public String getStart() {
		return name;
	}

	@Override
	public String getEnd() {
		return name;
	}

	@Override
	public boolean encloses(String value) {
		return value != null && name.equals(value);
	}

}
