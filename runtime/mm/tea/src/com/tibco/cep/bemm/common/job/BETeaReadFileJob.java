/**
 * 
 */
package com.tibco.cep.bemm.common.job;

import java.io.BufferedReader;
import java.util.List;

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
import com.tibco.cep.bemm.management.util.ManagementUtil;

/**
 * @author ssinghal
 *
 */
public class BETeaReadFileJob implements GroupJob<Object> {
	
	private String remoteFile;
	private String hostOS;
	private int timeout;
	private MessageService messageService;
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);
	
	public BETeaReadFileJob(String remoteFile, String hostOS, int timeout) {
		super();
		this.remoteFile = remoteFile;
		this.hostOS = hostOS;
		this.timeout = timeout;
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
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_READ_FILE, remoteFile));
		int maxRetryCount = 1;
		
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(this.hostOS);
		PooledConnection<Session> connection = null;
		List<String> lines = null;
		
		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				lines = (List<String>) ManagementUtil.readRemoteFile(remoteFile, session, timeout);
				maxRetryCount = -1;
			} catch (Exception e) {
				if (e instanceof JSchException) {
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.READ_FILE_ERROR, e.getMessage()));
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_READ_FILE, e.getMessage()));

				} else {
					maxRetryCount = -1;
				}
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}

		} while (maxRetryCount >= 0);
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_READ_FILE, remoteFile));
		return lines;
	}

}
