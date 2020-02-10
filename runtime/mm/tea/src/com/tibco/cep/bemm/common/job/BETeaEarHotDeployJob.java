package com.tibco.cep.bemm.common.job;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
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
 * Job is used to upload the file to remote location
 * 
 * @author dijadhav
 *
 */
public class BETeaEarHotDeployJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Remote file path
	 */
	private BEServiceInstancesManagementService instanceService;
	/**
	 * Local file path
	 */
	private String localFile;
	/**
	 * Name of logged in user
	 */
	private String loggedInUser;

	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * Constructor to set remote and local file
	 * 
	 * @param remoteFile
	 *            - Remote file
	 * @param instanceService
	 *            - Local file
	 */
	public BETeaEarHotDeployJob(String localFile, BEServiceInstancesManagementService instanceService,
			String loggedInUser) {
		super();
		this.localFile = localFile;
		this.instanceService = instanceService;
		this.loggedInUser = loggedInUser;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.common.pool.GroupJob#callUsingExecutionContext(com.
	 * tibco.cep.bemm.common.pool.GroupJobExecutionContext)
	 */
	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		int maxRetryCount = 1;
		boolean uploaded = false;
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(
				((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs());
		PooledConnection<Session> connection = null;
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;
		ServiceInstance serviceInstance = context.getServiceInstance();
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_STARTED_UPLOAD_EAR_FILE, serviceInstance.getName()));

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				instanceService.hotdeploy(serviceInstance, localFile, loggedInUser, session);
				maxRetryCount = -1;
				uploaded = true;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_UPLOAD_EAR_FILE_ERROR,
						serviceInstance.getName(), e.getMessage()));
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_RETRYING_UPLOAD_EAR_FILE,
							serviceInstance.getName()));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_COMPLETED_UPLOAD_EAR_FILE, serviceInstance.getName()));

		return uploaded;
	}

}
