package com.tibco.rta.service.metric;

import com.tibco.rta.runtime.model.MetricNodeEvent;

public interface MetricEventListener {
	
	void onValueChange(MetricNodeEvent metricNodeEvent) throws Exception;

}
