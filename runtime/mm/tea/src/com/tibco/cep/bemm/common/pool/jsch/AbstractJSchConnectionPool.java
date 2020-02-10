package com.tibco.cep.bemm.common.pool.jsch;

import java.util.Properties;

import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.pool.ConnectionPool;
import com.tibco.cep.bemm.common.pool.PooledConnection;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.util.ManagementUtil;

/**
 * Connection pool implementation for JSch connections.
 * 
 * @author moshaikh
 */
abstract public class AbstractJSchConnectionPool implements ConnectionPool<Session> {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractJSchConnectionPool.class);
	protected Properties configuration;
	
	private final int connectionTimeout;
	
	public AbstractJSchConnectionPool(Properties configuration) {
		this.configuration = configuration;
		this.connectionTimeout = Integer.parseInt((String) ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getValue(configuration));
	}

	protected PooledConnection<Session> makeNewConnection(String pooledConnectionIdentifier, SshConfig sshConfig)
			throws Exception {
		String hostIp = sshConfig.getHostIp();
		String username = sshConfig.getUserName();
		String password = ManagementUtil.getDecodedPwd(sshConfig.getPassword());
		int port = sshConfig.getPort();
		
		LOGGER.log(Level.DEBUG, "Creating a new JSch Session '" + pooledConnectionIdentifier + "' for the pool, timeout:" + connectionTimeout);
		Session session = ManagementUtil.connectJSchSession(hostIp, username, password, port, connectionTimeout);
		return new PooledConnection<Session>(session, pooledConnectionIdentifier);
	}
}
