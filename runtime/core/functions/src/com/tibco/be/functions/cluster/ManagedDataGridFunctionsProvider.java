package com.tibco.be.functions.cluster;

import java.util.Set;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.IndexDef;
import com.tibco.as.space.IndexDef.IndexType;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.query.SqlFilter;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: 11/21/11 / Time: 3:06 PM
*/
public class ManagedDataGridFunctionsProvider extends DataGridFunctionsProvider {

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(ManagedDataGridFunctionsProvider.class);

    final boolean lockFree;

    /**
     * Lock free = {@code true}.
     */
    public ManagedDataGridFunctionsProvider() {
        this(true);
    }

    public ManagedDataGridFunctionsProvider(boolean lockFree) {
        this.lockFree = lockFree;
    }

    @Override
    public boolean lock(String key, long timeout, boolean localOnly) {
        return lockFree || lockImpl(key, timeout, localOnly);
    }

    @Override
    public void unLock(String key, boolean localOnly) {
        if (lockFree) {
            return;
        }

        unLockImpl(key, localOnly);
    }

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
		if (deleteFromPersistence == false) {
			throw new RuntimeException("Evicting cache without deleting from persistence is not supported for shared-nothing");
		}
		
        boolean useClear = Boolean.parseBoolean(
                System.getProperty(SystemProperty.PROP_TUPLE_USECLEAR.getPropertyName(), Boolean.TRUE.toString()));

        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            String cacheFilter = (String) filter;

            Cluster cluster = session.getRuleServiceProvider().getCluster();
            DaoProvider daoProvider = cluster.getDaoProvider();
            
            EntityDao entityDao = daoProvider.getEntityDao(cacheName);
            
            if (entityDao != null) {
            	if (useClear) {
            		return performClear(entityDao, cacheFilter);
            	}
            	else {
            		Filter sqlFilter = new SqlFilter(cacheFilter);
            		Set<Long> keySet = entityDao.keySet(sqlFilter, -1);
	            
            		return performEvict(entityDao, keySet);
            	}
            }
        }
        return -1;
	}
	
    protected int performEvict(EntityDao entityDao, Set<Long> keysForDeletion) {

        int total = keysForDeletion.size();

        // Now, delete all the entries.
        entityDao.removeAll(keysForDeletion);

        return total;
    }

    protected int performClear(EntityDao entityDao, String filter) {
        // Now, delete all the entries.
    	return entityDao.clear(filter);
    }
}
