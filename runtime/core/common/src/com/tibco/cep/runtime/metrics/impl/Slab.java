package com.tibco.cep.runtime.metrics.impl;

/*
* Author: Ashwin Jayaprakash Date: Feb 16, 2009 Time: 10:55:50 PM
*/
public interface Slab {
    long getSlabStartTimeMillis();

    long getSlabEndTimeMillis();

    long getCount();

    void incrementCount();

    void addCount(long count);

    boolean isAlreadyProcessed();

    void markAlreadyProcessed();
}
