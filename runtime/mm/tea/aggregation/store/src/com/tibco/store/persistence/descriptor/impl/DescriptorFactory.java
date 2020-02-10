package com.tibco.store.persistence.descriptor.impl;

import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.MemoryTableDescriptor;
import com.tibco.store.persistence.descriptor.PartitionTableDescriptor;
import com.tibco.store.query.model.DataType;

public class DescriptorFactory {

	public static ColumnDescriptor createColumnDescriptor(String name, DataType dataType, boolean isIndexed) {
		return new ColumnDescriptorImpl(name, dataType, isIndexed);
	}

	public static ColumnDescriptor createColumnDescriptor(String name) {
		return new ColumnDescriptorImpl(name);
	}

	public static ColumnDescriptor createColumnDescriptor(String name, DataType dataType) {
		return new ColumnDescriptorImpl(name, dataType);
	}

	
	public static PartitionTableDescriptor createPartitionTableDescriptor(String tableName, String partitionKey) {
		return new PartitionTableDescriptorImpl(tableName, partitionKey);
	}
	
	
	public static MemoryTableDescriptor createMemoryTableDescriptor(String tableName) {
		return new MemoryTableDescriptorImpl(tableName);
	} 
		
}
