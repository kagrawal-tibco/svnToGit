package com.tibco.cep.loadbalancer.impl.jmx;

/*
* Author: Ashwin Jayaprakash / Date: 10/4/11 / Time: 2:40 PM
*/
public interface RouterDestinationMbean {
    String getParentName();

    String getName();

    int getTotalMessagesSent();

    int getTotalPendingAcks();

    void clearStats();
}
