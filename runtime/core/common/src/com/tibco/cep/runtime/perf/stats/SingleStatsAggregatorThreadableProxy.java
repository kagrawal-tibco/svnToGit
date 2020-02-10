package com.tibco.cep.runtime.perf.stats;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

public class SingleStatsAggregatorThreadableProxy implements StatsAggregator, Runnable {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SingleStatsAggregatorThreadableProxy.class);

    private StatsAggregator aggregator;

    private BlockingQueue<Update> updatesQueue;

    private volatile boolean keepRunning;

    public SingleStatsAggregatorThreadableProxy(StatsAggregator aggregator, int queueCapacity) {
        this.aggregator = aggregator;
        if (queueCapacity == -1) {
            this.updatesQueue = new LinkedBlockingQueue<Update>();
        }
        else {
            this.updatesQueue = new ArrayBlockingQueue<Update>(queueCapacity);
        }
        keepRunning = true;
    }

    @Override
    public boolean willAccept(FQName name) {
        return aggregator.willAccept(name);
    }

    @Override
    public void aggregate(long time, FQName name, Data data) {
        boolean accepted = this.updatesQueue.offer(new Update(time, name, data));
        if (accepted == false) {
            LOGGER.log(Level.WARN, "could not line up metric update for %s", name);
        }
    }

    @Override
    public void close() {
        keepRunning = false;
        this.updatesQueue.clear();
        aggregator.close();
    }

    @Override
    public void run() {
        while (keepRunning == true) {
            try {
                Update update = this.updatesQueue.take();
                aggregator.aggregate(update.time, update.name, update.data);
            } catch (InterruptedException e) {
            }
        }
    }

    private class Update {

        private long time;

        private FQName name;

        private Data data;

        public Update(long time, FQName name, Data data) {
            super();
            this.time = time;
            this.name = name;
            this.data = data;
        }

    }

}