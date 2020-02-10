package com.tibco.cep.runtime.perf.stats;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.session.RuleSession;

public final class PerformanceStatsCollectorFactory implements Service {

    private Map<String,PerformanceStatsCollector> collectors;

    private boolean enabled;

    public PerformanceStatsCollectorFactory(){
        collectors = new HashMap<String, PerformanceStatsCollector>();
        enabled = false;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

    @Override
    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        String value = configuration.getProperty(PerfStatsProperties.ENABLE_STATS);
        if (value != null) {
            enabled = Boolean.valueOf(value);
        }
    }

    @Override
    public void start() throws Exception {
        //do nothing
    }

    public void createCollector(RuleSession rs, String agentName, int agentId, MBeanServer beanServer) {
        if (enabled == true) {
            String key = agentName + ":" + agentId;
            PerformanceStatsCollector collector = collectors.get(key);
            if (collector == null) {
                collector = new PerformanceStatsCollector(rs, agentName, agentId, beanServer);
                collector.start();
                collectors.put(key, collector);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        for (PerformanceStatsCollector collector : collectors.values()) {
            collector.stop();
        }
        collectors.clear();
    }

}
