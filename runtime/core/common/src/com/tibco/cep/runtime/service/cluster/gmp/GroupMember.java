/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 7/7/2010
 */

package com.tibco.cep.runtime.service.cluster.gmp;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService.State;

/**
 * A Group Member is a Node (JVM process). This maps to AgentTableCache.AgentNode in 4.0/3.x
 */
public interface GroupMember<M> {
    /**
     * @return UID : A unique Identifier
     */
    UID getMemberId();

    /**
     * @return the memberName as specified by the provider - It can be null
     */
    String getMemberName();

    /**
     * @return the memberConfig as specified by the provider - It can be null
     */
    void setMemberConfig(MemberConfiguration config);
    MemberConfiguration getMemberConfig();
    
    /**
     * @return the memberState as decided by the quorum events in the cluster
     */
    void setMemberState(State state);
    State getMemberState();

    /**
     * Is the group member providing data resources such as memory.
     *
     * @return
     */
    boolean isSeeder();
    
    /**
     * 
     * @return the native representation of a cluster member
     */
    M getNativeMember();
    
}
