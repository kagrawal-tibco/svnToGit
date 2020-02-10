package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Invocable;

public class DistributedBatchJob implements Invocable {
	
	private DistributedBatch batch;
	private Long result = 0L;
	
	public DistributedBatchJob(DistributedBatch param) {
		super();
		this.batch = param;
	}

	@Override
    public Object invoke(Map.Entry entry) throws Exception {
		int typeId = batch.getTypeId();
		int maxRows = -1;
		long start = batch.getStartId();
		long end = batch.getEndId();
		int retryCount = 3;
		int currentRetryCount = 0;
		
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        Logger logger = cluster.getRuleServiceProvider().getLogger(this.getClass());
        while (cluster.getMetadataCache().hasStarted() == false) {
            Thread.sleep(1000);
            logger.log(Level.INFO, "Waiting for Metadata cache to initialize");
        }
        
        EntityDao cache = cluster.getMetadataCache().getEntityDao(typeId);
		String typeName = cache.getEntityClass().getName();
        boolean loadHandles = cache.getConfig().isRecoverOnStartup();
        boolean loadEntities = cache.getConfig().isLoadOnStartup();
        logger.log(Level.DEBUG, "#### Pre-loading %s batch %s", typeName, batch.getKey());
        		
		while(true) {
            try {
                BackingStore store = cluster.getRecoveryBackingStore();
				if (store != null) {
					long loaded = store.loadObjects(typeId, maxRows, start, end, loadHandles, loadEntities);
					result = Long.valueOf(loaded);
					logger.log(Level.DEBUG,
									"#### Pre-loaded %s batch %s and result count=%s",
									typeName, batch.getKey(), String.valueOf(result));
					return result;
				} else {
					throw new RuntimeException(
							"Unable to instantiate backing store for entityProvider="
									+ typeName);
				}
			} catch (Exception e) {
				currentRetryCount++;
				if (retryCount > 0 && currentRetryCount >= retryCount) {
					logger.log(Level.ERROR,
							"Exception %s while pre-loading class %s",
							e, e.getMessage(), typeName);
				    throw new RuntimeException(e);
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
	}
}
