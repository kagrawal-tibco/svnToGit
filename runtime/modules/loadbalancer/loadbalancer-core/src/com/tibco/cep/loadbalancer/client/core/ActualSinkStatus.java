package com.tibco.cep.loadbalancer.client.core;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 5:57:46 PM
*/
public interface ActualSinkStatus {
    long getRecentMembershipChangeAt();

    Collection<Object> getLastKnownMemberIds();
}
