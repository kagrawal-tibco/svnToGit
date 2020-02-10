package com.tibco.cep.metric.timer;

import java.util.List;

import com.tibco.cep.designtime.core.model.element.Metric;

public interface Scheduler {

	public abstract void initialize(long delay, long window);

	public abstract void schedule(List<Metric> metric) throws Exception;
}