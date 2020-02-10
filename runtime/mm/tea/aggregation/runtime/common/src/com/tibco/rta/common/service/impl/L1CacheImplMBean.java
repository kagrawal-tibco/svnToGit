package com.tibco.rta.common.service.impl;

public interface L1CacheImplMBean {
	/**
     * Return the size of queue at the given instant.
     *
     * @return the current size
     */
    public int getCurrentSize();
    
    
    /**
     * Return the total capacity of L1 cache
     * 
     * @return capacity
     */
    public long getCapacity();

    /**
     * 
     * Return cache hits till now
     * 
     * @return  cache hit
     */
    public long getHits();
    
    /**
     * 
     * Return cache misses till now
     * 
     * @return  cache miss
     */
    public long getMisses();
    
    /**
     * 
     *  Return % cache hit till now
     * 
     * @return
     */
    public double getHitPercentage();
    
    /**
     * 
     *  Return % cache miss till now
     * 
     * @return
     */
    public double getMissPercentage();
}
