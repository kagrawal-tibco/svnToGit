package com.tibco.rta.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/3/13
 * Time: 6:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionAwareThreadPoolExecutor extends ThreadPoolExecutor {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    /**
     * Main lock.
     */
	private final ReentrantLock pauseLock = new ReentrantLock();

    /**
     * Condition for pausing.
     */
	private final Condition unpaused = pauseLock.newCondition();

    /**
     * Whether thread pool is in pause condition.
     */
	private volatile boolean isPaused;

	public ConnectionAwareThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	public ConnectionAwareThreadPoolExecutor(int corePoolSize, int maximumPoolsize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue,
			ThreadFactory threadFactory, RejectedTaskResubmitter rejectedTaskResubmitter) {
		super(corePoolSize, maximumPoolsize, keepAliveTime, unit, workQueue, threadFactory, rejectedTaskResubmitter);
	}

	@Override
	protected void beforeExecute(Thread thread, Runnable runnable) {
		super.beforeExecute(thread, runnable);
		pauseLock.lock();

		try {
			while (isPaused) {
				unpaused.await();
			}
		} catch (InterruptedException ie) {
			thread.interrupt();
		} finally {
			pauseLock.unlock();
		}
	}

	public void pause() {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Pausing connection aware thread pool, possible connection loss");
		}

		pauseLock.lock();
		try {
			isPaused = true;
		} finally {
			pauseLock.unlock();
		}
	}

	public void resume() {
        if (!isPaused) {
            return;
        }
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Resuming connection aware thread pool, connection re-established");
		}

		pauseLock.lock();
		try {
			isPaused = false;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}
}


