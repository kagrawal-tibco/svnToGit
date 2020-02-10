package com.tibco.cep.bemm.monitoring.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;

public class BeTeaAgentMonitorableImpl implements BeTeaAgentMonitorable{
	
	private int jmxPort;
	
	private String name;
	
	private String hostIp;
	
	private String status;
	
	private String healthStatus=BEEntityHealthStatus.ok.getHealthStatus(); //default health ok

	private String jmxUserName="";

	private String jmxPassword="";
	
	private long startingTime;

	@Override
	public ENTITY_TYPE getType() {
		return ENTITY_TYPE.BE_TEA_AGENT;
	}

	@Override
	public String getKey() {
		return "";
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status=status;
		
	}

	@Override
	public Map<String, Object> getBasicAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Attribute> getBasicFactAttributes() {
		List<Attribute> basicAttributes = new ArrayList<Attribute>();
		//TODO : Get unique agent Id
		basicAttributes.add(new Attribute(MetricAttribute.BE_TEA_AGENT_NAME, this.getName()));
		basicAttributes.add(new Attribute(MetricAttribute.TIMESTAMP, System.currentTimeMillis()));
		basicAttributes.add(new Attribute(MetricAttribute.BE_TEA_AGENT_STATUS,status));

		return basicAttributes;
	}

	@Override
	public ConnectionInfo getConnectionInfo(String connectionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name=name;
		
	}

	@Override
	public int getJmxPort() {
		return jmxPort;
	}

	@Override
	public void setJmxPort(int jmxPort) {
		this.jmxPort=jmxPort;
	}
	
	@Override
	public String getHostIp() {
		return hostIp;
	}

	@Override
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	@Override
	public String getJmxUserName() {
		return jmxUserName;
	}

	@Override
	public String getJmxPassword() {
		return jmxPassword;
	}
	@Override
	public String getHealthStatus() {
		return healthStatus;
	}
	@Override
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}
	@Override
	public void setJmxUserName(String jmxUserName) {
		this.jmxUserName = jmxUserName;
	}
	@Override
	public void setJmxPassword(String jmxPassword) {
		this.jmxPassword = jmxPassword;
	}
	@Override
	public long getStartingTime() {
		return startingTime;
	}
	@Override
	public void setStartingTime(long startTime) {
		this.startingTime = startTime;
	}
}
