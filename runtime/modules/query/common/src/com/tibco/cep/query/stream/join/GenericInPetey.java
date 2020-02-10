package com.tibco.cep.query.stream.join;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;

/*
 * Author: Ashwin Jayaprakash Date: Oct 16, 2007 Time: 1:57:54 PM
 */

public abstract class GenericInPetey extends AbstractInPetey {
    protected final HashMap<Number, OuterTupleAndExtractedValue> outerTupleIdAndHolders;

    protected final HashMap<Object, OuterAndInnerTupleIds> extractedValueAndTupleIds;

    protected final HashMap<Number, Object> innerTupleIdAndExtractedValues;

    private Collection<Tuple> matchedOuterTuples;

    private Collection<Tuple> disconnectedOuterTuples;

    public GenericInPetey(InPeteyInfo inPeteInfo) {
        super(inPeteInfo);

        this.outerTupleIdAndHolders = new HashMap<Number, OuterTupleAndExtractedValue>();
        this.innerTupleIdAndExtractedValues = new HashMap<Number, Object>();
        this.extractedValueAndTupleIds = new HashMap<Object, OuterAndInnerTupleIds>();
    }

    public Map<Object, OuterAndInnerTupleIds> getExtractedValueAndTupleIds() {
        return extractedValueAndTupleIds;
    }

    public Map<Number, Object> getInnerTupleIdAndExtractedValues() {
        return innerTupleIdAndExtractedValues;
    }

    public Map<Number, OuterTupleAndExtractedValue> getOuterTupleIdAndHolders() {
        return outerTupleIdAndHolders;
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return <code>true</code> if this Tuple matched an inner Tuple.
     */
    @Override
    public Boolean addOuter(GlobalContext globalContext, DefaultQueryContext queryContext, String alias,
            Tuple tuple) {
        Object value = outerTupleValueExtractor.extract(globalContext, queryContext, tuple);
        outerTupleIdAndHolders.put(tuple.getId(), new OuterTupleAndExtractedValue(tuple, value));

        OuterAndInnerTupleIds ids = extractedValueAndTupleIds.get(value);
        if (ids == null) {
            ids = new OuterAndInnerTupleIds();
            extractedValueAndTupleIds.put(value, ids);
        }
        ids.addOuterTupleId(tuple.getId());

        return ids.hasInnerTupleIds();
    }

    /**
     * @param tuple
     * @return <code>null</code> or a Collection of outer Tuples that match
     *         this inner Tuple.
     */
    @Override
    public Collection<Tuple> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
            Tuple tuple) {
        Object value = innerTupleValueExtractor.extract(globalContext, queryContext, tuple);
        innerTupleIdAndExtractedValues.put(tuple.getId(), value);

        OuterAndInnerTupleIds ids = extractedValueAndTupleIds.get(value);
        if (ids == null) {
            ids = new OuterAndInnerTupleIds();
            extractedValueAndTupleIds.put(value, ids);
        }
        ids.addInnerTupleId(tuple.getId());

        if (matchedOuterTuples != null) {
            matchedOuterTuples.clear();
        }
        else {
            matchedOuterTuples = new AppendOnlyQueue<Tuple>(queryContext.getArrayPool());
        }

        Collection<Tuple> results = null;
        if (ids.getInnerTupleIds().size() == 1 && ids.hasOuterTupleIds()) {
            for (Number outerTupleId : ids.getOuterTupleIds()) {
                OuterTupleAndExtractedValue holder = outerTupleIdAndHolders.get(outerTupleId);

                if (results == null) {
                    results = matchedOuterTuples;
                }

                results.add(holder.getTuple());
            }
        }

        return results;
    }

    /**
     * @param tuple
     * @return <code>null</code> or a Collection of outer Tuples that were
     *         connected to this inner Tuple and do not have a match anymore.
     */
    @Override
    public Collection<Tuple> removeInner(GlobalContext globalContext, DefaultQueryContext queryContext,
            Tuple tuple) {
        Object oldValue = innerTupleIdAndExtractedValues.remove(tuple.getId());
        OuterAndInnerTupleIds ids = extractedValueAndTupleIds.get(oldValue);
        ids.removeInnerTupleId(tuple.getId());

        if (ids.hasInnerTupleIds() == false && ids.hasOuterTupleIds() == false) {
            // Clean up.
            extractedValueAndTupleIds.remove(oldValue);
        }

        if (disconnectedOuterTuples != null) {
            disconnectedOuterTuples.clear();
        }
        else {
            disconnectedOuterTuples = new AppendOnlyQueue<Tuple>(queryContext.getArrayPool());
        }

        Collection<Tuple> results = null;
        if (ids.hasInnerTupleIds() == false && ids.hasOuterTupleIds()) {
            for (Number outerTupleId : ids.getOuterTupleIds()) {
                OuterTupleAndExtractedValue holder = outerTupleIdAndHolders.get(outerTupleId);

                if (results == null) {
                    results = disconnectedOuterTuples;
                }

                results.add(holder.getTuple());
            }
        }

        return results;
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return <code>true</code> if this Tuple was matched with an inner
     *         Tuple.
     */
    @Override
    public Boolean removeOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
            String alias, Tuple tuple) {
        Number tupleId = tuple.getId();
        OuterTupleAndExtractedValue holder = outerTupleIdAndHolders.remove(tupleId);

        OuterAndInnerTupleIds ids = extractedValueAndTupleIds.get(holder.getExtractedValue());
        ids.removeOuterTupleId(tuple.getId());

        if (ids.hasInnerTupleIds() == false && ids.hasOuterTupleIds() == false) {
            // Clean up.
            extractedValueAndTupleIds.remove(holder.getExtractedValue());
        }

        return ids.hasInnerTupleIds();
    }

    @Override
    public void discard() {
        super.discard();

        outerTupleIdAndHolders.clear();
        extractedValueAndTupleIds.clear();
        innerTupleIdAndExtractedValues.clear();
    }

    // --------

    /**
     * <p>
     * Join node.
     * </p>
     * <p>
     * Each hashed value has a list of Producers that are Inner Tuples and
     * Consumers that are the Outer Tuples.
     * </p>
     */
    public static class OuterAndInnerTupleIds {
        // todo Change to single object pointer and then a list.
        protected CustomHashSet<Number> outerTupleIds;

        protected CustomHashSet<Number> innerTupleIds;

        public boolean hasInnerTupleIds() {
            return innerTupleIds != null && innerTupleIds.isEmpty() == false;
        }

        /**
         * @return Can be <code>null</code>
         */
        public Set<Number> getInnerTupleIds() {
            return innerTupleIds;
        }

        public void addInnerTupleId(Number innerTupleId) {
            if (innerTupleIds == null) {
                innerTupleIds = new CustomHashSet<Number>();
            }

            innerTupleIds.add(innerTupleId);
        }

        public void removeInnerTupleId(Number innerTupleId) {
            innerTupleIds.remove(innerTupleId);

            if (innerTupleIds.isEmpty()) {
                innerTupleIds = null;
            }
        }

        public boolean hasOuterTupleIds() {
            return outerTupleIds != null && outerTupleIds.isEmpty() == false;
        }

        /**
         * @return Can be <code>null</code>
         */
        public Set<Number> getOuterTupleIds() {
            return outerTupleIds;
        }

        public void addOuterTupleId(Number outerTupleId) {
            if (outerTupleIds == null) {
                outerTupleIds = new CustomHashSet<Number>();
            }

            outerTupleIds.add(outerTupleId);
        }

        public void removeOuterTupleId(Number outerTupleId) {
            outerTupleIds.remove(outerTupleId);

            if (outerTupleIds.isEmpty()) {
                outerTupleIds = null;
            }
        }
    }

    public static class OuterTupleAndExtractedValue {
        protected final Tuple tuple;

        protected final Object extractedValue;

        public OuterTupleAndExtractedValue(Tuple tuple, Object extractedValue) {
            this.tuple = tuple;
            this.extractedValue = extractedValue;
        }

        public Object getExtractedValue() {
            return extractedValue;
        }

        public Tuple getTuple() {
            return tuple;
        }
    }
}
