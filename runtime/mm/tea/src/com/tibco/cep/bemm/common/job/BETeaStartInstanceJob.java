package com.tibco.cep.bemm.common.job;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.ConnectionPool;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.PooledConnection;
import com.tibco.cep.bemm.common.pool.PooledConnectionConfig;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * Job used to start the service instance
 * 
 * @author dijadhav
 *
 */
public class BETeaStartInstanceJob implements GroupJob<Object> {
	private MessageService messageService;

	/**
	 * Instance Service
	 */
	private BEServiceInstancesManagementService instanceService;
	/**
	 * Name of logged in user
	 */
	private String loggedInUser;
	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param instanceService
	 * @param loggedInUser
	 */
	public BETeaStartInstanceJob(BEServiceInstancesManagementService instanceService, String loggedInUser) {
		super();
		this.instanceService = instanceService;
		this.loggedInUser = loggedInUser;
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

		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(
				((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs());
		int maxRetry = 1;
		BETeaOperationResult operationResult = new BETeaOperationResult();
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;
		ServiceInstance serviceInstance = context.getServiceInstance();
		PooledConnection<Session> connection = null;
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_START_INSTANCE, serviceInstance.getName()));

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				
				operationResult.setName(serviceInstance.getName());
				Object message = instanceService.start(serviceInstance, loggedInUser, session);
				operationResult.setResult(message);
				maxRetry = -1;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.START_INSTANCE_ERROR, serviceInstance.getName(),
						e.getMessage()));
				operationResult.setResult(e);
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetry--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_START_INSTANCE, serviceInstance.getName()));
				} else {
					maxRetry = -1;
				}
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetry >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLTED_START_INSTANCE, serviceInstance.getName()));

		return operationResult;
	}

}
