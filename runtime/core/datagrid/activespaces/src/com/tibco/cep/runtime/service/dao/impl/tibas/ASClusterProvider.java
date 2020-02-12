package com.tibco.cep.runtime.service.dao.impl.tibas;

import java.util.Properties;

import com.tibco.be.util.config.ClusterProviderConfig;
import com.tibco.cep.runtime.service.cluster.AbstractClusterProvider;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;

public class ASClusterProvider extends AbstractClusterProvider {

	private ASDaoProvider daoProvider;

	public ASClusterProvider(ClusterProviderConfig beClusterConfig) {
		super(beClusterConfig);
		daoProvider = ASDaoProvider.getInstance(beClusterConfig.getProperty("clusterName"), beClusterConfig.getProperties());
		groupMemberMediator = new ASGroupMemberMediator(daoProvider);
	}

	@Override
	public void init(Properties props, Object... objects) throws Exception {
		super.init(props, objects);
		daoProvider.init(cluster);
	}

	@Override
	public <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass, ControlDaoType daoType,
			Object... additionalProps) {
		String daoName = getDaoName(daoType, additionalProps);
		ControlDao cDao = controlDaos.get(daoName);
		if (cDao == null) {
			cDao = daoProvider.createControlDao(keyClass, valueClass, daoType, additionalProps);
			controlDaos.put(daoName, cDao);
		}
		return cDao;
	}

	public ASDaoProvider getDaoProvider() {
		return daoProvider;
	}
}
