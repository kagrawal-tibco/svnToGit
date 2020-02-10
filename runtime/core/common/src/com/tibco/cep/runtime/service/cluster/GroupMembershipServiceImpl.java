/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.cep.runtime.service.cluster;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.gmp.QuorumStatus;
import com.tibco.cep.runtime.service.cluster.gmp.ResumeAgents;
import com.tibco.cep.runtime.service.cluster.gmp.SuspendAgents;
import com.tibco.cep.runtime.service.cluster.system.ControlKey;
import com.tibco.cep.runtime.service.cluster.system.DefaultControlKey;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.service.Service;

public class GroupMembershipServiceImpl implements GroupMembershipService {

	final static Logger logger = LogManagerFactory.getLogManager().getLogger(GroupMembershipServiceImpl.class);

	public static State currentState = State.READY;

	protected Cluster cluster;

	protected ControlDao<String, byte[]> masterDao;

	protected GroupMemberMediator groupMemberMediator;

	protected GroupMemberServiceListener specialListener;

	protected final LinkedBlockingDeque<GroupMemberServiceListener> listeners = 
			new LinkedBlockingDeque<GroupMemberServiceListener>();

	protected final ReentrantLock gmpLock = new ReentrantLock();

	protected final Condition gmpCondition = gmpLock.newCondition();

	protected boolean strictCheck = false;
	protected boolean selfRepair = true;
	protected boolean lenientQuorumCheck = false;
	protected long lockWaitTime = 10000;
	protected Level lockLogLevel = Level.DEBUG;

//	private InvocationService invocationService;

	public GroupMembershipServiceImpl() {
		
	}

	public void init(Cluster cluster) throws Exception {
		this.cluster = cluster;
//		ASClusterProvider clusterProvider = (ASClusterProvider) ;
//		ASDaoProvider daoProvider = clusterProvider.getDaoProvider();

		this.groupMemberMediator = cluster.getGroupMemberMediator();
		lockWaitTime = Long.parseLong(System.getProperty(
				SystemProperty.CLUSTER_LOCK_WAIT.getPropertyName(), "10000"));
		lockLogLevel = Level.valueOf(System.getProperty(
				SystemProperty.CLUSTER_LOCK_LOG_LEVEL.getPropertyName(), "info"));
		strictCheck = Boolean.parseBoolean(System.getProperty(
				SystemProperty.CLUSTER_MIN_CACHE_SERVERS_STRICT.getPropertyName(), "false"));
		selfRepair = Boolean.parseBoolean(System.getProperty(
				SystemProperty.CLUSTER_MIN_CACHE_SERVERS_STRICT_SELF_REPAIR.getPropertyName(), "false"));
		lenientQuorumCheck = Boolean.parseBoolean(System.getProperty(
				SystemProperty.CLUSTER_CACHE_SERVER_QUORUM_CHECK_LENIENT.getPropertyName(), String.valueOf(getLocalMember().isSeeder())));
		masterDao = cluster.getClusterProvider().createControlDao(String.class, byte[].class, ControlDaoType.Master, cluster);
		
//		invocationService = cluster.getClusterProvider().getInvocationService();
	}

	@Override
	public void lock() {
		gmpLock.lock();

		ControlKey key = DefaultControlKey.GroupMembershipServiceStateKey;

		if (logger.isEnabledFor(lockLogLevel)) {
			logger.log(lockLogLevel, "Attempting to lock the cluster [%s : %s msec.] ", masterDao.getName(), lockWaitTime);
		}

		boolean acquireLock = false;
		try {
			for (;;) {
				acquireLock = masterDao.lock(key.toString(), lockWaitTime);
				if (acquireLock) {
					if (logger.isEnabledFor(lockLogLevel)) {
                        logger.log(lockLogLevel, "Cluster locked successfully [%s] ", masterDao.getName());
					}

					break;
				}
                if (logger.isEnabledFor(Level.INFO)) { /* always log at INFO level */
                    logger.log(Level.INFO, "Attempting to lock the cluster, retrying [%s] ", masterDao.getName());
                }
			}
		}
        finally {
			if (!acquireLock) {
				gmpLock.unlock();
			}
		}
	}

	@Override
	public boolean unlock() {
		ControlKey key = DefaultControlKey.GroupMembershipServiceStateKey;

		if (logger.isEnabledFor(lockLogLevel)) {
			logger.log(lockLogLevel, "Attempting to unlock the cluster [%s] ", masterDao.getName());
		}

		boolean unLock;
		try {
			unLock = masterDao.unlock(key.toString());
        }
        finally {
			gmpLock.unlock();
		}

		if (unLock) {
			if (logger.isEnabledFor(lockLogLevel)) {
                logger.log(lockLogLevel, "Cluster unlocked successfully [%s] ", masterDao.getName());
			}
		}

		return unLock;
	}

	
	public ControlDao getMasterDao() {
		return this.masterDao;
	}

	public Set<GroupMember> getMembers() {
		HashSet<GroupMember> members = new HashSet<GroupMember>();
		members.addAll(this.groupMemberMediator.getMembers());
		return members;
	}

	public GroupMember getLocalMember() {
		if (this.groupMemberMediator != null) {
			return this.groupMemberMediator.getLocalMember();
		}
		else {
			return null;
		}
	}

	public Set<GroupMember> getStorageEnabledMembers() {
        Collection<? extends GroupMember> members = this.groupMemberMediator.getMembers();
		HashSet<GroupMember> storageMembers = new HashSet<GroupMember>();
		for (GroupMember member : members) {
			if (member.isSeeder()) {
				storageMembers.add(member);
			}
		}
		return storageMembers;
	}

	public Set<GroupMember> refreshStorageEnabledMembers() {
        Collection<? extends GroupMember> members = this.groupMemberMediator.updateAllMembers();
		HashSet<GroupMember> storageMembers = new HashSet<GroupMember>();
		for (GroupMember member : members) {
			if (member.isSeeder()) {
				storageMembers.add(member);
			}
		}
		return storageMembers;
	}

	public boolean suspendAll() throws Exception {
		return SuspendAgents.suspendAgents(null);
	}

	public boolean resumeAll() throws Exception {
		return ResumeAgents.resumeAgents(null);
	}

	public boolean suspend(Set<GroupMember> members) {
		// TODO: Suresh
		return false;
	}

	public boolean resume(Set<GroupMember> members) {
		// TODO: Suresh
		return false;
	}

	/**
	 * Checks for all the StorageEnabledMembers availability. Generally Quorum
	 * is established when 'storageCount >= minCacheServers' In Lenient mode
	 * however, Quorum needs only 'storageCount >= minSeeders'
	 * 
	 * @return
	 */
	@Override
	public QuorumStatus checkQuorum() {
		return checkQuorum(lenientQuorumCheck, false);
	}

    @Override
	public QuorumStatus checkQuorum(boolean lenient, boolean forceUpdate) {
		gmpLock.lock();
		try {
			int minCacheServers = this.cluster.getClusterConfig().getMinCacheServers();
			int minSeeders = this.cluster.getClusterConfig().getMinSeeders();

			int storageEnabledCount = 0;
			try {
				if (forceUpdate) {
					storageEnabledCount = this.refreshStorageEnabledMembers().size();
				}
				else {
					storageEnabledCount = this.getStorageEnabledMembers().size();
				}
			}
			catch (Exception e) {
				logger.log(Level.ERROR, "Storage enabled quorum test failed. Assuming '0' quorum", e);
			}

			int storageInQuorumCount = 0;
			try {
				for (GroupMember member : this.getStorageEnabledMembers()) {
					if (member.getMemberState() == State.QUORUMREACHED || member.getMemberState() == State.QUORUMLOST) {
						storageInQuorumCount++;
					}
					logger.log(Level.TRACE, "Member %s cached quorum-state result: %s [%s]",
							member.getMemberName(), member.getMemberState(), storageInQuorumCount);
				}
			}
			catch (Exception e) {
				logger.log(Level.ERROR, "Storage cached in quorum test failed. Assuming '0' quorum", e);
			}

			/**
			storageInQuorumCount = 0;
			try {
				for (GroupMember member : this.refreshStorageEnabledMembers()) {
					if (member.getMemberState() == State.QUORUMREACHED || member.getMemberState() == State.QUORUMLOST) {
						storageInQuorumCount++;
					}
					logger.log(Level.TRACE, "Member %s recent quorum-state result: %s [%s]",
							member.getMemberName(), member.getMemberState(), storageInQuorumCount);
				}
			}
			catch (Exception e) {
				logger.log(Level.ERROR, "Storage recent in quorum test failed. Assuming '0' quorum", e);
			}
			*/

			boolean isQuorum = false;

			if (lenient) {
				isQuorum = ((storageInQuorumCount > 0) && (storageInQuorumCount >= minSeeders)) ||
						   ((storageEnabledCount > 0) && (storageEnabledCount >= minCacheServers));
			}
			else {
				isQuorum = ((storageEnabledCount > 0) && (storageEnabledCount >= minCacheServers));
			}

			logger.log(Level.TRACE, "Storage quorum check [%s] local-state=%s set-lenient=%s storage-enabled=%s reached-quorum=%s : Quorum=%s", 
					getLocalMember().getMemberName(), getLocalMember().getMemberState(), 
					lenient, storageEnabledCount, storageInQuorumCount, isQuorum);
			
			return new QuorumStatus(minCacheServers, minSeeders, storageEnabledCount, storageInQuorumCount, isQuorum);
			//BALA:TODO:6
//			return new QuorumStatus(0, 0, 0, 0, true);

		}
		finally {
			gmpLock.unlock();
		}
	}

	@Override
	public QuorumStatus waitForQuorum() {
		gmpLock.lock();
		QuorumStatus initialStatus = null;
		try {
			while (true) {
				QuorumStatus quorumStatus = checkQuorum();

				if (quorumStatus.equals(initialStatus) == false) {
	                logger.log(Level.DEBUG, "%s is in %s state waiting for quorum: %s of %s active",
							this.cluster.getClusterName(), currentState,
							quorumStatus.getActualSeeders(), quorumStatus.getMinCacheServers(), 
							quorumStatus.getQuorumReachedSeeders(), quorumStatus.getMinSeeders());
	                initialStatus = quorumStatus;
				}
				
				if (!quorumStatus.isQuorum()) {
					try {
						if (currentState != State.WAITINGFORQUORUM) {
							logger.log(Level.INFO, "%nWaiting for quorum:" +
								"%n At least [%d] or more storage enabled members (seeders) have to be present in the cluster for proper functioning." +
								"%n Currently there are [%d] storage enabled members in the cluster.",
									quorumStatus.getMinCacheServers(), quorumStatus.getActualSeeders());
							transitionTo(State.WAITINGFORQUORUM);
						}

						gmpCondition.await(10, TimeUnit.SECONDS);
					}
					catch (InterruptedException ie) {
					}
				}
				else {
					transitionTo(State.QUORUMREACHED);

					return quorumStatus;
				}
			}
		}
		finally {
			gmpLock.unlock();
		}
	}

	public void transitionTo(State state) {
		if (currentState != state) {
			logger.log(Level.INFO, "Member %s is transitioning from %s to: %s state",
					getLocalMember().getMemberName(), getLocalMember().getMemberState(), state);
			getLocalMember().setMemberState(state);
			currentState = state;
		}

		/**
		if (state == State.WAITINGFORQUORUM && 
            getLocalMember().getMemberState() == State.QUORUMREACHED) {
			getLocalMember().setMemberState(State.QUORUMLOST);
			logger.log(Level.INFO, "Member %s lost QUORUM transitioned to: %s state",
					getLocalMember().getMemberName(), getLocalMember().getMemberState());
		}
		else {
			getLocalMember().setMemberState(state);
		}
		*/
	}
	
    public State currentState() {
    	return currentState;
    }

	public void start() throws LifecycleException {
		masterDao.start();

		// TODO: Suresh a GMPPinger
	}

	public void stop() throws LifecycleException {

	}

	public Id getResourceId() {
		return null;
	}

    public Service recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
		return null;
	}

	@Override
    public void setFirstGroupMemberServiceListener(GroupMemberServiceListener listener) {
		synchronized (listeners) {
			if (specialListener != null) {
				// Remove the old one.
				listeners.remove(specialListener);
			}

			specialListener = listener;
			listeners.addFirst(specialListener);
		}
	}

    public void addGroupMemberServiceListener(GroupMemberServiceListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

    public void removeGroupMemberServiceListener(GroupMemberServiceListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);

			if (specialListener != null && specialListener.equals(listener)) {
				specialListener = null;
			}
		}
	}

	@Override
	public void memberJoined(GroupMember member) {
		for (GroupMemberServiceListener listener : listeners) {
			listener.memberJoined(member);
		}

		gmpLock.lock();
		try {
			if ((selfRepair == true) && (cluster != null)) {
				State prevState = GroupMembershipServiceImpl.currentState;
                QuorumStatus quorumStatus = checkQuorum();
				if (quorumStatus.isQuorum() == true) {
					if (prevState != State.QUORUMREACHED) {
						logger.log(Level.INFO, "Resuming operations: %n There are now [%d] storage enabled members in the cluster, satisfying minimum [%d] have to be present.",
								quorumStatus.getActualSeeders(), quorumStatus.getMinCacheServers());
					}
					resumeAll();
				}
			}
			gmpCondition.signalAll();
		}
		catch (Exception e) {
			logger.log(Level.WARN, e, "Failed handling member-join notification [%s]", member);
		}
		finally {
			gmpLock.unlock();
		}
	}

	@Override
	public void memberLeft(GroupMember member) {
		for (GroupMemberServiceListener listener : listeners) {
			listener.memberLeft(member);
		}

		gmpLock.lock();
		try {
			if (strictCheck) {
				State prevState = GroupMembershipServiceImpl.currentState;
                QuorumStatus quorumStatus = checkQuorum();
				if (quorumStatus.isQuorum() == false) {
					if (prevState != State.WAITINGFORQUORUM) {
						logger.log(Level.WARN, "Suspending operations: There are only [%d] storage enabled members in the cluster, where minimum [%d] have to be present.",
								quorumStatus.getActualSeeders(), quorumStatus.getMinCacheServers());
					}
					suspendAll();
				}
			}
			gmpCondition.signalAll();
		}
		catch (Exception e) {
			logger.log(Level.WARN, e, "Failed handling member-left notification");
		}
		finally {
			gmpLock.unlock();
		}
	}

	@Override
	public void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus) {
		for (GroupMemberServiceListener listener : listeners) {
			listener.memberStatusChanged(member, oldStatus, newStatus);
		}
	}

//	@Override
//	public InvocationService getInvocationService() {
//		return invocationService;
//	}

//	@SuppressWarnings("serial")
//	public static class GroupMembershipServiceStateKey implements ControlKey {
//		protected String fixedKey = "$sharedState";
//
//		@Override
//		public boolean equals(Object okey) {
//			if (this == okey) {
//				return true;
//			}
//			if (!(okey instanceof GroupMembershipServiceStateKey)) {
//				return false;
//			}
//
//			GroupMembershipServiceStateKey that = (GroupMembershipServiceStateKey) okey;
//
//			if (!fixedKey.equals(that.fixedKey)) {
//				return false;
//			}
//
//			return true;
//		}
//
//		@Override
//		public int hashCode() {
//			return fixedKey.hashCode();
//		}
//	}
}
