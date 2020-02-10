/**
 * 
 */
package com.tibco.cep.bemm.common.pool.jsch;

import java.util.Properties;

import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.pool.PooledConnection;
import com.tibco.cep.bemm.common.pool.PooledConnectionConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * A safe implementation for JSch connections pool. Creates a new connection each time.
 * @author moshaikh
 */
public class SafeJSchConnectionPool extends AbstractJSchConnectionPool {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SafeJSchConnectionPool.class);
	private MessageService messageService;
	
	public SafeJSchConnectionPool(Properties configuration) {
		super(configuration);
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CREATED_SAFE_JSCH_CONNECTION_POOL));
	}
	
	@Override
	public PooledConnection<Session> getConnection(PooledConnectionConfig connectionConfig) throws Exception {
		if (connectionConfig instanceof JSchGroupJobExecutionContext) {
			final String identifierString = connectionConfig.getConnectionIdentifierString();
			return makeNewConnection(identifierString, ((JSchGroupJobExecutionContext) connectionConfig).getSshConfig());
		} else {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE) + connectionConfig);
		}
	}
	
	@Override
	public void returnConnection(PooledConnection<Session> connection) {
		try {
			connection.getUnderlyingConnection().disconnect();
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.PROBLEM_CLOSING_SESSION, e.getMessage()));
		}
	}

	@Override
	public void close() {
		//Nothing to close, individual connections are already closed in returnConnection()
	}
}
