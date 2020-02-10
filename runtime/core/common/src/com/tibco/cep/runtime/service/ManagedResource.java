package com.tibco.cep.runtime.service;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 1:18:22 PM
*/
public interface ManagedResource {
    String getId();

    void start() throws Exception;

    void stop() throws Exception;
}
