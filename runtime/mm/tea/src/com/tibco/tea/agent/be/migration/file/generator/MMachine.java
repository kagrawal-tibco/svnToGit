package com.tibco.tea.agent.be.migration.file.generator;

import java.util.List;
import java.util.Set;

/**
 * @author ssinghal
 *
 */

public class MMachine {
	
	String machineName;
	String ip;
	String os;
	List<com.tibco.tea.agent.be.ui.model.BE> beList;
	String sshUser;
	String sshPass;
	int sshPort;
	String deploymentPath;
	Set<String> relatedApps;
	
	
	
	
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public List<com.tibco.tea.agent.be.ui.model.BE> getBeList() {
		return beList;
	}
	public void setBeList(List<com.tibco.tea.agent.be.ui.model.BE> beList) {
		this.beList = beList;
	}
	public String getSshUser() {
		return sshUser;
	}
	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}
	public String getSshPass() {
		return sshPass;
	}
	public void setSshPass(String sshPass) {
		this.sshPass = sshPass;
	}
	public int getSshPort() {
		return sshPort;
	}
	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}
	public String getDeploymentPath() {
		return deploymentPath;
	}
	public void setDeploymentPath(String deploymentPath) {
		this.deploymentPath = deploymentPath;
	}
	public Set<String> getRelatedApps() {
		return relatedApps;
	}
	public void setRelatedApps(Set<String> relatedApps) {
		this.relatedApps = relatedApps;
	}
	
	

}
