package com.tibco.cep.dashboard.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.tibco.cep.dashboard.psvr.streaming.DataCacheUpdatersMXBean;

public class JMXComponentReset {

	private static final String JMX_URI = "service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi";

	public JMXComponentReset(){

	}

	public void resetBySource(String servername, int port, String source) {
		try {
			String uri = String.format(JMX_URI, servername, port);
			JMXServiceURL url =  new JMXServiceURL(uri);
			JMXConnector connector = JMXConnectorFactory.connect(url, new Hashtable<String, Object>());
			MBeanServerConnection beanServerConnection = connector.getMBeanServerConnection();
			Set<ObjectName> mBeanObjectNames = beanServerConnection.queryNames(new ObjectName("com.tibco.be:type=Agent,agentId=*,subType=Dashboard,service=datacacheupdater"), null);
			for (ObjectName mBeanObjectName : mBeanObjectNames) {
//				try {
//					Object returnVal = beanServerConnection.invoke(mBeanObjectName, "purgeBySource", new Object[]{source}, new String[]{String.class.getName()});
//					System.err.println(returnVal);
//				} catch (InstanceNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (MBeanException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ReflectionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				DataCacheUpdatersMXBean dataCacheUpdatersMXBean = JMX.newMXBeanProxy(beanServerConnection, mBeanObjectName, DataCacheUpdatersMXBean.class);
				System.err.println(dataCacheUpdatersMXBean.purgeBySource(source));
			}
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		JMXComponentReset reset = new JMXComponentReset();
		reset.resetBySource("localhost", 8888, "M_*");
	}

}
