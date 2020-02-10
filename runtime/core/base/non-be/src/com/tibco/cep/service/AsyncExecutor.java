package com.tibco.cep.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.ResourceProvider;

/*
* Author: Ashwin Jayaprakash Date: Jul 20, 2009 Time: 1:34:24 PM
*/
public interface AsyncExecutor extends Service, ExecutorService {
    void setMaxThreads(int numThreads);

    int getMaxThreads();
    
    void submitToAllThreads(Callable<Object> callable);

    AsyncExecutor recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException;
}