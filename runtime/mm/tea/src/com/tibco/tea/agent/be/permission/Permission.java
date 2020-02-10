package com.tibco.tea.agent.be.permission;

/**
 * The class used to define the default permissions of the user to access the
 * application functionality.
 * 
 * @author dijadhav
 *
 */
public class Permission {

	/*-------------BE OPERATION PERMISSIONS-------------------*/

	public static final String START_PU_INSTANCE_PERMISSION = "START_PU_INSTANCE_PERMISSION";

	public static final String START_PU_INSTANCE_PERMISSION_DESCRIPTION = "Permission to start a PU instance";

	public static final String STOP_PU_INSTANCE_PERMISSION = "STOP_PU_INSTANCE_PERMISSION";

	public static final String STOP_PU_INSTANCE_PERMISSION_DESCRIPTION = "Permission to stop a PU instance";

	public static final String CREATE_DEPLOYMENT_PERMISSION = "CREATE_DEPLOYMENT_PERMISSION";

	public static final String CREATE_DEPLOYMENT_PERMISSION_DESCRIPTION = "Permission to create/import an application deployment";

	public static final String DELETE_DEPLOYMENT_PERMISSION = "DELETE_DEPLOYMENT_PERMISSION";

	public static final String DELETE_DEPLOYMENT_PERMISSION_DESCRIPTION = "Permission to delete an application deployment";

	public static final String UPDATE_DEPLOYMENT_PERMISSION = "UPDATE_DEPLOYMENT_PERMISSION";

	public static final String UPDATE_DEPLOYMENT_PERMISSION_DESCRIPTION = "Permission to update/edit an application deployment";

	public static final String CREATE_INSTANCE_PERMISSION = "CREATE_INSTANCE_PERMISSION";

	public static final String CREATE_INSTANCE_PERMISSION_DESCRIPTION = "Permission to create a PU Instance";

	public static final String DELETE_INSTANCE_PERMISSION = "DELETE_INSTANCE_PERMISSION";

	public static final String DELETE_INSTANCE_PERMISSION_DESCRIPTION = "Permission to delete a PU Instance";

	public static final String UPDATE_INSTANCE_PERMISSION = "UPDATE_INSTANCE_PERMISSION";

	public static final String UPDATE_INSTANCE_PERMISSION_DESCRIPTION = "Permission to update/edit a PU Instance";

	public static final String CREATE_HOST_PERMISSION = "CREATE_HOST_PERMISSION";

	public static final String CREATE_HOST_PERMISSION_DESCRIPTION = "Permission to create a Host";

	public static final String DELETE_HOST_PERMISSION = "DELETE_HOST_PERMISSION";

	public static final String DELETE_HOST_PERMISSION_DESCRIPTION = "Permission to delete a Host";

	public static final String UPDATE_HOST_PERMISSION = "UPDATE_HOST_PERMISSION";

	public static final String UPDATE_HOST_PERMISSION_DESCRIPTION = "Permission to update/edit a Host";

	public static final String DEPLOY_INSTANCE_PERMISSION = "DEPLOY_INSTANCE_PERMISSION";

	public static final String DEPLOY_INSTANCE_PERMISSION_DESCRIPTION = "Permission to deploy a PU instance";

	public static final String UNDEPLOY_INSTANCE_PERMISSION = "UNDEPLOY_INSTANCE_PERMISSION";

	public static final String UNDEPLOY_INSTANCE_PERMISSION_DESCRIPTION = "Permission to undeploy a server instance";

	public static final String UPDATE_GV_VAR_PERMISSION = "UPDATE_GV_VAR_PERMISSION";

	public static final String UPDATE_GV_VAR_PERMISSION_DESCRIPTION = "Permission to update Global Variables";

	public static final String UPDATE_LOG_LEVEL_PERMISSION = "UPDATE_LOG_LEVEL_PERMISSION";

	public static final String UPDATE_LOG_LEVEL_PERMISSION_DESCRIPTION = "Permission to update Log Levels";

	public static final String UPDATE_SYSTEM_PROPS_PERMISSION = "UPDATE_SYSTEM_PROPS_PERMISSION";

	public static final String UPDATE_SYSTEM_PROPS_PERMISSION_DESCRIPTION = "Permission to update System Properties";

	public static final String UPDATE_JVM_PROPS_PERMISSION = "UPDATE_JVM_PROPS_PERMISSION";

	public static final String UPDATE_JVM_PROPS_PERMISSION_DESCRIPTION = "Permission update JVM Properties";
	
	public static final String UPDATE_DEP_VAR_PERMISSION = "UPDATE_DEP_VAR_PERMISSION";

	public static final String UPDATE_DEP_VAR_PERMISSION_DESCRIPTION = "Permission to update Deployment Variables";

	public static final String HOT_DEPLOY_PERMISSION = "HOT_DEPLOY_PERMISSION";

	public static final String HOT_DEPLOY_PERMISSION_DESCRIPTION = "Permission to hot deploy";

	public static final String KILL_INSTANCE_PERMISSION = "KILL_INSTANCE_PERMISSION";

	public static final String KILL_INSTANCE_PERMISSION_DESCRIPTION = "Permission to kill a PU Instance";

	public static final String COPY_INSTANCE_PERMISSION = "COPY_INSTANCE_PERMISSION";

	public static final String COPY_INSTANCE_PERMISSION_DESCRIPTION = "Permission to copy a PU Instance";

	public static final String SUSPEND_AGENT_PERMISSION = "SUSPEND_AGENT_PERMISSION";

	public static final String SUSPEND_AGENT_PERMISSION_DESCRIPTION = "Permission to suspend running BE agent";

	public static final String RESUME_AGENT_PERMISSION = "RESUME_AGENT_PERMISSION";

	public static final String RESUME_AGENT_PERMISSION_DESCRIPTION = "Permission to resume running BE agent";

	public static final String UPLOAD_TRA_PERMISSION = "UPLOAD_TRA_PERMISSION";

	public static final String UPLOAD_TRA_PERMISSION_DESCRIPTION = "Permission to upload the tra";

	public static final String UPLOAD_CLASSES_PERMISSION = "UPLOAD_CLASSES_PERMISSION";

	public static final String UPLOAD_CLASSES_PERMISSION_DESCRIPTION = "Permission to upload the classes";

	public static final String DEPLOY_CLASSES_PERMISSION = "DEPLOY_CLASSES_PERMISSION";

	public static final String DEPLOY_CLASSES_PERMISSION_DESCRIPTION = "Permission to deploy the classes";

	public static final String UPLOAD_RULE_TEMPLATE_PERMISSION = "UPLOAD_CLASSES_PERMISSION";

	public static final String UPLOAD_RULE_TEMPLATE_PERMISSION_DESCRIPTION = "Permission to uplod rule template";

	public static final String DEPLOY_RULE_TEMPLATE_PERMISSION = "DEPLOY_RULE_TEMPLATE_PERMISSION";

	public static final String DEPLOY_RULE_TEMPLATE_PERMISSION_DESCRIPTION = "Permission to deploy the rule template";

	public static final String BE_TEA_AGENT_READ_PERMISSION = "BE_TEA_AGENT_READ_PERMISSION";

	public static final String BE_TEA_AGENT_READ_PERMISSION_DESCRIPTION = "Read permission for BE TEA agent resources";

	public static final String UPDATE_BE_PROPS_PERMISSION = "UPDATE_BE_PROPS_PERMISSION";

	public static final String UPDATE_BE_PROPS_PERMISSION_DESCRIPTION = "Permission to update business events property";

	public static final String CREATE_RULE_PERMISSION = "CREATE_RULE_PERMISSION";
	
	public static final String CREATE_RULE_PERMISSION_DESCRIPTION = "Permission to create the rule";

	public static final String UPDATE_RULE_PERMISSION = "UPDATE_RULE_PERMISSION";
	
	public static final String UPDATE_RULE_PERMISSION_DESCRIPTION = "Permission to update the rule";

	public static final String DELETE_RULE_PERMISSION = "DELETE_RULE_PERMISSION";
	
	public static final String DELETE_RULE_PERMISSION_DESCRIPTION = "Permission to delete the rule";

	public static final String GET_RULES_PERMISSION = "GET_RULES_PERMISSION";
	
	public static final String GET_RULES_PERMISSION_DESCRIPTION = "Permission to get the rules";

	public static final String RULE_ADMIN_PERMISSION = "RULE_ADMIN_PERMISSION";
	
	public static final String RULE_ADMIN_PERMISSION_DESCRIPTION = "Permission of the rule admin";

	public static final String CLEAR_ALERTS_PERMISSION = "CLEAR_ALERTS_PERMISSION";
	
	public static final String CLEAR_ALERTS_PERMISSION_DESCRIPTION = "Permission to clear the alerts";

	public static final String GET_ALERTS_PERMISSION = "GET_ALERTS_PERMISSION";
	
	public static final String GET_ALERTS_PERMISSION_DESCRIPTION = "Permission to get the alerts";

	public static final String ADD_PROFILE_PERMISSION = "ADD_PROFILE_PERMISSION";

	public static final String UPDATE_PROFILE_PERMISSION = "UPDATE_PROFILE_PERMISSION";

	public static final String DELETE_PROFILE_PERMISSION = "DELETE_PROFILE_PERMISSION";
	
	public static final String SET_DEFAULT_PROFILE_PERMISSION = "SET_DEFAULT_PROFILE_PERMISSION";
	
	public static final String ADD_PROFILE_PERMISSION_DESCRIPTION = "Permission to add the new profile";

	public static final String UPDATE_PROFILE_PERMISSION_DESCRIPTION = "Permission to update the existing profile";

	public static final String DELETE_PROFILE_PERMISSION_DESCRIPTION = "Permission to delete the existing profile";
	
	public static final String SET_DEFAULT_PROFILE_PERMISSION_DESCRIPTION = "Permission to set the default profile";
	
	public static final String UPLOAD_CUSTOM_JAR_PERMISSION = "UPLOAD_CUSTOM_JAR";
	
	public static final String UPLOAD_CUSTOM_JAR_PERMISSION_DESCRIPTION = "Permission to upload custom jar";

	// MORE Permissions to be added here

	/*-------------------------------------------------------*/

}
