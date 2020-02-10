package com.tibco.cep.query.stream.group;

import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 12:05:58 PM
 */

/**
 * Hold either {@link AggregateItemInfo} or {@link GroupItemInfo}, never both.
 */
public class GroupAggregateItemInfo {
    protected final AggregateItemInfo aggregateItemInfo;

    protected final GroupItemInfo groupItemInfo;

    public GroupAggregateItemInfo(AggregateItemInfo aggregateItemInfo) {
        this(aggregateItemInfo, null);
    }

    public GroupAggregateItemInfo(GroupItemInfo groupItemInfo) {
        this(null, groupItemInfo);
    }

    protected GroupAggregateItemInfo(AggregateItemInfo aggregateItemInfo,
            GroupItemInfo groupItemInfo) {
        this.aggregateItemInfo = aggregateItemInfo;
        this.groupItemInfo = groupItemInfo;
    }

    public AggregateItemInfo getAggregateItemInfo() {
        return aggregateItemInfo;
    }

    public GroupItemInfo getGroupItemInfo() {
        return groupItemInfo;
    }
}
