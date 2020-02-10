/**
 * 
 */
package com.tibco.tea.agent.be.ui.model.impl;

import java.util.List;
import java.util.Set;

import com.tibco.tea.agent.be.ui.model.BE;
import com.tibco.tea.agent.be.ui.model.MasterHost;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * 
 * Model to get the data from UI
 * 
 * @author dijadhav
 *
 */
public class MasterHostUIModel implements MasterHost {
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
	 * SSH port
	 */
	private int sshPort;
	

	private String deploymentPath;
	@Override
	public String getHostName() {
		return this.hostName;
	}

	@Override
	public String getHostId() {
		return this.hostId;
	}

	@Override
	public String getHostIp() {
		return this.hostIp;
	}

	@Override
	public String getOs() {
		return this.os;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public List<BE> getBE() {
		return this.be;
	}

	@Override
	public int getSshPort() {
		return this.sshPort ;
	}

	@Override
	public void setHostName(String hostName) {
		this.hostName=hostName;
	}

	@Override
	public void setHostId(String hostId) {
		this.hostId=hostId;
	}

	@Override
	public void setHostIp(String hostIp) {
		this.hostIp=hostIp;
	}

	@Override
	public void setOs(String os) {
		this.os=os;
	}

	@Override
	public void setUserName(String userName) {
		this.userName=userName;
	}

	@Override
	public void setPassword(String password) {
		this.password=password;
	}

	@Override
	public void setBE(List<BE> beDetails) {
		this.be=beDetails;
	}


	@Override
	public void setSshPort(int sshPort) {
		this.sshPort=sshPort;
	}

	
	@Override
	public String getDeploymentPath() {
		return this.deploymentPath;
	}

	@Override
	public void setDeploymentPath(String deploymentPath) {
		this.deploymentPath=deploymentPath;
	}
}
