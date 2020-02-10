package com.tibco.cep.loadbalancer.client.core;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 6:06:06 PM
*/
public interface ActualMemberStatus {
    long getRecentMembershipChangeAt();

    Collection<Object> getLastKnownMemberIds();
}
