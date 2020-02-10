package com.tibco.cep.bemm.common.job;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.MasterHostGroupJobExecutionContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.model.MasterHost;

/**
 * Job is used to delete the master host
 * 
 * @author dijadhav
 *
 */
public class BETeaDeleteMasterHostJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Master host management service
	 */
	private BEMasterHostManagementService masterHostManagementService;

	private String loggedInUser;

	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param masterHostManagementService
	 */
	public BETeaDeleteMasterHostJob(BEMasterHostManagementService masterHostManagementService, String loggedInUser) {
		super();
		this.masterHostManagementService = masterHostManagementService;
		this.loggedInUser = loggedInUser;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof MasterHostGroupJobExecutionContext)) {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}

		MasterHostGroupJobExecutionContext context = (MasterHostGroupJobExecutionContext) executionContext;
		MasterHost masterHost = context.getMasterHost();
		BETeaOperationResult operationResult = new BETeaOperationResult();
		operationResult.setName(masterHost.getHostName());

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_DELETE_HOST_CONFIGURATION, masterHost.getHostName()));

		try {
			String message = masterHostManagementService.deleteMasterHost(masterHost, loggedInUser);
			LOGGER.log(Level.DEBUG, message);
			operationResult.setResult(message);
		} catch (Exception e) {
			operationResult.setResult(e);
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DELETE_CONFIGURATION_HOST_ERROR,
					operationResult.getName(), e.getMessage()));
		}

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_DELETE_HOST_CONFIGURATION, masterHost.getHostName()));

		return operationResult;
	}

}
