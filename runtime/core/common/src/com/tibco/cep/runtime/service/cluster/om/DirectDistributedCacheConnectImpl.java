/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.om.DirectDistributedCacheConnect;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash Date: Oct 29, 2008 Time: 11:41:28 AM
*/

public class DirectDistributedCacheConnectImpl implements DirectDistributedCacheConnect {
    protected MetadataCache metadataCache;

    protected ObjectTable objectTable;

    public void init(MetadataCache metadataCache, ObjectTable objectTable) {
        this.metadataCache = metadataCache;
        this.objectTable = objectTable;
    }

    /**
     * @param extId
     * @return
     * @throws RuntimeException
     */
    public Entity loadEntity(String extId) {
        Entity entity = null;
        int typeId = -1;
        long id = -1;

        try {
            Tuple entityTuple = objectTable.getByExtId(extId);
            if (entityTuple != null) {
                typeId = entityTuple.getTypeId();

                EntityDao entityProvider = metadataCache.getEntityDao(typeId);
                if (entityProvider != null) {
                    id = entityTuple.getId();
                    entity = (Entity) entityProvider.get(id);
                }
            }
        }
        catch (Exception e) {
            String msg = "Error occurred while retrieving entity [extId: " + extId + ", id: " + id +
                    ", typeId: " + typeId + "]";

            throw new RuntimeException(msg, e);
        }

        return entity;
    }

    /**
     * @param id
     * @return
     * @throws RuntimeException
     */
    public Entity loadEntity(long id) {
        Entity entity = null;
        int typeId = -1;

        try {
            Tuple entityTuple = objectTable.getById(id);
            if (entityTuple != null) {
                typeId = entityTuple.getTypeId();

                EntityDao entityProvider = metadataCache.getEntityDao(typeId);
                if (entityProvider != null) {
                    id = entityTuple.getId();
                    entity = (Entity) entityProvider.get(id);
                }
            }
        }
        catch (Exception e) {
            String msg = "Error occurred while retrieving entity [id: " + id + ", typeId: " +
                    typeId + "]";

            throw new RuntimeException(msg, e);
        }

        return entity;
    }
}
