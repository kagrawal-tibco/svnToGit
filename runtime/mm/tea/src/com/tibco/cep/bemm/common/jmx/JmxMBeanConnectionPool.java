/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx.JmxConnectionManager;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx.JmxException;

/**
 * @author ssinghal
 *
 */
public class JmxMBeanConnectionPool {
	
	private static ConcurrentHashMap<String, BlockingQueue<MBeanServerConnection>> pool = new ConcurrentHashMap<String, BlockingQueue<MBeanServerConnection>>();
	
	public static MBeanServerConnection getConnection(String uri, String[] credentials, int timeout) throws Exception{
		
		MBeanServerConnection conn = null;
		if(pool.containsKey(uri)){
			conn = pool.get(uri).take();			
		}else{
			BlockingQueue<MBeanServerConnection> connectionQ = new LinkedBlockingQueue<MBeanServerConnection>(1);
			connectionQ.put(makeNewConnection(uri, credentials));
			pool.putIfAbsent(uri, connectionQ);
			
			conn = pool.get(uri).take();
		}
		return conn;
	}
	
	public static void returnConnection(String uri, MBeanServerConnection conn){
		try{
			pool.get(uri).put(conn);
		}catch(InterruptedException ie){
			//log statement here
		}
	}
	
	private static MBeanServerConnection makeNewConnection(String jmxServiceUrl, String[] credentials) throws Exception{
		Map<String,Object> environment=new HashMap<String,Object>();
		environment.put (JMXConnector.CREDENTIALS, credentials);
		JMXConnector connector = JmxConnectionManager.getInstance().getJMXConnector(jmxServiceUrl,environment);
		try {
			return connector.getMBeanServerConnection();
		} catch (IOException e) {
			throw new Exception(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INITIALIZING_MBEAN_SERVER_ERROR), e);
		}
	}

	public static MBeanServerConnection retryBadConnection(String uri, String[] credentials, String badString){
		MBeanServerConnection conn = null;
		while(true){
			try{
				conn = makeNewConnection(uri, credentials);
				if(conn!=null){
					pool.get(uri).put(conn);
					break;
				}
			}catch(Exception e){
				//log - retrying bad connection
			}
		}
		
		return conn;
		
	}

}
