package com.tibco.cep.query.stream.group;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.SimplePool;

/*
 * Author: Ashwin Jayaprakash Date: Oct 8, 2007 Time: 3:10:54 PM
 */

/**
 * Allows and groups only unique Tuples.
 */
public class GroupKeeper {
    /**
     * Can be <code>null</code>.
     */
    protected HashMap<Number, GroupKeyImpl> srcTupleIdAndGroupData;

    protected SessionChanges allChanges;

    /**
     * Same order as provided in the constructor.
     */
    protected final TupleValueExtractor[] cachedGroupColumnExtractors;

    /**
     * @see #findHash(com.tibco.cep.query.stream.context.DefaultGlobalContext ,
     *      com.tibco.cep.query.stream.context.DefaultQueryContext , com.tibco.cep.query.stream.tuple.Tuple)
     */
    private Object[] findHashRetValContainer;

    /**
     * @param aliasAndGroupColumnExtractors Send empty {@link LinkedHashMap} if there is no "group
     *                                      of columns". Key: Column aliases. The same order in
     *                                      which the results are to be produced.
     * @param receivesDeletes
     */
    public GroupKeeper(LinkedHashMap<String, TupleValueExtractor> aliasAndGroupColumnExtractors,
                       boolean receivesDeletes) {
        this.cachedGroupColumnExtractors =
                new TupleValueExtractor[aliasAndGroupColumnExtractors.size()];
        int i = 0;
        for (Entry<String, TupleValueExtractor> entry : aliasAndGroupColumnExtractors.entrySet()) {
            this.cachedGroupColumnExtractors[i++] = entry.getValue();
        }

        if (receivesDeletes) {
            this.srcTupleIdAndGroupData = new HashMap<Number, GroupKeyImpl>();
        }

        this.allChanges = new SessionChanges();

        this.findHashRetValContainer = new Object[2];
    }

    public int getGroupColumnsLength() {
        return cachedGroupColumnExtractors.length;
    }

    public void discard() {
        if (srcTupleIdAndGroupData != null) {
            srcTupleIdAndGroupData.clear();
            srcTupleIdAndGroupData = null;
        }

        allChanges.discard();
        allChanges = null;

        findHashRetValContainer = null;
    }

    // ---------

    public GroupKey createKey(int groupHash, Object[] groupColumns) {
        return new GroupKeyImpl(groupHash, groupColumns);
    }

    public Map<? extends GroupKey, ? extends GroupChanges> extractChanges(Context context) {
        allChanges.reset();

        handleDeletes(context);
        handleAdds(context);

        return allChanges.getGroups();
    }

    protected void handleAdds(Context context) {
        Collection<? extends Tuple> newTuples = context.getLocalContext().getNewTuples();
        if (newTuples == null) {
            return;
        }

        final DefaultGlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();
        final HashMap<Number, GroupKeyImpl> cachedSrcTupleIdAndGroupData = srcTupleIdAndGroupData;
        final SessionChanges cachedAllChanges = allChanges;

        for (Tuple tuple : newTuples) {
            Number id = tuple.getId();

            Object[] tmpArr = findHash(gc, qc, tuple);
            Integer hash = (Integer) tmpArr[0];
            Object[] groupColumns = (Object[]) tmpArr[1];
            tmpArr[0] = null;
            tmpArr[1] = null;

            GroupKeyImpl groupKey = new GroupKeyImpl(hash, groupColumns);

            if (cachedSrcTupleIdAndGroupData != null) {
                // Create reverse link for quick deletion.
                cachedSrcTupleIdAndGroupData.put(id, groupKey);
            }

            cachedAllChanges.recordAddition(context, groupKey, tuple);
        }
    }

    protected void handleDeletes(Context context) {
        Collection<? extends Tuple> deadTuples = context.getLocalContext().getDeadTuples();
        if (deadTuples == null) {
            return;
        }

        for (Tuple tuple : deadTuples) {
            Number tupleId = tuple.getId();

            GroupKeyImpl memberOf = srcTupleIdAndGroupData.remove(tupleId);

            allChanges.recordDeletion(context, memberOf, tuple);
        }
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return First element is the Hash as an Integer and the second is an Object[] of all the
     *         Group column values. Always uses the same instance {@link #findHashRetValContainer}.
     *         Make sure to clear the elements after reading them.
     */
    protected final Object[] findHash(DefaultGlobalContext globalContext,
                                      DefaultQueryContext queryContext,
                                      Tuple tuple) {
        int hash = 0;
        Object[] groupColumns = new Object[cachedGroupColumnExtractors.length];

        int i = 0;
        for (TupleValueExtractor extractor : cachedGroupColumnExtractors) {
            // Will not have any Shared Objects. All references are direct.
            groupColumns[i] = extractor.extract(globalContext, queryContext, tuple);

            hash = (hash * 37) + ((groupColumns[i] == null) ? 0 : groupColumns[i].hashCode());

            i++;
        }

        findHashRetValContainer[0] = hash;
        findHashRetValContainer[1] = groupColumns;

        return findHashRetValContainer;
    }

    // ----------

    protected static class GroupKeyImpl implements GroupKey {
        protected int groupHash;

        protected Object[] groupColumns;

        public GroupKeyImpl(int groupHash, Object[] groupColumns) {
            this.groupHash = groupHash;
            this.groupColumns = groupColumns;
        }

        public int getGroupHash() {
            return groupHash;
        }

        public Object[] getGroupColumns() {
            return groupColumns;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            GroupKeyImpl groupData = (GroupKeyImpl) o;

            if (groupHash != groupData.groupHash) {
                return false;
            }

            return Arrays.equals(groupColumns, groupData.groupColumns);
        }

        public int hashCode() {
            return groupHash;
        }
    }

    protected static class SessionChanges {
        protected final SimplePool<GroupChangesImpl> pool;

        protected final HashMap<GroupKeyImpl, GroupChangesImpl> groups;

        public SessionChanges() {
            this.pool = new SimplePool<GroupChangesImpl>(64);
            this.groups = new HashMap<GroupKeyImpl, GroupChangesImpl>();
        }

        public HashMap<GroupKeyImpl, GroupChangesImpl> getGroups() {
            return groups;
        }

        protected GroupChangesImpl findGroupAndStoreColumns(GroupKeyImpl groupData) {
            GroupChangesImpl groupChanges = groups.get(groupData);

            if (groupChanges == null) {
                groupChanges = pool.fetch();
                groupChanges = (groupChanges == null) ? new GroupChangesImpl() : groupChanges;

                groups.put(groupData, groupChanges);
            }

            return groupChanges;
        }

        protected void recordAddition(Context context, GroupKeyImpl groupData, Tuple tuple) {
            GroupChangesImpl groupChanges = findGroupAndStoreColumns(groupData);
            groupChanges.recordAddition(context, tuple);
        }

        protected void recordDeletion(Context context, GroupKeyImpl groupData, Tuple tuple) {
            GroupChangesImpl groupChanges = findGroupAndStoreColumns(groupData);
            groupChanges.recordDeletion(context, tuple);
        }

        protected void reset() {
            for (GroupChangesImpl groupChanges : groups.values()) {
                groupChanges.reset();
                pool.returnOrAdd(groupChanges);
            }

            groups.clear();
        }

        protected void discard() {
            reset();

            pool.discard();
        }
    }

    protected static class GroupChangesImpl implements GroupChanges {
        /**
         * Can be <code>null</code>.
         */
        protected AppendOnlyQueue<Tuple> additions;

        /**
         * Can be <code>null</code>.
         */
        protected AppendOnlyQueue<Tuple> deletions;

        protected void recordAddition(Context context, Tuple tuple) {
            if (additions == null) {
                additions = new AppendOnlyQueue<Tuple>(context.getQueryContext().getArrayPool());
            }

            additions.add(tuple);
        }

        protected void recordDeletion(Context context, Tuple tuple) {
            if (deletions == null) {
                deletions = new AppendOnlyQueue<Tuple>(context.getQueryContext().getArrayPool());
            }

            deletions.add(tuple);
        }

        public AppendOnlyQueue<Tuple> getAdditions() {
            return (additions != null && additions.isEmpty()) ? null : additions;
        }

        public AppendOnlyQueue<Tuple> getDeletions() {
            return (deletions != null && deletions.isEmpty()) ? null : deletions;
        }

        protected void reset() {
            if (additions != null) {
                additions.clear();
            }

            if (deletions != null) {
                deletions.clear();
            }
        }
    }
}
