package com.tibco.cep.bemm.monitoring.metric.collector.jmx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.MBeanServerConnection;

import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.collector.AbstractMetricsCollector;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsCollectorCallback;
import com.tibco.cep.bemm.monitoring.metric.collector.MonitorableEntityWrapper;
import com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler.CPUMetricJMXHandler;
import com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler.GarbageCollectorMetricJMXHandler;
import com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler.MemoryMetricJMXHandler;
import com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler.MetricTypeJMXHandler;
import com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler.ThreadMetricJMXHandler;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.rta.common.service.WorkItem;

public class JMXMetricsCollector extends AbstractMetricsCollector<JMXConnectionContext, MetricTypeJMXHandler> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JMXMetricsCollector.class);

	protected static Timer timer = new Timer("RemoteMetricsJMXCollector", true);
		 
	private MetricCollectingTimerTask collectorTimerTask = null;
	private MetricsCollectorCallback metricsCollectorCallback = null;
    private long frequency;
	
	public JMXMetricsCollector() {
		this.frequency = 30000;
	}
	
	@Override
	public void init(Properties Configuration) throws Exception {
		super.init(Configuration);
	}

	@Override
	public List<MetricTypeJMXHandler> getDefaultMetricHandlers() {
		List<MetricTypeJMXHandler> jmxHandlers = new ArrayList<>();
		jmxHandlers.add(new CPUMetricJMXHandler());
		jmxHandlers.add(new MemoryMetricJMXHandler());
		jmxHandlers.add(new ThreadMetricJMXHandler());
		jmxHandlers.add(new GarbageCollectorMetricJMXHandler());
		return Collections.unmodifiableList(jmxHandlers);
	}
	
	@Override
	public void setCallbackHandler(MetricsCollectorCallback metricCollectorCallback) {
		this.metricsCollectorCallback = metricCollectorCallback;
	}
		
	@Override
	protected JMXConnectionContext initConnection(Monitorable monitorableEntity) {
		JMXConnectionContext jmxConnContext;
        try {
    		ConnectionInfo remoteConnectionInfo = monitorableEntity.getConnectionInfo("JMX");
            JMXConnClient jmcc =  new JMXConnClient(remoteConnectionInfo.getRemoteHost(),
            		remoteConnectionInfo.getRemotePort(),
    				null, null, true);
        	MBeanServerConnection serverConnection = jmcc.connect();
        	jmxConnContext = new JMXConnectionContext(serverConnection);
        } catch (JMXConnClientException ex) {
        	String message = "Could not connect to " + monitorableEntity.getKey();
        	LOGGER.log(Level.ERROR, ex, message);
			throw new RuntimeException(message, ex);
        }
        return jmxConnContext;
	}

	@Override
	protected void start() {
		if (collectorTimerTask == null)
			collectorTimerTask = new MetricCollectingTimerTask();		
        if (collectorTimerTask.running == false) {
        	LOGGER.log(Level.DEBUG, "Scheduled timer task");
        	timer.schedule(collectorTimerTask, 100, frequency);
        	collectorTimerTask.running = true;
        }
	}

	@Override
	protected void stop() {
        if (collectorTimerTask != null) {
        	collectorTimerTask.running = false;
        	collectorTimerTask.cancel();
        	collectorTimerTask = null;
        }
	}

	@Override
	protected void closeConnection(JMXConnectionContext connContext) {
		connContext.closeConnection();	
	}

    private void collectAndPublishMetrics() {
    	Collection<MonitorableEntityWrapper<JMXConnectionContext>> registeredMonitorableEntities = this.getRegisteredMonitorableEntities();        	
		for (MonitorableEntityWrapper<JMXConnectionContext> registeredMonitorableEntity : registeredMonitorableEntities) {
			Set<String> metricTypesJMX = registeredMonitorableEntity.getMetricTypes();
			if (metricTypesJMX != null) {
				for (String metricTypeJMX : metricTypesJMX) {
					CollectAndPublishJob job = new CollectAndPublishJob (registeredMonitorableEntity, metricTypeJMX);
					collectorThreadPool.addWorkItem(job);
        		}
			}	
    	}
    }
    
    
    private void publishMetrics(Collection<Map<String, Object>> metricsCollection) {
    	for (Map<String, Object> metrics : metricsCollection) {
    		if (metricsCollectorCallback != null)
    			metricsCollectorCallback.publish(metrics);
    	}        	
    }
    


	@Override
	public String getCollectorType() {
		return COLLECTOR_TYPE.JMX.name();
	}

    class MetricCollectingTimerTask extends TimerTask {

        private boolean running;

        MetricCollectingTimerTask() {
            running = false;
        }

        @Override
        public void run() {
			collectAndPublishMetrics();
        }

        @Override
    	public boolean cancel() {
        	boolean flag = super.cancel();
        	running = false;
        	return flag;
    	}    	
    }
    
    class CollectAndPublishJob implements WorkItem {
    	
    	MonitorableEntityWrapper<JMXConnectionContext> registeredMonitorableEntity;
		String metricTypeJMX;
		
    	public CollectAndPublishJob (MonitorableEntityWrapper<JMXConnectionContext> registeredMonitorableEntity,
			String metricTypeJMX) {
    		this.registeredMonitorableEntity = registeredMonitorableEntity;
    		this.metricTypeJMX = metricTypeJMX;
    		
    	}
		@Override
		public Object call() throws Exception {
			collectAndPublish(registeredMonitorableEntity, metricTypeJMX);
			return null;
		}

		@Override
		public Object get() {
			// TODO Auto-generated method stub
			return null;
		}
		
		private void collectAndPublish(MonitorableEntityWrapper<JMXConnectionContext> registeredMonitorableEntity,
				String metricTypeJMX) {
			Collection<Map<String, Object>> metricsCollection = collectMetrics(registeredMonitorableEntity, metricTypeJMX);
			if (metricsCollection != null && !metricsCollection.isEmpty())
				publishMetrics(metricsCollection);
		}     
    	
    }

}
