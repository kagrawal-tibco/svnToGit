package com.tibco.cep.query.stream.sort;

/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 12:57:33 PM
 */

public class SortInfo {
    protected final SortItemInfo[] items;

    public SortInfo(SortItemInfo[] items) {
        this.items = items;
    }

    public SortItemInfo[] getItems() {
        return items;
    }
}
