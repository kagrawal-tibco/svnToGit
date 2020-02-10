package com.tibco.cep.bemm.monitoring.metric.collector;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bemm.common.ConnectionContext;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

abstract public class AbstractMetricsCollector<C extends ConnectionContext<?>, H extends MetricTypeHandler<C>> implements MetricsCollector<C, H> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractMetricsCollector.class);
	
	protected Map<String, H> defaultMetricHandlers = new HashMap<>();
	protected Map<String, H> customMetricHandlers = new HashMap<>();
	protected Map<Monitorable, MonitorableEntityWrapper<C>> monitorableEntitiesRegistry = new ConcurrentHashMap<>();
	
	protected WorkItemService collectorThreadPool;
	private MessageService messageService;

	public AbstractMetricsCollector() {
	}
	
	public void init(Properties Configuration) throws Exception {
		List<H> defaultMetricHandlerList = getDefaultMetricHandlers();
		for (H metricHandler : defaultMetricHandlerList) {
			defaultMetricHandlers.put(metricHandler.getType(), metricHandler);
		}
		
		collectorThreadPool = ServiceProviderManager.getInstance().newWorkItemService("collector");
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
	}
	
	public final synchronized void register(Monitorable monitorableEntity, String metricType) throws IOException {
		H metricHandler = getMetricHandler(metricType);		
		if (metricHandler != null) {
			if (!monitorableEntitiesRegistry.containsKey(monitorableEntity)) {
				MonitorableEntityWrapper<C> monitorableEntityWrapper = new MonitorableEntityWrapper<>(monitorableEntity);
				C connContext = initConnection(monitorableEntity);
				if (connContext != null) {
					monitorableEntityWrapper.setConnectionContext(connContext);
					monitorableEntitiesRegistry.put(monitorableEntity, monitorableEntityWrapper);
				} else {
					//log error and/or throw error
				}
			}
			MonitorableEntityWrapper<C> monitorableEntityWrapper = monitorableEntitiesRegistry.get(monitorableEntity);
			if (monitorableEntityWrapper != null) {
				if (!isRegistered(monitorableEntity, metricType)) {
					registerMetricType(monitorableEntity, metricType);
					start();
				}
			}
		} else {
			//log error and/or throw error
		}
	}

	@SuppressWarnings("unchecked")
	public final void addCustomMetricHandler(String metricType, String className) {
		try {
			H handler = (H) ManagementUtil.getInstance(className);
			customMetricHandlers.put(metricType, handler);
		} catch (ObjectCreationException ex) {
			throw new RuntimeException(messageService.getMessage(MessageKey.COULD_NOT_CREATE_HANDLER_METRICS, className, metricType), ex);
		}		
	}

	@Override
	public final synchronized void unregister(Monitorable monitorableEntity) {
		MonitorableEntityWrapper<C> monitorableEntityWrapper = monitorableEntitiesRegistry.get(monitorableEntity);
		if (monitorableEntityWrapper != null) {
			monitorableEntityWrapper.removeAllMetricTypes();
			completeUnregister(monitorableEntity);
			if (monitorableEntitiesRegistry.isEmpty()) {
				stop();
			}
		} else {
			//Log warning
		}
	}

	@Override
	public final synchronized void unregister(Monitorable monitorableEntity, String metricType) {
		MonitorableEntityWrapper<C> monitorableEntityWrapper = monitorableEntitiesRegistry.get(monitorableEntity);
		H metrichandler = getMetricHandler(metricType);
		if (metrichandler != null) {
			if (monitorableEntityWrapper != null) {
				boolean removed = monitorableEntityWrapper.removeMetricType(metricType);
				if (removed) {
					//Log warning
				}
			} else {
				//Log warning
			}
		} else {
			//Log warning
		}
		Set<String> metricTypes = monitorableEntityWrapper.getMetricTypes();
		if (metricTypes == null || metricTypes.isEmpty()) {
			completeUnregister(monitorableEntity);
		}	
		if (monitorableEntitiesRegistry.isEmpty()) {
			stop();
		}
	}

	protected final Collection<MonitorableEntityWrapper<C>> getRegisteredMonitorableEntities() {
		return this.monitorableEntitiesRegistry.values();
	}
	
    protected final Collection<Map<String, Object>> collectMetrics(MonitorableEntityWrapper<C> monitorableEntityWrapper, String metricType) {
    	Collection<Map<String, Object>> metricsCollection = null;
		H metricTypeHandler = getMetricHandler(metricType);
		if (metricTypeHandler != null) {
			try {
				metricsCollection = metricTypeHandler.getMetrics(monitorableEntityWrapper.getConnectionContext(), monitorableEntityWrapper.getMetricsContext(metricType));
			} catch (UndeclaredThrowableException e) {
                if (e.getUndeclaredThrowable() instanceof ConnectException) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    	LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.COULD_NOT_CONNECT_COLLECTING_METRICS, 
                    																					monitorableEntityWrapper.getMonitorableEntity().getKey(), 
                    																					monitorableEntityWrapper.getConnectionContext().getConnectionType(),
                    																					metricType), e);
                    }
                    unregister(monitorableEntityWrapper.getMonitorableEntity(), metricType);
                }
            } catch (IOException e) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                	LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.COULD_NOT_CONNECT_COLLECTING_METRICS, 
                																					monitorableEntityWrapper.getMonitorableEntity().getKey(), 
                																					monitorableEntityWrapper.getConnectionContext().getConnectionType(),
                																					metricType), e);
                }
                unregister(monitorableEntityWrapper.getMonitorableEntity(), metricType);
            }	
		}
		
		Map<String, Object> basicMetrics = monitorableEntityWrapper.getMonitorableEntity().getBasicAttributes();
    	if (metricsCollection != null && !metricsCollection.isEmpty()) {
        	for (Map<String, Object> metrics : metricsCollection) {
        		metrics.putAll(basicMetrics);
        		metrics.put(MetricAttribute.TIMESTAMP, System.currentTimeMillis());
            }
    	} else {
    		if (metricsCollection == null)
    			metricsCollection = new ArrayList<>();
    		metricsCollection.add(basicMetrics);
    	}
        return metricsCollection;
    }
	
    private void completeUnregister(Monitorable monitorableEntity) {
    	MonitorableEntityWrapper<C> monitorableEntityWrapper = monitorableEntitiesRegistry.get(monitorableEntity);
    	this.closeConnection(monitorableEntityWrapper.getConnectionContext());
		monitorableEntitiesRegistry.remove(monitorableEntity);
    }
    
	private H getMetricHandler(String metricType) {
		H metricHandler = defaultMetricHandlers.get(metricType);
		if (metricHandler == null && customMetricHandlers != null) {
			metricHandler = customMetricHandlers.get(metricType);
		}	
		return metricHandler;
	}	

	private final boolean isRegistered(Monitorable monitorableEntity, String metricType) {
		MonitorableEntityWrapper<C> monitorableEntityWrapper = monitorableEntitiesRegistry.get(monitorableEntity);
		return monitorableEntityWrapper.getMetricTypes().contains(metricType);
	}

	private void registerMetricType(Monitorable monitorableEntity, String metricType) throws IOException {
		H metricHandler = getMetricHandler(metricType);
		if (metricHandler != null) {
			MonitorableEntityWrapper<C> monitorableEntityWrapper = monitorableEntitiesRegistry.get(monitorableEntity);
			MetricsContext metricsContext = metricHandler.initMetricsContext(monitorableEntityWrapper.getConnectionContext());
			monitorableEntityWrapper.setMetricsContext(metricType, metricsContext);
			monitorableEntityWrapper.addMetricType(metricType);
		} else {
			//throw Exception
		}
	}
	
	abstract protected C initConnection(Monitorable monitorableEntity);
	
	abstract protected void closeConnection(C connContext);
	
	protected void start() {
		
	}
	
	protected void stop() {
		if (collectorThreadPool != null) {
			try {
				collectorThreadPool.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.STOPPING_COLLECTOR_POOL_SERVICE_ERROR));
			}
		}
	}

}
