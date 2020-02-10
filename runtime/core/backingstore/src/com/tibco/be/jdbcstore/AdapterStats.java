package com.tibco.be.jdbcstore;

import com.tibco.be.jdbcstore.impl.DBAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2008
 * Time: 8:57:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdapterStats implements AdapterStatsMBean {

    long numStores;
    long timeStores;
    long numLoads;
    long timeLoads;
    long numErase;
    long timeErase;
    DBAdapter adapter;
    int index;
    private String cacheName;

    public AdapterStats(int i, DBAdapter ad, String cacheName) {
        this.index = i ;
        this.adapter = ad;
        this.cacheName = cacheName;
    }

    public boolean isActive() {
        return adapter.isActive();
    }

    public String getCacheName() {
        return cacheName;
    }

    public void incrNumErase() {
        this.numErase++;
    }

    public void incrNumLoads() {
        this.numLoads++;
    }

    public void incrNumStores() {
        this.numStores++;
    }

    public long getTimeErase() {
        return timeErase;
    }

    public void incrTimeErase(long timeErase) {
        this.timeErase += timeErase;
    }

    public long getTimeLoads() {
        return timeLoads;
    }

    public void incrTimeLoads(long timeLoads) {
        this.timeLoads += timeLoads;
    }

    public long getTimeStores() {
        return timeStores;
    }

    public void incrTimeStores(long timeStores) {
        this.timeStores += timeStores;
    }

    public void incrNumErase(int i) {
        this.numErase += i;
    }

    public long getEraseCount() {
        return numErase;
    }

    public double getEraseAvgTime() {
        if (numErase > 0)
            return this.timeErase / (numErase*1.0);
        else
            return 0.0;
    }

    public long getLoadCount() {
        return numLoads;
    }

    public double getLoadAvgTime() {
        if (numLoads > 0)
            return this.timeLoads / (numLoads*1.0);
        else
            return 0.0;
    }

    public long getStoreCount() {
        return numStores;
    }

    public double getStoreAvgTime() {
        if (numStores > 0)
            return this.timeStores / (numStores*1.0);
        else
            return 0.0;
    }

    public void incrNumLoads(int i) {
        this.numLoads += i;
    }
}
