/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.NotificationListener;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author ssinghal
 *
 */
abstract public class AbstractJmxMBeanConnectionPool implements JmxConnectionPool, NotificationListener {

	protected JMXConnector makeNewConnection(String jmxServiceUrl, String[] credentials) throws IOException {
		Map<String, Object> environment = new HashMap<String, Object>();
		environment.put(JMXConnector.CREDENTIALS, credentials);
		JMXServiceURL url = new JMXServiceURL(jmxServiceUrl);
		JMXConnector connector =  JMXConnectorFactory.connect(url, environment);
		
		connector.addConnectionNotificationListener(this, null, jmxServiceUrl);
		
		return connector;
	}
}
