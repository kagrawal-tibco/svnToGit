package com.tibco.cep.bemm.management.pinger.service.impl;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.ConnectException;

import javax.management.MBeanServerConnection;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;

public class JMXPingerService extends AbstractPingerService<JMXConnectionContext, MBeanServerConnection> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JMXPingerService.class);
		
	@Override
	public JMXConnectionContext initConnection(Monitorable monitorableEntity) throws Exception {
		JMXConnectionContext jmxConnContext;
        try {
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
        		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.ATTEMPTING_JMX_CONNECTION_ON_MONITORABLE_ENTITY, monitorableEntity.getKey()));
        	}
        	ConnectionInfo remoteConnectionInfo = monitorableEntity.getConnectionInfo("JMX");
            JMXConnClient jmcc =  new JMXConnClient(remoteConnectionInfo.getRemoteHost(),
            		remoteConnectionInfo.getRemotePort(),
    				null, null, true);
        	MBeanServerConnection serverConnection = jmcc.connect();
        	jmxConnContext = new JMXConnectionContext(serverConnection);
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
        		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JMX_CONNECTION_ON_MONITORABLE_ENTITY_SUCCESS, monitorableEntity.getKey()));
        	}
        } catch (JMXConnClientException ex) {
        	String message = "Could not connect to " + monitorableEntity.getKey();
        	if (LOGGER.isEnabledFor(Level.ERROR)) {
        		LOGGER.log(Level.ERROR, ex, message);
        	}	
			throw new Exception(message, ex);
        }
        return jmxConnContext;
	}

	@Override
	public boolean ping(Monitorable monitorableEntity) {
		boolean success = false;
		JMXConnectionContext connContext = this.getConnectionContext(monitorableEntity);
		if (connContext != null) {
			try {
				RuntimeMXBean runtimeMXMBean = connContext.getMXBeanProxy(ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
				if (runtimeMXMBean != null) {
					int i = 3;
					while (success == false && i > 0) {
						try {
							runtimeMXMBean.getUptime();
							success = true;
						} catch (UndeclaredThrowableException e) {
							if (e.getUndeclaredThrowable() instanceof ConnectException) {
								i--;
								success = false;
					        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
					        		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CAN_NOT_CONNECT_MONITORABLE_ENTITY, monitorableEntity.getKey()));
					        	}							
							}
						}
					}
				}	
			}catch (ObjectCreationException e) {
				e.printStackTrace();
			}
			catch (IOException ioex) {
	        	if (LOGGER.isEnabledFor(Level.ERROR)) {
	        		try {
						LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.JMX_CONNECTION_MONITORABLE_ENTITY_FAILED, monitorableEntity.getKey()));
					} catch (ObjectCreationException e) {
						e.printStackTrace();
					}
	        	}			
			}
		} else {
        	if (LOGGER.isEnabledFor(Level.ERROR)) {
        		try {
					LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.NO_JMX_CONNECTION_MONITORABLE_ENTITY, monitorableEntity.getKey()));
				} catch (ObjectCreationException e) {
					e.printStackTrace();
				}
        	}			
		}
		return success;
	}

	@Override
	protected void closeConnection(Monitorable monitorableEntity) {
		JMXConnectionContext jmxConnContext = getConnectionContext(monitorableEntity);
		if (jmxConnContext != null) {
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
        		LOGGER.log(Level.DEBUG, "Closing JMX Connection for monitorable entity %s", monitorableEntity.getKey());
        	}
			jmxConnContext.closeConnection();
		}	
	}
	
}
