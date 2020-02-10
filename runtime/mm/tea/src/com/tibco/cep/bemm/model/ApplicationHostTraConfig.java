package com.tibco.cep.bemm.model;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.ApplicationHostTraConfigImpl;
@JsonDeserialize(as = ApplicationHostTraConfigImpl.class)
public interface ApplicationHostTraConfig {

	/**
	 * @return the isUploadFile
	 */
	public abstract boolean isUploadFile();

	/**
	 * @param isUploadFile the isUploadFile to set
	 */
	public abstract void setUploadFile(boolean isUploadFile);

	/**
	 * @return the hostId
	 */
	public abstract String getHostId();

	/**
	 * @param hostId the hostId to set
	 */
	public abstract void setHostId(String hostId);

	/**
	 * @return the deploymentPath
	 */
	public abstract String getDeploymentPath();

	/**
	 * @param deploymentPath the deploymentPath to set
	 */
	public abstract void setDeploymentPath(String deploymentPath);

	/**
	 * @return the uploadTRAfile
	 */
	@JsonDeserialize(as = FileDataSource.class)
	public abstract DataSource getUploadTRAfile();

	/**
	 * @param uploadTRAfile the uploadTRAfile to set
	 */
	@JsonDeserialize(as = FileDataSource.class)
	public abstract void setUploadTRAfile(DataSource uploadTRAfile);

	/**
	 * @return the traFilePath
	 */
	public abstract String getTraFilePath();

	/**
	 * @param traFilePath the traFilePath to set
	 */
	public abstract void setTraFilePath(String traFilePath);

}