package com.tibco.cep.bemm.monitoring.util;

import java.util.List;

import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;

public interface BeTeaAgentMonitorable extends Monitorable{
	
	public int getJmxPort();

	public void setJmxPort(int jmxPort);
	
	public void setName(String name);

	public List<Attribute> getBasicFactAttributes();

	public String getJmxUserName();

	public String getJmxPassword();

	public String getHealthStatus();

	public void setHealthStatus(String healthStatus);

	public void setJmxUserName(String jmxUserName);

	public void setJmxPassword(String jmxPassword);

	public String getHostIp();

	public void setHostIp(String hostIp);
	
	public long getStartingTime();

	public void setStartingTime(long startingTime);
}
