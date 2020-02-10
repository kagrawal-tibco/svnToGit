package com.tibco.cep.persister.bdb.jmx;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 8/28/12
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PersisterMBean {

    String getName();

    long getTotalGets();

    long getTotalTakes();

    long getTotalPuts();

    long getTotalRecovered();

    void clearStats();

    long getTupleRecoveryThroughputInMillis();

    long getTupleRecoveryBatchSize();
}
