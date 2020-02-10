package com.tibco.cep.query.stream.impl.cache;

import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Ashwin Jayaprakash Date: May 9, 2008 Time: 4:42:48 PM
*/
public class PrimaryLocalCache extends SimpleLocalCache {
    public PrimaryLocalCache(ResourceId parent, String name, int maxItems, long expiryTimeMillis) {
        super(parent, name, maxItems, expiryTimeMillis);
    }
}
