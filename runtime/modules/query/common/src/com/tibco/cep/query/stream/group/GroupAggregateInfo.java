package com.tibco.cep.query.stream.group;

import java.util.LinkedHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 12:04:15 PM
 */

public class GroupAggregateInfo {
    protected final LinkedHashMap<String, GroupAggregateItemInfo> itemInfos;

    /**
     * @param itemInfos The order in which the Group + Aggregate columns will appear in the results.
     *                  Keys: Column aliases
     */
    public GroupAggregateInfo(LinkedHashMap<String, GroupAggregateItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public LinkedHashMap<String, GroupAggregateItemInfo> getItemInfos() {
        return itemInfos;
    }
}
