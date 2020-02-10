package com.tibco.cep.dashboard.timer;

import java.util.concurrent.TimeUnit;

public final class TimerProvider {

	private static TimerProvider instance;

	public static final synchronized TimerProvider getInstance() {
		if (instance == null) {
			instance = new TimerProvider();
		}
		return instance;
	}

	private TimerThreadPool timerThreadPool;

	private TimerProvider() {
	}

	void setTimerThreadPool(TimerThreadPool timerThreadPool) {
		this.timerThreadPool = timerThreadPool;
	}

	public void schedule(ExceptionResistentTimerTask task, long delay, TimeUnit unit) {
		if (this.timerThreadPool == null) {
			throw new IllegalStateException("No timer thread pool assigned");
		}
		timerThreadPool.schedule(task, delay, unit);
	}

	public void scheduleAtFixedRate(ExceptionResistentTimerTask task, long initialDelay, long period, TimeUnit unit) {
		if (this.timerThreadPool == null) {
			throw new IllegalStateException("No timer thread pool assigned");
		}
		timerThreadPool.scheduleAtFixedRate(task, initialDelay, period, unit);
	}

	public void scheduleWithFixedDelay(ExceptionResistentTimerTask task, long initialDelay, long delay, TimeUnit unit) {
		if (this.timerThreadPool == null) {
			throw new IllegalStateException("No timer thread pool assigned");
		}
		timerThreadPool.scheduleWithFixedDelay(task, initialDelay, delay, unit);
	}



}