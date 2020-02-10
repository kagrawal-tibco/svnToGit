package com.tibco.cep.loadbalancer.server.core;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 11:54:31 AM
*/
public interface StateSnapshot {
    long getAt();

    @Optional
    Exception getException();
}
