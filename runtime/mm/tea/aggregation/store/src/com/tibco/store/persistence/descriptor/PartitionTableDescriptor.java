package com.tibco.store.persistence.descriptor;

import com.tibco.store.persistence.model.PartitionTableFactory;

public interface PartitionTableDescriptor extends TableDescriptor<PartitionTableFactory> {

	public String getPartitionKey();
	
	public RangeDescriptor<?> createRangeDescriptor(Object start, Object end);
	
}
