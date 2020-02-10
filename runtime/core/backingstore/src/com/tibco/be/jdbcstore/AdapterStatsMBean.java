package com.tibco.be.jdbcstore;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2008
 * Time: 9:07:47 PM
 */
public interface AdapterStatsMBean {

    boolean isActive();

    long getEraseCount();

    double getEraseAvgTime();

    long getLoadCount();

    double getLoadAvgTime();

    long getStoreCount();

    double getStoreAvgTime();

    String getCacheName();
}
