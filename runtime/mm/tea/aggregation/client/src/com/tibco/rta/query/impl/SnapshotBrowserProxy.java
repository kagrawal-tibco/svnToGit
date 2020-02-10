package com.tibco.rta.query.impl;

import com.tibco.rta.client.AbstractClientBrowser;
import com.tibco.rta.log.Level;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.MetricResultTuple;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/1/13
 * Time: 1:53 PM
 * A proxy objects that client will use to work with the real browser
 * for snapshot queries.
 * <p>
 *     Every browser proxy gets a unique ID for correlation.
 * </p>
 */
public class SnapshotBrowserProxy<T extends MetricResultTuple> extends AbstractClientBrowser<T> {

    /**
     * Internal buffering
     */
    private Queue<QueryResultTuple> resultsQueue = new LinkedList<QueryResultTuple>();


    @Override
    public boolean hasNext() {
        try {
            return resultsQueue.peek() != null || session.hasNextResult(id);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        QueryResultTuple nextResult = resultsQueue.poll();
        if (nextResult == null) {
            try {
                //Add to queue
                Collection<QueryResultTuple> queryResultTuples = session.nextResult(id);

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Number of tuples %s", queryResultTuples.size());
                }
                resultsQueue.addAll(queryResultTuples);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
            nextResult = resultsQueue.poll();
        }
        if (nextResult != null) {
            return (T) nextResult.getMetricResultTuple();
        }
        return null;
    }
}
