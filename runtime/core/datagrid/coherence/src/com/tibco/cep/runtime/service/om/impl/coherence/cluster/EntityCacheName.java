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

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 30, 2008
 * Time: 2:20:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityCacheName {
    public final static String DISTRIBUTED_CACHE_UNLIMITED_WITHBACKINGSTORE = "dist-unlimited-bs";
    public final static String DISTRIBUTED_CACHE_UNLIMITED_READONLY_WITHBACKINGSTORE = "dist-unlimited-bs-readOnly";
    public final static String DISTRIBUTED_CACHE_LIMITED_WITHBACKINGSTORE = "dist-limited-bs";
    public final static String DISTRIBUTED_CACHE_LIMITED_READONLY_WITHBACKINGSTORE = "dist-limited-bs-readOnly";
    public final static String DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE = "dist-unlimited-nobs";
    public final static String DISTRIBUTED_CACHE_LIMITED_NOBACKINGSTORE = "dist-limited-nobs";
    public final static String DISTRIBUTED_CACHE_OBJECTTABLE_NOBACKINGSTORE = "objectTableCache-nobs";
    public final static String DISTRIBUTED_CACHE_OBJECTTABLE_WITHBACKINGSTORE = "objectTableCache-bs";
    public final static String DISTRIBUTED_CACHE_OBJECTTABLE_READONLY_WITHBACKINGSTORE = "objectTableCache-bs-readOnly";
    public final static String DISTRIBUTED_CACHE_DELETEDENTITIES = "deletedEntities";
    public final static String DISTRIBUTED_CACHE_LOCKMANAGER = "lockManager";
    public final static String DISTRIBUTED_CACHE_AGENTTXNLOG = "agentTxnLog";
    //never used, scheduler always acts like cache-aside
    //public final static String DISTRIBUTED_CACHE_SCHEDULER_WITHBACKINGSTORE = "scheduler-bs";
    public final static String DISTRIBUTED_CACHE_SCHEDULER_READONLY_WITHBACKINGSTORE = "scheduler-bs-readOnly";
    public final static String DISTRIBUTED_CACHE_SCHEDULER_NOBACKINGSTORE = "scheduler-nobs";
    public final static String DISTRIBUTED_CACHE_SEQUENCE_WITHBACKINGSTORE = "sequence-bs";
    public static final String DISTRIBUTED_CACHE_SEQUENCE_NOBACKINGSTORE = "sequence-nobs";
    public final static String DISTRIBUTED_CACHE_LIMITED_NOOBJECTTABLE = "dist-limited-no-ot";

    public final static String REPLICATED_CACHE_UNLIMITED = "repl-unlimited";
    public final static String REPLICATED_CACHE_LIMITED = "repl-limited";

    // NOTE: Keep the static array in sync when adding new cache types
    private static String[] cacheTypes = new String[]{
            DISTRIBUTED_CACHE_UNLIMITED_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_UNLIMITED_READONLY_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_LIMITED_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_LIMITED_READONLY_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE,
            DISTRIBUTED_CACHE_LIMITED_NOBACKINGSTORE,
            DISTRIBUTED_CACHE_OBJECTTABLE_NOBACKINGSTORE,
            DISTRIBUTED_CACHE_OBJECTTABLE_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_OBJECTTABLE_READONLY_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_DELETEDENTITIES,
            DISTRIBUTED_CACHE_LOCKMANAGER,
            DISTRIBUTED_CACHE_AGENTTXNLOG,
            //never used, scheduler always acts like cache-aside
            //DISTRIBUTED_CACHE_SCHEDULER_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_SCHEDULER_READONLY_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_SCHEDULER_NOBACKINGSTORE,
            REPLICATED_CACHE_UNLIMITED,
            REPLICATED_CACHE_LIMITED,
            DISTRIBUTED_CACHE_SEQUENCE_WITHBACKINGSTORE,
            DISTRIBUTED_CACHE_SEQUENCE_NOBACKINGSTORE
    };

    String type;
    String clusterName;
    String agentName;
    String cacheName;

    EntityCacheName() {
    }

    public static String[] getCacheTypes() {
        return cacheTypes;
    }

    public String toCacheName() {
        return type + "$" + clusterName + "$" + agentName + "$" + cacheName;
    }

    public String toRemotePattern() {
        return type + "$" + clusterName + "*";
    }

    public String toString() {
        return "CACHE-NAME[" + toCacheName() + "]";
    }

    public String getCacheType() {
        return type;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public String getAgentName() {
        return agentName;
    }

    public final static EntityCacheName parseCacheName(String name) throws Exception {
        EntityCacheName cn = new EntityCacheName();
        int beginIndex = 0;
        int endIndex = name.indexOf('$');

        if (endIndex != -1) {
            cn.type = name.substring(beginIndex, endIndex);
        } else {
            throw new Exception("Invalid Cache Name " + name);
        }

        beginIndex = endIndex + 1;
        endIndex = name.indexOf('$', beginIndex);

        if (endIndex != -1) {
            cn.clusterName = name.substring(beginIndex, endIndex);
        } else {
            throw new Exception("Invalid Cache Name " + name);
        }

        beginIndex = endIndex + 1;
        endIndex = name.indexOf('$', beginIndex);

        if (endIndex != -1) {
            cn.agentName = name.substring(beginIndex, endIndex);
        } else {
            throw new Exception("Invalid Cache Name " + name);
        }

        beginIndex = endIndex + 1;
        //endIndex= name.indexOf('$', beginIndex);

        cn.cacheName = name.substring(beginIndex);
        return cn;
    }

    public final static EntityCacheName getCacheName(String type, String clusterName, String agentName, String cacheName) {
        EntityCacheName cn = new EntityCacheName();
        cn.type = type;
        cn.clusterName = clusterName;
        cn.agentName = agentName;
        cn.cacheName = cacheName;
        return cn;
    }


//    public static void main(String args[]) {
//
//        try {
//            EntityCacheName ecn = EntityCacheName.getCacheName(EntityCacheName.DISTRIBUTED_CACHE_LIMITED_NOBACKINGSTORE,
//                    "cluster", "", "be.gen.hello$hello");
//
//            String cacheName = ecn.toCacheName();
//            System.out.println("1: CACHE NAME=" + cacheName);
//
//            EntityCacheName ecn1 = EntityCacheName.parseCacheName(cacheName);
//            System.out.println("2: CACHE NAME=" + cacheName);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
