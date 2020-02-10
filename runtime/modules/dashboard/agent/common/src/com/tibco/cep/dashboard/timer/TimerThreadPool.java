package com.tibco.cep.dashboard.timer;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class TimerThreadPool {

	protected Logger logger;

	protected int count;

	protected ThreadGroup threadGroup;

	protected ScheduledThreadPoolExecutor timerThreadPool;

	public TimerThreadPool(Logger logger, int count, ThreadGroup threadGroup) {
		this.logger = logger;
		this.count = count;
		this.threadGroup = threadGroup;

		timerThreadPool = new ScheduledThreadPoolExecutor(count,
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(TimerThreadPool.this.threadGroup, r, TimerThreadPool.this.threadGroup.getName()+".thread."+(timerThreadPool.getPoolSize()+1));
				}

			},
			new RejectedExecutionHandler() {

				@Override
				public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					TimerThreadPool.this.logger.log(Level.ERROR, "could not execute "+r);
				}
			}
		);
	}

	public void stop() {
		timerThreadPool.shutdownNow();
	}

	public void schedule(ExceptionResistentTimerTask task, long delay, TimeUnit unit) {
		ScheduledFuture<?> future = timerThreadPool.schedule(task, delay, unit);
		task.setFuture(future);
	}

	public void scheduleAtFixedRate(ExceptionResistentTimerTask task, long initialDelay, long period, TimeUnit unit) {
		ScheduledFuture<?> future = timerThreadPool.scheduleAtFixedRate(task, initialDelay, period, unit);
		task.setFuture(future);
	}

	public void scheduleWithFixedDelay(ExceptionResistentTimerTask task, long initialDelay, long delay, TimeUnit unit) {
		ScheduledFuture<?> future = timerThreadPool.scheduleWithFixedDelay(task, initialDelay, delay, unit);
		task.setFuture(future);
	}

}
