package com.tibco.cep.loadbalancer.message;

/*
* Author: Ashwin Jayaprakash / Date: Mar 18, 2010 / Time: 2:41:23 PM
*/
public interface DistributionStrategy {
    DistributionKey[] getBootstrapKeys();
}
