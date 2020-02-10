package com.tibco.rta.common.service.session;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.util.ConnectionAwareThreadPoolExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/2/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompoundExecutorService extends AbstractExecutorService {

    //Maintain list of queries versus executors
    private ConcurrentHashMap<String, ConnectionAwareThreadPoolExecutor> queryExecutors;

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());

    SessionStateKeeper sessionStateKeeper;


    public CompoundExecutorService(SessionStateKeeper sessionStateKeeper) {
        queryExecutors = new ConcurrentHashMap<String, ConnectionAwareThreadPoolExecutor>();
        this.sessionStateKeeper = sessionStateKeeper;
    }

    @Override
    public void shutdown() {
        for (ExecutorService childExecutorService : queryExecutors.values()) {
            if (!childExecutorService.isShutdown()) {
                childExecutorService.shutdown();
            }
        }
    }

    @Override
    public List<Runnable> shutdownNow() {
        for (ExecutorService childExecutorService : queryExecutors.values()) {
            if (!childExecutorService.isShutdown()) {
                childExecutorService.shutdownNow();
            }
        }
        //TODO
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isTerminated() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Future<T> submit(Callable<T> task) {

        if (task instanceof TuplePublisher) {
            TuplePublisher tuplePublisher = (TuplePublisher) task;
            //Get query name and find appropriate executor
            String queryName = tuplePublisher.getQueryName();
            ConnectionAwareThreadPoolExecutor executorService = queryExecutors.get(queryName);
            if (executorService == null) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Creating new executor for query %s", queryName);
                }
                executorService = new ConnectionAwareThreadPoolExecutor(1, 1, 60L, TimeUnit.MINUTES,
                        new LinkedBlockingQueue<Runnable>(), new SessionPublisherThreadFactory());
                queryExecutors.put(queryName, executorService);
            }
            return (Future<T>) executorService.submit(tuplePublisher);
        }
        throw new RuntimeException("Illegal task type");
    }

    @Override
    public void execute(Runnable command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class SessionPublisherThreadFactory implements ThreadFactory {

        private AtomicInteger threadCounter = new AtomicInteger(0);

        static final String NAME_PREFIX = "Session-Publisher-Pool";

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, NAME_PREFIX + "-" + threadCounter.getAndIncrement());
        }
    }


    public void connectionFailure() {
        for (ConnectionAwareThreadPoolExecutor executor : queryExecutors.values()) {
            executor.pause();
        }
    }

    public void connectionEstablished() {
        for (ConnectionAwareThreadPoolExecutor executor : queryExecutors.values()) {
            executor.resume();
        }
    }
}
