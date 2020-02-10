package com.tibco.cep.runtime.service.om.api;

import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;

/*
* Author: Ashwin Jayaprakash / Date: Sep 22, 2010 / Time: 10:56:06 AM
*/

public enum CommonCacheType implements CacheType {
    DISTRIBUTED_CACHE_UNLIMITED_WITHBACKINGSTORE("dist-unlimited-bs", false, true, false),
    DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE("dist-unlimited-nobs", false, false, false),
    DISTRIBUTED_CACHE_UNLIMITED_READONLY_WITHBACKINGSTORE("dist-unlimited-bs-readOnly", false, true, true),

    DISTRIBUTED_CACHE_LIMITED_WITHBACKINGSTORE("dist-limited-bs", true, true, false),
    DISTRIBUTED_CACHE_LIMITED_NOBACKINGSTORE("dist-limited-nobs", true, false, false),
    DISTRIBUTED_CACHE_LIMITED_READONLY_WITHBACKINGSTORE("dist-limited-bs-readOnly", true, true, true);

    protected String alias;

    protected boolean cacheLimited;

    protected boolean backingStore;

    protected boolean cacheAside;

    CommonCacheType(String alias, boolean cacheLimited, boolean backingStore, boolean cacheAside) {
        this.alias = alias;
        this.cacheLimited = cacheLimited;
        this.backingStore = backingStore;
        this.cacheAside = cacheAside;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public boolean isCacheLimited() {
        return cacheLimited;
    }

    @Override
    public boolean hasBackingStore() {
        return backingStore;
    }

    @Override
    public boolean isCacheAside() {
        return cacheAside;
    }

    @Override
    public boolean isReplicated() {
        return false;
    }

//    public static CommonCacheType getControlCacheType(ClusterConfiguration cc) {
//        // Regardless of the ClusterConfiguration, Control DAO's should be
//        // "dist-unlimited-nobs"
//        return DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE;
//    }

//    public static CommonCacheType getCommonCacheType(ClusterConfiguration cc) {
//        if (cc.isCacheLimited()) {
//            if (cc.isHasBackingStore()) {
//                if (cc.isCacheAside()) {
//                    return DISTRIBUTED_CACHE_LIMITED_READONLY_WITHBACKINGSTORE;
//                }
//                return DISTRIBUTED_CACHE_LIMITED_WITHBACKINGSTORE;
//            }
//            else {
//                return DISTRIBUTED_CACHE_LIMITED_NOBACKINGSTORE;
//            }
//        }
//
//        if (cc.isHasBackingStore()) {
//            if (cc.isCacheAside()) {
//                return DISTRIBUTED_CACHE_UNLIMITED_READONLY_WITHBACKINGSTORE;
//            }
//            return DISTRIBUTED_CACHE_UNLIMITED_WITHBACKINGSTORE;
//        }
//        else {
//            return DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE;
//        }
//    }
    
    public static CommonCacheType getCommonCacheType(ClusterConfiguration cc, EntityDaoConfig entityDaoConfig) {
        if (entityDaoConfig.isCacheLimited()) {
            if (entityDaoConfig.hasBackingStore()) {
                if (cc.isCacheAside()) {
                    return DISTRIBUTED_CACHE_LIMITED_READONLY_WITHBACKINGSTORE;
                }
                return DISTRIBUTED_CACHE_LIMITED_WITHBACKINGSTORE;
            }
            return DISTRIBUTED_CACHE_LIMITED_NOBACKINGSTORE;
        }
        if (entityDaoConfig.hasBackingStore()) {
        	if (cc.isCacheAside()) {
        		return DISTRIBUTED_CACHE_UNLIMITED_READONLY_WITHBACKINGSTORE;
        	}
        	return DISTRIBUTED_CACHE_UNLIMITED_WITHBACKINGSTORE;
        }
        //default?
        return DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE;
    }
}
