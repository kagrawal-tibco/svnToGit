/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import javax.management.MBeanServerConnection;

import com.tibco.cep.bemm.common.taskdefs.Task;


/**
 * @author ssinghal
 *
 */
public class JmxMBeanManager implements Task{
	
	String uri;
	String[] credentials;
	Callback cb;
	
	public JmxMBeanManager(String uri, String[] credentials, Callback cb){
		this.uri=uri;
		this.credentials=credentials;
		this.cb=cb;
	}
	
	@Override
	public Object perform() throws Throwable {
		Object obj = null;
		MBeanServerConnection conn = null;
		
		try{
			conn = JmxMBeanConnectionPool.getConnection(uri, credentials, 0);
			obj = cb.perform(conn);
			JmxMBeanConnectionPool.returnConnection(uri, conn);
		}catch(Exception e){
			JmxMBeanConnectionPool.retryBadConnection(uri, credentials, "BadState");
			throw e;
		}finally{
			return obj;
		}	
	}

	
	@Override
	public String getTaskName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	/*public Object getMBeanConnection(String uri, String[] credentials, Callback cb){
	Object obj = null;
	int retryCnt = 0;
	MBeanServerConnection conn = null;
	while(retryCnt<3){			
		try{
			conn = JmxMBeanConnectionPool.getConnection(uri, credentials, 0);
			obj = cb.perform(conn);
			JmxMBeanConnectionPool.returnConnection(uri, conn);
		}catch(Exception e){
			JmxMBeanConnectionPool.retryBadConnection(uri, credentials, "BadState");
		}finally{
			return obj;
		}	
	}
	return obj;
}*/
}
