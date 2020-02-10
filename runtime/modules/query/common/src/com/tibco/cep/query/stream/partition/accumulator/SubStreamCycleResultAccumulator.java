package com.tibco.cep.query.stream.partition.accumulator;

import java.util.Collection;

import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 23, 2008 Time: 6:33:59 PM
*/
public interface SubStreamCycleResultAccumulator {
    /**
     * Invoked by the {@link com.tibco.cep.query.stream.core.SubStreamOwner} at the end of the cycle
     * ({@link com.tibco.cep.query.stream.core.SubStream#cycleEnd(com.tibco.cep.query.stream.context.Context)})
     * of all the substreams to collect the results.
     *
     * @param holder The invoker invokes {@link com.tibco.cep.query.stream.partition.accumulator.SubStreamCycleResultAccumulator.ResultHolder#clear()}
     *               after this method.
     */
    public void fillWithAccumulatedResults(ResultHolder holder);

    //------------

    public static class ResultHolder {
        protected Collection<? extends Tuple> additions;

        protected Collection<? extends Tuple> deletes;

        public Collection<? extends Tuple> getAdditions() {
            return additions;
        }

        /**
         * @param additions <code>null</code> or non-empty collection.
         */
        public void setAdditions(Collection<? extends Tuple> additions) {
            this.additions = additions;
        }

        public Collection<? extends Tuple> getDeletes() {
            return deletes;
        }

        /**
         * @param deletes <code>null</code> or non-empty collection.
         */
        public void setDeletes(Collection<? extends Tuple> deletes) {
            this.deletes = deletes;
        }

        /**
         * Clears the collections ({@link java.util.Collection#clear()}) that were set using {@link
         * #setAdditions(java.util.Collection)} and {@link #setDeletes(java.util.Collection)}.
         */
        public void clear() {
            if (additions != null) {
                additions.clear();

                additions = null;
            }

            if (deletes != null) {
                deletes.clear();

                deletes = null;
            }
        }
    }
}
