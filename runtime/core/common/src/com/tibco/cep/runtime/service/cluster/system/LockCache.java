package com.tibco.cep.runtime.service.cluster.system;

import com.tibco.cep.runtime.service.cluster.Cluster;

public interface LockCache<K> {

	boolean lock(K key, long givenTimeToSpendMillis) throws Exception;

	boolean unlock(K key) throws Exception;
	
	void init (Cluster cluster) throws Exception;
	
	void start();

}
