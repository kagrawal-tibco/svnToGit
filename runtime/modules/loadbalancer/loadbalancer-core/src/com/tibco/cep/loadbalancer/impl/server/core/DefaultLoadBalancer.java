package com.tibco.cep.loadbalancer.impl.server.core;

import static com.tibco.cep.util.Helper.$logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.impl.message.SimpleDistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.message.SpecialMessage;
import com.tibco.cep.loadbalancer.server.core.DistributionTable;
import com.tibco.cep.loadbalancer.server.core.DistributionTable.SuspectedMember;
import com.tibco.cep.loadbalancer.server.core.DistributionValue;
import com.tibco.cep.loadbalancer.server.core.LoadBalancer;
import com.tibco.cep.loadbalancer.server.core.LoadBalancerException;
import com.tibco.cep.loadbalancer.server.core.LoadBalancerStatus;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.NoRoutesFoundException;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.loadbalancer.server.core.sink.SinkException;
import com.tibco.cep.loadbalancer.server.core.sink.SinkState;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStateSnapshot;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Mar 19, 2010 / Time: 2:07:31 PM
*/

@LogCategory("loadbalancer.core.server")
public class DefaultLoadBalancer implements LoadBalancer {
    /**
     * {@value}.
     */
    public static final int WAIT_FOR_ROUTES_MILLIS = 5000;

    /**
     * {@value}.
     */
    public static final int NO_ROUTES_ERROR_AFTER_ITERS = 3;

    /**
     * {@value}.
     */
    public static final long DUP_DELIVERY_DURATION_AFTER_CHANGE_NANOS = TimeUnit.MINUTES.toNanos(5);

    /**
     * {@value}.
     */
    public static final long TEST_MEMBERS_AFTER_NANOS = DUP_DELIVERY_DURATION_AFTER_CHANGE_NANOS * 6;

    protected ReentrantReadWriteLock rwLock;

    protected ReadLock readLock;

    protected WriteLock writeLock;

    protected MessageSender messageSender;

    protected Id id;

    protected Id sourceId;

    protected DistributionTable distributionTable;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public DefaultLoadBalancer(Id id, Id sourceId, DistributionTable distributionTable) {
        this.id = id;
        this.sourceId = sourceId;
        this.distributionTable = distributionTable;
        this.rwLock = new ReentrantReadWriteLock();
        this.readLock = this.rwLock.readLock();
        this.writeLock = this.rwLock.writeLock();
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public Id getSourceId() {
        return sourceId;
    }

    @Override
    public DistributionTable getDistributionTable() {
        return distributionTable;
    }

    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public void setSourceId(Id sourceId) {
        this.sourceId = sourceId;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    @Override
    public void start() {
        if (messageSender != null) {
            return;
        }

        messageSender = new MessageSender();

        logger.log(Level.INFO, String.format("LoadBalancer [%s] started for source [%s]", getId(), sourceId));
    }

    /**
     * Sets the {@link KnownHeader}s.
     *
     * @param message
     * @return
     * @throws LoadBalancerException
     * @see #NO_ROUTES_ERROR_AFTER_ITERS
     * @see #TEST_MEMBERS_AFTER_NANOS
     * @see #DUP_DELIVERY_DURATION_AFTER_CHANGE_NANOS
     */
    @Override
    public MessageHandle send(DistributableMessage message) throws LoadBalancerException {
        return messageSender.send(message);
    }

    @Override
    public Map<Member, SinkException> sendToAll(SpecialMessage specialMessage) {
        readLock.lock();
        try {
            logger.log(Level.FINE,
                    String.format("LoadBalancer [%s] for source [%s] sending special message [%s:%s]", getId(),
                            sourceId, specialMessage.getUniqueId(), specialMessage.getSenderId()));

            Map<Member, SinkException> map = new HashMap<Member, SinkException>();

            Collection<? extends Member> knownMembers = distributionTable.getKnownMembers();
            for (Member knownMember : knownMembers) {
                Sink sink = knownMember.getEndpointFor(sourceId);

                try {
                    sink.send(specialMessage);

                    //------------------

                    map.put(knownMember, null);

                    logger.log(Level.FINE,
                            String.format(
                                    "LoadBalancer [%s] for source [%s] sent special message [%s:%s] to member [%s]",
                                    getId(), sourceId, specialMessage.getUniqueId(), specialMessage.getSenderId(),
                                    knownMember.getId()));
                }
                catch (SinkException e) {
                    map.put(knownMember, e);

                    logger.log(Level.WARNING,
                            String.format(
                                    "LoadBalancer [%s] for source [%s] could not send special message [%s:%s] to member [%s]",
                                    getId(), sourceId, specialMessage.getUniqueId(), specialMessage.getSenderId(),
                                    knownMember.getId()), e);
                }
            }

            return map;
        }
        finally {
            readLock.unlock();
        }
    }

    private void checkRoutes() throws NoRoutesFoundException {
        int keySize = distributionTable.getKeySize();

        for (int i = 0; keySize == 0; i++) {
            if (i == NO_ROUTES_ERROR_AFTER_ITERS) {
                String msg =
                        String.format("LoadBalancer [%s] did not find routes for source [%s] even after [%d] attempts.",
                                getId(), sourceId, i);

                throw new NoRoutesFoundException(msg);
            }
            else {
                String msg = String.format("LoadBalancer [%s] did not find routes for source [%s].", getId(), sourceId);

                logger.log(Level.WARNING, msg + " Waiting for routes to appear.");
            }

            //------------------

            waitForDistTableToChange();

            keySize = distributionTable.getKeySize();

            if (keySize > 0) {
                logger.log(Level.FINE, String.format(
                        "LoadBalancer [%s] has [%d] routes for source [%s].", getId(), keySize, sourceId));
            }
        }
    }

    /**
     * {@link #writeLock} should be acquired before calling this method.
     */
    private void waitForDistTableToChange() {
        try {
            distributionTable.waitUntilKeysChange(TimeUnit.MILLISECONDS, WAIT_FOR_ROUTES_MILLIS);
        }
        catch (InterruptedException e) {
            //Ignore.
        }
    }

    private void checkSuspectedMembers() {
        Collection<? extends SuspectedMember> suspectedMembers = distributionTable.getSuspectedMembers();
        if (suspectedMembers.isEmpty()) {
            return;
        }

        //------------------

        logger.log(Level.FINE, String.format("LoadBalancer [%s] checking suspected members.", getId()));

        for (SuspectedMember suspectedMember : suspectedMembers) {
            Member member = suspectedMember.getMember();
            Sink sink = member.getEndpointFor(sourceId);

            logger.log(Level.FINE, String.format("LoadBalancer [%s] checking suspected member [%s] sink [%s] state.",
                    getId(), member.getId(), sink.getId()));

            //------------------

            SinkStateSnapshot snapshot = sink.checkState();

            //------------------

            SinkState sinkState = snapshot.getSinkState();
            switch (sinkState) {
                case on: {
                    logger.log(Level.FINE,
                            String.format("LoadBalancer [%s] suspected member [%s] sink [%s] is now in [%s] state." +
                                    " This member will now be unmarked.",
                                    getId(), member.getId(), sink.getId(), sinkState.name()));

                    distributionTable.unmarkSuspect(member);
                }
                break;

                case off:
                case suspect:
                default: {
                    logger.log(Level.FINE,
                            String.format("LoadBalancer [%s] suspected member [%s] sink [%s] is now in [%s] state." +
                                    " This member will still remain suspect.",
                                    getId(), member.getId(), sink.getId(), sinkState.name()));
                }
            }
        }

        //------------------

        suspectedMembers = distributionTable.getSuspectedMembers();

        logger.log(Level.FINE, String.format("LoadBalancer [%s] is done checking suspected members." +
                " There are [%d] members still suspect.", getId(), suspectedMembers.size()));
    }

    /**
     * @param message
     * @param distributionKey
     * @param sourceId
     * @return <code>null</code> if unsuccessful.
     */
    private MessageHandle routeTo(DistributableMessage message, DistributionKey distributionKey,
                                  Id sourceId) {
        DistributionValue distributionValue = distributionTable.getValue(distributionKey);

        if (distributionValue == null) {
            logger.log(Level.WARNING, String.format(
                    "LoadBalancer [%s] did not find a route for message [%s/%s] coming from source [%s]." +
                            " Other routes will be explored if they exist.",
                    getId(), message.getHeaderValue(KnownHeader.__content_id__), distributionKey, sourceId));

            return null;
        }

        //------------------

        Member member = distributionValue.getCurrentOwner();

        if (member == null) {
            logger.log(Level.WARNING, String.format(
                    "LoadBalancer [%s] found a route for message [%s/%s] coming from source [%s]" +
                            " but the destination/member was absent. Other routes will be explored if they exist.",
                    getId(), message.getHeaderValue(KnownHeader.__content_id__), distributionKey, sourceId));

            return null;
        }

        //------------------

        Sink sink = member.getEndpointFor(sourceId);

        if (sink == null) {
            logger.log(Level.WARNING, String.format(
                    "LoadBalancer [%s] did not find a route for message [%s/%s] coming from source [%s]" +
                            " but the sink was absent. Other routes will be explored if they exist.",
                    getId(), message.getHeaderValue(KnownHeader.__content_id__), distributionKey, sourceId));

            return null;
        }

        //------------------

        message.setHeaderValue(KnownHeader.__version_id__, System.currentTimeMillis());

        try {
            return sink.send(message);
        }
        catch (SinkException e) {
            logger.log(Level.SEVERE,
                    String.format(
                            "LoadBalancer [%s] encountered an error occurred while sending message [%s/%s] coming from" +
                                    " source [%s] to sink [%s]. If it was a transport error, an attempt will be made to rectify it.",
                            getId(), message.getHeaderValue(KnownHeader.__content_id__), distributionKey,
                            sourceId, sink.getId()), e);

            //------------------

            DistributionKey[] keys = sink.getDistributionStrategy().getBootstrapKeys();

            distributionTable.markSuspect(member, keys, e);

            //------------------

            //Ping and reconnect if possible.
            SinkStateSnapshot sinkStateSnapshot = sink.checkState();

            if (sinkStateSnapshot.getSinkState() != SinkState.on) {
                String msg = String.format(
                        "LoadBalancer [%s] could not send the message [%s/%s] coming from source [%s]" +
                                " as the sink [%s] is not working. Other routes will be explored if they exist.",
                        getId(), message.getHeaderValue(KnownHeader.__content_id__), distributionKey,
                        sourceId, sink.getId());

                Exception newEx = sinkStateSnapshot.getException();
                if (newEx == null) {
                    logger.log(Level.WARNING, msg);
                }
                else {
                    logger.log(Level.WARNING, msg, newEx);
                }
            }

            //------------------

            //We don't know how much of the message went to the client.

            message.setHeaderValue(KnownHeader.__version_id__, null);

            return null;
        }
    }

    @Override
    public void stop() {
        if (messageSender == null) {
            return;
        }

        messageSender = null;

        logger.log(Level.INFO, String.format("LoadBalancer [%s] stopped for source [%s]", getId(), sourceId));
    }

    //---------------

    @Override
    public LoadBalancerStatus getLoadBalancerStatus() {
        //todo
        return null;
    }

    @Override
    public String[] findOwners(String key) {
        SimpleDistributableMessage msg = new SimpleDistributableMessage(null, "tmpMsg_" + System.nanoTime(), key, 4);
        DistributionKey[] keys = msg.getAllKeys();
        String[] owners = new String[keys.length];

        for (int i = 0; i < owners.length; i++) {
            DistributionValue distributionValue = distributionTable.getValue(keys[i]);
            if (distributionValue != null) {
                Member member = distributionValue.getCurrentOwner();
                owners[i] = keys[i] + "=" + ((member != null) ? member.toString() : "None");
            }
            else {
                owners[i] = keys[i] + "=" + "None";
            }
        }

        return owners;
    }

    @Override
    public void printReport() {
        distributionTable.cleanup();
        logger.log(Level.WARNING, String.format("[LoadBalancer report of [%s] for source [%s]]", getId(), sourceId));
        distributionTable.printReport();
    }

    //------------------

    protected class MessageSender {
        protected volatile int lastKnownTblVersion = -1;

        protected volatile long lastTblVersionChangeAtNanos = System.nanoTime();

        protected volatile long lastMemberTestAtNanos = 0;

        public MessageHandle send(DistributableMessage msgToDeliver) throws LoadBalancerException {
            final long nowNanos = System.nanoTime();
            int currentTblVersion = distributionTable.getCurrentVersion();

            try {
                int loopCount = 1;

                for (; loopCount <= 2 /*Try twice*/; loopCount++) {

                    if ((loopCount > 1) ||
                            (currentTblVersion != lastKnownTblVersion) ||
                            (nowNanos - lastMemberTestAtNanos) >= TEST_MEMBERS_AFTER_NANOS) {
                        writeLock.lock();
                        try {
                            checkSuspectedMembers();

                            checkRoutes();

                            lastMemberTestAtNanos = nowNanos;
                            currentTblVersion = distributionTable.getCurrentVersion();
                        }
                        finally {
                            writeLock.unlock();
                        }
                    }

                    //------------------

                    readLock.lock();
                    try {
                        int dupHeaderSituation = 0;
                        //Membership has changed.
                        if (currentTblVersion != lastKnownTblVersion) {
                            dupHeaderSituation = 1;
                        }
                        //Membership changed in the recent past or we booted up just now.
                        else if ((nowNanos - lastTblVersionChangeAtNanos) <= DUP_DELIVERY_DURATION_AFTER_CHANGE_NANOS) {
                            dupHeaderSituation = 2;
                        }

                        switch (dupHeaderSituation) {
                            //Cluster has not stabilized yet.
                            case 1:
                                lastKnownTblVersion = currentTblVersion;
                                lastTblVersionChangeAtNanos = nowNanos;
                            case 2:
                                msgToDeliver.setHeaderValue(KnownHeader.__likely_dup_delivery__, Boolean.TRUE);
                                break;

                            //All ok.
                            case 0:
                            default:
                                break;
                        }

                        //------------------

                        final DistributionKey primaryDistKey = msgToDeliver.getPrimaryKey();

                        //Try generating only the primary key and try routing using this.
                        MessageHandle messageHandle = routeTo(msgToDeliver, primaryDistKey, sourceId);

                        if (messageHandle != null && messageHandle.getSentAt() > MessageHandle.NOT_APPLICABLE) {
                            return messageHandle;
                        }

                        //Fetch all the keys this time (lazy key init).
                        DistributionKey[] distributionKeys = msgToDeliver.getAllKeys();

                        for (int i = 1 /*Skip the first*/; i < distributionKeys.length; i++) {
                            DistributionKey distributionKey = distributionKeys[i];

                            messageHandle = routeTo(msgToDeliver, distributionKey, sourceId);

                            if (messageHandle != null && messageHandle.getSentAt() > MessageHandle.NOT_APPLICABLE) {
                                return messageHandle;
                            }
                        }
                    }
                    finally {
                        readLock.unlock();
                    }
                }

                //------------------

                String msg = String.format(
                        "LoadBalancer [%s] could not deliver the message [%s] for source [%s] even after [%d] attempts.",
                        getId(), msgToDeliver.getHeaderValue(KnownHeader.__content_id__), sourceId, loopCount);

                throw new NoRoutesFoundException(msg);
            }
            catch (LoadBalancerException le) {
                throw le;
            }
            catch (Exception e) {
                throw undeliverableMessage(msgToDeliver, e);
            }
        }

        private LoadBalancerException undeliverableMessage(DistributableMessage msgToDeliver, @Optional Exception e) {
            String msg = String.format(
                    "LoadBalancer [%s] could not deliver the message [%s] coming from source [%s].",
                    getId(), msgToDeliver.getHeaderValue(KnownHeader.__content_id__), sourceId);

            if (e == null) {
                return new LoadBalancerException(msg);
            }

            return new LoadBalancerException(msg, e);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "sourceId=" + sourceId + ", id=" + id + '}';
    }
}
