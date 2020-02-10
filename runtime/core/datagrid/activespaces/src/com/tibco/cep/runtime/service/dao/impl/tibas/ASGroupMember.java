package com.tibco.cep.runtime.service.dao.impl.tibas;

import com.tibco.as.space.Member;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;
import com.tibco.cep.runtime.service.cluster.gmp.impl.AbstractGroupMember;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 1:38:03 PM
*/
public class ASGroupMember extends AbstractGroupMember<Member> {

	protected UID memberId;

    public ASGroupMember(ASGroupMemberMediator mediator, MemberConfiguration config, boolean local, Member member) {
        super(mediator, config, local, member);
        this.memberId = new UID(member.getName());
    }

    @Override
    public UID getMemberId() {
        return memberId;
    }
    
    @Override
    public String getMemberName() {
        return nativeMember.getName();
    }
    
    @Override 
    public Member getNativeMember() {
    	return nativeMember;
    }
    
    public String toString() {
    	return "ASGroupMember [id=" + this.memberId + 
    			" seeder=" + this.config.getClusterConfiguration().isStorageEnabled() + 
    			" state=" + this.getMemberState() + "]";
    }
}
