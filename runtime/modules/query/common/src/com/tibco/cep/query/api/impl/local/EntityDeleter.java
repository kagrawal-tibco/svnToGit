package com.tibco.cep.query.api.impl.local;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.query.SelectAllFilter;
import com.tibco.cep.runtime.service.om.api.query.SelectAlwaysFilter;

/*
* Author: Karthikeyan Subramanian / Date: Jun 30, 2010 / Time: 1:15:05 PM
*/
public class EntityDeleter {

    protected ObjectTable objectTable;

    public EntityDeleter(ObjectTable objectTable) {
        this.objectTable = objectTable;
    }

    public Map<Long, String> deleteEntities(EntityDao cache, Filter... filters) throws Exception {
        Filter filter = null;
        if (filters.length == 1) {
            filter = filters[0];
        } else if (filters.length > 1) {
            filter = new SelectAllFilter(filters);
        } else {
            filter = new SelectAlwaysFilter();
        }
        Set<Long> keysForDeletion = cache.keySet(filter, Integer.MAX_VALUE);
        //-------------

        return performDelete(cache, keysForDeletion);
    }

    private Map<Long, String> performDelete(EntityDao cache, Set<Long> keysForDeletion)
            throws Exception {
        Map<Long, String> result = new HashMap<Long, String>();
        if (keysForDeletion.size() == 0) {
            return result;
        }

        boolean sharedNothing = ManagedObjectManager.isOn();
        //Check if the Shared Nothing Feature is turned on for AS
        if (!sharedNothing) {
            // Remove Id and Ext ID from object table.
            Collection<ObjectTable.Tuple> tuples = objectTable.getByIds(keysForDeletion);
            Set<String> extIds = new HashSet<String>(tuples.size());
            for (ObjectTable.Tuple tuple : tuples) {
                extIds.add(tuple.getExtId());
                result.put(tuple.getId(), tuple.getExtId());
            }
            objectTable.removeAll(keysForDeletion, extIds);
        } else {
            //If Shared nothing is ON, the resultset contains only ids and will not contain the extIds
            for (Long l : keysForDeletion) {
                result.put(l, null);
            }
        }

        // Remove the object from the cache.
        cache.removeAll(keysForDeletion);
        return result;
    }
}
