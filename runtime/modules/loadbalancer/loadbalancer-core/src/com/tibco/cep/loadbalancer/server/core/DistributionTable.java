package com.tibco.cep.loadbalancer.server.core;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.util.annotation.Copy;
import com.tibco.cep.util.annotation.Optional;
import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Mar 16, 2010 / Time: 3:34:25 PM
*/

@ThreadSafe
public interface DistributionTable {
    Id getId();

    void setResourceProvider(ResourceProvider resourceProvider);

    //---------------

    int getCurrentVersion();

    void putMember(Member member, DistributionKey[] keys);

    /**
     * @param key
     * @return null if the table is empty.
     */
    @Optional
    DistributionValue getValue(DistributionKey key);

    int getKeySize();

    @Copy
    Set<DistributionKey> getKeys();

    @Copy
    Collection<? extends DistributionValue> getValues();

    /**
     * Regardless of suspect status.
     *
     * @return
     */
    @Copy
    Collection<? extends Member> getKnownMembers();

    void removeMember(Member member, DistributionKey[] keys);

    //---------------

    /**
     * @param timeUnit
     * @param time
     * @return The number of keys ({@link #getKeySize()}) after receiving an internal notification that the keys have
     *         changed.
     * @throws InterruptedException
     */
    int waitUntilKeysChange(TimeUnit timeUnit, long time) throws InterruptedException;

    void markSuspect(Member member, DistributionKey[] keys, @Optional Exception e);

    @Copy
    Collection<? extends SuspectedMember> getSuspectedMembers();

    void unmarkSuspect(Member member);

    /**
     * GC dead values, compact tables etc.
     */
    void cleanup();

    //---------------

    void discard();

    //---------------
    
    void printReport();
    
    //---------------

    public static interface SuspectedMember {
        Member getMember();

        DistributionKey[] getKeys();

        /**
         * @return Time in millis.
         */
        long getSuspectedAt();

        @Optional
        Exception getReason();
    }
}
