package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.WebserviceProperty;

/**
 * This class holds the general properties of Webservice task.
 * 
 * @author dijadhav
 * 
 */
public class WebserviceTaskGeneralProperty extends GeneralProperty implements WebserviceProperty{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -1379264268737940833L;
	/**
	 * Variable for resource mapped in webservice task.
	 */
	private String resource;
	/**
	 * Boolean value which indicates checkpoint is enabled or not.
	 */
	private boolean isCheckPoint;
	/**
	 * Selected service
	 */
	private String service ;
	/**
	 * Selected port
	 */
	private String port;
	/**
	 * Selected Operation
	 */
	private String operation;
	/**
	 * Soap action in selected wsdl
	 */
	private String soapAction;
	/**
	 * Timeout in selected wsdl.
	 */
	private String timeout;
	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return the isCheckPoint
	 */
	public boolean isCheckPoint() {
		return isCheckPoint;
	}
	/**
	 * @param isCheckPoint the isCheckPoint to set
	 */
	public void setCheckPoint(boolean isCheckPoint) {
		this.isCheckPoint = isCheckPoint;
	}
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	
	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	
	/**
	 * @return the soapAction
	 */
	public String getSoapAction() {
		return soapAction;
	}
	/**
	 * @param soapAction the soapAction to set
	 */
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return timeout;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public void setService(String service) {
		this.service = service;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}	
}
