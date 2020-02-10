/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.gmp;

import java.util.Set;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.service.Service;

public interface GroupMembershipService extends Service, GroupMemberServiceListener {
    
    Set<GroupMember> getMembers();

    GroupMember getLocalMember();

    Set<GroupMember> getStorageEnabledMembers();

    boolean suspendAll() throws Exception;

    boolean resumeAll() throws Exception;

    boolean suspend(Set<GroupMember> members);

    boolean resume(Set<GroupMember> members);

    QuorumStatus checkQuorum();

    QuorumStatus checkQuorum(boolean lenient, boolean forceUpdate);

    public void transitionTo(State state);

    public State currentState();

    QuorumStatus waitForQuorum();

    /**
     * Not for every one to use. Only the local JVM's {@link AgentManager} should use this.
     *
     * @param listener
     */
    void setFirstGroupMemberServiceListener(GroupMemberServiceListener listener);

    void addGroupMemberServiceListener(GroupMemberServiceListener listener);

    void removeGroupMemberServiceListener(GroupMemberServiceListener listener);

    void init(Cluster cluster) throws Exception;

    /**
     * Locks the cluster
     */
    void lock();

    /**
     * unlocks the cluster
     */
    boolean unlock();

    ControlDao getMasterDao();

    enum State {
        ERROR,
        INIT,
        WAITINGFORQUORUM,
        QUORUMREACHED,
        QUORUMLOST,
        LOADINGEXTERNALCLASSES,
        LOADEDEXTERNALCLASSES,
        LOADINGTYPES,
        LOADEDTYPES,
        INRECOVERY,
        RECOVERED,
        READY,
        PAUSED,
        SUSPENDED,
        SHUTDOWN_INPROGRESS,
        SHUTDOWN;
    }
}
