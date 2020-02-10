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
 * Job used to stop the service instance
 * 
 * @author dijadhav
 *
 */
public class BETeaDownloadThreadDump implements GroupJob<Object> {
	private MessageService messageService;

	/**
	 * Instance Service
	 */
	private BEServiceInstancesManagementService instanceService;

	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param instanceService
	 */
	public BETeaDownloadThreadDump(BEServiceInstancesManagementService instanceService) {
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
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;

		ServiceInstance serviceInstance = context.getServiceInstance();
		BETeaOperationResult operationResult = new BETeaOperationResult();

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_STARTED_DOWNLOAD_THREAD_DUMP, serviceInstance.getName()));

		try {
			operationResult.setName(serviceInstance.getKey());
			String message = instanceService.getThreadDump(serviceInstance);
			operationResult.setResult(message);
		} catch (Exception e) {
			operationResult.setResult(e);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_DOWNLOAD_THREAD_DUMP_ERROR,
					serviceInstance.getName(), e.getMessage()));
		}

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_COMPLETED_DOWNLOAD_THREAD_DUMP, serviceInstance.getName()));

		return operationResult;
	}

}
