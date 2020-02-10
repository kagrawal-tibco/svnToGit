package com.tibco.cep.bemm.common.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.service.ApplicationDataProviderService;
import com.tibco.cep.bemm.common.service.BEMMServiceFactory;
import com.tibco.cep.bemm.common.service.EntityMethodsDescriptorService;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
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
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.monitoring.metric.rule.BEEntityMetricRuleService;
import com.tibco.cep.bemm.monitoring.metric.service.AggregationService;
import com.tibco.cep.bemm.monitoring.metric.service.BEEntityMetricProviderService;
import com.tibco.cep.bemm.monitoring.metric.view.BEEntityMetricVisulizationService;
import com.tibco.cep.bemm.monitoring.service.BEEntityMonitoringService;
import com.tibco.cep.bemm.monitoring.service.BETeaAgentMonitoringService;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.rta.common.service.ShutdownService;
import com.tibco.tea.agent.be.migration.file.generator.BeTeaAgentExportService;

/**
 * Implementation of functionalities defined in BETeaAgentServiceFactory
 * interface
 * 
 * @author dijadhav
 *
 */
public class BEMMServiceFactoryImpl implements BEMMServiceFactory {
	private final String DATA_PROVIDER_SERVICE = "com.tibco.cep.bemm.common.service.impl.ApplicationDataProviderServiceImpl";
	private final String MBEAN_SERVICE = "com.tibco.cep.bemm.common.service.impl.MBeanServiceImpl";
	private final String ENTITY_METHODS_SERVICE = "com.tibco.cep.bemm.common.service.impl.EntityMethodsDescriptorServiceImpl";
	private final String HOST_MANAGEMENT_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEApplicationHostsManagementServiceImpl";
	private final String APPLICATION_MANAGEMENT_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEApplicationsManagementServiceImpl";
	private final String SERVICE_INSTANCE_MANAGEMENT_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEServiceInstancesManagementServiceImpl";
	private final String AGENT_MANAGEMENT_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEAgentManagementServiceImpl";
	private final String APPLICATION_DATA_STORE_SERVICE = "com.tibco.cep.bemm.persistence.service.impl.BEApplicationsManagementDataFileStoreServiceImpl";
	private final String MESSAGE_SERVICE = "com.tibco.cep.bemm.common.message.impl.MessageServiceImpl";
	private final String VALIDATION_SERVICE = "com.tibco.cep.bemm.common.service.impl.ValidationServiceImpl";
	private final String NOP_LOCK_MANAGER = "com.tibco.cep.bemm.common.service.impl.NoOpLockManager";
	private final String LOCK_MANAGER = "com.tibco.cep.bemm.common.service.impl.LockManagerImpl";

	private final String MASTER_HOST_MAMAGEMENT_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEMasterHostManagementServiceImpl";

	private final String CLUSTER_BASED_DISCOVERY_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEApplicationClusterBasedDiscoveryServiceImpl";
	private final String INSTANCES_BASED_DISCOVERY_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEApplicationInstancesBasedDiscoveryServiceImpl";
	private final String AS_DATA_FEED_HANDLER = "com.tibco.cep.bemm.common.handler.impl.ApplicationASDataFeedHandlerImpl";
	private final String COHERENCE_DATA_FEED_HANDLER = "com.tibco.cep.bemm.common.handler.impl.CoherenceDataFeedHandlerImpl";
	private final String JMX_DATA_FEED_HANDLER = "com.tibco.cep.bemm.common.handler.impl.JMXDataFeedHandlerImpl";
	// Monitoring services
	private final String ENTITY_MONITORING_SERVICE = "com.tibco.cep.bemm.monitoring.service.impl.BEEntityMonitoringServiceImpl";
	private final String BETEAAGENT_MONITORING_SERVICE = "com.tibco.cep.bemm.monitoring.service.impl.BETeaAgentMonitoringServiceImpl";
	private final String METRIC_VISULIZATION_SERVICE = "com.tibco.cep.bemm.monitoring.metric.view.impl.BEEntityMetricVisulizationServiceImpl";
	private final String METRIC_RULE_SERVICE = "com.tibco.cep.bemm.monitoring.metric.rule.impl.BEEntityMetricRuleServiceImpl";
	private final String AGGREGATION_SERVICE = "com.tibco.cep.bemm.monitoring.metric.service.impl.AggregationServiceImpl";
	private final String METRIC_PROVIDER_SERVICE = "com.tibco.cep.bemm.monitoring.metric.service.impl.BEEntityMetricProviderServiceImpl";
	private final String SHUTDOWN_SERVICE = "com.tibco.tea.agent.be.BETeaAgentShutdownHandler";
	private final String EXPORT_SERVICE = "com.tibco.tea.agent.be.migration.file.generator.MigrateAdminDataServiceImpl";
	private final String BE_HOME_DISCOVERY_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEHomeDiscoveryServiceImpl";
	private final String BE_APPLICATION_CDD_CACHE_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEApplicationCDDCacheServiceImpl";
	private final String BE_APPLICATION_GV_CACHE_SERVICE = "com.tibco.cep.bemm.management.service.impl.BEApplicationGVCacheServiceImpl";
	private final Map<String, Object> serviceMap = Collections.synchronizedMap(new HashMap<String, Object>());

	/**
	 * Instance of type BETeaAgentServiceFactory
	 */
	private static BEMMServiceFactory INSTANCE;

	/**
	 * Private Constructor
	 */
	private BEMMServiceFactoryImpl() {
	}

	/**
	 * Initialize and return the instance of type BETeaAgentServiceFactory
	 * 
	 * @return Object of type BETeaAgentServiceFactory.
	 */
	public synchronized static BEMMServiceFactory getInstance() {
		if (null == INSTANCE) {
			synchronized (BEMMServiceFactoryImpl.class) {
				INSTANCE = new BEMMServiceFactoryImpl();
			}
		}
		return INSTANCE;
	}

	@Override
	public synchronized ApplicationDataProviderService getDataProvicerService() throws ObjectCreationException {

		ApplicationDataProviderService dataProviderService = (ApplicationDataProviderService) serviceMap
				.get(DATA_PROVIDER_SERVICE);
		if (null == dataProviderService) {
			dataProviderService = (ApplicationDataProviderService) ManagementUtil.getInstance(DATA_PROVIDER_SERVICE);
			serviceMap.put(DATA_PROVIDER_SERVICE, dataProviderService);
		}
		return dataProviderService;

	}

	@Override
	public synchronized BeTeaAgentExportService getExportService() throws ObjectCreationException {

		BeTeaAgentExportService exportService = (BeTeaAgentExportService) serviceMap.get(EXPORT_SERVICE);
		if (null == exportService) {
			exportService = (BeTeaAgentExportService) ManagementUtil.getInstance(EXPORT_SERVICE);
			serviceMap.put(EXPORT_SERVICE, exportService);
		}
		return exportService;

	}

	@Override
	public synchronized ApplicationDataFeedHandler<?> getApplicationDataFeedHandler(OMEnum omenum)
			throws ObjectCreationException {
		ApplicationDataFeedHandler<?> serviceInstanceDataService = null;
		if (OMEnum.TIBCO.equals(omenum)) {
			serviceInstanceDataService = (ApplicationDataFeedHandler<?>) serviceMap.get(AS_DATA_FEED_HANDLER);
			if (null == serviceInstanceDataService) {
				serviceInstanceDataService = (ApplicationDataFeedHandler<?>) ManagementUtil
						.getInstance(AS_DATA_FEED_HANDLER);
				serviceMap.put(AS_DATA_FEED_HANDLER, serviceInstanceDataService);
			}
		} else if (OMEnum.COHERENCE.equals(omenum)) {
			serviceInstanceDataService = (ApplicationDataFeedHandler<?>) serviceMap.get(COHERENCE_DATA_FEED_HANDLER);
			if (null == serviceInstanceDataService) {
				serviceInstanceDataService = (ApplicationDataFeedHandler<?>) ManagementUtil
						.getInstance(COHERENCE_DATA_FEED_HANDLER);
				serviceMap.put(COHERENCE_DATA_FEED_HANDLER, serviceInstanceDataService);
			}
		} else if (OMEnum.JMX.equals(omenum)) {
			serviceInstanceDataService = (ApplicationDataFeedHandler<?>) serviceMap.get(JMX_DATA_FEED_HANDLER);
			if (null == serviceInstanceDataService) {
				serviceInstanceDataService = (ApplicationDataFeedHandler<?>) ManagementUtil
						.getInstance(JMX_DATA_FEED_HANDLER);
				serviceMap.put(JMX_DATA_FEED_HANDLER, serviceInstanceDataService);
			}
		}
		return serviceInstanceDataService;
	}

	@Override
	public synchronized BEApplicationsManagementService getApplicationsManagementService()
			throws ObjectCreationException {
		BEApplicationsManagementService applicationsManagementService = (BEApplicationsManagementService) serviceMap
				.get(APPLICATION_MANAGEMENT_SERVICE);
		if (null == applicationsManagementService) {
			applicationsManagementService = (BEApplicationsManagementService) ManagementUtil
					.getInstance(APPLICATION_MANAGEMENT_SERVICE);
			serviceMap.put(APPLICATION_MANAGEMENT_SERVICE, applicationsManagementService);
		}
		return applicationsManagementService;
	}

	@Override
	public synchronized BEApplicationHostsManagementService getApplicationHostsManagementService()
			throws ObjectCreationException {
		BEApplicationHostsManagementService applicationHostsManagementService = (BEApplicationHostsManagementService) serviceMap
				.get(HOST_MANAGEMENT_SERVICE);
		if (null == applicationHostsManagementService) {
			applicationHostsManagementService = (BEApplicationHostsManagementService) ManagementUtil
					.getInstance(HOST_MANAGEMENT_SERVICE);
			serviceMap.put(HOST_MANAGEMENT_SERVICE, applicationHostsManagementService);
		}
		return applicationHostsManagementService;
	}

	@Override
	public synchronized BEServiceInstancesManagementService getServiceInstancesManagementService()
			throws ObjectCreationException {
		BEServiceInstancesManagementService serviceInstancesManagementService = (BEServiceInstancesManagementService) serviceMap
				.get(SERVICE_INSTANCE_MANAGEMENT_SERVICE);
		if (null == serviceInstancesManagementService) {
			serviceInstancesManagementService = (BEServiceInstancesManagementService) ManagementUtil
					.getInstance(SERVICE_INSTANCE_MANAGEMENT_SERVICE);
			serviceMap.put(SERVICE_INSTANCE_MANAGEMENT_SERVICE, serviceInstancesManagementService);
		}
		return serviceInstancesManagementService;
	}

	@Override
	public synchronized MBeanService getMBeanService() throws ObjectCreationException {
		MBeanService mBeanService = (MBeanService) serviceMap.get(MBEAN_SERVICE);
		if (null == mBeanService) {
			mBeanService = (MBeanService) ManagementUtil.getInstance(MBEAN_SERVICE);
			serviceMap.put(MBEAN_SERVICE, mBeanService);
		}
		return mBeanService;
	}

	@Override
	public synchronized BEApplicationsManagementDataStoreService<?> getBEApplicationsManagementDataStoreService()
			throws ObjectCreationException {
		BEApplicationsManagementDataStoreService<?> applicationsManagementDataStoreService = (BEApplicationsManagementDataStoreService<?>) serviceMap
				.get(APPLICATION_DATA_STORE_SERVICE);
		if (null == applicationsManagementDataStoreService) {
			applicationsManagementDataStoreService = (BEApplicationsManagementDataStoreService<?>) ManagementUtil
					.getInstance(APPLICATION_DATA_STORE_SERVICE);
			serviceMap.put(APPLICATION_DATA_STORE_SERVICE, applicationsManagementDataStoreService);
		}
		return applicationsManagementDataStoreService;
	}

	@Override
	public synchronized MessageService getMessageService() throws ObjectCreationException {
		MessageService messageService = (MessageService) serviceMap.get(MESSAGE_SERVICE);
		if (null == messageService) {
			messageService = (MessageService) ManagementUtil.getInstance(MESSAGE_SERVICE);
			serviceMap.put(MESSAGE_SERVICE, messageService);
		}
		return messageService;
	}

	@Override
	public synchronized BEAgentManagementService getBEAgentManagementService() throws ObjectCreationException {
		BEAgentManagementService agentManagementService = (BEAgentManagementService) serviceMap
				.get(AGENT_MANAGEMENT_SERVICE);
		if (null == agentManagementService) {
			agentManagementService = (BEAgentManagementService) ManagementUtil.getInstance(AGENT_MANAGEMENT_SERVICE);
			serviceMap.put(AGENT_MANAGEMENT_SERVICE, agentManagementService);
		}
		return agentManagementService;
	}

	@Override
	public synchronized EntityMethodsDescriptorService getEntityMethodsDescriptorService()
			throws ObjectCreationException {
		EntityMethodsDescriptorService entityMethodsDescriptorService = (EntityMethodsDescriptorService) serviceMap
				.get(ENTITY_METHODS_SERVICE);
		if (null == entityMethodsDescriptorService) {
			entityMethodsDescriptorService = (EntityMethodsDescriptorService) ManagementUtil
					.getInstance(ENTITY_METHODS_SERVICE);
			serviceMap.put(ENTITY_METHODS_SERVICE, entityMethodsDescriptorService);
		}
		return entityMethodsDescriptorService;
	}

	@Override
	public synchronized AggregationService getAggregationService() throws ObjectCreationException {
		AggregationService aggregationService = (AggregationService) serviceMap.get(AGGREGATION_SERVICE);
		if (null == aggregationService) {
			aggregationService = (AggregationService) ManagementUtil.getInstance(AGGREGATION_SERVICE);
			serviceMap.put(AGGREGATION_SERVICE, aggregationService);
		}
		return aggregationService;
	}

	@Override
	public synchronized BEEntityMonitoringService getBEEntityMonitoringService() throws ObjectCreationException {
		BEEntityMonitoringService entityMonitoringService = (BEEntityMonitoringService) serviceMap
				.get(ENTITY_MONITORING_SERVICE);
		if (null == entityMonitoringService) {
			entityMonitoringService = (BEEntityMonitoringService) ManagementUtil.getInstance(ENTITY_MONITORING_SERVICE);
			serviceMap.put(ENTITY_MONITORING_SERVICE, entityMonitoringService);
		}
		return entityMonitoringService;
	}

	@Override
	public synchronized BETeaAgentMonitoringService getBETeaAgentMonitoringService() throws ObjectCreationException {
		BETeaAgentMonitoringService beTeaAgentMonitoringService = (BETeaAgentMonitoringService) serviceMap
				.get(BETEAAGENT_MONITORING_SERVICE);
		if (null == beTeaAgentMonitoringService) {
			beTeaAgentMonitoringService = (BETeaAgentMonitoringService) ManagementUtil
					.getInstance(BETEAAGENT_MONITORING_SERVICE);
			serviceMap.put(BETEAAGENT_MONITORING_SERVICE, beTeaAgentMonitoringService);
		}
		return beTeaAgentMonitoringService;
	}

	@Override
	public synchronized ValidationService getValidationService() throws ObjectCreationException {
		ValidationService validationService = (ValidationService) serviceMap.get(VALIDATION_SERVICE);
		if (null == validationService) {
			validationService = (ValidationService) ManagementUtil.getInstance(VALIDATION_SERVICE);
			serviceMap.put(VALIDATION_SERVICE, validationService);
		}
		return validationService;
	}

	@Override
	public synchronized BEMasterHostManagementService getMasterHostManagementService() throws ObjectCreationException {
		BEMasterHostManagementService masterHostManagementService = (BEMasterHostManagementService) serviceMap
				.get(MASTER_HOST_MAMAGEMENT_SERVICE);
		if (null == masterHostManagementService) {
			masterHostManagementService = (BEMasterHostManagementService) ManagementUtil
					.getInstance(MASTER_HOST_MAMAGEMENT_SERVICE);
			serviceMap.put(MASTER_HOST_MAMAGEMENT_SERVICE, masterHostManagementService);
		}
		return masterHostManagementService;
	}

	@Override
	public synchronized BEApplicationDiscoveryService getClusterBasedDiscoveryService() throws ObjectCreationException {
		BEApplicationDiscoveryService applicationDiscoveryService = (BEApplicationDiscoveryService) serviceMap
				.get(CLUSTER_BASED_DISCOVERY_SERVICE);
		if (null == applicationDiscoveryService) {
			applicationDiscoveryService = (BEApplicationDiscoveryService) ManagementUtil
					.getInstance(CLUSTER_BASED_DISCOVERY_SERVICE);
			serviceMap.put(CLUSTER_BASED_DISCOVERY_SERVICE, applicationDiscoveryService);
		}
		return applicationDiscoveryService;
	}

	@Override
	public synchronized BEApplicationDiscoveryService getInstanceBasedDiscoveryService()
			throws ObjectCreationException {
		BEApplicationDiscoveryService applicationDiscoveryService = (BEApplicationDiscoveryService) serviceMap
				.get(INSTANCES_BASED_DISCOVERY_SERVICE);
		if (null == applicationDiscoveryService) {
			applicationDiscoveryService = (BEApplicationDiscoveryService) ManagementUtil
					.getInstance(INSTANCES_BASED_DISCOVERY_SERVICE);
			serviceMap.put(INSTANCES_BASED_DISCOVERY_SERVICE, applicationDiscoveryService);
		}
		return applicationDiscoveryService;
	}

	@Override
	public synchronized BEEntityMetricVisulizationService getMetricVisulizationService()
			throws ObjectCreationException {
		BEEntityMetricVisulizationService entityMetricVisulizationService = (BEEntityMetricVisulizationService) serviceMap
				.get(METRIC_VISULIZATION_SERVICE);
		if (null == entityMetricVisulizationService) {
			entityMetricVisulizationService = (BEEntityMetricVisulizationService) ManagementUtil
					.getInstance(METRIC_VISULIZATION_SERVICE);
			serviceMap.put(METRIC_VISULIZATION_SERVICE, entityMetricVisulizationService);
		}
		return entityMetricVisulizationService;
	}

	@Override
	public synchronized BEEntityMetricRuleService getMetricRuleService() throws ObjectCreationException {
		BEEntityMetricRuleService entityMetricRuleService = (BEEntityMetricRuleService) serviceMap
				.get(METRIC_RULE_SERVICE);
		if (null == entityMetricRuleService) {
			entityMetricRuleService = (BEEntityMetricRuleService) ManagementUtil.getInstance(METRIC_RULE_SERVICE);
			serviceMap.put(METRIC_RULE_SERVICE, entityMetricRuleService);
		}
		return entityMetricRuleService;
	}

	@Override
	public synchronized BEEntityMetricProviderService getMetricProviderService() throws ObjectCreationException {
		BEEntityMetricProviderService entityMetricProviderService = (BEEntityMetricProviderService) serviceMap
				.get(METRIC_PROVIDER_SERVICE);
		if (null == entityMetricProviderService) {
			entityMetricProviderService = (BEEntityMetricProviderService) ManagementUtil
					.getInstance(METRIC_PROVIDER_SERVICE);
			serviceMap.put(METRIC_PROVIDER_SERVICE, entityMetricProviderService);
		}
		return entityMetricProviderService;
	}

	@Override
	public synchronized ShutdownService getShutdownService() throws ObjectCreationException {
		ShutdownService shutdownService = (ShutdownService) serviceMap.get(SHUTDOWN_SERVICE);
		if (null == shutdownService) {
			shutdownService = (ShutdownService) ManagementUtil.getInstance(SHUTDOWN_SERVICE);
			serviceMap.put(SHUTDOWN_SERVICE, shutdownService);
		}
		return shutdownService;
	}

	@Override
	public LockManager getLockManager(boolean isLockingEnabled) throws ObjectCreationException {
		if (isLockingEnabled) {
			return (LockManager) ManagementUtil.getInstance(LOCK_MANAGER);
		} else {
			return (LockManager) ManagementUtil.getInstance(NOP_LOCK_MANAGER);
		}
	}

	@Override
	public BEHomeDiscoveryService getBEHomeDiscoveryService() throws ObjectCreationException {
		return (BEHomeDiscoveryService) ManagementUtil.getInstance(BE_HOME_DISCOVERY_SERVICE);

	}

	@Override
	public BEApplicationCDDCacheService<?> getBEApplicationCDDCacheService() throws ObjectCreationException {
		return (BEApplicationCDDCacheService<?>) ManagementUtil.getInstance(BE_APPLICATION_CDD_CACHE_SERVICE);
	}

	@Override
	public BEApplicationGVCacheService<?> getBEApplicationGVCacheService() throws ObjectCreationException {
		return (BEApplicationGVCacheService<?>) ManagementUtil.getInstance(BE_APPLICATION_GV_CACHE_SERVICE);
	}

}
