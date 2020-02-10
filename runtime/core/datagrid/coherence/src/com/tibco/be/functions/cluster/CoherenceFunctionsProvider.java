package com.tibco.be.functions.cluster;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.ValueExtractor;
import com.tibco.be.functions.coherence.query.CoherenceQueryFunctions;
import com.tibco.cep.runtime.session.RuleSessionManager;

/*
* Author: Ashwin Jayaprakash / Date: Nov 10, 2010 / Time: 11:52:15 AM
*/
public class CoherenceFunctionsProvider extends DataGridFunctionsProvider {
    @Override
    public void setIndex(String cacheName, Object property, boolean isOrdered) {
        try {
            NamedCache cache = CacheFactory.getCache(cacheName,
                RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader());
            if (property instanceof ValueExtractor) {
                cache.addIndex((ValueExtractor) property, isOrdered, null);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public int evictCache(String cacheName, Object filter, boolean deleteFromPersistence) {
		if (deleteFromPersistence == false) {
			throw new RuntimeException("Evicting cache and deleting from persistence is not supported for Oracle datagrid");
		}
		return CoherenceQueryFunctions.C_CacheOnlyMode_DeleteConcepts(cacheName, filter);
	}
}
