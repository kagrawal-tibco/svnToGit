package com.tibco.cep.runtime.service.cluster.gmp;

import java.util.Map;

import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService.State;
import com.tibco.cep.runtime.service.cluster.gmp.impl.GroupMembershipServiceImpl;
import com.tibco.cep.runtime.service.om.api.Invocable;

public class MemberStateRetriever implements Invocable {

	private static final long serialVersionUID = 1264775252949977643L;

	public static State fetchLocalMemberState() {
        return GroupMembershipServiceImpl.currentState;
    }

	@Override
	public Object invoke(Map.Entry  entry) throws Exception {
		return fetchLocalMemberState();
	}
}
