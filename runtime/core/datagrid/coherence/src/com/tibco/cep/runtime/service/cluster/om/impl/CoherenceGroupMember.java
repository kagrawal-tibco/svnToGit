package com.tibco.cep.runtime.service.cluster.om.impl;

import com.tangosol.net.Member;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;
import com.tibco.cep.runtime.service.cluster.gmp.impl.AbstractGroupMember;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 1:38:03 PM
*/

public class CoherenceGroupMember extends AbstractGroupMember<Member> {
    protected UID memberId;

    public CoherenceGroupMember(CoherenceGroupMemberMediator mediator, MemberConfiguration config, boolean local, Member nativeMember) {
        super(mediator, config, local, nativeMember);
        this.memberId = new UID(nativeMember.getUid().toString());
    }

    @Override
    public UID getMemberId() {
        return memberId;
    }

    @Override
    public String getMemberName() {
        return nativeMember.getMemberName();
    }

	@Override
	public Member getNativeMember() {
		return nativeMember;
	}
    
}
