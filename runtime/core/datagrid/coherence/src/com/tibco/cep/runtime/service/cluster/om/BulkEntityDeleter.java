/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.filter.AlwaysFilter;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Oct 15, 2008 Time: 1:56:28 PM
*/

public class BulkEntityDeleter {
    protected InferenceAgent inferenceAgent;

    protected Logger logger;

    public BulkEntityDeleter(InferenceAgent inferenceAgent) {
        this.inferenceAgent = inferenceAgent;
        this.logger = inferenceAgent.getLogger(BulkEntityDeleter.class);
    }

    /**
     * Delete all items from the cache whose name is provided and those that pass the filter.
     *
     * @param cacheName
     * @param cacheFilter Can be <code>null</code>.
     * @param contextCL
     * @return The number of items that were deleted (non transactional).
     * @throws Exception
     */
    public int deleteItems(String cacheName, Filter cacheFilter, ClassLoader contextCL)
            throws Exception {
        //First, get the candidate keys.

        NamedCache cache = CacheFactory.getCache(cacheName, contextCL);

        if (cacheFilter == null) {
            cacheFilter = new AlwaysFilter();
        }
        Set<Long> keysForDeletion = cache.keySet(cacheFilter);

        //-------------

        return performDelete(cache, keysForDeletion);
    }

    /**
     * Delete all items from the cache whose Ids have been provided.
     *
     * @param cacheName
     * @param ids
     * @param contextCL
     * @return The number of items that were deleted (non transactional).
     * @throws Exception
     */
    public int deleteItems(String cacheName, long[] ids, ClassLoader contextCL) throws Exception {
        NamedCache cache = CacheFactory.getCache(cacheName, contextCL);

        HashSet<Long> keysToDelete = new HashSet<Long>(ids.length);
        for (Long id : ids) {
            keysToDelete.add(id);
        }

        return performDelete(cache, keysToDelete);
    }

    protected int performDelete(NamedCache cache, Set<Long> keysForDeletion) throws Exception {
        //Get the Ext Ids for those keys.

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
                    logger.log(Level.ERROR, "[" + getClass().getSimpleName() +
                            "] There is no record of the Entity with Id: " + id);
                }
            }

            //Let GC take the list.
            entityTuples.clear();
            entityTuples = null;
        }
        else {
            logger.log(Level.ERROR, "[" + getClass().getSimpleName() +
                    "] There is no record of Entities with the following Ids: " +
                    keysForDeletion);
        }

        //Do not call clear. It might clear it in the cache too!
        keysForDeletion = null;

        //--------------

        //Delete all the Ids and the Ext Ids first.

        ObjectTable otc = inferenceAgent.getCluster().getObjectTableCache();

        Collection<Long> theIds = keyAndExtIdsForDeletion.keySet();
        Collection<String> theExtIds = keyAndExtIdsForDeletion.values();

        otc.removeAll(theIds, theExtIds);

        //Now, delete all the entries.
        cache.keySet().removeAll(theIds);

        int total = keyAndExtIdsForDeletion.size();

        //Let GC take this.
        keyAndExtIdsForDeletion.clear();

        return total;
    }
}
