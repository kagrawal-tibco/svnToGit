package com.tibco.cep.bemm.common.pool.jsch;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

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
import com.tibco.cep.bemm.common.util.ConfigProperty;

/**
 * This implementation uses the same JSch Session for all requesting threads.
 * Discards a session only when it is marked unusable.
 * 
 * @author moshaikh
 */
public class SharedJSchConnectionPool extends AbstractJSchConnectionPool {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SharedJSchConnectionPool.class);

	private ConcurrentHashMap<String, PooledConnection<Session>> sessions = new ConcurrentHashMap<>();

	private MessageService messageService;
	public SharedJSchConnectionPool(Properties configuration) {
		super(configuration);
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CREATED_SHARED_JSCH_CONNECTION_POOL));
	}

	@Override
	public void returnConnection(PooledConnection<Session> connection) {
		if (connection.isMarkedUnusable()) {
			sessions.remove(connection.getConnectionIdentifier());
			connection.getUnderlyingConnection().disconnect();
		}
	}

	@Override
	public void close() {
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CLOSING_SHARED_JSCH_CONNECTION_POOL));
		for (PooledConnection<Session> connection : sessions.values()) {
			connection.getUnderlyingConnection().disconnect();
		}
	}

	@Override
	public PooledConnection<Session> getConnection(PooledConnectionConfig connectionConfig) throws Exception {
		if (connectionConfig instanceof JSchGroupJobExecutionContext) {
			final String pooledConnectionIdentifier = connectionConfig.getConnectionIdentifierString();
			PooledConnection<Session> connection = sessions.get(pooledConnectionIdentifier);
			if (connection == null || !connection.getUnderlyingConnection().isConnected()) {
				synchronized (this) {
					connection = sessions.get(pooledConnectionIdentifier);
					if (connection != null && !connection.getUnderlyingConnection().isConnected()) {
						sessions.remove(connection.getConnectionIdentifier());
						connection.getUnderlyingConnection().disconnect();
						connection = null;
					}
					if (connection == null) {
						connection = makeNewConnection(pooledConnectionIdentifier,
								((JSchGroupJobExecutionContext) connectionConfig).getSshConfig());
						sessions.putIfAbsent(pooledConnectionIdentifier, connection);
					}
				}
			}
			return connection;
		} else {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE) + connectionConfig);
		}
	}
}
