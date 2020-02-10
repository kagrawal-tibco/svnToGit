package com.tibco.cep.runtime.service.cluster.om.impl;

import com.tangosol.net.*;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfigurationRetriever;
import com.tibco.cep.runtime.service.cluster.gmp.impl.AbstractGroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.Invocable.Result;
import com.tibco.cep.util.annotation.LogCategory;

import java.util.*;
import java.util.logging.Level;

@LogCategory("coherence.runtime.cluster.om.membermediator")
public class CoherenceGroupMemberMediator
        extends AbstractGroupMemberMediator<Member, CoherenceGroupMember, CoherenceDaoProvider>
        implements MemberListener {
    protected CacheService memberService;

    protected InvocationService invocationService;

    public CoherenceGroupMemberMediator(CoherenceDaoProvider daoProvider) {
        super(daoProvider);
    }

    @Override
    protected void initHook() {
        memberService = daoProvider.getCoherenceReplicatedService();
        invocationService = daoProvider.getCoherenceInvocationService();

        memberService.addMemberListener(this);
    }

    //------------------

    @Override
    protected Member fetchLocalNativeMember() {
        return memberService.getCluster().getLocalMember();
    }

    private CoherenceGroupMember xform(Member member, boolean local) {
    	MemberConfiguration config = null;
    	if (local) {
    		config = MemberConfigurationRetriever.fetchLocalMemberConfiguration();
    	} else {
	        Set<GroupMember> set = new HashSet<GroupMember>();
	    	GroupMember<Member> dummy = new CoherenceGroupMember(this, null, true, member);
	    	set.add(dummy);
	    	
	        Map map = daoProvider.getInvocationService().invoke(new MemberConfigurationRetriever(), set);
	        Invocable.Result r = (Result) map.get(member.getUid().toString());
	        if (r == null) {
	        	Iterator i = map.entrySet().iterator();
//	        	while (i.hasNext()) {
//	        		Map.Entry e = (Entry) i.next();
//	        		Object key = e.getKey();
//	        		Object value = e.getValue();
//	        		System.err.println("* key="+ key + ", val=" + value.getClass().getName());
//	        	}
//
	            return null;
	        }
	        config = (MemberConfiguration) r.getResult();
    	}
        return new CoherenceGroupMember(this, config, local, member);
    }

    @Override
    protected CoherenceGroupMember transformLocalMember(Member member) {
        return xform(member, true);
    }

    @Override
    protected Collection<Member> fetchNativeMembers() {
        return memberService.getCluster().getMemberSet();
    }

    @Override
    protected CoherenceGroupMember transformLiveNativeMember(Member member) {
        try {
            Member local = fetchLocalNativeMember();

            return xform(member, (member == local));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving member details from internal grid library", e);

            return null;
        }
    }

    //------------------

    @Override
    public void memberJoined(MemberEvent memberEvent) {
        Member member = memberEvent.getMember();

        handleMemberJoined(member);
    }

    @Override
    public void memberLeaving(MemberEvent memberEvent) {
        //Ignore.
    }

    @Override
    public void memberLeft(MemberEvent memberEvent) {
        Member member = memberEvent.getMember();

        handleMemberLeft(member);
    }
}
