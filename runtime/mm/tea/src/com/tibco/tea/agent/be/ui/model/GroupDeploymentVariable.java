package com.tibco.tea.agent.be.ui.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.tea.agent.be.ui.model.impl.GroupDeploymentVariableImpl;

@JsonDeserialize(as = GroupDeploymentVariableImpl.class)
public interface GroupDeploymentVariable {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @param name
	 *            the name to set
	 */
	void setName(String name);

	/**
	 * @return the value
	 */
	String getValue();

	/**
	 * @param value
	 *            the value to set
	 */
	void setValue(String value);

	/**
	 * @return the desc
	 */
	String getDescription();

	/**
	 * @param desc
	 *            the desc to set
	 */
	void setDescription(String value);

	/**
	 * @return
	 */
	DeploymentVariableType getDeploymentVariableType();

	/**
	 * @param type
	 */
	void setDeploymentVariableType(DeploymentVariableType type);

	/**
	 * @return the selectedInstances
	 */
	List<String> getSelectedInstances();

	/**
	 * @param selectedInstances
	 *            the selectedInstances to set
	 */
	void setSelectedInstances(List<String> selectedInstances);

	/**
	 * Set whether instances from this group has same value for the deployment
	 * variable.
	 * 
	 * @param hasEqualValue
	 */
	void setHasEqualValue(boolean hasEqualValue);

	/**
	 * Get whether instances from this group has same value for the deployment
	 * variable.
	 * 
	 * @param hasEqualValue
	 */
	boolean getHasEqualValue();

	/**
	 * Set Deployed value
	 * 
	 * @param deployedValue
	 *            - Deployed value
	 */
	void setDeployedValue(String deployedValue);

	/**
	 * Get Deployed value
	 * 
	 * @return Deployed value
	 */
	String getDeployedValue();

	/**
	 * Set whether property is deleted or not
	 * 
	 * @param deleted
	 */
	void setDeleted(boolean deleted);

	/**
	 * Whether property is deleted or not
	 * 
	 * @param hasEqualValue
	 */
	boolean getDeleted();

	/**
	 * Get Effective value
	 * 
	 * @return
	 */
	String getEffectiveValue();

	/**
	 * Set Effective value
	 * 
	 * @return
	 */
	void setEffectiveValue(String effectiveValue);

	/**
	 * Set whether property is new or not
	 * 
	 * @param deleted
	 */
	void setIsNew(boolean isNew);

	/**
	 * Whether property is new or not
	 * 
	 * @param hasEqualValue
	 */
	boolean getIsNew();

	/**
	 * @param variablesVersion
	 */
	void setVariablesVersion(Map<String, Long> variablesVersion);

	/**
	 * @return variablesVersion
	 */
	Map<String, Long> getVariablesVersion();

	/**
	 * @param variablesVersion
	 */
	String getType();

	/**
	 * Set type
	 * 
	 * @param type
	 */
	void setType(String type);
}