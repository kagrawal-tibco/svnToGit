package com.tibco.cep.query.stream.impl.monitor.model;

/*
* Author: Ashwin Jayaprakash Date: Apr 9, 2009 Time: 10:55:16 AM
*/
public interface AgentInfoMBean {
    long getLocalCacheEvictSeconds();

    long getLocalCacheMaxElements();

    long getLocalCacheCurrentElements();

    boolean getLocalCachePrefetchAggressive();
}
