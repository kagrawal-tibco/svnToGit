package com.tibco.store.persistence.model.invm.impl;

import com.tibco.store.persistence.descriptor.MemoryTableDescriptor;
import com.tibco.store.persistence.model.MemTable;
import com.tibco.store.persistence.model.MemoryTableFactory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/1/14
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryTableFactoryImpl implements MemoryTableFactory {

	private MemoryTableDescriptor tableDescriptor;
	
	public InMemoryTableFactoryImpl(MemoryTableDescriptor memTableDescriptor) {
		this.tableDescriptor = memTableDescriptor;
	}    

	@Override
	public MemTable<?> createTable() {		
		return new InMemoryTableImpl(tableDescriptor);
	}
}
