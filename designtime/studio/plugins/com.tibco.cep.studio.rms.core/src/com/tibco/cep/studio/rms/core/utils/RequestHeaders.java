/**
 * 
 */
package com.tibco.cep.studio.rms.core.utils;

/**
 * @author aathalye
 *
 */
public class RequestHeaders {
	
	private String username;
	
	private String password;
	
	private String requestedRoleString;//To be used to fetch worklists for certain role
	
	private String checkinRequestID;
	
	private String selectedProject;//Used to specify which project to checkout
	
	private String lockResourceRequested; //Resource for lock/unlock
	
	private String lockResourceRequestor; //Username requesting lock/unlock
	
	private String implementedResource; //The full path of the resource whose implementation is requested (VRF).
	
	private String requestedResourceName; //The name of the implementation
	
	private String extEntityFileName; //The name of the implementation
	
	private boolean optimized; //Use if compressing data
	
	/**
	 * Required for granular resource update case
	 */
	private String updatePath;
	
	/**
	 * Required for correlating checkin
	 */
	private String patternId;
	
	/**
	 * Required for getting a task's details
	 */
	private String revisionId;
	
	/**
	 * Required for comparing with local copy
	 */
	private long diffRevisionId1;
	
	/**
	 * Required for comparing with master copy
	 */
	private long diffRevisionId2;
	
	
	private String diffArtifactPath1;
	
	
	private String diffArtifactPath2;
	
	
	private String diffArtifactExtension1;
	
	
	private String diffArtifactExtension2;
	
	/**
	 * Required for every remote interaction 
	 */
	private String loggedInUserName;
	
	/**
	 * One or more roles separated by ;
	 */
	private String delegationRolesString;
	
	/**
	 * For audit trail
	 */
	private String artifactPath;
	
	/**
	 * For history of artifact.
	 */
	private String artifactExtension;
	
	private boolean generateDebugInfo;
	
	private boolean includeServiceVars;
	
	/**
	 * Generate ear or classes only
	 */
	private boolean generateClassesOnly;
	
	private double archiveVersion; 
	
	private boolean isStandaloneDecisionManager;
	
	private boolean clearApprovedTasks;
	
	private String loginToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	/**
	 * @return the requestedRoleString
	 */
	public final String getRequestedRoleString() {
		return requestedRoleString;
	}

	/**
	 * @param requestedRoleString the requestedRoleString to set
	 */
	public final void setRequestedRoleString(String requestedRoleString) {
		this.requestedRoleString = requestedRoleString;
	}

	/**
	 * @return the checkinRequestID
	 */
	public final String getCheckinRequestID() {
		return checkinRequestID;
	}

	/**
	 * @param checkinRequestID the checkinRequestID to set
	 */
	public final void setCheckinRequestID(String checkinRequestID) {
		this.checkinRequestID = checkinRequestID;
	}

	/**
	 * @return the selectedProject
	 */
	public final String getSelectedProject() {
		return selectedProject;
	}

	/**
	 * @param selectedProject the selectedProject to set
	 */
	public final void setSelectedProject(String selectedProject) {
		this.selectedProject = selectedProject;
	}

	/**
	 * @return the lockResourceRequested
	 */
	public final String getLockResourceRequested() {
		return lockResourceRequested;
	}

	/**
	 * @param lockResourceRequested the lockResourceRequested to set
	 */
	public final void setLockResourceRequested(String lockResourceRequested) {
		this.lockResourceRequested = lockResourceRequested;
	}

	/**
	 * @return the lockResourceRequestor
	 */
	public final String getLockResourceRequestor() {
		return lockResourceRequestor;
	}

	/**
	 * @param lockResourceRequestor the lockResourceRequestor to set
	 */
	public final void setLockResourceRequestor(String lockResourceRequestor) {
		this.lockResourceRequestor = lockResourceRequestor;
	}

	/**
	 * @return the implementedResource
	 */
	public final String getImplementedResource() {
		return implementedResource;
	}

	/**
	 * @param implementedResource the implementedResource to set
	 */
	public final void setImplementedResource(String implementedResource) {
		this.implementedResource = implementedResource;
	}

	/**
	 * @return the requestedResourceName
	 */
	public final String getRequestedResourceName() {
		return requestedResourceName;
	}

	/**
	 * @param requestedResourceName the requestedResourceName to set
	 */
	public final void setRequestedResourceName(String requestedResourceName) {
		this.requestedResourceName = requestedResourceName;
	}

	/**
	 * @return the optimized
	 */
	public final boolean getOptimized() {
		return optimized;
	}

	/**
	 * @param optimized the optimized to set
	 */
	public final void setOptimized(boolean optimized) {
		this.optimized = optimized;
	}

	/**
	 * @return the extEntityFileName
	 */
	public final String getExtEntityFileName() {
		return extEntityFileName;
	}

	/**
	 * @param extEntityFileName the extEntityFileName to set
	 */
	public final void setExtEntityFileName(String extEntityFileName) {
		this.extEntityFileName = extEntityFileName;
	}

	/**
	 * @return the updatePath
	 */
	public final String getUpdatePath() {
		return updatePath;
	}

	/**
	 * @param updatePath the updatePath to set
	 */
	public final void setUpdatePath(String updatePath) {
		this.updatePath = updatePath;
	}

	/**
	 * @return the patternId
	 */
	public final String getPatternId() {
		return patternId;
	}

	/**
	 * @param patternId the patternId to set
	 */
	public final void setPatternId(String patternId) {
		this.patternId = patternId;
	}

	/**
	 * @return the revisionId
	 */
	public final String getRevisionId() {
		return revisionId;
	}

	/**
	 * @param revisionId the revisionId to set
	 */
	public final void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}

	/**
	 * @return the artifactPath
	 */
	public final String getArtifactPath() {
		return artifactPath;
	}

	/**
	 * @param artifactPath the artifactPath to set
	 */
	public final void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	/**
	 * @return the loggedInUserName
	 */
	public final String getLoggedInUserName() {
		return loggedInUserName;
	}

	/**
	 * @param loggedInUserName the loggedInUserName to set
	 */
	public final void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}

	/**
	 * @return the generateDebugInfo
	 */
	public final boolean isGenerateDebugInfo() {
		return generateDebugInfo;
	}

	/**
	 * @param generateDebugInfo the generateDebugInfo to set
	 */
	public final void setGenerateDebugInfo(boolean generateDebugInfo) {
		this.generateDebugInfo = generateDebugInfo;
	}

	/**
	 * @return the includeServiceVars
	 */
	public final boolean isIncludeServiceVars() {
		return includeServiceVars;
	}

	/**
	 * @param includeServiceVars the includeServiceVars to set
	 */
	public final void setIncludeServiceVars(boolean includeServiceVars) {
		this.includeServiceVars = includeServiceVars;
	}

	/**
	 * @return the archiveVersion
	 */
	public final double getArchiveVersion() {
		return archiveVersion;
	}

	/**
	 * @param archiveVersion the archiveVersion to set
	 */
	public final void setArchiveVersion(double archiveVersion) {
		this.archiveVersion = archiveVersion;
	}

	/**
	 * @return the isStandaloneDecisionManager
	 */
	public final boolean isStandaloneDecisionManager() {
		return isStandaloneDecisionManager;
	}

	/**
	 * @param isStandaloneDecisionManager the isStandaloneDecisionManager to set
	 */
	public final void setStandaloneDecisionManager(boolean isStandaloneDecisionManager) {
		this.isStandaloneDecisionManager = isStandaloneDecisionManager;
	}

	/**
	 * @return the diffRevisionId1
	 */
	public final long getDiffRevisionId1() {
		return diffRevisionId1;
	}

	/**
	 * @param diffRevisionId1 the diffRevisionId1 to set
	 */
	public final void setDiffRevisionId1(long diffRevisionId1) {
		this.diffRevisionId1 = diffRevisionId1;
	}

	/**
	 * @return the diffRevisionId2
	 */
	public final long getDiffRevisionId2() {
		return diffRevisionId2;
	}

	/**
	 * @param diffRevisionId2 the diffRevisionId2 to set
	 */
	public final void setDiffRevisionId2(long diffRevisionId2) {
		this.diffRevisionId2 = diffRevisionId2;
	}

	/**
	 * @return the artifactExtension
	 */
	public final String getArtifactExtension() {
		return artifactExtension;
	}

	/**
	 * @param artifactExtension the artifactExtension to set
	 */
	public final void setArtifactExtension(String artifactExtension) {
		this.artifactExtension = artifactExtension;
	}

	/**
	 * @return the diffArtifactPath1
	 */
	public final String getDiffArtifactPath1() {
		return diffArtifactPath1;
	}

	/**
	 * @param diffArtifactPath1 the diffArtifactPath1 to set
	 */
	public final void setDiffArtifactPath1(String diffArtifactPath1) {
		this.diffArtifactPath1 = diffArtifactPath1;
	}

	/**
	 * @return the diffArtifactPath2
	 */
	public final String getDiffArtifactPath2() {
		return diffArtifactPath2;
	}

	/**
	 * @param diffArtifactPath2 the diffArtifactPath2 to set
	 */
	public final void setDiffArtifactPath2(String diffArtifactPath2) {
		this.diffArtifactPath2 = diffArtifactPath2;
	}

	/**
	 * @return the diffArtifactExtension1
	 */
	public final String getDiffArtifactExtension1() {
		return diffArtifactExtension1;
	}

	/**
	 * @param diffArtifactExtension1 the diffArtifactExtension1 to set
	 */
	public final void setDiffArtifactExtension1(String diffArtifactExtension1) {
		this.diffArtifactExtension1 = diffArtifactExtension1;
	}

	/**
	 * @return the diffArtifactExtension
	 */
	public final String getDiffArtifactExtension2() {
		return diffArtifactExtension2;
	}

	/**
	 * @param diffArtifactExtension the diffArtifactExtension to set
	 */
	public final void setDiffArtifactExtension2(String diffArtifactExtension2) {
		this.diffArtifactExtension2 = diffArtifactExtension2;
	}

	/**
	 * @return the generateClassesOnly
	 */
	public final boolean isGenerateClassesOnly() {
		return generateClassesOnly;
	}

	/**
	 * @param generateClassesOnly the generateClassesOnly to set
	 */
	public final void setGenerateClassesOnly(boolean generateClassesOnly) {
		this.generateClassesOnly = generateClassesOnly;
	}

	/**
	 * @return the delegationRolesString
	 */
	public final String getDelegationRolesString() {
		return delegationRolesString;
	}

	/**
	 * @param delegationRolesString the delegationRolesString to set
	 */
	public final void setDelegationRolesString(String delegationRolesString) {
		this.delegationRolesString = delegationRolesString;
	}

	public final boolean isClearApprovedTasks() {
		return clearApprovedTasks;
	}

	public final void setClearApprovedTasks(boolean clearApprovedTasks) {
		this.clearApprovedTasks = clearApprovedTasks;
	}
	
	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
}
