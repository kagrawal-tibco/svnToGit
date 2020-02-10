package com.tibco.cep.bemm.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.AgentImpl;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;

/**
 * This interface is used to get details of agent(BusinessEvents agent)
 * 
 * @author dijadhav
 *
 */
@JsonDeserialize(as = AgentImpl.class)
public interface Agent extends Serializable, Monitorable {
	/**
	 * Get ServiceInstance object
	 * 
	 * @return the instance
	 */
	ServiceInstance getInstance();

	/**
	 * Set ServiceInstance
	 * 
	 * @param instance
	 *            the instance to set
	 */
	void setInstance(ServiceInstance instance);

	/**
	 * @return the agentName
	 */
	String getAgentName();

	/**
	 * @param agentName
	 *            the agentName to set
	 */
	void setAgentName(String agentName);

	/**
	 * @return the key
	 */
	String getKey();

	/**
	 * @return the agentId
	 */
	int getAgentId();

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	void setAgentId(int agentId);

	/**
	 * @return the agentType
	 */
	AgentType getAgentType();

	/**
	 * @param agentType
	 *            the agentType to set
	 */
	void setAgentType(AgentType agentType);

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
	 * @return Basic attributes to be included in a fact with entity status,count etc
	 */
	List<Attribute> getEntityStatusAttributes();
	
	/**
	 * @return Basic dimension attributes to be included in a fact
	 */
	public List<Attribute> getBasicFactAttributes();
	
	/**
	 * Set health status of the Agent
	 * 
	 * @return healthStatus
	 */
	public String getHealthStatus();
	
	/**
	 * Get health status of the Agent
	 * 
	 * @param healthStatus
	 */
	public void setHealthStatus(String healthStatus);

}
