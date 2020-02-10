package com.tibco.rta.query;

import com.tibco.rta.Metric;

import java.io.Serializable;

/**
 * Represents a query result.
 */
public class QueryResultTuple implements Serializable, ResultTuple {

	private static final long serialVersionUID = 4892257293324451171L;

	protected Metric<?> metric;
	
	protected MetricResultTuple metricResultTuple;

    protected String queryName;

    public Metric<?> getMetric() {
        return metric;
    }

    public void setMetric(Metric<?> metric) {
        this.metric = metric;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public MetricResultTuple getMetricResultTuple() {
    	return metricResultTuple;
    }
    
    public void setMetricResultTuple(MetricResultTuple metricResultTuple) {
        this.metricResultTuple = metricResultTuple;
    }
}
