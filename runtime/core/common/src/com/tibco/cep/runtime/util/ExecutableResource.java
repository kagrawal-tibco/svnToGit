package com.tibco.cep.runtime.util;

/*
* Author: Ashwin Jayaprakash Date: May 26, 2009 Time: 10:24:58 PM
*/
public interface ExecutableResource {
    boolean isStarted();

    boolean isSuspended();

    void suspendResource();

    void resumeResource();

    void shutdown();
}
