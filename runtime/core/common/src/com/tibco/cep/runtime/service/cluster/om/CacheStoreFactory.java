/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 30, 2006
 * Time: 9:20:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheStoreFactory {
    /**
     *
     * @param ruleSession
     * @return
     */
    //CacheStore getCacheStore(RuleSession ruleSession, long cacheId, String cacheName);

    /**
     * 
     * @param ruleSession
     * @return
     */
    CacheStore.CacheIdGenerator getCacheIdGenerator (RuleSession ruleSession);
}
