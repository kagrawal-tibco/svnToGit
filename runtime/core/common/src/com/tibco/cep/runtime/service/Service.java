package com.tibco.cep.runtime.service;

import com.tibco.cep.runtime.config.Configuration;

/*
* Author: Ashwin Jayaprakash Date: Nov 12, 2008 Time: 12:59:54 PM
*/
public interface Service extends ManagedResource {
    String getId();

    void init(Configuration configuration, Object... otherArgs) throws Exception;

    void start() throws Exception;

    void stop() throws Exception;
}
