/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.remote.JMXConnector;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * On similar lines as that used in MBeanServiceImpl.
 * Same instance is given out to multiple operations/threads (shared). Methods need to be synchronized since they need to update multiple collections atomically
 * 
 * @author bala
 *
 */
public class SharedJmxMBeanConnectionPool extends AbstractJmxMBeanConnectionPool {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SharedJmxMBeanConnectionPool.class);

	protected ConcurrentHashMap<MBeanServerConnection, JMXConnector> connectionMap = new ConcurrentHashMap<MBeanServerConnection, JMXConnector>();
	protected ConcurrentHashMap<JMXConnector, MBeanServerConnection> reverseConnectionMap = new ConcurrentHashMap<JMXConnector, MBeanServerConnection>();
	
	protected ConcurrentHashMap<String, JMXConnector> uriMap = new ConcurrentHashMap<String, JMXConnector>();
	protected ConcurrentHashMap<JMXConnector, String> reverseUriMap = new ConcurrentHashMap<JMXConnector,String>();
	
	@Override
	synchronized public MBeanServerConnection getConnection(String uri, String[] credentials, int timeout) throws IOException {
		
		JMXConnector connector = uriMap.get(uri);
		if (connector == null) {
			connector = makeNewConnection(uri, credentials);
			uriMap.put(uri, connector);
			reverseUriMap.put(connector, uri);
		}

		MBeanServerConnection connection = connector.getMBeanServerConnection();
		connectionMap.put(connection, connector);
		reverseConnectionMap.put(connector, connection);

		return connection;

	}

	@Override
	synchronized public void returnConnection (MBeanServerConnection conn) {
		returnConnection (conn, false);
	}

	@Override
	synchronized public void returnConnection(MBeanServerConnection conn, boolean isBad) {
		
		if (!isBad)	 {
			//do nothing
			return;
		}
		
		//bad connection, cleanup
		JMXConnector connector = connectionMap.remove(conn);
		if (connector != null) {
			try {
				connector.close();
			} catch (IOException e) {
				// ignore it
			}
		}
		reverseConnectionMap.remove(connector);
		
		String uri = reverseUriMap.remove(connector);
		if (uri != null) {
			uriMap.remove(uri);
		}
		
	}
	
	/**
	 * If a connection.closed notification, close the connection and clean up all the maps
	 * The next call to getConneciton will recreate a connection
	 * 
	 */
	@Override
	synchronized public void handleNotification(Notification notification, Object handback) {
		if ("jmx.remote.connection.closed".equalsIgnoreCase(notification.getType())) {
			Object source = notification.getSource();
			if (source instanceof JMXConnector) {
				JMXConnector connector = (JMXConnector) source;
				LOGGER.log(Level.DEBUG, "JMX connection closed for URL=[%s]", handback == null ? "" : handback.toString());

				MBeanServerConnection connection = reverseConnectionMap.remove(connector);
				if (connection != null) {
					connectionMap.remove(connection);
				}

				String uri = reverseUriMap.remove(connector);
				if (uri != null) {
					uriMap.remove(uri);
				}

				if (connector != null) {
					try {
						connector.close();
					} catch (IOException e) {
						// TODO:BG
					}
				}
			} else {
				LOGGER.log(Level.DEBUG, "JMX connection unrecognized for URL=[%s]", handback == null ? "" : handback.toString());
			}
		}
	}

	@Override
	synchronized public void closeConnection(String uri) {
		
		JMXConnector connector = uriMap.get(uri);
		if (connector != null) {
			try {
				connector.close();
			} catch (Exception e){
				
			}
			reverseUriMap.remove(connector);
			MBeanServerConnection connection = reverseConnectionMap.get(connector);
			if (connection != null) {
				connectionMap.remove(connection);
			}
		}
	}
	
	@Override
	synchronized public void close() {
		for (JMXConnector connector : uriMap.values()) {
			try {
				connector.close();
			} catch (Exception e) {
				
			}
		}
		uriMap.clear();
		reverseUriMap.clear();
		connectionMap.clear();
		reverseConnectionMap.clear();
	}
}
