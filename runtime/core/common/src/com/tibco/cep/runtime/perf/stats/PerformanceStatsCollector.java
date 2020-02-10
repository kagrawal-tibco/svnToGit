package com.tibco.cep.runtime.perf.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.jmx.AnnotationAwareStandardMBean;
import com.tibco.cep.runtime.management.ManagementCentral;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.perf.stats.impl.AllDestinationsPerfStatsMXBeanImpl;
import com.tibco.cep.runtime.perf.stats.impl.AllTPoolsJQueuesPerfStatsMXBeanImpl;
import com.tibco.cep.runtime.perf.stats.impl.DestinationPerfStatsAggregator;
import com.tibco.cep.runtime.perf.stats.impl.DestinationPerfStatsMXBeanImpl;
import com.tibco.cep.runtime.perf.stats.impl.EnginePerfStatsAggregator;
import com.tibco.cep.runtime.perf.stats.impl.EnginePerfStatsMXBeanImpl;
import com.tibco.cep.runtime.perf.stats.impl.EventsPerfStatsAggregator;
import com.tibco.cep.runtime.perf.stats.impl.EventsPerfStatsMXBeanImpl;
import com.tibco.cep.runtime.perf.stats.impl.EnginePerfStatsAccumulator;
import com.tibco.cep.runtime.perf.stats.impl.TPoolJQueuePerfStatsMXBeanImpl;
import com.tibco.cep.runtime.perf.stats.impl.TPoolJQueueStatsAggregator;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.FQName;

public class PerformanceStatsCollector {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(PerformanceStatsCollector.class);

    private PerfStatsProperties.THREADING_MODEL threadingModel;

    private ThreadGroup threadGroup;

    private int queueCapacity;

    private List<StatsAggregator> aggregators;

    private MetricTableDataListener metricTableDataListener;

    private MetricTable metricTable;

    private RuleSession ruleSession;

    PerformanceStatsCollector(RuleSession rs, String agentName, int agentId, MBeanServer beanServer) {
        this.ruleSession = rs;
        this.aggregators = new LinkedList<StatsAggregator>();
        //get the configured threading model
        threadingModel = PerfStatsProperties.THREADING_MODEL.SINGLE;
        String value = rs.getRuleServiceProvider().getProperties().getProperty(PerfStatsProperties.THREADING_MODEL_PROPERTY_KEY);
        if (value != null) {
            try {
                threadingModel = PerfStatsProperties.THREADING_MODEL.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.WARN, "Illegal value(%s) for %s, default to %s",value, PerfStatsProperties.THREADING_MODEL_PROPERTY_KEY, PerfStatsProperties.THREADING_MODEL.SINGLE);
                threadingModel = PerfStatsProperties.THREADING_MODEL.SINGLE;
            }
        }
        //get the configured stats queue capacity
        queueCapacity = PerfStatsProperties.DEFAULT_STATS_QUEUE_CAPACITY;
        value = rs.getRuleServiceProvider().getProperties().getProperty(PerfStatsProperties.STATS_QUEUE_CAPACITY);
        if (value != null) {
            try {
                queueCapacity = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARN, "Illegal value(%s) for %s, default to %d",value, PerfStatsProperties.STATS_QUEUE_CAPACITY, PerfStatsProperties.DEFAULT_STATS_QUEUE_CAPACITY);
                queueCapacity = PerfStatsProperties.DEFAULT_STATS_QUEUE_CAPACITY;
            }
        }
        //create the stats aggregator thread group
        threadGroup = new ThreadGroup("be.performance.stats.aggregators");
        //create beans and get the raw aggregators
        List<StatsAggregator> rawAggregators = createMBeans(agentName, agentId, beanServer);
        //create proxy aggregators
        aggregators = createProxyAggregators(rawAggregators, queueCapacity);
        //create metric table data listener
        metricTableDataListener = new MetricTableDataListener(agentName+agentId,aggregators);
    }


    private List<StatsAggregator> createMBeans(String agentName, int agentId, final MBeanServer beanServer) {
        try {
            List<StatsAggregator> aggregatorList = new LinkedList<StatsAggregator>();

            //create base name
            String baseName = "com.tibco.be:type=Agent,agentId=" + agentId + ",service=Stats";

            //EnginePerfStatsMXBean
            boolean accumulateStats = Boolean.parseBoolean(System.getProperty("com.tibco.be.metric.stats.accumulate", "true"));
            EnginePerfStatsAggregator enginePerformanceStatsAggregator = accumulateStats ? new EnginePerfStatsAccumulator() : new EnginePerfStatsAggregator();
            ObjectName objectName = new ObjectName(baseName + ",stat=Engine");
            beanServer.registerMBean(new AnnotationAwareStandardMBean(new EnginePerfStatsMXBeanImpl(enginePerformanceStatsAggregator), EnginePerfStatsMXBean.class, true), objectName);
            aggregatorList.add(enginePerformanceStatsAggregator);

            //DestinationPerfStatsMXBean
            DestinationPerfStatsAggregator destinationPerfStatsAggregator = new DestinationPerfStatsAggregator(ruleSession);
            String destinationsBaseName = baseName + ",stat=Destinations";
            objectName = new ObjectName(destinationsBaseName + ",name=All");
            beanServer.registerMBean(new AnnotationAwareStandardMBean(new AllDestinationsPerfStatsMXBeanImpl(destinationPerfStatsAggregator), AllDestinationsPerfStatsMXBean.class, true), objectName);
            Collection<DestinationStats> destinationStats = destinationPerfStatsAggregator.getDestinationStats();
            for (DestinationStats destinationStat : destinationStats) {
                objectName = new ObjectName(destinationsBaseName + ",name="+destinationStat.getName());
                beanServer.registerMBean(new AnnotationAwareStandardMBean(new DestinationPerfStatsMXBeanImpl(destinationStat), DestinationPerfStatsMXBean.class, true), objectName);
            }
            aggregatorList.add(destinationPerfStatsAggregator);

            //EventPerfStatsMXBean
            EventsPerfStatsAggregator eventsPerfStatsAggregator = new EventsPerfStatsAggregator(agentName, agentId);
            objectName = new ObjectName(baseName + ",stat=Events");
            beanServer.registerMBean(new AnnotationAwareStandardMBean(new EventsPerfStatsMXBeanImpl(eventsPerfStatsAggregator), EventsPerfStatsMXBean.class, true), objectName);
            aggregatorList.add(eventsPerfStatsAggregator);

            //TPoolsJQueuesPerfStatsMXBean
            final TPoolJQueueStatsAggregator tPoolJQueueStatsAggregator = new TPoolJQueueStatsAggregator(ruleSession);
            final String tPoolJQueuesBaseName = baseName + ",stat=tpool";
            objectName = new ObjectName(tPoolJQueuesBaseName + ",name=All");
            beanServer.registerMBean(new AnnotationAwareStandardMBean(new AllTPoolsJQueuesPerfStatsMXBeanImpl(tPoolJQueueStatsAggregator), AllTPoolsJQueuesPerfStatsMXBean.class, true), objectName);
            Collection<TPoolJQueueStats> tPoolJQueueStats = tPoolJQueueStatsAggregator.getThreadPoolJobQueueStats();
            for (TPoolJQueueStats tPoolJQueueStat : tPoolJQueueStats) {
                objectName = new ObjectName(tPoolJQueuesBaseName + ",name="+tPoolJQueueStat.getName());
                if (!beanServer.isRegistered(objectName)) {
                	beanServer.registerMBean(new AnnotationAwareStandardMBean(new TPoolJQueuePerfStatsMXBeanImpl(tPoolJQueueStat), TPoolJQueuePerfStatsMXBean.class, true), objectName);
                }
            }
            aggregatorList.add(tPoolJQueueStatsAggregator);

            //we do not need to maintain a reference to the observer since com.tibco.cep.runtime.perf.stats.impl.TPoolJQueueStatsAggregator.close() deletes all observers
            tPoolJQueueStatsAggregator.addObserver(new Observer() {

                @Override
                public void update(Observable o, Object arg) {
                    FQName name = (FQName) arg;
                    try {
                        TPoolJQueueStats stat = tPoolJQueueStatsAggregator.getThreadPoolJobQueueStat(name);
                        ObjectName objectName = new ObjectName(tPoolJQueuesBaseName + ",name="+stat.getName());
                        beanServer.registerMBean(new AnnotationAwareStandardMBean(new TPoolJQueuePerfStatsMXBeanImpl(stat), TPoolJQueuePerfStatsMXBean.class, true), objectName);
                    } catch (Exception e) {
                        LOGGER.log(Level.WARN, "could not register thread pool and job queue stats mbean for %s", name, e);
                    }
                }
            });

            return aggregatorList;
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "could not register performance stats mbeans",e);
            return Collections.emptyList();
        }
    }

    private List<StatsAggregator> createProxyAggregators(List<StatsAggregator> aggregators, int capacity) {
        if (threadingModel.compareTo(PerfStatsProperties.THREADING_MODEL.NONE) == 0) {
            return aggregators;
        }
        if (threadingModel.compareTo(PerfStatsProperties.THREADING_MODEL.SINGLE) == 0) {
            ArrayList<StatsAggregator> proxyAggregators = new ArrayList<StatsAggregator>(1);
            proxyAggregators.add(new MultipleStatsAggregatorThreadableProxy(aggregators, capacity));
            return proxyAggregators;
        }
        List<StatsAggregator> proxyAggregators = new ArrayList<StatsAggregator>(aggregators.size());
        for (StatsAggregator statsAggregator : aggregators) {
            proxyAggregators.add(new SingleStatsAggregatorThreadableProxy(statsAggregator, capacity));
        }
        return proxyAggregators;
    }

    void start() {
        //start thread(s) for aggregators if needed
        if (threadingModel.compareTo(PerfStatsProperties.THREADING_MODEL.SINGLE) == 0) {
            MultipleStatsAggregatorThreadableProxy proxyAggregator = (MultipleStatsAggregatorThreadableProxy) aggregators.get(0);
            Thread aggregatorThread = new Thread(threadGroup, proxyAggregator, "Stats_Aggregator");
            aggregatorThread.start();
        }
        else if (threadingModel.compareTo(PerfStatsProperties.THREADING_MODEL.MULTI) == 0) {
            int i = 1;
            for (StatsAggregator proxyAggregator : aggregators) {
                Thread t = new Thread(threadGroup, (SingleStatsAggregatorThreadableProxy)proxyAggregator, "Stats_Aggregator_"+i);
                t.start();
                i++;
            }
        }
        else if (threadingModel.compareTo(PerfStatsProperties.THREADING_MODEL.NONE) == 0) {
            //do nothing
        }
        //add metric table data listener to metric table
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        ManagementCentral managementCentral = registry.getService(ManagementCentral.class);
        metricTable = managementCentral.getMetricTable();
        metricTable.registerListener(metricTableDataListener, new FQName(metricTableDataListener.getName()));
    }

    void stop() {
        //unregister the metric table listener first so we dont get unwanted metric data updates
        metricTable.unregisterListener(metricTableDataListener.getName(), new FQName(metricTableDataListener.getName()));
        //stop all the aggregators
        for (StatsAggregator aggregator : aggregators) {
            aggregator.close();
        }
        //should we wait for all threads to end?
        metricTableDataListener = null;
        metricTable = null;
    }

}
