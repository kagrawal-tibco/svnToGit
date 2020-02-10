package com.tibco.rta.common.service.impl;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.rta.common.service.CustomWorkItemService;
import com.tibco.rta.log.Level;
import com.tibco.rta.util.ConnectionAwareThreadPoolExecutor;
import com.tibco.rta.util.RejectedTaskResubmitter;

public class CustomWorkItemServiceImpl extends WorkItemServiceImpl implements CustomWorkItemService {

//	private ConnectionAwareThreadPoolExecutor executorService;

	@Override
	public void init(Properties configuration) throws Exception {
		// TODO Auto-generated method stub
		super.readProperties(configuration);
		executorService = new ConnectionAwareThreadPoolExecutor(corePoolSize, threadCount, timeToLive,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueSize), new WorkItemServiceThreadFactory(
						moduleName), new RejectedTaskResubmitter(LOGGER));
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Thread pool [%s] initialized with [%d] threads and [%d] queue size.", moduleName,
					threadCount, queueSize);
		}
		registerMBean(configuration);
	}

	@Override
	public void pauseExecutor() {
		((ConnectionAwareThreadPoolExecutor) this.executorService).pause();
	}

	@Override
	public void resumeExecutor() {
		((ConnectionAwareThreadPoolExecutor) this.executorService).resume();
	}
}
