package com.tibco.cep.metric.timer;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledExecutor extends ScheduledThreadPoolExecutor {

	public ScheduledExecutor(int corePoolSize) {
		super(corePoolSize);
	}
}
