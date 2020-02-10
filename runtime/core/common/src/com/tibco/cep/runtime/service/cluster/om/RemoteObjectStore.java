/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 6, 2008
 * Time: 12:20:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteObjectStore {
    public static final String CLUSTER_SEPARATOR="$";
    

    Element getRemoteElement(String clusterName,String extId);

    Element getRemoteElement(String clusterName,long id);

    Entity getRemoteElementFromStore(String clusterName,String extId);

    Element getRemoteElementFromStore(String clusterName,long id);

    BaseHandle getRemoteElementHandle(String clusterName,String extId);
}
