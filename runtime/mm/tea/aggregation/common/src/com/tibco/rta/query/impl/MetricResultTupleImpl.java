package com.tibco.rta.query.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MetricValueDescriptor;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.query.MetricResultTuple;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Loose equivalent of metric node.
 */
public class MetricResultTupleImpl implements MetricResultTuple {

	private static final long serialVersionUID = -7704211812996000821L;

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
	protected Map<String, Metric> metrics = new LinkedHashMap<String, Metric>();
	
	protected MetricEventType metricEventType = MetricEventType.UPDATE;

    /**
     * Metric key is common for an entire tuple.
     */
    protected MetricKey metricKey;

    /**
     * Descriptor is common for entire metric node.
     */
    protected MetricValueDescriptor metricValueDescriptor;

    //Set created and updated timestamps here since they are common for every metric in the node.
    protected long createdTime;

    protected long updatedTime;
	
	public MetricResultTupleImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Metric getMetric(String name) {
		// TODO Auto-generated method stub
		return metrics.get(name);
	}

    public void setMetricKey(MetricKey metricKey) {
        this.metricKey = metricKey;
    }

    public MetricKey getMetricKey() {
        return metricKey;
    }

    public MetricValueDescriptor getMetricValueDescriptor() {
        return metricValueDescriptor;
    }

    public void setMetricValueDescriptor(MetricValueDescriptor metricValueDescriptor) {
        this.metricValueDescriptor = metricValueDescriptor;
    }

    @Override
    @JsonIgnore
	public String[] getMetricNames() {
		return metrics.keySet().toArray(new String[metrics.size()]);
	}

	public void addMetric(String metricName, Metric<?> metric) {
		metrics.put(metricName, metric);
	}
	
	public void setMetricEventType(MetricEventType metricEventType) {
		this.metricEventType = metricEventType;
	}
	
	public MetricEventType getMetricEventType() {
		return metricEventType;
	}

	@Override
	public Map<String, Metric> getMetrics() {
		return metrics;
	}

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }
}
