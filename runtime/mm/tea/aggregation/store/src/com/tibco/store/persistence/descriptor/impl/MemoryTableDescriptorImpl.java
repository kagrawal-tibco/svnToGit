package com.tibco.store.persistence.descriptor.impl;

import com.tibco.store.persistence.descriptor.MemoryTableDescriptor;
import com.tibco.store.persistence.model.MemoryTableFactory;
import com.tibco.store.persistence.model.invm.impl.InMemoryTableFactoryImpl;

public class MemoryTableDescriptorImpl implements MemoryTableDescriptor {

	private String tableName;
	private MemoryTableFactory memTableFactory;

	public MemoryTableDescriptorImpl(String tableName) {
		this(tableName, null);
	}

	public MemoryTableDescriptorImpl(String tableName, MemoryTableFactory memTableFactory) {
		this.tableName = tableName;
		this.memTableFactory = memTableFactory;
	}

	public String getTableName() {
		return tableName;
	}

	@Override
	public void setTableFactory(MemoryTableFactory tableFactory) {
		this.memTableFactory = tableFactory;
	}

	@Override
	public MemoryTableFactory getTableFactory() {
		if (memTableFactory == null) {
			memTableFactory = new InMemoryTableFactoryImpl(this);
		}
		return memTableFactory;
	}

}
