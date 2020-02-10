package com.tibco.cep.runtime.scheduler.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.scheduler.DispatcherThreadPool;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.CustomDaemonThreadFactory;
import com.tibco.cep.runtime.util.CustomThreadFactory;
import com.tibco.cep.runtime.util.CustomThreadPoolExecutor;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SuspendAwareBlockingQueue;
import com.tibco.cep.runtime.util.SuspendAwareLBQImpl;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 7:11:07 PM
*/

public class DispatcherThreadPoolImpl
        implements DispatcherThreadPool, DispatcherThreadPoolImplMBean {
    protected final InternalTPE actualExecutor;

    protected RuleServiceProvider rsp;

	public static DispatcherThreadPool create(String simpleName,
                                              int maximumThreadPoolSize,
                                              int maxJobQueueSize,
                                              RuleServiceProvider rsp) {
        CustomDaemonThreadFactory threadFactory =
                new CustomDaemonThreadFactory(simpleName, rsp);

        if (maxJobQueueSize <= 0) {
            maxJobQueueSize = Integer.MAX_VALUE;
        }

        SuspendAwareBlockingQueue<Runnable> workQueue = new SuspendAwareLBQImpl<Runnable>(maxJobQueueSize);

        return new DispatcherThreadPoolImpl(simpleName,
                1, maximumThreadPoolSize,
                10 * 60 /*10 mins*/, TimeUnit.SECONDS,
                workQueue, threadFactory, rsp);
    }

    /**
     * Starts in suspended mode - {@link com.tibco.cep.runtime.util.CustomThreadPoolExecutor#doSuspend()}.
     *
     * @param simpleName
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param rsp
     */
    protected DispatcherThreadPoolImpl(String simpleName,
                                   int corePoolSize, int maximumPoolSize,
                                   long keepAliveTime, TimeUnit unit,
                                   SuspendAwareBlockingQueue<Runnable> workQueue,
                                   CustomThreadFactory<? extends BEManagedThread> threadFactory,
                                   RuleServiceProvider rsp) {
        this.actualExecutor = new InternalTPE(simpleName,
                corePoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue, threadFactory, rsp.getLogger(DispatcherThreadPoolImpl.class));

        this.rsp = rsp;
    }

    @Override
	public void start(FQName name) {
        actualExecutor.setName(name);

        if (rsp instanceof RuleServiceProviderImpl) {
            ((RuleServiceProviderImpl) rsp).registerExecutableResource(this.actualExecutor);
        }
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        AsyncWorkerServiceWatcher workManagerWatcher = registry.getWorkManagerWatcher();
        if (workManagerWatcher != null) {
            workManagerWatcher.register(this);
        }

        resume();
    }

    //-----------

    @Override
	public void beforeExecute(Thread t, Runnable r) {
        BEManagedThread managedThread = (BEManagedThread) t;
        managedThread.executePrologue();
    }

    @Override
	public void afterExecute(Runnable r, Throwable t) {
        if (t == null) {
            BEManagedThread managedThread = (BEManagedThread) Thread.currentThread();
            managedThread.executeEpilogue();
        }
    }

    @Override
	public void shutdown() {
        ((RuleServiceProviderImpl) rsp).registerExecutableResource(this.actualExecutor);

        actualExecutor.shutdown();
    }

    @Override
	public List<Runnable> shutdownNow() {
        ((RuleServiceProviderImpl) rsp).registerExecutableResource(this.actualExecutor);

        return actualExecutor.shutdownNow();
    }

    //-----------

    @Override
	public String getThreadPoolName() {
        return actualExecutor.getThreadPoolName();
    }

    @Override
	public int getPoolSize() {
        return actualExecutor.getPoolSize();
    }

    @Override
	public long getCompletedTaskCount() {
        return actualExecutor.getCompletedTaskCount();
    }

    @Override
	public boolean isTerminated() {
        return actualExecutor.isTerminated();
    }

    @Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return actualExecutor.awaitTermination(timeout, unit);
    }

    /**
     * Blocks if {@link #isSuspended()} is <code>true</code>.
     *
     * @param command
     */
    @Override
	public void execute(Runnable command) {
        actualExecutor.execute(command);
    }

    //-----------

    @Override
	public boolean isActive() {
        return !actualExecutor.isShutdown();
    }

    @Override
	public FQName getName() {
        return actualExecutor.getName();
    }

    @Override
	public int getNumMaxThreads() {
        return actualExecutor.getMaximumPoolSize();
    }

    @Override
	public int getNumActiveThreads() {
        return getThreadCount();
    }

    @Override
	public int getJobQueueCapacity() {
        return getQueueCapacity();
    }

    @Override
	public int getJobQueueSize() {
        return getQueueSize();
    }

    //-----------

    @Override
	public String getDestinationName() {
        return actualExecutor.getThreadPoolName();
    }

    @Override
	public int getThreadCount() {
        return actualExecutor.getPoolSize();
    }

    @Override
	public void setThreadCount(int threadCount) {
        actualExecutor.setMaximumPoolSize(threadCount);
    }

    @Override
	public int getQueueSize() {
        return actualExecutor.getJobQueueSize();
    }

    @Override
	public int getQueueCapacity() {
        return actualExecutor.getJobQueueCapacity();
    }

    @Override
	public boolean isSuspended() {
        return actualExecutor.isSuspended();
    }

    @Override
	public boolean isStarted() {
        return !actualExecutor.isShutdown();
    }

    @Override
	public void suspend() {
        actualExecutor.suspendResource();
    }

    @Override
	public void resume() {
        actualExecutor.resumeResource();
    }

    //------------

    protected class InternalTPE extends CustomThreadPoolExecutor {
        protected InternalTPE(String threadPoolName, int corePoolSize, int maximumPoolSize,
                              long keepAliveTime, TimeUnit unit,
                              SuspendAwareBlockingQueue<Runnable> workQueue,
                              CustomThreadFactory<? extends BEManagedThread> threadFactory, Logger logger) {
            super(threadPoolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, logger);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            DispatcherThreadPoolImpl.this.beforeExecute(t, r);

            super.beforeExecute(t, r);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            DispatcherThreadPoolImpl.this.afterExecute(r, t);

            super.afterExecute(r, t);
        }
    }
}
