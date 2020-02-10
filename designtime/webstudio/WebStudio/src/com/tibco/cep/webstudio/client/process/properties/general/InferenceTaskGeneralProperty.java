package com.tibco.cep.webstudio.client.process.properties.general;

import java.util.Set;

import com.tibco.cep.webstudio.client.process.properties.InferenceTaskProperty;

/**
 * This method is used to populate the general properties of inference task.
 * 
 * @author dijadhav
 * 
 */
public class InferenceTaskGeneralProperty extends GeneralProperty implements InferenceTaskProperty{
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -6908815841628448502L;

	/**
	 * Boolean value which indicates the checkpoint is enabled or not.
	 */
	private boolean isCheckPoint;
	
	private Set<String>resources;
	/**
	 * @return the isCheckPoint
	 */
	public boolean isCheckPoint() {
		return isCheckPoint;
	}

	/**
	 * @param isCheckPoint
	 *            the isCheckPoint to set
	 */
	public void setCheckPoint(boolean isCheckPoint) {
		this.isCheckPoint = isCheckPoint;
	}

	/**
	 * @return the resources
	 */
	public Set<String> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Set<String> resources) {
		this.resources = resources;
	}

}
