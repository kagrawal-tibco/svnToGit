package com.tibco.cep.query.stream.join;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.SimplePool;

/*
 * Author: Ashwin Jayaprakash Date: Jan 4, 2008 Time: 5:07:00 PM
 */

public class JoinedStreamTupleTracker {
    /**
     * Holds either a Set or a single {@link ResultHolder}.
     */
    protected HashMap<Number, /* HashSet<ResultHolder> */Object> tupleIdToHolders;

    private AppendOnlyQueue<JoinedTuple> allPreviousJoinTuples;

    private SimplePool<ResultHolder> pooledHolders;

    public JoinedStreamTupleTracker() {
        this.tupleIdToHolders = new HashMap<Number, /* HashSet<ResultHolder>*/ Object>();
        this.pooledHolders = new SimplePool<ResultHolder>(512);
    }

    /**
     * @param comboTuple
     */
    public void recordJoin(JoinedTuple comboTuple) {
        ResultHolder holder = pooledHolders.fetch();
        if (holder == null) {
            holder = new ResultHolder();
        }
        holder.init(comboTuple);

        Object[] individualTuples = holder.getIndividualTuples();
        for (int i = 0; i < individualTuples.length; i++) {
            Tuple tuple = (Tuple) individualTuples[i];
            Number id = tuple.getId();

            /* HashSet<ResultHolder> */
            Object setOrSingleResult = tupleIdToHolders.get(id);
            if (setOrSingleResult != null) {
                HashSet<ResultHolder> tempSet = null;

                if (setOrSingleResult instanceof ResultHolder) {
                    tempSet = new HashSet<ResultHolder>();
                    tupleIdToHolders.put(id, tempSet);

                    // Add the original one.
                    tempSet.add((ResultHolder) setOrSingleResult);
                }
                else {
                    tempSet = (HashSet<ResultHolder>) setOrSingleResult;
                }

                // Add the new one.
                tempSet.add(holder);
            }
            else {
                tupleIdToHolders.put(id, holder);
            }
        }
    }

    /**
     * @param context
     * @param individualTuple
     * @return <code>null</code> or a list of combo-tuples that were recorded by {@link
     *         #recordJoin(JoinedTuple)}.
     */
    public Collection<? extends JoinedTuple> disconnectJoin(Context context,
                                                            Tuple individualTuple) {
        Collection<JoinedTuple> previousJoinTuples = null;

        if (allPreviousJoinTuples != null) {
            allPreviousJoinTuples.clear();
        }
        else {
            allPreviousJoinTuples = new AppendOnlyQueue<JoinedTuple>(context.getQueryContext()
                    .getArrayPool());
        }

        /* HashSet<ResultHolder> */
        Object setOrSingleObject = tupleIdToHolders
                .remove(individualTuple.getId());
        if (setOrSingleObject != null) {
            previousJoinTuples = allPreviousJoinTuples;

            if (setOrSingleObject instanceof HashSet) {
                for (ResultHolder holder : (HashSet<ResultHolder>) setOrSingleObject) {
                    notifyOtherMembers(individualTuple, holder);
                    previousJoinTuples.add(holder.getComboTuple());

                    holder.reset();
                    pooledHolders.returnOrAdd(holder);
                }
            }
            else {
                ResultHolder holder = (ResultHolder) setOrSingleObject;

                notifyOtherMembers(individualTuple, holder);
                previousJoinTuples.add(holder.getComboTuple());

                holder.reset();
                pooledHolders.returnOrAdd(holder);
            }
        }

        return previousJoinTuples;
    }

    private void notifyOtherMembers(Tuple individualTuple, ResultHolder holder) {
        Object[] individualTuples = holder.getIndividualTuples();

        for (int i = 0; i < individualTuples.length; i++) {
            Tuple otherMemberTuple = (Tuple) individualTuples[i];

            // Just disconnect the other members.
            if (otherMemberTuple != individualTuple) {
                /* HashSet<ResultHolder> */
                Object memberOf = tupleIdToHolders
                        .get(otherMemberTuple.getId());

                /*
                 * Can be null for Self-Joins, when one reference has already
                 * removed itself from the Set.
                 */
                if (memberOf != null) {
                    if (memberOf instanceof HashSet) {
                        HashSet set = (HashSet) memberOf;

                        /*
                         * Other member has many memberships. Among which, this
                         * is one.
                         */
                        set.remove(holder);

                        if (set.isEmpty()) {
                            tupleIdToHolders.remove(otherMemberTuple.getId());
                        }
                    }
                    else {
                        tupleIdToHolders.remove(otherMemberTuple.getId());
                    }
                }
            }
        }
    }

    public void discard() {
        tupleIdToHolders.clear();
        pooledHolders.discard();

        if (allPreviousJoinTuples != null) {
            allPreviousJoinTuples.clear();
        }
        allPreviousJoinTuples = null;
    }

    /**
     * 2 combo-tuples are treated equal if their group-hashes are the same, irrespective of whether
     * their constituent tuples have the same contents or not.
     */
    public static class ResultHolder {
        protected JoinedTuple comboTuple;

        protected int groupHash;

        public void init(JoinedTuple comboTuple) {
            this.comboTuple = comboTuple;

            Object[] tuples = comboTuple.getRawColumns();
            int x = 0;
            for (Object tuple : tuples) {
                x = (x * 37) + tuple.hashCode();
            }
            this.groupHash = x;
        }

        public void reset() {
            comboTuple = null;
            groupHash = 0;
        }

        /**
         * @return array, where each element is a Tuple.
         */
        public Object[] getIndividualTuples() {
            return comboTuple.getRawColumns();
        }

        public JoinedTuple getComboTuple() {
            return comboTuple;
        }

        public long getGroupHash() {
            return groupHash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            final ResultHolder other = (ResultHolder) obj;
            if (groupHash != other.groupHash) {
                return false;
            }

            return true;
        }

        @Override
        public String toString() {
            Object[] tuples = getIndividualTuples();
            return Arrays.asList(tuples).toString();
        }
    }
}
