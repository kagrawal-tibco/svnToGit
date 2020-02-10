/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: May 13, 2009 Time: 5:14:10 PM
*/
public interface ServiceInfo {
    String getName();

    Collection<ServiceMemberInfo> getAllMemberInfo();

    ServiceMemberInfo getLocalMemberInfo();
}
