package com.tibco.rta.model.stats;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 16/9/14
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class L1CacheStats {

    private long cacheCapacity;

    private int currentCacheSize;

    private long cacheHits;

    private long cacheMisses;

    private double hitPercentage;

    private double missPercentage;

    public L1CacheStats(long cacheCapacity,
                        int currentCacheSize,
                        long cacheHits,
                        long cacheMisses,
                        double hitPercentage,
                        double missPercentage) {
        this.cacheCapacity = cacheCapacity;
        this.currentCacheSize = currentCacheSize;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
        this.hitPercentage = hitPercentage;
        this.missPercentage = missPercentage;
    }

    public long getCacheCapacity() {
        return cacheCapacity;
    }


    public int getCurrentCacheSize() {
        return currentCacheSize;
    }


    public long getCacheHits() {
        return cacheHits;
    }


    public long getCacheMisses() {
        return cacheMisses;
    }


    public double getHitPercentage() {
        return hitPercentage;
    }


    public double getMissPercentage() {
        return missPercentage;
    }

}
