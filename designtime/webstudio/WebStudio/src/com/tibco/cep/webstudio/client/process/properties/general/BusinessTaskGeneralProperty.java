package com.tibco.cep.webstudio.client.process.properties.general;

import java.util.List;

import com.tibco.cep.webstudio.client.process.properties.BusinessTaskProperty;

/**
 * This class represents the business rule properties.
 * 
 * @author dijadhav
 * 
 */
public class BusinessTaskGeneralProperty extends GeneralProperty implements BusinessTaskProperty{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6063644346818196122L;
	
	/**
	 * Boolean value which indicates the checkpoint is enabled or not.
	 */
	private boolean isCheckPoint;

	/**
	 * Variable for selected resource.
	 */
	private String resource;

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
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	private List<VRFImplementationURI> impelmentationURIList;

	/**
	 * @return the impelmentationURIList
	 */
	public List<VRFImplementationURI> getImpelmentationURIList() {
		return impelmentationURIList;
	}

	/**
	 * @param impelmentationURIList
	 *            the impelmentationURIList to set
	 */
	public void setImpelmentationURIList(
			List<VRFImplementationURI> impelmentationURIList) {
		this.impelmentationURIList = impelmentationURIList;
	}
}
