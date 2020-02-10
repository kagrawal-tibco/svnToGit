package com.tibco.cep.impl.service;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.service.AsyncExecutor;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

public class HashAsyncExecutor
        implements AsyncExecutor
{

    public static final int DEFAULT_CORE_POOL_SIZE = 2;

    private String familyName = "hae";
    private HashProvider hashProvider;
    protected int maxThreads = DEFAULT_CORE_POOL_SIZE;
    protected Id resourceId;
    protected DefaultThreadFactory threadFactory;
    protected transient List<ExecutorService> executorServices = new ArrayList<ExecutorService>();


    /**
     * Called by {@link #start()} and {@link #recover(com.tibco.cep.common.resource.ResourceProvider, Object...)}
     * after all necessary parameters/fields (like {@link #hashProvider}, {@link #maxThreads} and {@link #threadFactory})
     * have been set.
     */
    protected List<ExecutorService> createExecutorServices()
    {
        final List<ExecutorService> executorServices = new ArrayList<ExecutorService>(this.maxThreads);

        for (int i=0; i<this.maxThreads; i++) {
            executorServices.add(new ThreadPoolExecutor(
                    1,
                    1,
                    2 * 60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(4),
                    new DefaultThreadFactory(this.familyName + i),
                    new AbortPolicy()));
        }

        return executorServices;
    }


    @Override
    public boolean awaitTermination(
            long timeout,
            TimeUnit unit)
            throws InterruptedException
    {
        long target = System.nanoTime() + (timeout * unit.toNanos(1));

        for (final ExecutorService es : this.executorServices) {
            timeout = target - System.nanoTime();
            if ((timeout < 0) || !es.awaitTermination(timeout, TimeUnit.NANOSECONDS)) {
                return false;
            }
        }

        return true;
    }


    @Override
    public void execute(
            Runnable command)
    {
        this.getExecutorService(command).execute(command);
    }


    protected ExecutorService getExecutorService(
            Object task)
    {
        final int hash;

        if(this.hashProvider.getHash(task) < 0) {
            hash = -1 * this.hashProvider.getHash(task);
        }
        else {
            hash = this.hashProvider.getHash(task);
        }

        return this.executorServices.get(hash % this.maxThreads);
    }


    @Override
    public int getMaxThreads()
    {
        return this.maxThreads;
    }


    public String getThreadFamilyName()
    {
        return this.familyName;
    }


    @Override
    public Id getResourceId()
    {
        return this.resourceId;
    }


    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks)
            throws InterruptedException
    {
        throw new UnsupportedOperationException(); //TODO
    }


    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks,
            long timeout,
            TimeUnit unit)
            throws InterruptedException
    {
        throw new UnsupportedOperationException(); //TODO
    }


    @Override
    public <T> T invokeAny(
            Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException
    {
        throw new UnsupportedOperationException(); //TODO
    }


    @Override
    public <T> T invokeAny(
            Collection<? extends Callable<T>> tasks,
            long timeout,
            TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException
    {
        throw new UnsupportedOperationException(); //TODO
    }


    @Override
    public boolean isShutdown()
    {
        return this.executorServices.isEmpty()
                || this.executorServices.get(0).isShutdown();
    }


    @Override
    public boolean isTerminated()
    {
        for (final ExecutorService es : this.executorServices) {
            if (!es.isTerminated()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public HashAsyncExecutor recover(
            ResourceProvider resourceProvider,
            Object... params)
            throws RecoveryException
    {
        if (this.isShutdown()
                || this.executorServices.isEmpty()) {
            this.executorServices = this.createExecutorServices();
        }

        return this;
    }


    public void setHashProvider(
            HashProvider hashProvider)
    {
        this.hashProvider = hashProvider;
    }


    @Override
    public void setMaxThreads(
            int maxThreads)
    {
        this.maxThreads = maxThreads;
    }


    public void setResourceId(
            Id resourceId)
    {
        this.resourceId = resourceId;
    }

    public void setThreadFamilyName(
            String familyName)
    {
        this.familyName = familyName;
    }


    @Override
    public void shutdown()
    {
        for (final ExecutorService es : this.executorServices) {
            es.shutdown();
        }
    }


    @Override
    public List<Runnable> shutdownNow()
    {
        final List<Runnable> result = new ArrayList<Runnable>();
        for (final ExecutorService es : this.executorServices) {
            result.addAll(es.shutdownNow());
        }
        return result;
    }


    @Override
    public void start()
    {
        this.executorServices = this.createExecutorServices();
    }


    @Override
    public void stop()
    {
        this.shutdownNow();
    }


    @Override
    public <T> Future<T> submit(
            Callable<T> task) {
        return this.getExecutorService(task).submit(task);
    }


    @Override
    public Future<?> submit(
            Runnable task)
    {
        return this.getExecutorService(task).submit(task);
    }


    @Override
    public <T> Future<T> submit(
            Runnable task,
            T result)
    {
        return this.getExecutorService(task).submit(task, result);
    }


    public interface HashProvider
    {

        int getHash(Object o);

    }

	public void submitToAllThreads(Callable<Object> callable) {
		for(ExecutorService executorService : this.executorServices) {
			executorService.submit(callable);
		}
	}

}
