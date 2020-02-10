package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.cache.CacheStore;
import com.tibco.be.migration.expimp.providers.cache.EntityStore;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 9:30:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVStatemachineStore extends CSVConceptStore {

    public CSVStatemachineStore(CSVStore csvStore, ExpImpContext context, ExpImpStats stats, String storeName) {
        super(csvStore, context,stats, storeName);
    }

    public void load(EntityStore entityStore, String inputVersion, String outputVersion) throws Exception  {

    }

    public void loadKeys(CacheStore cacheStore, EntityStore entityStore) throws Exception {

    }
}
