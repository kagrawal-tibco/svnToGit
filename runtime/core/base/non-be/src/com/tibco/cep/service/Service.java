package com.tibco.cep.service;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Recoverable;
import com.tibco.cep.common.resource.Resource;

/*
* Author: Ashwin Jayaprakash Date: Jul 20, 2009 Time: 1:37:22 PM
*/


//todo Why is Service Recoverable?
public interface Service extends Resource, Recoverable<Service> {
    void start() throws LifecycleException;

    void stop() throws LifecycleException;
}
