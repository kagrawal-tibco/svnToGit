package com.tibco.rta.common.service.session;

import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.queues.BatchJob;
import com.tibco.rta.queues.BatchedBlockingQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/2/13
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TuplePublisher implements Callable<Object> {

    private String queryName;

    private BatchedBlockingQueue workQueue;

    private ServerSession serverSession;

    public TuplePublisher(String queryName,
                          BatchedBlockingQueue workQueue,
                          ServerSession serverSession) {
        this.queryName = queryName;
        this.workQueue = workQueue;
        this.serverSession = serverSession;
    }

    @Override
    public Object call() throws Exception {
        Collection<BatchJob> jobs = workQueue.take();

        List<QueryResultTuple> queryResultTuples = new ArrayList<QueryResultTuple>(jobs.size());

        for (BatchJob batchJob : jobs) {
            QueryResultTuple queryResultTuple = (QueryResultTuple) batchJob.getWrappedObject();
            queryResultTuples.add(queryResultTuple);
        }
        if (queryResultTuples.size() > 0) {
            serverSession.commit(queryResultTuples);
        }
        return null;
    }

    public String getQueryName() {
        return queryName;
    }
}
