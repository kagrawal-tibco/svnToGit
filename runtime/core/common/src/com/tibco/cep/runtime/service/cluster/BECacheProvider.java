package com.tibco.cep.runtime.service.cluster;

import java.util.Iterator;

/**
 * @author ssinghal
 *
 */
public interface BECacheProvider extends CacheProvider{
	
	terator query(String query, Object[] queryParameters, Object queryOptions, String entityPath) throws Exception;

}
