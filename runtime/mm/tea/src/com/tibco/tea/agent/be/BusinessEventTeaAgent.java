package com.tibco.tea.agent.be;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.operations.model.Methods;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.EntityMethodsDescriptorService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEMasterHostAddException;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.MasterHostConvertor;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.impl.BEImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.monitoring.metric.view.ViewData;
import com.tibco.cep.bemm.monitoring.metric.view.config.Chart;
import com.tibco.cep.bemm.monitoring.metric.view.config.ChartSeries;
import com.tibco.cep.bemm.monitoring.metric.view.config.Section;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.tea.agent.annotations.Customize;
import com.tibco.tea.agent.annotations.TeaGetInfo;
import com.tibco.tea.agent.annotations.TeaOperation;
import com.tibco.tea.agent.annotations.TeaParam;
import com.tibco.tea.agent.annotations.TeaPermission;
import com.tibco.tea.agent.annotations.TeaPermissions;
import com.tibco.tea.agent.annotations.TeaPrivilege;
import com.tibco.tea.agent.annotations.TeaReference;
import com.tibco.tea.agent.annotations.TeaRequires;
import com.tibco.tea.agent.annotations.TeaRole;
import com.tibco.tea.agent.annotations.TeaRoles;
import com.tibco.tea.agent.api.BaseTeaObject;
import com.tibco.tea.agent.api.MethodType;
import com.tibco.tea.agent.api.TeaPrincipal;
import com.tibco.tea.agent.api.TeaSession;
import com.tibco.tea.agent.api.TopLevelTeaObject;
import com.tibco.tea.agent.be.comparator.BEApplicationComparatorByCreationTime;
import com.tibco.tea.agent.be.comparator.BEMasterHostComparatorByName;
import com.tibco.tea.agent.be.migration.file.generator.BeTeaAgentExportService;
import com.tibco.tea.agent.be.migration.file.generator.GenerateApplicationFile;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.role.Role;
import com.tibco.tea.agent.be.ui.model.BE;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.ui.model.impl.GroupDeploymentVariableImpl;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.be.util.BeAgentMigrationUtil;
import com.tibco.tea.agent.be.version.be_teagentVersion;
import com.tibco.tea.agent.internal.types.ClientType;
import com.tibco.tea.agent.support.TeaException;
import com.tibco.tea.agent.types.AgentObjectInfo;

/**
 * This is top level object for Business Event's TEA agent.
 * 
 * @author dijadhav
 *
 */
/*---------------------------------Initializing BE related Roles-------------------------------*/
@TeaRoles({ @TeaRole(name = Role.VIEW_ALL_ROLE, privileges = {
		@TeaPrivilege(permissions = { TeaPermission.READ }) }, desc = Role.VIEW_ALL_ROLE_DESCRIPTION),

		@TeaRole(name = Role.OPERATOR_ROLE, privileges = { @TeaPrivilege(permissions = {
				Permission.START_PU_INSTANCE_PERMISSION, Permission.STOP_PU_INSTANCE_PERMISSION,
				Permission.KILL_INSTANCE_PERMISSION, Permission.SUSPEND_AGENT_PERMISSION,
				Permission.RESUME_AGENT_PERMISSION, TeaPermission.READ }) }, desc = Role.OPERATOR_ROLE_DESCRIPTION),

		@TeaRole(name = Role.DEPLOYER_ROLE, privileges = { @TeaPrivilege(permissions = {
				Permission.HOT_DEPLOY_PERMISSION, Permission.CREATE_HOST_PERMISSION, Permission.UPDATE_HOST_PERMISSION,
				Permission.DELETE_HOST_PERMISSION, Permission.CREATE_INSTANCE_PERMISSION,
				Permission.UPDATE_INSTANCE_PERMISSION, Permission.DELETE_INSTANCE_PERMISSION,
				Permission.KILL_INSTANCE_PERMISSION, Permission.COPY_INSTANCE_PERMISSION,
				Permission.START_PU_INSTANCE_PERMISSION, Permission.STOP_PU_INSTANCE_PERMISSION,
				Permission.DEPLOY_INSTANCE_PERMISSION, Permission.UNDEPLOY_INSTANCE_PERMISSION,
				Permission.SUSPEND_AGENT_PERMISSION, Permission.RESUME_AGENT_PERMISSION,
				Permission.UPDATE_GV_VAR_PERMISSION, Permission.UPDATE_LOG_LEVEL_PERMISSION,
				Permission.UPDATE_SYSTEM_PROPS_PERMISSION, Permission.UPDATE_JVM_PROPS_PERMISSION, Permission.UPDATE_DEP_VAR_PERMISSION,
				Permission.SUSPEND_AGENT_PERMISSION, Permission.RESUME_AGENT_PERMISSION, TeaPermission.READ,
				Permission.UPLOAD_TRA_PERMISSION, Permission.UPLOAD_RULE_TEMPLATE_PERMISSION,
				Permission.UPLOAD_CLASSES_PERMISSION, Permission.DEPLOY_CLASSES_PERMISSION,
				Permission.DEPLOY_RULE_TEMPLATE_PERMISSION, Permission.UPDATE_BE_PROPS_PERMISSION,
				Permission.ADD_PROFILE_PERMISSION,Permission.UPDATE_PROFILE_PERMISSION,
				Permission.DELETE_PROFILE_PERMISSION,Permission.SET_DEFAULT_PROFILE_PERMISSION, Permission.UPLOAD_CUSTOM_JAR_PERMISSION}) }, desc = Role.DEPLOYER_ROLE_DESCRIPTION),

		@TeaRole(name = Role.APP_MANAGER_ROLE, privileges = { @TeaPrivilege(permissions = {
				Permission.CREATE_DEPLOYMENT_PERMISSION, Permission.UPDATE_DEPLOYMENT_PERMISSION,
				Permission.DELETE_DEPLOYMENT_PERMISSION, Permission.HOT_DEPLOY_PERMISSION,
				Permission.CREATE_HOST_PERMISSION, Permission.UPDATE_HOST_PERMISSION, Permission.DELETE_HOST_PERMISSION,
				Permission.CREATE_INSTANCE_PERMISSION, Permission.UPDATE_INSTANCE_PERMISSION,
				Permission.DELETE_INSTANCE_PERMISSION, Permission.KILL_INSTANCE_PERMISSION,
				Permission.COPY_INSTANCE_PERMISSION, Permission.START_PU_INSTANCE_PERMISSION,
				Permission.STOP_PU_INSTANCE_PERMISSION, Permission.DEPLOY_INSTANCE_PERMISSION,
				Permission.UNDEPLOY_INSTANCE_PERMISSION, Permission.SUSPEND_AGENT_PERMISSION,
				Permission.RESUME_AGENT_PERMISSION, Permission.UPDATE_GV_VAR_PERMISSION,
				Permission.UPDATE_LOG_LEVEL_PERMISSION, Permission.UPDATE_SYSTEM_PROPS_PERMISSION,
				Permission.UPDATE_JVM_PROPS_PERMISSION, Permission.UPDATE_DEP_VAR_PERMISSION, Permission.SUSPEND_AGENT_PERMISSION,
				Permission.RESUME_AGENT_PERMISSION, Permission.UPLOAD_TRA_PERMISSION,
				Permission.UPLOAD_RULE_TEMPLATE_PERMISSION, Permission.UPLOAD_CLASSES_PERMISSION,
				Permission.DEPLOY_CLASSES_PERMISSION, Permission.DEPLOY_RULE_TEMPLATE_PERMISSION,
				Permission.UPDATE_BE_PROPS_PERMISSION,Permission.ADD_PROFILE_PERMISSION,
				Permission.UPDATE_PROFILE_PERMISSION,Permission.DELETE_PROFILE_PERMISSION, Permission.UPLOAD_CUSTOM_JAR_PERMISSION,
				Permission.SET_DEFAULT_PROFILE_PERMISSION,TeaPermission.READ }) }, desc = Role.APP_MANAGER_ROLE_DESCRIPTION),

		@TeaRole(name = Role.RULE_AUTHOR, privileges = { @TeaPrivilege(permissions = {
				Permission.CREATE_RULE_PERMISSION, Permission.UPDATE_RULE_PERMISSION, Permission.DELETE_RULE_PERMISSION,
				Permission.GET_RULES_PERMISSION, Permission.CLEAR_ALERTS_PERMISSION,
				Permission.GET_ALERTS_PERMISSION ,TeaPermission.READ }) }, desc = Role.RULE_AUTHOR_DESCRIPTION),

		@TeaRole(name = Role.RULE_AUTHOR_ADMIN, privileges = { @TeaPrivilege(permissions = {
				Permission.CREATE_RULE_PERMISSION, Permission.UPDATE_RULE_PERMISSION, Permission.DELETE_RULE_PERMISSION,
				Permission.GET_RULES_PERMISSION, Permission.GET_ALERTS_PERMISSION, Permission.CLEAR_ALERTS_PERMISSION,
				Permission.RULE_ADMIN_PERMISSION , TeaPermission.READ }) }, desc = Role.RULE_AUTHOR_ADMIN_DESCRIPTION)

})

/*---------------------------------------------------------------------------------------------*/
/*---------------------------Initializing BE Related Permissions-------------------------------*/
@TeaPermissions({
		@TeaPermission(name = Permission.START_PU_INSTANCE_PERMISSION, desc = Permission.START_PU_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.STOP_PU_INSTANCE_PERMISSION, desc = Permission.STOP_PU_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.CREATE_DEPLOYMENT_PERMISSION, desc = Permission.CREATE_DEPLOYMENT_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_DEPLOYMENT_PERMISSION, desc = Permission.UPDATE_DEPLOYMENT_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DELETE_DEPLOYMENT_PERMISSION, desc = Permission.DELETE_DEPLOYMENT_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.CREATE_INSTANCE_PERMISSION, desc = Permission.CREATE_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DELETE_INSTANCE_PERMISSION, desc = Permission.DELETE_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_HOST_PERMISSION, desc = Permission.UPDATE_HOST_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.CREATE_HOST_PERMISSION, desc = Permission.CREATE_HOST_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DELETE_HOST_PERMISSION, desc = Permission.DELETE_HOST_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_INSTANCE_PERMISSION, desc = Permission.UPDATE_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DEPLOY_INSTANCE_PERMISSION, desc = Permission.DEPLOY_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UNDEPLOY_INSTANCE_PERMISSION, desc = Permission.UNDEPLOY_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_GV_VAR_PERMISSION, desc = Permission.UPDATE_GV_VAR_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_LOG_LEVEL_PERMISSION, desc = Permission.UPDATE_LOG_LEVEL_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_SYSTEM_PROPS_PERMISSION, desc = Permission.UPDATE_SYSTEM_PROPS_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_JVM_PROPS_PERMISSION, desc = Permission.UPDATE_JVM_PROPS_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_DEP_VAR_PERMISSION, desc = Permission.UPDATE_DEP_VAR_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.KILL_INSTANCE_PERMISSION, desc = Permission.KILL_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.COPY_INSTANCE_PERMISSION, desc = Permission.COPY_INSTANCE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.SUSPEND_AGENT_PERMISSION, desc = Permission.SUSPEND_AGENT_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.RESUME_AGENT_PERMISSION, desc = Permission.RESUME_AGENT_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.HOT_DEPLOY_PERMISSION, desc = Permission.HOT_DEPLOY_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPLOAD_TRA_PERMISSION, desc = Permission.UPLOAD_TRA_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DEPLOY_CLASSES_PERMISSION, desc = Permission.DEPLOY_CLASSES_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPLOAD_CLASSES_PERMISSION, desc = Permission.UPLOAD_CLASSES_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DEPLOY_RULE_TEMPLATE_PERMISSION, desc = Permission.DEPLOY_RULE_TEMPLATE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPLOAD_RULE_TEMPLATE_PERMISSION, desc = Permission.UPLOAD_RULE_TEMPLATE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.CREATE_RULE_PERMISSION, desc = Permission.CREATE_RULE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_RULE_PERMISSION, desc = Permission.UPDATE_RULE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DELETE_RULE_PERMISSION, desc = Permission.DELETE_RULE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.GET_RULES_PERMISSION, desc = Permission.GET_RULES_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.RULE_ADMIN_PERMISSION, desc = Permission.RULE_ADMIN_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.CLEAR_ALERTS_PERMISSION, desc = Permission.CLEAR_ALERTS_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.GET_ALERTS_PERMISSION, desc = Permission.GET_ALERTS_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_BE_PROPS_PERMISSION, desc = Permission.UPDATE_BE_PROPS_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.ADD_PROFILE_PERMISSION, desc = Permission.ADD_PROFILE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPDATE_PROFILE_PERMISSION, desc = Permission.UPDATE_PROFILE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.DELETE_PROFILE_PERMISSION, desc = Permission.DELETE_PROFILE_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.UPLOAD_CUSTOM_JAR_PERMISSION, desc = Permission.UPLOAD_CUSTOM_JAR_PERMISSION_DESCRIPTION),
		@TeaPermission(name = Permission.SET_DEFAULT_PROFILE_PERMISSION, desc = Permission.SET_DEFAULT_PROFILE_PERMISSION_DESCRIPTION)})

/*-----------------------------------------------------------------------------------------------*/
public class BusinessEventTeaAgent extends AbstractStartStopServiceImpl implements TopLevelTeaObject {
	// Logger
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BusinessEventTeaAgent.class);

	// Business event topology helper class.
	private String selectedApplication;

	// BusinessEventTeaAgent Instance
	private static BusinessEventTeaAgent beTeaAgent = new BusinessEventTeaAgent();
	@JsonIgnore
	private BEApplicationsManagementService applicationService;
	@JsonIgnore
	private BEMasterHostManagementService masterHostManagementService;
	@JsonIgnore
	private EntityMethodsDescriptorService descriptorService;
	@JsonIgnore
	private BeTeaAgentExportService exportService;

	private MessageService messageService;

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

	/**
	 * @return the descriptorService
	 */
	@JsonIgnore
	public EntityMethodsDescriptorService getDescriptorService() {
		return descriptorService;
	}

	/**
	 * @param descriptorService
	 *            the descriptorService to set
	 */
	@JsonIgnore
	public void setDescriptorService(EntityMethodsDescriptorService descriptorService) {
		this.descriptorService = descriptorService;
	}

	/**
	 * Get the instance of the type BusinessEventTeaAgent
	 * 
	 * @return
	 */
	public static BusinessEventTeaAgent getInstance() {
		return beTeaAgent;
	}

	/**
	 * The the information of BE-TEA agent
	 * 
	 * @return
	 */
	@TeaGetInfo
	public AgentObjectInfo getAgentInfo() {
		String agentName = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_NAME.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_NAME.getDefaultValue());
		AgentObjectInfo info = new AgentObjectInfo();
		info.setName(agentName);
		info.setDesc("");
		return info;
	}

	/**
	 * Get Applications collection of application
	 * 
	 * @return Collection of application
	 */
	@TeaReference(name = "Applications")
	public Collection<BEApplication> getAllApplication() {
		List<BEApplication> applications = new ArrayList<BEApplication>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			Map<String, Application> applicationMap = applicationService.getApplications();
			for (Entry<String, Application> entry : applicationMap.entrySet()) {
				Application application = entry.getValue();
				BEApplication beApplication = (BEApplication) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_APPLICATION).getInstance(application.getKey());
				if (null == beApplication) {
					beApplication = new BEApplication(application);
					beApplication.registerWithObjectProvider();
				}
				applications.add(beApplication);
			}
			Collections.sort(applications, new BEApplicationComparatorByCreationTime());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return applications;
	}

	/**
	 * Get Applications collection of application
	 * 
	 * @return Collection of application
	 */
	@TeaReference(name = "MasterHosts")
	public Collection<BEMasterHost> getAllMasterHost() {
		List<BEMasterHost> beMasterHosts = new ArrayList<BEMasterHost>();
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

			Map<String, MasterHost> masterHostMap = masterHostManagementService.getAllMasterHost();
			for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
				MasterHost masterHost = entry.getValue();
				BEMasterHost beMasterHost = (BEMasterHost) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_MASTER_HOST).getInstance(masterHost.getKey());
				if (null == beMasterHost) {
					beMasterHost = new BEMasterHost(masterHost);
					beMasterHost.registerWithObjectProvider();
				}
				beMasterHost.sethost(masterHost);
				beMasterHosts.add(beMasterHost);
			}

			Collections.sort(beMasterHosts, new BEMasterHostComparatorByName());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return beMasterHosts;
	}

	@TeaOperation(name = "getSupportedOS", description = "Get Supported OS", methodType = MethodType.READ)
	public Map<String, String> getSupportedOS() {
		Map<String, String> osTypes = new LinkedHashMap<>();
		osTypes.put("Windows Based", "Windows Based");
		osTypes.put("OS/X,Unix/Linux Based", "OS/X,Unix/Linux Based");

		return osTypes;
	}

	/**
	 * Upload method invoked while creating,editing and importing the
	 * application
	 * 
	 * @param name
	 *            - Name of the application
	 * @param description
	 *            - Description of the application
	 * @param cddpath
	 *            - CDD of the application
	 * @param earpath
	 *            - EAR of the application
	 * @param file
	 *            -Site topology/ Exported Zip of application
	 * @param isCreate
	 *            - Whether the application is creating or not
	 * @param isEdit
	 *            - Whether the application is editing o not or not
	 * @return
	 * @throws Exception
	 */
	@Customize(value = "label:Upload")
	@TeaOperation(name = "upload", methodType = MethodType.UPDATE, description = "")
	@TeaRequires(Permission.CREATE_DEPLOYMENT_PERMISSION)
	public String upload(@Customize(value = "label:Name") @TeaParam(name = "name", alias = "name") String name,
			@Customize(value = "label:Description") @TeaParam(name = "description", alias = "Description") String description,
			@Customize(value = "label:CDD") @TeaParam(name = "cddpath", alias = "cddpath") DataSource cddpath,
			@Customize(value = "label:EAR") @TeaParam(name = "earpath", alias = "earpath") DataSource earpath,
			@Customize(value = "label:Site Toplology") @TeaParam(name = "stPath", alias = "stPath") DataSource file,
			@Customize(value = "label:Is Create") @TeaParam(name = "isCreate", alias = "isCreate") boolean isCreate,
			@Customize(value = "label:Is Exported") @TeaParam(name = "isExportedCreate", alias = "isExportedCreate") boolean isExportedCreate,
			TeaPrincipal teaPrincipal) {
		String loggedInUser = teaPrincipal.getName();
		// Create Application
		if (isCreate) {
			try {
				applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_CREATING_MESSAGE));
				StringBuilder successMessage = new StringBuilder();
				Application application = applicationService.createApplication(name, cddpath, earpath, loggedInUser,
						successMessage);

				if (null != application) {
					BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);

				}
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_CREATED_MESSAGE));
				// Creating pre-configured rules for application
				if (application != null) {
					BEMMServiceProviderManager.getInstance().getMetricRuleService()
							.createPreconfiguredRules(application);
				}
				return successMessage.toString();
			} catch (Exception ex) {
				throw logErrorMessage(ex);
			} finally {
				applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			}
		} else if (isExportedCreate) {
			try {
				applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.IMPORTING_EXPORTED_ZIP));
				StringBuilder successMessage = new StringBuilder();
				Application application = applicationService.importExportedApplication(file, loggedInUser,
						successMessage);
				if (null != application) {
					BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);
					BETeaAgentManager.INSTANCE.createAndRegisterTeaObjects();
				}
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_CREATED_MESSAGE));
				// Creating pre-configured rules for application
				if (application != null) {
					BEMMServiceProviderManager.getInstance().getMetricRuleService()
							.createPreconfiguredRules(application);
				}
				return successMessage.toString();
			} catch (Exception ex) {
				throw logErrorMessage(ex);
			} finally {
				applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			}
		} else {

			// Import application
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.IMPORTING_APPLICATION));
			try {
				applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
				StringBuilder successMessage = new StringBuilder();
				Application application = applicationService.importApplication(name, file, cddpath, earpath,
						loggedInUser, successMessage);
				BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);
				BETeaAgentManager.INSTANCE.createAndRegisterTeaObjects();
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_IMPORTED_MESSAGE));
				// Creating pre-configured rules for application
				if (application != null) {
					BEMMServiceProviderManager.getInstance().getMetricRuleService()
							.createPreconfiguredRules(application);
				}
				return successMessage.toString();
			} catch (Exception ex) {
				throw logErrorMessage(ex);
			} finally {
				applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			}
		}
	}

	/**
	 * Set selected application name
	 * 
	 * @param selectedApp
	 */
	@TeaOperation(name = "setSelectedApp", description = "Set selected application name", methodType = MethodType.UPDATE)
	public void setSelectedApp(
			@Customize(value = "label:SelectedAppName") @TeaParam(name = "selectedApp", alias = "selectedApp") String selectedApp) {
		selectedApplication = selectedApp;
	}

	/**
	 * Get selected application name
	 * 
	 * @param selectedApp
	 */
	@TeaOperation(name = "getSelectedApp", description = "Get selected application name", methodType = MethodType.UPDATE)
	public String getSelectedApp(
			@Customize(value = "label:SelectedAppName") @TeaParam(name = "selectedApp", alias = "selectedApp") String selectedApp) {
		return selectedApplication;
	}

	@Override
	public String getDescription() {
		return BETeaAgentProps.AGENT_TYPE_DESCRIPTION;
	}

	@Override
	public String getName() {
		return "BusinessEvents";
	}

	@Override
	public Collection<BaseTeaObject> getMembers() {
		return null;
	}

	@Override
	public String getTypeDescription() {
		return BETeaAgentProps.AGENT_TYPE_DESCRIPTION;
	}

	@Override
	public String getTypeName() {
		return "BusinessEvents";
	}

	/**
	 * @param masterHostManagementService
	 *            the masterHostManagementService to set
	 */
	public void setMasterHostManagementService(BEMasterHostManagementService masterHostManagementService) {
		this.masterHostManagementService = masterHostManagementService;
	}

	@TeaOperation(name = "getEntityMethods", description = "This method is used to get the process/agent methods", methodType = MethodType.READ)
	public Map<String, Methods> getEntityMethods() {
		return descriptorService.getEnityMethods();
	}

	@SuppressWarnings("unused")
	private void addApplication(Application application) {
		if (null != application) {
			BEApplication beApplication = (BEApplication) ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_APPLICATION).getInstance(application.getKey());
			if (null == beApplication) {
				beApplication = new BEApplication(application);
				beApplication.registerWithObjectProvider();
			}
		}
	}

	@TeaOperation(name = "getBeHomes", description = "Adds a new application host", methodType = MethodType.READ)
	public List<BE> getBeHomes(
			@Customize(value = "label:Host Name") @TeaParam(name = "hostName", alias = "hostName") String hostName,
			@Customize(value = "label:IP Address") @TeaParam(name = "ipAddress", alias = "ipAddress") String ipAddress,
			@Customize(value = "label:Operating System") @TeaParam(name = "hostOS", alias = "hostOS") String hostOS,
			@Customize(value = "label:User Name") @TeaParam(name = "userName", alias = "userName") String userName,
			@Customize(value = "label:Password;private:true") @TeaParam(name = "password", alias = "password") String password,
			@Customize(value = "label:SSH Port") @TeaParam(name = "sshPort", alias = "sshPort") int sshPort,
			TeaPrincipal teaPrincipal) {
		masterHostManagementService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			return MasterHostConvertor.convertBEServiceModelUIModel(
					masterHostManagementService.getBeHomes(hostName, ipAddress, hostOS, userName, password, sshPort));
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Add Host")
	@TeaOperation(name = "addMasterHost", description = "Adds a new application host", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.CREATE_HOST_PERMISSION)
	public String addMasterHost(
			@Customize(value = "label:Master Host") @TeaParam(name = "masterHost", alias = "masterHost") com.tibco.tea.agent.be.ui.model.MasterHost masterHost,
			TeaPrincipal teaPrincipal, TeaSession session) {
		try {

			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			String message = masterHostManagementService.addMasterHost(masterHost.getHostName(), masterHost.getHostIp(),
					masterHost.getOs(), MasterHostConvertor.convertBEUIModelToBEServiceModel(masterHost.getBE()),
					masterHost.getUserName(), masterHost.getPassword(), masterHost.getSshPort(),
					masterHost.getDeploymentPath(), loggedInUser);
			BETeaAgentManager.INSTANCE.createAndRegisterTeaObjects();
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Add Host Cli")
	@TeaOperation(name = "addMasterHostCli", description = "Adds a new application host", methodType = MethodType.UPDATE, hideFromClients = ClientType.WEB_UI)
	@TeaRequires(Permission.CREATE_HOST_PERMISSION)
	public String addMasterHostCli(
			@Customize(value = "label:Host Name") @TeaParam(name = "hostName", alias = "hostName") String hostName,
			@Customize(value = "label:IP Address") @TeaParam(name = "ipAddress", alias = "ipAddress") String ipAddress,
			@Customize(value = "label:Operating System") @TeaParam(name = "hostOS", alias = "hostOS") String hostOS,
			@Customize(value = "label:BE Home") @TeaParam(name = "beHome", alias = "beHome") String beHome,
			@Customize(value = "label:BE TRA ") @TeaParam(name = "beTra", alias = "beTra") String beTra,
			@Customize(value = "label:BE VERSION ") @TeaParam(name = "beVersion", alias = "beVersion", defaultValue = "") String beVersion,
			@Customize(value = "label:User Name") @TeaParam(name = "userName", alias = "userName") String userName,
			@Customize(value = "label:Password;private:true") @TeaParam(name = "password", alias = "password") String password,
			@Customize(value = "label:SSH Port") @TeaParam(name = "sshPort", alias = "sshPort") int sshPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:Add BeHome") @TeaParam(name = "addBeHome", alias = "addBeHome", defaultValue = "false") boolean addBeHome,
			TeaPrincipal teaPrincipal) {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			boolean isMonitorable = Boolean.valueOf(
					configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
							ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
			String loggedInUser = teaPrincipal.getName();
			String message = "";
			List<com.tibco.cep.bemm.model.BE> beList = new ArrayList<com.tibco.cep.bemm.model.BE>();
			if (isMonitorable) {
				message = masterHostManagementService.addMasterHost(hostName, ipAddress, hostOS, beList, userName,
						password, sshPort, deploymentPath, loggedInUser);
				BETeaAgentManager.INSTANCE.createAndRegisterTeaObjects();
			} else {
				com.tibco.cep.bemm.model.BE beServiceModel = new BEImpl();
				beServiceModel.setBeHome(beHome);
				beServiceModel.setBeTra(beTra);
				beServiceModel.setVersion(beVersion);				
				beList.add(beServiceModel);
			
				if (addBeHome == true) {
					MasterHost masterHost = null;
					for (MasterHost host : masterHostManagementService.getAllMasterHost().values()) {
						if (null != host && host.getHostName().trim().equalsIgnoreCase(hostName.trim())) {
							masterHost = host;
							break;
						}
					}
					if (masterHost != null) {
						for (com.tibco.cep.bemm.model.BE be : masterHost.getBE()) {
							if (be.getBeHome().equals(beHome)) {
								throw new BEMasterHostAddException(
										messageService.getMessage(MessageKey.HOST_BE_HOME_DUPLICATE_MESSAGE));
							}
						}
						beList.addAll(masterHost.getBE());
						masterHostManagementService.editMasterHost(masterHost, hostName, ipAddress, hostOS, beList,
								userName, password, sshPort, deploymentPath, loggedInUser);
						message = messageService.getMessage(MessageKey.HOST_BE_HOME_ADDED_MESSAGE);
					} else {
						throw new BEMasterHostAddException(
								messageService.getMessage(MessageKey.HOST_EXIST_ERROR_MESSAGE, hostName));
					}
				} else {
					message = masterHostManagementService.addMasterHost(hostName, ipAddress, hostOS, beList, userName,
							password, sshPort, deploymentPath, loggedInUser);
					BETeaAgentManager.INSTANCE.createAndRegisterTeaObjects();
				}

			}

			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}
	

	/**
	 * Get Universal chart configuration for all entities Note : Currently al
	 * apps will have same chart configuration.If app wise config needed pass
	 * app as an argument and return only the app's configuration
	 * 
	 */
	@Customize(value = "label:Get Charts Configuration")
	@TeaOperation(name = "getAllChartsConfig", description = "", methodType = MethodType.READ)
	public ArrayList<Section> getChartsConfig() {
		try {
			ArrayList<Section> sectionList = BEMMServiceProviderManager.getInstance().getMetricVisulizationService()
					.getAllViewsConfig();
			for (Section section : sectionList) {
				for (Chart chart : section.getChart()) {
					for (ChartSeries series : chart.getSeries()) {
						series.setQuery(null);
					}
				}
			}
			return sectionList;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

	/**
	 * Get Universal Entity versus attribute mapping
	 */
	@Customize(value = "label:Get Rule attributes for all entities")
	@TeaOperation(name = "getRuleAttributesForAllEntities", description = "Get Rule attributes for all entities", methodType = MethodType.READ)
	public Map<String, Object> getRuleAttributes(TeaPrincipal teaPrincipal) {
		try {
			return BEMMServiceProviderManager.getInstance().getMetricRuleService().getAllEntitiesAttrMap();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

	/**
	 * Get Universal Action descriptors
	 */
	@Customize(value = "label:Get Universal Action descriptors")
	@TeaOperation(name = "getActionDescriptors", description = "Get Universal Action descriptors", methodType = MethodType.READ)
	public List<ActionFunctionDescriptor> getActionDescriptors(TeaPrincipal teaPrincipal) {
		try {
			return BEMMServiceProviderManager.getInstance().getMetricRuleService().getActionDescriptors();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

	@Override
	public void init(Properties configuration) throws Exception {
		this.configuration = configuration;
		applicationService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService();
		descriptorService = BEMMServiceProviderManager.getInstance().getEntityMethodsDescriptorService();
		masterHostManagementService = BEMMServiceProviderManager.getInstance().getMasterHostManagementService();
		exportService = BEMMServiceProviderManager.getInstance().getExportService();
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();

	}

	/**
	 * Get Health Map
	 */
	@Customize(value = "label:Get Entity Health Map")
	@TeaOperation(name = "getEntityHealthMap", description = "get entity health map", methodType = MethodType.READ)
	public Map<String, String> getEntityHealthMap(TeaPrincipal teaPrincipal) {
		try {
			return BEMMServiceProviderManager.getInstance().getMetricRuleService().getHealthMap();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

	/**
	 * Get Logged In user
	 */
	@Customize(value = "label:Get logged in user name")
	@TeaOperation(name = "getLoggedInUser", description = "Get logged in user name", methodType = MethodType.READ)
	public String getLoggedInUser(TeaPrincipal teaPrincipal) {
		try {
			return teaPrincipal.getName();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

	/**
	 * Get Logged In user
	 */
	@Customize(value = "label:Get BusinessEvensts Agent Info")
	@TeaOperation(name = "getBusinessEventsAgentInfo", description = "Get version,document url and copy rights information of BusinessEvents TEA agent", methodType = MethodType.READ)
	public Map<String, String> getBusinessEventsAgentInfo() {
		try {
			Map<String, String> agentInfoMap = new HashMap<String, String>();
			agentInfoMap.put("Version", be_teagentVersion.version);
			agentInfoMap.put("Copyrights", be_teagentVersion.copyright);
			agentInfoMap.put("DocUrl", configuration.getProperty(ConfigProperty.BE_TEA_AGENT_DOC_URL.getPropertyName(),
					ConfigProperty.BE_TEA_AGENT_DOC_URL.getDefaultValue()));
			return agentInfoMap;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}
	}

	@Customize(value = "label:Delete Host")
	@TeaOperation(name = "groupMasterHostsDelete", description = "Delete multiple master hosts", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DELETE_HOST_PERMISSION)
	public String groupMasterHostsDelete(
			@Customize(value = "label:hosts list") @TeaParam(name = "hosts", alias = "hosts") List<String> hosts,
			TeaPrincipal teaPrincipal) {
		try {
			applicationService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return masterHostManagementService.groupDelete(hosts, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	/**
	 * is Enable additional views
	 */
	@Customize(value = "label:is Additional charts enable")
	@TeaOperation(name = "enableAdditionalViews", description = "Get the flag which enables and disables the additional charts : GC and memory pools", methodType = MethodType.READ)
	public Boolean enableAdditionalViews(TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			Boolean enableViews = Boolean
					.parseBoolean((String) ConfigProperty.BE_TEA_AGENT_ENABLE_ADDITIONAL_VIEWS.getValue(configuration));

			return enableViews;
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			return false;
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

	@TeaOperation(name = "migrateUsersAndRoles", description = "migrateUsersAndRoles", methodType = MethodType.READ)
	public String[][] migrateUsersAndRoles(
			@TeaParam(name = "exportedfilePath", alias = "exportedfilePath") DataSource exportedfilePath,
			TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			return exportService.generateUsersAndRoles(exportedfilePath);
		} catch (Exception e) {
			throw new TeaException(e.getMessage());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "migrate", description = "migrate", methodType = MethodType.READ)
	public List migrate(@TeaParam(name = "exportedfilePath", alias = "exportedfilePath") DataSource exportedfilePath,
			@TeaParam(name = "source", alias = "source") String source, TeaPrincipal teaPrincipal) {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		try {
			List list = exportService.generate(exportedfilePath, source);
			if (source.equals(GenerateApplicationFile.SOURCE_CLI)) {
				createMigratedApplication(list, teaPrincipal);
			}
			return list;
		} catch (Exception e) {
			throw new TeaException(e.getMessage());
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@TeaOperation(name = "migrateCli", description = "migrateCli", methodType = MethodType.READ)
	public String migrateCli(
			@TeaParam(name = "exportedfilePath", alias = "exportedfilePath") DataSource exportedfilePath,
			TeaPrincipal teaPrincipal) {
		try {
			migrate(exportedfilePath, GenerateApplicationFile.SOURCE_CLI, teaPrincipal);
			return "Success";
		} catch (Exception e) {
			throw new TeaException(e.getMessage());
		}
	}

	@TeaOperation(name = "createMigratedApplication", description = "createMigratedApplication", methodType = MethodType.UPDATE)
	public String createMigratedApplication(@TeaParam(name = "modifiedList", alias = "modifiedList") List modifiedList,
			TeaPrincipal teaPrincipal) {

		String loggedInUser = teaPrincipal.getName();
		StringBuffer errormsg = new StringBuffer();
		int errorCode = 0;
		List<Object> applications = (List<Object>) modifiedList.get(0);
		List<Object> machines = (List<Object>) modifiedList.get(1);

		String machineName = null;
		String instanceName = null;
		String propName = null;

		String msg = null;
		for (Object machine : machines) {
			machineName = (String) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.MACHINENAME);
			String ip = (String) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.IP);
			String os = (String) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.OS);
			List<com.tibco.tea.agent.be.ui.model.BE> beList = BeAgentMigrationUtil
					.convertBeUiModelToJava((List) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.BELIST));
			String sshUser = (String) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.SSHUSER);
			String sshPass = (String) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.SSHPASS);
			int sshPort = (int) BeAgentMigrationUtil.getValue(machine, BeAgentMigrationUtil.SSHPORT);
			String deploymentPath = (String) BeAgentMigrationUtil.getValue(machine,
					BeAgentMigrationUtil.DEPLOYMENTPATH);

			try {
				/*
				 * com.tibco.tea.agent.be.ui.model.MasterHost masterHost = new
				 * MasterHostUIModel(); msg = addMasterHost(machineName, ip, os,
				 * beHome, beTra, sshUser, sshPass, sshPort, deploymentPath,
				 * teaPrincipal);
				 */

				String message = masterHostManagementService.addMasterHost(machineName, ip, os,
						MasterHostConvertor.convertBEUIModelToBEServiceModel(beList), sshUser, sshPass, sshPort,
						deploymentPath, loggedInUser);
				BETeaAgentManager.INSTANCE.createAndRegisterTeaObjects();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Migration - Unable to create machine" + e.getMessage());
				// errorCode=1;
				// errormsg.append("Machine - Unable to create
				// machine["+machineName+"]"); errormsg.append("\n");
			}
		}

		String allAppError = null;
		for (Object mApplication : applications) {
			String appName = null;
			String appError = null;
			try {
				// create applications
				DataSource cdd = new FileDataSource(
						(String) BeAgentMigrationUtil.getValue(mApplication, BeAgentMigrationUtil.CDDPATH));
				DataSource ear = new FileDataSource(
						(String) BeAgentMigrationUtil.getValue(mApplication, BeAgentMigrationUtil.EARPATH));

				String instanceError = null;
				String allInstanceError = null;
				String jvmPropsError = null;
				String gvError = null;

				appName = (String) BeAgentMigrationUtil.getValue(mApplication, BeAgentMigrationUtil.APPNAME);
				msg = upload(appName, "", cdd, ear, null, true, false, teaPrincipal); // create
																						// application
				LOGGER.log(Level.INFO, msg);

				// create instances

				Application app = applicationService.getApplicationByName(appName);
				List<Object> instances = (List<Object>) BeAgentMigrationUtil.getValue(mApplication,
						BeAgentMigrationUtil.INSTANCE);
				for (Object mInstance : instances) {
					gvError = null;
					jvmPropsError = null;
					try {
						instanceName = (String) BeAgentMigrationUtil.getValue(mInstance,
								BeAgentMigrationUtil.INSTANCENAME);
						String pu = (String) BeAgentMigrationUtil.getValue(mInstance, BeAgentMigrationUtil.PU);
						String iMachineName = (String) BeAgentMigrationUtil.getValue(mInstance,
								BeAgentMigrationUtil.MACHINENAME);
						int jmxPort = (int) BeAgentMigrationUtil.getValue(mInstance, BeAgentMigrationUtil.JMXPORT);
						String deployPath = (String) BeAgentMigrationUtil.getValue(mInstance,
								BeAgentMigrationUtil.DEPLOYPATH);
						String beHome = (String) BeAgentMigrationUtil.getValue(mInstance, BeAgentMigrationUtil.BEHOME);

						// get be id corresponding to behome for the given
						// machine
						MasterHost mHost = masterHostManagementService.getMasterHostByName(iMachineName);
						List<com.tibco.cep.bemm.model.BE> beList = mHost.getBE();
						String beId = null;
						for (com.tibco.cep.bemm.model.BE be : beList) {
							if (be.getBeHome().equals(beHome))
								beId = be.getId();
						}

						msg = applicationService.createServiceInstance(app, instanceName, pu, iMachineName, jmxPort,
								deployPath, true, loggedInUser, "", "", beId); // create
																				// instance
						LOGGER.log(Level.INFO, msg);

						// JVM props
						try {
							List<String> selIns = new ArrayList<String>();
							selIns.add(app.getKey() + "/" + "ServiceInstance/" + instanceName);
							Map<String, Object> jvmPropsMap = (Map<String, Object>) BeAgentMigrationUtil
									.getValue(mInstance, BeAgentMigrationUtil.JVMPROPERTIES);
							Iterator jvmProps = jvmPropsMap.entrySet().iterator();
							while (jvmProps.hasNext()) {
								Entry<String, Object> thisEntry = (Entry<String, Object>) jvmProps.next();
								propName = thisEntry.getKey();
								String value = String.valueOf((Integer) thisEntry.getValue());

								List<GroupDeploymentVariable> gdvs = new ArrayList<GroupDeploymentVariable>();
								GroupDeploymentVariable dv = new GroupDeploymentVariableImpl();
								dv.setSelectedInstances(selIns);
								dv.setName(propName);
								dv.setValue(value);
								gdvs.add(dv);
								msg = applicationService.storeGroupDeploymentVariable(app.getName(),
										DeploymentVariableType.JVM_PROPERTIES, gdvs, loggedInUser);// store
																									// jvm
																									// props
								LOGGER.log(Level.INFO, msg);
							}
						} catch (Exception bex) {
							LOGGER.log(Level.ERROR, bex.getMessage());
							errorCode = 1;
							if (jvmPropsError == null)
								jvmPropsError = messageService.getMessage(MessageKey.JVM_PROPS_UPDATE_ERROR);
						}

						try {
							List<String> selIns = new ArrayList<String>();
							selIns.add(app.getKey() + "/" + "ServiceInstance/" + instanceName);
							Map<String, Object> globalVarsMap = (Map<String, Object>) BeAgentMigrationUtil
									.getValue(mInstance, BeAgentMigrationUtil.GLOBALVARIABLES);
							Iterator globalVars = globalVarsMap.entrySet().iterator();
							while (globalVars.hasNext()) {
								Entry<String, Object> thisEntry = (Entry<String, Object>) globalVars.next();
								propName = thisEntry.getKey();
								String value = String.valueOf(thisEntry.getValue());

								List<GroupDeploymentVariable> gdvs = new ArrayList<GroupDeploymentVariable>();
								GroupDeploymentVariable dv = new GroupDeploymentVariableImpl();
								dv.setSelectedInstances(selIns);
								dv.setName(propName);
								dv.setValue(value);
								gdvs.add(dv);
								msg = applicationService.storeGroupDeploymentVariable(app.getName(),
										DeploymentVariableType.GLOBAL_VARIABLES, gdvs, loggedInUser);// store
																										// GV
																										// props
								LOGGER.log(Level.INFO, msg);
							}
						} catch (Exception bex) {
							LOGGER.log(Level.ERROR, bex.getMessage());
							errorCode = 1;
							if (gvError == null)
								gvError = messageService.getMessage(MessageKey.GV_PROPS_UPDATE_ERROR);
						}
						instanceError = jvmPropsError != null ? "[Instance " + instanceName + "[" + jvmPropsError + "]"
								: null;
						if (gvError != null)
							instanceError = instanceError == null ? "[Instance " + instanceName + "[" + gvError + "]"
									: "[" + gvError + "]";
					} catch (Exception ex) {
						LOGGER.log(Level.ERROR, ex.getMessage());
						errorCode = 1;
						instanceError = "[Instance " + instanceName + " Create Error]";

					}
					if (instanceError != null)
						allInstanceError = allInstanceError != null ? allInstanceError + instanceError : instanceError;
				}
				appError = allInstanceError != null ? "Application : " + appName + " - " + allInstanceError + "\n"
						: null;
				allAppError = allAppError != null ? allAppError + appError : appError;
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				errorCode = 1;
				appError = "Application : " + appName + " Create Error" + "\n";
			} finally {

			}
			if (appError != null)
				errormsg.append(appError);
		}
		exportService.deleteTempFiles();
		if (errorCode == 1) {
			throw new TeaException(errormsg.toString());
		}
		return messageService.getMessage(MessageKey.MIGRATED_SUCCESS);
	}

	/**
	 * Get Universal chart configuration for all entities. Note : Currently all
	 * apps will have same chart configuration.If app wise config needed pass
	 * app as an argument and return only the app's configuration
	 * 
	 */
	@Customize(value = "label:Get Self Charts Configuration")
	@TeaOperation(name = "getSelfChartsConfig", description = "", methodType = MethodType.READ)
	public ArrayList<Section> getSelfChartsConfig() {
		applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);

		try {
			ArrayList<Section> sectionList = BEMMServiceProviderManager.getInstance().getMetricVisulizationService()
					.getAllBeTeaAgentViewsConfig();
			for (Section section : sectionList) {
				for (Chart chart : section.getChart()) {
					for (ChartSeries series : chart.getSeries()) {
						series.setQuery(null);
					}
				}
			}
			return sectionList;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Chart data")
	@TeaOperation(name = "getChartData", description = "", methodType = MethodType.READ)
	public ViewData getChartData(@TeaParam(name = "sectionId", alias = "sectionId") int sectionId,
			@TeaParam(name = "chartId", alias = "chartId") int chartId,
			@TeaParam(name = "dimLevel", alias = "dimLevel") String dimLevel,
			@TeaParam(name = "threshold", alias = "threshold") Long threshold,
			@TeaParam(name = "appName", alias = "appName") String appName) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			if ("BusinessEvents".equals(appName)) {
				Monitorable agent = BEMMServiceProviderManager.getInstance().getBeTeaAgentMonitoringService()
						.getAgent();
				return BEMMServiceProviderManager.getInstance().getMetricVisulizationService().getChart(agent,
						sectionId, chartId, dimLevel, threshold, agent.getName());
			}
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		return null;
	}

	@Customize(value = "label:Discover BE Home")
	@TeaOperation(name = "discoverBEHome", description = "", methodType = MethodType.UPDATE)
	public MasterHost discoverBEHome(@TeaParam(name = "hosts", alias = "hosts") List<String> selectedHosts) {
		try {
			return masterHostManagementService.discoverBEHome(selectedHosts);
		} catch (Exception e) {
			throw logErrorMessage(e);
		}
	}

	@Customize(value = "label:Get Memory Pools")
	@TeaOperation(name = "getMemeoryPools", description = "Get the MomoryPools", methodType = MethodType.READ)
	public List<String> getMemeoryPools() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return applicationService.getMemoryPools();
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get Memory by Pool Name")
	@TeaOperation(name = "getMemeoryByPoolName", description = "Get the memeory details by pool name", methodType = MethodType.READ)
	public MemoryUsage getMemeoryByPoolName(@TeaParam(name = "poolName", alias = "poolName") String poolName) {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			BeTeaAgentMonitorable agent = BEMMServiceProviderManager.getInstance().getBeTeaAgentMonitoringService()
					.getAgent();
			return applicationService.getMemoryByPoolName(poolName, agent);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Get GC Details")
	@TeaOperation(name = "getGCDetails", description = "Get the garbage collection details of  BeTeaAgent", methodType = MethodType.READ)
	public OperationResult getGCDetails() {
		try {
			applicationService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			BeTeaAgentMonitorable agent = BEMMServiceProviderManager.getInstance().getBeTeaAgentMonitoringService()
					.getAgent();
			return applicationService.getGarbageCollectionDetails(agent);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			applicationService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}


}
