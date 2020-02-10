package com.tibco.cep.query.stream.impl.rete.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.cache.SharedPointerImpl;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.WithdrawableSource;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.impl.rete.GenericReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityBatchHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityClassHierarchyHelper;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.ReteEntityId;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.continuous.ContinuousQuery;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.ReusableIterator;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 2:40:39 PM
 */

public class ReteQueryImpl extends ContinuousQueryImpl<GenericReteEntityHandle>
        implements SnapshotAssistantOverseer, ReteQuery<GenericReteEntityHandle> {
    protected final HashMap<Class, SharedObjectSource> cachedSources;

    /**
     * Includes all the main runtime classes explicitly specified in the (From clause) Query and
     * their sub-classes that get added implicitly.
     */
    protected final HashMap<Class, ReteEntitySource[]> reteEntityClassAndListeners;

    protected final HashMap<Class, ReteEntityFilter[]> reteEntityClassAndFilters;

    /**
     * <p> Can be <code>null</code>. </p> <p> Holds the Tuple Ids until they are deleted. We just
     * create a new ReteEntity with the same old Id and send it downstream if we receive a
     * delete-notification from the Listener - that way we avoid storing the entities which is such
     * a waste of memory because the downstream component could've dropped it by now. When a
     * mod/delete arrives, we create a new Tuple with the same Tuple Id and send it downstream.</p>
     */
    protected final CustomHashSet<Long> reteIdsFromWithdrawableSources;

    private AppendOnlyQueue<ReteEntity> batchOpsContainer;

    protected final LinkedHashMap<Class, Class[]> sourceClassAndHierarchy;

    /**
     * Don't mark this as <code>volatile</code> directly. We don't want to keep checking a volatile
     * field even after the assistant has completed.
     */
    protected AtomicReference<SnapshotAssistant> snapshotAssistantRef;

    private AppendOnlyQueue<Long> reteIdsSeenBefore;

    private ReusableIterator<Long> reteIdsSeenBeforeIter;

    protected boolean reteEntityFiltersEnabled;

    protected boolean isAggressivePrefetchSupported;

    protected CacheScout cacheScout;

    protected LinkedBlockingQueue<GenericReteEntityHandle> accumulatedItemsDuringSSA;

    protected Object lastAccumulatedItemFromSSA;

    protected CustomHashSet<Long> idsOfNewItemsReceivedFromSSA;

    protected boolean[] snapshotRequiredForSource;

    protected RuleSession ruleSession;

    protected final HashMap<String, ReteEntityFilter[]> reteEntityResourceIdAndFilters;

    private boolean selfJoin;

    /**
     * @param regionName
     * @param resourceId
     * @param reteEntitySources
     * @param sink
     * @param syncWorker
     * @param ruleSession       Can be <code>null</code>.
     */
    public ReteQueryImpl(String regionName, ResourceId resourceId, ReteEntitySource[] reteEntitySources,
                         Sink sink, boolean syncWorker, RuleSession ruleSession) {
        this(regionName, resourceId, reteEntitySources, sink, syncWorker, ruleSession, null, null);
    }

    /**
     * Async-worker.
     *
     * @param regionName
     * @param resourceId
     * @param reteEntitySources
     * @param sink
     * @param snapshotAssistant
     * @param snapshotRequiredForRES
     * @param ruleSession            Can be <code>null</code>.
     */
    public ReteQueryImpl(String regionName, ResourceId resourceId, ReteEntitySource[] reteEntitySources,
                         Sink sink, SnapshotAssistant snapshotAssistant,
                         boolean[] snapshotRequiredForRES, RuleSession ruleSession) {
        this(regionName, resourceId, reteEntitySources, sink, false, ruleSession, snapshotAssistant,
                snapshotRequiredForRES);
    }

    /**
     * @param regionName
     * @param resourceId
     * @param reteEntitySources
     * @param sink
     * @param syncWorker
     * @param ruleSession            Can be <code>null</code>.
     * @param snapshotAssistant      Can be <code>null</code>. Used only if "syncWorker" is
     *                               <code>false</code> - CQ mode with Snapshot.
     * @param snapshotRequiredForRES Used if "snapshotAssistant" is not <code>null</code>. The order
     *                               and number should match the "reteEntitySources". Otherwise
     *                               should be <code>null</code>. Let's look at a certain class that
     *                               appears at a particular position and has been marked
     *                               no-snapshot-required. But, if the same class appears in another
     *                               class' hierarchy which has been marked as "snapshot-required",
     *                               then the "not-required" flag gets over-ridden by the other
     *                               "required" value.
     */
    protected ReteQueryImpl(String regionName, ResourceId resourceId,
                            ReteEntitySource[] reteEntitySources,
                            Sink sink, boolean syncWorker, RuleSession ruleSession,
                            SnapshotAssistant snapshotAssistant, boolean[] snapshotRequiredForRES) {
        super(regionName, resourceId, reteEntitySources, sink,
                (syncWorker ? null : new AsyncListenerImpl(ruleSession)));

        this.ruleSession = ruleSession;

        //----------

        this.snapshotAssistantRef = new AtomicReference<SnapshotAssistant>(snapshotAssistant);
        if (snapshotAssistant != null) {
            snapshotAssistant.setOverseer(this);

            this.accumulatedItemsDuringSSA = new LinkedBlockingQueue<GenericReteEntityHandle>();
            this.idsOfNewItemsReceivedFromSSA = new CustomHashSet<Long>();

            this.snapshotRequiredForSource = snapshotRequiredForRES;
        }

        //----------

        Manager manager = Registry.getInstance().getComponent(Manager.class);
        RegionManager regionManager = manager.getRegionManagers().get(regionName);
        ReteEntityClassHierarchyHelper hierarchyHelper = regionManager.getHierarchyHelper();

        /*
         Find all the Sub-classes + main Class for each Source. Then register the Source under each
         one of those Classes.
         */
        HashMap<Class, CustomHashSet<ReteEntitySource>> allListeners =
                new HashMap<Class, CustomHashSet<ReteEntitySource>>();

        this.sourceClassAndHierarchy = new LinkedHashMap<Class, Class[]>();

        HashMap<Class, CustomHashSet<ReteEntityFilter>> allFilters =
                new HashMap<Class, CustomHashSet<ReteEntityFilter>>();

        this.reteEntityResourceIdAndFilters = new HashMap<String, ReteEntityFilter[]>();

        for (ReteEntitySource reteSource : reteEntitySources) {
            Class sourceClass = reteSource.getReteEntityClass();
            Class[] allSubClasses = hierarchyHelper.getSubClasses(sourceClass);

            sourceClassAndHierarchy.put(sourceClass, allSubClasses);

            for (Class subClass : allSubClasses) {
                CustomHashSet<ReteEntitySource> listeners = allListeners.get(subClass);

                if (listeners == null) {
                    listeners = new CustomHashSet<ReteEntitySource>();
                    allListeners.put(subClass, listeners);
                }

                listeners.add(reteSource);
            }

            //-----------

            ReteEntityFilter filter = reteSource.getReteEntityFilter();
            if (filter != null) {
                for (Class subClass : allSubClasses) {
                    CustomHashSet<ReteEntityFilter> filters = allFilters.get(subClass);

                    if (filters == null) {
                        filters = new CustomHashSet<ReteEntityFilter>();
                        allFilters.put(subClass, filters);
                    }

                    filters.add(filter);
                }

                CustomHashSet<ReteEntityFilter> tempFilters = new CustomHashSet<ReteEntityFilter>();
                tempFilters.add(filter);
                this.reteEntityResourceIdAndFilters.put(reteSource.getResourceId().getId(), tempFilters.toArray(new ReteEntityFilter[tempFilters.size()]));
            }
        }

        //Convert to a faster array based version of the same.
        this.reteEntityClassAndListeners =
                new HashMap<Class, ReteEntitySource[]>(allListeners.size() * 2);
        for (Class clazz : allListeners.keySet()) {
            CustomHashSet<ReteEntitySource> listeners = allListeners.get(clazz);

            ReteEntitySource[] array = listeners.toArray(new ReteEntitySource[listeners.size()]);
            this.reteEntityClassAndListeners.put(clazz, array);
        }

        //Convert to a faster array based version of the same.
        this.reteEntityClassAndFilters =
                new HashMap<Class, ReteEntityFilter[]>(allFilters.size() * 2);
        for (Class clazz : allFilters.keySet()) {
            CustomHashSet<ReteEntityFilter> filters = allFilters.get(clazz);

            ReteEntityFilter[] array = filters.toArray(new ReteEntityFilter[filters.size()]);
            this.reteEntityClassAndFilters.put(clazz, array);
        }

        //----------

        this.reteIdsFromWithdrawableSources = new CustomHashSet<Long>();

        // Prime the map first. Populate it later in the init(...) method.
        this.cachedSources = new HashMap<Class, SharedObjectSource>(allListeners.size() * 2);
        for (Class clazz : allListeners.keySet()) {
            this.cachedSources.put(clazz, null);
        }

        this.reteIdsSeenBefore = new AppendOnlyQueue<Long>();
        this.reteIdsSeenBeforeIter = this.reteIdsSeenBefore.iterator();

        this.reteEntityFiltersEnabled = true;

        this.cacheScout = regionManager.getCacheScout();
        this.isAggressivePrefetchSupported = this.cacheScout.isAggressivePrefetchSupported();
    }

    /**
     * @return Keys are in the same order as the {@link com.tibco.cep.query.stream.impl.rete.ReteEntitySource}s.
     *         Value will also contain the key.
     */
    @Override
    public LinkedHashMap<Class, Class[]> getSourceClassAndHierarchy() {
        return sourceClassAndHierarchy;
    }

    /**
     * @return Same order as the keys in {@link #getSourceClassAndHierarchy()}.
     */
    @Override
    public boolean[] getSnapshotRequiredForSource() {
        return snapshotRequiredForSource;
    }

    @Override
    public Collection<Class> getReteEntityClasses() {
        return reteEntityClassAndListeners.keySet();
    }

    @Override
    public Map<Class, ReteEntityFilter[]> getReteEntityClassAndFilters() {
        return reteEntityClassAndFilters;
    }

    @Override
    public HashMap<Class, ReteEntitySource[]> getReteEntityClassAndListeners() {
        return reteEntityClassAndListeners;
    }

    @Override
    public HashMap<String, ReteEntityFilter[]> getReteEntityResourceIdAndFilters() {
        return reteEntityResourceIdAndFilters;
    }

    /**
     * Be very careful with this. Even iterating over this will affect performance. Do no modify the
     * elements unless you know what you are doing.
     *
     * @return
     */
    public ConcurrentLinkedQueue<GenericReteEntityHandle> getQueuedInput() {
        return queuedInput;
    }

    /**
     * <p>Thread-safe! Uses {@link #acquireQueryLock()} and {@link #relinquishQueryLock()}.</p>
     * <p>Enables the filters - i.e this query can use these filters to see if they should be
     * sending the rete-tuples to their respective rete-sources.</p>
     */
    @Override
    public void enableReteEntityFilters() {
        acquireQueryLock();
        try {
            reteEntityFiltersEnabled = true;
        } finally {
            relinquishQueryLock();
        }
    }

    /**
     * Thread-safe! Uses {@link #acquireQueryLock()} and {@link #relinquishQueryLock()}.
     */
    @Override
    public void disableReteEntityFilters() {
        acquireQueryLock();
        try {
            reteEntityFiltersEnabled = false;
        } finally {
            relinquishQueryLock();
        }
    }

    @Override
    protected String sanitizeMessage(Throwable t) {
        String s = t.getMessage();

        while (t != null) {
            if (t instanceof NullPointerException) {
                s = "An error occurred because an entity/property/reference to an entity" +
                        " that was not supposed to be null turned out to be null.";

                return s;
            }

            t = t.getCause();
        }

        return s;
    }

    /**
     * @return Can be <code>null</code>.
     */
    @Override
    public RuleSession getRuleSession() {
        return ruleSession;
    }

    /**
     * Invokes {@link ContinuousQuery#init(Map)}
     *
     * @param sourceRepository
     * @param externalData
     * @throws Exception
     */
    @Override
    public void init(SharedObjectSourceRepository sourceRepository,
                     Map<String, Object> externalData)
            throws Exception {
        super.init(externalData);

        //----------

        this.batchOpsContainer =
                new AppendOnlyQueue<ReteEntity>(context.getQueryContext().getArrayPool());

        for (Class klass : cachedSources.keySet()) {
            SharedObjectSource source = sourceRepository.getSource(klass.getName());

            cachedSources.put(klass, source);
        }
    }

    @Override
    public void setSelfJoin(boolean selfJoin) {
        this.selfJoin = selfJoin;
    }

    @Override
    public boolean isSelfJoin() {
        return this.selfJoin;
    }

    @Override
    public void start() throws Exception {
        super.start();

        cacheScout.startWarming(this);
    }

    @Override
    public void stop() throws Exception {
        if (hasStopped()) {
            //Already stopped.
            return;
        }

        super.stop();

        cacheScout.stopWarming(this);

        cachedSources.clear();
        reteEntityClassAndListeners.clear();
        reteIdsFromWithdrawableSources.clear();
        sourceClassAndHierarchy.clear();

        if (accumulatedItemsDuringSSA != null) {
            accumulatedItemsDuringSSA.clear();
            accumulatedItemsDuringSSA = null;
        }

        if (idsOfNewItemsReceivedFromSSA != null) {
            idsOfNewItemsReceivedFromSSA.clear();
            idsOfNewItemsReceivedFromSSA = null;
        }

        if (batchOpsContainer != null) {
            batchOpsContainer.discard();
            batchOpsContainer = null;
        }
    }

    public void assistantCompleted() {
        while (accumulatedItemsDuringSSA.isEmpty() == false) {
            accumulatedItemsDuringSSA.drainTo(queuedInput);
        }

        //---------

        //Just change the internal ref of the field, which is atomic. Do not change the field.
        snapshotAssistantRef.set(null);

        //---------

        /*
        Check again to make sure there weren't any latecomers. This could however, enqueue these
        events in the wrong order, but at least we won't lose them.
         */
        while (accumulatedItemsDuringSSA.isEmpty() == false) {
            accumulatedItemsDuringSSA.drainTo(queuedInput);
        }

        accumulatedItemsDuringSSA = null;
    }

    /**
     * Thread safe!
     *
     * @param handle
     * @param sender
     * @throws Exception
     */
    public final void enqueueInput(ReteEntityHandle handle, Sender sender) throws Exception {
        switch (sender) {
            case CURRENT: {
                //Fast path is without volatile check.
                if (snapshotAssistantRef == null) {
                    super.enqueueInput(handle);

                    return;
                }

                SnapshotAssistant assistant = snapshotAssistantRef.get();
                if (assistant == null) {
                    //No other thread mutates this field except for the "CURRENT" sender.
                    snapshotAssistantRef = null;

                    /*
                    So, the assistant has completed. The next time we won't take this path. We'll
                    take the fast path instead.
                    */
                    super.enqueueInput(handle);
                } else {
                    accumulateCurrent(handle);
                }

                return;
            }

            case SNAPSHOT:
                super.enqueueInput(handle);

                return;

            default:
                break;
        }
    }

    /**
     * Thread safe!
     *
     * @param batchHandle
     * @param sender
     * @throws Exception
     */
    public final void enqueueInput(ReteEntityBatchHandle batchHandle, Sender sender)
            throws Exception {
        switch (sender) {
            case CURRENT: {
                //Fast path is without volatile check.
                if (snapshotAssistantRef == null) {
                    super.enqueueInput(batchHandle);

                    return;
                }

                SnapshotAssistant assistant = snapshotAssistantRef.get();
                if (assistant == null) {
                    //No other thread mutates this field except for the "CURRENT" sender.
                    snapshotAssistantRef = null;

                    /*
                    So, the assistant has completed. The next time we won't take this path. We'll
                    take the fast path instead.
                    */
                    super.enqueueInput(batchHandle);
                } else {
                    accumulateCurrent(batchHandle);
                }

                return;
            }

            case SNAPSHOT:
                super.enqueueInput(batchHandle);

                return;

            default:
                break;
        }
    }

    private void accumulateCurrent(ReteEntityHandle handle) throws Exception {
        if (accumulatedItemsDuringSSA != null) {
            accumulatedItemsDuringSSA.offer(handle);

            lastAccumulatedItemFromSSA = handle;
        } else {
            super.enqueueInput(handle);
        }
    }

    private void accumulateCurrent(ReteEntityBatchHandle batchHandle) throws Exception {
        if (accumulatedItemsDuringSSA != null) {
            accumulatedItemsDuringSSA.offer(batchHandle);

            lastAccumulatedItemFromSSA = batchHandle;
        } else {
            super.enqueueInput(batchHandle);
        }
    }

    //-----------

    @Override
    public void pause() {
        super.pause();

        cacheScout.stopWarming(this);
    }

    @Override
    public void resume() {
        super.resume();

        cacheScout.startWarming(this);
    }

    //-----------

    @Override
    protected void handleQueuedInput(GenericReteEntityHandle input) throws Exception {
        if (isAggressivePrefetchSupported) {
            GenericReteEntityHandle nextHandle = queuedInput.peek();

            if (nextHandle != null && nextHandle.isWarm() == false) {
                cacheScout.prefetchNow(nextHandle);
            }
        }

        //------------

        if (input instanceof ReteEntityHandle) {
            sendToSources((ReteEntityHandle) input);
        } else {
            sendToSources((ReteEntityBatchHandle) input);
        }
    }

    /**
     * @param reteId
     * @param consumer
     * @param tupleInfo
     * @param optionalTupleId
     * @param sharedPointer
     * @param useFilter
     * @return
     * @see #makeAndReturnEntityIfNotFiltered(Long, com.tibco.cep.query.stream.impl.rete.ReteEntitySource,
     *      com.tibco.cep.query.stream.impl.rete.ReteEntityInfo, com.tibco.cep.query.stream.impl.rete.ReteEntityId,
     *      com.tibco.cep.query.stream.cache.SharedObjectSource, com.tibco.cep.query.stream.cache.SharedPointer,
     *      boolean)
     */
    private ReteEntity createAndReturnEntityIfNotFiltered(Long reteId, ReteEntitySource consumer,
                                                          ReteEntityInfo tupleInfo,
                                                          ReteEntityId optionalTupleId,
                                                          SharedPointer sharedPointer,
                                                          boolean useFilter) {
        return makeAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo, optionalTupleId, null,
                sharedPointer, useFilter);
    }

    /**
     * @param reteId
     * @param consumer
     * @param tupleInfo
     * @param optionalTupleId
     * @param objectSource
     * @param useFilter
     * @return
     * @see #makeAndReturnEntityIfNotFiltered(Long, com.tibco.cep.query.stream.impl.rete.ReteEntitySource,
     *      com.tibco.cep.query.stream.impl.rete.ReteEntityInfo, com.tibco.cep.query.stream.impl.rete.ReteEntityId,
     *      com.tibco.cep.query.stream.cache.SharedObjectSource, com.tibco.cep.query.stream.cache.SharedPointer,
     *      boolean)
     */
    private ReteEntity createAndReturnEntityIfNotFiltered(Long reteId, ReteEntitySource consumer,
                                                          ReteEntityInfo tupleInfo,
                                                          ReteEntityId optionalTupleId,
                                                          SharedObjectSource objectSource,
                                                          boolean useFilter) {
        return makeAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo, optionalTupleId,
                objectSource, null, useFilter);
    }

    public LinkedBlockingQueue<GenericReteEntityHandle> getAccumulatedItemsDuringSSA() {
        return accumulatedItemsDuringSSA;
    }

    /**
     * @param reteId
     * @param consumer
     * @param tupleInfo
     * @param tupleId
     * @param objectSource  <code>null</code> if "sharedObject" is present.
     * @param sharedPointer <code>null</code> if "objectSource" is present. If not
     *                      <code>null</code>, then the ReteEntity that gets created will use this
     *                      reference.
     * @param useFilter
     * @return <code>null</code> if the consumer had a filter and the new entity did not pass.
     */
    private ReteEntity makeAndReturnEntityIfNotFiltered(Long reteId, ReteEntitySource consumer,
                                                        ReteEntityInfo tupleInfo,
                                                        ReteEntityId tupleId,
                                                        SharedObjectSource objectSource,
                                                        SharedPointer sharedPointer,
                                                        boolean useFilter) {
        ReteEntity reteEntity = tupleInfo.createTuple(tupleId);
        reteEntity.setReteId(reteId);

        SharedPointer pointer = sharedPointer;
        if (pointer == null) {
            pointer = new SharedPointerImpl(reteId, objectSource);
        }

        reteEntity.setPointer(pointer);
        reteEntity.setReteId(reteId);

        //------------

        if (useFilter) {
            ReteEntityFilter filter = consumer.getReteEntityFilter();
            if (filter != null && filter.allow(context.getGlobalContext(),
                    context.getQueryContext(), reteEntity) == false) {
                reteEntity.decrementRefCount();

                //Did not pass the filter.
                return null;
            }
        }

        //------------

        // Store only if deletes/mods are expected.
        if (consumer instanceof WithdrawableSource) {
            reteIdsFromWithdrawableSources.add(reteId);
        }

        return reteEntity;
    }

    /**
     * Lock should be acquired first!
     *
     * @param batchHandle
     * @throws Exception
     */
    private void sendToSources(ReteEntityBatchHandle batchHandle) throws Exception {
        Class clazz = batchHandle.getReteClass();
        ReteEntitySource[] consumers = reteEntityClassAndListeners.get(clazz);

        switch (batchHandle.getType()) {
            case NEW:
                batchAdd(batchHandle, consumers);

                /*
                Ok, now we've processed the last handle that was accumulated by the SSA. So, far
                we were filtering the duplicate events that might've been sent by the Cache while
                 SSA was still running. After this, we don't have to anymore.
                */
                if (lastAccumulatedItemFromSSA == batchHandle) {
                    idsOfNewItemsReceivedFromSSA.clear();
                    idsOfNewItemsReceivedFromSSA = null;

                    lastAccumulatedItemFromSSA = null;
                }

                break;

            case MODIFICATION: {
                Long[] reteIds = batchHandle.getReteIds();
                for (Long reteId : reteIds) {
                    modify(consumers, clazz, reteId);
                }

                break;
            }

            case DELETION:
                batchDelete(batchHandle, consumers);
                break;
        }
    }

    private void batchAdd(ReteEntityBatchHandle batchHandle, ReteEntitySource[] consumers)
            throws Exception {
        final Class klass = batchHandle.getReteClass();

        final Long[] reteIds = batchHandle.getReteIds();
        final SharedPointer[] pointers = batchHandle.getSharedPointers();
        final int idsOrPointersLength = (reteIds == null) ? pointers.length : reteIds.length;

        final SharedObjectSource objectSource = cachedSources.get(klass);
        final Context cachedContext = context;
        final AppendOnlyQueue<ReteEntity> additions = batchOpsContainer;
        final CustomHashSet<Long> cachedSSAEvents = idsOfNewItemsReceivedFromSSA;
        final boolean filtersEnabled = reteEntityFiltersEnabled;

        additions.clear();

        short c = 0;
        for (ReteEntitySource consumer : consumers) {
            ReteEntityInfo tupleInfo = consumer.getOutputInfo();

            for (int r = 0; r < idsOrPointersLength; r++) {
                ReteEntity reteEntity = null;

                if (reteIds == null) {
                    SharedPointer pointer = pointers[r];
                    Long reteId = (Long) pointer.getKey();

                    if (cachedSSAEvents != null && cachedSSAEvents.add(reteId) == false) {
                        /*
                        This is a duplicate event received from the CacheListener while the SSA was still
                        running.
                        */
                        continue;
                    }

                    reteEntity = createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                            new ReteEntityId(c, reteId), pointer, filtersEnabled);
                } else {
                    Long reteId = reteIds[r];

                    if (cachedSSAEvents != null && cachedSSAEvents.add(reteId) == false) {
                        /*
                        This is a duplicate event received from the CacheListener while the SSA was still
                        running.
                        */
                        continue;
                    }

                    reteEntity =
                            createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                                    new ReteEntityId(c, reteId), objectSource, filtersEnabled);
                }


                if (reteEntity != null) {
                    additions.add(reteEntity);
                }
            }

            if (additions.size() > 0) {
                consumer.sendNewTuples(cachedContext, additions);
                additions.clear();
            }

            c++;
        }
    }

    private void batchDelete(ReteEntityBatchHandle batchHandle, ReteEntitySource[] consumers)
            throws Exception {
        final Long[] reteIds = batchHandle.getReteIds();
        SharedObjectSource objectSource = cachedSources.get(batchHandle.getReteClass());
        final Context cachedContext = context;
        final AppendOnlyQueue<ReteEntity> deletes = batchOpsContainer;

        deletes.clear();

        //---------

        final CustomHashSet<Long> cachedReteIdsFromWithdrawableSources =
                reteIdsFromWithdrawableSources;
        final AppendOnlyQueue<Long> cachedReteIdsSeenBefore = reteIdsSeenBefore;
        final ReusableIterator<Long> cachedReteIdsSeenBeforeIter = reteIdsSeenBeforeIter;

        cachedReteIdsSeenBefore.clear();
        for (Long reteId : reteIds) {
            if (cachedReteIdsFromWithdrawableSources.remove(reteId)) {
                cachedReteIdsSeenBefore.add(reteId);
            }
        }

        //---------

        short c = 0;
        for (ReteEntitySource consumer : consumers) {
            if (consumer instanceof WithdrawableSource == false) {
                continue;
            }

            for (cachedReteIdsSeenBeforeIter.reset(); cachedReteIdsSeenBeforeIter.hasNext(); ) {
                Long reteId = cachedReteIdsSeenBeforeIter.next();

                ReteEntityInfo tupleInfo = consumer.getOutputInfo();
                ReteEntity oldReteEntity =
                        createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                                new ReteEntityId(c, reteId), objectSource, false);

                deletes.add(oldReteEntity);
            }

            if (deletes.isEmpty() == false) {
                ((WithdrawableSource) consumer).sendDeadTuples(cachedContext, deletes);

                deletes.clear();
            }

            c++;
        }

        cachedReteIdsSeenBefore.clear();
    }

    /**
     * Lock should be acquired first!
     *
     * @param handle
     * @throws Exception
     */
    private void sendToSources(ReteEntityHandle handle) throws Exception {
        Long reteId = handle.getReteId();
        ReteEntityHandleType type = handle.getType();
        Class clazz = handle.getReteClass();

        ReteEntitySource[] consumers = reteEntityClassAndListeners.get(clazz);

        switch (type) {
            case NEW:
                SharedPointer sp = handle.getSharedPointer();
                add(consumers, sp, reteId);

                /*
                Ok, now we've processed the last handle that was accumulated by the SSA. So, far
                we were filtering the duplicate events that might've been sent by the Cache while
                 SSA was still running. After this, we don't have to anymore.
                */
                if (lastAccumulatedItemFromSSA == handle) {
                    idsOfNewItemsReceivedFromSSA.clear();
                    idsOfNewItemsReceivedFromSSA = null;

                    lastAccumulatedItemFromSSA = null;
                }

                break;

            case MODIFICATION:
                modify(consumers, clazz, reteId);
                break;

            case DELETION:
                delete(consumers, clazz, reteId);
                break;
        }
    }

    private void add(ReteEntitySource[] consumers, SharedPointer sharedPointer, Long reteId)
            throws Exception {
        final Context ctx = context;

        if (idsOfNewItemsReceivedFromSSA != null &&
                idsOfNewItemsReceivedFromSSA.add(reteId) == false) {
            /*
            This is a duplicate event received from the CacheListener while the SSA was still
            running.
            */
            return;
        }

        final boolean filtersEnabled = reteEntityFiltersEnabled;

        short c = 0;
        for (ReteEntitySource consumer : consumers) {
            ReteEntityInfo tupleInfo = consumer.getOutputInfo();
            ReteEntity reteEntity =
                    createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                            new ReteEntityId(c, reteId), sharedPointer, filtersEnabled);

            if (reteEntity != null) {
                consumer.sendNewTuple(ctx, reteEntity);
            }

            c++;
        }
    }

    private void modify(ReteEntitySource[] consumers, Class clazz, Long reteId) throws Exception {
        SharedObjectSource objectSource = cachedSources.get(clazz);

        final Context ctx = context;

        //---------

        boolean seenBefore = false;
        if (reteIdsFromWithdrawableSources.remove(reteId)) {
            seenBefore = true;
        }

        //---------

        final boolean filtersEnabled = reteEntityFiltersEnabled;
        final DefaultGlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();

        short c = 0;
        for (ReteEntitySource consumer : consumers) {
            boolean withdrawable = (consumer instanceof WithdrawableSource);

            ReteEntityInfo tupleInfo = consumer.getOutputInfo();

            /*
             * We've never received this event before. We are receiving the
             * modification/deletion now. So, just treat this as a new
             * operation.
             */
            if (withdrawable == false || seenBefore == false) {
                ReteEntity newReteEntity =
                        createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                                new ReteEntityId(c, reteId), objectSource, filtersEnabled);

                if (newReteEntity != null) {
                    consumer.sendNewTuple(ctx, newReteEntity);
                }
            } else {
                ReteEntity tempEntity =
                        createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                                new ReteEntityId(c, reteId), objectSource,
                                /*
                                Do not use the filter. Because we need an entity at least for the
                                Delete operation. So, do the filtering outside.
                                */
                                false);

                ReteEntity newReteEntity = tempEntity;
                ReteEntity oldReteEntity = tempEntity;

                ReteEntityFilter filter = consumer.getReteEntityFilter();
                if (filtersEnabled && filter != null &&
                        filter.allow(gc, qc, tempEntity) == false) {
                    /*
                    Ok, now the New version cannot be sent. But the old one has to be sent
                    anyway - as a Delete.
                    */

                    ((WithdrawableSource) consumer).sendDeadTuple(ctx, oldReteEntity);
                } else {
                    ((WithdrawableSource) consumer)
                            .sendModifiedTuple(ctx, oldReteEntity, newReteEntity);
                }
            }

            c++;
        }
    }

    private void delete(ReteEntitySource[] consumers, Class clazz, Long reteId) throws Exception {
        final Context ctx = context;
        SharedObjectSource objectSource = cachedSources.get(clazz);

        //---------

        boolean seenBefore = false;
        if (reteIdsFromWithdrawableSources.remove(reteId)) {
            seenBefore = true;
        }

        //---------

        short c = 0;
        for (ReteEntitySource consumer : consumers) {
            if (consumer instanceof WithdrawableSource == false) {
                continue;
            }

            // We've received this event before.
            if (seenBefore) {
                ReteEntityInfo tupleInfo = consumer.getOutputInfo();
                ReteEntity oldReteEntity =
                        createAndReturnEntityIfNotFiltered(reteId, consumer, tupleInfo,
                                new ReteEntityId(c, reteId),
                                objectSource, /*Do not use the filter.*/ false);

                ((WithdrawableSource) consumer).sendDeadTuple(ctx, oldReteEntity);
            }

            c++;
        }
    }

    //----------

    protected static class AsyncListenerImpl implements AsyncProcessListener {
        protected final RuleSession session;

        public AsyncListenerImpl(RuleSession session) {
            this.session = session;
        }

        public void beforeStart() {
            if (session != null) {
                RuleServiceProviderImpl provider =
                        (RuleServiceProviderImpl) session.getRuleServiceProvider();

                RuleSessionManagerImpl sessionManager =
                        (RuleSessionManagerImpl) provider.getRuleSessionManager();

                sessionManager.setCurrentRuleSession(session);
            }
        }

        public void afterEnd() {
            if (session != null) {
                RuleServiceProviderImpl provider =
                        (RuleServiceProviderImpl) session.getRuleServiceProvider();

                RuleSessionManagerImpl sessionManager =
                        (RuleSessionManagerImpl) provider.getRuleSessionManager();

                sessionManager.unsetCurrentRuleSession();
            }
        }
    }
}
