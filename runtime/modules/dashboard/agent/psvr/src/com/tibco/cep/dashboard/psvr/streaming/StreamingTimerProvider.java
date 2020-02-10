package com.tibco.cep.dashboard.psvr.streaming;

import java.util.concurrent.TimeUnit;

import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.dashboard.timer.TimerThreadPool;

public final class StreamingTimerProvider {

	private static StreamingTimerProvider instance;

	public static final synchronized StreamingTimerProvider getInstance() {
		if (instance == null) {
			instance = new StreamingTimerProvider();
		}
		return instance;
	}

	private TimerThreadPool timerThreadPool;

	private StreamingTimerProvider() {
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