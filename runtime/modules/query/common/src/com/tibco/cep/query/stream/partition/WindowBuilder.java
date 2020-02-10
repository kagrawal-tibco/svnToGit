package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Nov 8, 2007 Time: 11:47:48 AM
 */

public interface WindowBuilder {
    /**
     * @param parentResourceId
     * @param id                   Window Id
     * @param groupKey
     * @param aggregateInfo        Can be <code>null</code>.
     * @param windowInfo           Can be <code>null</code>.
     * @param windowOwner
     * @param aggregateTransformer Can be <code>null</code>.
     * @return A {@link Window} whose ultimate {@link TupleInfo output} matches the {@link
     *         PartitionedStream} output.
     */
    public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey,
                               AggregateInfo aggregateInfo, WindowInfo windowInfo,
                               WindowOwner windowOwner,
                               GroupAggregateTransformer aggregateTransformer);
}
