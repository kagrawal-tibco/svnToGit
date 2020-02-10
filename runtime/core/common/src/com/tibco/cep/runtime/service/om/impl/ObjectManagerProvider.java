/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.impl;


import java.util.Properties;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.runtime.service.om.impl.mem.InMemoryObjectManager;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * User: nbansal Date: Jul 28, 2006 Time: 4:01:35 PM.
 *
 * Suresh - This class has been superceded by DistributedCacheBasedStoreProvider. It requires a CacheAgent.
 * to be passed with Constructor.
 */
public class ObjectManagerProvider {
    public static BaseObjectManager createOM(Properties omConfig, RuleSession session)
            throws Exception {
        return new InMemoryObjectManager(session.getName());
    }







}
