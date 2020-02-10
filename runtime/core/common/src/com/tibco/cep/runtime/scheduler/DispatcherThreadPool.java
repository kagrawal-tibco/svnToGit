package com.tibco.cep.runtime.scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.runtime.scheduler.impl.WorkerBasedController.DispatcherMBean;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher.AsyncWorkerService;
import com.tibco.cep.runtime.util.FQName;

public interface DispatcherThreadPool extends DispatcherMBean, AsyncWorkerService {

	public abstract void execute(Runnable command);

	public abstract boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException;

	public abstract boolean isTerminated();

	public abstract long getCompletedTaskCount();

	public abstract int getPoolSize();

	public abstract String getThreadPoolName();

	public abstract List<Runnable> shutdownNow();

	public abstract void shutdown();

	public abstract void afterExecute(Runnable r, Throwable t);

	public abstract void beforeExecute(Thread t, Runnable r);

	public abstract void start(FQName name);

}
