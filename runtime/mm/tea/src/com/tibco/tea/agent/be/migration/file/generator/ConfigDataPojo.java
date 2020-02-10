package com.tibco.tea.agent.be.migration.file.generator;

public class ConfigDataPojo {
	
	String applicatioName;
	String instanceName;
	String machineName;
	String ip;
	String os;
	String deploymentPath;
	String sysUser;
	String sysPass;
	int sshPort;
	int jmxPort;
	public String getApplicatioName() {
		return applicatioName;
	}
	public void setApplicatioName(String applicatioName) {
		this.applicatioName = applicatioName;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
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
	public String getDeploymentPath() {
		return deploymentPath;
	}
	public void setDeploymentPath(String deploymentPath) {
		this.deploymentPath = deploymentPath;
	}
	public String getSysUser() {
		return sysUser;
	}
	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
	}
	public String getSysPass() {
		return sysPass;
	}
	public void setSysPass(String sysPass) {
		this.sysPass = sysPass;
	}
	public int getSshPort() {
		return sshPort;
	}
	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}
	public int getJmxPort() {
		return jmxPort;
	}
	public void setJmxPort(int jmxPort) {
		this.jmxPort = jmxPort;
	}
	
}
