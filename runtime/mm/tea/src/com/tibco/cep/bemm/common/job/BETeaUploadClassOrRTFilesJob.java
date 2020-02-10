package com.tibco.cep.bemm.common.job;

import javax.activation.DataSource;

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
 * Job is used to upload the file t remote location
 * 
 * @author dijadhav
 *
 */
public class BETeaUploadClassOrRTFilesJob implements GroupJob<Object> {
	private BEServiceInstancesManagementService service;
	private MessageService messageService;
	private DataSource dataSource;
	private String remoteUploadDir;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param service
	 * @param dataSource
	 * @param remoteUploadDir
	 */
	public BETeaUploadClassOrRTFilesJob(BEServiceInstancesManagementService service, DataSource dataSource,
			String uploadDir) {
		super();
		this.service = service;
		this.dataSource = dataSource;
		this.remoteUploadDir = uploadDir;
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
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPLOAD_DT_TEMPLATE_FILES));
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(
				((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs());

		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;

		ServiceInstance serviceInstance = context.getServiceInstance();

		int maxRetryCount = 1;
		boolean uploaded = false;
		PooledConnection<Session> connection = null;
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_UPLOAD_DT_TEMPLATE_FILES,
				serviceInstance.getName()));

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				service.upload(serviceInstance, dataSource, session, remoteUploadDir);
				maxRetryCount = -1;
				uploaded = true;
			} catch (Exception e) {
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPLOAD_DT_TEMPLATE_FILES_ERROR,
							serviceInstance.getName(), e.getMessage()));
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_UPLOAD_DT_TEMPLATE_FILES,
							serviceInstance.getName()));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_DT_TEMPLATE_FILES,
				serviceInstance.getName()));

		return uploaded;
	}

}
