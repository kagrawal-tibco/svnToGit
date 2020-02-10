package com.tibco.cep.query.stream.impl.rete.service;

import java.util.concurrent.ExecutorService;

import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.SnapshotAssistant;
import com.tibco.cep.query.stream.impl.rete.query.SnapshotAssistantOverseer;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
* Author: Ashwin Jayaprakash Date: May 15, 2008 Time: 3:32:40 PM
*/
public class SnapshotFeederForCQ {
    protected NoOpFlusher noOpFlusher;

    protected SnapshotQueryFeeder ssQueryFeeder;

    public SnapshotFeederForCQ() {
        ResourceId flusherId = new ResourceId(NoOpFlusher.class.getSimpleName());
        TupleInfo tupleInfo = new AbstractTupleInfo(Tuple.class, new String[0], new Class[0]) {
            public Tuple createTuple(Number id) {
                return null;
            }
        };

        this.noOpFlusher = new NoOpFlusher(flusherId, tupleInfo);
    }

    public SnapshotAssistant step1() {
        return noOpFlusher;
    }

    public void step2(ReteQuery query, SharedObjectSourceRepository sosRepo,
                      ExecutorService executorService) {
        ResourceId bridgeId = new ResourceId(NoOpBridge.class.getSimpleName());

        ssQueryFeeder = new SnapshotQueryFeeder(query, new NoOpBridge(bridgeId),
                new CompletionListener(), sosRepo, executorService);
    }

    public void stepGo() {
        ssQueryFeeder.start();
    }

    public void cancelFeedingAndDiscard() {
        ssQueryFeeder.stop();
        ssQueryFeeder = null;

        noOpFlusher = null;
    }

    //-----------

    protected static class CompletionListener implements SnapshotQueryCompletionListener {
        public void onComplete(SnapshotQueryFeeder feeder) {
            //Enable the filters because the feeder now has stopped.
            feeder.getQuery().enableReteEntityFilters();
        }
    }

    protected class NoOpBridge extends Bridge {
        public NoOpBridge(ResourceId id) {
            super(  /*Creates a cycle. But we use it here only to avoid NPE in the Bridge.*/
                    SnapshotFeederForCQ.this.noOpFlusher, id);
        }

        protected CustomCollection<? extends Tuple> getFinalCollectedTuples(Context context) {
            return null;
        }

        protected void clearFinalCollectedTuples(Context context) {
        }

        @Override
        public Stream getFlusher() {
            return SnapshotFeederForCQ.this.noOpFlusher;
        }
    }

    protected class NoOpFlusher extends AbstractStream implements SnapshotAssistant {
        protected SnapshotAssistantOverseer overseer;

        public NoOpFlusher(ResourceId id, TupleInfo outputInfo) {
            super(null, id, outputInfo);
        }

        @Override
        protected void doProcessing(Context context) throws Exception {
            overseer.assistantCompleted();
            overseer = null;

            /*
            Don't set accumulator to null becuuse events could still keep coming via
            accumulateCurrent(..).
            */
        }

        //----------

        public void setOverseer(SnapshotAssistantOverseer overseer) {
            this.overseer = overseer;
        }
    }
}
