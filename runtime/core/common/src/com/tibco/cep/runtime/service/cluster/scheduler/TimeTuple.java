/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.service.cluster.scheduler;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 6, 2008
 * Time: 2:46:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TimeTuple {
    public String getKey();
    public long getExpiryTime();
}
