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

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 21, 2006
 * Time: 7:35:07 PM
 * To change this template use File | Settings | File Templates.
 */
package com.tibco.cep.runtime.service.om.impl.coherence.tangosol;

import java.io.Serializable;

import com.tibco.cep.util.ResourceManager;

/**
 * Singleton using cache to back it. (Commented out code is from a single-JVM
 * traditional singleton pattern. New code supports stateless session EJB
 * singleton pattern.)
 */

public class CachedGlobalEntityIdAllocator implements Serializable {


    static public final String NAME = "MASTER_ENTITY_ID_ALLOCATOR";
    private long rangeCounter = 1;
    private long rangeSize = 10;
    static public long maxId = Long.MAX_VALUE;

    // ----- constructors ---------------------------------------------------

    /**
     * Allow multiple instances to be instantiated; they will all share the
     * same state.
     */
    public CachedGlobalEntityIdAllocator() {
    }

    /*
    ** Each global cached object will be put in a map using the name as a key.
    */

    public String getName() {
        return CachedGlobalEntityIdAllocator.NAME;
    }

    /*
    ** All ranges used must be >= RangeStart and <= RangeEnd
    */

    synchronized public EntityIdRange getRange() {
        long RangeStart = ((rangeCounter - 1) * rangeSize) + 1;
        long RangeEnd = rangeCounter * rangeSize;
        // 1   1        1000
        // 2   1001     2000
        // 3   2001     3000
        //System.out.println("Range Start:"+RangeStart+" RangeEnd:"+RangeEnd);
        if (RangeEnd >= maxId)
            throw new RuntimeException(ResourceManager.getInstance().formatMessage("entity.factory.id.max.exception", Long.toString(RangeEnd)));
        rangeCounter++;
        return new EntityIdRange(RangeStart, RangeEnd);

    }

    //public final CachedGlobalEntityIdAllocator INSTANCE = new CachedGlobalEntityIdAllocator();


}