package com.tibco.cep.runtime.scheduler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.runtime.scheduler.DispatcherThreadPool;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.CustomDaemonThreadFactory;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SuspendAwareBlockingQueue;
import com.tibco.cep.runtime.util.SuspendAwareLBQImpl;

/**
 * @author mjinia
 *
 */
public class HashedDispatcherThreadPoolImpl implements
		DispatcherThreadPool, HashedDispatcherThreadPoolImplMBean {
	
	private List<DispatcherThreadPool> executorList = new ArrayList<DispatcherThreadPool>();
	private HashProvider hashProvider = null;
	
	public HashedDispatcherThreadPoolImpl(String simpleName,
            int maximumThreadPoolSize,
            int maxJobQueueSize,
            RuleServiceProvider rsp,
            HashProvider hashProvider) {
		
        this.hashProvider = hashProvider;
		CustomDaemonThreadFactory threadFactory = new CustomDaemonThreadFactory(
				simpleName, rsp);

		if (maxJobQueueSize <= 0) {
			maxJobQueueSize = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < maximumThreadPoolSize; i++) {
			SuspendAwareBlockingQueue<Runnable> workQueue = new SuspendAwareLBQImpl<Runnable>(
					maxJobQueueSize);

			DispatcherThreadPool executor = new DispatcherThreadPoolImpl(
					simpleName, 1, 1, 10 * 60 /* 10 mins */, TimeUnit.SECONDS,
					workQueue, threadFactory, rsp);
			
			executorList.add(executor);
		}
	}

	@Override
	public String getDestinationName() {
		if(executorList.size() > 0) {
			return executorList.get(0).getDestinationName();
		}
		return null;
	}

	@Override
	public int getThreadCount() {
		return executorList.size();
	}

	@Override
	public void setThreadCount(int threadCount) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getQueueSize() {
		if(executorList.size() > 0) {
			return executorList.get(0).getQueueSize();
		}
		return 0;
	}

	@Override
	public int getQueueCapacity() {
		if(executorList.size() > 0) {
			return executorList.get(0).getQueueCapacity();
		}
		return 0;
	}

	@Override
	public boolean isSuspended() {
		if(executorList.size() > 0) {
			for(DispatcherThreadPool executor : executorList) {
				if(!executor.isSuspended()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isStarted() {
		if(executorList.size() > 0) {
			for(DispatcherThreadPool executor : executorList) {
				if(!executor.isStarted()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void suspend() {
		if(executorList.size() > 0) {
			for(DispatcherThreadPool executor : executorList) {
				executor.suspend();
			}
		}
	}

	@Override
	public void resume() {
		if(executorList.size() > 0) {
			for(DispatcherThreadPool executor : executorList) {
				executor.resume();
			}
		}

	}

	@Override
	public boolean isActive() {
		if(executorList.size() > 0) {
			for(DispatcherThreadPool executor : executorList) {
				if(!executor.isActive()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public FQName getName() {
		if(executorList.size() > 0) {
			return executorList.get(0).getName();
		}
		return null;
	}

	@Override
	public int getNumMaxThreads() {
		return executorList.size();
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

	@Override
	public void execute(Runnable command) {
		int hash = this.hashProvider.getHash(command);

        if(hash < 0) {
            hash = -1 * hash;
        }

        this.executorList.get(hash % executorList.size()).execute(command);		
	}
	
	public interface HashProvider {

		int getHash(Object o);

	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException {
		long target = System.nanoTime() + (timeout * unit.toNanos(1));

        for (final DispatcherThreadPool executor : executorList) {
            timeout = target - System.nanoTime();
            if ((timeout < 0) || !executor.awaitTermination(timeout, TimeUnit.NANOSECONDS)) {
                return false;
            }
        }

        return true;
	}

	@Override
	public boolean isTerminated() {
		if(executorList.size() > 0) {
			for(DispatcherThreadPool executor : executorList) {
				if(!executor.isTerminated()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public long getCompletedTaskCount() {
		long completedTaskCount = 0;
		for(DispatcherThreadPool executor : executorList) {
			completedTaskCount += executor.getCompletedTaskCount();
		}
		return completedTaskCount;
	}

	@Override
	public int getPoolSize() {
		return executorList.size();
	}

	@Override
	public String getThreadPoolName() {
		if(executorList.size() > 0) {
			return executorList.get(0).getThreadPoolName();
		}
		return null;
	}

	@Override
	public List<Runnable> shutdownNow() {
		final List<Runnable> result = new ArrayList<Runnable>();
        for (final DispatcherThreadPool executor : executorList) {
            result.addAll(executor.shutdownNow());
        }
        return result;
	}

	@Override
	public void shutdown() {
		for(DispatcherThreadPool executor : executorList) {
			executor.shutdown();
		}
	}

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
	public void start(FQName name) {
		for(DispatcherThreadPool executor : executorList) {
			executor.start(name);
		}
	}

}
