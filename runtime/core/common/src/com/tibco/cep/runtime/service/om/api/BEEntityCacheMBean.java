/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.api;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: May 12, 2008 Time: 11:07:28 PM To change this template use File |
 * Settings | File Templates.
 */
public interface BEEntityCacheMBean {

    public String getCacheName();

    public String getClassName();

    public int getTypeId();

    public long getPutCount();

    public double getPutAvgTime();

    public long getGetCount();

    public double getGetAvgTime();

    public long getRemoveCount();

    public double getRemoveAvgTime();

    public long getNumHandlesInStore();

    public int getCacheSize();

}
