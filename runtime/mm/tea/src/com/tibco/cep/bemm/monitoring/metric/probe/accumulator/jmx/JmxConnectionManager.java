package com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;



public class JmxConnectionManager {

	private static JmxConnectionManager instance = null;
	private Map<String, JMXConnector> connectors = new ConcurrentHashMap<String, JMXConnector>();
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JmxConnectionManager.class);
    private MessageService messageService;
    
	private JmxConnectionManager() {
       try {
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
	} catch (ObjectCreationException e) {
		e.printStackTrace();
	}
	}

	public static JmxConnectionManager getInstance() {
		if (instance == null) {
			instance = new JmxConnectionManager();
		}
		return instance;
	}

	public JMXConnector getJMXConnector(String serviceUrl) throws JmxException{
		return getJMXConnector(serviceUrl, null);
	}

	public JMXConnector getJMXConnector(String serviceUrl, Map<String, ?> environment) throws JmxException{
		if(connectors.containsKey(serviceUrl)){
			return connectors.get(serviceUrl);
		}
		JMXConnector jmxc = null;		
		try {
			JMXServiceURL url = new JMXServiceURL(serviceUrl);
			jmxc = JMXConnectorFactory.connect(url, environment);
			jmxc.addConnectionNotificationListener(new JMXConnectorNotificationListener(), null, serviceUrl);
			connectors.put(serviceUrl, jmxc);
		} catch (Exception e) {
			throw new JmxException(messageService.getMessage(MessageKey.INITIALIZING_JMX_CONNECTIVITY_ERROR), e);
		}
		return jmxc;
	}

	public class JMXConnectorNotificationListener implements NotificationListener{

		@Override
		public void handleNotification(Notification notification, Object handback) {
			if("jmx.remote.connection.closed".equalsIgnoreCase(notification.getType())){
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JMX_CONNECTION_CLOSED, handback==null?"":handback.toString()));
				connectors.remove(handback);
			}
		}

	}

}
