/**
 * 
 */
package com.tibco.cep.bemm.model.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * @author dijadhav
 *
 */
public class MasterHostImpl implements MasterHost {
	/**
	 * Name of the host
	 */
	private String hostName;
	/**
	 * Id of the host
	 */
	private String hostId;
	/**
	 * IP address of the host
	 */
	private String hostIp;
	/**
	 * Operating system of the host
	 */
	private String os;

	/**
	 * User Name to login on the host
	 */
	private String userName;
	/**
	 * Password of the user
	 */
	private String password;
	/**
	 * Path of the be home of the host
	 */
	private List<BE> be;

	/**
	 * BE Home
	 */
	private String beHome;

	/**
	 * BE Tra
	 */
	private String beTra;
	/**
	 * SSH port
	 */
	private int sshPort;
	/**
	 * Name of the machine
	 */
	private String machineName;
	/**
	 * Boolean value which indicates whether the host is predefined or not.
	 */
	private boolean isPredefined;

	private String status = BETeaAgentStatus.UNREACHABLE.getStatus();
	private String deploymentPath;

	private AtomicLong version = new AtomicLong(0);
	private Map<Integer, Boolean> jmxPortMap = new TreeMap<Integer, Boolean>();
	private boolean authenticated;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getHostName()
	 */
	@Override
	public String getHostName() {
		return hostName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getHostId()
	 */
	@Override
	public String getHostId() {
		return hostId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getHostIp()
	 */
	@Override
	public String getHostIp() {
		return hostIp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getOs()
	 */
	@Override
	public String getOs() {
		return os;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getUserName()
	 */
	@Override
	public String getUserName() {
		return userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getBeHome()
	 */
	@Override
	public List<BE> getBE() {
		return be;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getSshPort()
	 */
	@Override
	public int getSshPort() {
		return sshPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setHostId(java.lang.String)
	 */
	@Override
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setHostIp(java.lang.String)
	 */
	@Override
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setOs(java.lang.String)
	 */
	@Override
	public void setOs(String os) {
		this.os = os;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setBeHome(java.lang.String)
	 */
	@Override
	public void setBE(List<BE> beDetails) {
		this.be = beDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setSshPort(int)
	 */
	@Override
	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getMachineName()
	 */
	@Override
	public String getMachineName() {
		return machineName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setMachineName(java.lang.String)
	 */
	@Override
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#isPredefined()
	 */
	@Override
	public boolean isPredefined() {
		return this.isPredefined;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#setPredefined(boolean)
	 */
	@Override
	public void setPredefined(boolean isPredefined) {
		this.isPredefined = isPredefined;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.MasterHost#getKey()
	 */
	@Override
	public String getKey() {
		return this.hostId;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the value of the deploymentPath property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@Override
	public String getDeploymentPath() {
		return deploymentPath;
	}

	/**
	 * Sets the value of the deploymentPath property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	@Override
	public void setDeploymentPath(String value) {
		this.deploymentPath = value;
	}

	@Override
	public Long incrementVersion() {
		return version.incrementAndGet();
	}

	@Override
	public Long getVersion() {
		return version.get();
	}

	@Override
	public String getBEHome() {
		return beHome;
	}

	@Override
	public String getBETra() {
		return beTra;
	}

	@Override
	public void setBEHome(String beHome) {
		this.beHome = beHome;
	}

	@Override
	public void setBETra(String beTra) {
		this.beTra = beTra;
	}

	@Override
	public Map<Integer, Boolean> getJmxPortMap() {
		return jmxPortMap;
	}

	@Override
	public void addJMXPort(int jmxPort, boolean isUsed) {
		jmxPortMap.put(jmxPort, isUsed);

	}

	/**
	 * @return the authenticated
	 */
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	/**
	 * @param authenticated
	 *            the authenticated to set
	 */
	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

}
