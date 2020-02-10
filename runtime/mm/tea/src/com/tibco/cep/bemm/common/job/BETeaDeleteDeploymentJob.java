package com.tibco.cep.bemm.common.job;

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
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * This class is used to delete the deployment
 * 
 * @author dijadhav
 *
 */
public class BETeaDeleteDeploymentJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Instance service
	 */
	private BEServiceInstancesManagementService instanceService;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * Constructor to set
	 * 
	 * @param instanceService
	 *            - Instance management service
	 */
	public BETeaDeleteDeploymentJob(BEServiceInstancesManagementService instanceService) {
		super();
		this.instanceService = instanceService;
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
		BETeaOperationResult operationResult = new BETeaOperationResult();

		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;
		ServiceInstance serviceInstance = context.getServiceInstance();
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_DELETE_DEPLOYEMENT_CONFIGURATION_, serviceInstance.getName()));
		operationResult.setName(serviceInstance.getName());
		try {
			String message = instanceService.delele(serviceInstance.getHost().getApplication(),
					serviceInstance.getName());
			LOGGER.log(Level.DEBUG, message);
			operationResult.setResult(message);
			operationResult.setKey(serviceInstance.getKey());
		} catch (Exception e) {
			operationResult.setResult(e);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DELETE_DEPLOYMENT_CONFIGURATION_ERROR, operationResult.getName()));
		}
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_DELETE_HOST_CONFIGURATION, operationResult.getName()));
		return operationResult;
	}

}
