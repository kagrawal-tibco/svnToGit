package com.tibco.cep.query.stream.framework;

import java.util.concurrent.atomic.AtomicLong;

/*
* Author: Ashwin Jayaprakash Date: May 21, 2008 Time: 2:09:36 PM
*/
public class SimpleIdGenerator {
    protected static final AtomicLong COUNTER = new AtomicLong();

    public static Long generateNewId() {
        return COUNTER.incrementAndGet();
    }
}
