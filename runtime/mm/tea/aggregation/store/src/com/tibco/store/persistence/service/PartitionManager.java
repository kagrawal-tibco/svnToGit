package com.tibco.store.persistence.service;

import com.tibco.store.persistence.descriptor.PartitionTableDescriptor;
import com.tibco.store.persistence.descriptor.RangeDescriptor;
import com.tibco.store.persistence.model.invm.PartitionedTable;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/1/14
 * Time: 11:14 AM
 *
 * Use this manager to interact to the partition store.
 */
public interface PartitionManager<T> {

    public PartitionedTable getOrCreateTable(PartitionTableDescriptor partitionDescriptor);

    public PartitionedTable removePartition(RangeDescriptor<T> range);

    /**
     * Get matching partitioned table for a value.
     * If none is found new one is created.
     * @param value
     * @return
     */
    public PartitionedTable<PartitionTableDescriptor> getPartitionedTable(T value);
}
