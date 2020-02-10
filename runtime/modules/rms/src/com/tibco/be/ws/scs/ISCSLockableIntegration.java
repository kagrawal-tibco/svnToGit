package com.tibco.be.ws.scs;

public interface ISCSLockableIntegration extends ISCSIntegration {

    /**
     * 
     * @param repoRootURL
     * @param userName
     * @param password
     * @param projectName
     * @param artifactPath
     * @param artifactExtn
     * @return
     */
	public boolean lockArtifact(String repoRootURL, String userName, String password,
			String projectName, String artifactPath, String artifactExtn) throws SCSException;

	/**
	 * 
	 * @param repoRootURL
	 * @param userName
	 * @param password
	 * @param projectName
	 * @param artifactPath
	 * @param artifactExtn
	 * @return
	 */
	public boolean unlockArtifact(String repoRootURL, String userName,
			String password, String projectName, String artifactPath,
			String artifactExtn, String scsOperation) throws SCSException;

	/**
	 * 
	 * @param repoRootURL
	 * @param userName
	 * @param password
	 * @param projectName
	 * @param artifactPath
	 * @param artifactExtn
	 * @return
	 */
	public String getExistingLockInfo(String repoRootURL, String userName,
			String password, String projectName, String artifactPath, String artifactExtn) throws SCSException;

	/**
	 * 
	 * @param repoRootURL
	 * @param userName
	 * @param password
	 * @param projectName
	 * @param artifactPath
	 * @param artifactExtn
	 * @return
	 */
	public String getExistingLockOwner(String repoRootURL, String userName,
			String password, String projectName, String artifactPath, String artifactExtn) throws SCSException;
	
}
