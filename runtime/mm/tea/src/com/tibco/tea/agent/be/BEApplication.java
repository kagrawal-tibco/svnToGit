package com.tibco.tea.agent.be;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.StaleEntityVersionException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.exception.BEUploadFileException;
import com.tibco.cep.bemm.management.exception.InvalidParameterException;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.Versionable;
import com.tibco.cep.bemm.model.impl.ApplicationImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.impl.RuleDefImpl;
import com.tibco.tea.agent.annotations.Customize;
import com.tibco.tea.agent.annotations.TeaOperation;
import com.tibco.tea.agent.annotations.TeaParam;
import com.tibco.tea.agent.annotations.TeaReference;
import com.tibco.tea.agent.annotations.TeaRequires;
import com.tibco.tea.agent.api.MethodType;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.api.TeaPrincipal;
import com.tibco.tea.agent.api.WithConfig;
import com.tibco.tea.agent.api.WithStatus;
import com.tibco.tea.agent.be.comparator.BEApplicationHostComparatorByName;
import com.tibco.tea.agent.be.comparator.BEProcessingUnitAgentComparatorByName;
import com.tibco.tea.agent.be.comparator.BEProcessingUnitComparatorByName;
import com.tibco.tea.agent.be.comparator.BEServiceInstanceComparatorByName;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEApplicationProvider;
import com.tibco.tea.agent.be.provider.BEProcessingUnitAgentProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.ui.model.impl.GroupDeploymentVariableImpl;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;
import com.tibco.tea.agent.events.Event.EventType;
import com.tibco.tea.agent.events.NotificationService;
import com.tibco.tea.agent.support.TeaException;
import com.tibco.tea.agent.types.AgentObjectStatus;

/**
 * This class represents the tea object for business event application.
 * 
 * @author dijadhav
 *
 */
public class BEApplication implements TeaObject, WithStatus, WithConfig<Application> {

	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEApplication.class);
	/**
	 * Application object
	 */
	private Application application;

	/**
	 * Application management service
	 */
	@JsonIgnore
	private BEApplicationsManagementService applicationService;

	/**
	 * Constructor to set application object
	 * 
	 * @param application
	 */
	public BEApplication(Application application) {
		this.application = application;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.hierachy.impl.BEApplication#getDescription()
	 */
	@Override
	public String getDescription() {
		return application.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.hierachy.impl.BEApplication#getName()
	 */
	@Override
	public String getName() {
		return application.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.api.TeaObject#getKey()
	 */

	@Override
	public String getKey() {
		return application.getKey();
	}

	/**
	 * Get Application Management service
	 * 
	 * @return the applicationService
	 */
	@JsonIgnore
	public BEApplicationsManagementService getApplicationService() {
		return applicationService;
	}

	/**
	 * Set Application Management service
	 * 
	 * @param applicationService
	 *            the applicationService to set
	 */
	@JsonIgnore
	public void setApplicationService(BEApplicationsManagementService applicationService) {
		this.applicationService = applicationService;
	}

	@TeaReference(name = "ServiceInstances")
	public Collection<BEServiceInstance> getServiceInstances() {
		List<BEServiceInstance> serviceInstances = new ArrayList<BEServiceInstance>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			for (Host host : application.getHosts()) {
				for (ServiceInstance instance : host.getInstances()) {
					BEServiceInstance serviceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
							.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(instance.getKey());
					if (null == serviceInstance) {
						serviceInstance = new BEServiceInstance(instance);
						serviceInstance.registerWithObjectProvider();
					}
					instance.setUpTime(
							BEMMServiceProviderManager.getInstance().getBEMBeanService().getProcessStartTime(instance));
					serviceInstance.setInstance(instance);
					serviceInstances.add(serviceInstance);
				}
			}
			// applicationService.loadAllDeploymentVariable(application);
			sendDeploymentNotification();
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(serviceInstances, new BEServiceInstanceComparatorByName());
		return serviceInstances;
	}

	@TeaReference(name = "ProcessingUnits")
	public Collection<BEProcessingUnit> getAllProcessingUnits() {
		List<BEProcessingUnit> pus = new ArrayList<BEProcessingUnit>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			for (ProcessingUnit processingUnit : application.getProcessingUnits()) {
				BEProcessingUnit beProcessingUnit = (BEProcessingUnit) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_PROCESSING_UNIT).getInstance(processingUnit.getKey());
				if (null == beProcessingUnit) {
					beProcessingUnit = new BEProcessingUnit(processingUnit);
					beProcessingUnit.registerWithObjectProvider();
				}
				beProcessingUnit.setProcessingUnit(processingUnit);
				pus.add(beProcessingUnit);
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(pus, new BEProcessingUnitComparatorByName());
		return pus;
	}

	@TeaReference(name = "Hosts")
	public Collection<BEApplicationHost> getApplicationHosts() {
		List<BEApplicationHost> applicationHosts = new ArrayList<BEApplicationHost>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			for (Host host : application.getHosts()) {
				BEApplicationHost applicationHost = (BEApplicationHost) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_APPLICATION_HOST).getInstance(host.getKey());
				if (null == applicationHost) {
					applicationHost = new BEApplicationHost(host);
					applicationHost.registerWithObjectProvider();
				}
				applicationHost.sethost(host);
				applicationHosts.add(applicationHost);
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(applicationHosts, new BEApplicationHostComparatorByName());
		return applicationHosts;
	}

	@TeaReference(name = "ApplicationAgents")
	public Collection<BEProcessingUnitAgent> getApplicationAgents() {
		List<BEProcessingUnitAgent> appAgents = new ArrayList<BEProcessingUnitAgent>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			Collection<AgentConfig> agents = applicationService.getAgents(this.getName());
			for (AgentConfig agentConfig : agents) {
				BEProcessingUnitAgentProvider agentProvider = (BEProcessingUnitAgentProvider) ObjectCacheProvider
						.getInstance().getProvider(Constants.BE_PROCESSING_UNIT_AGENT);

				BEProcessingUnitAgent processingUnitAgent = agentProvider.getInstance(agentConfig.getTeaObjectKey());
				if (null != processingUnitAgent) {
					processingUnitAgent.setAgentConfig(agentConfig);
					appAgents.add(processingUnitAgent);
				}
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(appAgents, new BEProcessingUnitAgentComparatorByName());
		return appAgents;
	}

	@Customize(value = "label:Deploy")
	@TeaOperation(name = "deploy", description = "Deploy Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DEPLOY_INSTANCE_PERMISSION)
	public String deploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		String loggedInUser = teaPrincipal.getName();
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = applicationService.deploy(application, instances, loggedInUser);
			sendDeploymentNotification();
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:UnDeploy")
	@TeaOperation(name = "undeploy", description = "UnDeploy Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UNDEPLOY_INSTANCE_PERMISSION)
	public String undeploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.undeploy(application, instances, loggedInUser);
			sendDeploymentNotification();
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:HotDeploy")
	@TeaOperation(name = "hotdeploy", description = "Hot Deploy BE Application Instances", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.HOT_DEPLOY_PERMISSION)
	public String hotdeploy(
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			@TeaParam(name = "instances", alias = "instances") List<String> instances, TeaPrincipal teaPrincipal) {

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return applicationService.hotdeploy(application, earpath, loggedInUser, instances);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:HotDeployAll")
	@TeaOperation(name = "hotdeployAll", description = "Hot Deploy BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.HOT_DEPLOY_PERMISSION)
	public String hotdeploy(
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.hotDeployAll(application, earpath, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Start")
	@TeaOperation(name = "start", description = "Start Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.START_PU_INSTANCE_PERMISSION)
	public String start(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.start(application, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Stop")
	@TeaOperation(name = "stop", description = "Stop Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.STOP_PU_INSTANCE_PERMISSION)
	public String stop(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.stop(application, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Add Service Instance")
	@TeaOperation(name = "createServiceInstance", description = "Creates a new application service instance", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.CREATE_INSTANCE_PERMISSION)
	public String createServiceInstance(
			@Customize(value = "label:Name") @TeaParam(name = "name", alias = "name") String name,
			@Customize(value = "label:Processing Unit") @TeaParam(name = "processingUnit", alias = "processingUnit") String processingUnit,
			@Customize(value = "label:Host") @TeaParam(name = "hostId") final String hostName,
			@Customize(value = "label:JMX Port") @TeaParam(name = "jmxPort", alias = "jmxPort") int jmxPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:JMX UserName") @TeaParam(name = "jmxUserName", alias = "jmxUserName") String jmxUserName,
			@Customize(value = "label:JMX Password") @TeaParam(name = "jmxPassword", alias = "jmxPassword") String jmxPassword,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.application, version);
			String messagae = applicationService.createServiceInstance(getConfig(), name, processingUnit, hostName,
					jmxPort, deploymentPath, true, loggedInUser, jmxUserName, jmxPassword, beId);
			applicationService.getLockManager().incrementVersion(this.application);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);
			return messagae;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Edit")
	@TeaOperation(name = "upload", methodType = MethodType.UPDATE, description = "")
	@TeaRequires(Permission.UPDATE_DEPLOYMENT_PERMISSION)
	public String upload(
			@Customize(value = "label:Description") @TeaParam(name = "description", alias = "Description") String description,
			@Customize(value = "label:CDD") @TeaParam(name = "cddpath", alias = "cddpath") DataSource cddpath,
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			@Customize(value = "label:isCDDMentioned") @TeaParam(name = "isCDDMentioned", alias = "isCDDMentioned", defaultValue = "true") boolean isCDDMentioned,
			@Customize(value = "label:isEARMentioned") @TeaParam(name = "isEARMentioned", alias = "isEARMentioned", defaultValue = "true") boolean isEARMentioned,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			TeaPrincipal teaPrincipal) throws Exception {
		// Edit Application
		String loggedInUser = teaPrincipal.getName();

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.application, version);
			List<String> putoBeRemoved = new ArrayList<>();
			List<String> puAgentstoBeRemoved = new ArrayList<>();
			List<String> instanceAgentstoBeRemoved = new ArrayList<>();
			String message = applicationService.editApplication(this.application.getName(), cddpath, earpath,
					loggedInUser, isCDDMentioned, isEARMentioned, putoBeRemoved, puAgentstoBeRemoved,
					instanceAgentstoBeRemoved);
			for (String pu : putoBeRemoved) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_PROCESSING_UNIT).remove(pu);
			}
			for (String puAgent : puAgentstoBeRemoved) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_PROCESSING_UNIT_AGENT).remove(puAgent);
			}
			for (String instanceAgent : instanceAgentstoBeRemoved) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT).remove(instanceAgent);
			}
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);
			applicationService.getLockManager().incrementVersion(this.application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:Delete Application")
	@TeaOperation(name = "delete", methodType = MethodType.DELETE, description = "Delete the appication")
	@TeaRequires(Permission.DELETE_DEPLOYMENT_PERMISSION)
	public String delete() {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = applicationService.deleteApplication(application.getName());
			BETeaAgentManager.INSTANCE.unRegisterTEAObjects(application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getAppSummary", description = "gets an application summary", methodType = MethodType.READ)
	public Summary getAppSummary() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getApplicationSummary(this.getName());
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	public void registerWithObjectProvider() {
		BEApplicationProvider applicationProvider = (BEApplicationProvider) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_APPLICATION);
		if (null == applicationProvider.getInstance(this.getKey()))
			applicationProvider.add(this.getKey(), this);
	}

	public void setApplicationConfig(Application application) {
		this.application = application;
	}

	@Override
	public Application getConfig() {
		return application;
	}

	@Override
	public AgentObjectStatus getStatus() {
		AgentObjectStatus status = new AgentObjectStatus();
		status.setState(application.getStatus());
		return status;
	}

	@TeaOperation(name = "loadGroupGlobalVariable", description = "Load global variables of group", methodType = MethodType.UPDATE)
	public List<GroupDeploymentVariable> loadGroupGlobalVariable(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.getName(), DeploymentVariableType.GLOBAL_VARIABLES,
					instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupSystemVariable", description = "Load system variables of group")
	public List<GroupDeploymentVariable> loadGroupSystemVariable(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.getName(), DeploymentVariableType.SYSTEM_VARIABLES,
					instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupBEProperties", description = "Load BE prperties of group", methodType = MethodType.UPDATE)
	public List<GroupDeploymentVariable> loadGroupBEProperties(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.getName(), DeploymentVariableType.BE_PROPERTIES,
					instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupJVMProperties", description = "Load jvm properties of group")
	public List<GroupDeploymentVariable> loadGroupJVMProperties(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.getName(), DeploymentVariableType.JVM_PROPERTIES,
					instancesKey);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupLogPatternAndLevel", description = "Load log pattern and level of group", methodType = MethodType.READ)
	public List<GroupDeploymentVariable> loadGroupLogPatternAndLevel(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.getName(), DeploymentVariableType.LOG_PATTERNS,
					instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}
	
	@TeaOperation(name = "saveDeploymentVariables", description = "Save deployment variables of group", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_DEP_VAR_PERMISSION)
	public String saveDeploymentVariables(
			@TeaParam(name = "propertyFile", alias = "propertyFile") DataSource propertyFile,
			@TeaParam(name = "instances", alias = "instances") List<String> instances,
			@TeaParam(name = "type", alias = "type") String type, TeaPrincipal teaPrincipal) {
		
		Properties prop = new Properties();
		
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			
			try {
				if (propertyFile.getInputStream().available() <= 0)
					throw new InvalidParameterException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVALID_FILE));
			} catch (IOException e) {
				throw new BEUploadFileException(e.getMessage());
			}
			
			prop.load(propertyFile.getInputStream());
			
			List<GroupDeploymentVariable> groupDeploymentVariables = new ArrayList<GroupDeploymentVariable>();
			GroupDeploymentVariable gdv = null;
			
			for(Map.Entry entry : prop.entrySet()){
				gdv = new GroupDeploymentVariableImpl();
				gdv.setSelectedInstances(instances);
				gdv.setName((String)entry.getKey());
				gdv.setValue((String)entry.getValue());
				groupDeploymentVariables.add(gdv);
			}
			
			String message = "";
			switch(type){
				
				case "GV" :		message = saveGroupGlobalVariables(groupDeploymentVariables, teaPrincipal);
								break;
				case "JVM" :	message = saveGroupJVMproperties(groupDeploymentVariables, teaPrincipal);
								break;
				case "BE" :		message = saveGroupBEProperties(groupDeploymentVariables, teaPrincipal);
								break;
				case "SYS" :	message = saveGroupSystemVariables(groupDeploymentVariables, teaPrincipal);
								break;
			}
			
			
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupGlobalVariables", description = "Save global variables of group", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_GV_VAR_PERMISSION)
	public String saveGroupGlobalVariables(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.GLOBAL_VARIABLES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.storeGroupDeploymentVariable(this.getName(),
					DeploymentVariableType.GLOBAL_VARIABLES, groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification();
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupLogPatternAndLevel", description = "Save log pattern and level of group", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_LOG_LEVEL_PERMISSION)
	public String saveGroupLogPatternAndLevel(
			@TeaParam(name = "groupLogPatternsAndLevel", alias = "groupLogPatternsAndLevel") List<GroupDeploymentVariable> groupLogPatternsAndLevel,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.LOG_PATTERNS, groupLogPatternsAndLevel);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupLogPatternsAndLevel);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.storeGroupDeploymentVariable(this.getName(),
					DeploymentVariableType.LOG_PATTERNS, groupLogPatternsAndLevel, loggedInUser);
			sendDeploymentNotification();
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupSystemVariables", description = "Save system variables of group")
	@TeaRequires(Permission.UPDATE_SYSTEM_PROPS_PERMISSION)
	public String saveGroupSystemVariables(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.SYSTEM_VARIABLES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.storeGroupDeploymentVariable(this.getName(),
					DeploymentVariableType.SYSTEM_VARIABLES, groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification();
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupBEProperties", description = "Save business events properties of group")
	@TeaRequires(Permission.UPDATE_BE_PROPS_PERMISSION)
	public String saveGroupBEProperties(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.BE_PROPERTIES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.storeGroupDeploymentVariable(this.getName(),
					DeploymentVariableType.BE_PROPERTIES, groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification();
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupJVMproperties", description = "Save JVM  properties of group")
	@TeaRequires(Permission.UPDATE_JVM_PROPS_PERMISSION)
	public String saveGroupJVMproperties(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.JVM_PROPERTIES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.storeGroupDeploymentVariable(this.getName(),
					DeploymentVariableType.JVM_PROPERTIES, groupDeploymentVariables, loggedInUser);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			sendDeploymentNotification();
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "showApplicationCDDContent", description = "Display the cdd content of application", methodType = MethodType.READ)
	public String showApplicationCDDContent() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getBEApplicationsCDD(this.getName());
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:groupInvoke")
	@TeaOperation(name = "groupInvoke", description = "Invoke the passed method on the given group entities ", methodType = MethodType.READ)
	public Map<String, OperationResult> groupInvoke(
			@TeaParam(name = "entityName", alias = "entityName") String entityName,
			@TeaParam(name = "methodGroup", alias = "methodGroup") String methodGroup,
			@TeaParam(name = "methodName", alias = "methodName") String methodName,
			@TeaParam(name = "params", alias = "params") Map<String, String> params,
			@TeaParam(name = "instanceList", alias = "instanceList") List<String> instances,
			@TeaParam(name = "groupOperation", alias = "groupOperation") Boolean groupOperation,
			TeaPrincipal teaPrincipal) {
		String loggedInUser = teaPrincipal.getName();
		if (groupOperation == true) {
			Map<String, OperationResult> returnDataMap;
			try {
				applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
				returnDataMap = applicationService.invokeGroupOperation(entityName, methodGroup, methodName, params,
						instances, application, loggedInUser, null);

			} catch (MBeanOperationFailException ex) {
				throw logErrorMessage(ex);
			} finally {
				applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			}
			return returnDataMap;
		}
		return null;
	}

	@TeaOperation(name = "getApplication", description = "Get BE application Info", methodType = MethodType.READ)
	public Application getApplication() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return application;
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "copyInstance", description = "This method is used to copy the instance", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.COPY_INSTANCE_PERMISSION)
	public String copyInstance(@TeaParam(name = "instanceName", alias = "instanceName") String instanceName,
			@Customize(value = "label:Processing Unit") @TeaParam(name = "processingUnit", alias = "processingUnit") String processingUnit,
			@Customize(value = "label:Host") @TeaParam(name = "hostId") final String hostId,
			@Customize(value = "label:JMX Port") @TeaParam(name = "jmxPort", alias = "jmxPort") int jmxPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:Instance") @TeaParam(name = "instance", alias = "instance") ServiceInstance instance,
			@Customize(value = "label:JMX UserName") @TeaParam(name = "jmxUserName", alias = "jmxUserName") String jmxUserName,
			@Customize(value = "label:JMX Password") @TeaParam(name = "jmxPassword", alias = "jmxPassword") String jmxPassword,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().checkVersion(this.application, version);
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.copyInstance(instanceName, processingUnit, hostId, deploymentPath,
					jmxPort, instance, loggedInUser, jmxUserName, jmxPassword, beId);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(this.application);
			applicationService.getLockManager().incrementVersion(this.application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Group Kill")
	@TeaOperation(name = "groupKill", description = "Invoke kill operation on passed instances ", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.KILL_INSTANCE_PERMISSION)
	public String groupKill(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return applicationService.groupKill(instances, application, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Group ThreadDump")
	@TeaOperation(name = "groupThreadDump", description = "Get thread dumps of passed instances", methodType = MethodType.READ)
	public DataSource groupThreadDump(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return applicationService.groupThreadDumpZip(instances, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get InstanceAgents")
	@TeaOperation(name = "getInstanceAgents", description = "Get instance agents", methodType = MethodType.READ)
	public Set<String> getInstanceAgents(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return applicationService.getInstanceAgents(instances, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Alerts")
	@TeaOperation(name = "getAlerts", description = "", methodType = MethodType.READ)
	public ArrayList<Map<String, Object>> getAlerts(
			@TeaParam(name = "isRuleAdminPermission", alias = "isRuleAdminPermission") Boolean isRuleAdminPermission,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			Map<String, Application> applicationMap = applicationService.getApplications();
			for (Application app : applicationMap.values()) {
				app.setAlertCount(0);
			}
			return BEMMServiceProviderManager.getInstance().getMetricProviderService().getAlerts(
					MetricAttribute.ALERTS_QUERY_METRICS, this.getName(), isRuleAdminPermission, loggedInUser);

		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Clear alerts")
	@TeaOperation(name = "clearAlerts", description = "Clear alerts", methodType = MethodType.UPDATE)
	@TeaRequires({ Permission.CLEAR_ALERTS_PERMISSION })
	public String clearAlerts(@TeaParam(name = "alertIds", alias = "alertIds") List<String> alertIds,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return BEMMServiceProviderManager.getInstance().getMetricProviderService().clearAlerts(alertIds);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Rules for application")
	@TeaOperation(name = "getRules", description = "Get Rules for application", methodType = MethodType.READ)
	@TeaRequires({ Permission.GET_RULES_PERMISSION })
	public ArrayList<RuleDef> getRules(
			@TeaParam(name = "isTeaAdminPermission", alias = "isTeaAdminPermission") boolean isTeaAdminPermission,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return BEMMServiceProviderManager.getInstance().getMetricRuleService().getAppRules(this.getName(),
					loggedInUser, isTeaAdminPermission);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Delete rule")
	@TeaOperation(name = "deleteRule", description = "Delete Rule", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DELETE_RULE_PERMISSION)
	public String deleteRule(@TeaParam(name = "ruleNames", alias = "ruleNames") List<String> ruleNames,
			@TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.application, version);
			String message = BEMMServiceProviderManager.getInstance().getMetricRuleService().delete(ruleNames);
			applicationService.getLockManager().incrementVersion(application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Create rule")
	@TeaOperation(name = "createRule", description = "Create Rule", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.CREATE_RULE_PERMISSION)
	public String createRule(@TeaParam(name = "rule", alias = "rule") String ruleStr,
			@TeaParam(name = "entity", alias = "entity") String entity,
			@TeaParam(name = "isRuleAdminPermission", alias = "isRuleAdminPermission") boolean isRuleAdmin,
			@TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.application, version);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			RuleDefImpl ruleDef = (RuleDefImpl) mapper.readValue(ruleStr, RuleDefImpl.class);
			RuleDef preparedRule = BEMMServiceProviderManager.getInstance().getMetricRuleService().prepareRule(ruleDef,
					this.getApplication().getName(), entity, loggedInUser, false, isRuleAdmin);

			String message = BEMMServiceProviderManager.getInstance().getMetricRuleService().createRule(preparedRule);
			applicationService.getLockManager().incrementVersion(application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Update Rules state")
	@TeaOperation(name = "updateRulesState", description = "Update rules state", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_RULE_PERMISSION)
	public String enableRule(@TeaParam(name = "ruleNames", alias = "ruleNames") List<String> ruleNames,
			@TeaParam(name = "isEnable", alias = "isEnable") boolean isEnable,
			@TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.application, version);
			String ruleState = BEMMServiceProviderManager.getInstance().getMetricRuleService()
					.updateRulesState(ruleNames, isEnable);
			applicationService.getLockManager().incrementVersion(application);
			return ruleState;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Log Levels")
	@TeaOperation(name = "getLogLevels", description = "", methodType = MethodType.READ)
	public Map<String, String> getLogLevels() {
		return ManagementUtil.getLogLevels();
	}

	@Customize(value = "label:Get Loggers")
	@TeaOperation(name = "getLoggers", description = "", methodType = MethodType.READ)
	public Map<String, String> getGroupLoggers(
			@TeaParam(name = "instances", alias = "instances") List<String> instances) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getGroupRuntimeLoggerLevels(application, instances);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Update rule")
	@TeaOperation(name = "updateRule", description = "Update Rule", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_RULE_PERMISSION)
	public String updateRule(@TeaParam(name = "rule", alias = "rule") String ruleStr,
			@TeaParam(name = "entity", alias = "entity") String entity,
			@TeaParam(name = "isRuleAdminPermission", alias = "isRuleAdminPermission") boolean isAdmin,
			@TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.application, version);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			RuleDefImpl ruleDef = (RuleDefImpl) mapper.readValue(ruleStr, RuleDefImpl.class);
			RuleDef preparedRule = BEMMServiceProviderManager.getInstance().getMetricRuleService().prepareRule(ruleDef,
					this.getApplication().getName(), entity, loggedInUser, true, isAdmin);

			return BEMMServiceProviderManager.getInstance().getMetricRuleService().updateRule(preparedRule);

		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "applyLogPattern", description = "Apply Log pattern and log levels at runtime", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_LOG_LEVEL_PERMISSION)
	public String applyLogPattern(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			@TeaParam(name = "logPatternsAndLevel", alias = "logPatternsAndLevel") Map<String, String> logPatternsAndLevel,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return applicationService.applyLogPatterns(application, instances, logPatternsAndLevel, loggedInUser,
					false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Upload TRA Configration")
	@TeaOperation(name = "uploadTraConfiguration", methodType = MethodType.UPDATE, description = "")
	public String uploadTraConfiguration(@TeaParam(name = "hostId", alias = "hostId") String hostId,
			@TeaParam(name = "uploadFile", alias = "uploadFile") boolean uploadFile,
			@TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@TeaParam(name = "uploadTRAfile", alias = "uploadTRAfile") DataSource uploadTRAfile,
			@TeaParam(name = "traFilePath", alias = "traFilePath") String traFilePath,
			@TeaParam(name = "isDeleted", alias = "isDeleted") boolean isDeleted, TeaPrincipal teaPrincipal)
			throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.uploadHostTra(hostId, uploadFile, deploymentPath, uploadTRAfile, traFilePath,
					application, isDeleted, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Save Host TRA Configuration")
	@TeaOperation(name = "saveHostTraConfiguration", methodType = MethodType.UPDATE, description = "")
	public String saveHostTraConfiguration(
			@TeaParam(name = "version", alias = "version", defaultValue = "0") Long version, TeaPrincipal teaPrincipal)
			throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(application, version);
			String message = applicationService.saveHostTraConfig(application);
			applicationService.getLockManager().incrementVersion(application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Load TRA Configration")
	@TeaOperation(name = "loadTraConfiguration", methodType = MethodType.UPDATE, description = "")
	public void loadTraConfiguration(TeaPrincipal teaPrincipal) throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.loadApplicationTraConfig(application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Download Log")
	@TeaOperation(name = "downloadLog", methodType = MethodType.READ, description = "")
	public DataSource downloadLog(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<String> errors = new ArrayList<String>();
			DataSource logs = applicationService.downloadLogs(instances, application, false);
			if (errors.size() == instances.size()) {
				throw new TeaException(BEMMServiceProviderManager.getInstance().getMessageService()
						.getMessage(MessageKey.INSTANCE_LOG_FILE_NOT_EXIST_ERROR));
			}
			return logs;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Download AS Log")
	@TeaOperation(name = "downloadASLog", methodType = MethodType.READ, description = "")
	public DataSource downloadASLog(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<String> errors = new ArrayList<String>();
			DataSource logs = applicationService.downloadLogs(instances, application, true);
			if (errors.size() == instances.size()) {
				throw new TeaException(BEMMServiceProviderManager.getInstance().getMessageService()
						.getMessage(MessageKey.INSTANCE_LOG_FILE_NOT_EXIST_ERROR));
			}
			return logs;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Load All Deployment Variables")
	@TeaOperation(name = "loadAllDeploymentVariables", methodType = MethodType.READ, description = "")
	public void loadAllDeploymentVariables() throws Exception {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			if (!application.isMonitorableOnly())
				applicationService.loadAllDeploymentVariable(application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Application Propperties")
	@TeaOperation(name = "getApplicationPropperties", methodType = MethodType.UPDATE, description = "")
	public Map<String, Map<String, String>> getApplicationPropperties() throws Exception {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Host> applicationHosts = application.getHosts();
			Map<String, Map<String, String>> propertiesMap = new HashMap();
			Map<String, String> bePropertiesMap = new HashMap();
			Map<String, String> sysPropertiesMap = new HashMap();
			Map<String, String> gvPropertiesMap = new HashMap();
			if (null != applicationHosts && !applicationHosts.isEmpty()) {
				for (Host applicationHost : applicationHosts) {
					if (null != applicationHost) {
						for (ServiceInstance instance : applicationHost.getInstances()) {
							if (null != instance) {
								for (int i = 0; i < instance.getBEProperties().getNameValuePairs().getNameValuePair()
										.size(); i++) {
									bePropertiesMap.put(
											instance.getBEProperties().getNameValuePairs().getNameValuePair().get(i)
													.getName(),
											instance.getBEProperties().getNameValuePairs().getNameValuePair().get(i)
													.getDefaultValue());
								}

								for (int i = 0; i < instance.getGlobalVariables().getNameValuePairs().getNameValuePair()
										.size(); i++) {
									gvPropertiesMap.put(
											instance.getGlobalVariables().getNameValuePairs().getNameValuePair().get(i)
													.getName(),
											instance.getGlobalVariables().getNameValuePairs().getNameValuePair().get(i)
													.getDefaultValue());
								}

								for (int i = 0; i < instance.getSystemVariables().getNameValuePairs().getNameValuePair()
										.size(); i++) {
									sysPropertiesMap.put(
											instance.getSystemVariables().getNameValuePairs().getNameValuePair().get(i)
													.getName(),
											instance.getSystemVariables().getNameValuePairs().getNameValuePair().get(i)
													.getDefaultValue());
								}

							}
						}
						propertiesMap.put("BEPROPS", bePropertiesMap);
						propertiesMap.put("GV", gvPropertiesMap);
						propertiesMap.put("SYSTEMPROPS", sysPropertiesMap);
					}
				}
			}
			return propertiesMap;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	/**
	 * This method is used to sent the deployment status change notification.
	 */

	private void sendDeploymentNotification() {
		// Application and host deployment status
		boolean isAllAppInstancesDeployed = false;
		List<ServiceInstance> applInstances = new ArrayList<>();

		for (Host host : application.getHosts()) {
			if (null != host) {
				for (ServiceInstance instance : host.getInstances()) {
					if (null != instance) {
						applInstances.add(instance);
					}
				}
			}
		}
		if (!application.isMonitorableOnly()) {
			for (ServiceInstance serviceInstance : applInstances) {
				if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getDeploymentStatus())
						|| BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus()
								.equals(serviceInstance.getDeploymentStatus())) {
					isAllAppInstancesDeployed = false;
					break;
				} else {
					isAllAppInstancesDeployed = true;
				}
			}
			if (!isAllAppInstancesDeployed) {
				application.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
			} else {
				application.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
			}
		} else {
			for (ServiceInstance serviceInstance : applInstances) {
				if (BETeaAgentStatus.DEPLOYED.getStatus().equals(serviceInstance.getDeploymentStatus())) {
					isAllAppInstancesDeployed = true;
				} else {
					isAllAppInstancesDeployed = false;
					break;
				}
			}
			if (!isAllAppInstancesDeployed) {
				application.setDeploymentStatus(BETeaAgentStatus.UNDEPLOYED.getStatus());
			} else {
				application.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
			}
		}
		// Application instances are deployed
		for (ServiceInstance serviceInstance : applInstances) {
			if (serviceInstance.getDeployed()) {
				isAllAppInstancesDeployed = false;
				break;
			} else {
				isAllAppInstancesDeployed = true;
			}
		}
		application.setDeployed(isAllAppInstancesDeployed);
		ManagementUtil.setRunningStatus(application);
		NotificationService notificationService = ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_APPLICATION).getNotificationService();
		if (null != notificationService) {
			notificationService.notify(this, EventType.CHILDREN_CHANGE, null, this.application);
		}

	}

	/**
	 * Log error message
	 * 
	 * @param ex
	 *            - Exception
	 * @return TeaException
	 */
	protected TeaException logErrorMessage(Exception ex) {
		String message = ex.getMessage();
		if (ex instanceof Throwable) {
			if (ex instanceof RuntimeException) {
				message = "Unknown Error";
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			} else {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}
		return new TeaException(message);
	}

	@Customize(value = "label:Delete")
	@TeaOperation(name = "groupDelete", description = "Delete selected instances", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DELETE_INSTANCE_PERMISSION)
	public String groupDelete(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version", defaultValue = "0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(application, version);
			List<String> deletedInstances = new ArrayList<>();
			String message = applicationService.groupDelete(application, instances, loggedInUser, deletedInstances);
			if (!deletedInstances.isEmpty()) {
				ObjectProvider<? extends TeaObject> provider = ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE);
				if (null != provider) {
					for (String key : deletedInstances) {
						provider.remove(key);
					}
				}

			}

			// UnregisterApplicationHosts which have zero linked instances
			List<Host> applicationHosts = application.getHosts();
			if (null != applicationHosts && !applicationHosts.isEmpty()) {
				for (Host applicationHost : applicationHosts) {
					if (null != applicationHost) {
						List<ServiceInstance> serviceIns = applicationHost.getInstances();
						if (serviceIns != null && serviceIns.size() == 0) {
							application.getHosts().remove(applicationHost);
							BETeaAgentManager.INSTANCE.unRegisterTEAObjects(applicationHost);
						}
					}
				}
			}

			applicationService.getLockManager().incrementVersion(application);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Export")
	@TeaOperation(name = "export", description = "Export the selected application", methodType = MethodType.READ)
	public DataSource export(TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.export(this.application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Fire Tail Command")
	@TeaOperation(name = "fireTailCommand", description = "", methodType = MethodType.READ)
	public String fireTailCommand(
			@Customize(value = "label:NumberofLines") @TeaParam(name = "numberofLines", alias = "numberofLines") String numberofLines,
			@Customize(value = "label:isASLog") @TeaParam(name = "isASLog", alias = "isASLog") boolean isASLog,
			@Customize(value = "label:Instance") @TeaParam(name = "instance", alias = "instance") ServiceInstance instance) {

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.fireTailCommand(numberofLines, isASLog, instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Save Application Profile")
	@TeaOperation(name = "saveApplicationProfile", description = "", methodType = MethodType.UPDATE)
	public String saveApplicationProfile(
			@Customize(value = "label:Profile Name") @TeaParam(name = "profileName", alias = "profileName") String profileName,
			@Customize(value = "label:Global Variables") @TeaParam(name = "globalVariables", alias = "globalVariables") Map<String, String> globalVariables,
			@Customize(value = "label:System Properties") @TeaParam(name = "systemProperties", alias = "systemProperties") Map<String, String> systemProperties,
			@Customize(value = "label:BE Properties") @TeaParam(name = "beProperties", alias = "beProperties") Map<String, String> beProperties,
			@Customize(value = "label:Is Edit") @TeaParam(name = "isEdit", alias = "isEdit") boolean isEdit) {

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			if (!isEdit)
				return applicationService.addProfile(profileName, globalVariables, systemProperties, beProperties,
						application);

			return applicationService.editProfile(profileName, globalVariables, systemProperties, beProperties,
					application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Copy Application Profile")
	@TeaOperation(name = "copyApplicationProfile", description = "", methodType = MethodType.UPDATE)
	public String copyApplicationProfile(
			@Customize(value = "label:New Profile Name") @TeaParam(name = "newProfileName", alias = "newProfileName") String newProfileName,
			@Customize(value = "label:Old Profile Name") @TeaParam(name = "oldProfileName", alias = "oldProfileName") String oldProfileName) {

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.copyProfile(newProfileName, oldProfileName, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Set Application Default Profile")
	@TeaOperation(name = "setApplicationDefaultProfile", description = "", methodType = MethodType.UPDATE)
	public String setApplicationDefaultProfile(
			@Customize(value = "label:Profile Name") @TeaParam(name = "profileName", alias = "profileName") String profileName) {

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.setDefaultProfile(profileName, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "Get Application Profile")
	@TeaOperation(name = "getApplicationProfileDetails", description = "", methodType = MethodType.READ)
	public Map<String, Map<String, String>> getApplicationProfileDetails(
			@Customize(value = "label:Profile Name") @TeaParam(name = "profileName", alias = "profileName") String profileName) {

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getApplicationProfileDetails(profileName, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "Delete Application Profile")
	@TeaOperation(name = "deleteApplicationProfile", description = "", methodType = MethodType.UPDATE)
	public String deleteApplicationProfile(
			@Customize(value = "label:Profile Name") @TeaParam(name = "profileName", alias = "profileName") String profileName) {

		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.deleteApplicationProfile(profileName, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	/**
	 * @param groupDeploymentVariables
	 * @return
	 */
	private List<Versionable> getAffectedDeploymentVariables(DeploymentVariableType type,
			List<GroupDeploymentVariable> groupDeploymentVariables) {
		Set<String> serviceInstanceKeys = new HashSet<String>();
		for (GroupDeploymentVariable groupDeploymentVariable : groupDeploymentVariables) {
			serviceInstanceKeys.addAll(groupDeploymentVariable.getSelectedInstances());
		}
		List<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>();
		for (Host host : application.getHosts()) {
			for (ServiceInstance serviceInstance : host.getInstances()) {
				if (null != serviceInstance && serviceInstanceKeys.contains(serviceInstance.getKey())) {
					serviceInstances.add(serviceInstance);
				}
			}
		}
		List<Versionable> deploymentVariablesList = new ArrayList<>();
		switch (type) {
		case GLOBAL_VARIABLES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				deploymentVariablesList.add(serviceInstance.getGlobalVariables());
			}
			break;
		case SYSTEM_VARIABLES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				deploymentVariablesList.add(serviceInstance.getSystemVariables());
			}
			break;
		case JVM_PROPERTIES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				deploymentVariablesList.add(serviceInstance.getJVMProperties());
			}
			break;
		case LOG_CONFIG:
			for (ServiceInstance serviceInstance : serviceInstances) {
				deploymentVariablesList.add(serviceInstance.getLoggerLogLevels());
			}
			break;
		case LOG_PATTERNS:
			for (ServiceInstance serviceInstance : serviceInstances) {
				deploymentVariablesList.add(serviceInstance.getLoggerPatternAndLogLevel());
			}
			break;
		default:
			break;
		}
		return deploymentVariablesList;
	}

	/**
	 * @param type
	 * @param serviceInstances
	 * @param userEntityVersion
	 * @throws StaleEntityVersionException
	 */
	private void checkDeploymentVariablesVersion(List<Versionable> deploymentVariablesList,
			List<GroupDeploymentVariable> groupDeploymentVariables) throws StaleEntityVersionException {
		if (groupDeploymentVariables.size() > 0) {
			applicationService.getLockManager().checkVersions(deploymentVariablesList,
					groupDeploymentVariables.get(0).getVariablesVersion());
		}
	}

	private void incrementDeploymentVariablesVersion(List<Versionable> deploymentVariablesList) {
		for (Versionable versionable : deploymentVariablesList)
			applicationService.getLockManager().incrementVersion(versionable);
	}

	@Customize(value = "Get Application GV And Type")
	@TeaOperation(name = "getApplicationGVNameAndType", description = "", methodType = MethodType.READ)
	public Map<String, String> getApplicationGVNameAndType() {

		try {
			return applicationService.getApplicationGVNameAndType(application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

}
