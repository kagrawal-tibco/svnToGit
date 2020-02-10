package com.tibco.rta.service.om;

import com.tibco.rta.model.stats.L1CacheStats;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 16/9/14
 * Time: 4:55 PM
 *
 * Various L1 cache statistics provider
 */
public interface L1CacheProvider {

    public L1CacheStats getMetricCacheStats();

    public L1CacheStats getFactCacheStats();

    public L1CacheStats getRuleMetricCacheStats();
}
