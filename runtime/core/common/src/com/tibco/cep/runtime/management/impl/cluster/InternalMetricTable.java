package com.tibco.cep.runtime.management.impl.cluster;

import com.tibco.cep.runtime.management.MetricTable;

/*
* Author: Ashwin Jayaprakash Date: Jan 28, 2009 Time: 6:20:31 PM
*/
public interface InternalMetricTable extends MetricTable {
    /**
     * @param clusterURL
     */
    void init(String clusterURL, String role);

    void discard();
}
