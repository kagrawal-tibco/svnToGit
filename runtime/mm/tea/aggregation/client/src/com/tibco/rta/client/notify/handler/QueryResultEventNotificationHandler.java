package com.tibco.rta.client.notify.handler;

import com.tibco.rta.annotations.GuardedBy;
import com.tibco.rta.client.notify.AsyncNotificationEvent;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.serialize.impl.ModelJSONDeserializer;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.util.HeaderConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryResultEventNotificationHandler implements AsyncNotificationEventHandler {

    /**
     * Single event handler has multiple queries registered with it.
     */
    private List<Query> registeredQueries = new ArrayList<Query>();

    /**
     * Iterator protection lock.
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    public static final QueryResultEventNotificationHandler INSTANCE = new QueryResultEventNotificationHandler();

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private QueryResultEventNotificationHandler() {}

    @Override
    public void handleNotificationEvent(AsyncNotificationEvent notificationEvent) throws Exception {
        byte[] source = (byte[]) notificationEvent.getSource();
        Collection<?> resultTuples = deserialize(source);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Number of tuples %s", resultTuples.size());
        }
        for (Object resultTuple : resultTuples) {
            dispatchToQuery(resultTuple);
        }
    }

    @GuardedBy("mainLock")
    public void registerQuery(Query query) {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (registeredQueries.add(query)) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Registering streaming query [%s] for receiving updates", query.getName());
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @GuardedBy("mainLock")
    public void unregisterQuery(Query query) {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (registeredQueries.remove(query)) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Unregistering streaming query [%s] for receiving updates", query.getName());
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @GuardedBy("mainLock")
    private void dispatchToQuery(Object resultTuple) {
        if (!(resultTuple instanceof QueryResultTuple)) {
            return;
        }
        QueryResultTuple queryResultTuple = (QueryResultTuple) resultTuple;

        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            for (Query registeredQuery : registeredQueries) {
                if (queryResultTuple.getQueryName().equals(registeredQuery.getName())) {
                    registeredQuery.getResultHandler().onData(queryResultTuple);
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public boolean canHandle(byte[] header) throws Exception {
        String headerString = new String(header, "UTF-8");
        return HeaderConstants.QUERY_TUPLE_HEADER.equals(headerString);
    }


    private Collection<?> deserialize(byte[] buffer) throws Exception {
        QueryResultTupleCollection<?> resultTuples = ModelJSONDeserializer.INSTANCE.deserializeQueryResults(buffer);
        return resultTuples.getQueryResultTuples();
    }
}
