/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Element;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 6, 2008
 * Time: 5:10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoteCacheConceptExHandle extends CacheConceptExHandle {
    String clusterName;
    
    public RemoteCacheConceptExHandle(String clusterName, WorkingMemoryImpl wm, Element _concept) {
        super(wm,_concept);
        this.clusterName = clusterName;
    }

    public String getClusterName() {
        return clusterName;
    }
}
