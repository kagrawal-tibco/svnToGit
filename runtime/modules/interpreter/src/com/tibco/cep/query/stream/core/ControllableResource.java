package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.monitor.KnownResource;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 3:26:05 PM
 */

public interface ControllableResource extends KnownResource {
    public void start() throws Exception;

    public void stop() throws Exception;
}
