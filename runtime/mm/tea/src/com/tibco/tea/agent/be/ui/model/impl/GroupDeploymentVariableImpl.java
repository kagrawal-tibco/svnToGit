package com.tibco.tea.agent.be.ui.model.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;

/**
 * Deployment Variable for group operation
 * 
 * @author dijadhav
 *
 */
public class GroupDeploymentVariableImpl implements GroupDeploymentVariable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1148170422275675321L;
	private String name;
	private String value;
	private String description;
	private boolean hasEqualValue;
	private List<String> selectedInstances;
	private String deployedValue;
	private boolean deleted;
	private String effectiveValue;
	private boolean isNew;
	private DeploymentVariableType deploymentVariableType;
	private Map<String, Long> variablesVersion;
	private String type;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.ui.impl.GroupDeploymentVariable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.ui.impl.GroupDeploymentVariable#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.ui.impl.GroupDeploymentVariable#getValue()
	 */
	@Override
	public String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.ui.impl.GroupDeploymentVariable#setValue(java.
	 * lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.ui.impl.GroupDeploymentVariable#
	 * getSelectedInstances ()
	 */
	@Override
	public List<String> getSelectedInstances() {
		return selectedInstances;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.ui.impl.GroupDeploymentVariable#
	 * setSelectedInstances (java.util.List)
	 */
	@Override
	public void setSelectedInstances(List<String> selectedInstances) {
		this.selectedInstances = selectedInstances;
	}

	@Override
	public void setHasEqualValue(boolean hasEqualValue) {
		this.hasEqualValue = hasEqualValue;
	}

	@Override
	public boolean getHasEqualValue() {
		return this.hasEqualValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupDeploymentVariableImpl other = (GroupDeploymentVariableImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public void setDeployedValue(String deployedValue) {
		this.deployedValue = deployedValue;
	}

	@Override
	public String getDeployedValue() {
		return this.deployedValue;
	}

	/**
	 * @return the deleted
	 */
	public boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getEffectiveValue() {
		return effectiveValue;
	}

	@Override
	public void setEffectiveValue(String effectiveValue) {
		this.effectiveValue = effectiveValue;
	}

	@Override
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Override
	public boolean getIsNew() {
		return this.isNew;
	}

	@Override
	public void setVariablesVersion(Map<String, Long> variablesVersion) {
		this.variablesVersion = variablesVersion;
	}

	@Override
	public Map<String, Long> getVariablesVersion() {
		return variablesVersion;
	}

	@Override
	public DeploymentVariableType getDeploymentVariableType() {
		return deploymentVariableType;
	}

	@Override
	public void setDeploymentVariableType(DeploymentVariableType deploymentVariableType) {
		this.deploymentVariableType = deploymentVariableType;
	}
	@Override
	public String getType() {
		return type;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
}
