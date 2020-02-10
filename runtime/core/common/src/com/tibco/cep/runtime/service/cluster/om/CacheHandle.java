/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.service.om.ObjectStore;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 28, 2006
 * Time: 10:13:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheHandle extends ObjectStore.ObjectStoreHandle {

    public void removeRef();

    void touch(WorkingMemory wm);
    long getId();
}

