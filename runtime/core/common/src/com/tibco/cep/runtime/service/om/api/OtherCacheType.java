package com.tibco.cep.runtime.service.om.api;

/*
* Author: Ashwin Jayaprakash / Date: Sep 22, 2010 / Time: 10:56:06 AM
*/

public enum OtherCacheType implements CacheType {
    //TODO: Suresh - Check the LIMITED/UNLIMITED values
    DISTRIBUTED_CACHE_OBJECTTABLE_NOBACKINGSTORE("objectTableCache-nobs", false, false, false),
    DISTRIBUTED_CACHE_OBJECTTABLE_WITHBACKINGSTORE("objectTableCache-bs", true, true, false),
    DISTRIBUTED_CACHE_OBJECTTABLE_READONLY_WITHBACKINGSTORE("objectTableCache-bs-readOnly", true, true, true),

    DISTRIBUTED_CACHE_LIMITED_NOOBJECTTABLE("dist-limited-no-ot", true, false, true),

    DISTRIBUTED_CACHE_DELETEDENTITIES("deletedEntities", false, false, false),
    DISTRIBUTED_CACHE_LOCKMANAGER("lockManager", false, false, false),
    //TODO: Bala - change back to dist once seeder/leech ER is complete
    DISTRIBUTED_CACHE_AGENTTXNLOG("agentTxnLog", true, false, false, true),

    DISTRIBUTED_CACHE_SCHEDULER_WITHBACKINGSTORE("scheduler-bs", true, true, false),
    DISTRIBUTED_CACHE_SCHEDULER_READONLY_WITHBACKINGSTORE("scheduler-bs-readOnly", true, true, true),
    DISTRIBUTED_CACHE_SCHEDULER_NOBACKINGSTORE("scheduler-nobs", false, false, false),

    REPLICATED_CACHE_UNLIMITED("repl-unlimited", false, false, false, true),
    REPLICATED_CACHE_LIMITED("repl-limited", true, false, true, true);

    protected String alias;

    protected boolean cacheLimited;

    protected boolean backingStore;

    protected boolean cacheAside;

    protected boolean replicated;

    OtherCacheType(String alias, boolean cacheLimited, boolean backingStore, boolean cacheAside) {
        this(alias, cacheLimited, backingStore, cacheAside, false);
    }

    OtherCacheType(String alias, boolean cacheLimited, boolean backingStore, boolean cacheAside, boolean replicated) {
        this.alias = alias;
        this.cacheLimited = cacheLimited;
        this.backingStore = backingStore;
        this.cacheAside = cacheAside;
        this.replicated = replicated;
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
        return replicated;
    }
}
