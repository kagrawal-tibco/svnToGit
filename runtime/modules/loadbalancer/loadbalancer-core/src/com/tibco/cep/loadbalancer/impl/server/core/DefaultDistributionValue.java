package com.tibco.cep.loadbalancer.impl.server.core;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.server.core.DistributionValue;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.util.annotation.LogCategory;

import java.util.logging.Logger;

import static com.tibco.cep.util.Helper.$logger;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 5:55:51 PM
*/

@LogCategory("loadbalancer.core.server.router")
class DefaultDistributionValue implements DistributionValue {
    static Logger logger;

    final Member currentOwner;

    public DefaultDistributionValue(Member currentOwner, ResourceProvider resourceProvider) {
        if (logger == null) {
            logger = $logger(resourceProvider, getClass());
        }

        this.currentOwner = currentOwner;
    }

    @Override
    public Member getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public boolean hasCurrentOwner() {
        return (currentOwner != null);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "currentOwner=" + currentOwner + '}';
    }
}
