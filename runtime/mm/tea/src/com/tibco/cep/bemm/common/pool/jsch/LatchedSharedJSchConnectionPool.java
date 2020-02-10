package com.tibco.cep.bemm.common.pool.jsch;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

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
 * This implementation uses the same JSch Session for all requesting threads. Provides only limited number of permits.
 * Discards a session only when it is marked unusable or found to be disconnected.
 * 
 * @author moshaikh
 */
public class LatchedSharedJSchConnectionPool extends AbstractJSchConnectionPool {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LatchedSharedJSchConnectionPool.class);
	public static final int DEFAULT_MAX_PERMITS = 5;
	private MessageService messageService;

	
	/**
	 * JSch sessions pool
	 */
	private final ConcurrentHashMap<String, PooledConnection<Session>> sessions = new ConcurrentHashMap<>();
	
	/**
	 * A map to hold semaphores for each connection.
	 */
	private final ConcurrentHashMap<String, Semaphore> semaphores = new ConcurrentHashMap<>();
	
	/**
	 * Total available permits for each connection.
	 */
	private final int maxPermits;

	public LatchedSharedJSchConnectionPool(Properties configuration) {
		super(configuration);
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		String latchCount = configuration.getProperty("be.tea.agent.jsch.win.pool.latch.count", String.valueOf(DEFAULT_MAX_PERMITS));
		this.maxPermits = Integer.parseInt(latchCount) < 1 ? DEFAULT_MAX_PERMITS : Integer.parseInt(latchCount);
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CREATED_LATCHED_SHARED_JSCH_CONNECTION_POOL));
	}

	@Override
	public void returnConnection(PooledConnection<Session> connection) {
		if (connection.isMarkedUnusable()) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DISCARDING_CONNECTION , connection.getConnectionIdentifier()));
			sessions.remove(connection.getConnectionIdentifier());
			connection.getUnderlyingConnection().disconnect();
		}
		semaphores.get(connection.getConnectionIdentifier()).release();
	}

	@Override
	public void close() {
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.CLOASING_LATCHED_SHARED_JSCH_CONNECTION_POOL));
		for (PooledConnection<Session> connection : sessions.values()) {
			connection.getUnderlyingConnection().disconnect();
		}
	}

	@Override
	public PooledConnection<Session> getConnection(PooledConnectionConfig connectionConfig) throws Exception {
		if (!semaphores.containsKey(connectionConfig.getConnectionIdentifierString())) {
			semaphores.putIfAbsent(connectionConfig.getConnectionIdentifierString(), new Semaphore(this.maxPermits, true));
		}
		
		if (connectionConfig instanceof JSchGroupJobExecutionContext) {
			//Acquire a permit and then proceed to getting/creating a connection.
			semaphores.get(connectionConfig.getConnectionIdentifierString()).acquire();
			PooledConnection<Session> connection = null;
			try {
				connection = sessions.get(connectionConfig.getConnectionIdentifierString());
				if (connection == null || !connection.getUnderlyingConnection().isConnected()) {
					synchronized (semaphores.get(connectionConfig.getConnectionIdentifierString())) {
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
				}
			} catch(Exception e) {
				//Release back the permit.
				semaphores.get(connectionConfig.getConnectionIdentifierString()).release();
				throw e;
			}
			return connection;
		} else {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONNECTION, connectionConfig));
		}
	}
	
	//TODO: detect and handle permit leaks
}
