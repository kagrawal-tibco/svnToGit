/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification;

/*
* Author: Ashwin Jayaprakash Date: Nov 11, 2008 Time: 8:14:20 PM
*/
public interface CacheChangeEvent {
    CacheChangeType getChangeType();

    long getEntityId();

    String getEntityExtId();

    Class getEntityClass();

    int getEntityTypeId();

    int getEntityChangeVersion();

    int[] getEntityDirtyBits();
}
