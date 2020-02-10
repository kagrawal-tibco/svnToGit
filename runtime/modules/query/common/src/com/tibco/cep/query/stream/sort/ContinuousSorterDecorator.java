package com.tibco.cep.query.stream.sort;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.ReusableIterator;
import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Mar 20, 2008 Time: 12:00:29 PM
 */

/**
 * <p/> Used if the <code>limit: first m offset n</code> clause is specified. </p>
 */
public class ContinuousSorterDecorator extends ContinuousSorter {
    protected final CustomHashSet<Number> tupleIdsThatWentThruAtleastOnce;

    private AppendOnlyQueue<Tuple> resultCollection;

    private AppendOnlyQueue<Tuple> deadTupleCollection;

    private SingleElementCollection<Tuple> reusableSEC;

    private ReusableIterator<Tuple> reusableSECIter;

    public ContinuousSorterDecorator(SortInfo sortInfo, TupleValueExtractor[] extractors,
                                     List<Comparator<Object>> comparators,
                                     boolean sourceProducesDStream) {
        super(sortInfo, extractors, comparators, sourceProducesDStream);

        if (sourceProducesDStream) {
            this.tupleIdsThatWentThruAtleastOnce = new CustomHashSet<Number>();
        }
        else {
            this.tupleIdsThatWentThruAtleastOnce = null;
        }

        this.reusableSEC = new SingleElementCollection<Tuple>(null);
        this.reusableSECIter = this.reusableSEC.iterator();
    }

    @Override
    public void discard() {
        super.discard();

        if (tupleIdsThatWentThruAtleastOnce != null) {
            tupleIdsThatWentThruAtleastOnce.clear();
        }

        if (resultCollection != null) {
            resultCollection.clear();
            resultCollection = null;
        }

        if (deadTupleCollection != null) {
            deadTupleCollection.clear();
            deadTupleCollection = null;
        }
    }

    @Override
    public void endProcessing(Context context) {
        super.endProcessing(context);

        if (resultCollection != null) {
            resultCollection.clear();
        }

        if (deadTupleCollection != null) {
            deadTupleCollection.clear();
        }
    }

    /**
     * Forwards only the sorted Tuples based on the limit criteria.
     *
     * @param context
     * @return
     */
    @Override
    public CustomCollection<Tuple> getSortedLiveTuples(Context context) {
        if (resultCollection == null) {
            resultCollection = new AppendOnlyQueue<Tuple>(context.getQueryContext().getArrayPool());
        }

        CustomCollection<Tuple> results = null;

        if (topSortedTupleMap.size() > 0) {
            results = resultCollection;
            SortItemInfo[] itemInfos = sortInfo.getItems();

            limitRecursively(context, results, itemInfos, 0, topSortedTupleMap);
        }

        results = (results == null || results.isEmpty()) ? null : results;

        if (results != null && tupleIdsThatWentThruAtleastOnce != null) {
            for (Tuple t : results) {
                tupleIdsThatWentThruAtleastOnce.add(t.getId());
            }
        }

        return results;
    }

    protected void limitRecursively(Context context, Collection<Tuple> collectedResults,
                                    SortItemInfo[] itemInfos, int itemInfoPos,
                                    GenericHierarchicalSortedMap map) {

        // todo IF the top-x does not change, then don't send them again.

        final Iterator<Object> mapKeyIterator = map.keySet().iterator();
        final int x = itemInfos[itemInfoPos].getFirstX(context);
        final int offset = itemInfos[itemInfoPos].getFirstXOffset(context);
        final int total = x + offset;

        final CustomHashSet<Number> cachedTupleIdsThatWentThru = tupleIdsThatWentThruAtleastOnce;
        final SingleElementCollection<Tuple> cachedReusableSEC = reusableSEC;
        final ReusableIterator<Tuple> cachedReusableSECIter = reusableSECIter;

        int ctr = 0;
        while (ctr < total && mapKeyIterator.hasNext()) {
            Object key = mapKeyIterator.next();
            Object value = map.get(key);

            if (ctr >= offset) {
                if (value instanceof GenericHierarchicalSortedMap) {
                    limitRecursively(context, collectedResults, itemInfos, itemInfoPos + 1,
                            (GenericHierarchicalSortedMap) value);
                }
                //map is a leaf-map.
                else {
                    ReusableIterator<Tuple> valueIter = null;

                    if (value instanceof Tuple) {
                        cachedReusableSEC.setElement((Tuple) value);

                        cachedReusableSECIter.reset();
                        valueIter = cachedReusableSECIter;
                    }
                    else {
                        CustomCollection<Tuple> collection = (CustomCollection<Tuple>) value;
                        valueIter = collection.iterator();
                    }

                    while (valueIter.hasNext()) {
                        Tuple t = valueIter.next();

                        collectedResults.add(t);

                        if (cachedTupleIdsThatWentThru != null) {
                            cachedTupleIdsThatWentThru.add(t.getId());
                        }
                    }

                    if (value instanceof Tuple) {
                        cachedReusableSEC.clear();
                        cachedReusableSECIter.reset();
                    }
                    /*
                    * Delete the trailing Tuples. Do not delete the ones that
                    * are in [0 to offset+limit]. Only the ones that are after
                    * that and which never get sent to the next step get
                    * removed.
                    */
                    else if (cachedTupleIdsThatWentThru == null) {
                        while (valueIter.hasNext()) {
                            valueIter.next();
                            valueIter.remove();
                        }
                    }
                }
            }

            ctr++;
        }

        // Delete trailing Tuples that will never reach the next step.
        if (cachedTupleIdsThatWentThru == null) {
            while (mapKeyIterator.hasNext()) {
                Object key = mapKeyIterator.next();

                Object value = map.get(key);

                mapKeyIterator.remove();

                if (value instanceof GenericHierarchicalSortedMap) {
                    ((GenericHierarchicalSortedMap) value).discard();
                }
            }
        }
    }

    @Override
    public CustomCollection<? extends Tuple> handleDeletes(Context context) {
        CustomCollection<? extends Tuple> deadTuples = super.handleDeletes(context);

        if (deadTuples != null) {
            if (deadTupleCollection == null) {
                deadTupleCollection = new AppendOnlyQueue<Tuple>(context.getQueryContext()
                        .getArrayPool());
            }

            final CustomHashSet<Number> cachedTupleIdsThatWentThru =
                    tupleIdsThatWentThruAtleastOnce;
            final AppendOnlyQueue<Tuple> cachedDeadTupleCollection = deadTupleCollection;

            /*
            * Send only those Tuples in the D-Stream that went at least once in
            * the I-Stream.
            */
            for (Tuple t : deadTuples) {
                if (cachedTupleIdsThatWentThru.remove(t.getId())) {
                    cachedDeadTupleCollection.add(t);
                }
                else {
                    if (Flags.TRACK_TUPLE_REFS) {
                        // End of the road for this Tuple;
                        t.decrementRefCount();
                    }
                }
            }

            deadTuples = cachedDeadTupleCollection.isEmpty() ? null : cachedDeadTupleCollection;
        }

        return deadTuples;
    }
}
