/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import java.util.Map;
import java.util.Observer;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;

public interface InvocationService {
    /**
     * execute the callback function on the set of members. None means "ALL"
     * @param def
     * @param members
     * @return
     */
    Map<String, Invocable.Result> invoke(Invocable invocable, Set<GroupMember> members);
    
    void init(Cluster cluster) throws Exception;
    
    /**
     * execute the callback function on a member, and observe execution.
     * @param def
     * @param members
     * @return
     */
    //Map<String, Invocable.Result> invokeAndObserve(Invocable invocable, GroupMember member, Observer observer);
}
