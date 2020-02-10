package com.tibco.rta.common.service;

import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/11/12
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WorkItemService extends StartStopService {

    /**
     *
     * @param workitem
     * @param <T>
     * @param <W>
     */
    public <T, W extends WorkItem<T>> Future<T> addWorkItem(W workitem);
    
    void setThreadPoolName(String threadPoolName);

}
