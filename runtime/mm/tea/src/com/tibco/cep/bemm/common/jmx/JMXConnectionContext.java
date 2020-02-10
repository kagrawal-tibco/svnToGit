package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.PlatformManagedObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.tibco.cep.bemm.common.ConnectionContext;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsCollector.COLLECTOR_TYPE;

public class JMXConnectionContext implements ConnectionContext<MBeanServerConnection> {

	private MBeanServerConnection serverConnection = null;
	private Map<String, PlatformManagedObject> mxBeanProxies = new HashMap<>();
	private Set<ObjectName> gcMbeanNames = null;
	private String connectionType = null;
	
	public JMXConnectionContext(MBeanServerConnection serverConnection) {
		this.serverConnection = serverConnection;
		this.connectionType = COLLECTOR_TYPE.JMX.name();
	}
	
	@Override
	public MBeanServerConnection getConnection() {
		return serverConnection;
	}

	@Override
	public String getConnectionType() {
		return this.connectionType;
	}
	
	public Set<ObjectName> getGCMBeanNames() throws IOException {
		if (gcMbeanNames == null) {
			try {
				ObjectName gcObjectName = new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
				gcMbeanNames = serverConnection.queryNames(gcObjectName, null);
			} catch (MalformedObjectNameException ex) {
				try {
					throw new RuntimeException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.QUERY_GARBAGE_COLLECTION_POOL_NAMES_ERROR_MESSAGE), ex);
				} catch (ObjectCreationException e) {
					e.printStackTrace();
				}
			}
		}	
		return gcMbeanNames;
	}
	
	@SuppressWarnings("unchecked")
	public <P extends PlatformManagedObject> P getMXBeanProxy(String mxBeanName, Class<P> clazz) throws IOException {
		P mxMbean = (P) mxBeanProxies.get(mxBeanName);
		if (mxMbean == null)
			mxMbean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, mxBeanName, clazz);
		return mxMbean;
	}

	@Override
	public void closeConnection() {
		//close connection
	}

}
