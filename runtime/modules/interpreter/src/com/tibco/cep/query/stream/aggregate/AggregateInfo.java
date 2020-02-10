package com.tibco.cep.query.stream.aggregate;

import java.util.LinkedHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 9, 2007 Time: 11:37:12 AM
 */

public class AggregateInfo {
    protected final LinkedHashMap<String, AggregateItemInfo> aggregateItems;

    /**
     * @param aggregateItems Key: Column aliases. The same order in which the results are to be
     *                       produced.
     */
    public AggregateInfo(LinkedHashMap<String, AggregateItemInfo> aggregateItems) {
        this.aggregateItems = aggregateItems;
    }

    public LinkedHashMap<String, AggregateItemInfo> getAggregateItems() {
        return aggregateItems;
    }

    public AggregateItemInfo getAggregateItem(String name) {
        return aggregateItems.get(name);
    }
}
