package com.tibco.cep.bemm.monitoring.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.tibco.cep.bemm.model.Monitorable.ENTITY_TYPE;
import com.tibco.cep.bemm.monitoring.metric.config.Entity;
import com.tibco.cep.bemm.monitoring.metric.config.Metric;
import com.tibco.cep.bemm.monitoring.metric.config.MetricsConfig;

public class EntityMetricsCollectionConfigLoader {

	private static final String MONITOR_STATS_CONFIG_JAXB_PACKAGE = "com.tibco.cep.bemm.monitoring.metric.config";
	private static EntityMetricsCollectionConfigLoader instance = null;
	
	private Map<ENTITY_TYPE, List<Metric>> statsConfigMap = null;
	
	private EntityMetricsCollectionConfigLoader(String statsConfigFilePath) throws Exception {
		loadStatsConfig(statsConfigFilePath);
	}
	
	static synchronized EntityMetricsCollectionConfigLoader getInstance(String statsConfigFilePath) throws Exception {
		if (instance == null) {
			instance = new EntityMetricsCollectionConfigLoader(statsConfigFilePath);
		}
		return instance;
	}

	static synchronized EntityMetricsCollectionConfigLoader getInstance() {
		return instance;
	}

	private MetricsConfig parseConfigFile(String statsConfigFilePath) throws Exception {
		MetricsConfig statsConfig = null;
		try {
			File configFile = new File(statsConfigFilePath);
			FileInputStream fis = new FileInputStream(configFile);
			JAXBContext jaxbContext = JAXBContext.newInstance(MONITOR_STATS_CONFIG_JAXB_PACKAGE, this.getClass().getClassLoader());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			statsConfig = (MetricsConfig) unmarshaller.unmarshal(fis);
		} catch (JAXBException jbex) {
			jbex.printStackTrace();
			throw new Exception(jbex);
		}
		return statsConfig;
	}
	
	private void loadStatsConfig(String statsConfigFilePath) throws Exception {
		MetricsConfig statsConfig = parseConfigFile(statsConfigFilePath);
		this.statsConfigMap = new HashMap<>();
		for (Entity entity : statsConfig.getEntity()) {
			for (Metric stat : entity.getMetrics().getMetric()) {
				List<Metric> entityStats = statsConfigMap.get(entity.getType());
				if (entityStats == null) {
					entityStats = new ArrayList<Metric>();
					statsConfigMap.put(ENTITY_TYPE.valueOf(entity.getType()), entityStats);
				}
				entityStats.add(stat);
			}	
		}		
	}
	
	public List<Metric> getEntityStatsConfig(ENTITY_TYPE entityType) {
		List<Metric> stats = statsConfigMap.get(entityType);
		return Collections.unmodifiableList(stats);
	}

}
