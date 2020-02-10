package com.tibco.cep.runtime.perf.stats.impl;

import com.tibco.cep.runtime.perf.stats.TPoolJQueuePerfStatsMXBean;
import com.tibco.cep.runtime.perf.stats.TPoolJQueueStats;

public class TPoolJQueuePerfStatsMXBeanImpl implements TPoolJQueuePerfStatsMXBean {

	private TPoolJQueueStats stats;

	public TPoolJQueuePerfStatsMXBeanImpl(TPoolJQueueStats stats) {
		super();
		this.stats = stats;
	}

	@Override
	public int getMaximumThreads() {
		return stats.getMaximumThreads();
	}

	@Override
	public int getActiveThreads() {
		return stats.getActiveThreads();
	}

	@Override
	public int getQueueCapacity() {
		return stats.getQueueCapacity();
	}

	@Override
	public int getQueueSize() {
		return stats.getQueueSize();
	}

}