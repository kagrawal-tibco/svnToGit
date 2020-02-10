package com.tibco.cep.bemm.model;

import java.util.List;
import java.util.Map;

/**
 * @author dijadhav
 *
 */
public interface MasterHost extends Versionable {
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
	 * @return the beHome
	 */
	List<BE> getBE();

	/**
	 * @return the BE Home
	 */
	String getBEHome();

	/**
	 * @return the BE tra
	 */
	String getBETra();

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
	 * @param beHome
	 *            the beHome to set
	 */
	void setBE(List<BE> beDetail);

	/**
	 * @param beHome
	 *            the beHome to set
	 */
	void setBEHome(String beHome);

	/**
	 * @param beTra
	 *            the beTra to set
	 */
	void setBETra(String beTra);

	/**
	 * @param sshPort
	 *            the sshPort to set
	 */
	void setSshPort(int sshPort);

	/**
	 * @return the machineName
	 */
	String getMachineName();

	/**
	 * @param machineName
	 *            the machineName to set
	 */
	void setMachineName(String machineName);

	/**
	 * @return the isPredefined
	 */
	boolean isPredefined();

	/**
	 * @param isPredefined
	 *            the isPredefined to set
	 */
	void setPredefined(boolean isPredefined);

	/**
	 * @return the key
	 */
	String getKey();

	/**
	 * @return the status
	 */
	String getStatus();

	/**
	 * @param status
	 *            the status to set
	 */
	void setStatus(String status);

	/**
	 * Gets the value of the deploymentPath property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeploymentPath();

	/**
	 * Sets the value of the deploymentPath property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeploymentPath(String value);

	void addJMXPort(int jmxPort, boolean isUsed);

	Map<Integer, Boolean> getJmxPortMap();

	boolean isAuthenticated();

	void setAuthenticated(boolean authenticated);

}
