package com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

public class JmxBeanManager {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JmxBeanManager.class);
	private String jmxServiceUrl;
	private String[]  credentials = null;
	private MBeanServerConnection conn; //store it once.
	private MessageService messageService;
	
	public JmxBeanManager(String serviceUrl,String[] credentials) throws IOException, JmxException {
		try {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "JmxServiceUrl=[%s]", serviceUrl);
			}
			this.jmxServiceUrl= serviceUrl;
			this.credentials=credentials;
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		
		//this sets conn
		setBeanServerConnection();

	}

	public Object getAttribute(String objectName, String attributeName) throws JmxException, IOException{
		Object result;
		try {
			ObjectName mxbeanName = new ObjectName(objectName);
			result = getBeanServer().getAttribute(mxbeanName, attributeName);
		} 
		catch (IOException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.FETCHING_ATTRIBUTE_ERROR , attributeName) +" ",e);
			throw new JmxException(messageService.getMessage(MessageKey.FETCHING_ATTRIBUTE_ERROR , attributeName), e);
		}
		return result;
	}
	
	public Object[] invoke(String objectName, String methodName) throws JmxException, IOException {
		Object[] result = new Object[1];
		try {
			ObjectName mxbeanName = new ObjectName(objectName);
			if(getBeanServer().isRegistered(mxbeanName)){
				result[0] = getBeanServer().invoke(mxbeanName, methodName,
						new Object[] {}, new String[] {});
			}
			else{
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.MBEAN_NOT_REGISTERED ,objectName));
			}
		} 
		catch (IOException e) {
			throw e;
		} 
		catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.ERROR_WHILE_INVOKING_METHOD ,methodName)+" ", e);
			throw new JmxException(messageService.getMessage(MessageKey.ERROR_WHILE_INVOKING_METHOD, methodName), e);
		}
		return result;
	}
	public Object invoke(String objectName, String methodName,Object[] params,String[] signature) throws JmxException, IOException{
		Object result = null;
		try {
			ObjectName mxbeanName = new ObjectName(objectName);
			if(getBeanServer().isRegistered(mxbeanName)){
				result = getBeanServer().invoke(mxbeanName, methodName,
						params, signature);
			}
			else{
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.MBEAN_NOT_REGISTERED, objectName));
			}

		}	catch (IOException e) {
			throw e;
		} 	
		catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKING_PARAMETERIAZED_METHOD_ERROR ,methodName)+" ",e);
			throw new JmxException(messageService.getMessage(MessageKey.ERROR_WHILE_INVOKING_METHOD, methodName), e);
		}
		return result;
	}
	
	
	public Set<ObjectName> queryNames(String objectName) throws JmxException{
		Set<ObjectName> result = null;
		try {
			ObjectName mxbeanPattern = new ObjectName(objectName);
			result= getBeanServer().queryNames(mxbeanPattern,null);
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.QUERYING_PATTERN_ERROR ,objectName)+" ",e);
			throw new JmxException(messageService.getMessage(MessageKey.QUERYING_PATTERN_ERROR, objectName), e);
		}
		return result;
	}
	

	private MBeanServerConnection getBeanServer() {
		return conn;
	}
	
	private void setBeanServerConnection() throws JmxException, IOException {
//		Map<String,Object> environment=new HashMap<String,Object>();
//		environment.put (JMXConnector.CREDENTIALS, credentials);
		try {
			conn = BEMMServiceProviderManager.getInstance().getJmxConnectionPool().getConnection(jmxServiceUrl, credentials, -1);//(jmxServiceUrl,environment);
			
		} catch (IOException e) {
			throw e;
		} catch (Exception ex) {
			throw new JmxException(messageService.getMessage(MessageKey.INITIALIZING_MBEAN_SERVER_ERROR), ex);
		}
	}
	

	public AttributeList getAttributes(String mbeanName, String[] attributeNames) throws JmxException, IOException {

		AttributeList list=new AttributeList();
		try {
			ObjectName mxbeanName = new ObjectName(mbeanName);
			if(getBeanServer().isRegistered(mxbeanName)){
				list = getBeanServer().getAttributes(mxbeanName, attributeNames);
			}
			else{
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.MBEAN_NOT_REGISTERED, mbeanName));
			}
		} 
		catch (IOException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.FETCHING_MBEAN_ATTRIBUTE_ERROR, mbeanName)+" ",e);
			throw new JmxException(messageService.getMessage(MessageKey.FETCHING_MBEAN_ATTRIBUTE_ERROR, mbeanName), e);
		}
		return list;
	}

	public Map<String,Object> invokeAll(String mbeanName, String[] operationArray, HashMap<String, Object[]> operationParameters, HashMap<String, String[]> operationSignatures) throws JmxException, IOException {
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		try {
			for(String operation : operationArray){	
				ObjectName mxbeanName = new ObjectName(mbeanName);
				if(getBeanServer().isRegistered(mxbeanName)){
				Object result = getBeanServer().invoke(mxbeanName, operation,
						operationParameters.get(operation), operationSignatures.get(operation));
				resultMap.put(operation, result);
				}
				else{
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.MBEAN_NOT_REGISTERED, mbeanName));
				}
			}
		} 
		catch (IOException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.MBEAN_INVOKING_METHOD_ERROR, mbeanName)+" ", e);
			throw new JmxException(messageService.getMessage(MessageKey.MBEAN_INVOKING_METHOD_ERROR ,mbeanName), e);
		}
		return resultMap;
	}
	
	public void close() {
		close(false); //conneciton is clean
	}

	public void close (boolean isBad) {
		//this is called when an IOException is caught. Need to cleanup the connection and mark it as bad
		try {
			BEMMServiceProviderManager.getInstance().getJmxConnectionPool().returnConnection(conn, isBad);
			conn = null; //no one should use this
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
