package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.runtime.model.element.Metric;

abstract public class MetricImpl extends GeneratedConceptImpl implements Metric {
    protected MetricImpl() {
        super();
    }

    protected MetricImpl(long _id) {
        super(_id);
    }

    protected MetricImpl(long id, String uri) {
        super(id, uri);
    }

    protected MetricImpl(String uri) {
        super(uri);
    }
}
