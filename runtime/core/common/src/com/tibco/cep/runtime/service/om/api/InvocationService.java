/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.om.EventLivenessChecker;
import com.tibco.cep.runtime.service.om.api.Invocable.Result;

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
    
    /**
	 * 
	 * @param keys Keys to use for invoking the Invocable, once for each key found in the cache
	 * @param invocable Invocable to use on the remote nodes
	 * @return Value returned by the Invocable wrapped in a Result
	 * 
	 * @throws Exception
	 */
	Map invoke(Set keys, Invocable invocable) throws Exception;
	
	/**
	 * 
	 * @param filter Filter to use on the remote nodes
	 * @param invocable Invocable to invoke on the remote node
	 * @return a map of keys that passed the filter and value returned by the Invocable wrapped in Invocable.Result 
	 * @throws Exception
	 */
	Map invoke(Filter filter, Invocable invocable) throws Exception;
	
    /**
	 * 
	 * @param key Key to use for invoking the Invocable
	 * @param invocable Invocable to use on the remote nodes
	 * @return Value returned by the Invocable wrapped in a Result
	 * 
	 * @throws Exception
	 */
	Invocable.Result invokeWithKey(Object key, Invocable invocable) throws Exception;
}
