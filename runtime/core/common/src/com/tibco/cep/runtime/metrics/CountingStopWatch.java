package com.tibco.cep.runtime.metrics;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 6:37:01 PM
*/
public interface CountingStopWatch extends StopWatch {
    /**
     * Increment the counter by 1 - invoked after {@link #start()} and before {@link #stop()}.
     */
    void count();

    /**
     * @param i Increment the counter by the number provided - invoked after {@link #start()} and
     *          before {@link #stop()}.
     */
    void count(int i);
}
