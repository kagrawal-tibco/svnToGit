package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.LockCache;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;

public class DefaultLockCache<K> implements LockCache<K> {

	ControlDao<String, String> lock;
	
	public DefaultLockCache () {
	}
	@Override
	public boolean lock(Object key, long givenTimeToSpendMillis) {
		return lock.lock(key.toString(), givenTimeToSpendMillis);
	}

	@Override
	public boolean unlock(Object key) {
		return lock.unlock(key.toString());
	}
	@Override
	public void init(Cluster cluster) throws Exception {
		
		lock = cluster.getClusterProvider().createControlDao(String.class, String.class, ControlDaoType.ClusterLocks, cluster);
	}
	@Override
	public void start() {
		lock.start();
	}

}
