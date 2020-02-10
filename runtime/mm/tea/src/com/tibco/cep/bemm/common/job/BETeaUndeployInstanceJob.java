
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
 * Job To undeploy the service instance
 * 
 * @author dijadhav
 *
 */
public class BETeaUndeployInstanceJob implements GroupJob<Object> {
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
	public BETeaUndeployInstanceJob(BEServiceInstancesManagementService instanceService, String loggedInUser,
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
		PooledConnection<Session> connection = null;
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;

		ServiceInstance serviceInstance = context.getServiceInstance();
		BETeaOperationResult operationResult = new BETeaOperationResult();

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_UNDEPLOY_INSTANCE, serviceInstance.getName()));

		int maxRetryCount = 1;
		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				operationResult.setName(serviceInstance.getName());
				String message = (String) instanceService.undeploy(serviceInstance, loggedInUser,
						connection.getUnderlyingConnection(), globalVariableDescriptors);
				operationResult.setResult(message);
				maxRetryCount = -1;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UNDEPLOY_INSTANCE_ERROR, serviceInstance.getName()));
				operationResult.setResult(e);
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_UNDEPLOY_INSTANCE, serviceInstance.getName()));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_UNDEPLOY_INSTANCE, serviceInstance.getName()));
		return operationResult;
	}
}
