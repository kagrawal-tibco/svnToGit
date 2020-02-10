package com.tibco.cep.runtime.perf.stats;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

public class MultipleStatsAggregatorThreadableProxy implements StatsAggregator, Runnable {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(MultipleStatsAggregatorThreadableProxy.class);

    private List<StatsAggregator> aggregators;

    private BlockingQueue<Update> updates;

    private volatile boolean keepRunning;

    public MultipleStatsAggregatorThreadableProxy(List<StatsAggregator> aggregators, int queueCapacity) {
        this.aggregators = new ArrayList<StatsAggregator>(aggregators);
        updates = queueCapacity == -1 ? new LinkedBlockingQueue<MultipleStatsAggregatorThreadableProxy.Update>() : new ArrayBlockingQueue<MultipleStatsAggregatorThreadableProxy.Update>(queueCapacity);
        keepRunning = true;
    }

    @Override
    public boolean willAccept(FQName name) {
        for (StatsAggregator aggregator : aggregators) {
            if (aggregator.willAccept(name) == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void aggregate(long time, FQName name, Data data) {
        for (StatsAggregator aggregator : aggregators) {
            if (aggregator.willAccept(name) == true) {
                boolean accepted = updates.offer(new Update(time, aggregator, name, data));
                if (accepted == false) {
                    LOGGER.log(Level.WARN, "could not line up metric update for %s", name);
                }
            }
        }
    }

    @Override
    public void close() {
        keepRunning = false;
        updates.clear();
        aggregators.clear();
    }

    @Override
    public void run() {
        while (keepRunning == true) {
            try {
                Update update = updates.take();
                if (keepRunning == true) {
                    update.aggregator.aggregate(update.time, update.name, update.data);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    private class Update {

        private long time;

        private StatsAggregator aggregator;

        private FQName name;

        private Data data;

        public Update(long time, StatsAggregator aggregator, FQName name, Data data) {
            super();
            this.time = time;
            this.aggregator = aggregator;
            this.name = name;
            this.data = data;
        }

    }

}
