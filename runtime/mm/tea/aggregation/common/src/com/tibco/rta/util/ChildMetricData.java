package com.tibco.rta.util;

import java.io.Serializable;
import java.util.List;

import com.tibco.rta.Metric;
import com.tibco.rta.query.MetricFieldTuple;

public class ChildMetricData implements Serializable{

	private List<MetricFieldTuple> orderList;
	private Metric<?> metric;

	public ChildMetricData(Metric<?> metric, List<MetricFieldTuple> orderList) {
		this.metric = metric;
		this.orderList = orderList;
	}

	public Metric getMetric() {
		return metric;
	}

	public List<MetricFieldTuple> getOrderList() {
		return orderList;
	}
}
