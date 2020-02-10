package com.tibco.be.oracle;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2008
 * Time: 9:07:47 PM
 * To change this template use File | Settings | File Templates.
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
