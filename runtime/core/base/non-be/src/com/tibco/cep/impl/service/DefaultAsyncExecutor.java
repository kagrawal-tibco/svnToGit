package com.tibco.cep.impl.service;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.service.AsyncExecutor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

/*
* Author: Ashwin Jayaprakash Date: Jul 20, 2009 Time: 1:50:57 PM
*/

/**
 * The return values like {@link ScheduledFuture} are not recoverable. The scheduled jobs are not serialized.
 */
public class DefaultAsyncExecutor implements AsyncExecutor {
    /**
     * {@value}
     */
    public static final int DEFAULT_CORE_POOL_SIZE = 2;

    protected Id resourceId;

    protected int maxThreads;

    protected DefaultThreadFactory threadFactory;

    protected transient ThreadPoolExecutor threadPoolExecutor;

    public DefaultAsyncExecutor() {
        this.maxThreads = DEFAULT_CORE_POOL_SIZE;
    }

    public Id getResourceId() {
        return resourceId;
    }

    public void setResourceId(Id resourceId) {
        this.resourceId = resourceId;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;

        if (threadPoolExecutor != null) {
            threadPoolExecutor.setCorePoolSize(maxThreads);
            threadPoolExecutor.setMaximumPoolSize(maxThreads);
        }
    }

    public String getThreadFamilyName() {
        return (threadFactory == null) ? null : threadFactory.getPoolName();
    }

    public void setThreadFamilyName(String familyName) {
        this.threadFactory = new DefaultThreadFactory(familyName);
    }

    //---------------

    /**
     * Called by {@link #start()} and {@link #recover(ResourceProvider, Object...)} after all necessary
     * parameters/fields (like {@link #maxThreads} and {@link #threadFactory}) have been set.
     *
     * @return
     */
    protected ThreadPoolExecutor createThreadPoolExecutor() {
        return new ThreadPoolExecutor(maxThreads, maxThreads, 2 * 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(4 * maxThreads), threadFactory, new AbortPolicy());
    }

    /**
     * Calls {@link #createThreadPoolExecutor()} and sets it to {@link #threadPoolExecutor}.
     */
    public void start() {
        threadPoolExecutor = createThreadPoolExecutor();
    }

    /**
     * Calls {@link #shutdownNow()}.
     */
    public void stop() {
        shutdownNow();
    }

    //---------------

    public DefaultAsyncExecutor recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = createThreadPoolExecutor();
        }

        return this;
    }

    //---------------

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return threadPoolExecutor.shutdownNow();
    }

    public boolean isShutdown() {
        return threadPoolExecutor.isShutdown();
    }

    public boolean isTerminated() {
        return threadPoolExecutor.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return threadPoolExecutor.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return threadPoolExecutor.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        return threadPoolExecutor.submit(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return threadPoolExecutor.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout,
                                         TimeUnit unit)
            throws InterruptedException {
        return threadPoolExecutor.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return threadPoolExecutor.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return threadPoolExecutor.invokeAny(tasks, timeout, unit);
    }

    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }

	public void submitToAllThreads(Callable<Object> callable) {
		throw new UnsupportedOperationException("DefaultAsyncExecutor does not support submitToAllThreads");
	}
}