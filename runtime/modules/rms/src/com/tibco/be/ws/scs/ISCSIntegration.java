package com.tibco.be.ws.scs;

import com.tibco.be.ws.scs.impl.filter.IFilterContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/11/11
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISCSIntegration {

    /**
     * List all the projects managed under the SCS repository
     * 
     * @param repoRootURL The url of the repository in which projects exist as immediate children
     * @param userName User name to access the SCS repository
     * @param password password to access the SCS repository
     * 
     * @return
     * 
     * @throws SCSException
     */
    public String listManagedProjects(String repoRootURL, String userName, String password) throws SCSException;

    /**
     * Fetch a list of all the artifacts under the given project from the SCS repository
     * 
     * @param repoRootURL
     * @param projectName
     * @param updatePath - A folder/file inside project. If null default to project.
     * @param userName User name to access the SCS repository
     * @param password password to access the SCS repository
     * 
     * @return
     * 
     * @throws SCSException
     */
    public <F extends IFilterContext, I extends IArtifactFilter> String listManagedProjectArtifacts(String repoRootURL, String projectName, String updatePath,  String userName, String password, F filterContext, I... artifactFilter) throws SCSException;
    
    /**
     * Fetch the contents of a given file from the SCS repository
     *  
     * @param repoRootURL
     * @param projectName - Name of the project in SCS.
     * @param artifactPath
     * @param artifactExtension
     * @param userName
     * @param password
     * 
     * @return
     * 
     * @throws SCSException 
     */
    public String showFileContents(String repoRootURL, 
                                   String projectName,
                                   String artifactPath,
                                   String artifactExtension,
                                   String userName, 
                                   String password) throws SCSException;

    /**
     * Checks if the given file exists
     * 
     * @param repoRootURL
     * @param projectName  - Name of the project in SCS.
     * @param artifactPath
     * 
     * @return
     * 
     * @throws SCSException
     */
    public boolean fileExists(String repoRootURL,
                              String projectName,
                              String artifactPath,
                              String artifactExtension) throws SCSException;

    /**
     * Deletes a file from the SCS repository
     * 
     * @param repoRootURL
     * @param projectName  - Name of the project in SCS.
     * @param artifactPath
     * @param deleteComments
     * @param revisionId
     * 
     * @throws SCSException
     */
    public void deleteFile(String repoRootURL,
                              String projectName,
                              String artifactPath,
                              String artifactExtension,
                              String deleteComments,
                              Long revisionId) throws SCSException;


    /**
     * Commits the file Contents to the SCS repository
     * 
     * @param repoRootURL
     * @param projectName
     * @param artifactPath
     * @param artifactExtension
     * @param artifactContents
     * @param commitComments
     * @param revisionId
     * 
     * @throws SCSException
     */
    public void commitFileContents(String repoRootURL,
                                   String projectName,
                                   String artifactPath,
                                   String artifactExtension,
                                   byte[] artifactContents,
                                   String commitComments,
                                   Long revisionId) throws SCSException;
    
    /**
     * Checks out project contents from the given repository location
     * 
     * @param repoRootURL
     * @param projectName
     * @param destinationDirectory
     * @param userName
     * @param password
     * 
     * @return
     * 
     * @throws SCSException
     */
    public boolean checkoutProjectArtifacts(String repoRootURL,
                                   String projectName,
                                   String destinationDirectory,
                                   String userName,
                                   String password) throws SCSException;
    
    /**
     * Set's the SCS command path
     * 
     * @param command
     * @throws SCSException
     */
    public void setSCSCommandPath(String command) throws SCSException;
    
    /**
     * Flush the changes to the SCS repository
     * 
     * @param repoRootURL
     * @param revisionId
     * @param scsUserName
     * @param scsPassword
     * @return
     * @throws SCSException
     */
    public String flushContentsToSCS(String repoRootURL, Long revisionId, String scsUserName, String scsPassword) throws SCSException;
    
    
    /**
     * Reverts the changes done as part of approve/reject
     * 
     * @param repoRootURL
     * @param revisionId
     * @param scsUserName
     * @param scsPassword
     * 
     * @throws SCSException
     */
    public void revertChangesFromSCS(String repoRootURL, Long revisionId, String scsUserName, String scsPassword) throws SCSException;
    
    /**
     * Updates Project artifacts
     * 
     * @param repoRootURL
     * @param projectName
     * @param scsUserName
     * @param scsPassword
     * 
     * @throws SCSException
     */
    public void updateProjectArtifacts(String repoRootURL, String projectName, String scsUserName, String scsPassword) throws SCSException;

}
