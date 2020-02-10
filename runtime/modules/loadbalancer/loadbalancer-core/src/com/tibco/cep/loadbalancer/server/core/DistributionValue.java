package com.tibco.cep.loadbalancer.server.core;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 5:22:40 PM
*/
public interface DistributionValue {
    Member getCurrentOwner();

    boolean hasCurrentOwner();
}
