package com.tibco.cep.query.stream.context;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.monitor.QueryConfig;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.util.ArrayPool;
import com.tibco.cep.query.stream.util.LazyContainerTreeMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 11:48:45 AM
 */

/**
 * <b>Not</b> Thread-safe!
 */
public final class DefaultQueryContext implements QueryContext {
    protected long currentCycleTimestamp;

    /*
     * These 3 schedules are closely related. The Streams have to add and remove
     * themselves from these lists on their own.
     */

    protected final LinkedHashSet<Stream> currentCycleSchedule;

    protected final LinkedHashSet<Stream> nextCycleSchedule;

    protected final LazyContainerTreeMap<Long, Stream, LinkedHashSet<Stream>> timeScheduledStreams;

    protected final QueryWatcher queryWatcher;

    protected final QueryConfig queryConfig;

    protected final IdGenerator idGenerator;

    protected final ArrayPool arrayPool;

    protected final String queryName;

    protected final String regionName;

    protected final HashMap<String, Object> genericStore;

    /**
     * @param regionName
     * @param queryName
     */
    public DefaultQueryContext(String regionName, String queryName) {
        this(regionName, queryName, new HashMap<String, Object>());
    }

    /**
     * @param regionName
     * @param queryName
     * @param externalData <p> Serves as a container for all the "parameterized" information - i.e
     *                     Bind variables used in Expressions. Use names that are unlikely to clash
     *                     with other keys. These key-values will make it into the {@link
     *                     #genericStore}. </p> <p> Can be <code>null</code>. </p>
     */
    public DefaultQueryContext(String regionName, String queryName, Map<String, Object> externalData) {
        this.regionName = regionName;
        this.queryName = queryName;
        this.queryWatcher = new QueryWatcher();
        this.queryConfig = new QueryConfig();

        this.genericStore = externalData == null ? new HashMap<String, Object>()
                : new HashMap<String, Object>(externalData);
        this.currentCycleSchedule = new LinkedHashSet<Stream>();
        this.nextCycleSchedule = new LinkedHashSet<Stream>();

        LazyContainerTreeMap.ValueCollectionCreator<Stream, LinkedHashSet<Stream>> creator =
                new LazyContainerTreeMap.ValueCollectionCreator<Stream, LinkedHashSet<Stream>>() {
                    public LinkedHashSet<Stream> createContainer() {
                        return new LinkedHashSet<Stream>();
                    }
                };
        this.timeScheduledStreams =
                new LazyContainerTreeMap<Long, Stream, LinkedHashSet<Stream>>(creator);

        this.arrayPool = new ArrayPool();

        this.idGenerator = new IdGenerator();
    }

    public String getRegionName() {
        return regionName;
    }

    public String getQueryName() {
        return queryName;
    }

    public Map<String, Object> getGenericStore() {
        return genericStore;
    }

    public QueryWatcher getQueryWatcher() {
        return queryWatcher;
    }

    public QueryConfig getQueryConfig() {
        return queryConfig;
    }

    public void discard() {
        currentCycleSchedule.clear();
        nextCycleSchedule.clear();
        timeScheduledStreams.clear();

        arrayPool.discard();

        genericStore.clear();

        queryWatcher.discard();

        idGenerator.discard();
    }

    // -----------

    public LinkedHashSet<Stream> getCurrentCycleSchedule() {
        return currentCycleSchedule;
    }

    public LinkedHashSet<Stream> getNextCycleSchedule() {
        return nextCycleSchedule;
    }

    public void transferNextToCurrentCycleSchedule() {
        currentCycleSchedule.addAll(nextCycleSchedule);
        nextCycleSchedule.clear();
    }

    public void clearCurrrentCycleSchedule() {
        currentCycleSchedule.clear();
    }

    public void clearNextCycleSchedule() {
        nextCycleSchedule.clear();
    }

    public void addStreamForNextCycle(Stream stream) {
        nextCycleSchedule.add(stream);
    }

    /**
     * Iterators must be wary of concurrent modifications!
     *
     * @param stream
     * @return <code>true</code> if the Stream was in the scheduled set.
     */
    public boolean removeIfStreamInCurrentCycle(Stream stream) {
        return currentCycleSchedule.remove(stream);
    }

    // -----------

    public LazyContainerTreeMap<Long, Stream, LinkedHashSet<Stream>> getTimeScheduledStreams() {
        return timeScheduledStreams;
    }

    /**
     * @param stream
     * @param timestamp Seconds resolution only.
     */
    public void scheduleStreamAt(Stream stream, Long timestamp) {
        timeScheduledStreams.put(timestamp, stream);
    }

    public void removeStreamScheduledAt(Stream stream, Long timestamp) {
        timeScheduledStreams.removeValueForKey(timestamp, stream);
    }

    // -----------

    public long getCurrentCycleTimestamp() {
        return currentCycleTimestamp;
    }

    public void setCurrentCycleTimestamp(long currentTimestamp) {
        this.currentCycleTimestamp = currentTimestamp;
    }

    // -----------

    public ArrayPool getArrayPool() {
        return arrayPool;
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }
}
