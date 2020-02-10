package com.tibco.cep.bemm.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * This class holds the agent details.
 * 
 * @author dijadhav
 *
 */
public class AgentImpl extends AbstractMonitorableEntity implements Agent {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -2459478198104920406L;

	/**
	 * Name of the agent
	 */
	private String agentName;

	/**
	 * Id of the agent
	 */
	private int agentId = -1;
	/**
	 * Type of the agent.
	 */
	private AgentType agentType;

	/**
	 * Service Instance object
	 */
	@JsonIgnore(value = true)
	private ServiceInstance instance;
	
	/**
	 * health state of application
	 */
	private String healthStatus=BEEntityHealthStatus.ok.getHealthStatus(); //default health ok

	public AgentImpl() {
		status = BETeaAgentStatus.STOPPED.getStatus();
	}

	/**
	 * @return the instance
	 */
	public ServiceInstance getInstance() {
		return instance;
	}

	/**
	 * @param instance
	 *            the instance to set
	 */
	public void setInstance(ServiceInstance instance) {
		this.instance = instance;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}
	
	/**
	 * @return the agentName
	 */
	
	@Override
	public String getName() {
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
	 * @return the key
	 */
	public String getKey() {
		return instance.getKey() + "/" + agentName;
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
	 * @return the agentType
	 */
	public AgentType getAgentType() {
		return agentType;
	}

	/**
	 * @param agentType
	 *            the agentType to set
	 */
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		super.setStatus(status);
	}

	@Override
	public ENTITY_TYPE getType() {
		ENTITY_TYPE entityType = null;
		switch (agentType) {
		case INFERENCE:
			entityType = ENTITY_TYPE.INFERENCE_AGENT;
			break;
		case CACHE:
			entityType = ENTITY_TYPE.CACHE_AGENT;
			break;
		case QUERY:
			entityType = ENTITY_TYPE.QUERY_AGENT;
			break;
		case PROCESS:
			entityType = ENTITY_TYPE.PROCESS_AGENT;
			break;
		case DASHBOARD:
			entityType = ENTITY_TYPE.DASHBOARD_AGENT;
			break;
		default:
			entityType = ENTITY_TYPE.INFERENCE_AGENT;
		}
		return entityType;
	}
	
	//TODO : Remove this method : not used anymore
	@Override
	public Map<String, Object> getBasicAttributes() {
		Map<String, Object> basicAttributes = new HashMap<>();
		basicAttributes.put(MetricAttribute.CLUSTER, this.getInstance().getHost().getApplication().getClusterName());
		basicAttributes.put(MetricAttribute.HOST, this.getInstance().getHost().getHostName());
		basicAttributes.put(MetricAttribute.PU_INSTANCE, this.getInstance().getName());
		basicAttributes.put(MetricAttribute.AGENT_TYPE, this.getAgentType());
		basicAttributes.put(MetricAttribute.AGENT_NAME, this.getAgentName());
		return basicAttributes;
	}
	
	@Override
	public List<Attribute> getBasicFactAttributes() {
		List<Attribute> basicAttributes = new ArrayList<Attribute>();
		basicAttributes.add(new Attribute(MetricAttribute.CLUSTER, this.getInstance().getHost().getApplication().getName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE, this.getInstance().getName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_NAME, null==this.getInstance().getPuId()?"":this.getInstance().getPuId()));
		basicAttributes.add(new Attribute(MetricAttribute.AGENT_TYPE, this.getAgentType().getType()));
		basicAttributes.add(new Attribute(MetricAttribute.AGENT_NAME, this.getAgentName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE_ISACTIVE, (getInstance().isRunning()==true)?1:0));
		basicAttributes.add(new Attribute(MetricAttribute.TIMESTAMP, System.currentTimeMillis()));
		
		return basicAttributes;
	}
	
	@Override
	public List<Attribute> getEntityStatusAttributes() {
		List<Attribute> basicAttributes = new ArrayList<Attribute>();
		basicAttributes.add(new Attribute(MetricAttribute.CLUSTER_DUMMY,MetricAttribute.CLUSTER_DUMMY_VALUE));
		basicAttributes.add(new Attribute(MetricAttribute.CLUSTER, this.getInstance().getHost().getApplication().getName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE, this.getInstance().getName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE_ISACTIVE, (getInstance().isRunning()==true)?1:0));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE_COUNT, 1));
		basicAttributes.add(new Attribute(MetricAttribute.AGENT_NAME, this.getAgentName()));
		basicAttributes.add(new Attribute(MetricAttribute.AGENT_COUNT,1));
		basicAttributes.add(new Attribute(MetricAttribute.TIMESTAMP, System.currentTimeMillis()));
		
		return basicAttributes;
	}
	
	

	@Override
	public ConnectionInfo getConnectionInfo(String connectionType) {
		return null;
	}
	
	@Override
	public String getHealthStatus() {
		return healthStatus;
	}
	
	@Override
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

}
