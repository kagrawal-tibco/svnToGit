package com.tibco.cep.runtime.perf.stats;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.runtime.management.MetricTable.DataListener;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

class MetricTableDataListener implements DataListener {

    private List<StatsAggregator> aggregators;
    private String name;

    MetricTableDataListener(String name, List<StatsAggregator> aggregators){
    	this.name = name;
        this.aggregators = new LinkedList<StatsAggregator>(aggregators);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onNew(FQName key, Data data) {
        if (key != null && data != null) {
            for (StatsAggregator aggregator : aggregators) {
                if (aggregator.willAccept(key) == true) {
                    aggregator.aggregate(System.currentTimeMillis(), key, data);
                }
            }
        }
    }

}