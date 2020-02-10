/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

public class EntityDaoConfig extends BasicDaoConfig {
    protected String uri;

    protected String ruleFunctionUri;

    protected boolean constant;

    protected boolean deployed = true;

    protected boolean subscribed;

    protected CacheMode cacheMode;

    protected long conceptTTL;

    private Cluster cluster;

    public EntityDaoConfig() {
        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        cluster = rsp.getCluster();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRuleFunctionUri() {
        return ruleFunctionUri;
    }

    public void setRuleFunctionUri(String ruleFunctionUri) {
        this.ruleFunctionUri = ruleFunctionUri;
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
    }

    public long getConceptTTL() {
        return conceptTTL;
    }

    public void setConceptTTL(long conceptTTL) {
        this.conceptTTL = conceptTTL;
    }

    //-------------------

    public boolean hasBackingStore() {
        return (databaseConfig != null)
                && (this.cacheMode != EntityDaoConfig.CacheMode.Memory);
    }

    public boolean hasBackingStore(boolean checkTraditionalStore) {

        boolean traditionalStore = false;
        if (cluster != null) {
            GenericBackingStore genericBackingStore = cluster.getBackingStore();
            if ((genericBackingStore != null) && (genericBackingStore instanceof BackingStore)) {
                traditionalStore = true;
            }
        }

        if (!checkTraditionalStore) {
            return (databaseConfig != null)
                && (this.cacheMode != EntityDaoConfig.CacheMode.Memory);
        } else {
            boolean hasStore = (databaseConfig != null) && (this.cacheMode != EntityDaoConfig.CacheMode.Memory);
            return (hasStore && traditionalStore);
        }
    }


    public boolean isRecoverOnStartup() {
        if (databaseConfig != null) {
            return databaseConfig.isRecoverOnStartup();
        }
        return false;
    }

    public boolean isLoadOnStartup() {
        if (databaseConfig != null) {
            return databaseConfig.isLoadOnStartup();
        }
        return false;
    }

    public boolean requiresVersionCheck() {
        if (dataCacheConfig != null) {
            return dataCacheConfig.isVersionCheckRequired();
        }
        return false;
    }

    public boolean isEvictOnUpdate() {
        if (dataCacheConfig != null) {
            return dataCacheConfig.isEvictOnUpdateRequired();
        }
        return false;
    }

    public long getLoadFetchSize() {
        if (databaseConfig != null) {
            return databaseConfig.getLoadFetchSize();
        }
        return 0L;
    }

    public boolean isCacheEnabled() {
        CacheMode cMode = getCacheMode();
        return cMode!= null && cMode != CacheMode.Memory;
    }

    public boolean isCacheLimited() {
        if (dataCacheConfig != null) {
            return dataCacheConfig.isLimited();
        }
        return false;
    }

    public boolean isLoaded() {
        return true;
    }

    //-------------------

    public static enum CacheMode {
        Memory,
        CacheAndMemory,
        Cache;

        public static CacheMode parseString(String mode) {
            for (CacheMode cacheMode : values()) {
                if (mode.equalsIgnoreCase(cacheMode.name())) {
                    return cacheMode;
                }
            }

            throw new IllegalArgumentException("Unrecognized " + CacheMode.class.getSimpleName() + " [" + mode + "]");
        }

        public static boolean isMemoryMode(String mode) {
            return (mode.equalsIgnoreCase(Memory.name()));
        }

        public static boolean isCacheMode(String mode) {
            return !isMemoryMode(mode);
        }
    }
}
