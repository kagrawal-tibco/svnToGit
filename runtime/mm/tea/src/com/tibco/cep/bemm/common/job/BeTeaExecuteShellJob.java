/**
 * @author ssinghal
 */
package com.tibco.cep.bemm.common.job;

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
 * 
 */
public class BeTeaExecuteShellJob implements GroupJob<Object>{
	
	private String remoteTempShellFileName;
	private String hostOs;
	private int timeout;
	private int maxRetry;
	private int threadSleepTime;
	private List<String> script;
	private MessageService messageService;
	
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);
	
	public BeTeaExecuteShellJob(String remoteTempShellFileName, String hostOs, int timeout, int maxRetry, int threadSleepTime, List<String> script){
		super();
		this.remoteTempShellFileName = remoteTempShellFileName;
		this.hostOs = hostOs;
		this.timeout = timeout;
		this.maxRetry = maxRetry;
		this.threadSleepTime = threadSleepTime;
		this.script = script;
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
		
		boolean executed = false;
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance()
				.getJSchConnectionPool(this.hostOs);
		PooledConnection<Session> connection = null;
		int maxRetryCount = 1;
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.EXECUTE_SHELL_SCRIPT));
		
		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				ManagementUtil.executeShellScript(script, remoteTempShellFileName, session, true, timeout, maxRetry, threadSleepTime);
				executed = true;
				maxRetryCount = -1;
			} catch (Exception e) {
				if (e instanceof JSchException) {
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.EXECUTE_SCRIPT_ERROR), e.getMessage());
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_EXECUTE_SCRIPT), e.getMessage());

				} else {
					maxRetryCount = -1;
				}
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}

		} while (maxRetryCount >= 0);
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.EXECUTE_SHELL_COMMAND));
		return executed;
	}

}
