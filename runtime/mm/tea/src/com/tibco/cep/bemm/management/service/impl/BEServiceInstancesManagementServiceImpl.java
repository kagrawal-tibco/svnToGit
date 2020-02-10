
package com.tibco.cep.bemm.management.service.impl;

import static com.tibco.cep.bemm.management.util.Constants.SYSTEM_PROPERTY_PREFIX;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.tibco.cep.bemm.common.audit.AuditRecord;
import com.tibco.cep.bemm.common.audit.AuditRecords;
import com.tibco.cep.bemm.common.enums.BETEAAgentAction;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.job.BETeaDeployInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaDownloadLogJob;
import com.tibco.cep.bemm.common.job.BETeaKillInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaStartInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaTailCommandJob;
import com.tibco.cep.bemm.common.job.BETeaUndeployInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaUploadClassOrRTFilesJob;
import com.tibco.cep.bemm.common.job.BETeaUploadFileJob;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.model.ThreadDetail;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.operations.model.Rows;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEApplicationNotFoundException;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEProcessingUnitStartFailException;
import com.tibco.cep.bemm.management.exception.BEProcessingUnitStopFailException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceAddException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceEditException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceSaveException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceValidationException;
import com.tibco.cep.bemm.management.exception.BEUploadFileException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.exception.CopyInstanceFailException;
import com.tibco.cep.bemm.management.exception.DeploymentFailException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableLoadFailException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableStoreFailException;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.management.util.BETraProperties;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.DeploymentVariableUtil;
import com.tibco.cep.bemm.management.util.FetchCddDataUtil;
import com.tibco.cep.bemm.management.util.GroupOperationUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BEMMModelFactory;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.model.impl.ObjectFactory;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BEAgentUtil;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * Implementation of BEServiceInstancesManagementService
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstancesManagementServiceImpl extends AbstractBETeaManagementServiceImpl
		implements BEServiceInstancesManagementService {
	protected static final Pattern GLOBAL_VARIABLE_PATTERN = Pattern.compile("%%(.*?)%%");
	protected static final String PRE_APPENDREPLACEMENT_PATTERN_STR = "([\\\\\\$])";
	protected static final String PRE_APPENDREPLACEMENT_REPLACE = "\\\\$1";
	/**
	 * Configuration details
	 */
	private Properties configuration;
	/**
	 * s Deployment variables object factory
	 */
	private ObjectFactory deployVariablesObjectFactory = null;
	/**
	 * BEMM model factory
	 */
	private BEMMModelFactory agentModelFactory;
	/**
	 * Data store service
	 */
	// private BEApplicationsManagementDataStoreService<?> dataStoreService =
	// null;
	/**
	 * Message service
	 */
	private MessageService messageService;
	/**
	 * Data validation service;
	 */
	private ValidationService validationService;

	/**
	 * MBeanService to invoke service
	 */
	private MBeanService mbeanService;
	private LockManager lockManager;
	private BEApplicationGVCacheService<?> applicationGVCacheService;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(BEServiceInstancesManagementServiceImpl.class);

	@Override
	public void init(Properties configuration) throws ServiceInitializationException {
		try {
			super.init(configuration);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
					.getMessage(MessageKey.INITIALIZING_SERVICE, "instance of BEServiceInstancesManagementService"));
			this.configuration = configuration;
			this.deployVariablesObjectFactory = new ObjectFactory();
			this.agentModelFactory = BEMMModelFactoryImpl.getInstance();

			BEMMServiceProviderManager instance = BEMMServiceProviderManager.getInstance();
			// this.dataStoreService =
			// instance.getBEApplicationsManagementDataStoreService();
			this.messageService = instance.getMessageService();
			this.mbeanService = instance.getBEMBeanService();
			this.validationService = instance.getValidationService();
			this.poolService = instance.getGroupOpExecutorService();
			this.applicationGVCacheService = instance.getBEApplicationGVCacheService();
		} catch (ObjectCreationException e) {
			throw new ServiceInitializationException(
					messageService.getMessage(MessageKey.INSTANCE_INITIALIZE_INSTANCE_ERROR_MESSAGE));
		}
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.INITIALIZED, "instance of BEServiceInstancesManagementService"));
	}

	@Override
	public String createServiceInstance(Application application, String name, String processingUnit, String hostName,
			int jmxPort, String deploymentPath, boolean isPredefined, String loggedInUser, String jmxUserName,
			String jmxPassword, String beId) throws BEServiceInstanceAddException, BEValidationException {

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_CREATE_INSTANCE_METHOD));

		if (null == application)
			throw new BEValidationException(
					messageService.getMessage(MessageKey.APPLICATION_INSTANCE_NULL_ERROR_MESSAGE));

		// Instance name must be unique//TODO Add code for unique instance id
		if (isInstanceExistByName(name, application)) {
			throw new BEServiceInstanceAddException(
					messageService.getMessage(MessageKey.INSTANCE_DUPLICATE_ERROR_MESSAGE, name));
		}

		try {
			BEApplicationsManagementService applicationManagementService = BEMMServiceProviderManager.getInstance()
					.getBEApplicationsManagementService();
			Host matchedHost = applicationManagementService.getHostByHostName(application.getName(), hostName);
			/*
			 * If application does not have host for passed host then add new
			 * host.
			 */
			if (null == matchedHost) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.ADDING_MASTER_HOST_AS_APPLICATION_HOST));
				matchedHost = applicationManagementService.addApplicationHost(application, hostName);
			}
			if (null != matchedHost) {
				LOGGER.log(Level.DEBUG,
						"Creating new service instance Application=%s,Instance Name=%s,Processing unit=%s,host=%s,JMX Port=%d,Deployment Path=%s",
						application.getName(), name, processingUnit, matchedHost.getName(), jmxPort, deploymentPath);

				if (isInstanceExistByJMXPort(matchedHost, jmxPort, null)) {
					throw new BEServiceInstanceAddException(
							messageService.getMessage(MessageKey.INSTANCE_DUPLICATE_JMX_PORT_ERROR_MESSAGE, jmxPort));
				}
				if (!application.isMonitorableOnly()) {
					// Validate Deployment path
					try {
						deploymentPath = ManagementUtil.getDefaultDeploymentPath(deploymentPath, null, matchedHost);
					} catch (BEServiceInstanceValidationException e) {
						throw new BEServiceInstanceAddException(e.getMessage());
					}
				}

				if (!isPredefined) {
					name = ManagementUtil.nextUndefinedInstanceName(application);
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_STARTED_EXTERNALLY, name));
				}
				String duId = application.getNextDeploymentUnitId();
				markOldInstances(name, application, jmxPort);
				ServiceInstance serviceInstance;

				serviceInstance = BEMMModelFactoryImpl.getInstance().getServiceInstance();
				serviceInstance.setJmxPort(jmxPort);
				matchedHost.getMasterHost().addJMXPort(jmxPort, true);
				serviceInstance.setName(name);
				serviceInstance.setDeploymentPath(deploymentPath);
				serviceInstance.setDuId(duId);
				serviceInstance.setBeId(beId);
				serviceInstance.setPuId(processingUnit);
				serviceInstance.setHost(matchedHost);
				serviceInstance.setPredefined(isPredefined);
				matchedHost.addInstance(serviceInstance);
				if (!application.isMonitorableOnly()) {
					serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
				} else {
					serviceInstance.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
					serviceInstance.setStatus(BETeaAgentStatus.STOPPED.getStatus());
					serviceInstance.setDeployed(true);
				}

				serviceInstance
						.setDeploymentDescription(messageService.getMessage(MessageKey.INSTANCE_CREATED_MESSAGE));
				serviceInstance.setDeployed(false);
				serviceInstance.setJmxUserName(jmxUserName);
				serviceInstance.setJmxPassword(ManagementUtil.getEncodedPwd(jmxPassword));
				if (!application.isMonitorableOnly()) {
					ProcessingUnit procUnit = application.getProcessingUnit(processingUnit);
					boolean hasInferneceAgent = false;
					for (com.tibco.cep.bemm.model.AgentConfig agentConfig : procUnit.getAgents()) {
						Agent agent = agentModelFactory.getAgent();
						agent.setAgentName(agentConfig.getAgentName());
						agent.setAgentType(agentConfig.getAgentType());
						agent.setInstance(serviceInstance);
						serviceInstance.addAgent(agent);
						if (!hasInferneceAgent && AgentType.INFERENCE.getType()
								.equalsIgnoreCase(agentConfig.getAgentType().getType())) {
							hasInferneceAgent = true;
						}
					}
					if (hasInferneceAgent) {
						serviceInstance.setDeployClasses(null != application.getExternalClassesPath()
								&& !application.getExternalClassesPath().trim().isEmpty() ? true : false);
					}
					serviceInstance.setRuleTemplateDeploy(null != application.getRuleTemplateDeployDir()
							&& !application.getRuleTemplateDeployDir().trim().isEmpty() ? true : false);

					Collection<GlobalVariableDescriptor> globalVariableDescriptors = applicationGVCacheService
							.getServiceSettableGlobalVariables(application.getName(),
									((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());
					Map<Object, Object> cddProperties = new HashMap<Object, Object>();
					ManagementUtil.loadCDDProps(application, cddProperties);

					Map<String, String> traProperties = loadTRAProperties(application, matchedHost, serviceInstance);

					DeploymentVariableUtil.initDeploymentVariables(globalVariableDescriptors, dataStoreService,
							serviceInstance, cddProperties, traProperties, false);

				}
				DeploymentVariables instanceConfig = new DeploymentVariables();
				instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
				storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
				if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
					AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.CREATE_INSTANCE,
							messageService.getMessage(MessageKey.INSTANCE_CREATED_MESSAGE), loggedInUser);
					storeAuditRecords(auditRecord, serviceInstance);
				}
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.LOGGER_INSTANCE_CREATED_SUCCESS));
			} else {
				throw new BEServiceInstanceAddException(messageService.getMessage(MessageKey.HOST_EXIST_ERROR_MESSAGE));
			}

		} catch (Exception e) {
			throw new BEServiceInstanceAddException(
					messageService.getMessage(MessageKey.INSTANCE_CREATE_ERROR_MESSAGE, name), e);
		}

		return messageService.getMessage(MessageKey.INSTANCE_CREATE_SUCCESS_MESSAGE, name);

	}

	@Override
	public String deleteServiceInstance(Application application, String name)
			throws BEServiceInstanceDeleteException, BEValidationException {
		return delele(application, name);
	}

	@Override
	public String deploy(ServiceInstance serviceInstance, String loggedInUser)
			throws DeploymentFailException, BEValidationException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_DEPLOY_SERVICE_INSTANCE_PAGE));
		validationService.vaidateServiceInstance(serviceInstance);
		if (!serviceInstance.getHost().getMasterHost().isAuthenticated())
			throw new DeploymentFailException(messageService
					.getMessage(MessageKey.INSTANCE_AUTHENTICATION_ERROR_MESSAGE, serviceInstance.getName()));

		// Create A Job and submit it
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

		jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
					serviceInstance.getHost().getApplication().getName(),
					((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.GET_GLOBAL_VARIABLES_ERROR));
		}
		List<Object> results = poolService.submitJobs(
				new BETeaDeployInstanceJob(this, loggedInUser, globalVariableDescriptors), jobExecutionContexts);
		for (Object object : results) {
			if (null == object)
				return messageService.getMessage(MessageKey.INSTANCE_DEPLOY_ERROR_MESSAGE, serviceInstance.getName());
			if (object instanceof Exception)
				throw new DeploymentFailException((Exception) object);
		}
		return messageService.getMessage(MessageKey.INSTANCE_DEPLOY_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public String undeploy(ServiceInstance serviceInstance, String loggedInUser)
			throws DeploymentFailException, BEValidationException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_DEPLOY_SERVICE_INSTANCE_PAGE));
		validationService.vaidateServiceInstance(serviceInstance);
		if (!serviceInstance.getHost().getMasterHost().isAuthenticated())
			throw new DeploymentFailException(messageService
					.getMessage(MessageKey.INSTANCE_AUTHENTICATION_ERROR_MESSAGE, serviceInstance.getName()));

		// Create a job and submit it
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

		jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
					serviceInstance.getHost().getApplication().getName(),
					((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());

		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.GET_GLOBAL_VARIABLES_ERROR));
		}
		List<Object> results = poolService.submitJobs(
				new BETeaUndeployInstanceJob(this, loggedInUser, globalVariableDescriptors), jobExecutionContexts);
		for (Object object : results) {
			if (null == object)
				return messageService.getMessage(MessageKey.INSTANCE_UNDEPLOY_ERROR_MESSAGE, serviceInstance.getName());
			if (object instanceof Exception)
				throw new DeploymentFailException((Exception) object);
		}
		return messageService.getMessage(MessageKey.INSTANCE_UNDEPLOY_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public String start(ServiceInstance serviceInstance, String loggedInUser)
			throws BEProcessingUnitStartFailException, BEValidationException, JschAuthenticationException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_START_SERVICE_INSTANCE_PAGE));
		validationService.vaidateServiceInstance(serviceInstance);
		// Create a job and submit it
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

		jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));

		List<Object> results = poolService.submitJobs(new BETeaStartInstanceJob(this, loggedInUser),
				jobExecutionContexts);
		for (Object object : results) {
			if (null == object)
				return messageService.getMessage(MessageKey.INSTANCE_START_ERROR_MESSAGE, serviceInstance.getName());
			if (object instanceof Exception)
				throw new BEProcessingUnitStartFailException((Exception) object);
		}
		return messageService.getMessage(MessageKey.INSTANCE_START_SUCCESS_MESSAGE, serviceInstance.getName());

	}

	@Override
	public String stop(ServiceInstance serviceInstance, String loggedInUser)
			throws BEProcessingUnitStopFailException, BEValidationException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_STOP_SERVICE_INSTANCE_PAGE));
		validationService.vaidateServiceInstance(serviceInstance);
		if (!serviceInstance.getHost().getApplication().isMonitorableOnly() && !serviceInstance.getDeployed()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INSTANCE_NOT_DEPLOYED));
		}
		if (BETeaAgentStatus.RUNNING.getStatus().equals(serviceInstance.getStatus())) {

			/**
			 * Get the processId and update status
			 */
			try {
				mbeanService.stopInstance(serviceInstance);
				serviceInstance.setStatus(BETeaAgentStatus.STOPPING.getStatus());
				for (Agent agent : serviceInstance.getAgents()) {
					agent.setStatus(BETeaAgentStatus.STOPPING.getStatus());
				}
				serviceInstance.setRemoteMetricsCollectorService(null);
				mbeanService.removeJMXConnection(serviceInstance.getKey());
				if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
					AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.STOP_INSTANCE,
							messageService.getMessage(MessageKey.INSTANCE_STOPPED), loggedInUser);
					storeAuditRecords(auditRecord, serviceInstance);
				}
			} catch (Exception e) {
				throw new BEProcessingUnitStopFailException(
						messageService.getMessage(MessageKey.INSTANCE_STOP_ERROR_MESSAGE, serviceInstance.getName()),
						e);
			}

		} else {
			throw new BEProcessingUnitStopFailException(
					messageService.getMessage(MessageKey.SERVICE_INSTANCE_NOT_RUNNING_ERROR_MESSAGE));
		}
		return messageService.getMessage(MessageKey.INSTANCE_STOP_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public String editServiceInstance(ServiceInstance config, String processingUnit, String hostName, int jmxPort,
			String deploymentPath, String loggedInUser, String jmxUserName, String jmxPassword, String beId)
			throws BEServiceInstanceEditException, BEValidationException {

		validationService.vaidateServiceInstance(config);
		Host oldHost = config.getHost();
		Application application = oldHost.getApplication();

		try {
			validateHost(hostName, beId, application.getAppVersion());
		} catch (ObjectCreationException e) {
			throw new BEValidationException(e);
		}

		ServiceInstance instance = config;
		// if
		// (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(instance.getStatus()))
		// {
		// throw new BEServiceInstanceEditException(
		// messageService.getMessage(MessageKey.INSTANCE_EDIT_DEPLOYED_INSTANCE_ERROR_MESSAGE));
		// }
		if (null != application) {
			// If host name of current instance is not matching with passed
			// host name then get the host from application for passed host
			// name.
			Host host = null;
			if (!oldHost.getHostName().trim().equals(hostName.trim())) {
				host = getHost(hostName, instance, oldHost, application);
			} else {
				host = oldHost;
			}

			if (isInstanceExistByJMXPort(host, jmxPort, config)) {
				throw new BEServiceInstanceEditException(
						messageService.getMessage(MessageKey.INSTANCE_DUPLICATE_JMX_PORT_ERROR_MESSAGE, jmxPort));
			}

			// Validate Deployment path
			try {
				deploymentPath = ManagementUtil.getDefaultDeploymentPath(deploymentPath, config, host);
			} catch (BEServiceInstanceValidationException e) {
				throw new BEServiceInstanceEditException(e.getMessage());
			}

			// Update agent
			if (!config.getPuId().trim().equals(processingUnit.trim())) {
				config.getAgents().clear();
				updateAgent(processingUnit, config, application);
			}

			config.setHost(host);
			config.setPuId(processingUnit);
			config.setDeploymentPath(deploymentPath);
			if (jmxPort != config.getJmxPort()
					|| (null != config.getJmxUserName() && config.getJmxUserName().trim().equals(jmxUserName))
					|| !(null != config.getJmxPassword()
							&& config.getJmxPassword().trim().equals(ManagementUtil.getEncodedPwd(jmxPassword)))) {
				mbeanService.removeJMXConnection(config.getKey());
				host.getMasterHost().addJMXPort(jmxPort, true);
				host.getMasterHost().addJMXPort(config.getJmxPort(), false);
			}
			config.setBeId(beId);
			config.setJmxPort(jmxPort);
			config.setJmxUserName(jmxUserName);
			config.setJmxPassword(ManagementUtil.getEncodedPwd(jmxPassword));

			config.setDeploymentDescription(messageService.getMessage(MessageKey.INSTANCE_DETAILS_UPDATED_MESSAGE));
			if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(config.getStatus())) {
				config.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
			} else {
				config.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			}

			Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;

			DeploymentVariables instanceConfig = deployVariablesObjectFactory.createDeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			try {
				storeDeploymentVariables(config, instanceConfig, loggedInUser, true);
				if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
					AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
							messageService.getMessage(MessageKey.INSTANCE_UPDATED_MESSAGE), loggedInUser);

					storeAuditRecords(auditRecord, config);
				}

				// If old and new host are not same then on edit instance change
				// the deployment variables
				if (!oldHost.getKey().equals(host.getKey())) {
					host.getInstances().remove(config);
					oldHost.getInstances().remove(instance);
					host.getInstances().add(config);
					try {
						globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
								application.getName(),
								((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());

					} catch (Exception e) {
						LOGGER.log(Level.DEBUG, e, e.getMessage());
					}

					Map<Object, Object> cddProperties = new HashMap<Object, Object>();
					ManagementUtil.loadCDDProps(application, cddProperties);
					Map<String, String> traProperties = loadTRAProperties(host.getApplication(), host, config);
					DeploymentVariableUtil.initDeploymentVariables(globalVariableDescriptors, dataStoreService, config,
							cddProperties, traProperties, false);
				}
				dataStoreService.storeApplicationTopology(application);
			} catch (Exception e) {
				config = instance;
				oldHost.getInstances().add(config);
				throw new BEServiceInstanceEditException(
						messageService.getMessage(MessageKey.INSTANCE_EDIT_ERROR_MESSAGE, instance.getName()), e);
			}
		}

		return messageService.getMessage(MessageKey.INSTANCE_EDIT_SUCCESS_MESSAGE, instance.getName());
	}

	@Override
	public String hotdeploy(ServiceInstance serviceInstance, DataSource earArchive, String loggedInUser)
			throws DeploymentFailException, BEValidationException {
		validationService.vaidateServiceInstance(serviceInstance);
		if (!serviceInstance.getHost().getMasterHost().isAuthenticated())
			throw new DeploymentFailException(messageService
					.getMessage(MessageKey.INSTANCE_AUTHENTICATION_ERROR_MESSAGE, serviceInstance.getName()));

		Host host = serviceInstance.getHost();

		Application application = host.getApplication();
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.APPLICATION_NULL_ERROR_MESSAGE));
		}
		if (null == earArchive) {
			throw new BEValidationException(messageService.getMessage(MessageKey.BE_ARCHIVE_NULL_ERROR_MESSAGE));
		}

		String name = serviceInstance.getName();
		File tempEarFile = null;
		try {
			tempEarFile = File.createTempFile(name + "_" + System.currentTimeMillis(), ".ear",
					dataStoreService.getTempFileLocation());
			OutputStream out = new FileOutputStream(tempEarFile);
			byte[] b = ManagementUtil.getByteArrayFromStream(earArchive.getInputStream());
			out.write(b);
			out.close();

			String applicationName = application.getName();

			// Get deployment path details.
			String deploymentPath = serviceInstance.getDeploymentPath();
			String remoteFilePath = deploymentPath + "/" + applicationName + "/" + name + "/" + applicationName
					+ ".ear";
			String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
			if (startPuMethod.startsWith("windows")) {
				remoteFilePath = BEAgentUtil.getWinSshPath(remoteFilePath);
			}
			SshConfig sshConfig = getSshConfig(serviceInstance);
			List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<GroupJobExecutionContext>();
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, sshConfig));
			List<Object> results = poolService.submitJobs(
					new BETeaUploadFileJob(remoteFilePath, tempEarFile.getAbsolutePath(), null, timeout()),
					jobExecutionContexts);
			if (null != results) {
				int errorCount = 0;
				for (Object object : results) {
					if (!(object instanceof Boolean))
						errorCount++;
					else {
						boolean result = (Boolean) object;
						if (!result)
							errorCount++;
					}
				}
				if (errorCount != 0)
					throw new DeploymentFailException(messageService
							.getMessage(MessageKey.INSTANCE_HOTDEPLOY_ERROR_MESSAGE, serviceInstance.getName()));
			}

		} catch (Exception e) {
			throw new DeploymentFailException(messageService
					.getMessage(MessageKey.APPLICATION_INSTANCE_HOTDEPLOY_ERROR_MESSAGE, serviceInstance.getName()), e);
		} finally {
			if (null != tempEarFile)
				try {
					ManagementUtil.delete(tempEarFile);
				} catch (FileNotFoundException e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				}
		}
		return messageService.getMessage(MessageKey.INSTANCE_HOTDEPLOY_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public String hotdeploy(ServiceInstance serviceInstance, String localEarFile, String loggedInUser, Session session)
			throws DeploymentFailException, BEValidationException {
		validationService.vaidateServiceInstance(serviceInstance);

		Host host = serviceInstance.getHost();

		Application application = host.getApplication();

		String name = serviceInstance.getName();
		try {

			String applicationName = application.getName();

			// Get deployment path details.
			String deploymentPath = serviceInstance.getDeploymentPath();
			String remoteFilePath = deploymentPath + "/" + applicationName + "/" + name + "/" + applicationName
					+ ".ear";
			String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
			if (startPuMethod.startsWith("windows")) {
				remoteFilePath = BEAgentUtil.getWinSshPath(remoteFilePath);
			}
			ManagementUtil.uploadToRemoteMahine(remoteFilePath, session, localEarFile, timeout());

		} catch (Exception e) {
			throw new DeploymentFailException(
					messageService.getMessage(MessageKey.INSTANCE_HOTDEPLOY_ERROR_MESSAGE, serviceInstance.getName()),
					e);
		} finally {

		}
		return messageService.getMessage(MessageKey.INSTANCE_HOTDEPLOY_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public String copyInstance(String instanceName, String processingUnit, String hostName, String deploymentPath,
			int jmxPort, String jmxUserName, String jmxPassword, String beId, ServiceInstance origInstance,
			String loggedInUser) throws CopyInstanceFailException, BEValidationException {

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_COPYINSTANCE_METHOD));
		validationService.vaidateServiceInstance(origInstance);
		try {
			validateHost(hostName, beId, origInstance.getHost().getApplication().getAppVersion());
		} catch (ObjectCreationException e) {
			throw new CopyInstanceFailException(e);
		}

		// If instance Name is not valid then throw exception
		if (!validationService.isNotNullAndEmpty(instanceName)) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INVALID_INSTANCE_NAME_MESSAGE));
		}
		// JMX Port should be valid
		if (jmxPort <= 0) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INVALID_JMX_PORT_MESSAGE));
		}

		ServiceInstance copyInstance = null;
		try {
			BEApplicationsManagementService applicationManagementService = BEMMServiceProviderManager.getInstance()
					.getBEApplicationsManagementService();
			Application application = origInstance.getHost().getApplication();
			/*
			 * Instance name should be unique through out the application as it
			 * is used as engine name
			 */
			if (isInstanceExistByName(instanceName, application)) {
				throw new BEServiceInstanceAddException(messageService.getMessage(MessageKey.INSTANCE_DUPLICATE_ERROR_MESSAGE, instanceName));
			}
			
			Host matchedHost = applicationManagementService.getHostByHostName(application.getName(), hostName);
			/*
			 * If application does not have host for passed host then add new
			 * host.
			 */
			if (null == matchedHost) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.ADDING_MASTER_HOST_AS_APPLICATION_HOST));
				matchedHost = applicationManagementService.addApplicationHost(application, hostName);
			}
			LOGGER.log(Level.DEBUG,
					"Input parameters are Instance Name= %s,Processing Unit =%s,Host Name=%s,Deployment path=%s,JMX Port %d",
					instanceName, processingUnit, matchedHost.getName(), deploymentPath, jmxPort);

			// Validate deployment path
			try {
				deploymentPath = ManagementUtil.getDefaultDeploymentPath(deploymentPath, origInstance, matchedHost);
			} catch (BEServiceInstanceValidationException e) {
				throw new CopyInstanceFailException(e.getMessage());
			}
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYMENT_PATH, deploymentPath));

			copyInstance = agentModelFactory.getServiceInstance();
			copyInstance.setDeployed(false);
			copyInstance.setDeploymentDescription(messageService.getMessage(MessageKey.INSTANCE_CREATED_MESSAGE));
			copyInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			copyInstance.setHost(matchedHost);
			copyInstance.setName(instanceName);
			copyDeploymentVariable(origInstance.getGlobalVariables(), DeploymentVariableType.GLOBAL_VARIABLES,
					copyInstance);
			copyDeploymentVariable(origInstance.getSystemVariables(), DeploymentVariableType.SYSTEM_VARIABLES,
					copyInstance);
			copyDeploymentVariable(origInstance.getJVMProperties(), DeploymentVariableType.JVM_PROPERTIES,
					copyInstance);
			copyDeploymentVariable(origInstance.getLoggerPatternAndLogLevel(), DeploymentVariableType.LOG_PATTERNS,
					copyInstance);
			copyDeploymentVariable(origInstance.getBEProperties(), DeploymentVariableType.BE_PROPERTIES, copyInstance);

			copyInstance.setJmxPort(jmxPort);

			copyInstance.setPuId(processingUnit);
			copyInstance.setBeId(beId);
			copyInstance.setPredefined(true);
			copyInstance.setDeploymentPath(deploymentPath);
			copyInstance.setDuId(application.getNextDeploymentUnitId());
			copyInstance.setJmxUserName(jmxUserName);
			copyInstance.setJmxPassword(ManagementUtil.getEncodedPwd(jmxPassword));

			// Change agent if pu is diffrent
			updateAgent(processingUnit, copyInstance, application);
			copyInstance.setRuleTemplateDeploy(null != application.getRuleTemplateDeployDir()
					&& !application.getRuleTemplateDeployDir().trim().isEmpty() ? true : false);
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);

			storeDeploymentVariables(copyInstance, copyInstance.getGlobalVariables(), loggedInUser, true);
			storeDeploymentVariables(copyInstance, copyInstance.getJVMProperties(), loggedInUser, true);
			storeDeploymentVariables(copyInstance, copyInstance.getSystemVariables(), loggedInUser, true);
			storeDeploymentVariables(copyInstance, copyInstance.getLoggerLogLevels(), loggedInUser, true);
			storeDeploymentVariables(copyInstance, instanceConfig, loggedInUser, true);
			storeDeploymentVariables(copyInstance, copyInstance.getBEProperties(), loggedInUser, true);
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.CREATE_INSTANCE,
						messageService.getMessage(MessageKey.INSTANCE_CREATE), loggedInUser);

				storeAuditRecords(auditRecord, copyInstance);
			}
			matchedHost.addInstance(copyInstance);
			dataStoreService.storeApplicationTopology(application);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CLONED_INSTANCE));

		}catch(BEServiceInstanceAddException c ){
			
			throw new CopyInstanceFailException(
					messageService.getMessage(MessageKey.INSTANCE_DUPLICATE_ERROR_MESSAGE, instanceName), c);
		}		
		catch (Exception e) {
			LOGGER.log(Level.ERROR, e, messageService.getMessage(MessageKey.CLONED_INSTANCE));
			throw new CopyInstanceFailException(
					messageService.getMessage(MessageKey.INSTANCE_CLONE_ERROR_MESSAGE, origInstance.getName()), e);
		}

		return (messageService.getMessage(MessageKey.INSTANCE_CLONE_SUCCESS_MESSAGE, origInstance.getName()));
	}

	@Override
	public DataSource downloadLog(ServiceInstance instance, boolean isASLog)
			throws BEValidationException, BEDownloadLogException {

		Host host = instance.getHost();
		if (!host.getMasterHost().isAuthenticated()) {
			throw new BEDownloadLogException(
					messageService.getMessage(MessageKey.INSTANCE_AUTHENTICATION_ERROR_MESSAGE, instance.getName()));
		}
		File tempFile = null;
		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String fileName = instance.getName() + "_" + currentTimeStamp;
		if (null != instance && null != host) {
			if (instance.getDeployed()) {
				List<GroupJobExecutionContext> contexts = new ArrayList<>();
				contexts.add(new JSchGroupJobExecutionContext(instance, getSshConfig(instance)));

				Map<String, String> traProps = loadTRAProperties(instance.getHost().getApplication(),
						instance.getHost(), instance);
				Map<String, String> instanceLogLocation = new HashMap<String, String>();
				instanceLogLocation = FetchCddDataUtil.getLogLocationMap(instanceLogLocation, instance,
						instance.getHost().getApplication().getName(), dataStoreService, traProps);

				List<Object> results = poolService.submitJobs(new BETeaDownloadLogJob(
						dataStoreService.getTempFileLocation(), instanceLogLocation, timeout(), isASLog), contexts);
				if (null != results) {
					for (Object object : results) {
						if (object instanceof BETeaDownloadLogEntry) {
							BETeaDownloadLogEntry result = (BETeaDownloadLogEntry) object;
							if (result.getErrorCode() == 0) {
								FileOutputStream fos = null;
								ZipOutputStream zos = null;
								try {
									InputStream inputStream = new FileInputStream(result.getLogFilePath());
									if (null != inputStream) {

										tempFile = File.createTempFile(fileName, "_logs.zip",
												dataStoreService.getTempFileLocation());
										fos = new FileOutputStream(tempFile);
										zos = new ZipOutputStream(fos);
										ZipEntry zipEntry = new ZipEntry(result.getLogfilename());
										zos.putNextEntry(zipEntry);
										byte[] bytes = new byte[1024];
										int length;
										while ((length = inputStream.read(bytes)) >= 0) {
											zos.write(bytes, 0, length);
										}
										inputStream.close();
										zos.closeEntry();
									}

								} catch (IOException e) {
									LOGGER.log(Level.DEBUG, e.getMessage());
								} finally {
									try {
										if (null != zos)
											zos.close();
									} catch (IOException e) {
									}
									try {
										if (null != fos)
											fos.close();
									} catch (IOException e) {
									}
								}
							} else {
								if (404 == result.getErrorCode())
									throw new BEDownloadLogException(
											messageService.getMessage(MessageKey.INSTANCE_LOG_EXIST_ERROR_MESSAGE));
								if (500 == result.getErrorCode())
									throw new BEDownloadLogException(
											messageService.getMessage(MessageKey.INSTANCE_DOWNLOAD_LOG_ERROR_MESSAGE));
							}
						}
					}
				}

			}

		}
		final String logFileName = fileName + "_logs.zip";
		DataSource instanceLog = new FileDataSource(tempFile) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.activation.FileDataSource#getName()
			 */
			@Override
			public String getName() {
				return logFileName;
			}
		};
		return instanceLog;
	}

	@Override
	public String storeDeploymentVariables(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction)
			throws DeploymentVariableStoreFailException, BEValidationException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.PERSISTENING_DEPLOYMENT_VARIABLE,
				deploymentVariables, serviceInstance.getName()));
		String message = null;
		if (null != deploymentVariables) {
			DeploymentVariableType type = deploymentVariables.getType();
			if (DeploymentVariableType.INSTANCE_CONFIG.equals(type)) {
				try {
					if (null != loggedInUser)
						deploymentVariables.setName(serviceInstance.getName());

					NameValuePairs nameValuePairs = new NameValuePairs();
					NameValuePair deployed = new NameValuePair();
					deployed.setName(Constants.DEPLOYMENT);
					deployed.setValue(String.valueOf(serviceInstance.getDeployed()));
					nameValuePairs.getNameValuePair().add(deployed);

					NameValuePair deploymentStatus = new NameValuePair();
					deploymentStatus.setName(Constants.DEPLOYMENT_STATUS);
					deploymentStatus.setValue(serviceInstance.getDeploymentStatus());
					nameValuePairs.getNameValuePair().add(deploymentStatus);

					NameValuePair description = new NameValuePair();
					description.setName(Constants.DESCRIPTION);
					description.setValue(serviceInstance.getDeploymentDescription());
					nameValuePairs.getNameValuePair().add(description);

					NameValuePair deploymentTime = new NameValuePair();
					deploymentTime.setName(Constants.LAST_DEPLOYEMNT_TIME);
					deploymentTime.setValue(String.valueOf(new Date().getTime()));
					nameValuePairs.getNameValuePair().add(deploymentTime);

					NameValuePair hotDeployable = new NameValuePair();
					hotDeployable.setName(Constants.HOT_DEPLOYABLE);
					hotDeployable.setValue(Boolean.toString(serviceInstance.isHotDeployable()));
					nameValuePairs.getNameValuePair().add(hotDeployable);

					NameValuePair defaultProfile = new NameValuePair();
					hotDeployable.setName(Constants.DEFAULT_PROFILE);
					hotDeployable.setValue(serviceInstance.getDefaultProfile());
					nameValuePairs.getNameValuePair().add(defaultProfile);

					deploymentVariables.setNameValuePairs(nameValuePairs);
					String applicationNanme = serviceInstance.getHost().getApplication().getName();
					dataStoreService.storeDeploymentVariables(applicationNanme, serviceInstance.getName(),
							deploymentVariables);
					message = messageService.getMessage(MessageKey.INSTANCE_CONFIG_SAVE_SUCCESS_MESSAGE,
							serviceInstance.getName());
				} catch (BEServiceInstanceSaveException e) {
					throw new DeploymentVariableStoreFailException(messageService
							.getMessage(MessageKey.INSTANCE_CONFIG_SAVE_ERROR_MESSAGE, serviceInstance.getName()), e);
				}
			} else {
				NameValuePairs sourceNameValuePairs = deploymentVariables.getNameValuePairs();
				if (null != sourceNameValuePairs) {
					List<NameValuePair> sourceDeploymentVariables = sourceNameValuePairs.getNameValuePair();
					if (null != sourceNameValuePairs)
						switch (type) {
						case GLOBAL_VARIABLES:

							try {

								message = storeGlobalVariables(serviceInstance, deploymentVariables, loggedInUser,
										isIntermediateAction, message, sourceDeploymentVariables);
							} catch (Exception ex) {
								throw new DeploymentVariableStoreFailException(messageService.getMessage(
										MessageKey.INSTANCE_SAVE_GV_ERROR_MESSAGE, serviceInstance.getName()));
							}

							break;
						case SYSTEM_VARIABLES:

							try {
								message = storeSystemProperties(serviceInstance, deploymentVariables, loggedInUser,
										isIntermediateAction, message, sourceDeploymentVariables);
							} catch (Exception ex) {
								throw new DeploymentVariableStoreFailException(messageService.getMessage(
										MessageKey.INSTANCE_SAVE_SV_ERROR_MESSAGE, serviceInstance.getName()));
							}

							break;
						case JVM_PROPERTIES:

							try {
								message = storeJVMProperties(serviceInstance, deploymentVariables, loggedInUser,
										isIntermediateAction, message, sourceDeploymentVariables);
							} catch (Exception ex) {
								throw new DeploymentVariableStoreFailException(messageService.getMessage(
										MessageKey.INSTANCE_SAVE_JP_ERROR_MESSAGE, serviceInstance.getName()));
							}

							break;
						// Not In use.Kept for future use
						case LOG_CONFIG:

							break;
						case LOG_PATTERNS:
							try {
								message = storeLogPatternAndLogLevel(serviceInstance, deploymentVariables, loggedInUser,
										isIntermediateAction, message, sourceDeploymentVariables);

							} catch (Exception ex) {
								throw new DeploymentVariableStoreFailException(
										messageService.getMessage(MessageKey.INSTANCE_SAVE_LOG_LEVEL_ERROR_MESSAGE),
										ex);
							}

							break;
						case BE_PROPERTIES:

							try {
								message = storeBEProperties(serviceInstance, deploymentVariables, loggedInUser,
										isIntermediateAction, message, sourceDeploymentVariables);
							} catch (Exception ex) {
								throw new DeploymentVariableStoreFailException(
										messageService.getMessage(MessageKey.INSTANCE_SAVE_BE_PROPERTIES_ERROR_MESSAGE),
										ex);
							}

							break;
						default:
							break;
						}
				}
			}

		}
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.PERSISTED_DEPLOYMENT_VARIABLE, serviceInstance.getName()));
		return message;

	}

	@Override
	public AuditRecords loadAuditRecords(ServiceInstance instance) throws Exception {
		return dataStoreService.fetchAuditRecords(instance.getHost().getApplication().getName(), instance.getName());
	}

	@Override
	public void storeAuditRecords(AuditRecord auditRecord, ServiceInstance instance) throws Exception {
		if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
			AuditRecords auditRecords = dataStoreService
					.fetchAuditRecords(instance.getHost().getApplication().getName(), instance.getName());
			if (null == auditRecords) {
				auditRecords = new AuditRecords();
			}
			auditRecords.getAuditRecord().add(auditRecord);
			dataStoreService.storeAuditRecords(instance.getHost().getApplication().getName(), instance.getName(),
					auditRecords);
		}

	}

	@Override
	public CPUUsage getCPUUsage(ServiceInstance instance) throws MBeanOperationFailException {
		return mbeanService.getCPUUsage(instance);
	}

	@Override
	public MemoryUsage getMemoryByPoolName(String poolName, ServiceInstance instance)
			throws MBeanOperationFailException {
		return mbeanService.getMemoryByPoolName(poolName, instance);
	}

	@Override
	public List<String> getMemoryPools() throws MBeanOperationFailException {
		return mbeanService.getMemoryPools();
	}

	@Override
	public List<ProcessMemoryUsageImpl> getMemoryUsage(ServiceInstance instance) throws MBeanOperationFailException {
		return mbeanService.getMemoryUsage(instance);
	}

	@Override
	public ThreadDetail getThreadDetails(ServiceInstance instance) throws MBeanOperationFailException {
		return mbeanService.getThreadDetails(instance);
	}

	@Override
	public List<LogDetailImpl> getRuntimeLoggerLevels(ServiceInstance instance) throws MBeanOperationFailException {
		return mbeanService.getLoggerDetails(instance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult invoke(String entityName, String methodGroup, String methodName, Map<String, String> params,
			String username, String password, String hostIp, int jmxPort, int agentId, ServiceInstance serviceInstance)
			throws MBeanOperationFailException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKED_METHOD_FROM_GROUP, methodName, methodGroup,
				entityName, params));
		List<String> sessionNames = new ArrayList<String>();
		String paramName = "";
		if (params.containsKey("Session")) {
			paramName = "Session";
			String paramValue = params.get("Session");
			if (null != paramValue && !paramValue.trim().isEmpty()) {
				String[] parts = paramValue.split(",");
				sessionNames.addAll(Arrays.asList(parts));
			}

		} else if (params.containsKey("sessionName")) {
			paramName = "sessionName";
			String paramValue = params.get("sessionName");
			if (null != paramValue && !paramValue.trim().isEmpty()) {
				String[] parts = paramValue.split(",");
				sessionNames.addAll(Arrays.asList(parts));
			}
		}
		OperationResult operationResult = new OperationResult();
		String clusterName = serviceInstance.getHost().getApplication().getClusterName();
		if ("" != paramName) {
			if (!sessionNames.isEmpty()) {
				Map<String, OperationResult> resultMap = new HashMap<>();
				for (String sessionName : sessionNames) {
					if (isSessionNameExist(sessionName, serviceInstance)) {
						Map<String, String> newParams = new HashMap<>();
						newParams.putAll(params);
						newParams.put(paramName, sessionName);

						OperationResult result = mbeanService.invoke(entityName, methodGroup, methodName, newParams,
								username, password, hostIp, jmxPort, agentId, serviceInstance.getKey(), clusterName);
						resultMap.put(sessionName, result);
					}
				}
				for (OperationResult result : resultMap.values()) {
					operationResult.setHeaders(result.getHeaders());
					if (null == operationResult.getRows())
						operationResult.setRows(new Rows());
					if (null != result.getRows())
						operationResult.getRows().getRow().addAll(result.getRows().getRow());
				}

			} else {
				params.put(paramName, "");
				operationResult = mbeanService.invoke(entityName, methodGroup, methodName, params, username, password,
						hostIp, jmxPort, agentId, serviceInstance.getKey(), clusterName);
			}

		} else {
			operationResult = mbeanService.invoke(entityName, methodGroup, methodName, params, username, password,
					hostIp, jmxPort, agentId, serviceInstance.getKey(), clusterName);
		}
		return operationResult;

	}

	@Override
	public OperationResult getGarbageCollectionDetails(ServiceInstance instance) throws MBeanOperationFailException {
		return mbeanService.getGarbageCollectionDetails(instance);
	}

	private DeploymentVariables getDeploymentVariables(ServiceInstance serviceInstance,
			DeploymentVariableType deploymentVariableType) throws DeploymentVariableLoadFailException {
		DeploymentVariables deploymentvariables = null;
		switch (deploymentVariableType) {
		case GLOBAL_VARIABLES:
			try {
				deploymentvariables = serviceInstance.getGlobalVariables();
			} catch (Exception e) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_GV_ERROR_MESSAGE) + " - "
								+ serviceInstance.getName(),
						e);
			}
			break;
		case SYSTEM_VARIABLES:
			try {
				deploymentvariables = serviceInstance.getSystemVariables();
			} catch (Exception ex) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_SP_ERROR_MESSAGE) + " - "
								+ serviceInstance.getName(),
						ex);
			}
			break;
		case JVM_PROPERTIES:
			try {
				deploymentvariables = serviceInstance.getJVMProperties();
			} catch (Exception ex) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_JVM_PROPERTIES_ERROR)
								+ serviceInstance.getName(),
						ex);
			}
			break;
		case LOG_CONFIG:
			try {
				deploymentvariables = serviceInstance.getLoggerLogLevels();
			} catch (Exception ex) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_LOG_LEVEL_ERROR_MESSAGE) + " - "
								+ serviceInstance.getName(),
						ex);
			}
			break;
		case LOG_PATTERNS:
			try {
				deploymentvariables = serviceInstance.getLoggerPatternAndLogLevel();
			} catch (Exception ex) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_LOG_LEVEL_ERROR_MESSAGE) + " - "
								+ serviceInstance.getName(),
						ex);
			}
			break;
		case BE_PROPERTIES:
			try {
				deploymentvariables = serviceInstance.getBEProperties();
			} catch (Exception ex) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_LOG_LEVEL_ERROR_MESSAGE) + " - "
								+ serviceInstance.getName(),
						ex);
			}
			break;
		case INSTANCE_CONFIG:
			try {
				deploymentvariables = dataStoreService.fetchDeploymentVaribles(
						serviceInstance.getHost().getApplication().getName(), serviceInstance, deploymentVariableType);
			} catch (Exception ex) {
				throw new DeploymentVariableLoadFailException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_LOAD_CONFIGURATION_ERROR_MESSAGE)
								+ serviceInstance.getName(),
						ex);
			}
			break;
		default:
			break;
		}
		return deploymentvariables;
	}

	@Override
	public void addDeploymentVariables(DeploymentVariableType type, ServiceInstance serviceInstance,
			GroupDeploymentVariable groupDeploymentVariable) {
		switch (type) {
		case GLOBAL_VARIABLES:
			if (null == serviceInstance.getGlobalVariables()) {
				DeploymentVariables globalVariable = deployVariablesObjectFactory.createDeploymentVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, globalVariable,
						nameValuePairs);
			} else if (null == serviceInstance.getGlobalVariables().getNameValuePairs()) {
				DeploymentVariables globalVariables = serviceInstance.getGlobalVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, globalVariables,
						nameValuePairs);
			} else {
				DeploymentVariables globalVariables = serviceInstance.getGlobalVariables();
				NameValuePairs nameValuePairs = globalVariables.getNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, globalVariables,
						nameValuePairs);
			}
			break;
		case SYSTEM_VARIABLES:
			if (null == serviceInstance.getSystemVariables()) {
				DeploymentVariables systemVariables = deployVariablesObjectFactory.createDeploymentVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, systemVariables,
						nameValuePairs);
			} else if (null == serviceInstance.getSystemVariables().getNameValuePairs()) {
				DeploymentVariables systemVariables = serviceInstance.getSystemVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, systemVariables,
						nameValuePairs);
			} else {
				DeploymentVariables systemVariables = serviceInstance.getSystemVariables();
				NameValuePairs nameValuePairs = systemVariables.getNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, systemVariables,
						nameValuePairs);
			}
			break;
		case JVM_PROPERTIES:
			if (null == serviceInstance.getJVMProperties()) {
				DeploymentVariables jvmProperties = deployVariablesObjectFactory.createDeploymentVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, jvmProperties, nameValuePairs);
			} else if (null == serviceInstance.getJVMProperties().getNameValuePairs()) {
				DeploymentVariables jvmProperties = serviceInstance.getJVMProperties();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, jvmProperties, nameValuePairs);
			} else {
				DeploymentVariables jvmProperties = serviceInstance.getJVMProperties();
				NameValuePairs nameValuePairs = jvmProperties.getNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, jvmProperties, nameValuePairs);
			}
			break;
		case LOG_PATTERNS:
			if (null == serviceInstance.getLoggerPatternAndLogLevel()) {
				DeploymentVariables logPatternAndLevels = deployVariablesObjectFactory.createDeploymentVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, logPatternAndLevels,
						nameValuePairs);
			} else if (null == serviceInstance.getLoggerPatternAndLogLevel().getNameValuePairs()) {
				DeploymentVariables logPatternAndLevels = serviceInstance.getLoggerPatternAndLogLevel();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, logPatternAndLevels,
						nameValuePairs);
			} else {
				DeploymentVariables logPatternAndLevels = serviceInstance.getLoggerPatternAndLogLevel();
				NameValuePairs nameValuePairs = logPatternAndLevels.getNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, logPatternAndLevels,
						nameValuePairs);
			}
			break;
		case APPLICATION_CONFIG:
			break;
		case INSTANCE_CONFIG:
			break;
		case BE_PROPERTIES:
			if (null == serviceInstance.getBEProperties()) {
				DeploymentVariables systemVariables = deployVariablesObjectFactory.createDeploymentVariables();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, systemVariables,
						nameValuePairs);
			} else if (null == serviceInstance.getBEProperties().getNameValuePairs()) {
				DeploymentVariables systemVariables = serviceInstance.getBEProperties();
				NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, systemVariables,
						nameValuePairs);
			} else {
				DeploymentVariables systemVariables = serviceInstance.getBEProperties();
				NameValuePairs nameValuePairs = systemVariables.getNameValuePairs();
				addNewDeploymentVariable(type, serviceInstance, groupDeploymentVariable, systemVariables,
						nameValuePairs);
			}
			break;
		default:
			break;

		}
	}

	/**
	 * Get the thread dump
	 * 
	 * @throws BEValidationException
	 * @throws JschAuthenticationException
	 */

	@Override
	public String getThreadDump(ServiceInstance instance) throws BEValidationException {
		if (null == instance) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INSTANCE_NULL_ERROR_MESSAGE));
		}
		try {
			if (instance.isRunning())
				return mbeanService.getThreadDump(instance);

		} catch (MBeanOperationFailException e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
		}
		return null;

	}

	@Override
	public String applyLogPatterns(ServiceInstance instance, Map<String, String> logPatternsAndLevel,
			String loggedInUser, boolean isIntermediateAction) throws DeploymentVariableStoreFailException {
		try {
			mbeanService.applyLogPatterns(instance, logPatternsAndLevel);
			return (messageService.getMessage(MessageKey.LOG_PATTERN_APPLIED_SUCCESS_MESSAGE));
		} catch (MBeanOperationFailException e) {
			throw new DeploymentVariableStoreFailException(
					messageService.getMessage(MessageKey.LOG_PATTERN_APPLIED_ERROR_MESSAGE));
		}
	}

	@Override
	public String uploadClassFiles(DataSource classZipFiles, ServiceInstance instance)
			throws BEValidationException, DeploymentFailException {
		validationService.vaidateServiceInstance(instance);
		Host host = instance.getHost();

		if (!host.getMasterHost().isAuthenticated()) {
			throw new DeploymentFailException(
					messageService.getMessage(MessageKey.INSTANCE_AUTHENTICATION_ERROR_MESSAGE, instance.getName()));
		}
		Application application = host.getApplication();
		List<GroupJobExecutionContext> contexts = new ArrayList<>();
		contexts.add(new JSchGroupJobExecutionContext(instance, getSshConfig(instance)));
		String externalDirPath = substituteVariables(instance.getExternalClassesPath(), instance).toString();
		List<Object> objects = poolService
				.submitJobs(new BETeaUploadClassOrRTFilesJob(this, classZipFiles, externalDirPath), contexts);
		if (null != objects) {
			for (Object object : objects) {
				if (object instanceof Boolean) {
					boolean result = (Boolean) object;
					if (result)
						return (messageService.getMessage(MessageKey.CLASS_FILES_UPLOAD_SUCCESS_MESSAGE));
				}
			}
		}

		throw new DeploymentFailException(messageService.getMessage(MessageKey.CLASS_FILES_UPLOAD_ERROR_MESSAGE));
	}

	public CharSequence substituteVariables(String externalClassesPath, ServiceInstance instance) {
		if (null == externalClassesPath) {
			return "";
		} // if

		final Matcher matcher = GLOBAL_VARIABLE_PATTERN.matcher(externalClassesPath);
		final StringBuffer substitutedText = new StringBuffer();
		while (matcher.find()) {
			final String variableValue = this.getVariableAsString(matcher.group(1), matcher.group(0), instance)
					.replaceAll(PRE_APPENDREPLACEMENT_PATTERN_STR, PRE_APPENDREPLACEMENT_REPLACE);
			matcher.appendReplacement(substitutedText, variableValue);
		} // while
		matcher.appendTail(substitutedText);

		return substitutedText;
	}

	public String getVariableAsString(String varName, String defaultValue, ServiceInstance instance) {
		String effectiveValue = null;
		List<NameValuePair> gvPropsNvList = instance.getGlobalVariables().getNameValuePairs().getNameValuePair();
		for (NameValuePair gvPropsNvPair : gvPropsNvList) {
			if (gvPropsNvPair.getName().equals(varName)) {
				effectiveValue = gvPropsNvPair.getDefaultValue();

			}
		}
		return effectiveValue;
	}

	/**
	 * Check whether the passed session name is exist in the passed service
	 * instance or not.
	 * 
	 * @param sessionName
	 * @param serviceInstance
	 * @return
	 */
	private boolean isSessionNameExist(String sessionName, ServiceInstance serviceInstance) {
		boolean isExist = false;
		List<Agent> agents = serviceInstance.getAgents();
		if (null != agents && !agents.isEmpty()) {
			for (Agent agent : agents) {
				if (null != agent && agent.getAgentName().trim().equals(sessionName)) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	private void upload(DataSource classZipFiles, String deploymemtPath, String startPuMethod, Session session)
			throws JSchException, SftpException, FileNotFoundException, IOException {

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.STARTED_UPLOAD, classZipFiles.getName()));
		try {
			File tempFile = Files.createTempDirectory("beclasses_" + System.currentTimeMillis()).toFile();
			String filePath = tempFile.getAbsolutePath();
			unzip(classZipFiles.getInputStream(), filePath);
			createFolderStructure(startPuMethod, tempFile, deploymemtPath, tempFile.getAbsolutePath(), session);
			uploadFiles(startPuMethod, tempFile, deploymemtPath, tempFile.getAbsolutePath(), session);
			ManagementUtil.delete(tempFile);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.COMPLETED_UPLOAD, classZipFiles.getName()));
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
		}
	}

	/**
	 * Upload the files to target host
	 * 
	 * @param startPuMethod
	 * @param file
	 * @param externalClassesPath
	 * @param jschClient
	 * @param tempPath
	 * @param session
	 */
	private void uploadFiles(String startPuMethod, File file, String externalClassesPath, String tempPath,
			Session session) throws FileNotFoundException {

		if (null != file && file.exists()) {
			if (!file.isDirectory()) {
				String remoteFile = externalClassesPath + file.getAbsolutePath().replace(tempPath, "");
				try {
					if (startPuMethod.startsWith("windows")) {
						remoteFile = BEAgentUtil.getWinSshPath(remoteFile);
					}
					remoteFile = "'" + remoteFile + "'";
					ManagementUtil.uploadToRemoteMahine(remoteFile, session, file.getAbsolutePath(), timeout());
				} catch (JschCommandFailException e) {
					LOGGER.log(Level.ERROR,
							messageService.getMessage(MessageKey.UPLOAD_FILE_ERROR, file.getAbsolutePath()), e);
				}

			} else {

				for (File file1 : file.listFiles()) {
					uploadFiles(startPuMethod, file1, externalClassesPath, tempPath, session);
				}
			}

		}

	}

	/**
	 * Create the folder structure
	 * 
	 * @param startPuMethod
	 * @param file
	 * @param externalClassesPath
	 * @param jschClient
	 * @param tempPath
	 * @param session
	 * @throws FileNotFoundException
	 */
	private void createFolderStructure(String startPuMethod, File file, String externalClassesPath, String tempPath,
			Session session) throws FileNotFoundException {
		if (null != file && file.exists()) {
			if (file.isDirectory()) {
				String folder = file.getAbsolutePath();
				String command = "mkdir -p '" + externalClassesPath + folder.replace(tempPath, "") + "'";
				if (startPuMethod.startsWith("windows")) {
					command = "mkdir -p '"
							+ BEAgentUtil.getWinSshPath(externalClassesPath + folder.replace(tempPath, "") + "'");

				}
				try {
					ManagementUtil.executeCommand(command, session, true, timeout(), maxRetry(), threadSleepTime());
				} catch (JschCommandFailException e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				}
				for (File file1 : file.listFiles()) {
					createFolderStructure(startPuMethod, file1, externalClassesPath, tempPath, session);
				}
			}

		}
	}

	@Override
	public String loadAndDeploy(String vrfURI, String implName, ServiceInstance instance) throws Exception {
		try {
			mbeanService.loadAndDeploy(vrfURI, implName, instance);
			if (null != vrfURI && !vrfURI.trim().isEmpty() && null != implName && !implName.trim().isEmpty()) {
				return "Class file for " + implName + " implementation of " + vrfURI + " deployed successfully";
			}
			return "Class files deployed successfully";
		} catch (MBeanOperationFailException e) {
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public void updateDeploymentStatus(ServiceInstance serviceInstance, BETeaAgentStatus deploymentStatus,
			String loggedInUser) throws DeploymentVariableStoreFailException, BEValidationException {
		if (BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.equals(deploymentStatus)) {
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
		}
	}

	@Override
	public String uploadRuleTemplates(DataSource ruleTemplateZipFile, ServiceInstance instance)
			throws BEValidationException, DeploymentFailException {
		validationService.vaidateServiceInstance(instance);
		Host host = instance.getHost();

		if (!host.getMasterHost().isAuthenticated()) {
			throw new DeploymentFailException(
					messageService.getMessage(MessageKey.INSTANCE_AUTHENTICATION_ERROR_MESSAGE, instance.getName()));
		}
		Application application = host.getApplication();

		List<GroupJobExecutionContext> contexts = new ArrayList<>();
		contexts.add(new JSchGroupJobExecutionContext(instance, getSshConfig(instance)));
		String ruleTemplateDeployDir = substituteVariables(instance.getRuleTemplateDeployDir(), instance).toString();
		List<Object> objects = poolService.submitJobs(
				new BETeaUploadClassOrRTFilesJob(this, ruleTemplateZipFile, ruleTemplateDeployDir), contexts);
		if (null != objects) {
			for (Object object : objects) {
				if (object instanceof Boolean) {
					boolean result = (Boolean) object;
					if (result)
						return (messageService.getMessage(MessageKey.RULE_TEMPLATE_FILE_UPLOAD_SUCCESS_MESSAGE));
				}
			}
		}

		throw new DeploymentFailException(
				messageService.getMessage(MessageKey.RULE_TEMPLATE_FILE_UPLOAD_ERROR_MESSAGE));
	}

	@Override
	public String loadAndDeployRuleTemplates(String agentName, String projectName, String ruleTemplateInstanceFQN,
			ServiceInstance instance) throws Exception {
		try {
			mbeanService.loadAndDeployRuleTemplates(agentName, projectName, ruleTemplateInstanceFQN, instance);
			if (null != agentName && !agentName.trim().isEmpty()) {
				return (messageService.getMessage(MessageKey.RULE_TEMPLATE_FILE_DEPLOY_SUCCESS));
			}
			return (messageService.getMessage(MessageKey.RULE_TEMPLATE_FILE_DEPLOY_SUCCESS_MESSAGE));
		} catch (MBeanOperationFailException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Load TRA properties
	 * 
	 * @param application
	 *            - Application object
	 * @param matchedHost
	 *            - Matched Host from application
	 * @return
	 */
	/*
	 * public Map<String, String> loadTRAProperties(Application application,
	 * Host matchedHost, ServiceInstance config) { LOGGER.log(Level.DEBUG,
	 * messageService.getMessage(MessageKey.LOAD_TRA_PROPERTIES,
	 * application.getName(), matchedHost.getName())); // Load TRA Properties
	 * InputStream hostTraStream = null; // Load TRA Properties if
	 * (ManagementUtil.isHostTraConfigExist(config.getHost().getHostId(),
	 * config.getHost().getApplication())) { hostTraStream =
	 * dataStoreService.fetchHostTraFile(config.getHost().getApplication().
	 * getName(), config.getHost().getHostId()); LOGGER.log(Level.DEBUG,
	 * messageService.getMessage(MessageKey.OVERRIDDEN_TRA_EXIST_FOR_APPLICATION
	 * )); } else { hostTraStream = dataStoreService.fetchHostTraFile(null,
	 * config.getHost().getHostId() + "_" + config.getBeId()); } Map<String,
	 * String> traProperties = new HashMap<String, String>();
	 * 
	 * ManagementUtil.loadTraProperties(traProperties, hostTraStream);
	 * LOGGER.log(Level.DEBUG,
	 * messageService.getMessage(MessageKey.LOAD_TRA_PROPERTIES,
	 * application.getName(), matchedHost.getName()));
	 * 
	 * return traProperties; }
	 */

	@Override
	public Object deploy(ServiceInstance serviceInstance, String loggedInUser, Session underlyingConnection,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors)
			throws BEValidationException, DeploymentFailException, JschCommandFailException {
		validationService.vaidateServiceInstance(serviceInstance);
		Host host = serviceInstance.getHost();
		Application application = host.getApplication();

		if (BETeaAgentStatus.DEPLOYED.getStatus().equals(serviceInstance.getDeploymentStatus())) {
			throw new DeploymentFailException(messageService.getMessage(MessageKey.INSTANCE_ALREADY_DEPLOY_MESSAGE));
		}
		if (!host.getMasterHost().isAuthenticated())
			throw new DeploymentFailException(messageService.getMessage(MessageKey.HOST_NOT_AUTHENTICATED_MESSAGE));

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYING_INSTANCE, serviceInstance.getName(),
				application.getName(), host.getName()));

		File appDataStoreLocation = (File) this.dataStoreService.getApplicationManagementDataStore();
		// Get deployment artifact details.
		String deploymentPath = serviceInstance.getDeploymentPath();
		// Validate deployment path
		try {
			deploymentPath = ManagementUtil.getDefaultDeploymentPath(deploymentPath, serviceInstance, host);
		} catch (BEServiceInstanceValidationException e) {
			return new DeploymentFailException(e.getMessage());
		}

		String applicationName = application.getName();
		String name = serviceInstance.getName();
		String deployedPath = deploymentPath + "/" + applicationName + "/" + name;
		String filepath = appDataStoreLocation.getAbsolutePath() + File.separator + applicationName + File.separator
				+ applicationName;
		String masterEarFilePath = filepath + ".ear";
		String masterCddFilePath = filepath + ".cdd";

		String startPuMethod = BEAgentUtil.determineMethod(host.getOs());

		// Create deployment directories

		makeDir(deployedPath, underlyingConnection, startPuMethod);

		// Upload the files in directory.
		upload(host, applicationName, deployedPath, masterEarFilePath, masterCddFilePath, serviceInstance,
				underlyingConnection, startPuMethod, globalVariableDescriptors, appDataStoreLocation);

		try {

			serviceInstance.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
			serviceInstance
					.setDeploymentDescription(messageService.getMessage(MessageKey.INSTANCE_DEPLOYED_DESCRIPTION));
			serviceInstance.setDeployed(true);
			if (serviceInstance.getStatus().equals(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus())) {
				serviceInstance.setStatus(BETeaAgentStatus.STOPPED.getStatus());
			}

			ProcessingUnit processingUnit = application.getProcessingUnit(serviceInstance.getPuId());
			serviceInstance.setHotDeployable(processingUnit.isHotDeploy());
			setDtRti(application, serviceInstance, processingUnit);

			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.DEPLOY_INSTANCE,
						messageService.getMessage(MessageKey.INSTANCE_DEPLOYED_DESCRIPTION), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}

		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
			throw new DeploymentFailException(
					messageService.getMessage(MessageKey.INSTANCE_DEPLOY_ERROR_MESSAGE, serviceInstance.getName()), e);
		}
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYED_INSTANCE, application.getName(),
				serviceInstance.getName(), host.getName()));
		return messageService.getMessage(MessageKey.INSTANCE_DEPLOY_SUCCESS_MESSAGE, serviceInstance.getName());

	}

	private void setDtRti(Application application, ServiceInstance instance, ProcessingUnit processingUnit) {

		boolean hasInferneceAgent = false;
		instance.setRuleTemplateDeployDir(null);
		instance.setExternalClassesPath(null);
		instance.setRuleTemplateDeploy(false);
		instance.setDeployClasses(false);

		for (Agent agentConfig : instance.getAgents()) {
			if (!hasInferneceAgent
					&& AgentType.INFERENCE.getType().equalsIgnoreCase(agentConfig.getAgentType().getType())) {
				hasInferneceAgent = true;
			}
		}

		if (null != application.getRuleTemplateDeployDir()
				&& !application.getRuleTemplateDeployDir().trim().isEmpty()) {
			instance.setRuleTemplateDeployDir(application.getRuleTemplateDeployDir().trim());
		}

		if (null != processingUnit) {
			Map<String, String> properties = processingUnit.getProperties();
			if (null != properties && !properties.isEmpty()) {
				Object ruleTempleDeployDir = properties
						.get(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
				if (null != ruleTempleDeployDir && !ruleTempleDeployDir.toString().isEmpty()) {
					instance.setRuleTemplateDeployDir(ruleTempleDeployDir.toString());
				}
			}
		}

		DeploymentVariables beProps = instance.getBEProperties();
		if (beProps != null) {
			NameValuePairs bePropsPairs = beProps.getNameValuePairs();
			List<NameValuePair> bePropsNvList = bePropsPairs.getNameValuePair();
			for (NameValuePair bePropsNvPair : bePropsNvList) {
				String effectiveValue = null;
				if (bePropsNvPair.getName()
						.equals(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName())) {
					effectiveValue = GroupOperationUtil.getEffectiveValue(bePropsNvPair);
					if (null != effectiveValue && !effectiveValue.toString().isEmpty()) {
						instance.setRuleTemplateDeployDir(effectiveValue.toString());
					}
				} else if (bePropsNvPair.getName()
						.equals(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName())) {
					effectiveValue = GroupOperationUtil.getEffectiveValue(bePropsNvPair);
					if (null != effectiveValue && !effectiveValue.toString().isEmpty()) {
						instance.setExternalClassesPath(effectiveValue.toString());
					}
				}
			}
		}

		if (hasInferneceAgent) {
			if (null != instance.getRuleTemplateDeployDir() && !instance.getRuleTemplateDeployDir().trim().isEmpty()) {
				instance.setRuleTemplateDeploy(true);
			}

			if (null != instance.getExternalClassesPath() && !instance.getExternalClassesPath().trim().isEmpty()) {
				instance.setDeployClasses(true);
			}
		}

	}

	@Override
	public Object start(ServiceInstance serviceInstance, String loggedInUser, Session session)
			throws BEValidationException, BEProcessingUnitStartFailException {
		validationService.vaidateServiceInstance(serviceInstance);

		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.STARTING_SERVICE_INSTANCE, serviceInstance.getName()));

		String name = serviceInstance.getName();
		if (!BETeaAgentStatus.STOPPED.getStatus().equals(serviceInstance.getStatus())) {
			throw new BEProcessingUnitStartFailException(
					messageService.getMessage(MessageKey.SERVICE_INSTANCE_NOT_STOPPED));
		}

		if (!serviceInstance.getDeployed()) {
			throw new BEProcessingUnitStartFailException(
					messageService.getMessage(MessageKey.SERVICE_INSTANCE_NOT_DEPLOYED));
		}
		Host host = serviceInstance.getHost();
		String applicationName = host.getApplication().getName();

		String deployedPath = serviceInstance.getDeploymentPath() + "/" + applicationName + "/" + name;
		String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
		if (startPuMethod.startsWith("windows")) {
			deployedPath = BEAgentUtil.getWinSshPath(deployedPath);
		}
		String command = Constants.START_PU_COMMAND;
		command = command.replace("{0}", deployedPath);
		command = command.replace("{1}", deployedPath);
		command = command.replace("{2}", name) + " " + serviceInstance.getJmxPort() + " " + host.getHostIp();
		try {

			ManagementUtil.executeCommand(command, session, false, timeout(), maxRetry(), threadSleepTime());
			Long startTime = System.currentTimeMillis();
			serviceInstance.setStartingTime(startTime);
			// Storing starting time in the deployment variables
			storeInstanceStartTime(serviceInstance, applicationName, startTime);

			serviceInstance.setStatus(BETeaAgentStatus.STARTING.getStatus());
			for (Agent agent : serviceInstance.getAgents()) {
				agent.setStatus(BETeaAgentStatus.STARTING.getStatus());
			}
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.START_INSTANCE,
						messageService.getMessage(MessageKey.INSTANCE_STARTED_MESSAGE), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}
		} catch (Exception e) {
			throw new BEProcessingUnitStartFailException(
					messageService.getMessage(MessageKey.INSTANCE_START_ERROR_MESSAGE, serviceInstance.getName()), e);
		}
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.STARTED_SERVICE_INSTANCE, serviceInstance.getName()));

		return messageService.getMessage(MessageKey.INSTANCE_START_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public Object undeploy(ServiceInstance serviceInstance, String loggedInUser, Session session,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors)
			throws DeploymentFailException, BEValidationException {
		validationService.vaidateServiceInstance(serviceInstance);

		Host host = serviceInstance.getHost();

		String applicationName = host.getApplication().getName();
		String name = serviceInstance.getName();
		if (!serviceInstance.getDeployed()
				&& BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
			throw new DeploymentFailException(messageService.getMessage(MessageKey.INSTANCE_NOT_DEPLOYED));
		}
		if (!BETeaAgentStatus.STOPPED.getStatus().equals(serviceInstance.getStatus())) {
			throw new DeploymentFailException(messageService.getMessage(MessageKey.STOP_INSTANCE_TO_UNDEPLOY));
		}

		try {
			undeploy(host, serviceInstance, applicationName, name, session);

			serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			serviceInstance
					.setDeploymentDescription(messageService.getMessage(MessageKey.INSTANCE_UNDEPLOYED_DESCRIPTION));
			serviceInstance.setDeployed(false);
			serviceInstance.setHotDeployable(false);
			serviceInstance.setStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			mbeanService.removeJMXConnection(serviceInstance.getKey());

			dataStoreService.deleteConfigData(applicationName + File.separator + name);
			Map<Object, Object> cddProperties = new HashMap<Object, Object>();
			ManagementUtil.loadCDDProps(serviceInstance.getHost().getApplication(), cddProperties);
			Map<String, String> traProperties = loadTRAProperties(host.getApplication(), host, serviceInstance);
			DeploymentVariableUtil.initDeploymentVariables(globalVariableDescriptors, dataStoreService, serviceInstance,
					cddProperties, traProperties, false);

			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
						messageService.getMessage(MessageKey.INSTANCE_UNDEPLOYED_DESCRIPTION), loggedInUser);
				storeAuditRecords(auditRecord, serviceInstance);
			}
		} catch (Exception e) {
			throw new DeploymentFailException(
					messageService.getMessage(MessageKey.INSTANCE_UNDEPLOY_ERROR_MESSAGE, serviceInstance.getName()),
					e);
		}
		return messageService.getMessage(MessageKey.INSTANCE_UNDEPLOY_SUCCESS_MESSAGE, serviceInstance.getName());
	}

	@Override
	public String kill(ServiceInstance serviceInstance, String loggedInUser, Session session)
			throws BEValidationException, BEServiceInstanceKillException {

		if (null == serviceInstance) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INSTANCE_NULL_ERROR_MESSAGE));
		}
		Host host = serviceInstance.getHost();
		if (null == host) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INSTANCE_NULL_ERROR_MESSAGE));
		}

		try {
			String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
			String processId = serviceInstance.getProcessId();
			if (null == processId || processId.trim().isEmpty()) {
				throw new BEServiceInstanceKillException("Process is yet not started");
			}
			String killcmd = "kill -9 " + processId;
			if (startPuMethod.startsWith("windows")) {
				killcmd = "taskkill /f /pid " + processId;
			}

			ManagementUtil.executeCommand(killcmd, session, true, timeout(), maxRetry(), threadSleepTime());
			serviceInstance.setProcessId("");
			serviceInstance.setStatus(BETeaAgentStatus.STOPPED.getStatus());
			mbeanService.removeJMXConnection(serviceInstance.getKey());
			return (messageService.getMessage(MessageKey.SERVICE_INSTANCE_STOPPED_SUCCESS_MESSAGE));
		} catch (JschCommandFailException e) {
			throw new BEServiceInstanceKillException(
					messageService.getMessage(MessageKey.INSTANCE_KILLED_ERROR_MESSAGE), e);
		}

	}

	@Override
	public String killServiceServiceInstance(ServiceInstance serviceInstance, String loggedInUser)
			throws BEServiceInstanceKillException, BEValidationException {
		validationService.vaidateServiceInstance(serviceInstance);
		String processId = serviceInstance.getProcessId();
		if (null == processId || processId.trim().isEmpty()) {
			throw new BEServiceInstanceKillException("Process is yet not started");
		}
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		List<Object> results = poolService.submitJobs(new BETeaKillInstanceJob(this, loggedInUser),
				jobExecutionContexts);
		List<String> failedInstances = new ArrayList<String>();
		int errorCnt = 0;
		String message = "Failed to kill  " + serviceInstance.getName();
		for (Object object : results) {
			if (object instanceof BETeaOperationResult) {
				BETeaOperationResult operationResult = (BETeaOperationResult) object;
				Object result = operationResult.getResult();
				if (result instanceof Exception) {
					failedInstances.add(operationResult.getName());
					errorCnt++;
				}
			} else if (object instanceof Exception) {
				failedInstances.add(serviceInstance.getName());
				errorCnt++;
			}
		}
		// If error count is same as count of
		if (errorCnt != 0)
			return (messageService.getMessage(MessageKey.INSTANCE_KILLED_ERROR_MESSAGE));

		return (messageService.getMessage(MessageKey.SERVICE_INSTANCE_KILLED_SUCCESS_MESSAGE));
	}

	/**
	 * Delete the passed instance from application
	 * 
	 * @param application
	 *            - Application Name
	 * @param name
	 *            - Name of service instance from application
	 * @return Success message
	 * @throws BEServiceInstanceDeleteException
	 *             - Exception is thrown when deleting instance failed.
	 * 
	 */
	@Override
	public String delele(Application application, String name) throws BEServiceInstanceDeleteException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DELETING_INSTANCE, name));

		ServiceInstance serviceInstanceToBeRemoved = null;
		List<ServiceInstance> serviceInstancesToBeRemoved = null;
		for (Host host : application.getHosts()) {
			List<ServiceInstance> intances = host.getInstances();
			Iterator<ServiceInstance> iterator = intances.iterator();
			while (iterator.hasNext()) {
				ServiceInstance serviceInstance = iterator.next();
				if (null != serviceInstance && serviceInstance.getName().equals(name)) {
					if (null != serviceInstance) {
						if (!application.isMonitorableOnly()) {
							if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
									.equals(serviceInstance.getDeploymentStatus())) {
								throw new BEServiceInstanceDeleteException(
										messageService.getMessage(MessageKey.ONLY_UNDEPLOYED_INSTANCES_DELETED));
							}

						}
						if (BETeaAgentStatus.STOPPING.getStatus().equals(serviceInstance.getStatus())
								|| BETeaAgentStatus.STARTING.getStatus().equals(serviceInstance.getStatus())
								|| BETeaAgentStatus.RUNNING.getStatus().equals(serviceInstance.getStatus())) {
							throw new BEServiceInstanceDeleteException(
									messageService.getMessage(MessageKey.STOPPED_DELETE_SERVICE_INSTANCE));
						}

					}
					serviceInstanceToBeRemoved = serviceInstance;
					serviceInstancesToBeRemoved = intances;
					break;
				}
			}
		}
		if (null != serviceInstanceToBeRemoved) {
			try {
				serviceInstancesToBeRemoved.remove(serviceInstanceToBeRemoved);
				serviceInstanceToBeRemoved.getHost().getMasterHost().addJMXPort(serviceInstanceToBeRemoved.getJmxPort(),
						false);
				mbeanService.removeJMXConnection(serviceInstanceToBeRemoved.getKey());
				dataStoreService.storeApplicationTopology(application);
				dataStoreService.deleteConfigData(application.getName() + File.separator + name);
			} catch (BEApplicationSaveException | FileNotFoundException e) {
				serviceInstancesToBeRemoved.add(serviceInstanceToBeRemoved);
				throw new BEServiceInstanceDeleteException(
						messageService.getMessage(MessageKey.INSTANCE_DELETE_ERROR_MESSAGE, name), e);
			}
		}
		return messageService.getMessage(MessageKey.INSTANCE_DELETE_SUCCESS_MESSAGE, name);
	}

	@Override
	public void upload(ServiceInstance serviceInstance, DataSource dataSource, Session session, String remoteUploadDir)
			throws BEUploadFileException {

		String startPUMethod = BEAgentUtil.determineMethod(serviceInstance.getHost().getOs());
		try {
			upload(dataSource, remoteUploadDir, startPUMethod, session);
		} catch (JSchException | SftpException | IOException e) {
			throw new BEUploadFileException(
					messageService.getMessage(MessageKey.UPLOAD_FILE_ERROR, dataSource.getName()));
		}
	}

	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Store BE Properties
	 * 
	 * @param serviceInstance
	 * @param deploymentVariables
	 * @param loggedInUser
	 * @param isIntermediateAction
	 * @param message
	 * @param sourceDeploymentVariables
	 * @return
	 * @throws BEServiceInstanceSaveException
	 * @throws DeploymentVariableStoreFailException
	 * @throws BEValidationException
	 * @throws DatatypeConfigurationException
	 * @throws Exception
	 */
	private String storeBEProperties(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction, String message,
			List<NameValuePair> sourceDeploymentVariables) throws BEServiceInstanceSaveException,
			DeploymentVariableStoreFailException, BEValidationException, DatatypeConfigurationException, Exception {
		DeploymentVariables beProperties = serviceInstance.getBEProperties();

		if (null == beProperties) {
			beProperties = deployVariablesObjectFactory.createDeploymentVariables();
		}

		NameValuePairs nameValuePairs = beProperties.getNameValuePairs();
		if (null == nameValuePairs) {
			nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
		}
		beProperties.setKey(serviceInstance.getKey() + "/" + deploymentVariables.getType().name());
		if (serviceInstance.getBEProperties() != null) {
			beProperties.setVersion(serviceInstance.getBEProperties().getVersion());
		}
		updateExistingNameValuePair(deploymentVariables, beProperties, serviceInstance);
		for (NameValuePair nameValuePair : sourceDeploymentVariables) {
			NameValuePair valuePair = GroupOperationUtil.getInstanceMatchedNameValuePair(beProperties, nameValuePair);
			if (nameValuePair.getValue() != null && !nameValuePair.getValue().trim().isEmpty()) {

				if (null == valuePair) {
					nameValuePairs.getNameValuePair().add(nameValuePair);
				}
			}
		}

		String applicationNanme = serviceInstance.getHost().getApplication().getName();
		dataStoreService.storeDeploymentVariables(applicationNanme, serviceInstance.getName(), beProperties);
		serviceInstance.setBEProperties(beProperties);
		if (!isIntermediateAction) {
			if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
			} else {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			}

			serviceInstance
					.setDeploymentDescription(messageService.getMessage(MessageKey.BE_PROERTIES_CHANGED_DESCRIPTION));
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			message = messageService.getMessage(MessageKey.BE_PROERTIES_SAVED_SUCCESS_MESSAGE);
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
						messageService.getMessage(MessageKey.BE_PROERTIES_UPDATED_MESSAGE), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}
		}

		return message;
	}

	/**
	 * Store Log Pattern and Level
	 * 
	 * @param serviceInstance
	 * @param deploymentVariables
	 * @param loggedInUser
	 * @param isIntermediateAction
	 * @param message
	 * @param sourceDeploymentVariables
	 * @return
	 * @throws BEServiceInstanceSaveException
	 * @throws DeploymentVariableStoreFailException
	 * @throws BEValidationException
	 * @throws DatatypeConfigurationException
	 * @throws Exception
	 */
	private String storeLogPatternAndLogLevel(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction, String message,
			List<NameValuePair> sourceDeploymentVariables) throws BEServiceInstanceSaveException,
			DeploymentVariableStoreFailException, BEValidationException, DatatypeConfigurationException, Exception {
		DeploymentVariables logPatterAndLevel = serviceInstance.getLoggerPatternAndLogLevel();

		if (null == logPatterAndLevel) {
			logPatterAndLevel = deployVariablesObjectFactory.createDeploymentVariables();
		}

		NameValuePairs nameValuePairs = logPatterAndLevel.getNameValuePairs();
		if (null == nameValuePairs) {
			nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
		}

		logPatterAndLevel.setName(deploymentVariables.getName());
		logPatterAndLevel.setType(deploymentVariables.getType());
		logPatterAndLevel.setKey(serviceInstance.getKey() + "/" + deploymentVariables.getType().name());
		logPatterAndLevel.setNameValuePairs(nameValuePairs);
		if (serviceInstance.getLoggerPatternAndLogLevel() != null) {
			logPatterAndLevel.setVersion(serviceInstance.getLoggerPatternAndLogLevel().getVersion());
		}
		updateExistingNameValuePair(deploymentVariables, logPatterAndLevel, serviceInstance);

		// Add New Log patterns
		for (NameValuePair nameValuePair : sourceDeploymentVariables) {
			if (nameValuePair.getValue() != null && !nameValuePair.getValue().trim().isEmpty()) {
				NameValuePair valuePair = GroupOperationUtil.getInstanceMatchedNameValuePair(logPatterAndLevel,
						nameValuePair);
				if (null == valuePair) {
					nameValuePairs.getNameValuePair().add(nameValuePair);
				}
			}
		}

		String applicationNanme = serviceInstance.getHost().getApplication().getName();
		dataStoreService.storeDeploymentVariables(applicationNanme, serviceInstance.getName(), logPatterAndLevel);
		serviceInstance.setLoggerPatternAndLogLevel(logPatterAndLevel);

		if (!isIntermediateAction) {

			if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
			} else {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			}

			serviceInstance.setDeploymentDescription(messageService.getMessage(MessageKey.LOGGER_PATTERN_DESCRIPTION));
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
						messageService.getMessage(MessageKey.LOGGER_PATTERN_DESCRIPTION), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}
			message = messageService.getMessage(MessageKey.LOGGER_PATTERN_STORED_SUCCESS_MESSAGE);
		}

		return message;
	}

	/**
	 * Store JVM Variables
	 * 
	 * @param serviceInstance
	 * @param deploymentVariables
	 * @param loggedInUser
	 * @param isIntermediateAction
	 * @param message
	 * @param sourceDeploymentVariables
	 * @return
	 * @throws BEServiceInstanceSaveException
	 * @throws DeploymentVariableStoreFailException
	 * @throws BEValidationException
	 * @throws DatatypeConfigurationException
	 * @throws Exception
	 */
	private String storeJVMProperties(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction, String message,
			List<NameValuePair> sourceDeploymentVariables) throws BEServiceInstanceSaveException,
			DeploymentVariableStoreFailException, BEValidationException, DatatypeConfigurationException, Exception {
		String applicationName = serviceInstance.getHost().getApplication().getName();

		DeploymentVariables jvmProperties = dataStoreService.fetchDeploymentVaribles(
				serviceInstance.getHost().getApplication().getName(), serviceInstance,
				DeploymentVariableType.JVM_PROPERTIES);
		if (null == jvmProperties)
			jvmProperties = deployVariablesObjectFactory.createDeploymentVariables();
		NameValuePairs nameValuePairs = jvmProperties.getNameValuePairs();
		if (null == nameValuePairs)
			nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();

		jvmProperties.setName(deploymentVariables.getName());
		jvmProperties.setType(deploymentVariables.getType());
		jvmProperties.setNameValuePairs(nameValuePairs);
		jvmProperties.setKey(serviceInstance.getKey() + "/" + deploymentVariables.getType().name());
		if (serviceInstance.getJVMProperties() != null) {
			jvmProperties.setVersion(serviceInstance.getJVMProperties().getVersion());
		}
		if (!nameValuePairs.getNameValuePair().isEmpty()) {
			for (NameValuePair nameValuePair : sourceDeploymentVariables) {
				for (NameValuePair nameValuePair1 : nameValuePairs.getNameValuePair()) {
					if (null != nameValuePair1 && nameValuePair1.getName().equals(nameValuePair.getName())) {
						nameValuePair1.setValue(nameValuePair.getValue());
					}
				}
			}
		} else {
			nameValuePairs.getNameValuePair().addAll(sourceDeploymentVariables);
		}

		dataStoreService.storeDeploymentVariables(applicationName, serviceInstance.getName(), jvmProperties);
		serviceInstance.setJVMProperties(jvmProperties);
		if (!isIntermediateAction) {
			if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
			} else {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			}

			serviceInstance
					.setDeploymentDescription(messageService.getMessage(MessageKey.JVM_PROPERTIES_CHANGED_DESCRIPTION));
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			message = messageService.getMessage(MessageKey.INSTANCE_SAVE_JP_SUCCESS_MESSAGE, serviceInstance.getName());
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
						messageService.getMessage(MessageKey.JVM_PROPERTIES_UPDATED_MESSAGE), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}

		}
		return message;
	}

	/**
	 * Store system variables
	 * 
	 * @param serviceInstance
	 * @param deploymentVariables
	 * @param loggedInUser
	 * @param isIntermediateAction
	 * @param message
	 * @param sourceDeploymentVariables
	 * @return
	 * @throws BEServiceInstanceSaveException
	 * @throws DeploymentVariableStoreFailException
	 * @throws BEValidationException
	 * @throws DatatypeConfigurationException
	 * @throws Exception
	 */
	private String storeSystemProperties(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction, String message,
			List<NameValuePair> sourceDeploymentVariables) throws BEServiceInstanceSaveException,
			DeploymentVariableStoreFailException, BEValidationException, DatatypeConfigurationException, Exception {
		Set<String> excludedProperties = dataStoreService.getExcludedProperties();
		for (Iterator<NameValuePair> iterator = sourceDeploymentVariables.iterator(); iterator.hasNext();) {
			NameValuePair nameValuePair = iterator.next();
			if (null != nameValuePair
					&& excludedProperties.contains(nameValuePair.getName().replace(SYSTEM_PROPERTY_PREFIX, ""))) {
				iterator.remove();
			}

		}
		DeploymentVariables systemVariables = serviceInstance.getSystemVariables();

		if (null == systemVariables) {
			systemVariables = deployVariablesObjectFactory.createDeploymentVariables();
		}

		NameValuePairs nameValuePairs = systemVariables.getNameValuePairs();
		if (null == nameValuePairs) {
			nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
		}

		systemVariables.setName(deploymentVariables.getName());
		systemVariables.setType(deploymentVariables.getType());
		systemVariables.setNameValuePairs(nameValuePairs);
		systemVariables.setKey(serviceInstance.getKey() + "/" + deploymentVariables.getType().name());
		if (serviceInstance.getSystemVariables() != null) {
			systemVariables.setVersion(serviceInstance.getSystemVariables().getVersion());
		}
		updateExistingNameValuePair(deploymentVariables, systemVariables, serviceInstance);
		// Add new system properties
		for (NameValuePair nameValuePair : sourceDeploymentVariables) {
			NameValuePair valuePair = GroupOperationUtil.getInstanceMatchedNameValuePair(systemVariables,
					nameValuePair);
			if (nameValuePair.getValue() != null && !nameValuePair.getValue().trim().isEmpty()) {
				if (null == valuePair) {
					nameValuePairs.getNameValuePair().add(nameValuePair);
				}
			}
		}

		String applicationNanme = serviceInstance.getHost().getApplication().getName();
		dataStoreService.storeDeploymentVariables(applicationNanme, serviceInstance.getName(), systemVariables);
		serviceInstance.setSystemVariables(systemVariables);
		if (!isIntermediateAction) {
			if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
			} else {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			}

			serviceInstance.setDeploymentDescription(
					messageService.getMessage(MessageKey.SYSTEM_VARIBALE_CHANGED_DESCRIPTION));
			DeploymentVariables instanceConfig = deployVariablesObjectFactory.createDeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			message = messageService.getMessage(MessageKey.INSTANCE_SAVE_SV_SUCCESS_MESSAGE, serviceInstance.getName());
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
						messageService.getMessage(MessageKey.SYSTEM_VARIBALE_UPDATED_MESSAGE), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}
		}

		return message;
	}

	/**
	 * Store Global Variables
	 * 
	 * @param serviceInstance
	 * @param deploymentVariables
	 * @param loggedInUser
	 * @param isIntermediateAction
	 * @param message
	 * @param sourceDeploymentVariables
	 * @return
	 * @throws Exception
	 * @throws BEServiceInstanceSaveException
	 * @throws DeploymentVariableStoreFailException
	 * @throws BEValidationException
	 * @throws DatatypeConfigurationException
	 */
	private String storeGlobalVariables(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction, String message,
			List<NameValuePair> sourceDeploymentVariables) throws Exception, BEServiceInstanceSaveException,
			DeploymentVariableStoreFailException, BEValidationException, DatatypeConfigurationException {
		DeploymentVariables globalVariables = serviceInstance.getGlobalVariables();

		if (null == globalVariables) {
			globalVariables = deployVariablesObjectFactory.createDeploymentVariables();
		}

		NameValuePairs nameValuePairs = globalVariables.getNameValuePairs();
		if (null == nameValuePairs) {
			nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
		}

		globalVariables.setName(deploymentVariables.getName());
		globalVariables.setType(deploymentVariables.getType());
		globalVariables.setNameValuePairs(nameValuePairs);
		globalVariables.setKey(serviceInstance.getKey() + "/" + deploymentVariables.getType().name());
		if (serviceInstance.getGlobalVariables() != null) {
			globalVariables.setVersion(serviceInstance.getSystemVariables().getVersion());
		}
		updateExistingNameValuePair(deploymentVariables, globalVariables, serviceInstance);

		String applicationNanme = serviceInstance.getHost().getApplication().getName();
		dataStoreService.storeDeploymentVariables(applicationNanme, serviceInstance.getName(), globalVariables);

		serviceInstance.setGlobalVariables(globalVariables);

		if (!isIntermediateAction) {
			if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
			} else {
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
			}
			serviceInstance.setDeploymentDescription(
					messageService.getMessage(MessageKey.GLOBAL_VARIBALE_CHANGED_DESCRIPTION));
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			storeDeploymentVariables(serviceInstance, instanceConfig, loggedInUser, true);
			message = messageService.getMessage(MessageKey.INSTANCE_SAVE_GV_SUCCESS_MESSAGE, serviceInstance.getName());
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.UPDATE_INSTANCE,
						messageService.getMessage(MessageKey.GLOBAL_VARIBALE_UPDATED_MESSAGE), loggedInUser);

				storeAuditRecords(auditRecord, serviceInstance);
			}
		}

		return message;
	}

	/**
	 * Update global variables
	 * 
	 * @param applicationName
	 * 
	 * @param config
	 * @param traProperties
	 * @param traProps
	 * @throws DeploymentVariableLoadFailException
	 * @throws Exception
	 */
	private void updateGlobalVariables(String applicationName, ServiceInstance config, BETraProperties traProperties,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors, Map<Object, Object> cddProperties,
			Map<String, String> traProps) throws DeploymentVariableLoadFailException, Exception {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPDATING_GV));
		DeploymentVariables globalVariables = this.getDeploymentVariables(config,
				DeploymentVariableType.GLOBAL_VARIABLES);
		BEApplicationsManagementService applicationService = BEMMServiceProviderManager.getInstance()
				.getBEApplicationsManagementService();
		Application application = applicationService.getApplicationByName(applicationName);
		ProcessingUnit processingUnit = application.getProcessingUnit(config.getPuId());
		String logConfig = processingUnit.getLogConfig();
		if (logConfig != null && logConfig.indexOf("%") != -1) {
			logConfig = logConfig.replaceAll("%%", "");
		}

		if (globalVariables != null) {

			for (Iterator<NameValuePair> iterator = globalVariables.getNameValuePairs().getNameValuePair()
					.iterator(); iterator.hasNext();) {
				NameValuePair nameValuePair = (NameValuePair) iterator.next();
				if (null != nameValuePair) {
					/*-
					 * If existing property is deleted then if it has default
					 * value then set it to default else  delete property
					 * from TRA and local
					 */
					if (nameValuePair.isIsDeleted()) {
						nameValuePair.setValue("");
						if (nameValuePair.isHasDefaultValue()) {
							if (null != globalVariableDescriptors) {
								for (GlobalVariableDescriptor globalVariableDescriptor : globalVariableDescriptors) {
									if (null != globalVariableDescriptor && globalVariableDescriptor.getName().trim()
											.equals(nameValuePair.getName())) {
										// For deleted take overridden value as
										// default value
										String overriddenValue = null;
										Object cddValue = cddProperties
												.get(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName());
										if (null != cddValue && !cddValue.toString().trim().isEmpty()) {
											overriddenValue = cddValue.toString();
										}

										if (traProps.containsKey(
												Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName())) {
											String traValue = traProps
													.get(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName());
											if (null != traValue && !traValue.trim().isEmpty()) {
												overriddenValue = traValue;
											}
										}
										if ("Password".equals(nameValuePair.getType())) {
											if (null != overriddenValue && !overriddenValue.trim().isEmpty()) {
												overriddenValue = ManagementUtil.getEncodedPwd(overriddenValue);
											}
										}

										if (null != overriddenValue && !overriddenValue.trim().isEmpty()) {
											nameValuePair.setDefaultValue(overriddenValue);
										} else {
											nameValuePair.setDefaultValue(
													(String) globalVariableDescriptor.getValueAsString());
										}
										// For deleted take default value as
										// deployed value
										nameValuePair.setDeployedValue(nameValuePair.getDefaultValue());
										break;
									}
								}
							}
						} else {
							/*
							 * GV who don't have default value and marked as
							 * deleted then set deployed value as empty
							 */
							nameValuePair.setDeployedValue("");
							traProperties.removeKey(nameValuePair.getName());
							iterator.remove();
						}
					} else {
						/*-
						 * If value is not null or empty then save value as
						 * deployed value and set value as TRA property.
						 */
						if (nameValuePair.getName().equalsIgnoreCase(logConfig)) {
							config.getLoggerPatternAndLogLevel().getNameValuePairs().getNameValuePair().get(0)
									.setDeployedValue(nameValuePair.getValue());
							// config.getLoggerPatternAndLogLevel().getNameValuePairs().getNameValuePair().get(0).setValue(nameValuePair.getValue());
						}
						if (nameValuePair.getValue() != null && !nameValuePair.getValue().trim().isEmpty()) {
							nameValuePair.setDeployedValue(nameValuePair.getValue());
							traProperties.setProperty(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName(),
									nameValuePair.getValue());
							nameValuePair.setValue("");
						} else {
							if (!nameValuePair.isHasDefaultValue() && nameValuePair.getDeployedValue() != null
									&& !nameValuePair.getDeployedValue().trim().isEmpty()) {
								traProperties.setProperty(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName(),
										nameValuePair.getDeployedValue());
							} else if (nameValuePair.isHasDefaultValue()) {
								if (nameValuePair.getDeployedValue() == null
										|| nameValuePair.getDeployedValue().trim().isEmpty()) {
									nameValuePair.setDeployedValue(nameValuePair.getDefaultValue());
								} else {
									/*
									 * Add properties in tra if deployed value
									 * is there and it is not same to default
									 * value
									 */
									if (!nameValuePair.getDefaultValue().equals(nameValuePair.getDeployedValue())) {
										traProperties.setProperty(
												Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName(),
												nameValuePair.getDeployedValue());
									}
								}
							}
						}
					}
				}
			}
		}
		config.setGlobalVariables(globalVariables);
	}

	/**
	 * Update System Properties
	 * 
	 * @param config
	 * @param traProperties
	 * @param cddProperties
	 * @param systemVariables
	 * @param traProps
	 * @return
	 */
	private void updateSystemVariables(ServiceInstance config, BETraProperties traProperties,
			Map<Object, Object> cddProperties, DeploymentVariables systemVariables, Map<String, String> traProps) {
		Map<String, String> puProperties = config.getHost().getApplication().getProcessingUnit(config.getPuId())
				.getProperties();
		if (systemVariables != null) {
			for (Iterator<NameValuePair> iterator = systemVariables.getNameValuePairs().getNameValuePair()
					.iterator(); iterator.hasNext();) {
				NameValuePair nameValuePair = iterator.next();

				/*-
				 * If existing property is deleted then if it has default
				 * value then set it to default else else delete property
				 * from TRA and local
				 */
				if (nameValuePair.isIsDeleted()) {
					nameValuePair.setValue("");
					if (nameValuePair.isHasDefaultValue()) {
						// Value from CDD
						String overriddenValue = (String) cddProperties
								.get(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName());

						// Overridden value is from PU
						if (puProperties.containsKey(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName())) {
							String puLevelValue = puProperties
									.get(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName());
							overriddenValue = puLevelValue;
						}

						// Overridden value is from TRA
						if (traProps.containsKey(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName())) {
							String traValue = traProps.get(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName());
							overriddenValue = traValue;
						}

						nameValuePair.setDefaultValue(overriddenValue);

						// For deleted take default value as
						// deployed value
						nameValuePair.setDeployedValue(nameValuePair.getDefaultValue());
					} else {
						/*
						 * System Properties who don't have default value and
						 * marked as deleted then set deployed value as empty
						 */
						nameValuePair.setDeployedValue("");
						traProperties.removeKey(nameValuePair.getName());
						iterator.remove();
					}
				} else {
					/*-
					 * If value is not null or empty then save value as
					 * deployed value and set value as TRA property.
					 */
					if (nameValuePair.getValue() != null && !nameValuePair.getValue().trim().isEmpty()) {
						nameValuePair.setDeployedValue(nameValuePair.getValue());
						traProperties.setProperty(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName(),
								nameValuePair.getValue());
						nameValuePair.setValue("");
					} else {
						if (!nameValuePair.isHasDefaultValue() && nameValuePair.getDeployedValue() != null
								&& !nameValuePair.getDeployedValue().trim().isEmpty()) {
							traProperties.setProperty(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName(),
									nameValuePair.getDeployedValue());
						} else if (nameValuePair.isHasDefaultValue()) {
							if (nameValuePair.getDeployedValue() == null
									|| nameValuePair.getDeployedValue().trim().isEmpty()) {
								nameValuePair.setDeployedValue(nameValuePair.getDefaultValue());
							} else {
								/*
								 * Add properties in tra if deployed value is
								 * there and it is not same to default value
								 */
								if (!nameValuePair.getDefaultValue().equals(nameValuePair.getDeployedValue())) {
									traProperties.setProperty(
											Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName(),
											nameValuePair.getDeployedValue());
								}
							}
						}
					}
				}
			}
		}
		config.setSystemVariables(systemVariables);
	}

	/**
	 * Update System Properties
	 * 
	 * @param config
	 * @param traProperties
	 * @param cddProperties
	 * @param beProperties
	 * @param traProps
	 * @return
	 */
	private void updateBEProperties(ServiceInstance config, BETraProperties traProperties,
			Map<Object, Object> cddProperties, DeploymentVariables beProperties, Map<String, String> traProps) {
		Map<String, String> puProperties = config.getHost().getApplication().getProcessingUnit(config.getPuId())
				.getProperties();
		if (beProperties != null) {
			for (Iterator<NameValuePair> iterator = beProperties.getNameValuePairs().getNameValuePair()
					.iterator(); iterator.hasNext();) {
				NameValuePair nameValuePair = iterator.next();

				/*-
				 * If existing property is deleted then if it has default
				 * value then set it to default else else delete property
				 * from TRA and local
				 */
				if (nameValuePair.isIsDeleted()) {
					nameValuePair.setValue("");
					if (nameValuePair.isHasDefaultValue()) {
						// Value from CDD
						String overriddenValue = (String) cddProperties.get(nameValuePair.getName());

						// Overridden value is from PU
						if (puProperties.containsKey(nameValuePair.getName())) {
							String puLevelValue = puProperties.get(nameValuePair.getName());
							overriddenValue = puLevelValue;
						}

						// Overridden value is from TRA
						if (traProps.containsKey(nameValuePair.getName())) {
							String traValue = traProps.get(nameValuePair.getName());
							overriddenValue = traValue;
						}
						nameValuePair.setDefaultValue(overriddenValue);
						// For deleted take default value as
						// deployed value
						nameValuePair.setDeployedValue(nameValuePair.getDefaultValue());
					} else {
						/*
						 * SyBEstem Properties who don't have default value and
						 * marked as deleted then set deployed value as empty
						 */
						nameValuePair.setDeployedValue("");
						traProperties.removeKey(nameValuePair.getName());
						iterator.remove();
					}
				} else {
					/*-
					 * If value is not null or empty then save value as
					 * deployed value and set value as TRA property.
					 */
					if (nameValuePair.getValue() != null && !nameValuePair.getValue().trim().isEmpty()) {
						nameValuePair.setDeployedValue(nameValuePair.getValue());
						traProperties.setProperty(nameValuePair.getName(), nameValuePair.getValue());
						nameValuePair.setValue("");
					} else {
						if (!nameValuePair.isHasDefaultValue() && nameValuePair.getDeployedValue() != null
								&& !nameValuePair.getDeployedValue().trim().isEmpty()) {
							traProperties.setProperty(nameValuePair.getName(), nameValuePair.getDeployedValue());
						} else if (nameValuePair.isHasDefaultValue()) {
							if (nameValuePair.getDeployedValue() == null
									|| nameValuePair.getDeployedValue().trim().isEmpty()) {
								nameValuePair.setDeployedValue(nameValuePair.getDefaultValue());
							} else {
								/*
								 * Add properties in tra if deployed value is
								 * there and it is not same to default value
								 */
								if (!nameValuePair.getDefaultValue().equals(nameValuePair.getDeployedValue())) {
									traProperties.setProperty(nameValuePair.getName(),
											nameValuePair.getDeployedValue());
								}
							}
						}
					}
				}
			}
		}
		config.setBEProperties(beProperties);
	}

	/**
	 * Check any service instance on given host have same JMX port or not
	 * 
	 * @param matchedHost
	 *            - Host instance
	 * @param jmxPort
	 *            - JMX port
	 * @param config
	 *            - Service instance
	 * @return True/False
	 */
	private boolean isInstanceExistByJMXPort(Host matchedHost, int jmxPort, ServiceInstance config) {
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.CHECKING_INSTANCES_HAVE_SAME_JMX_PORT, matchedHost.getName()));
		boolean isExist = false;
		for (ServiceInstance serviceInstance : matchedHost.getInstances()) {
			if (null != serviceInstance) {
				if (null != config) {
					if (!serviceInstance.getKey().equals(config.getKey()) && serviceInstance.getJmxPort() == jmxPort)
						isExist = true;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.SERVICE_INSTANCE_SAME_JMX_PORT,
							serviceInstance.getName(), jmxPort));
					break;
				} else if (null == config) {
					if (serviceInstance.getJmxPort() == jmxPort) {
						LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.SERVICE_INSTANCE_SAME_JMX_PORT,
								serviceInstance.getName(), jmxPort));
						isExist = true;
						break;
					}
				}
			}
		}

		return isExist;
	}

	private void storeInstanceStartTime(ServiceInstance serviceInstance, String applicationName, Long startTime)
			throws Exception {

		// setting start time in the deployment variables
		DeploymentVariables deploymentVariables = dataStoreService.fetchDeploymentVaribles(
				serviceInstance.getHost().getApplication().getName(), serviceInstance,
				DeploymentVariableType.INSTANCE_CONFIG);
		NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
		nameValuePair.setValue(startTime.toString());
		nameValuePair.setName(Constants.INSTANCE_START_TIME);
		deploymentVariables.getNameValuePairs().getNameValuePair().add(nameValuePair);
		dataStoreService.storeDeploymentVariables(applicationName, serviceInstance.getName(), deploymentVariables);

	}

	/**
	 * @param config
	 * @param processingUnit
	 * @param instance
	 * @param application
	 */
	private void updateAgent(String processingUnit, ServiceInstance instance, Application application) {

		ProcessingUnit procUnit = application.getProcessingUnit(processingUnit);
		boolean hasInferneceAgent = false;
		for (com.tibco.cep.bemm.model.AgentConfig agentConfig : procUnit.getAgents()) {

			try {
				Agent agent = BEMMModelFactoryImpl.getInstance().getAgent();
				agent.setAgentName(agentConfig.getAgentName());
				agent.setAgentType(agentConfig.getAgentType());
				agent.setInstance(instance);
				instance.addAgent(agent);
				if (!hasInferneceAgent
						&& AgentType.INFERENCE.getType().equalsIgnoreCase(agentConfig.getAgentType().getType())) {
					hasInferneceAgent = true;
				}
			} catch (ObjectCreationException e) {
				LOGGER.log(Level.ERROR, e, e.getMessage());
			}
		}
		if (hasInferneceAgent) {
			instance.setDeployClasses(null != application.getExternalClassesPath()
					&& !application.getExternalClassesPath().trim().isEmpty() ? true : false);
		}
	}

	/**
	 * @param hostName
	 * @param instance
	 * @param oldHost
	 * @param host
	 * @param application
	 * @return
	 * @throws BEServiceInstanceEditException
	 */
	private Host getHost(String hostName, ServiceInstance instance, Host oldHost, Application application)
			throws BEServiceInstanceEditException {
		Host host = null;
		try {
			BEApplicationsManagementService applicationManagementService = BEMMServiceProviderManager.getInstance()
					.getBEApplicationsManagementService();
			host = applicationManagementService.getHostByHostName(application.getName(), hostName);

			if (null == host) {
				host = applicationManagementService.addApplicationHost(application, hostName);
			}
		} catch (BEApplicationNotFoundException | BEValidationException | ObjectCreationException e) {
			throw new BEServiceInstanceEditException(
					messageService.getMessage(MessageKey.HOST_GET_ERROR_MESSAGE) + hostName);
		}
		return host;
	}

	/**
	 * @param type
	 * @param serviceInstance
	 * @param groupDeploymentVariable
	 * @param deploymentVariables
	 * @param nameValuePairs
	 */
	private void addNewDeploymentVariable(DeploymentVariableType type, ServiceInstance serviceInstance,
			GroupDeploymentVariable groupDeploymentVariable, DeploymentVariables deploymentVariables,
			NameValuePairs nameValuePairs) {
		NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
		nameValuePair.setValue(groupDeploymentVariable.getValue());
		nameValuePair.setIsDeleted(false);
		nameValuePair.setName(groupDeploymentVariable.getName());
		nameValuePairs.getNameValuePair().add(nameValuePair);
		deploymentVariables.setNameValuePairs(nameValuePairs);
		deploymentVariables.setType(type);
		deploymentVariables.setName(serviceInstance.getHost().getApplication().getName());
		if (DeploymentVariableType.BE_PROPERTIES.equals(type)) {
			serviceInstance.setBEProperties(deploymentVariables);
		} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(type)) {
			serviceInstance.setSystemVariables(deploymentVariables);
		} else if (DeploymentVariableType.GLOBAL_VARIABLES.equals(type)) {
			serviceInstance.setGlobalVariables(deploymentVariables);
		} else if (DeploymentVariableType.LOG_PATTERNS.equals(type)) {
			serviceInstance.setLoggerPatternAndLogLevel(deploymentVariables);
		} else if (DeploymentVariableType.JVM_PROPERTIES.equals(type)) {
			serviceInstance.setJVMProperties(deploymentVariables);
		}
	}

	/**
	 * Check instance of same name exist across the application or not. if exist
	 * then return true else false
	 * 
	 * @param instanceName
	 *            - Name of instance
	 * @param application
	 *            - Application instance
	 */
	private boolean isInstanceExistByName(String instanceName, Application application) {
		boolean isExist = false;
		for (Host existingHost : application.getHosts()) {
			if (null != existingHost) {
				List<ServiceInstance> instances = existingHost.getInstances();
				if (null != instances) {
					for (ServiceInstance serviceInstance : instances) {
						if (null != serviceInstance) {
							if (serviceInstance.getName().trim().toLowerCase()
									.equals(instanceName.trim().toLowerCase())) {
								isExist = true;
								break;
							}

						}
					}
				}
			}
		}
		return isExist;
	}

	/**
	 * Mark instances which are stopped in cloud as old instance
	 * 
	 * @param instanceName
	 *            - Name of instance
	 * @param application
	 *            - Application instance
	 * @param jmxPort
	 *            - Jmx port of the instance
	 */
	private void markOldInstances(String instanceName, Application application, int jmxPort) {
		if (application.isMonitorableOnly()) {

			String[] parts = instanceName.split("_");
			instanceName = parts[0] + "_" + parts[1];
			String podName = parts[2];
			for (Host existingHost : application.getHosts()) {
				if (null != existingHost) {
					List<ServiceInstance> instances = existingHost.getInstances();
					if (null != instances) {
						for (ServiceInstance serviceInstance : instances) {
							if (null != serviceInstance) {
								parts = serviceInstance.getName().split("_");
								String name = parts[0] + "_" + parts[1];
								String podName1 = parts[2];
								if (name.trim().equals(instanceName.trim()) && !podName.equals(podName1)
										&& jmxPort == serviceInstance.getJmxPort()) {
									serviceInstance.setOldInstance(true);
									serviceInstance.setUpTime(-1L);
									serviceInstance.setProcessId("");

									serviceInstance.setStatus(BETeaAgentStatus.STOPPED.getStatus());
									for (Agent stAgent : serviceInstance.getAgents()) {
										stAgent.setAgentId(-1);
										stAgent.setStatus(BETeaAgentStatus.STOPPED.getStatus());
									}

									if (serviceInstance.isPredefined()) {
										if (null != serviceInstance.getRemoteMetricsCollectorService()) {
											serviceInstance.setRemoteMetricsCollectorService(null);
										}
									}
									// Reset CPU/Memory usage to 0
									serviceInstance.getMemoryUsage().setPercentUsed(0.0D);
									serviceInstance.getCpuUsage().setCpuUsageInPercent(0.0D);
									if (serviceInstance.getHost().getApplication().isMonitorableOnly()) {
										serviceInstance.setDeployed(false);
										serviceInstance.setDeploymentStatus(BETeaAgentStatus.UNDEPLOYED.getStatus());
									}
								}
							}
						}
					}
				}
			}
		}

	}

	/**
	 * s Extracts a zip file specified by the zipFilePath to a directory
	 * specified by destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	private void unzip(InputStream inputStream, String destDirectory) {

		ZipInputStream zipIn = new ZipInputStream(inputStream);

		try {
			File destDir = new File(destDirectory);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			ZipEntry entry = zipIn.getNextEntry();

			// iterates over entries in the zip file
			while (entry != null) {
				String filePath = destDirectory + File.separator + entry.getName();
				if (!entry.isDirectory()) {
					// if the entry is a file, extracts it
					try {
						// Defensive code to make dir if not already created
						File destFile = new File(filePath);
						if (!destFile.getParentFile().exists()) {
							destFile.getParentFile().mkdirs();
						}
					} catch (Exception e) {
						// Ignore errors if any while creating directories
					}
					extractFile(zipIn, filePath);
				} else {
					// if the entry is a directory, make the directory
					File dir = new File(filePath);
					dir.mkdirs();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}

		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
		} finally {
			try {
				if (null != zipIn) {
					zipIn.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
			}
		}
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	/**
	 * Update Existing Name value pair
	 * 
	 * @param deploymentVariables
	 * @param existingNameValuePair
	 * @param serviceInstance
	 */
	private void updateExistingNameValuePair(DeploymentVariables deploymentVariables,
			DeploymentVariables existingNameValuePair, ServiceInstance serviceInstance) {
		if (null != existingNameValuePair) {
			NameValuePairs existingNameValuePairs = existingNameValuePair.getNameValuePairs();
			if (null != existingNameValuePairs) {
				List<NameValuePair> existingNameValuePairList = existingNameValuePairs.getNameValuePair();
				if (null != existingNameValuePairList) {
					for (NameValuePair nameValuePair : existingNameValuePairList) {
						NameValuePair matchedNameValuePair = GroupOperationUtil
								.getInstanceMatchedNameValuePair(deploymentVariables, nameValuePair);
						if (null != matchedNameValuePair) {
							nameValuePair.setIsDeleted(matchedNameValuePair.isIsDeleted());
							if (!matchedNameValuePair.isIsDeleted()) {

								if ("Password".equals(nameValuePair.getType())) {
									nameValuePair
											.setValue(ManagementUtil.getEncodedPwd(matchedNameValuePair.getValue()));
								} else {
									nameValuePair.setValue(matchedNameValuePair.getValue());
								}
							}

						}
					}
				}
			}
		}
	}

	/**
	 * Copy deployment variable to the clone instance
	 * 
	 * @param deploymentVariables
	 *            - Deployment variables to be copied
	 * @param type
	 *            - Type of deployment variable
	 * @param copyInstance
	 *            - Service instance where we need to copy the deployment
	 *            variable
	 */
	private void copyDeploymentVariable(DeploymentVariables deploymentVariables, DeploymentVariableType type,
			ServiceInstance copyInstance) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.COPYING_DEPLOYMENT_VARIABLES, type.name(),
				copyInstance.getName()));
		switch (type) {
		case GLOBAL_VARIABLES:
			DeploymentVariables globalVariables = createDeploymentVariables(deploymentVariables, type, copyInstance);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYMENT_VARIABELS, globalVariables));
			copyInstance.setGlobalVariables(globalVariables);
			break;
		case SYSTEM_VARIABLES:
			DeploymentVariables systemProperties = createDeploymentVariables(deploymentVariables, type, copyInstance);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYMENT_VARIABELS, systemProperties));
			copyInstance.setSystemVariables(systemProperties);
			break;
		case JVM_PROPERTIES:
			DeploymentVariables jvmProperties = createDeploymentVariables(deploymentVariables, type, copyInstance);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYMENT_VARIABELS, jvmProperties));
			copyInstance.setJVMProperties(jvmProperties);
			break;
		case LOG_PATTERNS:
			DeploymentVariables logPatterns = createDeploymentVariables(deploymentVariables, type, copyInstance);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYMENT_VARIABELS, logPatterns));
			copyInstance.setLoggerPatternAndLogLevel(logPatterns);
			break;
		case BE_PROPERTIES:
			DeploymentVariables beProperties = createDeploymentVariables(deploymentVariables, type, copyInstance);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOYMENT_VARIABELS, beProperties));
			copyInstance.setBEProperties(beProperties);
			break;

		default:
			break;
		}
	}

	/**
	 * Create Deployment variable
	 * 
	 * @param deploymentVariables
	 *            - Deployment variables to be copied
	 * @param type
	 *            - Type of deployment variable
	 * @param copyInstance
	 *            - Service instance where we need to copy the deployment
	 *            variable
	 * @return Newly create deployment variables
	 */
	private DeploymentVariables createDeploymentVariables(DeploymentVariables deploymentVariables,
			DeploymentVariableType type, ServiceInstance copyInstance) {
		DeploymentVariables variables = deployVariablesObjectFactory.createDeploymentVariables();
		NameValuePairs valuePairs = deployVariablesObjectFactory.createNameValuePairs();

		if (null != deploymentVariables) {
			NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
			if (null != nameValuePairs) {
				List<NameValuePair> nameValuePairList = nameValuePairs.getNameValuePair();
				if (null != nameValuePairList) {
					for (NameValuePair nameValuePair : nameValuePairList) {
						if (null != nameValuePair) {
							if (nameValuePair.isHasDefaultValue()) {
								createNameValuePair(valuePairs, nameValuePair);
							} else {
								if (!nameValuePair.isIsDeleted()) {
									createNameValuePair(valuePairs, nameValuePair);
								}
							}
						}
					}
				}
			}
		}
		variables.setNameValuePairs(valuePairs);
		variables.setType(type);
		variables.setKey(copyInstance.getKey() + "/" + type.name());
		variables.setName(copyInstance.getHost().getApplication().getName());
		return variables;
	}

	/**
	 * Create NameValue pair
	 * 
	 * @param valuePairs
	 *            - NameValuePairs instance
	 * @param nameValuePair
	 *            - Source NameValue pair
	 */
	protected void createNameValuePair(NameValuePairs valuePairs, NameValuePair nameValuePair) {
		NameValuePair valuePair = deployVariablesObjectFactory.createNameValuePair();
		valuePair.setDefaultValue(nameValuePair.getDefaultValue());
		valuePair.setDescription(nameValuePair.getDescription());
		valuePair.setHasDefaultValue(nameValuePair.isHasDefaultValue());
		valuePair.setName(nameValuePair.getName());
		if (!nameValuePair.isIsDeleted())
			valuePair.setValue(nameValuePair.getValue());
		valuePair.setType(nameValuePair.getType());
		valuePair.setIsDeleted(false);
		valuePairs.getNameValuePair().add(valuePair);
	}

	/**
	 * This method is used to create the deployment directories on remote
	 * machine.
	 * 
	 * @param hostResource
	 *            - Host details.
	 * @param deployedPath
	 *            - deployment path
	 * @param session
	 *            - Jsch Session
	 * @param startPuMethod
	 *            - Processing Unit start method.
	 * @throws JschCommandFailException
	 */
	private void makeDir(String deployedPath, Session session, String startPuMethod) throws JschCommandFailException {

		// Create the command as per target os
		String mkdircmd = "mkdir -p " + deployedPath;
		if (startPuMethod.startsWith("windows")) {
			mkdircmd = "mkdir -p " + BEAgentUtil.getWinSshPath(deployedPath);
		}
		ManagementUtil.executeCommand(mkdircmd, session, true, timeout(), maxRetry(), threadSleepTime());

	}

	/**
	 * This method is used to upload the cdd,ear,sh,tra files to remote machine.
	 * 
	 * @param hostResource
	 *            - Host Details.
	 * @param applicationName
	 *            - Application Name.
	 * @param deployedPath
	 *            - Deployment path
	 * @param masterEarFilePath
	 *            - Master Ear path
	 * @param masterCddFilePath
	 *            - Master CDD path
	 * @param config
	 *            - Processing unit details
	 * @param underlyingConnection
	 *            - Jsch Client
	 * @param startPuMethod
	 *            - Processing Unit start method.
	 * @param globalVariableDescriptors
	 * @param appDataStoreLocation
	 * @param defaultProfileName
	 * @param defaultProfileFilePath
	 * @param connectToCluster
	 * @param string
	 * @throws DeploymentFailException
	 *             -This class is used to throw the exception when deployment
	 *             related things(deploy, un-deploy,re-deploy) fails.
	 * @throws JschCommandFailException
	 * @throws JschAuthenticationException
	 */
	private void upload(Host host, String applicationName, String deployedPath, String masterEarFilePath,
			String masterCddFilePath, ServiceInstance config, Session session, String startPuMethod,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors, File appDataStoreLocation)
			throws JschCommandFailException, DeploymentFailException {
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.UPLOADING_ARTIFACTS_OF_INSTANCE, config.getName()));

		try {
			// Create the command as per target os
			if (startPuMethod.startsWith("windows")) {
				deployedPath = BEAgentUtil.getWinSshPath(deployedPath);
			}
			String remoteEARFile = deployedPath + "/" + applicationName + ".ear";
			ManagementUtil.uploadToRemoteMahine(remoteEARFile, session, masterEarFilePath, timeout());

			String remoteCDD = deployedPath + "/" + applicationName + ".cdd";
			ManagementUtil.uploadToRemoteMahine(remoteCDD, session, masterCddFilePath, timeout());

			Set<String> profiles = host.getApplication().getProfiles();
			if (null != profiles && !profiles.isEmpty())
				for (String profile : profiles) {
					if (null != profile && !profile.trim().isEmpty()) {
						String defaultProfileFilePath = appDataStoreLocation.getAbsolutePath() + File.separator
								+ applicationName + File.separator + profile + ".properties";
						String remotePropertyFile = deployedPath + "/" + profile + ".properties";
						ManagementUtil.uploadToRemoteMahine(remotePropertyFile, session, defaultProfileFilePath,
								timeout());
					}
				}

			InputStream traContentsStream = fetchAndUpdateTra(applicationName, config, globalVariableDescriptors);
			byte[] buffer = new byte[traContentsStream.available()];
			traContentsStream.read(buffer);
			File targetFile = File.createTempFile(applicationName + "_" + config.getName(), ".tra",
					dataStoreService.getTempFileLocation());
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.flush();
			outStream.close();
			traContentsStream.close();
			String remoteTRA = deployedPath + "/" + applicationName + "_" + config.getName() + ".tra";

			ManagementUtil.uploadToRemoteMahine(remoteTRA, session, targetFile.getAbsolutePath(), timeout());

			// Get start pu command and create sh file
			String startComd = getStartPuCommand(host, applicationName, config, config.getName(),
					host.getApplication().getDefaultProfile());
			String shFile = config.getName() + ".sh";
			File sourceScriptFile = File.createTempFile("temp_" + config.getName(), ".sh",
					dataStoreService.getTempFileLocation());
			BufferedWriter writer = new BufferedWriter(new FileWriter(sourceScriptFile));
			writer.write(startComd);
			writer.flush();
			writer.close();
			String remoteScritptFile = deployedPath + "/" + shFile;

			ManagementUtil.uploadToRemoteMahine(remoteScritptFile, session, sourceScriptFile.getAbsolutePath(),
					timeout());
			sourceScriptFile.delete();
			targetFile.delete();

			LOGGER.log(Level.DEBUG,
					messageService.getMessage(MessageKey.UPLOADED_ARTIFACTS_OF_INSTANCE, config.getName()));
		} catch (IOException e) {
			throw new JschCommandFailException(e.getMessage());
		}

	}

	/**
	 * This method is used to get the processing unit start command for target
	 * host.
	 * 
	 * @param hostResource
	 *            - Details Of the host.
	 * @param applicationName
	 *            - Name of the application.
	 * @param deployedPath
	 *            - Deployment path
	 * @param config
	 *            - Processing unit configuration details.
	 * @param defaultProfileName
	 * @return Command to start processing unit on given host
	 */
	private String getStartPuCommand(Host host, String applicationName, ServiceInstance config, String name,
			String defaultProfileName) {

		// Get command for unix
		String startPuCmd = formUnixCommand(host, applicationName, config, name, defaultProfileName);

		String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
		if (startPuMethod.startsWith("windows")) {

			// Get command for windows.
			startPuCmd = formWinSshCommand(host, applicationName, config, name, defaultProfileName);
		}
		return startPuCmd;
	}

	/**
	 * This method is used to get the Business Events bin directory path
	 * 
	 * @param beId
	 * 
	 * @param hostResource
	 *            - Details of Host resource.
	 * @return Bin directory path.
	 */
	private String getBeBinPath(Host host, String beId) {
		String binPath = host.getBEDetailsById(beId).getBeHome();
		String startMethod = BEAgentUtil.determineMethod(host.getOs());
		if (startMethod != null && startMethod.equals("pstools")) {
			binPath = binPath.replace('/', File.separatorChar);
			if (!binPath.endsWith(File.separator)) {
				binPath = binPath + File.separator;
			}
			binPath = binPath + "bin" + File.separator;
		} else {
			if (!binPath.endsWith("/")) {
				binPath = binPath + "/";
			}
			if (binPath.contains("\\")) {
				binPath = binPath.replaceAll("\\\\", "/");
			}
			binPath = binPath + "bin" + "/";
		}
		return binPath;
	}

	/**
	 * This method is used to get Unix command to start the processing unit
	 * 
	 * @param host
	 *            - Details of the host resource.
	 * @param applicationName
	 *            - Name of the application
	 * @param deployedPath
	 *            - Deployment Path
	 * @param config
	 *            - Processing Unit configuration details.
	 * @param defaultProfileName
	 * @return Unix command to start processing unit.
	 */
	private String formUnixCommand(Host host, String applicationName, ServiceInstance config, String name,
			String defaultProfileName) {

		String binPath = getBeBinPath(host, config.getBeId());

		if (binPath == null || binPath.trim().isEmpty())
			return null;

		String command = Constants.UNIX_COMMAND;

		String traPath = applicationName + "_" + name + ".tra";
		String cddPath = applicationName + ".cdd";
		String earPath = applicationName + ".ear";
		String beEngineExePath = binPath + Constants.ENGINE_EXEC_NAME;
		String jmxPort = "";
		String jmxHost = "";
		if (config.getJmxPort() != 0) {
			jmxPort = "jmx_port=$1";
			jmxHost = "jmx_host=$2";
			command = MessageFormat.format(command, beEngineExePath, traPath, name, cddPath, config.getPuId(), earPath,
					jmxPort, jmxHost);
		} else {
			command = command.replace("--propVar {6}", "");
			command = command.replace("--propVar {7}", "");
			command = MessageFormat.format(command, beEngineExePath, traPath, name, cddPath, config.getPuId(), earPath);
		}
		if (null != defaultProfileName && !defaultProfileName.trim().isEmpty()) {
			command = command + " -p " + defaultProfileName + ".properties";
		}
		return command;

	}

	/**
	 * Fetch the TRA and update it with new deployment variables in it
	 * 
	 * @param applicationName
	 * 
	 * @param config
	 *            - Service instance
	 * @param globalVariableDescriptors
	 *            - Collection of Global deployment Descriptors
	 * @return Input Stream for updated TRA file
	 */
	private InputStream fetchAndUpdateTra(String applicationName, ServiceInstance config,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors) throws DeploymentFailException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPDATING_REMOTE_TRA_PROPERTIES));
		InputStream deployableTraContentsStream = null;
		Boolean isLogConfigExist = false;
		try {
			// Load TRA Properties
			InputStream hostTraFileStream = null;
			if (ManagementUtil.isHostTraConfigExist(config.getHost().getHostId(), config.getHost().getApplication())) {
				hostTraFileStream = dataStoreService.fetchHostTraFile(config.getHost().getApplication().getName(),
						config.getHost().getHostId());
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.OVERRIDDEN_TRA_EXIST_FOR_APPLICATION));
			} else {
				hostTraFileStream = dataStoreService.fetchHostTraFile(null,
						config.getHost().getHostId() + "_" + config.getBeId());
			}
			if (null == hostTraFileStream)
				throw new DeploymentFailException("TRA file does not exist for " + config.getName() + " instance");
			BETraProperties traProperties = new BETraProperties();
			traProperties.load(hostTraFileStream);
			Map<String, String> traProps = loadTRAProperties(config.getHost().getApplication(), config.getHost(),
					config);
			Map<Object, Object> cddProperties = new HashMap<Object, Object>();
			ManagementUtil.loadCDDProps(config.getHost().getApplication(), cddProperties);

			// update global variables
			updateGlobalVariables(applicationName, config, traProperties, globalVariableDescriptors, cddProperties,
					traProps);
			String applicationNanme = config.getHost().getApplication().getName();
			if (null != config.getGlobalVariables())
				dataStoreService.storeDeploymentVariables(applicationNanme, config.getName(),
						config.getGlobalVariables());

			BEApplicationsManagementService applicationService = BEMMServiceProviderManager.getInstance()
					.getBEApplicationsManagementService();
			Application application = applicationService.getApplicationByName(applicationName);
			ProcessingUnit processingUnit = application.getProcessingUnit(config.getPuId());
			String logConfig = processingUnit.getLogConfig();
			if (logConfig != null && logConfig.indexOf("%") != -1) {
				logConfig = logConfig.replaceAll("%%", "");
			}

			DeploymentVariables globalVariables = config.getGlobalVariables();
			if (globalVariables != null) {

				for (Iterator<NameValuePair> iterator = globalVariables.getNameValuePairs().getNameValuePair()
						.iterator(); iterator.hasNext();) {
					NameValuePair nameValuePair = (NameValuePair) iterator.next();
					if (null != nameValuePair) {
						if (nameValuePair.getName().equals(logConfig))
							isLogConfigExist = true;
					}
				}
			}
			DeploymentVariables systemVariables = this.getDeploymentVariables(config,
					DeploymentVariableType.SYSTEM_VARIABLES);

			updateSystemVariables(config, traProperties, cddProperties, systemVariables, traProps);
			if (null != config.getSystemVariables())
				dataStoreService.storeDeploymentVariables(applicationNanme, config.getName(),
						config.getSystemVariables());

			DeploymentVariables jvmProperties = this.getDeploymentVariables(config,
					DeploymentVariableType.JVM_PROPERTIES);
			if (jvmProperties != null) {
				for (NameValuePair runtimeVariable : jvmProperties.getNameValuePairs().getNameValuePair()) {
					if (runtimeVariable.isIsDeleted()) {
						traProperties.removeKey(runtimeVariable.getName());
						runtimeVariable.setDeployedValue("");
						runtimeVariable.setValue("");
					} else {
						if (runtimeVariable.getValue() != null && !runtimeVariable.getValue().trim().isEmpty()) {

							if (Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL.equals(runtimeVariable.getName())) {
								traProperties.setProperty(Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL,
										runtimeVariable.getValue());
							} else if (Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX.equals(runtimeVariable.getName())) {
								traProperties.setProperty(Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX,
										runtimeVariable.getValue());
							}
							runtimeVariable.setDeployedValue(runtimeVariable.getValue());
							runtimeVariable.setValue("");
						} else {
							if (runtimeVariable.getDeployedValue() == null
									|| runtimeVariable.getDeployedValue().trim().isEmpty()) {

								if (Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL.equals(runtimeVariable.getName())) {
									String extendedJavaProperties = traProps.get("java.extended.properties");
									String xms = null;

									if (null != extendedJavaProperties && !extendedJavaProperties.trim().isEmpty()) {
										String[] parts = extendedJavaProperties.split(" ");
										for (String part : parts) {
											if (null != part) {
												if (part.contains("-Xms")) {
													xms = part.replace("-Xms", "").trim();
												}
											}
										}
									}
									String intialHeap = traProps.get(Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL);
									if (null == intialHeap
											|| intialHeap.trim().isEmpty() && (null != xms && !xms.trim().isEmpty())) {
										intialHeap = xms;
									}

									traProperties.setProperty(Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL, intialHeap);
									runtimeVariable.setDeployedValue(intialHeap);
									runtimeVariable.setValue("");
								} else if (Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX.equals(runtimeVariable.getName())) {
									String extendedJavaProperties = traProps.get("java.extended.properties");
									String xmx = null;

									if (null != extendedJavaProperties && !extendedJavaProperties.trim().isEmpty()) {
										String[] parts = extendedJavaProperties.split(" ");
										for (String part : parts) {
											if (null != part) {
												if (part.contains("-Xmx")) {
													xmx = part.replace("-Xmx", "").trim();
												}
											}
										}
									}
									String maxHeap = traProps.get(Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX);

									if (null == maxHeap
											|| maxHeap.trim().isEmpty() && (null != xmx && !xmx.trim().isEmpty())) {
										maxHeap = xmx;
									}
									traProperties.setProperty(Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX, maxHeap);
									runtimeVariable.setDeployedValue(maxHeap);
									runtimeVariable.setValue("");
								}

							} else {
								if (Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL.equals(runtimeVariable.getName())) {
									traProperties.setProperty(Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL,
											runtimeVariable.getDeployedValue());
								} else if (Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX.equals(runtimeVariable.getName())) {
									traProperties.setProperty(Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX,
											runtimeVariable.getDeployedValue());
								}
							}
						}
					}

				}
			}
			if (null != config.getJVMProperties())
				dataStoreService.storeDeploymentVariables(applicationNanme, config.getName(),
						config.getJVMProperties());
			dataStoreService.storeDeploymentVariables(applicationNanme, config.getName(),
					config.getLoggerPatternAndLogLevel());
			config.setLoggerPatternAndLogLevel(config.getLoggerPatternAndLogLevel());
			DeploymentVariables loggerLogLevels = this.getDeploymentVariables(config,
					DeploymentVariableType.LOG_PATTERNS);
			StringBuilder logRoles = new StringBuilder();
			if (loggerLogLevels != null) {

				NameValuePairs nameValuePairs = loggerLogLevels.getNameValuePairs();
				if (null != nameValuePairs) {
					List<NameValuePair> nameValuePair = nameValuePairs.getNameValuePair();
					if (null != nameValuePair) {
						for (Iterator<NameValuePair> iterator = nameValuePair.iterator(); iterator.hasNext();) {
							NameValuePair valuePair = iterator.next();
							if (null != valuePair) {
								if (valuePair.isIsDeleted()) {
									traProperties.removeKey(valuePair.getName());
									iterator.remove();
								} else {
									if (valuePair.getValue() != null && !valuePair.getValue().trim().isEmpty()) {
										if (null != valuePair.getDeployedValue()
												&& valuePair.getDeployedValue().equalsIgnoreCase(valuePair.getValue()))
											valuePair.setDeployedValue(valuePair.getValue());
										else
											valuePair.setDeployedValue(valuePair.getDeployedValue());

										logRoles.append(valuePair.getName());
										logRoles.append(":");
										logRoles.append(valuePair.getValue());
									}
								}
							}

						}
					}
				}

				if (logRoles.length() > 0 && !isLogConfigExist) {
					traProperties.setProperty(
							Constants.SYSTEM_PROPERTY_PREFIX + SystemProperty.TRACE_ROLES.getPropertyName(),
							logRoles.toString());
				}
			}
			if (null != config.getLoggerPatternAndLogLevel())

				dataStoreService.storeDeploymentVariables(applicationNanme, config.getName(),
						config.getLoggerPatternAndLogLevel());

			DeploymentVariables beProperties = this.getDeploymentVariables(config,
					DeploymentVariableType.BE_PROPERTIES);
			updateBEProperties(config, traProperties, cddProperties, beProperties, traProps);
			if (null != config.getBEProperties())
				dataStoreService.storeDeploymentVariables(applicationNanme, config.getName(), config.getBEProperties());

			// Add the jmx_port property
			traProperties.setProperty("java.property.be.engine.jmx.connector.port",
					"%" + Constants.TRA_PROP_VAR_JMX_PORT + "%");

			// Add the jmx_host property
			traProperties.setProperty("java.property.be.engine.jmx.connector.host",
					"%" + Constants.TRA_PROP_VAR_JMX_HOST + "%");

			hostTraFileStream.close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			traProperties.store(baos);
			baos.write(new String(
					"tibco.class.path.extended %BE_HOME%/lib/ext/tpcl/beTeagentUpload%PSP%%tibco.class.path.extended%")
							.getBytes());
			byte[] traFileContents = baos.toByteArray();
			deployableTraContentsStream = new ByteArrayInputStream(traFileContents);
			return deployableTraContentsStream;

		} catch (Exception ex) {
			throw new DeploymentFailException(ex);
		}
	}

	/**
	 * This method is sued to get the processing unit start command whn target
	 * host is Windows
	 * 
	 * @param host
	 *            - Details of the host
	 * @param applicationName'
	 *            - Name of the host
	 * @param deployedPath
	 *            -Deployment Path
	 * @param config
	 *            - Processing unit details.
	 * @param defaultProfileName
	 * @return Command to start processing unit on windows
	 */
	private String formWinSshCommand(Host host, String applicationName, ServiceInstance config, String name,
			String defaultProfileName) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.FORMING_BE_EXECUTION_COMMAND));
		String command = Constants.WINDOWS_COMMAND;

		String binPath = getBeBinPath(host, config.getBeId());

		binPath = BEAgentUtil.getWinSshPath(binPath);
		String traPath = applicationName + "_" + name + ".tra";
		String cddPath = applicationName + ".cdd";
		String earPath = applicationName + ".ear";
		String beEngineExePath = binPath + Constants.ENGINE_EXEC_NAME + ".exe";
		String jmxPort = "";
		String jmxHost = "";
		if (config.getJmxPort() != 0) {
			jmxPort = "jmx_port=$1";
			jmxHost = "jmx_host=$2";
			command = MessageFormat.format(command, beEngineExePath, traPath, name, cddPath, config.getPuId(), earPath,
					jmxPort, jmxHost);
		} else {
			command = command.replace("--propVar {6}", "");
			command = command.replace("--propVar {7}", "");
			command = MessageFormat.format(command, beEngineExePath, traPath, name, cddPath, config.getPuId(), earPath);
		}

		if (null != defaultProfileName && !defaultProfileName.trim().isEmpty()) {
			command = command + " -p " + defaultProfileName + ".properties";
		}
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.WINDOWS_BE_EXECUTION_COMMAND, command));
		return command;
	}

	/**
	 * Un-deploy the service instance.
	 * 
	 * @param host
	 *            - Service instance host details.
	 * @param serviceInstance
	 *            - Deployment Unit details.
	 * @param applicationName
	 *            - Name of the application
	 * @param session-
	 *            JSCH Session
	 * @throws JschCommandFailException
	 *             - Fails if jsch command fails
	 */

	private void undeploy(Host host, ServiceInstance serviceInstance, String applicationName, String name,
			Session session) throws JschCommandFailException {
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.UNDEPLOYING_SERVICE_INSTANCE, serviceInstance.getName()));
		String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
		String deploymentPath = serviceInstance.getDeploymentPath();
		deploymentPath = deploymentPath + "/" + applicationName + "/" + name;
		String rmdircmd = "rm -r " + deploymentPath;
		if (startPuMethod.startsWith("windows")) {
			rmdircmd = "rm -r " + BEAgentUtil.getWinSshPath(deploymentPath);
		}
		try {
			ManagementUtil.executeCommand(rmdircmd, session, true, timeout(), maxRetry(), threadSleepTime());
			LOGGER.log(Level.DEBUG,
					messageService.getMessage(MessageKey.UNDEPLOYED_SERVICE_INSTANCE, serviceInstance.getName()));
		} catch (JschCommandFailException e) {
			throw new JschCommandFailException(e);
		}
	}

	/**
	 * Get the audit record
	 * 
	 * @param agentAction
	 *            - Action perform
	 * @param description
	 *            - Description
	 * @param performedBy
	 *            - Action Performed by
	 * @return Audit Record
	 * @throws DatatypeConfigurationException
	 *             -Indicates a serious configuration error.
	 * 
	 */
	private AuditRecord getAuditRecord(BETEAAgentAction agentAction, String description, String performedBy)
			throws DatatypeConfigurationException {
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setAction(agentAction.getAction());
		auditRecord.setDescription(description);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		auditRecord.setPerformedOn(calendar);
		auditRecord.setPeformedBy(performedBy);
		auditRecord.setEntity("Instance");
		return auditRecord;
	}

	/**
	 * @return
	 */
	private Integer timeout() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getDefaultValue()));
	}

	/**
	 * @return
	 */
	private Integer maxRetry() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_MAX_RETRY.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_SSH_MAX_RETRY.getDefaultValue()));
	}

	/**
	 * @return
	 */
	private Integer threadSleepTime() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_SLEEP_TIME.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_SSH_SLEEP_TIME.getDefaultValue()));
	}

	@Override
	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}

	@Override
	public LockManager getLockManager() {
		return lockManager;
	}

	@Override
	public String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance serviceInstance) {
		// Create A Job and submit it
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

		jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));

		Map<String, String> traProps = loadTRAProperties(serviceInstance.getHost().getApplication(),
				serviceInstance.getHost(), serviceInstance);
		Map<String, String> instanceLogLocation = new HashMap<String, String>();
		instanceLogLocation = FetchCddDataUtil.getLogLocationMap(instanceLogLocation, serviceInstance,
				serviceInstance.getHost().getApplication().getName(), dataStoreService, traProps);

		List<Object> results = poolService.submitJobs(
				new BETeaTailCommandJob(this, numberofLines, isASLog, instanceLogLocation), jobExecutionContexts);
		for (Object object : results) {
			if (null != object) {
				if (object instanceof Exception) {
					LOGGER.log(Level.DEBUG, ((Exception) object).getMessage());
				} else if (object instanceof BETeaOperationResult) {
					String logs = ((BETeaOperationResult) object).getResult().toString();
					return logs;
				}
			}
		}
		return null;
	}

	@Override
	public String fireTailCommand(String command, Session session) throws JschCommandFailException {
		return ManagementUtil.fireTailCommand(session, command, timeout());
	}

	@Override
	public String setDefaultProfile(String profileName, ServiceInstance instance) throws Exception {
		if (null == profileName || profileName.trim().isEmpty()) {
			throw new Exception(messageService.getMessage(MessageKey.INVALID_PROFILE_NAME_MESSAGE));
		}
		DeploymentVariables deploymentVariables = getDeploymentVariables(instance,
				DeploymentVariableType.INSTANCE_CONFIG);
		NameValuePair defaultProfile = new NameValuePair();
		defaultProfile.setName(Constants.DEFAULT_PROFILE);
		defaultProfile.setValue(profileName);
		instance.setDefaultProfile(profileName);
		deploymentVariables.getNameValuePairs().getNameValuePair().add(defaultProfile);
		instance.setDeploymentDescription(
				messageService.getMessage(MessageKey.INSTANCE_DEFAULT_PROFILE_UPDATED_MESSAGE));
		if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(instance.getStatus())) {
			instance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
		} else {
			instance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
		}
		storeDeploymentVariables(instance, deploymentVariables, "", false);
		return (messageService.getMessage(MessageKey.INSTANCE_PROFILE_UPDATED_SUCCESS_MESSAGE));
	}
}
