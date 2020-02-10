package com.tibco.cep.runtime.management.impl.cluster;

import com.tibco.cep.runtime.management.CacheTable;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2009 Time: 1:49:20 PM
*/
public interface InternalCacheTable extends CacheTable {
    /**
     * @param clusterURL
     */
    void init(String clusterURL);

    void discard();
}
