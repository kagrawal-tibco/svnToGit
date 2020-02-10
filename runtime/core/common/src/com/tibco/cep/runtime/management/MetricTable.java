package com.tibco.cep.runtime.management;

import java.util.Collection;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 28, 2009 Time: 11:52:04 AM
*/

/**
 * This class has to be independent from all runtime BE dependencies. This class will also be
 * instantiated and used by non-BE clients.
 */
public interface MetricTable {
    Collection<FQName> getMetricDefNames();

    void addMetricDef(MetricDef metricDef);

    MetricDef getMetricDef(FQName fqn);

    MetricDef removeMetricDef(FQName fqn);

    //-------------

    Collection<FQName> getMetricNames();

    void addMetricData(FQName fqn, Data data);

    Data getMetricData(FQName fqn);

    Data removeMetricData(FQName fqn);

    //-------------

    /**
     * Uses {@link MetricTable.DataListener#getName()}.
     *
     * @param listener
     * @param fqnToListenTo Can be <code>null</code> to indicate all or *.
     */
    void registerListener(DataListener listener, FQName fqnToListenTo);

    /**
     * @param listenerName
     * @param fqnToListenTo Can be <code>null</code> if that was how it was registered.
     */
    void unregisterListener(String listenerName, FQName fqnToListenTo);

    //-------------

    public interface DataListener {
        String getName();

        void onNew(FQName key, Data data);
    }
}
