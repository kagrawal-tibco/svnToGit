package com.tibco.tea.agent.be;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.pool.ConnectionPool;
import com.tibco.cep.bemm.common.pool.GroupJobExecutorService;
import com.tibco.cep.bemm.common.service.ApplicationDataProviderService;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.EntityMethodsDescriptorService;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.taskdefs.IdempotentRetryTask;
import com.tibco.cep.bemm.common.taskdefs.Task;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEApplicationLoadException;
import com.tibco.cep.bemm.management.service.BEAgentManagementService;
import com.tibco.cep.bemm.management.service.BEApplicationCDDCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationHostsManagementService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEHomeDiscoveryService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.Monitorable.ENTITY_TYPE;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.rule.BEEntityMetricRuleService;
import com.tibco.cep.bemm.monitoring.metric.service.AggregationService;
import com.tibco.cep.bemm.monitoring.metric.view.BEEntityMetricVisulizationService;
import com.tibco.cep.bemm.monitoring.service.BEEntityMonitoringService;
import com.tibco.cep.bemm.monitoring.service.BETeaAgentMonitoringService;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.rta.common.service.ShutdownService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.be.migration.file.generator.BeTeaAgentExportService;
import com.tibco.tea.agent.be.provider.BEAgentProvider;
import com.tibco.tea.agent.be.provider.BEApplicationHostProvider;
import com.tibco.tea.agent.be.provider.BEApplicationProvider;
import com.tibco.tea.agent.be.provider.BEMasterHostProvider;
import com.tibco.tea.agent.be.provider.BEProcessingUnitAgentProvider;
import com.tibco.tea.agent.be.provider.BEProcessingUnitProvider;
import com.tibco.tea.agent.be.provider.BEServiceInstanceProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.version.be_teagentVersion;
import com.tibco.tea.agent.server.TeaAgentServer;
import com.tibco.tea.model.listener.BEAgentStatusChangeListener;
import com.tibco.tea.model.listener.BEServiceInstanceStatusChangeListener;

/**
 * This class is used to initialize required service instance,create TEA object
 * hierarchy,Register TEA Object providers, register and start BE TEA Agent
 * 
 * @author vdhumal,dijadhav
 *
 */
public class BETeaAgentManager extends AbstractStartStopServiceImpl {
	private MessageService teaMessageService;
	/**
	 * Logger object
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaAgentManager.class);

	/**
	 * Instance of BETeaAgentManager
	 */
	public static final BETeaAgentManager INSTANCE = new BETeaAgentManager();

	/**
	 * BE Agent management service
	 */
	private BEAgentManagementService agentManagementService;
	/**
	 * Instance Management Service
	 */
	private BEServiceInstancesManagementService instanceService;
	/**
	 * Host management service
	 */
	private BEApplicationHostsManagementService hostService;

	/**
	 * Application management service
	 */
	private BEApplicationsManagementService applicationService;

	/**
	 * Entity operation descriptor service
	 */
	private EntityMethodsDescriptorService descriptorService;

	/**
	 * Data provider service
	 */
	private ApplicationDataProviderService dataProviderService;

	/**
	 * Data provider service
	 */
	private BEMasterHostManagementService masterHostManagementService;

	private Task autoRegisterTask; // store the handle so that we can clean up
									// the internals properly.

	private Properties configuration;

	private MessageService messageService;

	private MBeanService mbeanService;

	private ValidationService validationService;

	private BEApplicationsManagementDataStoreService<?> dataStoreService;

	private BEEntityMonitoringService entityMonitoringService;

	private BETeaAgentMonitoringService beTeaAgentMonitoringService;

	private AggregationService aggService;

	private BEEntityMetricVisulizationService metricVisService;

	private BEEntityMetricRuleService ruleService;

	private TeaAgentServer server;

	private BusinessEventTeaAgent beTeaAgent;

	private ShutdownService shutdownService;

	private GroupJobExecutorService groupJobExecutorService;
	private ConnectionPool<Session> jschConnectionPool;
	private LockManager lockManager;
	private BeTeaAgentExportService exportService;
	private BEHomeDiscoveryService beHomeDiscoveryService;
	private BEApplicationCDDCacheService<?> applicationCDDCacheService;
	private BEApplicationGVCacheService<?> applicationGVCacheService;
	/**
	 * Executor service for various poller jobs.
	 */
	private GroupJobExecutorService pollerJobsExecutorService;

	/**
	 * Initialized the BETeaAgentManager
	 * 
	 * @param agentPropFile
	 *            - BE-TEA Agent properties file
	 * @throws Exception
	 *             - The class Exception and its subclasses are a form of
	 *             Throwable that indicates conditions that a reasonable
	 *             application might want to catch.
	 */
	@Override
	public void init(Properties configuration) throws Exception {

		this.configuration = configuration;

		shutdownService = BEMMServiceProviderManager.getInstance().getShutdownService();
		shutdownService.init(configuration);
		teaMessageService = BEMMServiceProviderManager.getInstance().getMessageService();
		// Initialize all the BEMM management services and model
		initManagementServices();

		// Create TEA Object providers
		createTeaObjectProviders();

		// Load the complete TEA hierarchy
		loadTeaObjectHierachy();

		// Initialize all the BEMM monitoring services
		initMonitoringServices();

	}

	/**
	 * Start various services
	 * 
	 * @throws Exception
	 */
	@Override
	public void start() throws Exception {
		if (Boolean.valueOf(
				configuration.getProperty(ConfigProperty.BE_TEA_AGENT_METRICS_COLLECTION_ENABLE.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_METRICS_COLLECTION_ENABLE.getDefaultValue()))) {
			aggService.start();

			ruleService.start();

			// Perform bootstrap operations
			entityMonitoringService.performMonitoringBootstrapOperations();
		}

		beTeaAgent = BusinessEventTeaAgent.getInstance();
		beTeaAgent.init(configuration);

		Object started = startTeaServer();
		setStarted(false);
		if (started instanceof Boolean) {
			setStarted((Boolean) started);
		}

	}

	private Object startTeaServer() throws UnknownHostException, Exception {

		String agentVersion = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_VERSION.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_VERSION.getDefaultValue());
		String agentPort = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_PORT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_PORT.getDefaultValue());
		String hostName = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_HOST.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_HOST.getDefaultValue());
		hostName = JMXConnUtil.sanitizeIPv6(hostName);
		String contextPath = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_CONTEXT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_CONTEXT.getDefaultValue());
		String teaServerURL = configuration.getProperty(ConfigProperty.BE_TEA_SERVER_URL.getPropertyName(),
				ConfigProperty.BE_TEA_SERVER_URL.getDefaultValue());
		String agentName = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_NAME.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_NAME.getDefaultValue());

		String resourceLocation = configuration.getProperty(
				ConfigProperty.BE_TEA_AGENT_RESOURCES_BASE.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_RESOURCES_BASE.getDefaultValue())
				+ ConfigProperty.BE_TEA_AGENT_UI_RESOURCES_BASE.getDefaultValue();
		LOGGER.log(Level.INFO, "TEA Server : " + teaServerURL);
		String agentURL = "http://" + hostName + ":" + agentPort + contextPath;
		if (teaServerURL.startsWith("https")) {
			agentURL = "https://" + hostName + ":" + agentPort + contextPath;
		}
		LOGGER.log(Level.INFO, teaMessageService.getMessage(MessageKey.BE_TEA_AGENT_URL, agentURL));

		// Create TeaAgent Server
		server = new TeaAgentServer("BusinessEvents", agentVersion, be_teagentVersion.getComponent(), hostName,
				Integer.parseInt(agentPort), contextPath, true);
		// Register agent
		server.registerInstance(beTeaAgent);
		boolean isMonitorable = Boolean
				.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
		if (isMonitorable) {
			resourceLocation = resourceLocation + "/" + "dockerui";
		}
		server.registerResourceLocation(new File(resourceLocation));
		//
		// Register Object providers
		for (ObjectProvider<? extends TeaObject> objectProvider : getObjectProviders()) {
			server.registerInstance(objectProvider);
		}

		int retryInterval = Integer
				.parseInt(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_RETRY_INTERVAL.getPropertyName(),
						String.valueOf(ConfigProperty.BE_TEA_AGENT_RETRY_INTERVAL.getDefaultValue())));

		long pollerDelay = Long
				.parseLong(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_POLLER_DELAY.getPropertyName(),
						String.valueOf(ConfigProperty.BE_TEA_AGENT_POLLER_DELAY.getDefaultValue())));
		long tempFileDeleteTaskDelay = Long.parseLong(
				configuration.getProperty(ConfigProperty.BE_TEA_AGENT_TEMP_FILE_DELETE_TASK_DELAY.getPropertyName(),
						String.valueOf(ConfigProperty.BE_TEA_AGENT_TEMP_FILE_DELETE_TASK_DELAY.getDefaultValue())));
		boolean isExposePythonAPI = Boolean
				.parseBoolean(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_EXPOSE_PYTHON_API.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_EXPOSE_PYTHON_API.getDefaultValue()));
		boolean unregisterEnabled = Boolean
				.parseBoolean(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_UNREGISTER_ENABLE.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_UNREGISTER_ENABLE.getDefaultValue()));
		boolean autoregisterEnabled = Boolean
				.parseBoolean(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_AUTO_REGISTRATION_ENABLED.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_AUTO_REGISTRATION_ENABLED.getDefaultValue()));
		LOGGER.log(Level.INFO, teaMessageService.getMessage(MessageKey.EXPOSE_PYTHIN_API, isExposePythonAPI));

		// BE-23304
		server.setIdleTimeout(
				Integer.parseInt(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_IDLE_TIMEOUT.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_IDLE_TIMEOUT.getDefaultValue())));
		server.setExposePythonAPI(isExposePythonAPI);
		server.setAgentId(agentName);

		// Start the BE TEA agent
		server.start();

		
		// Task for auto registration
		autoRegisterTask = new BETEAAgentAutoRegistrationTask(dataProviderService, applicationService, server,
				teaServerURL, pollerDelay, masterHostManagementService, dataStoreService.getTempFileLocation(),
				tempFileDeleteTaskDelay, unregisterEnabled,agentName,autoregisterEnabled);
		Task task = new IdempotentRetryTask(autoRegisterTask, Integer.MAX_VALUE, retryInterval);
		Object started = null;
		try {
			started = task.perform();
		} catch (Throwable e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
		}
		return started;
	}

	/**
	 * Create and register TEA Objects
	 * 
	 * @param application
	 *            - Instance of Application
	 */
	public void createAndRegisterTEAObjects(Application application) {
		BEApplication beApplication = (BEApplication) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_APPLICATION).getInstance(application.getKey());
		if (null == beApplication) {
			beApplication = new BEApplication(application);
			beApplication.registerWithObjectProvider();
			beApplication.setApplicationService(applicationService);
		}

		for (Host host : application.getHosts()) {
			BEApplicationHost applicationHost = (BEApplicationHost) ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_APPLICATION_HOST).getInstance(host.getKey());
			if (null == applicationHost) {
				applicationHost = new BEApplicationHost(host);
				applicationHost.registerWithObjectProvider();
				applicationHost.setHostService(hostService);
			}

			for (ServiceInstance instance : host.getInstances()) {
				BEServiceInstance serviceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(instance.getKey());
				if (null == serviceInstance) {
					serviceInstance = new BEServiceInstance(instance);
					serviceInstance.registerWithObjectProvider();
					serviceInstance.setInstanceService(instanceService);
				}

				for (Agent agent : instance.getAgents()) {
					BEAgent beAgent = (BEAgent) ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT)
							.getInstance(agent.getKey());
					if (null == beAgent) {
						beAgent = new BEAgent(agent);
						beAgent.setAgentService(agentManagementService);
						beAgent.registerWithObjectProvider();
					}
				}
			}
		}
		for (ProcessingUnit processingUnit : application.getProcessingUnits()) {

			BEProcessingUnit beProcessingUnit = (BEProcessingUnit) ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_PROCESSING_UNIT).getInstance(processingUnit.getKey());
			if (null == beProcessingUnit) {
				beProcessingUnit = new BEProcessingUnit(processingUnit);
				beProcessingUnit.registerWithObjectProvider();
				beProcessingUnit.setApplicationService(applicationService);
			}
			for (AgentConfig agentConfig : processingUnit.getAgents()) {
				BEProcessingUnitAgent beProcessingUnitAgent = (BEProcessingUnitAgent) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_PROCESSING_UNIT_AGENT).getInstance(agentConfig.getTeaObjectKey());
				if (null == beProcessingUnitAgent) {
					beProcessingUnitAgent = new BEProcessingUnitAgent(agentConfig, applicationService,
							agentManagementService);
					beProcessingUnitAgent.registerWithObjectProvider();
				}
			}
		}
	}

	/**
	 * @param application
	 * @param hostService
	 * @param instanceService
	 */
	public void unRegisterTEAObjects(Application application) {
		BEApplication beApplication = (BEApplication) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_APPLICATION).getInstance(application.getKey());
		if (null != beApplication) {
			ObjectCacheProvider.getInstance().getProvider(Constants.BE_APPLICATION).remove(application.getKey());
		}

		for (Host host : application.getHosts()) {
			BEApplicationHost applicationHost = (BEApplicationHost) ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_APPLICATION_HOST).getInstance(host.getKey());
			if (null != applicationHost) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_APPLICATION_HOST).remove(host.getKey());
			}

			for (ServiceInstance instance : host.getInstances()) {
				BEServiceInstance serviceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(instance.getKey());
				if (null != serviceInstance) {
					ObjectCacheProvider.getInstance().getProvider(Constants.BE_SERVICE_INSTANCE)
							.remove(instance.getKey());
				}

				for (Agent agent : instance.getAgents()) {
					BEAgent beAgent = (BEAgent) ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT)
							.getInstance(agent.getKey());
					if (null != beAgent) {
						ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT).remove(agent.getKey());
					}
				}
			}
		}
		for (ProcessingUnit processingUnit : application.getProcessingUnits()) {

			BEProcessingUnit beProcessingUnit = (BEProcessingUnit) ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_PROCESSING_UNIT).getInstance(processingUnit.getKey());
			if (null != beProcessingUnit) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_PROCESSING_UNIT)
						.remove(processingUnit.getKey());
			}
			for (AgentConfig agentConfig : processingUnit.getAgents()) {
				BEProcessingUnitAgent beProcessingUnitAgent = (BEProcessingUnitAgent) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_PROCESSING_UNIT_AGENT).getInstance(agentConfig.getTeaObjectKey());
				if (null != beProcessingUnitAgent) {
					ObjectCacheProvider.getInstance().getProvider(Constants.BE_PROCESSING_UNIT_AGENT)
							.remove(agentConfig.getTeaObjectKey());
				}
			}
		}
	}

	public void unRegisterTEAObjects(Host host) {
		BEApplicationHost applicationHost = (BEApplicationHost) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_APPLICATION_HOST).getInstance(host.getKey());
		if (null != applicationHost) {
			ObjectCacheProvider.getInstance().getProvider(Constants.BE_APPLICATION_HOST).remove(host.getKey());
		}
	}

	private Collection<ObjectProvider<? extends TeaObject>> getObjectProviders() {
		ObjectCacheProvider objectProvider = ObjectCacheProvider.getInstance();
		return objectProvider.getProviders();
	}

	private void createTeaObjectProviders() {
		ObjectCacheProvider objectProvider = ObjectCacheProvider.getInstance();
		objectProvider.addProvider(Constants.BE_APPLICATION, new BEApplicationProvider());
		objectProvider.addProvider(Constants.BE_SERVICE_INSTANCE, new BEServiceInstanceProvider());
		objectProvider.addProvider(Constants.BE_APPLICATION_HOST, new BEApplicationHostProvider());
		objectProvider.addProvider(Constants.BE_PROCESSING_UNIT, new BEProcessingUnitProvider());
		objectProvider.addProvider(Constants.BE_AGENT, new BEAgentProvider());
		objectProvider.addProvider(Constants.BE_PROCESSING_UNIT_AGENT, new BEProcessingUnitAgentProvider());
		objectProvider.addProvider(Constants.BE_MASTER_HOST, new BEMasterHostProvider());
	}

	/**
	 * Initialize the management service
	 * 
	 * @throws Exception
	 */
	private void initManagementServices() throws Exception {

		LOGGER.log(Level.DEBUG, "Initializing Application Management Service");
		BEMMServiceProviderManager.getInstance().setConfiguration(configuration);

		applicationCDDCacheService = BEMMServiceProviderManager.getInstance().getBEApplicationCDDCacheService();
		dataStoreService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
		dataStoreService.init(configuration);

		lockManager = BEMMServiceProviderManager.getInstance().getLockManager();
		lockManager.init(configuration);

		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		messageService.init(configuration);
		mbeanService = BEMMServiceProviderManager.getInstance().getBEMBeanService();
		mbeanService.init(configuration);
		validationService = BEMMServiceProviderManager.getInstance().getValidationService();

		descriptorService = BEMMServiceProviderManager.getInstance().getEntityMethodsDescriptorService();

		beHomeDiscoveryService = BEMMServiceProviderManager.getInstance().getBEHomeDiscoveryService();
		beHomeDiscoveryService.init(configuration);

		masterHostManagementService = BEMMServiceProviderManager.getInstance().getMasterHostManagementService();
		masterHostManagementService.init(configuration);
		masterHostManagementService.setLockManager(lockManager);

		agentManagementService = BEMMServiceProviderManager.getInstance().getBEAgentManagementService();
		agentManagementService.init(configuration);

		instanceService = BEMMServiceProviderManager.getInstance().getBEServiceInstancesManagementService();
		instanceService.init(configuration);
		instanceService.setLockManager(lockManager);

		hostService = BEMMServiceProviderManager.getInstance().getBEApplicationHostsManagementService();
		hostService.init(configuration);
		hostService.setLockManager(lockManager);

		applicationService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService();
		applicationService.init(configuration);
		applicationService.setLockManager(lockManager);

		// there is a cycle here.. need to revisit.
		masterHostManagementService.setApplicationsManagementService(applicationService);

		dataProviderService = BEMMServiceProviderManager.getInstance().getBEDataProviderService();

		applicationService.registerStatusChangeListener(ENTITY_TYPE.PU_INSTANCE,
				new BEServiceInstanceStatusChangeListener());
		applicationService.registerStatusChangeListener(ENTITY_TYPE.QUERY_AGENT, new BEAgentStatusChangeListener());
		applicationService.registerStatusChangeListener(ENTITY_TYPE.CACHE_AGENT, new BEAgentStatusChangeListener());
		applicationService.registerStatusChangeListener(ENTITY_TYPE.INFERENCE_AGENT, new BEAgentStatusChangeListener());

		groupJobExecutorService = BEMMServiceProviderManager.getInstance().getGroupOpExecutorService();
		jschConnectionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(null);

		pollerJobsExecutorService = BEMMServiceProviderManager.getInstance().getPollerExecutorService();

		exportService = BEMMServiceProviderManager.getInstance().getExportService();
		exportService.init(configuration);

		LOGGER.log(Level.DEBUG, "Initialized Application Management Service");
	}

	private void loadTeaObjectHierachy() throws BEApplicationLoadException {
		ObjectCacheProvider.getInstance().clear();
		createAndRegisterTeaObjects();

		Map<String, Application> applicationMap = applicationService.getApplications();

		for (Application application : applicationMap.values()) {
			createAndRegisterTEAObjects(application);
		}
	}

	/**
	 * Initialized
	 * 
	 * @throws Exception
	 */
	private void initMonitoringServices() {
		boolean enableMonitorStats = Boolean
				.parseBoolean((String) ConfigProperty.BE_TEA_AGENT_METRICS_COLLECTION_ENABLE.getValue(configuration));
		if (enableMonitorStats) {
			try {

				beTeaAgentMonitoringService = BEMMServiceProviderManager.getInstance().getBeTeaAgentMonitoringService();
				beTeaAgentMonitoringService.init(configuration);

				entityMonitoringService = BEMMServiceProviderManager.getInstance().getEntityMonitoringService();
				entityMonitoringService.init(configuration);

				// Init & Start the Aggregation engine
				aggService = BEMMServiceProviderManager.getInstance().getAggregationService();
				aggService.init(configuration);

				// DONE:BG Moved to start phase
				// BEMMServiceProviderManager.getInstance().getAggregationService().start();

				// Initialization of Visualizations service
				metricVisService = BEMMServiceProviderManager.getInstance().getMetricVisulizationService();
				metricVisService.init(configuration);

				// Initialization of Metric Rule service
				ruleService = BEMMServiceProviderManager.getInstance().getMetricRuleService();
				ruleService.init(configuration);

				// //Publish initial facts for applications which are already
				// loaded//DONE:BG Moved to start phase
				//// entityMonitoringService.sendApplicationsBootstrapFacts();
				//
				// //Enable the loaded rules//DONE:BG Moved to start phase
				// ruleService.enableLoadedRules();

			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.MONITORING_SERVICE_ERROR), e);
			}
		}
	}

	/**
	 * 
	 */
	public void createAndRegisterTeaObjects() {
		// Register MasterHot
		Map<String, MasterHost> masterHostMap = masterHostManagementService.getAllMasterHost();
		for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
			MasterHost masterHost = entry.getValue();
			BEMasterHost beMasterHost = (BEMasterHost) ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_MASTER_HOST).getInstance(masterHost.getKey());
			if (null == beMasterHost) {
				beMasterHost = new BEMasterHost(masterHost);
				beMasterHost.setHostService(masterHostManagementService);
				beMasterHost.registerWithObjectProvider();
			}
		}
	}

	@Override
	public void stop() throws Exception {
		LOGGER.log(Level.INFO, teaMessageService.getMessage(MessageKey.SHOTDOWN_PROGRESS));
		if (server != null) {
			try {
				server.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_TEA_SERVER_ERROR));
			}
		}
		try {
			if (autoRegisterTask != null) {
				autoRegisterTask.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_PINGSERVICE_ERROR));
		}

		stopMonitoringServices();

		stopManagementServices();

		LOGGER.log(Level.INFO, teaMessageService.getMessage(MessageKey.SHUTDOWN_COMPLETE));
	}

	private void stopManagementServices() throws Exception {

		if (dataProviderService != null) {
			try {
				dataProviderService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "DataProvider Service"), e);
			}
		}
		if (masterHostManagementService != null) {
			try {
				masterHostManagementService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "MasterHost Service"), e);
			}
		}
		if (applicationService != null) {
			try {
				applicationService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Application Service"), e);
			}
		}
		if (hostService != null) {
			try {
				hostService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Host Service"), e);
			}
		}
		if (instanceService != null) {
			try {
				instanceService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Instance Service"), e);
			}
		}
		if (agentManagementService != null) {
			try {
				agentManagementService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "AgentMgmt Service"), e);
			}
		}
		if (validationService != null) {
			try {
				validationService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Validation Service"), e);
			}
		}
		if (mbeanService != null) {
			try {
				mbeanService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "MBean Service"), e);
			}
		}
		if (messageService != null) {
			try {
				messageService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Message Service"), e);
			}
		}
		if (dataStoreService != null) {
			try {
				dataStoreService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "DataStore Service"), e);
			}
		}

		if (pollerJobsExecutorService != null) {
			try {
				pollerJobsExecutorService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Poller Jobs Executor Service"), e);
			}
		}

		if (groupJobExecutorService != null) {
			try {
				groupJobExecutorService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Group Job Executor Service"), e);
			}
		}

		if (jschConnectionPool != null) {
			try {
				jschConnectionPool.close();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.CLOSING_SHARED_JSCH_CONNECTION_POOL), e);
			}
		}
		if (beHomeDiscoveryService != null) {
			try {
				beHomeDiscoveryService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "BE Home Discovery Service"), e);
			}
		}
		if (applicationCDDCacheService != null) {
			try {
				applicationCDDCacheService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "BE Application CDD cache Service"), e);
			}
		}
		if (applicationGVCacheService != null) {
			try {
				applicationGVCacheService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "BE Application GV cache Service"), e);
			}
		}

	}

	private void stopMonitoringServices() throws Exception {
		if (ruleService != null) {
			try {
				ruleService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Rule Service"), e);
			}
		}
		if (metricVisService != null) {
			try {
				metricVisService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "MetricVisualization Service"), e);
			}
		}
		if (aggService != null) {
			try {
				aggService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "Aggregation Service"), e);
			}
		}
		if (entityMonitoringService != null) {
			try {
				entityMonitoringService.stop();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, teaMessageService.getMessage(MessageKey.STOPPING_ERROR, "EntityMonitoring Service"), e);
			}
		}
	}

}
