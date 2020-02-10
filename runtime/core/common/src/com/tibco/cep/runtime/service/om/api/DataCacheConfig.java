package com.tibco.cep.runtime.service.om.api;

import com.tibco.cep.util.annotation.Optional;

/*
 * Author: Ashwin Jayaprakash / Date: Sep 8, 2010 / Time: 4:45:26 PM
 */
public class DataCacheConfig {
    
    protected boolean limited;

    protected long limitedSize = 10000;

    // ActiveSpaces: 
    //      capacity = -1 : does-not try to load any object from persistence store, 
    //                      if not found in cache (not a concern for shared-nothing)
    //      capacity = >0 : try to load object from persistence store, if not found 
    //                      in cache
    // Note: We can take advantage of this behavior when cache is fully loaded
    protected long genericUsageUnlimitedSize = Long.MAX_VALUE;
    protected long shareNothingUnlimitedSize = new Long(-1);
    
    protected boolean distributedOrReplicated;

    protected int backupCount = 1;

    protected boolean versionCheckRequired;

    protected boolean cacheAside;

    protected boolean evictOnUpdateRequired;
    
    @Optional
    protected IndexConfig[] indexConfigs;
    
    @Optional
    protected EncryptionConfig encryptionConfig;

    public DataCacheConfig() {
    }

    public boolean isLimited() {
        return limited;
    }

    public void setLimited(boolean limited) {
        this.limited = limited;
    }

    public long getLimitedSize() {
        return limitedSize;
    }

    public void setLimitedSize(long limitedSize) {
        this.limitedSize = limitedSize;
    }

    public long getUnlimitedSize(boolean usesShareNothing) {
        if (usesShareNothing) {
            return this.shareNothingUnlimitedSize;
        } else {
            return this.genericUsageUnlimitedSize;
        }
    }

    public void setUnlimitedSize(long unlimitedSize, boolean sharedNothing) {
        if (sharedNothing) {
            this.shareNothingUnlimitedSize = unlimitedSize;
        } else {
            this.genericUsageUnlimitedSize = unlimitedSize;
        }
    }

    public boolean isDistributedOrReplicated() {
        return distributedOrReplicated;
    }

    public void setDistributedOrReplicated(boolean distributedOrReplicated) {
        this.distributedOrReplicated = distributedOrReplicated;
    }

    public int getBackupCount() {
        return backupCount;
    }

    public void setBackupCount(int backupCount) {
        this.backupCount = backupCount;
    }

    public boolean isVersionCheckRequired() {
        return versionCheckRequired;
    }

    public void setVersionCheckRequired(boolean versionCheckRequired) {
        this.versionCheckRequired = versionCheckRequired;
    }

    public boolean isCacheAside() {
        return cacheAside;
    }

    public void setCacheAside(boolean cacheAside) {
        this.cacheAside = cacheAside;
    }

    public boolean isEvictOnUpdateRequired() {
        return evictOnUpdateRequired;
    }

    public void setEvictOnUpdateRequired(boolean evictOnUpdateRequired) {
        this.evictOnUpdateRequired = evictOnUpdateRequired;
    }

    @Optional
    public IndexConfig[] getIndexConfigs() {
        return indexConfigs;
    }

    @Optional
    public void setIndexConfigs(IndexConfig[] indexConfigs) {
        this.indexConfigs = indexConfigs;
    }

    @Optional
    public EncryptionConfig getEncryptionConfig() {
        return encryptionConfig;
    }

    @Optional
    public void setEncryptionConfig(EncryptionConfig encryptionConfig) {
        this.encryptionConfig = encryptionConfig;
    }
}
