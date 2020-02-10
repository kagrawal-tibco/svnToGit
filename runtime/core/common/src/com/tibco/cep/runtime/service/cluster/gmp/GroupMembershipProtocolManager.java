/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.service.cluster.gmp;

import com.tibco.cep.runtime.service.cluster.MultiAgentCluster;

/**
 * This class manages the GMP protocol for a BusinessEvent Cluster Members
 * Should we designate a Manager or should we use the underlying Transport (AS/Coherence)
 * to provide the membership
 */
public class GroupMembershipProtocolManager {

    MultiAgentCluster multiAgentCluster;

    public GroupMembershipProtocolManager(MultiAgentCluster mac) {
        this.multiAgentCluster = mac;
    }
}
