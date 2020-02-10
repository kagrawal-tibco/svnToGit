package com.tibco.cep.bemm.common.job;

import java.util.Collection;
import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.util.DeploymentVariableUtil;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.repo.GlobalVariableDescriptor;

/**
 * Job to load the deployment variables of instance
 * 
 * @author dijadhav
 *
 */
public class BETeaDeploymentVariableLoadJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Collection of Global Variables
	 */
	private Collection<GlobalVariableDescriptor> globalVariableDescriptors;

	/**
	 * Map of properties from CDD
	 */
	private Map<Object, Object> cddProperties;
	/**
	 * Map of properties from CDD
	 */
	private Map<String, Map<String, String>> traProperties;
	/**
	 * Data store service
	 */
	private BEApplicationsManagementDataStoreService<?> dataStoreService;
	
	private boolean isEdit;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);
	

	/**
	 * Constructor to set filed values
	 * 
	 * @param globalVariableDescriptors
	 *            - Collection of global variables
	 * @param instance
	 *            - Service Instance
	 * @param cddProperties
	 *            - Map of CDD properties
	 * @param dataStoreService
	 *            - Data store service
	 * @param isEdit 
	 */
	public BETeaDeploymentVariableLoadJob(Collection<GlobalVariableDescriptor> globalVariableDescriptors,
			Map<Object, Object> cddProperties, Map<String, Map<String, String>> traProperties,
			BEApplicationsManagementDataStoreService<?> dataStoreService, boolean isEdit) {
		super();
		this.globalVariableDescriptors = globalVariableDescriptors;
		this.cddProperties = cddProperties;
		this.dataStoreService = dataStoreService;
		this.traProperties = traProperties;
		this.isEdit=isEdit;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {

		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;

		ServiceInstance serviceInstance = context.getServiceInstance();
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_STARTED_LOAD_DEPLOYMENT_VARIABLES, serviceInstance.getName()));

		BETeaOperationResult operationResult = new BETeaOperationResult();
		try {

			operationResult.setName(serviceInstance.getName());
			DeploymentVariableUtil.initDeploymentVariables(globalVariableDescriptors, dataStoreService, serviceInstance,
					cddProperties, traProperties.get(serviceInstance.getHost().getKey()+"_"+serviceInstance.getBeId()), isEdit);
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_LOAD_DEPLOYEMENT_VARIABLES_ERROR,
					serviceInstance.getName(), e.getMessage()));

			operationResult.setResult(e);
		}

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_COMPLETED_LOAD_DEPLOYMENT_VARIABLES, serviceInstance.getName()));
		return operationResult;
	}

}
