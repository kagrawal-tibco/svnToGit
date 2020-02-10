
package com.tibco.tea.agent.be;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.audit.AuditRecords;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.model.ThreadDetail;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.monitoring.metric.view.ViewData;
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
import com.tibco.tea.agent.be.comparator.BEAgentComparatorByName;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEServiceInstanceProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.internal.types.ClientType;
import com.tibco.tea.agent.support.TeaException;
import com.tibco.tea.agent.types.AgentObjectStatus;

/**
 * This class is used create the Tea object for Service instance.
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstance implements TeaObject, WithStatus, WithConfig<ServiceInstance> {
	@JsonIgnore
	private BEServiceInstancesManagementService instanceService;

	private ServiceInstance instance;
	/**
	 * Logger instance
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEServiceInstance.class);

	/**
	 * Parameterized Constructor to set name and key.
	 * 
	 * @param name
	 *            - Name of the this service instance
	 * @param key
	 *            - key of the service instance.
	 * @param string
	 */
	public BEServiceInstance(ServiceInstance instance) {
		this.instance = instance;
	}

	/*
	 * (non-Javadoc) ik
	 * 
	 * @see com.tibco.tea.agent.api.BaseTeaObject#getDescription()
	 */
	@Override
	public String getDescription() {
		return " Service Instance";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.api.BaseTeaObject#getName()
	 */
	@Override
	public String getName() {
		return instance.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.api.TeaObject#getKey()
	 */
	@Override
	public String getKey() {
		return instance.getKey();
	}

	/**
	 * @return the instanceService
	 */
	@JsonIgnore
	public BEServiceInstancesManagementService getInstanceService() {
		return instanceService;
	}

	/**
	 * @param instanceService
	 *            the instanceService to set
	 */
	@JsonIgnore
	public void setInstanceService(BEServiceInstancesManagementService instanceService) {
		this.instanceService = instanceService;
	}

	@TeaReference(name = "Agent")
	@JsonIgnore(value = true)
	public Collection<BEAgent> getAgents() {
		List<BEAgent> beAgents = new ArrayList<BEAgent>();
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			for (Agent agent : this.instance.getAgents()) {
				BEAgent beAgent = (BEAgent) ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT)
						.getInstance(agent.getKey());
				if (null == beAgent) {
					beAgent = new BEAgent(agent);
					beAgent.registerWithObjectProvider();
				}

				beAgent.setAgent(agent);
				beAgents.add(beAgent);
			}
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		Collections.sort(beAgents, new BEAgentComparatorByName());
		return beAgents;
	}

	@Override
	public AgentObjectStatus getStatus() {
		AgentObjectStatus status = new AgentObjectStatus();
		status.setState(instance.getStatus());
		return status;
	}

	@Customize(value = "label:Deploy")
	@TeaOperation(name = "deploy", description = "Deploy new BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DEPLOY_INSTANCE_PERMISSION)
	public String deploy(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.deploy(this.getInstance(), loggedInUser);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:UnDeploy")
	@TeaOperation(name = "undeploy", description = "UnDeploy Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UNDEPLOY_INSTANCE_PERMISSION)
	public String undeploy(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.undeploy(this.getInstance(), loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:HotDeploy")
	@TeaOperation(name = "hotdeploy", description = "Hot deploy Instance", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.HOT_DEPLOY_PERMISSION)
	public String hotdeploy(
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.hotdeploy(this.getInstance(), earpath, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Start;if:state=Stopped")
	@TeaOperation(name = "start", description = "Start Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.START_PU_INSTANCE_PERMISSION)
	public String start(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.start(this.getInstance(), loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Stop;if:state=Running")
	@TeaOperation(name = "stop", description = "Stop Existing BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.STOP_PU_INSTANCE_PERMISSION)
	public String stop(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.stop(this.getInstance(), loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Edit")
	@TeaOperation(name = "edit", description = "Edit the Service instance", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_INSTANCE_PERMISSION)
	public String edit(
			@Customize(value = "label:Processing Unit") @TeaParam(name = "processingUnit", alias = "processingUnit") String processingUnit,
			@Customize(value = "label:Host") @TeaParam(name = "hostId") final String hostId,
			@Customize(value = "label:JMX Port") @TeaParam(name = "jmxPort", alias = "jmxPort") int jmxPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:JMX UserName") @TeaParam(name = "jmxUserName", alias = "jmxUserName") String jmxUserName,
			@Customize(value = "label:JMX Password") @TeaParam(name = "jmxPassword", alias = "jmxPassword") String jmxPassword,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance, version);
			String loggedInUser = teaPrincipal.getName();
			List<String> agentKeys = new ArrayList<String>();

			if (!this.getInstance().getPuId().trim().equals(processingUnit.trim())) {
				for (Agent agent : this.getInstance().getAgents()) {
					agentKeys.add(agent.getKey());
				}
			}
			String message = instanceService.editServiceInstance(this.getInstance(), processingUnit, hostId, jmxPort,
					deploymentPath, loggedInUser, jmxUserName, jmxPassword, beId);
			for (String agentKey : agentKeys) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT).getInstance(agentKey);
			}
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(this.getInstance().getHost().getApplication());
			instanceService.getLockManager().incrementVersion(this.instance);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Delete")
	@TeaOperation(name = "delete", description = "Delete the Service instance", methodType = MethodType.DELETE)
	@TeaRequires(Permission.DELETE_INSTANCE_PERMISSION)
	public String delete(
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getHost().getApplication(), version);
			String message = instanceService.deleteServiceInstance(this.getInstance().getHost().getApplication(),
					this.getName());
			ObjectProvider<? extends TeaObject> provider = ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_SERVICE_INSTANCE);
			if (null != provider)
				provider.remove(this.getKey());
			instanceService.getLockManager().incrementVersion(this.instance.getHost().getApplication());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getGlobalVariables", description = "Service settable global variables", methodType = MethodType.READ)
	public DeploymentVariables getGlobalVariables() throws Exception {

		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instance.getGlobalVariables();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getSystemVariables", description = "System variables", methodType = MethodType.READ)
	public DeploymentVariables getSystemVariables() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instance.getSystemVariables();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getBEProperties", description = "Get BE Propeties", methodType = MethodType.READ)
	public DeploymentVariables getBEProperties() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instance.getBEProperties();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveGlobalVariables", description = "Save Service settable global variables", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_GV_VAR_PERMISSION)
	public String saveGlobalVariables(
			@TeaParam(name = "globalVariables", alias = "globalVariables") final DeploymentVariables globalVariables,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getGlobalVariables(),
					globalVariables.getVersion());
			String loggedInUser = teaPrincipal.getName();
			String message = instanceService.storeDeploymentVariables(this.instance, globalVariables, loggedInUser,
					false);
			instanceService.getLockManager().incrementVersion(this.instance.getBEProperties());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveBEProperties", description = "Save BusinessEvents Propertues", methodType = MethodType.UPDATE, hideFromClients = ClientType.WEB_UI)
	@TeaRequires(Permission.UPDATE_BE_PROPS_PERMISSION)
	public String saveBEProperties(
			@TeaParam(name = "beprops", alias = "beprops") final DeploymentVariables deploymentVariables,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getSystemVariables(),
					deploymentVariables.getVersion());
			String loggedInUser = teaPrincipal.getName();
			String message = instanceService.storeDeploymentVariables(this.instance, deploymentVariables, loggedInUser,
					false);
			instanceService.getLockManager().incrementVersion(this.instance.getBEProperties());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveSystemVariables", description = "Save system variables", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_SYSTEM_PROPS_PERMISSION)
	public String saveSystemVariables(
			@TeaParam(name = "systemVariables", alias = "systemVariables") final DeploymentVariables systemVariables,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getSystemVariables(),
					systemVariables.getVersion());
			String loggedInUser = teaPrincipal.getName();
			String message = instanceService.storeDeploymentVariables(this.instance, systemVariables, loggedInUser,
					false);
			instanceService.getLockManager().incrementVersion(this.instance.getSystemVariables());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getInstanceInfo", description = "Get the information about service instance", methodType = MethodType.READ)
	public ServiceInstance getInstanceInfo() {
		ServiceInstance instanceInfo = getInstance();
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			long UpTime = BEMMServiceProviderManager.getInstance().getBEMBeanService()
					.getProcessStartTime(getInstance());
			instanceInfo.setUpTime(UpTime);
		} catch (ObjectCreationException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return instanceInfo;
	}

	public void registerWithObjectProvider() {
		BEServiceInstanceProvider serviceInstanceProvider = (BEServiceInstanceProvider) ObjectCacheProvider
				.getInstance().getProvider(Constants.BE_SERVICE_INSTANCE);
		if (null == serviceInstanceProvider.getInstance(this.getInstance().getKey())) {
			serviceInstanceProvider.add(this.getKey(), this);
		}

	}

	public ServiceInstance getInstance() {
		return instance;
	}

	@Customize(value = "label:Get Memory Usage")
	@TeaOperation(name = "getMemoryUsage", description = "", methodType = MethodType.READ)
	public List<ProcessMemoryUsageImpl> getMemoryUsage() {
		try {
			return instanceService.getMemoryUsage(instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Loggers")
	@TeaOperation(name = "getLoggers", description = "", methodType = MethodType.READ)
	public List<LogDetailImpl> getLoggers() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.getRuntimeLoggerLevels(this.getInstance());
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Log Levels")
	@TeaOperation(name = "getLogLevels", description = "", methodType = MethodType.READ)
	public Map<String, String> getLogLevels() {
		return ManagementUtil.getLogLevels();
	}

	@TeaOperation(name = "getJVMProperties", description = "System variables", methodType = MethodType.READ)
	public DeploymentVariables getJVMProperties() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instance.getJVMProperties();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getDepVarType", description = "getDepVarType")
	public DeploymentVariableType getDepVarType(
			@TeaParam(name = "depVarType", alias = "depVarType") final String depVarType, TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return DeploymentVariableType.valueOf(depVarType);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveJVMProperties", description = "Save JVM Props", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_JVM_PROPS_PERMISSION)
	public String saveJVMProperties(
			@TeaParam(name = "jvmProperties", alias = "jvmProperties") final DeploymentVariables jvmProperties,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getJVMProperties(), jvmProperties.getVersion());
			String loggedInUser = teaPrincipal.getName();
			String message = instanceService.storeDeploymentVariables(this.instance, jvmProperties, loggedInUser,
					false);
			instanceService.getLockManager().incrementVersion(this.instance.getJVMProperties());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "saveLogPattern", description = "Save log pattern and level", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_LOG_LEVEL_PERMISSION)
	public String saveLogPattern(
			@TeaParam(name = "logPatternAndLevel", alias = "logPatternAndLevel") DeploymentVariables logPatternAndLevel,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getLoggerPatternAndLogLevel(),
					logPatternAndLevel.getVersion());
			String loggedInUser = teaPrincipal.getName();
			String message = instanceService.storeDeploymentVariables(this.instance, logPatternAndLevel, loggedInUser,
					false);
			instanceService.getLockManager().incrementVersion(this.instance.getLoggerPatternAndLogLevel());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getLogPatternsAndLogLevel", description = "Get log pattern and level", methodType = MethodType.READ)
	public DeploymentVariables getLogPatternsAndLogLevel(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instance.getLoggerPatternAndLogLevel();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "applyLogPattern", description = "Apply Log pattern and log levels at runtime", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_LOG_LEVEL_PERMISSION)
	public String applyLogPattern(
			@TeaParam(name = "logPatternsAndLevel", alias = "logPatternsAndLevel") Map<String, String> logPatternsAndLevel,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.applyLogPatterns(this.instance, logPatternsAndLevel, loggedInUser, false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Invoke")
	@TeaOperation(name = "invoke", description = "Invoke the passed method on given entity ", methodType = MethodType.READ, hideFromClients = ClientType.PYTHON)
	public OperationResult invoke(@TeaParam(name = "entityName", alias = "entityName") String entityName,
			@TeaParam(name = "methodGroup", alias = "methodGroup") String methodGroup,
			@TeaParam(name = "methodName", alias = "methodName") String methodName,
			@TeaParam(name = "params", alias = "params", defaultValue = "null") Map<String, String> params) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			Host host = instance.getHost();
			if (null != host) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();
				String hostIp = host.getHostIp();
				int jmxPort = instance.getJmxPort();
				OperationResult result = instanceService.invoke(entityName, methodGroup, methodName, params, username,
						password, hostIp, jmxPort, -1, this.instance);
				return result;
			}

		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return null;
	}

	@Customize(value = "label:Get GC Details")
	@TeaOperation(name = "getGCDetails", description = "Get the garbage collection details of  running service instance", methodType = MethodType.READ, hideFromClients = ClientType.PYTHON)
	public OperationResult getGCDetails() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.getGarbageCollectionDetails(instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Thread Details")
	@TeaOperation(name = "getThreadDetails", description = "Get the running and deadlocked thread details in running service instance", methodType = MethodType.READ)
	public ThreadDetail getThreadDetails() {
		try {

			/*
			 * if(threadcountChartQuery==null)
			 * threadcountChartQuery=BEMMServiceProviderManager.getInstance()
			 * .getBEApplicationMonitoringService()
			 * .initQuery(this.getInstance(), BETeaAgentProps.BEMM_DIM_LEVEL_PU,
			 * BETeaAgentProps.BEMM_RAW_DATA_HIERARCHY,
			 * BETeaAgentProps.BEMM_MEASUREMENT_RAW_THREAD_COUNT);
			 * 
			 * return BEMMServiceProviderManager.getInstance()
			 * .getBEApplicationMonitoringService()
			 * .getChartData(threadcountChartQuery,
			 * BETeaAgentProps.BEMM_THREAD_COUNT_CHART_METRICS);
			 */
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.getThreadDetails(instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Memory Pools")
	@TeaOperation(name = "getMemeoryPools", description = "Get the MomoryPools", methodType = MethodType.READ)
	public List<String> getMemeoryPools() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.getMemoryPools();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Memory by Pool Name")
	@TeaOperation(name = "getMemeoryByPoolName", description = "Get the memeory details by pool name", methodType = MethodType.READ)
	public MemoryUsage getMemeoryByPoolName(@TeaParam(name = "poolName", alias = "poolName") String poolName) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.getMemoryByPoolName(poolName, instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get CPUUsage")
	@TeaOperation(name = "getCPUUsage", description = "Get cpu usage of running service instance", methodType = MethodType.READ)
	public CPUUsage getCPUUsage() {
		try {
			/*
			 * if(cpuUsageQuery==null)
			 * cpuUsageQuery=BEMMServiceProviderManager.getInstance()
			 * .getBEApplicationMonitoringService()
			 * .initQuery(this.getInstance(), BETeaAgentProps.BEMM_DIM_LEVEL_PU,
			 * BETeaAgentProps.BEMM_RAW_DATA_HIERARCHY,
			 * BETeaAgentProps.BEMM_MEASUREMENT_RAW_CPU_USAGE);
			 * 
			 * return BEMMServiceProviderManager.getInstance()
			 * .getBEApplicationMonitoringService() .getChartData(cpuUsageQuery,
			 * BETeaAgentProps.BEMM_CPU_USAGE_CHART_METRICS);
			 */
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.getCPUUsage(instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "copyInstance", description = "This method is used to copy the instance", methodType = MethodType.UPDATE)
	public String copyInstance(@TeaParam(name = "instanceName", alias = "instanceName") String instanceName,
			@Customize(value = "label:Processing Unit") @TeaParam(name = "processingUnit", alias = "processingUnit") String processingUnit,
			@Customize(value = "label:Host") @TeaParam(name = "hostId") final String hostId,
			@Customize(value = "label:JMX Port") @TeaParam(name = "jmxPort", alias = "jmxPort") int jmxPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:JMX UserName") @TeaParam(name = "jmxUserName", alias = "jmxUserName") String jmxUserName,
			@Customize(value = "label:JMX Password") @TeaParam(name = "jmxPassword", alias = "jmxPassword") String jmxPassword,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			@Customize(value = "label:BE Id") @TeaParam(name = "beId", alias = "beId") String beId,
			TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			instanceService.getLockManager().checkVersion(this.instance.getHost().getApplication(), version);
			String message = instanceService.copyInstance(instanceName, processingUnit, hostId, deploymentPath, jmxPort,
					jmxUserName, jmxPassword, beId, this.instance, loggedInUser);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(this.getInstance().getHost().getApplication());
			instanceService.getLockManager().incrementVersion(this.getInstance().getHost().getApplication());
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "downloadLog", description = "This method is used to download logs", methodType = MethodType.READ)
	public DataSource downloadLog() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.downloadLog(this.instance, false);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "downloadASLog", description = "This method is used to download AS logs", methodType = MethodType.READ)
	public DataSource downloadASLog() {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.downloadLog(this.instance, true);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "getInstanceAudit", description = "This method is used to get the instance audit.", methodType = MethodType.READ)
	public AuditRecords getInstanceAudit() {

		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.loadAuditRecords(instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Kill Service Instance")
	@TeaOperation(name = "KillServiceInstance", description = "Forcefully kill the running service instance", methodType = MethodType.UPDATE)
	public String killServiceInstance(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.killServiceServiceInstance(getInstance(), loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Thread Dump")
	@TeaOperation(name = "getThreadDump", description = "Get thread dump of running service instance", methodType = MethodType.READ)
	public String getThreadDump(TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return instanceService.getThreadDump(getInstance());
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Override
	public ServiceInstance getConfig() {
		return this.getInstance();
	}

	@Customize(value = "label:Get Chart data")
	@TeaOperation(name = "getChartData", description = "", methodType = MethodType.READ)
	public ViewData getChartData(@TeaParam(name = "sectionId", alias = "sectionId") int sectionId,
			@TeaParam(name = "chartId", alias = "chartId") int chartId,
			@TeaParam(name = "dimLevel", alias = "dimLevel") String dimLevel,
			@TeaParam(name = "threshold", alias = "threshold") Long threshold,
			@TeaParam(name = "appName", alias = "appName") String appName) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return BEMMServiceProviderManager.getInstance().getMetricVisulizationService().getChart(this.getInstance(),
					sectionId, chartId, dimLevel, threshold, appName);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Upload Classes")
	@TeaOperation(name = "uploadClasses", description = "", methodType = MethodType.UPDATE)
	public String uploadClasses(
			@Customize(value = "label:classFiles") @TeaParam(name = "classFiles", alias = "classFiles") DataSource classFiles,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.uploadClassFiles(classFiles, instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:loadAndDeploy")
	@TeaOperation(name = "loadAndDeployClasses", description = "", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DEPLOY_CLASSES_PERMISSION)
	public String loadAndDeploy(
			@Customize(value = "label:vrfURI") @TeaParam(name = "vrfURI", alias = "vrfURI") String vrfURI,
			@Customize(value = "label:implName") @TeaParam(name = "implName", alias = "implName") String implName) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.loadAndDeploy(vrfURI, implName, this.instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:Upload Rule Templates")
	@TeaOperation(name = "uploadRuleTemplates", description = "", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPLOAD_RULE_TEMPLATE_PERMISSION)
	public String uploadRuleTemplates(
			@Customize(value = "label:rtFiles") @TeaParam(name = "rtFiles", alias = "rtFiles") DataSource rtFiles,
			TeaPrincipal teaPrincipal) {
		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.uploadRuleTemplates(rtFiles, instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:loadAndDeployRuleTemplates")
	@TeaOperation(name = "loadAndDeployRuleTemplates", description = "", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DEPLOY_RULE_TEMPLATE_PERMISSION)
	public String loadAndDeployRuleTemplates(
			@Customize(value = "label:agentName") @TeaParam(name = "agentName", alias = "agentName") String agentName,
			@Customize(value = "label:implName") @TeaParam(name = "projectName", alias = "projectName") String projectName,
			@Customize(value = "label:ruleTemplateInstanceFQN") @TeaParam(name = "ruleTemplateInstanceFQN", alias = "ruleTemplateInstanceFQN") String ruleTemplateInstanceFQN) {

		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.loadAndDeployRuleTemplates(agentName, projectName, ruleTemplateInstanceFQN,
					instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:fireTailCommand")
	@TeaOperation(name = "fireTailCommand", description = "", methodType = MethodType.READ)
	public String fireTailCommand(
			@Customize(value = "label:NumberofLines") @TeaParam(name = "numberofLines", alias = "numberofLines") String numberofLines,
			@Customize(value = "label:isASLog") @TeaParam(name = "isASLog", alias = "isASLog") boolean isASLog) {

		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.fireTailCommand(numberofLines, isASLog, this.instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
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
	 * @param instance
	 *            the instance to set
	 */
	public void setInstance(ServiceInstance instance) {
		this.instance = instance;
	}
	@Customize(value = "label:Set instance Default Profile")
	@TeaOperation(name = "setInstanceDefaultProfile", description = "", methodType = MethodType.UPDATE)
	public String setInstanceDefaultProfile(@Customize(value = "label:Profile Name") @TeaParam(name = "profileName", alias = "profileName") String profileName){

		try {
			instanceService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return instanceService.setDefaultProfile(profileName,instance);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			instanceService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}
}
