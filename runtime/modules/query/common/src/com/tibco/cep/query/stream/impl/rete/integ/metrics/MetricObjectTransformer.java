package com.tibco.cep.query.stream.impl.rete.integ.metrics;

import java.util.Properties;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.management.CacheTable;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 3, 2009 Time: 1:53:25 PM
*/
public interface MetricObjectTransformer {
    void init(Properties properties, BEClassLoader classLoader,
              ManagementTable managementTable, MetricTable metricTable, CacheTable cacheTable);

    /**
     * @param name
     * @param data
     * @return Cannot be <code>null</code>.
     */
    Entity transform(FQName name, Data data);

    void discard();
}
