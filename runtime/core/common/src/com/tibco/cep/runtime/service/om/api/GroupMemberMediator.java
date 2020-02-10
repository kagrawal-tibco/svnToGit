package com.tibco.cep.runtime.service.om.api;

import java.util.Collection;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;

public interface GroupMemberMediator {
    void init(Cluster cluster) throws Exception;

    void addGroupMemberServiceListener(GroupMemberServiceListener listener);

    void removeGroupMemberServiceListener(GroupMemberServiceListener listener);

    GroupMember getLocalMember();

    Collection<? extends GroupMember> getMembers();
    
    Collection<? extends GroupMember> updateAllMembers();
}
