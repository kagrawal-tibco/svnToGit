package com.tibco.tea.agent.be;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.exception.StaleEntityVersionException;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationHostsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Host;
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
import com.tibco.tea.agent.api.WithStatus;
import com.tibco.tea.agent.be.comparator.BEServiceInstanceComparatorByName;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEApplicationHostProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;
import com.tibco.tea.agent.events.Event.EventType;
import com.tibco.tea.agent.events.NotificationService;
import com.tibco.tea.agent.internal.types.ClientType;
import com.tibco.tea.agent.support.TeaException;
import com.tibco.tea.agent.types.AgentObjectStatus;

/**
 * This class is used to represent host of the Business Events application.
 * 
 * @author dijadhav
 *
 */
public class BEApplicationHost implements TeaObject, WithStatus, WithConfig<Host> {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEApplicationHost.class);

	@JsonIgnore
	private BEApplicationHostsManagementService hostService;
	private Host host;

	/**
	 * Parameterized Constructor.
	 * 
	 * @param name
	 *            - Name of the application.
	 * @param key
	 *            - Key of the application.
	 * @param application
	 *            - Application which is deployed on the this host.
	 */
	public BEApplicationHost(Host host) {
		this.host = host;
	}

	@Override
	public String getDescription() {
		return "Business Events Application Host";
	}

	@Override
	public String getName() {
		return host.getHostName();
	}

	@Override
	public String getKey() {
		return host.getKey();
	}

	@Override
	public AgentObjectStatus getStatus() {
		AgentObjectStatus status = new AgentObjectStatus();
		status.setState(host.getStatus());
		return status;
	}

	/**
	 * @return the host
	 */
	@Override
	public Host getConfig() {
		return this.host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void sethost(Host host) {
		this.host = host;
	}

	/**
	 * @return the hostService
	 */
	@JsonIgnore
	public BEApplicationHostsManagementService getHostService() {
		return hostService;
	}

	/**
	 * @param hostService
	 *            the hostService to set
	 */
	@JsonIgnore
	public void setHostService(BEApplicationHostsManagementService hostService) {
		this.hostService = hostService;
	}

	public void registerWithObjectProvider() {
		BEApplicationHostProvider applicationHostProvider = (BEApplicationHostProvider) ObjectCacheProvider
				.getInstance().getProvider(Constants.BE_APPLICATION_HOST);
		if (null == applicationHostProvider.getInstance(this.getKey())) {
			applicationHostProvider.add(this.getKey(), this);
		}
	}

	@TeaReference(name = "ServiceInstances")
	public Collection<BEServiceInstance> getAllServiceIntances() {
		hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		List<BEServiceInstance> instances = new ArrayList<BEServiceInstance>();
		try {
			for (ServiceInstance serviceInstance : host.getInstances()) {
				BEServiceInstance beServiceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(serviceInstance.getKey());
				serviceInstance.setUpTime(BEMMServiceProviderManager.getInstance().getBEMBeanService()
						.getProcessStartTime(serviceInstance));
				beServiceInstance.setInstance(serviceInstance);
				instances.add(beServiceInstance);
			}
			sendDeploymentNotification(false);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(instances, new BEServiceInstanceComparatorByName());
		return instances;
	}

	@Customize(value = "label:Deploy")
	@TeaOperation(name = "deploy", description = "Deploy new BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DEPLOY_INSTANCE_PERMISSION)
	public String deploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = hostService.deploy(this.getConfig(), instances, loggedInUser);
			sendDeploymentNotification(true);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:UnDeploy")
	@TeaOperation(name = "undeploy", description = "UnDeploy Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UNDEPLOY_INSTANCE_PERMISSION)
	public String undeploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = hostService.undeploy(this.getConfig(), instances, loggedInUser);
			sendDeploymentNotification(true);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:HotDeploy")
	@TeaOperation(name = "hotdeploy", description = "HotDeploy BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.HOT_DEPLOY_PERMISSION)
	public String hotdeploy(
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			@TeaParam(name = "instances", alias = "instances") List<String> instances, TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String message = hostService.hotdeploy(this.getConfig(), earpath, instances, loggedInUser);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Start")
	@TeaOperation(name = "start", description = "Start Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.START_PU_INSTANCE_PERMISSION)
	public String start(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.start(this.getConfig(), instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Stop")
	@TeaOperation(name = "stop", description = "Stop Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.STOP_PU_INSTANCE_PERMISSION)
	public String stop(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.stop(this.getConfig(), instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getAppSummary", description = "gets an application summary", methodType = MethodType.READ, hideFromClients = ClientType.WEB_UI)
	public Summary getAppSummary() {
		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.getHostSummary(host);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getHostInfo", description = "Get the application Host info", methodType = MethodType.READ)
	public Host getHostInfo() {
		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return host;
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupGlobalVariable", description = "Load global variables of group", methodType = MethodType.UPDATE)
	public List<GroupDeploymentVariable> loadGroupGlobalVariable(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.loadDeploymentVariable(this.host, DeploymentVariableType.GLOBAL_VARIABLES, instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupSystemVariable", description = "Load system variables of group")
	public List<GroupDeploymentVariable> loadGroupSystemVariable(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.loadDeploymentVariable(this.host, DeploymentVariableType.SYSTEM_VARIABLES, instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupJVMProperties", description = "Load jvm properties of group")
	public List<GroupDeploymentVariable> loadGroupJVMProperties(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.loadDeploymentVariable(this.host, DeploymentVariableType.JVM_PROPERTIES, instancesKey);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupGlobalVariables", description = "Save global variables of group", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_GV_VAR_PERMISSION)
	public String saveGroupGlobalVariables(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.GLOBAL_VARIABLES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = hostService.saveDeploymentVariable(this.host, DeploymentVariableType.GLOBAL_VARIABLES,
					groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification(true);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupSystemVariables", description = "Save system variables of group")
	@TeaRequires(Permission.UPDATE_SYSTEM_PROPS_PERMISSION)
	public String saveGroupSystemVariables(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.SYSTEM_VARIABLES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = hostService.saveDeploymentVariable(this.host, DeploymentVariableType.SYSTEM_VARIABLES,
					groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification(true);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupJVMproperties", description = "Save JVM  properties of group")
	@TeaRequires(Permission.UPDATE_JVM_PROPS_PERMISSION)
	public String saveGroupJVMproperties(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.JVM_PROPERTIES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = hostService.saveDeploymentVariable(this.host, DeploymentVariableType.JVM_PROPERTIES,
					groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification(true);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupLogPatternAndLevel", description = "Load log pattern and level of group", methodType = MethodType.READ)
	public List<GroupDeploymentVariable> loadGroupLogPatternAndLevel(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {

			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.loadDeploymentVariable(this.host, DeploymentVariableType.LOG_PATTERNS, instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupLogPatternAndLevel", description = "Save log pattern and level of group", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_LOG_LEVEL_PERMISSION)
	public String saveGroupLogPatternAndLevel(
			@TeaParam(name = "groupLogPatternsAndLevel", alias = "groupLogPatternsAndLevel") List<GroupDeploymentVariable> groupLogPatternsAndLevel,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.LOG_PATTERNS, groupLogPatternsAndLevel);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupLogPatternsAndLevel);
			String message = hostService.saveDeploymentVariable(this.host, DeploymentVariableType.LOG_PATTERNS,
					groupLogPatternsAndLevel, loggedInUser);
			sendDeploymentNotification(true);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupBEProperties", description = "Load business events properties of group")
	public List<GroupDeploymentVariable> loadGroupBEProperties(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.loadDeploymentVariable(host, DeploymentVariableType.BE_PROPERTIES, instancesKey);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGroupBEProperties", description = "Save business events properties of group")
	@TeaRequires(Permission.UPDATE_BE_PROPS_PERMISSION)
	public String saveGroupBEProperties(
			@TeaParam(name = "groupDeploymentVariables", alias = "groupDeploymentVariables") List<GroupDeploymentVariable> groupDeploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.BE_PROPERTIES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = hostService.saveDeploymentVariable(host, DeploymentVariableType.BE_PROPERTIES,
					groupDeploymentVariables, loggedInUser);
			sendDeploymentNotification(true);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
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
		hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			if (groupOperation == true) {
				String loggedInUser = teaPrincipal.getName();
				Map<String, OperationResult> returnDataMap;
				try {
					returnDataMap = hostService.invokeGroupOperation(entityName, methodGroup, methodName, params,
							instances, host, loggedInUser);
				} catch (Exception ex) {
					throw logErrorMessage(ex);
				}
				return returnDataMap;
			}
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return null;
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
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			hostService.getLockManager().checkVersion(this.host.getApplication(), version);
			String message = hostService.copyInstance(instanceName, processingUnit, hostId, deploymentPath, jmxPort,
					instance, loggedInUser, jmxUserName, jmxPassword,beId);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(this.host.getApplication());
			hostService.getLockManager().incrementVersion(this.host.getApplication());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:groupKill")
	@TeaOperation(name = "groupKill", description = "Invoke kill operation on passed instances ", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.KILL_INSTANCE_PERMISSION)
	public String groupKill(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.groupKill(instances, host, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Group ThreadDump")
	@TeaOperation(name = "groupThreadDump", description = "Get thread dumps of passed instances", methodType = MethodType.READ)
	public DataSource groupThreadDump(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			Map<String, String> threaddumps = hostService.groupThreadDump(instances, host);
			return ManagementUtil.createZip(threaddumps, host.getHostName());
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get InstanceAgents")
	@TeaOperation(name = "getInstanceAgents", description = "Get instance agents", methodType = MethodType.READ)
	public Set<String> getInstanceAgents(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.getInstanceAgents(instances, host);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "applyLogPattern", description = "Apply Log pattern and log levels at runtime", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_LOG_LEVEL_PERMISSION)
	public String applyLogPattern(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			@TeaParam(name = "logPatternsAndLevel", alias = "logPatternsAndLevel") Map<String, String> logPatternsAndLevel,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.applyLogPatterns(host, instances, logPatternsAndLevel, loggedInUser, false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
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
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.getGroupRuntimeLoggerLevels(host, instances);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Download Log")
	@TeaOperation(name = "downloadLog", methodType = MethodType.READ, description = "")
	public DataSource downloadLog(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			DataSource logs = hostService.downloadLogs(instances, host, false);
			return logs;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Download AS Log")
	@TeaOperation(name = "downloadASLog", methodType = MethodType.READ, description = "")
	public DataSource downloadASLog(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) throws Exception {

		String loggedInUser = teaPrincipal.getName();

		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			DataSource logs = hostService.downloadLogs(instances, host, true);
			return logs;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	/**
	 * @param host
	 */
	private void sendDeploymentNotification(boolean sendNotification) {

		// Host deployment status
		boolean isAllHostInstancesDeployed = false;
		for (ServiceInstance instance : host.getInstances()) {
			if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(instance.getDeploymentStatus())
					|| BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus().equals(instance.getDeploymentStatus())) {
				isAllHostInstancesDeployed = false;
				break;
			} else {
				isAllHostInstancesDeployed = true;
			}
		}

		if (isAllHostInstancesDeployed) {
			host.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
		} else {
			host.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
		}

		// Host deployment

		boolean isHostDeployed = false;
		for (ServiceInstance instance : host.getInstances()) {
			if (instance.getDeployed()) {
				isHostDeployed = true;
			} else {
				isHostDeployed = false;
				break;
			}
		}
		host.setDeployed(isHostDeployed);
		if (sendNotification) {
			NotificationService notificationService = ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_APPLICATION_HOST).getNotificationService();
			if (null != notificationService) {
				notificationService.notify(this, EventType.CHILDREN_CHANGE, null, this.host);
			}
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
				throw logErrorMessage(ex);
			} else {
				LOGGER.log(Level.ERROR, ex.getMessage());
			}
		}
		return new TeaException(message);
	}

	@Customize(value = "label:Delete")
	@TeaOperation(name = "groupDelete", description = "Delete selected instances", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DELETE_INSTANCE_PERMISSION)
	public String groupDelete(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			hostService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			hostService.getLockManager().checkVersion(this.host.getApplication(), version);
			List<String> deletedInstances = new ArrayList<>();
			String message = hostService.groupDelete(host, instances, loggedInUser, deletedInstances);
			if (!deletedInstances.isEmpty()) {
				ObjectProvider<? extends TeaObject> provider = ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE);
				if (null != provider) {
					for (String key : deletedInstances) {
						provider.remove(key);
					}
				}

			}
			hostService.getLockManager().incrementVersion(this.host.getApplication());
			return message;

		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:fireTailCommand")
	@TeaOperation(name = "fireTailCommand", description = "", methodType = MethodType.READ)
	public String fireTailCommand(
			@Customize(value = "label:NumberofLines") @TeaParam(name = "numberofLines", alias = "numberofLines") String numberofLines,
			@Customize(value = "label:isASLog") @TeaParam(name = "isASLog", alias = "isASLog") boolean isASLog,
			@Customize(value = "label:Instance") @TeaParam(name = "instance", alias = "instance") ServiceInstance instance) {

		try {
			hostService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return hostService.fireTailCommand(numberofLines, isASLog, instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			hostService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
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

		for (ServiceInstance serviceInstance : host.getInstances()) {
			if (null != serviceInstance && serviceInstanceKeys.contains(serviceInstance.getKey())) {
				serviceInstances.add(serviceInstance);
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
			hostService.getLockManager().checkVersions(deploymentVariablesList,
					groupDeploymentVariables.get(0).getVariablesVersion());
		}
	}

	private void incrementDeploymentVariablesVersion(List<Versionable> deploymentVariablesList) {
		for (Versionable versionable : deploymentVariablesList)
			hostService.getLockManager().incrementVersion(versionable);
	}

}
