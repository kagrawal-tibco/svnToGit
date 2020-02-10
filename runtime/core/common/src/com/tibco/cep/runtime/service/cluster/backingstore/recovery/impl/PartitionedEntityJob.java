package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.om.api.Invocable;

public class PartitionedEntityJob implements Invocable {
	
	private PartitionedEntity batch;
	private Long resultCount = 0L;
	
	public PartitionedEntityJob(PartitionedEntity batch) {
		super();
		this.batch = batch;
	}
	
	@Override
    public Object invoke(Map.Entry entry) throws Exception {
        int retryCount = batch.getRetryCount();
        int currentRetryCount = 0;
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        Logger logger = cluster.getRuleServiceProvider().getLogger(this.getClass());
        BackingStore store = cluster.getRecoveryBackingStore();
		if (store == null) {
			throw new RuntimeException(
					"Unable to instantiate backing store for entityProvider="
							+ batch.getTypeName());
		}

		logger.log(Level.DEBUG, "#### Pre-loading batch %s", batch.getKey());
		
		return false;

		/**
		NamedCache namedCache = CacheFactory.getCache(batch.getCacheName(),
				(ClassLoader) cluster.getRuleServiceProvider().getTypeManager());
		PartitionSet partsMember = new PartitionSet(batch.getPartitionCount());
		partsMember.add(batch.getPartition());
		AlwaysFilter filter = new AlwaysFilter();
		PartitionedIterator pIterator = 
			new PartitionedIterator(namedCache, filter, partsMember,
					PartitionedIterator.OPT_KEYS | PartitionedIterator.OPT_BY_PARTITION);
		int batchSize = 1000;
		ArrayList<Long> keys = new ArrayList<Long>();
		while(true) {
			try {
				while(pIterator.hasNext()) {
					Long entityId = (Long) pIterator.next();
					keys.add(entityId);
					if(keys.size() == batchSize) {
						long loaded = store.loadObjects(batch.getTypeId(), keys.toArray(new Long[keys.size()]), batch.isLoadHandles());
						resultCount = resultCount + loaded;
						logger.log(Level.TRACE, "#### Pre-load reached=%s", resultCount);
						keys.clear();
					}
				}
				if (keys.size() > 0) {
					long loaded = store.loadObjects(batch.getTypeId(), keys.toArray(new Long[keys.size()]), batch.isLoadHandles());
					resultCount = resultCount + loaded;
					keys.clear();
				}
				logger.log(Level.DEBUG,
						"#### Pre-loaded resultcount=%s", resultCount);
				return true;
			} catch (Exception e) {
				currentRetryCount++;
				if (retryCount > 0 && currentRetryCount >= retryCount) {
					logger.log(Level.ERROR,
							"Exception %s while pre-loading class %s",
							e.getMessage(), batch.getTypeName());
				    throw new RuntimeException(msg);
				} else {
					logger.log(Level.ERROR, 
							"Exception while preload. Retrying after 5 sec.", e);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
					}
				}
			}
		}
		*/
	}
}