package com.tibco.cep.runtime.util.scheduler.impl;

import com.tibco.cep.runtime.util.scheduler.Scheduler;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class ParametersImpl implements Scheduler.Parameters{

    private String name;
    private int maxThreads, minThreads;

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxThreads() {
        return this.maxThreads;
    }

    public int getMinThreads() {
        return this.minThreads;
    }

}
