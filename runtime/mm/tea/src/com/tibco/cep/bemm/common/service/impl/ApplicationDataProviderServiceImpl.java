package com.tibco.cep.bemm.common.service.impl;

import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.ApplicationDataProviderService;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.runtime.management.impl.cluster.ManagementTableMBeanImpl;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

/**
 * This is the implementation class of ApplicationDataProviderService interface.
 * 
 * @author dijadhav
 *
 */
public class ApplicationDataProviderServiceImpl extends AbstractStartStopServiceImpl implements ApplicationDataProviderService {
	private MessageService messageService;

	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ApplicationDataProviderServiceImpl.class);

	/**
	 * Default constructor
	 */
	public ApplicationDataProviderServiceImpl() {
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getTopologyData(ServiceInstance serviceInstance) {
		return getInstanceInfo(serviceInstance);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getInstanceInfo(ServiceInstance instance) {
		String password = instance.getHost().getPassword();
		String username = instance.getHost().getUserName();
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		JMXConnClient client = new JMXConnClient(instance.getHost().getHostIp(), instance.getJmxPort(), username,
				decodedPassword, true);
		JMXConnector jmxConnector = null;
		Map<String, Object> instanceInfo = null;
		try {
			jmxConnector = client.getJMXConnector();
			MBeanServerConnection connection = client.getMBeanServerConnection();
			if (null != connection) {
				ObjectName mgmtMBeanObjName = new ObjectName(ManagementTableMBeanImpl.OBJ_NAME_MNGMT_TABLE);
				instanceInfo = (Map<String, Object>) connection.getAttribute(mgmtMBeanObjName, "InstanceInfo");
			}
		} catch (Exception ex) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UNABLE_CONNECT_INSTANCE_AT_JMX_PORT, instance.getName(), instance.getJmxPort()));
		} finally {
			try {
				if (jmxConnector != null)
					jmxConnector.close();
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CLOSING_JMX_CONNECTION_INSTANCE_ERROR, instance.getName(), instance.getJmxPort()));
			}
		}
		return instanceInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getInstanceAgentData(ServiceInstance serviceInstance) {
		String password = serviceInstance.getJmxPassword();
		String username = serviceInstance.getJmxUserName();
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		JMXConnClient client = new JMXConnClient(serviceInstance.getHost().getHostIp(), serviceInstance.getJmxPort(), username,
				decodedPassword, true);
		JMXConnector jmxConnector = null;
		Map<String, String> instanceAgentsInfo = null;
		try {
			jmxConnector = client.getJMXConnector();
			MBeanServerConnection connection = client.getMBeanServerConnection();
			if (null != connection) {
				ObjectName mgmtMBeanObjName = new ObjectName(ManagementTableMBeanImpl.OBJ_NAME_MNGMT_TABLE);
				instanceAgentsInfo = (Map<String, String>) connection.getAttribute(mgmtMBeanObjName, "InstanceAgentsInfo");
			}
		} catch (Exception ex) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UNABLE_CONNECT_INSTANCE_AT_JMX_PORT, serviceInstance.getName(), serviceInstance.getJmxPort()));
		} finally {
			try {
				if (jmxConnector != null)
					jmxConnector.close();
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CLOSING_JMX_CONNECTION_INSTANCE_ERROR, serviceInstance.getName(), serviceInstance.getJmxPort()));
			}
		}
		return instanceAgentsInfo;
	}

}
