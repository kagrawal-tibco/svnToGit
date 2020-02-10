package com.tibco.cep.dashboard.timer;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.Service;

public class TimerService extends Service {

	public static final String NAME = "timer";

	private TimerThreadPool timerThreadPool;

	public TimerService() {
		super(NAME, "Timer Service");
	}

	@Override
	protected void doInit() throws ManagementException {
		int count = (Integer) TimerProperties.THREAD_POOL_COUNT.getValue(properties);
		timerThreadPool = new TimerThreadPool(logger, count, new ThreadGroup("dashboard.agent.timers"));
		TimerProvider.getInstance().setTimerThreadPool(timerThreadPool);
	}

	@Override
	protected boolean doStop() {
		timerThreadPool.stop();
		TimerProvider.getInstance().setTimerThreadPool(null);
		return true;
	}

	MessageGenerator getMessageGenerator() {
		return messageGenerator;
	}

	ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}
}
