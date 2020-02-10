/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 30, 2006
 * Time: 9:11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheStore {
    /**
     *
     * @return
     */
    boolean keysOnly();

    /**
     *
     * @return
     */
    Iterator keySet();

    /**
     *
     * @return
     */
    Iterator entrySet();

    Map loadAll(Collection keys);

    /**
     * 
     */
    void close();

    public interface CacheIdGenerator {
        public long nextCacheId() throws Exception;
    }
}
