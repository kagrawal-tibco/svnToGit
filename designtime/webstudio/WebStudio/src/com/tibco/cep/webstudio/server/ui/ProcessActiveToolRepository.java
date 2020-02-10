package com.tibco.cep.webstudio.server.ui;

/**
 * This class is used to manage the active tool data
 * 
 * @author dijadhav
 * 
 */
public class ProcessActiveToolRepository {
	private static ProcessActiveToolRepository instance;
	private String activeToolEmfType;
	private String activeToolExtendedType;
	private String activeToolType;
	private String artifactName;
	private String arifactPath;
	private String viewId;
	
	private ProcessActiveToolRepository() {
	}

	public static ProcessActiveToolRepository getInstance() {
		if (instance == null) {
			instance = new ProcessActiveToolRepository();
		}
		return instance;
	}

	/**
	 * @return the activeToolEmfType
	 */
	public String getActiveToolEmfType() {
		return activeToolEmfType;
	}

	/**
	 * @param activeToolEmfType
	 *            the activeToolEmfType to set
	 */
	public void setActiveToolEmfType(String activeToolEmfType) {
		this.activeToolEmfType = activeToolEmfType;
	}

	/**
	 * @return the activeToolExtendedType
	 */
	public String getActiveToolExtendedType() {
		return activeToolExtendedType;
	}

	/**
	 * @param activeToolExtendedType
	 *            the activeToolExtendedType to set
	 */
	public void setActiveToolExtendedType(String activeToolExtendedType) {
		this.activeToolExtendedType = activeToolExtendedType;
	}

	/**
	 * @return the activeToolType
	 */
	public String getActiveToolType() {
		return activeToolType;
	}

	/**
	 * @param activeToolType
	 *            the activeToolType to set
	 */
	public void setActiveToolType(String activeToolType) {
		this.activeToolType = activeToolType;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	/**
	 * @return the arifactPath
	 */
	public String getArifactPath() {
		return arifactPath;
	}

	/**
	 * @param arifactPath the arifactPath to set
	 */
	public void setArifactPath(String arifactPath) {
		this.arifactPath = arifactPath;
	}

	/**
	 * @return the viewId
	 */
	public String getViewId() {
		return viewId;
	}

	/**
	 * @param viewId the viewId to set
	 */
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
}
