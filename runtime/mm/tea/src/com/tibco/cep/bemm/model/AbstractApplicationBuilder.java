package com.tibco.cep.bemm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentClassesConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.AgentsConfig;
import com.tibco.be.util.config.cdd.BusinessworksConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.EvictionConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.LocalCacheConfig;
import com.tibco.be.util.config.cdd.LogConfigConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitsConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.cdd.RevisionConfig;
import com.tibco.be.util.config.cdd.SharedQueueConfig;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Abstract class to build application hierarchy
 * 
 * @author dijadhav
 *
 * @param <T>
 */
public abstract class AbstractApplicationBuilder<T> implements ApplicationBuilder<T> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractApplicationBuilder.class);

	protected BEMMModelFactory beMMModelFactory = null;
	protected BEApplicationsManagementDataStoreService<?> dataStoreService;
	private MessageService messageService;
	
	public AbstractApplicationBuilder() {
		beMMModelFactory = BEMMModelFactoryImpl.getInstance();
		try {
			dataStoreService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
		}
	}

	@Override
	public void enrichApplicationConfig(Application application, DeploymentVariables applicationConfig)
			throws Exception {
		if (null == applicationConfig)
			return;

		NameValuePairs nameValuePairs = applicationConfig.getNameValuePairs();
		if (null == nameValuePairs)
			return;

		if (null == nameValuePairs.getNameValuePair())
			return;
		for (NameValuePair nameValuePair : nameValuePairs.getNameValuePair()) {
			if (null != nameValuePair) {
				if ("creationTime".equals(nameValuePair.getName())) {
					application.setCreationTime(Long.parseLong(nameValuePair.getValue()));
				}
				if (Constants.CONNECT_TO_CLUSTER.equals(nameValuePair.getName())) {
					application.setConnectToCluster(Boolean.valueOf(nameValuePair.getValue()));
				}
				if (Constants.TEA_AGENT_APP_CLUSTER_LISTEN_URL.equals(nameValuePair.getName())) {
					application.setListenURL(nameValuePair.getValue());
				}
				if (Constants.IS_MONITORABLE_ONLY.equals(nameValuePair.getName())) {
					application.setMonitorableOnly(Boolean.valueOf(nameValuePair.getValue()));
				}
			}
		}
	}

	@Override
	public void enrichClusterConfigData(Application application, ClusterConfig clusterConfig) {
		try {
			if (!application.isMonitorableOnly())
			// Get Application Config
			setApplicationInfo(application, clusterConfig);

			// Resolve the data feed handlers
			resolveFeedDataHandler(application, clusterConfig);
			if (!application.isMonitorableOnly()) {
			// Enrich the PU configs in Application
			ProcessingUnitsConfig processingUnitsConfig = clusterConfig.getProcessingUnits();
			List<ProcessingUnitConfig> processingUnitConfigs = processingUnitsConfig.getProcessingUnit();
			Map<String, com.tibco.cep.bemm.model.AgentConfig> agentMap = new HashMap<String, com.tibco.cep.bemm.model.AgentConfig>();
			for (ProcessingUnitConfig processingUnitConfig : processingUnitConfigs) {

				ProcessingUnit processingUnit = beMMModelFactory.getProcessingUnit();
				processingUnit.setApplicationConfig(application);
				processingUnit.setPuId(processingUnitConfig.getId());
				setHotDeploy(processingUnit, processingUnitConfig);
				setEnableCacheStorage(processingUnit, processingUnitConfig);
				setEnableDBConcept(processingUnit, processingUnitConfig);
				setLogConfig(processingUnit, processingUnitConfig);
				Map<Object, Object> props = new TreeMap<>();
				props.putAll(clusterConfig.getPropertyGroup().toProperties());
				AgentsConfig agentsConfig = processingUnitConfig.getAgents();
				if (null != agentsConfig) {

					for (AgentConfig agentConfig : agentsConfig.getAgent()) {
						if (null != agentConfig) {

							AgentType type = null;

							AgentClassConfig typeClass = agentConfig.getRef();
							String agentName = typeClass.getId();
							if (typeClass instanceof InferenceAgentClassConfig) {
								props.putAll(((InferenceAgentClassConfig) typeClass).getPropertyGroup().toProperties());
								type = AgentType.INFERENCE;
							} else if (typeClass instanceof QueryAgentClassConfig) {
								props.putAll(((QueryAgentClassConfig) typeClass).getPropertyGroup().toProperties());
								type = AgentType.QUERY;
							} else if (typeClass instanceof CacheAgentClassConfig) {
								props.putAll(((CacheAgentClassConfig) typeClass).getPropertyGroup().toProperties());
								type = AgentType.CACHE;
							} else if (typeClass instanceof ProcessAgentClassConfig) {
								props.putAll(((ProcessAgentClassConfig) typeClass).getPropertyGroup().toProperties());
								type = AgentType.PROCESS;
							} else {
								props.putAll(((DashboardAgentClassConfig) typeClass).getPropertyGroup().toProperties());
								type = AgentType.DASHBOARD;
							}
							String key = application.getName() + "/Agents/" + agentName;
							com.tibco.cep.bemm.model.AgentConfig agent = agentMap.get(key);
							if (null == agent) {
								agent = beMMModelFactory.getAgentConfig();
							}
							agent.setAgentName(agentName);
							agent.setAgentType(type);
							setPriority(agent, agentConfig);
							setKey(agent, agentConfig);
							agent.setTeaObjectKey(key);
							setConfigInfo(agent, clusterConfig, typeClass);
							if (!agentMap.containsKey(key)) {
								agentMap.put(key, agent);
							}
							processingUnit.addAgent(agent);
						}
					}
				}
				props.putAll(processingUnitConfig.getPropertyGroup().toProperties());
				if (null != props && !props.isEmpty()) {
					for (Entry<Object, Object> entry : props.entrySet()) {
						if (null != entry) {
							Object key = entry.getKey();
							if (null != key) {
								Object value = entry.getValue();
								if (null == value) {
									processingUnit.put(key.toString(), "");
								} else {
									processingUnit.put(key.toString(), value.toString());
								}
							}
						}
					}
				}

				application.addProcessingUnit(processingUnit);
			}
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}

	private void setApplicationInfo(Application application, ClusterConfig clusterConfig) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.SETTING_CDD_APPLICATION_CONFIG_DETAILS));

		Map<Object, Object> properties = clusterConfig.toProperties();
		if (null != properties && !properties.isEmpty()) {
			Object externalClassDir = properties.get(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName());
			if (null != externalClassDir) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CDD_EXTERNAL_CLASSES_PATH_PROPERTY_SPECIFIED));
				application.setExternalClassesPath(externalClassDir.toString());
			} else {
				application.setExternalClassesPath(null);
			}
			Object ruleTempleDeployDir = properties.get(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
			if (null != ruleTempleDeployDir) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RULE_TEMPLATE_SPECIFIED_IN_CDD));
				application.setRuleTemplateDeployDir(ruleTempleDeployDir.toString());
			} else {
				application.setRuleTemplateDeployDir(null);
			}
		}
		OverrideConfig nameConfig = clusterConfig.getName();
		if (null != nameConfig) {

			FeatureMap nameMixed = nameConfig.getMixed();
			if (null != nameMixed && nameMixed.size() > 0) {
				String name = nameMixed.getValue(0).toString();				
				application.setClusterName(ManagementUtil.resolveGV(name,dataStoreService,application.getName()));
			}
		}

		CacheManagerConfig cacheManager = clusterConfig.getObjectManagement().getCacheManager();
		if (cacheManager != null) {
			application.setInMemoryMode(false);
		} else {
			application.setInMemoryMode(true);
		}

		RevisionConfig revision = clusterConfig.getRevision();
		if (null != revision) {
			// Set author
			OverrideConfig authorConfig = revision.getAuthor();
			if (null != authorConfig) {
				FeatureMap authorMixed = authorConfig.getMixed();
				if (null != authorMixed && authorMixed.size() > 0) {
					String author = authorMixed.getValue(0).toString();
					application.setAuthor(author);
				}
			}
			// Set comment
			OverrideConfig commentConfig = revision.getComment();
			if (null != commentConfig) {
				FeatureMap commentMixed = commentConfig.getMixed();
				if (null != commentMixed && commentMixed.size() > 0) {
					String comments = commentMixed.getValue(0).toString();
					application.setComments(comments);
				}
			}

			// Set date
			OverrideConfig dateConfig = revision.getDate();
			if (null != dateConfig) {
				FeatureMap dateMixed = dateConfig.getMixed();
				if (null != dateMixed && dateMixed.size() > 0) {
					String date = dateMixed.getValue(0).toString();
					application.setDate(date);
				}
			}

			// Set version
			String version = revision.getVersion();
			if (null != version) {
				application.setCddVersion(version);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void resolveFeedDataHandler(Application application, ClusterConfig clusterConfig) {
		// Create Properties
		Properties properties = new Properties();
		properties.put(Constants.APPLICATION_NAME, application.getName());

		try {
			ApplicationDataFeedHandler<ServiceInstance> applicationJMXDataFeedHandler = application
					.getApplicationDataFeedHandler(OMEnum.JMX);
			if (applicationJMXDataFeedHandler == null) {
				applicationJMXDataFeedHandler = (ApplicationDataFeedHandler<ServiceInstance>) BEMMServiceProviderManager
						.getInstance().getApplicationDataFeedHandler(OMEnum.JMX);
				applicationJMXDataFeedHandler.init(properties);
				application.addApplicationDataFeedHandler(OMEnum.JMX, applicationJMXDataFeedHandler);
			}
		} catch (ObjectCreationException ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}

		if (!application.isMonitorableOnly() && application.connectToCluster()) {
			CacheManagerConfig cacheManager = clusterConfig.getObjectManagement().getCacheManager();
			Map<Object, Object> maps = cacheManager.getProvider().toProperties();
			if (null != maps && maps.containsKey(Constants.BE_DAO_PROVIDER_TYPE)) {
				String type = maps.get(Constants.BE_DAO_PROVIDER_TYPE).toString();

				if (Constants.TIBCO.equalsIgnoreCase(type)) {
					try {
						java.util.Properties cddProperties = (java.util.Properties) clusterConfig.toProperties();
						String discoveryURL = cddProperties.getProperty(ASConstants.PROP_DISCOVER_URL);
						ApplicationDataFeedHandler<Application> applicationDataFeedHandler = application
								.getApplicationDataFeedHandler(OMEnum.TIBCO);
						if (applicationDataFeedHandler == null) {
							// Set Discovery URL
							application.setDiscoveryURL(discoveryURL);
							// Init & connect to the feed handler
							applicationDataFeedHandler = (ApplicationDataFeedHandler<Application>) BEMMServiceProviderManager
									.getInstance().getApplicationDataFeedHandler(OMEnum.TIBCO);
							applicationDataFeedHandler.init(properties);
							applicationDataFeedHandler.connect(application);
							// Add the feed handler
							application.addApplicationDataFeedHandler(OMEnum.TIBCO, applicationDataFeedHandler);
						} else {
							// if discovery URL has changed, so reconnect with
							// new URL
							if (null != discoveryURL && !discoveryURL.isEmpty()
									&& !discoveryURL.equals(application.getDiscoveryURL())) {
								application.removeApplicationDataFeedHandler(OMEnum.TIBCO);
								application.setDiscoveryURL(discoveryURL);
								applicationDataFeedHandler.disconnect(application);
								applicationDataFeedHandler.connect(application);
								application.addApplicationDataFeedHandler(OMEnum.TIBCO, applicationDataFeedHandler);
							}
						}
					} catch (ObjectCreationException ex) {
						LOGGER.log(Level.ERROR, ex, ex.getMessage());
					}

				} else if (Constants.COHERENCE.equalsIgnoreCase(type)) {
					ApplicationDataFeedHandler<?> applicationCoherenceDataFeedHandler;
					try {
						ApplicationDataFeedHandler<Application> applicationDataFeedHandler = application
								.getApplicationDataFeedHandler(OMEnum.COHERENCE);
						if (applicationDataFeedHandler == null) {
							applicationCoherenceDataFeedHandler = BEMMServiceProviderManager.getInstance()
									.getApplicationDataFeedHandler(OMEnum.COHERENCE);
							applicationCoherenceDataFeedHandler.init(properties);
							application.addApplicationDataFeedHandler(OMEnum.COHERENCE,
									applicationCoherenceDataFeedHandler);
						}
					} catch (ObjectCreationException ex) {
						LOGGER.log(Level.ERROR, ex, ex.getMessage());
					}
				}
			}
		}
	}

	private void setConfigInfo(com.tibco.cep.bemm.model.AgentConfig agent, ClusterConfig clusterConfig,
			AgentClassConfig typeClass) throws ObjectCreationException {
		AgentClassesConfig agentClassesConfig = clusterConfig.getAgentClasses();
		if (null != agentClassesConfig) {
			if (typeClass instanceof InferenceAgentClassConfig) {
				EList<InferenceAgentClassConfig> inferenceAgents = agentClassesConfig.getInferenceAgentConfig();
				if (null != inferenceAgents) {
					for (InferenceAgentClassConfig inferenceAgentClassConfig : inferenceAgents) {
						if (null != inferenceAgentClassConfig
								&& inferenceAgentClassConfig.getId().equals(agent.getAgentName())) {

							// Set BW URI
							BusinessworksConfig businessworks = inferenceAgentClassConfig.getBusinessworks();
							if (null != businessworks) {
								String bwURI = businessworks.getUri();
								agent.setBwURI(bwURI);
							}
							setLocalCache(agent, inferenceAgentClassConfig);
							setSharedQueue(agent, inferenceAgentClassConfig);

							setConcurrentRTC(agent, inferenceAgentClassConfig);
							setCheckforDuplicates(agent, inferenceAgentClassConfig);
							setMaxActive(agent, inferenceAgentClassConfig);

							break;
						}
					}
				}
			} else if (typeClass instanceof QueryAgentClassConfig) {

				EList<QueryAgentClassConfig> queryAgentClassConfigs = agentClassesConfig.getQueryAgentConfig();
				if (null != queryAgentClassConfigs) {
					for (QueryAgentClassConfig queryAgentClassConfig : queryAgentClassConfigs) {
						if (null != queryAgentClassConfig && queryAgentClassConfig.getId().equals(agent.getAgentName())) {
							setLocalCache(agent, queryAgentClassConfig);
							setSharedQueue(agent, queryAgentClassConfig);
							setMaxActive(agent, queryAgentClassConfig);
							break;
						}
					}
				}

			} else if (typeClass instanceof CacheAgentClassConfig) {
			} else if (typeClass instanceof DashboardAgentClassConfig) {

			}
		}

	}

	/**
	 * @param agent
	 * @param inferenceAgentClassConfig
	 */
	private void setConcurrentRTC(com.tibco.cep.bemm.model.AgentConfig agent,
			InferenceAgentClassConfig inferenceAgentClassConfig) {
		// Set Concurrent RTC
		OverrideConfig concurrentRTCConfig = inferenceAgentClassConfig.getConcurrentRtc();
		if (null != concurrentRTCConfig) {
			FeatureMap currentRTCMixed = concurrentRTCConfig.getMixed();
			if (null != currentRTCMixed && currentRTCMixed.size() > 0) {
				String currntRTCStr = currentRTCMixed.getValue(0).toString();
				boolean concurrentRTC = Boolean.parseBoolean(currntRTCStr);
				agent.setConcurrentRTC(concurrentRTC);
			}
		}
	}

	/**
	 * @param agent
	 * @param inferenceAgentClassConfig
	 */
	private void setCheckforDuplicates(com.tibco.cep.bemm.model.AgentConfig agent,
			InferenceAgentClassConfig inferenceAgentClassConfig) {
		// Set Concurrent RTC
		OverrideConfig checkForDuplicatesConfig = inferenceAgentClassConfig.getCheckForDuplicates();
		if (null != checkForDuplicatesConfig) {
			FeatureMap checkForDuplicatesMixed = checkForDuplicatesConfig.getMixed();
			if (null != checkForDuplicatesMixed && checkForDuplicatesMixed.size() > 0) {
				String checkForDuplicatesStr = checkForDuplicatesMixed.getValue(0).toString();
				boolean checkForDuplicates = Boolean.parseBoolean(checkForDuplicatesStr);
				agent.setCheckforDuplicates(checkForDuplicates);
			}
		}
	}

	/**
	 * @param agent
	 * @param inferenceAgentClassConfig
	 */
	private void setMaxActive(com.tibco.cep.bemm.model.AgentConfig agent, AgentClassConfig agentClassConfig) {
		// Set Max Active
		LoadConfig loadConfig = null;
		if (agentClassConfig instanceof InferenceAgentClassConfig) {

			InferenceAgentClassConfig inferenceAgentClassConfig = (InferenceAgentClassConfig) agentClassConfig;

			loadConfig = inferenceAgentClassConfig.getLoad();

		} else if (agentClassConfig instanceof QueryAgentClassConfig) {

			QueryAgentClassConfig queryAgentClassConfig = (QueryAgentClassConfig) agentClassConfig;

			loadConfig = queryAgentClassConfig.getLoad();
		}

		if (null != loadConfig) {
			OverrideConfig maxActiveConfig = loadConfig.getMaxActive();
			if (null != maxActiveConfig) {
				FeatureMap maxActiveMap = maxActiveConfig.getMixed();
				if (null != maxActiveMap && maxActiveMap.size() > 0) {
					String maxActive = maxActiveMap.getValue(0).toString();
					agent.setMaxActive(maxActive);
				}
			}

		}
	}

	private void setLocalCache(com.tibco.cep.bemm.model.AgentConfig agent, AgentClassConfig agentClassConfig)
			throws ObjectCreationException {
		LocalCacheConfig localCacheConfig = null;
		if (agentClassConfig instanceof InferenceAgentClassConfig) {

			InferenceAgentClassConfig inferenceAgentClassConfig = (InferenceAgentClassConfig) agentClassConfig;

			localCacheConfig = inferenceAgentClassConfig.getLocalCache();

		} else if (agentClassConfig instanceof QueryAgentClassConfig) {

			QueryAgentClassConfig queryAgentClassConfig = (QueryAgentClassConfig) agentClassConfig;

			localCacheConfig = queryAgentClassConfig.getLocalCache();
		}

		if (null != localCacheConfig) {

			EvictionConfig eviction = localCacheConfig.getEviction();
			if (null != eviction) {

				// Set local cache data
				LocalCache localCache = beMMModelFactory.getLocalCache();

				// Set MaxSize
				OverrideConfig maxSizeConfig = eviction.getMaxSize();
				if (null != maxSizeConfig) {
					FeatureMap maxSizeMixed = maxSizeConfig.getMixed();
					if (null != maxSizeMixed && maxSizeMixed.size() > 0) {
						String maxSize = maxSizeMixed.getValue(0).toString();
						localCache.setMaxSize(Long.parseLong(maxSize));
					}
				}
				// Set MaxTime
				OverrideConfig maxTimeConfig = eviction.getMaxTime();
				if (null != maxTimeConfig) {
					FeatureMap maxTimeMixed = maxTimeConfig.getMixed();
					if (null != maxTimeMixed && maxTimeMixed.size() > 0) {
						String maxTime = maxTimeMixed.getValue(0).toString();
						localCache.setEvictionTime(Long.parseLong(maxTime));
					}
				}

				agent.setLocalCache(localCache);
			}
		}
	}

	private void setSharedQueue(com.tibco.cep.bemm.model.AgentConfig agent, AgentClassConfig agentClassConfig)
			throws ObjectCreationException {
		SharedQueueConfig sharedQueueConfig = null;
		if (agentClassConfig instanceof InferenceAgentClassConfig) {

			InferenceAgentClassConfig inferenceAgentClassConfig = (InferenceAgentClassConfig) agentClassConfig;

			sharedQueueConfig = inferenceAgentClassConfig.getSharedQueue();

		} else if (agentClassConfig instanceof QueryAgentClassConfig) {

			QueryAgentClassConfig queryAgentClassConfig = (QueryAgentClassConfig) agentClassConfig;

			sharedQueueConfig = queryAgentClassConfig.getSharedQueue();
		}

		if (null != sharedQueueConfig) {
			// Set shared queue data
			SharedQueue sharedQueue = beMMModelFactory.getSharedQueue();

			// Set Size
			OverrideConfig sizeConfig = sharedQueueConfig.getSize();
			if (null != sizeConfig) {
				FeatureMap sizeMixed = sizeConfig.getMixed();
				if (null != sizeMixed && sizeMixed.size() > 0) {
					String size = sizeMixed.getValue(0).toString();
					sharedQueue.setQueueSize(Long.parseLong(size));
				}
			}

			// Set ThreadCount
			OverrideConfig workersConfig = sharedQueueConfig.getWorkers();
			if (null != workersConfig) {
				FeatureMap workersMixed = workersConfig.getMixed();
				if (null != workersMixed && workersMixed.size() > 0) {
					String threadCount = workersMixed.getValue(0).toString();
					sharedQueue.setThreadCount(Long.parseLong(threadCount));
				}
			}

			agent.setSharedQueue(sharedQueue);

		}
	}

	private void setKey(com.tibco.cep.bemm.model.AgentConfig agent, AgentConfig agentConfig) {
		// Set comment
		OverrideConfig priorityConfig = agentConfig.getPriority();
		if (null != priorityConfig) {
			FeatureMap priorityMixed = priorityConfig.getMixed();
			if (null != priorityMixed && priorityMixed.size() > 0) {
				String priority = priorityMixed.getValue(0).toString();
				agent.setAgentPriority(priority);
			}
		}

	}

	private void setPriority(com.tibco.cep.bemm.model.AgentConfig agent, AgentConfig agentConfig) {

		// Set comment
		OverrideConfig keyConfig = agentConfig.getKey();
		if (null != keyConfig) {
			FeatureMap keyMixed = keyConfig.getMixed();
			if (null != keyMixed && keyMixed.size() > 0) {
				String key = keyMixed.getValue(0).toString();
				agent.setAgentKey(key);
			}
		}

	}

	private void setEnableDBConcept(ProcessingUnit processingUnit, ProcessingUnitConfig processingUnitConfig) {
		// Set DB concept enabled
		OverrideConfig dbConceptEnabledConfig = processingUnitConfig.getDbConcepts();
		if (null != dbConceptEnabledConfig) {
			FeatureMap dbConceptMixed = dbConceptEnabledConfig.getMixed();
			if (null != dbConceptMixed && dbConceptMixed.size() > 0) {
				String dbConceptEnabled = dbConceptMixed.getValue(0).toString();
				processingUnit.setEnableDBConcept(Boolean.parseBoolean(dbConceptEnabled));
			}
		}
	}

	private void setEnableCacheStorage(ProcessingUnit processingUnit, ProcessingUnitConfig processingUnitConfig) {
		// Set HotDeploy
		OverrideConfig cacheStorageEnabledConfig = processingUnitConfig.getCacheStorageEnabled();
		if (null != cacheStorageEnabledConfig) {
			FeatureMap cacheStorageEnabledMixed = cacheStorageEnabledConfig.getMixed();
			if (null != cacheStorageEnabledMixed && cacheStorageEnabledMixed.size() > 0) {
				String cacheStorageEnabled = cacheStorageEnabledMixed.getValue(0).toString();
				processingUnit.setEnableCacheStorage(Boolean.parseBoolean(cacheStorageEnabled));
			}
		}
	}

	private void setHotDeploy(ProcessingUnit processingUnit, ProcessingUnitConfig processingUnitConfig) {
		// Set HotDeploy
		OverrideConfig hotDeployConfig = processingUnitConfig.getHotDeploy();
		boolean hotDeploy = Boolean.parseBoolean(CddTools.getValueFromMixed(hotDeployConfig, true));
		processingUnit.setHotDeploy(hotDeploy);
	}

	private void setLogConfig(ProcessingUnit processingUnit, ProcessingUnitConfig processingUnitConfig) {
		// Set LogConfig
		LogConfigConfig logConfig = processingUnitConfig.getLogs();

		if (logConfig != null) {

			processingUnit.setLogConfigId(logConfig.getId());
			if (new Boolean(CddTools.getValueFromMixed(logConfig.getEnabled(), true))) {
				if (null != logConfig.getRoles()) {
					if (null != logConfig.getRoles().getMixed()) {
						String value = CddTools.getValueFromMixed(logConfig.getRoles(), true);
						if (null != value && !value.trim().isEmpty()) {
							processingUnit.setLogConfig(value);
						}
					}
				}
			}
		}

	}
}
