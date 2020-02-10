package com.tibco.cep.runtime.metrics;

/*
* Author: Ashwin Jayaprakash Date: Feb 9, 2009 Time: 1:35:14 PM
*/
public interface StopWatchOwnerLifecycleListener {
    void onNew(StopWatchOwner watchOwner);

    void onDiscard(StopWatchOwner watchOwner);
}
