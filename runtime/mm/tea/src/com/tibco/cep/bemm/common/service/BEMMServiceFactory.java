package com.tibco.cep.bemm.common.service;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
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
 * Provides the functionality to get different types of services.
 * 
 * @author dijadhav
 *
 */
public interface BEMMServiceFactory {
	/**
	 * Returns the object of type ApplicationDataProviderService
	 * 
	 * @return ApplicationDataProviderService object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	ApplicationDataProviderService getDataProvicerService() throws ObjectCreationException;

	/**
	 * Returns the object of type ApplicationDataFeedHandler
	 * 
	 * @param omenum
	 *            Object Management type in CDD
	 * @return ApplicationDataFeedHandler object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	ApplicationDataFeedHandler<?> getApplicationDataFeedHandler(OMEnum omenum) throws ObjectCreationException;

	/**
	 * Returns the object of type BEApplicationsManagementService
	 * 
	 * @return BEApplicationsManagementService object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEApplicationsManagementService getApplicationsManagementService() throws ObjectCreationException;

	/**
	 * Returns object of type BEApplicationHostsManagementService
	 * 
	 * @return BEApplicationHostsManagementService object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */

	BEApplicationHostsManagementService getApplicationHostsManagementService() throws ObjectCreationException;

	/**
	 * Return object of type BEServiceInstancesManagementService
	 * 
	 * @return BEServiceInstancesManagementService object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEServiceInstancesManagementService getServiceInstancesManagementService() throws ObjectCreationException;

	/**
	 * Return object of type MBeanService
	 * 
	 * @return MBeanService object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	MBeanService getMBeanService() throws ObjectCreationException;

	/**
	 * Returns object of type BEApplicationsManagementDataStoreService
	 * 
	 * @return BEApplicationsManagementDataStoreService object
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEApplicationsManagementDataStoreService<?> getBEApplicationsManagementDataStoreService()
			throws ObjectCreationException;

	/**
	 * Returns object of type MessageService
	 * 
	 * @return
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	MessageService getMessageService() throws ObjectCreationException;

	/**
	 * Get the instance of BEAgentManagementService
	 * 
	 * @return BEAgentManagementService instance
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEAgentManagementService getBEAgentManagementService() throws ObjectCreationException;

	/**
	 * Get the lock manager instance
	 * 
	 * @return Instance of LockManager
	 * @throws ObjectCreationException
	 *             - Exception is thrown when creation of object fails.
	 */
	LockManager getLockManager(boolean isLockingEnabled) throws ObjectCreationException;

	/**
	 * Get the instance of EntityMethodsDescriptorService
	 * 
	 * @return EntityMethodsDescriptorService instance
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	EntityMethodsDescriptorService getEntityMethodsDescriptorService() throws ObjectCreationException;

	/**
	 * Get the instance of AggregationService
	 * 
	 * @return AggregationService instance
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	AggregationService getAggregationService() throws ObjectCreationException;

	/**
	 * Get the instance of BEEntityMonitoringService
	 * 
	 * @return BEEntityMonitoringService instance.
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEEntityMonitoringService getBEEntityMonitoringService() throws ObjectCreationException;
	
	/**
	 * Get the instance of BETeaAgentMonitoringService
	 * 
	 * @return BETeaAgentMonitoringService instance.
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BETeaAgentMonitoringService getBETeaAgentMonitoringService() throws ObjectCreationException;

	/**
	 * Get the validation service instance.
	 * 
	 * @return ValidationService Instance
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	ValidationService getValidationService() throws ObjectCreationException;

	/**
	 * @return
	 * @throws ObjectCreationException
	 */
	BEMasterHostManagementService getMasterHostManagementService() throws ObjectCreationException;

	/**
	 * @return
	 * @throws ObjectCreationException
	 */
	BEApplicationDiscoveryService getClusterBasedDiscoveryService() throws ObjectCreationException;

	/**
	 * @return
	 * @throws ObjectCreationException
	 */
	BEApplicationDiscoveryService getInstanceBasedDiscoveryService() throws ObjectCreationException;

	/**
	 * Get the instance of MetricVisulizationService
	 * 
	 * @return BEEntityMetricVisulizationService Instance
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEEntityMetricVisulizationService getMetricVisulizationService() throws ObjectCreationException;

	/**
	 * Get the instance of BEEntityMetricRuleService
	 * 
	 * @return BEEntityMetricVisulizationService Instance
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEEntityMetricRuleService getMetricRuleService() throws ObjectCreationException;

	/**
	 * Get the instance of BEEntityMetricProviderService
	 * 
	 * @return BEEntityMetricProviderService instance.
	 * @throws ObjectCreationException
	 *             -Exception is thrown when creation of object fails.
	 */
	BEEntityMetricProviderService getMetricProviderService() throws ObjectCreationException;

	ShutdownService getShutdownService() throws ObjectCreationException;
	
	BeTeaAgentExportService getExportService()  throws ObjectCreationException;

	BEHomeDiscoveryService getBEHomeDiscoveryService() throws ObjectCreationException;

	BEApplicationCDDCacheService<?> getBEApplicationCDDCacheService() throws ObjectCreationException;

	BEApplicationGVCacheService<?> getBEApplicationGVCacheService() throws ObjectCreationException;
	

}
