package com.tibco.tea.agent.be;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.StaleEntityVersionException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.Versionable;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.tea.agent.annotations.Customize;
import com.tibco.tea.agent.annotations.TeaOperation;
import com.tibco.tea.agent.annotations.TeaParam;
import com.tibco.tea.agent.annotations.TeaReference;
import com.tibco.tea.agent.annotations.TeaRequires;
import com.tibco.tea.agent.api.MethodType;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.api.TeaPrincipal;
import com.tibco.tea.agent.api.WithConfig;
import com.tibco.tea.agent.be.comparator.BEServiceInstanceComparatorByName;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEProcessingUnitProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;
import com.tibco.tea.agent.events.Event.EventType;
import com.tibco.tea.agent.events.NotificationService;
import com.tibco.tea.agent.support.TeaException;

/**
 * This class holds the details of processing unit in TEA object hierarchy
 * 
 * @author dijadhav
 *
 */
public class BEProcessingUnit implements TeaObject, WithConfig<ProcessingUnit> {

	/**
	 * Logger instance
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEProcessingUnit.class);

	private ProcessingUnit processingUnit;
	@JsonIgnore
	private BEApplicationsManagementService applicationService;

	/**
	 * @param application
	 */
	public BEProcessingUnit(ProcessingUnit processingUnit) {
		this.processingUnit = processingUnit;
	}

	@Override
	public String getDescription() {
		return "Processing unit " + processingUnit.getPuId();
	}

	@Override
	public String getName() {
		return processingUnit.getPuId();
	}

	@Override
	public String getKey() {
		return processingUnit.getKey();
	}

	/**
	 * @return the applicationService
	 */
	@JsonIgnore
	public BEApplicationsManagementService getApplicationService() {
		return applicationService;
	}

	/**
	 * @param applicationService
	 *            the applicationService to set
	 */
	@JsonIgnore
	public void setApplicationService(BEApplicationsManagementService applicationService) {
		this.applicationService = applicationService;
	}

	public void registerWithObjectProvider() {
		BEProcessingUnitProvider processingUnitProvider = (BEProcessingUnitProvider) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_PROCESSING_UNIT);
		if (null == processingUnitProvider.getInstance(this.getKey()))
			processingUnitProvider.add(this.getKey(), this);

	}

	@TeaReference(name = "ServiceInstances")
	public Collection<BEServiceInstance> getServiceInstances() {
		List<BEServiceInstance> beServiceInstances = new ArrayList<BEServiceInstance>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);			
			Collection<ServiceInstance> serviceInstances = applicationService
					.getServiceInstancesByPU(processingUnit.getApplicationConfig().getName(), processingUnit);
			for (ServiceInstance serviceInstance : serviceInstances) {
				BEServiceInstance beServiceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(serviceInstance.getKey());
				if (null == beServiceInstance) {
					beServiceInstance = new BEServiceInstance(serviceInstance);
					beServiceInstance.registerWithObjectProvider();
				}
				serviceInstance.setUpTime(BEMMServiceProviderManager.getInstance().getBEMBeanService()
						.getProcessStartTime(serviceInstance));
				beServiceInstance.setInstance(serviceInstance);
				beServiceInstances.add(beServiceInstance);
			}
			setDeployed(serviceInstances);
			setDeploymentStatus(serviceInstances);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(beServiceInstances, new BEServiceInstanceComparatorByName());
		return beServiceInstances;
	}

	@TeaReference(name = "Hosts")
	public Collection<BEApplicationHost> getHost() {
		Set<BEApplicationHost> applicationHosts = new HashSet<BEApplicationHost>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);			
			Collection<Host> hosts = applicationService.getHostsForPU(processingUnit.getApplicationConfig().getName(),
					processingUnit);
			for (Host host : hosts) {
				BEApplicationHost applicationHost = (BEApplicationHost) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_APPLICATION_HOST).getInstance(host.getKey());
				if (null == applicationHost) {
					applicationHost = new BEApplicationHost(host);
					applicationHost.registerWithObjectProvider();
				}
				applicationHosts.add(applicationHost);
			}
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return applicationHosts;
	}

	@TeaOperation(name = "getAgents", description = "Get PU Agents", methodType = MethodType.READ)
	public Collection<AgentConfig> getAgents(){
		
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return processingUnit.getAgents();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getProcessingUnitSummary", description = "Get an application summary", methodType = MethodType.READ)
	public Summary getAppSummary() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getApplicationSummaryByPUId(processingUnit.getApplicationConfig().getName(),
					processingUnit.getPuId());
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Deploy")
	@TeaOperation(name = "deploy", description = "Deploy new BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.DEPLOY_INSTANCE_PERMISSION)
	public String deploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = applicationService.deploy(processingUnit.getApplicationConfig(), instances, loggedInUser);
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
		List<String> errorMessages;
		try {
			errorMessages = new LinkedList<String>();
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = applicationService.undeploy(this.processingUnit.getApplicationConfig(), instances,
					loggedInUser);
			sendDeploymentNotification();
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:HotDeploy")
	@TeaOperation(name = "hotdeploy", description = "HotDeploy BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UNDEPLOY_INSTANCE_PERMISSION)
	public String hotdeploy(
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			@TeaParam(name = "instances", alias = "instances") List<String> instances, TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = applicationService.hotdeploy(this.processingUnit.getApplicationConfig(), earpath,
					loggedInUser, instances);
			return message;
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
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return applicationService.start(this.processingUnit.getApplicationConfig(), instances, loggedInUser);
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
			return applicationService.stop(this.processingUnit.getApplicationConfig(), instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupGlobalVariable", description = "Load global variables of group", methodType = MethodType.UPDATE)
	public List<GroupDeploymentVariable> loadGroupGlobalVariable(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.processingUnit.getApplicationConfig().getName(),
					DeploymentVariableType.GLOBAL_VARIABLES, instancesKey);
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
			return applicationService.loadDeploymentVariable(this.processingUnit.getApplicationConfig().getName(),
					DeploymentVariableType.SYSTEM_VARIABLES, instancesKey);
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
			return applicationService.loadDeploymentVariable(this.processingUnit.getApplicationConfig().getName(),
					DeploymentVariableType.JVM_PROPERTIES, instancesKey);
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			if (groupOperation == true) {
				String loggedInUser = teaPrincipal.getName();
				Map<String, OperationResult> returnDataMap = applicationService.invokeGroupOperation(entityName,
						methodGroup, methodName, params, instances, this.processingUnit.getApplicationConfig(),
						loggedInUser, null);
				Collection<BEServiceInstance> allInstancesList = getServiceInstances();

				for (BEServiceInstance instance : allInstancesList) {
					if (instances.contains(instance.getKey())) {
						Host host = instance.getInstance().getHost();
						if (null != host) {
							String password = instance.getInstance().getJmxPassword();
							String username = instance.getInstance().getJmxUserName();
							String hostIp = host.getHostIp();
							int jmxPort = instance.getInstance().getJmxPort();
							OperationResult operationResult = new OperationResult();
							try {
								operationResult = BEMMServiceProviderManager.getInstance().getBEMBeanService().invoke(
										entityName, methodGroup, methodName, params, username, password, hostIp,
										jmxPort, -1, instance.getKey(), host.getApplication().getClusterName());
								returnDataMap.put(instance.getInstance().getName(), operationResult);
							} catch (MBeanOperationFailException e) {
								if ("No running inference agents were found. This operation only applies to sessions of type Inference"
										.toLowerCase().equals(e.getLocalizedMessage().toLowerCase())) {
									continue;
								} else {
									throw e;
								}
							}
						}
					}
				}
				return returnDataMap;
			}
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return null;
	}

	@TeaOperation(name = "getProcessingUnit", description = "Get Processing Unit", methodType = MethodType.READ)
	public ProcessingUnit getProcessingUnit() {
		return processingUnit;
	}

	private void setDeploymentStatus(Collection<ServiceInstance> serviceInstances) {
		for (ServiceInstance instance : serviceInstances) {
			if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(instance.getDeploymentStatus())
					|| BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus().equals(instance.getDeploymentStatus())) {
				processingUnit.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
				processingUnit.setDeploymentDescription("Some instances are updated.");
				break;
			}
		}

	}

	/**
	 * @param serviceInstances
	 */
	private void setDeployed(Collection<ServiceInstance> serviceInstances) {
		boolean isDeployed = false;
		for (ServiceInstance instance : serviceInstances) {

			if (instance.getDeployed()) {
				isDeployed = true;
			} else {
				isDeployed = false;
				break;
			}
		}
		processingUnit.setDeployed(isDeployed);
	}

	/**
	 * This method is used to sent the deployment status change notification.
	 */

	private void sendDeploymentNotification() {

		List<ServiceInstance> applInstances = new ArrayList<>();

		for (Host host : this.processingUnit.getApplicationConfig().getHosts()) {
			if (null != host) {
				for (ServiceInstance instance : host.getInstances()) {
					if (null != instance && instance.getPuId().equals(this.processingUnit.getPuId())) {
						applInstances.add(instance);
					}
				}
			}
		}
		boolean isAllDeployed = false;
		for (ServiceInstance serviceInstance : applInstances) {
			if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getDeploymentStatus())
					|| BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus().equals(serviceInstance.getDeploymentStatus())) {
				isAllDeployed = false;
				break;
			} else {
				isAllDeployed = true;
			}
		}
		if (!isAllDeployed) {
			this.processingUnit.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
		} else {
			this.processingUnit.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
		}
		boolean isDeployed = false;
		for (ServiceInstance instance : applInstances) {
			if (instance.getDeployed()) {
				isDeployed = true;
			} else {
				isDeployed = false;
				break;
			}
		}

		this.processingUnit.setDeployed(isDeployed);
		NotificationService notificationService = ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_PROCESSING_UNIT).getNotificationService();
		if (null != notificationService) {
			notificationService.notify(this, EventType.CHILDREN_CHANGE, null, this.processingUnit);
		}
	}

	@TeaOperation(name = "copyInstance", description = "This method is used to copy the instance", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.COPY_INSTANCE_PERMISSION)
	public String copyInstance(@TeaParam(name = "instanceName", alias = "instanceName") String instanceName,
			@Customize(value = "label:Processing Unit") @TeaParam(name = "processingUnit", alias = "processingUnit") String pu,
			@Customize(value = "label:Host") @TeaParam(name = "hostId") final String hostId,
			@Customize(value = "label:JMX Port") @TeaParam(name = "jmxPort", alias = "jmxPort") int jmxPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:Instance") @TeaParam(name = "instance", alias = "instance") ServiceInstance instance,
			@Customize(value = "label:JMX UserName") @TeaParam(name = "jmxUserName", alias = "jmxUserName") String jmxUserName,
			@Customize(value = "label:JMX Password") @TeaParam(name = "jmxPassword", alias = "jmxPassword") String jmxPassword,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.processingUnit.getApplicationConfig(), version);
			String message = applicationService.copyInstance(instanceName, pu, hostId, deploymentPath, jmxPort,
					instance, loggedInUser, jmxUserName, jmxPassword,beId);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(this.processingUnit.getApplicationConfig());
			applicationService.getLockManager().incrementVersion(this.processingUnit.getApplicationConfig());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Override
	public ProcessingUnit getConfig() {
		return this.processingUnit;
	}

	@Customize(value = "label:Group Kill")
	@TeaOperation(name = "groupKill", description = "Invoke kill operation on passed instances ", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.KILL_INSTANCE_PERMISSION)
	public String groupKill(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.groupKill(instances, processingUnit.getApplicationConfig(), loggedInUser);
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
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			Map<String, String> threadDumps = applicationService.groupThreadDump(instances,
					processingUnit.getApplicationConfig());
			return ManagementUtil.createZip(threadDumps, processingUnit.getPuId());
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
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getInstanceAgents(instances, processingUnit.getApplicationConfig());
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
			String loggedInUser = teaPrincipal.getName();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.applyLogPatterns(processingUnit.getApplicationConfig(), instances,
					logPatternsAndLevel, loggedInUser, false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupLogPatternAndLevel", description = "Load log pattern and level of group", methodType = MethodType.READ)
	public List<GroupDeploymentVariable> loadGroupLogPatternAndLevel(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(this.processingUnit.getApplicationConfig().getName(),
					DeploymentVariableType.LOG_PATTERNS, instancesKey);
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
			return applicationService.getGroupRuntimeLoggerLevels(processingUnit.getApplicationConfig(), instances);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupBEProperties", description = "Load business events properties of group")
	public List<GroupDeploymentVariable> loadGroupBEProperties(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.loadDeploymentVariable(processingUnit.getApplicationConfig().getName(),
					DeploymentVariableType.BE_PROPERTIES, instancesKey);
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

	@Customize(value = "label:Download Log")
	@TeaOperation(name = "downloadLog", methodType = MethodType.READ, description = "")
	public DataSource downloadLog(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			List<String> errors = new ArrayList<String>();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			DataSource logs = applicationService.downloadLogs(instances, processingUnit.getApplicationConfig(), false);
			if (errors.size() == instances.size()) {
				throw new TeaException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INSTANCE_DOWNLOAD_LOG_ERROR_MESSAGE));
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
			List<String> errors = new ArrayList<String>();
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			DataSource logs = applicationService.downloadLogs(instances, processingUnit.getApplicationConfig(), true);
			if (errors.size() == instances.size()) {
				throw new TeaException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INSTANCE_DOWNLOAD_LOG_ERROR_MESSAGE));
			}
			return logs;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}
	@Customize(value = "label:Delete")
	@TeaOperation(name = "groupDelete", description = "Delete selected instances", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DELETE_INSTANCE_PERMISSION)
	public String groupDelete(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			List<String> deletedInstances = new ArrayList<>();
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			applicationService.getLockManager().checkVersion(this.processingUnit.getApplicationConfig(), version);

			String message = applicationService.groupDelete(processingUnit.getApplicationConfig(), instances,
					loggedInUser, deletedInstances);
			if (!deletedInstances.isEmpty()) {
				ObjectProvider<? extends TeaObject> provider = ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE);
				if (null != provider) {
					for (String key : deletedInstances) {
						provider.remove(key);
					}
				}

			}
			applicationService.getLockManager().incrementVersion(this.processingUnit.getApplicationConfig());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:fireTailCommand")
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

	/**
	 * Log error message
	 * 
	 * @param ex
	 *            - Exception
	 * @return TeaException
	 */
	protected TeaException logErrorMessage(Exception ex) {
		ex.printStackTrace();
		String message = ex.getMessage();
		if (ex instanceof Throwable) {
			if (ex instanceof RuntimeException) {
				message = "Unknown Error";
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			} else {
				LOGGER.log(Level.ERROR, ex.getMessage());
			}
		}
		return new TeaException(message);
	}

	/**
	 * @param processingUnit
	 *            the processingUnit to set
	 */
	public void setProcessingUnit(ProcessingUnit processingUnit) {
		this.processingUnit = processingUnit;
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
		for (Host host : this.processingUnit.getApplicationConfig().getHosts()) {
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
}
