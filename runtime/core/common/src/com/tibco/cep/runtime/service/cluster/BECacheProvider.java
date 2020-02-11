package com.tibco.cep.runtime.service.cluster;

import java.util.Iterator;

import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;

/**
 * @author ssinghal
 *
 */
public interface BECacheProvider extends CacheProvider{
	
	void saveWorkItem(WorkTuple item) throws Exception;
	
	Iterator query(String query, Object[] queryParameters, Object queryOptions, String entityPath) throws Exception;

}
