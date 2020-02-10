package com.tibco.cep.runtime.management;

import java.util.Collection;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 15, 2009 Time: 5:41:57 PM
*/
public interface CacheTable {
    Collection<CacheInfo> getCacheInfos();

    CacheInfo getCacheInfo(FQName name);
}
