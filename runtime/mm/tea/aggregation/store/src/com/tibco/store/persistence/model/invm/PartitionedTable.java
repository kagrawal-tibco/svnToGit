package com.tibco.store.persistence.model.invm;

import com.tibco.store.persistence.descriptor.RangeDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/1/14
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PartitionedTable<T> extends InMemoryTable {

    public MemoryPartition createPartition(RangeDescriptor<T> rangeDescriptor);

    public MemoryPartition removePartition(RangeDescriptor<T> rangeDescriptor);
}
