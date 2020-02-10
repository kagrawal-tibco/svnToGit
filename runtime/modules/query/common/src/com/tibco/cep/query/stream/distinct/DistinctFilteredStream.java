package com.tibco.cep.query.stream.distinct;

import java.util.Arrays;
import java.util.Collection;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ArrayPool;
import com.tibco.cep.query.stream.util.CustomHashSet;

/*
 * Author: Ashwin Jayaprakash Date: Jan 31, 2008 Time: 1:17:27 PM
 */

/**
 * <p/> Sends out only 1 distinct new Tuple per group. Suppresses the rest. </p> <p/> The same Tuple
 * can go through over and over, if it is distinct. If 10 tuples of the same group are sent
 * separately (different cycles), then they are all allowed through. This works only if the whole
 * set is sent in together. i.e - This stream does not remember what it saw in the previous cycles.
 * </p>
 */
public class DistinctFilteredStream extends AbstractStream {
    protected final int numColumns;

    protected final CustomHashSet<Number> tupleIdsThatWentThruAtleastOnce;

    protected CustomHashSet<Key> distinctKeysInCycle;

    protected AppendOnlyQueue<Tuple> newTupleCollection;

    protected AppendOnlyQueue<Tuple> deadTupleCollection;

    private Key reusableKey;

    /**
     * @param source Uses this stream's {@link com.tibco.cep.query.stream.core.AbstractStream#getOutputInfo()}.
     * @param id
     */
    public DistinctFilteredStream(Stream source, ResourceId id) {
        this(source, id, source.getOutputInfo());
    }

    /**
     * @param id
     * @param tupleInfo
     */
    public DistinctFilteredStream(ResourceId id, TupleInfo tupleInfo) {
        this(null, id, tupleInfo);
    }

    protected DistinctFilteredStream(Stream source, ResourceId id, TupleInfo outputInfo) {
        super(source, id, outputInfo);

        String[] aliases = outputInfo.getColumnAliases();
        this.numColumns = aliases.length;

        if (sourceProducesDStream) {
            this.tupleIdsThatWentThruAtleastOnce = new CustomHashSet<Number>();
        }
        else {
            this.tupleIdsThatWentThruAtleastOnce = null;
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        if (newTupleCollection == null) {
            distinctKeysInCycle = new CustomHashSet<Key>();

            ArrayPool arrayPool = context.getQueryContext().getArrayPool();
            newTupleCollection = new AppendOnlyQueue<Tuple>(arrayPool);
            deadTupleCollection = new AppendOnlyQueue<Tuple>(arrayPool);
        }

        final LocalContext lc = context.getLocalContext();
        final CustomHashSet<Number> cachedTupleIdsThatWentThruAtleastOnce =
                tupleIdsThatWentThruAtleastOnce;
        final CustomHashSet<Key> cachedDistinctKeys = distinctKeysInCycle;
        final AppendOnlyQueue<Tuple> cachedNewTupleCollection = newTupleCollection;
        final AppendOnlyQueue<Tuple> cachedDeadTupleCollection = deadTupleCollection;
        final int cachedNumColumns = numColumns;

        Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
        if (deadTuples != null) {
            /*
             * Send only those Tuples in the D-Stream that went at least once in
             * the I-Stream.
             */
            for (Tuple t : deadTuples) {
                if (cachedTupleIdsThatWentThruAtleastOnce.remove(t.getId())) {
                    cachedDeadTupleCollection.add(t);
                }
                else {
                    if (Flags.TRACK_TUPLE_REFS) {
                        // End of the road for this Tuple;
                        t.decrementRefCount();
                    }
                }
            }
        }

        Collection<? extends Tuple> newTuples = lc.getNewTuples();
        if (newTuples != null) {
            for (Tuple t : newTuples) {
                Key key = findHash(t, cachedNumColumns);

                // First tuple with that hash.
                if (cachedDistinctKeys.add(key)) {
                    if (cachedTupleIdsThatWentThruAtleastOnce != null) {
                        cachedTupleIdsThatWentThruAtleastOnce.add(t.getId());
                    }

                    cachedNewTupleCollection.add(t);

                    //We're using this ref. So, don't share it anymore.
                    reusableKey = null;
                }
                else {
                    key.reset();
                }
            }
        }

        if (cachedDeadTupleCollection.isEmpty() == false) {
            localContext.setDeadTuples(cachedDeadTupleCollection);
        }
        if (cachedNewTupleCollection.isEmpty() == false) {
            localContext.setNewTuples(cachedNewTupleCollection);
        }
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (newTupleCollection != null) {
            newTupleCollection.clear();
            deadTupleCollection.clear();

            distinctKeysInCycle.clear();
        }

        if (reusableKey != null) {
            reusableKey.reset();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (tupleIdsThatWentThruAtleastOnce != null) {
            tupleIdsThatWentThruAtleastOnce.clear();
        }

        if (newTupleCollection == null) {
            distinctKeysInCycle.clear();
            distinctKeysInCycle = null;

            newTupleCollection.clear();
            newTupleCollection = null;

            deadTupleCollection.clear();
            deadTupleCollection = null;

            reusableKey.reset();
            reusableKey = null;
        }
    }

    /**
     * @param tuple
     * @param colCount
     * @return {@link #reusableKey} If the key gets consumed, then  remember to set the reference to
     *         <code>null</code> so that it won't get reused again.
     */
    protected final Key findHash(Tuple tuple, int colCount) {
        int hash = 0;
        Object[] columns = new Object[colCount];
        for (int i = 0; i < colCount; i++) {
            Object columnValue = tuple.getColumn(i);

            hash = (hash * 37) + ((columnValue == null) ? 0 : columnValue.hashCode());

            columns[i] = columnValue;
        }

        if (reusableKey == null) {
            reusableKey = new Key(hash, columns);
        }
        else {
            reusableKey.reuse(hash, columns);
        }

        return reusableKey;
    }

    //------------

    protected static class Key {
        protected int hash;

        protected Object[] columns;

        public Key(int hash, Object[] columns) {
            doReuse(hash, columns);
        }

        private void doReuse(int hash, Object[] columns) {
            this.hash = hash;
            this.columns = columns;
        }

        protected void reuse(int hash, Object[] columns) {
            doReuse(hash, columns);
        }

        public void reset() {
            hash = 0;
            columns = null;
        }

        public int getHash() {
            return hash;
        }

        public Object[] getColumns() {
            return columns;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Key key = (Key) o;

            if (hash != key.hash) {
                return false;
            }

            return Arrays.equals(columns, key.columns);
        }

        public int hashCode() {
            return hash;
        }
    }
}
