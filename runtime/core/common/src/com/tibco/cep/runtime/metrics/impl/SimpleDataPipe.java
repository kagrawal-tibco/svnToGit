package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 6:41:32 PM
*/
public class SimpleDataPipe extends LinkedBlockingQueue<Data> implements InternalDataPipe {
    protected FQName ownerName;

    protected MetricDef metricDef;

    public SimpleDataPipe(FQName ownerName, MetricDef metricDef) {
        this.ownerName = ownerName;
        this.metricDef = metricDef;
    }

    public FQName getOwnerName() {
        return ownerName;
    }

    public MetricDef getMetricDef() {
        return metricDef;
    }
}
