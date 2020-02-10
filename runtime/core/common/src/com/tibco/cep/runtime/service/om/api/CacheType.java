package com.tibco.cep.runtime.service.om.api;

/*
* Author: Ashwin Jayaprakash / Date: Nov 24, 2010 / Time: 4:33:37 PM
*/
public interface CacheType {
    String getAlias();

    boolean isCacheLimited();

    boolean hasBackingStore();

    boolean isCacheAside();

    boolean isReplicated();
}
