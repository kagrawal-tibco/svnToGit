package com.tibco.cep.loadbalancer.server.core;

/*
* Author: Ashwin Jayaprakash / Date: 2/13/12 / Time: 4:53 PM
*/
public interface LoadBalancerMBean {
    void printReport();

    String[] findOwners(String key);
}
