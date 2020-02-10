package com.tibco.cep.bemm.common.message.impl;

/**
 * This class is used to hold the keys of message
 * 
 * @author dijadhav
 *
 */
public class MessageKey {

	// Application Success Message
	public static final String APPLICATION_CREATE_SUCCESS_MESSAGE = "be.tea.agent.application.create.success";
	public static final String APPLICATION_DELETE_SUCCESS_MESSAGE = "be.tea.agent.application.delete.success";
	public static final String APPLICATION_EDIT_SUCCESS_MESSAGE = "be.tea.agent.application.edit.success";
	public static final String APPLICATION_IMPORT_SUCCESS_MESSAGE = "be.tea.agent.application.import.success";
	public static final String APPLICATION_INSTANCE_START_SUCCESS_MESSAGE = "be.tea.agent.application.start.instance.success";
	public static final String APPLICATION_INSTANCE_STOP_SUCCESS_MESSAGE = "be.tea.agent.application.stop.instance.success";
	public static final String APPLICATION_INSTANCE_DEPLOY_SUCCESS_MESSAGE = "be.tea.agent.application.deploy.instance.success";
	public static final String APPLICATION_INSTANCE_REDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.application.redeploy.instance.success";
	public static final String APPLICATION_INSTANCE_HOTDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.application.hotdeploy.instance.success";
	public static final String APPLICATION_INSTANCE_UNDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.application.undeploy.instance.success";
	public static final String APPLICATION_INSTANCE_SAVE_GV_SUCCESS_MESSAGE = "be.tea.agent.application.save.gv.success";
	public static final String APPLICATION_INSTANCE_SAVE_SV_SUCCESS_MESSAGE = "be.tea.agent.application.save.sv.success";
	public static final String APPLICATION_INSTANCE_SAVE_JP_SUCCESS_MESSAGE = "be.tea.agent.application.save.jp.success";
	public static final String APPLICATION_CONFIG_SAVE_SUCCESS_MESSAGE = "be.tea.agent.application.save.config.success";
	public static final String CLASS_FILES_UPLOAD_SUCCESS_MESSAGE = "be.tea.agent.application.class.files.uploaded.success";
	public static final String CLASS_FILES_DEPLOY_SUCCESS_MESSAGE = "be.tea.agent.application.class.files.deploy.success";
	public static final String APPLICATION_EDITED_MESSAGE = "be.tea.agent.application.edited";
	public static final String APPLICATION_CREATED_MESSAGE = "be.tea.agent.application.created";
	public static final String APPLICATION_IMPORTED_MESSAGE = "be.tea.agent.application.imported";
	public static final String APPLICATION_LOG_PATTERN_LEVEL_SAVE_SUCCESS_MESSAGE = "be.tea.agent.application.log.pattern.level.save.success";
	public static final String APPLICATION_BUSINESSEVENTS_PROPERTIES_SAVE_SUCCESS_MESSAGE = "be.tea.agent.application.businessevents.properties.save.success";
	public static final String APPLICATION_PROFILE_DELETED = "be.tea.agent.application.profile.deleted";
	public static final String APPLICATION_PROFILE_UPDATED = "be.tea.agent.application.profile.updated";
	public static final String APPLICATION_DEFAULT_PROFILE_UPDATED = "be.tea.agent.application.default.profile.updated";
	public static final String APPLICATION_CREATING_MESSAGE = "be.tea.agent.application.creating";

	// Application Error Message
	public static final String APPLICATION_CREATE_ERROR_MESSAGE = "be.tea.agent.application.create.error";
	public static final String APPLICATION_DELETE_ERROR_MESSAGE = "be.tea.agent.application.delete.error";
	public static final String APPLICATION_EDIT_ERROR_MESSAGE = "be.tea.agent.application.edit.error";
	public static final String APPLICATION_IMPORT_ERROR_MESSAGE = "be.tea.agent.application.import.error";
	public static final String APPLICATION_INSTANCE_START_ERROR_MESSAGE = "be.tea.agent.application.start.instance.error";
	public static final String APPLICATION_INSTANCE_STOP_ERROR_MESSAGE = "be.tea.agent.application.stop.instance.error";
	public static final String APPLICATION_INSTANCE_DEPLOY_ERROR_MESSAGE = "be.tea.agent.application.deploy.instance.error";
	public static final String APPLICATION_INSTANCE_REDEPLOY_ERROR_MESSAGE = "be.tea.agent.application.redeploy.instance.error";
	public static final String APPLICATION_INSTANCE_HOTDEPLOY_ERROR_MESSAGE = "be.tea.agent.application.hotdeploy.instance.error";
	public static final String APPLICATION_INSTANCE_UNDEPLOY_ERROR_MESSAGE = "be.tea.agent.application.undeploy.instance.error";
	public static final String APPLICATION_INSTANCE_SAVE_GV_ERROR_MESSAGE = "be.tea.agent.application.save.gv.error";
	public static final String APPLICATION_INSTANCE_SAVE_SV_ERROR_MESSAGE = "be.tea.agent.application.save.sv.error";
	public static final String APPLICATION_INSTANCE_SAVE_JP_ERROR_MESSAGE = "be.tea.agent.application.save.jp.error";
	public static final String APPLICATION_LOAD_CDD_ERROR_MESSAGE = "be.tea.agent.aplication.load.cdd.error";
	public static final String APPLICATION_CONFIG_SAVE_ERROR_MESSAGE = "be.tea.agent.application.save.config.error";
	public static final String BE_AGENT_DESCRIPTION = "be.agent.description";
	public static final String APPLICATION_DUPLICATE_ERROR_MESSAGE = "be.tea.agent.application.duplicate.error";
	public static final String APPLICATION_AUTHENTICATION_ERROR_MESSAGE = "be.tea.agent.application.authentication.error";
	public static final String CLASS_FILES_UPLOAD_ERROR_MESSAGE = "be.tea.agent.application.class.files.upload.error";
	public static final String APPLICATION_CREATE_INCOMPATIBLE_CDD_ERROR = "be.tea.agent.application.create.incompatible.cdd.error";
	public static final String APPLICATION_EDIT_INCOMPATIBLE_CDD_ERROR = "be.tea.agent.application.edit.incompatible.cdd.error";
	public static final String APPLICATION_DELETE_ALL_INSTANCE_MUST_UNDEPLOYED = "be.tea.agent.application.delete.all.instance.undeployed";
	public static final String APPLICATION_DELETE_ALL_INSTANCE_MUST_STOPPED = "be.tea.agent.application.delete.all.instance.stopped";
	public static final String APPLICATION_DOES_NOT_EXIST_ERROR_MESSAGE = "be.tea.agent.application.does.not.exist.error";
	public static final String APPLICATION_DOES_NOT_HAVE_INSTANCES = "be.tea.agent.application.does.not.have.instances";
	public static final String APPLICATION_LOG_PATTERN_LEVEL_SAVE_ERROR_MESSAGE = "be.tea.agent.application.save.group.log.pattern.level.error";
	public static final String APPLICATION_BUSINESSEVENTS_PROPERTIES_SAVE_ERROR_MESSAGE = "be.tea.agent.application.save.group.businessevents.properties.error";
	public static final String APPLICATION_NOT_FOUND_ERROR = "be.tea.agent.application.not.found.error";
	public static final String APPLICATION_EXPORT_ERROR_MESSAGE = "be.tea.agent.application.export.error";
	public static final String APPLICATION_IMPORT_ERROR_DESCRIPTION = "be.tea.agent.application.import.error.description";
	public static final String APPLICATION_SAVE_PROFILE_ERROR = "be.tea.agent.application.save.profile.error";
	public static final String APPLICATION_INITIALIZE_DATA_POLLER_EXECUTOR_SERVICE_ERROR = "be.tea.agent.application.initialize.data.poller.executor.service.error";
	
	// Host Success Message
	public static final String HOST_CREATE_SUCCESS_MESSAGE = "be.tea.agent.host.create.success";
	public static final String HOST_DELETE_SUCCESS_MESSAGE = "be.tea.agent.host.delete.success.message";
	public static final String HOST_EDIT_SUCCESS_MESSAGE = "be.tea.agent.host.edit.success";
	public static final String HOST_IMPORT_SUCCESS_MESSAGE = "be.tea.agent.host.import.success";
	public static final String HOST_INSTANCE_START_SUCCESS_MESSAGE = "be.tea.agent.host.start.instance.success";
	public static final String HOST_INSTANCE_STOP_SUCCESS_MESSAGE = "be.tea.agent.host.stop.instance.success";
	public static final String HOST_INSTANCE_DEPLOY_SUCCESS_MESSAGE = "be.tea.agent.host.deploy.instance.success";
	public static final String HOST_INSTANCE_REDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.host.redeploy.instance.success";
	public static final String HOST_INSTANCE_HOTDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.host.hotdeploy.instance.success";
	public static final String HOST_INSTANCE_UNDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.host.undeploy.instance.success";
	public static final String HOST_INSTANCE_SAVE_GV_SUCCESS_MESSAGE = "be.tea.agent.host.save.gv.success";
	public static final String HOST_INSTANCE_SAVE_SV_SUCCESS_MESSAGE = "be.tea.agent.host.save.sv.success";
	public static final String HOST_INSTANCE_SAVE_JP_SUCCESS_MESSAGE = "be.tea.agent.host.save.jp.success";
	public static final String HOST_NOT_AUTHENTICATED_MESSAGE = "be.tea.agent.host.not.authenticated";
	public static final String MASTER_HOST_DESCRIPTION = "be.tea.agent.master.host.description";
	public static final String APPLICATION_HOST_DESCRIPTION = "be.tea.agent.application.host.description";
	public static final String HOST_LOG_PATTERN_LEVEL_SAVE_SUCCESS_MESSAGE = "be.tea.agent.host.log.pattern.level.save.success";
	public static final String HOST_BUSINESSEVENTS_PROPERTIES_SAVE_SUCCESS_MESSAGE = "be.tea.agent.host.businessevents.properties.save.success";

	// Host Error Message
	public static final String HOST_CREATE_ERROR_MESSAGE = "be.tea.agent.host.create.error";
	public static final String HOST_DELETE_ERROR_MESSAGE = "be.tea.agent.host.delete.error";
	public static final String HOST_EDIT_ERROR_MESSAGE = "be.tea.agent.host.edit.error";
	public static final String HOST_INSTANCE_START_ERROR_MESSAGE = "be.tea.agent.host.start.instance.error";
	public static final String HOST_INSTANCE_STOP_ERROR_MESSAGE = "be.tea.agent.host.stop.instance.error";
	public static final String HOST_INSTANCE_DEPLOY_ERROR_MESSAGE = "be.tea.agent.host.deploy.instance.error";
	public static final String HOST_INSTANCE_REDEPLOY_ERROR_MESSAGE = "be.tea.agent.host.redeploy.instance.error";
	public static final String HOST_INSTANCE_HOTDEPLOY_ERROR_MESSAGE = "be.tea.agent.host.hotdeploy.instance.error";
	public static final String HOST_INSTANCE_UNDEPLOY_ERROR_MESSAGE = "be.tea.agent.host.undeploy.instance.error";
	public static final String HOST_INSTANCE_SAVE_GV_ERROR_MESSAGE = "be.tea.agent.host.save.gv.error";
	public static final String HOST_INSTANCE_SAVE_SV_ERROR_MESSAGE = "be.tea.agent.host.save.sv.error";
	public static final String HOST_INSTANCE_SAVE_JP_ERROR_MESSAGE = "be.tea.agent.host.save.jp.error";
	public static final String HOST_DUPLICATE_NAME_ERROR_MESSAGE = "be.tea.agent.host.duplicate.name.error";
	public static final String HOST_DUPLICATE_IP_ERROR_MESSAGE = "be.tea.agent.host.duplicate.ip.error";
	public static final String HOST_AUTHENTICATION_ERROR_MESSAGE = "be.tea.agent.host.authentication.error";
	public static final String HOST_EXIST_ERROR_MESSAGE = "be.tea.agent.host.exist.error";
	public static final String HOST_GET_ERROR_MESSAGE = "be.tea.agent.host.get.error";
	public static final String HOST_GET_JMX_CONNECTION_PORT_ERROR_MESSAGE = "be.tea.agent.host.get.jmx.connection.port.error";
	public static final String HOST_LOG_PATTERN_LEVEL_SAVE_ERROR_MESSAGE = "be.tea.agent.host.save.group.log.pattern.level.error";
	public static final String HOST_BUSINESSEVENTS_PROPERTIES_SAVE_ERROR_MESSAGE = "be.tea.agent.host.save.group.businessevents.properties.error";
	public static final String HOST_UPLOAD_TRA_ERROR_MESSAGE = "be.tea.agent.host.upload.tra.error";
	public static final String HOST_GET_TRA_FILE_ERROR = "be.tea.agent.host.get.tra.file.error";
	public static final String HOST_NO_SSH_PORT_SPECIFIED = "be.tea.agent.host.no.ssh.port.specified";
	public static final String ILLEGAL_SSH_PORT_ATTEMPTING_CONNECTION_WITH_DEFAULT_PORT = "be.tea.agent.host.illegal.ssh.port.attempting.connection.with.default.port";
	public static final String HOST_BE_HOME_ADDED_MESSAGE = "be.tea.agent.host.be.home.added";
	public static final String HOST_BE_HOME_DUPLICATE_MESSAGE = "be.tea.agent.host.be.home.duplicate";
	public static final String HOST_CONNECTION_ERROR_MESSAGE = "be.tea.agent.host.connection.error";
	public static final String SPECIFY_BE_HOME_ERROR_MESSAGE = "be.tea.agent.host.specify.be.home.error";
	public static final String HOST_NOT_AUTHENTICATED_ERROR_MESSAGE = "be.tea.agent.host.not.authenticated";
	
	// Instance Success Message
	public static final String INSTANCE_CREATE_SUCCESS_MESSAGE = "be.tea.agent.instance.create.success";
	public static final String INSTANCE_DELETE_SUCCESS_MESSAGE = "be.tea.agent.instance.delete.success";
	public static final String INSTANCE_EDIT_SUCCESS_MESSAGE = "be.tea.agent.instance.edit.success";
	public static final String INSTANCE_START_SUCCESS_MESSAGE = "be.tea.agent.instance.start.success";
	public static final String INSTANCE_STOP_SUCCESS_MESSAGE = "be.tea.agent.instance.stop.success";
	public static final String INSTANCE_DEPLOY_SUCCESS_MESSAGE = "be.tea.agent.instance.deploy.success";
	public static final String INSTANCE_HOTDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.instance.hotdeploy.success";
	public static final String INSTANCE_REDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.instance.redeploy.success";
	public static final String INSTANCE_UNDEPLOY_SUCCESS_MESSAGE = "be.tea.agent.instance.undeploy.success";
	public static final String INSTANCE_SAVE_GV_SUCCESS_MESSAGE = "be.tea.agent.instance.save.gv.success";
	public static final String INSTANCE_SAVE_SV_SUCCESS_MESSAGE = "be.tea.agent.instance.save.sv.success";
	public static final String INSTANCE_SAVE_JP_SUCCESS_MESSAGE = "be.tea.agent.instance.save.jp.success";
	public static final String INSTANCE_CLONE_SUCCESS_MESSAGE = "be.tea.agent.instance.clone.success";
	public static final String INSTANCE_CONFIG_SAVE_SUCCESS_MESSAGE = "be.tea.agent.instance.save.config.success";
	public static final String INSTANCE_UPDATE_SUCCESS_MESSAGE = "be.tea.agent.instance.update.suceess";
	public static final String INSTANCE_CREATED_MESSAGE = "be.tea.agent.instance.create.message";
	public static final String INSTANCE_DETAILS_UPDATED_MESSAGE = "be.tea.agent.instance.deatils.updated.success";
	public static final String INSTANCE_UPDATED_MESSAGE = "be.tea.agent.instance.updated.message";
	public static final String LOG_PATTERN_APPLIED_SUCCESS_MESSAGE = "be.tea.agent.instance.log.pattern.applied.success";
	public static final String INSTANCE_ALREADY_DEPLOY_MESSAGE = "be.tea.agent.instance.already.deploy";
	public static final String INSTANCE_DEPLOYED_DESCRIPTION = "be.tea.agent.instance.deployed.description";
	public static final String INSTANCE_STARTED_MESSAGE = "be.tea.agent.instance.started";
	public static final String INSTANCE_NOT_DEPLOYED = "be.tea.agent.instance.not.deployed";
	public static final String STOP_INSTANCE_TO_UNDEPLOY = "be.tea.agent.stop.instance.to.undeploy";
	public static final String INSTANCE_UNDEPLOYED_DESCRIPTION = "be.tea.agent.instance.undeploy.description";
	public static final String SERVICE_INSTANCE_STOPPED_SUCCESS_MESSAGE = "be.tea.agent.service.instance.stopped.success";
	public static final String SERVICE_INSTANCE_KILLED_SUCCESS_MESSAGE = "be.tea.agent.service.instance.kill.success";
	public static final String INSTANCE_DEFAULT_PROFILE_UPDATED_MESSAGE = "be.tea.agent.instance.default.profile.updated.success";
	public static final String INSTANCE_PROFILE_UPDATED_SUCCESS_MESSAGE = "be.tea.agent.instance.profile.updated.success";
	public static final String GLOBAL_VARIBALE_CHANGED_DESCRIPTION = "be.tea.agent.global.variable.changed.description";
	public static final String GLOBAL_VARIBALE_UPDATED_MESSAGE = "be.tea.agent.global.variable.updated.message";
	public static final String SYSTEM_VARIBALE_CHANGED_DESCRIPTION = "be.tea.agent.system.variable.changed.description";
	public static final String SYSTEM_VARIBALE_UPDATED_MESSAGE = "be.tea.agent.system.variable.updated.message";
	public static final String JVM_PROPERTIES_CHANGED_DESCRIPTION = "be.tea.agent.jmx.properties.changed.description";
	public static final String JVM_PROPERTIES_UPDATED_MESSAGE = "be.tea.agent.jmx.properties.updated.message";
	public static final String LOGGER_PATTERN_DESCRIPTION = "be.tea.agent.logger.pattern.description";
	public static final String LOGGER_PATTERN_STORED_SUCCESS_MESSAGE = "be.tea.agent.logger.pattern.stored.success";
	public static final String BE_PROERTIES_CHANGED_DESCRIPTION = "be.tea.agent.BE.properties.changed.description";
	public static final String BE_PROERTIES_SAVED_SUCCESS_MESSAGE = "be.tea.agent.BE.properties.saved.success";
	public static final String BE_PROERTIES_UPDATED_MESSAGE = "be.tea.agent.BE.properties.updated";
	public static final String SELECTED_INSTANCE_NOT_DEPLOYED_MESSAGE = "be.tea.agent.selected.instance.not.deployed";
	public static final String INSTANCE_DEFAULT_PROFILE_UPDATED_DESCRIPTION = "be.tea.agent.instance.default.profile.updated.description";
	public static final String INSTANCE_STOPPED = "be.tea.agent.instance.stopped";
	public static final String INSTANCE_CREATE = "be.tea.agent.instance.create";

	
	// Instance Error Message
	public static final String INSTANCE_CREATE_ERROR_MESSAGE = "be.tea.agent.instance.create.error";
	public static final String INSTANCE_DELETE_ERROR_MESSAGE = "be.tea.agent.instance.delete.error";
	public static final String INSTANCE_EDIT_ERROR_MESSAGE = "be.tea.agent.instance.edit.error";
	public static final String INSTANCE_START_ERROR_MESSAGE = "be.tea.agent.instance.start.error";
	public static final String INSTANCE_STOP_ERROR_MESSAGE = "be.tea.agent.instance.stop.error";
	public static final String INSTANCE_DEPLOY_ERROR_MESSAGE = "be.tea.agent.instance.deploy.error";
	public static final String INSTANCE_HOTDEPLOY_ERROR_MESSAGE = "be.tea.agent.instance.hotdeploy.error";
	public static final String INSTANCE_REDEPLOY_ERROR_MESSAGE = "be.tea.agent.instance.redeploy.error";
	public static final String INSTANCE_UNDEPLOY_ERROR_MESSAGE = "be.tea.agent.instance.undeploy.error";
	public static final String INSTANCE_SAVE_GV_ERROR_MESSAGE = "be.tea.agent.instance.save.gv.error";
	public static final String INSTANCE_SAVE_SV_ERROR_MESSAGE = "be.tea.agent.instance.save.sv.error";
	public static final String INSTANCE_SAVE_JP_ERROR_MESSAGE = "be.tea.agent.instance.save.jp.error";
	public static final String INSTANCE_CLONE_ERROR_MESSAGE = "be.tea.agent.instance.clone.error";
	public static final String INSTANCE_CONFIG_SAVE_ERROR_MESSAGE = "be.tea.agent.instance.save.config.error";
	public static final String INSTANCE_DUPLICATE_ERROR_MESSAGE = "be.tea.agent.instance.duplicate.error";
	public static final String INSTANCE_DUPLICATE_JMX_PORT_ERROR_MESSAGE = "be.tea.agent.instance.duplicate.jmx.port.error";
	public static final String INSTANCE_AUTHENTICATION_ERROR_MESSAGE = "be.tea.agent.instance.authentication.error";
	public static final String INSTANCE_PU_UNDEFINED_ERROR = "be.tea.agent.instance.pu.undefined.error";
	public static final String INSTANCE_DOWNLOAD_LOG_ERROR_MESSAGE = "be.tea.agent.instance.download.log.error";
	public static final String INSTANCE_INITIALIZE_INSTANCE_ERROR_MESSAGE = "be.tea.agent.instance.initialize.instance.error";
	public static final String SERVICE_INSTANCE_NOT_RUNNING_ERROR_MESSAGE = "be.tea.agent.service.instace.not.running.error";
	public static final String INSTANCE_EDIT_DEPLOYED_INSTANCE_ERROR_MESSAGE = "be.tea.agent.instance.edit.deployed.instance.error";
	public static final String INSTANCE_SAVE_LOG_LEVEL_ERROR_MESSAGE = "be.tea.agent.instance.save.log.level.error.message";
	public static final String INSTANCE_SAVE_BE_PROPERTIES_ERROR_MESSAGE = "be.tea.agent.instance.save.be.properties.error";
	public static final String INSTANCE_KILLED_ERROR_MESSAGE = "be.tea.agent.instance.killed.error";
	public static final String INSTANCE_LOG_FILE_NOT_EXIST_ERROR = "be.tea.agent.instance.log.file.not.exist.error";
	public static final String INSTANCE_NOT_RUNNING_ERROR = "be.tea.agent.instance.not.running";
	public static final String INSTANCE_INITIALZED_BEAPPLICATIONSMANAGEMENTSERVICE_ERROR = "be.tea.agent.instance.initialzed.beapplicationsmanagementservice.error";
	public static final String INSTANCE_SELECTED_DEPLOY_ERROR = "be.tea.agent.instance.selected.deploy.error";
	public static final String INSTANCE_SELECTED_UNDEPLOY_ERROR = "be.tea.agent.instance.selected.undeploy.error";
	public static final String INSTANCE_SELECTED_START_ERROR = "be.tea.agent.instance.selected.start.error";
	public static final String INSTANCE_SELECTED_STOP_ERROR = "be.tea.agent.instance.selected.stop.error";
	public static final String INSTANCE_SELECTED_KILL_ERROR = "be.tea.agent.instance.selected.kill.error";
	
	public static final String SERVICE_INSTANCE_LOAD_GV_ERROR_MESSAGE = "be.tea.agent.service.instance.load.gv.error";
	public static final String SERVICE_INSTANCE_LOAD_SP_ERROR_MESSAGE = "be.tea.agent.service.instance.load.sp.error";
	public static final String SERVICE_INSTANCE_LOAD_JMX_PROPERTIES_ERROR_MESSAGE = "be.tea.agent.service.instance.load.jmx.properties.error";
	public static final String SERVICE_INSTANCE_LOAD_LOG_LEVEL_ERROR_MESSAGE = "be.tea.agent.service.instance.load.log.level.error";
	public static final String SERVICE_INSTANCE_LOAD_CONFIGURATION_ERROR_MESSAGE = "be.tea.agent.service.instance.load.configuration.error";
	public static final String SERVICE_INSTANCE_NOT_STOPPED = "be.tea.agent.service.instance.not.stopped";
	public static final String SERVICE_INSTANCE_NOT_DEPLOYED = "be.tea.agent.service.instance.not.deployed";
	public static final String LOG_PATTERN_APPLIED_ERROR_MESSAGE = "be.tea.agent.instance.log.pattern.applied.error";
	public static final String SERVICE_INSTANCE_DELETE_ERROR = "be.tea.agent.service.instance.delete.error";
	public static final String SERVICE_INSTANCE_HOT_DEPLOY_ERROR = "be.tea.agent.service.instance.hot.deploy.error";
	
	//Rule CRUD operations messages
	public static final String RULE_CREATE_SUCCESS_MESSAGE = "be.tea.agent.rule.create.success";
	public static final String RULE_CREATE_ERROR_MESSAGE = "be.tea.agent.rule.create.error";
	public static final String RULE_EXISTS_ERROR_MESSAGE = "be.tea.agent.rule.exists.error";
	public static final String RULE_UPDATE_SUCCESS_MESSAGE = "be.tea.agent.rule.update.success";
	public static final String RULE_UPDATE_ERROR_MESSAGE = "be.tea.agent.rule.update.error";
	public static final String RULE_DELETE_SUCCESS_MESSAGE = "be.tea.agent.rule.delete.success";
	public static final String RULE_DELETE_ERROR_MESSAGE = "be.tea.agent.rule.delete.error";
	public static final String RULE_STATUS_UPDATE_SUCCESS_MESSAGE = "be.tea.agent.rule.status.update.success";
	public static final String RULE_TEMPLATE_FILE_UPLOAD_SUCCESS_MESSAGE = "be.tea.agent.rule.template.file.upload.success";
	public static final String RULE_TEMPLATE_FILE_UPLOAD_ERROR_MESSAGE = "be.tea.agent.rule.template.file.upload.error";
	public static final String RULE_TEMPLATE_FILE_DEPLOY_SUCCESS_MESSAGE = "be.tea.agent.rule.template.file.deploy.success";
	public static final String RULE_TEMPLATE_FILE_DEPLOY_SUCCESS = "be.tea.agent.rule.template.file.deploy.success.message";
	
	//Alert operations message
	public static final String ALERTS_CLEARED_SUCCESS_MESSAGE = "be.tea.agent.alerts.clear.success";
	
	//Validation messages
	public static final String APPLICATION_INSTANCE_NULL_ERROR_MESSAGE = "be.tea.agent.application.instance.null.error";
	public static final String INSTANCE_NULL_ERROR_MESSAGE = "be.tea.agent.instance.null.error";
	public static final String BE_ARCHIVE_NULL_ERROR_MESSAGE = "be.tea.agent.be.archive.null.error";	
	public static final String HOST_NULL_ERROR_MESSAGE = "be.tea.agent.host.null.error";
	public static final String APPLICATION_NULL_ERROR_MESSAGE = "be.tea.agent.application.null.error";
	public static final String INVALID_INSTANCE_NAME_MESSAGE = "be.tea.agent.invalid.instance.name.error";
	public static final String INVALID_JMX_PORT_MESSAGE = "be.tea.agent.invalid.jmx.port.error";
	public static final String DUPLICATE_INSTANCE_MESSAGE = "be.tea.agent.duplicate.instance.error";
	public static final String INSTANCE_LOG_EXIST_ERROR_MESSAGE = "be.tea.agent.instance.log.exist.error";
	public static final String INVALID_PROFILE_NAME_MESSAGE = "be.tea.agent.invalid.profile.name";
	public static final String INVALID_OBJECT_KEY_MESSAGE = "be.tea.agent.invalid.object.key";
	public static final String UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE = "be.tea.agent.unsupported.execution.context.error";
	public static final String SERVICE_INSTANCE_NULL_ERROR_MESSAGE = "be.tea.agent.service.instance.null.error";
	public static final String HOST_NULL_SERVICE_INSTANCE_OBJECT_ERROR_MESSAGE = "be.tea.agent.host.null.service.instance.object.error";	
	public static final String APPLICATION_NULL_SERVICE_INSTANCE_HOST_OBJECT_MESSAGE = "be.tea.agent.application.null.service.instance.host.object.error";
    public static final String QUERY_GARBAGE_COLLECTION_POOL_NAMES_ERROR_MESSAGE = "be.tea.agent.query.garbage.collection.pool.names.error";
    public static final String INVALID_POOL_NAME_MESSAGE = "be.tea.agent.invalid.pool.name";
    public static final String PASSED_INSTANCE_LIST_EMPTY_MESSAGE = "be.tea.agent.passed.instace.list.empty.message";
    public static final String DEPLOYMENT_VARIABLES_NULL_ERROR_MESSAGE = "be.tea.agent.deployment.variables.null.error";
    public static final String INVALID_CDD_EAR_ERROR_MESSAGE = "be.tea.agent.invalid.ear.cdd.error";
    public static final String INVALID_APPLICATION_NAME = "be.tea.agent.invalid.application.name.error";
    public static final String INVALID_HOST_IP_ADDRESS = "be.tea.agent.invalid.host.ipaddress.error";
    public static final String PU_DELETE_FROM_CDD = "be.tea.agent.PU.delete.from.CDD";
    public static final String AGENT_CLASS_DELETE_FROM_CDD = "be.tea.agent.class.delete.from.CDD";
    public static final String INVALID_DEPLOYMENT_PATH = "be.tea.agent.invalid.deployment.path";
    public static final String INVALID_TRA_FILE = "be.tea.agent.invalid.tra.file";
    public static final String NOT_TRA_FILE = "be.tea.agent.not.tra.file";
    public static final String INCOMPATIBLE_CDD_VERSION = "be.tea.agent.incompatible.CDD.version";
    public static final String PROFILE_ALREADY_EXIST = "be.tea.agent.profile.already.exist";
    
    //Logger messages
    public static final String NOTIFICATION_SENT_ON_STATUS_CHANGE = "be.tea.agent.notification.event.sent.status.change";
    public static final String NOTIFICATION_SERVICE_NOT_INITIALIZED = "be.tea.agent.notification.service.not.initialized";
    public static final String JOB_STARTED_DELETE_DEPLOYMENT_CONFIGURATION = "be.tea.agent.job.started.delete.deployment.configuration";
    public static final String JOB_COMPLETED_DELETE_SERVICE_INSTANCE = "be.tea.agent.job.completed.delete.service.instance";
    public static final String INVOKED_CREATE_INSTANCE_METHOD = "be.tea.agent.invoked.create.instance.method";
    public static final String ADDING_MASTER_HOST_AS_APPLICATION_HOST = "be.tea.agent.addding.master.host.as.application.host";
    public static final String INSTANCE_STARTED_EXTERNALLY = "be.tea.agent.instance.started.externally";
    public static final String LOGGER_INSTANCE_CREATED_SUCCESS = "be.tea.agent.logger.instance.create.success";
    public static final String INVOKED_DEPLOY_SERVICE_INSTANCE_PAGE = "be.tea.agent.invoked.deploy.service.instance.page";
    public static final String IMPORTING_EXPORTED_ZIP = "be.tea.agent.importing.exported.zip";
    public static final String GET_GLOBAL_VARIABLES_ERROR = "be.tea.agent.get.global.variables.error";
    public static final String INVOKED_UNDEPLOY_SERVICE_INSTANCE_PAGE = "be.tea.agent.invoked.undeploy.service.instance.page";
    public static final String INVOKED_START_SERVICE_INSTANCE_PAGE = "be.tea.agent.invoked.start.service.instance.page";
    public static final String INVOKED_STOP_SERVICE_INSTANCE_PAGE = "be.tea.agent.invoked.stop.service.instance.page";
    public static final String INVOKED_COPYINSTANCE_METHOD = "be.tea.agent.invoked.copyinstance.method";
    public static final String CLONED_INSTANCE = "be.tea.agent.cloned.instance";
    public static final String PERSISTENING_DEPLOYMENT_VARIABLE = "be.tea.agent.persisteing.deployment.varibale";
    public static final String PERSISTED_DEPLOYMENT_VARIABLE = "be.tea.agent.persisted.deployment.varibale";
    public static final String SERVICE_INSTANCE_LOAD_GV_ERROR = "be.tea.agent.service.instance.load.gv.error";
    public static final String SERVICE_INSTANCE_LOAD_SV_ERROR = "be.tea.agent.service.instance.load.sv.error";
    public static final String SERVICE_INSTANCE_LOAD_JVM_PROPERTIES_ERROR = "be.tea.agent.service.instance.load.jvm.properties.error";
    public static final String STARTED_UPLOAD = "be.tea.agent.started.upload";
    public static final String COMPLETED_UPLOAD = "be.tea.agent.completed.upload";
    public static final String UPLOAD_FILE_ERROR = "be.tea.agent.upload.file.error.from";
    public static final String LOAD_TRA_PROPERTIES = "be.tea.agent.load.tra.properties";
    public static final String DEPLOYING_INSTANCE = "be.tea.agent.deploying.instance";
    public static final String DEPLOYED_INSTANCE = "be.tea.agent.deployed.instance";
    public static final String STARTING_SERVICE_INSTANCE = "be.tea.agent.starting.service.instance";
    public static final String STARTED_SERVICE_INSTANCE = "be.tea.agent.started.service.insatnce";
    public static final String ONLY_UNDEPLOYED_INSTANCES_DELETED = "be.tea.agent.undeployed.instances.deleted";
    public static final String STOPPED_DELETE_SERVICE_INSTANCE = "be.tea.agent.delete.service.instance.must.stopped";
    public static final String UPLOAD_FILES_ERROR = "be.tea.agent.upload.files.error";
    public static final String UPDATING_GV = "be.tea.agent.uploading.gv";
    public static final String CHECKING_INSTANCES_HAVE_SAME_JMX_PORT = "be.tea.agent.checking.instance.have.same.JMX.port";
    public static final String SERVICE_INSTANCE_SAME_JMX_PORT = "be.tea.agent.service.insatnce.same.JMX.port";
    public static final String COPYING_DEPLOYMENT_VARIABLES = "be.tea.agent.copying.deployement.varibales";
    public static final String DEPLOYMENT_VARIABELS = "be.tea.agent.deployement.variables";
    public static final String UPLOADED_ARTIFACTS_OF_INSTANCE = "be.tea.agent.uploaded.artifacts";
    public static final String UPDATING_REMOTE_TRA_PROPERTIES = "be.tea.agent.updating.remote.TRA.properties";
    public static final String OVERRIDDEN_TRA_EXIST_FOR_APPLICATION = "be.tea.agent.overridden.TRA.exist";
    public static final String WINDOWS_BE_EXECUTION_COMMAND = "be.tea.agent.BE.execution.command.windows";
    public static final String UNDEPLOYING_SERVICE_INSTANCE = "be.tea.agent.undeploying.service.insatnce";
    public static final String UNDEPLOYED_SERVICE_INSTANCE = "be.tea.agent.undeployed.service.insatnce";
    public static final String DEPLOYMENT_PATH = "be.tea.agent.deployment.path";
    public static final String DELETING_INSTANCE = "be.tea.agent.deleting.instance";
    public static final String UPLOADING_ARTIFACTS_OF_INSTANCE = "be.tea.agent.uploading.artifacts";
    public static final String FORMING_BE_EXECUTION_COMMAND = "be.tea.agent.forming.be.execution.command";
    public static final String INVOKED_METHOD_FROM_GROUP = "be.tea.agent.invoked.method.from.group";
    
    public static final String INITIALIZING_MASTER_HOST_REPOSITORY = "be.tea.agent.initializing.master.host.repository";
    public static final String INITIALIZED_MASTER_HOST_REPOSITORY = "be.tea.agent.initialized.master.host.repository";
    public static final String LOADING_EXISTING_HOSTS_CONFIGURATION = "be.tea.agent.loading.existing.hosts.configuration";
    public static final String LOADED_EXISTING_HOSTS_CONFIGURATION = "be.tea.agent.loaded.existing.hosts.configuration";
    public static final String HOST_ID = "be.tea.agent.host.id";
    public static final String NEW_HOST_ADDED_SUCCESS = "be.tea.agent.new.host.added.success";
    public static final String INSTANCES_DEPLOYED_HOST_EDIT_ERROR = "be.tea.agent.instances.deployed.host.edit.error";
    public static final String GET_BE_HOME_ERROR = "be.tea.agent.get.be.home.error";
    public static final String ENV_FILE_DOWNLOAD_HOST = "be.tea.agent.evn.file.download.host";
    public static final String ENV_FILE_DOWNLOAD_HOST_ERROR = "be.tea.agent.env.file.download.host.error";
    public static final String OVERRIDDEN_TRA_CONFIG_NOT_EXIST = "be.tea.agent.overridden.tra.config.not.exist";
    public static final String TRA_FILE_DOWNLOAD = "be.tea.agent.tra.file.download";
    public static final String CHECKING_OVERRIDDEN_TRA_CONFIG_FILE_EXISTENCE = "be.tea.agent.checking.overridden.tra.config.file.existance";
    public static final String DELETE_ALL_INSTANCES_TO_BOUND_MACHINE = "be.tea.agent.delete.all.instances.to.bound.machine";
    public static final String HOST_DELETE_SUCCESS = "be.tea.agent.host.delete.success";
    public static final String CREATING_NEW_MASTER_HOST = "be.tea.agent.creating.new.master.host";
    public static final String CREATED_NEW_MASTER_HOST = "be.tea.agent.created.new.master.host";
    public static final String HOST_SETTING_RUNNING_STATUS = "be.tea.agent.host.setting.running.status";
    public static final String UNKNOWN_HOST = "be.tea.agent.unknown.host";
    public static final String DELETE_SELECTED_HOST_ERROR = "be.tea.agent.delete.selected.host.error";
    public static final String HOST_FAILED = "be.tea.agent.host.failed";
    public static final String INVALID_BE_HOME = "be.tea.agent.invalid.be.home";
    public static final String INVALID_FILE = "be.tea.agent.invalid.file";
    public static final String UPLOAD_JAR_FILES_ERROR = "be.tea.agent.upload.jar.files.error";
    public static final String UPLOAD_FILE_FAIL = "be.tea.agent.upload.file.fail";
    public static final String AUTHENTICATE_CREDENTIALS_ERROR = "be.tea.agent.authenticate.credentials.error";
    public static final String FILE_UPLOADED_SUCCESS = "be.tea.agent.file.uploaded.success";
    public static final String SELECTED_HOST_DELETE_SUCCESS = "be.tea.agent.selected.host.deleted.success";
    public static final String MASTER_HOST_NOT_SELECTED = "be.tea.agent.master.host.not.selected";
    public static final String GENERATING_NEXT_HOST_ID = "be.tea.agent.generating.next.host.id";
    public static final String DEPLOYMENT_PATH_NOT_EMPTY = "be.tea.agent.deployement.path.not.empty";
    public static final String DOWNLOAD_LOG_FILE_FOR_SELECTED_INSTANCE_ERROR = "be.tea.agent.download.log.file.for.selected.instance.error";
    public static final String APPLICATION_LOADING_BE_PROPERTIES_FROM_CDD = "be.tea.agent.application.loading.be.properties.from.cdd";
    public static final String HOST_OVERRIDDEN_TRA_CONFIG_EXIST = "be.tea.agent.host.overridden.tra.config.exist";
    public static final String NOT_EXIST = "be.tea.agent.not.exist";
    public static final String EXECUTING_SCRIPT = "be.tea.agent.executing.script";
    public static final String NO_FILE_DIRECTORY = "be.tea.agent.no.file.directory";
    public static final String INVOKED_METHOD = "be.tea.agent.invoked.method";
    public static final String ACTIVATING_LEVEL_FOR_LOGGERNAME = "be.tea.agent.activating.level.for.loggername";
    public static final String INVALID_LOG_NAME_PATTERN = "be.tea.agent.invalid.log.name.pattern";
    public static final String INVALID_LEVEL = "be.tea.agent.invalid.level";
    public static final String UNKNOWN_LEVEL = "be.tea.agent.unknown.level";
    public static final String INVOKING_METHOD_ERROR = "be.tea.agent.invoking.method.error";    
    public static final String JOB_STARTED_DELETE_REMOTE_FILE = "be.tea.agent.job.started.delete.remote.file";
    public static final String DELETE_REMOTE_FILE_ERROR = "be.tea.agent.delete.remote.file.error";
    public static final String RETRYING_DELETE_REMOTE_FILE = "be.tea.agent.retrying.delete.remote.file";
    public static final String DELETE_REMOTE_FILE_COMPLETED = "be.tea.agent.delete.remote.file.completed";
    
    public static final String JOB_STARTED_TO_AUTHENTICATE_HOST = "be.tea.agent.job.started.to.authenticate.host";
    public static final String AUTHENTICATE_HOST_ERROR = "be.tea.agent.authenticate.host.error";
    public static final String JOB_COMPLETED_TO_AUTHETICATE_HOST = "be.tea.agent.job.completed.to.autheticated.host";
    
    public static final String OPERATION_NOT_SUPORTED_SSH_TRANSPORT= "be.tea.agent.operation.not.supported.ssh.transport";
    public static final String STARTING_TIBCO_TEA_AGENT = "be.tea.agent.starting.tibco.tea.agent";
    public static final String LAUNCH_TIBCO_TEA_AGENT_ERROR = "be.tea.agent.launch.tibco.tea.agent.error";
    public static final String TIBCO_BUSINESSEVENTS_STARTED_SUCCESS = "be.tea.agent.tibco.businessevents.started.success";
    public static final String PARSING_COMMANDLINE_ARGUMETS = "be.tea.agent.parsing.command.line.arguments";
    public static final String PARSED_COMMANDLINE_ARGUMENTS = "be.tea.agent.parsed.command.line.arguments";
    public static final String INITIALIZE_JMX_CONNECTION_SERVER_ERROR = "be.tea.agent.initialize.jmx.connection.server.error";
    
    public static final String REGISTERED_APPLICATIONASDATAFEEDHANDLERIMPL = "be.tea.agent.registered.applicationasdatafeedhandlerimpl";
    public static final String APPLICATION_BROWSING_DOMAIN_MEMBER_METASPACE = "be.tea.agent.application.browsing.domain.member.metaspace";
    public static final String DISCONNECTING_METASPACE = "be.tea.agent.disconnecting.metaspace";
    public static final String DISCONNECTED_METASPACE = "be.tea.agent.disconnected.metaspace";
    public static final String BROWSING_DOMAIN_DATA_METASPACE = "be.tea.agent.browsing.domain.data.metaspace";
    public static final String APPLICATION_CONNECTING_AS_CLUSTER = "be.tea.agent.application.connecting.as.cluster";
    public static final String APPLICATION_CONNECTING_METASPACE = "be.tea.agent.application.connecting.metaspace";
    public static final String APPLICATION_CONNECTED_AS_CLUSTER_SUCCESS = "be.tea.agent.application.connected.as.cluster.success";

    public static final String JOB_STARTED_DELETE_DEPLOYEMENT_CONFIGURATION_ = "be.tea.agent.job.started.delete.deployment.configuration"; 
    public static final String DELETE_DEPLOYMENT_CONFIGURATION_ERROR = "be.tea.agent.delete.deployment.configuration.error";
    public static final String SERVICE_INSTANCE_COMPLETED_DELETE = "be.tea.agent.service.instance.completed.delete";
    public static final String JOB_COMPLETED_DELETE_HOST_CONFIGURATION = "be.tea.agent.job.completed.delete.host.configuration";
    public static final String DELETE_CONFIGURATION_HOST_ERROR = "be.tea.agent.delete.configuration.host.error";
    public static final String JOB_STARTED_DELETE_HOST_CONFIGURATION = "be.tea.agent.job.started.delete.host.configuration";
    public static final String JOB_STARTED_DEPLOY_INSTANCE_ON_HOST = "be.tea.agent.job.started.deploy.instance.on.host";
    public static final String DEPLOY_INSTANCE_ON_HOST_ERROR = "be.tea.agent.deploy.instance.on.host.error";
    public static final String RETRYING_DEPLOY_INSTANCE_ON_HOST = "be.tea.agent.retrying.deploy.instance.on.host";
    public static final String INSTANCE_JOB_COMPLETED_LOAD_DEPLOYMENT_VARIABLES = "be.tea.agent.instance.job.completed.load.deployment.variables";
    public static final String INSTANCE_LOAD_DEPLOYEMENT_VARIABLES_ERROR = "be.tea.agent.instance.load.deployment.variables.error";
    public static final String INSTANCE_JOB_STARTED_LOAD_DEPLOYMENT_VARIABLES = "be.tea.agent.instance.job.started.load.deployemnt.variables";
    public static final String INSTANCE_DOWNLOAD_LOG_FILE_ERROR = "be.tea.agent.instance.download.log.file.error";
    public static final String INSTANCE_JOB_STARTED_DOWNLOAD_LOG_FILE = "be.tea.agent.instance.job.started.download.log.file";
    public static final String INSTANCE_RETRYING_DOWNLOAD_LOG_FILE = "be.tea.agent.instance.retrying.download.log.file";
    public static final String INSTANCE_JOB_COMPLETED_DOWNLOAD_LOG_FILE = "be.tea.agent.instance.job.completed.download.log.file";
    public static final String JOB_COMPLETED_DOWNLOAD_REMOTE_FILE = "be.tea.agent.job.completed.download.remote.file";
    public static final String RETRYING_DOWNLOAD_REMOTE_FILE = "be.tea.agent.retrying.download.remote.file";
    public static final String DOWNLOAD_REMOTE_FILE_ERROR = "be.tea.agent.download.remote.file.error";
    public static final String INSTANCE_JOB_STARTED_DOWNLOAD_THREAD_DUMP = "be.tea.agent.instance.job.started.download.thread.dump";
    public static final String INSTANCE_DOWNLOAD_THREAD_DUMP_ERROR = "be.tea.agent.instance.download.thread.dump.error";
    public static final String INSTANCE_JOB_COMPLETED_DOWNLOAD_THREAD_DUMP = "be.tea.agent.instance.job.completed.download.thread.dump";
    public static final String INSTANCE_JOB_STARTED_UPLOAD_EAR_FILE = "be.tea.agent.instance.job.started.upload.ear.file";
    public static final String INSTANCE_UPLOAD_EAR_FILE_ERROR = "be.tea.agent.instance.upload.ear.file.error";
    public static final String INSTANCE_RETRYING_UPLOAD_EAR_FILE = "be.tea.agent.instance.retrying.upload.ear.file";
    public static final String INSTANCE_JOB_COMPLETED_UPLOAD_EAR_FILE = "be.tea.agent.instance.job.completed.upload.ear.file";
    public static final String EXECUTE_SHELL_SCRIPT = "be.tea.agent.execute.shell.script";
    public static final String RETRYING_EXECUTE_SCRIPT = "be.tea.agent.retrying.execute.script";
    public static final String EXECUTE_SCRIPT_ERROR = "be.tea.agent.execute.script.error";
    public static final String EXECUTE_SHELL_COMMAND = "be.tea.agent.execute.shell.commands";
    public static final String STARTED_INVOKE_METHOD = "be.tea.agent.started.invoke.method";
    public static final String INVOKE_METHOD_ERROR = "be.tea.agent.invoke.method.error";
    public static final String COMPLETED_INVOKE_METHOD  = "be.tea.agent.completed.invoke.method";
    public static final String RETRYING_KILL_INSTANCE = "be.tea.agent.retrying.kill.instance";
    public static final String INSTANCE_KILL_ERROR = "be.tea.agent.instance.kill.error";
    public static final String INSTANCE_STARTED_KILL = "be.tea.agent.instance.started.kill";
    public static final String JOB_COMPLETED_KILL_INSTANCE = "be.tea.agent.job.completed.kill.instance";
    public static final String JOB_COMPLETED_READ_FILE = "be.tea.agent.job.completed.read.file";
    public static final String RETRYING_READ_FILE = "be.tea.agent.retrying.read.file";
    public static final String READ_FILE_ERROR = "be.tea.agent.read.file.error";
    public static final String JOB_STARTED_READ_FILE = "be.tea.agent.job.started.read.file";
    public static final String HOST_GET_OS_DETAILS_ERROR = "be.tea.agent.host.get.os.details.error";
    public static final String HOST_RETRYING_GET_OS_DETAILS = "be.tea.agent.host.retrying.get.os.details";
    public static final String JOB_STARTED_START_INSTANCE = "be.tea.agent.job.started.start.instance";
    public static final String START_INSTANCE_ERROR = "be.tea.agent.start.instance.error";
    public static final String RETRYING_START_INSTANCE = "be.tea.agent.retrying.start.instance";
    public static final String JOB_COMPLTED_START_INSTANCE = "be.tea.agent.job.completed.start.instance";
    public static final String INVOKING_MBAEN_ERROR = "be.tea.agent.invoking.mbean.error";
    public static final String INSTANCE_STATE_MODE = "be.tea.agent.instance.state.mode";
    public static final String JOB_STARTED_UPDATE_STATUS_INSTANCE = "be.tea.agent.job.started.update.status.instance";
    public static final String INSTANCE_STATUS_UPDATE_ERROR = "be.tea.agent.instance.status.update.error";
    public static final String JOB_COMPLETED_INSTANCE_STATUS_UPDATE = "be.tea.agent.job.completed.instance.status.update";
    public static final String JOB_STARTED_FIRE_TAIL_COMMAND = "be.tea.agent.job.started.fire.tail.command";
    public static final String FIRE_TAIL_COMMAND_ERROR = "be.tea.agent.fire.tail.command.error";
    public static final String RETRYING_FIRE_TAIL_COMMAND = "be.tea.agent.retrying.fire.tail.command";
    public static final String JOB_COMPLETED_DEPLOY_INSTANCE_ON_HOST = "be.tea.agent.job.completed.deploy.instance.on.host";
    public static final String UNDEPLOY_INSTANCE_ERROR = "be.tea.agent.undeploy.instance.error";
    public static final String RETRYING_UNDEPLOY_INSTANCE = "be.tea.agent.retrying.undeploy.instance";
    public static final String JOB_COMPLETED_UNDEPLOY_INSTANCE = "be.tea.agent.job.completed.undeploy.instance";
    public static final String JOB_COMPLETED_DT_TEMPLATE_FILES = "be.tea.agent.job.completed.upload.dt.template.files";
    public static final String RETRYING_UPLOAD_DT_TEMPLATE_FILES = "be.tea.agent.retrying.upload.dt.template.files";
    public static final String UPLOAD_DT_TEMPLATE_FILES_ERROR = "be.tea.agent.upload.dt.template.files.error";
    public static final String JOB_STARTED_UPLOAD_DT_TEMPLATE_FILES = "be.tea.agent.job.started.upload.de.template.files";
    public static final String UPLOADING_FILE = "be.tea.agent.uploading.file";
    public static final String FAILED_UPLOAD_FILE = "be.tea.agent.upload.file.error";
    public static final String RETRYING_UPLOAD_FILE = "be.tea.agent.retrying.upload.file";
    public static final String JOB_COMPLETED_UPLOAD_FILE = "be.tea.agent.job.completed.upload.file";
    public static final String JOB_STARTED_UNDEPLOY_INSTANCE = "be.tea.agent.jon.started.undeploy.instance";
    public static final String UPLOAD_DT_TEMPLATE_FILES = "be.tea.agent.upload.dt.template.files";
    public static final String JOB_STRATED_UPLOAD_FILE = "be.tea.agent.job.started.upload.file";
    
    public static final String PINGING_TEA_SERVER = "be.tea.agent.pinging.tea.server";
    public static final String INVOKING_METHOD_EXCEPTION = "be.tea.agent.invoking.method.exception";
    public static final String TEA_SERVER_RUNNING = "be.tea.agent.tea.server.running";
    public static final String TEA_SERVER_NOT_RUNNING = "be.tea.agent.server.not.running";
    public static final String SERVER_PINGER_TASK_ERROR = "be.tea.agent.server.pinger.task.error";
    public static final String CREATED_SAFE_JSCH_CONNECTION_POOL = "be.tea.agent.created.safe.jsch.connection.pool";
    public static final String PROBLEM_CLOSING_SESSION = "be.tea.agent.problem.closing.session";

    public static final String SOME_SERVICE_INSTANCE_NOT_DEPLOYED = "be.tea.agent.some.service.instance.not.deployed";
    public static final String SELECTED_SERVICE_INSTANCE_DEPLOYED_SUCCESS = "be.tea.agent.selected.service.instance.deployed.success";
    public static final String SELECTED_SERVICE_INSTANCE_ALREADY_UNDEPLOYED = "be.tea.agent.selected.service.instance.already.undeployed";
    public static final String SELECTED_SERVICE_INSTANCE_CANNOT_UNDEPLOYED = "be.tea.agent.selected.service.instance.cannot.undeployed";
    public static final String SOME_INSTANCE_NOT_UNDEPLOYED = "be.tea.agent.some.instance.not.undeployed";
    public static final String SELECTED_SERVICE_INSTANCE_UNDEPLOY_SUCCESS  = "be.tea.agent.selected.service.instance.undeploy.success";
    public static final String SELECTED_SERVICE_INSTANCE_ALREADY_STARTED_OR_UNDEPLOYED = "be.tea.agent.selected.service.instance.already.started.or.undeployed";
    public static final String SELECTED_SERVICE_INSTANCE_ALREADY_STARTED = "be.tea.agent.selected.service.instance.already.started";
    public static final String SELECTED_SERVICE_INSTANCE_STARTED_SUCCESS = "be.tea.agent.selected.service.instance.started.success";
    public static final String SELECTED_SERVICE_INSTANCE_ALREADY_STOPPED = "be.tea.agent.selected.service.instance.already.stopped";
    public static final String SELECTED_SERVICE_INSTANCE_STOPPED_SUCCESS = "be.tea.agent.selected.service.instance.stopped.success";
    public static final String SELECTED_SERVICE_INSTANCE_KILLED_SUCCESS = "be.tea.agent.selected.service.instance.killed.success";
    public static final String SELECTED_INSTANCES_NOT_DELETED = "be.tea.agent.selected.instances.not.deleted";
    public static final String SELECTED_INSTANCES_DELETE_SUCCESS = "be.tea.agent.selected.instances.delete.success";
    public static final String HOT_DEPLOY_FAILED = "be.tea.agent.hot.deploy.failed";
    public static final String SELECTED_SERVICE_INSTANCE_HOT_DEPLOYED = "be.tea.agent.selected.service.instance.hot.deployed";
    public static final String SELECTED_SERVICE_INSTANCE_ALREADY_DEPLOYED = "be.tea.agent.selected.service.instance.already.deployed";

    public static final String UNABLE_CONNECT_INSTANCE_AT_JMX_PORT = "be.tea.agent.unable.connect.instance.at.jmx.port";
    public static final String CLOSING_JMX_CONNECTION_INSTANCE_ERROR = "be.tea.agent.closing.jmx.connection.instance.error";

    public static final String INITIALIZED_VERSION_CHECK = "be.tea.agent.initialized.version.check";
    public static final String AQUIRED_READ_LOCK_KEY = "be.tea.agent.aquired.read.lock.key";
    public static final String AQUIRED_WRITE_LOCK_KEY = "be.tea.agent.aquired.write.lock.key";
    public static final String RELEASED_LOCK_KEY = "be.tea.agent.released.lock.key";
    public static final String CHECK_ENTITY_STALE = "be.tea.agent.check.entity.stale";
    public static final String SERVER_USER_VERSION = "be.tea.agent.server.user.version";

    public static final String RETRIVING_TOPOLOGY_DATA = "be.tea.agent.retriving.topology.data";
    public static final String UNABLE_CONNECT_INSTANCE = "be.tea.agent.unable.connect.instance";
    public static final String NOT_REGISTERED = "be.tea.agent.not.registered";
    public static final String STOPPED_MBEANSERVICE = "be.tea.agent.stopped.mbeanservice";
    public static final String STOPPING_MBEANSERVICE = "be.tea.agent.stopping.mbeanservice";
    
    public static final String STOPPING_BE_TEA_THREADPOOL = "be.tea.agent.stopping.be.tea.threadpool";
    public static final String STOPPING_BE_TEA_THREADPOOL_ERROR = "be.tea.agent.stopping.be.tea.threadpool.error";

    public static final String INVALID_PROPERTY_VALUE = "be.tea.agent.invalid.property.value";
    public static final String SCHEDULED_TIMER_TASK = "be.tea.agent.scheduled.timer.task";
    public static final String INITCONNECTION_MONITORABLE_ENTITY_FAILED = "be.tea.agent.initconnection.monitorable.entity.failed";
    public static final String MONITORABLE_ENTITY_ALREADY_REGISTERED = "be.tea.agent.monitorable.entity.already.registered";
    public static final String UNREGISTER_MONITORABLE_ENTITY_FAILED = "be.tea.agent.unregister.monitorable.entity.failed";

    public static final String ATTEMPTING_JMX_CONNECTION_ON_MONITORABLE_ENTITY = "be.tea.agent.attempting.jmx.connection.on.monitorable.entity";
    public static final String JMX_CONNECTION_ON_MONITORABLE_ENTITY_SUCCESS = "be.tea.agent.jmx.connection.on.monitorable.entity.success";
    public static final String CAN_NOT_CONNECT_MONITORABLE_ENTITY = "be.tea.agent.can.not.connect.monitorable.entity";
    public static final String JMX_CONNECTION_MONITORABLE_ENTITY_FAILED = "be.tea.agent.jmx.connection.monitorable.entity.failed";
    public static final String NO_JMX_CONNECTION_MONITORABLE_ENTITY = "be.tea.agent.no.jmx.connection.monitorable.entity";
    public static final String CLOSING_JMX_CONNECTION_MONITORABLE_ENTITY = "be.tea.agent.closing.jmx.connection.monitorable.entity";

    public static final String CREATED_SHARED_JSCH_CONNECTION_POOL = "be.tea.agent.created.shared.jsch.connection.pool";
    public static final String CLOSING_SHARED_JSCH_CONNECTION_POOL = "be.tea.agent.closing.shared.jsch.connection.pool";

    public static final String PERFORMING_BE_TEA_AGENT_AUTO_REGISTRATION_TASK = "be.tea.agent.performing.be.tea.agent.auto.registration.task";
    public static final String UNREGISTERING_BE_TEA_AGENT = "be.tea.agent.unregistering.be.tea.agent";
    public static final String UNREGISTRED_BE_TEA_AGENT = "be.tea.agent.unregistred.be.tea.agent";
    public static final String PERFORMED_BE_TEA_AGENT_AUTO_REGISTRATION_TASK = "be.tea.agent.performed.be.tea.agent.auto.registration.task";
    public static final String REGISTERING_BE_TEA_AGENT_MBEAN = "be.tea.agent.registering.be.tea.agent.mbean";
    public static final String REGISTRED_BE_TEA_MBEAN_SUCCESS = "be.tea.agent.registered.be.tea.mbean.success";
    public static final String AUTO_REGISTRATION_TASK = "be.tea.agent.auto.registration.task";
    
    public static final String CREATED_EXCLUSIVE_ACCESS_JSCH_CONNECTION_POOL = "be.tea.agent.created.exclusive.access.jsch.connection.pool";
    public static final String CLOSING_EXCLUSIVE_ACCESS_JSCH_CONNECTION_POOL = "be.tea.agent.closing.exclusive.access.jsch.connection.pool";
    public static final String TIMEOUT_WAITING_LOCK_ON_CONNECTION_POOL = "be.tea.agent.timeout.waiting.lock.on.connection.pool";
    public static final String CREATED_NEW_JSCH_SESSION_POOL = "be.tea.agent.created.new.jsch.session.pool";
    public static final String UNSUPPORTED_EXECUTION_CONNECTION = "be.tea.agent.unsuppported.execution.connection";

    public static final String CREATED_LATCHED_SHARED_JSCH_CONNECTION_POOL = "be.tea.agent.created.latched.shared.jsch.connection.pool";
    public static final String DISCARDING_CONNECTION = "be.tea.agent.discarding.connection";
    public static final String CLOASING_LATCHED_SHARED_JSCH_CONNECTION_POOL = "be.tea.agent.cloasing.latched.shared.jsch.connection.pool";
   
    public static final String BE_TEA_AGENT_URL = "be.tea.agent.be.tea.agent.url";
    public static final String EXPOSE_PYTHIN_API = "be.tea.agent.expose.python.api";
    public static final String MONITORING_SERVICE_ERROR = "be.tea.agent.monitoring.service.error";
    public static final String SHOTDOWN_PROGRESS = "be.tea.agent.shutdown.progress";
    public static final String STOPPING_TEA_SERVER_ERROR = "be.tea.agent.stopping.tea.server.error";
    public static final String STOPPING_PINGSERVICE_ERROR = "be.tea.agent.stopping.pingservice.error";
    public static final String SHUTDOWN_COMPLETE = "be.tea.agent.shutdown.complete";
    public static final String STOPPING_ERROR = "be.tea.agent.stopping.error";
    public static final String CLOSING_JSCH_CONNECTION_POOL_ERROR = "be.tea.agent.closing.jsch.connection.pool.error";

    public static final String JMX_CONNECTION_CLOSED = "be.tea.agent.jmx.connection.closed";
    public static final String JMX_CONNECTION_UNRECOGNIZED = "be.tea.agent.jmx.connection.unrecognized";

    public static final String CONFIG_CSV_FILE_NOT_PRESENT = "be.tea.agent.config.csv.file.not.present";
    public static final String READEXPORTEDFILE_ERROR = "be.tea.agent.readexportedfile.error";
    public static final String READING_MIGRATION_CONFIG_FILE_ERROR = "be.tea.agent.reading.migration.config.file.error";

    public static final String INVOKED_DEPLOYMENT_METHOD = "be.tea.agent.invoked.deployment.method";
    public static final String INITIALIZING_DEPLOYMENT_VARIABLES_FOR_UNDEPLOYED_INSTANCES = "be.tea.agent.initializing.deployemnt.variables.for.undeployed.instances";
    public static final String INITIALIZED_DEPLOYMENT_VARIABLES_FOR_UNDEPLOYED_INSTANCES  = "be.tea.agent.initialized.deployemnt.variables.for.undeployed.instances";
    public static final String LOADING_VARIABLES = "be.tea.agent.loading.varibales";
    public static final String CONVERTING_GV_TO_DEPLOYMENT_VARIABLES = "be.tea.agent.converting.gv.to.deployment.variables";
    public static final String CONVERTED_GV_TO_DEPLOYMENT_VARIABLES = "be.tea.agent.converted.gv.to.deployment.variables";
    public static final String LOADED_VARIABLES = "be.tea.agent.loaded.variables";
    
    public static final String INSIDE_ENRICHTOPOLOGYDATA = "be.tea.agent.inside.enrichTopologyData";
    public static final String RESOLVING_MASTER_HOST = "be.tea.agent.resolving.master.host";
    public static final String RESOLVED_MASTER_HOST = "be.tea.agent.resolved.master.host";

    public static final String SETTING_CDD_APPLICATION_CONFIG_DETAILS = "be.tea.agent.setting.cdd.application.config.details";
    public static final String CDD_EXTERNAL_CLASSES_PATH_PROPERTY_SPECIFIED = "be.tea.agent.cdd.external.classes.path.property.specified";
    public static final String RULE_TEMPLATE_SPECIFIED_IN_CDD = "be.tea.agent.rule.template.specified.in.cdd";

    public static final String COULD_NOT_CREATE_HANDLER_METRICS = "be.tea.agent.could.not.create.handler.metrics";
    public static final String COULD_NOT_CONNECT_COLLECTING_METRICS = "be.tea.agent.could.not.connect.collecting.metrics";
    public static final String STOPPING_COLLECTOR_POOL_SERVICE_ERROR = "be.tea.agent.stopping.collector.service.threadpool";
    public static final String STARTING_STOPPING_METRIC_COLLECTION_ERROR = "be.tea.agent.starting.stopping.metric.collection.error";
    
    public static final String INITIALIZING_JMX_CONNECTIVITY_ERROR = "be.tea.agent.initializing.jmx.connectivity.error";
    public static final String FETCHING_ATTRIBUTE_ERROR = "be.tea.agent.fetching.attribute.error";
    public static final String MBEAN_NOT_REGISTERED = "be.tea.agent.mbean.not.registered";
    public static final String ERROR_WHILE_INVOKING_METHOD = "be.tea.agent.error.while.invoking.method";
    public static final String INVOKING_PARAMETERIAZED_METHOD_ERROR = "be.tea.agent.invoking.parameteriazed.method.error";
    public static final String QUERYING_PATTERN_ERROR = "be.tea.agent.querying.pattern.error";
    public static final String INITIALIZING_MBEAN_SERVER_ERROR = "be.tea.agent.initializing.mbean.server.error";
    public static final String FETCHING_MBEAN_ATTRIBUTE_ERROR = "be.tea.agent.fetching.mbean.attribute.error";
    public static final String MBEAN_INVOKING_METHOD_ERROR = "be.tea.agent.mbean.invoking.method.error";

    public static final String FETCHING_OBJECT = "be.tea.agent.fetching.object";
    public static final String POPULATING_MBEAN_ATTRIBUTES_ERROR = "be.tea.agent.populating.mbean.attributes.error";
    public static final String GETTING_INTERCEPTOR_ERROR = "be.tea.agent.getting.interceptor.error";
    public static final String FETCHING_OBJECTNAME_ATTRIBUTE = "be.tea.agent.fetching.objectname.attribute";
    public static final String INVOKING_MBEAN_OPERATION_ERROR = "be.tea.agent.invoking.mbean.operation.error";
    public static final String PUBLISHING_FACT_ERROR = "be.tea.agent.publishing.fact.error";
    public static final String PREPARING_FACT_ERROR = "be.tea.agent.preaparing.fact.error";
    
    public static final String INITIALIZING_WITH_CONFIG = "be.tea.agent.initializing.with.config";
    public static final String INVALID_POLL_INTERVAL_PARAMETER = "be.tea.agent.invalid.poll.interval.parameter";
    public static final String CONFIGURAED_POLL_INTERVAL = "be.tea.agent.configuraed.poll.interval";
    public static final String GET_MONITORABLE_REGISTRY_ERROR = "be.tea.agent.get.monitorable.registry.error";
    public static final String PROCESSING_MAPENTRY = "be.tea.agent.processing.mapentry";
    public static final String METRIC_COLLECTION_ERROR = "be.tea.agent.metric.collection.error";

    public static final String PARSING_CONFIGURED_PLUGINS_ERROR = "be.tea.agent.parsing.configured.plugins.error";
    public static final String INITIALIZING_SERVICE = "be.tea.agent.initializing.service";
    public static final String INVALID_ACCUMULATOR_OBJECT = "be.tea.agent.invalid.accumulator.object";
    public static final String STARTED_SUCCESS = "be.tea.agent.started.success";
    public static final String INITIALIZING_ERROR = "be.tea.agent.initializing.error";
    public static final String PLUGIN_DIRECTORY_DOES_NOT_EXIST = "be.tea.agent.plugin.directory.does.not.exist";
    public static final String PARSING_XML_FILE_ERROR = "be.tea.agent.parsing.xml.file.error";
    
    public static final String DELETE_RULE_FAILED = "be.tea.agent.delete.rule.failed";
    public static final String GETTING_RULES_FOR_APP_ERROR = "be.tea.agent.getting.rules.for.app.error";
    public static final String CREATING_RULE_ERROR = "be.tea.agent.creating.rule.error";
    public static final String UPDATING_RULE_ERROR = "be.tea.agent.updating.rule.error";
    public static final String RULE_REPO_APP_NOT_FOUND = "be.tea.agent.rule.repo.app.not.found";

    public static final String PINGER_TASK_UPDATE_STATUS_HOST = "be.tea.agent.pinger.task.update.status.host";
 	public static final String MASTER_HOST_STATUS_TASK_ERROR = "be.tea.agent.master.host.status.task.error";
 	public static final String DELETE_OLD_FILES_TASK = "be.tea.agent.delete.old.files.task";
 	public static final String FILE_ELIGIBLE_DELETE = "be.tea.agent.file.eligible.delete";
 	public static final String DELETE_OLD_FILES_TASK_ERROR = "be.tea.agent.delete.old.files.task.error";

 	public static final String CREATING_COMPOSITETYPE_ERROR = "be.tea.agent.creating.compositetype.error";
 	public static final String CREATING_TABULARTYPE_ERROR = "be.tea.agent.creating.tabulartype.error";
 	public static final String CREATING_COMPOSITEDATASUPPORT_ERROR = "be.tea.agent.creating.compositedatasupport.error";

 	public static final String EXISTS_BUT_NOT_DIRECTORY = "be.tea.agent.exists.not.directory";
 	public static final String CAN_NOT_WRITE_TO_DIRECTORY = "be.tea.agent.can.not.write.to.directory";
 	public static final String NOT_DIRECTORY = "be.tea.agent.not.directory";
 	public static final String NOT_HAVE_PERMISSION_WRITE = "be.tea.agent.not.have.permission.write";

 	public static final String INVALID_DATA_TYPE_FOR_FINDING_DIFFERENCE = "be.tea.agent.not.valid.data.type.for.finding.difference";
 	public static final String CHECK_DATA_STRUCURE_ERROR = "be.tea.agent.check.data.structure.error";

 	public static final String POPULATING_VIEW_REGISTERY_ERROR = "be.tea.agent.populating.view.registry.error";
 	public static final String GETTING_DATA_FOR_CHART_SERIES_ERROR = "be.tea.agent.getting.data.for.chart.series.error";
 	public static final String CAN_NOT_CREATE_INSTANCE = "be.tea.agent.not.create.instance";

 	public static final String SENDING_SERVICE_INSTANCE_ERROR = "be.tea.agent.sending.service.instance.start.fact.error";
 	public static final String PERFORMING_MONITORING_RELATED_BOOTSTRAP_OPERATIONS_ERROR = "be.tea.agent.performing.monitoring.related.bootstrap.operations.error";

 	public static final String INITIALIZED_SUCCESS = "be.tea.agent.initialized.success";
 	public static final String STOPPING_COLLECTOR_THREADPOOL = "be.tea.agent.stopping.collector.threadpool";
 	public static final String STOPPING_COLLECTOR_THREADPOOL_ERROR = "be.tea.agent.stopping.collector.threadpool.error";
 	public static final String INVOKING_MBEAN_ENTITY_ERROR = "be.tea.agent.invoking.mbean.entity.error";
    public static final String IMPORTING_APPLICATION = "be.tea.agent.importing.application";
    public static final String JVM_PROPS_UPDATE_ERROR = "be.tea.agent.jvm.props.update.error";
    public static final String GV_PROPS_UPDATE_ERROR = "be.tea.agent.gv.props.update.error";
    public static final String MIGRATED_SUCCESS = "be.tea.agent.migrated.success";
    public static final String UNKNOWN_JSCH_POOL_TYPE = "be.tea.agent.unknown.jsch.pool.type";
    public static final String INITIALIZE_GROUP_OP_SERVICE_ERROR = "be.tea.agent.initialize.group.op.service.error";
    public static final String LOCKING_ENABLED = "be.tea.agent.locking.enabled";
    public static final String INITIALIZED = "be.tea.agent.initialized";
    public static final String UNKNOWN_ERROR = "be.tea.agent.unknown.error";
    public static final String RETRY_COUNT_OPERATION = "be.tea.agent.retry.count.operation";
    public static final String RETRIES_EXCEEDED_TASK = "be.tea.agent.retries.exceeded.task";
    public static final String ATTEMPTING_RETRY_OPERATION = "be.tea.agent.attempting.retry.operation";
    public static final String STATUS_CHANGED = "be.tea.agent.status.changed";
    public static final String INSTANCE_STATUS_CHANGED = "be.tea.agent.instance.status.changed";
    public static final	String BE_VERSION_NOT_MENTIONED_ERROR_MESSAGE = "be.tea.agent.be.version.not.mentioned";
    public static final String NOT_MATCHING_VERSION_ERROR_MESSAGE = "be.tea.agent.not.matching.version";
}
