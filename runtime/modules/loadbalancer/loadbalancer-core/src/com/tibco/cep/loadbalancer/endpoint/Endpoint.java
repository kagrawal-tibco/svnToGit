package com.tibco.cep.loadbalancer.endpoint;

import java.util.Properties;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.message.DistributionStrategy;
import com.tibco.cep.util.annotation.ReadOnly;

/*
* Author: Ashwin Jayaprakash / Date: Jul 19, 2010 / Time: 2:19:38 PM
*/
public interface Endpoint {
    Id getId();

    Id getSourceId();

    @ReadOnly
    Properties getProperties();

    DistributionStrategy getDistributionStrategy();
}
