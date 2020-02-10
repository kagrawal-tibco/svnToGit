package com.tibco.cep.runtime.service.dao.impl.tibas;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Member;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.listener.SpaceMemberListener;
import com.tibco.as.space.listener.SpaceRemoteMemberListener;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService.State;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfigurationRetriever;
import com.tibco.cep.runtime.service.cluster.gmp.MemberStateRetriever;
import com.tibco.cep.runtime.service.cluster.gmp.impl.AbstractGroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.Invocable.Result;
import com.tibco.cep.util.annotation.LogCategory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 10:23:32 AM
*/
@LogCategory("as.runtime.cluster.om.membermediator")
public class ASGroupMemberMediator
        extends AbstractGroupMemberMediator<Member, ASGroupMember, ASDaoProvider> {
	
    protected SpaceMembershipAdapter memberListener;

    public ASGroupMemberMediator(ASDaoProvider daoProvider) {
        super(daoProvider);
    }

    @Override
    protected void initHook() {
        try {
            ASControlDao masterControlDao =
                    (ASControlDao) daoProvider.createControlDao(Object.class, Object.class, ControlDaoType.Master);
            memberListener = new SpaceMembershipAdapter();

            daoProvider.getMetaspace().listenSpaceMembers(masterControlDao.getName(), memberListener);
            daoProvider.getMetaspace().listenSpaceRemoteMembers(masterControlDao.getName(), memberListener);
            
            // Initialize ASPersistence provider listener if necessary
            ASPersistenceProvider asPersistenceProvider = daoProvider.getBDBPersistenceProvider();
            if (asPersistenceProvider != null) {
                asPersistenceProvider.startMembershipListener();
            }
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    //---------------

    @Override
    protected Member fetchLocalNativeMember() {
        try {
            return daoProvider.getMetaspace().getSelfMember();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized ASGroupMember xform(Member member, boolean local) {
    	State state = null;
        MemberConfiguration config = null;
        GroupMember<Member> groupMember = null;

        if (local) {
            config = MemberConfigurationRetriever.fetchLocalMemberConfiguration();
            state = MemberStateRetriever.fetchLocalMemberState();
            groupMember = new ASGroupMember(this, config, local, member);
        }
//BALA: Using the invocation service instead..        
//        else {
//            InvocableContext context = new DefaultInvocableContext();
//            context.put(Member.class, member);
//            context.put(InvocableMember.class, ASMemberConfigurationRetriever.class);
//
//            DefaultInvocableDef invocableDef = new DefaultInvocableDef(null, context);
//
//            ControlDao masterControlDao =
//                    daoProvider.createControlDao(Object.class, Object.class, ControlDaoType.Master);
//
//            Map results = (Map) masterControlDao.invoke(invocableDef);
//
//            config = (MemberConfiguration) results.values().iterator().next();
//        }
//BALA: Using the invocation service instead..     
        else {
            Set<GroupMember> set = new HashSet<GroupMember>();
            groupMember = new ASGroupMember(this, null, local, member);
            set.add(groupMember);

            //--- Retrieve Config
            Map configmap = daoProvider.getInvocationService().invoke(new MemberConfigurationRetriever(), set);
            if (configmap == null) {
            	config = null;
            }
            else {
	            Invocable.Result res = (Result) configmap.get(member.getName());
	            if (res == null) {
	            	config = null;
	            }
	            else {
	            	config = (MemberConfiguration) res.getResult();
	            }
            }
            
            //--- Retrieve State
            Map statemap = daoProvider.getInvocationService().invoke(new MemberStateRetriever(), set);
            if (statemap == null) {
                state = null;
            }
            else {
	            Invocable.Result res = (Result) statemap.get(member.getName());
	            if (res == null) {
	                state = null;
	            }
	            else {
	            	state = (State) res.getResult();
	            }
            }
        }

        groupMember.setMemberConfig(config);
        groupMember.setMemberState(state);
        
        return (ASGroupMember) groupMember;
    }

    @Override
    protected ASGroupMember transformLocalMember(Member member) {
        return xform(member, true);
    }

    @Override
    protected Collection<Member> fetchNativeMembers() {
        try {
            ASControlDao masterControlDao =
                    (ASControlDao) daoProvider.createControlDao(Object.class, Object.class, ControlDaoType.Master);
            Collection<Member> members = daoProvider.getMetaspace().getSpaceMembers(masterControlDao.getName());
            members.addAll(daoProvider.getMetaspace().getSpaceRemoteMembers(masterControlDao.getName()));
            return members;
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected ASGroupMember transformLiveNativeMember(Member member) {
        try {
            Member local = fetchLocalNativeMember();

            return xform(member, member.equals(local));
        }
        catch (Exception e) {
            logger.log(Level.FINE, "Failed translating from generic member to AS [" + e.getMessage() + "]", e);

            return null;
        }
    }

    //---------------

    protected class SpaceMembershipAdapter implements SpaceMemberListener, SpaceRemoteMemberListener {
        @Override
        public void onJoin(String s, Member member, DistributionRole distributionRole) {
            logger.log(Level.FINE, "SpaceMembershipAdapter onJoin [" + member + " / " + s + "]");
            handleMemberJoined(member);
        }

        @Override
        public void onUpdate(String s, Member member, DistributionRole distributionRole) {
            logger.log(Level.FINE, "SpaceMembershipAdapter onUpdate [" + member + " / " + s + "]");
        }

        @Override
        public void onLeave(String s, Member member) {
            logger.log(Level.FINE, "SpaceMembershipAdapter onLeave [" + member + " / " + s + "]");
            handleMemberLeft(member);
        }

		@Override
		public void onJoin(String spaceName, Member remoteMember, Member proxy) {
			logger.log(Level.FINE, "SpaceMembershipAdapter onJoin RemoteMember [" + remoteMember + " / " + spaceName + "]");
			handleMemberJoined(remoteMember);
		}

		@Override
		public void onLeave(String spaceName, Member remoteMember, Member proxy) {
			logger.log(Level.FINE, "SpaceMembershipAdapter onLeave RemoteMember [" + remoteMember + " / " + spaceName + "]");
			handleMemberLeft(remoteMember);
		}
    }
}
