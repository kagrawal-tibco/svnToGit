package com.tibco.cep.runtime.service.cluster;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.Invocable.Result;
import com.tibco.cep.runtime.service.om.api.InvocationService;

public class DefaultInvocationService implements InvocationService {

	ControlDao<String, Integer> remoteInvocation;

	public DefaultInvocationService() {
	}

	@Override
	public Map<String, Result> invoke(Invocable invocable, Set<? extends GroupMember> members) {

		// Set<GroupMember> ftlGrpMap = new HashSet<GroupMember>();
		// if (members != null) {
		// for (GroupMember m : members) {
		// ftlGrpMap.add(m);
		// }
		// }

		return remoteInvocation.invoke(invocable, members);
	}

	@Override
	public void init(Cluster cluster) throws Exception {
		remoteInvocation = cluster.getClusterProvider().createControlDao(String.class, Integer.class,
				ControlDaoType.Master, cluster);
	}

}
