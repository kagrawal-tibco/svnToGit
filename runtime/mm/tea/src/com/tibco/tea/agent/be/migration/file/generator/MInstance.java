package com.tibco.tea.agent.be.migration.file.generator;

import java.util.Map;

/**
 * @author ssinghal
 *
 */

public class MInstance {
	
	String instanceName;
	String pu;
	String machineName;
	String beHome;
	int jmxPort;
	String deployPath;	
	Map<String, Object> jvmProperties;
	Map<String, Object> globalVariables;
	
	
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getPu() {
		return pu;
	}
	public void setPu(String pu) {
		this.pu = pu;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getBeHome() {
		return beHome;
	}
	public void setBeHome(String beHome) {
		this.beHome = beHome;
	}
	public int getJmxPort() {
		return jmxPort;
	}
	public void setJmxPort(int jmxPort) {
		this.jmxPort = jmxPort;
	}
	public String getDeployPath() {
		return deployPath;
	}
	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}
	public Map<String, Object> getJvmProperties() {
		return jvmProperties;
	}
	public void setJvmProperties(Map<String, Object> jvmProperties) {
		this.jvmProperties = jvmProperties;
	}
	public Map<String, Object> getGlobalVariables() {
		return globalVariables;
	}
	public void setGlobalVariables(Map<String, Object> globalVariables) {
		this.globalVariables = globalVariables;
	}
	
	
	
	

	
}
