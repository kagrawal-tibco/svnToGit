package com.tibco.cep.bemm.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.HostImpl;

@JsonDeserialize(as = HostImpl.class)
public interface Host extends Serializable, Monitorable {
	/**
	 * 
	 * @return master host
	 */
	MasterHost getMasterHost();

	/**
	 * @param masterHost
	 *            - Master Host instance
	 */
	void setMasterHost(MasterHost masterHost);

	/**
	 * @return the hostName
	 */
	String getHostName();

	/**
	 * @return the hostId
	 */
	String getHostId();

	/**
	 * @return the hostIp
	 */
	String getHostIp();

	/**
	 * @return the os
	 */
	String getOs();

	/**
	 * @return the userName
	 */
	String getUserName();

	/**
	 * @return the password
	 */
	String getPassword();

	/**
	 * @return the sshPort
	 */
	int getSshPort();

	/**
	 * @return the machineName
	 */
	String getMachineName();

	/**
	 * @return the isPredefined
	 */
	boolean isPredefined();

	/**
	 * @return the key
	 */
	String getKey();

	/**
	 * @return the instances
	 */
	List<ServiceInstance> getInstances();

	/**
	 * Add the service instance
	 * 
	 * @param instance
	 */
	void addInstance(ServiceInstance instance);

	/**
	 * @return the application
	 */
	Application getApplication();

	/**
	 * @param application
	 *            the application to set
	 */
	void setApplication(Application application);

	/**
	 * @return the status
	 */
	String getStatus();

	/**
	 * @param status
	 *            the status to set
	 */
	void setStatus(String status);

	void setDeployed(boolean deployed);

	boolean getDeployed();

	/**
	 * @return the deploymentStatus
	 */
	public String getDeploymentStatus();

	/**
	 * @param deploymentStatus
	 *            the deploymentStatus to set
	 */
	public void setDeploymentStatus(String deploymentStatus);

	/**
	 * @return the deploymentDescription
	 */
	public String getDeploymentDescription();

	/**
	 * @param deploymentDescription
	 *            the deploymentDescription to set
	 */
	public void setDeploymentDescription(String deploymentDescription);

	/**
	 * Get BE details by Id
	 * 
	 * @param id
	 * @return
	 */
	BE getBEDetailsById(String id);

	List<BE> getBE();
}