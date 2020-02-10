package com.tibco.cep.bemm.monitoring.metric.probe;

import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.AccumulatorPlugin;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.MapEntry;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Mapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.MultiMapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Property;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.AbstractAccumulator;
import com.tibco.rta.Fact;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;


public class PollerProbe implements Runnable{

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(PollerProbe.class);
	private boolean isRunning = true;
	private AbstractAccumulator accumulator;
	private String schemaName;
	private int pollInterval = 15000;
	List<Mapping> outputMapperList;
	List<MapEntry> mapEntryList;
	List<MultiMapping> outputMultiMapperList;
	Map<Monitorable,AbstractAccumulator> monitorableEntityMap=null;
	boolean isSystemPlugin=false;
	private MessageService messageService;

	public PollerProbe(String schemaName, AbstractAccumulator accumulator) {
		this.schemaName = schemaName;
		this.accumulator = accumulator;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}


	public void init(List<Property> list, AccumulatorPlugin accumulatorConfig) throws Exception {
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INITIALIZING_WITH_CONFIG, list));
		}
		accumulator.init(accumulatorConfig);

		String interval = getPropertyValue(AccumulatorConstants.PROPERTY_POLLING_INTERVAL,accumulatorConfig.getConfig().getProperty());
		String systemPluginProp = getPropertyValue(AccumulatorConstants.PROPERTY_SYSTEM_PLUGIN,accumulatorConfig.getConfig().getProperty());
		if(systemPluginProp!=null && !systemPluginProp.isEmpty() && systemPluginProp.equals("true")){
			isSystemPlugin=true;
		}


		try {
			pollInterval = Integer.parseInt(interval);
		} catch (NumberFormatException nfe) {
			LOGGER.log(Level.WARN, messageService.getMessage(MessageKey.INVALID_POLL_INTERVAL_PARAMETER, nfe, interval), pollInterval);
		}
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CONFIGURAED_POLL_INTERVAL, pollInterval));
		}
	}

	public void start(List<MapEntry> mapEntryList) {
		this.mapEntryList = mapEntryList;
		Thread t = new Thread(this, "teagent-metrics-collector");
		t.start();
	}

	public void stop() {
		this.isRunning = false;
		accumulator.stop();
	}

	@Override
	public void run() {
		while (isRunning) {
			Map<Monitorable,Object> monitorableEntitiesRegistry=null;
			//Checking if it is a system plugin to collect beteagent related stats
			if(!isSystemPlugin){
				try {
					if(BEMMServiceProviderManager.getInstance().getEntityMonitoringService()!=null)
						monitorableEntitiesRegistry=BEMMServiceProviderManager.getInstance().getEntityMonitoringService().getMonitorableRegistry();
				} catch (ObjectCreationException e) {
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.GET_MONITORABLE_REGISTRY_ERROR),e);
				}

				try {
					if(monitorableEntitiesRegistry!=null && monitorableEntitiesRegistry.size()!=0) {	
						if(mapEntryList!=null){
							for(MapEntry entry : mapEntryList){
								LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.PROCESSING_MAPENTRY));
								accumulator.publishFacts(entry.getMapping(),entry.getMultiMapping(),monitorableEntitiesRegistry,schemaName);
							}
						}
					}
					Thread.sleep(pollInterval);
				}
				catch (Exception e) {
					LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.METRIC_COLLECTION_ERROR),e);
				}
			}
			else{
				try {
					if(BEMMServiceProviderManager.getInstance().getBeTeaAgentMonitoringService()!=null)
						monitorableEntitiesRegistry=BEMMServiceProviderManager.getInstance().getBeTeaAgentMonitoringService().getMonitorableRegistry();
				} catch (ObjectCreationException e) {
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.GET_MONITORABLE_REGISTRY_ERROR),e);
				}

				try {
					if(monitorableEntitiesRegistry!=null && monitorableEntitiesRegistry.size()!=0) {	
						if(mapEntryList!=null){
							for(MapEntry entry : mapEntryList){
								
									LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.PROCESSING_MAPENTRY));
									accumulator.publishFacts(entry.getMapping(),entry.getMultiMapping(),monitorableEntitiesRegistry,schemaName);
								
							}
						}
					}

					Thread.sleep(pollInterval);
				}
				catch (Exception e) {
					LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.METRIC_COLLECTION_ERROR),e);
				}

			}
		}
	}

	public void publishFact(String schemaName, List<Fact> factList) throws ObjectCreationException, Exception{
		if(factList == null || factList.isEmpty()) {
			return;
		}
		for(Fact fact:factList)
			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);
	}

	public void publishFact(Fact fact) throws ObjectCreationException, Exception{
		if(fact == null){
			return;
		}
		BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);
	}

	public String getPropertyValue(String propertyName, List<Property> propList)
	{
		for(Property prop : propList) {
			if(prop!=null)
				if(prop.getName().equals(propertyName))
					return prop.getValue();
		}
		return "";
	}


}
