/**
 * 
 */
package com.tibco.be.ws.scs.impl.repo;

import static com.tibco.be.ws.scs.SCSConstants.ATTR_MANAGED_PROJECTS_ENTRY_TYPE;
import static com.tibco.be.ws.scs.SCSConstants.LF;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECTS_ENTRY_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECTS_ENTRY_PATH_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECTS_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACTS_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_PATH_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_TYPE_NAME;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.tibco.be.ws.scs.IArtifactFilter;
import com.tibco.be.ws.scs.SCSCommitEntry;
import com.tibco.be.ws.scs.SCSException;
import com.tibco.be.ws.scs.impl.AbstractSCSIntegration;
import com.tibco.be.ws.scs.impl.filter.IFilterContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.net.mime.Base64Codec;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Base implementation class for integration with an Repository. Specific repositories should extend this class to provide necessary repository specific implementation details.
 * 
 * @.category public-api
 * @version 5.5.0
 * @since 5.5.0
 */
public abstract class AbstractRepositoryIntegration extends AbstractSCSIntegration {
	
	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractRepositoryIntegration.class);
    private static final int ERROR_RETRIES;
    private static final long RETRY_SLEEP_INTERVAL;
    
    static {
        Properties beProperties = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties();
        ERROR_RETRIES = Integer.parseInt(beProperties.getProperty("be.rms.repository.stream.error.retry", "3"));
        RETRY_SLEEP_INTERVAL = Long.parseLong(beProperties.getProperty("be.rms.repository.stream.retry.interval", "500"));
    }
	
	@Override
	public final String listManagedProjects(String repoRootURL, String userName, String password) throws SCSException {
		
		if (repoRootURL.startsWith("http")) {
    		password = validate(userName, password);
    		
    		String[] allEntries = list(repoRootURL, userName, password, false, null, null);
    		Arrays.sort(allEntries);
    		
    		//Create response xml
            XiFactory factory = XiFactoryFactory.newInstance();
            XiNode document = factory.createDocument();
            XiNode managedProjectsNode =
                    factory.createElement(MANAGED_PROJECTS_NAME);
            document.appendChild(managedProjectsNode);
            
            for (String anyEntry : allEntries) {
            	anyEntry = checkAndUpdateDirectoryPath(anyEntry);
                if (anyEntry.endsWith("/")) {
                    //Use this																																																													
                    XiNode entryNode =
                        factory.createElement(MANAGED_PROJECTS_ENTRY_NAME);
                    entryNode.setAttributeStringValue(ATTR_MANAGED_PROJECTS_ENTRY_TYPE, "dir");
                    XiNode pathNode =
                        factory.createElement(MANAGED_PROJECTS_ENTRY_PATH_NAME);
                    pathNode.setStringValue(anyEntry.substring(0, anyEntry.indexOf('/')));
                    entryNode.appendChild(pathNode);
                    managedProjectsNode.appendChild(entryNode);
                }
            }
            
            return serializeXiNode(document);
    	} else {
    		return getManagedProjectsFromFileSCS(repoRootURL);
    	}
	}
	
	@Override
	public final <F extends IFilterContext, I extends IArtifactFilter> String listManagedProjectArtifacts(String repoRootURL,
			String projectName, String updatePath, String userName, String password, F filterContext,
			I... artifactFilters) throws SCSException {
		
		if (repoRootURL.startsWith("http")) {
			password = validate(userName, password);

			String[] allEntries = list(repoRootURL, userName, password, true, projectName, updatePath);
    		
    		XiFactory factory = XiFactoryFactory.newInstance();
    		XiNode document = factory.createDocument();
    		XiNode projectArtifactsNode =
    				factory.createElement(MANAGED_PROJECT_ARTIFACTS_NAME);
    		document.appendChild(projectArtifactsNode);

    		for (String anyEntry : allEntries) {
    			anyEntry = checkAndUpdateDirectoryPath(anyEntry);
    			boolean filter = false;
    			for (I artifactFilter : artifactFilters) {
    				filter = filter & artifactFilter.shouldFilter(filterContext, anyEntry);
    				if (filter) {
    					break;
    				}
    			}

    			if (!anyEntry.endsWith("/") && !filter) {
    				//Use this
    				XiNode artifactNode =
    						factory.createElement(MANAGED_PROJECT_ARTIFACT_NAME);
    				XiNode artifactTypeNode =
    						factory.createElement(MANAGED_PROJECT_ARTIFACT_TYPE_NAME);
    				String fileExtension = anyEntry.substring(anyEntry.lastIndexOf('.') + 1, anyEntry.length());
    				artifactTypeNode.setStringValue(fileExtension);

    				XiNode pathNode =
    						factory.createElement(MANAGED_PROJECT_ARTIFACT_PATH_NAME);
    				pathNode.setStringValue("/" + anyEntry.substring(0, anyEntry.lastIndexOf('.')));
    				
    				artifactNode.appendChild(artifactTypeNode);
    				artifactNode.appendChild(pathNode);
    				
    				projectArtifactsNode.appendChild(artifactNode);
    			}
    		}
    		return serializeXiNode(document);
    	} else {
    		return getArtifactListFromFileSCS(repoRootURL, projectName, updatePath, filterContext, artifactFilters);
    	}
		
	}
	
	@Override
	public final boolean checkoutProjectArtifacts(String repoRootURL, String projectName, String destinationPath,
			String userName, String password) throws SCSException {
		password = validate(userName, password);
    	
    	StringBuilder stringBuilder = new StringBuilder(repoRootURL);
        stringBuilder.append("/");
        stringBuilder.append(projectName);
        
        String projectDirectory = stringBuilder.toString();
        
        boolean bReturn = false;
        // check if project destination directory exists, if not create it
        File projectDestinationDir = new File(destinationPath + "/" + projectName);
        if (!projectDestinationDir.exists()) {
        	File destinationDir = new File(destinationPath);
        	destinationDir.mkdirs();
        	bReturn = checkout(projectDirectory, destinationPath, projectName, userName, password);
        }
        return bReturn;
	}
	
	@Override
	public final String showFileContents(String repoRootURL, String projectName, String artifactPath,
			String artifactExtension, String userName, String password) throws SCSException {
    	String fileContent = null;
    	if (repoRootURL.startsWith("http")) {
    		password = validate(userName, password);
    		
    		StringBuilder stringBuilder = new StringBuilder(repoRootURL);
            stringBuilder.append("/");
            stringBuilder.append(projectName);
            stringBuilder.append(artifactPath);
            stringBuilder.append(".");
            stringBuilder.append(artifactExtension);
            
            String output = content(stringBuilder.toString(), userName, password);
    		
    		fileContent = output;
    	} else {
    		fileContent = getFileContents(repoRootURL, projectName, artifactPath, artifactExtension);
    	}
    	
    	// cleanup file content
    	int startIndex = fileContent.indexOf("<?xml version=\"1.0\"");
    	if (startIndex > 0) {
    		fileContent = fileContent.substring(startIndex, fileContent.length());
    	}
    	
    	return fileContent;
	}
	
	@Override
	public final void commitFileContents(String repoRootURL, String projectName, String artifactPath,
			String artifactExtension, byte[] artifactContents, String commitComments, Long revisionId)
			throws SCSException {
		
		List<SCSCommitEntry> svnCommitList = getSvnCommitEntriesByUser().get(revisionId);
    	if (svnCommitList == null) {
    		svnCommitList = new ArrayList<SCSCommitEntry>();
    		getSvnCommitEntriesByUser().put(revisionId, svnCommitList);
    	}
    		
		SCSCommitEntry addModifyEntry = new SCSCommitEntry(projectName, artifactPath, artifactExtension, commitComments, artifactContents, false);
		svnCommitList.add(addModifyEntry);
	}
	
	@Override
	public final void deleteFile(String repoRootURL, String projectName, String artifactPath, String artifactExtension,
			String deleteComments, Long revisionId) throws SCSException {
		
		List<SCSCommitEntry> svnCommitList = getSvnCommitEntriesByUser().get(revisionId);
    	if (svnCommitList == null) {
    		svnCommitList = new ArrayList<SCSCommitEntry>();
    		getSvnCommitEntriesByUser().put(revisionId, svnCommitList);
    	}
    		
		SCSCommitEntry deleteEntry = new SCSCommitEntry(projectName, artifactPath, artifactExtension, deleteComments, null, true);
		svnCommitList.add(deleteEntry);
	}

	@Override
	public final boolean fileExists(String repoRootURL, String projectName, String artifactPath, String artifactExtension)
			throws SCSException {
		return checkIfFileExists(repoRootURL, projectName, artifactPath, artifactExtension);
	}

	@Override
	public final String flushContentsToSCS(String repoRootURL, Long revisionId, String scsUserName, String scsPassword)
			throws SCSException {
		File baseLocationDirectory = new File(repoRootURL);
    	check(baseLocationDirectory);
    	
    	scsPassword = validate(scsUserName, scsPassword);

    	List<SCSCommitEntry> svnCommitList = getSvnCommitEntriesByUser().get(revisionId);
    	if (svnCommitList == null) {
    		LOGGER.log(Level.WARN, "Nothing is marked for commit. Possibly case for Build&Deploy.");
    		return null;
    	}
    	
    	boolean isNew = false;
    	String absoluteFilePath = null, commandOutput = null, commitComments = null, artifactsToCommitPath = "";
    	File artifactFile = null;
    	StringBuilder stringBuilder = new StringBuilder();
    	boolean bOutput = false;
    	
    	for (SCSCommitEntry svnCommitEntry : svnCommitList) {
    		stringBuilder.append(repoRootURL);
    		stringBuilder.append(File.separatorChar);
    		stringBuilder.append(svnCommitEntry.getProjectName());
    		stringBuilder.append(svnCommitEntry.getArtifactPath());
    		stringBuilder.append(".");
    		stringBuilder.append(svnCommitEntry.getArtifactExtn());

    		absoluteFilePath = stringBuilder.toString();
    		artifactFile = new File(absoluteFilePath);

    		if (!artifactFile.isAbsolute()) {
    			throw new SCSException(String.format("File path specified by %s needs to be absolute", absoluteFilePath));
    		}
    		artifactsToCommitPath += artifactFile.getAbsolutePath() + " ";
    		
    		if (svnCommitEntry.isDeleted()) {
    			// mark for deletion
    	    	if (artifactFile.exists()) {
    	    		bOutput = delete(artifactFile.getAbsolutePath(), scsUserName, scsPassword);
    	    		
    	    		if (!bOutput) {
    	    			throw new SCSException(String.format("Failed to delete artifact (%s) to SCS repository", artifactFile.getAbsolutePath()));
    	    		}
    	    	} else {
    	    		throw new SCSException(String.format("Specified artifact (%s) does not exist", artifactFile.getAbsolutePath()));
    	    	}
    		} else {
    			isNew = !artifactFile.exists();
    			
    			if (svnCommitEntry.getArtifactContents() == null) {
    				throw new SCSException("No contents to write for the artifact");
    			}
    			
    			FileOutputStream fos = null;
    	    	try {
    	    		fos = new FileOutputStream(artifactFile);
    	    		fos.write(svnCommitEntry.getArtifactContents());
    	    	} catch (IOException e) {
    	    		throw new SCSException(e);
    	    	} finally {
    	    		if (fos != null) {
    	    			try {
    	    				fos.close();
    	    			} catch (IOException e) {
    	    				throw new SCSException(String.format("Error closing output stream while writing artifact (%s) to SCS repository", artifactFile.getAbsolutePath()));
    	    			}
    	    		}
    	    	}
    			
    			if (isNew) {
    	    		bOutput = add(artifactFile.getAbsolutePath(), scsUserName, scsPassword);
    	    		
    	    		if (!bOutput) {
    	    			throw new SCSException(String.format("Failed to add artifact (%s) to SCS repository", artifactFile.getAbsolutePath()));
    	    		}
    	    	}
    		}
    		
    		if (commitComments == null) commitComments = svnCommitEntry.getComments();
    		stringBuilder.setLength(0);
    	}
    	
    	// remove the last comma
    	artifactsToCommitPath = artifactsToCommitPath.substring(0, artifactsToCommitPath.length() - 1);
    	
    	commandOutput = commit(artifactsToCommitPath, commitComments, scsUserName, scsPassword);

    	cleanupRevisionDetails(revisionId);
    	String commitRevision = (commandOutput != null && !commandOutput.isEmpty()) ? commandOutput : null;
    	
    	return commitRevision;
	}

	@Override
	public final void revertChangesFromSCS(String repoRootURL, Long revisionId, String scsUserName, String scsPassword)
			throws SCSException {
		File baseLocationDirectory = new File(repoRootURL);
    	check(baseLocationDirectory);

    	scsPassword = validate(scsUserName, scsPassword);

    	List<SCSCommitEntry> svnCommitList = getSvnCommitEntriesByUser().get(revisionId);
    	if (svnCommitList == null) {
    		LOGGER.log(Level.WARN, "Nothing was marked for commit. Possibly case for Build&Deploy.");
    	} else {
    		StringBuilder stringBuilder = new StringBuilder();
    		String absoluteFilePath = "";
    		File artifactFile = null;

    		for (SCSCommitEntry svnCommitEntry : svnCommitList) {
    			stringBuilder.append(repoRootURL);
    			stringBuilder.append(File.separatorChar);
    			stringBuilder.append(svnCommitEntry.getProjectName());
    			stringBuilder.append(svnCommitEntry.getArtifactPath());
    			stringBuilder.append(".");
    			stringBuilder.append(svnCommitEntry.getArtifactExtn());

    			absoluteFilePath = stringBuilder.toString();
    			artifactFile = new File(absoluteFilePath);

    			if (!artifactFile.isAbsolute()) {
    				throw new SCSException(String.format("File path specified by %s needs to be absolute", absoluteFilePath));
    			}

    			LOGGER.log(Level.DEBUG, "Reverting Artifact[%s].", artifactFile.getAbsolutePath());

    			if (revert(artifactFile.getAbsolutePath() , scsUserName, scsPassword)) {
    				if (!status(artifactFile.getAbsolutePath() , scsUserName, scsPassword)) {
    					LOGGER.log(Level.DEBUG, "Artifact[%s] is unversioned. Safe to delete.", artifactFile.getAbsolutePath());
    					artifactFile.delete();
    				}
    			} else {
    				throw new SCSException(String.format("Failed to revert artifact (%s)", artifactFile.getAbsolutePath()));
    			}

    			stringBuilder.setLength(0);
    		}

    		cleanupRevisionDetails(revisionId);
    	}
	}

	@Override
	public final void updateProjectArtifacts(String repoRootURL, String projectName, String scsUserName, String scsPassword)
			throws SCSException {
		File baseLocationDirectory = new File(repoRootURL);
    	check(baseLocationDirectory);

    	scsPassword = validate(scsUserName, scsPassword);
    	
    	LOGGER.log(Level.DEBUG, "Updating Project[%s].", (repoRootURL + File.separator + projectName));
		
		if (!update((repoRootURL + File.separator + projectName) , scsUserName, scsPassword)) {
			throw new SCSException(String.format("Failed to update project (%s)", (repoRootURL + File.separator + projectName)));
		}
	}
	
	
	/**
	 * Executes the Repository specific 'list' command and returns the list of projects/artifacts under the given repoUrl.
	 * 
	 * @param repoUrl URL of the repository to access
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * @param recursive Whether to recursively list artifacts from the folders. 
	 * @param projectName Name of the project, largely to list artifacts from within the project.
	 * @param updatePath The sub path under repoUrl. Providing this will list artifacts only under this sub path
	 *  
	 * @return The list of projects or artifacts under the given repoUrl
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract String[] list(String repoUrl, String userName, String password, boolean recursive, String projectName, String updatePath) throws SCSException;
	
	/**
	 * Executes the repository specific 'content' command and returns the content of the specified artifact.
	 * 
	 * @param artifactPath Path of the artifact whose content will be retrieved
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return Content of the specfied artifact
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract String content(String artifactPath, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'checkout' command to checkout the specified project.
	 * 
	 * @param projectDirectory The project directory(under repoUrl) that is to be checked out. The path would be the fully qualified path till the project.
	 * @param destinationDirectory Path where the project should be checked out. This path would be the local to where the server runs. Project artifacts should be checkedout within the <project name> directory, append the project name to this path if necessary.
	 * @param projectName Name of the project being checked out
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return True if the checkout is successful, false otherwise
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract boolean checkout(String projectDirectory, String destinationDirectory, String projectName, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'add' command to mark a new artifact for addition to the repository. Returns true if successfully added & false if it fails.
	 * 
	 * @param artifactPath Fully qualified path of the artifact that is to be marked for addition to the repository
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return True if add is successful, false otherwise
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract boolean add(String artifactPath, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'delete' command to mark an existing artifact for deletion from the repository.
	 * 
	 * @param artifactPath Fully qualified path of the artifact that is to be marked for deletion from the repository
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return True if delete is successful, false otherwise
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract boolean delete(String artifactPath, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'commit' command to commit all the artifact changes (add/modify/delete) to the repository and returns the commit revision.
	 * E.g. For svn commit returns an output as 'Committed revision 33432', this method should parse and return the commit revision only i.e. 33432
	 * 
	 * @param artifactPaths List of artifactPaths that need to be committed together. Multiple paths are separated by delimiters, these delimiters are repository specific, i.e. space for svn
	 * @param commitComments Comments to the associated with this commit
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return Commit revision number associated with this commit
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract String commit(String artifactPaths, String commitComments, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'revert' command to revert the local changes to the specified artifact. Returns true if artifact is successfully reverted, false otherwise.
	 * 
	 * @param artifactPath Fully qualified path of the artifact that is to be reverted
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return True if revert is successful, false otherwise
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract boolean revert(String artifactPath, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'status' command to check for the artifact status. Return true for versioned artifacts and false for unversioned artifacts.
	 * E.g. 'Status' command in svn will return the artifact prefixed with '?' if it is unversioned. The method should return false in those cases.
	 * 
	 * @param artifactPath Fully qualified path of the artifact whose status needs to be checked
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return True if status is successful, false otherwise
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract boolean status(String artifactPath, String userName, String password) throws SCSException;
	
	/**
	 * Executes repository specific 'update' command to reconcile the project with remote changes. Returns true if update is successful, false otherwise.
	 * 
	 * @param projectPath Fully qualified path of the project that needs to be updated
	 * @param userName Valid username that has permissions to access the above repository
	 * @param password User password
	 * 
	 * @return True if status is successful, false otherwise
	 * 
	 * @throws SCSException
	 * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
	 */
	protected abstract boolean update(String projectPath, String userName, String password) throws SCSException;
	
	
	/**
     * Check if SCS authentication went through successfully/failed. This is repository specific since every repository sends out different error codes/messages associated with authentication failures.
     * E.g. For svn, the authentication failure either return null/empty responses or error code E170001. 
     * 
     * @param commandOutput Output of the command executed
     * 
     * @throws SCSException
     * 
	 * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
     */
    protected abstract void checkForAuthenticationFailure(String commandOutput) throws SCSException;
	
	/**
     * Perform basic validation around mandatory attributes/fields
     * 
     * @param userName
     * @param password
     * @throws SCSException
     */
    private String validate(String userName, String password) throws SCSException {
    	if (getSCSCommandPath() == null || getSCSCommandPath().isEmpty()) {
			throw new SCSException(String.format("SCS Command path property %s not set.", "ws.scs.command.path"));
		}
		
		if ((userName == null || userName.isEmpty()) || (password == null || password.isEmpty())) {
			throw new SCSException("UserName/Password cannot be empty.");
		}
		
		try {
			password = Base64Codec.decodeBase64(password, "UTF-8");
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
		
		return password;
    }
	
	
    /**
     * Executes the given command. The command should just have the actual command and the associated parameters as List of strings, the executable path should not be included, that is picked up from the cdd property 'ws.scs.command.path'.
     * 
     * @param command Command to execute along with the necessary arguments as a list of strings
     * @param charset Character set used to read the output. If none if provided, it defaults to UTF-8
     * @param workingDir Directory from where the command is executed. Can be null in most cases, where command can be executed from anywhere. Cases where this is relevant is during 'checkout', where the project needs to checkout in the given directory.
     * 
     * @return Output of the command
     */
    protected final String execCommand(List<String> command, String charset, File workingDir) throws SCSException {
    	String commandOutput = null;
    	try {
    		if (command == null || command.size() == 0) throw new SCSException("Empty command specified");
    		
    		command.add(0, getSCSCommandPath());
    		String[] cmdArgs = command.toArray(new String[command.size()]);
    		LOGGER.log(Level.TRACE, "Command [%s]", String.join(" ", cmdArgs));
            Process procObject = Runtime.getRuntime().exec(cmdArgs, null, workingDir);
            
            commandOutput = getStreamOutput(procObject.getInputStream(), charset, false);
            LOGGER.log(Level.TRACE, "Command output [%s]", commandOutput);
            
            if (commandOutput == null || commandOutput.trim().isEmpty()) {
                String errorOutput = getStreamOutput(procObject.getErrorStream(), charset, true);
                if (errorOutput != null && !errorOutput.isEmpty()) throw new SCSException(errorOutput);
            }
            
            // check for authentication failures
            checkForAuthenticationFailure(commandOutput);
    	} catch (IOException e) {
            throw new SCSException(e);
    	} catch (Throwable th) {
            throw new SCSException(th);
    	}
    	return commandOutput;
    }
    
    private String getStreamOutput(final InputStream inputStream, String charset, final boolean isError) throws Exception {
    	try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
    		try (BufferedReader cmdStreamReader = new BufferedReader(new InputStreamReader(inputStream))) {
    			String cmdOutput = null; int cnt = 0;
    			int retries = (isError) ? ERROR_RETRIES : 1;
    			while (cnt <= retries) {
    				if (cmdStreamReader.ready() || !isError) {
    					if (isError) LOGGER.log(Level.TRACE, "Stream is ready after %s retries", (cnt+1));
    					while ((cmdOutput = cmdStreamReader.readLine()) != null) {
    						outputStream.write((cmdOutput + LF).getBytes());
    					}
    					cmdOutput = outputStream.toString(charset = (charset == null) ? "UTF-8" : charset);
    					break;
    				} else {
    					cnt++;
    					if (cnt <= retries) {
    						LOGGER.log(Level.TRACE, "Sleeping for %s sec and then retrying again", (RETRY_SLEEP_INTERVAL/1000));
    						Thread.sleep(RETRY_SLEEP_INTERVAL);
    					}
    				}
    			}
    			return cmdOutput;
    		}
    	}
    }
        
    /**
     * Clean up the revision and the item details associated with it
     * 
     * @param revisionId
     */
    private void cleanupRevisionDetails(Long revisionId) {
    	List<SCSCommitEntry> svnCommitList = getSvnCommitEntriesByUser().get(revisionId);
    	// clear off the commit entries
		svnCommitList.clear();
		svnCommitList = null;
		// and the mapping
		getSvnCommitEntriesByUser().remove(revisionId);
    }
    
    private String checkAndUpdateDirectoryPath(String pathEntry) {
    	// check only for directories
    	if (pathEntry.indexOf(".") == -1 && !pathEntry.endsWith("/")) {
    		pathEntry += "/";
    	}
    	return pathEntry;
    }
}
