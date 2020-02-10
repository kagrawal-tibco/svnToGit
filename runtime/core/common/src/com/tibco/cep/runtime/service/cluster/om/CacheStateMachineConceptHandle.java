/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Dec 9, 2009
 * Time: 6:27:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheStateMachineConceptHandle extends CacheConceptHandle
{
    public CacheStateMachineConceptHandle(WorkingMemoryImpl wm, Element _concept, boolean forceSet) {
        super(wm, _concept, forceSet);
    }

    public EntitySharingLevel getSharingLevel() {
        Handle ownerHandle = ((StateMachineConceptImpl)getObject()).getOwnerHandle();
        if(ownerHandle != null) return ownerHandle.getSharingLevel();
        else return super.getSharingLevel();
    }
}