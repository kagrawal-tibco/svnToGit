package com.tibco.tea.agent.be.ui.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.ServiceInstanceImpl;
import com.tibco.tea.agent.be.ui.model.impl.MasterHostUIModel;

/**
 * Interface for master host to communicate between UI and Service
 * 
 * @author dijadhav
 *
 */
@JsonDeserialize(as = MasterHostUIModel.class)
public interface MasterHost extends Serializable {

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
	 * Get BE details
	 * 
	 * @return the BE
	 */
	List<BE> getBE();

	/**
	 * @return the sshPort
	 */
	int getSshPort();

	/**
	 * @param hostName
	 *            the hostName to set
	 */
	void setHostName(String hostName);

	/**
	 * @param hostId
	 *            the hostId to set
	 */
	void setHostId(String hostId);

	/**
	 * @param hostIp
	 *            the hostIp to set
	 */
	void setHostIp(String hostIp);

	/**
	 * @param os
	 *            the os to set
	 */
	void setOs(String os);

	/**
	 * @param userName
	 *            the userName to set
	 */
	void setUserName(String userName);

	/**
	 * @param password
	 *            the password to set
	 */
	void setPassword(String password);

	/**
	 * Set BE details
	 * 
	 * @param beDetails
	 *            BE Details
	 */
	void setBE(List<BE> beDetails);

	/**
	 * @param sshPort
	 *            the sshPort to set
	 */
	void setSshPort(int sshPort);

	/**
	 * Get the deployment path
	 * 
	 * @return Deployment path
	 */
	public String getDeploymentPath();

	/**
	 * Sets the value of the deploymentPath property.
	 * 
	 * @param deploymentPath
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeploymentPath(String deploymentPath);

}
