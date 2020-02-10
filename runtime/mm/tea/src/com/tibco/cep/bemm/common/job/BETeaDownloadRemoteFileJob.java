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
public class BETeaDownloadRemoteFileJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Remote file path
	 */
	private String remoteFile;
	/**
	 * Local file path
	 */
	private String localFile;
	
	private String hostOS;
	/**
	 * Timeout
	 */
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
	public BETeaDownloadRemoteFileJob(String remoteFile, String localFile,String hostOS,int timeout) {
		super();
		this.remoteFile = remoteFile;
		this.localFile = localFile;
		this.hostOS=hostOS;
		this.timeout=timeout;
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
		boolean downloaded = false;
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(this.hostOS);
		PooledConnection<Session> connection = null;

		LOGGER.log(Level.DEBUG, "Job started to download remote file %s", remoteFile);

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				
				ManagementUtil.downloadRemoteFile(null, remoteFile, session, localFile,timeout);
				maxRetryCount = -1;
				downloaded = true;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DOWNLOAD_REMOTE_FILE_ERROR, remoteFile, e.getMessage()));
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_DOWNLOAD_REMOTE_FILE, remoteFile));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_DOWNLOAD_REMOTE_FILE, remoteFile));

		return downloaded;
	}

}
