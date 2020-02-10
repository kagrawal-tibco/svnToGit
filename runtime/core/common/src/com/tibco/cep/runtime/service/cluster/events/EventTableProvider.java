package com.tibco.cep.runtime.service.cluster.events;

import com.tibco.cep.runtime.service.cluster.Cluster;

public interface EventTableProvider {
    void init(Cluster cluster) throws Exception;

    EventTable getEventTable(Class eventClass);

    EventTable newEventTable(Class eventClass) throws Exception;

    void start() throws Exception;

    void stop();
    
    void releaseEventsOwnedByAgent(int agentId);
}
