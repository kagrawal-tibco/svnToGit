/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification;


import com.tibco.cep.runtime.session.impl.HotDeployListener;

/**
 * User: apuneet Date: Nov 16, 2007 Time: 11:33:53 AM
 */
public interface ClusterEntityListener extends HotDeployListener {
    /**
     * @return <code>true</code> if {@link #onEntity(Object)} must be invoked by an asynchronous
     *         job. This should be used if {@link #onEntity(Object)} is going to take a while to
     *         complete.
     */
    boolean requireAsyncInvocation();

    /**
     * @param obj
     * @see #requireAsyncInvocation()
     */
    void onEntity(Object obj);

//    public Filter getEntityFilter();

    String getListenerName();
	
	@Override
    void entitiesAdded();
}
