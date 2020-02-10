package com.tibco.cep.bemm.common.service;

import java.util.Properties;

import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.jmx.JmxConnectionPool;
import com.tibco.cep.bemm.common.jmx.SafeJmxMBeanConnectionPool;
import com.tibco.cep.bemm.common.jmx.SharedJmxMBeanConnectionPool;
import com.tibco.cep.bemm.common.pool.ConnectionPool;
import com.tibco.cep.bemm.common.pool.GroupJobExecutorService;
import com.tibco.cep.bemm.common.pool.jsch.ExclusiveAccessJSchConnectionPool;
import com.tibco.cep.bemm.common.pool.jsch.LatchedSharedJSchConnectionPool;
import com.tibco.cep.bemm.common.pool.jsch.SafeJSchConnectionPool;
import com.tibco.cep.bemm.common.pool.jsch.SharedJSchConnectionPool;
import com.tibco.cep.bemm.common.service.impl.BEMMServiceFactoryImpl;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.management.service.BEAgentManagementService;
import com.tibco.cep.bemm.management.service.BEApplicationCDDCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationDiscoveryService;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationHostsManagementService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEHomeDiscoveryService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.monitoring.metric.rule.BEEntityMetricRuleService;
import com.tibco.cep.bemm.monitoring.metric.service.AggregationService;
import com.tibco.cep.bemm.monitoring.metric.service.BEEntityMetricProviderService;
import com.tibco.cep.bemm.monitoring.metric.view.BEEntityMetricVisulizationService;
import com.tibco.cep.bemm.monitoring.service.BEEntityMonitoringService;
import com.tibco.cep.bemm.monitoring.service.BETeaAgentMonitoringService;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.rta.common.service.ShutdownService;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.tea.agent.be.migration.file.generator.BeTeaAgentExportService;

public class BEMMServiceProviderManager {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEMMServiceProviderManager.class);

	
	private static BEMMServiceProviderManager instance = null;

	private Properties configuration;
	private BEMMServiceFactory serviceFactory;
	
	private BEApplicationsManagementDataStoreService<?> applicationsManagementDataStoreService = null;
	private BEApplicationsManagementService applicationsManagementService = null;
	private BEApplicationHostsManagementService applicationHostsManagementService = null;
	private BEServiceInstancesManagementService serviceInstancesManagementService = null;
	private BEAgentManagementService agentManagementService = null;
	private EntityMethodsDescriptorService methodsDescriptorService = null;
	private MBeanService beanService = null;
	private ApplicationDataProviderService applicationDataProviderService;
	private MessageService messageService;
	private ValidationService validationService;

	private LockManager lockManager;

	
	private AggregationService aggregationService = null;
	private BEEntityMonitoringService entityMonitoringService = null;
	private BETeaAgentMonitoringService beTeaAgentMonitoringService = null;
	private BEMasterHostManagementService masterHostManagementService=null;

	private BEApplicationDiscoveryService clusterBasedApplicationDiscoveryService = null;
	private BEApplicationDiscoveryService instanceBasedApplicationDiscoveryService = null;

	private BEEntityMetricVisulizationService metricVisulizationService = null;
	private BEEntityMetricRuleService ruleService = null;
	private BEEntityMetricProviderService metricProviderService = null;

	private JmxConnectionPool jmxConnectionPool;
	
	private GroupJobExecutorService groupJobExecutorService;
	private GroupJobExecutorService pollerExecutorService;
	private ConnectionPool<Session> jschConnectionPool;
	private ConnectionPool<Session> jschWinConnectionPool;

	private ShutdownService shutdownService;
	private BeTeaAgentExportService exportService = null;
	private BEHomeDiscoveryService beHomeDiscoveryService = null;
	private BEApplicationCDDCacheService<?>  applicationCDDCacheService = null;
	private BEApplicationGVCacheService<?> applicationGVCacheService;
	
	private BEMMServiceProviderManager() {
		serviceFactory = BEMMServiceFactoryImpl.getInstance();
	}

	public static synchronized BEMMServiceProviderManager getInstance() {
		if (instance == null) {
			instance = new BEMMServiceProviderManager();
		}
		return instance;
	}

	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}

	public Properties getConfiguration() {
		return configuration;
	}

	public BEApplicationsManagementDataStoreService<?> getBEApplicationsManagementDataStoreService()
			throws ObjectCreationException {
		if (applicationsManagementDataStoreService == null) {
			applicationsManagementDataStoreService = serviceFactory.getBEApplicationsManagementDataStoreService();
		}
		return applicationsManagementDataStoreService;
	}

	public BEApplicationsManagementService getBEApplicationsManagementService() throws ObjectCreationException {
		if (applicationsManagementService == null) {
			applicationsManagementService = serviceFactory.getApplicationsManagementService();
		}
		return applicationsManagementService;
	}
	
	public BeTeaAgentExportService getExportService() throws ObjectCreationException {
		if (exportService == null) {
			exportService = serviceFactory.getExportService();
		}
		return exportService;
	}

	public BEApplicationHostsManagementService getBEApplicationHostsManagementService() throws ObjectCreationException {
		if (applicationHostsManagementService == null) {
			applicationHostsManagementService = serviceFactory.getApplicationHostsManagementService();
		}
		return applicationHostsManagementService;
	}

	public BEServiceInstancesManagementService getBEServiceInstancesManagementService() throws ObjectCreationException {
		if (serviceInstancesManagementService == null) {
			serviceInstancesManagementService = serviceFactory.getServiceInstancesManagementService();
		}
		return serviceInstancesManagementService;
	}

	public MBeanService getBEMBeanService() throws ObjectCreationException {
		if (beanService == null) {
			beanService = serviceFactory.getMBeanService();
		}
		return beanService;
	}

	public ApplicationDataProviderService getBEDataProviderService() throws ObjectCreationException {
		if (applicationDataProviderService == null) {
			applicationDataProviderService = serviceFactory.getDataProvicerService();
		}
		return applicationDataProviderService;
	}

	public ApplicationDataFeedHandler<?> getApplicationDataFeedHandler(OMEnum omEnum) throws ObjectCreationException {
		return serviceFactory.getApplicationDataFeedHandler(omEnum);
	}

	public BEAgentManagementService getBEAgentManagementService() throws ObjectCreationException {
		if (agentManagementService == null) {
			agentManagementService = serviceFactory.getBEAgentManagementService();
		}
		return agentManagementService;
	}

	public LockManager getLockManager() throws ObjectCreationException {
		if (lockManager == null) {
			String lockingEnabled = (String) ConfigProperty.BE_TEA_AGENT_BE_LOCKING_ENABLE.getValue(configuration);
			boolean isLockingEnable = Boolean.parseBoolean(lockingEnabled);
			LOGGER.log(Level.INFO, "Locking enabled - %s", isLockingEnable);
			lockManager = serviceFactory.getLockManager(isLockingEnable);
		}
		return lockManager;
	}
	public EntityMethodsDescriptorService getEntityMethodsDescriptorService() throws ObjectCreationException {
		if (methodsDescriptorService == null) {
			methodsDescriptorService = serviceFactory.getEntityMethodsDescriptorService();
		}
		return methodsDescriptorService;
	}

	public MessageService getMessageService() throws ObjectCreationException {
		if (messageService == null) {
			messageService = serviceFactory.getMessageService();
		}
		return messageService;
	}

	public BEEntityMonitoringService getEntityMonitoringService() throws ObjectCreationException {
		if (entityMonitoringService == null) {
			entityMonitoringService = serviceFactory.getBEEntityMonitoringService();
		}
		return entityMonitoringService;
	}

	public BETeaAgentMonitoringService getBeTeaAgentMonitoringService() throws ObjectCreationException {
		if (beTeaAgentMonitoringService == null) {
			beTeaAgentMonitoringService = serviceFactory.getBETeaAgentMonitoringService();
		}
		return beTeaAgentMonitoringService;
	}

	public AggregationService getAggregationService() throws ObjectCreationException {
		if (aggregationService == null) {
			aggregationService = serviceFactory.getAggregationService();
		}
		return aggregationService;
	}

	public ValidationService getValidationService() throws ObjectCreationException {
		if (validationService == null) {
			validationService = serviceFactory.getValidationService();
		}
		return validationService;
	}

	public BEMasterHostManagementService getMasterHostManagementService() throws ObjectCreationException {
		if (masterHostManagementService == null) {
			masterHostManagementService = serviceFactory.getMasterHostManagementService();
		}
		return masterHostManagementService;
	}

	public BEApplicationDiscoveryService getClusterBasedDiscoveryService() throws ObjectCreationException {
		if (clusterBasedApplicationDiscoveryService == null) {
			clusterBasedApplicationDiscoveryService = serviceFactory.getClusterBasedDiscoveryService();
		}
		return clusterBasedApplicationDiscoveryService;
	}

	public BEApplicationDiscoveryService getInstanceBasedDiscoveryService() throws ObjectCreationException {
		if (instanceBasedApplicationDiscoveryService == null) {
			instanceBasedApplicationDiscoveryService = serviceFactory.getInstanceBasedDiscoveryService();
		}
		return instanceBasedApplicationDiscoveryService;
	}

	public BEEntityMetricVisulizationService getMetricVisulizationService() throws ObjectCreationException {
		if (metricVisulizationService == null) {
			metricVisulizationService = serviceFactory.getMetricVisulizationService();
		}
		return metricVisulizationService;
	}

	public BEEntityMetricRuleService getMetricRuleService() throws ObjectCreationException {
		if (ruleService == null) {
			ruleService = serviceFactory.getMetricRuleService();
		}
		return ruleService;
	}

	public BEEntityMetricProviderService getMetricProviderService() throws ObjectCreationException {
		if (metricProviderService == null) {
			metricProviderService = serviceFactory.getMetricProviderService();
		}
		return metricProviderService;
	}

	public ShutdownService getShutdownService() throws ObjectCreationException {
		if (shutdownService == null) {
			shutdownService = serviceFactory.getShutdownService();
		}
		return shutdownService;
	}

	public JmxConnectionPool getJmxConnectionPool() throws Exception {
		if (jmxConnectionPool == null) {
			if (configuration.getProperty("be.teagent.jmx.pool.type", "safe").equals("safe")) {
				jmxConnectionPool = new SafeJmxMBeanConnectionPool();
			} else if (configuration.getProperty("be.teagent.jmx.pool.type").equals("shared")) {
				jmxConnectionPool = new SharedJmxMBeanConnectionPool();
			}
		}
		return jmxConnectionPool;
	}
	
	public WorkItemService newWorkItemService(String threadPoolName) throws Exception {
		WorkItemService wis = (WorkItemService) Class.forName("com.tibco.rta.common.service.impl.WorkItemServiceImpl")
				.newInstance();
		wis.setThreadPoolName(threadPoolName);
		return wis;
	}
	
	/**
	 * Returns the instance of Group Job Executor Service (Creating if not already created).
	 * @return
	 * @throws ServiceInitializationException
	 */
	public GroupJobExecutorService getGroupOpExecutorService() throws ServiceInitializationException {
		try {
			if (groupJobExecutorService == null) {
				synchronized (this.getClass()) {
					if (groupJobExecutorService == null) {
						WorkItemService workItemService = BEMMServiceProviderManager.getInstance().newWorkItemService("jsch_group_op");
						groupJobExecutorService = new GroupJobExecutorService(workItemService);
						
						groupJobExecutorService.init(configuration);
						groupJobExecutorService.start();
					}
				}
			}
			return groupJobExecutorService;
		} catch(Exception e) {
			throw new ServiceInitializationException("Failed to initialize group op service.", e);
		}
	}

	/**
	 * Returns the instance of JSch Session Pool (Creating if not already created).
	 * @return
	 * @throws ServiceInitializationException
	 */
	public ConnectionPool<Session> getJSchConnectionPool(String hostOs) throws ServiceInitializationException {
		if ("windows".equalsIgnoreCase(hostOs) && Boolean.parseBoolean(configuration.getProperty("be.tea.agent.jsch.separate.win.pool", "false"))) {
			return getWinJSchConnectionPool();
		} else {
			return getStandardJSchConnectionPool();
		}
	}
	private ConnectionPool<Session> getStandardJSchConnectionPool() throws ServiceInitializationException {
		if (jschConnectionPool == null) {
			synchronized (this) {
				if (jschConnectionPool == null) {
					String jschConnectionPoolType = configuration.getProperty("be.tea.agent.jsch.pool.type", "latched");
					if ("safe".equals(jschConnectionPoolType)) {
						jschConnectionPool = new SafeJSchConnectionPool(configuration);
					} else if ("shared".equals(jschConnectionPoolType)) {
						jschConnectionPool = new SharedJSchConnectionPool(configuration);
					} else if ("exclusive".equals(jschConnectionPoolType)) {
						jschConnectionPool = new ExclusiveAccessJSchConnectionPool(configuration);
					} else if ("latched".equals(jschConnectionPoolType)) {
						jschConnectionPool = new LatchedSharedJSchConnectionPool(configuration);
					} else {
						throw new ServiceInitializationException("Unkown jsch pool type - " + jschConnectionPoolType);
					}
				}
			}
		}
		return jschConnectionPool;
	}
	private ConnectionPool<Session> getWinJSchConnectionPool() throws ServiceInitializationException {
		if (jschWinConnectionPool == null) {
			synchronized (this) {
				if (jschWinConnectionPool == null) {
					String jschConnectionPoolType = configuration.getProperty("be.tea.agent.jsch.win.pool.type", "latched");
					if ("safe".equals(jschConnectionPoolType)) {
						jschWinConnectionPool = new SafeJSchConnectionPool(configuration);
					} else if ("shared".equals(jschConnectionPoolType)) {
						jschWinConnectionPool = new SharedJSchConnectionPool(configuration);
					} else if ("exclusive".equals(jschConnectionPoolType)) {
						jschConnectionPool = new ExclusiveAccessJSchConnectionPool(configuration);
					} else if ("latched".equals(jschConnectionPoolType)) {
						jschWinConnectionPool = new LatchedSharedJSchConnectionPool(configuration);
					} else {
						throw new ServiceInitializationException("Unkown jsch pool type - " + jschConnectionPoolType);
					}
				}
			}
		}
		return jschWinConnectionPool;
	}
	
	/**
	 * Returns the instance of Group Job Executor Service (Creating if not already created).
	 * @return
	 * @throws ServiceInitializationException
	 */
	public GroupJobExecutorService getPollerExecutorService() throws ServiceInitializationException {
		try {
			if (pollerExecutorService == null) {
				synchronized (this.getClass()) {
					if (pollerExecutorService == null) {
						WorkItemService workItemService = BEMMServiceProviderManager.getInstance().newWorkItemService("application-data-poller");
						pollerExecutorService = new GroupJobExecutorService(workItemService);
						
						pollerExecutorService.init(configuration);
						pollerExecutorService.start();
					}
				}
			}
			return pollerExecutorService;
		} catch(Exception e) {
			throw new ServiceInitializationException("Failed to initialize application data poller executor service.", e);
		}
	}

	public BEHomeDiscoveryService getBEHomeDiscoveryService() throws ObjectCreationException {
		if(null==beHomeDiscoveryService){
			beHomeDiscoveryService=serviceFactory.getBEHomeDiscoveryService();
		}
		return beHomeDiscoveryService;
	}

	public BEApplicationCDDCacheService<?> getBEApplicationCDDCacheService() throws ObjectCreationException {
		if(null==applicationCDDCacheService){
			applicationCDDCacheService=serviceFactory.getBEApplicationCDDCacheService();
		}
		return applicationCDDCacheService;
	}
	
	public BEApplicationGVCacheService<?> getBEApplicationGVCacheService() throws ObjectCreationException {
		if(null==applicationGVCacheService){
			applicationGVCacheService=serviceFactory.getBEApplicationGVCacheService();
		}
		return applicationGVCacheService;
	}
	
}
