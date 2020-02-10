package com.tibco.cep.runtime.management;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2009 Time: 1:14:08 PM
*/
public interface CacheInfo {
    FQName getName();

    long getSize();

    long getNumberOfGets();

    long getNumberOfPuts();

    double getAvgGetTimeMillis();

    double getAvgPutTimeMillis();

    double getHitRatio();

    long getMaxSize();

    long getMinSize();

    long getExpiryDelayMillis();
}
