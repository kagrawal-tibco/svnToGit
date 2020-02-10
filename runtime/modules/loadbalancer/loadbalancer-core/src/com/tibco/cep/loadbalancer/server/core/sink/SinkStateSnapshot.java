package com.tibco.cep.loadbalancer.server.core.sink;

import com.tibco.cep.loadbalancer.server.core.StateSnapshot;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 11:09:18 AM
*/
public interface SinkStateSnapshot extends StateSnapshot {
    SinkState getSinkState();
}
