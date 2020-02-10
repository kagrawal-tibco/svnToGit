/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.remote.JMXConnector;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.impl.MBeanServiceImpl;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * A safe (not optimized) JMX connection pool. Each get creates a new connection and each return closes it.
 * 
 * @author bala
 *
 */
public class SafeJmxMBeanConnectionPool extends AbstractJmxMBeanConnectionPool {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SafeJmxMBeanConnectionPool.class);
	
	protected ConcurrentHashMap<MBeanServerConnection, JMXConnector> connectionMap = new ConcurrentHashMap<MBeanServerConnection, JMXConnector>();
	protected ConcurrentHashMap<JMXConnector, MBeanServerConnection> reverseConnectionMap = new ConcurrentHashMap<JMXConnector, MBeanServerConnection>();

	
	@Override
	synchronized public MBeanServerConnection getConnection(String uri, String[] credentials, int timeout) throws IOException {
		JMXConnector connector = makeNewConnection(uri, credentials);
		
		MBeanServerConnection connection = connector.getMBeanServerConnection();
		connectionMap.putIfAbsent(connection, connector);
		reverseConnectionMap.putIfAbsent(connector, connection);	

		return connection;

	}

	@Override
	synchronized public void returnConnection (MBeanServerConnection conn) {
		returnConnection (conn, false);
	}

	@Override
	synchronized public void returnConnection(MBeanServerConnection conn, boolean isBad) {
		JMXConnector connector = connectionMap.remove(conn);
		if (connector != null) {
			try {
				connector.close();
			} catch (IOException e) {
				// TODO:BG
			}
		}
		if (connector != null) {
			reverseConnectionMap.remove(connector);
		}
	}

	@Override
	synchronized public void handleNotification(Notification notification, Object handback) {
		try{
		if ("jmx.remote.connection.closed".equalsIgnoreCase(notification.getType())) {
			Object source = notification.getSource();
			if (source instanceof JMXConnector) {
				JMXConnector connector = (JMXConnector) source;
				LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JMX_CONNECTION_CLOSED, handback == null ? "" : handback.toString()));

				MBeanServerConnection connection = reverseConnectionMap.remove(connector);
				if (connection != null) {
					connectionMap.remove(connection);
				}
			} else {
				LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JMX_CONNECTION_UNRECOGNIZED, handback == null ? "" : handback.toString()));
			}
		 }
		}catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override 
	synchronized public void closeConnection(String uri) {
		//no-op here.
		
	}

	@Override
	synchronized public void close() {
		for (JMXConnector connector : reverseConnectionMap.keySet()) {
			try {
				connector.close();
			} catch (Exception e) {
				
			}
		}
		
	}
	
}
