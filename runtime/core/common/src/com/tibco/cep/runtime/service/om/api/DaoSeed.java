package com.tibco.cep.runtime.service.om.api;


import java.io.Serializable;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Nov 25, 2010 / Time: 11:20:33 PM
*/
public class DaoSeed implements Serializable {
    protected CacheType cacheType;

    protected long limitedCacheCapacity = 10000;

    protected String name;

    @Optional
    protected Object[] additionalProps;

    public DaoSeed() {
    }

    public DaoSeed(CacheType cacheType, String name, long limitedCacheCapacity, Object... additionalProps) {
        this.cacheType = cacheType;
        this.name = name;
        this.limitedCacheCapacity = limitedCacheCapacity;
        this.additionalProps = (additionalProps == null || additionalProps.length == 0) ? null : additionalProps;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public String getName() {
        return name;
    }
    public long getLimitedCacheCapacity() {
		return limitedCacheCapacity;
	}

	public void setLimitedCacheCapacity(long limitedCacheCapacity) {
		this.limitedCacheCapacity = limitedCacheCapacity;
	}

    public Object[] getAdditionalProps() {
        return additionalProps;
    }
}
