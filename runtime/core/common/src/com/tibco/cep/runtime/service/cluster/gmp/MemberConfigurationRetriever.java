package com.tibco.cep.runtime.service.cluster.gmp;

import java.util.Map;

import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.Invocable;

public class MemberConfigurationRetriever implements Invocable {

	private static final long serialVersionUID = 8395760347960007682L;

	public static MemberConfiguration fetchLocalMemberConfiguration() {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();

        return new MemberConfiguration(cluster.getClusterConfig(), null);
    }

	public Object invoke(Map.Entry  entry) throws Exception {
		return fetchLocalMemberConfiguration();
	}
}
