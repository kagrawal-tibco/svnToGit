/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.system;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 25, 2008
 * Time: 1:33:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntityIdMask {
    //        public static final long SITE_MASK = 0xF000000000000000L;
    public static int SITE_MASK_SHIFT = 60;
    public static long SITE_MASK_BITS = 0xFL;
    public static long SITE_MASK = (SITE_MASK_BITS << SITE_MASK_SHIFT) | 0L;

    public static long getEntityId(long id) {
        return (id & ~SITE_MASK);
    }

    public static long getMaskedId(long id) {
        return (id & SITE_MASK) >> SITE_MASK_SHIFT;
    }

    public static boolean isMasked(long id) {
        return (getMaskedId(id) > 0L);
    }

    public static long mask(long siteId, long id) {
        long ret = id;
        ret |= (siteId << SITE_MASK_SHIFT);
        return ret;
    }

    public boolean isLocalSite(short id) {
        return (getMaskedId(id) == getLocalSiteId());
    }

    abstract public short getLocalSiteId();
    
    public static void main(String args[]) {
    	
    	long x = 100;
    	long encoded = mask (0, x);
    	
    	long siteid = getMaskedId(encoded);
    	x = getEntityId(encoded);
    	System.out.println ("siteid=" + siteid + ", id=" + x);
    }
}
