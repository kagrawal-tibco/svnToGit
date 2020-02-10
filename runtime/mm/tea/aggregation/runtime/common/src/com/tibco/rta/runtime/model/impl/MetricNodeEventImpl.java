package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;

public class MetricNodeEventImpl implements MetricNodeEvent {

	private static final long serialVersionUID = 7265658786335765115L;

	protected MetricNode metricNode;
	protected MetricEventType metricNodeEventType;
	
	public MetricNodeEventImpl (MetricNode metricNode, MetricEventType metricEventType) {
		this.metricNode = metricNode;
		this.metricNodeEventType = metricEventType;
	}

	@Override
	public MetricNode getMetricNode() {
		// TODO Auto-generated method stub
		return metricNode;
	}

	@Override
	public MetricEventType getEventType() {
		// TODO Auto-generated method stub
		return metricNodeEventType;
	}
	
	@Override
	public String toString() {
		return String.format("MetricNodeEvent: [EventType=%s, %s]", metricNodeEventType, metricNode);
	}

}
