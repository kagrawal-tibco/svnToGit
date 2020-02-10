package com.tibco.cep.runtime.perf.stats;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

public interface StatsAggregator {

    boolean willAccept(FQName name);

    void aggregate(long time, FQName name, Data data);

    void close();
}
