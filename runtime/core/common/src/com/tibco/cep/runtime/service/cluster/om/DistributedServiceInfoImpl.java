/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.runtime.service.cluster.CacheAgent;

/*
* Author: Ashwin Jayaprakash Date: May 13, 2009 Time: 5:20:58 PM
*/
public class DistributedServiceInfoImpl implements ServiceInfo {
    protected CacheAgent cacheAgent;

    public DistributedServiceInfoImpl(CacheAgent cacheAgent) {
        this.cacheAgent = cacheAgent;
    }

    public String getName() {
        return cacheAgent.getCluster().getClusterName();
    }

    public Collection<ServiceMemberInfo> getAllMemberInfo() {
        LinkedList<ServiceMemberInfo> list =
                new LinkedList<ServiceMemberInfo>();
        list.add(getLocalMemberInfo());

        return list;
    }

    public ServiceMemberInfo getLocalMemberInfo() {
        return new DistributedServiceMemberInfoImpl();
    }

    public class DistributedServiceMemberInfoImpl implements ServiceMemberInfo {
        public String getName() {
            return cacheAgent.getAgentName();
        }

        public int getUniqueId() {
            return cacheAgent.getAgentId();
        }
    }
}
