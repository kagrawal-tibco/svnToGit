package com.tibco.cep.service;

import java.util.concurrent.ScheduledExecutorService;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.ResourceProvider;

/*
* Author: Ashwin Jayaprakash Date: Jul 20, 2009 Time: 1:34:24 PM
*/
public interface AsyncScheduler extends Service, ScheduledExecutorService {
    void setMaxThreads(int numThreads);

    int getMaxThreads();

    AsyncScheduler recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException;
}
