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
import com.tibco.cep.bemm.management.util.ManagementUtil;

/**
 * Job is used to upload the file t remote location
 * 
 * @author dijadhav
 *
 */
public class BETeaUploadFileJob implements GroupJob<Object> {
	/**
	 * Remote file path
	 */
	private String remoteFile;
	/**
	 * Local file path
	 */
	private String localFile;
	private String hostOS;
	private MessageService messageService;
	private int timeout;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * Constructor to set remote and local file
	 * 
	 * @param remoteFile
	 *            - Remote file
	 * @param localFile
	 *            - Local file
	 */
	public BETeaUploadFileJob(String remoteFile, String localFile, String hostOS, int timeout) {
		super();
		this.remoteFile = remoteFile;
		this.localFile = localFile;
		this.hostOS = hostOS;
		this.timeout = timeout;
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
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STRATED_UPLOAD_FILE, localFile));
		int maxRetryCount = 1;
		boolean uploaded = false;
		String os = this.hostOS;
		if (null == os)
			os = ((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs();
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(os);
		PooledConnection<Session> connection = null;
		// Retry if there is JSCH exception
		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();

				ManagementUtil.uploadToRemoteMahine(remoteFile, session, localFile, this.timeout);
				maxRetryCount = -1;
				uploaded = true;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPLOAD_FILE_ERROR, localFile));
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_UPLOAD_FILE, localFile));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_UPLOAD_FILE, localFile));
		return uploaded;
	}

}
