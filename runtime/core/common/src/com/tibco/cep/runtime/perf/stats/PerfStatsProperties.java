package com.tibco.cep.runtime.perf.stats;

public interface PerfStatsProperties {

    static final String ENABLE_STATS = "be.stats.enabled";

    static enum THREADING_MODEL {NONE, SINGLE, MULTI }

    static final String THREADING_MODEL_PROPERTY_KEY = "be.stats.threading.model";

    static final String STATS_QUEUE_CAPACITY = "be.stats.queue.capacity";

    static final int DEFAULT_STATS_QUEUE_CAPACITY = 1024;

    static final int TIMED_WINDOW_DURATION = 1000;
}
