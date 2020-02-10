package com.tibco.cep.bemm.common.job;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.pool.ConnectionPool;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.PooledConnection;
import com.tibco.cep.bemm.common.pool.PooledConnectionConfig;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.util.ManagementUtil;

/**
 * This class is used to delete the deployment
 * 
 * @author dijadhav
 *
 */
public class BETeaDeleteRemoteFile implements GroupJob<Object> {
	/**
	 * Remote file which needs to be deleted
	 */
	private String remoteFile;
	private String hostOs;
	private int timeout;
	private int maxRetry;
	private int threadSleepTime;

	/**
	 * Constructor to set field value
	 * 
	 * @param remoteFile
	 * @param hostOs
	 */
	public BETeaDeleteRemoteFile(String remoteFile, String hostOs, int timeout, int maxRetry, int threadSleepTime) {
		super();
		this.remoteFile = remoteFile;
		this.hostOs = hostOs;
		this.timeout = timeout;
		this.maxRetry = maxRetry;
		this.threadSleepTime = threadSleepTime;
	}

	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		boolean uploaded = false;
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance()
				.getJSchConnectionPool(this.hostOs);
		PooledConnection<Session> connection = null;
		int maxRetryCount = 1;
		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JOB_STARTED_DELETE_REMOTE_FILE, remoteFile));
		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				ManagementUtil.executeCommand("rm -r " + remoteFile, session, true, timeout, maxRetry, threadSleepTime);
				uploaded = true;
				maxRetryCount = -1;
			} catch (Exception e) {
				if (e instanceof JSchException) {
					LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DELETE_REMOTE_FILE_ERROR, remoteFile, e.getMessage()));
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.RETRYING_DELETE_REMOTE_FILE, remoteFile,
							e.getMessage()));

				} else {
					maxRetryCount = -1;
				}
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}

		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DELETE_REMOTE_FILE_COMPLETED, remoteFile));
		return uploaded;
	}

}
