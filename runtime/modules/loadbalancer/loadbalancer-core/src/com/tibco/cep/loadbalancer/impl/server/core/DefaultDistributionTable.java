package com.tibco.cep.loadbalancer.impl.server.core;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.loadbalancer.server.core.DistributionTable;
import com.tibco.cep.loadbalancer.server.core.DistributionValue;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.util.annotation.Copy;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;
import com.tibco.cep.util.annotation.ReadOnly;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tibco.cep.util.Helper.$logger;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 3:35:08 PM
*/

@LogCategory("loadbalancer.core.server.table")
public class DefaultDistributionTable implements DistributionTable {
    protected Id id;

    protected ReentrantReadWriteLock readWriteLock;

    protected ReadLock readLock;

    protected WriteLock writeLock;

    protected Condition writeLockChangeCondition;

    protected NavigableMap<DistributionKey, DefaultDistributionValue> actual;

    protected HashMap<Id, DefaultSuspectedMember> suspectedMembers;

    protected HashMap<Id, Member> knownMembers;

    protected volatile int currentVersion;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public DefaultDistributionTable(Id id) {
        this.readWriteLock = new ReentrantReadWriteLock();
        this.readLock = this.readWriteLock.readLock();
        this.writeLock = this.readWriteLock.writeLock();
        this.writeLockChangeCondition = this.writeLock.newCondition();

        this.id = id;
        this.actual = new TreeMap<DistributionKey, DefaultDistributionValue>();
        this.suspectedMembers = new HashMap<Id, DefaultSuspectedMember>();
        this.knownMembers = new HashMap<Id, Member>();
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    //---------------

    @Override
    public int getCurrentVersion() {
        return currentVersion;
    }

    @Override
    public void putMember(Member member, DistributionKey[] keys) {
        writeLock.lock();
        try {
            DefaultDistributionValue value = new DefaultDistributionValue(member, resourceProvider);

            for (DistributionKey key : keys) {
                DefaultDistributionValue existingValue = actual.put(key, value);

                /*
                If node A and node B join the cluster at the same time but on node X, A reaches first but on node Y, B reaches
                first.

                If both A and B produce the same hash keys, then X's ring will be different from Y's ring in some places.

                We need a global ordering of nodes joining - globally synchronized order.
                */

                Member prevMember = (existingValue != null) ? existingValue.getCurrentOwner() : null;

                logger.log(Level.FINE,
                        String.format("Connecting new Member: [%s], Key: [%s], PrevMember: [%s]", member, key, prevMember));
            }

            knownMembers.put(member.getId(), member);

            currentVersion++;

            writeLockChangeCondition.signal();
        }
        finally {
            writeLock.unlock();
        }
    }

    @Copy
    @Override
    public Collection<Member> getKnownMembers() {
        readLock.lock();
        try {
            return new LinkedList<Member>(knownMembers.values());
        }
        finally {
            readLock.unlock();
        }
    }

    /**
     * @param key
     * @return The whole entry may be null. Otherwise the value will never be null but it may not have an owner ({@link
     *         DistributionValue#hasCurrentOwner()}).
     */
    @ReadOnly
    Entry<DistributionKey, DefaultDistributionValue> getNearestMappedEntry(DistributionKey key) {
        readLock.lock();
        try {
            Entry<DistributionKey, DefaultDistributionValue> previousOwner = actual.floorEntry(key);

            //Wrap around.
            if (previousOwner == null) {
                previousOwner = actual.lastEntry();
            }

            //todo Need a threadsafe snapshot here.
            return previousOwner;
        }
        finally {
            readLock.unlock();
        }
    }

    /*
     <pre>
               .----------------------------.
               ?                            ?
               L(Low)                       H(High)

       ?                          ?                     ?
       A                          B                     C

               +?----------------X<--------+
               |                            ?
               ?                            |
               +--------------------------?+
     </pre>

     * @param givenKey
     * @return The whole entry may be null. Otherwise the value will never be null but it will have an owner ({@link
     *         DistributionValue#getCurrentOwner()}).
     */

    @ReadOnly
    Entry<DistributionKey, DefaultDistributionValue> getNearestValidEntry(DistributionKey givenKey) {
        //todo Need a threadsafe snapshot here.

        readLock.lock();
        try {
            Entry<DistributionKey, DefaultDistributionValue> floorEntry = actual.floorEntry(givenKey);
            if (validateEntry(floorEntry)) {
                return floorEntry;
            }

            //----------------

            int totalSize = actual.size();
            if (totalSize == 0) {
                return null;
            }

            //----------------

            //Found nothing - Assume hash position is at B.

            Entry<DistributionKey, DefaultDistributionValue> entryAtB = null;

            //Scan lower entries.
            {
                NavigableMap<DistributionKey, DefaultDistributionValue> allLowerEntries =
                        actual.headMap(givenKey, true);

                //Reverse it.
                allLowerEntries = allLowerEntries.descendingMap();

                for (Entry<DistributionKey, DefaultDistributionValue> entry : allLowerEntries.entrySet()) {
                    if (validateEntry(entry)) {
                        return entry;
                    }

                    if (entryAtB == null) {
                        entryAtB = entry;

                        //Found something but it is invalid. Position is at B.
                    }
                }
            }

            //----------------

            //Still nothing. Position must've been at A. So, we have to wrap around and start from H.
            {
                //Get the reverse from H.
                NavigableMap<DistributionKey, DefaultDistributionValue> allEntries = actual.descendingMap();

                for (Entry<DistributionKey, DefaultDistributionValue> entry : allEntries.entrySet()) {
                    if (validateEntry(entry)) {
                        return entry;
                    }

                    //We' did this when we scanned the lower entries.
                    if (entry == entryAtB) {
                        break;
                    }
                }
            }

            //----------------

            //Found nothing even after scanning the entire set. Give up.

            return null;
        }
        finally {
            readLock.unlock();
        }
    }

    /**
     * @param entry
     * @return true if entry is not null and has a valid owner ({@link DefaultDistributionValue#hasCurrentOwner()}).
     */
    static boolean validateEntry(Entry<DistributionKey, DefaultDistributionValue> entry) {
        if (entry != null) {
            DefaultDistributionValue value = entry.getValue();

            return (value != null && value.hasCurrentOwner());
        }

        return false;
    }

    @Override
    public int getKeySize() {
        readLock.lock();
        try {
            return actual.size();
        }
        finally {
            readLock.unlock();
        }
    }

    @Copy
    @Override
    public Set<DistributionKey> getKeys() {
        readLock.lock();
        try {
            return new TreeSet<DistributionKey>(actual.keySet());
        }
        finally {
            readLock.unlock();
        }
    }

    @ReadOnly
    @Override
    public DistributionValue getValue(DistributionKey key) {
        Entry<DistributionKey, DefaultDistributionValue> previousOwner = getNearestValidEntry(key);

        //So, there was someone.
        if (previousOwner != null) {
            return previousOwner.getValue();
        }

        return null;
    }

    @Copy
    @Override
    public Collection<? extends DistributionValue> getValues() {
        readLock.lock();
        try {
            return new LinkedList<DistributionValue>(actual.values());
        }
        finally {
            readLock.unlock();
        }
    }

    /**
     * Also removes from suspect list.
     *
     * @param member
     * @param keys
     */
    @Override
    public void removeMember(Member member, DistributionKey[] keys) {
        writeLock.lock();
        try {
            knownMembers.remove(member.getId());

            DefaultSuspectedMember suspectedMember = justRemoveSuspectedMember(member);
            if (suspectedMember != null) {
                suspectedMember.discard();
            }

            justRemoveMember(member, keys);
        }
        finally {
            writeLock.unlock();
        }
    }

    private void justRemoveMember(Member member, DistributionKey[] keys) {
        writeLock.lock();
        try {
            for (DistributionKey key : keys) {
                DefaultDistributionValue existingValue = actual.remove(key);

                if (existingValue == null) {
                    logger.log(Level.FINE, String.format("No Member to remove for Key [%s]", key));
                }
                else {
                    if (!existingValue.getCurrentOwner().equals(member)) {
                        //Oops... this is not ours. Put it back.. How did this even happen?
                        actual.put(key, existingValue);
                    }
                    else {
                        logger.log(Level.FINE, String.format("Disconnecting Member: [%s], Key: [%s]", member, key));
                    }
                }
            }

            currentVersion++;

            writeLockChangeCondition.signal();
        }
        finally {
            writeLock.unlock();
        }
    }

    //---------------

    @Override
    public int waitUntilKeysChange(TimeUnit timeUnit, long time) throws InterruptedException {
        writeLock.lock();
        try {
            writeLockChangeCondition.await(time, timeUnit);

            return actual.size();
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public void markSuspect(Member member, DistributionKey[] keys, @Optional Exception e) {
        writeLock.lock();
        try {
            logger.log(Level.WARNING, String.format("Member: [%s] has been marked as suspect", member.getId()));

            //---------------

            DefaultSuspectedMember
                    suspectedMember = new DefaultSuspectedMember(member, keys, System.currentTimeMillis(), e);

            suspectedMembers.put(member.getId(), suspectedMember);

            justRemoveMember(member, keys);
        }
        finally {
            writeLock.unlock();
        }
    }

    @Copy
    @Override
    public Collection<DefaultSuspectedMember> getSuspectedMembers() {
        readLock.lock();
        try {
            return new LinkedList<DefaultSuspectedMember>(suspectedMembers.values());
        }
        finally {
            readLock.unlock();
        }
    }

    @Override
    public void unmarkSuspect(Member member) {
        writeLock.lock();
        try {
            DefaultSuspectedMember suspectedMember = justRemoveSuspectedMember(member);
            if (suspectedMember == null) {
                return;
            }

            //---------------

            logger.log(Level.INFO, String.format("Member: [%s] is no longer suspect", member.getId()));

            putMember(member, suspectedMember.getKeys());

            suspectedMember.discard();
        }
        finally {
            writeLock.unlock();
        }
    }

    /**
     * @param member
     * @return Remember to call {@link DefaultSuspectedMember#discard()}.
     */
    private DefaultSuspectedMember justRemoveSuspectedMember(Member member) {
        DefaultSuspectedMember suspectedMember = suspectedMembers.remove(member.getId());
        if (suspectedMember == null) {
            return null;
        }

        return suspectedMember;
    }

    @Override
    public void cleanup() {
        //Compact empty values.

        writeLock.lock();
        try {
            Iterator<Entry<DistributionKey, DefaultDistributionValue>> iterator = actual.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry<DistributionKey, DefaultDistributionValue> entry = iterator.next();

                if (entry != null) {
                    DefaultDistributionValue value = entry.getValue();

                    if (value == null || value.hasCurrentOwner() == false) {
                        iterator.remove();
                    }
                }
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    //---------------

    @Override
    public void discard() {
        writeLock.lock();
        try {
            actual.clear();
            actual = null;

            currentVersion = 0;
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{" + "id=" + id + ", actual=" + actual + ", version=" + currentVersion + '}';
    }

    //---------------

    @Override
    public void printReport() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos);

        readLock.lock();
        try {
            pw.println();
            pw.println("[Distribution table report]");
            pw.println();

            pw.print("Table id: ");
            pw.println(id);
            pw.println();

            pw.print("Members: ");
            pw.println(knownMembers.size());
            for (Member member : knownMembers.values()) {
                pw.print("\t");
                pw.println(member);
            }
            pw.println();

            pw.print("Suspected members: ");
            pw.println(suspectedMembers.size());
            for (DefaultSuspectedMember member : suspectedMembers.values()) {
                pw.print("\t");
                pw.print(member.member);
                pw.print(" at ");
                pw.println(new Date(member.suspectedAt));

                if (member.reason != null) {
                    pw.print("\t");
                    pw.println(member.reason);
                }
            }
            pw.println();

            pw.print("Keys: ");
            pw.println(actual.size());
            for (Entry<DistributionKey, DefaultDistributionValue> entry : actual.entrySet()) {
                pw.print("\t");
                pw.print(entry.getKey());
                pw.print(" = ");
                pw.println(entry.getValue().getCurrentOwner());
            }

            pw.println();
        }
        finally {
            readLock.unlock();
        }

        pw.close();

        logger.log(Level.WARNING, baos.toString());
        baos = null;
    }


    //---------------

    public static class DefaultSuspectedMember implements SuspectedMember {
        protected Member member;

        protected DistributionKey[] keys;

        protected long suspectedAt;

        protected Exception reason;

        public DefaultSuspectedMember(Member member, DistributionKey[] keys, long suspectedAt,
                                      @Optional Exception reason) {
            this.member = member;
            this.keys = keys;
            this.suspectedAt = suspectedAt;
            this.reason = reason;
        }

        public Member getMember() {
            return member;
        }

        public DistributionKey[] getKeys() {
            return keys;
        }

        public long getSuspectedAt() {
            return suspectedAt;
        }

        @Optional
        public Exception getReason() {
            return reason;
        }

        public void discard() {
            member = null;
            keys = null;
            reason = null;
        }
    }
}
