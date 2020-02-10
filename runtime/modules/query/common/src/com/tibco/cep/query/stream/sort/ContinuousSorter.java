package com.tibco.cep.query.stream.sort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.FixedKeys;
import com.tibco.cep.query.stream.util.SimplePool;

/*
 * Author: Ashwin Jayaprakash Date: Mar 20, 2008 Time: 11:27:43 AM
 */

/**
 * <p/> Sends the <b>entire</b> sorted live-tuple list during every cycle - only if something
 * changes. Otherwise does not send anything. </p>
 */
public class ContinuousSorter {
    protected final GenericHierarchicalSortedMap topSortedTupleMap;

    /**
     * Left to right matches the top to bottom hierarchical-map levels.
     */
    protected final TupleValueExtractor[] extractors;

    private final Object[] extractedValuesHolder;

    private HierarchicalSortedLeafMap lowestLeafMap;

    protected final DualDirectionProxyComparator[] comparators;

    /**
     * Quick link to the leaf-map the Tuple is in, for fast deletes.
     */
    protected final HashMap<Number, RemovalEntry> idAndRemovalEntry;

    protected final SimplePool<RemovalEntry> pooledRemovalEntries;

    protected final SortInfo sortInfo;

    /**
     * @param sortInfo              Cannot be <code>null</code>.
     * @param extractors
     * @param comparators           Has to be the same length as "extractors".
     * @param sourceProducesDStream
     */
    public ContinuousSorter(SortInfo sortInfo, TupleValueExtractor[] extractors,
                            List<Comparator<Object>> comparators, boolean sourceProducesDStream) {
        this.sortInfo = sortInfo;
        this.extractors = extractors;

        this.comparators = new DualDirectionProxyComparator[comparators.size()];
        int x = 0;
        SortItemInfo[] sortItemInfos = sortInfo.getItems();
        for (Comparator<Object> comparator : comparators) {
            boolean asc = sortItemInfos[x].isAscending();
            this.comparators[x] = new DualDirectionProxyComparator(asc, comparator);

            x++;
        }

        this.extractedValuesHolder = new Object[x];

        DualDirectionProxyComparator firstComparator = this.comparators[0];

        if (x == 1) {
            this.topSortedTupleMap = new HierarchicalSortedLeafMap(firstComparator);
            this.lowestLeafMap = (HierarchicalSortedLeafMap) this.topSortedTupleMap;
        }
        else {
            this.topSortedTupleMap = new HierarchicalSortedMap(firstComparator);
        }

        if (sourceProducesDStream) {
            this.idAndRemovalEntry = new HashMap<Number, RemovalEntry>();
            this.pooledRemovalEntries = new SimplePool<RemovalEntry>(64);
        }
        else {
            this.idAndRemovalEntry = null;
            this.pooledRemovalEntries = null;
        }
    }

    public SortInfo getSortInfo() {
        return sortInfo;
    }

    public void discard() {
        topSortedTupleMap.discard();

        lowestLeafMap = null;

        if (pooledRemovalEntries != null) {
            pooledRemovalEntries.discard();
        }

        if (idAndRemovalEntry != null) {
            for (RemovalEntry removalEntry : idAndRemovalEntry.values()) {
                removalEntry.discard();
            }
            idAndRemovalEntry.clear();
        }
    }
    // ---------

    /**
     * By default, it collects the entire sorted data.
     *
     * @param context
     * @return <code>null</code> or a list of sorted Tuples.
     */
    public CustomCollection<? extends Tuple> getSortedLiveTuples(Context context) {
        return goToStartAndCollectAll(context);
    }

    /**
     * @param context
     * @return <code>null</code> or a list of <b>all</b> the sorted Tuples.
     */
    protected CustomCollection<Tuple> goToStartAndCollectAll(Context context) {
        if (lowestLeafMap != null) {
            return lowestLeafMap.getCompleteValueChain();
        }

        return null;
    }

    /**
     * Invoked after {@link #getSortedLiveTuples(com.tibco.cep.query.stream.context.Context)} .
     *
     * @param context
     */
    public void endProcessing(Context context) {
    }

    /**
     * @param context
     * @return <code>true</code> if something changed.
     */
    public boolean handleAdds(Context context) {
        CustomCollection<? extends Tuple> newTuples = context.getLocalContext().getNewTuples();
        if (newTuples == null) {
            return false;
        }

        final GlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();

        final TupleValueExtractor[] cachedExtractors = extractors;
        final HashMap<Number, RemovalEntry> cachedIdAndRemovalEntry = idAndRemovalEntry;
        final Object[] cachedExtractedValHolder = extractedValuesHolder;
        final DualDirectionProxyComparator[] cachedComparators = comparators;
        final GenericHierarchicalSortedMap cachedTopSortedTupleMap = topSortedTupleMap;
        final SimplePool<RemovalEntry> cachedPooledRemovalEntries = pooledRemovalEntries;

        for (Tuple tuple : newTuples) {
            final Number id = tuple.getId();

            GenericHierarchicalSortedMap currentLevelMap = cachedTopSortedTupleMap;

            for (int level = 0; level < cachedExtractors.length; level++) {
                Object key = cachedExtractors[level].extract(gc, qc, tuple);
                if (key == null) {
                    /*
                     * Leaving null as is causes several problems in the
                     * TreeMap.
                     */
                    key = FixedKeys.NULL;
                }

                cachedExtractedValHolder[level] = key;

                //-------------

                // Leaf level.
                if (level + 1 == cachedExtractors.length) {
                    HierarchicalSortedLeafMap hslm = (HierarchicalSortedLeafMap) currentLevelMap;
                    hslm.put(key, tuple);

                    if (cachedIdAndRemovalEntry != null) {
                        RemovalEntry removalEntry = cachedPooledRemovalEntries.fetch();
                        if (removalEntry == null) {
                            removalEntry = new RemovalEntry();
                        }
                        removalEntry.setKeyInLeafMap(key);
                        removalEntry.setLeafMap(hslm);

                        cachedIdAndRemovalEntry.put(id, removalEntry);
                    }

                    break;
                }

                Object nextLevelMap = currentLevelMap.get(key);
                if (nextLevelMap == null) {
                    //We are now just one level above the last level.
                    if (level + 2 == cachedExtractors.length) {
                        nextLevelMap = new HierarchicalSortedLeafMap(cachedComparators[level + 1]);

                        HierarchicalSortedLeafMap tmpLeafMap =
                                (HierarchicalSortedLeafMap) nextLevelMap;

                        if (!connectToLowLeaf(cachedExtractedValHolder, level, tmpLeafMap,
                                currentLevelMap)) {
                            connectToHighLeaf(cachedExtractedValHolder, level, tmpLeafMap,
                                    currentLevelMap);
                        }

                        //First leaf ever.
                        if (lowestLeafMap == null) {
                            lowestLeafMap = tmpLeafMap;
                        }
                        //Newest entry at the far left.
                        else if (tmpLeafMap.getPrevious() == null) {
                            lowestLeafMap = tmpLeafMap;
                        }
                    }
                    else {
                        nextLevelMap = new HierarchicalSortedMap(cachedComparators[level + 1]);
                    }

                    GenericHierarchicalSortedMap tmpMap =
                            (GenericHierarchicalSortedMap) nextLevelMap;
                    tmpMap.setParent(currentLevelMap);
                    tmpMap.setKeyInParent(key);

                    currentLevelMap.put(key, nextLevelMap);
                }

                currentLevelMap = (GenericHierarchicalSortedMap) nextLevelMap;
            }
        }

        //Clear.
        for (int i = 0; i < cachedExtractedValHolder.length; i++) {
            cachedExtractedValHolder[i] = null;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    protected boolean connectToLowLeaf(Object[] extractedValues, int level,
                                       HierarchicalSortedLeafMap newLeafMap,
                                       GenericHierarchicalSortedMap parentMap) {
        if (parentMap == null) {
            return false;
        }

        // ----------

        SortedMap<Object, Object> beforeMap = parentMap.headMap(extractedValues[level]);
        if (beforeMap.isEmpty()) {
            // Backtrack and check in other partitions.
            return connectToLowLeaf(extractedValues, level - 1, newLeafMap,
                    parentMap.getParent());
        }

        // ----------

        Object key = beforeMap.lastKey();
        Object value = beforeMap.get(key);
        // Still at the top of the partition.
        while (value instanceof HierarchicalSortedLeafMap == false) {
            key = ((HierarchicalSortedMap) value).lastKey();
            value = ((HierarchicalSortedMap) value).get(key);
        }

        HierarchicalSortedLeafMap previous = (HierarchicalSortedLeafMap) value;
        previous.linkAsNext(newLeafMap);

        return true;
    }

    @SuppressWarnings("unchecked")
    protected boolean connectToHighLeaf(Object[] extractedValues, int level,
                                        HierarchicalSortedLeafMap newLeafMap,
                                        GenericHierarchicalSortedMap parentMap) {
        if (parentMap == null) {
            return false;
        }

        // ----------

        SortedMap<Object, Object> fromMap = parentMap.tailMap(extractedValues[level]);
        if (fromMap.isEmpty() ||
                /*
                * tailMap(..) includes Key, so exclude it from SubMap.
                */
                (fromMap.size() == 1 && fromMap.firstKey().equals(extractedValues[level]))) {
            // Backtrack and check in other partitions.
            return connectToHighLeaf(extractedValues, level - 1, newLeafMap, parentMap
                    .getParent());
        }

        // ----------

        Object key = fromMap.firstKey();
        if (key.equals(extractedValues[level])) {
            Iterator<Object> iter = fromMap.keySet().iterator();
            iter.next();

            // Move to the next key, because of tailMap(..) behavior.
            key = iter.next();
        }

        Object value = fromMap.get(key);
        // Still at the top of the partition.
        while (value instanceof HierarchicalSortedLeafMap == false) {
            key = ((HierarchicalSortedMap) value).firstKey();
            value = ((HierarchicalSortedMap) value).get(key);
        }

        HierarchicalSortedLeafMap next = (HierarchicalSortedLeafMap) value;
        next.linkAsPrevious(newLeafMap);

        return true;
    }

    /**
     * Invoked only if Source produces a DStream.
     *
     * @param context
     * @return
     */
    public CustomCollection<? extends Tuple> handleDeletes(Context context) {
        CustomCollection<? extends Tuple> deadTuples = context.getLocalContext().getDeadTuples();
        if (deadTuples == null) {
            return null;
        }

        final HashMap<Number, RemovalEntry> cachedIdAndRemovalEntry = idAndRemovalEntry;
        final SimplePool<RemovalEntry> cachedPooledRemovalEntries = pooledRemovalEntries;
        final GenericHierarchicalSortedMap cachedTopSortedTupleMap = topSortedTupleMap;

        for (Tuple tuple : deadTuples) {
            final Number id = tuple.getId();

            RemovalEntry removalEntry = cachedIdAndRemovalEntry.remove(id);
            HierarchicalSortedLeafMap leafMap = removalEntry.leafMap;
            leafMap.removeTuple(removalEntry.keyInLeafMap, tuple);
            removalEntry.discard();
            cachedPooledRemovalEntries.returnOrAdd(removalEntry);

            // Clean up recursively.
            GenericHierarchicalSortedMap parentMap = null;
            if (leafMap.size() == 0 && (parentMap = leafMap.getParent()) != null &&
                    parentMap != cachedTopSortedTupleMap) {
                parentMap.remove(leafMap.getKeyInParent());

                //The leftmost leaf is being removed.
                if (lowestLeafMap == leafMap) {
                    lowestLeafMap = leafMap.getNext();
                }

                leafMap.discard();

                while (parentMap != cachedTopSortedTupleMap && parentMap.size() == 0) {
                    GenericHierarchicalSortedMap tmp = parentMap.getParent();
                    tmp.remove(parentMap.getKeyInParent());

                    parentMap.discard();

                    parentMap = tmp;
                }
            }
        }

        // The same source Tuples are the dead Tuples for this Stream.
        return deadTuples;
    }

    //------------

    protected static class RemovalEntry {
        protected Object keyInLeafMap;

        protected HierarchicalSortedLeafMap leafMap;

        public void setKeyInLeafMap(Object keyInLeafMap) {
            this.keyInLeafMap = keyInLeafMap;
        }

        public void setLeafMap(HierarchicalSortedLeafMap leafMap) {
            this.leafMap = leafMap;
        }

        public void discard() {
            keyInLeafMap = null;
            leafMap = null;
        }
    }
}
