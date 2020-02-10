/**
 * 
 */
package com.tibco.cep.bemm.model.impl;

import javax.activation.DataSource;

import com.tibco.cep.bemm.model.ApplicationHostTraConfig;

/**
 * @author dijadhav
 *
 */
public class ApplicationHostTraConfigImpl implements ApplicationHostTraConfig {
	private boolean isUploadFile;
	private String hostId;
	private String deploymentPath;
	private DataSource uploadTRAfile;
	private String traFilePath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#isUploadFile()
	 */
	@Override
	public boolean isUploadFile() {
		return isUploadFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#setUploadFile(
	 * boolean)
	 */
	@Override
	public void setUploadFile(boolean isUploadFile) {
		this.isUploadFile = isUploadFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#getHostId()
	 */
	@Override
	public String getHostId() {
		return hostId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#setHostId(java
	 * .lang.String)
	 */
	@Override
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#getDeploymentPath
	 * ()
	 */
	@Override
	public String getDeploymentPath() {
		return deploymentPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#setDeploymentPath
	 * (java.lang.String)
	 */
	@Override
	public void setDeploymentPath(String deploymentPath) {
		this.deploymentPath = deploymentPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#getUploadTRAfile()
	 */
	@Override
	public DataSource getUploadTRAfile() {
		return uploadTRAfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#setUploadTRAfile
	 * (javax.activation.DataSource)
	 */
	@Override
	public void setUploadTRAfile(DataSource uploadTRAfile) {
		this.uploadTRAfile = uploadTRAfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#getTraFilePath()
	 */
	@Override
	public String getTraFilePath() {
		return traFilePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.ApplicationHostTraConfig#setTraFilePath
	 * (java.lang.String)
	 */
	@Override
	public void setTraFilePath(String traFilePath) {
		this.traFilePath = traFilePath;
	}

}
