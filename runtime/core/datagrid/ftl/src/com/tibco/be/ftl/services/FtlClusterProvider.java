package com.tibco.be.ftl.services;

import com.tibco.be.util.config.ClusterProviderConfig;
import com.tibco.cep.runtime.service.cluster.AbstractClusterProvider;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;

public class FtlClusterProvider extends AbstractClusterProvider {
	
	
	//TODO - Write FTL Cluster impl
	

	public FtlClusterProvider(ClusterProviderConfig beClusterConfig) {
		super(beClusterConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass, ControlDaoType daoType,
			Object... additionalProps) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
