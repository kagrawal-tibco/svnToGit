package com.tibco.cep.query.stream.transform;

import java.util.LinkedHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Nov 6, 2007 Time: 11:08:25 AM
 */

public class TransformInfo {
    protected final LinkedHashMap<String, TransformItemInfo> itemInfos;

    /**
     * @param itemInfos The same order in which the columns should appear in the results. Keys:
     *                  Column aliases.
     */
    public TransformInfo(LinkedHashMap<String, TransformItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public LinkedHashMap<String, TransformItemInfo> getItemInfos() {
        return itemInfos;
    }
}
