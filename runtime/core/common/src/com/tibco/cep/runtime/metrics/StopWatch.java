package com.tibco.cep.runtime.metrics;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 22, 2009 Time: 2:35:08 PM
*/
public interface StopWatch {
    FQName getOwnerName();

    /**
     * Each call to this method must be followed by a call to {@link #stop()}.
     */
    void start();

    /**
     * @see #start()
     */
    void stop();
}
