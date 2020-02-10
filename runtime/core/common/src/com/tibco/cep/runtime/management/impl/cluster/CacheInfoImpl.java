package com.tibco.cep.runtime.management.impl.cluster;

import java.io.Serializable;

import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2009 Time: 5:27:48 PM
*/
public class CacheInfoImpl implements CacheInfo, Serializable {
    protected FQName name;

    protected long size;

    protected long numberOfGets;

    protected long numberOfPuts;

    protected double avgGetTimeMillis;

    protected double avgPutTimeMillis;

    protected double hitRatio;

    protected long maxSize;

    protected long minSize;

    protected long expiryDelayMillis;

    public FQName getName() {
        return name;
    }

    public void setName(FQName name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getNumberOfGets() {
        return numberOfGets;
    }

    public void setNumberOfGets(long numberOfGets) {
        this.numberOfGets = numberOfGets;
    }

    public long getNumberOfPuts() {
        return numberOfPuts;
    }

    public void setNumberOfPuts(long numberOfPuts) {
        this.numberOfPuts = numberOfPuts;
    }

    public double getAvgGetTimeMillis() {
        return avgGetTimeMillis;
    }

    public void setAvgGetTimeMillis(double avgGetTimeMillis) {
        this.avgGetTimeMillis = avgGetTimeMillis;
    }

    public double getAvgPutTimeMillis() {
        return avgPutTimeMillis;
    }

    public void setAvgPutTimeMillis(double avgPutTimeMillis) {
        this.avgPutTimeMillis = avgPutTimeMillis;
    }

    public double getHitRatio() {
        return hitRatio;
    }

    public void setHitRatio(double hitRatio) {
        this.hitRatio = hitRatio;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getMinSize() {
        return minSize;
    }

    public void setMinSize(long minSize) {
        this.minSize = minSize;
    }

    public long getExpiryDelayMillis() {
        return expiryDelayMillis;
    }

    public void setExpiryDelayMillis(long expiryDelayMillis) {
        this.expiryDelayMillis = expiryDelayMillis;
    }
}
