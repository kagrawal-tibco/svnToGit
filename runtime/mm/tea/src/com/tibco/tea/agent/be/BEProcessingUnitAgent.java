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
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.exception.StaleEntityVersionException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEAgentManagementService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
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
import com.tibco.tea.agent.be.comparator.BEServiceInstanceComparatorByName;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEProcessingUnitAgentProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;
import com.tibco.tea.agent.events.Event.EventType;
import com.tibco.tea.agent.events.NotificationService;
import com.tibco.tea.agent.support.TeaException;

/**
 * This class is used for processing unit agents configured in CDD
 * 
 * @author dijadhav
 *
 */
public class BEProcessingUnitAgent implements TeaObject {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEProcessingUnitAgent.class);

	@JsonIgnore
	private BEApplicationsManagementService applicationService;
	@JsonIgnore
	private BEAgentManagementService agentService;
	private AgentConfig agentConfig;
	private MessageService messageService;

	public BEProcessingUnitAgent(AgentConfig agentConfig, BEApplicationsManagementService applicationService,
			BEAgentManagementService agentManagementService) {
		this.agentConfig = agentConfig;
		this.applicationService = applicationService;
		this.agentService = agentManagementService;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.api.BaseTeaObject#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Agent class in processing unit.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.api.BaseTeaObject#getName()
	 */
	@Override
	public String getName() {
		return agentConfig.getAgentName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.api.TeaObject#getKey()
	 */
	@Override
	public String getKey() {
		return agentConfig.getTeaObjectKey();
	}

	public void registerWithObjectProvider() {
		BEProcessingUnitAgentProvider processingUnitAgentProvider = (BEProcessingUnitAgentProvider) ObjectCacheProvider
				.getInstance().getProvider(Constants.BE_PROCESSING_UNIT_AGENT);
		if (null == processingUnitAgentProvider.getInstance(this.getKey()))
			processingUnitAgentProvider.add(this.getKey(), this);

	}

	@TeaOperation(name = "getSummary", description = "Get Summary of processing unit agent ", methodType = MethodType.READ)
	public Summary getSummary() {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		try {
			return agentService.getApplicationAgentSummary(this.agentConfig, application);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaReference(name = "ServiceInstances")
	public Collection<BEServiceInstance> getServiceInstances() {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		List<BEServiceInstance> agentInstances = new ArrayList<BEServiceInstance>();
		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			for (Host host : application.getHosts()) {
				for (ServiceInstance instance : host.getInstances()) {
					BEServiceInstance serviceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
							.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(instance.getKey());
					if (null == serviceInstance) {
						serviceInstance = new BEServiceInstance(instance);
						serviceInstance.registerWithObjectProvider();
					}

					if (null != serviceInstance) {

						for (BEAgent beAgent : serviceInstance.getAgents()) {
							if (null != beAgent && beAgent.getName().equals(getName())) {
								instance.setUpTime(BEMMServiceProviderManager.getInstance().getBEMBeanService()
										.getProcessStartTime(instance));
								serviceInstance.setInstance(instance);
								agentInstances.add(serviceInstance);
								break;
							}
						}
					}

				}
			}
			setDeployed(application, false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(agentInstances, new BEServiceInstanceComparatorByName());
		return agentInstances;
	}

	@Customize(value = "label:Deploy")
	@TeaOperation(name = "deploy", description = "Deploy new BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.DEPLOY_INSTANCE_PERMISSION)
	public String deploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		try {
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.deploy(application, instances, loggedInUser);
			setDeployed(application, true);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:UnDeploy")
	@TeaOperation(name = "undeploy", description = "UnDeploy Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.UNDEPLOY_INSTANCE_PERMISSION)
	public String undeploy(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		try {
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.undeploy(application, instances, loggedInUser);
			setDeployed(application, true);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:HotDeploy")
	@TeaOperation(name = "hotdeploy", description = "HotDeploy BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.HOT_DEPLOY_PERMISSION)
	public String hotdeploy(
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			@TeaParam(name = "instances", alias = "instances") List<String> instances, TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		try {
			String loggedInUser = teaPrincipal.getName();
			String message = applicationService.hotdeploy(application, earpath, loggedInUser, instances);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Start")
	@TeaOperation(name = "start", description = "Start Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.START_PU_INSTANCE_PERMISSION)
	public String start(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			String loggedInUser = teaPrincipal.getName();
			Application application = applicationService.getApplicationByName(parts[0].trim());
			return applicationService.start(application, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Stop")
	@TeaOperation(name = "stop", description = "Stop Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(value = Permission.STOP_PU_INSTANCE_PERMISSION)
	public String stop(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			String loggedInUser = teaPrincipal.getName();
			Application application = applicationService.getApplicationByName(parts[0].trim());
			return applicationService.stop(application, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupGlobalVariable", description = "Load global variables of group", methodType = MethodType.UPDATE)
	public List<GroupDeploymentVariable> loadGroupGlobalVariable(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			return applicationService.loadDeploymentVariable(parts[0], DeploymentVariableType.GLOBAL_VARIABLES,
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			return applicationService.loadDeploymentVariable(parts[0], DeploymentVariableType.SYSTEM_VARIABLES,
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			return applicationService.loadDeploymentVariable(parts[0], DeploymentVariableType.JVM_PROPERTIES,
					instancesKey);
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			String loggedInUser = teaPrincipal.getName();
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.SYSTEM_VARIABLES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);

			String message = applicationService.storeGroupDeploymentVariable(parts[0].trim(),
					DeploymentVariableType.GLOBAL_VARIABLES, groupDeploymentVariables, loggedInUser);

			setDeployed(application, true);
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		if (null == application) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			String loggedInUser = teaPrincipal.getName();
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.SYSTEM_VARIABLES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = applicationService.storeGroupDeploymentVariable(parts[0].trim(),
					DeploymentVariableType.SYSTEM_VARIABLES, groupDeploymentVariables, loggedInUser);
			setDeployed(application, true);
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		if (null == application) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		try {
			String loggedInUser = teaPrincipal.getName();
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.JVM_PROPERTIES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = applicationService.storeGroupDeploymentVariable(parts[0].trim(),
					DeploymentVariableType.JVM_PROPERTIES, groupDeploymentVariables, loggedInUser);
			setDeployed(application, true);
			incrementDeploymentVariablesVersion(deploymentVariablesList);
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
				String[] parts = getKey().split("/");
				if (null == parts || parts.length < 3) {
					throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
				}
				Application application = applicationService.getApplicationByName(parts[0].trim());
				if (null == application) {
					throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
				}
				Map<String, OperationResult> returnDataMap = applicationService.invokeGroupOperation(entityName,
						methodGroup, methodName, params, instances, application, loggedInUser, agentConfig);

				return returnDataMap;
			}
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return null;
	}

	@TeaOperation(name = "getProcessingUnitAgent", description = "Get Processing Unit Agent Info", methodType = MethodType.READ)
	public AgentConfig getProcessingUnitAgent() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return agentConfig;
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	/**
	 * @param application
	 */
	private void setDeployed(Application application, boolean isSendNotification) {

		List<ServiceInstance> applInstances = new ArrayList<>();

		for (Host host : application.getHosts()) {
			if (null != host) {
				for (ServiceInstance instance : host.getInstances()) {
					if (null != instance) {
						List<Agent> agents = instance.getAgents();
						if (null != agents) {
							for (Agent agent : agents) {
								if (null != agent && agent.getAgentName().equals(this.agentConfig.getAgentName())
										&& agent.getAgentType().equals(this.agentConfig.getAgentType())) {
									applInstances.add(instance);
								}
							}
						}
					}
				}
			}
		}
		boolean isAllDeployed = false;
		for (ServiceInstance instance : applInstances) {
			if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(instance.getDeploymentStatus())
					|| BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus().equals(instance.getDeploymentStatus())) {
				isAllDeployed = false;
				break;
			} else {
				isAllDeployed = true;
			}
		}
		if (!isAllDeployed) {
			agentConfig.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
			agentConfig.setDeploymentDescription("Some instances are updated.");
		} else {
			agentConfig.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
			agentConfig.setDeploymentDescription("All instances are deployed");
		}
		boolean isDeployed = false;
		for (ServiceInstance instance : applInstances) {
			if (instance.getDeployed()) {
				isDeployed = false;
				break;
			} else {
				isDeployed = true;
			}
		}
		agentConfig.setDeployed(isDeployed);

		if (isSendNotification) {
			NotificationService notificationService = ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_PROCESSING_UNIT_AGENT).getNotificationService();
			if (null != notificationService) {
				notificationService.notify(this, EventType.CHILDREN_CHANGE, null, this.agentConfig);
			}
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
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			String loggedInUser = teaPrincipal.getName();
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

			String message = applicationService.copyInstance(instanceName, processingUnit, hostId, deploymentPath,
					jmxPort, instance, loggedInUser, jmxUserName, jmxPassword,beId);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);
			applicationService.getLockManager().incrementVersion(application);
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			String loggedInUser = teaPrincipal.getName();
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			String loggedInUser = teaPrincipal.getName();
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}

			Map<String, String> threadDumps = applicationService.groupThreadDump(instances, application);
			return ManagementUtil.createZip(threadDumps, agentConfig.getAgentName());
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			String loggedInUser = teaPrincipal.getName();
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}

			return applicationService.applyLogPatterns(application, instances, logPatternsAndLevel, loggedInUser,
					false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupLogPatternAndLevel", description = "Load log pattern and level of group", methodType = MethodType.READ)
	public List<GroupDeploymentVariable> loadGroupLogPatternAndLevel(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			return applicationService.loadDeploymentVariable(application.getName(), DeploymentVariableType.LOG_PATTERNS,
					instancesKey);
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			String loggedInUser = teaPrincipal.getName();
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.LOG_PATTERNS, groupLogPatternsAndLevel);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupLogPatternsAndLevel);

			String message = applicationService.storeGroupDeploymentVariable(application.getName(),
					DeploymentVariableType.LOG_PATTERNS, groupLogPatternsAndLevel, loggedInUser);
			setDeployed(application, true);
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}

			return applicationService.getGroupRuntimeLoggerLevels(application, instances);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "loadGroupBEProperties", description = "Load business events properties of group")
	public List<GroupDeploymentVariable> loadGroupBEProperties(
			@TeaParam(name = "instancesKey", alias = "instancesKey") List<String> instancesKey) {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			return applicationService.loadDeploymentVariable(application.getName(),
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			String loggedInUser = teaPrincipal.getName();

			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			List<Versionable> deploymentVariablesList = getAffectedDeploymentVariables(
					DeploymentVariableType.BE_PROPERTIES, groupDeploymentVariables);
			checkDeploymentVariablesVersion(deploymentVariablesList, groupDeploymentVariables);
			String message = applicationService.storeGroupDeploymentVariable(application.getName(),
					DeploymentVariableType.BE_PROPERTIES, groupDeploymentVariables, loggedInUser);
			setDeployed(application, true);
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			List<String> errors = new ArrayList<String>();
			DataSource logs = applicationService.downloadLogs(instances, application, false);
			if (errors.size() == instances.size()) {
				throw new TeaException(messageService.getMessage(MessageKey.INSTANCE_DOWNLOAD_LOG_ERROR_MESSAGE));
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
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			List<String> errors = new ArrayList<String>();
			DataSource logs = applicationService.downloadLogs(instances, application, true);
			if (errors.size() == instances.size()) {
				throw new TeaException(messageService.getMessage(MessageKey.INSTANCE_DOWNLOAD_LOG_ERROR_MESSAGE));
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
		applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			String loggedInUser = teaPrincipal.getName();
			String[] parts = getKey().split("/");
			if (null == parts || parts.length < 3) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			Application application = applicationService.getApplicationByName(parts[0].trim());
			if (null == application) {
				throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
			}
			List<String> deletedInstances = new ArrayList<>();
			applicationService.getLockManager().checkVersion(application, version);

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
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
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
	 * @param agentConfig
	 *            the agentConfig to set
	 */
	public void setAgentConfig(AgentConfig agentConfig) {
		this.agentConfig = agentConfig;
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
		String[] parts = getKey().split("/");
		if (null == parts || parts.length < 3) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
		Application application = applicationService.getApplicationByName(parts[0].trim());
		if (null == application) {
			throw new TeaException(messageService.getMessage(MessageKey.INVALID_OBJECT_KEY_MESSAGE));
		}
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
}
