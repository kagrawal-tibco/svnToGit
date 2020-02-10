package com.tibco.cep.bemm.common.job;

import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
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
import com.tibco.cep.bemm.model.MasterHost;

/**
 * Job is used to authenticate the master host
 * 
 * @author dijadhav
 *
 */
public class BETeaAuthenticateMasterHostJob implements GroupJob<Object> {
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);
	private MasterHost masterHost;
	/**
	 * @param masterHost
	 */
	public BETeaAuthenticateMasterHostJob(MasterHost masterHost) {
		super();
		this.masterHost=masterHost;
	}

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		boolean isAuthenticated = false;
		BETeaOperationResult operationResult = new BETeaOperationResult();
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance()
				.getJSchConnectionPool(masterHost.getOs());
		PooledConnection<Session> connection = null;
		try {
			connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
			isAuthenticated = true;
			operationResult.setName(masterHost.getHostName());
			operationResult.setResult(isAuthenticated);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JOB_STARTED_TO_AUTHENTICATE_HOST , masterHost.getHostName()));
		} catch (Exception e) {
			operationResult.setResult(e);
			if(!e.getMessage().contains("Auth fail") || e.getMessage().contains("Auth cancel"))
				LOGGER.log(Level.DEBUG,e.getMessage());
		} finally {
			if (null != connection) {
				sessionPool.returnConnection(connection);
			}
		}

		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JOB_COMPLETED_TO_AUTHETICATE_HOST , masterHost.getHostName()));

		return operationResult;
	}

}
