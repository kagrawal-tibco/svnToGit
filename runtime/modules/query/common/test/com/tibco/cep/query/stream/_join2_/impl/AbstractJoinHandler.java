package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream._join2_.api.JoinHandler;
import com.tibco.cep.query.stream._join2_.api.Source;
import com.tibco.cep.query.stream._join2_.api.SourceChangeListener;

/*
* Author: Ashwin Jayaprakash Date: Jun 2, 2009 Time: 9:34:39 PM
*/
public abstract class AbstractJoinHandler implements JoinHandler {
    protected Source[] joinSources;

    protected Evaluator<Object, Object>[] primaryKeyExtractors;

    protected SourceChangeListener<Object, Object>[] sourceChangeListeners;

    public Source[] getJoinSources() {
        return joinSources;
    }

    /**
     * Creates the {@link SourceChangeListener}s which can later be retrieved using {@link
     * #getSourceChangeListeners()}. Also captures the {@link Evaluator primary-key-extractor}s from
     * each {@link Source}.
     *
     * @param joinSources
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public AbstractJoinHandler setJoinSources(Source[] joinSources) {
        this.joinSources = joinSources;

        this.primaryKeyExtractors = new Evaluator[joinSources.length];
        this.sourceChangeListeners = new SourceChangeListener[joinSources.length];

        for (int i = 0; i < joinSources.length; i++) {
            Source joinSource = joinSources[i];

            this.primaryKeyExtractors[i] = joinSource.getPrimaryKeyExtractor();
            this.sourceChangeListeners[i] = createSourceChangeListener(joinSource, i);
        }

        return this;
    }

    protected SourceChangeListener<Object, Object> createSourceChangeListener(Source joinSource,
                                                                              int joinSourcePosition) {
        return new ForwardingSourceChangeListener(joinSource, joinSourcePosition);
    }

    public Evaluator<Object, Object>[] getPrimaryKeyExtractors() {
        return primaryKeyExtractors;
    }

    public SourceChangeListener<Object, Object>[] getSourceChangeListeners() {
        return sourceChangeListeners;
    }

    //------------

    protected abstract void sourceBatchStart(Source source, int sourcePosition);

    protected abstract void sourceOnRemove(Source source, int sourcePosition,
                                           Object key, Object value);

    /**
     * @param source
     * @param sourcePosition
     */
    protected abstract void sourceBatchEnd(Source source, int sourcePosition);

    //------------

    protected class ForwardingSourceChangeListener implements SourceChangeListener<Object, Object> {
        protected Source source;

        protected int joinSourcePosition;

        public ForwardingSourceChangeListener(Source source, int joinSourcePosition) {
            this.source = source;
            this.joinSourcePosition = joinSourcePosition;
        }

        public void batchStart() {
            AbstractJoinHandler.this.sourceBatchStart(source, joinSourcePosition);
        }

        public void onRemove(Object key, Object value) {
            AbstractJoinHandler.this.sourceOnRemove(source, joinSourcePosition, key, value);
        }

        public void batchEnd() {
            AbstractJoinHandler.this.sourceBatchEnd(source, joinSourcePosition);
        }
    }
}
