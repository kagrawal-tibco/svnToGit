
package com.tibco.cep.bemm.common.job;

import java.util.Collection;

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
import com.tibco.cep.repo.GlobalVariableDescriptor;

/**
 * Job To deploy the service instance
 * 
 * @author dijadhav
 *
 */
public class BETeaDeployInstanceJob implements GroupJob<Object> {
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
	 * Collection of Global Variables
	 */
	private Collection<GlobalVariableDescriptor> globalVariableDescriptors;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * Constructor to set the field value
	 * 
	 * @param instanceService
	 *            - Instance Service
	 * @param instance
	 *            -Service instance
	 * @param loggedInUser-Name
	 *            of logged in user
	 * @param globalVariableDescriptors
	 *            - Collection of Global Variables
	 */
	public BETeaDeployInstanceJob(BEServiceInstancesManagementService instanceService, String loggedInUser,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors) {
		super();
		this.instanceService = instanceService;
		this.loggedInUser = loggedInUser;
		this.globalVariableDescriptors = globalVariableDescriptors;
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
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;
		ServiceInstance serviceInstance = context.getServiceInstance();
		PooledConnection<Session> connection = null;
		BETeaOperationResult operationResult = new BETeaOperationResult();
		operationResult.setName(serviceInstance.getName());
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_DEPLOY_INSTANCE_ON_HOST, serviceInstance.getName(),
				serviceInstance.getHost().getName()));
		int maxRetryCount = 1;

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				Object result = instanceService.deploy(serviceInstance, loggedInUser, session,
						globalVariableDescriptors);
				operationResult.setResult(result);
				maxRetryCount = -1;
				LOGGER.log(Level.DEBUG, result.toString());
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DEPLOY_INSTANCE_ON_HOST_ERROR, serviceInstance.getName(),
						serviceInstance.getHost().getName(), e.getMessage()));
				operationResult.setResult(e);
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					maxRetryCount--;
					if (null != connection)
						connection.setMarkedUnusable(true);
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_DEPLOY_INSTANCE_ON_HOST, serviceInstance.getName(),
							serviceInstance.getHost().getName(), e.getMessage()));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}

		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_DEPLOY_INSTANCE_ON_HOST, serviceInstance.getName(),
				serviceInstance.getHost().getName()));
		return operationResult;
	}
}
