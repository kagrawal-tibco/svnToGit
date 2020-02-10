/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 12/7/2010
 */

package com.tibco.cep.runtime.service.om;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;

/*
* Author: Ashwin Jayaprakash Date: Oct 29, 2008 Time: 11:30:51 AM
*/
public interface DirectDistributedCacheConnect {
    void init(MetadataCache metadataCache, ObjectTable objectTable);

    Entity loadEntity(String extId);

    Entity loadEntity(long id);
}
