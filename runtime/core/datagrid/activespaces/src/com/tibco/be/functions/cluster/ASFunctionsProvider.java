package com.tibco.be.functions.cluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.IndexDef;
import com.tibco.as.space.IndexDef.IndexType;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTableCache;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.query.SqlFilter;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: Nov 10, 2010 / Time: 11:52:15 AM
*/
public class ASFunctionsProvider extends DataGridFunctionsProvider {

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(ASFunctionsProvider.class);

    @Override
    public void setIndex(String cacheName, Object property, boolean isOrdered) {
        String propString = "_" + property;
        try {
            String metaspaceName = System.getProperty(SystemProperty.CLUSTER_NAME.getPropertyName());
            Metaspace metaspace = ASCommon.getMetaspace(metaspaceName);
            Space space = metaspace.getSpace(cacheName);
            SpaceDef spaceDef = space.getSpaceDef();
            IndexDef indexDef;
            
            if (property instanceof String) {
        		propString = "_" + property;
                indexDef = IndexDef.create("Index_" + cacheName + propString);
                indexDef.setFieldNames((String)property);
                if (isOrdered == false) {
                    indexDef.setIndexType(IndexType.HASH); // AS default is TREE
                }
                if (null == spaceDef.getIndexDef("Index_" + cacheName + propString)) {
                    spaceDef.addIndexDef(indexDef);
                    metaspace.alterSpace(spaceDef);
                    logger.log(Level.INFO, "Created index on %s field=%s", cacheName, property);
            } else {
                    logger.log(Level.INFO, "Existing index on %s field=%s", cacheName, property);
                }
        	} else if (property instanceof String[]) {
        		String[] properties = (String[])property;
        		propString = "";
        		for (String prop : properties) {
        			propString = propString.concat("_").concat(prop);
            }
                indexDef = IndexDef.create("Index_" + cacheName + propString);
                indexDef.setFieldNames(properties);
                if (isOrdered == false) {
                    indexDef.setIndexType(IndexType.HASH); // AS default is TREE
                }
                if (null == spaceDef.getIndexDef("Index_" + cacheName + propString)) {
                spaceDef.addIndexDef(indexDef);
                metaspace.alterSpace(spaceDef);
                    logger.log(Level.INFO, "Created composite index on %s fields=%s", cacheName, propString.substring(1));
                } else {
                    logger.log(Level.INFO, "Existing composite index on %s field=%s", cacheName, propString.substring(1));
                }
        	} else {
        		throw new RuntimeException("Index properties must be provided in the form of String, or String[]");
            }
        }
        catch (Exception e) {
            logger.log(Level.WARN, e, "Failed to create index on %s field=%s", cacheName, propString.substring(1));
        }
    }

	@Override
	public int evictCache(String cacheName, Object filter, boolean deleteFromPersistence) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                InferenceAgent inferenceAgent = (InferenceAgent) cs_om.getCacheAgent();
                String cacheFilter = (String) filter;

                Cluster cluster = session.getRuleServiceProvider().getCluster();
                DaoProvider daoProvider = cluster.getDaoProvider();
                              
                EntityDao entityDao = daoProvider.getEntityDao(cacheName);
                
                Filter sqlFilter = new SqlFilter(cacheFilter);
                Set<Long> keySet = entityDao.keySet(sqlFilter, -1);
                
                Set<Long> evictSet = performEvict(inferenceAgent, entityDao, keySet, deleteFromPersistence);
                if (deleteFromPersistence) {
                	performDelete(cluster, entityDao, evictSet);
                }
                
                int total = evictSet.size();
                
                // Do not call clear. It might clear it in the cache too!
                keySet = null;
                evictSet = null;
                
                return total;
            }
        }
        return -1;
	}
	
    protected Set<Long> performEvict(InferenceAgent inferenceAgent, EntityDao entityDao, Set<Long> keysForDeletion, boolean deleteFromPersistent) {
    	try {
	        // Get the ExtIds for those keys.
	        HashMap<Long, String> keyAndExtIdsForDeletion =
	                new HashMap<Long, String>(keysForDeletion.size());
	
	        Collection<Tuple> entityTuples =
	                inferenceAgent.getEntityTuplesById(keysForDeletion);
	
	        if (entityTuples != null) {
	            Iterator<Long> idsIter = keysForDeletion.iterator();
	            Iterator<Tuple> tuplesIter =
	                    entityTuples.iterator();
	
	            for (; idsIter.hasNext() && tuplesIter.hasNext();) {
	                Long id = idsIter.next();
	                Tuple tuple = tuplesIter.next();
	
	                if (tuple != null) {
	                    String extId = tuple.getExtId();
	                    keyAndExtIdsForDeletion.put(id, extId);
	                }
	                else {
	                    logger.log(Level.ERROR, "[%s] There is no record of the Entity with Id: %s",
	                    		entityDao.getClass().getSimpleName(), id);
	                }
	            }
	
	            // Let GC take the list.
	            entityTuples.clear();
	            entityTuples = null;
	        }
	
	        // Delete all the Ids and the ExtIds first.
	        ObjectTable otc = inferenceAgent.getCluster().getObjectTableCache();
	
	        Set<Long> theIds = keyAndExtIdsForDeletion.keySet();
	        Collection<String> theExtIds = keyAndExtIdsForDeletion.values();
	
	        // remove from objecttable cache + depending on the deleteFromPersistence flag remove from DB (object table + base table)
	        ((ObjectTableCache)otc).removeAll(theIds, theExtIds, deleteFromPersistent, entityDao.getEntityClass());
	
	        // Now, delete all the entries from cache
	        entityDao.removeAll(theIds);
	
	        return keyAndExtIdsForDeletion.keySet();
    	}
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    private void performDelete(Cluster cluster, EntityDao entityDao, Set<Long> keysForDeletion) {
    	try {
	        if (cluster.getClusterConfig().isHasBackingStore() && cluster.getClusterConfig().isCacheAside()) {
	            if (entityDao.getConfig().hasBackingStore()) {
	            	BackingStore backingStore = cluster.getCacheAsideStore();
	            	backingStore.removeEntities(entityDao.getEntityClass(), keysForDeletion);
	            }
	        }
    	}
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
}
