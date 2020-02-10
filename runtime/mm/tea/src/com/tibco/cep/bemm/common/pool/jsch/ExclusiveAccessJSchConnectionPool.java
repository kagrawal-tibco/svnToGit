package com.tibco.cep.bemm.common.pool.jsch;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
 * This implementation uses the same JSch Session for all requesting threads, provides exclusive access to a session.
 * Discards a session only when it is marked unusable.
 * 
 * @author moshaikh
 */
public class ExclusiveAccessJSchConnectionPool extends AbstractJSchConnectionPool {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ExclusiveAccessJSchConnectionPool.class);

	private final ConcurrentHashMap<String, PooledConnection<Session>> sessions = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();
	
	private final int lockWaitTimeoutMillis;
	private MessageService messageService;

	public ExclusiveAccessJSchConnectionPool(Properties configuration) {
		super(configuration);
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		this.lockWaitTimeoutMillis = Integer.parseInt((String) ConfigProperty.BE_TEA_AGENT_SSH_EXCLUSIVE_SESSION_LOCK_WAIT_TIMEOUT.getValue(configuration));
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CREATED_EXCLUSIVE_ACCESS_JSCH_CONNECTION_POOL));
	}

	@Override
	public void returnConnection(PooledConnection<Session> connection) {
		if (connection.isMarkedUnusable()) {
			sessions.remove(connection.getConnectionIdentifier());
			connection.getUnderlyingConnection().disconnect();
		}
		locks.get(connection.getConnectionIdentifier()).unlock();
	}

	@Override
	public void close() {
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CLOSING_EXCLUSIVE_ACCESS_JSCH_CONNECTION_POOL));
		for (PooledConnection<Session> connection : sessions.values()) {
			connection.getUnderlyingConnection().disconnect();
		}
	}

	@Override
	public PooledConnection<Session> getConnection(PooledConnectionConfig connectionConfig) throws Exception {
		if (!locks.containsKey(connectionConfig.getConnectionIdentifierString())) {
			locks.putIfAbsent(connectionConfig.getConnectionIdentifierString(), new ReentrantLock(true));
		}
		
		if (connectionConfig instanceof JSchGroupJobExecutionContext) {
			if (!locks.get(connectionConfig.getConnectionIdentifierString()).tryLock(this.lockWaitTimeoutMillis, TimeUnit.MILLISECONDS)) {
				throw new TimeoutException(messageService.getMessage(MessageKey.TIMEOUT_WAITING_LOCK_ON_CONNECTION_POOL , this.lockWaitTimeoutMillis));
			}
			PooledConnection<Session> connection = null;
			try {
				connection = sessions.get(connectionConfig.getConnectionIdentifierString());
				if (connection == null || !connection.getUnderlyingConnection().isConnected()) {
					connection = sessions.get(connectionConfig.getConnectionIdentifierString());
					if (connection != null && !connection.getUnderlyingConnection().isConnected()) {
						sessions.remove(connectionConfig.getConnectionIdentifierString());
						connection.getUnderlyingConnection().disconnect();
						connection = null;
					}
					if (connection == null) {
						connection = makeNewConnection(connectionConfig.getConnectionIdentifierString(),
								((JSchGroupJobExecutionContext) connectionConfig).getSshConfig());
						LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CREATED_NEW_JSCH_SESSION_POOL , connectionConfig.getConnectionIdentifierString()));
						sessions.putIfAbsent(connectionConfig.getConnectionIdentifierString(), connection);
					}
				}
			} catch(Exception e) {
				//unlock
				locks.get(connectionConfig.getConnectionIdentifierString()).unlock();
				throw e;
			}
			return connection;
		} else {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONNECTION , connectionConfig));
		}
	}
}
