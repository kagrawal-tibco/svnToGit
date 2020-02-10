package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream._join2_.api.Source;
import com.tibco.cep.query.stream._join2_.util.MultiValueBag;

import java.util.Collection;
import java.util.LinkedList;

/*
* Author: Ashwin Jayaprakash Date: Jun 3, 2009 Time: 5:13:43 PM
*/
public abstract class AbstractJoinResultTracker<K> extends AbstractJoinHandler {
    protected Evaluator<Object[], K> resultPrimaryKeyExtractor;

    protected MultiValueBag<Object, JoinResultInfo<K>>[] joinResultInfoOfSources;

    private LinkedList<K> deletedResultPrimaryKeysInBatch;

    @Override
    @SuppressWarnings({"unchecked"})
    public AbstractJoinResultTracker setJoinSources(Source[] joinSources) {
        super.setJoinSources(joinSources);
        joinResultInfoOfSources = new MultiValueBag[joinSources.length];

        for (int i = 0; i < joinSources.length; i++) {
            joinResultInfoOfSources[i] = new MultiValueBag<Object, JoinResultInfo<K>>();
        }

        return this;
    }

    public Evaluator<Object[], K> getResultPrimaryKeyExtractor() {
        return resultPrimaryKeyExtractor;
    }

    public AbstractJoinResultTracker setResultPrimaryKeyExtractor(
            Evaluator<Object[], K> resultPrimaryKeyExtractor) {
        this.resultPrimaryKeyExtractor = resultPrimaryKeyExtractor;

        return this;
    }

    //-------------

    public final void onJoin(Object[] joinMembers) {
        K resultPrimaryKey = resultPrimaryKeyExtractor.evaluate(joinMembers);

        Object[] primaryKeysOfJoinMembers = new Object[joinMembers.length];
        for (int i = 0; i < joinMembers.length; i++) {
            Object joinMember = joinMembers[i];
            primaryKeysOfJoinMembers[i] = primaryKeyExtractors[i].evaluate(joinMember);
        }

        JoinResultInfo<K> joinResultInfo =
                new JoinResultInfo<K>(resultPrimaryKey, primaryKeysOfJoinMembers);

        for (int i = 0; i < joinResultInfoOfSources.length; i++) {
            joinResultInfoOfSources[i].put(primaryKeysOfJoinMembers[i], joinResultInfo);
        }

        onJoinEpilogue(joinMembers, primaryKeysOfJoinMembers, resultPrimaryKey);
    }

    protected abstract void onJoinEpilogue(Object[] joinMembers, Object[] primaryKeysOfJoinMembers,
                                           K joinResultPrimaryKey);

    //-------------

    protected void sourceOnRemove(Source source, int sourcePosition, Object key, Object value) {
        Collection<JoinResultInfo<K>> joinResultInfos =
                joinResultInfoOfSources[sourcePosition].removeAll(key);
        if (joinResultInfos == null) {
            return;
        }

        //-------------

        deletedResultPrimaryKeysInBatch = new LinkedList<K>();

        for (JoinResultInfo<K> joinResultInfo : joinResultInfos) {
            Object[] memberPrimaryKeys = joinResultInfo.getPrimaryKeysOfMembers();

            for (int i = 0; i < joinResultInfoOfSources.length; i++) {
                joinResultInfoOfSources[i].remove(memberPrimaryKeys[i], joinResultInfo);
            }

            K resultPrimaryKey = joinResultInfo.getPrimaryKeyOfResult();

            deletedResultPrimaryKeysInBatch.add(resultPrimaryKey);
        }
    }

    protected final void sourceBatchEnd(Source source, int sourcePosition) {
        Collection<K> retVal = deletedResultPrimaryKeysInBatch;
        deletedResultPrimaryKeysInBatch = null;

        sourceBatchEndEpilogue(source, sourcePosition, retVal);
    }

    /**
     * @param source
     * @param sourcePosition
     * @param deletedResultPrimaryKeysInBatch
     *                       Can be <code>null</code>.
     */
    protected abstract void sourceBatchEndEpilogue(Source source, int sourcePosition,
                                                   Collection<K> deletedResultPrimaryKeysInBatch);

    //-------------

    protected static class JoinResultInfo<K> {
        protected K primaryKeyOfResult;

        protected Object[] primaryKeysOfMembers;

        public JoinResultInfo(K primaryKeyOfResult, Object[] primaryKeysOfMembers) {
            this.primaryKeyOfResult = primaryKeyOfResult;
            this.primaryKeysOfMembers = primaryKeysOfMembers;
        }

        public K getPrimaryKeyOfResult() {
            return primaryKeyOfResult;
        }

        public Object[] getPrimaryKeysOfMembers() {
            return primaryKeysOfMembers;
        }
    }
}
