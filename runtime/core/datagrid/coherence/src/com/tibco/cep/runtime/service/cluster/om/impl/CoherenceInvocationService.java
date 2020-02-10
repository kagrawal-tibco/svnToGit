/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Set;
import java.util.Map.Entry;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Member;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.om.api.DefaultInvocableResult;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/*
 * Author: Bala / Date: Dec 24, 2010 / Time: 7:15:36 PM
 */
public class CoherenceInvocationService implements InvocationService {

    final static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(CoherenceInvocationService.class);

    com.tangosol.net.InvocationService nativeService;
    CoherenceDaoProvider daoProvider;

    public CoherenceInvocationService(CoherenceDaoProvider daoProvider) {
        this.daoProvider = daoProvider;
        this.nativeService = daoProvider.getCoherenceInvocationService();
    }

    @Override
    public Map<String, Invocable.Result> invokeAndObserve(Invocable invocable, GroupMember groupMember, Observer observer) {
        Set<Member> nativeMembers = new HashSet<Member>();
        nativeMembers.add((Member)groupMember.getNativeMember());
        Map resultMap = nativeService.query(new CoherenceInvocableWrapper(invocable), nativeMembers);
        Map<String, Invocable.Result> memberResults = new LinkedHashMap<String, Invocable.Result>();
        for (Iterator iter = resultMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Entry) iter.next();
            Member member = (Member) entry.getKey();
            Invocable.Result result = (Invocable.Result) entry.getValue();
            if (member.getUid().toString().equals(groupMember.getMemberId().toString())) {
                memberResults.put(member.getUid().toString(), result);
            }
        }
        observer.update(null, memberResults);
        return memberResults;
    }
      
    @Override
    public Map<String, Invocable.Result> invoke(Invocable invocable, Set<GroupMember> members) {
    	Set<Member> nativeMembers = toNative(members);
    	if (nativeMembers.size() == 0) { // If null, invoke on all members.
    		nativeMembers = CacheFactory.getCluster().getMemberSet();
    	}
        
    	Map mapResult = nativeService.query(new CoherenceInvocableWrapper(invocable), nativeMembers);
    	
		Map<String, Invocable.Result> memberResults = new LinkedHashMap<String, Invocable.Result>();
        for (Iterator iter = mapResult.entrySet().iterator(); iter.hasNext();) {
        	Map.Entry entry = (Entry) iter.next();
            Member member = (Member) entry.getKey();
			Invocable.Result result = (Invocable.Result) entry.getValue();
			memberResults.put(member.getUid().toString(), result);
        }
        return memberResults;
    }
    
    static class CoherenceInvocableWrapper implements com.tangosol.net.Invocable {
    	
    	Invocable invocable;
    	Object result;
    	
    	public CoherenceInvocableWrapper (Invocable invocable) {
    		this.invocable = invocable;
    	}

		@Override
		public Object getResult() {
			return result;
		}

		@Override
		public void init(com.tangosol.net.InvocationService arg0) {
		
		}

		@Override
		public void run() {
			DefaultInvocableResult res = new DefaultInvocableResult();
			try {
                LOGGER.log(Level.DEBUG, "Invoking %s", invocable.getClass().getName());
				Object ret = invocable.invoke(null);
                LOGGER.log(Level.DEBUG, "Invocation %s completed with success [%s]", invocable.getClass().getName(), res);
				res.setResult(ret);
				res.setStatus(Invocable.Status.SUCCESS);
			} catch (Exception e) {
				res.setResult(e);
				res.setStatus(Invocable.Status.ERROR);
                LOGGER.log(Level.DEBUG, "Invocation %s completed with exception", e, invocable.getClass().getName());
			}
			result = res;
		}
    }
    
	private Set<Member> toNative(Set<GroupMember> members) {
		Set<Member> set = new HashSet<Member>();
		if (members != null) {
			for (GroupMember<Member> m : members) {
				set.add(m.getNativeMember());
			}
		}
		return set;
	}
}
