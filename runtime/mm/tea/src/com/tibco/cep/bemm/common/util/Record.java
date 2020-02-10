package com.tibco.cep.bemm.common.util;

/**
 * This class contains the data related to instance/pu read from AS ,Coherence
 * or In-Memory
 * 
 * @author dijadhav
 *
 */
public class Record {
	private String clusterName;
	private String agentName;
	private int agentId;
	private String hostIP;
	private String machineName;
	private String engineType;
	private int processId;
	private int jmxPort;

	public Record() {

	}

	/**
	 * @return the clusterName
	 */
	public String getClusterName() {
		return clusterName;
	}

	/**
	 * @param clusterName
	 *            the clusterName to set
	 */
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName
	 *            the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the agentId
	 */
	public int getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the hostIP
	 */
	public String getHostIP() {
		return hostIP;
	}

	/**
	 * @param hostIP
	 *            the hostIP to set
	 */
	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}

	/**
	 * @param machineName
	 *            the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/**
	 * @return the engineType
	 */
	public String getEngineType() {
		return engineType;
	}

	/**
	 * @param engineType
	 *            the engineType to set
	 */
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	/**
	 * @return the processId
	 */
	public int getProcessId() {
		return processId;
	}

	/**
	 * @param processId
	 *            the processId to set
	 */
	public void setProcessId(int processId) {
		this.processId = processId;
	}

	/**
	 * @return the jmxPort
	 */
	public int getJmxPort() {
		return jmxPort;
	}

	/**
	 * @param jmxPort
	 *            the jmxPort to set
	 */
	public void setJmxPort(int jmxPort) {
		this.jmxPort = jmxPort;
	}
}
