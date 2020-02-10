package com.tibco.cep.runtime.session;

/*
* Author: Suresh Subramani / Date: 1/17/12 / Time: 1:55 PM
*/
public interface RuleSessionMetrics {

    enum MetricType {
        PRERTC,
        INRTC,
        POSTRTC,

    }

    void setMetric(MetricType metricType, int typeId, long timems);

}
