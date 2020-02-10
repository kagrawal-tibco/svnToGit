package com.tibco.cep.persister.bdb.jmx;

import com.tibco.cep.persister.bdb.BDBPersistenceConstants;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 8/28/12
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ASPersisterMBeanImpl implements PersisterMBean {

    protected String name;
    protected AtomicLong tupleGets;
    protected AtomicLong tuplePuts;
    protected AtomicLong tupleTakes;
    protected AtomicLong tupleRecovered;
    protected AtomicLong numTuplesRecoveredThroughput;
    protected AtomicLong tupleRecoveryBatchSize;

    public ASPersisterMBeanImpl() {
        tupleGets = new AtomicLong();
        tuplePuts = new AtomicLong();
        tupleTakes = new AtomicLong();
        tupleRecovered = new AtomicLong();
        numTuplesRecoveredThroughput = new AtomicLong();
        tupleRecoveryBatchSize = new AtomicLong();
    }

    public abstract void register() throws Exception;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getTotalGets() {
        return tupleGets.get();
    }

    @Override
    public long getTotalTakes() {
        return tupleTakes.get();
    }

    @Override
    public long getTotalPuts() {
        return tuplePuts.get();
    }

    @Override
    public long getTotalRecovered() {
        return tupleRecovered.get();
    }

    @Override
    public void clearStats() {
        tupleGets.set(0);
        tuplePuts.set(0);
        tupleTakes.set(0);
        tupleRecovered.set(0);
    }

    public long incrementGets() {
        return tupleGets.incrementAndGet();
    }

    public long incrementPuts() {
        return tuplePuts.incrementAndGet();
    }

    public long incrementTake() {
        return tupleTakes.incrementAndGet();
    }

    public long incrementRecovered() {
        return tupleRecovered.incrementAndGet();
    }

    public long setTupleRecoveryThroughput(long recoveredPerMilliSecond) {
        return this.numTuplesRecoveredThroughput.getAndSet(recoveredPerMilliSecond);
    }

    public long getTupleRecoveryThroughputInMillis() {
        return this.numTuplesRecoveredThroughput.get();
    }

    public long setTupleRecoveryBatchSize(long batchSize) {
        return this.tupleRecoveryBatchSize.getAndSet(batchSize);
    }

    public long getTupleRecoveryBatchSize() {
        return this.tupleRecoveryBatchSize.get();
    }
}
