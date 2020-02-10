package com.tibco.store.persistence.model.invm.impl;

import com.tibco.store.persistence.descriptor.PartitionTableDescriptor;
import com.tibco.store.persistence.model.MemTable;
import com.tibco.store.persistence.model.PartitionTableFactory;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 13/1/14 Time: 11:57 AM
 * 
 * Default factory to create
 * {@link com.tibco.store.persistence.model.invm.impl.PartitionedMemoryTable}
 */
public class PartitionedTableFactoryImpl implements PartitionTableFactory {

	private PartitionTableDescriptor partitionDescriptor;

	public PartitionedTableFactoryImpl(PartitionTableDescriptor partitionDescriptor) {
		this.partitionDescriptor = partitionDescriptor;
	}

	@Override
	public MemTable<?> createTable() {
		return new PartitionedMemoryTable<Object>(partitionDescriptor);
	}

}
